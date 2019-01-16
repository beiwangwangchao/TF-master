/*
 *
 */
package com.lkkhpg.dsis.common.product.dto;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 商品可用性dto.
 * 
 * @author wangchao
 *
 */
@Table(name = "inv_item_availability")
public class InvItemAvailability extends BaseDTO {

    @Id
    @Column(name = "availability_id")
    private Long availabilityId;

    @NotNull
    private Long itemId;

    @NotEmpty
    private String functionArea;

    @NotEmpty
    private String enabledFlag;

    public Long getAvailabilityId() {
        return availabilityId;
    }

    public void setAvailabilityId(Long availabilityId) {
        this.availabilityId = availabilityId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getFunctionArea() {
        return functionArea;
    }

    public void setFunctionArea(String functionArea) {
        this.functionArea = functionArea;
    }

    public String getEnabledFlag() {
        return enabledFlag;
    }

    public void setEnabledFlag(String enabledFlag) {
        this.enabledFlag = enabledFlag == null ? null : enabledFlag.trim();
    }

}