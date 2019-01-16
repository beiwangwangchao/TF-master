
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
    "id",
    "createBy",
    "lastUpdateBy",
    "lastUpdateTime",
    "productCode",
    "productPrice",
    "productPoint",
    "localSalePrice",
    "localSalePoint",
    "localSaleRebate",
    "localSaleQty",
    "lineNo",
    "localSaleType",
    "localSaleTaxAmt"
})
public class GsoDetail {

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
    /**
     * 产品代码
     * 
     */
    @JsonProperty("productCode")
    private String productCode;
    /**
     * 产品优惠价
     * 
     */
    @JsonProperty("productPrice")
    private Double productPrice;
    /**
     * 产品点数
     * 
     */
    @JsonProperty("productPoint")
    private Double productPoint;
    /**
     * 本国销售单价
     * 
     */
    @JsonProperty("localSalePrice")
    private Double localSalePrice;
    /**
     * 本国销售点数
     * 
     */
    @JsonProperty("localSalePoint")
    private Double localSalePoint;
    /**
     * 本国销售优差
     * 
     */
    @JsonProperty("localSaleRebate")
    private Double localSaleRebate;
    /**
     * 本国销售数量
     * 
     */
    @JsonProperty("localSaleQty")
    private Long localSaleQty;
    /**
     * 行号
     * 
     */
    @JsonProperty("lineNo")
    private int lineNo;
    /**
     * 本国销售类型
     * 
     */
    @JsonProperty("localSaleType")
    private String localSaleType;
    /**
     * 本国销售税
     * 
     */
    @JsonProperty("localSaleTaxAmt")
    private Double localSaleTaxAmt;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public GsoDetail() {
    }

