/*
 *
 */
package com.lkkhpg.dsis.platform.cache.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lkkhpg.dsis.platform.mapper.system.RoleFunctionMapper;

/**
 * 角色资源缓存.
 * 
 * @author wuyichu
 */
public class RoleResourceCache extends HashStringRedisCache<Set<Long>> {

    private String roleResouceQuerySqlId = RoleFunctionMapper.class.getName() + ".selectAllRoleResouces";

    private String roleResoucesSqlId = RoleFunctionMapper.class.getName() + ".selectRoleResouces";

    private final Logger logger = LoggerFactory.getLogger(RoleResourceCache.class);

    {
        setType(HashSet.class);
    }

    @Override
    public void init() {
        strSerializer = getRedisTemplate().getStringSerializer();
        initLoad();
    }

    /**
     * key 为roleId.
     *
     * @param key
     *            roleId
     * @return values
     */
    @Override
    public Set<Long> getValue(String key) {
        return super.getValue(key);
    }

    /*
     * .
     * 
     * @see
     * HashStringRedisCache#setValue(java.lang.String,
     * java.lang.Object)
     */
    @Override
    public void setValue(String key, Set<Long> values) {
        super.setValue(key, values);
    }

    protected void initLoad() {
        Map<String, Set<Long>> roleResources = new HashMap<>();
        try (SqlSession sqlSession = getSqlSessionFactory().openSession()) {
            sqlSession.select(roleResouceQuerySqlId, (resultContext) -> {
                Map<String, Object> value = (Map<String, Object>) resultContext.getResultObject();
                String roleId = "" + value.get("ROLE_ID");
                Set<Long> sets = roleResources.get(roleId);
                if (sets == null) {
                    sets = new HashSet<>();
                    roleResources.put(roleId, sets);
                }
                Long resourceId = ((Number) value.get("RESOURCE_ID")).longValue();
                sets.add(resourceId);
            });

            roleResources.forEach((k, v) -> {
                setValue(k, v);
            });
        } catch (Throwable e) {
            if (logger.isErrorEnabled()) {
                logger.error("init role resource cache exception: ", e);
            }
        }
    }

    /**
     * 按照roleId加载资源.
     * 
     * @param roleId 角色id
     */
    public void loadRoleResource(Long roleId) {
        Map<Long, Set<Long>> roleResources = new HashMap<>();
        try (SqlSession sqlSession = getSqlSessionFactory().openSession()) {
            sqlSession.select(roleResoucesSqlId, roleId, (resultContext) -> {
                Map<String, Object> value = (Map<String, Object>) resultContext.getResultObject();
                Set<Long> sets = roleResources.get(roleId);
                if (sets == null) {
                    sets = new HashSet<>();
                    roleResources.put(roleId, sets);
                }
                Long resourceId = ((Number) value.get("RESOURCE_ID")).longValue();
                sets.add(resourceId);
            });

            roleResources.forEach((k, v) -> {
                setValue(k.toString(), v);
            });
        } catch (Throwable e) {
            if (logger.isErrorEnabled()) {
                logger.error("loadRoleResource exception: ", e);
            }
        }
    }
}
