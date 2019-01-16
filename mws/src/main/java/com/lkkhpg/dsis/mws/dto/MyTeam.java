/*
 *
 */
package com.lkkhpg.dsis.mws.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * mws我的团队 dto.
 * @author Zhaoqi
 *
 */
public class MyTeam {

    private String period;
    private String dealerNo;
    private String sponsorNo;
    private String saleOrgCode;
    private BigDecimal leverNo;
    private BigDecimal pv;
    private BigDecimal gv;
    private BigDecimal refCardCnt;
    private BigDecimal totalRefCardCnt;
    private BigDecimal accTt;
    private BigDecimal ttLocalLbcnt;
    private BigDecimal ttLocalLbgv;
    private String calcSponsorNo;
    private BigDecimal calcLevelNo;
    private String comments;
    private Date lastUpdatedTime;
    private String lastUpdatedBy;
    
    private Long memberId;
    private String englishLastName;
    private String englishFirstName;
    private String chineseLastName;
    private String chineseFirstName;
    private String memberCode;
    private Date jointDate;
    private Date terminationDate;
    private String chineseName;
    private String englishName;
    private BigDecimal dealerPostCode;
    private BigDecimal localGv;
    private BigDecimal localBonus;
    private BigDecimal interBonus;
    private Date typeEffectiveDate;
    private BigDecimal memberCntNew;
    private BigDecimal ps;
    private BigDecimal gpv;
    private BigDecimal omkOv;
    private BigDecimal omkOvLocal;
    private String refPeriod;
    private BigDecimal omkGv;
    private BigDecimal omkPgv;
    private BigDecimal omkRecruitCnt;
    private BigDecimal omkQualifiedLeg;
    private BigDecimal omkTt;
    private String flag;
    // 终止期限
    private Integer deadLine;
    
    
    
