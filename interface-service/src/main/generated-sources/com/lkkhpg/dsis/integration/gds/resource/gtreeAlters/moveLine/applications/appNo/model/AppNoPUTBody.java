
package com.lkkhpg.dsis.integration.gds.resource.gtreeAlters.moveLine.applications.appNo.model;

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
    "sponsorNo",
    "appDocNo",
    "appMemo"
})
public class AppNoPUTBody {

    /**
     * 卡號
     * 
     */
    @JsonProperty("dealerNo")
    private String dealerNo;
    /**
     * 推薦人卡號
     * 
     */
    @JsonProperty("sponsorNo")
    private String sponsorNo;
    /**
     * 文件號
     * 
     */
    @JsonProperty("appDocNo")
    private String appDocNo;
    /**
     * 申請備註
     * 
     */
    @JsonProperty("appMemo")
    private String appMemo;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public AppNoPUTBody() {
    }

    /**
     * 
     * @param appMemo
     * @param appDocNo
     * @param dealerNo
     * @param sponsorNo
     */
    public AppNoPUTBody(String dealerNo, String sponsorNo, String appDocNo, String appMemo) {
        this.dealerNo = dealerNo;
        this.sponsorNo = sponsorNo;
        this.appDocNo = appDocNo;
        this.appMemo = appMemo;
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

    public AppNoPUTBody withDealerNo(String dealerNo) {
        this.dealerNo = dealerNo;
        return this;
    }

    /**
     * 推薦人卡號
     * 
     * @return
     *     The sponsorNo
     */
    @JsonProperty("sponsorNo")
    public String getSponsorNo() {
        return sponsorNo;
    }

    /**
     * 推薦人卡號
     * 
     * @param sponsorNo
     *     The sponsorNo
     */
    @JsonProperty("sponsorNo")
    public void setSponsorNo(String sponsorNo) {
        this.sponsorNo = sponsorNo;
    }

    public AppNoPUTBody withSponsorNo(String sponsorNo) {
        this.sponsorNo = sponsorNo;
        return this;
    }

    /**
     * 文件號
     * 
     * @return
     *     The appDocNo
     */
    @JsonProperty("appDocNo")
    public String getAppDocNo() {
        return appDocNo;
    }

    /**
     * 文件號
     * 
     * @param appDocNo
     *     The appDocNo
     */
    @JsonProperty("appDocNo")
    public void setAppDocNo(String appDocNo) {
        this.appDocNo = appDocNo;
    }

    public AppNoPUTBody withAppDocNo(String appDocNo) {
        this.appDocNo = appDocNo;
        return this;
    }

    /**
     * 申請備註
     * 
     * @return
     *     The appMemo
     */
    @JsonProperty("appMemo")
    public String getAppMemo() {
        return appMemo;
    }

    /**
     * 申請備註
     * 
     * @param appMemo
     *     The appMemo
     */
    @JsonProperty("appMemo")
    public void setAppMemo(String appMemo) {
        this.appMemo = appMemo;
    }

    public AppNoPUTBody withAppMemo(String appMemo) {
        this.appMemo = appMemo;
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

    public AppNoPUTBody withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(dealerNo).append(sponsorNo).append(appDocNo).append(appMemo).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof AppNoPUTBody) == false) {
            return false;
        }
        AppNoPUTBody rhs = ((AppNoPUTBody) other);
        return new EqualsBuilder().append(dealerNo, rhs.dealerNo).append(sponsorNo, rhs.sponsorNo).append(appDocNo, rhs.appDocNo).append(appMemo, rhs.appMemo).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
