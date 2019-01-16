
package com.lkkhpg.dsis.integration.gds.resource.dealers.dealerNo.sponsorVerify.model;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Generated;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "dealerNo",
    "privacyFlag",
    "certificateNationCode",
    "certificateType",
    "certificateNo",
    "certificateAddrZipCode",
    "certificateAddrProvince",
    "certificateAddrCity",
    "certificateAddrTail01",
    "certificateAddrTail02",
    "certificateMemo",
    "addrNationCode",
    "addrZipCode",
    "addrProvince",
    "addrCity",
    "addrTail01",
    "addrTail02",
    "addrMemo",
    "addrEnabled",
    "contactTele",
    "contactFaxNo",
    "contactMobile",
    "contactEmail",
    "education",
    "occupation",
    "birthday",
    "race",
    "comments",
    "id",
    "createBy",
    "lastUpdateBy",
    "lastUpdateTime"
})
public class GdealerPersonalInfo {

    /**
     * 卡号
     * 
     */
    @JsonProperty("dealerNo")
    private String dealerNo;
    /**
     * 隐私标志
     * 
     */
    @JsonProperty("privacyFlag")
    private String privacyFlag;
    /**
     * 证件国别
     * 
     */
    @JsonProperty("certificateNationCode")
    private String certificateNationCode;
    /**
     * 证件类型
     * 
     */
    @JsonProperty("certificateType")
    private String certificateType;
    /**
     * 证件编号
     * 
     */
    @JsonProperty("certificateNo")
    private String certificateNo;
    /**
     * 证件地址邮编
     * 
     */
    @JsonProperty("certificateAddrZipCode")
    private String certificateAddrZipCode;
    /**
     * 证件地址省份
     * 
     */
    @JsonProperty("certificateAddrProvince")
    private String certificateAddrProvince;
    /**
     * 证件地址城市
     * 
     */
    @JsonProperty("certificateAddrCity")
    private String certificateAddrCity;
    /**
     * 证件详细地址01
     * 
     */
    @JsonProperty("certificateAddrTail01")
    private String certificateAddrTail01;
    /**
     * 证件详细地址02
     * 
     */
    @JsonProperty("certificateAddrTail02")
    private String certificateAddrTail02;
    /**
     * 证件备注
     * 
     */
    @JsonProperty("certificateMemo")
    private String certificateMemo;
    /**
     * 地址国家
     * 
     */
    @JsonProperty("addrNationCode")
    private String addrNationCode;
    /**
     * 地址邮编
     * 
     */
    @JsonProperty("addrZipCode")
    private String addrZipCode;
    /**
     * 地址省份
     * 
     */
    @JsonProperty("addrProvince")
    private String addrProvince;
    /**
     * 地址城市
     * 
     */
    @JsonProperty("addrCity")
    private String addrCity;
    /**
     * 区内详细地址01
     * 
     */
    @JsonProperty("addrTail01")
    private String addrTail01;
    /**
     * 区内详细地址02
     * 
     */
    @JsonProperty("addrTail02")
    private String addrTail02;
    /**
     * 地址备注信息
     * 
     */
    @JsonProperty("addrMemo")
    private String addrMemo;
    /**
     * 地址是否有效
     * 
     */
    @JsonProperty("addrEnabled")
    private Boolean addrEnabled;
    /**
     * 联络电话
     * 
     */
    @JsonProperty("contactTele")
    private String contactTele;
    /**
     * 传真号码
     * 
     */
    @JsonProperty("contactFaxNo")
    private String contactFaxNo;
    /**
     * 移动电话
     * 
     */
    @JsonProperty("contactMobile")
    private String contactMobile;
    /**
     * 电子邮件地址
     * 
     */
    @JsonProperty("contactEmail")
    private String contactEmail;
    /**
     * 教育程度
     * 
     */
    @JsonProperty("education")
    private String education;
    /**
     * 职业
     * 
     */
    @JsonProperty("occupation")
    private String occupation;
    /**
     * 出生日期
     * 
     */
    @JsonProperty("birthday")
    private String birthday;
    /**
     * 民族
     * 
     */
    @JsonProperty("race")
    private String race;
    /**
     * 备注
     * 
     */
    @JsonProperty("comments")
    private String comments;
    /**
     * 主键 id
     * 
     */
    @JsonProperty("id")
    private Long id;
    /**
     * 创建人
     * 
     */
    @JsonProperty("createBy")
    private String createBy;
    /**
     * 最后更新人
     * 
     */
    @JsonProperty("lastUpdateBy")
    private String lastUpdateBy;
    /**
     * 最后更新时间
     * 
     */
    @JsonProperty("lastUpdateTime")
    private String lastUpdateTime;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public GdealerPersonalInfo() {
    }

