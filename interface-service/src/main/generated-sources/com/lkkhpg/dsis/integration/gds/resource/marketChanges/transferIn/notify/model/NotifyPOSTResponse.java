
package com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferIn.notify.model;

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
    "success",
    "id",
    "message"
})
public class NotifyPOSTResponse {

    /**
     * 介面調用是否成功標誌
     * 
     */
    @JsonProperty("success")
    private Boolean success;
    /**
     * id
     * 
     */
    @JsonProperty("id")
    private String id;
    /**
     * message
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
    public NotifyPOSTResponse() {
    }

    /**
     * 
     * @param success
     * @param id
     * @param message
     */
    public NotifyPOSTResponse(Boolean success, String id, String message) {
        this.success = success;
        this.id = id;
        this.message = message;
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

    public NotifyPOSTResponse withSuccess(Boolean success) {
        this.success = success;
        return this;
    }

    /**
     * id
     * 
     * @return
     *     The id
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * id
     * 
     * @param id
     *     The id
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public NotifyPOSTResponse withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * message
     * 
     * @return
     *     The message
     */
    @JsonProperty("message")
    public String getMessage() {
        return message;
    }

    /**
     * message
     * 
     * @param message
     *     The message
     */
    @JsonProperty("message")
    public void setMessage(String message) {
        this.message = message;
    }

    public NotifyPOSTResponse withMessage(String message) {
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

    public NotifyPOSTResponse withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(success).append(id).append(message).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof NotifyPOSTResponse) == false) {
            return false;
        }
        NotifyPOSTResponse rhs = ((NotifyPOSTResponse) other);
        return new EqualsBuilder().append(success, rhs.success).append(id, rhs.id).append(message, rhs.message).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
