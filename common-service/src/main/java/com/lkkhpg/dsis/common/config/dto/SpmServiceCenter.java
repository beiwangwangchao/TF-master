/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.config.dto;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.lkkhpg.dsis.common.member.dto.Member;
import com.lkkhpg.dsis.platform.annotation.Children;
import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 服务中心dto.
 * 
 * @author wangc
 *
 */
public class SpmServiceCenter extends BaseDTO {
    private Long serviceCenterId;

    @NotNull
    private Long marketId;

    @NotEmpty
    private String code;

    @NotEmpty
    private String name;

    private String status;

    @NotEmpty
    private String phone;

    @NotNull
    private Long chargeMemberId;

    @NotNull
    private Date joinDate;

    private Date approvedDate;

    private Long locationId;

    private String remark;
    
    private SpmLocation spmLocation;
    
    private String locationName;
    
    private String chargeMemberCode;
    
    @Children
    private List<SpmServiceCenterAssign> spmMembers;
    
    /**
     * 负责会员.
     */
    private Member member;
    
    @NotEmpty
    private String areaCode;
    
    private Long salesOrgId;
    
    private Date terminateDate;
    
    private String marketName;
    
    private String salesOrgName;
    
    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public String getSalesOrgName() {
        return salesOrgName;
    }

    public void setSalesOrgName(String salesOrgName) {
        this.salesOrgName = salesOrgName;
    }

    public Date getTerminateDate() {
        return terminateDate;
    }

    public void setTerminateDate(Date terminateDate) {
        this.terminateDate = terminateDate;
    }

    public Long getSalesOrgId() {
        return salesOrgId;
    }

    public void setSalesOrgId(Long salesOrgId) {
        this.salesOrgId = salesOrgId;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public SpmLocation getSpmLocation() {
        return spmLocation;
    }

    public void setSpmLocation(SpmLocation spmLocation) {
        this.spmLocation = spmLocation;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getChargeMemberCode() {
        return chargeMemberCode;
    }

    public void setChargeMemberCode(String chargeMemberCode) {
        this.chargeMemberCode = chargeMemberCode;
    }

    public List<SpmServiceCenterAssign> getSpmMembers() {
        return spmMembers;
    }

    public void setSpmMembers(List<SpmServiceCenterAssign> spmMembers) {
        this.spmMembers = spmMembers;
    }

    public Long getServiceCenterId() {
        return serviceCenterId;
    }

    public void setServiceCenterId(Long serviceCenterId) {
        this.serviceCenterId = serviceCenterId;
    }

    public Long getMarketId() {
        return marketId;
    }

    public void setMarketId(Long marketId) {
        this.marketId = marketId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public Long getChargeMemberId() {
        return chargeMemberId;
    }

    public void setChargeMemberId(Long chargeMemberId) {
        this.chargeMemberId = chargeMemberId;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    public Date getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(Date approvedDate) {
        this.approvedDate = approvedDate;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}