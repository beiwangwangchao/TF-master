/*
 *
 */
package com.lkkhpg.dsis.common.system.dto;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * @author shiliyan
 *
 */
public class SysMsMessagePublishResult extends BaseDTO {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Long resultId;
    private Long msMessageId;
    private Long sysMessageId;
    // MEM:会员
    // USER:用户
    private String assignType;
    // 会员id;用户id
    private String assignValue;

    private String publishStatus;

    public Long getResultId() {
        return resultId;
    }

    public void setResultId(Long resultId) {
        this.resultId = resultId;
    }

    public Long getMsMessageId() {
        return msMessageId;
    }

    public void setMsMessageId(Long msMessageId) {
        this.msMessageId = msMessageId;
    }

    public Long getSysMessageId() {
        return sysMessageId;
    }

    public void setSysMessageId(Long sysMessageId) {
        this.sysMessageId = sysMessageId;
    }

    public String getAssignType() {
        return assignType;
    }

    public void setAssignType(String assignType) {
        this.assignType = assignType;
    }

    public String getAssignValue() {
        return assignValue;
    }

    public void setAssignValue(String assignValue) {
        this.assignValue = assignValue;
    }

    public String getPublishStatus() {
        return publishStatus;
    }

    public void setPublishStatus(String publishStatus) {
        this.publishStatus = publishStatus;
    }
//
//    public String toSString() {
//        return "{" + "[" + "msMessageId" + ":" + msMessageId + "]" + "[" + "sysMessageId" + ":" + sysMessageId + "]"
//                + "[" + "assignType" + ":" + assignType + "]" + "[" + "assignValue" + ":" + assignValue + "]" + "["
//                + "publishStatus" + ":" + publishStatus + "]" + "}";
//    }


}
