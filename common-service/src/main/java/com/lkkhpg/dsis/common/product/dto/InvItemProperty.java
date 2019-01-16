/*
 *
 */
package com.lkkhpg.dsis.common.product.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 商品库存组织参数.
 * 
 * @author chuangsheng.zhang.
 */
@Table(name = "inv_item_property")
public class InvItemProperty extends BaseDTO {

    @Id
    @Column(name = "item_property_id")
    private Long itemPropertyId;

    private Long organizationId;

    private Long itemId;

    private String propertyType;

    private String propertyValue;

    private String enabledFlag;

    private Date startActiveDate;

    private Date endActiveDate;
    
    private String countItemName;
    
    private String countItemNumber;
    
    private boolean isLotControl;
    
    public String getCountItemNumber() {
        return countItemNumber;
    }

    public void setCountItemNumber(String countItemNumber) {
        this.countItemNumber = countItemNumber;
    }

    public String getCountItemName() {
        return countItemName;
    }

    public void setCountItemName(String countItemName) {
        this.countItemName = countItemName;
    }

    public Long getItemPropertyId() {
        return itemPropertyId;
    }

    public void setItemPropertyId(Long itemPropertyId) {
        this.itemPropertyId = itemPropertyId;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType == null ? null : propertyType.trim();
    }

    public String getPropertyValue() {
        return propertyValue;
    }

    public void setPropertyValue(String propertyValue) {
        this.propertyValue = propertyValue == null ? null : propertyValue.trim();
    }

    public String getEnabledFlag() {
        return enabledFlag;
    }

    public void setEnabledFlag(String enabledFlag) {
        this.enabledFlag = enabledFlag == null ? null : enabledFlag.trim();
    }

    public Date getStartActiveDate() {
        return startActiveDate;
    }

    public void setStartActiveDate(Date startActiveDate) {
        this.startActiveDate = startActiveDate;
    }

    public Date getEndActiveDate() {
        return endActiveDate;
    }

    public void setEndActiveDate(Date endActiveDate) {
        this.endActiveDate = endActiveDate;
    }

    public boolean isLotControl() {
        return isLotControl;
    }

    public void setLotControl(boolean isLotControl) {
        this.isLotControl = isLotControl;
    }
}