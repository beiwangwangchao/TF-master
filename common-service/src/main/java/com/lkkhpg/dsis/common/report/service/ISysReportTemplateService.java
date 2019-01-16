/*
 *
 */
package com.lkkhpg.dsis.common.report.service;

import java.util.List;

import com.lkkhpg.dsis.common.system.dto.SysReportTemplate;
import com.lkkhpg.dsis.common.system.dto.SysTemplateAssign;
import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 模板管理.
 * @author hanrui.huang
 *
 */
public interface ISysReportTemplateService extends ProxySelf<ISysReportTemplateService> {

    /**
     * 模板查询.
     * @param request 请求上下文
     * @param sysReportTemplate 报表模板DTO
     * @param page 页
     * @param pagesize 页大小
     * @return sysReportTemplates 模板List
     */
     List<SysReportTemplate> queryReportTemplate(IRequest request, SysReportTemplate sysReportTemplate, int page,
            int pagesize);

    /**
     * 保存模板.
     * @param request 请求上下文
     * @param sysReportTemplates 报表模板List
     * @param page 页
     * @param pagesize 页大小
     * @return sysReportTemplates 报表模板List
     */
     List<SysReportTemplate> saveReportTemplate(IRequest request,
            @StdWho List<SysReportTemplate> sysReportTemplates);

    /**
     * 查询模板分配市场.
     * @param request 请求上下文
     * @param templateId 报表模板ID
     * @param page 页
     * @param pagesize 页大小
     * @return SysTemplateAssigns 模板分配市场List
     */
    List<SysTemplateAssign> querySysTemplateAssign(IRequest request, Long templateId, int page, int pagesize);

    /**
     * 保存模板分配市场.
     * @param sysReportTemplate 报表模板DTO 
     * @return sysTemplateAssigns 模板分配市场
     */
    List<SysTemplateAssign> saveSysTemplateAssign(SysReportTemplate sysReportTemplate);

    /**
     * 删除模板分配市场.
     * @param request 请求上下文
     * @param sysTemplateAssigns 模板分配市场List
     * @return boolean
     */
    boolean deleteSysTemplateAssign(IRequest request, List<SysTemplateAssign> sysTemplateAssigns);
}
