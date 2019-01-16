
package com.lkkhpg.dsis.integration.gds.resource.dealers.dealerNo.model;

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
    "dealerNo"
})
public class DealerNoPUTResponse {

    /**
     * 卡号
     * 
     */
    @JsonProperty("dealerNo")
    private String dealerNo;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public DealerNoPUTResponse() {
    }

    /**
     * 
     * @param dealerNo
     */
    public DealerNoPUTResponse(String dealerNo) {
        this.dealerNo = dealerNo;
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

    public DealerNoPUTResponse withDealerNo(String dealerNo) {
        this.dealerNo = dealerNo;
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

    public DealerNoPUTResponse withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(dealerNo).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof DealerNoPUTResponse) == false) {
            return false;
        }
        DealerNoPUTResponse rhs = ((DealerNoPUTResponse) other);
        return new EqualsBuilder().append(dealerNo, rhs.dealerNo).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
