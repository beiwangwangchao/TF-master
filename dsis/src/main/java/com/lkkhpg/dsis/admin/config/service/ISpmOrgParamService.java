/*
 *
 */
package com.lkkhpg.dsis.admin.config.service;

import java.util.List;

import com.lkkhpg.dsis.admin.config.dto.SpmOrgParamValue;
import com.lkkhpg.dsis.common.config.dto.OrgParamDef;
import com.lkkhpg.dsis.common.config.dto.OrgParamValue;
import com.lkkhpg.dsis.platform.core.IRequest;

/**
 * 组织参数Service.
 * @author chenjingxiong
 */
public interface ISpmOrgParamService {

    /**
     * 获取组织参数定义.
     * @param request 请求上下文
     * @param orgType SALES、INV、MKT、OU.
     * @return 已分配给该组织类型的参数定义.
     */
    List<OrgParamDef> getOrgParamDefByOrgType(IRequest request, String orgType);
    
    
    /**
     * 获取该组织已定义的组织参数值.
     * @param request 请求上下文
     * @param orgType SALES、INV、MKT、OU.
     * @param orgId 对应参数ID.
     * @return 已定义好的组织参数值.
     */
    List<OrgParamValue> getOrgParamValues(IRequest request, String orgType, Long orgId);
    /**
     * 查询组织参数定义.
     * @param request 请求上下文
     * @param orgParamDef 查询条件
     * @param page  页数
     * @param pageSize 每页记录数
     * @return 已分配给该组织类型的参数定义.
     */
    List<OrgParamDef> queryOrgParamDef(IRequest request, OrgParamDef orgParamDef, int page, int pageSize);
    
    /**
     * 查询组织参数定义.
     * @param request  请求上下文
     * @param orgParamDefs  组织参数
     * @return 已分配给该组织类型的参数定义.
     */
    List<OrgParamDef> saveOrgParamDef(IRequest request, List<OrgParamDef> orgParamDefs);
    
    /**
     * 保存组织参数.
     * @param request 请求上下文.
     * @param smpOrgparamValues 组织参数.
     * @return 已保存的组织参数.
     */
    List<OrgParamValue> saveOrgParamValues(IRequest request, List<SpmOrgParamValue> smpOrgparamValues);
        
}