    /**
     * 
     * @param birthday
     * @param privacyFlag
     * @param education
     * @param occupation
     * @param contactTele
     * @param dealerNo
     * @param addrTail01
     * @param addrTail02
     * @param addrProvince
     * @param certificateNationCode
     * @param certificateAddrZipCode
     * @param addrMemo
     * @param id
     * @param certificateAddrProvince
     * @param certificateAddrCity
     * @param contactMobile
     * @param comments
     * @param race
     * @param contactEmail
     * @param certificateNo
     * @param addrCity
     * @param addrEnabled
     * @param createBy
     * @param addrNationCode
     * @param certificateAddrTail02
     * @param certificateAddrTail01
     * @param certificateMemo
     * @param addrZipCode
     * @param contactFaxNo
     * @param certificateType
     * @param lastUpdateBy
     * @param lastUpdateTime
     */
    public GdealerPersonalInfo(String dealerNo, String privacyFlag, String certificateNationCode, String certificateType, String certificateNo, String certificateAddrZipCode, String certificateAddrProvince, String certificateAddrCity, String certificateAddrTail01, String certificateAddrTail02, String certificateMemo, String addrNationCode, String addrZipCode, String addrProvince, String addrCity, String addrTail01, String addrTail02, String addrMemo, Boolean addrEnabled, String contactTele, String contactFaxNo, String contactMobile, String contactEmail, String education, String occupation, String birthday, String race, String comments, Long id, String createBy, String lastUpdateBy, String lastUpdateTime) {
        this.dealerNo = dealerNo;
        this.privacyFlag = privacyFlag;
        this.certificateNationCode = certificateNationCode;
        this.certificateType = certificateType;
        this.certificateNo = certificateNo;
        this.certificateAddrZipCode = certificateAddrZipCode;
        this.certificateAddrProvince = certificateAddrProvince;
        this.certificateAddrCity = certificateAddrCity;
        this.certificateAddrTail01 = certificateAddrTail01;
        this.certificateAddrTail02 = certificateAddrTail02;
        this.certificateMemo = certificateMemo;
        this.addrNationCode = addrNationCode;
        this.addrZipCode = addrZipCode;
        this.addrProvince = addrProvince;
        this.addrCity = addrCity;
        this.addrTail01 = addrTail01;
        this.addrTail02 = addrTail02;
        this.addrMemo = addrMemo;
        this.addrEnabled = addrEnabled;
        this.contactTele = contactTele;
        this.contactFaxNo = contactFaxNo;
        this.contactMobile = contactMobile;
        this.contactEmail = contactEmail;
        this.education = education;
        this.occupation = occupation;
        this.birthday = birthday;
        this.race = race;
        this.comments = comments;
        this.id = id;
        this.createBy = createBy;
        this.lastUpdateBy = lastUpdateBy;
        this.lastUpdateTime = lastUpdateTime;
    }

    /**
     * 卡号
     * 
     * @return
     *     The dealerNo
     */
    @JsonProperty("dealerNo")
    public String getDealerNo() {
        return dealerNo;
    }

