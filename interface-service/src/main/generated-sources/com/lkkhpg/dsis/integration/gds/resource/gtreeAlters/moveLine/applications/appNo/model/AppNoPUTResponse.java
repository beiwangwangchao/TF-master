
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
    "appNo"
})
public class AppNoPUTResponse {

    /**
     * 申請單號
     * 
     */
    @JsonProperty("appNo")
    private String appNo;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public AppNoPUTResponse() {
    }

    /**
     * 
     * @param appNo
     */
    public AppNoPUTResponse(String appNo) {
        this.appNo = appNo;
    }

    /**
     * 申請單號
     * 
     * @return
     *     The appNo
     */
    @JsonProperty("appNo")
    public String getAppNo() {
        return appNo;
    }

    /**
     * 申請單號
     * 
     * @param appNo
     *     The appNo
     */
    @JsonProperty("appNo")
    public void setAppNo(String appNo) {
        this.appNo = appNo;
    }

    public AppNoPUTResponse withAppNo(String appNo) {
        this.appNo = appNo;
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

    public AppNoPUTResponse withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(appNo).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof AppNoPUTResponse) == false) {
            return false;
        }
        AppNoPUTResponse rhs = ((AppNoPUTResponse) other);
        return new EqualsBuilder().append(appNo, rhs.appNo).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
