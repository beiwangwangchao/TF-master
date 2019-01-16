/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.delivery.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lkkhpg.dsis.platform.annotation.Children;
import com.lkkhpg.dsis.platform.constant.BaseConstants;
import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 发运单头DTO.
 * 
 * @author liang.rao
 *
 */
public class OrderDelivery extends BaseDTO {

    private Long deliveryId;

    private Long invOrgId;

    private Long orderHeaderId;

    private String deliveryNumber;

    private String deliveryType;

    private String deliveryStatus;

    @JsonFormat(pattern = BaseConstants.DATE_TIME_FORMAT)
    private Date deliveryDate;

    private String remark;

    private String contactName;

    private String phone;

    private String cityCode;

    private String stateCode;

    private String countryCode;

    private String address;
    
    private String address1;
    
    private String address2;
    
    private String address3;
    
    private String  areaCode;
    
    private String zipCode;

    private Long pickReleaseId;

    private String invOrgName;

    private String ouName;

    private String orderNumber;

    private String memberIdentify;

    private Long memberId;

    private String memberName;

    private String memberCode;
    
    private String receiptFlag;

    private Long headerId;

    public Long getHeaderId() {
        return headerId;
    }

    public void setHeaderId(Long headerId) {
        this.headerId = headerId;
    }

    /**
     * 市场编码.
     */
    private String marketCode;
    
    private String logisticsCompany;
    
    private String trackerNumber;
    
    private String internalRemark;
    
    private String orderRemark;
    
    private String orderInternalRemark;

    public String getLogisticsCompany() {
		return logisticsCompany;
	}

	public void setLogisticsCompany(String logisticsCompany) {
		this.logisticsCompany = logisticsCompany;
	}

	public String getTrackerNumber() {
		return trackerNumber;
	}

	public void setTrackerNumber(String trackerNumber) {
		this.trackerNumber = trackerNumber;
	}

	@JsonFormat(pattern = BaseConstants.DATE_TIME_FORMAT)
    private Date creationDate;

    @Children
    private List<OrderDeliveryLine> deliveryLines;

    public Long getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(Long deliveryId) {
        this.deliveryId = deliveryId;
    }

    public Long getInvOrgId() {
        return invOrgId;
    }

    public void setInvOrgId(Long invOrgId) {
        this.invOrgId = invOrgId;
    }

    public Long getOrderHeaderId() {
        return orderHeaderId;
    }

    public void setOrderHeaderId(Long orderHeaderId) {
        this.orderHeaderId = orderHeaderId;
    }

    public String getDeliveryNumber() {
        return deliveryNumber;
    }

    public void setDeliveryNumber(String deliveryNumber) {
        this.deliveryNumber = deliveryNumber == null ? null : deliveryNumber.trim();
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType == null ? null : deliveryType.trim();
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus == null ? null : deliveryStatus.trim();
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Long getPickReleaseId() {
        return pickReleaseId;
    }

    public void setPickReleaseId(Long pickReleaseId) {
        this.pickReleaseId = pickReleaseId;
    }

    public String getInvOrgName() {
        return invOrgName;
    }

    public void setInvOrgName(String invOrgName) {
        this.invOrgName = invOrgName;
    }

    public String getOuName() {
        return ouName;
    }

    public void setOuName(String ouName) {
        this.ouName = ouName;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getMemberIdentify() {
        return memberIdentify;
    }

    public void setMemberIdentify(String memberIdentify) {
        this.memberIdentify = memberIdentify;
    }

    public List<OrderDeliveryLine> getDeliveryLines() {
        return deliveryLines;
    }

    public void setDeliveryLines(List<OrderDeliveryLine> deliveryLines) {
        this.deliveryLines = deliveryLines;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberCode() {
        return memberCode;
    }

    public void setMemberCode(String memberCode) {
        this.memberCode = memberCode;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAddress3() {
        return address3;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    public String getMarketCode() {
        return marketCode;
    }

    public void setMarketCode(String marketCode) {
        this.marketCode = marketCode;
    }

    public String getReceiptFlag() {
        return receiptFlag;
    }

    public void setReceiptFlag(String receiptFlag) {
        this.receiptFlag = receiptFlag;
    }

    public String getInternalRemark() {
        return internalRemark;
    }

    public void setInternalRemark(String internalRemark) {
        this.internalRemark = internalRemark;
    }

    public String getOrderRemark() {
        return orderRemark;
    }

    public void setOrderRemark(String orderRemark) {
        this.orderRemark = orderRemark;
    }

    public String getOrderInternalRemark() {
        return orderInternalRemark;
    }

    public void setOrderInternalRemark(String orderInternalRemark) {
        this.orderInternalRemark = orderInternalRemark;
    }

}