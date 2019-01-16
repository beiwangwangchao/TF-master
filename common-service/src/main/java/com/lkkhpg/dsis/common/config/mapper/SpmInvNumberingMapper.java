/*
 *
 */
package com.lkkhpg.dsis.common.config.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.lkkhpg.dsis.common.config.dto.SpmInvNumbering;

/**
 * 发票编号发放Mapper.
 * 
 * @author li.peng@hand-china.com
 *
 */
public interface SpmInvNumberingMapper {

    /**
     * 添加发票编号发放.
     * 
     * @param record
     *            规则
     * @return 成功条数
     */
    int insertNumbering(SpmInvNumbering record);

    /**
     * 根据规则查询当前可用发票编号发放.
     * 
     * @param marketId
     *            规则名
     * 
     * @return 发票编号发放
     */
    List<SpmInvNumbering> selectNumberRule(@Param("marketId") String marketId);

    
    /**
     * 查询发票规则.
     * @param spmInvNumbering 查询条件.
     * @return 发票规则.
     */
    List<SpmInvNumbering> queryNumbering(SpmInvNumbering spmInvNumbering);
    
    
    /**
     * 查询统一市场下是否有规则时间重叠.
     * @param spmInvNumbering 待校验的规则.
     * @return 重叠的个数.
     */
    int selectCountInDateRange(SpmInvNumbering spmInvNumbering);
    
    /**
     * 更新发票编号发放规则.
     * 
     * @param spmInvNumbering
     *            发票编号发放
     * @return 成功条数
     */
    int updateNumbering(SpmInvNumbering spmInvNumbering);
    
    /**
     * 删除发票规则.
     * @param ruleId 规则id.
     * @return 成功条数.
     */
    int deleteByPrimaryKey(Long ruleId);

    /**
     * 查询市场ID.
     * 
     * @param orderId
     *            订单Id
     * @return 市场信息
     */
    Map<String, Object> selectMarket(@Param("orderId") Long orderId);
}