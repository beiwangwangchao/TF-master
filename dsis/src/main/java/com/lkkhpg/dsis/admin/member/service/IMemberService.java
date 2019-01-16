/*
 *
 */

package com.lkkhpg.dsis.admin.member.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.lkkhpg.dsis.admin.integration.gds.exception.IntegrationException;
import com.lkkhpg.dsis.admin.member.exception.MemberException;
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
import com.lkkhpg.dsis.platform.annotation.AuditEntry;
import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 会员Service接口.
 *
 * @author frank.li
 */
public interface IMemberService extends ProxySelf<IMemberService> {

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
     * 消息中查询激活状态的会员.
     *
     * @param request
     *            请求上下文
     * @param member
     *            会员
     * @param page
     *            页
     * @param pagesize
     *            分页大小
     * @return sites
     */
    List<Member> queryMessageMember(IRequest request, Member member, int page, int pagesize);

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
    @AuditEntry("MEMBER")
    Member saveMember(IRequest request, @StdWho Member member) throws CommMemberException;

    /**
     * 创建会员账号.
     *
     * @param request
     *            请求上下文
     * @param member
     *            会员Dto
     * @throws CommMemberException
     *             会员统一异常
     */
    @AuditEntry("MEMBER")
    void createMemAccount(IRequest request, @StdWho Member member) throws CommMemberException;

    /**
     * 提交会员.
     *
     * @param request
     *            请求上下文
     * @param member
     *            会员Dto
     * @return member 会员Dto
     * @throws MemberException
     *             会员统一异常
     */
    @AuditEntry("MEMBER")
    Member submitMember(IRequest request, @StdWho Member member) throws MemberException;

    /**
     * 更改所有权，原会员信息保存到快照会员，预存会员信息覆盖原会员信息，删除预存会员信息.
     *
     * @param request
     *            请求上下文
     * @param sourceMemberId
     *            原会员Id
     * @param tempMemberId
     *            预存会员Id
     * @return snapshootMemberId 快照会员Id
     * @throws MemberException
     *             会员统一异常
     */
    @AuditEntry("MEMBER")
    Long changeOwnership(IRequest request, Long sourceMemberId, Long tempMemberId) throws MemberException;

    /**
     * 查询原上线（联系人）Id.
     *
     * @param request
     *            请求上下文
     * @param memberId
     *            会员Id
     * @return member 会员DTO
     */
    Member querySponsorId(IRequest request, Long memberId);

    /**
     * 查询原市场Id.
     *
     * @param request
     *            请求上下文
     * @param memberId
     *            会员Id
     * @return member 会员DTO
     */
    Member queryMarketId(IRequest request, Long memberId);

    /**
     * 检查新上线的状态.
     *
     * @param request
     *            请求上下文
     * @param memberCode
     *            会员Id
     * @return boolean 返回true为激活,返回false为非激活
     */
    boolean checkNewUpstreamStatus(IRequest request, String memberCode);

    /**
     * 变更市场/上线的会员查询功能.
     *
     * @param request
     *            请求上下文
     * @param member
     *            会员DTO
     * @param page
     *            页
     * @param pagesize
     *            分页大小
     * @return members 会员列表
     * @throws MemberException
     *             会员异常
     */
    List<Member> queryMember(IRequest request, Member member, int page, int pagesize) throws MemberException;

    /**
     * 新会员审核查询.
     *
     * @param request
     *            请求上下文
     * @param member
     *            会员DTO
     * @param page
     *            页
     * @param pagesize
     *            分页大小
     * @return members 会员列表
     */
    List<Member> queryExamineMembers(IRequest request, Member member, int page, int pagesize);

    /**
     * 新会员激活.
     *
     * @param request
     *            请求上下文
     * @param member
     *            会员DTO 会员DTO
     * @throws MemberException
     *             会员统一异常
     */
    @AuditEntry("MEMBER")
    void activeMember(IRequest request, @StdWho Member member) throws MemberException;

    /**
     * 新会员拒绝.
     *
     * @param request
     *            请求上下文
     * @param member
     *            会员DTO
     * @throws MemberException
     *             会员统一异常
     */
    @AuditEntry("MEMBER")
    void rejectMember(IRequest request, @StdWho Member member) throws MemberException;

    /**
     * 批量新会员激活.
     *
     * @param request
     *            请求上下文
     * @param members
     *            会员列表
     * @throws MemberException
     *             会员统一异常
     */
    @AuditEntry("MEMBER")
    void batchActive(IRequest request, @StdWho List<Member> members) throws MemberException;

    /**
     * 批量新会员拒绝.
     *
     * @param request
     *            请求上下文
     * @param members
     *            会员列表
     * @throws MemberException
     *             会员统一异常
     */
    @AuditEntry("MEMBER")
    void batchReject(IRequest request, @StdWho List<Member> members) throws MemberException;

