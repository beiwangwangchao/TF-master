
package com.lkkhpg.dsis.integration.gds.resource.dealers.batchDelete.model;

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
    "success",
    "message"
})
public class BatchDeletePOSTResponse {

    /**
     * 卡號
     * 
     */
    @JsonProperty("dealerNo")
    private String dealerNo;
    /**
     * 介面調用是否成功標誌
     * 
     */
    @JsonProperty("success")
    private Boolean success;
    /**
     * 信息
     * 
     */
    @JsonProperty("message")
    private String message;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public BatchDeletePOSTResponse() {
    }

    /**
     * 
     * @param success
     * @param dealerNo
     * @param message
     */
    public BatchDeletePOSTResponse(String dealerNo, Boolean success, String message) {
        this.dealerNo = dealerNo;
        this.success = success;
        this.message = message;
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

    public BatchDeletePOSTResponse withDealerNo(String dealerNo) {
        this.dealerNo = dealerNo;
        return this;
    }

    /**
     * 介面調用是否成功標誌
     * 
     * @return
     *     The success
     */
    @JsonProperty("success")
    public Boolean getSuccess() {
        return success;
    }

    /**
     * 介面調用是否成功標誌
     * 
     * @param success
     *     The success
     */
    @JsonProperty("success")
    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public BatchDeletePOSTResponse withSuccess(Boolean success) {
        this.success = success;
        return this;
    }

    /**
     * 信息
     * 
     * @return
     *     The message
     */
    @JsonProperty("message")
    public String getMessage() {
        return message;
    }

    /**
     * 信息
     * 
     * @param message
     *     The message
     */
    @JsonProperty("message")
    public void setMessage(String message) {
        this.message = message;
    }

    public BatchDeletePOSTResponse withMessage(String message) {
        this.message = message;
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

    public BatchDeletePOSTResponse withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(dealerNo).append(success).append(message).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof BatchDeletePOSTResponse) == false) {
            return false;
        }
        BatchDeletePOSTResponse rhs = ((BatchDeletePOSTResponse) other);
        return new EqualsBuilder().append(dealerNo, rhs.dealerNo).append(success, rhs.success).append(message, rhs.message).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
