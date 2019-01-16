/*
 * Copyright LKK Health Products Group Limited
 */

package com.lkkhpg.dsis.common.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.lkkhpg.dsis.common.config.dto.SpmSalesOrganization;
import com.lkkhpg.dsis.common.config.mapper.SpmSalesOrganizationMapper;
import com.lkkhpg.dsis.common.constant.MemberConstants;
import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.common.constant.VoucherConstants;
import com.lkkhpg.dsis.common.exception.CommMemberException;
import com.lkkhpg.dsis.common.exception.CommVoucherException;
import com.lkkhpg.dsis.common.member.dto.Member;
import com.lkkhpg.dsis.common.member.dto.MemberList;
import com.lkkhpg.dsis.common.member.mapper.MemberListMapper;
import com.lkkhpg.dsis.common.member.mapper.MemberMapper;
import com.lkkhpg.dsis.common.order.dto.OrderPayment;
import com.lkkhpg.dsis.common.order.dto.SalesOrder;
import com.lkkhpg.dsis.common.order.dto.SalesVoucher;
import com.lkkhpg.dsis.common.order.mapper.OrderPaymentMapper;
import com.lkkhpg.dsis.common.order.mapper.SalesOrderMapper;
import com.lkkhpg.dsis.common.order.mapper.SalesVoucherMapper;
import com.lkkhpg.dsis.common.promotion.dto.PdmVoucherGift;
import com.lkkhpg.dsis.common.promotion.dto.PdmVoucherItem;
import com.lkkhpg.dsis.common.promotion.dto.PdmVoucherMember;
import com.lkkhpg.dsis.common.promotion.dto.Voucher;
import com.lkkhpg.dsis.common.promotion.dto.VoucherAssign;
import com.lkkhpg.dsis.common.promotion.dto.VoucherTransaction;
import com.lkkhpg.dsis.common.promotion.mapper.PdmVoucherGiftMapper;
import com.lkkhpg.dsis.common.promotion.mapper.PdmVoucherItemMapper;
import com.lkkhpg.dsis.common.promotion.mapper.PdmVoucherMemberMapper;
import com.lkkhpg.dsis.common.promotion.mapper.VoucherAssignMapper;
import com.lkkhpg.dsis.common.promotion.mapper.VoucherMapper;
import com.lkkhpg.dsis.common.service.IVoucherQuantityTrxService;
import com.lkkhpg.dsis.common.service.IVoucherService;
import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;

/**
 * 优惠券Service接口实现.
 * 
 * @author hand
 */
@Service
@Transactional
public class VoucherServiceImpl implements IVoucherService {

    private Logger logger = LoggerFactory.getLogger(VoucherServiceImpl.class);

    @Autowired
    private VoucherMapper voucherMapper;

    @Autowired
    private VoucherAssignMapper voucherAssignMapper;

    @Autowired
    private PdmVoucherMemberMapper pdmVoucherMemberMapper;

    @Autowired
    private PdmVoucherGiftMapper pdmVoucherGiftMapper;

    @Autowired
    private PdmVoucherItemMapper pdmVoucherItemMapper;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private MemberListMapper memberListMapper;

    @Autowired
    private OrderPaymentMapper orderPaymentMapper;

    @Autowired
    private SalesOrderMapper salesOrderMapper;

    @Autowired
    private SpmSalesOrganizationMapper spmSalesOrganizationMapper;

    @Autowired
    private SalesVoucherMapper salesVoucherMapper;

    @Autowired
    private IVoucherQuantityTrxService voucherQuantityTrxService;
    /**
     * 已发放.
     */
    private static final String RELD = "RELD";

    /**
     * 使用类型--销售组织.
     */
    private static final String SALES = "SALES";

    /**
     * 使用类型--会员.
     */
    private static final String MEM = "MEM";

    @Override
    public boolean createVoucher(IRequest request, Voucher voucher) {
        voucherMapper.insert(voucher);
        return true;
    };

