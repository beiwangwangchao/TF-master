/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.inventory.dto;

import java.util.Date;

/**
 * InvTransactionQuery 库存事务查询参数dto.
 * 
 * @author shenqb
 */
public class InvTransactionQuery extends InvTransaction {
    private static final long serialVersionUID = 1L;
    
    private Long organizationId;
    
    private String itemNumber;

    private Date trxDateFrom;

    private Date trxDateTo;
    
    private Date expiryDateFrom;
    
    private Date expiryDateTo;
    
    

    public Date getExpiryDateFrom() {
        return expiryDateFrom;
    }

    public void setExpiryDateFrom(Date expiryDateFrom) {
        this.expiryDateFrom = expiryDateFrom;
    }

    public Date getExpiryDateTo() {
        return expiryDateTo;
    }

    public void setExpiryDateTo(Date expiryDateTo) {
        this.expiryDateTo = expiryDateTo;
    }

    public String getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }

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

	public Long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

}
