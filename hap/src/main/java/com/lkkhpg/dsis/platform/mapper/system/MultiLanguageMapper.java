/*
 *
 */

package com.lkkhpg.dsis.platform.mapper.system;

import java.util.List;
import java.util.Map;

import com.lkkhpg.dsis.platform.dto.system.MultiLanguageField;

/**
 * 
 * @author njq.niu@hand-china.com
 *
 *         2016年3月30日
 */
public interface MultiLanguageMapper {

    List<MultiLanguageField> select(Map<String, String> map);
    
    List<String> querySysLangs();
}