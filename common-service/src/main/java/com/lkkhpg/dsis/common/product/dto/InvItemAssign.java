/*
 *
 */
package com.lkkhpg.dsis.common.product.dto;

import com.lkkhpg.dsis.platform.dto.BaseDTO;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 库存组织dto.
 * 
 * @author wangchao
 *
 */
@Table(name = "inv_item_assign")
public class InvItemAssign extends BaseDTO {

    @Id
    @Column(name = "assign_id")
    private Long assignId;

    private Long itemId;

    private String assignType;

    @NotNull
    private Long assignValue;

    @NotEmpty
    private String enabledFlag;

    private Date startActiveDate;

    private Date endActiveDate;

    private String assignName;
    
    private String assignCode;
    
    //库存属性值
    private BigDecimal quantityAlert;

    private String expiryAlert;
    
    private String savePropertyFlag;

    //默认值
    private String attribute1;

    @Override
    public String getAttribute1() {
        return attribute1;
    }

    @Override
    public void setAttribute1(String attribute1) {
        this.attribute1 = attribute1;
    }

    public String getSavePropertyFlag() {
        return savePropertyFlag;
    }

    public void setSavePropertyFlag(String savePropertyFlag) {
        this.savePropertyFlag = savePropertyFlag;
    }

    public BigDecimal getQuantityAlert() {
        return quantityAlert;
    }

    public void setQuantityAlert(BigDecimal quantityAlert) {
        this.quantityAlert = quantityAlert;
    }

    public String getExpiryAlert() {
        return expiryAlert;
    }

    public void setExpiryAlert(String expiryAlert) {
        this.expiryAlert = expiryAlert;
    }

    public String getAssignCode() {
        return assignCode;
    }

    public void setAssignCode(String assignCode) {
        this.assignCode = assignCode;
    }

    public String getAssignName() {
        return assignName;
    }

    public void setAssignName(String assignName) {
        this.assignName = assignName;
    }

    public Long getAssignId() {
        return assignId;
    }

    public void setAssignId(Long assignId) {
        this.assignId = assignId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getAssignType() {
        return assignType;
    }

    public void setAssignType(String assignType) {
        this.assignType = assignType == null ? null : assignType.trim();
    }

    public Long getAssignValue() {
        return assignValue;
    }

    public void setAssignValue(Long assignValue) {
        this.assignValue = assignValue;
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

}