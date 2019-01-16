/*
 *
 */
package com.lkkhpg.dsis.common.report.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;

import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.common.report.service.ISysReportTemplateService;
import com.lkkhpg.dsis.common.system.dto.SysReportTemplate;
import com.lkkhpg.dsis.common.system.dto.SysTemplateAssign;
import com.lkkhpg.dsis.common.system.mapper.SysReportTemplateMapper;
import com.lkkhpg.dsis.common.system.mapper.SysTemplateAssignMapper;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.DTOStatus;
import com.lkkhpg.dsis.platform.dto.system.DocSequence;
import com.lkkhpg.dsis.platform.service.IDocSequenceService;

/**
 * 模板管理ServiceImpl.
 * @author hanrui.huang
 *
 */
@Service
@Transactional
public class SysReportTemplateServiceImpl implements ISysReportTemplateService {

    @Autowired
    private SysReportTemplateMapper sysReportTemplateMapper;

    @Autowired
    private SysTemplateAssignMapper sysTemplateAssignMapper;

    @Autowired
    private IDocSequenceService docSequenceService;

    private static final String DOC_TYPE = "REPORT_TEMPLATE";
    
    private static final String DOC_PREFIX = "TE";

    public static final Long INIT_SEQUENCE = 10000L;
    
    @Override
    public List<SysReportTemplate> queryReportTemplate(IRequest request, SysReportTemplate sysReportTemplate, int page,
            int pagesize) {
        PageHelper.startPage(page, pagesize);
        return sysReportTemplateMapper.querySysReportTemplate(sysReportTemplate);
    }

    @Override
    public List<SysReportTemplate> saveReportTemplate(IRequest request, List<SysReportTemplate> sysReportTemplates) {
        for (SysReportTemplate sysReportTemplate : sysReportTemplates) {
            String status = sysReportTemplate.get__status();
            //判断模板ID不为空的
            if (StringUtils.isBlank(sysReportTemplate.getTemplateCode())) {
                sysReportTemplate.setTemplateCode(docSequenceService.getSequence(request,
                        new DocSequence(DOC_TYPE, DOC_PREFIX, null, null, null, null), DOC_PREFIX,
                        SystemProfileConstants.CODE_LENGTH_FIVE, INIT_SEQUENCE));
                sysReportTemplate.setEnabledFlag(SystemProfileConstants.YES);
                sysReportTemplateMapper.insert(sysReportTemplate);
                self().saveSysTemplateAssign(sysReportTemplate);
            } else {
                //模板已存在，修改
                if (DTOStatus.UPDATE.equals(status)) {
                    sysReportTemplateMapper.updateByPrimaryKeySelective(sysReportTemplate);
                    self().saveSysTemplateAssign(sysReportTemplate);
                } else {
                    //模板无效
                    sysReportTemplate.setEnabledFlag(SystemProfileConstants.PEROID_CLOSE_STATUS_N);
                    sysReportTemplate.setEndActiveDate(new Date());
                    sysReportTemplateMapper.updateByPrimaryKeySelective(sysReportTemplate);
                }
                
            }
        }
        return sysReportTemplates;
    }

    @Override
    public List<SysTemplateAssign> querySysTemplateAssign(IRequest request, Long templateId, int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
        return sysTemplateAssignMapper.querySysTemplateAssign(templateId);
    }

    @Override
    public List<SysTemplateAssign> saveSysTemplateAssign(SysReportTemplate sysReportTemplate) {
        List<SysTemplateAssign> sysTemplateAssigns = sysReportTemplate.getSysTemplateAssigns();
        if (sysTemplateAssigns != null) {
            for (SysTemplateAssign sysTemplateAssign : sysTemplateAssigns) {
                sysTemplateAssign.setTemplateId(sysReportTemplate.getTemplateId());
                sysTemplateAssignMapper.insert(sysTemplateAssign);
            }
        }
        return sysTemplateAssigns;
    }

    @Override
    public boolean deleteSysTemplateAssign(IRequest request, List<SysTemplateAssign> sysTemplateAssigns) {
        for (SysTemplateAssign sysTemplateAssign : sysTemplateAssigns) {
            Long assignId = sysTemplateAssign.getAssignId();
            if (assignId != null) {
                sysTemplateAssignMapper.deleteByPrimaryKey(assignId);
                
            }
        }
        return true;
    }

}
