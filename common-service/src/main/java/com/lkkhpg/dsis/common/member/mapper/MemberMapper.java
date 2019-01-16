/*
 *
 */
package com.lkkhpg.dsis.common.member.mapper;

import com.lkkhpg.dsis.common.member.dto.ChangeOwnership;
import com.lkkhpg.dsis.common.member.dto.MemSite;
import com.lkkhpg.dsis.common.member.dto.Member;
import com.lkkhpg.dsis.common.member.dto.MemberList;
import com.lkkhpg.dsis.platform.dto.system.Account;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 会员Mapper.
 *
 * @author frank.li
 */
public interface MemberMapper {
    Long deleteByPrimaryKey(Long memberId);

    Long insert(Member record);

    Long insertSelective(Member record);

    Member selectByPrimaryKey(Long memberId);

    List<Member> selectMember(String outTradeNo);

    List<Member> selectByFullPrimaryKey(Member record);

    List<Member> selectMembers(Member record);

    List<Member> selectMembersForPick(Member record);

    List<Member> selectSalesOrganization(Member memberId);

    /**
     * 根据推荐人ID查询下线树.
     *
     * @param sponsorId 推荐人ID
     * @return 下线树列表
     */
    List<Member> selectBySponsorId(Long sponsorId);

    /**
     * 查询原上线Id.
     *
     * @param memberId 会员Id
     * @return Member 会员DTO
     */
    Member selectSponsorId(Long memberId);

    /**
     * 查询原市场Id.
     *
     * @param memberId 会员Id
     * @return Member 会员DTO
     */
    Member selectMarketId(Long memberId);

    Long updateByPrimaryKeySelective(Member record);

    Long updateByPrimaryKeyWithBLOBs(Member record);

    Long updateByPrimaryKey(Member record);

    List<Member> changeMemberSelect(Member record);

    List<Member> selectExamineMembers(Member record);

    int examineMember(@Param("member") Member member, @Param("newStatus") String status);

    List<Member> queryMembers(Map<String, Object> map);

    Member selectMembersByMemberCode(String memberCode);

    Member selectValidMemberByMemberCode(Map<String, Object> map);

    List<Member> selectValidMembers(Member member);

    List<Member> selectYearAgoValidMembers(Member member);

    Long updateStatusForNew(Member record);

    List<Member> selectSponsoresById(Long memberId);

    List<Member> queryByMemberAccountId(Member member);

    Member queryByMemberList(Long memberId);

    List<Member> selectMembersByStatusNotIn(Map<Object, List<String>> map);

    List<Member> queryMembersByMemberListId(@Param("memList") List<MemberList> memList);

    List<Member> queryMemNameAuto(String key);

    /* mclin 修改 */
    // Long updateStatusByMemberId(Member member);
    Long updateStatusByMemberId(@Param("member") Member member);

    List<Member> getMemberIdByCode(Map<String, Object> map);

    /**
     * mws用到.
     *
     * @param account 账户信息
     * @return 会员信息
     */
    Member selectByAccountId(Account account);

    /**
     * mws忘记用户名.
     *
     * @param member 会员信息
     * @return 会员所在信息
     */
    List<Member> checkPhoneOrEmail(Member member);

    /**
     * 检查用户是否已存在
     * @param member
     * @return
     */
    List<Member> checkUser(Member member);

    /**
     * mws我的团队-点击树形菜单获取信息.
     *
     * @param memberId 会员id
     * @return 个人信息
     */
    List<Member> getMemberTree(Long memberId);

    int updateMarketByMemberCode(@Param("memberCode") String memberCode,
                                 @Param("newSaleOrgCode") String newSaleOrgCode);

    void updateSynFlagByMemberId(Map<String, Object> map);

    List<Member> selectBySyncFlag(List<Long> marketIdList);

    void updateSponsorByMemberCode(Map<String, Object> map);

    void updateStatusByMemberCode(Map<String, Object> map);

    Member selectByPK(Long memberId);

    /**
     * 根据会员卡号查询OMK下线树.
     *
     * @param map 参数包括memberCode,层次level,排除本身excludeSelf
     * @return 会员DTO列表
     */
    List<Member> selectOmkDownLineByMemberCode(Map<String, Object> map);

    /**
     * 根据会员Id查询下线树.
     *
     * @param map 参数包括memberId,层次level,排除本身excludeSelf
     * @return 会员DTO列表
     */
    List<Member> selectDownLineByMemberId(Map<String, Object> map);

    /**
     * 根据会员Id查询下线树.
     *
     * @param map 参数包括memberId,层次level,排除本身excludeSelf
     * @return 会员DTO列表
     */
    List<Member> selectUpLineByMemberId(Map<String, Object> map);

    String isMemberIdNumberExist(Map<String, Object> map);

    String isMemberPhoneNoExit(Map<String,Object>map);

    String isMemberIdNumberExistForMY(Map<String, Object> map);

    String isSpouseIdNumberExist(Map<String, Object> map);

    String isBrNumberExist(Map<String, Object> map);

    List<Member> selectMembersByDate(Map<String, Object> map);

