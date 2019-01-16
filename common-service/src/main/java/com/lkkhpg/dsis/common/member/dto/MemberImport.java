/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.member.dto;

import java.util.List;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 导入会员列表的表单Dto.
 * 
 * @author mclin
 */
public class MemberImport extends BaseDTO {
    private static final long serialVersionUID = 1L;

    private Long voucherId;
    private List<MemberList> memberLists;
    private String idType;
    
    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public Long getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(Long voucherId) {
        this.voucherId = voucherId;
    }

    public List<MemberList> getMemberLists() {
        return memberLists;
    }

    public void setMemberLists(List<MemberList> memberLists) {
        this.memberLists = memberLists;
    }
}
