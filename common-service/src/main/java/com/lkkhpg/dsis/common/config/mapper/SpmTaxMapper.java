/*
 *
 */
package com.lkkhpg.dsis.common.config.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lkkhpg.dsis.common.config.dto.SpmTax;

/**
 * 税率MAPPER.
 * 
 * @author chenjingxiong
 */
public interface SpmTaxMapper {

    int deleteByPrimaryKey(Long taxId);

    int insert(SpmTax record);

    int insertSelective(SpmTax record);

    SpmTax selectByPrimaryKey(Long taxId);

    int updateByPrimaryKeySelective(SpmTax record);

    int updateByPrimaryKey(SpmTax record);

    List<SpmTax> queryByTax(SpmTax taxId);

    List<SpmTax> queryForOrder(SpmTax tax);
    /**
     * 根据税码查询当前的有效税.
     * 
     * @param taxCode
     *            税码，系统中当前时间必须只有一个该税码激活的税
     * @return 税的信息
     */
    SpmTax getTaxByCode(@Param("taxCode") String taxCode);
    
    int getCountTaxByCode(@Param("taxCode") String taxCode);
}