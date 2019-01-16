/*
 *
 */

package com.lkkhpg.dsis.platform.interceptor;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lkkhpg.dsis.platform.annotation.AuditEnabled;
import com.lkkhpg.dsis.platform.audit.mapper.AuditMapper;
import com.lkkhpg.dsis.platform.audit.service.IAuditTableNameProvider;
import com.lkkhpg.dsis.platform.audit.service.impl.DefaultAuditTableNameProvider;
import com.lkkhpg.dsis.platform.dto.BaseDTO;
import com.lkkhpg.dsis.platform.dto.DTOClassInfo;

/**
 * @author shengyang.zhou@hand-china.com
 */
@Intercepts({ @Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }) })
public class AuditInterceptor implements Interceptor {
    private Logger logger = LoggerFactory.getLogger(AuditInterceptor.class);

    public static final ThreadLocal<String> LOCAL_AUDIT_SESSION = new ThreadLocal<>();

    @Autowired
    private BeanFactory beanFactory;
    private AuditMapper templateMapper;

    @Autowired(required = false)
    private IAuditTableNameProvider auditTableNameProvider = DefaultAuditTableNameProvider.instance;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        Object param = invocation.getArgs()[1];
        if (!(param instanceof BaseDTO)) {
            return invocation.proceed();
        }

        // only enable for @AuditEnabled
        if (param.getClass().getAnnotation(AuditEnabled.class) == null) {
            return invocation.proceed();
        }

        BaseDTO dto = (BaseDTO) param;

        Object result;
        SqlCommandType type = mappedStatement.getSqlCommandType();
        switch (type) {
        case INSERT:
        case UPDATE:
            result = invocation.proceed();
            doAudit(dto, type.name());
            break;
        case DELETE:
            doAudit(dto, type.name());
            result = invocation.proceed();
            break;
        default:
            result = invocation.proceed();
            break;
        }
        return result;
    }

    /**
     * create audit record.
     * 
     * @param param
     *            object to be CRU
     * @throws Exception
     */
    private void doAudit(BaseDTO param, String type) throws Exception {
        Class clazz = param.getClass();
        Table tbl = checkTable(clazz);
        if (tbl == null) {
            return;
        }
        String tableName = tbl.name();
        Field[] ids = DTOClassInfo.getIdFields(clazz);
        if (ids.length == 0) {
            if (logger.isDebugEnabled()) {
                logger.debug("Can not get PrimaryKey(s) of dto class:" + clazz);
            }
            return;
        }
        String asid = LOCAL_AUDIT_SESSION.get();
        if (asid == null) {
        	if(logger.isDebugEnabled()){
        		logger.debug("audit session is null, skip.");
        	}
            return;
        }
        String auditTableName = auditTableNameProvider.getAuditTableName(tableName);

        Map<String, Object> auditParam = new HashMap<>();
        auditParam.put("_baseTableName", tableName);
        auditParam.put("_auditTableName", auditTableName);
        auditParam.put("_auditSessionId", asid);
        auditParam.put("_auditType", type);

        Map<String, Object> pks = new HashMap<>();
        for (Field f : ids) {
            pks.put(f.getAnnotation(Column.class).name(), f.get(param));
        }
        auditParam.put("_pks", pks);

        try {
            int count = getTemplateMapper().auditInsert(auditParam);
            if (count == 1) {
                if (logger.isDebugEnabled()) {
                    logger.debug("audit result:1, normal.");
                }
            } else if (logger.isDebugEnabled()) {
                logger.debug("audit result:{}, abnormal.", count);
            }
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error("audit error:" + e.getMessage());
            }
            throw e;
        }

    }

    private Table checkTable(Class clazz) {
        Table tbl = (Table) clazz.getAnnotation(Table.class);
        if (tbl == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("@Table not found on dto class:" + clazz);
            }
            return null;
        }
        String tableName = tbl.name();
        if (StringUtils.isBlank(tableName)) {
            if (logger.isDebugEnabled()) {
                logger.debug("Can not get tableName of dto class:" + clazz);
            }
            return null;
        }
        return tbl;
    }

    protected AuditMapper getTemplateMapper() {
        if (templateMapper == null) {
            templateMapper = beanFactory.getBean(AuditMapper.class);
        }
        return templateMapper;
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof Executor) {
            return Plugin.wrap(target, this);
        }
        return target;
    }

    @Override
    public void setProperties(Properties properties) {

    }

    public static String generateAndSetAuditSessionId() {
        String id = UUID.randomUUID().toString().replaceAll("-", "");
        LOCAL_AUDIT_SESSION.set(id);
        return id;
    }

    public static void clearAuditSessionId() {
        LOCAL_AUDIT_SESSION.remove();
    }
}
