
package com.lkkhpg.dsis.integration.gds.resource.gtreeAlters.terminate.results.model;

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
    "alterNo",
    "alterDealerNo",
    "alterMode",
    "alterPeriod",
    "alterOrgCode",
    "refFromSponsorNo",
    "refToSponsorNo",
    "refSoNo",
    "comments",
    "lastUpdatedTime",
    "lastUpdatedBy",
    "appNo",
    "gtreeAlterDetails"
})
public class ResultsGETResponse {

    /**
     * 變更編號
     * 
     */
    @JsonProperty("alterNo")
    private String alterNo;
    /**
     * 變更卡號
     * 
     */
    @JsonProperty("alterDealerNo")
    private String alterDealerNo;
    /**
     * 變更模式
     * 
     */
    @JsonProperty("alterMode")
    private String alterMode;
    /**
     * 變更月份
     * 
     */
    @JsonProperty("alterPeriod")
    private String alterPeriod;
    /**
     * 變更機構
     * 
     */
    @JsonProperty("alterOrgCode")
    private String alterOrgCode;
    /**
     * 參考源推薦人號
     * 
     */
    @JsonProperty("refFromSponsorNo")
    private String refFromSponsorNo;
    /**
     * 參考目標推薦人號
     * 
     */
    @JsonProperty("refToSponsorNo")
    private String refToSponsorNo;
    /**
     * 參考回單號 (NULL)
     * 
     */
    @JsonProperty("refSoNo")
    private String refSoNo;
    /**
     * 備註 (NULL)
     * 
     */
    @JsonProperty("comments")
    private String comments;
    /**
     * 最後更新時間
     * 
     */
    @JsonProperty("lastUpdatedTime")
    private String lastUpdatedTime;
    /**
     * 最後更新用戶
     * 
     */
    @JsonProperty("lastUpdatedBy")
    private String lastUpdatedBy;
    /**
     * 申請號
     * 
     */
    @JsonProperty("appNo")
    private String appNo;
    @JsonProperty("gtreeAlterDetails")
    private List<GtreeAlterDetail> gtreeAlterDetails = new ArrayList<GtreeAlterDetail>();
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
     * @param lastUpdatedBy
     * @param alterOrgCode
     * @param comments
     * @param alterDealerNo
     * @param refToSponsorNo
     * @param appNo
     * @param alterNo
     * @param refSoNo
     * @param refFromSponsorNo
     * @param lastUpdatedTime
     * @param alterPeriod
     * @param gtreeAlterDetails
     * @param alterMode
     */
    public ResultsGETResponse(String alterNo, String alterDealerNo, String alterMode, String alterPeriod, String alterOrgCode, String refFromSponsorNo, String refToSponsorNo, String refSoNo, String comments, String lastUpdatedTime, String lastUpdatedBy, String appNo, List<GtreeAlterDetail> gtreeAlterDetails) {
        this.alterNo = alterNo;
        this.alterDealerNo = alterDealerNo;
        this.alterMode = alterMode;
        this.alterPeriod = alterPeriod;
        this.alterOrgCode = alterOrgCode;
        this.refFromSponsorNo = refFromSponsorNo;
        this.refToSponsorNo = refToSponsorNo;
        this.refSoNo = refSoNo;
        this.comments = comments;
        this.lastUpdatedTime = lastUpdatedTime;
        this.lastUpdatedBy = lastUpdatedBy;
        this.appNo = appNo;
        this.gtreeAlterDetails = gtreeAlterDetails;
    }

    /**
     * 變更編號
     * 
     * @return
     *     The alterNo
     */
    @JsonProperty("alterNo")
    public String getAlterNo() {
        return alterNo;
    }

    /**
     * 變更編號
     * 
     * @param alterNo
     *     The alterNo
     */
    @JsonProperty("alterNo")
    public void setAlterNo(String alterNo) {
        this.alterNo = alterNo;
    }

    public ResultsGETResponse withAlterNo(String alterNo) {
        this.alterNo = alterNo;
        return this;
    }

