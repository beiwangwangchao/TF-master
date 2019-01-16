/*
 *
 */
package com.lkkhpg.dsis.admin.system;

import java.util.List;

import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.platform.core.impl.ServiceRequest;
import com.lkkhpg.dsis.platform.dto.system.User;

/**
 * Dsis统一上下文实现类.
 * 
 * @author chenjingxiong
 */
public class DsisServiceRequest extends ServiceRequest {

    public Long getUserId() {
        return (Long) super.getAttribute(User.FILED_USER_ID);
    }
    
    public void setUserId(Long userId) {
        super.setAttribute(User.FILED_USER_ID, userId);
    }
    
    public void setDefaultSalesOrgId(Long salesOrgId) {
        super.setAttribute(SystemProfileConstants.DEFAULT_SALES_ORG_ID, salesOrgId);
    }

    public Long getDefaultSalesOrgId() {
        return (Long) super.getAttribute(SystemProfileConstants.DEFAULT_SALES_ORG_ID);
    }

    public void setDefaultInvOrgId(Long invOrgId) {
        super.setAttribute(SystemProfileConstants.DEFAULT_INV_ORG_ID, invOrgId);
    }

    public Long getDefaultInvOrgId() {
        return (Long) super.getAttribute(SystemProfileConstants.DEFAULT_INV_ORG_ID);
    }

    public void setSalesOrgIds(List<Long> salesOrgIds) {
        super.setAttribute(SystemProfileConstants.SALES_ORG_IDS, salesOrgIds);
    }

    @SuppressWarnings("unchecked")
    public List<Long> getSalesOrgIds() {
        return (List<Long>) super.getAttribute(SystemProfileConstants.SALES_ORG_IDS);
    }

    public void setInvOrgIds(List<Long> invOrgIds) {
        super.setAttribute(SystemProfileConstants.INV_ORG_IDS, invOrgIds);
    }

    @SuppressWarnings("unchecked")
    public List<Long> getInvOrgIds() {
        return (List<Long>) super.getAttribute(SystemProfileConstants.INV_ORG_IDS);
    }

    public void setSalesOrgId(Long salesOrgId) {
        super.setAttribute(SystemProfileConstants.SALES_ORG_ID, salesOrgId);
    }

    public Long getSalesOrgId() {
        return (Long) super.getAttribute(SystemProfileConstants.SALES_ORG_ID);
    }

    public void setInvOrgId(Long invOrgId) {
        super.setAttribute(SystemProfileConstants.INV_ORG_ID, invOrgId);
    }

    public Long getInvOrgId() {
        return (Long) super.getAttribute(SystemProfileConstants.INV_ORG_ID);
    }

    public void setMarketId(Long marketId) {
        super.setAttribute(SystemProfileConstants.MARKET_ID, marketId);
    }
    
    public Long getMarketId() {
        return (Long) super.getAttribute(SystemProfileConstants.MARKET_ID);
    }
}
