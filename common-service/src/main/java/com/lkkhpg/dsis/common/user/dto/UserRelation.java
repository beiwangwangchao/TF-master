/*
 *
 */
package com.lkkhpg.dsis.common.user.dto;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 用户关系.
 * 
 * @author runbai.chen
 */
public class UserRelation extends BaseDTO {

    private static final long serialVersionUID = 1L;

    // 默认为用户类型
    private Long userId;
    // 会员ID
    private Long memberId;
    private Long companyId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

}
