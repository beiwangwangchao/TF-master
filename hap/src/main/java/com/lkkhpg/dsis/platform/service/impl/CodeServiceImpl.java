/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.platform.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.lkkhpg.dsis.platform.cache.impl.SysCodeCache;
import com.lkkhpg.dsis.platform.core.ILanguageProvider;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.system.Code;
import com.lkkhpg.dsis.platform.dto.system.CodeValue;
import com.lkkhpg.dsis.platform.dto.system.Language;
import com.lkkhpg.dsis.platform.mapper.system.CodeMapper;
import com.lkkhpg.dsis.platform.mapper.system.CodeValueMapper;
import com.lkkhpg.dsis.platform.service.ICodeService;

/**
 * @author shengyang.zhou@hand-china.com
 */
@Service
@Transactional
public class CodeServiceImpl implements ICodeService {
    @Autowired
    private CodeMapper codeMapper;

    @Autowired
    private CodeValueMapper codeValueMapper;

    @Autowired
    private ILanguageProvider languageProvider;

    @Autowired
    private SysCodeCache codeCache;

    /**
     * 批量操作快码行数据.
     *
     * @param code
     *            头行数据
     */
    private void processCodeValues(Code code) {
        for (CodeValue codeValue : code.getCodeValues()) {
            if (codeValue.getCodeValueId() == null) {
                codeValue.setCodeId(code.getCodeId()); // 设置头ID跟行ID一致
                codeValueMapper.insert(codeValue);
            } else if (codeValue.getCodeValueId() != null) {
                codeValueMapper.updateByPrimaryKey(codeValue);
            }
        }
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Code> selectCodes(IRequest request, Code code, int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
        List<Code> codes = codeMapper.selectCodes(code);
        return codes;
    }

    @Override
    public List<CodeValue> selectCodeValues(IRequest request, CodeValue value) {
        return codeValueMapper.selectCodeValues(value);
    }

    @Override
    public List<CodeValue> selectCodeValuesByCodeName(IRequest request, String codeName) {
        Code code = codeCache.getValue(codeName + "." + request.getLocale());
        if (code != null) {
            return code.getCodeValues();
        } else {
            return codeValueMapper.selectCodeValuesByCodeName(codeName);
        }
    }

    /**
     * 根据代码和值获取CodeValue.
     * <p>
     * 从 cache 直接取值.
     *
     * @param request
     *            请求上下文
     * @param codeName
     *            代码
     * @param value
     *            代码值
     * @return codeValue 代码值DTO
     * @author frank.li
     */
    @Override
    public CodeValue getCodeValue(IRequest request, String codeName, String value) {
        Code code = codeCache.getValue(codeName + "." + request.getLocale());
        if (code == null) {
            return null;
        }

        if (code.getCodeValues() == null) {
            return null;
        }
        for (CodeValue v : code.getCodeValues()) {
            if (value.equals(v.getValue())) {
                return v;
            }
        }
        return null;
    };

    /**
     * 根据代码和含义获取代码值.
     * <p>
     * 从 cache 直接取值.
     *
     * @param request
     *            请求上下文
     * @param codeName
     *            代码
     * @param meaning
     *            含义
     * @return value 代码值
     * @author frank.li
     */
    @Override
    public String getCodeValueByMeaning(IRequest request, String codeName, String meaning) {
        Code code = codeCache.getValue(codeName + "." + request.getLocale());
        if (code == null) {
            return null;
        }

        if (code.getCodeValues() == null) {
            return null;
        }
        for (CodeValue v : code.getCodeValues()) {
            if (meaning.equals(v.getMeaning())) {
                return v.getValue();
            }
        }
        return null;
    };

    /**
     * 根据代码和含义获取代码值列表.
     * <p>
     * 从 cache 直接取值.
     *
     * @param request
     *            请求上下文
     * @param codeName
     *            代码
     * @param meaning
     *            含义
     * @return value 代码值
     * @author frank.li
     */
    @Override
    public List<CodeValue> getCodeValuesByMeaning(IRequest request, String codeName, String meaning) {
        Code code = codeCache.getValue(codeName + "." + request.getLocale());
        if (code == null) {
            return null;
        }

        if (code.getCodeValues() == null) {
            return null;
        }

        List<CodeValue> codes = new ArrayList<CodeValue>();
        for (CodeValue value : code.getCodeValues()) {
            if (meaning.equals(value.getMeaning())) {
                codes.add(value);
            }
        }
        return codes;
    };

    /**
     * 根据代码、含义、描述获取代码值.
     * <p>
     * 从 cache 直接取值.
     *
     * @param request
     *            请求上下文
     * @param codeName
     *            代码
     * @param meaning
     *            含义
     * @param description
     *            说明
     * @return value 代码值
     * @author frank.li
     */
    @Override
    public String getCodeValueByMeaningAndDesc(IRequest request, String codeName, String meaning, String description) {
        Code code = codeCache.getValue(codeName + "." + request.getLocale());
        if (code == null) {
            return null;
        }

        if (code.getCodeValues() == null) {
            return null;
        }
        for (CodeValue v : code.getCodeValues()) {
            if (meaning.equals(v.getMeaning()) && description.equals(v.getDescription())) {
                return v.getValue();
            }
        }
        return null;
    };

    /**
     * 根据代码和值获取含义.
     *
     * @param codeName
     *            代码
     * @param value
     *            代码值
     * @return meaning 含义
     */
    @Override
    public String getCodeMeaningByValue(IRequest request, String codeName, String value) {
//        System.out.println(codeName);
//        System.out.println(request.getLocale());
        Code code = codeCache.getValue(codeName + "." + request.getLocale());

//        System.out.println("code:"+code);
        if (code == null) {
            return null;
        }

        if (code.getCodeValues() == null) {
            return null;
        }
        for (CodeValue v : code.getCodeValues()) {
//            System.out.println("value:" + value);
//            System.out.println("v.getValue() :" + v.getValue());
            if (value.equals(v.getValue())) {
                return v.getMeaning();
            }
        }
        return null;
    };

    /**
     * 根据代码和值获取描述.
     *
     * @param codeName
     *            代码
     * @param value
     *            代码值
     * @return description 描述
     */
    @Override
    public String getCodeDescByValue(IRequest request, String codeName, String value) {
        Code code = codeCache.getValue(codeName + "." + request.getLocale());
        if (code == null) {
            return null;
        }

        if (code.getCodeValues() == null) {
            return null;
        }
        for (CodeValue v : code.getCodeValues()) {
            if (value.equals(v.getValue())) {
                return v.getDescription();
            }
        }
        return null;
    }

    @Override
    public List<String> getBankCodeByBankName(IRequest request, String bankName) {
        return  codeValueMapper.getBankCodeByBankName(bankName);
    }

    ;

    @Override
    public Code createCode(Code code) {
        // 插入头行
        codeMapper.insert(code);
        // 判断如果行不为空，则迭代循环插入
        if (code.getCodeValues() != null) {
            processCodeValues(code);
        }
        for (Language lang : languageProvider.getSupportedLanguages()) {
            codeCache.setValue(code.getCode() + "." + lang.getLangCode(), code);
        }
        return code;
    }

    @Override
    public boolean batchDelete(IRequest request, List<Code> codes) {
        // 删除头行
        for (Code code : codes) {
            CodeValue codeValue = new CodeValue();
            codeValue.setCodeId(code.getCodeId());
            // 首先删除行的多语言数据
            codeValueMapper.deleteTlByCodeId(codeValue);
            // 然后删除行
            codeValueMapper.deleteByCodeId(codeValue);

            // 最后删除头
            codeMapper.deleteByPrimaryKey(code);
            codeCache.remove(code.getCode());
        }
        return true;
    }

    @Override
    public boolean batchDeleteValues(IRequest request, List<CodeValue> values) {
        for (CodeValue value : values) {
            codeValueMapper.deleteByPrimaryKey(value);
        }
        return true;
    }

    @Override
    public Code updateCode(Code code) {
        codeMapper.updateByPrimaryKeySelective(code);
        // 判断如果行不为空，则迭代循环插入
        if (code.getCodeValues() != null) {
            processCodeValues(code);
        }
        codeCache.remove(code.getCode());
        codeCache.reload(code.getCodeId());
        return code;
    }

    @Override
    public List<Code> batchUpdate(IRequest request, List<Code> codes) {
        for (Code code : codes) {
            if (code.getCodeId() == null) {
                self().createCode(code);
            } else if (code.getCodeId() != null) {
                self().updateCode(code);
            }
        }
        return codes;
    }

    /**
     * 根据代码和描述获取代码值.
     * <p>
     * 从 cache 直接取值.
     *
     * @param request
     *            请求上下文
     * @param codeName
     *            代码
     * @param description
     *            说明
     * @return value 代码值
     * @author linyuheng
     */
    @Override
    public String getCodeValueByDesc(IRequest request, String codeName, String description) {
        Code code = codeCache.getValue(codeName + "." + request.getLocale());
        if (code == null) {
            return null;
        }

        if (code.getCodeValues() == null) {
            return null;
        }
        for (CodeValue v : code.getCodeValues()) {
            if (description.equals(v.getDescription())) {
                return v.getValue();
            }
        }
        return null;
    };

//    @Override
//    public String getBankCodeByBankName(IRequest request, String bankName){
//        String bankCode = codeValueMapper.getBankCodeByBankName(bankName);
//        return bankCode;
//    };

}
