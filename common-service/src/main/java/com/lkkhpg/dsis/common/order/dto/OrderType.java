/*
 *
 */
package com.lkkhpg.dsis.common.order.dto;

import com.lkkhpg.dsis.platform.annotation.Children;
import com.lkkhpg.dsis.platform.dto.BaseDTO;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 订单类型的 DTO类，税引擎模块
 * 
 * @author zhiwei.zhang@hand-china.com 
 * [#PE20-4][ADD],2016-12-06 14:20:03.
 */
@Table(name = "OM_ORDER_TYPE")
public class OrderType extends BaseDTO {

	private static final long serialVersionUID = 1L;

	@Id
    @Column(name = "order_type_id")
	private Long orderTypeId;
	
	@NotNull
	private Long salesOrgId;
	
	// 销售组织名称
	private String name;
	
	@NotNull
	private String orderType;
	
	@NotNull
	private String priceType;
	
	@Children
	private List<SalesType> salesType ;

	public Long getOrderTypeId() {
		return orderTypeId;
	}

	public void setOrderTypeId(Long orderTypeId) {
		this.orderTypeId = orderTypeId;
	}

	public Long getSalesOrgId() {
		return salesOrgId;
	}

	public void setSalesOrgId(Long salesOrgId) {
		this.salesOrgId = salesOrgId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getPriceType() {
		return priceType;
	}

	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}

	public List<SalesType> getSalesType() {
		return salesType;
	}

	public void setSalesType(List<SalesType> salesType) {
		this.salesType = salesType;
	}
	
}
