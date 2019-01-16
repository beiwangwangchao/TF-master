
package com.lkkhpg.dsis.integration.gds.resource.orders.batchSave.model;

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
    "soNo",
    "success",
    "message"
})
public class BatchSavePOSTResponse {

    /**
     * soNo
     * 
     */
    @JsonProperty("soNo")
    private String soNo;
    /**
     * 
     * 
     */
    @JsonProperty("success")
    private Boolean success;
    /**
     * 
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
    public BatchSavePOSTResponse() {
    }

    /**
     * 
     * @param success
     * @param soNo
     * @param message
     */
    public BatchSavePOSTResponse(String soNo, Boolean success, String message) {
        this.soNo = soNo;
        this.success = success;
        this.message = message;
    }

    /**
     * soNo
     * 
     * @return
     *     The soNo
     */
    @JsonProperty("soNo")
    public String getSoNo() {
        return soNo;
    }

    /**
     * soNo
     * 
     * @param soNo
     *     The soNo
     */
    @JsonProperty("soNo")
    public void setSoNo(String soNo) {
        this.soNo = soNo;
    }

    public BatchSavePOSTResponse withSoNo(String soNo) {
        this.soNo = soNo;
        return this;
    }

    /**
     * 
     * 
     * @return
     *     The success
     */
    @JsonProperty("success")
    public Boolean getSuccess() {
        return success;
    }

    /**
     * 
     * 
     * @param success
     *     The success
     */
    @JsonProperty("success")
    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public BatchSavePOSTResponse withSuccess(Boolean success) {
        this.success = success;
        return this;
    }

    /**
     * 
     * 
     * @return
     *     The message
     */
    @JsonProperty("message")
    public String getMessage() {
        return message;
    }

    /**
     * 
     * 
     * @param message
     *     The message
     */
    @JsonProperty("message")
    public void setMessage(String message) {
        this.message = message;
    }

    public BatchSavePOSTResponse withMessage(String message) {
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

    public BatchSavePOSTResponse withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(soNo).append(success).append(message).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof BatchSavePOSTResponse) == false) {
            return false;
        }
        BatchSavePOSTResponse rhs = ((BatchSavePOSTResponse) other);
        return new EqualsBuilder().append(soNo, rhs.soNo).append(success, rhs.success).append(message, rhs.message).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