    /**
     * 卡号
     * 
     * @param dealerNo
     *     The dealerNo
     */
    @JsonProperty("dealerNo")
    public void setDealerNo(String dealerNo) {
        this.dealerNo = dealerNo;
    }

    public GdealerPersonalInfo withDealerNo(String dealerNo) {
        this.dealerNo = dealerNo;
        return this;
    }

    /**
     * 隐私标志
     * 
     * @return
     *     The privacyFlag
     */
    @JsonProperty("privacyFlag")
    public String getPrivacyFlag() {
        return privacyFlag;
    }

    /**
     * 隐私标志
     * 
     * @param privacyFlag
     *     The privacyFlag
     */
    @JsonProperty("privacyFlag")
    public void setPrivacyFlag(String privacyFlag) {
        this.privacyFlag = privacyFlag;
    }

    public GdealerPersonalInfo withPrivacyFlag(String privacyFlag) {
        this.privacyFlag = privacyFlag;
        return this;
    }

    /**
     * 证件国别
     * 
     * @return
     *     The certificateNationCode
     */
    @JsonProperty("certificateNationCode")
    public String getCertificateNationCode() {
        return certificateNationCode;
    }

    /**
     * 证件国别
     * 
     * @param certificateNationCode
     *     The certificateNationCode
     */
    @JsonProperty("certificateNationCode")
    public void setCertificateNationCode(String certificateNationCode) {
        this.certificateNationCode = certificateNationCode;
    }

    public GdealerPersonalInfo withCertificateNationCode(String certificateNationCode) {
        this.certificateNationCode = certificateNationCode;
        return this;
    }

    /**
     * 证件类型
     * 
     * @return
     *     The certificateType
     */
    @JsonProperty("certificateType")
    public String getCertificateType() {
        return certificateType;
    }

    /**
     * 证件类型
     * 
     * @param certificateType
     *     The certificateType
     */
    @JsonProperty("certificateType")
    public void setCertificateType(String certificateType) {
        this.certificateType = certificateType;
    }

    public GdealerPersonalInfo withCertificateType(String certificateType) {
        this.certificateType = certificateType;
        return this;
    }

    /**
     * 证件编号
     * 
     * @return
     *     The certificateNo
     */
    @JsonProperty("certificateNo")
    public String getCertificateNo() {
        return certificateNo;
    }

    /**
     * 证件编号
     * 
     * @param certificateNo
     *     The certificateNo
     */
    @JsonProperty("certificateNo")
    public void setCertificateNo(String certificateNo) {
        this.certificateNo = certificateNo;
    }

    public GdealerPersonalInfo withCertificateNo(String certificateNo) {
        this.certificateNo = certificateNo;
        return this;
    }

    /**
     * 证件地址邮编
     * 
     * @return
     *     The certificateAddrZipCode
     */
    @JsonProperty("certificateAddrZipCode")
    public String getCertificateAddrZipCode() {
        return certificateAddrZipCode;
    }

    /**
     * 证件地址邮编
     * 
     * @param certificateAddrZipCode
     *     The certificateAddrZipCode
     */
    @JsonProperty("certificateAddrZipCode")
    public void setCertificateAddrZipCode(String certificateAddrZipCode) {
        this.certificateAddrZipCode = certificateAddrZipCode;
    }

    public GdealerPersonalInfo withCertificateAddrZipCode(String certificateAddrZipCode) {
        this.certificateAddrZipCode = certificateAddrZipCode;
        return this;
    }

    /**
     * 证件地址省份
     * 
     * @return
     *     The certificateAddrProvince
     */
    @JsonProperty("certificateAddrProvince")
    public String getCertificateAddrProvince() {
        return certificateAddrProvince;
    }

    /**
     * 证件地址省份
     * 
     * @param certificateAddrProvince
     *     The certificateAddrProvince
     */
    @JsonProperty("certificateAddrProvince")
    public void setCertificateAddrProvince(String certificateAddrProvince) {
        this.certificateAddrProvince = certificateAddrProvince;
    }

