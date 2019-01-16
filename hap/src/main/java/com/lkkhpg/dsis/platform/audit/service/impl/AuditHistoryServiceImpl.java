/*
 * Copyright LKK Health Products Group Limited
 */

package com.lkkhpg.dsis.platform.audit.service.impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Table;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import com.github.pagehelper.PageHelper;
import com.lkkhpg.dsis.platform.audit.mapper.AuditMapper;
import com.lkkhpg.dsis.platform.audit.service.IAuditHistoryService;
import com.lkkhpg.dsis.platform.audit.service.IAuditTableNameProvider;
import com.lkkhpg.dsis.platform.audit.util.AuditUtils;
import com.lkkhpg.dsis.platform.dto.BaseDTO;
import com.lkkhpg.dsis.platform.dto.DTOClassInfo;

/**
 * @author shengyang.zhou@hand-china.com
 */
@Service
public class AuditHistoryServiceImpl implements IAuditHistoryService {
	
	private Logger logger = LoggerFactory.getLogger(AuditHistoryServiceImpl.class);

    @Autowired
    private AuditMapper auditMapper;

    @Autowired(required = false)
    private IAuditTableNameProvider auditTableNameProvider = DefaultAuditTableNameProvider.instance;

    public AuditHistoryServiceImpl() {
    }

    @Override
    public List<Map<String, Object>> selectAuditHistory(BaseDTO dto, int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
        Map<String, Object> auditParam = new HashMap<>();
        Class clazz = dto.getClass();
        Table tbl = (Table) clazz.getAnnotation(Table.class);
        String baseTableName = tbl.name();
        String auditTableName = auditTableNameProvider.getAuditTableName(baseTableName);
        auditParam.put("_auditTableName", auditTableName);
        Field[] ids = DTOClassInfo.getIdFields(clazz);
        Map<String, Object> pks = new HashMap<>();
        for (Field f : ids) {
            try {
                pks.put(f.getAnnotation(Column.class).name(), f.get(dto));
            } catch (IllegalAccessException e) {
                //e.printStackTrace();
                if(logger.isErrorEnabled()){
                	logger.error(e.getMessage(), e);
                }
            }
        }
        Map<String, Field> fieldsMap = new HashMap<>();
        ReflectionUtils.doWithFields(clazz, f -> fieldsMap.put(f.getName(), f));
        auditParam.put("_pks", pks);
        List<Map<String, Object>> res = auditMapper.selectAuditHistory(auditParam);
        List<Map<String, Object>> resTrans = new ArrayList<>();
        for (Map<String, Object> e : res) {
            Map<String, Object> n = new HashMap<>();
            e.forEach((k, v) -> {
                String camelName = AuditUtils.camel(k);
                if (v instanceof Date) {
                    Field f = fieldsMap.get(camelName);
                    v = AuditUtils.convertDateToString((Date) v, f);
                }
                n.put(camelName, v);
            });
            resTrans.add(n);
        }
        return resTrans;
    }

}
