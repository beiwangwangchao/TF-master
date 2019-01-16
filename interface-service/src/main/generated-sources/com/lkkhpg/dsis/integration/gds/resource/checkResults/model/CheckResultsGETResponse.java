
package com.lkkhpg.dsis.integration.gds.resource.checkResults.model;

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
    "checkDate",
    "checkOrgCode",
    "checkEntityType",
    "checkEntityNo",
    "checkEntityRefPeriod",
    "checkPhase",
    "checkResultCode",
    "checkResultMemo01",
    "checkResultMemo02",
    "orgReadFlag",
    "orgReadTime",
    "orgReadBy",
    "orgAmendFlag",
    "orgAmendMemo",
    "orgAmendTime",
    "orgAmendBy",
    "enabled",
    "comments",
    "orgAutoSyn"
})
public class CheckResultsGETResponse {

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
     * 校验日期
     * 
     */
    @JsonProperty("checkDate")
    private String checkDate;
    /**
     * 校验机构
     * 
     */
    @JsonProperty("checkOrgCode")
    private String checkOrgCode;
    /**
     * 校验实体类型
     * 
     */
    @JsonProperty("checkEntityType")
    private String checkEntityType;
    /**
     * 校验实体编号
     * 
     */
    @JsonProperty("checkEntityNo")
    private String checkEntityNo;
    /**
     * 校验实体相关月份
     * 
     */
    @JsonProperty("checkEntityRefPeriod")
    private String checkEntityRefPeriod;
    /**
     * 校验阶段
     * 
     */
    @JsonProperty("checkPhase")
    private String checkPhase;
    /**
     * 校验结果码
     * 
     */
    @JsonProperty("checkResultCode")
    private String checkResultCode;
    /**
     * 校验结果备注01
     * 
     */
    @JsonProperty("checkResultMemo01")
    private String checkResultMemo01;
    /**
     * 校验结果备注02
     * 
     */
    @JsonProperty("checkResultMemo02")
    private String checkResultMemo02;
    /**
     * 各机构读取标志
     * 
     */
    @JsonProperty("orgReadFlag")
    private String orgReadFlag;
    /**
     * 各机构读取时间
     * 
     */
    @JsonProperty("orgReadTime")
    private String orgReadTime;
    /**
     * 各机构读取用户
     * 
     */
    @JsonProperty("orgReadBy")
    private String orgReadBy;
    /**
     * 各机构修正标志
     * 
     */
    @JsonProperty("orgAmendFlag")
    private String orgAmendFlag;
    /**
     * 各机构修正备注
     * 
     */
    @JsonProperty("orgAmendMemo")
    private String orgAmendMemo;
    /**
     * 各机构修正时间
     * 
     */
    @JsonProperty("orgAmendTime")
    private String orgAmendTime;
    /**
     * 各机构修正用户
     * 
     */
    @JsonProperty("orgAmendBy")
    private String orgAmendBy;
    /**
     * 是否有效
     * 
     */
    @JsonProperty("enabled")
    private Boolean enabled;
    /**
     * 备注说明
     * 
     */
    @JsonProperty("comments")
    private String comments;
    /**
     * orgAutoSyn
     * 
     */
    @JsonProperty("orgAutoSyn")
    private Boolean orgAutoSyn;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public CheckResultsGETResponse() {
    }

