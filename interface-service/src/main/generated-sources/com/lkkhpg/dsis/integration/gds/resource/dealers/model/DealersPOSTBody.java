
package com.lkkhpg.dsis.integration.gds.resource.dealers.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    "dealerType",
    "dealerSubType",
    "dealerPostCode",
    "dealerName",
    "sponsorNo",
    "registerSpouse",
    "typeEffectiveDate",
    "typeInactiveDate",
    "postEffectiveDate",
    "postInactiveDate",
    "saleOrgCode",
    "saleBranchNo",
    "saleZoneNo",
    "appPeriod",
    "appFirstSoNo",
    "appSpPeriod",
    "appForInternational",
    "sex",
    "englishName",
    "comments",
    "synLockStatus",
    "opLockStatus",
    "taxCustCode",
    "gdealerPersonalInfo",
    "gdealerSpouseInfo",
    "gdealerTypeStatuses",
    "adviseNo",
    "id",
    "createBy",
    "lastUpdateBy",
    "lastUpdateTime"
})
public class DealersPOSTBody {

    /**
     * 卡号
     * 
     */
    @JsonProperty("dealerNo")
    private String dealerNo;
    /**
     * 身份类型
     * 
     */
    @JsonProperty("dealerType")
    private String dealerType;
    /**
     * 身份子类型
     * 
     */
    @JsonProperty("dealerSubType")
    private String dealerSubType;
    /**
     * 职级代码
     * 
     */
    @JsonProperty("dealerPostCode")
    private Long dealerPostCode;
    /**
     * 持卡人姓名
     * 
     */
    @JsonProperty("dealerName")
    private String dealerName;
    /**
     * 推荐人卡号
     * 
     */
    @JsonProperty("sponsorNo")
    private String sponsorNo;
    /**
     * 是否登记配偶信息
     * 
     */
    @JsonProperty("registerSpouse")
    private Boolean registerSpouse;
    /**
     * 资格开始日期
     * 
     */
    @JsonProperty("typeEffectiveDate")
    private String typeEffectiveDate;
    /**
     * 资格失效日期
     * 
     */
    @JsonProperty("typeInactiveDate")
    private String typeInactiveDate;
    /**
     * 职级开始日期
     * 
     */
    @JsonProperty("postEffectiveDate")
    private String postEffectiveDate;
    /**
     * 职级失效日期
     * 
     */
    @JsonProperty("postInactiveDate")
    private String postInactiveDate;
    /**
     * 所属机构代号
     * 
     */
    @JsonProperty("saleOrgCode")
    private String saleOrgCode;
    /**
     * 所属分支机构代号
     * 
     */
    @JsonProperty("saleBranchNo")
    private String saleBranchNo;
    /**
     * 所属销售片区代号
     * 
     */
    @JsonProperty("saleZoneNo")
    private String saleZoneNo;
    /**
     * 申请办卡月份
     * 
     */
    @JsonProperty("appPeriod")
    private String appPeriod;
    /**
     * 申请首单单号
     * 
     */
    @JsonProperty("appFirstSoNo")
    private String appFirstSoNo;
    /**
     * 申请业务员月份
     * 
     */
    @JsonProperty("appSpPeriod")
    private String appSpPeriod;
    /**
     * 是否国际推荐
     * 
     */
    @JsonProperty("appForInternational")
    private Boolean appForInternational;
    /**
     * 性别
     * 
     */
    @JsonProperty("sex")
    private String sex;
    /**
     * 英文姓名
     * 
     */
    @JsonProperty("englishName")
    private String englishName;
    /**
     * 备注
     * 
     */
    @JsonProperty("comments")
    private String comments;
    /**
     * 锁状态
     * 
     */
    @JsonProperty("synLockStatus")
    private String synLockStatus;
    /**
     * 锁状态
     * 
     */
    @JsonProperty("opLockStatus")
    private String opLockStatus;
    /**
     * TAX_CUST_CODE
     * 
     */
    @JsonProperty("taxCustCode")
    private String taxCustCode;
    @JsonProperty("gdealerPersonalInfo")
    private GdealerPersonalInfo gdealerPersonalInfo;
    @JsonProperty("gdealerSpouseInfo")
    private GdealerSpouseInfo gdealerSpouseInfo;
    @JsonProperty("gdealerTypeStatuses")
    private List<GdealerTypeStatus> gdealerTypeStatuses = new ArrayList<GdealerTypeStatus>();
    /**
     * 
     * 
     */
    @JsonProperty("adviseNo")
    private String adviseNo;
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
    public DealersPOSTBody() {
    }

