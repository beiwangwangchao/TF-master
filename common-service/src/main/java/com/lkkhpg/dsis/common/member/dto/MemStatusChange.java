/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.member.dto;

import java.util.Date;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 会员状态改变Dto.
 * 
 * @author yuchuan.zeng@hand-china.com
 */
public class MemStatusChange extends BaseDTO {

    private static final long serialVersionUID = 1L;

    private Long changeId;

    private Long memberId;

    private String operationType;

    private Date applyDate;

    private String approvalStatus;

    private Date approvalDate;

    private String remark;

    private String adviseNo;

    private String appNo;

    private String synStatus;

    private String synMessage;

    private Date synDate;

    /*
     * 以下关联Member属性
     */
    private String englishName;

    private String chineseName;

    private String memberName;

    private String memberCode;
    
    /*通过Member的marketId寻找market表的marketName*/
    private String marketName;

    /*
     * 以下是针对此Dto的查询条件
     */
    private Date beginDate; // time from

    private Date endDate; // time to
    
    private Long marketId;
    
    private Date terminateDate;

    public Long getMarketId() {
        return marketId;
    }

    public void setMarketId(Long marketId) {
        this.marketId = marketId;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public String getMemberCode() {
        return memberCode;
    }

    public void setMemberCode(String memberCode) {
        this.memberCode = memberCode;
    }

    public String getAdviseNo() {
        return adviseNo;
    }

    public void setAdviseNo(String adviseNo) {
        this.adviseNo = adviseNo;
    }

    public String getAppNo() {
        return appNo;
    }

    public void setAppNo(String appNo) {
        this.appNo = appNo;
    }

    public String getSynStatus() {
        return synStatus;
    }

    public void setSynStatus(String synStatus) {
        this.synStatus = synStatus;
    }

    public String getSynMessage() {
        return synMessage;
    }

    public void setSynMessage(String synMessage) {
        this.synMessage = synMessage;
    }

    public Date getSynDate() {
        return synDate;
    }

    public void setSynDate(Date synDate) {
        this.synDate = synDate;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public Long getChangeId() {
        return changeId;
    }

    public void setChangeId(Long changeId) {
        this.changeId = changeId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public Date getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(Date approvalDate) {
        this.approvalDate = approvalDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getChineseName() {
        return chineseName;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }

    public MemStatusChange() {
        super();
        // TODO Auto-generated constructor stub
    }

    public void checkSortName() {
        if ("changeId".equals(this.getSortname())) {
            setSortname("CHANGE_ID");
        } else if ("memberId".equals(this.getSortname())) {
            setSortname("MEMBER_ID");
        } else if ("operationType".equals(this.getSortname())) {
            setSortname("OPERATION_TYPE");
        } else if ("applyDate".equals(this.getSortname())) {
            setSortname("APPLY_DATE");
        } else if ("approvalStatus".equals(this.getSortname())) {
            setSortname("APPROVAL_STATUS");
        } else if ("approvalDate".equals(this.getSortname())) {
            setSortname("APPROVAL_DATE");
        } else if ("remark".equals(this.getSortname())) {
            setSortname("REMARK");
        } else if ("memberName".equals(this.getSortname())) {
            setSortname("ENGLISH_NAME,CHINESE_NAME");
        } else {
            setSortname("CHANGE_ID");
        }
    }

    public Date getTerminateDate() {
        return terminateDate;
    }

    public void setTerminateDate(Date terminateDate) {
        this.terminateDate = terminateDate;
    }
    
}