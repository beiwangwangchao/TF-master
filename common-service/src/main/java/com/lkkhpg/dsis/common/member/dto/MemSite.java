/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.member.dto;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.lkkhpg.dsis.common.config.dto.SpmLocation;
import com.lkkhpg.dsis.platform.annotation.AuditEnabled;
import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 会员地点Dto.
 * 
 * @author frank.li
 */
@AuditEnabled
@Table(name = "MM_MEM_SITE")
public class MemSite extends BaseDTO {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "site_id")
    private Long siteId;

    // @NotNull 由保存Member对象时赋值，加上@NotNull会影响saveMember的validate
    private Long memberId;

    @NotNull
    private String siteUseCode;

    private Long locationId;

    @NotNull
    private String name;

    private String areaCode;

    private String phone;

    @NotNull
    private String defaultFlag;

    private String address;

    private SpmLocation spmLocation;

    public Long getSiteId() {
        return siteId;
    }

    public void setSiteId(Long siteId) {
        this.siteId = siteId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getSiteUseCode() {
        return siteUseCode;
    }

    public void setSiteUseCode(String siteUseCode) {
        this.siteUseCode = siteUseCode == null ? null : siteUseCode.trim();
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locatonId) {
        this.locationId = locatonId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode == null ? null : areaCode.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDefaultFlag() {
        return defaultFlag;
    }

    public void setDefaultFlag(String defaultFlag) {
        this.defaultFlag = defaultFlag;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public SpmLocation getSpmLocation() {
        return spmLocation;
    }

    public void setSpmLocation(SpmLocation spmLocation) {
        this.spmLocation = spmLocation;
    }

}