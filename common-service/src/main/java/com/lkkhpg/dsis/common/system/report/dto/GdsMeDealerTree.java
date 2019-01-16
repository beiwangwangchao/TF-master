/*
 *
 */
package com.lkkhpg.dsis.common.system.report.dto;

import java.util.Date;

/**
 * gds实体类.
 * 
 * @author HuangJiaJing
 *
 */
public class GdsMeDealerTree {
    private String period;

    private String dealerNo;

    private String sponsorNo;

    private String dealerType;

    private String dealerOrgCode;

    private Long levelNo;

    private String comments;

    private Date lastUpdatedTime;

    private String lastUpdatedBy;

    private Long dealerPostCode;

    private String dealerAppPeriod;

    private String dealername;

    private String typeeffectivedate;

    private Long isnew;

    private Long ps;

    private Long gpv;

    private Long lastgv;

    private Long isleaf;

    private Long levelq;

    private String memberNo;

    private String bonusPeriod;

    private String concat;

    private String salesOrgCode;
    
    private String marketCode;
    
    public String getSalesOrgCode() {
        return salesOrgCode;
    }

    public void setSalesOrgCode(String salesOrgCode) {
        this.salesOrgCode = salesOrgCode;
    }

    public String getConcat() {
        String prep = " / ";
        String str = dealerNo + prep + dealerPostCode + prep + dealername + prep + ps + prep + lastgv + prep
                + typeeffectivedate;
        return str;
    }

    public void setConcat(String concat) {
        this.concat = concat;
    }

    public String getMemberNo() {
        return memberNo;
    }

    public void setMemberNo(String memberNo) {
        this.memberNo = memberNo;
    }

    public String getBonusPeriod() {
        return bonusPeriod;
    }

    public void setBonusPeriod(String bonusPeriod) {
        this.bonusPeriod = bonusPeriod;
    }

    public Long getIsnew() {
        return isnew;
    }

    public void setIsnew(Long isnew) {
        this.isnew = isnew;
    }

    public Long getIsleaf() {
        return isleaf;
    }

    public void setIsleaf(Long isleaf) {
        this.isleaf = isleaf;
    }

    public Long getLevelq() {
        return levelq;
    }

    public void setLevelq(Long levelq) {
        this.levelq = levelq;
    }

    public String getDealername() {
        return dealername;
    }

    public void setDealername(String dealername) {
        this.dealername = dealername;
    }

    public String getTypeeffectivedate() {
        return typeeffectivedate;
    }

    public void setTypeeffectivedate(String typeeffectivedate) {
        this.typeeffectivedate = typeeffectivedate;
    }

    public Long getPs() {
        return ps;
    }

    public void setPs(Long ps) {
        this.ps = ps;
    }

    public Long getGpv() {
        return gpv;
    }

    public void setGpv(Long gpv) {
        this.gpv = gpv;
    }

    public Long getLastgv() {
        return lastgv;
    }

    public void setLastgv(Long lastgv) {
        this.lastgv = lastgv;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period == null ? null : period.trim();
    }

    public String getDealerNo() {
        return dealerNo;
    }

    public void setDealerNo(String dealerNo) {
        this.dealerNo = dealerNo == null ? null : dealerNo.trim();
    }

    public String getSponsorNo() {
        return sponsorNo;
    }

    public void setSponsorNo(String sponsorNo) {
        this.sponsorNo = sponsorNo == null ? null : sponsorNo.trim();
    }

    public String getDealerType() {
        return dealerType;
    }

    public void setDealerType(String dealerType) {
        this.dealerType = dealerType == null ? null : dealerType.trim();
    }

    public String getDealerOrgCode() {
        return dealerOrgCode;
    }

    public void setDealerOrgCode(String dealerOrgCode) {
        this.dealerOrgCode = dealerOrgCode == null ? null : dealerOrgCode.trim();
    }

    public Long getLevelNo() {
        return levelNo;
    }

    public void setLevelNo(Long levelNo) {
        this.levelNo = levelNo;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments == null ? null : comments.trim();
    }

    public Date getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    public void setLastUpdatedTime(Date lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy == null ? null : lastUpdatedBy.trim();
    }

    public Long getDealerPostCode() {
        return dealerPostCode;
    }

    public void setDealerPostCode(Long dealerPostCode) {
        this.dealerPostCode = dealerPostCode;
    }

    public String getDealerAppPeriod() {
        return dealerAppPeriod;
    }

    public void setDealerAppPeriod(String dealerAppPeriod) {
        this.dealerAppPeriod = dealerAppPeriod == null ? null : dealerAppPeriod.trim();
    }

    public String getMarketCode() {
        return marketCode;
    }

    public void setMarketCode(String marketCode) {
        this.marketCode = marketCode;
    }
}