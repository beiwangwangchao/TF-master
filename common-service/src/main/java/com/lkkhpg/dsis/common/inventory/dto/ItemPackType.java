/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.inventory.dto;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 
 * @author chuangsheng.zhang. 商品包装行类型
 */
public class ItemPackType extends BaseDTO {
    private static final long serialVersionUID = 1L;

    // 商品包装类型
    private String packingType;

    // 商品包装名称
    private String packingName;

    // 与主商品类型的转化率
    private Long convertRate;

    // 商品包装主单位
    private String sourcePackingType;

    public String getPackingType() {
        return packingType;
    }

    public void setPackingType(String packingType) {
        this.packingType = packingType;
    }

    public String getPackingName() {
        return packingName;
    }

    public void setPackingName(String packingName) {
        this.packingName = packingName;
    }

    public Long getConvertRate() {
        return convertRate;
    }

    public void setConvertRate(Long convertRate) {
        this.convertRate = convertRate;
    }

    public String getSourcePackingType() {
        return sourcePackingType;
    }

    public void setSourcePackingType(String sourcePackingType) {
        this.sourcePackingType = sourcePackingType;
    }

}
