/*
 * Copyright LKK Health Products Group Limited
 */

package com.lkkhpg.dsis.platform.service;

import java.util.List;

import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;
import com.lkkhpg.dsis.platform.dto.system.Language;

/**
 * @author shengyang.zhou@hand-china.com
 */
public interface ILanguageService extends ProxySelf<ILanguageService> {
    /**
     * 插入.
     * 
     * @param lang
     *            新数据
     * @return 插入成功的记录数 0|1
     */
    Language insert(Language lang);

    /**
     * 更新.
     * 
     * @param lang
     *            新数据
     * @return 更新成功的记录数0|1
     */
    Language update(Language lang);

    /**
     * 删除.
     * 
     * @param lang
     *            待删数据
     * @return 删除成功的记录数 0|1
     */
    int delete(Language lang);

    /**
     * 查询全部数据,用于缓存.
     * 
     * @return 所有语言数据
     */
    List<Language> selectAll();

    /**
     * 条件查询.
     * 
     * @param request
     *            request context
     * @param example
     *            包含条件
     * @param page
     *            page
     * @param pagesize
     *            pageSize
     * @return 查询结果
     */
    List<Language> select(IRequest request, Language example, int page, int pagesize);

    /**
     * 批量 增改.
     * 
     * @param request
     *            request context
     * @param languageList
     *            datas
     * @return the datas passed in
     */
    List<Language> batchUpdate(IRequest request, @StdWho List<Language> languageList);

    /**
     * 批量删除.
     * 
     * @param languageList
     *            data to delete
     */
    void batchDelete(List<Language> languageList);
}
