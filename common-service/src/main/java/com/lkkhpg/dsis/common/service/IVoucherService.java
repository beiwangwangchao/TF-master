/*
 *
 */

package com.lkkhpg.dsis.common.service;

import java.util.List;

import com.lkkhpg.dsis.common.exception.CommMemberException;
import com.lkkhpg.dsis.common.exception.CommVoucherException;
import com.lkkhpg.dsis.common.member.dto.Member;
import com.lkkhpg.dsis.common.member.dto.MemberList;
import com.lkkhpg.dsis.common.promotion.dto.PdmVoucherGift;
import com.lkkhpg.dsis.common.promotion.dto.PdmVoucherItem;
import com.lkkhpg.dsis.common.promotion.dto.PdmVoucherMember;
import com.lkkhpg.dsis.common.promotion.dto.Voucher;
import com.lkkhpg.dsis.common.promotion.dto.VoucherAssign;
import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 优惠券Service接口.
 * 
 * @author frank.li
 */
public interface IVoucherService extends ProxySelf<IVoucherService> {

    /**
     * 创建优惠券.
     * 
     * @param request
     *            请求上下文
     * @param voucher
     *            优惠券Dto
     * @return boolean 操作成功与否标志
     */
    boolean createVoucher(IRequest request, Voucher voucher);

    /**
     * 取消优惠券.
     * 
     * @param request
     *            请求上下文
     * @param voucher
     *            优惠券Dto
     * @return boolean 操作成功与否标志
     */
    boolean cancelVoucher(IRequest request, Voucher voucher);

    /**
     * 应用优惠券.
     * 
     * @param request
     *            请求上下文
     * @param voucherId
     *            优惠券ID
     * @param memberId
     *            会员ID
     * @return boolean 操作成功与否标志
     */
    boolean applyVoucher(IRequest request, Long voucherId, Long memberId);

    /**
     * 优惠券查询.
     * 
     * @param request
     *            请求上下文
     * @param voucher
     *            优惠券DTO
     * @param page
     *            页
     * @param pageSize
     *            页大小
     * @return 响应数据
     */
    List<Voucher> queryVoucher(IRequest request, Voucher voucher, int page, int pageSize);

    /**
     * 编辑优惠券.
     * 
     * @param request
     *            请求上下文
     * @param voucher
     * @return 响应数据
     * @throws CommVoucherException
     *             异常
     */
    List<Voucher> saveVoucher(IRequest request, @StdWho Voucher voucher) throws CommVoucherException;

    /**
     * 编辑优惠券分配会员表.
     * 
     * @param request
     * @param voucher
     * @return 响应数据
     * @throws CommVoucherException 
     */
    List<PdmVoucherMember> savePdmVoucherMember(Voucher voucher) throws CommVoucherException;

    /**
     * 编辑优惠券商品.
     * 
     * @param request
     *            请求上下文
     * @param voucher
     * @return 响应数据
     */
    List<PdmVoucherItem> savePdmVoucherItem(Voucher voucher);

    /**
     * 编辑优惠券赠品.
     * 
     * @param request
     *            请求上下文
     * @param voucher
     * @return 响应数据
     */
    List<PdmVoucherGift> savePdmVoucherGift(Voucher voucher);

    /**
     * 发放优惠券.
     * 
     * @param requestContext
     * @param voucher
     * @return 响应数据
     * @throws CommVoucherException
     *             异常
     */
    List<Voucher> saveVoucherAssign(IRequest requestContext, @StdWho Voucher voucher) throws CommVoucherException;

    /**
     * 删除优惠券商品.
     * 
     * @param requestContext
     *            请求
     * @param pdmVouchergifts
     *            pdmVouchergifts
     * @return true
     */
    boolean removeItem(IRequest requestContext, List<PdmVoucherItem> pdmVouchergifts);

    /**
     * 删除优惠券赠品.
     * 
     * @param requestContext
     *            请求
     * @param pdmVouchergifts
     *            pdmVouchergifts
     * @return true
     */
    boolean removeGift(IRequest requestContext, List<PdmVoucherGift> pdmVouchergifts);

    /**
     * 删除优惠券会员.
     * 
     * @param requestContext
     *            请求
     * @param pdmVoucherMembers
     *            pdmVoucherMembers
     * @return true
     */
    boolean removeMember(IRequest requestContext, List<PdmVoucherMember> pdmVoucherMembers);