    /**
     * 根據市場代碼查询D-App未同步的数据.
     *
     * @param map
     * @return 会员DTO列表
     */
    List<Member> selectMemberByDAppSyncFlag();

    /**
     * 更新会员DAPP同步标记.
     *
     * @param member 会员DTO
     */
    void updateDAppSyncFlag(Member member);

    List<Member> selectMemberByMarket(Map<String, Object> map);

    List<Member> selectMemberByDappSync();

    /**
     * 根据消息里的会员Id列表获取会员
     *
     * @param memberIds 会员Id列表，一组会员Id的字符串，以"{}"包含，以","分隔，例如{10001},{10002}
     * @param adOptIn
     * @param sysMsgIn
     * @return 会员DTO列表
     */
    List<Member> selectMemberByMsgMemberIds(@Param("memberIds") String memberIds, @Param("adOptIn") String adOptIn,
                                            @Param("sysMsgIn") String sysMsgIn);

    /**
     * 查询Service Center中满足条件的会员.
     *
     * @param member 会员DTO
     * @return 满足条件的会员
     */
    List<Member> selectMemberServiceCenter(Member member);

    List<ChangeOwnership> selectWaitingChgMember();

    Long selectKey();

    Long selectMaxSeqByMemberCode(String memberCode);

    int upgradeMemberId(Map<String, Object> param);

    /**
     * 订单创建时的会员查询.
     *
     * @param member 会员DTO
     * @return 满足条件的会员
     */
    List<Member> queryMembersForOrder(Member member);

    List<Member> queryMembersForIpointOrder(Member member);

    Member getDistributorByDrbNumAndIdNum(Map<String, Object> map);

    Integer getRankFromIsgPostCode(String memberCode);

    BigDecimal getTPFromGdsSalaryLctTT(String memberCode);

    List<Member> selectByDappNo(String appNo);

    List<Member> queryMembersInTw(@Param("memberCode") String memberCode);

    /**
     * 会员转市场后失效其当前市场下的所有优惠券.
     *
     * @param member 会员DTO
     */
    void updateCouponInvalid(Member member);

    /**
     * 检查是否有重复配偶姓名.
     *
     * @param map 字段映射
     * @return Y-有,N-无
     */
    String isSameSpouseName(Map<String, Object> map);

    Long checkVIP(String memberCode);

    /**
     * 根据会员卡号查询市场CODE.
     *
     * @param memberCode 会员卡号
     * @return 市场CODE
     */
    String selectMarketCodeByMemberCode(String memberCode);

    List<MemSite> queryMemSites(String memberCode);

    /**
     * 根据市场CODE和会员卡号查询会员.
     *
     * @param map 查询条件映射
     * @return 会员信息
     */
    Member selectMemberByMarketAndMemberCode(Map<String, Object> map);

    /**
     * 根据角色判断是否有功能权限.
     *
     * @param roleId       角色ID
     * @param functionCode 功能代码
     * @return Y为是, null为否
     */
    String isAccessToFunctionByRole(@Param("roleId") Long roleId, @Param("functionCode") String functionCode);

    /**
     * 该卡号是否存在会员表或海外推荐人表.
     *
     * @param dealerNo 会员卡号
     * @return Y为是, null为否
     */
    String isInMemtabAndMemOvertab(@Param("memberCode") String dealerNo);


    /**
     * 查询折扣
     *
     * @param member 会员
     * @return
     */
    List<Member> queryDiscount(Member member);

    /*
     * 查询会员是否存在
     *
     * updated at 2018/01/18 14:06
     */
    int calculateMemberCount(@Param("memberId") Long memberId);


    /**
     * 添加新折扣
     *
     * @param member updated by 11816 at 2018/01/18 14:50
     * @return
     */
    long addNewDiscountAmt(Member member);

    /**
     * 调整折扣加
     *
     * @param member updated by 11816 at 2018/01/18 14:50
     * @return
     */
    long adjustDiscountAmtIn(Member member);

    /**
     * 调整折扣减
     *
     * @param member updated by 11816 at 2018/01/18 14:50
     * @return
     */
    long adjustDiscountAmtOut(Member member);

    /**
     * 查询该用户是否没有添加过折扣
     *
     * @param member
     * @return
     */
    long queryIfDiscountAmtIsNull(Member member);

    /**
     * 根据会员ID查找市场Id和公司Id
     * add by furong.tang
     *
     * @param memberId
     * @return
     */
    Member queryMarketIdAndCompanyIdByMemberId(@Param("memberId") Long memberId);

    /**
     *
     * @return
     */
    List<Member> queryMemberForDiscountLov();

    /*
     *更新折扣金额
     */
    int updateMemberDiscount(@Param("memberId") Long memberId, @Param("discountAmt") Long discountAmt);

    Integer createAccount(@Param("uname") String uname, @Param("password") String password,@Param("phoneNum") String phoneNum);

    /**
     *
     * 查询是否存在相同号码的会员
     * added by 13525 at 2018.03.07
     */

    List<Member> selectMemberByPhone(Member member);

    /**
     * 查询会员可用remainingBalance
     * @param memberId
     * @return
     */
    Member queryRemBalByPrimaryKey(@Param("memberId") Long memberId);
}