/*
 *
 */
package com.lkkhpg.dsis.common.order.dto;

import java.math.BigDecimal;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * CSV授权文件查询表行.
 * 
 * @author xiawang.liu@hand-china.com
 *
 */
public class LicenseFile extends BaseDTO {

    private String orderNumber;

    private BigDecimal actrualPayAmt;

    private String creditCardNum;

    private String validYear;

    private String validMonth;

    private String batchNumber;
    
    private Long salesOrgId;

	public String getBatchNumber() {
		return batchNumber;
	}

	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public BigDecimal getActrualPayAmt() {
		return actrualPayAmt;
	}

	public void setActrualPayAmt(BigDecimal actrualPayAmt) {
		this.actrualPayAmt = actrualPayAmt;
	}

	public String getCreditCardNum() {
		return creditCardNum;
	}

	public void setCreditCardNum(String creditCardNum) {
		this.creditCardNum = creditCardNum;
	}

	public String getValidYear() {
		return validYear;
	}

	public void setValidYear(String validYear) {
		this.validYear = validYear;
	}

	public String getValidMonth() {
		return validMonth;
	}

	public void setValidMonth(String validMonth) {
		this.validMonth = validMonth;
	}

    public Long getSalesOrgId() {
        return salesOrgId;
    }

    public void setSalesOrgId(Long salesOrgId) {
        this.salesOrgId = salesOrgId;
    }
	
	

}