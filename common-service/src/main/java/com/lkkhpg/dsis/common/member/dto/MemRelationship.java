/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.member.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.lkkhpg.dsis.platform.annotation.AuditEnabled;
import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 会员相关人DTO.
 * 
 * @author frank.li
 */
@AuditEnabled
@Table(name = "MM_MEM_RELATIONSHIP")
public class MemRelationship extends BaseDTO {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "mem_rel_id")
    private Long memRelId;

    //@NotNull 由保存Member对象时赋值，加上@NotNull会影响saveMember的validate
    private Long memberId;

    @NotNull
    private String relType;

    private String englishName;

    private String chineseName;

    private Date dob;

    private String idType;

    private String idNumber;

    private String relDesc;

    private String remark;

    public Long getMemRelId() {
        return memRelId;
    }

    public void setMemRelId(Long memRelId) {
        this.memRelId = memRelId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getRelType() {
        return relType;
    }

    public void setRelType(String relType) {
        this.relType = relType == null ? null : relType.trim();
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName == null ? null : englishName.trim();
    }

    public String getChineseName() {
        return chineseName;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName == null ? null : chineseName.trim();
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType == null ? null : idType.trim();
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber == null ? null : idNumber.trim();
    }

    public String getRelDesc() {
        return relDesc;
    }

    public void setRelDesc(String relDesc) {
        this.relDesc = relDesc;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemarks(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

}