    /**
     * 變更卡號
     * 
     * @return
     *     The alterDealerNo
     */
    @JsonProperty("alterDealerNo")
    public String getAlterDealerNo() {
        return alterDealerNo;
    }

    /**
     * 變更卡號
     * 
     * @param alterDealerNo
     *     The alterDealerNo
     */
    @JsonProperty("alterDealerNo")
    public void setAlterDealerNo(String alterDealerNo) {
        this.alterDealerNo = alterDealerNo;
    }

    public ResultsGETResponse withAlterDealerNo(String alterDealerNo) {
        this.alterDealerNo = alterDealerNo;
        return this;
    }

    /**
     * 變更模式
     * 
     * @return
     *     The alterMode
     */
    @JsonProperty("alterMode")
    public String getAlterMode() {
        return alterMode;
    }

    /**
     * 變更模式
     * 
     * @param alterMode
     *     The alterMode
     */
    @JsonProperty("alterMode")
    public void setAlterMode(String alterMode) {
        this.alterMode = alterMode;
    }

    public ResultsGETResponse withAlterMode(String alterMode) {
        this.alterMode = alterMode;
        return this;
    }

    /**
     * 變更月份
     * 
     * @return
     *     The alterPeriod
     */
    @JsonProperty("alterPeriod")
    public String getAlterPeriod() {
        return alterPeriod;
    }

    /**
     * 變更月份
     * 
     * @param alterPeriod
     *     The alterPeriod
     */
    @JsonProperty("alterPeriod")
    public void setAlterPeriod(String alterPeriod) {
        this.alterPeriod = alterPeriod;
    }

    public ResultsGETResponse withAlterPeriod(String alterPeriod) {
        this.alterPeriod = alterPeriod;
        return this;
    }

    /**
     * 變更機構
     * 
     * @return
     *     The alterOrgCode
     */
    @JsonProperty("alterOrgCode")
    public String getAlterOrgCode() {
        return alterOrgCode;
    }

    /**
     * 變更機構
     * 
     * @param alterOrgCode
     *     The alterOrgCode
     */
    @JsonProperty("alterOrgCode")
    public void setAlterOrgCode(String alterOrgCode) {
        this.alterOrgCode = alterOrgCode;
    }

    public ResultsGETResponse withAlterOrgCode(String alterOrgCode) {
        this.alterOrgCode = alterOrgCode;
        return this;
    }

    /**
     * 參考源推薦人號
     * 
     * @return
     *     The refFromSponsorNo
     */
    @JsonProperty("refFromSponsorNo")
    public String getRefFromSponsorNo() {
        return refFromSponsorNo;
    }

    /**
     * 參考源推薦人號
     * 
     * @param refFromSponsorNo
     *     The refFromSponsorNo
     */
    @JsonProperty("refFromSponsorNo")
    public void setRefFromSponsorNo(String refFromSponsorNo) {
        this.refFromSponsorNo = refFromSponsorNo;
    }

    public ResultsGETResponse withRefFromSponsorNo(String refFromSponsorNo) {
        this.refFromSponsorNo = refFromSponsorNo;
        return this;
    }

    /**
     * 參考目標推薦人號
     * 
     * @return
     *     The refToSponsorNo
     */
    @JsonProperty("refToSponsorNo")
    public String getRefToSponsorNo() {
        return refToSponsorNo;
    }

    /**
     * 參考目標推薦人號
     * 
     * @param refToSponsorNo
     *     The refToSponsorNo
     */
    @JsonProperty("refToSponsorNo")
    public void setRefToSponsorNo(String refToSponsorNo) {
        this.refToSponsorNo = refToSponsorNo;
    }

    public ResultsGETResponse withRefToSponsorNo(String refToSponsorNo) {
        this.refToSponsorNo = refToSponsorNo;
        return this;
    }

    /**
     * 參考回單號 (NULL)
     * 
     * @return
     *     The refSoNo
     */
    @JsonProperty("refSoNo")
    public String getRefSoNo() {
        return refSoNo;
    }