    /**
     * 
     * @param productPoint
     * @param localSalePrice
     * @param localSalePoint
     * @param createBy
     * @param productCode
     * @param lineNo
     * @param id
     * @param localSaleRebate
     * @param localSaleType
     * @param productPrice
     * @param lastUpdateBy
     * @param lastUpdateTime
     * @param localSaleQty
     */
    public GsoDetail(Long id, String createBy, String lastUpdateBy, String lastUpdateTime, String productCode, Double productPrice, Double productPoint, Double localSalePrice, Double localSalePoint, Double localSaleRebate, Long localSaleQty, int lineNo, String localSaleType) {
        this.id = id;
        this.createBy = createBy;
        this.lastUpdateBy = lastUpdateBy;
        this.lastUpdateTime = lastUpdateTime;
        this.productCode = productCode;
        this.productPrice = productPrice;
        this.productPoint = productPoint;
        this.localSalePrice = localSalePrice;
        this.localSalePoint = localSalePoint;
        this.localSaleRebate = localSaleRebate;
        this.localSaleQty = localSaleQty;
        this.lineNo = lineNo;
        this.localSaleType = localSaleType;
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

    public GsoDetail withId(Long id) {
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

    public GsoDetail withCreateBy(String createBy) {
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

    public GsoDetail withLastUpdateBy(String lastUpdateBy) {
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

    public GsoDetail withLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
        return this;
    }

    /**
     * 产品代码
     * 
     * @return
     *     The productCode
     */
    @JsonProperty("productCode")
    public String getProductCode() {
        return productCode;
    }

    /**
     * 产品代码
     * 
     * @param productCode
     *     The productCode
     */
    @JsonProperty("productCode")
    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public GsoDetail withProductCode(String productCode) {
        this.productCode = productCode;
        return this;
    }

    /**
     * 产品优惠价
     * 
     * @return
     *     The productPrice
     */
    @JsonProperty("productPrice")
    public Double getProductPrice() {
        return productPrice;
    }

    /**
     * 产品优惠价
     * 
     * @param productPrice
     *     The productPrice
     */
    @JsonProperty("productPrice")
    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public GsoDetail withProductPrice(Double productPrice) {
        this.productPrice = productPrice;
        return this;
    }

    /**
     * 产品点数
     * 
     * @return
     *     The productPoint
     */
    @JsonProperty("productPoint")
    public Double getProductPoint() {
        return productPoint;
    }

    /**
     * 产品点数
     * 
     * @param productPoint
     *     The productPoint
     */
    @JsonProperty("productPoint")
    public void setProductPoint(Double productPoint) {
        this.productPoint = productPoint;
    }

    public GsoDetail withProductPoint(Double productPoint) {
        this.productPoint = productPoint;
        return this;
    }

    /**
     * 本国销售单价
     * 
     * @return
     *     The localSalePrice
     */
    @JsonProperty("localSalePrice")
    public Double getLocalSalePrice() {
        return localSalePrice;
    }

    /**
     * 本国销售单价
     * 
     * @param localSalePrice
     *     The localSalePrice
     */
    @JsonProperty("localSalePrice")
    public void setLocalSalePrice(Double localSalePrice) {
        this.localSalePrice = localSalePrice;
    }

    public GsoDetail withLocalSalePrice(Double localSalePrice) {
        this.localSalePrice = localSalePrice;
        return this;
    }

    /**
     * 本国销售点数
     * 
     * @return
     *     The localSalePoint
     */
    @JsonProperty("localSalePoint")
    public Double getLocalSalePoint() {
        return localSalePoint;
    }

    /**
     * 本国销售点数
     * 
     * @param localSalePoint
     *     The localSalePoint
     */
    @JsonProperty("localSalePoint")
    public void setLocalSalePoint(Double localSalePoint) {
        this.localSalePoint = localSalePoint;
    }

    public GsoDetail withLocalSalePoint(Double localSalePoint) {
        this.localSalePoint = localSalePoint;
        return this;
    }

    /**
     * 本国销售优差
     * 
     * @return
     *     The localSaleRebate
     */
    @JsonProperty("localSaleRebate")
    public Double getLocalSaleRebate() {
        return localSaleRebate;
    }

    /**
     * 本国销售优差
     * 
     * @param localSaleRebate
     *     The localSaleRebate
     */
    @JsonProperty("localSaleRebate")
    public void setLocalSaleRebate(Double localSaleRebate) {
        this.localSaleRebate = localSaleRebate;
    }

    public GsoDetail withLocalSaleRebate(Double localSaleRebate) {
        this.localSaleRebate = localSaleRebate;
        return this;
    }

    /**
     * 本国销售数量
     * 
     * @return
     *     The localSaleQty
     */
    @JsonProperty("localSaleQty")
    public Long getLocalSaleQty() {
        return localSaleQty;
    }

    /**
     * 本国销售数量
     * 
     * @param localSaleQty
     *     The localSaleQty
     */
    @JsonProperty("localSaleQty")
    public void setLocalSaleQty(Long localSaleQty) {
        this.localSaleQty = localSaleQty;
    }

    public GsoDetail withLocalSaleQty(Long localSaleQty) {
        this.localSaleQty = localSaleQty;
        return this;
    }

    /**
     * 行号
     * 
     * @return
     *     The lineNo
     */
    @JsonProperty("lineNo")
    public int getLineNo() {
        return lineNo;
    }

    /**
     * 行号
     * 
     * @param lineNo
     *     The lineNo
     */
    @JsonProperty("lineNo")
    public void setLineNo(int lineNo) {
        this.lineNo = lineNo;
    }

    public GsoDetail withLineNo(int lineNo) {
        this.lineNo = lineNo;
        return this;
    }

    /**
     * 本国销售类型
     * 
     * @return
     *     The localSaleType
     */
    @JsonProperty("localSaleType")
    public String getLocalSaleType() {
        return localSaleType;
    }

    /**
     * 本国销售类型
     * 
     * @param localSaleType
     *     The localSaleType
     */
    @JsonProperty("localSaleType")
    public void setLocalSaleType(String localSaleType) {
        this.localSaleType = localSaleType;
    }
    
    /**
     * 本国销售税
     * 
     * @return
     *     The localSaleTaxAmt
     */
    @JsonProperty("localSaleTaxAmt")
    public Double getLocalSaleTaxAmt() {
        return localSaleTaxAmt;
    }

    /**
     * 本国销售税
     * 
     * @param localSaleTaxAmt
     *     The localSaleTaxAmt
     */
    @JsonProperty("localSaleTaxAmt")
    public void setLocalSaleTaxAmt(Double localSaleTaxAmt) {
        this.localSaleTaxAmt = localSaleTaxAmt;
    }

    public GsoDetail withLocalSaleType(String localSaleType) {
        this.localSaleType = localSaleType;
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

    public GsoDetail withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).append(createBy).append(lastUpdateBy).append(lastUpdateTime).append(productCode).append(productPrice).append(productPoint).append(localSalePrice).append(localSalePoint).append(localSaleRebate).append(localSaleQty).append(lineNo).append(localSaleType).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof GsoDetail) == false) {
            return false;
        }
        GsoDetail rhs = ((GsoDetail) other);
        return new EqualsBuilder().append(id, rhs.id).append(createBy, rhs.createBy).append(lastUpdateBy, rhs.lastUpdateBy).append(lastUpdateTime, rhs.lastUpdateTime).append(productCode, rhs.productCode).append(productPrice, rhs.productPrice).append(productPoint, rhs.productPoint).append(localSalePrice, rhs.localSalePrice).append(localSalePoint, rhs.localSalePoint).append(localSaleRebate, rhs.localSaleRebate).append(localSaleQty, rhs.localSaleQty).append(lineNo, rhs.lineNo).append(localSaleType, rhs.localSaleType).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
