
package com.lkkhpg.dsis.integration.gds.resource.gtreeAlters.terminate.results.model;

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
    "alterNo",
    "tranPeriod",
    "tranOrgCode",
    "tranDealerNo",
    "tranBase",
    "tranMode",
    "tranFromSponsorNo",
    "tranFromDealerType",
    "tranToSponsorNo",
    "tranToDealerType",
    "orgSyn",
    "orgSynTime",
    "orgSynBy",
    "gdsRecheckFlag",
    "gdsRecheckTime",
    "gdsRecheckBy",
    "comments"
})
public class GtreeAlterDetail {

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
     * 变更编号
     * 
     */
    @JsonProperty("alterNo")
    private String alterNo;
    /**
     * 处理月份
     * 
     */
    @JsonProperty("tranPeriod")
    private String tranPeriod;
    /**
     * 处理机构
     * 
     */
    @JsonProperty("tranOrgCode")
    private String tranOrgCode;
    /**
     * 处理卡号
     * 
     */
    @JsonProperty("tranDealerNo")
    private String tranDealerNo;
    /**
     * 是否主卡
     * 
     */
    @JsonProperty("tranBase")
    private Boolean tranBase;
    /**
     * 处理模式
     * 
     */
    @JsonProperty("tranMode")
    private String tranMode;
    /**
     * 处理前推荐人
     * 
     */
    @JsonProperty("tranFromSponsorNo")
    private String tranFromSponsorNo;
    /**
     * 处理前身份类型
     * 
     */
    @JsonProperty("tranFromDealerType")
    private String tranFromDealerType;
    /**
     * 处理后推荐人
     * 
     */
    @JsonProperty("tranToSponsorNo")
    private String tranToSponsorNo;
    /**
     * 处理后身份类型
     * 
     */
    @JsonProperty("tranToDealerType")
    private String tranToDealerType;
    /**
     * 机构同步状态
     * 
     */
    @JsonProperty("orgSyn")
    private Boolean orgSyn;
    /**
     * 机构同步处理时间
     * 
     */
    @JsonProperty("orgSynTime")
    private String orgSynTime;
    /**
     * 机构同步处理用户
     * 
     */
    @JsonProperty("orgSynBy")
    private String orgSynBy;
    /**
     * GDS重新检查标志
     * 
     */
    @JsonProperty("gdsRecheckFlag")
    private String gdsRecheckFlag;
    /**
     * GDS重新检查时间
     * 
     */
    @JsonProperty("gdsRecheckTime")
    private String gdsRecheckTime;
    /**
     * GDS重新检查用户
     * 
     */
    @JsonProperty("gdsRecheckBy")
    private String gdsRecheckBy;
    /**
     * 备注
     * 
     */
    @JsonProperty("comments")
    private String comments;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public GtreeAlterDetail() {
    }

