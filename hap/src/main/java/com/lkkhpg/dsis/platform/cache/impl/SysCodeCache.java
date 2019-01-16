/*
 *
 */
package com.lkkhpg.dsis.platform.cache.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;

import com.lkkhpg.dsis.platform.core.ILanguageProvider;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.impl.RequestHelper;
import com.lkkhpg.dsis.platform.core.impl.ServiceRequest;
import com.lkkhpg.dsis.platform.dto.system.Code;
import com.lkkhpg.dsis.platform.dto.system.CodeValue;
import com.lkkhpg.dsis.platform.dto.system.Language;
import com.lkkhpg.dsis.platform.mapper.system.CodeMapper;
import com.lkkhpg.dsis.platform.mapper.system.CodeValueMapper;

/**
 * @author shengyang.zhou@hand-china.com
 */
public class SysCodeCache extends HashStringRedisCache<Code> {

    private Logger logger = LoggerFactory.getLogger(SysCodeCache.class);

    private String codeQuerySqlId = CodeMapper.class.getName() + ".selectCodes";
    private String codeValueQuerySqlId = CodeValueMapper.class.getName() + ".selectCodeValues";

    @Autowired
    private ILanguageProvider languageProvider;

    @Override
    public void init() {
        setType(Code.class);
        strSerializer = getRedisTemplate().getStringSerializer();
        initLoad();
    }

    /**
     * key 包含 code 和语言两部分,用'.'拼接.
     *
     * @param key
     *            code.lang
     * @return 一个仅包含 code value 的空的 code
     */
    @Override
    public Code getValue(String key) {
        return super.getValue(key);
    }

    /**
     *
     * @param key
     *            code.lang
     * @param code
     *            需要放入缓存的 Code
     */
    @Override
    public void setValue(String key, Code code) {
        super.setValue(key, code);
    }

    /**
     * 
     * @param key
     *            code
     */
    @Override
    public void remove(String key) {
        getRedisTemplate().execute((RedisCallback) (connection) -> {
            for (Language language : languageProvider.getSupportedLanguages()) {
                connection.hDel(strSerializer.serialize(getFullKey(key)),
                        strSerializer.serialize(key + "." + language.getLangCode()));
            }
            return null;
        });
    }

    public void reload(Long codeId) {
        IRequest oldRequest = RequestHelper.getCurrentRequest();
        try (SqlSession sqlSession = getSqlSessionFactory().openSession()) {
            for (Language language : languageProvider.getSupportedLanguages()) {
                IRequest request = new ServiceRequest();
                request.setLocale(language.getLangCode());
                RequestHelper.setCurrentRequest(request);
                Code para = new Code();
                para.setCodeId(codeId);
                Code code = sqlSession.selectOne(codeQuerySqlId, para);
                CodeValue p2 = new CodeValue();
                p2.setCodeId(codeId);
                List<CodeValue> codeValues = sqlSession.selectList(codeValueQuerySqlId, p2);
                code.setCodeValues(codeValues);
                setValue(code.getCode() + "." + language.getLangCode(), code);
            }
        } finally {
            RequestHelper.setCurrentRequest(oldRequest);
        }
    }

    @Override
    protected void initLoad() {
        Map<Long, Code> tempMap = new HashMap<>();
        try (SqlSession sqlSession = getSqlSessionFactory().openSession()) {
            for (Language language : languageProvider.getSupportedLanguages()) {
                IRequest request = new ServiceRequest();
                request.setLocale(language.getLangCode());
                RequestHelper.setCurrentRequest(request);

                sqlSession.select(codeQuerySqlId, (resultContext) -> {
                    Code code = (Code) resultContext.getResultObject();
                    tempMap.put(code.getCodeId(), code);
                });

                sqlSession.select(codeValueQuerySqlId, new CodeValue(), (resultContext) -> {
                    CodeValue value = (CodeValue) resultContext.getResultObject();
                    Code code = tempMap.get(value.getCodeId());
                    if (code != null) {
                        List<CodeValue> codeValues = code.getCodeValues();
                        if (codeValues == null) {
                            codeValues = new ArrayList<>();
                            code.setCodeValues(codeValues);
                        }
                        codeValues.add(value);
                    }
                });
                tempMap.forEach((k, v) -> {
                    setValue(v.getCode() + "." + language.getLangCode(), v);
                });
                tempMap.clear();
            }
            RequestHelper.clearCurrentRequest();
        } catch (Throwable e) {
            if (logger.isErrorEnabled()) {
                logger.error("init syscode exception ", e);
            }
        }
    }

}