    /**
     * 查询分配会员.
     * 
     * @param request
     * @param voucherId
     * @param page
     * @param pageSize
     * @return 响应数据
     */
    List<PdmVoucherMember> queryPdmVoucherMember(IRequest request, Long voucherId);
    
    PdmVoucherMember queryMember(IRequest request, Long memberId);

    /**
     * 查询优惠券商品.
     * 
     * @param request
     * @param voucherId
     * @param page
     * @param pageSize
     * @return 响应数据
     */
    List<PdmVoucherItem> queryPdmVoucherItem(IRequest request, Long voucherId, int page, int pageSize);

    /**
     * 查询赠品.
     * 
     * @param request
     * @param voucherId
     * @param page
     * @param pageSize
     * @return 响应数据
     */
    List<PdmVoucherGift> queryPdmVoucherGift(IRequest request, Long voucherId, int page, int pageSize);

    /**
     * 优惠券分配.
     * 
     * @param request
     * @param voucherAssign
     * @return 响应数据
     * @throws CommVoucherException 
     */
    VoucherAssign createVoucherAssign(IRequest request, @StdWho VoucherAssign voucherAssign) throws CommVoucherException;

    /**
     * 获取优惠券.
     * 
     * @param requestContext
     * @param voucherId
     * @return 响应数据
     */
    List<Voucher> getVoucher(IRequest requestContext, Long voucherId);

    /**
     * 根据会员、市场、销售组织查询可用优惠券.
     * 
     * @param request
     *            统一上下文
     * @param memberId
     *            会员ID
     * @return 满足条件的优惠券集合
     */
    List<Voucher> getMemberVouchers(IRequest request, Long memberId);

    /**
     * 根据会员、市场、销售组织查询可用优惠券.
     * 
     * @param request
     *            统一上下文
     * @param memberId
     *            会员ID
     * @param salesOrgId
     *            销售组织ID
     * @return 满足条件的优惠券集合
     */
    List<Voucher> getMemberVouchers(IRequest request, Long memberId, Long salesOrgId);

    /**
     * 验证优惠券导入会员.
     * 
     * @param request
     *            请求上下文
     * @param list
     *            会员List
     * @param idType
     *            单据类型
     * @param mentionId
     *            单据ID
     * @param marketId
     *            市场ID
     * @return 响应数据
     * @throws CommMemberException
     */
    Long validate(IRequest request, List<MemberList> list, String idType, Long mentionId, Long marketId)
            throws CommMemberException;

    /**
     * 导入会员.
     * 
     * @param request
     *            请求上下文
     * @param groupId
     * @param mentionId
     * @return 响应数据
     */
    Long importMemebers(IRequest request, Long groupId, Long mentionId);

    /**
     * 更新Ecoupon为失效状态.
     * 
     * @param request
     *            请求上下文
     * @param voucher
     *            优惠券对象
     */
    void updateEcouponInvalid(IRequest request, Voucher voucher);

    /**
     * 根据会员、市场、销售组织查询可用优惠券(VIP).
     * 
     * @param request
     *            统一上下文
     * @param memberId
     *            会员ID
     * @return 满足条件的优惠券集合
     */
    List<Voucher> getMemberVouchersForVIP(IRequest request, Long memberId);

    /**
     * 取消订单返回优惠券.
     * 
     * @param request
     *            统一上下文
     * @param orderHeaderId
     *            订单头ID
     * @throws CommVoucherException
     *             优惠券异常
     */
    /**
     * 
     * @param request
     * @param orderHeaderId
     * @throws CommVoucherException 
     * 
     */
    void cancelOrderReturnCoupon(IRequest request, Long orderHeaderId) throws CommVoucherException;

    /**
     * Ecoupon校验.
     * 
     * @param request
     *            统一上下文
     * @param orderHeaderId
     *            订单头ID
     */
    void validateEcoupon(IRequest request, Long orderHeaderId);

    /**
     * 失效会员转市场前未用的优惠券及Ecoupon.
     * 
     * @param request
     *            统一上下文
     * @param member
     *            会员DTO
     */
    void invalidMemberVoucher(IRequest request, Member member);

    /**
     * 失效优惠券(JOB).
     * @param request
     *            统一上下文
     */
    void invalidVoucherForJob(IRequest request);
}
