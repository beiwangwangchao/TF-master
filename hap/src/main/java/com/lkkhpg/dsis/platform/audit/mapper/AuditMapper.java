/*
 * Copyright LKK Health Products Group Limited
 */

package com.lkkhpg.dsis.platform.audit.mapper;

import java.util.List;
import java.util.Map;

import com.lkkhpg.dsis.platform.audit.dto.Audit;

/**
 * @author shengyang.zhou@hand-china.com
 */
public interface AuditMapper {

    int auditInsert(Map map);

    List<Map<String, Object>> selectAuditHistory(Map<String, Object> para);
    
    List<Audit> selectAuditEntityAll();
    
    int saveAuditEntity(Audit audit);

}

