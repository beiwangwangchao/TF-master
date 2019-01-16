/*
 *
 */

package com.lkkhpg.dsis.common.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.lkkhpg.dsis.common.exception.CommMemberException;
import com.lkkhpg.dsis.common.member.dto.MemAccount;
import com.lkkhpg.dsis.common.member.dto.MemAccountingBalance;
import com.lkkhpg.dsis.common.member.dto.MemAccountingTrx;
import com.lkkhpg.dsis.common.member.dto.MemAttribute;
import com.lkkhpg.dsis.common.member.dto.MemCard;
import com.lkkhpg.dsis.common.member.dto.MemRank;
import com.lkkhpg.dsis.common.member.dto.MemRelationship;
import com.lkkhpg.dsis.common.member.dto.MemSite;
import com.lkkhpg.dsis.common.member.dto.MemWithdraw;
import com.lkkhpg.dsis.common.member.dto.Member;
import com.lkkhpg.dsis.common.promotion.dto.VoucherAssign;
import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 会员Service接口.
 * 
 * @author frank.li
 */
public interface ICommMemberService extends ProxySelf<ICommMemberService> {

    /**
     * 获取会员详情.
     * 
     * @param request
     *            请求上下文
     * @param memberId
     *            会员Id
     * @return member 会员Dto
     */
    Member getMember(IRequest request, Long memberId);

    /**
     * 获取销售组织
     * @param memberId
     * @return
     */
    List<Member> selectSalesOrganization(Member memberId);

    /**
     * 发送站内信查询会员及市场
     * @param orderNumber
     * @return
     */
    List<Member>selectMember(String orderNumber);

    /**
     * 查询会员详情.
     * 
     * @param request
     *            请求上下文
     * @param member
     *            会员Dto
     * @param page
     *            页
     * @param pagesize
     *            分页大小
     * @return members 会员Dto
     */

    List<Member> queryMembers(IRequest request, Member member, int page, int pagesize);

    /**
     * 根据会员ID查询会员相关人.
     * 
     * @param request
     *            请求上下文
     * @param memberId
     *            会员Id
     * @param page
     *            页
     * @param pagesize
     *            分页大小
     * @return relationships
     */
    List<MemRelationship> queryRelationships(IRequest request, Long memberId, int page, int pagesize);

    /**
     * 根据会员ID查询会员属性.
     * 
     * @param request
     *            请求上下文
     * @param memberId
     *            会员Id
     * @param page
     *            页
     * @param pagesize
     *            分页大小
     * @return attributes
     */
    List<MemAttribute> queryAttributes(IRequest request, Long memberId, int page, int pagesize);

    /**
     * 根据会员ID查询地点.
     * 
     * @param request
     *            请求上下文
     * @param memberId
     *            会员Id
     * @param page
     *            页
     * @param pagesize
     *            分页大小
     * @return sites
     */
    List<MemSite> querySites(IRequest request, Long memberId, int page, int pagesize);

    /**
     * 根据会员ID查询账户.
     * 
     * @param request
     *            请求上下文
     * @param memberId
     *            会员Id
     * @param page
     *            页
     * @param pagesize
     *            分页大小
     * @return accounts
     */
    List<MemAccount> queryAccounts(IRequest request, Long memberId, int page, int pagesize);

    /**
     * 根据会员ID查询银行卡.
     * 
     * @param request
     *            请求上下文
     * @param memberId
     *            会员Id
     * @param page
     *            页
     * @param pagesize
     *            分页大小
     * @return cards
     */
    List<MemCard> queryCards(IRequest request, Long memberId, int page, int pagesize);

    /**
     * 根据会员ID查询等级.
     * 
     * @param request
     *            请求上下文
     * @param memberCode
     *            会员Id
     * @param monthFrom
     *            月份从
     * @param monthTo
     *            月份至
     * @param page
     *            页
     * @param pagesize
     *            分页大小
     * @return ranks
     * @throws CommMemberException
     *             会员统一异常
     */
    List<MemRank> queryRanks(IRequest request, String memberCode, Date monthFrom, Date monthTo, int page, int pagesize)
            throws CommMemberException;

