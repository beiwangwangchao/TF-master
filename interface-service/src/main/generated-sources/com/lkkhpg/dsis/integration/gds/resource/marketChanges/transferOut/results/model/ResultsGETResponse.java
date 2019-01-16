
package com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferOut.results.model;

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
    "id",
    "dealerNo",
    "oldSaleOrgCode",
    "newSaleOrgCode",
    "newDealerType",
    "newDealerSubType",
    "appFullName",
    "appLastName",
    "appFirstName",
    "appCertificateType",
    "appCertificateNo"
})
public class ResultsGETResponse {

    /**
     * 記錄 ID
     * 
     */
    @JsonProperty("id")
    private String id;
    /**
     * 卡號
     * 
     */
    @JsonProperty("dealerNo")
    private String dealerNo;
    /**
     * 原國家/地區
     * 
     */
    @JsonProperty("oldSaleOrgCode")
    private String oldSaleOrgCode;
    /**
     * 新國家/地區
     * 
     */
    @JsonProperty("newSaleOrgCode")
    private String newSaleOrgCode;
    /**
     * 原身份類型
     * 
     */
    @JsonProperty("newDealerType")
    private String newDealerType;
    /**
     * 新身份類型
     * 
     */
    @JsonProperty("newDealerSubType")
    private String newDealerSubType;
    /**
     * 姓名
     * 
     */
    @JsonProperty("appFullName")
    private String appFullName;
    /**
     * 姓
     * 
     */
    @JsonProperty("appLastName")
    private String appLastName;
    /**
     * 名
     * 
     */
    @JsonProperty("appFirstName")
    private String appFirstName;
    /**
     * 證件類型
     * 
     */
    @JsonProperty("appCertificateType")
    private String appCertificateType;
    /**
     * 證件號
     * 
     */
    @JsonProperty("appCertificateNo")
    private String appCertificateNo;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public ResultsGETResponse() {
    }

    /**
     * 
     * @param oldSaleOrgCode
     * @param appLastName
     * @param appFirstName
     * @param appCertificateNo
     * @param dealerNo
     * @param appCertificateType
     * @param id
     * @param newSaleOrgCode
     * @param newDealerSubType
     * @param appFullName
     * @param newDealerType
     */
    public ResultsGETResponse(String id, String dealerNo, String oldSaleOrgCode, String newSaleOrgCode, String newDealerType, String newDealerSubType, String appFullName, String appLastName, String appFirstName, String appCertificateType, String appCertificateNo) {
        this.id = id;
        this.dealerNo = dealerNo;
        this.oldSaleOrgCode = oldSaleOrgCode;
        this.newSaleOrgCode = newSaleOrgCode;
        this.newDealerType = newDealerType;
        this.newDealerSubType = newDealerSubType;
        this.appFullName = appFullName;
        this.appLastName = appLastName;
        this.appFirstName = appFirstName;
        this.appCertificateType = appCertificateType;
        this.appCertificateNo = appCertificateNo;
    }

    /**
     * 記錄 ID
     * 
     * @return
     *     The id
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * 記錄 ID
     * 
     * @param id
     *     The id
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public ResultsGETResponse withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * 卡號
     * 
     * @return
     *     The dealerNo
     */
    @JsonProperty("dealerNo")
    public String getDealerNo() {
        return dealerNo;
    }

    /**
     * 卡號
     * 
     * @param dealerNo
     *     The dealerNo
     */
    @JsonProperty("dealerNo")
    public void setDealerNo(String dealerNo) {
        this.dealerNo = dealerNo;
    }

    public ResultsGETResponse withDealerNo(String dealerNo) {
        this.dealerNo = dealerNo;
        return this;
    }

    /**
     * 原國家/地區
     * 
     * @return
     *     The oldSaleOrgCode
     */
    @JsonProperty("oldSaleOrgCode")
    public String getOldSaleOrgCode() {
        return oldSaleOrgCode;
    }

    /**
     * 原國家/地區
     * 
     * @param oldSaleOrgCode
     *     The oldSaleOrgCode
     */
    @JsonProperty("oldSaleOrgCode")
    public void setOldSaleOrgCode(String oldSaleOrgCode) {
        this.oldSaleOrgCode = oldSaleOrgCode;
    }

    public ResultsGETResponse withOldSaleOrgCode(String oldSaleOrgCode) {
        this.oldSaleOrgCode = oldSaleOrgCode;
        return this;
    }

    /**
     * 新國家/地區
     * 
     * @return
     *     The newSaleOrgCode
     */
    @JsonProperty("newSaleOrgCode")
    public String getNewSaleOrgCode() {
        return newSaleOrgCode;
    }

    /**
     * 新國家/地區
     * 
     * @param newSaleOrgCode
     *     The newSaleOrgCode
     */
    @JsonProperty("newSaleOrgCode")
    public void setNewSaleOrgCode(String newSaleOrgCode) {
        this.newSaleOrgCode = newSaleOrgCode;
    }

