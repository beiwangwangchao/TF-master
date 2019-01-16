package com.lkkhpg.dsis.common.inventory.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lkkhpg.dsis.platform.constant.BaseConstants;
import com.lkkhpg.dsis.platform.dto.BaseDTO;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by hand on 2018/6/4.
 */
public class Storage extends BaseDTO{
    private static final long serialVersionUID = 1L;
    private Long trxId;
    @JsonFormat(pattern = BaseConstants.DATE_TIME_FORMAT)
    private Date trxDate;
    private String trxNumber;
    private Long organizationId;
    private String name;
    private String  meaning;
    private String categoryName;
    private String itemName;
    private String itemNumber;
    private BigDecimal unitPrice;
    private String userName;
    private BigDecimal quantity;
    private Date trxDateFrom;
    private Date trxDateTo;


    public Date getTrxDateFrom() {
        return trxDateFrom;
    }

    public void setTrxDateFrom(Date trxDateFrom) {
        this.trxDateFrom = trxDateFrom;
    }

    public Date getTrxDateTo() {
        return trxDateTo;
    }

    public void setTrxDateTo(Date trxDateTo) {
        this.trxDateTo = trxDateTo;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public Date getTrxDate() {
        return trxDate;
    }

    public void setTrxDate(Date trxDate) {
        this.trxDate = trxDate;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getTrxId() {
        return trxId;
    }

    public void setTrxId(Long trxId) {
        this.trxId = trxId;
    }

    public String getTrxNumber() {
        return trxNumber;
    }

    public void setTrxNumber(String trxNumber) {
        this.trxNumber = trxNumber;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
