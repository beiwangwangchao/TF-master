/*
 *
 */
package com.lkkhpg.dsis.common.product.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 商品单位转换率DTO.
 * 
 * @author mclin
 */
@Table(name = "INV_UNIT_CONVERT")
public class InvUnitConvert extends BaseDTO {
    
    @Id
    @Column(name = "convert_id")
    private Long convertId;

    private Long itemId;

    @NotEmpty
    private String fromUom;

    @NotEmpty
    private String toUom;

    @NotNull
    private Long convertRate;

    private String name;

    private String fromUomName;

    private String toUomName;

    private String enabledFlag;

    private Date startActiveDate;

    private Date endActiveDate;

    public Long getConvertId() {
        return convertId;
    }

    public void setConvertId(Long convertId) {
        this.convertId = convertId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getFromUom() {
        return fromUom;
    }

    public void setFromUom(String fromUom) {
        this.fromUom = fromUom == null ? null : fromUom.trim();
    }

    public String getToUom() {
        return toUom;
    }

    public void setToUom(String toUom) {
        this.toUom = toUom == null ? null : toUom.trim();
    }

    public Long getConvertRate() {
        return convertRate;
    }

    public void setConvertRate(Long convertRate) {
        this.convertRate = convertRate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getFromUomName() {
        return fromUomName;
    }

    public void setFromUomName(String fromUomName) {
        this.fromUomName = fromUomName;
    }

    public String getToUomName() {
        return toUomName;
    }

    public void setToUomName(String toUomName) {
        this.toUomName = toUomName;
    }

    public String getEnabledFlag() {
        return enabledFlag;
    }

    public void setEnabledFlag(String enabledFlag) {
        this.enabledFlag = enabledFlag;
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