/*
 *
 */
package com.lkkhpg.dsis.common.product.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lkkhpg.dsis.common.product.dto.InvUnitOfMeasureB;

/**
 * 单位Mapper.
 * 
 * @author wangchao
 *
 */
public interface InvUnitOfMeasureBMapper {
    int deleteByPrimaryKey(String uomCode);

    int insert(InvUnitOfMeasureB record);

    int insertSelective(InvUnitOfMeasureB record);

    InvUnitOfMeasureB selectByPrimaryKey(String uomCode);

    int updateByPrimaryKeySelective(InvUnitOfMeasureB record);

    int updateByPrimaryKey(InvUnitOfMeasureB record);

    InvUnitOfMeasureB selectUomCodeLov();

    List<InvUnitOfMeasureB> selectUomCodeLov(InvUnitOfMeasureB invUom);

    InvUnitOfMeasureB getUom(@Param("uomCode") String uomCode);
    
    InvUnitOfMeasureB queryUomByCode(@Param("uomCode") String uomCode);
    

    List<InvUnitOfMeasureB> queryAllUom();

    List<InvUnitOfMeasureB> queryUnitNamesOfUomManage(InvUnitOfMeasureB invUomb);

    InvUnitOfMeasureB selectByUomCodeIsExist(@Param("uomCode") String uomCode);
    
    int selectByUomNameIsExist(@Param("name") String uomName);
    
    int insertUnitOfMeasureB(InvUnitOfMeasureB invUnitOfMeasureB);

    int updateUnitOfMeasureB(InvUnitOfMeasureB invUnitOfMeasureB);

}