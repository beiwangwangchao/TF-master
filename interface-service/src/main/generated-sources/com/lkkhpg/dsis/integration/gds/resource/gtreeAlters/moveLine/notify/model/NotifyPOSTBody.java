
package com.lkkhpg.dsis.integration.gds.resource.gtreeAlters.moveLine.notify.model;

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
    "alterNo",
    "tranDealerNo"
})
public class NotifyPOSTBody {

    @JsonProperty("alterNo")
    private String alterNo;
    @JsonProperty("tranDealerNo")
    private String tranDealerNo;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public NotifyPOSTBody() {
    }

    /**
     * 
     * @param tranDealerNo
     * @param alterNo
     */
    public NotifyPOSTBody(String alterNo, String tranDealerNo) {
        this.alterNo = alterNo;
        this.tranDealerNo = tranDealerNo;
    }

    /**
     * 
     * @return
     *     The alterNo
     */
    @JsonProperty("alterNo")
    public String getAlterNo() {
        return alterNo;
    }

    /**
     * 
     * @param alterNo
     *     The alterNo
     */
    @JsonProperty("alterNo")
    public void setAlterNo(String alterNo) {
        this.alterNo = alterNo;
    }

    public NotifyPOSTBody withAlterNo(String alterNo) {
        this.alterNo = alterNo;
        return this;
    }

    /**
     * 
     * @return
     *     The tranDealerNo
     */
    @JsonProperty("tranDealerNo")
    public String getTranDealerNo() {
        return tranDealerNo;
    }

    /**
     * 
     * @param tranDealerNo
     *     The tranDealerNo
     */
    @JsonProperty("tranDealerNo")
    public void setTranDealerNo(String tranDealerNo) {
        this.tranDealerNo = tranDealerNo;
    }

    public NotifyPOSTBody withTranDealerNo(String tranDealerNo) {
        this.tranDealerNo = tranDealerNo;
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

    public NotifyPOSTBody withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(alterNo).append(tranDealerNo).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof NotifyPOSTBody) == false) {
            return false;
        }
        NotifyPOSTBody rhs = ((NotifyPOSTBody) other);
        return new EqualsBuilder().append(alterNo, rhs.alterNo).append(tranDealerNo, rhs.tranDealerNo).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