    public ResultsGETResponse withNewSaleOrgCode(String newSaleOrgCode) {
        this.newSaleOrgCode = newSaleOrgCode;
        return this;
    }

    /**
     * 原身份類型
     * 
     * @return
     *     The newDealerType
     */
    @JsonProperty("newDealerType")
    public String getNewDealerType() {
        return newDealerType;
    }

    /**
     * 原身份類型
     * 
     * @param newDealerType
     *     The newDealerType
     */
    @JsonProperty("newDealerType")
    public void setNewDealerType(String newDealerType) {
        this.newDealerType = newDealerType;
    }

    public ResultsGETResponse withNewDealerType(String newDealerType) {
        this.newDealerType = newDealerType;
        return this;
    }

    /**
     * 新身份類型
     * 
     * @return
     *     The newDealerSubType
     */
    @JsonProperty("newDealerSubType")
    public String getNewDealerSubType() {
        return newDealerSubType;
    }

    /**
     * 新身份類型
     * 
     * @param newDealerSubType
     *     The newDealerSubType
     */
    @JsonProperty("newDealerSubType")
    public void setNewDealerSubType(String newDealerSubType) {
        this.newDealerSubType = newDealerSubType;
    }

    public ResultsGETResponse withNewDealerSubType(String newDealerSubType) {
        this.newDealerSubType = newDealerSubType;
        return this;
    }

    /**
     * 姓名
     * 
     * @return
     *     The appFullName
     */
    @JsonProperty("appFullName")
    public String getAppFullName() {
        return appFullName;
    }

    /**
     * 姓名
     * 
     * @param appFullName
     *     The appFullName
     */
    @JsonProperty("appFullName")
    public void setAppFullName(String appFullName) {
        this.appFullName = appFullName;
    }

    public ResultsGETResponse withAppFullName(String appFullName) {
        this.appFullName = appFullName;
        return this;
    }

    /**
     * 姓
     * 
     * @return
     *     The appLastName
     */
    @JsonProperty("appLastName")
    public String getAppLastName() {
        return appLastName;
    }

    /**
     * 姓
     * 
     * @param appLastName
     *     The appLastName
     */
    @JsonProperty("appLastName")
    public void setAppLastName(String appLastName) {
        this.appLastName = appLastName;
    }

    public ResultsGETResponse withAppLastName(String appLastName) {
        this.appLastName = appLastName;
        return this;
    }

    /**
     * 名
     * 
     * @return
     *     The appFirstName
     */
    @JsonProperty("appFirstName")
    public String getAppFirstName() {
        return appFirstName;
    }

    /**
     * 名
     * 
     * @param appFirstName
     *     The appFirstName
     */
    @JsonProperty("appFirstName")
    public void setAppFirstName(String appFirstName) {
        this.appFirstName = appFirstName;
    }

    public ResultsGETResponse withAppFirstName(String appFirstName) {
        this.appFirstName = appFirstName;
        return this;
    }

    /**
     * 證件類型
     * 
     * @return
     *     The appCertificateType
     */
    @JsonProperty("appCertificateType")
    public String getAppCertificateType() {
        return appCertificateType;
    }

    /**
     * 證件類型
     * 
     * @param appCertificateType
     *     The appCertificateType
     */
    @JsonProperty("appCertificateType")
    public void setAppCertificateType(String appCertificateType) {
        this.appCertificateType = appCertificateType;
    }

    public ResultsGETResponse withAppCertificateType(String appCertificateType) {
        this.appCertificateType = appCertificateType;
        return this;
    }

    /**
     * 證件號
     * 
     * @return
     *     The appCertificateNo
     */
    @JsonProperty("appCertificateNo")
    public String getAppCertificateNo() {
        return appCertificateNo;
    }

    /**
     * 證件號
     * 
     * @param appCertificateNo
     *     The appCertificateNo
     */
    @JsonProperty("appCertificateNo")
    public void setAppCertificateNo(String appCertificateNo) {
        this.appCertificateNo = appCertificateNo;
    }

    public ResultsGETResponse withAppCertificateNo(String appCertificateNo) {
        this.appCertificateNo = appCertificateNo;
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

    public ResultsGETResponse withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).append(dealerNo).append(oldSaleOrgCode).append(newSaleOrgCode).append(newDealerType).append(newDealerSubType).append(appFullName).append(appLastName).append(appFirstName).append(appCertificateType).append(appCertificateNo).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ResultsGETResponse) == false) {
            return false;
        }
        ResultsGETResponse rhs = ((ResultsGETResponse) other);
        return new EqualsBuilder().append(id, rhs.id).append(dealerNo, rhs.dealerNo).append(oldSaleOrgCode, rhs.oldSaleOrgCode).append(newSaleOrgCode, rhs.newSaleOrgCode).append(newDealerType, rhs.newDealerType).append(newDealerSubType, rhs.newDealerSubType).append(appFullName, rhs.appFullName).append(appLastName, rhs.appLastName).append(appFirstName, rhs.appFirstName).append(appCertificateType, rhs.appCertificateType).append(appCertificateNo, rhs.appCertificateNo).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