    public GdealerPersonalInfo withCertificateAddrProvince(String certificateAddrProvince) {
        this.certificateAddrProvince = certificateAddrProvince;
        return this;
    }

    /**
     * 证件地址城市
     * 
     * @return
     *     The certificateAddrCity
     */
    @JsonProperty("certificateAddrCity")
    public String getCertificateAddrCity() {
        return certificateAddrCity;
    }

    /**
     * 证件地址城市
     * 
     * @param certificateAddrCity
     *     The certificateAddrCity
     */
    @JsonProperty("certificateAddrCity")
    public void setCertificateAddrCity(String certificateAddrCity) {
        this.certificateAddrCity = certificateAddrCity;
    }

    public GdealerPersonalInfo withCertificateAddrCity(String certificateAddrCity) {
        this.certificateAddrCity = certificateAddrCity;
        return this;
    }

    /**
     * 证件详细地址01
     * 
     * @return
     *     The certificateAddrTail01
     */
    @JsonProperty("certificateAddrTail01")
    public String getCertificateAddrTail01() {
        return certificateAddrTail01;
    }

    /**
     * 证件详细地址01
     * 
     * @param certificateAddrTail01
     *     The certificateAddrTail01
     */
    @JsonProperty("certificateAddrTail01")
    public void setCertificateAddrTail01(String certificateAddrTail01) {
        this.certificateAddrTail01 = certificateAddrTail01;
    }

    public GdealerPersonalInfo withCertificateAddrTail01(String certificateAddrTail01) {
        this.certificateAddrTail01 = certificateAddrTail01;
        return this;
    }

    /**
     * 证件详细地址02
     * 
     * @return
     *     The certificateAddrTail02
     */
    @JsonProperty("certificateAddrTail02")
    public String getCertificateAddrTail02() {
        return certificateAddrTail02;
    }

    /**
     * 证件详细地址02
     * 
     * @param certificateAddrTail02
     *     The certificateAddrTail02
     */
    @JsonProperty("certificateAddrTail02")
    public void setCertificateAddrTail02(String certificateAddrTail02) {
        this.certificateAddrTail02 = certificateAddrTail02;
    }

    public GdealerPersonalInfo withCertificateAddrTail02(String certificateAddrTail02) {
        this.certificateAddrTail02 = certificateAddrTail02;
        return this;
    }

    /**
     * 证件备注
     * 
     * @return
     *     The certificateMemo
     */
    @JsonProperty("certificateMemo")
    public String getCertificateMemo() {
        return certificateMemo;
    }

    /**
     * 证件备注
     * 
     * @param certificateMemo
     *     The certificateMemo
     */
    @JsonProperty("certificateMemo")
    public void setCertificateMemo(String certificateMemo) {
        this.certificateMemo = certificateMemo;
    }

    public GdealerPersonalInfo withCertificateMemo(String certificateMemo) {
        this.certificateMemo = certificateMemo;
        return this;
    }

    /**
     * 地址国家
     * 
     * @return
     *     The addrNationCode
     */
    @JsonProperty("addrNationCode")
    public String getAddrNationCode() {
        return addrNationCode;
    }

    /**
     * 地址国家
     * 
     * @param addrNationCode
     *     The addrNationCode
     */
    @JsonProperty("addrNationCode")
    public void setAddrNationCode(String addrNationCode) {
        this.addrNationCode = addrNationCode;
    }

    public GdealerPersonalInfo withAddrNationCode(String addrNationCode) {
        this.addrNationCode = addrNationCode;
        return this;
    }

    /**
     * 地址邮编
     * 
     * @return
     *     The addrZipCode
     */
    @JsonProperty("addrZipCode")
    public String getAddrZipCode() {
        return addrZipCode;
    }

    /**
     * 地址邮编
     * 
     * @param addrZipCode
     *     The addrZipCode
     */
    @JsonProperty("addrZipCode")
    public void setAddrZipCode(String addrZipCode) {
        this.addrZipCode = addrZipCode;
    }

