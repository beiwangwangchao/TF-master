/*
 *
 */
package com.lkkhpg.dsis.common.report.dto;

import java.math.BigDecimal;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * gds报表-bonus_detail_inquiry dto.
 * 
 * @author guanghui.liu
 *
 */
public class GdsSalaryMaster extends BaseDTO {

    private String period;
    private String dealerNo;
    private String saleOrgCode;
    private String sponsorNo;
    private String bbRefno;
    private String isLeaf;

    private BigDecimal pv;
    private BigDecimal gv;
    private BigDecimal titlerate;
    private String parsedTitlerate;
    private BigDecimal gdcamt;

    private BigDecimal lbcnt;
    private BigDecimal lbgv;
    private BigDecimal lbamt;

    private BigDecimal pbrate;
    private String parsedPbrate;
    private BigDecimal pbamt;

    private BigDecimal bbcnt;
    private BigDecimal bbgv;
    private BigDecimal bblevelno;
    private BigDecimal bbamt;

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getDealerNo() {
        return dealerNo;
    }

    public void setDealerNo(String dealerNo) {
        this.dealerNo = dealerNo;
    }

    public String getSaleOrgCode() {
        return saleOrgCode;
    }

    public void setSaleOrgCode(String saleOrgCode) {
        this.saleOrgCode = saleOrgCode;
    }

    public String getSponsorNo() {
        return sponsorNo;
    }

    public void setSponsorNo(String sponsorNo) {
        this.sponsorNo = sponsorNo;
    }

    public String getBbRefno() {
        return bbRefno;
    }

    public void setBbRefno(String bbRefno) {
        this.bbRefno = bbRefno;
    }

    public String getIsLeaf() {
        return isLeaf;
    }

    public void setIsLeaf(String isLeaf) {
        this.isLeaf = isLeaf;
    }

    public BigDecimal getPv() {
        return pv;
    }

    public void setPv(BigDecimal pv) {
        this.pv = pv;
    }

    public BigDecimal getTitlerate() {
        return titlerate;
    }

    public void setTitlerate(BigDecimal titlerate) {
        this.titlerate = titlerate;
    }

    public String getParsedTitlerate() {
        return parsedTitlerate;
    }

    public void setParsedTitlerate(String parsedTitlerate) {
        this.parsedTitlerate = parsedTitlerate;
    }

    public BigDecimal getGdcamt() {
        return gdcamt;
    }

    public void setGdcamt(BigDecimal gdcamt) {
        this.gdcamt = gdcamt;
    }

    public BigDecimal getLbcnt() {
        return lbcnt;
    }

    public void setLbcnt(BigDecimal lbcnt) {
        this.lbcnt = lbcnt;
    }

    public BigDecimal getLbgv() {
        return lbgv;
    }

    public void setLbgv(BigDecimal lbgv) {
        this.lbgv = lbgv;
    }

    public BigDecimal getLbamt() {
        return lbamt;
    }

    public void setLbamt(BigDecimal lbamt) {
        this.lbamt = lbamt;
    }

    public BigDecimal getGv() {
        return gv;
    }

    public void setGv(BigDecimal gv) {
        this.gv = gv;
    }

    public BigDecimal getPbrate() {
        return pbrate;
    }

    public void setPbrate(BigDecimal pbrate) {
        this.pbrate = pbrate;
    }

    public String getParsedPbrate() {
        return parsedPbrate;
    }

    public void setParsedPbrate(String parsedPbrate) {
        this.parsedPbrate = parsedPbrate;
    }

    public BigDecimal getPbamt() {
        return pbamt;
    }

    public void setPbamt(BigDecimal pbamt) {
        this.pbamt = pbamt;
    }

    public BigDecimal getBbcnt() {
        return bbcnt;
    }

    public void setBbcnt(BigDecimal bbcnt) {
        this.bbcnt = bbcnt;
    }

    public BigDecimal getBbgv() {
        return bbgv;
    }

    public void setBbgv(BigDecimal bbgv) {
        this.bbgv = bbgv;
    }

    public BigDecimal getBblevelno() {
        return bblevelno;
    }

    public void setBblevelno(BigDecimal bblevelno) {
        this.bblevelno = bblevelno;
    }

    public BigDecimal getBbamt() {
        return bbamt;
    }

    public void setBbamt(BigDecimal bbamt) {
        this.bbamt = bbamt;
    }

}