    /**
     * 根据会员ID查询冲销节余.
     * 
     * @param request
     *            请求上下文
     * @param memberId
     *            会员Id
     * @param page
     *            页
     * @param pagesize
     *            分页大小
     * @return withdraws
     */
    List<MemWithdraw> queryWithdraws(IRequest request, Long memberId, int page, int pagesize);

    /**
     * 根据会员ID查询优惠券分配.
     * 
     * @param request
     *            请求上下文
     * @param memberId
     *            会员Id
     * @param page
     *            页
     * @param pagesize
     *            分页大小
     * @return withdraws
     */
    List<VoucherAssign> queryVoucherAssigns(IRequest request, Long memberId, int page, int pagesize);

    /**
     * 根据会员ID查询资产.
     * 
     * @param request
     *            请求上下文
     * @param memberId
     *            会员Id
     * @return accountingBalances
     */
    List<MemAccountingBalance> queryMemAccountingBalances(IRequest request, Long memberId);

    /**
     * 根据会员ID查询资产交易明细.
     * 
     * @param request
     *            请求上下文
     * @param memberId
     *            会员Id
     * @param accountingType
     *            账务类型
     * @param trxDateFrom
     *            事务处理日期从
     * @param trxDateTo
     *            事务处理日期至
     * @param page
     *            页
     * @param pagesize
     *            分页大小
     * @return accountingTrxs 账务事务处理
     */
    List<MemAccountingTrx> queryMemAccountingTrxs(IRequest request, Long memberId, String accountingType,
            Date trxDateFrom, Date trxDateTo, int page, int pagesize);

    /**
     * 保存会员.
     * 
     * @param request
     *            请求上下文
     * @param member
     *            会员Dto
     * @return member 会员Dto
     * @throws CommMemberException
     *             会员统一异常
     */
    Member saveMember(IRequest request, Member member) throws CommMemberException;

    /**
     * 根据会员ID查询下线树.
     * 
     * @param request
     *            请求上下文
     * @param memberId
     *            会员Id
     * @return 会员List
     */
    List<Member> queryDownLines(IRequest request, Long memberId);

    /**
     * 根据会员卡号查询OMK下线树.
     * 
     * @param request
     *            请求上下文
     * @param memberCode
     *            会员卡号
     * @param level
     *            层次
     * @param excludeSelf
     *            排除本身
     * @return 会员List
     */
    List<Member> getOmkDownLines(IRequest request, String memberCode, Integer level, String excludeSelf);

    /**
     * 根据会员ID查询下线树.
     * 
     * @param request
     *            请求上下文
     * @param memberId
     *            会员Id
     * @param level
     *            层次
     * @param excludeSelf
     *            排除本身
     * @return 会员List
     */
    List<Member> getDownLines(IRequest request, Long memberId, Integer level, String excludeSelf);

    /**
     * 根据会员ID查询上线.
     * 
     * @param request
     *            请求上下文
     * @param memberId
     *            会员Id
     * @param level
     *            层次
     * @param excludeSelf
     *            排除本身
     * @return 会员List
     */
    List<Member> getUpLines(IRequest request, Long memberId, Integer level, String excludeSelf);

    /**
     * 根据条件查询所有会员冲销节余.
     * 
     * @param request
     *            请求上下文
     * @param memberCode
     *            会员Id
     * @param periodFrom
     *            年份从
     * @param periodTo
     *            年份至
     * @param page
     *            页
     * @param pagesize
     *            分页大小
     * @return MemWithdraws 会员冲销列表
     */
    List<MemWithdraw> queryWithdrawsByParams(IRequest request, String memberCode, String periodFrom, String periodTo,
            int page, int pagesize);

    /**
     * 会员申请冲销节余.
     * 
     * @param request
     *            请求上下文
     * @param memWithdraw
     *            冲销节余DTO
     * @param remainingBalance
     *            会员剩余奖金
     * @param page
     *            页
     * @param pagesize
     *            分页大小
     * @param isConfirm
     *            是否确认执行冲销
     * @return responseData 响应数据
     * @throws CommMemberException
     *             会员统一异常
     */
    List<String> addWithdraw(IRequest request, MemWithdraw memWithdraw, String remainingBalance, int page, int pagesize,
            boolean isConfirm) throws CommMemberException;

