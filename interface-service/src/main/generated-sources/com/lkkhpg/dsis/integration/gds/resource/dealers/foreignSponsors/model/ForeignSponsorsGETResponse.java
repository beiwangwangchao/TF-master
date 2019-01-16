
package com.lkkhpg.dsis.integration.gds.resource.dealers.foreignSponsors.model;

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
    "dealerName",
    "sponsorNo",
    "dealerPostCode",
    "saleBranchNo",
    "saleOrgCode",
    "taxCustCode",
    "certificateNo"
})
public class ForeignSponsorsGETResponse {

    /**
     * 卡號
     * 
     */
    @JsonProperty("dealerNo")
    private String dealerNo;
    /**
     * 持卡人姓名
     * 
     */
    @JsonProperty("dealerName")
    private String dealerName;
    /**
     * 推荐人卡号
     * 
     */
    @JsonProperty("sponsorNo")
    private String sponsorNo;
    /**
     * 职级代码
     * 
     */
    @JsonProperty("dealerPostCode")
    private String dealerPostCode;
    /**
     * 所属分支机构代号
     * 
     */
    @JsonProperty("saleBranchNo")
    private String saleBranchNo;
    /**
     * 所属机构代号
     * 
     */
    @JsonProperty("saleOrgCode")
    private String saleOrgCode;
    /**
     * TAX_CUST_CODE
     * 
     */
    @JsonProperty("taxCustCode")
    private String taxCustCode;
    /**
     * certificate No
     * 
     */
    @JsonProperty("certificateNo")
    private String certificateNo;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public ForeignSponsorsGETResponse() {
    }

    /**
     * 
     * @param dealerPostCode
     * @param dealerName
     * @param dealerNo
     * @param sponsorNo
     * @param taxCustCode
     * @param saleOrgCode
     * @param saleBranchNo
     * @param certificateNo
     */
    public ForeignSponsorsGETResponse(String dealerNo, String dealerName, String sponsorNo, String dealerPostCode, String saleBranchNo, String saleOrgCode, String taxCustCode, String certificateNo) {
        this.dealerNo = dealerNo;
        this.dealerName = dealerName;
        this.sponsorNo = sponsorNo;
        this.dealerPostCode = dealerPostCode;
        this.saleBranchNo = saleBranchNo;
        this.saleOrgCode = saleOrgCode;
        this.taxCustCode = taxCustCode;
        this.certificateNo = certificateNo;
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

    public ForeignSponsorsGETResponse withDealerNo(String dealerNo) {
        this.dealerNo = dealerNo;
        return this;
    }

    /**
     * 持卡人姓名
     * 
     * @return
     *     The dealerName
     */
    @JsonProperty("dealerName")
    public String getDealerName() {
        return dealerName;
    }

    /**
     * 持卡人姓名
     * 
     * @param dealerName
     *     The dealerName
     */
    @JsonProperty("dealerName")
    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    public ForeignSponsorsGETResponse withDealerName(String dealerName) {
        this.dealerName = dealerName;
        return this;
    }

    /**
     * 推荐人卡号
     * 
     * @return
     *     The sponsorNo
     */
    @JsonProperty("sponsorNo")
    public String getSponsorNo() {
        return sponsorNo;
    }

    /**
     * 推荐人卡号
     * 
     * @param sponsorNo
     *     The sponsorNo
     */
    @JsonProperty("sponsorNo")
    public void setSponsorNo(String sponsorNo) {
        this.sponsorNo = sponsorNo;
    }

    public ForeignSponsorsGETResponse withSponsorNo(String sponsorNo) {
        this.sponsorNo = sponsorNo;
        return this;
    }

    /**
     * 职级代码
     * 
     * @return
     *     The dealerPostCode
     */
    @JsonProperty("dealerPostCode")
    public String getDealerPostCode() {
        return dealerPostCode;
    }

    /**
     * 职级代码
     * 
     * @param dealerPostCode
     *     The dealerPostCode
     */
    @JsonProperty("dealerPostCode")
    public void setDealerPostCode(String dealerPostCode) {
        this.dealerPostCode = dealerPostCode;
    }

    public ForeignSponsorsGETResponse withDealerPostCode(String dealerPostCode) {
        this.dealerPostCode = dealerPostCode;
        return this;
    }

    /**
     * 所属分支机构代号
     * 
     * @return
     *     The saleBranchNo
     */
    @JsonProperty("saleBranchNo")
    public String getSaleBranchNo() {
        return saleBranchNo;
    }

    /**
     * 所属分支机构代号
     * 
     * @param saleBranchNo
     *     The saleBranchNo
     */
    @JsonProperty("saleBranchNo")
    public void setSaleBranchNo(String saleBranchNo) {
        this.saleBranchNo = saleBranchNo;
    }

    public ForeignSponsorsGETResponse withSaleBranchNo(String saleBranchNo) {
        this.saleBranchNo = saleBranchNo;
        return this;
    }

    /**
     * 所属机构代号
     * 
     * @return
     *     The saleOrgCode
     */
    @JsonProperty("saleOrgCode")
    public String getSaleOrgCode() {
        return saleOrgCode;
    }

    /**
     * 所属机构代号
     * 
     * @param saleOrgCode
     *     The saleOrgCode
     */
    @JsonProperty("saleOrgCode")
    public void setSaleOrgCode(String saleOrgCode) {
        this.saleOrgCode = saleOrgCode;
    }

    public ForeignSponsorsGETResponse withSaleOrgCode(String saleOrgCode) {
        this.saleOrgCode = saleOrgCode;
        return this;
    }

    /**
     * TAX_CUST_CODE
     * 
     * @return
     *     The taxCustCode
     */
    @JsonProperty("taxCustCode")
    public String getTaxCustCode() {
        return taxCustCode;
    }

    /**
     * TAX_CUST_CODE
     * 
     * @param taxCustCode
     *     The taxCustCode
     */
    @JsonProperty("taxCustCode")
    public void setTaxCustCode(String taxCustCode) {
        this.taxCustCode = taxCustCode;
    }

    public ForeignSponsorsGETResponse withTaxCustCode(String taxCustCode) {
        this.taxCustCode = taxCustCode;
        return this;
    }

    /**
     * certificate No
     * 
     * @return
     *     The certificateNo
     */
    @JsonProperty("certificateNo")
    public String getCertificateNo() {
        return certificateNo;
    }

    /**
     * certificate No
     * 
     * @param certificateNo
     *     The certificateNo
     */
    @JsonProperty("certificateNo")
    public void setCertificateNo(String certificateNo) {
        this.certificateNo = certificateNo;
    }

    public ForeignSponsorsGETResponse withCertificateNo(String certificateNo) {
        this.certificateNo = certificateNo;
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

    public ForeignSponsorsGETResponse withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(dealerNo).append(dealerName).append(sponsorNo).append(dealerPostCode).append(saleBranchNo).append(saleOrgCode).append(taxCustCode).append(certificateNo).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ForeignSponsorsGETResponse) == false) {
            return false;
        }
        ForeignSponsorsGETResponse rhs = ((ForeignSponsorsGETResponse) other);
        return new EqualsBuilder().append(dealerNo, rhs.dealerNo).append(dealerName, rhs.dealerName).append(sponsorNo, rhs.sponsorNo).append(dealerPostCode, rhs.dealerPostCode).append(saleBranchNo, rhs.saleBranchNo).append(saleOrgCode, rhs.saleOrgCode).append(taxCustCode, rhs.taxCustCode).append(certificateNo, rhs.certificateNo).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
