
package com.lkkhpg.dsis.integration.gds.resource.dealers.dealerNo.sponsorVerify.model;

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
    "dealerType",
    "effectiveDate",
    "inactiveDate",
    "current",
    "comments",
    "id",
    "createBy",
    "lastUpdateBy",
    "lastUpdateTime"
})
public class GdealerTypeStatus {

    /**
     * 卡号
     * 
     */
    @JsonProperty("dealerNo")
    private String dealerNo;
    /**
     * 身份类型
     * 
     */
    @JsonProperty("dealerType")
    private String dealerType;
    /**
     * 开始日期
     * 
     */
    @JsonProperty("effectiveDate")
    private String effectiveDate;
    /**
     * 失效日期
     * 
     */
    @JsonProperty("inactiveDate")
    private String inactiveDate;
    /**
     * 是否当前记录
     * 
     */
    @JsonProperty("current")
    private Boolean current;
    /**
     * 备注
     * 
     */
    @JsonProperty("comments")
    private String comments;
    /**
     * 主键 id
     * 
     */
    @JsonProperty("id")
    private Long id;
    /**
     * 创建人
     * 
     */
    @JsonProperty("createBy")
    private String createBy;
    /**
     * 最后更新人
     * 
     */
    @JsonProperty("lastUpdateBy")
    private String lastUpdateBy;
    /**
     * 最后更新时间
     * 
     */
    @JsonProperty("lastUpdateTime")
    private String lastUpdateTime;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public GdealerTypeStatus() {
    }

    /**
     * 
     * @param current
     * @param createBy
     * @param comments
     * @param inactiveDate
     * @param dealerNo
     * @param dealerType
     * @param id
     * @param effectiveDate
     * @param lastUpdateBy
     * @param lastUpdateTime
     */
    public GdealerTypeStatus(String dealerNo, String dealerType, String effectiveDate, String inactiveDate, Boolean current, String comments, Long id, String createBy, String lastUpdateBy, String lastUpdateTime) {
        this.dealerNo = dealerNo;
        this.dealerType = dealerType;
        this.effectiveDate = effectiveDate;
        this.inactiveDate = inactiveDate;
        this.current = current;
        this.comments = comments;
        this.id = id;
        this.createBy = createBy;
        this.lastUpdateBy = lastUpdateBy;
        this.lastUpdateTime = lastUpdateTime;
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

    public GdealerTypeStatus withDealerNo(String dealerNo) {
        this.dealerNo = dealerNo;
        return this;
    }

    /**
     * 身份类型
     * 
     * @return
     *     The dealerType
     */
    @JsonProperty("dealerType")
    public String getDealerType() {
        return dealerType;
    }

    /**
     * 身份类型
     * 
     * @param dealerType
     *     The dealerType
     */
    @JsonProperty("dealerType")
    public void setDealerType(String dealerType) {
        this.dealerType = dealerType;
    }

    public GdealerTypeStatus withDealerType(String dealerType) {
        this.dealerType = dealerType;
        return this;
    }

    /**
     * 开始日期
     * 
     * @return
     *     The effectiveDate
     */
    @JsonProperty("effectiveDate")
    public String getEffectiveDate() {
        return effectiveDate;
    }

    /**
     * 开始日期
     * 
     * @param effectiveDate
     *     The effectiveDate
     */
    @JsonProperty("effectiveDate")
    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public GdealerTypeStatus withEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
        return this;
    }

    /**
     * 失效日期
     * 
     * @return
     *     The inactiveDate
     */
    @JsonProperty("inactiveDate")
    public String getInactiveDate() {
        return inactiveDate;
    }

    /**
     * 失效日期
     * 
     * @param inactiveDate
     *     The inactiveDate
     */
    @JsonProperty("inactiveDate")
    public void setInactiveDate(String inactiveDate) {
        this.inactiveDate = inactiveDate;
    }

    public GdealerTypeStatus withInactiveDate(String inactiveDate) {
        this.inactiveDate = inactiveDate;
        return this;
    }

    /**
     * 是否当前记录
     * 
     * @return
     *     The current
     */
    @JsonProperty("current")
    public Boolean getCurrent() {
        return current;
    }

    /**
     * 是否当前记录
     * 
     * @param current
     *     The current
     */
    @JsonProperty("current")
    public void setCurrent(Boolean current) {
        this.current = current;
    }

    public GdealerTypeStatus withCurrent(Boolean current) {
        this.current = current;
        return this;
    }

    /**
     * 备注
     * 
     * @return
     *     The comments
     */
    @JsonProperty("comments")
    public String getComments() {
        return comments;
    }

    /**
     * 备注
     * 
     * @param comments
     *     The comments
     */
    @JsonProperty("comments")
    public void setComments(String comments) {
        this.comments = comments;
    }

    public GdealerTypeStatus withComments(String comments) {
        this.comments = comments;
        return this;
    }

    /**
     * 主键 id
     * 
     * @return
     *     The id
     */
    @JsonProperty("id")
    public Long getId() {
        return id;
    }

    /**
     * 主键 id
     * 
     * @param id
     *     The id
     */
    @JsonProperty("id")
    public void setId(Long id) {
        this.id = id;
    }

    public GdealerTypeStatus withId(Long id) {
        this.id = id;
        return this;
    }

    /**
     * 创建人
     * 
     * @return
     *     The createBy
     */
    @JsonProperty("createBy")
    public String getCreateBy() {
        return createBy;
    }

    /**
     * 创建人
     * 
     * @param createBy
     *     The createBy
     */
    @JsonProperty("createBy")
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public GdealerTypeStatus withCreateBy(String createBy) {
        this.createBy = createBy;
        return this;
    }

    /**
     * 最后更新人
     * 
     * @return
     *     The lastUpdateBy
     */
    @JsonProperty("lastUpdateBy")
    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    /**
     * 最后更新人
     * 
     * @param lastUpdateBy
     *     The lastUpdateBy
     */
    @JsonProperty("lastUpdateBy")
    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    public GdealerTypeStatus withLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
        return this;
    }

    /**
     * 最后更新时间
     * 
     * @return
     *     The lastUpdateTime
     */
    @JsonProperty("lastUpdateTime")
    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    /**
     * 最后更新时间
     * 
     * @param lastUpdateTime
     *     The lastUpdateTime
     */
    @JsonProperty("lastUpdateTime")
    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public GdealerTypeStatus withLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
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

    public GdealerTypeStatus withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(dealerNo).append(dealerType).append(effectiveDate).append(inactiveDate).append(current).append(comments).append(id).append(createBy).append(lastUpdateBy).append(lastUpdateTime).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof GdealerTypeStatus) == false) {
            return false;
        }
        GdealerTypeStatus rhs = ((GdealerTypeStatus) other);
        return new EqualsBuilder().append(dealerNo, rhs.dealerNo).append(dealerType, rhs.dealerType).append(effectiveDate, rhs.effectiveDate).append(inactiveDate, rhs.inactiveDate).append(current, rhs.current).append(comments, rhs.comments).append(id, rhs.id).append(createBy, rhs.createBy).append(lastUpdateBy, rhs.lastUpdateBy).append(lastUpdateTime, rhs.lastUpdateTime).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
