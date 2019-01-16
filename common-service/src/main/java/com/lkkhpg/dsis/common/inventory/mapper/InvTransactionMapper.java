/*
 *
 */
package com.lkkhpg.dsis.common.inventory.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.lkkhpg.dsis.common.config.dto.SpmInvOrganization;
import com.lkkhpg.dsis.common.inventory.dto.InvTransaction;

/**
 * 库存事务MAPPER.
 * 
 * @author chenjingxiong
 */
public interface InvTransactionMapper {

    int insert(InvTransaction record);

    List<InvTransaction> selectByPrimaryKey(long trxId);

    List<InvTransaction> getTransactions(Map<String, Object> map);

    List<Map<String, Object>> getOrganization();

    List<Map<String, Object>> queryOrgsByRole();

    List<Map<String, Object>> getOrganization1();

    List<Map<String, Object>> getItems(Map<String, Object> map);

    List<SpmInvOrganization> getCurrentOrganization(Map<String, Object> map);

    /**
     * 先进先出方式,获取本月库存组织下的成本.
     * 
     * @param costOrgId
     *            库存归集中心
     * @param trxDateName
     *            事务处理日期年月(eg:'201601')
     * @return 成本信息
     */
    List<InvTransaction> queryInvTrxQtySumOfFIFO(@Param("costOrgId") Long costOrgId,
            @Param("trxDateName") String trxDateName);

    /**
     * 加权平局方式获取参数年、月、库存组织下的成本.
     * 
     * @param invOrgId
     *            库存组织
     * @param year
     *            起始日期
     * @param month
     *            终止日期
     * @return 成本信息
     */
    List<InvTransaction> queryCostDetailsOfAvrag(@Param("invOrgId") Long invOrgId, @Param("year") Integer year,
            @Param("month") Integer month);

    Integer getQuantity(InvTransaction record);

    /**
     * 查询满足参数条件下是否有产生事务.
     * 
     * @param invOrgId
     *            库存归集中心Id
     * @param year
     *            年
     * @param month
     *            月
     * @return 事务记录
     */
    List<InvTransaction> queryTrxs(@Param("costOrgId") Long costOrgId, @Param("year") Integer year,
            @Param("month") Integer month);
    Integer queryOutRefund(String outRefundNo);
}