    public GdealerPersonalInfo withAddrZipCode(String addrZipCode) {
        this.addrZipCode = addrZipCode;
        return this;
    }

    /**
     * 地址省份
     * 
     * @return
     *     The addrProvince
     */
    @JsonProperty("addrProvince")
    public String getAddrProvince() {
        return addrProvince;
    }

    /**
     * 地址省份
     * 
     * @param addrProvince
     *     The addrProvince
     */
    @JsonProperty("addrProvince")
    public void setAddrProvince(String addrProvince) {
        this.addrProvince = addrProvince;
    }

    public GdealerPersonalInfo withAddrProvince(String addrProvince) {
        this.addrProvince = addrProvince;
        return this;
    }

    /**
     * 地址城市
     * 
     * @return
     *     The addrCity
     */
    @JsonProperty("addrCity")
    public String getAddrCity() {
        return addrCity;
    }

    /**
     * 地址城市
     * 
     * @param addrCity
     *     The addrCity
     */
    @JsonProperty("addrCity")
    public void setAddrCity(String addrCity) {
        this.addrCity = addrCity;
    }

    public GdealerPersonalInfo withAddrCity(String addrCity) {
        this.addrCity = addrCity;
        return this;
    }

    /**
     * 区内详细地址01
     * 
     * @return
     *     The addrTail01
     */
    @JsonProperty("addrTail01")
    public String getAddrTail01() {
        return addrTail01;
    }

    /**
     * 区内详细地址01
     * 
     * @param addrTail01
     *     The addrTail01
     */
    @JsonProperty("addrTail01")
    public void setAddrTail01(String addrTail01) {
        this.addrTail01 = addrTail01;
    }

    public GdealerPersonalInfo withAddrTail01(String addrTail01) {
        this.addrTail01 = addrTail01;
        return this;
    }

    /**
     * 区内详细地址02
     * 
     * @return
     *     The addrTail02
     */
    @JsonProperty("addrTail02")
    public String getAddrTail02() {
        return addrTail02;
    }

    /**
     * 区内详细地址02
     * 
     * @param addrTail02
     *     The addrTail02
     */
    @JsonProperty("addrTail02")
    public void setAddrTail02(String addrTail02) {
        this.addrTail02 = addrTail02;
    }

    public GdealerPersonalInfo withAddrTail02(String addrTail02) {
        this.addrTail02 = addrTail02;
        return this;
    }

    /**
     * 地址备注信息
     * 
     * @return
     *     The addrMemo
     */
    @JsonProperty("addrMemo")
    public String getAddrMemo() {
        return addrMemo;
    }

    /**
     * 地址备注信息
     * 
     * @param addrMemo
     *     The addrMemo
     */
    @JsonProperty("addrMemo")
    public void setAddrMemo(String addrMemo) {
        this.addrMemo = addrMemo;
    }

    public GdealerPersonalInfo withAddrMemo(String addrMemo) {
        this.addrMemo = addrMemo;
        return this;
    }

    /**
     * 地址是否有效
     * 
     * @return
     *     The addrEnabled
     */
    @JsonProperty("addrEnabled")
    public Boolean getAddrEnabled() {
        return addrEnabled;
    }

    /**
     * 地址是否有效
     * 
     * @param addrEnabled
     *     The addrEnabled
     */
    @JsonProperty("addrEnabled")
    public void setAddrEnabled(Boolean addrEnabled) {
        this.addrEnabled = addrEnabled;
    }

    public GdealerPersonalInfo withAddrEnabled(Boolean addrEnabled) {
        this.addrEnabled = addrEnabled;
        return this;
    }

    /**
     * 联络电话
     * 
     * @return
     *     The contactTele
     */
    @JsonProperty("contactTele")
    public String getContactTele() {
        return contactTele;
    }

