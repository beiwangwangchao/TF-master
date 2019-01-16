/*
 *
 */

package com.lkkhpg.dsis.platform.mapper.system;

import java.util.List;

import com.lkkhpg.dsis.platform.dto.system.Language;

/**
 * @author wuyichu
 */
public interface LanguageMapper {
    int deleteByPrimaryKey(String langCode);

    int insert(Language record);

    int insertSelective(Language record);

    Language selectByPrimaryKey(String langCode);

    int updateByPrimaryKeySelective(Language record);

    int updateByPrimaryKey(Language record);

    List<Language> select(Language example);
}