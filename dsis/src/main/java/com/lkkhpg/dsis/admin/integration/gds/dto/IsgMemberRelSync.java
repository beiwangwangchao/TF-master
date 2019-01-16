/*
 *
 */
package com.lkkhpg.dsis.admin.integration.gds.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 会员相关人信息DTO.
 * 
 * @author linyuheng
 */
public class IsgMemberRelSync extends BaseDTO {
    private Long interfaceDetailId;

    private Long interfaceId;

    private String dealerNo;

    private String appDealerNo;

    private String appPeriod;

    private String appType;

    private String spouseFullName;

    private String spouseCertificateNationCode;

    private String spouseCertificateType;

    private String spouseCertificateNo;

    private String spouseSex;

    private String spouseMobile;

    private String spouseTele;

    private String spouseRace;

    private String spouseRefDealerNo;

    private String comments;

    private String adviseNo;

    private String processStatus;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date processDate;

    private String processMessage;

    public Long getInterfaceDetailId() {
        return interfaceDetailId;
    }

    public void setInterfaceDetailId(Long interfaceDetailId) {
        this.interfaceDetailId = interfaceDetailId;
    }

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

    public String getAppDealerNo() {
        return appDealerNo;
    }

    public void setAppDealerNo(String appDealerNo) {
        this.appDealerNo = appDealerNo == null ? null : appDealerNo.trim();
    }

    public String getAppPeriod() {
        return appPeriod;
    }

    public void setAppPeriod(String appPeriod) {
        this.appPeriod = appPeriod == null ? null : appPeriod.trim();
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType == null ? null : appType.trim();
    }

    public String getSpouseFullName() {
        return spouseFullName;
    }

    public void setSpouseFullName(String spouseFullName) {
        this.spouseFullName = spouseFullName == null ? null : spouseFullName.trim();
    }

    public String getSpouseCertificateNationCode() {
        return spouseCertificateNationCode;
    }

    public void setSpouseCertificateNationCode(String spouseCertificateNationCode) {
        this.spouseCertificateNationCode = spouseCertificateNationCode == null ? null
                : spouseCertificateNationCode.trim();
    }

    public String getSpouseCertificateType() {
        return spouseCertificateType;
    }

    public void setSpouseCertificateType(String spouseCertificateType) {
        this.spouseCertificateType = spouseCertificateType == null ? null : spouseCertificateType.trim();
    }

    public String getSpouseCertificateNo() {
        return spouseCertificateNo;
    }

    public void setSpouseCertificateNo(String spouseCertificateNo) {
        this.spouseCertificateNo = spouseCertificateNo == null ? null : spouseCertificateNo.trim();
    }

    public String getSpouseSex() {
        return spouseSex;
    }

    public void setSpouseSex(String spouseSex) {
        this.spouseSex = spouseSex == null ? null : spouseSex.trim();
    }

    public String getSpouseMobile() {
        return spouseMobile;
    }

    public void setSpouseMobile(String spouseMobile) {
        this.spouseMobile = spouseMobile == null ? null : spouseMobile.trim();
    }

    public String getSpouseTele() {
        return spouseTele;
    }

    public void setSpouseTele(String spouseTele) {
        this.spouseTele = spouseTele == null ? null : spouseTele.trim();
    }

    public String getSpouseRace() {
        return spouseRace;
    }

    public void setSpouseRace(String spouseRace) {
        this.spouseRace = spouseRace == null ? null : spouseRace.trim();
    }

    public String getSpouseRefDealerNo() {
        return spouseRefDealerNo;
    }

    public void setSpouseRefDealerNo(String spouseRefDealerNo) {
        this.spouseRefDealerNo = spouseRefDealerNo == null ? null : spouseRefDealerNo.trim();
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
}