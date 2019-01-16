/*
 *
 */
package com.lkkhpg.dsis.admin.integration.gds.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 小批量下载海外推荐人dto.
 * @author gulin
 *
 */
public class IsgOverseasSponsor extends BaseDTO {
    private Long interfaceId;

    private String dealerNo;

    private String dealerName;

    private String dealerPostCode;

    private String sponsorNo;

    private String saleBranchNo;

    private String saleOrgCode;

    private String taxCustCode;

    private String adviseNo;

    private String processStatus;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date processDate;

    private String processMessage;

    public Long getInterfaceId() {
        return interfaceId;
    }

    public void setInterfaceId(Long interfaceId) {
        this.interfaceId = interfaceId;
    }

    public String getDealerNo() {
        return dealerNo;
    }

    public void setDealerNo(String dealerNo) {
        this.dealerNo = dealerNo == null ? null : dealerNo.trim();
    }

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName == null ? null : dealerName.trim();
    }

    public String getDealerPostCode() {
        return dealerPostCode;
    }

    public void setDealerPostCode(String dealerPostCode) {
        this.dealerPostCode = dealerPostCode == null ? null : dealerPostCode.trim();
    }

    public String getSponsorNo() {
        return sponsorNo;
    }

    public void setSponsorNo(String sponsorNo) {
        this.sponsorNo = sponsorNo == null ? null : sponsorNo.trim();
    }

    public String getSaleBranchNo() {
        return saleBranchNo;
    }

    public void setSaleBranchNo(String saleBranchNo) {
        this.saleBranchNo = saleBranchNo == null ? null : saleBranchNo.trim();
    }

    public String getSaleOrgCode() {
        return saleOrgCode;
    }

    public void setSaleOrgCode(String saleOrgCode) {
        this.saleOrgCode = saleOrgCode == null ? null : saleOrgCode.trim();
    }

    public String getTaxCustCode() {
        return taxCustCode;
    }

    public void setTaxCustCode(String taxCustCode) {
        this.taxCustCode = taxCustCode == null ? null : taxCustCode.trim();
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
}