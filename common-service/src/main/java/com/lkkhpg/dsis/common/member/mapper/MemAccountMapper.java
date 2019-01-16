/*
 *
 */
package com.lkkhpg.dsis.common.member.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lkkhpg.dsis.common.member.dto.MemAccount;

/**
 * 会员账户Mapper.
 * 
 * @author frank.li
 */
public interface MemAccountMapper {
    int deleteByPrimaryKey(Long accountId);

    int insert(MemAccount record);

    int insertSelective(MemAccount record);

    MemAccount selectByPrimaryKey(Long accountId);

    List<MemAccount> selectByMemberId(Long memberId);

    List<MemAccount> selectMemAccounts(MemAccount record);

    int updateByPrimaryKeySelective(MemAccount record);

    int updateByPrimaryKey(MemAccount record);

    int updateByAccountId(MemAccount record);

    int deleteByMemberId(Long memberId);

    int upgradeMemberId(@Param("oldMemberId") Long oldMemberId, @Param("newMemberId") Long newMemberId);

    /**
     * 触发审计.
     * 
     * @author xiawang.liu
     * @param memAccount
     *            会员账户信息
     * @return 更新条数
     */
    int updateAccountLastUpdateDate(MemAccount memAccount);

    Long selectAccountId(@Param("memberId") Long memberId);

    /**
     * 校验是否存在重复的账户号码(银行编码和账户号码).
     * 
     * @param bankId
     *            银行表主键
     * @param accountNumber
     *            账户号码
     * @param memberId
     *            会员表主键
     * @param marketCode
     *            会员市场code
     * @return 重复的会员卡号
     */
    String isExistAccount(@Param("bankId") Long bankId, @Param("accountNumber") String accountNumber,
            @Param("memberId") Long memberId, @Param("marketCode") String marketCode);
}