    /**
     * 查询会员.
     *
     * @param request
     *            请求上下文
     * @param member
     *            会员Dto
     * @param yearFrom
     *            年份从
     * @param yearTo
     *            年份至
     * @param month
     *            月份
     * @param userType
     *            用户类型
     * @param page
     *            页
     * @param pagesize
     *            分页大小
     * @return members 会员列表
     */
    List<Member> queryMembers(IRequest request, Member member, String month, String yearFrom, String yearTo,
                              String userType, int page, int pagesize);

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
     * @param memberId
     *            会员表ID
     * @param memberName
     *            会员姓名
     * @param page
     *            页
     * @param pagesize
     *            分页大小
     * @return MemWithdraws 会员冲销列表
     */
    List<MemWithdraw> queryWithdrawsByParams(IRequest request, String memberCode, String periodFrom, String periodTo,
                                             Long memberId, String memberName, int page, int pagesize, Long marketId);

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
     *             会员异常
     */
    List<String> addWithdraw(IRequest request, @StdWho MemWithdraw memWithdraw, String remainingBalance, int page, int pagesize,
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
     * 根据memberId查找此人12个月内是否有推荐线下会员.
     *
     * @param member
     *            会员DTO
     * @return boolean
     */
    Map<String, Object> validTerminate(Member member);

    /**
     * 查找出状态为非终止的会员.
     *
     * @return members 会员列表
     */
    List<Member> queryActiveMembers();

    /**
     * 安全更新会员表的status.
     *
     * @param record
     *            会员Dto
     * @return boolean
     */
    @AuditEntry("MEMBER")
    boolean updateByPrimaryKeySelective(Member record);

    /**
     * 根据会员ID查询此人的PV是否为0.
     *
     * @param memberId
     *            会员ID
     * @return boolean
     */
    boolean checkMemPVInYear(Long memberId);

    /**
     * 用户管理模块用. auto complete查询会员的中文名/英文名
     *
     * @param key
     *            auto complete查询条件
     * @return 会员信息
     */
    List<Member> queryMemNameAuto(String key);

    /**
     * 根据会员ID获取剩余RB.
     *
     * @param memberId
     *            会员id
     * @return Long 响应数据
     */
    Long getRBBymemberId(Long memberId);

    /**
     * @param memberId
     * @return 操作结果
     */
    boolean clearRBalance(Long memberId);

    /**
     * 根据会员ID获取激活状态的会员.
     *
     * @param request
     *            请求上下文
     * @param memberCode
     *            会员ID
     * @return 操作结果
     */
    List<Member> getMemberIdByCode(IRequest request, String memberCode, Long marketId);

    /**
     * 判断当前月份是否是该会员的生日月份.
     *
     * @param request
     *            请求上下文
     * @param memberId
     *            会员Id
     * @return Map
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
     * @throws IntegrationException
     *             接口统一异常
     */

    List<Member> queryMembersForOrder(IRequest request, Member member, int page, int pagesize)
            throws IntegrationException;

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
     * @throws IntegrationException
     *             接口统一异常
     */

    List<Member> queryMembersForIpointOrder(IRequest request, Member member, int page, int pagesize)
            throws IntegrationException;

    /**
     * 会员查询页-发送通知，保存通知人.
     *
     * @param request
     *            统一上下文
     * @param memberIds
     *            选中的会员信息
     * @return groudId 批主键
     */
    List<Long> saveReceiver(IRequest request, List<Long> memberIds);

    /**
     * 会员详情页 - 更改角色.
     *
     * @param request
     *            统一上下文
     * @param memberId
     *            会员ID
     * @throws CommMemberException
     *             会员统一异常
     */
    void vipChangeToDistributor(IRequest request, Long memberId) throws CommMemberException;

    /**
     * 获取当前会员完整的银行卡信息（解密卡号）.
     *
     * @param request
     *            统一上下文.
     * @param memberId
     *            会员id.
     * @return 返回结果.
     */
    List<MemCard> queryBankInfo(IRequest iRequest, Long memberId);

    /**
     * 通过编码请求gds会员，并插入dsis中.
     *
     * @param request
     *            请求上下文
     * @param memberCode
     *            会员编码
     * @return 会员
     * @throws IntegrationException
     *             接口统一异常
     */

    Member insertGdsMemberToDsis(IRequest request, String memberCode) throws IntegrationException;

    /**
     * 根据会员编号获取会员地址（账单，发运）.
     *
     * @param memberCode
     *            会员编号.
     * @param request
     *            统一上下文.
     * @return 返回结果.
     */
    List<MemSite> queryMemSites(String memberCode, IRequest request);

    /**
     * 用户是否有权限访问最终奖金功能.
     *
     * @param roleId
     *            角色ID
     * @return true为是,false为否
     */
    Boolean isRoleAssignBonusFinal(Long roleId);

    /**
     * 校验是否存在重复的银行账户.
     *
     * @param request
     *            请求上下文
     * @param member
     *            会员DTO
     * @return 会员卡号
     */
    String isSameAccount(IRequest request, Member member);


    /**
     *
     * @param request
     *          请求上下文
     * @param member
     *          会员
     * @return
     *          折扣相关
     */
    List<Member> queryDiscount(IRequest request, Member member, int page, int pageSize);


    /**
     * 根据市场获取用户
     * @param request
     * @return
     */
    List<Member> queryMemberByMarket(IRequest request);


    int updateMemberDiscount(IRequest request, Long memberId, Long discountAmt);
}

