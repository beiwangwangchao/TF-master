
package com.lkkhpg.dsis.integration.gds.resource.orders.batchSave.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    "soNo",
    "soType",
    "soOrgCode",
    "soDealerNo",
    "soPeriod",
    "soEntryClass",
    "soEntryTime",
    "soEntryBy",
    "orderDealerNo",
    "orderDate",
    "orderAmt",
    "orderDealerName",
    "orderDealerTele",
    "firstSo",
    "refSoNo",
    "localCurrencyCode",
    "localTotalAmt",
    "localTotalEcoupon",
    "localTotalPoint",
    "localTotalRebate",
    "realTimeProcFlag",
    "realTimeProcTime",
    "comments",
    "calcPeriod",
    "adviseNo",
    "localTotalSaleTaxAmt",
    "gsoDetails"
})
public class BatchSavePOSTBody {

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
     * 销售单号
     * 
     */
    @JsonProperty("soNo")
    private String soNo;
    /**
     * 销售单类型
     * 
     */
    @JsonProperty("soType")
    private String soType;
    /**
     * 销售机构代号
     * 
     */
    @JsonProperty("soOrgCode")
    private String soOrgCode;
    /**
     * 销售经销商代码
     * 
     */
    @JsonProperty("soDealerNo")
    private String soDealerNo;
    /**
     * 销售单月份区间
     * 
     */
    @JsonProperty("soPeriod")
    private String soPeriod;
    /**
     * 销售单输入种类
     * 
     */
    @JsonProperty("soEntryClass")
    private String soEntryClass;
    /**
     * 销售单输入时间
     * 
     */
    @JsonProperty("soEntryTime")
    private String soEntryTime;
    /**
     * 销售单输入用户
     * 
     */
    @JsonProperty("soEntryBy")
    private String soEntryBy;
    /**
     * 购货人卡号
     * 
     */
    @JsonProperty("orderDealerNo")
    private String orderDealerNo;
    /**
     * 购货单日期
     * 
     */
    @JsonProperty("orderDate")
    private String orderDate;
    /**
     * 购货单金额
     * 
     */
    @JsonProperty("orderAmt")
    private Double orderAmt;
    /**
     * 购货人姓名
     * 
     */
    @JsonProperty("orderDealerName")
    private String orderDealerName;
    /**
     * 购货人电话
     * 
     */
    @JsonProperty("orderDealerTele")
    private String orderDealerTele;
    /**
     * 是否首单
     * 
     */
    @JsonProperty("firstSo")
    private Boolean firstSo;
    /**
     * 参考销售单号
     * 
     */
    @JsonProperty("refSoNo")
    private String refSoNo;
    /**
     * 本国货币代号
     * 
     */
    @JsonProperty("localCurrencyCode")
    private String localCurrencyCode;
    /**
     * 本国合计总金额
     * 
     */
    @JsonProperty("localTotalAmt")
    private Double localTotalAmt;
    /**
     * 本国合计总电子礼券
     * 
     */
    @JsonProperty("localTotalEcoupon")
    private Double localTotalEcoupon;
    /**
     * 本国合计总点数
     * 
     */
    @JsonProperty("localTotalPoint")
    private Double localTotalPoint;
    /**
     * 本国合计总优惠差额
     * 
     */
    @JsonProperty("localTotalRebate")
    private Double localTotalRebate;
    /**
     * 实时处理标志
     * 
     */
    @JsonProperty("realTimeProcFlag")
    private String realTimeProcFlag;
    /**
     * 实时处理时间
     * 
     */
    @JsonProperty("realTimeProcTime")
    private String realTimeProcTime;
    /**
     * 备注
     * 
     */
    @JsonProperty("comments")
    private String comments;
    /**
     * 计分月份区间
     * 
     */
    @JsonProperty("calcPeriod")
    private String calcPeriod;
    /**
     * 通知號
     * 
     */
    @JsonProperty("adviseNo")
    private String adviseNo;
    /**
     * 计分合计本国合计销售税
     * 
     */
    @JsonProperty("localTotalSaleTaxAmt")
    private Double localTotalSaleTaxAmt;
    @JsonProperty("gsoDetails")
    private List<GsoDetail> gsoDetails = new ArrayList<GsoDetail>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    
    /**
     * 本国销售类型
     * 
     */
    @JsonProperty("localSaleType")
    private String localSaleType;

    /**
     * No args constructor for use in serialization
     * 
     */
    public BatchSavePOSTBody() {
    }

