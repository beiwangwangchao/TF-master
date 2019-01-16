/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.system.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lkkhpg.dsis.admin.system.service.IOrgInfoService;
import com.lkkhpg.dsis.common.config.dto.SpmInvOrganization;
import com.lkkhpg.dsis.common.config.dto.SpmSalesOrganization;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;

/**
 * 获取当前组织信息的Controller.
 * @author chenjingxiong
 */
@Controller
public class OrgInfoController extends BaseController {

    @Autowired
    private IOrgInfoService orgInfoService;
    
    /**
     * 获取当前组织信息.
     * @param request 请求上下文.
     * @return 组织信息Scripts文本.
     */
    @RequestMapping(value = "/sys/org/current", produces = "application/javascript;charset=utf8")
    @ResponseBody
    public String getCurrentOrgInfo(HttpServletRequest request) {
         IRequest requestContext = createRequestContext(request);
         SpmSalesOrganization salesOrg = orgInfoService.querySalesInfo(requestContext);
         SpmInvOrganization invOrg = orgInfoService.queryInvInfo(requestContext);
         
         StringBuilder sb = new StringBuilder();
         sb.append("var _salesOrgId,_salesOrgName,_marketId,_marketCode,_marketName,_invOrgId,_invOrgName,_ouId,_ouName,_salesOrgType;");
         if (salesOrg != null) {
             sb.append("_salesOrgId = ").append(salesOrg.getSalesOrgId()).append(";");
             sb.append("_salesOrgName = '").append(salesOrg.getName()).append("';");
             sb.append("_marketId = ").append(salesOrg.getMarketId()).append(";");
             sb.append("_marketCode = '").append(salesOrg.getMarketCode()).append("';");
             sb.append("_marketName = '").append(salesOrg.getMarketName()).append("';");
             sb.append("_salesOrgType = '").append(salesOrg.getSalesOrgType()).append("';");
         }
         if (invOrg != null) {
             sb.append("_invOrgId = ").append(invOrg.getInvOrgId()).append(";");
             sb.append("_invOrgName = '").append(invOrg.getName()).append("';");
             sb.append("_ouId = ").append(invOrg.getOperationUnitId()).append(";");
             sb.append("_ouName = '").append(invOrg.getOperationUnitName()).append("';");
         }
         return sb.toString();    
    }

}
