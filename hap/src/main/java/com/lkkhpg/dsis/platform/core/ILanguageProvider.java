/*
 * Copyright LKK Health Products Group Limited
 */

package com.lkkhpg.dsis.platform.core;

import java.util.List;

import com.lkkhpg.dsis.platform.dto.system.Language;

/**
 * 用于获取系统所支持的语言环境.
 * 
 * @author shengyang.zhou@hand-china.com
 */
public interface ILanguageProvider {

    /**
     * 取得系统支持的语言.
     *
     * @return 没有数据的话, 返回空的 list. 不返回null
     */
    List<Language> getSupportedLanguages();
}