    /**
     * 參考回單號 (NULL)
     * 
     * @param refSoNo
     *     The refSoNo
     */
    @JsonProperty("refSoNo")
    public void setRefSoNo(String refSoNo) {
        this.refSoNo = refSoNo;
    }

    public ResultsGETResponse withRefSoNo(String refSoNo) {
        this.refSoNo = refSoNo;
        return this;
    }

    /**
     * 備註 (NULL)
     * 
     * @return
     *     The comments
     */
    @JsonProperty("comments")
    public String getComments() {
        return comments;
    }

    /**
     * 備註 (NULL)
     * 
     * @param comments
     *     The comments
     */
    @JsonProperty("comments")
    public void setComments(String comments) {
        this.comments = comments;
    }

    public ResultsGETResponse withComments(String comments) {
        this.comments = comments;
        return this;
    }

    /**
     * 最後更新時間
     * 
     * @return
     *     The lastUpdatedTime
     */
    @JsonProperty("lastUpdatedTime")
    public String getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    /**
     * 最後更新時間
     * 
     * @param lastUpdatedTime
     *     The lastUpdatedTime
     */
    @JsonProperty("lastUpdatedTime")
    public void setLastUpdatedTime(String lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }

    public ResultsGETResponse withLastUpdatedTime(String lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
        return this;
    }

    /**
     * 最後更新用戶
     * 
     * @return
     *     The lastUpdatedBy
     */
    @JsonProperty("lastUpdatedBy")
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * 最後更新用戶
     * 
     * @param lastUpdatedBy
     *     The lastUpdatedBy
     */
    @JsonProperty("lastUpdatedBy")
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public ResultsGETResponse withLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
        return this;
    }

    /**
     * 申請號
     * 
     * @return
     *     The appNo
     */
    @JsonProperty("appNo")
    public String getAppNo() {
        return appNo;
    }

    /**
     * 申請號
     * 
     * @param appNo
     *     The appNo
     */
    @JsonProperty("appNo")
    public void setAppNo(String appNo) {
        this.appNo = appNo;
    }

    public ResultsGETResponse withAppNo(String appNo) {
        this.appNo = appNo;
        return this;
    }

    /**
     * 
     * @return
     *     The gtreeAlterDetails
     */
    @JsonProperty("gtreeAlterDetails")
    public List<GtreeAlterDetail> getGtreeAlterDetails() {
        return gtreeAlterDetails;
    }

    /**
     * 
     * @param gtreeAlterDetails
     *     The gtreeAlterDetails
     */
    @JsonProperty("gtreeAlterDetails")
    public void setGtreeAlterDetails(List<GtreeAlterDetail> gtreeAlterDetails) {
        this.gtreeAlterDetails = gtreeAlterDetails;
    }

    public ResultsGETResponse withGtreeAlterDetails(List<GtreeAlterDetail> gtreeAlterDetails) {
        this.gtreeAlterDetails = gtreeAlterDetails;
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
        return new HashCodeBuilder().append(alterNo).append(alterDealerNo).append(alterMode).append(alterPeriod).append(alterOrgCode).append(refFromSponsorNo).append(refToSponsorNo).append(refSoNo).append(comments).append(lastUpdatedTime).append(lastUpdatedBy).append(appNo).append(gtreeAlterDetails).append(additionalProperties).toHashCode();
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
        return new EqualsBuilder().append(alterNo, rhs.alterNo).append(alterDealerNo, rhs.alterDealerNo).append(alterMode, rhs.alterMode).append(alterPeriod, rhs.alterPeriod).append(alterOrgCode, rhs.alterOrgCode).append(refFromSponsorNo, rhs.refFromSponsorNo).append(refToSponsorNo, rhs.refToSponsorNo).append(refSoNo, rhs.refSoNo).append(comments, rhs.comments).append(lastUpdatedTime, rhs.lastUpdatedTime).append(lastUpdatedBy, rhs.lastUpdatedBy).append(appNo, rhs.appNo).append(gtreeAlterDetails, rhs.gtreeAlterDetails).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
