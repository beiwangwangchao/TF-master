/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.service.impl;

import com.github.pagehelper.PageHelper;
import com.lkkhpg.dsis.common.config.dto.*;
import com.lkkhpg.dsis.common.config.mapper.*;
import com.lkkhpg.dsis.common.constant.*;
import com.lkkhpg.dsis.common.delivery.mapper.OrderDeliveryMapper;
import com.lkkhpg.dsis.common.enums.SequenceType;
import com.lkkhpg.dsis.common.exception.CommMemberException;
import com.lkkhpg.dsis.common.exception.CommOrderException;
import com.lkkhpg.dsis.common.exception.CommVoucherException;
import com.lkkhpg.dsis.common.inventory.dto.InvOnhandQuantity;
import com.lkkhpg.dsis.common.member.dto.MemAccountingTrx;
import com.lkkhpg.dsis.common.member.dto.MemSite;
import com.lkkhpg.dsis.common.member.dto.Member;
import com.lkkhpg.dsis.common.member.mapper.MemSiteMapper;
import com.lkkhpg.dsis.common.member.mapper.MemberMapper;
import com.lkkhpg.dsis.common.order.dto.*;
import com.lkkhpg.dsis.common.order.mapper.*;
import com.lkkhpg.dsis.common.product.dto.InvItem;
import com.lkkhpg.dsis.common.product.dto.InvItemHierarchy;
import com.lkkhpg.dsis.common.product.dto.PriceListDetail;
import com.lkkhpg.dsis.common.product.mapper.InvItemHierarchyMapper;
import com.lkkhpg.dsis.common.product.mapper.InvItemMapper;
import com.lkkhpg.dsis.common.product.mapper.PriceListDetailMapper;
import com.lkkhpg.dsis.common.promotion.dto.Voucher;
import com.lkkhpg.dsis.common.promotion.dto.VoucherTransaction;
import com.lkkhpg.dsis.common.service.*;
import com.lkkhpg.dsis.common.system.dto.SysMsMessage;
import com.lkkhpg.dsis.common.system.dto.SysMsMessageAssign;
import com.lkkhpg.dsis.common.system.mapper.SysMsMessageAssignMapper;
import com.lkkhpg.dsis.common.system.mapper.SysMsMessageMapper;
import com.lkkhpg.dsis.platform.constant.BaseConstants;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.system.DocSequence;
import com.lkkhpg.dsis.platform.service.IDocSequenceService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 销售订单服务接口具体实现.
 *
 * @author wuyichu
 */
@Service
@Transactional
public class CommSalesOrderServiceImpl implements ICommSalesOrderService {

    private final Logger log = LoggerFactory.getLogger(CommSalesOrderServiceImpl.class);

    @Autowired
    private SalesOrderMapper salesOrderMapper;
    @Autowired
    private SalesLineMapper salesLineMapper;
    @Autowired
    private SalesAdjustmentMapper salesadjustMentMapper;
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private MemSiteMapper memSiteMapper;
    @Autowired
    private SalesLogisticsMapper salesLogisticsMapper;
    @Autowired
    private SpmSalesOrganizationMapper spmSalesOrganizationMapper;
    @Autowired
    private ITaxService taxService;
    @Autowired
    private IDocSequenceService docSequenceService;
    @Autowired
    private ISpmSalesOrganizationService spmSalesOrganizationService;
    @Autowired
    private IMemWriteOffService memWriteOffService;
    @Autowired
    private OrderDeliveryMapper orderDeliveryMapper;
    @Autowired
    private ICommPaymentRefundService commPaymentRefundService;
    @Autowired
    private IMemberBalanceTrxService memberBalanceTrxService;
    @Autowired
    private ISpmPeriodService spmPeriodService;
    @Autowired
    private IParamService paramService;
    @Autowired
    private InvItemMapper invItemMapper;
    @Autowired
    private PriceListDetailMapper priceListDetailMapper;
    @Autowired
    private InvItemHierarchyMapper invItemHierarchyMapper;
    @Autowired
    private SpmServiceCenterMapper spmServiceCenterMapper;
    @Autowired
    private ISalesSitesService salesSitesService;
    @Autowired
    private IVoucherApplyValiService voucherApplyValiService;
    @Autowired
    private SalesVoucherMapper salesVoucherMapper;
    @Autowired
    private IInvOnhandQuantityService invOnhandQuantityService;
    @Autowired
    private ISpmInvOrganizationService spmInvOrganizationService;
    @Autowired
    private IVoucherQuantityTrxService voucherQuantityTrxService;
    @Autowired
    private SpmPeriodMapper spmPeriodMapper;
    @Autowired
    private SpmCurrencyMapper spmCurrencyMapper;
    @Autowired
    private ISpmCurrencyService spmCurrencyService;
    @Autowired
    private SpmMarketMapper spmMarketMapper;
    @Autowired
    private SalesSitesMapper salesSitesMapper;
    @Autowired
    private IDeliveryInvOrgMatchService deliveryInvOrgMatchService;
    @Autowired
    private IVoucherService voucherService;
    @Autowired
    private OrderPaymentMapper orderPaymentMapper;
    @Autowired
    private OmMwsOrderPaymentMapper omMwsOrderPaymentMapper;

    @Autowired
    private IMwsOrderPaymentService mwsOrderPaymentService;

    @Autowired
    private ICommMemberService commMemberService;
    @Autowired
    private SysMsMessageMapper msMessageMapper;
    @Autowired
    private SysMsMessageAssignMapper msMessageAssignMapper;
    @Autowired
    private SysMsMessageAssignMapper sysMsMessageAssignMapper;
    @Autowired
    private IManualMessageService manualMessageService;
    @Autowired
    private QueryOrderMapper queryOrderMapper;

    @Override
    public List<SalesOrder> selectDiscountTrx(String orderNumber) {
        return salesOrderMapper.selectDiscountTrx(orderNumber);
    }

    @Override
    public List<SalesOrder> queryOrders(IRequest request, SalesOrder salesOrder, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return salesOrderMapper.queryOrders(salesOrder);
    }

    @Override
    public List<SalesOrder> queryMwsOrders(IRequest request, SalesOrder salesOrder, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return salesOrderMapper.queryMwsOrders(salesOrder);
    }

