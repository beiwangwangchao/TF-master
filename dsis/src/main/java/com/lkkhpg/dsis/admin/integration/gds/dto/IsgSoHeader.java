/*
 *
 */
package com.lkkhpg.dsis.admin.integration.gds.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 小批量同步销售资料.
 * 
 * @author wuyichu
 */
public class IsgSoHeader extends BaseDTO {
    private Long interfaceId;

    private String soNo;

    private String soType;

    private String soOrgCode;

    private String soDealerNo;

    private String soPeriod;

    private String soEntryClass;

    private String soEntryTime;

    private String soEntryBy;

    private String orderDealerNo;

    private String orderDate;

    private Double orderAmt;

    private String orderDealerName;

    private String orderDealerTele;

    private String firstSoNo;

    private String refSoNo;

    private String localCurrencyCode;

    private Double localTotalAmt;

    private Double localTotalEcoupon;

    private Double localTotalPoint;

    private Double localTotalRebate;

    private String realTimeProcFlag;

    private String realTimeProcTime;

    private String comments;

    private String adviseNo;

    private String processStatus;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date processDate;

    private String processMessage;

    private Long headerId;

    public Long getInterfaceId() {
        return interfaceId;
    }

    public void setInterfaceId(Long interfaceId) {
        this.interfaceId = interfaceId;
    }

    public String getSoNo() {
        return soNo;
    }

    public void setSoNo(String soNo) {
        this.soNo = soNo == null ? null : soNo.trim();
    }

    public String getSoType() {
        return soType;
    }

    public void setSoType(String soType) {
        this.soType = soType == null ? null : soType.trim();
    }

    public String getSoOrgCode() {
        return soOrgCode;
    }

    public void setSoOrgCode(String soOrgCode) {
        this.soOrgCode = soOrgCode == null ? null : soOrgCode.trim();
    }

    public String getSoDealerNo() {
        return soDealerNo;
    }

    public void setSoDealerNo(String soDealerNo) {
        this.soDealerNo = soDealerNo == null ? null : soDealerNo.trim();
    }

    public String getSoPeriod() {
        return soPeriod;
    }

    public void setSoPeriod(String soPeriod) {
        this.soPeriod = soPeriod == null ? null : soPeriod.trim();
    }

    public String getSoEntryClass() {
        return soEntryClass;
    }

    public void setSoEntryClass(String soEntryClass) {
        this.soEntryClass = soEntryClass == null ? null : soEntryClass.trim();
    }

    public String getSoEntryTime() {
        return soEntryTime;
    }

    public void setSoEntryTime(String soEntryTime) {
        this.soEntryTime = soEntryTime;
    }

    public String getSoEntryBy() {
        return soEntryBy;
    }

    public void setSoEntryBy(String soEntryBy) {
        this.soEntryBy = soEntryBy == null ? null : soEntryBy.trim();
    }

    public String getOrderDealerNo() {
        return orderDealerNo;
    }

    public void setOrderDealerNo(String orderDealerNo) {
        this.orderDealerNo = orderDealerNo == null ? null : orderDealerNo.trim();
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public Double getOrderAmt() {
        return orderAmt;
    }

    public void setOrderAmt(Double orderAmt) {
        this.orderAmt = orderAmt;
    }

    public String getOrderDealerName() {
        return orderDealerName;
    }

    public void setOrderDealerName(String orderDealerName) {
        this.orderDealerName = orderDealerName == null ? null : orderDealerName.trim();
    }

    public String getOrderDealerTele() {
        return orderDealerTele;
    }

    public void setOrderDealerTele(String orderDealerTele) {
        this.orderDealerTele = orderDealerTele == null ? null : orderDealerTele.trim();
    }

    public String getFirstSoNo() {
        return firstSoNo;
    }

    public void setFirstSoNo(String firstSoNo) {
        this.firstSoNo = firstSoNo == null ? null : firstSoNo.trim();
    }

    public String getRefSoNo() {
        return refSoNo;
    }

    public void setRefSoNo(String refSoNo) {
        this.refSoNo = refSoNo == null ? null : refSoNo.trim();
    }

    public String getLocalCurrencyCode() {
        return localCurrencyCode;
    }

    public void setLocalCurrencyCode(String localCurrencyCode) {
        this.localCurrencyCode = localCurrencyCode == null ? null : localCurrencyCode.trim();
    }

    public Double getLocalTotalAmt() {
        return localTotalAmt;
    }

    public void setLocalTotalAmt(Double localTotalAmt) {
        this.localTotalAmt = localTotalAmt;
    }

    public Double getLocalTotalEcoupon() {
        return localTotalEcoupon;
    }

    public void setLocalTotalEcoupon(Double localTotalEcoupon) {
        this.localTotalEcoupon = localTotalEcoupon;
    }

    public Double getLocalTotalPoint() {
        return localTotalPoint;
    }

    public void setLocalTotalPoint(Double localTotalPoint) {
        this.localTotalPoint = localTotalPoint;
    }

    public Double getLocalTotalRebate() {
        return localTotalRebate;
    }

    public void setLocalTotalRebate(Double localTotalRebate) {
        this.localTotalRebate = localTotalRebate;
    }

    public String getRealTimeProcFlag() {
        return realTimeProcFlag;
    }

    public void setRealTimeProcFlag(String realTimeProcFlag) {
        this.realTimeProcFlag = realTimeProcFlag == null ? null : realTimeProcFlag.trim();
    }

    public String getRealTimeProcTime() {
        return realTimeProcTime;
    }

    public void setRealTimeProcTime(String realTimeProcTime) {
        this.realTimeProcTime = realTimeProcTime;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments == null ? null : comments.trim();
    }

    public String getAdviseNo() {
        return adviseNo;
    }

    public void setAdviseNo(String adviseNo) {
        this.adviseNo = adviseNo == null ? null : adviseNo.trim();
    }

    public String getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(String processStatus) {
        this.processStatus = processStatus == null ? null : processStatus.trim();
    }

    public Date getProcessDate() {
        return processDate;
    }

    public void setProcessDate(Date processDate) {
        this.processDate = processDate;
    }

    public String getProcessMessage() {
        return processMessage;
    }

    public void setProcessMessage(String processMessage) {
        this.processMessage = processMessage == null ? null : processMessage.trim();
    }

    public Long getHeaderId() {
        return headerId;
    }

    public void setHeaderId(Long headerId) {
        this.headerId = headerId;
    }
}