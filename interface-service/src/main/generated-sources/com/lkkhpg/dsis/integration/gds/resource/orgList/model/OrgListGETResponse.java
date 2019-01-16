
package com.lkkhpg.dsis.integration.gds.resource.orgList.model;

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
    "code",
    "value"
})
public class OrgListGETResponse {

    /**
     * code
     * 
     */
    @JsonProperty("code")
    private String code;
    /**
     * value
     * 
     */
    @JsonProperty("value")
    private String value;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public OrgListGETResponse() {
    }

    /**
     * 
     * @param code
     * @param value
     */
    public OrgListGETResponse(String code, String value) {
        this.code = code;
        this.value = value;
    }

    /**
     * code
     * 
     * @return
     *     The code
     */
    @JsonProperty("code")
    public String getCode() {
        return code;
    }

    /**
     * code
     * 
     * @param code
     *     The code
     */
    @JsonProperty("code")
    public void setCode(String code) {
        this.code = code;
    }

    public OrgListGETResponse withCode(String code) {
        this.code = code;
        return this;
    }

    /**
     * value
     * 
     * @return
     *     The value
     */
    @JsonProperty("value")
    public String getValue() {
        return value;
    }

    /**
     * value
     * 
     * @param value
     *     The value
     */
    @JsonProperty("value")
    public void setValue(String value) {
        this.value = value;
    }

    public OrgListGETResponse withValue(String value) {
        this.value = value;
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

    public OrgListGETResponse withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(code).append(value).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof OrgListGETResponse) == false) {
            return false;
        }
        OrgListGETResponse rhs = ((OrgListGETResponse) other);
        return new EqualsBuilder().append(code, rhs.code).append(value, rhs.value).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
