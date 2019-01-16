/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.order.mapper;

import java.util.List;

import com.lkkhpg.dsis.common.order.dto.LicenseFile;

/**
 * 授权文件查询Mapper.
 * 
 * @author liuxiawang
 *
 */
public interface LicenseFileMapper {

    /**
     * 根据批次号查询该批次的所有订单资料.
     * @param licenseFile 
     * @return 查询结果
     */
    List<LicenseFile> selectByBatchNumber(LicenseFile licenseFile);
    
}
