/*
 *
 */
package com.lkkhpg.dsis.common.member.dto;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 海外推荐人DTO.
 * 
 * @author gulin
 */
public class MemOverseasSponsor extends BaseDTO {
    private Long sponsorId;

    private String dealerNo;

    private String dealerName;

    private String dealerPostCode;

    private String sponsorNo;

    private String saleBranchNo;

    private String saleOrgCode;

    private String taxCustCode;

    private String adviseNo;

    public Long getSponsorId() {
        return sponsorId;
    }

    public void setSponsorId(Long sponsorId) {
        this.sponsorId = sponsorId;
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
}