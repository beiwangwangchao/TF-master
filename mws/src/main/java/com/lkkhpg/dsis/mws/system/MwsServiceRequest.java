/**
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.mws.system;

import com.lkkhpg.dsis.common.constant.MemberConstants;
import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.common.member.dto.Member;
import com.lkkhpg.dsis.platform.core.impl.ServiceRequest;


/** 
 * @author runbai.chen 
 */
public class MwsServiceRequest extends ServiceRequest {
    
    public Long getMemberId() {
        return (Long) super.getAttribute(Member.FIELD_MEMBER_ID);
    }
    
    public void setMemberId(Long memberId) {
        super.setAttribute(Member.FIELD_MEMBER_ID, memberId);
    }
    
    public Long getMemberRole() {
        return (Long) super.getAttribute(MemberConstants.FIELD_MEMBER_ROLE);
    }
    
    public void setMemberRole(String memberRole) {
        super.setAttribute(MemberConstants.FIELD_MEMBER_ROLE, memberRole);
    }
    
    public void setSalesOrgId(Long salesOrgId) {
        super.setAttribute(SystemProfileConstants.SALES_ORG_ID, salesOrgId);
    }

    public Long getSalesOrgId() {
        return (Long) super.getAttribute(SystemProfileConstants.SALES_ORG_ID);
    }
    
    public void setMarketId(Long marketId) {
        super.setAttribute(SystemProfileConstants.MARKET_ID, marketId);
    }
    
    public Long getMarketId() {
        return (Long) super.getAttribute(SystemProfileConstants.MARKET_ID);
    }

}