    @Override
    public List<SalesOrder> queryOrdersId(IRequest request, SalesOrder salesOrder) {
        return salesOrderMapper.queryOrdersId(salesOrder);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public SalesOrder insertOrder(IRequest request, SalesOrder salesOrder)
            throws CommOrderException, CommVoucherException {
        if (salesOrder == null) {
            return salesOrder;
        }
        self().validateOrderData(request, salesOrder);
        salesOrder.setOrderNumber(self().generateCode(request, SequenceType.SALESORDER, salesOrder));
        if (salesOrder.getPeriodId() == null) {
            SpmPeriod period = new SpmPeriod();
            period.setOrgType(SystemProfileConstants.ORG_TYPE);
            period.setOrgId(request.getAttribute(SystemProfileConstants.MARKET_ID));
            period.setPeriodName(salesOrder.getPeriod());
            period.setPeriodType(SystemProfileConstants.PERIOD_TYPE);
            period = spmPeriodService.getPeriod(request, period);
            if (period.getPeriodId() == null) {
                throw new CommOrderException(CommOrderException.MSG_ERROR_OM_BONUS_MONTH_CLOSED,
                        new Object[]{request, period});
            }
            salesOrder.setPeriodId(period.getPeriodId());
        }
        if (salesOrder.getSourceType() == null) {
            salesOrder.setSourceType(OrderConstants.MANUAL);
        }
        if (salesOrder.getSalesPoints() == null) {
            salesOrder.setSalesPoints(BigDecimal.ZERO);
        }
        if (OrderConstants.SALES_STATUS_SAVED.equals(salesOrder.getOrderStatus())
                && salesOrder.getActrualPayAmt() == null) {
            salesOrder.setActrualPayAmt(salesOrder.getOrderAmt());
        }
        salesOrderMapper.insert(salesOrder);
        final SalesLogistics logistics = salesOrder.getLogistics();
        if (logistics != null) {
            logistics.setHeaderId(salesOrder.getHeaderId());
            logistics.setAutoshipFlag(OrderConstants.NO);
            salesLogisticsMapper.insert(logistics);
        }

        if (salesOrder.getSalesSites() != null) {
            List<SalesSites> salesSites = salesOrder.getSalesSites();
            for (SalesSites sites : salesSites) {
                sites.setHeaderId(salesOrder.getHeaderId());
                sites.setAutoshipFlag(OrderConstants.NO);
                salesSitesService.submit(request, sites);
            }
        }

        // 订单状态为待付款时写入
        // TODO 此逻辑后期需要修改
        if (OrderConstants.SALES_STATUS_WPAY.equals(salesOrder.getOrderStatus()) && salesOrder.getVouchers() != null) {
            List<Voucher> vouchers = salesOrder.getVouchers();
            for (Voucher voucher : vouchers) {
                SalesVoucher salesVoucher = new SalesVoucher();
                salesVoucher.setHeaderId(salesOrder.getHeaderId());
                salesVoucher.setVoucherId(voucher.getVoucherId());
                salesVoucher.setSalesOrgId(salesOrder.getSalesOrgId());
                salesVoucherMapper.insert(salesVoucher);
            }
        }

        if (OrderConstants.WAIT_PAT.equals(salesOrder.getOrderStatus())) {
            List<SalesLine> lines = salesOrder.getLines();
            if (lines == null || lines.isEmpty()) {
                return salesOrder;
            }
            boolean check = checkInv(request, salesOrder);
            if (!check) {
                // 组织参数 允许backorder
                List<String> allowBackOrders = paramService.getParamValues(request,
                        SystemProfileConstants.PARAM_ALLOW_BACK_ORDER, SystemProfileConstants.ORG_TYPE_SALES,
                        salesOrder.getSalesOrgId());
                // 商品允许 backorder
                boolean itemBackorder = true;
                for (SalesLine l : lines) {
                    if (OrderConstants.NO.equals(l.getBackOrder())) {
                        itemBackorder = false;
                    }
                }
                if (allowBackOrders == null || allowBackOrders.isEmpty()
                        || OrderConstants.NO.equals(allowBackOrders.listIterator().next()) || !itemBackorder) {
                    SalesOrder order = new SalesOrder();
                    order.setHeaderId(salesOrder.getHeaderId());
                    order.setOrderStatus(OrderConstants.SALES_STATUS_SAVED);
                    salesOrderMapper.updateOrderStatus(order);
                    salesOrder.setOrderStatus(OrderConstants.SALES_STATUS_SAVED);
                }
            }
        }

        // 订单行写入
        processLine(salesOrder, true);

        // 价格调整写入
        processAdjustMents(salesOrder, true);

        return salesOrder;
    }

    /**
     * 订单行处理.
     *
     * @param salesOrder 销售订单.
     * @param isnew      是否新增.
     */
    private void processLine(final SalesOrder salesOrder, boolean isnew) {
        List<SalesLine> lines = salesOrder.getLines();
        if (lines == null || lines.isEmpty()) {
            return;
        }
        // 订单行起始行号设置
        Long lineBeginNum = 0L;
        if (!isnew) {
            for (SalesLine line : lines) {
                if (line.getLineNum() != null && line.getLineNum() > lineBeginNum) {
                    lineBeginNum = line.getLineNum();
                }
            }
        }

        for (SalesLine line : lines) {
            // 未挑库数量与订单行数量保持一致
            line.setUnpickQuantity(line.getQuantity());
            if (line.getLineId() == null && line.getItemId() == null) {
                continue;
            }
            // 为了便于展示税 插入时候将税插入Attribute1
            if (line.getDisableTaxFlag() != null) {
                line.setAttribute1(line.getDisableTaxFlag());
            }
            if (line.getLineId() != null && !isnew) {
                //0111更新订单，包括售卖价格
                salesLineMapper.updateByPrimaryKeySelective(line);
                salesLineMapper.deleteByParentLineId(line.getLineId());
            } else {
                lineBeginNum++;
                line.setLineNum(lineBeginNum);
                line.setHeaderId(salesOrder.getHeaderId());
                line.setStatus(OrderConstants.SALESLINE_STATUS_UNSHIPPED);
                line.setSourceType(salesOrder.getSourceType());

                // 设置SalesOrgId
                line.setSalesOrgId(salesOrder.getSalesOrgId());
                //0111添加订单
                salesLineMapper.insert(line);
            }

        }
    }

    public void processVirtualPackageItem(IRequest request, SalesLine line) {
        List<InvItemHierarchy> invItemHierarchies = invItemHierarchyMapper.getHierarchyByItemId(line.getItemId());
        if (invItemHierarchies.isEmpty()) {
            return;
        }
        for (InvItemHierarchy invItemHierarchy : invItemHierarchies) {
            SalesLine childenLine = new SalesLine();
            childenLine.setHeaderId(line.getHeaderId());
            childenLine.setParentLineId(line.getLineId());
            childenLine.setQuantity(invItemHierarchy.getQuantity().multiply(line.getQuantity()));
            childenLine.setUomCode(invItemHierarchy.getUomCode());
            childenLine.setItemId(invItemHierarchy.getItemId());
            childenLine.setStatus(line.getStatus());
            childenLine.setUnitDiscountPrice(BigDecimal.ZERO);
            childenLine.setUnitOrigPrice(BigDecimal.ZERO);
            childenLine.setUnitRedeemPoint(BigDecimal.ZERO);
            childenLine.setUnitSellingPrice(BigDecimal.ZERO);
            childenLine.setUnpickQuantity(childenLine.getQuantity());
            childenLine.setItemSalesType(line.getItemSalesType());
            if (ProductConstants.NO.equals(invItemHierarchy.getCountStockFlag())) {
                childenLine.setItemType(OrderConstants.LINE_ITEM_TYPE_VIRTUAL_PACKG_COUNT_CHILDREN_NOT_COUNT);
            } else {
                childenLine.setItemType(OrderConstants.LINE_ITEM_TYPE_ITEM);
            }
            childenLine.setPv(BigDecimal.ZERO);
            childenLine.setAmount(BigDecimal.ZERO);
            childenLine.setRedeemPoint(BigDecimal.ZERO);
            childenLine.setSourceKey(line.getSourceKey());
            childenLine.setSourceType(line.getSourceType());
            childenLine.setDefaultInvOrgId(line.getDefaultInvOrgId());
            childenLine.setSalesOrgId(line.getSalesOrgId());
            // 商品包拆行的无须同步，只在发运时候用,订单行号默认为0.
            childenLine.setLineNum(Long.parseLong(OrderConstants.ZERO));
            childenLine.setSyncFlag(OrderConstants.YES);
            childenLine.setCreatedBy(line.getCreatedBy());
            childenLine.setRequestId(line.getRequestId());
            childenLine.setProgramId(line.getProgramId());
            childenLine.setCreationDate(line.getCreationDate());
            childenLine.setLastUpdateDate(line.getLastUpdateDate());
            childenLine.setLastUpdatedBy(line.getLastUpdatedBy());
            childenLine.setObjectVersionNumber(line.getObjectVersionNumber());
            salesLineMapper.insert(childenLine);
        }
    }

    /**
     * 支付调整处理.
     *
     * @param salesOrder 销售订单.
     * @param isnew      是否新增.
     */
    private void processAdjustMents(final SalesOrder salesOrder, boolean isnew) {
        List<SalesAdjustment> adjustMents = salesOrder.getAdjustMents();
        if (adjustMents == null || adjustMents.isEmpty()) {
            return;
        }
        for (SalesAdjustment adjustMent : adjustMents) {
            if (adjustMent.getSalesAdjustmentId() != null && !isnew) {
                salesadjustMentMapper.updateByPrimaryKey(adjustMent);
            } else {
                adjustMent.setHeaderId(salesOrder.getHeaderId());
                salesadjustMentMapper.insert(adjustMent);
            }
        }
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Long querByOrderNumber(String orderNumber) {

        return salesOrderMapper.queryByOrderNumber(orderNumber.trim()).getHeaderId();

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public SalesOrder updateOrder(IRequest request, SalesOrder salesOrder)
            throws CommOrderException, CommVoucherException {
        self().validateOrderData(request, salesOrder);

        //0111更新订单状态为待付款
        if ("CHECK01".equals(salesOrder.getOrderStatus())) {
            salesOrder.setOrderStatus(OrderConstants.SALES_STATUS_WAIT_PAYMENT);
        }

        //salesOrderMapper.updateByPrimaryKey(salesOrder);


        /*modified by furong.tang*/
        if (OrderConstants.SALES_STATUS_WPAY.equals(salesOrder.getOrderStatus()) || OrderConstants.SALES_STATUS_CHECK.equals(salesOrder.getOrderStatus())) {
            List<SalesLine> lines = salesOrder.getLines();
            if (lines == null || lines.isEmpty()) {
                return salesOrder;
            }
            boolean check = checkInv(request, salesOrder);

            if (!check) {
                // 组织参数 允许backorder
                List<String> allowBackOrders = paramService.getParamValues(request,
                        SystemProfileConstants.PARAM_ALLOW_BACK_ORDER, SystemProfileConstants.ORG_TYPE_SALES,
                        salesOrder.getSalesOrgId());
                // 商品允许 backorder
                boolean itemBackorder = true;
                for (SalesLine l : lines) {
                    if (OrderConstants.NO.equals(l.getBackOrder())) {
                        itemBackorder = false;
                    }
                }
                if (allowBackOrders == null || allowBackOrders.isEmpty()
                        || OrderConstants.NO.equals(allowBackOrders.listIterator().next()) || !itemBackorder) {
                    SalesOrder order = new SalesOrder();
                    order.setHeaderId(salesOrder.getHeaderId());
                    order.setOrderStatus(OrderConstants.SALES_STATUS_SAVED);
                    salesOrderMapper.updateOrderStatus(order);
                    salesOrder.setOrderStatus(OrderConstants.SALES_STATUS_SAVED);
                }
                salesOrder.setOrderStatus(OrderConstants.SALES_STATUS_SAVED);
            }

        }

        salesOrderMapper.updateByPrimaryKey(salesOrder);
        if (salesOrder.getSalesSites() != null) {
            List<SalesSites> salesSites = salesOrder.getSalesSites();
            for (SalesSites sites : salesSites) {
                sites.setHeaderId(salesOrder.getHeaderId());
                sites.setAutoshipFlag(OrderConstants.NO);
                salesSitesService.submit(request, sites);
            }
        }
        processAdjustMents(salesOrder, false);
        //0111更新订单中的每一项

        processLine(salesOrder, false);

        SalesLogistics logistics = salesOrder.getLogistics();
        if (logistics != null) {
            SalesLogistics salesLogistics = salesLogisticsMapper.selectByHeaderId(salesOrder.getHeaderId(),
                    OrderConstants.NO);
            if (salesLogistics != null && salesLogistics.getLogisticsId() != null) {
                logistics.setLogisticsId(salesLogistics.getLogisticsId());
                logistics.setHeaderId(salesOrder.getHeaderId());
                logistics.setAutoshipFlag(OrderConstants.NO);
                salesLogisticsMapper.updateByPrimaryKey(logistics);
            } else {
                logistics.setHeaderId(salesOrder.getHeaderId());
                logistics.setAutoshipFlag(OrderConstants.NO);
                salesLogisticsMapper.insert(logistics);
            }
        }

        // 订单状态为待付款时写入
        // TODO 此逻辑后期需要修改
        if (OrderConstants.SALES_STATUS_WPAY.equals(salesOrder.getOrderStatus()) && salesOrder.getVouchers() != null) {
            List<Voucher> vouchers = salesOrder.getVouchers();
            for (Voucher voucher : vouchers) {
                SalesVoucher salesVoucher = new SalesVoucher();
                salesVoucher.setHeaderId(salesOrder.getHeaderId());
                salesVoucher.setVoucherId(voucher.getVoucherId());
                salesVoucher.setSalesOrgId(salesOrder.getSalesOrgId());
                salesVoucher.setCreatedBy(request.getAccountId());
                salesVoucher.setLastUpdatedBy(request.getAccountId());
                salesVoucherMapper.insert(salesVoucher);
            }
        }

        if (salesOrder.getSalesSites() != null) {
            List<SalesSites> salesSites = salesOrder.getSalesSites();
            for (SalesSites sites : salesSites) {
                sites.setHeaderId(salesOrder.getHeaderId());
                sites.setAutoshipFlag(OrderConstants.NO);
                salesSitesService.submit(request, sites);
            }
        }

        return salesOrder;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public SalesOrder updateOrderBySelective(IRequest request, SalesOrder salesOrder)
            throws CommOrderException, CommVoucherException {
        if (salesOrder.getHeaderId() == null || salesOrder.getOrderStatus() == null) {
            if (log.isDebugEnabled()) {
                log.debug("HeaderId or OrderStatus is null!");
            }
            return salesOrder;
        }
        SalesOrder so = salesOrderMapper.selectForUpdateByHeadId(salesOrder.getHeaderId());
        if (so == null) {
            if (log.isDebugEnabled()) {
                log.debug("Not found salesOrder info.headerId : {}", new Object[]{salesOrder.getHeaderId()});
            }
            return salesOrder;
        }
        if (!OrderConstants.SALES_STATUS_WPAY.equals(so.getOrderStatus())
                && !OrderConstants.SALES_STATUS_CONF.equals(so.getOrderStatus())
                && !OrderConstants.SALES_STATUS_SAVED.equals(so.getOrderStatus())) {
            throw new CommOrderException(CommOrderException.MSG_ERROR_NOT_ALLOW_PAY, null);
        }
        // 更新订单状态
        salesOrderMapper.updateByPrimaryKeySelective(salesOrder);
        if (OrderConstants.SALES_STATUS_CANCELED.equals(salesOrder.getOrderStatus())) {
            // 查询订单优惠券使用信息
            List<SalesVoucher> salesVouchers = salesVoucherMapper.getVouchersByOrderId(salesOrder.getHeaderId());
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
                    Long voucherId = it.next();
                    VoucherTransaction voucherTrx = new VoucherTransaction();
                    voucherTrx.setSalesOrgId(so.getSalesOrgId());
                    voucherTrx.setMemberId(so.getMemberId());
                    voucherTrx.setTrxType(VoucherConstants.TRX_TYPE_BACK);
                    voucherTrx.setTrxSourceType(VoucherConstants.TRX_SOURCE_TYPE_ORDER_HEAD);
                    voucherTrx.setTrxSourceKey(String.valueOf(so.getHeaderId()));
                    voucherTrx.setTrxSourceReference(so.getOrderNumber());
                    voucherTrx.setTrxDate(new Date());
                    voucherTrx.setVoucherId(voucherId);
                    voucherTrx.setTrxQty(voucherQtyMap.get(voucherId));
                    voucherTrxs.add(voucherTrx);
                }
                voucherQuantityTrxService.processVoucherQuantityTrx(request, voucherTrxs);
            }
        }
        return salesOrder;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public SalesOrder submitOrder(IRequest request, SalesOrder salesOrder)
            throws CommOrderException, CommVoucherException {
        if (salesOrder == null) {
            return null;
        }
        if (salesOrder.getHeaderId() == null) {
            if (log.isDebugEnabled()) {
                log.debug("into create order function");
            }

            salesOrder = self().insertOrder(request, salesOrder);
        } else {
            salesOrder = self().updateOrder(request, salesOrder);
        }
        return salesOrder;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public SalesOrder getDetailOrder(IRequest request, Long headId) {
        SalesOrder order = self().getOrder(request, headId, true, false);
        if (order == null) {
            return null;
        }
        if (order.getMemberId() != null) {
            System.out.println("the memberid is"+order.getMemberId());
            Member member = memberMapper.selectByPrimaryKey(order.getMemberId());
            if (MemberConstants.MEMBER_TYPE_COMP.equals(member.getMemberType())) {
                member.setEnglishName(member.getRefCompanyEn());
                member.setChineseName(member.getRefCompany());
            }
            //取出会员RemainingBalance
            /*modified by furong.tang*/
            Member memberInfo = memberMapper.queryRemBalByPrimaryKey(order.getMemberId());
            if(memberInfo.getRemainingBalance() != null && member.getRemainingBalance() == null){
                member.setRemainingBalance(memberInfo.getRemainingBalance());
            }
            // 扣除mws中该会员占用的rb
            OmMwsOrderPayment rbSum = omMwsOrderPaymentMapper.queryRemainingBalSum(order.getMemberId());
            if (null != rbSum) {
                member.setRemainingBalance(member.getRemainingBalance().subtract(rbSum.getPaymentAmount()));
            }
            List<MemSite> memSites = memSiteMapper.selectByMemberId(member.getMemberId());
            member.setMemSites(memSites);
            // List<MemAccountingBalance> accountingBalances =
            // accountingBalanceMapper
            // .selectByMemberId(member.getMemberId());
            // member.setMemAccountingBalances(accountingBalances);
            order.setMember(member);
        }
        SpmSalesOrganization organization = spmSalesOrganizationService.getSalesOrganizationDetail(request,
                order.getSalesOrgId());
        organization.setCurrency(order.getCurrency());
        order.setSalesOrganization(organization);
        return order;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public SalesOrder getOrder(IRequest request, Long headId, boolean isVirtual, boolean isDetail) {
        SalesOrder order = salesOrderMapper.selectByPrimaryKey(headId);
        if (order == null) {
            return null;
        }
        SpmPeriod period = spmPeriodMapper.selectByPrimaryKey(order.getPeriodId());
        order.setPeriod(period.getPeriodName());
        List<SalesLine> salesLines = salesLineMapper.selectByHeadId(headId, isVirtual, isDetail);
        order.setLines(salesLines);
        List<SalesAdjustment> adjustMents = salesadjustMentMapper.selectByHeaderId(headId);
        order.setAdjustMents(adjustMents);
        SalesLogistics logistics = salesLogisticsMapper.selectByHeaderId(headId, OrderConstants.NO);
        order.setLogistics(logistics);
        List<SalesSites> salesSites = salesSitesService.getSitesByHeaderId(request, headId, OrderConstants.NO);
        order.setSalesSites(salesSites);
        List<SalesVoucher> salesVouchers = salesVoucherMapper.getVouchersByOrderId(headId);
        order.setSalesVouchers(salesVouchers);
        SpmCurrency currency = spmCurrencyMapper.selectByPrimaryKey(order.getCurrency());
        order.setSpmCurrency(currency);

        OmMwsOrderPayment omMwsOrderPayment = new OmMwsOrderPayment();
        omMwsOrderPayment.setOrderHeaderId(order.getHeaderId());
        List<OmMwsOrderPayment> mwsOrderPaymentlist = mwsOrderPaymentService.getMwsOrderPayments(request,
                omMwsOrderPayment);
        order.setMwsOrderPayments(mwsOrderPaymentlist);

        if (OrderConstants.ORDER_CHANNEL_SRVC.equals(order.getChannel())) {
            SpmServiceCenter ssc = new SpmServiceCenter();
            ssc.setServiceCenterId(order.getServiceCenterId());
            ssc.setSalesOrgId(order.getSalesOrgId());
            SpmServiceCenter serviceCenter = spmServiceCenterMapper.selectByPrimaryKey(ssc);
            if (serviceCenter != null) {
                order.setServiceCenterName(serviceCenter.getName());
            }
        }
        return order;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteLine(IRequest request, List<SalesLine> lines) {
        if (lines == null || lines.isEmpty()) {
            return;
        }
        for (SalesLine line : lines) {
            if (line.getLineId() != null) {
                salesLineMapper.deleteByPrimaryKey(line);
            }
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<SalesLine> calculateLinePrice(IRequest request, final List<SalesLine> lines,
                                              final SalesOrder salesOrder) throws CommOrderException {
        if (lines == null || lines.isEmpty()) {
            return lines;
        }

        // set allowInvOrgs
        Set<Long> allowInvOrgs = new HashSet<Long>();
        if (OrderConstants.ORDER_DELIVERY_PCKUP.equals(salesOrder.getDeliveryType())) {
            List<SpmInvOrganization> invOrganizations = spmInvOrganizationService.getSupplyInvOrgsBySalesOrg(request,
                    salesOrder.getSalesOrgId());
            for (SpmInvOrganization invOrganization : invOrganizations) {
                allowInvOrgs.add(invOrganization.getInvOrgId());
            }
        }
        InvItem invItemParams = getItemParams(request, salesOrder);
        Set<String> allowSaleType = getAllowSaleType(request, salesOrder);
        for (SalesLine line : lines) {
            self().calculateLinePrice(request, line, salesOrder, allowInvOrgs, allowSaleType, invItemParams);
        }
        return lines;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public SalesOrder calculateOrderPrice(IRequest request, SalesOrder order) throws CommOrderException {
        if (order == null) {
            return null;
        }
        BigDecimal orderAmt = BigDecimal.ZERO;
        BigDecimal totalPay = BigDecimal.ZERO;
        BigDecimal exchangeAmt = BigDecimal.ZERO;
        SpmTax tax = taxService.getTaxBySalesOrg(request, order.getSalesOrgId());

        order.setTax(tax);
        // 为了报表能取到税率，将税率存到次字段
        if (tax != null && tax.getTaxPercent() != null) {
            order.setAttribute5(tax.getTaxPercent().toString());
        }
        SpmCurrency currency = spmCurrencyMapper.selectByPrimaryKey(order.getCurrency());
        order.setSpmCurrency(currency);
        if (order.getAdjustMents() != null && !order.getAdjustMents().isEmpty()) {
            totalPay = totalPay.add(self().caculateAdjustMents(request, order.getAdjustMents()));
        }
        final List<SalesLine> lines = self().calculateLinePrice(request, order.getLines(), order);
        for (SalesLine line : lines) {
            orderAmt = orderAmt.add(line.getAmount());
            if (OrderConstants.LINE_SALETYPE_EXCH.equals(line.getItemSalesType())) {
                exchangeAmt = exchangeAmt.add(line.getAmount());
            }
        }

        BigDecimal taxAmt = BigDecimal.ZERO;
        if (orderAmt.compareTo(BigDecimal.ZERO) > 0 && tax != null) {
            if (!tax.getPriceInclueTax()) {
                // 在行计算时已经包含了税额，因此需要先改变此时的是否包含税
                // tax.setPriceInclueTax(Boolean.TRUE);
                // taxAmt = taxService.getTaxAmount(request, orderAmt, tax);
                // tax.setPriceInclueTax(Boolean.FALSE);
                for (SalesLine line : lines) {
                    taxAmt = taxAmt.add(line.getTax().multiply(line.getQuantity()));
                }

            } else {
                taxAmt = taxService.getTaxAmount(request, orderAmt, tax);
            }

        }
        SalesLogistics logistics = order.getLogistics();

        if (logistics == null && order.getHeaderId() != null) {
            logistics = salesLogisticsMapper.selectByHeaderId(order.getHeaderId(), OrderConstants.NO);
        }

        if (logistics != null && logistics.getShippingFee() != null) {
            totalPay = totalPay.add(logistics.getShippingFee());
        }
        totalPay = totalPay.add(orderAmt);
        if (order.getDiscountAmt() == null) {
            order.setDiscountAmt(BigDecimal.ZERO);
        }
        totalPay = totalPay.subtract(order.getDiscountAmt());
        order.setTaxAmt(spmCurrencyService.toPrecisionValue(order.getCurrency(), taxAmt));
        totalPay = spmCurrencyService.toPrecisionValue(order.getCurrency(), totalPay);
        if (BigDecimal.ZERO.compareTo(totalPay) > 0) {
            if (log.isErrorEnabled()) {
                log.error("order total error {}", orderAmt);
            }
            throw new CommOrderException(CommOrderException.MSG_ERROR_OM_ACTUAL_ERROR, new Object[]{totalPay});
        }
        order.setActrualPayAmt(totalPay);
        // DISAP 不需要计算订单总价，直接使用传入的.
        if (!OrderConstants.ORDER_CHANNEL_DISAP.equals(order.getChannel())) {
            orderAmt = spmCurrencyService.toPrecisionValue(order.getCurrency(), orderAmt);
            if (BigDecimal.ZERO.compareTo(orderAmt) > 0) {
                if (log.isErrorEnabled()) {
                    log.error("order amt error {}", orderAmt);
                }
                throw new CommOrderException(CommOrderException.MSG_ERROR_OM_ACTUAL_ERROR, new Object[]{orderAmt});
            }
            order.setOrderAmt(orderAmt);
        }
        order.setExchangeAmt(spmCurrencyService.toPrecisionValue(order.getCurrency(), exchangeAmt));
        return order;
    }

    /**
     * 获取订单行商品查询参数.
     *
     * @param request    统一上下文
     * @param salesOrder 销售订单
     * @return 订单行商品查询参数
     */
    private InvItem getItemParams(IRequest request, SalesOrder salesOrder) {
        InvItem invItem = new InvItem();
        switch (salesOrder.getChannel()) {
            case OrderConstants.ORDER_CHANNEL_AUTOS:
                invItem.setChannel(ProductConstants.FUNCTION_AREA_AUTOSHIP);
                break;
            case OrderConstants.ORDER_CHANNEL_FAX:
                invItem.setChannel(ProductConstants.FUNCTION_AREA_IN_STORE);
                break;
            case OrderConstants.ORDER_CHANNEL_DISWB:
                invItem.setChannel(ProductConstants.FUNCTION_AREA_DISTRIBUTOR_WEB);
                break;
            case OrderConstants.ORDER_CHANNEL_DISAP:
                invItem.setChannel(ProductConstants.FUNCTION_AREA_DISTRIBUTOR_APP);
                break;
            default:
                invItem.setChannel(ProductConstants.FUNCTION_AREA_IN_STORE);
                break;
        }
        invItem.setSalesOrgId(String.valueOf(salesOrder.getSalesOrgId()));
        invItem.setIsActive(OrderConstants.YES);
        if (salesOrder.getMemberId() != null) {
            Member member = salesOrder.getMember();
            if (member == null) {
                member = memberMapper.selectByPrimaryKey(salesOrder.getMemberId());
            }
            /*
             * if (OrderConstants.MEMBER_ROLE_DIS.equals(member.getMemberRole())
             * && OrderConstants.MEMBER_STATUS_NEW.equals(member.getStatus())) {
             * invItem.setStarterAid(OrderConstants.YES); }
             */
        }

        switch (salesOrder.getOrderType()) {
            case OrderConstants.ORDER_TYPE_DIRP:
            case OrderConstants.ORDER_TYPE_STFP:
                invItem.setPriceType(ProductConstants.PRICE_TYPE_STUFF_PRICE);
                invItem.setCurrency(salesOrder.getCurrency());
                break;
            case OrderConstants.ORDER_TYPE_STDP:

            case OrderConstants.ORDER_TYPE_NMDP:
                invItem.setPriceType(ProductConstants.PRICE_TYPE_DISTRI_PRICE);
                invItem.setCurrency(salesOrder.getCurrency());
                break;
            case OrderConstants.ORDER_TYPE_STFPT:
                invItem.setPriceType(ProductConstants.PRICE_TYPE_STUFF_PRICE_2);
                invItem.setCurrency(salesOrder.getCurrency());
                break;
            case OrderConstants.ORDER_TYPE_CONP:
                invItem.setPriceType(ProductConstants.PRICE_TYPE_RETAIL_PRICE);
                invItem.setCurrency(salesOrder.getCurrency());
                break;
            case OrderConstants.ORDER_TYPE_CONPT:
                invItem.setPriceType(ProductConstants.PRICE_TYPE_DISTRI_PRICE);
                invItem.setCurrency(salesOrder.getCurrency());
                break;
            case OrderConstants.ORDER_TYPE_RDEM:
                invItem.setPriceType(ProductConstants.PRICE_TYPE_REDEEM_POINT);
                invItem.setCurrency(salesOrder.getCurrency());
                invItem.setRedeemFlag(OrderConstants.YES);
                break;
            case OrderConstants.ORDER_TYPE_VIPP:
                invItem.setPriceType(ProductConstants.PRICE_TYPE_VIP);
                invItem.setCurrency(salesOrder.getCurrency());
                break;
            default:
                // invItem.setPriceType(OrderConstants.ZERO);
                break;
        }
        return invItem;
    }

    /**
     * 获取允许的销售方式.
     *
     * @param request    请求上下文
     * @param salesOrder 销售订单信息
     * @return 允许的销售方式集合
     */
    private Set<String> getAllowSaleType(final IRequest request, final SalesOrder salesOrder) {
        Set<String> result = new HashSet<String>();
        switch (salesOrder.getOrderType()) {
            case OrderConstants.ORDER_TYPE_STDP:
                result.add(OrderConstants.LINE_SALETYPE_PURC);
                result.add(OrderConstants.LINE_SALETYPE_EXCH);
                result.add(OrderConstants.LINE_SALETYPE_FREE);
                break;
            case OrderConstants.ORDER_TYPE_BIGF:
                result.add(OrderConstants.LINE_SALETYPE_GIFT);
                break;
            case OrderConstants.ORDER_TYPE_RDEM:
                result.add(OrderConstants.LINE_SALETYPE_REDE);
                break;
            default:
                result.add(OrderConstants.LINE_SALETYPE_PURC);
                break;
        }
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public SalesLine calculateLinePrice(IRequest request, final SalesLine line, final SalesOrder salesOrder,
                                        final Set<Long> allowInvOrgs, final Set<String> allowSaleTypes, final InvItem invItemParams)
            throws CommOrderException {
        BigDecimal zero = BigDecimal.ZERO;
        if (line.getItemId() == null || line.getUomCode() == null) {
            if (log.isErrorEnabled()) {
                log.error("itemId or uomCode not null");
            }
            throw new CommOrderException(CommOrderException.MSG_ERROR_OM_LINE_ITEM_ERROR,
                    new Object[]{request, line, salesOrder, allowInvOrgs, allowSaleTypes, invItemParams});
        }
        if (line.getQuantity() == null || line.getQuantity().compareTo(OrderConstants.MIN_QUANTITY) < 0
                || line.getQuantity().compareTo(OrderConstants.MAX_QUANTITY) > 0) {
            if (log.isErrorEnabled()) {
                log.error("line quantity error{}", line.getQuantity());
            }
            throw new CommOrderException(CommOrderException.MSG_ERROR_OM_LINE_ITEM_ERROR,
                    new Object[]{request, line, salesOrder, allowInvOrgs, allowSaleTypes, invItemParams});
        }
        invItemParams.setItemId(line.getItemId());
        if (!allowInvOrgs.isEmpty() && line.getDefaultInvOrgId() != null
                && !allowInvOrgs.contains(line.getDefaultInvOrgId())) {
            if (log.isErrorEnabled()) {
                log.error("line inv not allow");
            }
            throw new CommOrderException(CommOrderException.MSG_ERROR_OM_LINE_ITEM_ERROR,
                    new Object[]{request, line, salesOrder, allowInvOrgs, allowSaleTypes, invItemParams});
        }
        if (!allowSaleTypes.contains(line.getItemSalesType())) {
            if (log.isErrorEnabled()) {
                log.error("line salestype not allow{}", line.getItemSalesType());
            }
            throw new CommOrderException(CommOrderException.MSG_ERROR_OM_LINE_ITEM_ERROR,
                    new Object[]{request, line, salesOrder, allowInvOrgs, allowSaleTypes, invItemParams});
        }

        List<InvItem> items = invItemMapper.queryItemForOrder(invItemParams);
        if (items.isEmpty()) {
            if (log.isErrorEnabled()) {
                log.error("item not query {}", line.getItemId());
            }
            throw new CommOrderException(CommOrderException.MSG_ERROR_OM_LINE_ITEM_ERROR,
                    new Object[]{request, line, salesOrder, allowInvOrgs, allowSaleTypes, invItemParams});
        }

        // 订单行商品的类型设置
        InvItem item = items.iterator().next();
        line.setBackOrder(item.getBackOrder());
        if (line.getQuantity().compareTo(item.getMinOrderQuantity()) < 0
                && !OrderConstants.ORDER_CHANNEL_DISAP.equals(salesOrder.getChannel())) {
            if (log.isErrorEnabled()) {
                log.error("line quantity is {},but item minOrderQuantity is {}", line.getQuantity(),
                        item.getMinOrderQuantity());
            }
            throw new CommOrderException(CommOrderException.MSG_ERROR_OM_LINE_MIN_QUANTITY_ERROR,
                    new Object[]{line.getItemName(), item.getMinOrderQuantity()});
        }
        if (ProductConstants.ITEM_TYPE_ITEM.equals(item.getItemType())) {
            if (ProductConstants.NO.equals(item.getCountStockFlag())) {
                line.setItemType(OrderConstants.LINE_ITEM_TYPE_VIRTUAL_PACKG_COUNT_CHILDREN_NOT_COUNT);
            } else {
                line.setItemType(ProductConstants.ITEM_TYPE_ITEM);
            }
        } else if (ProductConstants.ITEM_TYPE_PACKAGE.equals(item.getItemType())
                && ProductConstants.YES.equals(item.getCountStockFlag())
                && ProductConstants.ITEM_COUNT_TYPE_IDV.equals(item.getQuantityCountType())) {
            line.setItemType(OrderConstants.LINE_ITEM_TYPE_VIRTUAL_PACKG_COUNT);
        } else if (ProductConstants.ITEM_TYPE_PACKAGE.equals(item.getItemType())
                && ProductConstants.NO.equals(item.getCountStockFlag())) {
            line.setItemType(OrderConstants.LINE_ITEM_TYPE_VIRTUAL_PACKG_NOT_COUNT);
        } else {
            line.setItemType(OrderConstants.LINE_ITEM_TYPE_PACKG);
        }
        line.setCountStockFlag(item.getCountStockFlag());
        // 1011免费类型与经销商APP的不进行设置价格.
        /*if (!OrderConstants.LINE_SALETYPE_FREE.equals(line.getItemSalesType())
                && !OrderConstants.LINE_SALETYPE_GIFT.equals(line.getItemSalesType())) {
            List<PriceListDetail> priceListDetails = priceListDetailMapper.selectByItemId(line.getItemId(),
                    salesOrder.getCurrency(), line.getUomCode(), salesOrder.getSalesOrgId());
            if (OrderConstants.ORDER_CHANNEL_DISAP.equals(salesOrder.getChannel())) {
                // 因前面商品可用性已经校验过是否存在该组织的价格，因此不做空判断
                line.setAttribute1(priceListDetails.get(0).getDisableTaxFlag());
            } else {
                //0111 设置价格
                setLinePrice(invItemParams.getPriceType(), priceListDetails, line, salesOrder.getOrderType());
            }

        }*/

        if (line.getUnitOrigPrice() == null && line.getUnitSellingPrice() == null
                && line.getUnitRedeemPoint() == null) {
            if (!OrderConstants.LINE_SALETYPE_FREE.equals(line.getItemSalesType())) {
                if (log.isErrorEnabled()) {
                    log.error("item price error");
                }
                throw new CommOrderException(CommOrderException.MSG_ERROR_OM_LINE_ITEM_ERROR,
                        new Object[]{request, line, salesOrder, allowInvOrgs, allowSaleTypes, invItemParams});
            }
            line.setUnitDiscountPrice(zero);
            line.setUnitOrigPrice(zero);
            line.setUnitSellingPrice(zero);
            line.setUnitRedeemPoint(zero);
        }
        if (line.getUnitDiscountPrice() == null || line.getVoucherId() == null) {
            line.setUnitDiscountPrice(zero);
        } else {
            // TODO: 订单折扣金额 (暂时设置为零)
            line.setUnitDiscountPrice(zero);
        }
        line.setUnitOrigPrice(
                spmCurrencyService.toPrecisionValue(salesOrder.getSpmCurrency(), line.getUnitOrigPrice()));
        SpmTax spmTax = salesOrder.getTax();
        if (spmTax != null && !spmTax.getPriceInclueTax()) {

            // 订单行不计税
            if (BaseConstants.YES.equals(line.getAttribute1())) {
                line.setTax(BigDecimal.ZERO);
                line.setUnitSellingPrice(spmCurrencyService.toPrecisionValue(salesOrder.getSpmCurrency(),
                        line.getUnitOrigPrice().subtract(line.getUnitDiscountPrice())));
            } else {
                BigDecimal tax;
                if (OrderConstants.ORDER_CHANNEL_DISAP.equals(salesOrder.getChannel())) {
                    tax = line.getUnitSellingPrice().subtract(line.getUnitOrigPrice());
                } else {
                    // TODO 加拿大市场写死税率
                    SpmMarket market = spmMarketMapper.selectBySalesOrgId(salesOrder.getSalesOrgId());
                    if (market != null && "CA".equals(market.getCode()) && "15160502".equals(line.getItemNumber())) {
                        tax = line.getUnitOrigPrice().multiply(new BigDecimal("0.07"));
                    } else {
                        tax = taxService.getTaxAmount(request, line.getUnitOrigPrice(), spmTax);
                    }
                }

                tax = spmCurrencyService.toPrecisionValue(salesOrder.getSpmCurrency(), tax);
                line.setTax(tax);
                line.setUnitSellingPrice(spmCurrencyService.toPrecisionValue(salesOrder.getSpmCurrency(),
                        line.getUnitOrigPrice().add(tax).subtract(line.getUnitDiscountPrice())));
            }
        } else {
            // add by zhangchuangsheng 2016.7.6 找不到维护的税的时候，原价=销售价格 - 折扣价格
            line.setUnitSellingPrice(spmCurrencyService.toPrecisionValue(salesOrder.getSpmCurrency(),
                    line.getUnitOrigPrice().subtract(line.getUnitDiscountPrice())));
        }
        if (line.getUnitRedeemPoint() != null) {
            line.setUnitRedeemPoint(
                    spmCurrencyService.toPrecisionValue(salesOrder.getSpmCurrency(), line.getUnitRedeemPoint()));
            line.setRedeemPoint(spmCurrencyService.toPrecisionValue(salesOrder.getSpmCurrency(),
                    line.getUnitRedeemPoint().multiply(line.getQuantity())));
        }
        if (line.getUnitSellingPrice() != null) {
            line.setAmount(spmCurrencyService.toPrecisionValue(salesOrder.getSpmCurrency(),
                    line.getUnitSellingPrice().multiply(line.getQuantity())));
        } else {
            line.setAmount(zero);
        }
        return line;
    }

    /**
     * 设置订单行价格.
     *
     * @param priceType        价格类型
     * @param priceListDetails 价格明细
     * @param line             订单行
     * @param orderType        订单类型
     */
    private void setLinePrice(String priceType, List<PriceListDetail> priceListDetails, SalesLine line,
                              String orderType) {
        for (PriceListDetail priceListDetail : priceListDetails) {
            // PV 设置
            line.setAttribute1(priceListDetail.getDisableTaxFlag());
            if (ProductConstants.PRICE_TYPE_PV.equals(priceListDetail.getPriceType())) {
                if (OrderConstants.LINE_SALETYPE_PURC.equals(line.getItemSalesType())
                        && (OrderConstants.ORDER_TYPE_STDP.equals(orderType)
                        || OrderConstants.ORDER_TYPE_VIPP.equals(orderType))) {
                    line.setPv(priceListDetail.getUnitPrice());
                } else {
                    line.setPv(BigDecimal.ZERO);
                }
            } else if (priceType.equals(priceListDetail.getPriceType())) {
                if (ProductConstants.PRICE_TYPE_REDEEM_POINT.equals(priceListDetail.getPriceType())) {
                    line.setUnitRedeemPoint(priceListDetail.getUnitPrice());
                    line.setRedeemPoint(line.getUnitRedeemPoint().multiply(line.getQuantity()));
                    line.setUnitOrigPrice(BigDecimal.ZERO);
                    line.setUnitSellingPrice(BigDecimal.ZERO);
                    line.setUnitDiscountPrice(BigDecimal.ZERO);
                    line.setAmount(BigDecimal.ZERO);
                } else {
                    line.setUnitRedeemPoint(BigDecimal.ZERO);
                    line.setRedeemPoint(BigDecimal.ZERO);
                    line.setUnitOrigPrice(priceListDetail.getUnitPrice());
                }
            }
        }
    }

    @Override
    public BigDecimal caculateAdjustMents(IRequest request, List<SalesAdjustment> adjustMents) {
        if (adjustMents == null) {
            return BigDecimal.ZERO;
        }
        BigDecimal result = BigDecimal.ZERO;
        for (SalesAdjustment adjustMent : adjustMents) {
            if (adjustMent.getAdjustmentAmount() == null) {
                continue;
            }
            result = result.add(adjustMent.getAdjustmentAmount());
        }
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String generateCode(IRequest request, SequenceType sequenceType, Object param) {
        SimpleDateFormat sdf = new SimpleDateFormat(OrderConstants.GENERATECODE_DATE_FORMAT);
        String date = sdf.format(new Date());
        DocSequence docSequence = new DocSequence(sequenceType.toString(), date, null, null, null, null);
        StringBuffer result = new StringBuffer();
        switch (sequenceType) {
            case SALESORDER:
                SalesOrder salesOrder = (SalesOrder) param;
                SpmSalesOrganization salesOrganization = spmSalesOrganizationMapper
                        .selectByPrimaryKey(salesOrder.getSalesOrgId());
                result.append(sequenceType.toString());
                result.append(salesOrganization.getCode());
                docSequence.setPk2Value(salesOrganization.getCode());
                result.append(docSequenceService.getSequence(request, docSequence, date, OrderConstants.SALESORDER_DIGIT,
                        OrderConstants.BEGIN_NUM));
                break;
            case AUTOSHIP:
                AutoshipOrder autoshipOrder = (AutoshipOrder) param;
                Member memberInfo = memberMapper.selectByPrimaryKey(autoshipOrder.getMemberId());
                result.append(sequenceType.toString());
                result.append(String.valueOf(memberInfo.getMemberCode()));
                result.append(docSequenceService.getSequence(request, docSequence, date, OrderConstants.AUTOSHIP_DIGIT,
                        OrderConstants.BEGIN_NUM));
                break;
            case AUTOSHIPBATCH:
                result.append(docSequenceService.getSequence(request, docSequence, date, OrderConstants.BATCH_DIGIT,
                        OrderConstants.BEGIN_NUM));
                break;
            default:
                result.append(date).append(docSequenceService.getSequence(request, docSequence, date,
                        OrderConstants.DEFAULT_DIGIT, OrderConstants.BEGIN_NUM));
                break;
        }
        return result.toString();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<SalesOrder> batchInsert(IRequest request, List<SalesOrder> salesOrders)
            throws CommOrderException, CommVoucherException {
        if (salesOrders == null || salesOrders.isEmpty()) {
            return null;
        }
        for (SalesOrder salesOrder : salesOrders) {
            self().insertOrder(request, salesOrder);
        }
        return salesOrders;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int updateOrderStatus(IRequest request, Long headerId, String orderStatus) {
        if (headerId == null) {
            if (log.isDebugEnabled()) {
                log.debug("orderHeaderId is null");
            }
            return 0;
        }
        SalesOrder salesOrder = new SalesOrder();
        salesOrder.setHeaderId(headerId);
        salesOrder.setOrderStatus(orderStatus);
        return salesOrderMapper.updateOrderStatus(salesOrder);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean pulishSysRefuseMessage(String orderNumber, Long marketId, String memberId, String member_code) {
        String content = "<P>"+"订单号："+orderNumber+" 退款失败，请联系客服！"+"</P>";
        SysMsMessage msMessage = new SysMsMessage();
        msMessage.setMarketId(marketId);
        msMessage.setMessageName("订单消息通知");
        msMessage.setMessageType("SYSME");
        msMessage.setMessageStatus("DRAFT");
        msMessage.setPublishChannel("DSIS");
        msMessage.setPublishDateType("NOW");
        msMessage.setSender(1L);
        msMessage.setMessageContent(content);
        msMessageMapper.insert(msMessage);
        Date currentDate = new Date(System.currentTimeMillis());

        SysMsMessageAssign msMessageAssign = new SysMsMessageAssign();
        msMessageAssign.setAssignType("MEM");
        msMessageAssign.setMsMessageId(msMessage.getMsMessageId());
        msMessageAssign.setRequestId(-1L);
        msMessageAssign.setProgramId(-1L);
        msMessageAssign.setObjectVersionNumber(1L);
        msMessageAssign.setCreatedBy(1L);
        msMessageAssign.setCreationDate(currentDate);
        msMessageAssign.setLastUpdatedBy(1L);
        msMessageAssign.setLastUpdateDate(currentDate);
        msMessageAssign.setAssignValue(memberId);

        msMessageAssignMapper.insert(msMessageAssign);

        msMessage.set__status("update");

        SysMsMessageAssign assign = new SysMsMessageAssign();
        assign.setMsMessageId(msMessage.getMsMessageId());
        List<SysMsMessageAssign> arrayList = sysMsMessageAssignMapper.queryByMemAssign(assign);
        try {
            manualMessageService.publishMessage(msMessage, arrayList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 更新前台状态
     *
     * @param salesOrder
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean updateOrderStatusRefund(SalesOrder salesOrder) throws Exception {
        try {
            salesOrderMapper.updateOrderStatusRefund(salesOrder);

        } catch (Exception e) {
            log.debug("the paymentResult insert is error");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean updateOrderStatusRefunds(String OrderNumber) throws Exception {
        try{
            salesOrderMapper.updateOrderStatusRefunds(OrderNumber);
        }catch (Exception e){
            e.printStackTrace();
            return  false;
        }


        return true;
    }

    @Override
    public String selectOrderStatus(String OrderNumber) throws Exception {
        return salesOrderMapper.selectOrderStatus(OrderNumber);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int updateOrderStatusWithFormerStatus(IRequest request, Long headerId, String orderStatus,
                                                 String orderFormerStatus) {
        if (headerId == null) {
            if (log.isDebugEnabled()) {
                log.debug("orderHeaderId is null");
            }
            return 0;
        }
        return salesOrderMapper.updateOrderStatusWithFormerStatus(headerId, orderStatus, orderFormerStatus);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void invalidOrderToRemaining(IRequest request, Long headerId, String remark)
            throws CommMemberException, CommVoucherException {
        SalesOrder salesOrder = self().getOrder(request, headerId, true, false);
        // 失效该订单使用的Ecupon
        voucherService.validateEcoupon(request, headerId);
        List<MemAccountingTrx> trxs = memWriteOffService.createTrxByOrderInvalid(request, salesOrder, (long) 1);
        for (MemAccountingTrx temp : trxs) {
            // 调用接口IMemberBalanceTrxService方法processAccountingTrx()
            memberBalanceTrxService.processAccountingTrx(request, temp);
        }
        // 优惠券冲销处理
        List<SalesVoucher> salesVouchers = salesVoucherMapper.getVouchersByOrderId(salesOrder.getHeaderId());
        if (!salesVouchers.isEmpty()) {
            List<VoucherTransaction> voucherTrxs = memWriteOffService.createVoucherTrxByOrderInvalid(request,
                    salesOrder, salesVouchers);
            voucherQuantityTrxService.processVoucherQuantityTrx(request, voucherTrxs);
        }
        // 生成退款信息并保存
        List<OrderPayment> orderPayments = orderPaymentMapper.selectByHeaderId(headerId);
        BigDecimal refundRB = BigDecimal.ZERO;
        for (OrderPayment temp : orderPayments) {
            if (!OrderConstants.PAY_METHOD_MODIFY_EBPAY.equals(temp.getPaymentMethod())
                    && !OrderConstants.PAY_METHOD_MODIFY_RBPAY.equals(temp.getPaymentMethod())
                    && !OrderConstants.PAYMENT_METHOD_ECUP.equals(temp.getPaymentMethod())) {
                refundRB = refundRB.add(temp.getPaymentAmount());
            }
        }
        PaymentRefund paymentRefund = new PaymentRefund();
        paymentRefund.setSourceId(headerId);
        paymentRefund.setSourceType(OrderConstants.REFUND_SOURCE_TYPE_ORDER);
        paymentRefund.setPaymentMethod(OrderConstants.PAY_METHOD_MODIFY_RBPAY);
        paymentRefund.setSalesOrgId(salesOrder.getSalesOrgId());
        paymentRefund.setPaymentAmount(refundRB);
        paymentRefund.setRemark(remark);
        commPaymentRefundService.insertPaymentRefund(request, paymentRefund);
        // 更新订单相关发运单状态至已取消
        List<String> status = new ArrayList<String>();
        status.add(DeliveryConstants.DELIVERY_STATUS_NEW);
        status.add(DeliveryConstants.DELIVERY_STATUS_PENDDING);
        orderDeliveryMapper.updateDeliveryStatus(headerId, DeliveryConstants.DELIVERY_STATUS_CANCLED, status);
        // 更新订单状态至已失效
        SalesOrder order = new SalesOrder();
        order.setHeaderId(headerId);
        order.setOrderStatus(OrderConstants.SALES_STATUS_VOIDED);
        salesOrderMapper.updateOrderStatus(order);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void invalidOrderToRefund(IRequest request, Long headerId, List<PaymentRefund> paymentRefunds)
            throws CommOrderException, CommMemberException, CommVoucherException {
        SalesOrder salesOrder = self().getOrder(request, headerId, true, false);
        // 验证退款信息是否合法
        commPaymentRefundService.checkRefundInvalid(request, headerId, paymentRefunds);
        // 失效该订单使用的Ecupon
        voucherService.validateEcoupon(request, headerId);
        List<MemAccountingTrx> trxs = memWriteOffService.createTrxByOrderInvalid(request, salesOrder, (long) 0);
        for (MemAccountingTrx temp : trxs) {
            // 调用接口IMemberBalanceTrxService方法processAccountingTrx()
            memberBalanceTrxService.processAccountingTrx(request, temp);
        }
        // 优惠券冲销处理
        List<SalesVoucher> salesVouchers = salesVoucherMapper.getVouchersByOrderId(salesOrder.getHeaderId());
        if (!salesVouchers.isEmpty()) {
            List<VoucherTransaction> voucherTrxs = memWriteOffService.createVoucherTrxByOrderInvalid(request,
                    salesOrder, salesVouchers);
            voucherQuantityTrxService.processVoucherQuantityTrx(request, voucherTrxs);
        }
        // 保存退款信息
        if (paymentRefunds.size() == 1
                && paymentRefunds.get(0).getPaymentMethod().equals(OrderConstants.PAYMENT_METHOD_DELIV)) {
            PaymentRefund refund = paymentRefunds.get(0);
            refund.setSourceId(refund.getOrderHeaderId());
            refund.setSourceType(OrderConstants.REFUND_SOURCE_TYPE_ORDER);
            refund.setPaymentAmount(new BigDecimal(0));
            commPaymentRefundService.insertPaymentRefund(request, refund);
        } else {
            for (PaymentRefund t : paymentRefunds) {
                // 如果支付方式非EB,RB,ECUP的时候存入refund表
                if (!OrderConstants.PAY_METHOD_MODIFY_EBPAY.equals(t.getPaymentMethod())
                        && !OrderConstants.PAY_METHOD_MODIFY_RBPAY.equals(t.getPaymentMethod())
                        && !OrderConstants.PAYMENT_METHOD_ECUP.equals(t.getPaymentMethod())) {
                    t.setSourceId(t.getOrderHeaderId());
                    t.setSourceType(OrderConstants.REFUND_SOURCE_TYPE_ORDER);
                    commPaymentRefundService.insertPaymentRefund(request, t);
                }
            }
        }
        // 更新订单相关发运单状态至已取消
        List<String> status = new ArrayList<String>();
        status.add(DeliveryConstants.DELIVERY_STATUS_NEW);
        status.add(DeliveryConstants.DELIVERY_STATUS_PENDDING);
        orderDeliveryMapper.updateDeliveryStatus(headerId, DeliveryConstants.DELIVERY_STATUS_CANCLED, status);
        // 更新订单状态至已失效
        SalesOrder order = new SalesOrder();
        order.setHeaderId(headerId);
        order.setOrderStatus(OrderConstants.SALES_STATUS_VOIDED);
        salesOrderMapper.updateOrderStatus(order);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int updateInvoiceNumberByHeaderId(IRequest request, String invoiceNumber, Long headerId) {
        if ("".equals(invoiceNumber) || headerId == null) {
            return 0;
        }
        return salesOrderMapper.updateInvoiceNumberByHeaderId(invoiceNumber, headerId);
    }

    @Override
    public void deleteAdjustment(IRequest request, Long adjustmentId) {
        if (adjustmentId == null) {
            return;
        }
        salesadjustMentMapper.deleteByPrimaryKey(adjustmentId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void validateOrderData(IRequest request, SalesOrder salesOrder)
            throws CommOrderException, CommVoucherException {
        checkBasicData(request, salesOrder);
        checkMember(request, salesOrder);

        // 已保存状态的订单不做订单金额等的校验.
        /*modified by furong.tang*/
        if (OrderConstants.SALES_STATUS_SAVED.equals(salesOrder.getOrderStatus())) {
            return;
        }
        self().calculateOrderPrice(request, salesOrder);
        if (salesOrder.getVouchers() != null && !salesOrder.getVouchers().isEmpty()) {
            voucherApplyValiService.voucherApplyValidator(request, salesOrder, true);
        }
        checkAdjustMent(request, salesOrder);
        checkDelivery(request, salesOrder);
    }

    /**
     * 校验订单基础数据.
     *
     * @param request    统一上下文
     * @param salesOrder 销售订单
     * @throws CommOrderException 校验不通过时抛出
     */
    private void checkBasicData(IRequest request, SalesOrder salesOrder) throws CommOrderException {
        // 校验销售区域
        /*
         * if
         * (!request.getAttribute(SystemProfileConstants.SALES_ORG_ID).equals(
         * salesOrder.getSalesOrgId()) || salesOrder.getSalesOrgId() == null) {
         * if (log.isErrorEnabled()) { log.error(
         * "order basic data salesOrgID error,system salesOrgId{} and order salesOrgId {}"
         * , request.getAttribute(SystemProfileConstants.SALES_ORG_ID),
         * salesOrder.getSalesOrgId()); } throw new
         * CommOrderException(CommOrderException.MSG_ERROR_OM_BASIC_CHECK_ERROR,
         * new Object[] { request, salesOrder }); }
         */
        // 本位币校验
        List<String> currencyParams = paramService.getParamValues(request, SystemProfileConstants.SPM_CURRENCY,
                SystemProfileConstants.ORG_TYPE_SALES, salesOrder.getSalesOrgId());
        if (currencyParams == null || currencyParams.isEmpty()
                || !currencyParams.iterator().next().equals(salesOrder.getCurrency())
                || salesOrder.getCurrency() == null) {
            if (log.isErrorEnabled()) {
                log.error("order basic data currency error");
            }
            throw new CommOrderException(CommOrderException.MSG_ERROR_OM_BASIC_CHECK_ERROR,
                    new Object[]{request, salesOrder});
        }
        // 销售渠道空校验
        if (salesOrder.getOrderType() == null) {
            if (log.isErrorEnabled()) {
                log.error("order basic data orderType error");
            }
            throw new CommOrderException(CommOrderException.MSG_ERROR_OM_BASIC_CHECK_ERROR,
                    new Object[]{request, salesOrder});
        }
        if (OrderConstants.ORDER_CHANNEL_SRVC.equals(salesOrder.getOrderType())) {
            if (salesOrder.getServiceCenterId() == null || salesOrder.getMemberId() == null) {
                if (log.isErrorEnabled()) {
                    log.error("order basic data servicecenter error");
                }
                throw new CommOrderException(CommOrderException.MSG_ERROR_OM_BASIC_CHECK_ERROR,
                        new Object[]{request, salesOrder});
            }
        }
        if (!OrderConstants.ORDER_CHANNEL_IPTC.equals(salesOrder.getOrderType())) {
            return;
        }
        // TODO: 销售渠道校验--用户是否为Ipoint用户

    }

    /**
     * 订单会员校验.
     *
     * @param request    统一上下文
     * @param salesOrder 销售订单
     * @throws CommOrderException 校验不通过时抛出
     */
    private void checkMember(IRequest request, SalesOrder salesOrder) throws CommOrderException {

        // 校验会员ID与订单类型
        if (!OrderConstants.ORDER_TYPE_DIRP.equals(salesOrder.getOrderType())
                && !OrderConstants.ORDER_TYPE_CONP.equals(salesOrder.getOrderType())
                && !OrderConstants.ORDER_TYPE_CONPT.equals(salesOrder.getOrderType())
                && !OrderConstants.ORDER_TYPE_STFP.equals(salesOrder.getOrderType())
                && !OrderConstants.ORDER_TYPE_STFPT.equals(salesOrder.getOrderType())) {
            if (salesOrder.getMemberId() == null) {
                if (log.isErrorEnabled()) {
                    log.error("order basic data memberId is not null");
                }
                throw new CommOrderException(CommOrderException.MSG_ERROR_OM_MEMBER_CHECK_ERROR,
                        new Object[]{request, salesOrder});
            }
        } else {
            if (salesOrder.getMemberId() != null) {
                if (log.isErrorEnabled()) {
                    log.error("order basic data memberId must be null");
                }
                throw new CommOrderException(CommOrderException.MSG_ERROR_OM_MEMBER_CHECK_ERROR,
                        new Object[]{request, salesOrder});
            }
            // 总监购买/员工购买/非会员购买 不需要会员信息,因此不需要对会员做校验.
            return;
        }

        // 会员可用性校验
        Member memberParams = new Member();
        memberParams.setMemberId(salesOrder.getMemberId());
        memberParams.setIsActive(OrderConstants.YES);
        if (!OrderConstants.ORDER_TYPE_NMDP.equals(salesOrder.getOrderType())) {
            memberParams.setMarketId(salesOrder.getMarketId());
            Long igiMarketId = spmMarketMapper.selectIGIMappingMarket(memberParams.getMarketId());
            if (igiMarketId != null) {
                memberParams.setIgiMarketId(igiMarketId);
            }
        }

        List<Member> members = memberMapper.queryMembersForOrder(memberParams);
        if (members.isEmpty()) {
            if (log.isErrorEnabled()) {
                log.error("order basic data memberId error");
            }
            throw new CommOrderException(CommOrderException.MSG_ERROR_OM_MEMBER_CHECK_ERROR,
                    new Object[]{request, salesOrder});
        }
        salesOrder.setMember(members.iterator().next());

        if (OrderConstants.ORDER_TYPE_BIGF.equals(salesOrder.getOrderType())) { // 会员生日校验-与订单类型校验
            // if
            /*
             * = commMemberService.isMemberBirthdayMonth(request,
             * salesOrder.getMemberId()
             */
            Map map = commMemberService.isMemberBirthdayMonth(request, salesOrder.getMemberId());

            if (Boolean.FALSE.equals(map.get("brithdayV"))) {
                if (log.isErrorEnabled()) {
                    log.error("order type is birthday but not check access");
                    throw new CommOrderException(CommOrderException.MSG_ERROR_OM_MEMBER_IS_NOT_BIRTHDAY_MONTH,
                            new Object[]{request, salesOrder});
                }
            }
            if (Boolean.TRUE.equals(map.get("brithdayOrder"))) {
                if (log.isErrorEnabled()) {
                    log.error("order type is birthday but not check access");
                    throw new CommOrderException(CommOrderException.MSG_ERROR_OM_MEMBER_HAD_GIFT_ORDER,
                            new Object[]{request, salesOrder});
                }
            }
        }

        if (OrderConstants.ORDER_CHANNEL_SRVC.equals(salesOrder.getOrderType())) {
            // 会员与服务中心校验-会员是否该服务中心的负责人.
            SpmServiceCenter spmServiceCenter = new SpmServiceCenter();
            spmServiceCenter.setServiceCenterId(salesOrder.getServiceCenterId());
            spmServiceCenter.setSalesOrgId(salesOrder.getSalesOrgId());
            spmServiceCenter = spmServiceCenterMapper.selectByPrimaryKey(spmServiceCenter);
            if (!salesOrder.getMemberId().equals(spmServiceCenter.getMarketId())) {
                if (salesOrder.getServiceCenterId() == null) {
                    if (log.isErrorEnabled()) {
                        log.error("order type is service center member id not access check");
                    }
                    throw new CommOrderException(CommOrderException.MSG_ERROR_OM_MEMBER_CHECK_ERROR,
                            new Object[]{request, salesOrder});
                }
            }
        }
    }

    /**
     * 支付调整校验.
     *
     * @param request    统一上下文
     * @param salesOrder 销售订单
     * @throws CommOrderException 校验不通过时抛出
     */
    private void checkAdjustMent(IRequest request, SalesOrder salesOrder) throws CommOrderException {
        List<SalesAdjustment> salesAdjustments = salesOrder.getAdjustMents();
        // 无支付调整不需校验
        if (salesAdjustments == null || salesAdjustments.isEmpty()) {
            return;
        }
        // 校验用户的Balance跟优惠券，如果没有用到Balance跟优惠券则不做查询
        Member checkMember = null;
        BigDecimal totalAdjust = BigDecimal.ZERO;
        for (SalesAdjustment salesAdjustment : salesAdjustments) {
            if (StringUtils.isEmpty(salesAdjustment.getAdjustmentType())
                    || salesAdjustment.getAdjustmentAmount() == null) {
                if (log.isErrorEnabled()) {
                    log.error("order adjust data adjustment error");
                }
                throw new CommOrderException(CommOrderException.MSG_ERROR_OM_ADJUSTMENT_CHECK_ERROR,
                        new Object[]{request, salesOrder});
            }
            if (salesAdjustment.getAdjustmentAmount().add(salesOrder.getOrderAmt()).compareTo(BigDecimal.ZERO) < 0) {
                if (log.isErrorEnabled()) {
                    log.error("order adjust data adjustment error not allow bigger then orderAmount");
                }
                throw new CommOrderException(CommOrderException.MSG_ERROR_OM_ADJUSTMENT_CHECK_ERROR,
                        new Object[]{request, salesOrder});
            }
            totalAdjust = totalAdjust.add(salesAdjustment.getAdjustmentAmount());
            if (OrderConstants.ADJUSTMENT_TYPE_EB.equals(salesAdjustment.getAdjustmentType())) {
                if (checkMember == null) {
                    checkMember = memberMapper.selectByPrimaryKey(salesOrder.getMemberId());
                    salesOrder.setMember(checkMember);
                }
                if (salesAdjustment.getAdjustmentAmount().compareTo(checkMember.getExchangeBalance()) > 0) {
                    if (log.isErrorEnabled()) {
                        log.error("order adjust data adjustment error not allow bigger then member exchangeBalance");
                    }
                    throw new CommOrderException(CommOrderException.MSG_ERROR_OM_ADJUSTMENT_CHECK_ERROR,
                            new Object[]{request, salesOrder});
                }
                // 与换货金额对比
                if (salesAdjustment.getAdjustmentAmount().compareTo(salesOrder.getExchangeAmt()) > 0) {
                    if (log.isErrorEnabled()) {
                        log.error("order adjust data adjustment error not allow bigger then order exchangeAmt");
                    }
                    throw new CommOrderException(CommOrderException.MSG_ERROR_OM_ADJUSTMENT_CHECK_ERROR,
                            new Object[]{request, salesOrder});
                }

            } else if (OrderConstants.ADJUSTMENT_TYPE_RB.equals(salesAdjustment.getAdjustmentType())) {

                List<String> isRBUse = paramService.getParamValues(request, SystemProfileConstants.PARAM_PAY_BY_RB,
                        SystemProfileConstants.ORG_TYPE_SALES);
                if (isRBUse == null || isRBUse.isEmpty() || isRBUse.iterator().next().equals(OrderConstants.NO)) {
                    if (log.isErrorEnabled()) {
                        log.error("order adjust data adjustment error RB not allow");
                    }
                    throw new CommOrderException(CommOrderException.MSG_ERROR_OM_ADJUSTMENT_CHECK_ERROR,
                            new Object[]{request, salesOrder});
                }
                if (checkMember == null) {
                    checkMember = memberMapper.selectByPrimaryKey(salesOrder.getMemberId());
                    salesOrder.setMember(checkMember);
                }
                if (salesAdjustment.getAdjustmentAmount().compareTo(checkMember.getRemainingBalance()) > 0) {
                    if (log.isErrorEnabled()) {
                        log.error("order adjust data adjustment error not allow bigger then member exchangeBalance");
                    }
                    throw new CommOrderException(CommOrderException.MSG_ERROR_OM_ADJUSTMENT_CHECK_ERROR,
                            new Object[]{request, salesOrder});
                }
            }
        }
        if (totalAdjust.add(salesOrder.getOrderAmt()).compareTo(BigDecimal.ZERO) < 0) {
            if (log.isErrorEnabled()) {
                log.error("order adjust data totalAdjustment error not allow bigger then orderAmount");
            }
            throw new CommOrderException(CommOrderException.MSG_ERROR_OM_ADJUSTMENT_CHECK_ERROR,
                    new Object[]{request, salesOrder});
        }
    }

    /**
     * 物流配送校验.
     *
     * @param request    统一上下文
     * @param salesOrder 销售订单
     * @throws CommOrderException 校验不通过时抛出
     */
    private void checkDelivery(IRequest request, SalesOrder salesOrder) throws CommOrderException {
        // 配送类型校验
        if (StringUtils.isEmpty(salesOrder.getDeliveryType())) {
            if (log.isErrorEnabled()) {
                log.error("order delivery data deliveryType error");
            }
            throw new CommOrderException(CommOrderException.MSG_ERROR_OM_DELIVERY_CHECK_ERROR,
                    new Object[]{request, salesOrder});
        }
        // 自动订货单必须为物流配送
        // if
        // (OrderConstants.ORDER_CHANNEL_AUTOS.equals(salesOrder.getOrderType())
        // &&
        // OrderConstants.ORDER_DELIVERY_PCKUP.equals(salesOrder.getDeliveryType()))
        // {
        // if (log.isErrorEnabled()) {
        // log.error("order delivery data deliveryType error");
        // }
        // throw new
        // CommOrderException(CommOrderException.MSG_ERROR_OM_DELIVERY_CHECK_ERROR,
        // new Object[] { request, salesOrder });
        // }
        // 订单地址校验
        List<SalesSites> sites = salesOrder.getSalesSites();
        if (sites == null || sites.isEmpty()) {
            if (salesOrder.getDeliveryLocationId() != null) {
                if (log.isErrorEnabled()) {
                    log.error("order sites data should not empty orderID is {}", salesOrder.getHeaderId());
                }
                throw new CommOrderException(CommOrderException.MSG_ERROR_OM_DELIVERY_CHECK_ERROR,
                        new Object[]{request, salesOrder});
            }
        }
        boolean hasBill = false;
        for (SalesSites site : sites) {
            if (OrderConstants.ORDER_SITES_SHIP.equals(site.getSiteType())) {
                if (OrderConstants.ORDER_DELIVERY_PCKUP.equals(salesOrder.getDeliveryType())) {
                    if (log.isErrorEnabled()) {
                        log.error("order delivery data not need when order deliveryType is pckup");
                    }
                    throw new CommOrderException(CommOrderException.MSG_ERROR_OM_DELIVERY_CHECK_ERROR,
                            new Object[]{request, salesOrder});
                }

            } else if (OrderConstants.ORDER_SITES_BILL.equals(site.getSiteType())) {
                hasBill = true;
            } else {
                if (log.isErrorEnabled()) {
                    log.error("order site data type is not BILL or SHIP ,this type is {}", site.getSiteType());
                }
                throw new CommOrderException(CommOrderException.MSG_ERROR_OM_DELIVERY_CHECK_ERROR,
                        new Object[]{request, salesOrder});
            }
        }
        if (!hasBill && OrderConstants.ORDER_DELIVERY_SHIPP.equals(salesOrder.getDeliveryType())) {
            if (log.isErrorEnabled()) {
                log.error("order bill site not null orderID {}", salesOrder.getHeaderId());
            }
            throw new CommOrderException(CommOrderException.MSG_ERROR_OM_DELIVERY_CHECK_ERROR,
                    new Object[]{request, salesOrder});
        }

        // 物流配送时的配送信息以及运费校验
        // TODO: 运费查询以及额外费用校验
        if (OrderConstants.ORDER_DELIVERY_SHIPP.equals(salesOrder.getDeliveryType())) {

            if (salesOrder.getLogistics() == null) {
                if (log.isErrorEnabled()) {
                    log.error("order delivery data not null when order deliveryType is SHIPP");
                }
                throw new CommOrderException(CommOrderException.MSG_ERROR_OM_DELIVERY_CHECK_ERROR,
                        new Object[]{request, salesOrder});
            }
            // 运费
            SalesLogistics logistics = salesOrder.getLogistics();
            if (logistics.getShippingTierId() == null || logistics.getShippingFee() == null) {
                if (log.isErrorEnabled()) {
                    log.error("order logistics error");
                }
                throw new CommOrderException(CommOrderException.MSG_ERROR_OM_DELIVERY_CHECK_ERROR,
                        new Object[]{request, salesOrder, logistics});
            }
        }
    }

    /**
     * 订单库存校验.
     *
     * @param request    统一上下文
     * @param salesOrder 销售订单
     * @return true/false
     */
    private boolean checkInv(IRequest request, SalesOrder salesOrder) {
        Map<String, BigDecimal> itemQuantity = new HashMap<String, BigDecimal>();
        List<SalesLine> lines = salesOrder.getLines();
        if (lines == null || lines.isEmpty()) {
            return true;
        }
        boolean isShip = false;
        if (OrderConstants.ORDER_DELIVERY_SHIPP.equals(salesOrder.getDeliveryType())) {
            isShip = true;
        }
        SalesSites deliverySite = null;
        if (salesOrder.getSalesSites() == null || salesOrder.getSalesSites().isEmpty()) {
            List<SalesSites> salesSites = salesSitesMapper.selectSitesByHeaderIdAndAutoshipFlag(OrderConstants.NO,
                    salesOrder.getHeaderId());
            salesOrder.setSalesSites(salesSites);
            for (SalesSites sites : salesSites) {
                if (OrderConstants.ORDER_SITES_SHIP.equals(sites.getSiteType())) {
                    deliverySite = sites;
                }
            }
        } else {
            for (SalesSites sites : salesOrder.getSalesSites()) {
                if (OrderConstants.ORDER_SITES_SHIP.equals(sites.getSiteType())) {
                    deliverySite = sites;
                }
            }
        }
        for (SalesLine line : lines) {
            if (StringUtils.isEmpty(line.getCountStockFlag()) || OrderConstants.NO.equals(line.getCountStockFlag())) {
                continue;
            }
            // 如果是计算库存虚拟商品包
            if (OrderConstants.LINE_ITEM_TYPE_VIRTUAL_PACKG_COUNT.equals(line.getItemType())) {
                List<InvItemHierarchy> invItemHierarchies = invItemHierarchyMapper
                        .getHierarchyByItemId(line.getItemId());
                for (InvItemHierarchy invItemHierarchy : invItemHierarchies) {
                    if (StringUtils.isEmpty(invItemHierarchy.getCountStockFlag())
                            || OrderConstants.NO.equals(invItemHierarchy.getCountStockFlag())) {
                        continue;
                    }
                    if (isShip) {
                        Long invOrgId = deliveryInvOrgMatchService.matchInvOrg(request, salesOrder.getSalesOrgId(),
                                deliverySite, invItemHierarchy.getItemId(),
                                line.getQuantity().multiply(invItemHierarchy.getQuantity()));
                        line.setDefaultInvOrgId(invOrgId);
                    }
                    String key = invItemHierarchy.getItemId() + ";" + line.getDefaultInvOrgId() + ";"
                            + invItemHierarchy.getItemName();
                    if (itemQuantity.containsKey(key)) {
                        BigDecimal quantity = itemQuantity.get(key);
                        quantity = quantity.add(line.getQuantity().multiply(invItemHierarchy.getQuantity()));
                        itemQuantity.put(key, quantity);
                    } else {
                        itemQuantity.put(key, line.getQuantity().multiply(invItemHierarchy.getQuantity()));
                    }
                }

            } else {

                if (isShip) {
                    Long invOrgId = deliveryInvOrgMatchService.matchInvOrg(request, salesOrder.getSalesOrgId(),
                            deliverySite, line.getItemId(), line.getQuantity());
                    line.setDefaultInvOrgId(invOrgId);
                }
                String key = line.getItemId() + ";" + line.getDefaultInvOrgId() + ";" + line.getItemName();
                if (itemQuantity.containsKey(key)) {
                    BigDecimal quantity = itemQuantity.get(key);
                    quantity = quantity.add(line.getQuantity());
                    itemQuantity.put(key, quantity);
                } else {
                    itemQuantity.put(key, line.getQuantity());
                }
            }
        }
        for (Map.Entry<String, BigDecimal> entry : itemQuantity.entrySet()) {
            String key = entry.getKey();
            BigDecimal quantity = entry.getValue();
            String[] ids = key.split(";");
            Long itemId = new Long(ids[0]);
            Long invId = new Long(ids[1]);
            InvOnhandQuantity criteria = new InvOnhandQuantity();
            criteria.setItemId(itemId);
            //update by furong.tang 2018.3.26 14:43 修改校验销售组织下默认库存的可用量,默认仓库由商品信息直接子查询出来
            //criteria.setOrganizationId(salesOrder.getSalesOrgId());
            //criteria.setOrganizationId(invId);
            BigDecimal availableQuantity = invOnhandQuantityService.getAvailableQuantity(request, criteria);
            if (availableQuantity == null) {
                availableQuantity = BigDecimal.ZERO;
            }
            /*sign by furong.tang*/
            if (availableQuantity.compareTo(quantity) < 0) {
                salesOrder.setAttribute1(OrderConstants.SALE_LINE_INV_QUANTITY_CHECK_OUT);
                salesOrder.setAttribute2(availableQuantity.toString());
                //salesOrder.setAttribute2(ids[2]);
                salesOrder.setAttribute3(availableQuantity.toString());
                return false;
            }
        }
        return true;
    }

    @Override
    public Long queryNumberwithGiftRule(final IRequest request, Long memberId, Date startTime, Date endTime) {
        // TODO Auto-generated method stub

        return salesOrderMapper.queryNumberwithGiftRule(memberId, startTime, endTime);
    }

    @Override
    public int updateSalesOrderGiftRuleFlag(IRequest request, Long memberId, Date startTime, Date endTime) {
        // TODO Auto-generated method stub
        return salesOrderMapper.updateSalesOrderGiftRuleFlag(memberId, startTime, endTime);
    }

    @Override
    public List<SalesOrder> queryAutoshipWpayOrder() {
        // TODO Auto-generated method stub
        return salesOrderMapper.queryAutoshipWpayOrder();
    }

    @Override
    public SalesOrder orderWPayToSave(IRequest request, Long orderId) throws CommOrderException, CommVoucherException {

        if (orderId == null) {
            return null;
        }
        // 优惠券
        voucherService.cancelOrderReturnCoupon(request, orderId);

        // 删除订单促销信息
        salesVoucherMapper.deleteByOrderId(orderId);

        // 删除订单支付调整

        salesadjustMentMapper.deleteByHeaderId(orderId);

        // 删除订单物流信息
        salesLogisticsMapper.deleteByHeaderId(orderId, BaseConstants.NO);
        // 删除订单地址信息
        salesSitesMapper.deleteByHeaderIdAndAutoshipFlag(BaseConstants.NO, orderId);

        // 更新订单
        SalesOrder salesOrder = this.getDetailOrder(request, orderId);

        salesOrder.setDeliveryLocationId(null);
        salesOrder.setDeliveryTo(null);
        salesOrder.setBillingLocationId(null);
        salesOrder.setBillingTo(null);
        salesOrder.setFreeShipping(BaseConstants.NO);
        salesOrder.setDeliveryType(OrderConstants.ORDER_DELIVERY_PCKUP);
        salesOrder.setCodFlag(BaseConstants.NO);
        salesOrder.setOrderStatus(OrderConstants.SALES_STATUS_SAVED);
        salesOrder.setDiscountAmt(BigDecimal.ZERO);
        salesOrder.setActrualPayAmt(salesOrder.getOrderAmt());
        // this.calculateOrderPrice(request, salesOrder);
        self().updateByPrimaryKey(request, salesOrder);
        return salesOrder;
    }

    @Override
    public int updateByPrimaryKey(IRequest request, SalesOrder salesOrder) {
        // TODO Auto-generated method stub
        return salesOrderMapper.updateByPrimaryKey(salesOrder);
    }

    @Override
    public Boolean isOrderReturnAll(IRequest request, SalesOrder salesOrder) {

        // 查询是否存在退货单

        BigDecimal quantity = salesOrderMapper.notReturnedQuantity(salesOrder.getHeaderId());
        if (quantity.compareTo(BigDecimal.ZERO) == 0) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }

    }
    @Override
    public String queryOrderStatusByKey(Long headerId){
        return queryOrderMapper.queryOrderStatusByKey(headerId);
    }
}