    @Override
    public boolean cancelVoucher(IRequest request, Voucher voucher) {
        voucherMapper.updateByPrimaryKey(voucher);
        return true;
    };

    @Override
    public boolean applyVoucher(IRequest request, Long voucherId, Long memberId) {
        VoucherAssign voucherAssign = new VoucherAssign();
        voucherAssign.setVoucherId(voucherId);
        voucherAssign.setMemberId(memberId);
        voucherAssignMapper.insert(voucherAssign);
        return true;
    };

    @Override
    public List<Voucher> queryVoucher(IRequest request, Voucher voucher, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return voucherMapper.queryVouchers(voucher);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Voucher> saveVoucher(IRequest request, @StdWho Voucher voucher) throws CommVoucherException {
        List<PdmVoucherMember> voucherMember = voucher.getPdmVoucherMembers();
        if (voucherMember != null) {
            voucher.setPdmVoucherMembers(voucherMember);
        }
        List<PdmVoucherItem> voucherItem = voucher.getPdmVoucherItems();
        if (voucherItem != null) {
            voucher.setPdmVoucherItems(voucherItem);
        }
        List<PdmVoucherGift> voucherGift = voucher.getPdmVoucherGifts();
        if (voucherGift != null) {
            voucher.setPdmVoucherGifts(voucherGift);
        }
        // 结束日期时分秒
        Calendar endCalendar=Calendar.getInstance();
        endCalendar.setTime(voucher.getEndActiveDate()); 
        endCalendar.set(Calendar.HOUR, 23);
        endCalendar.set(Calendar.MINUTE, 59);
        endCalendar.set(Calendar.SECOND, 59);
        //endCalendar.set(Calendar.MILLISECOND, 0);
        voucher.setEndActiveDate(endCalendar.getTime());
        
        // 开始日期时分秒
        Calendar atartCalendar=Calendar.getInstance();
        atartCalendar.setTime(voucher.getStartActiveDate());
        atartCalendar.set(Calendar.HOUR_OF_DAY, 0);
        atartCalendar.set(Calendar.MINUTE, 0);
        atartCalendar.set(Calendar.SECOND, 0);
        //atartCalendar.set(Calendar.MILLISECOND, 456);
        voucher.setStartActiveDate(atartCalendar.getTime());
        
        if (voucher.getVoucherId() == null) {
            if (voucherMapper.selectByVoucherCode(voucher.getVoucherCode()) == null) {
                voucherMapper.insert(voucher);
            } else {
                if (logger.isDebugEnabled()) {
                    logger.debug(CommVoucherException.MSG_ERROR_PDM_VOUCHER_CODE_UNIQUE + "{}:",
                            voucher.getVoucherCode());
                }
                throw new CommVoucherException(CommVoucherException.MSG_ERROR_PDM_VOUCHER_CODE_UNIQUE,
                        new Object[] { voucher.getVoucherCode() });
            }
        } else {
            Voucher vc = new Voucher();
            vc.setVoucherId(voucher.getVoucherId());
            vc.setEnabledFlag(voucher.getEnabledFlag());
            vc.setSalesOrgId(voucher.getSalesOrgId());
            if(voucherMapper.queryVouchers(vc).size()>0){
                VoucherAssign vas = new VoucherAssign();
                vas.setVoucherId(voucher.getVoucherId());
                List<VoucherAssign> ls = voucherAssignMapper.selectVoucherAssign(vas);
                if(ls.size()>0){
                    //throw new CommVoucherException("该优惠券已经发放",  new Object [] {});
                    List<Voucher> vouchers = new ArrayList<>();
                    voucher.setFlag("Y");
                    vouchers.add(voucher);
                    return vouchers;
                }else{
                    voucherMapper.updateByPrimaryKeySelective(voucher);
                }
            }else{
                voucherMapper.updateByPrimaryKeySelective(vc);
                List<Voucher> vouchers = new ArrayList<>();
                vouchers.add(voucher);
                return vouchers;
            }
        }
        self().savePdmVoucherMember(voucher);
        self().savePdmVoucherItem(voucher);
        self().savePdmVoucherGift(voucher);
        List<Voucher> vouchers = new ArrayList<>();
        vouchers.add(voucher);
        return vouchers;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<PdmVoucherMember> savePdmVoucherMember(Voucher voucher) throws CommVoucherException {
        List<PdmVoucherMember> pdmVoucherMembers = voucher.getPdmVoucherMembers();
        if (!pdmVoucherMembers.isEmpty()) {
            for (PdmVoucherMember pdmVoucherMember : pdmVoucherMembers) {
                if (pdmVoucherMember.getVoucherMemberId() == null) {
                    pdmVoucherMember.setVoucherId(voucher.getVoucherId());
                    pdmVoucherMemberMapper.insert(pdmVoucherMember);
                }
            }
        }
        return pdmVoucherMembers;
    }

    @Override
    public List<PdmVoucherItem> savePdmVoucherItem(Voucher voucher) {
        List<PdmVoucherItem> pdmVoucherItems = voucher.getPdmVoucherItems();
        if (pdmVoucherItems != null) {
            for (PdmVoucherItem pdmVoucherItem : pdmVoucherItems) {
                if (pdmVoucherItem.getVoucherItemId() == null) {
                    pdmVoucherItem.setVoucherId(voucher.getVoucherId());
                    pdmVoucherItemMapper.insert(pdmVoucherItem);
                } else {
                    pdmVoucherItemMapper.updateByPrimaryKeySelective(pdmVoucherItem);
                }
            }
        }
        return pdmVoucherItems;
    }

    @Override
    public List<PdmVoucherGift> savePdmVoucherGift(Voucher voucher) {
        List<PdmVoucherGift> pdmVoucherGifts = voucher.getPdmVoucherGifts();
        if (pdmVoucherGifts != null) {
            for (PdmVoucherGift pdmVoucherGift : pdmVoucherGifts) {
                if (pdmVoucherGift.getVoucherGiftId() == null) {
                    pdmVoucherGift.setVoucherId(voucher.getVoucherId());
                    pdmVoucherGiftMapper.insert(pdmVoucherGift);
                } else {
                    pdmVoucherGiftMapper.updateByPrimaryKeySelective(pdmVoucherGift);
                }
            }
        }
        return pdmVoucherGifts;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Voucher> saveVoucherAssign(IRequest requestContext, @StdWho Voucher voucher)
            throws CommVoucherException {
        voucher.setStatus(RELD);
        List<Voucher> vouchers = self().saveVoucher(requestContext, voucher);
        if("Y".equals(vouchers.get(0).getFlag())){
            return vouchers;
        }
        Voucher voucher2 = voucherMapper.selectByPrimaryKey(voucher.getVoucherId());
        if (voucher2 != null) {
            if (SALES.equals(voucher2.getAppScope())) {
                VoucherAssign assign = new VoucherAssign();
                assign.setSalesOrgId(voucher2.getSalesOrgId());
                assign.setVoucherId(voucher2.getVoucherId());
                assign.setQuantity(voucher2.getQuantity());
                assign.setStatus(voucher2.getStatus());
                assign.setAssignType(SALES);
                assign.setMarketId(voucher2.getMarketId());
                self().createVoucherAssign(requestContext, assign);
            } else if (MEM.equals(voucher2.getAppScope())) {
                List<PdmVoucherMember> voucherMember = voucher.getPdmVoucherMembers();
                if (!voucherMember.isEmpty()) {
                    for (PdmVoucherMember pdmVoucherMember : voucherMember) {
                        VoucherAssign assign = new VoucherAssign();
                        assign.setMemberId(pdmVoucherMember.getMemberId());
                        assign.setVoucherId(voucher2.getVoucherId());
                        assign.setQuantity(voucher2.getQuantity());
                        assign.setStatus(voucher2.getStatus());
                        assign.setMarketId(voucher2.getMarketId());
                        assign.setAssignType(MEM);
                        self().createVoucherAssign(requestContext, assign);
                    }
                }
            }
        }
        return vouchers;
    }

    @Override
    public boolean removeItem(IRequest requestContext, List<PdmVoucherItem> pdmVouchergifts) {
        for (PdmVoucherItem pdmVoucherItem : pdmVouchergifts) {
            pdmVoucherItemMapper.deleteByPrimaryKey(pdmVoucherItem.getVoucherItemId());
        }
        return true;
    }

    @Override
    public boolean removeGift(IRequest requestContext, List<PdmVoucherGift> pdmVouchergifts) {
        for (PdmVoucherGift pdmVoucherGift : pdmVouchergifts) {
            pdmVoucherGiftMapper.deleteByPrimaryKey(pdmVoucherGift.getVoucherGiftId());
        }
        return true;
    }

    @Override
    public boolean removeMember(IRequest requestContext, List<PdmVoucherMember> pdmVoucherMembers) {
        for (PdmVoucherMember pdmVoucherMember : pdmVoucherMembers) {
            pdmVoucherMemberMapper.deleteByPrimaryKey(pdmVoucherMember.getVoucherMemberId());
        }
        return true;
    }

    @Override
    public List<PdmVoucherMember> queryPdmVoucherMember(IRequest request, Long voucherId) {
        //PageHelper.startPage(page, pageSize);
        PdmVoucherMember member = new PdmVoucherMember();
        member.setVoucherId(voucherId);
        List<PdmVoucherMember> members = pdmVoucherMemberMapper.qureyMember(member);
        return members;
    }

    @Override
    public List<PdmVoucherItem> queryPdmVoucherItem(IRequest request, Long voucherId, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        PdmVoucherItem item = new PdmVoucherItem();
        item.setVoucherId(voucherId);
        List<PdmVoucherItem> voucherItems = pdmVoucherItemMapper.qureyItem(item);
        return voucherItems;
    }

    @Override
    public List<PdmVoucherGift> queryPdmVoucherGift(IRequest request, Long voucherId, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        PdmVoucherGift gift = new PdmVoucherGift();
        gift.setVoucherId(voucherId);
        List<PdmVoucherGift> gigts = pdmVoucherGiftMapper.qureyGift(gift);
        return gigts;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public VoucherAssign createVoucherAssign(IRequest request, @StdWho VoucherAssign voucherAssign) throws CommVoucherException {
        if (voucherAssign.getAssignId() != null) {
            voucherAssignMapper.updateByPrimaryKeySelective(voucherAssign);
        } else {
            voucherAssignMapper.insert(voucherAssign);
            VoucherAssign vas = new VoucherAssign();
            vas.setVoucherId(voucherAssign.getVoucherId());
            vas.setMemberId(voucherAssign.getMemberId());
            List<VoucherAssign> list = voucherAssignMapper.selectVoucherAssign(vas);
            if(list.size()>1){
                throw new CommVoucherException(CommVoucherException.MSG_ERROR_PDM_VOUCHER_CODE_UNIQUE,  new Object [] {});
            }
        }
        return voucherAssign;
    }

    @Override
    public List<Voucher> getVoucher(IRequest requestContext, Long voucherId) {
        List<Voucher> vouchers = new ArrayList<>();
        Voucher voucher2 = voucherMapper.selectByPrimaryKey(voucherId);
        List<VoucherAssign> voucherAssigns = voucherAssignMapper.queryQuantity(voucher2.getVoucherId());
        if (SALES.equals(voucher2.getAppScope())) {
            for (VoucherAssign voucherAssign : voucherAssigns) {
                voucher2.setSurplus(voucherAssign.getQuantity());
            }
        }
        vouchers.add(voucher2);
        return vouchers;
    }

    @Override
    public List<Voucher> getMemberVouchers(IRequest request, Long memberId) {
        Long salesOrgId = request.getAttribute(SystemProfileConstants.SALES_ORG_ID);
        return voucherMapper.getMemberVouchers(memberId, salesOrgId);
    }

    @Override
    public List<Voucher> getMemberVouchers(IRequest request, Long memberId, Long salesOrgId) {
        return voucherMapper.getMemberVouchers(memberId, salesOrgId);
    }

    @Override
    public Long validate(IRequest request, List<MemberList> list, String idType, Long mentionId, Long marketId)
            throws CommMemberException {
        Long groupId = memberListMapper.getNextGroupId();
        // 验证导入的会员
        for (MemberList memberList : list) {
            MemberList memList = new MemberList();
            String code = memberList.getMemberCode();
            Member member = memberMapper.selectMembersByMemberCode(code);
            memList.setGroupId(groupId);
            memList.setGroupType(MemberConstants.MEMIMPORT_TYPE_VOUCHER);
            memList.setMemberCode(code);
            // 会员是否存在
            if (member == null) {
                memList.setValidateFlag(MemberConstants.MEMIMPORT_FLAG_DISABLE);
                memList.setReasonCode(MemberConstants.MEMIMPORT_MEMBER_ID_FAIL);
            } else {
                Long memMarketId = member.getMarketId();
                String status = member.getStatus();
                // 根据市场
                PdmVoucherMember pdmVoucherMember = new PdmVoucherMember();
                if(mentionId == null){
                    pdmVoucherMember.setVoucherId(0L);
                }else{
                    pdmVoucherMember.setVoucherId(mentionId);
                }
                pdmVoucherMember.setMemberId(member.getMemberId());
                List<PdmVoucherMember> pdmVoucherMembers = pdmVoucherMemberMapper.qureyMember(pdmVoucherMember);
                memList.setMemberId(member.getMemberId());
                if (memMarketId == null || !memMarketId.equals(marketId)) {
                    memList.setValidateFlag(MemberConstants.MEMIMPORT_FLAG_DISABLE);
                    memList.setReasonCode(MemberConstants.MEMIMPORT_MEMBER_MARKET_FAIL);
                } else if (MemberConstants.MEMBER_STATUS_TERMINATED.equals(status)
                        || MemberConstants.MEMBER_STATUS_AUTO_TERMINATED.equals(status)
                        || MemberConstants.MEM_STATUS_CHANGE_AUTO_TERMINATE.equals(status)
                        || MemberConstants.MEMBER_STATUS_WCHG.equals(status)
                        || MemberConstants.MEMBER_STATUS_SUSPENDED.equals(status)
                        || MemberConstants.MEMBER_STATUS_PENDING.equals(status)
                        || MemberConstants.MEMBER_STATUS_NEW.equals(status)) {
                    memList.setValidateFlag(MemberConstants.MEMIMPORT_FLAG_DISABLE);
                    memList.setReasonCode(MemberConstants.MEMIMPORT_MEMBER_STATUS_FAIL);
                } else if (!pdmVoucherMembers.isEmpty()) {
                    memList.setValidateFlag(MemberConstants.MEMIMPORT_FLAG_DISABLE);
                    memList.setReasonCode(MemberConstants.MEMIMPORT_MEMBER_IMPORT_VOUCHER_EXIST);
                } else {
                    memList.setValidateFlag(MemberConstants.MEMIMPORT_FLAG_ENABLE);
                }
            }
            memberListMapper.insert(memList);
        }
        return groupId;
    }

    @Override
    public Long importMemebers(IRequest request, Long groupId, Long mentionId) {
        // 根据groupid查处会员，写进活动会员关系表
        List<MemberList> memberLists = memberListMapper.getMemberListByGroupId(groupId);
        for (MemberList member : memberLists) {
            Long memberId = member.getMemberId();
            Member mem = new Member();
            mem.setMemberId(memberId);
            List<Member> members = memberMapper.queryMembersForOrder(mem);
            PdmVoucherMember pdmVoucherMember = new PdmVoucherMember();
            for (Member member2 : members) {
                pdmVoucherMember.setMemberId(memberId);
                pdmVoucherMember.setMemberCode(member2.getMemberCode());
                pdmVoucherMember.setChineseName(member2.getChineseName());
                pdmVoucherMember.setEnglishName(member2.getEnglishName());
                pdmVoucherMember.setPhoneNo(member2.getPhoneNo());
                pdmVoucherMember.setVoucherId(mentionId);
            }
            /*if (pdmVoucherMemberMapper.qureyMember(pdmVoucherMember).isEmpty() && mentionId != null) {
                pdmVoucherMemberMapper.insert(pdmVoucherMember);
                Voucher voucher = new Voucher();
                voucher.setVoucherId(mentionId);
                voucher.setAppScope(MEM);
                voucherMapper.updateByPrimaryKeySelective(voucher);
            }*/
        }
        return mentionId;
    }

    @Override
    public void updateEcouponInvalid(IRequest request, Voucher voucher) {
        voucherMapper.updateEcouponInvalid(voucher);
    }

    @Override
    public List<Voucher> getMemberVouchersForVIP(IRequest request, Long memberId) {
        return voucherMapper.getMemberVouchersForVIP(memberId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void validateEcoupon(IRequest request, Long orderHeaderId) {
        // 根据订单头Id获取当前订单的基本信息
        SalesOrder salesOrder = salesOrderMapper.selectByPrimaryKeyOnly(orderHeaderId);
        Member member = memberMapper.selectByPrimaryKey(salesOrder.getMemberId());
        // add 2016-08-29 排除非会员订单
        if (salesOrder.getMemberId() == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("Non-Member Order do not have Ecoupon.OrderNumber : [{}]", salesOrder.getOrderNumber());
            }
            return;
        }
        SpmSalesOrganization org = spmSalesOrganizationMapper.selectByPrimaryKey(salesOrder.getSalesOrgId());
        // 对于生成Ecoupon的订单失效情况
        // TODO:sourceOrderId是订单编号还是订单ID?
        Voucher voucher = voucherMapper.selectBySourceOrderId(orderHeaderId);
        if (voucher != null && "VIP".equals(voucher.getCategory())) {
            OrderPayment orderPaymentByVoucherId = orderPaymentMapper.selectByVoucherId(voucher.getVoucherId());
            if (orderPaymentByVoucherId == null) { // 若该Ecoupon没被使用
                self().updateEcouponInvalid(request, voucher);
            }
        }
        // 查询该订单使用的优惠券集合
        List<OrderPayment> orderPayments = orderPaymentMapper.selectByHeaderId(orderHeaderId);
        for (OrderPayment orderPayment : orderPayments) {
            if (orderPayment.getVoucherId() != null && "ECUP".equals(orderPayment.getPaymentMethod())) {
                // 根据Ecoupon的ID回溯到生成该Ecoupon的订单
                // OrderPayment orderPaymentByVoucherId = orderPaymentMapper
                // .selectByVoucherId(orderPayment.getVoucherId());
                Voucher voucherForSouceId = voucherMapper.selectByPrimaryKey(orderPayment.getVoucherId());
                if (voucherForSouceId != null && "VIP".equals(voucherForSouceId.getCategory())
                        && voucherForSouceId.getSourceOrderId() != null) {
                    // 判断回溯的订单的状态是否为失效
                    String orderStatus = salesOrderMapper
                            .getOrderStatusByPrimaryKey(voucherForSouceId.getSourceOrderId());
                    if (!("VOID".equals(orderStatus))) { // 若不为失效，则将优惠券的数量+1写回优惠券分配表
                        // 判断当前失效订单的市场和会员的市场是否一致，若一致则将优惠券的数量+1写回优惠券分配表，若不一致则不回退
                        if (org.getMarketId().equals(member.getMarketId())) {
                            VoucherAssign voucherAssign = new VoucherAssign();
                            voucherAssign.setVoucherId(orderPayment.getVoucherId());
                            voucherAssign.setMemberId(member.getMemberId());
                            // 根据优惠券ID查询分配记录
                            List<VoucherAssign> voucherAssigns = voucherAssignMapper.selectVoucherAssign(voucherAssign);
                            if (voucherAssigns != null && voucherAssigns.size() > 0) {
                                VoucherAssign voucherAssign2 = voucherAssigns.get(0);
                                // 优惠券数量置一
                                voucherAssign2.setQuantity(1L);
                                // 更新优惠券分配表
                                voucherAssignMapper.updateVoucherAssignQty(voucherAssign2);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void invalidMemberVoucher(IRequest request, Member member) {
        List<VoucherAssign> voucherAssigns = voucherAssignMapper.selectVoucherAssignByMember(member);
        for (VoucherAssign voucherAssign : voucherAssigns) {
            if (voucherAssign.getQuantity() >= 1) {
                Long voucherId = voucherAssign.getVoucherId();
                Voucher voucher = voucherMapper.selectByPrimaryKey(voucherId);
                if (voucher != null) {
                    if ("VIP".equals(voucher.getCategory())) {
                        self().updateEcouponInvalid(request, voucher);
                    } else {
                        // 优惠券数量置零
                        voucherAssign.setQuantity(0L);
                        voucherAssign.setMemberId(member.getMemberId());
                        // 更新优惠券分配表
                        voucherAssignMapper.updateVoucherAssignQty(voucherAssign);
                    }
                }
            }
        }
    }

    @Override
    public void cancelOrderReturnCoupon(IRequest request, Long orderHeaderId) throws CommVoucherException {
        SalesOrder so = salesOrderMapper.selectByPrimaryKeyOnly(orderHeaderId);
        // 查询订单优惠券使用信息
        List<SalesVoucher> salesVouchers = salesVoucherMapper.getVouchersByOrderId(so.getHeaderId());
        if (!salesVouchers.isEmpty()) {
            // 优惠券事务处理
            Map<Long, Long> voucherQtyMap = new HashMap<Long, Long>();
            for (SalesVoucher salesVoucher : salesVouchers) {
                if (voucherQtyMap.containsKey(salesVoucher.getVoucherId())) {
                    voucherQtyMap.put(salesVoucher.getVoucherId(),
                            voucherQtyMap.get(salesVoucher.getVoucherId()) + (1L));
                } else {
                    voucherQtyMap.put(salesVoucher.getVoucherId(), 1L);
                }
            }
            Iterator<Long> it = voucherQtyMap.keySet().iterator();
            List<VoucherTransaction> voucherTrxs = new ArrayList<VoucherTransaction>();

            while (it.hasNext()) {
                VoucherTransaction voucherTrx = new VoucherTransaction();
                voucherTrx.setSalesOrgId(so.getSalesOrgId());
                voucherTrx.setMemberId(so.getMemberId());
                voucherTrx.setTrxType(VoucherConstants.TRX_TYPE_BACK);
                voucherTrx.setTrxSourceType(VoucherConstants.TRX_SOURCE_TYPE_ORDER_HEAD);
                voucherTrx.setTrxSourceKey(String.valueOf(so.getHeaderId()));
                voucherTrx.setTrxSourceReference(so.getOrderNumber());
                voucherTrx.setTrxDate(new Date());

                Long voucherId = it.next();
                voucherTrx.setVoucherId(voucherId);
                voucherTrx.setTrxQty(voucherQtyMap.get(voucherId));
                voucherTrxs.add(voucherTrx);
            }
            voucherQuantityTrxService.processVoucherQuantityTrx(request, voucherTrxs);
        }
    }

    @Override
    public PdmVoucherMember queryMember(IRequest request, Long memberId) {
        //PageHelper.startPage(page, pageSize);
        PdmVoucherMember pvm = new PdmVoucherMember();
        pvm.setMemberId(memberId);
        return pdmVoucherMemberMapper.qureyByMemberId(pvm);
    }

    @Override
    public void invalidVoucherForJob(IRequest request) {
        voucherMapper.updateFlagByEndActiveDate();
    }
}
