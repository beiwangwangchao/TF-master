
package com.lkkhpg.dsis.integration.gds.resource.dealers.model;

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
    "appDealerNo",
    "appPeriod",
    "appType",
    "spouseFullName",
    "spouseCertificateNationCode",
    "spouseCertificateType",
    "spouseCertificateNo",
    "spouseSex",
    "spouseRace",
    "spouseMobile",
    "spouseTele",
    "comments",
    "id",
    "createBy",
    "lastUpdateBy",
    "lastUpdateTime"
})
public class GdealerSpouseInfo {

    /**
     * 经销商代码
     * 
     */
    @JsonProperty("dealerNo")
    private String dealerNo;
    /**
     * 申请经销商号
     * 
     */
    @JsonProperty("appDealerNo")
    private String appDealerNo;
    /**
     * 申请月份
     * 
     */
    @JsonProperty("appPeriod")
    private String appPeriod;
    /**
     * 申请类型
     * 
     */
    @JsonProperty("appType")
    private String appType;
    /**
     * 配偶全称
     * 
     */
    @JsonProperty("spouseFullName")
    private String spouseFullName;
    /**
     * 配偶证件国别
     * 
     */
    @JsonProperty("spouseCertificateNationCode")
    private String spouseCertificateNationCode;
    /**
     * 配偶证件类型
     * 
     */
    @JsonProperty("spouseCertificateType")
    private String spouseCertificateType;
    /**
     * 配偶证件号码
     * 
     */
    @JsonProperty("spouseCertificateNo")
    private String spouseCertificateNo;
    /**
     * 配偶性别
     * 
     */
    @JsonProperty("spouseSex")
    private String spouseSex;
    /**
     * 配偶民族
     * 
     */
    @JsonProperty("spouseRace")
    private String spouseRace;
    /**
     * 配偶电话
     * 
     */
    @JsonProperty("spouseMobile")
    private String spouseMobile;
    /**
     * 配偶电话
     * 
     */
    @JsonProperty("spouseTele")
    private String spouseTele;
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
    public GdealerSpouseInfo() {
    }

    /**
     * 
     * @param spouseCertificateNationCode
     * @param spouseTele
     * @param spouseFullName
     * @param comments
     * @param dealerNo
     * @param spouseSex
     * @param appPeriod
     * @param spouseCertificateType
     * @param spouseMobile
     * @param createBy
     * @param appType
     * @param spouseCertificateNo
     * @param spouseRace
     * @param id
     * @param appDealerNo
     * @param lastUpdateBy
     * @param lastUpdateTime
     */
    public GdealerSpouseInfo(String dealerNo, String appDealerNo, String appPeriod, String appType, String spouseFullName, String spouseCertificateNationCode, String spouseCertificateType, String spouseCertificateNo, String spouseSex, String spouseRace, String spouseMobile, String spouseTele, String comments, Long id, String createBy, String lastUpdateBy, String lastUpdateTime) {
        this.dealerNo = dealerNo;
        this.appDealerNo = appDealerNo;
        this.appPeriod = appPeriod;
        this.appType = appType;
        this.spouseFullName = spouseFullName;
        this.spouseCertificateNationCode = spouseCertificateNationCode;
        this.spouseCertificateType = spouseCertificateType;
        this.spouseCertificateNo = spouseCertificateNo;
        this.spouseSex = spouseSex;
        this.spouseRace = spouseRace;
        this.spouseMobile = spouseMobile;
        this.spouseTele = spouseTele;
        this.comments = comments;
        this.id = id;
        this.createBy = createBy;
        this.lastUpdateBy = lastUpdateBy;
        this.lastUpdateTime = lastUpdateTime;
    }

    /**
     * 经销商代码
     * 
     * @return
     *     The dealerNo
     */
    @JsonProperty("dealerNo")
    public String getDealerNo() {
        return dealerNo;
    }

    /**
     * 经销商代码
     * 
     * @param dealerNo
     *     The dealerNo
     */
    @JsonProperty("dealerNo")
    public void setDealerNo(String dealerNo) {
        this.dealerNo = dealerNo;
    }

    public GdealerSpouseInfo withDealerNo(String dealerNo) {
        this.dealerNo = dealerNo;
        return this;
    }

