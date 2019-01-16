/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.delivery.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lkkhpg.dsis.platform.constant.BaseConstants;
import com.lkkhpg.dsis.platform.dto.BaseDTO;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 挑库单行dto.
 * 
 * @author Zhaoqi
 *
 */
public class DeliveryPickLine extends BaseDTO {
    private Long pickReleaseLineId;

    private Long pickReleaseId;

    private Long orderLineId;

    private Long invOrgId;
    // 挑库数量
    private BigDecimal pickQuantity;

    private Long lineId;

    /**
     * 父亲行.
     */
    private Long parentLineId;

    private String itemType;

    private Long itemId;

    private Long headerId;

    private Long salesOrgId;

    // private Long defaultInvOrgId;
    private BigDecimal returnQty;

    private String itemNumber;

    private String itemName;

    private String uomCode;
    /**
     * 商品单位名称.
     */
    private String uomName;

    private BigDecimal quantity;

    private String name;

    private String orderNumber;

    private String deliveryType;

    private String invOrgName;

    private BigDecimal inventory;

    private String deliveryStatus;

    /**
     * 所属商品包id.
     */
    private Long packageItemId;
    // 已挑库数量
    private BigDecimal pickedQty;
    // 未挑库数量
    private BigDecimal surplusQty;
    // 收货人电话
    private String phone;
    // 收货姓名
    private String userName;
    // 地址拼接
    private String address;
    // 城市code
    private String cityCode;
    // 省分code
    private String stateCode;
    // 国家code
    private String countryCode;

    private String defaultFlag;
    // 组织类型
    private String supplyType;
    /**
     * 计算库存的商品ID.
     */
    private Long countItemId;
    /**
     * 计算库存的商品Number.
     */
    private String countItemNumber;

    /**
     * 计算库存的商品Name.
     */
    private String countItemName;

    /**
     * add to 2018.3.1 by furong.tang
     * 订单状态
     */
    private String orderStatus;

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    /*add to 2016.09.06 by xiawang.liu*/
    @JsonFormat(pattern = BaseConstants.DATE_FORMAT)
    private Date payDateFrom;
    
    @JsonFormat(pattern = BaseConstants.DATE_FORMAT)
    private Date payDateTo;
    
    @JsonFormat(pattern = BaseConstants.DATE_FORMAT)
    private Date payDate;
    
    private String orderType;
    
    private String memberNumber;
    
    private String memberName;
    
    private String invoiceNumber;
    
    private String transactionDocument;
    /*end*/
    
    public Long getPickReleaseLineId() {
        return pickReleaseLineId;
    }
    
	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}

	public Date getPayDateFrom() {
		return payDateFrom;
	}

	public void setPayDateFrom(Date payDateFrom) {
		this.payDateFrom = payDateFrom;
	}

	public Date getPayDateTo() {
		return payDateTo;
	}

	public void setPayDateTo(Date payDateTo) {
		this.payDateTo = payDateTo;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getMemberNumber() {
		return memberNumber;
	}

	public void setMemberNumber(String memberNumber) {
		this.memberNumber = memberNumber;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public String getTransactionDocument() {
		return transactionDocument;
	}

	public void setTransactionDocument(String transactionDocument) {
		this.transactionDocument = transactionDocument;
	}

	public void setPickReleaseLineId(Long pickReleaseLineId) {
        this.pickReleaseLineId = pickReleaseLineId;
    }

    public Long getPickReleaseId() {
        return pickReleaseId;
    }

    public void setPickReleaseId(Long pickReleaseId) {
        this.pickReleaseId = pickReleaseId;
    }

    public Long getOrderLineId() {
        return orderLineId;
    }

    public void setOrderLineId(Long orderLineId) {
        this.orderLineId = orderLineId;
    }

    public Long getInvOrgId() {
        return invOrgId;
    }

    public void setInvOrgId(Long invOrgId) {
        this.invOrgId = invOrgId;
    }

    public BigDecimal getPickQuantity() {
        return this.pickQuantity;
    }

    public void setPickQuantity(BigDecimal pickQuantity) {
        this.pickQuantity = pickQuantity;
    }

    public Long getLineId() {
        return lineId;
    }

    public void setLineId(Long lineId) {
        this.lineId = lineId;
    }

    public Long getParentLineId() {
        return parentLineId;
    }

    public void setParentLineId(Long parentLineId) {
        this.parentLineId = parentLineId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getHeaderId() {
        return headerId;
    }

    public void setHeaderId(Long headerId) {
        this.headerId = headerId;
    }

    public Long getSalesOrgId() {
        return salesOrgId;
    }

    public void setSalesOrgId(Long salesOrgId) {
        this.salesOrgId = salesOrgId;
    }

    public BigDecimal getReturnQty() {
        return returnQty;
    }

    public void setReturnQty(BigDecimal returnQty) {
        this.returnQty = returnQty;
    }

    public String getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getUomCode() {
        return uomCode;
    }

    public void setUomCode(String uomCode) {
        this.uomCode = uomCode;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public String getInvOrgName() {
        return invOrgName;
    }

    public void setInvOrgName(String invOrgName) {
        this.invOrgName = invOrgName;
    }

    public BigDecimal getInventory() {
        return inventory;
    }

    public void setInventory(BigDecimal inventory) {
        this.inventory = inventory;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public BigDecimal getPickedQty() {
        if (this.getQuantity() != null) {
            return this.getQuantity().subtract(this.surplusQty);
        } else {
            return BigDecimal.ZERO;
        }
    }

    public void setPickedQty(BigDecimal pickedQty) {
        this.pickedQty = pickedQty;
    }

    public BigDecimal getSurplusQty() {
        return surplusQty;
    }

    public void setSurplusQty(BigDecimal surplusQty) {
        this.surplusQty = surplusQty;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setOrgName(String name) {
        this.name = name;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getDefaultFlag() {
        return defaultFlag;
    }

    public void setDefaultFlag(String defaultFlag) {
        this.defaultFlag = defaultFlag;
    }

    public String getSupplyType() {
        return supplyType;
    }

    public void setSupplyType(String supplyType) {
        this.supplyType = supplyType;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCountItemId() {
        return countItemId;
    }

    public void setCountItemId(Long countItemId) {
        this.countItemId = countItemId;
    }

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

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public Long getPackageItemId() {
        return packageItemId;
    }

    public void setPackageItemId(Long packageItemId) {
        this.packageItemId = packageItemId;
    }

    public String getUomName() {
        return uomName;
    }

    public void setUomName(String uomName) {
        this.uomName = uomName;
    }

}