/*
 *
 */
package com.lkkhpg.dsis.common.order.dto;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 销售类型的 DTO类，税引擎模块
 * 
 * @author zhiwei.zhang@hand-china.com 
 * [#PE20-4][ADD],2016-12-06 14:20:03.
 */
@Table(name = "OM_SALES_TYPE")
public class SalesType extends BaseDTO {

	private static final long serialVersionUID = 1L;
	
	@Id
    @Column(name = "sales_type_id")
	private Long salesTypeId;
	
	private Long orderTypeId;
	
	private String salesType;
	
	private String itemSalesType;
	
	private String itemSalesTypeName;
	
	private String freeFlag;
	
	private String calPvFlag;
	
	private String taxPrice;
	
	private String defaultFlag;

	public Long getSalesTypeId() {
		return salesTypeId;
	}

	public void setSalesTypeId(Long salesTypeId) {
		this.salesTypeId = salesTypeId;
	}

	public Long getOrderTypeId() {
		return orderTypeId;
	}

	public void setOrderTypeId(Long orderTypeId) {
		this.orderTypeId = orderTypeId;
	}

	public String getSalesType() {
		return salesType;
	}

	public void setSalesType(String salesType) {
		this.salesType = salesType;
	}

	public String getFreeFlag() {
		return freeFlag;
	}

	public void setFreeFlag(String freeFlag) {
		this.freeFlag = freeFlag;
	}

	public String getCalPvFlag() {
		return calPvFlag;
	}

	public void setCalPvFlag(String calPvFlag) {
		this.calPvFlag = calPvFlag;
	}

	public String getTaxPrice() {
		return taxPrice;
	}

	public void setTaxPrice(String taxPrice) {
		this.taxPrice = taxPrice;
	}

    public String getItemSalesType() {
        return itemSalesType;
    }

    public void setItemSalesType(String itemSalesType) {
        this.itemSalesType = itemSalesType;
    }

    public String getItemSalesTypeName() {
        return itemSalesTypeName;
    }

    public void setItemSalesTypeName(String itemSalesTypeName) {
        this.itemSalesTypeName = itemSalesTypeName;
    }
    
    	public String getDefaultFlag() {
		return defaultFlag;
	}

	public void setDefaultFlag(String defaultFlag) {
		this.defaultFlag = defaultFlag;
	}
    

}