    /**
     * 联络电话
     * 
     * @param contactTele
     *     The contactTele
     */
    @JsonProperty("contactTele")
    public void setContactTele(String contactTele) {
        this.contactTele = contactTele;
    }

    public GdealerPersonalInfo withContactTele(String contactTele) {
        this.contactTele = contactTele;
        return this;
    }

    /**
     * 传真号码
     * 
     * @return
     *     The contactFaxNo
     */
    @JsonProperty("contactFaxNo")
    public String getContactFaxNo() {
        return contactFaxNo;
    }

    /**
     * 传真号码
     * 
     * @param contactFaxNo
     *     The contactFaxNo
     */
    @JsonProperty("contactFaxNo")
    public void setContactFaxNo(String contactFaxNo) {
        this.contactFaxNo = contactFaxNo;
    }

    public GdealerPersonalInfo withContactFaxNo(String contactFaxNo) {
        this.contactFaxNo = contactFaxNo;
        return this;
    }

    /**
     * 移动电话
     * 
     * @return
     *     The contactMobile
     */
    @JsonProperty("contactMobile")
    public String getContactMobile() {
        return contactMobile;
    }

    /**
     * 移动电话
     * 
     * @param contactMobile
     *     The contactMobile
     */
    @JsonProperty("contactMobile")
    public void setContactMobile(String contactMobile) {
        this.contactMobile = contactMobile;
    }

    public GdealerPersonalInfo withContactMobile(String contactMobile) {
        this.contactMobile = contactMobile;
        return this;
    }

    /**
     * 电子邮件地址
     * 
     * @return
     *     The contactEmail
     */
    @JsonProperty("contactEmail")
    public String getContactEmail() {
        return contactEmail;
    }