    /**
     * 根据会员ID获取剩余奖金.
     * 
     * @param request
     *            请求上下文
     * @param memberId
     *            会员表主键
     * @param page
     *            页
     * @param pagesize
     *            分页大小
     * @return responseData 响应数据
     */
    List<String> getBalanceByMemberId(IRequest request, String memberId, int page, int pagesize);

    /**
     * 暂停会员.
     * 
     * @param request
     *            请求上下文
     * @param member
     *            会员DTO
     * @return 会员DTO
     * @throws CommMemberException
     *             会员统一异常
     */
    Member suspendMember(IRequest request, @StdWho Member member) throws CommMemberException;

    /**
     * 终止会员.
     * 
     * @param request
     *            请求上下文
     * @param member
     *            会员DTO
     * @return 会员DTO
     * @throws CommMemberException
     *             会员统一异常
     */
    Member terminateMember(IRequest request, @StdWho Member member) throws CommMemberException;

    /**
     * 激活会员.
     * 
     * @param request
     *            请求上下文
     * @param member
     *            会员DTO
     * @return 会员DTO
     * @throws CommMemberException
     *             会员统一异常
     */
    Member activeMember(IRequest request, @StdWho Member member) throws CommMemberException;

    /**
     * 会员下单时生日检查.
     * <ul>
     * <li>当前月份不是会员生日月份或者会员已经下过今年的生日礼物类型订单时返回false</li>
     * <li>当前月份是会员生日月份且未下过生日礼物类型的订单返回true</li>
     * </ul>
     * 
     * @param request
     *            请求上下文
     * @param memberId
     *            会员id
     * @return true/false
     */
    Map isMemberBirthdayMonth(IRequest request, Long memberId);

    /**
     * 订单创建时的会员查询.
     * 
     * @param request
     *            统一上下文
     * @param member
     *            会员条件
     * @param page
     *            页
     * @param pagesize
     *            分页大小
     * @return 满足条件的会员
     */
    List<Member> queryMembersForOrder(IRequest request, Member member, int page, int pagesize);

    /**
     * ipoint用户订单创建时的会员查询.
     * 
     * @param request
     *            统一上下文
     * @param member
     *            会员条件
     * @param page
     *            页
     * @param pagesize
     *            分页大小
     * @return 满足条件的会员
     */
    List<Member> queryMembersForIpointOrder(IRequest request, Member member, int page, int pagesize);

    /**
     * 根据会员id查询银行卡数据，银行卡名字和银行卡的后四位拼接在一起.
     * 
     * @param request
     *            请求上下文
     * @param memberId
     *            会员ID
     * @return cards
     */

    List<MemCard> autoQueryCard(IRequest request, Long memberId);

    /**
     * 校验VIP会员转为Distributor.
     * 
     * @param request
     *            请求上下文
     * @param memberId
     *            会员ID
     * @return Map, true-该会员可以转为经销商 false-不可以
     * @throws CommMemberException
     *             会员统一异常
     */
    Map<String, Object> validateForVIPToDIS(IRequest request, Long memberId) throws CommMemberException;

    /**
     * 获取终止期限.
     * 
     * @param request
     *            请求上下文
     * @param memberCode
     *            会员卡号
     * @return 结果集
     */
    Map<String, Object> getDeadLine(IRequest request, String memberCode);

    /**
     * 根据会员id查询银行卡数据(包括启用和停用).
     * 
     * @param request
     *            请求上下文
     * @param memberId
     *            会员ID
     * @return cards
     */
    List<MemCard> queryAllCardInfo(IRequest request, Long memberId);

    /**
     * 插入会员.
     * 
     * @param request
     *            请求上下文
     * @param record
     *            会员
     * @return 会员id
     */
    Long insert(IRequest request, @StdWho Member record);
}
