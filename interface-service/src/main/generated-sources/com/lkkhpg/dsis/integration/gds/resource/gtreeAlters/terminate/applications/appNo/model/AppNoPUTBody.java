
package com.lkkhpg.dsis.integration.gds.resource.gtreeAlters.terminate.applications.appNo.model;

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
    "appDocNo",
    "appPeriod",
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
     * 文件號
     * 
     */
    @JsonProperty("appDocNo")
    private String appDocNo;
    /**
     * 申請月份
     * 
     */
    @JsonProperty("appPeriod")
    private String appPeriod;
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
     * @param appPeriod
     * @param appMemo
     * @param appDocNo
     * @param dealerNo
     */
    public AppNoPUTBody(String dealerNo, String appDocNo, String appPeriod, String appMemo) {
        this.dealerNo = dealerNo;
        this.appDocNo = appDocNo;
        this.appPeriod = appPeriod;
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
     * 申請月份
     * 
     * @return
     *     The appPeriod
     */
    @JsonProperty("appPeriod")
    public String getAppPeriod() {
        return appPeriod;
    }

    /**
     * 申請月份
     * 
     * @param appPeriod
     *     The appPeriod
     */
    @JsonProperty("appPeriod")
    public void setAppPeriod(String appPeriod) {
        this.appPeriod = appPeriod;
    }

    public AppNoPUTBody withAppPeriod(String appPeriod) {
        this.appPeriod = appPeriod;
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
        return new HashCodeBuilder().append(dealerNo).append(appDocNo).append(appPeriod).append(appMemo).append(additionalProperties).toHashCode();
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
        return new EqualsBuilder().append(dealerNo, rhs.dealerNo).append(appDocNo, rhs.appDocNo).append(appPeriod, rhs.appPeriod).append(appMemo, rhs.appMemo).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
