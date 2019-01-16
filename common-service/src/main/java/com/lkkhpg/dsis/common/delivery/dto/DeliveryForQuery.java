/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.delivery.dto;

import java.util.Date;
import java.util.List;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * . 发运单查询DTO.
 * 
 * @author pengli
 *
 */
public class DeliveryForQuery extends BaseDTO {

    // 主键id
    private Integer deliveryId;

    // 库存组织
    private Integer invOrgId;

    // 销售订单编号
    private String orderNumber;

    // 会员编号
    private Integer memberId;

    // 发运单编号
    private String deliveryNumber;

    // 发运单类型
    private String deliveryType;

    // 发运单状态
    private String deliveryStatus;

    // 创建时间
    private Date creationDate;

    // 创建-开始时间(供查询条件使用)
    private Date creationBegin;

    // 创建-结束时间(供查询条件使用)
    private Date creationEnd;

    // 处理时间
    private Date deliveryDate;

    // 处理-结束时间(供查询条件使用)
    private Date deliveryDateBegin;

    // 处理-结束时间(供查询条件使用)
    private Date deliveryDateEnd;

    //会员编号
    private String memberCode;
    
    //会员名称
    private String memberName;
    
    //物流公司
    private String logisticsCompany;
    
	//物流跟踪号
    private String trackerNumber;
    
    private List<String> deliveryStatusList;
    
    public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

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

    public Integer getDeliveryId() {
        return deliveryId;
    }
    
    public void setDeliveryId(Integer deliveryId) {
        this.deliveryId = deliveryId;
    }

    public Integer getInvOrgId() {
        return invOrgId;
    }

    public void setInvOrgId(Integer invOrgId) {
        this.invOrgId = invOrgId;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getDeliveryNumber() {
        return deliveryNumber;
    }

    public void setDeliveryNumber(String deliveryNumber) {
        this.deliveryNumber = deliveryNumber;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getCreationBegin() {
        return creationBegin;
    }

    public void setCreationBegin(Date creationBegin) {
        this.creationBegin = creationBegin;
    }

    public Date getCreationEnd() {
        return creationEnd;
    }

    public void setCreationEnd(Date creationEnd) {
        this.creationEnd = creationEnd;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Date getDeliveryDateBegin() {
        return deliveryDateBegin;
    }

    public void setDeliveryDateBegin(Date deliveryDateBegin) {
        this.deliveryDateBegin = deliveryDateBegin;
    }

    public Date getDeliveryDateEnd() {
        return deliveryDateEnd;
    }

    public void setDeliveryDateEnd(Date deliveryDateEnd) {
        this.deliveryDateEnd = deliveryDateEnd;
    }

    public List<String> getDeliveryStatusList() {
        return deliveryStatusList;
    }

    public void setDeliveryStatusList(List<String> deliveryStatusList) {
        this.deliveryStatusList = deliveryStatusList;
    }

    public String getMemberCode() {
        return memberCode;
    }

    public void setMemberCode(String memberCode) {
        this.memberCode = memberCode;
    }

}
