/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.system.service;

import java.util.List;

import com.lkkhpg.dsis.common.system.dto.OrgTemplate;
import com.lkkhpg.dsis.common.system.dto.OrgTemplateDtl;
import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 组织模板接口.
 * 
 * @author runbai.chen
 */
public interface IOrgTemplateService extends ProxySelf<IOrgTemplateService> {

    /**
     * 获取模板列表.
     * @param request 
     * 
     * @param orgTemplate
     *            组织模板
     * @param page
     *            页面数
     * @param pagesize
     *            页面数据数量
     * @return 组织模板列表
     */
    List<OrgTemplate> selectOrgTemplates(IRequest request,OrgTemplate orgTemplate, int page, int pagesize);

    /**
     * 获取组织详细列表.
     * @param request 
     * 
     * @param orgTemplateDtl
     *            组织详细模板参数
     * @param page
     *            页面数
     * @param pagesize
     *            页面数据数量
     * @return 组织模板明细列表
     */
    List<OrgTemplateDtl> selectOrgTemplateDtls(IRequest request,OrgTemplateDtl orgTemplateDtl, int page, int pagesize);

    /**
     * 批量处理模板.
     * 
     * @param request
     *            用户请求
     * @param orgTemplates
     *            组织模板列表
     * @return 组织模板列表
     */
    List<OrgTemplate> batchUpdate(IRequest request, @StdWho List<OrgTemplate> orgTemplates);

    /**
     * 批量删除组织模板.
     * 
     * 先删除行表，再删除头表信息
     * 
     * @param request
     *            用户请求
     * @param orgTemplates
     *            组织模板列表
     * @return 批量删除组织模板
     */
    boolean batchOrgTemplateDelete(IRequest request, List<OrgTemplate> orgTemplates);

    /**
     * 批量删除行表.
     * 
     * @param request
     *            用户请求
     * @param orgTemplateDtls
     *            组织模板详细列表
     * @return 批量删除组织模板明细
     */
    boolean batchOrgTemplateDtlDelete(IRequest request, List<OrgTemplateDtl> orgTemplateDtls);

    /**
     * 创建组织模板.
     * 
     * @param request
     *            用户请求
     * @param orgTemplate
     *            组织模板
     * @return 组织模板
     */
    OrgTemplate createOrgTemplate(IRequest request, @StdWho OrgTemplate orgTemplate);

    /**
     * 更新组织模板.
     * 
     * @param request
     *            用户请求
     * @param orgTemplate
     *            组织模板
     * @return 组织模板
     */
    OrgTemplate updateOrgTemplate(IRequest request, @StdWho OrgTemplate orgTemplate);

    /**
     * 批量处理组织模板明细.
     * 
     * @param request
     *            用户请求
     * @param orgTemplate
     *            组织模板对象
     * 
     */
    void processOrgTemplateDtls(IRequest request, @StdWho OrgTemplate orgTemplate);

    /**
     * 创建组织模板明细.
     * 
     * @param request
     *            用户请求
     * @param orgTemplateDtl
     *            组织模板明细
     * @return 组织模板明细
     */
    OrgTemplateDtl createOrgTemplateDtl(IRequest request, @StdWho OrgTemplateDtl orgTemplateDtl);

    /**
     * 更新组织模板明细.
     * 
     * @param request
     *            用户请求
     * @param orgTemplateDtl
     *            组织模板明细
     * @return 组织模板明细
     */
    OrgTemplateDtl updateOrgTemplateDtl(IRequest request, @StdWho OrgTemplateDtl orgTemplateDtl);
}