    /**
     * 申请经销商号
     * 
     * @return
     *     The appDealerNo
     */
    @JsonProperty("appDealerNo")
    public String getAppDealerNo() {
        return appDealerNo;
    }

    /**
     * 申请经销商号
     * 
     * @param appDealerNo
     *     The appDealerNo
     */
    @JsonProperty("appDealerNo")
    public void setAppDealerNo(String appDealerNo) {
        this.appDealerNo = appDealerNo;
    }

    public GdealerSpouseInfo withAppDealerNo(String appDealerNo) {
        this.appDealerNo = appDealerNo;
        return this;
    }

    /**
     * 申请月份
     * 
     * @return
     *     The appPeriod
     */
    @JsonProperty("appPeriod")
    public String getAppPeriod() {
        return appPeriod;
    }

    /**
     * 申请月份
     * 
     * @param appPeriod
     *     The appPeriod
     */
    @JsonProperty("appPeriod")
    public void setAppPeriod(String appPeriod) {
        this.appPeriod = appPeriod;
    }

    public GdealerSpouseInfo withAppPeriod(String appPeriod) {
        this.appPeriod = appPeriod;
        return this;
    }

    /**
     * 申请类型
     * 
     * @return
     *     The appType
     */
    @JsonProperty("appType")
    public String getAppType() {
        return appType;
    }

    /**
     * 申请类型
     * 
     * @param appType
     *     The appType
     */
    @JsonProperty("appType")
    public void setAppType(String appType) {
        this.appType = appType;
    }

    public GdealerSpouseInfo withAppType(String appType) {
        this.appType = appType;
        return this;
    }

    /**
     * 配偶全称
     * 
     * @return
     *     The spouseFullName
     */
    @JsonProperty("spouseFullName")
    public String getSpouseFullName() {
        return spouseFullName;
    }

    /**
     * 配偶全称
     * 
     * @param spouseFullName
     *     The spouseFullName
     */
    @JsonProperty("spouseFullName")
    public void setSpouseFullName(String spouseFullName) {
        this.spouseFullName = spouseFullName;
    }

    public GdealerSpouseInfo withSpouseFullName(String spouseFullName) {
        this.spouseFullName = spouseFullName;
        return this;
    }

    /**
     * 配偶证件国别
     * 
     * @return
     *     The spouseCertificateNationCode
     */
    @JsonProperty("spouseCertificateNationCode")
    public String getSpouseCertificateNationCode() {
        return spouseCertificateNationCode;
    }

    /**
     * 配偶证件国别
     * 
     * @param spouseCertificateNationCode
     *     The spouseCertificateNationCode
     */
    @JsonProperty("spouseCertificateNationCode")
    public void setSpouseCertificateNationCode(String spouseCertificateNationCode) {
        this.spouseCertificateNationCode = spouseCertificateNationCode;
    }

    public GdealerSpouseInfo withSpouseCertificateNationCode(String spouseCertificateNationCode) {
        this.spouseCertificateNationCode = spouseCertificateNationCode;
        return this;
    }

    /**
     * 配偶证件类型
     * 
     * @return
     *     The spouseCertificateType
     */
    @JsonProperty("spouseCertificateType")
    public String getSpouseCertificateType() {
        return spouseCertificateType;
    }

    /**
     * 配偶证件类型
     * 
     * @param spouseCertificateType
     *     The spouseCertificateType
     */
    @JsonProperty("spouseCertificateType")
    public void setSpouseCertificateType(String spouseCertificateType) {
        this.spouseCertificateType = spouseCertificateType;
    }

    public GdealerSpouseInfo withSpouseCertificateType(String spouseCertificateType) {
        this.spouseCertificateType = spouseCertificateType;
        return this;
    }

    /**
     * 配偶证件号码
     * 
     * @return
     *     The spouseCertificateNo
     */
    @JsonProperty("spouseCertificateNo")
    public String getSpouseCertificateNo() {
        return spouseCertificateNo;
    }

    /**
     * 配偶证件号码
     * 
     * @param spouseCertificateNo
     *     The spouseCertificateNo
     */
    @JsonProperty("spouseCertificateNo")
    public void setSpouseCertificateNo(String spouseCertificateNo) {
        this.spouseCertificateNo = spouseCertificateNo;
    }