    public Integer getDeadLine() {
        return deadLine;
    }
    public void setDeadLine(Integer deadLine) {
        this.deadLine = deadLine;
    }
    public BigDecimal getOmkGv() {
        return omkGv;
    }
    public void setOmkGv(BigDecimal omkGv) {
        this.omkGv = omkGv;
    }
    public BigDecimal getPs() {
        return ps;
    }
    public void setPs(BigDecimal ps) {
        this.ps = ps;
    }
    public BigDecimal getGpv() {
        return gpv;
    }
    public void setGpv(BigDecimal gpv) {
        this.gpv = gpv;
    }
    public BigDecimal getOmkOv() {
        return omkOv;
    }
    public void setOmkOv(BigDecimal omkOv) {
        this.omkOv = omkOv;
    }
    public BigDecimal getOmkOvLocal() {
        return omkOvLocal;
    }
    public void setOmkOvLocal(BigDecimal omkOvLocal) {
        this.omkOvLocal = omkOvLocal;
    }
    public String getRefPeriod() {
        return refPeriod;
    }
    public void setRefPeriod(String refPeriod) {
        this.refPeriod = refPeriod;
    }
    public BigDecimal getMemberCntNew() {
        return memberCntNew;
    }
    public void setMemberCntNew(BigDecimal memberCntNew) {
        this.memberCntNew = memberCntNew;
    }
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
    public String getSponsorNo() {
        return sponsorNo;
    }
    public void setSponsorNo(String sponsorNo) {
        this.sponsorNo = sponsorNo;
    }
    public String getSaleOrgCode() {
        return saleOrgCode;
    }
    public void setSaleOrgCode(String saleOrgCode) {
        this.saleOrgCode = saleOrgCode;
    }
    public BigDecimal getLeverNo() {
        return leverNo;
    }
    public void setLeverNo(BigDecimal leverNo) {
        this.leverNo = leverNo;
    }
    public BigDecimal getPv() {
        return pv;
    }
    public void setPv(BigDecimal pv) {
        this.pv = pv;
    }
    public BigDecimal getGv() {
        return gv;
    }
    public void setGv(BigDecimal gv) {
        this.gv = gv;
    }
    public BigDecimal getRefCardCnt() {
        return refCardCnt;
    }
    public void setRefCardCnt(BigDecimal refCardCnt) {
        this.refCardCnt = refCardCnt;
    }
    public BigDecimal getTotalRefCardCnt() {
        return totalRefCardCnt;
    }
    public void setTotalRefCardCnt(BigDecimal totalRefCardCnt) {
        this.totalRefCardCnt = totalRefCardCnt;
    }
    public BigDecimal getAccTt() {
        return accTt;
    }
    public void setAccTt(BigDecimal accTt) {
        this.accTt = accTt;
    }
    public BigDecimal getTtLocalLbcnt() {
        return ttLocalLbcnt;
    }
    public void setTtLocalLbcnt(BigDecimal ttLocalLbcnt) {
        this.ttLocalLbcnt = ttLocalLbcnt;
    }
    public BigDecimal getTtLocalLbgv() {
        return ttLocalLbgv;
    }
    public void setTtLocalLbgv(BigDecimal ttLocalLbgv) {
        this.ttLocalLbgv = ttLocalLbgv;
    }
    public String getCalcSponsorNo() {
        return calcSponsorNo;
    }
    public void setCalcSponsorNo(String calcSponsorNo) {
        this.calcSponsorNo = calcSponsorNo;
    }
    public BigDecimal getCalcLevelNo() {
        return calcLevelNo;
    }
    public void setCalcLevelNo(BigDecimal calcLevelNo) {
        this.calcLevelNo = calcLevelNo;
    }
    public String getComments() {
        return comments;
    }
    public void setComments(String comments) {
        this.comments = comments;
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
        this.lastUpdatedBy = lastUpdatedBy;
    }
    public Long getMemberId() {
        return memberId;
    }
    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }
    public String getEnglishLastName() {
        return englishLastName;
    }
    public void setEnglishLastName(String englishLastName) {
        this.englishLastName = englishLastName;
    }
    public String getEnglishFirstName() {
        return englishFirstName;
    }
    public void setEnglishFirstName(String englishFirstName) {
        this.englishFirstName = englishFirstName;
    }
    public String getChineseLastName() {
        return chineseLastName;
    }
    public void setChineseLastName(String chineseLastName) {
        this.chineseLastName = chineseLastName;
    }
    public String getChineseFirstName() {
        return chineseFirstName;
    }
    public void setChineseFirstName(String chineseFirstName) {
        this.chineseFirstName = chineseFirstName;
    }
    public String getMemberCode() {
        return memberCode;
    }
    public void setMemberCode(String memberCode) {
        this.memberCode = memberCode;
    }
    public Date getJointDate() {
        return jointDate;
    }
    public void setJointDate(Date jointDate) {
        this.jointDate = jointDate;
    }
    public Date getTerminationDate() {
        return terminationDate;
    }
    public void setTerminationDate(Date terminationDate) {
        this.terminationDate = terminationDate;
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
    public BigDecimal getDealerPostCode() {
        return dealerPostCode;
    }
    public void setDealerPostCode(BigDecimal dealerPostCode) {
        this.dealerPostCode = dealerPostCode;
    }
    public BigDecimal getLocalGv() {
        return localGv;
    }
    public void setLocalGv(BigDecimal localGv) {
        this.localGv = localGv;
    }
    public BigDecimal getLocalBonus() {
        return localBonus;
    }
    public void setLocalBonus(BigDecimal localBonus) {
        this.localBonus = localBonus;
    }
    public BigDecimal getInterBonus() {
        return interBonus;
    }
    public void setInterBonus(BigDecimal interBonus) {
        this.interBonus = interBonus;
    }
    public Date getTypeEffectiveDate() {
        return typeEffectiveDate;
    }
    public void setTypeEffectiveDate(Date typeEffectiveDate) {
        this.typeEffectiveDate = typeEffectiveDate;
    }
    public BigDecimal getOmkPgv() {
        return omkPgv;
    }
    public void setOmkPgv(BigDecimal omkPgv) {
        this.omkPgv = omkPgv;
    }
    public BigDecimal getOmkRecruitCnt() {
        return omkRecruitCnt;
    }
    public void setOmkRecruitCnt(BigDecimal omkRecruitCnt) {
        this.omkRecruitCnt = omkRecruitCnt;
    }
    public BigDecimal getOmkQualifiedLeg() {
        return omkQualifiedLeg;
    }
    public void setOmkQualifiedLeg(BigDecimal omkQualifiedLeg) {
        this.omkQualifiedLeg = omkQualifiedLeg;
    }
    public BigDecimal getOmkTt() {
        return omkTt;
    }
    public void setOmkTt(BigDecimal omkTt) {
        this.omkTt = omkTt;
    }
    public String getFlag() {
        return flag;
    }
    public void setFlag(String flag) {
        this.flag = flag;
    }
    
    
}
