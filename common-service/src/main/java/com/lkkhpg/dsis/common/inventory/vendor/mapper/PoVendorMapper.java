/*
 *
 */
package com.lkkhpg.dsis.common.inventory.vendor.mapper;

import java.util.List;

import com.lkkhpg.dsis.common.inventory.vendor.dto.PoVendor;

/**
 * 供应商管理Mapper.
 * 
 * @author liang.rao
 *
 */
public interface PoVendorMapper {
    
    List<PoVendor> selectByRecord(PoVendor record);
    
    int updateByRecord(PoVendor record);
    
    int insertByRecord(PoVendor record);
    
    PoVendor selectByName(PoVendor record);
}
