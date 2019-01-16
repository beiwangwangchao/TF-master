/*
 *
 */
package com.lkkhpg.dsis.common.system.report.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * dto.
 * @author HuangJiaJing
 *
 */
public class OmkRtlSalaryBalance {
    private String dealerNo;

    private Long dealerPostCode;

    private String sponsorNo;

    private Long levelNo;

    private Long level;
    
    private BigDecimal ps;

    private BigDecimal pr;

    private BigDecimal gp;

    private BigDecimal gpv;

    private Long gpvUpperFlag;

    private BigDecimal lastGv;

    private String refPeriod;

    private String comments;

    private Date lastUpdatedTime;

    private String lastUpdatedBy;

    private String dealername;

    private String typeeffectivedate;

    private Long isnew;

    private Long lastgv;

    private Long isleaf;

    private Long levelq;

    private String memberNo;

    private String bonusPeriod;
    
    private Long type;

    private String marketCode;

    public String getMarketCode() {
        return marketCode;
    }

    public void setMarketCode(String marketCode) {
        this.marketCode = marketCode;
    }

    public Long getLevel() {
        return level;
    }

    public void setLevel(Long level) {
        this.level = level;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
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

    public Long getIsnew() {
        return isnew;
    }

    public void setIsnew(Long isnew) {
        this.isnew = isnew;
    }

    public Long getLastgv() {
        return lastgv;
    }

    public void setLastgv(Long lastgv) {
        this.lastgv = lastgv;
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

    public String getDealerNo() {
        return dealerNo;
    }

    public void setDealerNo(String dealerNo) {
        this.dealerNo = dealerNo == null ? null : dealerNo.trim();
    }

    public Long getDealerPostCode() {
        return dealerPostCode;
    }

    public void setDealerPostCode(Long dealerPostCode) {
        this.dealerPostCode = dealerPostCode;
    }

    public String getSponsorNo() {
        return sponsorNo;
    }

    public void setSponsorNo(String sponsorNo) {
        this.sponsorNo = sponsorNo == null ? null : sponsorNo.trim();
    }

    public Long getLevelNo() {
        return levelNo;
    }

    public void setLevelNo(Long levelNo) {
        this.levelNo = levelNo;
    }

    public BigDecimal getPs() {
        return ps;
    }

    public void setPs(BigDecimal ps) {
        this.ps = ps;
    }

    public BigDecimal getPr() {
        return pr;
    }

    public void setPr(BigDecimal pr) {
        this.pr = pr;
    }

    public BigDecimal getGp() {
        return gp;
    }

    public void setGp(BigDecimal gp) {
        this.gp = gp;
    }

    public BigDecimal getGpv() {
        return gpv;
    }

    public void setGpv(BigDecimal gpv) {
        this.gpv = gpv;
    }

    public Long getGpvUpperFlag() {
        return gpvUpperFlag;
    }

    public void setGpvUpperFlag(Long gpvUpperFlag) {
        this.gpvUpperFlag = gpvUpperFlag;
    }

    public BigDecimal getLastGv() {
        return lastGv;
    }

    public void setLastGv(BigDecimal lastGv) {
        this.lastGv = lastGv;
    }

    public String getRefPeriod() {
        return refPeriod;
    }

    public void setRefPeriod(String refPeriod) {
        this.refPeriod = refPeriod == null ? null : refPeriod.trim();
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
}