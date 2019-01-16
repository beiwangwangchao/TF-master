/*
 *
 */

package com.lkkhpg.dsis.platform.audit.service.impl;

import com.lkkhpg.dsis.platform.audit.service.IAuditTableNameProvider;

/**
 * default impl, add '_A' suffix to baseTableName.
 * 
 * @author shengyang.zhou@hand-china.com
 */
public class DefaultAuditTableNameProvider implements IAuditTableNameProvider {

    public static DefaultAuditTableNameProvider instance = new DefaultAuditTableNameProvider();

    private DefaultAuditTableNameProvider() {
    }

    @Override
    public String getAuditTableName(String baseTableName) {
        return baseTableName + "_Archive";
    }
}