    /**
     * 电子邮件地址
     * 
     * @param contactEmail
     *     The contactEmail
     */
    @JsonProperty("contactEmail")
    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public GdealerPersonalInfo withContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
        return this;
    }

    /**
     * 教育程度
     * 
     * @return
     *     The education
     */
    @JsonProperty("education")
    public String getEducation() {
        return education;
    }

    /**
     * 教育程度
     * 
     * @param education
     *     The education
     */
    @JsonProperty("education")
    public void setEducation(String education) {
        this.education = education;
    }

    public GdealerPersonalInfo withEducation(String education) {
        this.education = education;
        return this;
    }

    /**
     * 职业
     * 
     * @return
     *     The occupation
     */
    @JsonProperty("occupation")
    public String getOccupation() {
        return occupation;
    }

    /**
     * 职业
     * 
     * @param occupation
     *     The occupation
     */
    @JsonProperty("occupation")
    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public GdealerPersonalInfo withOccupation(String occupation) {
        this.occupation = occupation;
        return this;
    }

    /**
     * 出生日期
     * 
     * @return
     *     The birthday
     */
    @JsonProperty("birthday")
    public String getBirthday() {
        return birthday;
    }

    /**
     * 出生日期
     * 
     * @param birthday
     *     The birthday
     */
    @JsonProperty("birthday")
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public GdealerPersonalInfo withBirthday(String birthday) {
        this.birthday = birthday;
        return this;
    }

    /**
     * 民族
     * 
     * @return
     *     The race
     */
    @JsonProperty("race")
    public String getRace() {
        return race;
    }

    /**
     * 民族
     * 
     * @param race
     *     The race
     */
    @JsonProperty("race")
    public void setRace(String race) {
        this.race = race;
    }

    public GdealerPersonalInfo withRace(String race) {
        this.race = race;
        return this;
    }

    /**
     * 备注
     * 
     * @return
     *     The comments
     */
    @JsonProperty("comments")
    public String getComments() {
        return comments;
    }

    /**
     * 备注
     * 
     * @param comments
     *     The comments
     */
    @JsonProperty("comments")
    public void setComments(String comments) {
        this.comments = comments;
    }

    public GdealerPersonalInfo withComments(String comments) {
        this.comments = comments;
        return this;
    }

    /**
     * 主键 id
     * 
     * @return
     *     The id
     */
    @JsonProperty("id")
    public Long getId() {
        return id;
    }

    /**
     * 主键 id
     * 
     * @param id
     *     The id
     */
    @JsonProperty("id")
    public void setId(Long id) {
        this.id = id;
    }

    public GdealerPersonalInfo withId(Long id) {
        this.id = id;
        return this;
    }

    /**
     * 创建人
     * 
     * @return
     *     The createBy
     */
    @JsonProperty("createBy")
    public String getCreateBy() {
        return createBy;
    }

    /**
     * 创建人
     * 
     * @param createBy
     *     The createBy
     */
    @JsonProperty("createBy")
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public GdealerPersonalInfo withCreateBy(String createBy) {
        this.createBy = createBy;
        return this;
    }

    /**
     * 最后更新人
     * 
     * @return
     *     The lastUpdateBy
     */
    @JsonProperty("lastUpdateBy")
    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    /**
     * 最后更新人
     * 
     * @param lastUpdateBy
     *     The lastUpdateBy
     */
    @JsonProperty("lastUpdateBy")
    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    public GdealerPersonalInfo withLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
        return this;
    }

    /**
     * 最后更新时间
     * 
     * @return
     *     The lastUpdateTime
     */
    @JsonProperty("lastUpdateTime")
    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    /**
     * 最后更新时间
     * 
     * @param lastUpdateTime
     *     The lastUpdateTime
     */
    @JsonProperty("lastUpdateTime")
    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public GdealerPersonalInfo withLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public GdealerPersonalInfo withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(dealerNo).append(privacyFlag).append(certificateNationCode).append(certificateType).append(certificateNo).append(certificateAddrZipCode).append(certificateAddrProvince).append(certificateAddrCity).append(certificateAddrTail01).append(certificateAddrTail02).append(certificateMemo).append(addrNationCode).append(addrZipCode).append(addrProvince).append(addrCity).append(addrTail01).append(addrTail02).append(addrMemo).append(addrEnabled).append(contactTele).append(contactFaxNo).append(contactMobile).append(contactEmail).append(education).append(occupation).append(birthday).append(race).append(comments).append(id).append(createBy).append(lastUpdateBy).append(lastUpdateTime).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof GdealerPersonalInfo) == false) {
            return false;
        }
        GdealerPersonalInfo rhs = ((GdealerPersonalInfo) other);
        return new EqualsBuilder().append(dealerNo, rhs.dealerNo).append(privacyFlag, rhs.privacyFlag).append(certificateNationCode, rhs.certificateNationCode).append(certificateType, rhs.certificateType).append(certificateNo, rhs.certificateNo).append(certificateAddrZipCode, rhs.certificateAddrZipCode).append(certificateAddrProvince, rhs.certificateAddrProvince).append(certificateAddrCity, rhs.certificateAddrCity).append(certificateAddrTail01, rhs.certificateAddrTail01).append(certificateAddrTail02, rhs.certificateAddrTail02).append(certificateMemo, rhs.certificateMemo).append(addrNationCode, rhs.addrNationCode).append(addrZipCode, rhs.addrZipCode).append(addrProvince, rhs.addrProvince).append(addrCity, rhs.addrCity).append(addrTail01, rhs.addrTail01).append(addrTail02, rhs.addrTail02).append(addrMemo, rhs.addrMemo).append(addrEnabled, rhs.addrEnabled).append(contactTele, rhs.contactTele).append(contactFaxNo, rhs.contactFaxNo).append(contactMobile, rhs.contactMobile).append(contactEmail, rhs.contactEmail).append(education, rhs.education).append(occupation, rhs.occupation).append(birthday, rhs.birthday).append(race, rhs.race).append(comments, rhs.comments).append(id, rhs.id).append(createBy, rhs.createBy).append(lastUpdateBy, rhs.lastUpdateBy).append(lastUpdateTime, rhs.lastUpdateTime).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
