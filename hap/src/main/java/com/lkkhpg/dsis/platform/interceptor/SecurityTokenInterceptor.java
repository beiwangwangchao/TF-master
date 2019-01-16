/*
 *
 */
package com.lkkhpg.dsis.platform.interceptor;

import java.util.List;
import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import com.lkkhpg.dsis.platform.dto.BaseDTO;
import com.lkkhpg.dsis.platform.security.TokenUtils;

/**
 * 自动数据多语言支持.
 *
 * @author shengyang.zhou@hand-china.com
 */
@Intercepts({
        @Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class,
                RowBounds.class, ResultHandler.class }),
        @Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }) })
public class SecurityTokenInterceptor implements Interceptor {

    public static final ThreadLocal<String> LOCAL_SECURITY_KEY = new ThreadLocal<>();

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object result = invocation.proceed();

        String securityKey = LOCAL_SECURITY_KEY.get();
        if (securityKey == null) {
            return result;
        }

        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        if (mappedStatement.getSqlCommandType() == SqlCommandType.SELECT) {
            if (result instanceof List) {
                for (Object o : (List) result) {
                    if (o instanceof BaseDTO) {
                        TokenUtils.generateAndSetToken(securityKey, (BaseDTO) o);
                    }
                }
            }
        } else if (mappedStatement.getSqlCommandType() == SqlCommandType.INSERT) {
            Object domain = invocation.getArgs()[1];
            if (domain instanceof BaseDTO) {
                TokenUtils.generateAndSetToken(securityKey, (BaseDTO) domain);
            }
        }
        return result;
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

}
