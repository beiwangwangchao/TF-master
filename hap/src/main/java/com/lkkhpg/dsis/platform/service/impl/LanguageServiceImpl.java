/*
 *
 */
package com.lkkhpg.dsis.platform.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.lkkhpg.dsis.platform.cache.CacheDelete;
import com.lkkhpg.dsis.platform.cache.CacheSet;
import com.lkkhpg.dsis.platform.cache.impl.HashStringRedisCache;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.DTOStatus;
import com.lkkhpg.dsis.platform.dto.system.Language;
import com.lkkhpg.dsis.platform.mapper.system.LanguageMapper;
import com.lkkhpg.dsis.platform.service.ILanguageService;

/**
 * @author shengyang.zhou@hand-china.com
 */
@Service
public class LanguageServiceImpl implements ILanguageService {

    @Autowired
    private LanguageMapper languageMapper;

    @Autowired
    @Qualifier("languageCache")
    private HashStringRedisCache<Language> languageCache;

    @Override
    @CacheSet(cache = "language")
    public Language insert(Language lang) {
        languageMapper.insert(lang);
        return lang;
    }

    @Override
    @CacheSet(cache = "language")
    public Language update(Language lang) {
        languageMapper.updateByPrimaryKey(lang);
        return lang;
    }

    @Override
    @CacheDelete(cache = "language")
    public int delete(Language lang) {
        // sys_lang 暂时没有多语言支持的必要,删除时可以用主键直接删除
        return languageMapper.deleteByPrimaryKey(lang.getLangCode());
    }

    @Override
    public List<Language> selectAll() {
        return languageCache.getAll();
    }

    @Override
    public List<Language> select(IRequest request, Language example, int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
        return languageMapper.select(example);
    }

    @Override
    public List<Language> batchUpdate(IRequest request, List<Language> languageList) {
        for (Language language : languageList) {
            if (DTOStatus.ADD.equals(language.get__status())) {
                self().insert(language);
            } else if (DTOStatus.UPDATE.equals(language.get__status())) {
                self().update(language);
            } else if (DTOStatus.DELETE.equals(language.get__status())) {
                self().delete(language);
            }
        }
        return languageList;
    }

    @Override
    public void batchDelete(List<Language> languageList) {
        languageList.forEach(self()::delete);
    }

}