    /**
     * 
     * @param tranOrgCode
     * @param comments
     * @param tranPeriod
     * @param orgSyn
     * @param tranFromDealerType
     * @param gdsRecheckFlag
     * @param tranFromSponsorNo
     * @param tranToSponsorNo
     * @param tranMode
     * @param alterNo
     * @param tranToDealerType
     * @param orgSynBy
     * @param orgSynTime
     * @param createBy
     * @param gdsRecheckTime
     * @param gdsRecheckBy
     * @param tranDealerNo
     * @param id
     * @param tranBase
     * @param lastUpdateBy
     * @param lastUpdateTime
     */
    public GtreeAlterDetail(Long id, String createBy, String lastUpdateBy, String lastUpdateTime, String alterNo, String tranPeriod, String tranOrgCode, String tranDealerNo, Boolean tranBase, String tranMode, String tranFromSponsorNo, String tranFromDealerType, String tranToSponsorNo, String tranToDealerType, Boolean orgSyn, String orgSynTime, String orgSynBy, String gdsRecheckFlag, String gdsRecheckTime, String gdsRecheckBy, String comments) {
        this.id = id;
        this.createBy = createBy;
        this.lastUpdateBy = lastUpdateBy;
        this.lastUpdateTime = lastUpdateTime;
        this.alterNo = alterNo;
        this.tranPeriod = tranPeriod;
        this.tranOrgCode = tranOrgCode;
        this.tranDealerNo = tranDealerNo;
        this.tranBase = tranBase;
        this.tranMode = tranMode;
        this.tranFromSponsorNo = tranFromSponsorNo;
        this.tranFromDealerType = tranFromDealerType;
        this.tranToSponsorNo = tranToSponsorNo;
        this.tranToDealerType = tranToDealerType;
        this.orgSyn = orgSyn;
        this.orgSynTime = orgSynTime;
        this.orgSynBy = orgSynBy;
        this.gdsRecheckFlag = gdsRecheckFlag;
        this.gdsRecheckTime = gdsRecheckTime;
        this.gdsRecheckBy = gdsRecheckBy;
        this.comments = comments;
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

    public GtreeAlterDetail withId(Long id) {
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

    public GtreeAlterDetail withCreateBy(String createBy) {
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

    public GtreeAlterDetail withLastUpdateBy(String lastUpdateBy) {
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

    public GtreeAlterDetail withLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
        return this;
    }

    /**
     * 变更编号
     * 
     * @return
     *     The alterNo
     */
    @JsonProperty("alterNo")
    public String getAlterNo() {
        return alterNo;
    }

    /**
     * 变更编号
     * 
     * @param alterNo
     *     The alterNo
     */
    @JsonProperty("alterNo")
    public void setAlterNo(String alterNo) {
        this.alterNo = alterNo;
    }

    public GtreeAlterDetail withAlterNo(String alterNo) {
        this.alterNo = alterNo;
        return this;
    }

    /**
     * 处理月份
     * 
     * @return
     *     The tranPeriod
     */
    @JsonProperty("tranPeriod")
    public String getTranPeriod() {
        return tranPeriod;
    }

    /**
     * 处理月份
     * 
     * @param tranPeriod
     *     The tranPeriod
     */
    @JsonProperty("tranPeriod")
    public void setTranPeriod(String tranPeriod) {
        this.tranPeriod = tranPeriod;
    }

    public GtreeAlterDetail withTranPeriod(String tranPeriod) {
        this.tranPeriod = tranPeriod;
        return this;
    }

    /**
     * 处理机构
     * 
     * @return
     *     The tranOrgCode
     */
    @JsonProperty("tranOrgCode")
    public String getTranOrgCode() {
        return tranOrgCode;
    }

    /**
     * 处理机构
     * 
     * @param tranOrgCode
     *     The tranOrgCode
     */
    @JsonProperty("tranOrgCode")
    public void setTranOrgCode(String tranOrgCode) {
        this.tranOrgCode = tranOrgCode;
    }

    public GtreeAlterDetail withTranOrgCode(String tranOrgCode) {
        this.tranOrgCode = tranOrgCode;
        return this;
    }

    /**
     * 处理卡号
     * 
     * @return
     *     The tranDealerNo
     */
    @JsonProperty("tranDealerNo")
    public String getTranDealerNo() {
        return tranDealerNo;
    }

    /**
     * 处理卡号
     * 
     * @param tranDealerNo
     *     The tranDealerNo
     */
    @JsonProperty("tranDealerNo")
    public void setTranDealerNo(String tranDealerNo) {
        this.tranDealerNo = tranDealerNo;
    }

    public GtreeAlterDetail withTranDealerNo(String tranDealerNo) {
        this.tranDealerNo = tranDealerNo;
        return this;
    }

    /**
     * 是否主卡
     * 
     * @return
     *     The tranBase
     */
    @JsonProperty("tranBase")
    public Boolean getTranBase() {
        return tranBase;
    }

    /**
     * 是否主卡
     * 
     * @param tranBase
     *     The tranBase
     */
    @JsonProperty("tranBase")
    public void setTranBase(Boolean tranBase) {
        this.tranBase = tranBase;
    }

    public GtreeAlterDetail withTranBase(Boolean tranBase) {
        this.tranBase = tranBase;
        return this;
    }

    /**
     * 处理模式
     * 
     * @return
     *     The tranMode
     */
    @JsonProperty("tranMode")
    public String getTranMode() {
        return tranMode;
    }

    /**
     * 处理模式
     * 
     * @param tranMode
     *     The tranMode
     */
    @JsonProperty("tranMode")
    public void setTranMode(String tranMode) {
        this.tranMode = tranMode;
    }

    public GtreeAlterDetail withTranMode(String tranMode) {
        this.tranMode = tranMode;
        return this;
    }

    /**
     * 处理前推荐人
     * 
     * @return
     *     The tranFromSponsorNo
     */
    @JsonProperty("tranFromSponsorNo")
    public String getTranFromSponsorNo() {
        return tranFromSponsorNo;
    }

    /**
     * 处理前推荐人
     * 
     * @param tranFromSponsorNo
     *     The tranFromSponsorNo
     */
    @JsonProperty("tranFromSponsorNo")
    public void setTranFromSponsorNo(String tranFromSponsorNo) {
        this.tranFromSponsorNo = tranFromSponsorNo;
    }

    public GtreeAlterDetail withTranFromSponsorNo(String tranFromSponsorNo) {
        this.tranFromSponsorNo = tranFromSponsorNo;
        return this;
    }

    /**
     * 处理前身份类型
     * 
     * @return
     *     The tranFromDealerType
     */
    @JsonProperty("tranFromDealerType")
    public String getTranFromDealerType() {
        return tranFromDealerType;
    }

    /**
     * 处理前身份类型
     * 
     * @param tranFromDealerType
     *     The tranFromDealerType
     */
    @JsonProperty("tranFromDealerType")
    public void setTranFromDealerType(String tranFromDealerType) {
        this.tranFromDealerType = tranFromDealerType;
    }

    public GtreeAlterDetail withTranFromDealerType(String tranFromDealerType) {
        this.tranFromDealerType = tranFromDealerType;
        return this;
    }

    /**
     * 处理后推荐人
     * 
     * @return
     *     The tranToSponsorNo
     */
    @JsonProperty("tranToSponsorNo")
    public String getTranToSponsorNo() {
        return tranToSponsorNo;
    }

    /**
     * 处理后推荐人
     * 
     * @param tranToSponsorNo
     *     The tranToSponsorNo
     */
    @JsonProperty("tranToSponsorNo")
    public void setTranToSponsorNo(String tranToSponsorNo) {
        this.tranToSponsorNo = tranToSponsorNo;
    }

    public GtreeAlterDetail withTranToSponsorNo(String tranToSponsorNo) {
        this.tranToSponsorNo = tranToSponsorNo;
        return this;
    }

    /**
     * 处理后身份类型
     * 
     * @return
     *     The tranToDealerType
     */
    @JsonProperty("tranToDealerType")
    public String getTranToDealerType() {
        return tranToDealerType;
    }

    /**
     * 处理后身份类型
     * 
     * @param tranToDealerType
     *     The tranToDealerType
     */
    @JsonProperty("tranToDealerType")
    public void setTranToDealerType(String tranToDealerType) {
        this.tranToDealerType = tranToDealerType;
    }

    public GtreeAlterDetail withTranToDealerType(String tranToDealerType) {
        this.tranToDealerType = tranToDealerType;
        return this;
    }

    /**
     * 机构同步状态
     * 
     * @return
     *     The orgSyn
     */
    @JsonProperty("orgSyn")
    public Boolean getOrgSyn() {
        return orgSyn;
    }

    /**
     * 机构同步状态
     * 
     * @param orgSyn
     *     The orgSyn
     */
    @JsonProperty("orgSyn")
    public void setOrgSyn(Boolean orgSyn) {
        this.orgSyn = orgSyn;
    }

    public GtreeAlterDetail withOrgSyn(Boolean orgSyn) {
        this.orgSyn = orgSyn;
        return this;
    }

    /**
     * 机构同步处理时间
     * 
     * @return
     *     The orgSynTime
     */
    @JsonProperty("orgSynTime")
    public String getOrgSynTime() {
        return orgSynTime;
    }

    /**
     * 机构同步处理时间
     * 
     * @param orgSynTime
     *     The orgSynTime
     */
    @JsonProperty("orgSynTime")
    public void setOrgSynTime(String orgSynTime) {
        this.orgSynTime = orgSynTime;
    }

    public GtreeAlterDetail withOrgSynTime(String orgSynTime) {
        this.orgSynTime = orgSynTime;
        return this;
    }

    /**
     * 机构同步处理用户
     * 
     * @return
     *     The orgSynBy
     */
    @JsonProperty("orgSynBy")
    public String getOrgSynBy() {
        return orgSynBy;
    }

    /**
     * 机构同步处理用户
     * 
     * @param orgSynBy
     *     The orgSynBy
     */
    @JsonProperty("orgSynBy")
    public void setOrgSynBy(String orgSynBy) {
        this.orgSynBy = orgSynBy;
    }

    public GtreeAlterDetail withOrgSynBy(String orgSynBy) {
        this.orgSynBy = orgSynBy;
        return this;
    }

    /**
     * GDS重新检查标志
     * 
     * @return
     *     The gdsRecheckFlag
     */
    @JsonProperty("gdsRecheckFlag")
    public String getGdsRecheckFlag() {
        return gdsRecheckFlag;
    }

    /**
     * GDS重新检查标志
     * 
     * @param gdsRecheckFlag
     *     The gdsRecheckFlag
     */
    @JsonProperty("gdsRecheckFlag")
    public void setGdsRecheckFlag(String gdsRecheckFlag) {
        this.gdsRecheckFlag = gdsRecheckFlag;
    }

    public GtreeAlterDetail withGdsRecheckFlag(String gdsRecheckFlag) {
        this.gdsRecheckFlag = gdsRecheckFlag;
        return this;
    }

    /**
     * GDS重新检查时间
     * 
     * @return
     *     The gdsRecheckTime
     */
    @JsonProperty("gdsRecheckTime")
    public String getGdsRecheckTime() {
        return gdsRecheckTime;
    }

    /**
     * GDS重新检查时间
     * 
     * @param gdsRecheckTime
     *     The gdsRecheckTime
     */
    @JsonProperty("gdsRecheckTime")
    public void setGdsRecheckTime(String gdsRecheckTime) {
        this.gdsRecheckTime = gdsRecheckTime;
    }

    public GtreeAlterDetail withGdsRecheckTime(String gdsRecheckTime) {
        this.gdsRecheckTime = gdsRecheckTime;
        return this;
    }

    /**
     * GDS重新检查用户
     * 
     * @return
     *     The gdsRecheckBy
     */
    @JsonProperty("gdsRecheckBy")
    public String getGdsRecheckBy() {
        return gdsRecheckBy;
    }

    /**
     * GDS重新检查用户
     * 
     * @param gdsRecheckBy
     *     The gdsRecheckBy
     */
    @JsonProperty("gdsRecheckBy")
    public void setGdsRecheckBy(String gdsRecheckBy) {
        this.gdsRecheckBy = gdsRecheckBy;
    }

    public GtreeAlterDetail withGdsRecheckBy(String gdsRecheckBy) {
        this.gdsRecheckBy = gdsRecheckBy;
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

    public GtreeAlterDetail withComments(String comments) {
        this.comments = comments;
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

    public GtreeAlterDetail withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).append(createBy).append(lastUpdateBy).append(lastUpdateTime).append(alterNo).append(tranPeriod).append(tranOrgCode).append(tranDealerNo).append(tranBase).append(tranMode).append(tranFromSponsorNo).append(tranFromDealerType).append(tranToSponsorNo).append(tranToDealerType).append(orgSyn).append(orgSynTime).append(orgSynBy).append(gdsRecheckFlag).append(gdsRecheckTime).append(gdsRecheckBy).append(comments).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof GtreeAlterDetail) == false) {
            return false;
        }
        GtreeAlterDetail rhs = ((GtreeAlterDetail) other);
        return new EqualsBuilder().append(id, rhs.id).append(createBy, rhs.createBy).append(lastUpdateBy, rhs.lastUpdateBy).append(lastUpdateTime, rhs.lastUpdateTime).append(alterNo, rhs.alterNo).append(tranPeriod, rhs.tranPeriod).append(tranOrgCode, rhs.tranOrgCode).append(tranDealerNo, rhs.tranDealerNo).append(tranBase, rhs.tranBase).append(tranMode, rhs.tranMode).append(tranFromSponsorNo, rhs.tranFromSponsorNo).append(tranFromDealerType, rhs.tranFromDealerType).append(tranToSponsorNo, rhs.tranToSponsorNo).append(tranToDealerType, rhs.tranToDealerType).append(orgSyn, rhs.orgSyn).append(orgSynTime, rhs.orgSynTime).append(orgSynBy, rhs.orgSynBy).append(gdsRecheckFlag, rhs.gdsRecheckFlag).append(gdsRecheckTime, rhs.gdsRecheckTime).append(gdsRecheckBy, rhs.gdsRecheckBy).append(comments, rhs.comments).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
