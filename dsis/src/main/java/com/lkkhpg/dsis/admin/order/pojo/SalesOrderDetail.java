/*
 *
 */
package com.lkkhpg.dsis.admin.order.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

import com.lkkhpg.dsis.common.order.dto.SalesOrder;

/**
 * 订单详情pojo.
 * 
 * @author wuyichu
 */
public class SalesOrderDetail extends SalesOrder implements Serializable {

    private String chineseName;

    private String englishName;

    private BigDecimal currentPoints;

    private BigDecimal currentPV;

    private String memberCode;

    private String remark;

    private Long orderId;
    
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getChineseName() {
        return chineseName;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public BigDecimal getCurrentPoints() {
        return currentPoints;
    }

    public void setCurrentPoints(BigDecimal currentPoints) {
        this.currentPoints = currentPoints;
    }

    public BigDecimal getCurrentPV() {
        return currentPV;
    }

    public void setCurrentPV(BigDecimal currentPV) {
        this.currentPV = currentPV;
    }

    public String getMemberCode() {
        return memberCode;
    }

    public void setMemberCode(String memberCode) {
        this.memberCode = memberCode;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    
    
    
}