    /**
     * 
     * @param englishName
     * @param registerSpouse
     * @param appSpPeriod
     * @param dealerSubType
     * @param dealerNo
     * @param saleOrgCode
     * @param typeEffectiveDate
     * @param saleZoneNo
     * @param typeInactiveDate
     * @param adviseNo
     * @param postInactiveDate
     * @param dealerType
     * @param taxCustCode
     * @param id
     * @param dealerPostCode
     * @param dealerName
     * @param comments
     * @param sex
     * @param appForInternational
     * @param sponsorNo
     * @param saleBranchNo
     * @param opLockStatus
     * @param gdealerSpouseInfo
     * @param gdealerTypeStatuses
     * @param appPeriod
     * @param appFirstSoNo
     * @param createBy
     * @param synLockStatus
     * @param gdealerPersonalInfo
     * @param postEffectiveDate
     * @param lastUpdateBy
     * @param lastUpdateTime
     */
    public DealersPOSTBody(String dealerNo, String dealerType, String dealerSubType, Long dealerPostCode, String dealerName, String sponsorNo, Boolean registerSpouse, String typeEffectiveDate, String typeInactiveDate, String postEffectiveDate, String postInactiveDate, String saleOrgCode, String saleBranchNo, String saleZoneNo, String appPeriod, String appFirstSoNo, String appSpPeriod, Boolean appForInternational, String sex, String englishName, String comments, String synLockStatus, String opLockStatus, String taxCustCode, GdealerPersonalInfo gdealerPersonalInfo, GdealerSpouseInfo gdealerSpouseInfo, List<GdealerTypeStatus> gdealerTypeStatuses, String adviseNo, Long id, String createBy, String lastUpdateBy, String lastUpdateTime) {
        this.dealerNo = dealerNo;
        this.dealerType = dealerType;
        this.dealerSubType = dealerSubType;
        this.dealerPostCode = dealerPostCode;
        this.dealerName = dealerName;
        this.sponsorNo = sponsorNo;
        this.registerSpouse = registerSpouse;
        this.typeEffectiveDate = typeEffectiveDate;
        this.typeInactiveDate = typeInactiveDate;
        this.postEffectiveDate = postEffectiveDate;
        this.postInactiveDate = postInactiveDate;
        this.saleOrgCode = saleOrgCode;
        this.saleBranchNo = saleBranchNo;
        this.saleZoneNo = saleZoneNo;
        this.appPeriod = appPeriod;
        this.appFirstSoNo = appFirstSoNo;
        this.appSpPeriod = appSpPeriod;
        this.appForInternational = appForInternational;
        this.sex = sex;
        this.englishName = englishName;
        this.comments = comments;
        this.synLockStatus = synLockStatus;
        this.opLockStatus = opLockStatus;
        this.taxCustCode = taxCustCode;
        this.gdealerPersonalInfo = gdealerPersonalInfo;
        this.gdealerSpouseInfo = gdealerSpouseInfo;
        this.gdealerTypeStatuses = gdealerTypeStatuses;
        this.adviseNo = adviseNo;
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

    public DealersPOSTBody withDealerNo(String dealerNo) {
        this.dealerNo = dealerNo;
        return this;
    }

    /**
     * 身份类型
     * 
     * @return
     *     The dealerType
     */
    @JsonProperty("dealerType")
    public String getDealerType() {
        return dealerType;
    }

    /**
     * 身份类型
     * 
     * @param dealerType
     *     The dealerType
     */
    @JsonProperty("dealerType")
    public void setDealerType(String dealerType) {
        this.dealerType = dealerType;
    }

    public DealersPOSTBody withDealerType(String dealerType) {
        this.dealerType = dealerType;
        return this;
    }

    /**
     * 身份子类型
     * 
     * @return
     *     The dealerSubType
     */
    @JsonProperty("dealerSubType")
    public String getDealerSubType() {
        return dealerSubType;
    }

    /**
     * 身份子类型
     * 
     * @param dealerSubType
     *     The dealerSubType
     */
    @JsonProperty("dealerSubType")
    public void setDealerSubType(String dealerSubType) {
        this.dealerSubType = dealerSubType;
    }

    public DealersPOSTBody withDealerSubType(String dealerSubType) {
        this.dealerSubType = dealerSubType;
        return this;
    }

    /**
     * 职级代码
     * 
     * @return
     *     The dealerPostCode
     */
    @JsonProperty("dealerPostCode")
    public Long getDealerPostCode() {
        return dealerPostCode;
    }

    /**
     * 职级代码
     * 
     * @param dealerPostCode
     *     The dealerPostCode
     */
    @JsonProperty("dealerPostCode")
    public void setDealerPostCode(Long dealerPostCode) {
        this.dealerPostCode = dealerPostCode;
    }

    public DealersPOSTBody withDealerPostCode(Long dealerPostCode) {
        this.dealerPostCode = dealerPostCode;
        return this;
    }

    /**
     * 持卡人姓名
     * 
     * @return
     *     The dealerName
     */
    @JsonProperty("dealerName")
    public String getDealerName() {
        return dealerName;
    }

    /**
     * 持卡人姓名
     * 
     * @param dealerName
     *     The dealerName
     */
    @JsonProperty("dealerName")
    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    public DealersPOSTBody withDealerName(String dealerName) {
        this.dealerName = dealerName;
        return this;
    }

    /**
     * 推荐人卡号
     * 
     * @return
     *     The sponsorNo
     */
    @JsonProperty("sponsorNo")
    public String getSponsorNo() {
        return sponsorNo;
    }

    /**
     * 推荐人卡号
     * 
     * @param sponsorNo
     *     The sponsorNo
     */
    @JsonProperty("sponsorNo")
    public void setSponsorNo(String sponsorNo) {
        this.sponsorNo = sponsorNo;
    }

    public DealersPOSTBody withSponsorNo(String sponsorNo) {
        this.sponsorNo = sponsorNo;
        return this;
    }

    /**
     * 是否登记配偶信息
     * 
     * @return
     *     The registerSpouse
     */
    @JsonProperty("registerSpouse")
    public Boolean getRegisterSpouse() {
        return registerSpouse;
    }

    /**
     * 是否登记配偶信息
     * 
     * @param registerSpouse
     *     The registerSpouse
     */
    @JsonProperty("registerSpouse")
    public void setRegisterSpouse(Boolean registerSpouse) {
        this.registerSpouse = registerSpouse;
    }

    public DealersPOSTBody withRegisterSpouse(Boolean registerSpouse) {
        this.registerSpouse = registerSpouse;
        return this;
    }

    /**
     * 资格开始日期
     * 
     * @return
     *     The typeEffectiveDate
     */
    @JsonProperty("typeEffectiveDate")
    public String getTypeEffectiveDate() {
        return typeEffectiveDate;
    }

    /**
     * 资格开始日期
     * 
     * @param typeEffectiveDate
     *     The typeEffectiveDate
     */
    @JsonProperty("typeEffectiveDate")
    public void setTypeEffectiveDate(String typeEffectiveDate) {
        this.typeEffectiveDate = typeEffectiveDate;
    }

    public DealersPOSTBody withTypeEffectiveDate(String typeEffectiveDate) {
        this.typeEffectiveDate = typeEffectiveDate;
        return this;
    }

    /**
     * 资格失效日期
     * 
     * @return
     *     The typeInactiveDate
     */
    @JsonProperty("typeInactiveDate")
    public String getTypeInactiveDate() {
        return typeInactiveDate;
    }

    /**
     * 资格失效日期
     * 
     * @param typeInactiveDate
     *     The typeInactiveDate
     */
    @JsonProperty("typeInactiveDate")
    public void setTypeInactiveDate(String typeInactiveDate) {
        this.typeInactiveDate = typeInactiveDate;
    }

    public DealersPOSTBody withTypeInactiveDate(String typeInactiveDate) {
        this.typeInactiveDate = typeInactiveDate;
        return this;
    }

    /**
     * 职级开始日期
     * 
     * @return
     *     The postEffectiveDate
     */
    @JsonProperty("postEffectiveDate")
    public String getPostEffectiveDate() {
        return postEffectiveDate;
    }

    /**
     * 职级开始日期
     * 
     * @param postEffectiveDate
     *     The postEffectiveDate
     */
    @JsonProperty("postEffectiveDate")
    public void setPostEffectiveDate(String postEffectiveDate) {
        this.postEffectiveDate = postEffectiveDate;
    }

    public DealersPOSTBody withPostEffectiveDate(String postEffectiveDate) {
        this.postEffectiveDate = postEffectiveDate;
        return this;
    }

    /**
     * 职级失效日期
     * 
     * @return
     *     The postInactiveDate
     */
    @JsonProperty("postInactiveDate")
    public String getPostInactiveDate() {
        return postInactiveDate;
    }

    /**
     * 职级失效日期
     * 
     * @param postInactiveDate
     *     The postInactiveDate
     */
    @JsonProperty("postInactiveDate")
    public void setPostInactiveDate(String postInactiveDate) {
        this.postInactiveDate = postInactiveDate;
    }

    public DealersPOSTBody withPostInactiveDate(String postInactiveDate) {
        this.postInactiveDate = postInactiveDate;
        return this;
    }

    /**
     * 所属机构代号
     * 
     * @return
     *     The saleOrgCode
     */
    @JsonProperty("saleOrgCode")
    public String getSaleOrgCode() {
        return saleOrgCode;
    }

    /**
     * 所属机构代号
     * 
     * @param saleOrgCode
     *     The saleOrgCode
     */
    @JsonProperty("saleOrgCode")
    public void setSaleOrgCode(String saleOrgCode) {
        this.saleOrgCode = saleOrgCode;
    }

    public DealersPOSTBody withSaleOrgCode(String saleOrgCode) {
        this.saleOrgCode = saleOrgCode;
        return this;
    }

    /**
     * 所属分支机构代号
     * 
     * @return
     *     The saleBranchNo
     */
    @JsonProperty("saleBranchNo")
    public String getSaleBranchNo() {
        return saleBranchNo;
    }

    /**
     * 所属分支机构代号
     * 
     * @param saleBranchNo
     *     The saleBranchNo
     */
    @JsonProperty("saleBranchNo")
    public void setSaleBranchNo(String saleBranchNo) {
        this.saleBranchNo = saleBranchNo;
    }

    public DealersPOSTBody withSaleBranchNo(String saleBranchNo) {
        this.saleBranchNo = saleBranchNo;
        return this;
    }

    /**
     * 所属销售片区代号
     * 
     * @return
     *     The saleZoneNo
     */
    @JsonProperty("saleZoneNo")
    public String getSaleZoneNo() {
        return saleZoneNo;
    }

    /**
     * 所属销售片区代号
     * 
     * @param saleZoneNo
     *     The saleZoneNo
     */
    @JsonProperty("saleZoneNo")
    public void setSaleZoneNo(String saleZoneNo) {
        this.saleZoneNo = saleZoneNo;
    }

    public DealersPOSTBody withSaleZoneNo(String saleZoneNo) {
        this.saleZoneNo = saleZoneNo;
        return this;
    }

    /**
     * 申请办卡月份
     * 
     * @return
     *     The appPeriod
     */
    @JsonProperty("appPeriod")
    public String getAppPeriod() {
        return appPeriod;
    }

    /**
     * 申请办卡月份
     * 
     * @param appPeriod
     *     The appPeriod
     */
    @JsonProperty("appPeriod")
    public void setAppPeriod(String appPeriod) {
        this.appPeriod = appPeriod;
    }

    public DealersPOSTBody withAppPeriod(String appPeriod) {
        this.appPeriod = appPeriod;
        return this;
    }

    /**
     * 申请首单单号
     * 
     * @return
     *     The appFirstSoNo
     */
    @JsonProperty("appFirstSoNo")
    public String getAppFirstSoNo() {
        return appFirstSoNo;
    }

    /**
     * 申请首单单号
     * 
     * @param appFirstSoNo
     *     The appFirstSoNo
     */
    @JsonProperty("appFirstSoNo")
    public void setAppFirstSoNo(String appFirstSoNo) {
        this.appFirstSoNo = appFirstSoNo;
    }

    public DealersPOSTBody withAppFirstSoNo(String appFirstSoNo) {
        this.appFirstSoNo = appFirstSoNo;
        return this;
    }

    /**
     * 申请业务员月份
     * 
     * @return
     *     The appSpPeriod
     */
    @JsonProperty("appSpPeriod")
    public String getAppSpPeriod() {
        return appSpPeriod;
    }

    /**
     * 申请业务员月份
     * 
     * @param appSpPeriod
     *     The appSpPeriod
     */
    @JsonProperty("appSpPeriod")
    public void setAppSpPeriod(String appSpPeriod) {
        this.appSpPeriod = appSpPeriod;
    }

    public DealersPOSTBody withAppSpPeriod(String appSpPeriod) {
        this.appSpPeriod = appSpPeriod;
        return this;
    }

    /**
     * 是否国际推荐
     * 
     * @return
     *     The appForInternational
     */
    @JsonProperty("appForInternational")
    public Boolean getAppForInternational() {
        return appForInternational;
    }

    /**
     * 是否国际推荐
     * 
     * @param appForInternational
     *     The appForInternational
     */
    @JsonProperty("appForInternational")
    public void setAppForInternational(Boolean appForInternational) {
        this.appForInternational = appForInternational;
    }

    public DealersPOSTBody withAppForInternational(Boolean appForInternational) {
        this.appForInternational = appForInternational;
        return this;
    }

    /**
     * 性别
     * 
     * @return
     *     The sex
     */
    @JsonProperty("sex")
    public String getSex() {
        return sex;
    }

    /**
     * 性别
     * 
     * @param sex
     *     The sex
     */
    @JsonProperty("sex")
    public void setSex(String sex) {
        this.sex = sex;
    }

    public DealersPOSTBody withSex(String sex) {
        this.sex = sex;
        return this;
    }

    /**
     * 英文姓名
     * 
     * @return
     *     The englishName
     */
    @JsonProperty("englishName")
    public String getEnglishName() {
        return englishName;
    }

    /**
     * 英文姓名
     * 
     * @param englishName
     *     The englishName
     */
    @JsonProperty("englishName")
    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public DealersPOSTBody withEnglishName(String englishName) {
        this.englishName = englishName;
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

    public DealersPOSTBody withComments(String comments) {
        this.comments = comments;
        return this;
    }

    /**
     * 锁状态
     * 
     * @return
     *     The synLockStatus
     */
    @JsonProperty("synLockStatus")
    public String getSynLockStatus() {
        return synLockStatus;
    }

    /**
     * 锁状态
     * 
     * @param synLockStatus
     *     The synLockStatus
     */
    @JsonProperty("synLockStatus")
    public void setSynLockStatus(String synLockStatus) {
        this.synLockStatus = synLockStatus;
    }

    public DealersPOSTBody withSynLockStatus(String synLockStatus) {
        this.synLockStatus = synLockStatus;
        return this;
    }

    /**
     * 锁状态
     * 
     * @return
     *     The opLockStatus
     */
    @JsonProperty("opLockStatus")
    public String getOpLockStatus() {
        return opLockStatus;
    }

    /**
     * 锁状态
     * 
     * @param opLockStatus
     *     The opLockStatus
     */
    @JsonProperty("opLockStatus")
    public void setOpLockStatus(String opLockStatus) {
        this.opLockStatus = opLockStatus;
    }

    public DealersPOSTBody withOpLockStatus(String opLockStatus) {
        this.opLockStatus = opLockStatus;
        return this;
    }

    /**
     * TAX_CUST_CODE
     * 
     * @return
     *     The taxCustCode
     */
    @JsonProperty("taxCustCode")
    public String getTaxCustCode() {
        return taxCustCode;
    }

    /**
     * TAX_CUST_CODE
     * 
     * @param taxCustCode
     *     The taxCustCode
     */
    @JsonProperty("taxCustCode")
    public void setTaxCustCode(String taxCustCode) {
        this.taxCustCode = taxCustCode;
    }

    public DealersPOSTBody withTaxCustCode(String taxCustCode) {
        this.taxCustCode = taxCustCode;
        return this;
    }

    /**
     * 
     * @return
     *     The gdealerPersonalInfo
     */
    @JsonProperty("gdealerPersonalInfo")
    public GdealerPersonalInfo getGdealerPersonalInfo() {
        return gdealerPersonalInfo;
    }

    /**
     * 
     * @param gdealerPersonalInfo
     *     The gdealerPersonalInfo
     */
    @JsonProperty("gdealerPersonalInfo")
    public void setGdealerPersonalInfo(GdealerPersonalInfo gdealerPersonalInfo) {
        this.gdealerPersonalInfo = gdealerPersonalInfo;
    }

    public DealersPOSTBody withGdealerPersonalInfo(GdealerPersonalInfo gdealerPersonalInfo) {
        this.gdealerPersonalInfo = gdealerPersonalInfo;
        return this;
    }

    /**
     * 
     * @return
     *     The gdealerSpouseInfo
     */
    @JsonProperty("gdealerSpouseInfo")
    public GdealerSpouseInfo getGdealerSpouseInfo() {
        return gdealerSpouseInfo;
    }

    /**
     * 
     * @param gdealerSpouseInfo
     *     The gdealerSpouseInfo
     */
    @JsonProperty("gdealerSpouseInfo")
    public void setGdealerSpouseInfo(GdealerSpouseInfo gdealerSpouseInfo) {
        this.gdealerSpouseInfo = gdealerSpouseInfo;
    }

    public DealersPOSTBody withGdealerSpouseInfo(GdealerSpouseInfo gdealerSpouseInfo) {
        this.gdealerSpouseInfo = gdealerSpouseInfo;
        return this;
    }

    /**
     * 
     * @return
     *     The gdealerTypeStatuses
     */
    @JsonProperty("gdealerTypeStatuses")
    public List<GdealerTypeStatus> getGdealerTypeStatuses() {
        return gdealerTypeStatuses;
    }

    /**
     * 
     * @param gdealerTypeStatuses
     *     The gdealerTypeStatuses
     */
    @JsonProperty("gdealerTypeStatuses")
    public void setGdealerTypeStatuses(List<GdealerTypeStatus> gdealerTypeStatuses) {
        this.gdealerTypeStatuses = gdealerTypeStatuses;
    }

    public DealersPOSTBody withGdealerTypeStatuses(List<GdealerTypeStatus> gdealerTypeStatuses) {
        this.gdealerTypeStatuses = gdealerTypeStatuses;
        return this;
    }

    /**
     * 
     * 
     * @return
     *     The adviseNo
     */
    @JsonProperty("adviseNo")
    public String getAdviseNo() {
        return adviseNo;
    }

    /**
     * 
     * 
     * @param adviseNo
     *     The adviseNo
     */
    @JsonProperty("adviseNo")
    public void setAdviseNo(String adviseNo) {
        this.adviseNo = adviseNo;
    }

    public DealersPOSTBody withAdviseNo(String adviseNo) {
        this.adviseNo = adviseNo;
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

    public DealersPOSTBody withId(Long id) {
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

    public DealersPOSTBody withCreateBy(String createBy) {
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

    public DealersPOSTBody withLastUpdateBy(String lastUpdateBy) {
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

    public DealersPOSTBody withLastUpdateTime(String lastUpdateTime) {
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

    public DealersPOSTBody withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(dealerNo).append(dealerType).append(dealerSubType).append(dealerPostCode).append(dealerName).append(sponsorNo).append(registerSpouse).append(typeEffectiveDate).append(typeInactiveDate).append(postEffectiveDate).append(postInactiveDate).append(saleOrgCode).append(saleBranchNo).append(saleZoneNo).append(appPeriod).append(appFirstSoNo).append(appSpPeriod).append(appForInternational).append(sex).append(englishName).append(comments).append(synLockStatus).append(opLockStatus).append(taxCustCode).append(gdealerPersonalInfo).append(gdealerSpouseInfo).append(gdealerTypeStatuses).append(adviseNo).append(id).append(createBy).append(lastUpdateBy).append(lastUpdateTime).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof DealersPOSTBody) == false) {
            return false;
        }
        DealersPOSTBody rhs = ((DealersPOSTBody) other);
        return new EqualsBuilder().append(dealerNo, rhs.dealerNo).append(dealerType, rhs.dealerType).append(dealerSubType, rhs.dealerSubType).append(dealerPostCode, rhs.dealerPostCode).append(dealerName, rhs.dealerName).append(sponsorNo, rhs.sponsorNo).append(registerSpouse, rhs.registerSpouse).append(typeEffectiveDate, rhs.typeEffectiveDate).append(typeInactiveDate, rhs.typeInactiveDate).append(postEffectiveDate, rhs.postEffectiveDate).append(postInactiveDate, rhs.postInactiveDate).append(saleOrgCode, rhs.saleOrgCode).append(saleBranchNo, rhs.saleBranchNo).append(saleZoneNo, rhs.saleZoneNo).append(appPeriod, rhs.appPeriod).append(appFirstSoNo, rhs.appFirstSoNo).append(appSpPeriod, rhs.appSpPeriod).append(appForInternational, rhs.appForInternational).append(sex, rhs.sex).append(englishName, rhs.englishName).append(comments, rhs.comments).append(synLockStatus, rhs.synLockStatus).append(opLockStatus, rhs.opLockStatus).append(taxCustCode, rhs.taxCustCode).append(gdealerPersonalInfo, rhs.gdealerPersonalInfo).append(gdealerSpouseInfo, rhs.gdealerSpouseInfo).append(gdealerTypeStatuses, rhs.gdealerTypeStatuses).append(adviseNo, rhs.adviseNo).append(id, rhs.id).append(createBy, rhs.createBy).append(lastUpdateBy, rhs.lastUpdateBy).append(lastUpdateTime, rhs.lastUpdateTime).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
