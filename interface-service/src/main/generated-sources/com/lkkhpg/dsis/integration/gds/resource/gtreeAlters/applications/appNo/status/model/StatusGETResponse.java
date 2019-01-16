
package com.lkkhpg.dsis.integration.gds.resource.gtreeAlters.applications.appNo.status.model;

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
    "tranStatus",
    "downStatus"
})
public class StatusGETResponse {

    /**
     * tranStatus
     * 
     */
    @JsonProperty("tranStatus")
    private String tranStatus;
    /**
     * tranStatus
     * 
     */
    @JsonProperty("downStatus")
    private String downStatus;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public StatusGETResponse() {
    }

    /**
     * 
     * @param downStatus
     * @param tranStatus
     */
    public StatusGETResponse(String tranStatus, String downStatus) {
        this.tranStatus = tranStatus;
        this.downStatus = downStatus;
    }

    /**
     * tranStatus
     * 
     * @return
     *     The tranStatus
     */
    @JsonProperty("tranStatus")
    public String getTranStatus() {
        return tranStatus;
    }

    /**
     * tranStatus
     * 
     * @param tranStatus
     *     The tranStatus
     */
    @JsonProperty("tranStatus")
    public void setTranStatus(String tranStatus) {
        this.tranStatus = tranStatus;
    }

    public StatusGETResponse withTranStatus(String tranStatus) {
        this.tranStatus = tranStatus;
        return this;
    }

    /**
     * tranStatus
     * 
     * @return
     *     The downStatus
     */
    @JsonProperty("downStatus")
    public String getDownStatus() {
        return downStatus;
    }

    /**
     * tranStatus
     * 
     * @param downStatus
     *     The downStatus
     */
    @JsonProperty("downStatus")
    public void setDownStatus(String downStatus) {
        this.downStatus = downStatus;
    }

    public StatusGETResponse withDownStatus(String downStatus) {
        this.downStatus = downStatus;
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

    public StatusGETResponse withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(tranStatus).append(downStatus).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof StatusGETResponse) == false) {
            return false;
        }
        StatusGETResponse rhs = ((StatusGETResponse) other);
        return new EqualsBuilder().append(tranStatus, rhs.tranStatus).append(downStatus, rhs.downStatus).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