    /**
     * 
     * @param soDealerNo
     * @param gsoDetails
     * @param soNo
     * @param firstSo
     * @param orderAmt
     * @param localTotalAmt
     * @param orderDealerName
     * @param soEntryTime
     * @param calcPeriod
     * @param adviseNo
     * @param refSoNo
     * @param localTotalPoint
     * @param soEntryBy
     * @param localTotalRebate
     * @param realTimeProcFlag
     * @param id
     * @param comments
     * @param orderDealerNo
     * @param localCurrencyCode
     * @param orderDealerTele
     * @param createBy
     * @param soOrgCode
     * @param realTimeProcTime
     * @param soType
     * @param soEntryClass
     * @param localTotalEcoupon
     * @param orderDate
     * @param soPeriod
     * @param lastUpdateBy
     * @param lastUpdateTime
     * @param localTotalSaleTaxAmt
     */
    public BatchSavePOSTBody(Long id, String createBy, String lastUpdateBy, String lastUpdateTime, String soNo, String soType, String soOrgCode, String soDealerNo, String soPeriod, String soEntryClass, String soEntryTime, String soEntryBy, String orderDealerNo, String orderDate, Double orderAmt, String orderDealerName, String orderDealerTele, Boolean firstSo, String refSoNo, String localCurrencyCode, Double localTotalAmt, Double localTotalEcoupon, Double localTotalPoint, Double localTotalRebate, String realTimeProcFlag, String realTimeProcTime, String comments, String calcPeriod, String adviseNo, Double localTotalSaleTaxAmt, List<GsoDetail> gsoDetails) {
        this.id = id;
        this.createBy = createBy;
        this.lastUpdateBy = lastUpdateBy;
        this.lastUpdateTime = lastUpdateTime;
        this.soNo = soNo;
        this.soType = soType;
        this.soOrgCode = soOrgCode;
        this.soDealerNo = soDealerNo;
        this.soPeriod = soPeriod;
        this.soEntryClass = soEntryClass;
        this.soEntryTime = soEntryTime;
        this.soEntryBy = soEntryBy;
        this.orderDealerNo = orderDealerNo;
        this.orderDate = orderDate;
        this.orderAmt = orderAmt;
        this.orderDealerName = orderDealerName;
        this.orderDealerTele = orderDealerTele;
        this.firstSo = firstSo;
        this.refSoNo = refSoNo;
        this.localCurrencyCode = localCurrencyCode;
        this.localTotalAmt = localTotalAmt;
        this.localTotalEcoupon = localTotalEcoupon;
        this.localTotalPoint = localTotalPoint;
        this.localTotalRebate = localTotalRebate;
        this.realTimeProcFlag = realTimeProcFlag;
        this.realTimeProcTime = realTimeProcTime;
        this.comments = comments;
        this.calcPeriod = calcPeriod;
        this.adviseNo = adviseNo;
        this.localTotalSaleTaxAmt = localTotalSaleTaxAmt;
        this.gsoDetails = gsoDetails;
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

    public BatchSavePOSTBody withId(Long id) {
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

    public BatchSavePOSTBody withCreateBy(String createBy) {
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

    public BatchSavePOSTBody withLastUpdateBy(String lastUpdateBy) {
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

    public BatchSavePOSTBody withLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
        return this;
    }

    /**
     * 销售单号
     * 
     * @return
     *     The soNo
     */
    @JsonProperty("soNo")
    public String getSoNo() {
        return soNo;
    }

    /**
     * 销售单号
     * 
     * @param soNo
     *     The soNo
     */
    @JsonProperty("soNo")
    public void setSoNo(String soNo) {
        this.soNo = soNo;
    }

    public BatchSavePOSTBody withSoNo(String soNo) {
        this.soNo = soNo;
        return this;
    }

    /**
     * 销售单类型
     * 
     * @return
     *     The soType
     */
    @JsonProperty("soType")
    public String getSoType() {
        return soType;
    }

    /**
     * 销售单类型
     * 
     * @param soType
     *     The soType
     */
    @JsonProperty("soType")
    public void setSoType(String soType) {
        this.soType = soType;
    }

    public BatchSavePOSTBody withSoType(String soType) {
        this.soType = soType;
        return this;
    }

    /**
     * 销售机构代号
     * 
     * @return
     *     The soOrgCode
     */
    @JsonProperty("soOrgCode")
    public String getSoOrgCode() {
        return soOrgCode;
    }

    /**
     * 销售机构代号
     * 
     * @param soOrgCode
     *     The soOrgCode
     */
    @JsonProperty("soOrgCode")
    public void setSoOrgCode(String soOrgCode) {
        this.soOrgCode = soOrgCode;
    }

    public BatchSavePOSTBody withSoOrgCode(String soOrgCode) {
        this.soOrgCode = soOrgCode;
        return this;
    }

    /**
     * 销售经销商代码
     * 
     * @return
     *     The soDealerNo
     */
    @JsonProperty("soDealerNo")
    public String getSoDealerNo() {
        return soDealerNo;
    }

    /**
     * 销售经销商代码
     * 
     * @param soDealerNo
     *     The soDealerNo
     */
    @JsonProperty("soDealerNo")
    public void setSoDealerNo(String soDealerNo) {
        this.soDealerNo = soDealerNo;
    }

    public BatchSavePOSTBody withSoDealerNo(String soDealerNo) {
        this.soDealerNo = soDealerNo;
        return this;
    }

    /**
     * 销售单月份区间
     * 
     * @return
     *     The soPeriod
     */
    @JsonProperty("soPeriod")
    public String getSoPeriod() {
        return soPeriod;
    }

    /**
     * 销售单月份区间
     * 
     * @param soPeriod
     *     The soPeriod
     */
    @JsonProperty("soPeriod")
    public void setSoPeriod(String soPeriod) {
        this.soPeriod = soPeriod;
    }

    public BatchSavePOSTBody withSoPeriod(String soPeriod) {
        this.soPeriod = soPeriod;
        return this;
    }

    /**
     * 销售单输入种类
     * 
     * @return
     *     The soEntryClass
     */
    @JsonProperty("soEntryClass")
    public String getSoEntryClass() {
        return soEntryClass;
    }

    /**
     * 销售单输入种类
     * 
     * @param soEntryClass
     *     The soEntryClass
     */
    @JsonProperty("soEntryClass")
    public void setSoEntryClass(String soEntryClass) {
        this.soEntryClass = soEntryClass;
    }

    public BatchSavePOSTBody withSoEntryClass(String soEntryClass) {
        this.soEntryClass = soEntryClass;
        return this;
    }

    /**
     * 销售单输入时间
     * 
     * @return
     *     The soEntryTime
     */
    @JsonProperty("soEntryTime")
    public String getSoEntryTime() {
        return soEntryTime;
    }

    /**
     * 销售单输入时间
     * 
     * @param soEntryTime
     *     The soEntryTime
     */
    @JsonProperty("soEntryTime")
    public void setSoEntryTime(String soEntryTime) {
        this.soEntryTime = soEntryTime;
    }

    public BatchSavePOSTBody withSoEntryTime(String soEntryTime) {
        this.soEntryTime = soEntryTime;
        return this;
    }

    /**
     * 销售单输入用户
     * 
     * @return
     *     The soEntryBy
     */
    @JsonProperty("soEntryBy")
    public String getSoEntryBy() {
        return soEntryBy;
    }

    /**
     * 销售单输入用户
     * 
     * @param soEntryBy
     *     The soEntryBy
     */
    @JsonProperty("soEntryBy")
    public void setSoEntryBy(String soEntryBy) {
        this.soEntryBy = soEntryBy;
    }

    public BatchSavePOSTBody withSoEntryBy(String soEntryBy) {
        this.soEntryBy = soEntryBy;
        return this;
    }

    /**
     * 购货人卡号
     * 
     * @return
     *     The orderDealerNo
     */
    @JsonProperty("orderDealerNo")
    public String getOrderDealerNo() {
        return orderDealerNo;
    }

    /**
     * 购货人卡号
     * 
     * @param orderDealerNo
     *     The orderDealerNo
     */
    @JsonProperty("orderDealerNo")
    public void setOrderDealerNo(String orderDealerNo) {
        this.orderDealerNo = orderDealerNo;
    }

    public BatchSavePOSTBody withOrderDealerNo(String orderDealerNo) {
        this.orderDealerNo = orderDealerNo;
        return this;
    }

    /**
     * 购货单日期
     * 
     * @return
     *     The orderDate
     */
    @JsonProperty("orderDate")
    public String getOrderDate() {
        return orderDate;
    }

    /**
     * 购货单日期
     * 
     * @param orderDate
     *     The orderDate
     */
    @JsonProperty("orderDate")
    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public BatchSavePOSTBody withOrderDate(String orderDate) {
        this.orderDate = orderDate;
        return this;
    }

    /**
     * 购货单金额
     * 
     * @return
     *     The orderAmt
     */
    @JsonProperty("orderAmt")
    public Double getOrderAmt() {
        return orderAmt;
    }

    /**
     * 购货单金额
     * 
     * @param orderAmt
     *     The orderAmt
     */
    @JsonProperty("orderAmt")
    public void setOrderAmt(Double orderAmt) {
        this.orderAmt = orderAmt;
    }

    public BatchSavePOSTBody withOrderAmt(Double orderAmt) {
        this.orderAmt = orderAmt;
        return this;
    }

    /**
     * 购货人姓名
     * 
     * @return
     *     The orderDealerName
     */
    @JsonProperty("orderDealerName")
    public String getOrderDealerName() {
        return orderDealerName;
    }

    /**
     * 购货人姓名
     * 
     * @param orderDealerName
     *     The orderDealerName
     */
    @JsonProperty("orderDealerName")
    public void setOrderDealerName(String orderDealerName) {
        this.orderDealerName = orderDealerName;
    }

    public BatchSavePOSTBody withOrderDealerName(String orderDealerName) {
        this.orderDealerName = orderDealerName;
        return this;
    }

    /**
     * 购货人电话
     * 
     * @return
     *     The orderDealerTele
     */
    @JsonProperty("orderDealerTele")
    public String getOrderDealerTele() {
        return orderDealerTele;
    }

    /**
     * 购货人电话
     * 
     * @param orderDealerTele
     *     The orderDealerTele
     */
    @JsonProperty("orderDealerTele")
    public void setOrderDealerTele(String orderDealerTele) {
        this.orderDealerTele = orderDealerTele;
    }

    public BatchSavePOSTBody withOrderDealerTele(String orderDealerTele) {
        this.orderDealerTele = orderDealerTele;
        return this;
    }

    /**
     * 是否首单
     * 
     * @return
     *     The firstSo
     */
    @JsonProperty("firstSo")
    public Boolean getFirstSo() {
        return firstSo;
    }

    /**
     * 是否首单
     * 
     * @param firstSo
     *     The firstSo
     */
    @JsonProperty("firstSo")
    public void setFirstSo(Boolean firstSo) {
        this.firstSo = firstSo;
    }

    public BatchSavePOSTBody withFirstSo(Boolean firstSo) {
        this.firstSo = firstSo;
        return this;
    }

    /**
     * 参考销售单号
     * 
     * @return
     *     The refSoNo
     */
    @JsonProperty("refSoNo")
    public String getRefSoNo() {
        return refSoNo;
    }

    /**
     * 参考销售单号
     * 
     * @param refSoNo
     *     The refSoNo
     */
    @JsonProperty("refSoNo")
    public void setRefSoNo(String refSoNo) {
        this.refSoNo = refSoNo;
    }

    public BatchSavePOSTBody withRefSoNo(String refSoNo) {
        this.refSoNo = refSoNo;
        return this;
    }

    /**
     * 本国货币代号
     * 
     * @return
     *     The localCurrencyCode
     */
    @JsonProperty("localCurrencyCode")
    public String getLocalCurrencyCode() {
        return localCurrencyCode;
    }

    /**
     * 本国货币代号
     * 
     * @param localCurrencyCode
     *     The localCurrencyCode
     */
    @JsonProperty("localCurrencyCode")
    public void setLocalCurrencyCode(String localCurrencyCode) {
        this.localCurrencyCode = localCurrencyCode;
    }

    public BatchSavePOSTBody withLocalCurrencyCode(String localCurrencyCode) {
        this.localCurrencyCode = localCurrencyCode;
        return this;
    }

    /**
     * 本国合计总金额
     * 
     * @return
     *     The localTotalAmt
     */
    @JsonProperty("localTotalAmt")
    public Double getLocalTotalAmt() {
        return localTotalAmt;
    }

    /**
     * 本国合计总金额
     * 
     * @param localTotalAmt
     *     The localTotalAmt
     */
    @JsonProperty("localTotalAmt")
    public void setLocalTotalAmt(Double localTotalAmt) {
        this.localTotalAmt = localTotalAmt;
    }

    public BatchSavePOSTBody withLocalTotalAmt(Double localTotalAmt) {
        this.localTotalAmt = localTotalAmt;
        return this;
    }

    /**
     * 本国合计总电子礼券
     * 
     * @return
     *     The localTotalEcoupon
     */
    @JsonProperty("localTotalEcoupon")
    public Double getLocalTotalEcoupon() {
        return localTotalEcoupon;
    }

    /**
     * 本国合计总电子礼券
     * 
     * @param localTotalEcoupon
     *     The localTotalEcoupon
     */
    @JsonProperty("localTotalEcoupon")
    public void setLocalTotalEcoupon(Double localTotalEcoupon) {
        this.localTotalEcoupon = localTotalEcoupon;
    }

    public BatchSavePOSTBody withLocalTotalEcoupon(Double localTotalEcoupon) {
        this.localTotalEcoupon = localTotalEcoupon;
        return this;
    }

    /**
     * 本国合计总点数
     * 
     * @return
     *     The localTotalPoint
     */
    @JsonProperty("localTotalPoint")
    public Double getLocalTotalPoint() {
        return localTotalPoint;
    }

    /**
     * 本国合计总点数
     * 
     * @param localTotalPoint
     *     The localTotalPoint
     */
    @JsonProperty("localTotalPoint")
    public void setLocalTotalPoint(Double localTotalPoint) {
        this.localTotalPoint = localTotalPoint;
    }

    public BatchSavePOSTBody withLocalTotalPoint(Double localTotalPoint) {
        this.localTotalPoint = localTotalPoint;
        return this;
    }

    /**
     * 本国合计总优惠差额
     * 
     * @return
     *     The localTotalRebate
     */
    @JsonProperty("localTotalRebate")
    public Double getLocalTotalRebate() {
        return localTotalRebate;
    }

    /**
     * 本国合计总优惠差额
     * 
     * @param localTotalRebate
     *     The localTotalRebate
     */
    @JsonProperty("localTotalRebate")
    public void setLocalTotalRebate(Double localTotalRebate) {
        this.localTotalRebate = localTotalRebate;
    }

    public BatchSavePOSTBody withLocalTotalRebate(Double localTotalRebate) {
        this.localTotalRebate = localTotalRebate;
        return this;
    }

    /**
     * 实时处理标志
     * 
     * @return
     *     The realTimeProcFlag
     */
    @JsonProperty("realTimeProcFlag")
    public String getRealTimeProcFlag() {
        return realTimeProcFlag;
    }

    /**
     * 实时处理标志
     * 
     * @param realTimeProcFlag
     *     The realTimeProcFlag
     */
    @JsonProperty("realTimeProcFlag")
    public void setRealTimeProcFlag(String realTimeProcFlag) {
        this.realTimeProcFlag = realTimeProcFlag;
    }

    public BatchSavePOSTBody withRealTimeProcFlag(String realTimeProcFlag) {
        this.realTimeProcFlag = realTimeProcFlag;
        return this;
    }

    /**
     * 实时处理时间
     * 
     * @return
     *     The realTimeProcTime
     */
    @JsonProperty("realTimeProcTime")
    public String getRealTimeProcTime() {
        return realTimeProcTime;
    }

    /**
     * 实时处理时间
     * 
     * @param realTimeProcTime
     *     The realTimeProcTime
     */
    @JsonProperty("realTimeProcTime")
    public void setRealTimeProcTime(String realTimeProcTime) {
        this.realTimeProcTime = realTimeProcTime;
    }

    public BatchSavePOSTBody withRealTimeProcTime(String realTimeProcTime) {
        this.realTimeProcTime = realTimeProcTime;
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

    public BatchSavePOSTBody withComments(String comments) {
        this.comments = comments;
        return this;
    }

    /**
     * 计分月份区间
     * 
     * @return
     *     The calcPeriod
     */
    @JsonProperty("calcPeriod")
    public String getCalcPeriod() {
        return calcPeriod;
    }

    /**
     * 计分月份区间
     * 
     * @param calcPeriod
     *     The calcPeriod
     */
    @JsonProperty("calcPeriod")
    public void setCalcPeriod(String calcPeriod) {
        this.calcPeriod = calcPeriod;
    }

    public BatchSavePOSTBody withCalcPeriod(String calcPeriod) {
        this.calcPeriod = calcPeriod;
        return this;
    }

    /**
     * 通知號
     * 
     * @return
     *     The adviseNo
     */
    @JsonProperty("adviseNo")
    public String getAdviseNo() {
        return adviseNo;
    }

    /**
     * 通知號
     * 
     * @param adviseNo
     *     The adviseNo
     */
    @JsonProperty("adviseNo")
    public void setAdviseNo(String adviseNo) {
        this.adviseNo = adviseNo;
    }

    public BatchSavePOSTBody withAdviseNo(String adviseNo) {
        this.adviseNo = adviseNo;
        return this;
    }

    /**
     * 计分合计本国合计销售税
     * 
     * @return
     *     The localTotalSaleTaxAmt
     */
    @JsonProperty("localTotalSaleTaxAmt")
    public Double getLocalTotalSaleTaxAmt() {
        return localTotalSaleTaxAmt;
    }

    /**
     * 计分合计本国合计销售税
     * 
     * @param localTotalSaleTaxAmt
     *     The localTotalSaleTaxAmt
     */
    @JsonProperty("localTotalSaleTaxAmt")
    public void setLocalTotalSaleTaxAmt(Double localTotalSaleTaxAmt) {
        this.localTotalSaleTaxAmt = localTotalSaleTaxAmt;
    }

    public BatchSavePOSTBody withLocalTotalSaleTaxAmt(Double localTotalSaleTaxAmt) {
        this.localTotalSaleTaxAmt = localTotalSaleTaxAmt;
        return this;
    }

    /**
     * 
     * @return
     *     The gsoDetails
     */
    @JsonProperty("gsoDetails")
    public List<GsoDetail> getGsoDetails() {
        return gsoDetails;
    }

    /**
     * 
     * @param gsoDetails
     *     The gsoDetails
     */
    @JsonProperty("gsoDetails")
    public void setGsoDetails(List<GsoDetail> gsoDetails) {
        this.gsoDetails = gsoDetails;
    }

    public BatchSavePOSTBody withGsoDetails(List<GsoDetail> gsoDetails) {
        this.gsoDetails = gsoDetails;
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

    public BatchSavePOSTBody withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).append(createBy).append(lastUpdateBy).append(lastUpdateTime).append(soNo).append(soType).append(soOrgCode).append(soDealerNo).append(soPeriod).append(soEntryClass).append(soEntryTime).append(soEntryBy).append(orderDealerNo).append(orderDate).append(orderAmt).append(orderDealerName).append(orderDealerTele).append(firstSo).append(refSoNo).append(localCurrencyCode).append(localTotalAmt).append(localTotalEcoupon).append(localTotalPoint).append(localTotalRebate).append(realTimeProcFlag).append(realTimeProcTime).append(comments).append(calcPeriod).append(adviseNo).append(localTotalSaleTaxAmt).append(gsoDetails).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof BatchSavePOSTBody) == false) {
            return false;
        }
        BatchSavePOSTBody rhs = ((BatchSavePOSTBody) other);
        return new EqualsBuilder().append(id, rhs.id).append(createBy, rhs.createBy).append(lastUpdateBy, rhs.lastUpdateBy).append(lastUpdateTime, rhs.lastUpdateTime).append(soNo, rhs.soNo).append(soType, rhs.soType).append(soOrgCode, rhs.soOrgCode).append(soDealerNo, rhs.soDealerNo).append(soPeriod, rhs.soPeriod).append(soEntryClass, rhs.soEntryClass).append(soEntryTime, rhs.soEntryTime).append(soEntryBy, rhs.soEntryBy).append(orderDealerNo, rhs.orderDealerNo).append(orderDate, rhs.orderDate).append(orderAmt, rhs.orderAmt).append(orderDealerName, rhs.orderDealerName).append(orderDealerTele, rhs.orderDealerTele).append(firstSo, rhs.firstSo).append(refSoNo, rhs.refSoNo).append(localCurrencyCode, rhs.localCurrencyCode).append(localTotalAmt, rhs.localTotalAmt).append(localTotalEcoupon, rhs.localTotalEcoupon).append(localTotalPoint, rhs.localTotalPoint).append(localTotalRebate, rhs.localTotalRebate).append(realTimeProcFlag, rhs.realTimeProcFlag).append(realTimeProcTime, rhs.realTimeProcTime).append(comments, rhs.comments).append(calcPeriod, rhs.calcPeriod).append(adviseNo, rhs.adviseNo).append(localTotalSaleTaxAmt, rhs.localTotalSaleTaxAmt).append(gsoDetails, rhs.gsoDetails).append(additionalProperties, rhs.additionalProperties).isEquals();
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

}
