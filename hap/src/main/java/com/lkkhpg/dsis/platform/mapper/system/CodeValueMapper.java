/*
 *
 */

package com.lkkhpg.dsis.platform.mapper.system;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lkkhpg.dsis.platform.dto.system.CodeValue;

/**
 * @author shengyang.zhou@hand-china.com
 */
public interface CodeValueMapper {

    int deleteByPrimaryKey(CodeValue key);

    int deleteByCodeValueId(CodeValue key);

    int deleteByCodeId(CodeValue key);

    int deleteTlByCodeId(CodeValue key);

    int insert(CodeValue record);

    CodeValue selectByPrimaryKey(CodeValue key);

    List<CodeValue> selectCodeValues(CodeValue record);

    List<CodeValue> selectCodeValuesByCodeName(String codeName);

    List<CodeValue> selectCodeValuesByCode(@Param("codeName") String codeName, @Param("langCode") String langcode);

    int updateByPrimaryKey(CodeValue record);

    List<CodeValue> queryMsgTemCodeLov(@Param("value") String value, @Param("meaning") String meaning);

    List<CodeValue> queryEmlAccountCodeLov(@Param("value") String value, @Param("meaning") String meaning);

    String getMemberRoleCodeByDescription(@Param("description") String description);

    List<CodeValue> queryMemberTypeCodeByDescription(@Param("description") String description);

    String getDefaultCountryByMarketCode(@Param("marketCode") String marketCode);



  List<String> getBankCodeByBankName(@Param("bankName") String bankName);

    List<CodeValue>  getUserNameByCode();
}