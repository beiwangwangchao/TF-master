/*
 *
 */
package com.lkkhpg.dsis.admin.integration.dapp.dto;

/**
 * 获取下线绩效列表响应DTO.
 * 
 * @author songyuanhuang
 *
 */
public class OmkDownlinePerforResponse {
    private String distributorId;
    private String sponsorId;
    private String fullName;
    private String totalOv;
    private String totalNew;
    private String position;
    private String positionCode;

    public String getDistributorId() {
        return distributorId;
    }

    public void setDistributorId(String distributorId) {
        this.distributorId = distributorId;
    }

    public String getSponsorId() {
        return sponsorId;
    }

    public void setSponsorId(String sponsorId) {
        this.sponsorId = sponsorId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getTotalOv() {
        return totalOv;
    }

    public void setTotalOv(String totalOv) {
        this.totalOv = totalOv;
    }

    public String getTotalNew() {
        return totalNew;
    }

    public void setTotalNew(String totalNew) {
        this.totalNew = totalNew;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPositionCode() {
        return positionCode;
    }

    public void setPositionCode(String positionCode) {
        this.positionCode = positionCode;
    }

}
