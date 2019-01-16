/*
 *
 */
package com.lkkhpg.dsis.admin.integration.gds.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 会员个人信息DTO.
 * @author linyuheng
 *
 */
public class IsgMemberInfoSync extends BaseDTO {
    private Long interfaceDetailId;

    private Long interfaceId;

    private String dealerNo;

    private String privacyFlag;

    private String certificateNationCode;

    private String certificateType;

    private String certificateNo;

    private String certificateMemo;

    private String certificateAddrZipCode;

    private String certificateAddrProvince;

    private String certificateAddrCity;

    private String certificateAddrTail01;

    private String certificateAddrTail02;

    private String addrNationCode;

    private String addrZipCode;

    private String addrProvince;

    private String addrCity;

    private String addrTail01;

    private String addrTail02;

    private String addrEnabled;

    private String addrMemo;

    private String contactTele;

    private String contactFaxNo;

    private String contactMobile;

    private String contactEmail;

    private String race;

    private String education;

    private String occupation;

    private String birthday;

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

    public String getPrivacyFlag() {
        return privacyFlag;
    }

    public void setPrivacyFlag(String privacyFlag) {
        this.privacyFlag = privacyFlag == null ? null : privacyFlag.trim();
    }

    public String getCertificateNationCode() {
        return certificateNationCode;
    }

    public void setCertificateNationCode(String certificateNationCode) {
        this.certificateNationCode = certificateNationCode == null ? null : certificateNationCode.trim();
    }

    public String getCertificateType() {
        return certificateType;
    }

    public void setCertificateType(String certificateType) {
        this.certificateType = certificateType == null ? null : certificateType.trim();
    }

    public String getCertificateNo() {
        return certificateNo;
    }

    public void setCertificateNo(String certificateNo) {
        this.certificateNo = certificateNo == null ? null : certificateNo.trim();
    }

    public String getCertificateMemo() {
        return certificateMemo;
    }

    public void setCertificateMemo(String certificateMemo) {
        this.certificateMemo = certificateMemo == null ? null : certificateMemo.trim();
    }

    public String getCertificateAddrZipCode() {
        return certificateAddrZipCode;
    }

    public void setCertificateAddrZipCode(String certificateAddrZipCode) {
        this.certificateAddrZipCode = certificateAddrZipCode == null ? null : certificateAddrZipCode.trim();
    }

    public String getCertificateAddrProvince() {
        return certificateAddrProvince;
    }

    public void setCertificateAddrProvince(String certificateAddrProvince) {
        this.certificateAddrProvince = certificateAddrProvince == null ? null : certificateAddrProvince.trim();
    }

    public String getCertificateAddrCity() {
        return certificateAddrCity;
    }

    public void setCertificateAddrCity(String certificateAddrCity) {
        this.certificateAddrCity = certificateAddrCity == null ? null : certificateAddrCity.trim();
    }

    public String getCertificateAddrTail01() {
        return certificateAddrTail01;
    }

    public void setCertificateAddrTail01(String certificateAddrTail01) {
        this.certificateAddrTail01 = certificateAddrTail01 == null ? null : certificateAddrTail01.trim();
    }

    public String getCertificateAddrTail02() {
        return certificateAddrTail02;
    }

    public void setCertificateAddrTail02(String certificateAddrTail02) {
        this.certificateAddrTail02 = certificateAddrTail02 == null ? null : certificateAddrTail02.trim();
    }

    public String getAddrNationCode() {
        return addrNationCode;
    }

    public void setAddrNationCode(String addrNationCode) {
        this.addrNationCode = addrNationCode == null ? null : addrNationCode.trim();
    }

    public String getAddrZipCode() {
        return addrZipCode;
    }

    public void setAddrZipCode(String addrZipCode) {
        this.addrZipCode = addrZipCode == null ? null : addrZipCode.trim();
    }

    public String getAddrProvince() {
        return addrProvince;
    }

    public void setAddrProvince(String addrProvince) {
        this.addrProvince = addrProvince == null ? null : addrProvince.trim();
    }

    public String getAddrCity() {
        return addrCity;
    }

    public void setAddrCity(String addrCity) {
        this.addrCity = addrCity == null ? null : addrCity.trim();
    }

    public String getAddrTail01() {
        return addrTail01;
    }

    public void setAddrTail01(String addrTail01) {
        this.addrTail01 = addrTail01 == null ? null : addrTail01.trim();
    }

    public String getAddrTail02() {
        return addrTail02;
    }

    public void setAddrTail02(String addrTail02) {
        this.addrTail02 = addrTail02 == null ? null : addrTail02.trim();
    }

    public String getAddrEnabled() {
        return addrEnabled;
    }

    public void setAddrEnabled(String addrEnabled) {
        this.addrEnabled = addrEnabled == null ? null : addrEnabled.trim();
    }

    public String getAddrMemo() {
        return addrMemo;
    }

    public void setAddrMemo(String addrMemo) {
        this.addrMemo = addrMemo == null ? null : addrMemo.trim();
    }

    public String getContactTele() {
        return contactTele;
    }

    public void setContactTele(String contactTele) {
        this.contactTele = contactTele == null ? null : contactTele.trim();
    }

    public String getContactFaxNo() {
        return contactFaxNo;
    }

    public void setContactFaxNo(String contactFaxNo) {
        this.contactFaxNo = contactFaxNo == null ? null : contactFaxNo.trim();
    }

    public String getContactMobile() {
        return contactMobile;
    }

    public void setContactMobile(String contactMobile) {
        this.contactMobile = contactMobile == null ? null : contactMobile.trim();
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail == null ? null : contactEmail.trim();
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race == null ? null : race.trim();
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education == null ? null : education.trim();
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation == null ? null : occupation.trim();
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
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