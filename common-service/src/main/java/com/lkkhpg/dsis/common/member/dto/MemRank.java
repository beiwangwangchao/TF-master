/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.member.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 会员等级Dto.
 * 
 * @author frank.li
 */
public class MemRank extends BaseDTO {

    private static final long serialVersionUID = 1L;

    private Long rankId;

    private Long memberId;

    private Long rank;

    private Date monthToTerminate;

    private Double bonusLocal;

    private Double bonusInternational;

    private BigDecimal pv;

    private Double pgv;

    private Double localOv;

    private Double ov;

    private Double tp;

    private Long recruitCount;

    private String month;

    private String chineseName;

    private String englishName;

    private Date jointDate;

    private Long downLine;

    private String memberCode;

    private String rankDesc;

    private Long interMarketId;

    private Long localMarketId;

    private Long localBonusId;

    private Long interBonusId;

    public Long getRankId() {
        return rankId;
    }

    public void setRankId(Long rankId) {
        this.rankId = rankId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getRank() {
        return rank;
    }

    public void setRank(Long rank) {
        this.rank = rank;
    }

    public Date getMonthToTerminate() {
        return monthToTerminate;
    }

    public void setMonthToTerminate(Date monthToTerminate) {
        this.monthToTerminate = monthToTerminate;
    }

    public Double getBonusLocal() {
        return bonusLocal;
    }

    public void setBonusLocal(Double bonusLocal) {
        this.bonusLocal = bonusLocal;
    }

    public Double getBonusInternational() {
        return bonusInternational;
    }

    public void setBonusInternational(Double bonusInternational) {
        this.bonusInternational = bonusInternational;
    }

    public BigDecimal getPv() {
        return pv;
    }

    public void setPv(BigDecimal pv) {
        this.pv = pv;
    }

    public Double getPgv() {
        return pgv;
    }

    public void setPgv(Double pgv) {
        this.pgv = pgv;
    }

    public Double getLocalOv() {
        return localOv;
    }

    public void setLocalOv(Double localOv) {
        this.localOv = localOv;
    }

    public Double getOv() {
        return ov;
    }

    public void setOv(Double ov) {
        this.ov = ov;
    }

    public Double getTp() {
        return tp;
    }

    public void setTp(Double tp) {
        this.tp = tp;
    }

    public Long getRecruitCount() {
        return recruitCount;
    }

    public void setRecruitCount(Long recruitCount) {
        this.recruitCount = recruitCount;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
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

    public Date getJointDate() {
        return jointDate;
    }

    public void setJointDate(Date jointDate) {
        this.jointDate = jointDate;
    }

    public Long getDownLine() {
        return downLine;
    }

    public void setDownLine(Long downLine) {
        this.downLine = downLine;
    }

    public String getMemberCode() {
        return memberCode;
    }

    public void setMemberCode(String memberCode) {
        this.memberCode = memberCode;
    }

    public String getRankDesc() {
        return rankDesc;
    }

    public void setRankDesc(String rankDesc) {
        this.rankDesc = rankDesc;
    }

    public Long getInterMarketId() {
        return interMarketId;
    }

    public void setInterMarketId(Long interMarketId) {
        this.interMarketId = interMarketId;
    }

    public Long getLocalMarketId() {
        return localMarketId;
    }

    public void setLocalMarketId(Long localMarketId) {
        this.localMarketId = localMarketId;
    }

    public Long getLocalBonusId() {
        return localBonusId;
    }

    public void setLocalBonusId(Long localBonusId) {
        this.localBonusId = localBonusId;
    }

    public Long getInterBonusId() {
        return interBonusId;
    }

    public void setInterBonusId(Long interBonusId) {
        this.interBonusId = interBonusId;
    }

}