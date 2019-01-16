/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.system.report.controllers;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.lkkhpg.dsis.common.config.dto.SpmSalesOrganization;
import com.lkkhpg.dsis.platform.dto.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lkkhpg.dsis.admin.system.report.service.OrganizationService;
import com.lkkhpg.dsis.common.system.report.dto.GdsMeDealerTree;
import com.lkkhpg.dsis.common.system.report.dto.OmkRtlSalaryBalance;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;

/**
 * GDS树的controller.
 * 
 * @author HuangJiaJing
 *
 */
@Controller
public class OrganizationController extends BaseController {



    @Autowired
    private OrganizationService orgService;
    /**
     * 查询History数据.
     * 
     * @param request
     *            应用上下文.
     * @param tree dto
     * @return ResponseData集合
     */
    @RequestMapping(value = "/sys/metree/query", method = RequestMethod.POST)
    @ResponseBody
    public List<GdsMeDealerTree> queryMeTree(HttpServletRequest request, GdsMeDealerTree tree) {
        IRequest requestContext = createRequestContext(request);
        List<GdsMeDealerTree> list = orgService.queryMeTree(requestContext, tree);
        return list;
    }

    @RequestMapping(value = "/sys/Organization/selectOrganization")
    @ResponseBody
    public ResponseData selectOrganization(SpmSalesOrganization marketId, HttpServletRequest request)throws Exception{
        System.out.println("llllllllllllllllllllllll");
        IRequest requestContext = createRequestContext(request);
        List<SpmSalesOrganization> marketIdList=orgService.selectOrganization(requestContext,marketId);
       // Map<String,String> map= new HashMap<String,String>();
      //   String[] array=marketIdList.toArray(new String[marketIdList.size()])    ;
//        for(SpmSalesOrganization s:marketIdList){
//          if(s==null){
//           continue;
//          }
//
////          map.put("name",s.getName());
//        }
       return  new ResponseData(marketIdList);
    }



    /**
     * 查询WebSite数据.
     * 
     * @param request
     *            应用上下文.
     * @param rtlSalary Dto
     * @return ResponseData集合
     */
    @RequestMapping(value = "/sys/rttree/query", method = RequestMethod.POST)
    @ResponseBody
    public List<OmkRtlSalaryBalance> queryRtlSalary(HttpServletRequest request, OmkRtlSalaryBalance rtlSalary) {
        IRequest requestContext = createRequestContext(request);
        List<OmkRtlSalaryBalance> list = orgService.queryRtlTree(requestContext, rtlSalary);
        return list;
    }

    
}
