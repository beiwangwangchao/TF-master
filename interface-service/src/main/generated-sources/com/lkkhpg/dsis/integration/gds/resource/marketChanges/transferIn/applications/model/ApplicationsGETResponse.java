
package com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferIn.applications.model;

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
    "oldSaleOrgCode",
    "newSaleOrgCode",
    "newDealerType",
    "newDealerSubType",
    "appDate",
    "appDocNo",
    "appEntryTime",
    "appEntryBy",
    "appCheckedTime",
    "appCheckedBy",
    "appCheckedMemo",
    "appAuditedTime",
    "appAuditedBy",
    "appAuditedMemo",
    "gdsTranStatus",
    "gdsTranSoPeriod",
    "gdsTranEffectivePeriod",
    "gdsTranTime",
    "gdsTranBy",
    "gdsTranMemo",
    "oldOrgTranFlag",
    "oldOrgTranTime",
    "oldOrgTranBy",
    "oldOrgTranMemo",
    "newOrgTranFlag",
    "newOrgTranTime",
    "newOrgTranBy",
    "newOrgTranMemo",
    "appFullName",
    "appLastName",
    "appFirstName",
    "appCertificateNationCode",
    "appCertificateType",
    "appCertificateNo",
    "newSaleBranchNo",
    "newSaleZoneNo",
    "comments",
    "id",
    "createBy",
    "createTime",
    "lastUpdateBy",
    "lastUpdateTime"
})
public class ApplicationsGETResponse {

    /**
     * 卡號
     * 
     */
    @JsonProperty("dealerNo")
    private String dealerNo;
    /**
     * 源销售机构
     * 
     */
    @JsonProperty("oldSaleOrgCode")
    private String oldSaleOrgCode;
    /**
     * 新销售机构
     * 
     */
    @JsonProperty("newSaleOrgCode")
    private String newSaleOrgCode;
    /**
     * 新身份类型
     * 
     */
    @JsonProperty("newDealerType")
    private String newDealerType;
    /**
     * 新身份子类型
     * 
     */
    @JsonProperty("newDealerSubType")
    private String newDealerSubType;
    /**
     * 申请日期
     * 
     */
    @JsonProperty("appDate")
    private String appDate;
    /**
     * 申请文件号
     * 
     */
    @JsonProperty("appDocNo")
    private String appDocNo;
    /**
     * 申请输入时间
     * 
     */
    @JsonProperty("appEntryTime")
    private String appEntryTime;
    /**
     * 申请输入用户
     * 
     */
    @JsonProperty("appEntryBy")
    private String appEntryBy;
    /**
     * 申请复核时间
     * 
     */
    @JsonProperty("appCheckedTime")
    private String appCheckedTime;
    /**
     * 申请复核用户
     * 
     */
    @JsonProperty("appCheckedBy")
    private String appCheckedBy;
    /**
     * 申请复核备注
     * 
     */
    @JsonProperty("appCheckedMemo")
    private String appCheckedMemo;
    /**
     * 申请审核时间
     * 
     */
    @JsonProperty("appAuditedTime")
    private String appAuditedTime;
    /**
     * 申请审核人
     * 
     */
    @JsonProperty("appAuditedBy")
    private String appAuditedBy;
    /**
     * 申请审核备注
     * 
     */
    @JsonProperty("appAuditedMemo")
    private String appAuditedMemo;
    /**
     * 处理转换状态
     * 
     */
    @JsonProperty("gdsTranStatus")
    private String gdsTranStatus;
    /**
     * 处理转换的回单月
     * 
     */
    @JsonProperty("gdsTranSoPeriod")
    private String gdsTranSoPeriod;
    /**
     * 处理转换的生效月
     * 
     */
    @JsonProperty("gdsTranEffectivePeriod")
    private String gdsTranEffectivePeriod;
    /**
     * 处理转换时间
     * 
     */
    @JsonProperty("gdsTranTime")
    private String gdsTranTime;
    /**
     * 处理转换人
     * 
     */
    @JsonProperty("gdsTranBy")
    private String gdsTranBy;
    /**
     * 处理转换备注
     * 
     */
    @JsonProperty("gdsTranMemo")
    private String gdsTranMemo;
    /**
     * 源机构处理标志
     * 
     */
    @JsonProperty("oldOrgTranFlag")
    private String oldOrgTranFlag;
    /**
     * 源机构处理时间
     * 
     */
    @JsonProperty("oldOrgTranTime")
    private String oldOrgTranTime;
    /**
     * 源机构处理用户
     * 
     */
    @JsonProperty("oldOrgTranBy")
    private String oldOrgTranBy;
    /**
     * 源机构处理备注
     * 
     */
    @JsonProperty("oldOrgTranMemo")
    private String oldOrgTranMemo;
    /**
     * 新机构处理标志
     * 
     */
    @JsonProperty("newOrgTranFlag")
    private String newOrgTranFlag;
    /**
     * 新机构处理时间
     * 
     */
    @JsonProperty("newOrgTranTime")
    private String newOrgTranTime;
    /**
     * 新机构处理用户
     * 
     */
    @JsonProperty("newOrgTranBy")
    private String newOrgTranBy;
    /**
     * 新机构处理备注
     * 
     */
    @JsonProperty("newOrgTranMemo")
    private String newOrgTranMemo;
    /**
     * 申请人姓名
     * 
     */
    @JsonProperty("appFullName")
    private String appFullName;
    /**
     * 申请人姓
     * 
     */
    @JsonProperty("appLastName")
    private String appLastName;
    /**
     * 申请人名
     * 
     */
    @JsonProperty("appFirstName")
    private String appFirstName;
    /**
     * 申请证件国别
     * 
     */
    @JsonProperty("appCertificateNationCode")
    private String appCertificateNationCode;
    /**
     * 申请证件类型
     * 
     */
    @JsonProperty("appCertificateType")
    private String appCertificateType;
    /**
     * 证件编号
     * 
     */
    @JsonProperty("appCertificateNo")
    private String appCertificateNo;
    /**
     * 新销售分公司
     * 
     */
    @JsonProperty("newSaleBranchNo")
    private String newSaleBranchNo;
    /**
     * 新销售片区代号
     * 
     */
    @JsonProperty("newSaleZoneNo")
    private String newSaleZoneNo;
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
     * 创建时间
     * 
     */
    @JsonProperty("createTime")
    private String createTime;
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
    public ApplicationsGETResponse() {
    }

