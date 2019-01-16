/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.lkkhpg.dsis.admin.system.service.IOrgTemplateService;
import com.lkkhpg.dsis.common.system.dto.OrgTemplate;
import com.lkkhpg.dsis.common.system.dto.OrgTemplateDtl;
import com.lkkhpg.dsis.common.system.mapper.OrgTemplateDtlMapper;
import com.lkkhpg.dsis.common.system.mapper.OrgTemplateMapper;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.DTOStatus;

/**
 * 组织模板接口实现.
 *
 * @author runbai.chen
 */
@Service
@Transactional
public class OrgTemplateServiceImpl implements IOrgTemplateService {

    @Autowired
    private OrgTemplateMapper orgTemplateMapper;

    @Autowired
    private OrgTemplateDtlMapper orgTemplateDtlMapper;

    @Override
    public List<OrgTemplate> selectOrgTemplates(IRequest request,OrgTemplate orgTemplate, int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
        return orgTemplateMapper.selectOrgTemplatesByParas(orgTemplate);
    }

    @Override
    public List<OrgTemplateDtl> selectOrgTemplateDtls(IRequest request,OrgTemplateDtl orgTemplateDtl, int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
        return orgTemplateDtlMapper.selectOrgTemplateDtlsByParas(orgTemplateDtl);
    }

    @Override
    public List<OrgTemplate> batchUpdate(IRequest request, List<OrgTemplate> orgTemplates) {
        for (OrgTemplate orgTemplate : orgTemplates) {
            if (orgTemplate.getOrgTemplateId() == null) {
                self().createOrgTemplate(request, orgTemplate);
            } else if (orgTemplate.getOrgTemplateId() != null) {
                self().updateOrgTemplate(request, orgTemplate);
            }
        }
        return orgTemplates;
    }

    @Override
    public boolean batchOrgTemplateDelete(IRequest request, List<OrgTemplate> orgTemplates) {
        // 删除头
        for (OrgTemplate orgTemplate : orgTemplates) {
            OrgTemplateDtl orgTemplateDtl = new OrgTemplateDtl();
            orgTemplateDtl.setOrgTemplateId(orgTemplate.getOrgTemplateId());
            orgTemplateDtlMapper.deleteByOrgTemplateId(orgTemplateDtl);
            orgTemplateMapper.deleteByPrimaryKey(orgTemplate);
        }
        return true;

    }

    @Override
    public boolean batchOrgTemplateDtlDelete(IRequest request, List<OrgTemplateDtl> orgTemplateDtls) {
        for (OrgTemplateDtl orgTemplateDtl : orgTemplateDtls) {
            orgTemplateDtlMapper.deleteByPrimaryKey(orgTemplateDtl);
        }
        return true;
    }

    @Override
    public OrgTemplate createOrgTemplate(IRequest request, OrgTemplate orgTemplate) {
        // 插入头行
        orgTemplateMapper.insert(orgTemplate);
        // 判断如果行不为空，则迭代循环插入
        if (!orgTemplate.getOrgTemplateDtls().isEmpty()) {
            self().processOrgTemplateDtls(request, orgTemplate);
        }
        return orgTemplate;
    }

    @Override
    public OrgTemplate updateOrgTemplate(IRequest request, OrgTemplate orgTemplate) {
        orgTemplateMapper.updateByPrimaryKeySelective(orgTemplate);
        // 判断如果行不为空，则迭代循环插入
        if (!orgTemplate.getOrgTemplateDtls().isEmpty()) {
            self().processOrgTemplateDtls(request, orgTemplate);
        }
        return orgTemplate;
    }

    @Override
    public void processOrgTemplateDtls(IRequest request, OrgTemplate orgTemplate) {
        for (OrgTemplateDtl orgTemplateDtl : orgTemplate.getOrgTemplateDtls()) {
            if (DTOStatus.ADD.equals(orgTemplateDtl.get__status())) {
                orgTemplateDtl.setOrgTemplateId(orgTemplate.getOrgTemplateId());
                self().createOrgTemplateDtl(request, orgTemplateDtl);
            } else if (DTOStatus.UPDATE.equals(orgTemplateDtl.get__status())) {
                self().updateOrgTemplateDtl(request, orgTemplateDtl);
            } else if (DTOStatus.DELETE.equals(orgTemplateDtl.get__status())) {
                orgTemplateDtlMapper.deleteByPrimaryKey(orgTemplateDtl);
            }
        }
    }

    @Override
    public OrgTemplateDtl createOrgTemplateDtl(IRequest request, OrgTemplateDtl orgTemplateDtl) {
        // 插入头行
        List<OrgTemplateDtl> olists = orgTemplateDtlMapper.selectOrgTemplateDtls(orgTemplateDtl);
        if (olists.size() == 0) {
            orgTemplateDtlMapper.insert(orgTemplateDtl);
        }
        return orgTemplateDtl;
    }

    @Override
    public OrgTemplateDtl updateOrgTemplateDtl(IRequest request, OrgTemplateDtl orgTemplateDtl) {
        List<OrgTemplateDtl> olists = orgTemplateDtlMapper.selectOrgTemplateDtls(orgTemplateDtl);
        if (olists.size() == 0) {
            orgTemplateDtlMapper.updateByPrimaryKeySelective(orgTemplateDtl);
        }
        return orgTemplateDtl;
    }

}