    public GdealerSpouseInfo withSpouseCertificateNo(String spouseCertificateNo) {
        this.spouseCertificateNo = spouseCertificateNo;
        return this;
    }

    /**
     * 配偶性别
     * 
     * @return
     *     The spouseSex
     */
    @JsonProperty("spouseSex")
    public String getSpouseSex() {
        return spouseSex;
    }

    /**
     * 配偶性别
     * 
     * @param spouseSex
     *     The spouseSex
     */
    @JsonProperty("spouseSex")
    public void setSpouseSex(String spouseSex) {
        this.spouseSex = spouseSex;
    }

    public GdealerSpouseInfo withSpouseSex(String spouseSex) {
        this.spouseSex = spouseSex;
        return this;
    }

    /**
     * 配偶民族
     * 
     * @return
     *     The spouseRace
     */
    @JsonProperty("spouseRace")
    public String getSpouseRace() {
        return spouseRace;
    }

    /**
     * 配偶民族
     * 
     * @param spouseRace
     *     The spouseRace
     */
    @JsonProperty("spouseRace")
    public void setSpouseRace(String spouseRace) {
        this.spouseRace = spouseRace;
    }

    public GdealerSpouseInfo withSpouseRace(String spouseRace) {
        this.spouseRace = spouseRace;
        return this;
    }

    /**
     * 配偶电话
     * 
     * @return
     *     The spouseMobile
     */
    @JsonProperty("spouseMobile")
    public String getSpouseMobile() {
        return spouseMobile;
    }

    /**
     * 配偶电话
     * 
     * @param spouseMobile
     *     The spouseMobile
     */
    @JsonProperty("spouseMobile")
    public void setSpouseMobile(String spouseMobile) {
        this.spouseMobile = spouseMobile;
    }

    public GdealerSpouseInfo withSpouseMobile(String spouseMobile) {
        this.spouseMobile = spouseMobile;
        return this;
    }

    /**
     * 配偶电话
     * 
     * @return
     *     The spouseTele
     */
    @JsonProperty("spouseTele")
    public String getSpouseTele() {
        return spouseTele;
    }

    /**
     * 配偶电话
     * 
     * @param spouseTele
     *     The spouseTele
     */
    @JsonProperty("spouseTele")
    public void setSpouseTele(String spouseTele) {
        this.spouseTele = spouseTele;
    }

    public GdealerSpouseInfo withSpouseTele(String spouseTele) {
        this.spouseTele = spouseTele;
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

    public GdealerSpouseInfo withComments(String comments) {
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

    public GdealerSpouseInfo withId(Long id) {
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

    public GdealerSpouseInfo withCreateBy(String createBy) {
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

    public GdealerSpouseInfo withLastUpdateBy(String lastUpdateBy) {
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

    public GdealerSpouseInfo withLastUpdateTime(String lastUpdateTime) {
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

    public GdealerSpouseInfo withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(dealerNo).append(appDealerNo).append(appPeriod).append(appType).append(spouseFullName).append(spouseCertificateNationCode).append(spouseCertificateType).append(spouseCertificateNo).append(spouseSex).append(spouseRace).append(spouseMobile).append(spouseTele).append(comments).append(id).append(createBy).append(lastUpdateBy).append(lastUpdateTime).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof GdealerSpouseInfo) == false) {
            return false;
        }
        GdealerSpouseInfo rhs = ((GdealerSpouseInfo) other);
        return new EqualsBuilder().append(dealerNo, rhs.dealerNo).append(appDealerNo, rhs.appDealerNo).append(appPeriod, rhs.appPeriod).append(appType, rhs.appType).append(spouseFullName, rhs.spouseFullName).append(spouseCertificateNationCode, rhs.spouseCertificateNationCode).append(spouseCertificateType, rhs.spouseCertificateType).append(spouseCertificateNo, rhs.spouseCertificateNo).append(spouseSex, rhs.spouseSex).append(spouseRace, rhs.spouseRace).append(spouseMobile, rhs.spouseMobile).append(spouseTele, rhs.spouseTele).append(comments, rhs.comments).append(id, rhs.id).append(createBy, rhs.createBy).append(lastUpdateBy, rhs.lastUpdateBy).append(lastUpdateTime, rhs.lastUpdateTime).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