    /**
     * 
     * @param appCheckedBy
     * @param dealerNo
     * @param gdsTranMemo
     * @param appDate
     * @param newSaleOrgCode
     * @param newOrgTranMemo
     * @param gdsTranStatus
     * @param gdsTranBy
     * @param oldOrgTranFlag
     * @param appLastName
     * @param appCertificateNo
     * @param appCertificateType
     * @param newOrgTranTime
     * @param appCertificateNationCode
     * @param id
     * @param appFullName
     * @param newSaleZoneNo
     * @param newDealerType
     * @param gdsTranSoPeriod
     * @param appEntryTime
     * @param newOrgTranBy
     * @param comments
     * @param appDocNo
     * @param appAuditedTime
     * @param appCheckedTime
     * @param oldOrgTranMemo
     * @param oldSaleOrgCode
     * @param appFirstName
     * @param appAuditedBy
     * @param newSaleBranchNo
     * @param appEntryBy
     * @param appAuditedMemo
     * @param gdsTranEffectivePeriod
     * @param createBy
     * @param appCheckedMemo
     * @param createTime
     * @param gdsTranTime
     * @param newOrgTranFlag
     * @param newDealerSubType
     * @param oldOrgTranBy
     * @param oldOrgTranTime
     * @param lastUpdateBy
     * @param lastUpdateTime
     */
    public ApplicationsGETResponse(String dealerNo, String oldSaleOrgCode, String newSaleOrgCode, String newDealerType, String newDealerSubType, String appDate, String appDocNo, String appEntryTime, String appEntryBy, String appCheckedTime, String appCheckedBy, String appCheckedMemo, String appAuditedTime, String appAuditedBy, String appAuditedMemo, String gdsTranStatus, String gdsTranSoPeriod, String gdsTranEffectivePeriod, String gdsTranTime, String gdsTranBy, String gdsTranMemo, String oldOrgTranFlag, String oldOrgTranTime, String oldOrgTranBy, String oldOrgTranMemo, String newOrgTranFlag, String newOrgTranTime, String newOrgTranBy, String newOrgTranMemo, String appFullName, String appLastName, String appFirstName, String appCertificateNationCode, String appCertificateType, String appCertificateNo, String newSaleBranchNo, String newSaleZoneNo, String comments, Long id, String createBy, String createTime, String lastUpdateBy, String lastUpdateTime) {
        this.dealerNo = dealerNo;
        this.oldSaleOrgCode = oldSaleOrgCode;
        this.newSaleOrgCode = newSaleOrgCode;
        this.newDealerType = newDealerType;
        this.newDealerSubType = newDealerSubType;
        this.appDate = appDate;
        this.appDocNo = appDocNo;
        this.appEntryTime = appEntryTime;
        this.appEntryBy = appEntryBy;
        this.appCheckedTime = appCheckedTime;
        this.appCheckedBy = appCheckedBy;
        this.appCheckedMemo = appCheckedMemo;
        this.appAuditedTime = appAuditedTime;
        this.appAuditedBy = appAuditedBy;
        this.appAuditedMemo = appAuditedMemo;
        this.gdsTranStatus = gdsTranStatus;
        this.gdsTranSoPeriod = gdsTranSoPeriod;
        this.gdsTranEffectivePeriod = gdsTranEffectivePeriod;
        this.gdsTranTime = gdsTranTime;
        this.gdsTranBy = gdsTranBy;
        this.gdsTranMemo = gdsTranMemo;
        this.oldOrgTranFlag = oldOrgTranFlag;
        this.oldOrgTranTime = oldOrgTranTime;
        this.oldOrgTranBy = oldOrgTranBy;
        this.oldOrgTranMemo = oldOrgTranMemo;
        this.newOrgTranFlag = newOrgTranFlag;
        this.newOrgTranTime = newOrgTranTime;
        this.newOrgTranBy = newOrgTranBy;
        this.newOrgTranMemo = newOrgTranMemo;
        this.appFullName = appFullName;
        this.appLastName = appLastName;
        this.appFirstName = appFirstName;
        this.appCertificateNationCode = appCertificateNationCode;
        this.appCertificateType = appCertificateType;
        this.appCertificateNo = appCertificateNo;
        this.newSaleBranchNo = newSaleBranchNo;
        this.newSaleZoneNo = newSaleZoneNo;
        this.comments = comments;
        this.id = id;
        this.createBy = createBy;
        this.createTime = createTime;
        this.lastUpdateBy = lastUpdateBy;
        this.lastUpdateTime = lastUpdateTime;
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

    public ApplicationsGETResponse withDealerNo(String dealerNo) {
        this.dealerNo = dealerNo;
        return this;
    }

    /**
     * 源销售机构
     * 
     * @return
     *     The oldSaleOrgCode
     */
    @JsonProperty("oldSaleOrgCode")
    public String getOldSaleOrgCode() {
        return oldSaleOrgCode;
    }

    /**
     * 源销售机构
     * 
     * @param oldSaleOrgCode
     *     The oldSaleOrgCode
     */
    @JsonProperty("oldSaleOrgCode")
    public void setOldSaleOrgCode(String oldSaleOrgCode) {
        this.oldSaleOrgCode = oldSaleOrgCode;
    }

    public ApplicationsGETResponse withOldSaleOrgCode(String oldSaleOrgCode) {
        this.oldSaleOrgCode = oldSaleOrgCode;
        return this;
    }

    /**
     * 新销售机构
     * 
     * @return
     *     The newSaleOrgCode
     */
    @JsonProperty("newSaleOrgCode")
    public String getNewSaleOrgCode() {
        return newSaleOrgCode;
    }

    /**
     * 新销售机构
     * 
     * @param newSaleOrgCode
     *     The newSaleOrgCode
     */
    @JsonProperty("newSaleOrgCode")
    public void setNewSaleOrgCode(String newSaleOrgCode) {
        this.newSaleOrgCode = newSaleOrgCode;
    }

    public ApplicationsGETResponse withNewSaleOrgCode(String newSaleOrgCode) {
        this.newSaleOrgCode = newSaleOrgCode;
        return this;
    }

    /**
     * 新身份类型
     * 
     * @return
     *     The newDealerType
     */
    @JsonProperty("newDealerType")
    public String getNewDealerType() {
        return newDealerType;
    }

    /**
     * 新身份类型
     * 
     * @param newDealerType
     *     The newDealerType
     */
    @JsonProperty("newDealerType")
    public void setNewDealerType(String newDealerType) {
        this.newDealerType = newDealerType;
    }

    public ApplicationsGETResponse withNewDealerType(String newDealerType) {
        this.newDealerType = newDealerType;
        return this;
    }

    /**
     * 新身份子类型
     * 
     * @return
     *     The newDealerSubType
     */
    @JsonProperty("newDealerSubType")
    public String getNewDealerSubType() {
        return newDealerSubType;
    }

    /**
     * 新身份子类型
     * 
     * @param newDealerSubType
     *     The newDealerSubType
     */
    @JsonProperty("newDealerSubType")
    public void setNewDealerSubType(String newDealerSubType) {
        this.newDealerSubType = newDealerSubType;
    }

    public ApplicationsGETResponse withNewDealerSubType(String newDealerSubType) {
        this.newDealerSubType = newDealerSubType;
        return this;
    }

    /**
     * 申请日期
     * 
     * @return
     *     The appDate
     */
    @JsonProperty("appDate")
    public String getAppDate() {
        return appDate;
    }

    /**
     * 申请日期
     * 
     * @param appDate
     *     The appDate
     */
    @JsonProperty("appDate")
    public void setAppDate(String appDate) {
        this.appDate = appDate;
    }

    public ApplicationsGETResponse withAppDate(String appDate) {
        this.appDate = appDate;
        return this;
    }

    /**
     * 申请文件号
     * 
     * @return
     *     The appDocNo
     */
    @JsonProperty("appDocNo")
    public String getAppDocNo() {
        return appDocNo;
    }

    /**
     * 申请文件号
     * 
     * @param appDocNo
     *     The appDocNo
     */
    @JsonProperty("appDocNo")
    public void setAppDocNo(String appDocNo) {
        this.appDocNo = appDocNo;
    }

    public ApplicationsGETResponse withAppDocNo(String appDocNo) {
        this.appDocNo = appDocNo;
        return this;
    }

    /**
     * 申请输入时间
     * 
     * @return
     *     The appEntryTime
     */
    @JsonProperty("appEntryTime")
    public String getAppEntryTime() {
        return appEntryTime;
    }

    /**
     * 申请输入时间
     * 
     * @param appEntryTime
     *     The appEntryTime
     */
    @JsonProperty("appEntryTime")
    public void setAppEntryTime(String appEntryTime) {
        this.appEntryTime = appEntryTime;
    }

    public ApplicationsGETResponse withAppEntryTime(String appEntryTime) {
        this.appEntryTime = appEntryTime;
        return this;
    }

    /**
     * 申请输入用户
     * 
     * @return
     *     The appEntryBy
     */
    @JsonProperty("appEntryBy")
    public String getAppEntryBy() {
        return appEntryBy;
    }

    /**
     * 申请输入用户
     * 
     * @param appEntryBy
     *     The appEntryBy
     */
    @JsonProperty("appEntryBy")
    public void setAppEntryBy(String appEntryBy) {
        this.appEntryBy = appEntryBy;
    }

    public ApplicationsGETResponse withAppEntryBy(String appEntryBy) {
        this.appEntryBy = appEntryBy;
        return this;
    }

    /**
     * 申请复核时间
     * 
     * @return
     *     The appCheckedTime
     */
    @JsonProperty("appCheckedTime")
    public String getAppCheckedTime() {
        return appCheckedTime;
    }

    /**
     * 申请复核时间
     * 
     * @param appCheckedTime
     *     The appCheckedTime
     */
    @JsonProperty("appCheckedTime")
    public void setAppCheckedTime(String appCheckedTime) {
        this.appCheckedTime = appCheckedTime;
    }

    public ApplicationsGETResponse withAppCheckedTime(String appCheckedTime) {
        this.appCheckedTime = appCheckedTime;
        return this;
    }

    /**
     * 申请复核用户
     * 
     * @return
     *     The appCheckedBy
     */
    @JsonProperty("appCheckedBy")
    public String getAppCheckedBy() {
        return appCheckedBy;
    }

    /**
     * 申请复核用户
     * 
     * @param appCheckedBy
     *     The appCheckedBy
     */
    @JsonProperty("appCheckedBy")
    public void setAppCheckedBy(String appCheckedBy) {
        this.appCheckedBy = appCheckedBy;
    }

    public ApplicationsGETResponse withAppCheckedBy(String appCheckedBy) {
        this.appCheckedBy = appCheckedBy;
        return this;
    }

    /**
     * 申请复核备注
     * 
     * @return
     *     The appCheckedMemo
     */
    @JsonProperty("appCheckedMemo")
    public String getAppCheckedMemo() {
        return appCheckedMemo;
    }

    /**
     * 申请复核备注
     * 
     * @param appCheckedMemo
     *     The appCheckedMemo
     */
    @JsonProperty("appCheckedMemo")
    public void setAppCheckedMemo(String appCheckedMemo) {
        this.appCheckedMemo = appCheckedMemo;
    }

    public ApplicationsGETResponse withAppCheckedMemo(String appCheckedMemo) {
        this.appCheckedMemo = appCheckedMemo;
        return this;
    }

    /**
     * 申请审核时间
     * 
     * @return
     *     The appAuditedTime
     */
    @JsonProperty("appAuditedTime")
    public String getAppAuditedTime() {
        return appAuditedTime;
    }

    /**
     * 申请审核时间
     * 
     * @param appAuditedTime
     *     The appAuditedTime
     */
    @JsonProperty("appAuditedTime")
    public void setAppAuditedTime(String appAuditedTime) {
        this.appAuditedTime = appAuditedTime;
    }

    public ApplicationsGETResponse withAppAuditedTime(String appAuditedTime) {
        this.appAuditedTime = appAuditedTime;
        return this;
    }

    /**
     * 申请审核人
     * 
     * @return
     *     The appAuditedBy
     */
    @JsonProperty("appAuditedBy")
    public String getAppAuditedBy() {
        return appAuditedBy;
    }

    /**
     * 申请审核人
     * 
     * @param appAuditedBy
     *     The appAuditedBy
     */
    @JsonProperty("appAuditedBy")
    public void setAppAuditedBy(String appAuditedBy) {
        this.appAuditedBy = appAuditedBy;
    }

    public ApplicationsGETResponse withAppAuditedBy(String appAuditedBy) {
        this.appAuditedBy = appAuditedBy;
        return this;
    }

    /**
     * 申请审核备注
     * 
     * @return
     *     The appAuditedMemo
     */
    @JsonProperty("appAuditedMemo")
    public String getAppAuditedMemo() {
        return appAuditedMemo;
    }

    /**
     * 申请审核备注
     * 
     * @param appAuditedMemo
     *     The appAuditedMemo
     */
    @JsonProperty("appAuditedMemo")
    public void setAppAuditedMemo(String appAuditedMemo) {
        this.appAuditedMemo = appAuditedMemo;
    }

    public ApplicationsGETResponse withAppAuditedMemo(String appAuditedMemo) {
        this.appAuditedMemo = appAuditedMemo;
        return this;
    }

    /**
     * 处理转换状态
     * 
     * @return
     *     The gdsTranStatus
     */
    @JsonProperty("gdsTranStatus")
    public String getGdsTranStatus() {
        return gdsTranStatus;
    }

    /**
     * 处理转换状态
     * 
     * @param gdsTranStatus
     *     The gdsTranStatus
     */
    @JsonProperty("gdsTranStatus")
    public void setGdsTranStatus(String gdsTranStatus) {
        this.gdsTranStatus = gdsTranStatus;
    }

    public ApplicationsGETResponse withGdsTranStatus(String gdsTranStatus) {
        this.gdsTranStatus = gdsTranStatus;
        return this;
    }

    /**
     * 处理转换的回单月
     * 
     * @return
     *     The gdsTranSoPeriod
     */
    @JsonProperty("gdsTranSoPeriod")
    public String getGdsTranSoPeriod() {
        return gdsTranSoPeriod;
    }

    /**
     * 处理转换的回单月
     * 
     * @param gdsTranSoPeriod
     *     The gdsTranSoPeriod
     */
    @JsonProperty("gdsTranSoPeriod")
    public void setGdsTranSoPeriod(String gdsTranSoPeriod) {
        this.gdsTranSoPeriod = gdsTranSoPeriod;
    }

    public ApplicationsGETResponse withGdsTranSoPeriod(String gdsTranSoPeriod) {
        this.gdsTranSoPeriod = gdsTranSoPeriod;
        return this;
    }

    /**
     * 处理转换的生效月
     * 
     * @return
     *     The gdsTranEffectivePeriod
     */
    @JsonProperty("gdsTranEffectivePeriod")
    public String getGdsTranEffectivePeriod() {
        return gdsTranEffectivePeriod;
    }

    /**
     * 处理转换的生效月
     * 
     * @param gdsTranEffectivePeriod
     *     The gdsTranEffectivePeriod
     */
    @JsonProperty("gdsTranEffectivePeriod")
    public void setGdsTranEffectivePeriod(String gdsTranEffectivePeriod) {
        this.gdsTranEffectivePeriod = gdsTranEffectivePeriod;
    }

    public ApplicationsGETResponse withGdsTranEffectivePeriod(String gdsTranEffectivePeriod) {
        this.gdsTranEffectivePeriod = gdsTranEffectivePeriod;
        return this;
    }

    /**
     * 处理转换时间
     * 
     * @return
     *     The gdsTranTime
     */
    @JsonProperty("gdsTranTime")
    public String getGdsTranTime() {
        return gdsTranTime;
    }

    /**
     * 处理转换时间
     * 
     * @param gdsTranTime
     *     The gdsTranTime
     */
    @JsonProperty("gdsTranTime")
    public void setGdsTranTime(String gdsTranTime) {
        this.gdsTranTime = gdsTranTime;
    }

    public ApplicationsGETResponse withGdsTranTime(String gdsTranTime) {
        this.gdsTranTime = gdsTranTime;
        return this;
    }

    /**
     * 处理转换人
     * 
     * @return
     *     The gdsTranBy
     */
    @JsonProperty("gdsTranBy")
    public String getGdsTranBy() {
        return gdsTranBy;
    }

    /**
     * 处理转换人
     * 
     * @param gdsTranBy
     *     The gdsTranBy
     */
    @JsonProperty("gdsTranBy")
    public void setGdsTranBy(String gdsTranBy) {
        this.gdsTranBy = gdsTranBy;
    }

    public ApplicationsGETResponse withGdsTranBy(String gdsTranBy) {
        this.gdsTranBy = gdsTranBy;
        return this;
    }

    /**
     * 处理转换备注
     * 
     * @return
     *     The gdsTranMemo
     */
    @JsonProperty("gdsTranMemo")
    public String getGdsTranMemo() {
        return gdsTranMemo;
    }

    /**
     * 处理转换备注
     * 
     * @param gdsTranMemo
     *     The gdsTranMemo
     */
    @JsonProperty("gdsTranMemo")
    public void setGdsTranMemo(String gdsTranMemo) {
        this.gdsTranMemo = gdsTranMemo;
    }

    public ApplicationsGETResponse withGdsTranMemo(String gdsTranMemo) {
        this.gdsTranMemo = gdsTranMemo;
        return this;
    }

    /**
     * 源机构处理标志
     * 
     * @return
     *     The oldOrgTranFlag
     */
    @JsonProperty("oldOrgTranFlag")
    public String getOldOrgTranFlag() {
        return oldOrgTranFlag;
    }

    /**
     * 源机构处理标志
     * 
     * @param oldOrgTranFlag
     *     The oldOrgTranFlag
     */
    @JsonProperty("oldOrgTranFlag")
    public void setOldOrgTranFlag(String oldOrgTranFlag) {
        this.oldOrgTranFlag = oldOrgTranFlag;
    }

    public ApplicationsGETResponse withOldOrgTranFlag(String oldOrgTranFlag) {
        this.oldOrgTranFlag = oldOrgTranFlag;
        return this;
    }

    /**
     * 源机构处理时间
     * 
     * @return
     *     The oldOrgTranTime
     */
    @JsonProperty("oldOrgTranTime")
    public String getOldOrgTranTime() {
        return oldOrgTranTime;
    }

    /**
     * 源机构处理时间
     * 
     * @param oldOrgTranTime
     *     The oldOrgTranTime
     */
    @JsonProperty("oldOrgTranTime")
    public void setOldOrgTranTime(String oldOrgTranTime) {
        this.oldOrgTranTime = oldOrgTranTime;
    }

    public ApplicationsGETResponse withOldOrgTranTime(String oldOrgTranTime) {
        this.oldOrgTranTime = oldOrgTranTime;
        return this;
    }

    /**
     * 源机构处理用户
     * 
     * @return
     *     The oldOrgTranBy
     */
    @JsonProperty("oldOrgTranBy")
    public String getOldOrgTranBy() {
        return oldOrgTranBy;
    }

    /**
     * 源机构处理用户
     * 
     * @param oldOrgTranBy
     *     The oldOrgTranBy
     */
    @JsonProperty("oldOrgTranBy")
    public void setOldOrgTranBy(String oldOrgTranBy) {
        this.oldOrgTranBy = oldOrgTranBy;
    }

    public ApplicationsGETResponse withOldOrgTranBy(String oldOrgTranBy) {
        this.oldOrgTranBy = oldOrgTranBy;
        return this;
    }

    /**
     * 源机构处理备注
     * 
     * @return
     *     The oldOrgTranMemo
     */
    @JsonProperty("oldOrgTranMemo")
    public String getOldOrgTranMemo() {
        return oldOrgTranMemo;
    }

    /**
     * 源机构处理备注
     * 
     * @param oldOrgTranMemo
     *     The oldOrgTranMemo
     */
    @JsonProperty("oldOrgTranMemo")
    public void setOldOrgTranMemo(String oldOrgTranMemo) {
        this.oldOrgTranMemo = oldOrgTranMemo;
    }

    public ApplicationsGETResponse withOldOrgTranMemo(String oldOrgTranMemo) {
        this.oldOrgTranMemo = oldOrgTranMemo;
        return this;
    }

    /**
     * 新机构处理标志
     * 
     * @return
     *     The newOrgTranFlag
     */
    @JsonProperty("newOrgTranFlag")
    public String getNewOrgTranFlag() {
        return newOrgTranFlag;
    }

    /**
     * 新机构处理标志
     * 
     * @param newOrgTranFlag
     *     The newOrgTranFlag
     */
    @JsonProperty("newOrgTranFlag")
    public void setNewOrgTranFlag(String newOrgTranFlag) {
        this.newOrgTranFlag = newOrgTranFlag;
    }

    public ApplicationsGETResponse withNewOrgTranFlag(String newOrgTranFlag) {
        this.newOrgTranFlag = newOrgTranFlag;
        return this;
    }

    /**
     * 新机构处理时间
     * 
     * @return
     *     The newOrgTranTime
     */
    @JsonProperty("newOrgTranTime")
    public String getNewOrgTranTime() {
        return newOrgTranTime;
    }

    /**
     * 新机构处理时间
     * 
     * @param newOrgTranTime
     *     The newOrgTranTime
     */
    @JsonProperty("newOrgTranTime")
    public void setNewOrgTranTime(String newOrgTranTime) {
        this.newOrgTranTime = newOrgTranTime;
    }

    public ApplicationsGETResponse withNewOrgTranTime(String newOrgTranTime) {
        this.newOrgTranTime = newOrgTranTime;
        return this;
    }

    /**
     * 新机构处理用户
     * 
     * @return
     *     The newOrgTranBy
     */
    @JsonProperty("newOrgTranBy")
    public String getNewOrgTranBy() {
        return newOrgTranBy;
    }

    /**
     * 新机构处理用户
     * 
     * @param newOrgTranBy
     *     The newOrgTranBy
     */
    @JsonProperty("newOrgTranBy")
    public void setNewOrgTranBy(String newOrgTranBy) {
        this.newOrgTranBy = newOrgTranBy;
    }

    public ApplicationsGETResponse withNewOrgTranBy(String newOrgTranBy) {
        this.newOrgTranBy = newOrgTranBy;
        return this;
    }

    /**
     * 新机构处理备注
     * 
     * @return
     *     The newOrgTranMemo
     */
    @JsonProperty("newOrgTranMemo")
    public String getNewOrgTranMemo() {
        return newOrgTranMemo;
    }

    /**
     * 新机构处理备注
     * 
     * @param newOrgTranMemo
     *     The newOrgTranMemo
     */
    @JsonProperty("newOrgTranMemo")
    public void setNewOrgTranMemo(String newOrgTranMemo) {
        this.newOrgTranMemo = newOrgTranMemo;
    }

    public ApplicationsGETResponse withNewOrgTranMemo(String newOrgTranMemo) {
        this.newOrgTranMemo = newOrgTranMemo;
        return this;
    }

    /**
     * 申请人姓名
     * 
     * @return
     *     The appFullName
     */
    @JsonProperty("appFullName")
    public String getAppFullName() {
        return appFullName;
    }

    /**
     * 申请人姓名
     * 
     * @param appFullName
     *     The appFullName
     */
    @JsonProperty("appFullName")
    public void setAppFullName(String appFullName) {
        this.appFullName = appFullName;
    }

    public ApplicationsGETResponse withAppFullName(String appFullName) {
        this.appFullName = appFullName;
        return this;
    }

    /**
     * 申请人姓
     * 
     * @return
     *     The appLastName
     */
    @JsonProperty("appLastName")
    public String getAppLastName() {
        return appLastName;
    }

    /**
     * 申请人姓
     * 
     * @param appLastName
     *     The appLastName
     */
    @JsonProperty("appLastName")
    public void setAppLastName(String appLastName) {
        this.appLastName = appLastName;
    }

    public ApplicationsGETResponse withAppLastName(String appLastName) {
        this.appLastName = appLastName;
        return this;
    }

    /**
     * 申请人名
     * 
     * @return
     *     The appFirstName
     */
    @JsonProperty("appFirstName")
    public String getAppFirstName() {
        return appFirstName;
    }

    /**
     * 申请人名
     * 
     * @param appFirstName
     *     The appFirstName
     */
    @JsonProperty("appFirstName")
    public void setAppFirstName(String appFirstName) {
        this.appFirstName = appFirstName;
    }

    public ApplicationsGETResponse withAppFirstName(String appFirstName) {
        this.appFirstName = appFirstName;
        return this;
    }

    /**
     * 申请证件国别
     * 
     * @return
     *     The appCertificateNationCode
     */
    @JsonProperty("appCertificateNationCode")
    public String getAppCertificateNationCode() {
        return appCertificateNationCode;
    }

    /**
     * 申请证件国别
     * 
     * @param appCertificateNationCode
     *     The appCertificateNationCode
     */
    @JsonProperty("appCertificateNationCode")
    public void setAppCertificateNationCode(String appCertificateNationCode) {
        this.appCertificateNationCode = appCertificateNationCode;
    }

    public ApplicationsGETResponse withAppCertificateNationCode(String appCertificateNationCode) {
        this.appCertificateNationCode = appCertificateNationCode;
        return this;
    }

    /**
     * 申请证件类型
     * 
     * @return
     *     The appCertificateType
     */
    @JsonProperty("appCertificateType")
    public String getAppCertificateType() {
        return appCertificateType;
    }

    /**
     * 申请证件类型
     * 
     * @param appCertificateType
     *     The appCertificateType
     */
    @JsonProperty("appCertificateType")
    public void setAppCertificateType(String appCertificateType) {
        this.appCertificateType = appCertificateType;
    }

    public ApplicationsGETResponse withAppCertificateType(String appCertificateType) {
        this.appCertificateType = appCertificateType;
        return this;
    }

    /**
     * 证件编号
     * 
     * @return
     *     The appCertificateNo
     */
    @JsonProperty("appCertificateNo")
    public String getAppCertificateNo() {
        return appCertificateNo;
    }

    /**
     * 证件编号
     * 
     * @param appCertificateNo
     *     The appCertificateNo
     */
    @JsonProperty("appCertificateNo")
    public void setAppCertificateNo(String appCertificateNo) {
        this.appCertificateNo = appCertificateNo;
    }

    public ApplicationsGETResponse withAppCertificateNo(String appCertificateNo) {
        this.appCertificateNo = appCertificateNo;
        return this;
    }

    /**
     * 新销售分公司
     * 
     * @return
     *     The newSaleBranchNo
     */
    @JsonProperty("newSaleBranchNo")
    public String getNewSaleBranchNo() {
        return newSaleBranchNo;
    }

    /**
     * 新销售分公司
     * 
     * @param newSaleBranchNo
     *     The newSaleBranchNo
     */
    @JsonProperty("newSaleBranchNo")
    public void setNewSaleBranchNo(String newSaleBranchNo) {
        this.newSaleBranchNo = newSaleBranchNo;
    }

    public ApplicationsGETResponse withNewSaleBranchNo(String newSaleBranchNo) {
        this.newSaleBranchNo = newSaleBranchNo;
        return this;
    }

    /**
     * 新销售片区代号
     * 
     * @return
     *     The newSaleZoneNo
     */
    @JsonProperty("newSaleZoneNo")
    public String getNewSaleZoneNo() {
        return newSaleZoneNo;
    }

    /**
     * 新销售片区代号
     * 
     * @param newSaleZoneNo
     *     The newSaleZoneNo
     */
    @JsonProperty("newSaleZoneNo")
    public void setNewSaleZoneNo(String newSaleZoneNo) {
        this.newSaleZoneNo = newSaleZoneNo;
    }

    public ApplicationsGETResponse withNewSaleZoneNo(String newSaleZoneNo) {
        this.newSaleZoneNo = newSaleZoneNo;
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

    public ApplicationsGETResponse withComments(String comments) {
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

    public ApplicationsGETResponse withId(Long id) {
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

    public ApplicationsGETResponse withCreateBy(String createBy) {
        this.createBy = createBy;
        return this;
    }

    /**
     * 创建时间
     * 
     * @return
     *     The createTime
     */
    @JsonProperty("createTime")
    public String getCreateTime() {
        return createTime;
    }

    /**
     * 创建时间
     * 
     * @param createTime
     *     The createTime
     */
    @JsonProperty("createTime")
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public ApplicationsGETResponse withCreateTime(String createTime) {
        this.createTime = createTime;
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

    public ApplicationsGETResponse withLastUpdateBy(String lastUpdateBy) {
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

    public ApplicationsGETResponse withLastUpdateTime(String lastUpdateTime) {
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

    public ApplicationsGETResponse withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(dealerNo).append(oldSaleOrgCode).append(newSaleOrgCode).append(newDealerType).append(newDealerSubType).append(appDate).append(appDocNo).append(appEntryTime).append(appEntryBy).append(appCheckedTime).append(appCheckedBy).append(appCheckedMemo).append(appAuditedTime).append(appAuditedBy).append(appAuditedMemo).append(gdsTranStatus).append(gdsTranSoPeriod).append(gdsTranEffectivePeriod).append(gdsTranTime).append(gdsTranBy).append(gdsTranMemo).append(oldOrgTranFlag).append(oldOrgTranTime).append(oldOrgTranBy).append(oldOrgTranMemo).append(newOrgTranFlag).append(newOrgTranTime).append(newOrgTranBy).append(newOrgTranMemo).append(appFullName).append(appLastName).append(appFirstName).append(appCertificateNationCode).append(appCertificateType).append(appCertificateNo).append(newSaleBranchNo).append(newSaleZoneNo).append(comments).append(id).append(createBy).append(createTime).append(lastUpdateBy).append(lastUpdateTime).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ApplicationsGETResponse) == false) {
            return false;
        }
        ApplicationsGETResponse rhs = ((ApplicationsGETResponse) other);
        return new EqualsBuilder().append(dealerNo, rhs.dealerNo).append(oldSaleOrgCode, rhs.oldSaleOrgCode).append(newSaleOrgCode, rhs.newSaleOrgCode).append(newDealerType, rhs.newDealerType).append(newDealerSubType, rhs.newDealerSubType).append(appDate, rhs.appDate).append(appDocNo, rhs.appDocNo).append(appEntryTime, rhs.appEntryTime).append(appEntryBy, rhs.appEntryBy).append(appCheckedTime, rhs.appCheckedTime).append(appCheckedBy, rhs.appCheckedBy).append(appCheckedMemo, rhs.appCheckedMemo).append(appAuditedTime, rhs.appAuditedTime).append(appAuditedBy, rhs.appAuditedBy).append(appAuditedMemo, rhs.appAuditedMemo).append(gdsTranStatus, rhs.gdsTranStatus).append(gdsTranSoPeriod, rhs.gdsTranSoPeriod).append(gdsTranEffectivePeriod, rhs.gdsTranEffectivePeriod).append(gdsTranTime, rhs.gdsTranTime).append(gdsTranBy, rhs.gdsTranBy).append(gdsTranMemo, rhs.gdsTranMemo).append(oldOrgTranFlag, rhs.oldOrgTranFlag).append(oldOrgTranTime, rhs.oldOrgTranTime).append(oldOrgTranBy, rhs.oldOrgTranBy).append(oldOrgTranMemo, rhs.oldOrgTranMemo).append(newOrgTranFlag, rhs.newOrgTranFlag).append(newOrgTranTime, rhs.newOrgTranTime).append(newOrgTranBy, rhs.newOrgTranBy).append(newOrgTranMemo, rhs.newOrgTranMemo).append(appFullName, rhs.appFullName).append(appLastName, rhs.appLastName).append(appFirstName, rhs.appFirstName).append(appCertificateNationCode, rhs.appCertificateNationCode).append(appCertificateType, rhs.appCertificateType).append(appCertificateNo, rhs.appCertificateNo).append(newSaleBranchNo, rhs.newSaleBranchNo).append(newSaleZoneNo, rhs.newSaleZoneNo).append(comments, rhs.comments).append(id, rhs.id).append(createBy, rhs.createBy).append(createTime, rhs.createTime).append(lastUpdateBy, rhs.lastUpdateBy).append(lastUpdateTime, rhs.lastUpdateTime).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
