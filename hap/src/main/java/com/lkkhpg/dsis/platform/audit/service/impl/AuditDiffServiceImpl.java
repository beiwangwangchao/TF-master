/*
 * Copyright LKK Health Products Group Limited
 */

package com.lkkhpg.dsis.platform.audit.service.impl;

import com.github.pagehelper.PageHelper;

/**
 * @author shengyang.zhou@hand-china.com
 */
public class AuditDiffServiceImpl {
    public void getAuditHistory(String tableName, Long id, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

    }
}