    /**
     * 
     * @param orgAmendBy
     * @param checkOrgCode
     * @param orgReadFlag
     * @param comments
     * @param orgAmendTime
     * @param checkEntityRefPeriod
     * @param orgAutoSyn
     * @param checkDate
     * @param checkResultMemo01
     * @param orgReadTime
     * @param checkEntityNo
     * @param checkResultMemo02
     * @param enabled
     * @param orgReadBy
     * @param orgAmendFlag
     * @param checkPhase
     * @param checkResultCode
     * @param createBy
     * @param checkEntityType
     * @param id
     * @param orgAmendMemo
     * @param lastUpdateBy
     * @param lastUpdateTime
     */
    public CheckResultsGETResponse(Long id, String createBy, String lastUpdateBy, String lastUpdateTime, String checkDate, String checkOrgCode, String checkEntityType, String checkEntityNo, String checkEntityRefPeriod, String checkPhase, String checkResultCode, String checkResultMemo01, String checkResultMemo02, String orgReadFlag, String orgReadTime, String orgReadBy, String orgAmendFlag, String orgAmendMemo, String orgAmendTime, String orgAmendBy, Boolean enabled, String comments, Boolean orgAutoSyn) {
        this.id = id;
        this.createBy = createBy;
        this.lastUpdateBy = lastUpdateBy;
        this.lastUpdateTime = lastUpdateTime;
        this.checkDate = checkDate;
        this.checkOrgCode = checkOrgCode;
        this.checkEntityType = checkEntityType;
        this.checkEntityNo = checkEntityNo;
        this.checkEntityRefPeriod = checkEntityRefPeriod;
        this.checkPhase = checkPhase;
        this.checkResultCode = checkResultCode;
        this.checkResultMemo01 = checkResultMemo01;
        this.checkResultMemo02 = checkResultMemo02;
        this.orgReadFlag = orgReadFlag;
        this.orgReadTime = orgReadTime;
        this.orgReadBy = orgReadBy;
        this.orgAmendFlag = orgAmendFlag;
        this.orgAmendMemo = orgAmendMemo;
        this.orgAmendTime = orgAmendTime;
        this.orgAmendBy = orgAmendBy;
        this.enabled = enabled;
        this.comments = comments;
        this.orgAutoSyn = orgAutoSyn;
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

    public CheckResultsGETResponse withId(Long id) {
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

    public CheckResultsGETResponse withCreateBy(String createBy) {
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

    public CheckResultsGETResponse withLastUpdateBy(String lastUpdateBy) {
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

    public CheckResultsGETResponse withLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
        return this;
    }

    /**
     * 校验日期
     * 
     * @return
     *     The checkDate
     */
    @JsonProperty("checkDate")
    public String getCheckDate() {
        return checkDate;
    }

    /**
     * 校验日期
     * 
     * @param checkDate
     *     The checkDate
     */
    @JsonProperty("checkDate")
    public void setCheckDate(String checkDate) {
        this.checkDate = checkDate;
    }

    public CheckResultsGETResponse withCheckDate(String checkDate) {
        this.checkDate = checkDate;
        return this;
    }

    /**
     * 校验机构
     * 
     * @return
     *     The checkOrgCode
     */
    @JsonProperty("checkOrgCode")
    public String getCheckOrgCode() {
        return checkOrgCode;
    }

    /**
     * 校验机构
     * 
     * @param checkOrgCode
     *     The checkOrgCode
     */
    @JsonProperty("checkOrgCode")
    public void setCheckOrgCode(String checkOrgCode) {
        this.checkOrgCode = checkOrgCode;
    }

    public CheckResultsGETResponse withCheckOrgCode(String checkOrgCode) {
        this.checkOrgCode = checkOrgCode;
        return this;
    }

    /**
     * 校验实体类型
     * 
     * @return
     *     The checkEntityType
     */
    @JsonProperty("checkEntityType")
    public String getCheckEntityType() {
        return checkEntityType;
    }

    /**
     * 校验实体类型
     * 
     * @param checkEntityType
     *     The checkEntityType
     */
    @JsonProperty("checkEntityType")
    public void setCheckEntityType(String checkEntityType) {
        this.checkEntityType = checkEntityType;
    }

    public CheckResultsGETResponse withCheckEntityType(String checkEntityType) {
        this.checkEntityType = checkEntityType;
        return this;
    }

    /**
     * 校验实体编号
     * 
     * @return
     *     The checkEntityNo
     */
    @JsonProperty("checkEntityNo")
    public String getCheckEntityNo() {
        return checkEntityNo;
    }

    /**
     * 校验实体编号
     * 
     * @param checkEntityNo
     *     The checkEntityNo
     */
    @JsonProperty("checkEntityNo")
    public void setCheckEntityNo(String checkEntityNo) {
        this.checkEntityNo = checkEntityNo;
    }

    public CheckResultsGETResponse withCheckEntityNo(String checkEntityNo) {
        this.checkEntityNo = checkEntityNo;
        return this;
    }

    /**
     * 校验实体相关月份
     * 
     * @return
     *     The checkEntityRefPeriod
     */
    @JsonProperty("checkEntityRefPeriod")
    public String getCheckEntityRefPeriod() {
        return checkEntityRefPeriod;
    }

    /**
     * 校验实体相关月份
     * 
     * @param checkEntityRefPeriod
     *     The checkEntityRefPeriod
     */
    @JsonProperty("checkEntityRefPeriod")
    public void setCheckEntityRefPeriod(String checkEntityRefPeriod) {
        this.checkEntityRefPeriod = checkEntityRefPeriod;
    }

    public CheckResultsGETResponse withCheckEntityRefPeriod(String checkEntityRefPeriod) {
        this.checkEntityRefPeriod = checkEntityRefPeriod;
        return this;
    }

    /**
     * 校验阶段
     * 
     * @return
     *     The checkPhase
     */
    @JsonProperty("checkPhase")
    public String getCheckPhase() {
        return checkPhase;
    }

    /**
     * 校验阶段
     * 
     * @param checkPhase
     *     The checkPhase
     */
    @JsonProperty("checkPhase")
    public void setCheckPhase(String checkPhase) {
        this.checkPhase = checkPhase;
    }

    public CheckResultsGETResponse withCheckPhase(String checkPhase) {
        this.checkPhase = checkPhase;
        return this;
    }

    /**
     * 校验结果码
     * 
     * @return
     *     The checkResultCode
     */
    @JsonProperty("checkResultCode")
    public String getCheckResultCode() {
        return checkResultCode;
    }

    /**
     * 校验结果码
     * 
     * @param checkResultCode
     *     The checkResultCode
     */
    @JsonProperty("checkResultCode")
    public void setCheckResultCode(String checkResultCode) {
        this.checkResultCode = checkResultCode;
    }

    public CheckResultsGETResponse withCheckResultCode(String checkResultCode) {
        this.checkResultCode = checkResultCode;
        return this;
    }

    /**
     * 校验结果备注01
     * 
     * @return
     *     The checkResultMemo01
     */
    @JsonProperty("checkResultMemo01")
    public String getCheckResultMemo01() {
        return checkResultMemo01;
    }

    /**
     * 校验结果备注01
     * 
     * @param checkResultMemo01
     *     The checkResultMemo01
     */
    @JsonProperty("checkResultMemo01")
    public void setCheckResultMemo01(String checkResultMemo01) {
        this.checkResultMemo01 = checkResultMemo01;
    }

    public CheckResultsGETResponse withCheckResultMemo01(String checkResultMemo01) {
        this.checkResultMemo01 = checkResultMemo01;
        return this;
    }

    /**
     * 校验结果备注02
     * 
     * @return
     *     The checkResultMemo02
     */
    @JsonProperty("checkResultMemo02")
    public String getCheckResultMemo02() {
        return checkResultMemo02;
    }

    /**
     * 校验结果备注02
     * 
     * @param checkResultMemo02
     *     The checkResultMemo02
     */
    @JsonProperty("checkResultMemo02")
    public void setCheckResultMemo02(String checkResultMemo02) {
        this.checkResultMemo02 = checkResultMemo02;
    }

    public CheckResultsGETResponse withCheckResultMemo02(String checkResultMemo02) {
        this.checkResultMemo02 = checkResultMemo02;
        return this;
    }

    /**
     * 各机构读取标志
     * 
     * @return
     *     The orgReadFlag
     */
    @JsonProperty("orgReadFlag")
    public String getOrgReadFlag() {
        return orgReadFlag;
    }

    /**
     * 各机构读取标志
     * 
     * @param orgReadFlag
     *     The orgReadFlag
     */
    @JsonProperty("orgReadFlag")
    public void setOrgReadFlag(String orgReadFlag) {
        this.orgReadFlag = orgReadFlag;
    }

    public CheckResultsGETResponse withOrgReadFlag(String orgReadFlag) {
        this.orgReadFlag = orgReadFlag;
        return this;
    }

    /**
     * 各机构读取时间
     * 
     * @return
     *     The orgReadTime
     */
    @JsonProperty("orgReadTime")
    public String getOrgReadTime() {
        return orgReadTime;
    }

    /**
     * 各机构读取时间
     * 
     * @param orgReadTime
     *     The orgReadTime
     */
    @JsonProperty("orgReadTime")
    public void setOrgReadTime(String orgReadTime) {
        this.orgReadTime = orgReadTime;
    }

    public CheckResultsGETResponse withOrgReadTime(String orgReadTime) {
        this.orgReadTime = orgReadTime;
        return this;
    }

    /**
     * 各机构读取用户
     * 
     * @return
     *     The orgReadBy
     */
    @JsonProperty("orgReadBy")
    public String getOrgReadBy() {
        return orgReadBy;
    }

    /**
     * 各机构读取用户
     * 
     * @param orgReadBy
     *     The orgReadBy
     */
    @JsonProperty("orgReadBy")
    public void setOrgReadBy(String orgReadBy) {
        this.orgReadBy = orgReadBy;
    }

    public CheckResultsGETResponse withOrgReadBy(String orgReadBy) {
        this.orgReadBy = orgReadBy;
        return this;
    }

    /**
     * 各机构修正标志
     * 
     * @return
     *     The orgAmendFlag
     */
    @JsonProperty("orgAmendFlag")
    public String getOrgAmendFlag() {
        return orgAmendFlag;
    }

    /**
     * 各机构修正标志
     * 
     * @param orgAmendFlag
     *     The orgAmendFlag
     */
    @JsonProperty("orgAmendFlag")
    public void setOrgAmendFlag(String orgAmendFlag) {
        this.orgAmendFlag = orgAmendFlag;
    }

    public CheckResultsGETResponse withOrgAmendFlag(String orgAmendFlag) {
        this.orgAmendFlag = orgAmendFlag;
        return this;
    }

    /**
     * 各机构修正备注
     * 
     * @return
     *     The orgAmendMemo
     */
    @JsonProperty("orgAmendMemo")
    public String getOrgAmendMemo() {
        return orgAmendMemo;
    }

    /**
     * 各机构修正备注
     * 
     * @param orgAmendMemo
     *     The orgAmendMemo
     */
    @JsonProperty("orgAmendMemo")
    public void setOrgAmendMemo(String orgAmendMemo) {
        this.orgAmendMemo = orgAmendMemo;
    }

    public CheckResultsGETResponse withOrgAmendMemo(String orgAmendMemo) {
        this.orgAmendMemo = orgAmendMemo;
        return this;
    }

    /**
     * 各机构修正时间
     * 
     * @return
     *     The orgAmendTime
     */
    @JsonProperty("orgAmendTime")
    public String getOrgAmendTime() {
        return orgAmendTime;
    }

    /**
     * 各机构修正时间
     * 
     * @param orgAmendTime
     *     The orgAmendTime
     */
    @JsonProperty("orgAmendTime")
    public void setOrgAmendTime(String orgAmendTime) {
        this.orgAmendTime = orgAmendTime;
    }

    public CheckResultsGETResponse withOrgAmendTime(String orgAmendTime) {
        this.orgAmendTime = orgAmendTime;
        return this;
    }

    /**
     * 各机构修正用户
     * 
     * @return
     *     The orgAmendBy
     */
    @JsonProperty("orgAmendBy")
    public String getOrgAmendBy() {
        return orgAmendBy;
    }

    /**
     * 各机构修正用户
     * 
     * @param orgAmendBy
     *     The orgAmendBy
     */
    @JsonProperty("orgAmendBy")
    public void setOrgAmendBy(String orgAmendBy) {
        this.orgAmendBy = orgAmendBy;
    }

    public CheckResultsGETResponse withOrgAmendBy(String orgAmendBy) {
        this.orgAmendBy = orgAmendBy;
        return this;
    }

    /**
     * 是否有效
     * 
     * @return
     *     The enabled
     */
    @JsonProperty("enabled")
    public Boolean getEnabled() {
        return enabled;
    }

    /**
     * 是否有效
     * 
     * @param enabled
     *     The enabled
     */
    @JsonProperty("enabled")
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public CheckResultsGETResponse withEnabled(Boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    /**
     * 备注说明
     * 
     * @return
     *     The comments
     */
    @JsonProperty("comments")
    public String getComments() {
        return comments;
    }

    /**
     * 备注说明
     * 
     * @param comments
     *     The comments
     */
    @JsonProperty("comments")
    public void setComments(String comments) {
        this.comments = comments;
    }

    public CheckResultsGETResponse withComments(String comments) {
        this.comments = comments;
        return this;
    }

    /**
     * orgAutoSyn
     * 
     * @return
     *     The orgAutoSyn
     */
    @JsonProperty("orgAutoSyn")
    public Boolean getOrgAutoSyn() {
        return orgAutoSyn;
    }

    /**
     * orgAutoSyn
     * 
     * @param orgAutoSyn
     *     The orgAutoSyn
     */
    @JsonProperty("orgAutoSyn")
    public void setOrgAutoSyn(Boolean orgAutoSyn) {
        this.orgAutoSyn = orgAutoSyn;
    }

    public CheckResultsGETResponse withOrgAutoSyn(Boolean orgAutoSyn) {
        this.orgAutoSyn = orgAutoSyn;
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

    public CheckResultsGETResponse withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).append(createBy).append(lastUpdateBy).append(lastUpdateTime).append(checkDate).append(checkOrgCode).append(checkEntityType).append(checkEntityNo).append(checkEntityRefPeriod).append(checkPhase).append(checkResultCode).append(checkResultMemo01).append(checkResultMemo02).append(orgReadFlag).append(orgReadTime).append(orgReadBy).append(orgAmendFlag).append(orgAmendMemo).append(orgAmendTime).append(orgAmendBy).append(enabled).append(comments).append(orgAutoSyn).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof CheckResultsGETResponse) == false) {
            return false;
        }
        CheckResultsGETResponse rhs = ((CheckResultsGETResponse) other);
        return new EqualsBuilder().append(id, rhs.id).append(createBy, rhs.createBy).append(lastUpdateBy, rhs.lastUpdateBy).append(lastUpdateTime, rhs.lastUpdateTime).append(checkDate, rhs.checkDate).append(checkOrgCode, rhs.checkOrgCode).append(checkEntityType, rhs.checkEntityType).append(checkEntityNo, rhs.checkEntityNo).append(checkEntityRefPeriod, rhs.checkEntityRefPeriod).append(checkPhase, rhs.checkPhase).append(checkResultCode, rhs.checkResultCode).append(checkResultMemo01, rhs.checkResultMemo01).append(checkResultMemo02, rhs.checkResultMemo02).append(orgReadFlag, rhs.orgReadFlag).append(orgReadTime, rhs.orgReadTime).append(orgReadBy, rhs.orgReadBy).append(orgAmendFlag, rhs.orgAmendFlag).append(orgAmendMemo, rhs.orgAmendMemo).append(orgAmendTime, rhs.orgAmendTime).append(orgAmendBy, rhs.orgAmendBy).append(enabled, rhs.enabled).append(comments, rhs.comments).append(orgAutoSyn, rhs.orgAutoSyn).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
