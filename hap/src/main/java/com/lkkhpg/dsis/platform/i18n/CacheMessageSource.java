/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.platform.i18n;

import java.text.MessageFormat;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.AbstractMessageSource;

import com.lkkhpg.dsis.platform.cache.impl.HashStringRedisCache;

/**
 * CacheMessageSource.
 * 
 * @author shengyang.zhou@hand-china.com
 */
public class CacheMessageSource extends AbstractMessageSource {

    private static final String SINGLE_QUOTES_REPLACEMENT = "&#39;";
    private static final String DOUBLE_QUOTES_REPLACEMENT = "&#34;";

    private static final String[] REPLACE_CHARS = { "'", "\"" };
    private static final String[] REPLACEMENT = { SINGLE_QUOTES_REPLACEMENT, DOUBLE_QUOTES_REPLACEMENT };

    @Autowired
    @Qualifier("promptCache")
    private HashStringRedisCache<String> promptCache;

    public CacheMessageSource() {
        reload();
    }

    public void reload() {
    }

    @Override
    protected MessageFormat resolveCode(String code, Locale locale) {
        code = StringUtils.lowerCase(code);
        String pmt = promptCache.getValue(code + "." + locale);
        if (pmt != null) {
            pmt = StringUtils.replaceEach(pmt, REPLACE_CHARS, REPLACEMENT);
        }
        return createMessageFormat(pmt == null ? code : pmt, locale);
    }

    @Override
    protected String resolveCodeWithoutArguments(String code, Locale locale) {
        code = StringUtils.lowerCase(code);
        String pmt = promptCache.getValue(code + "." + locale);
        if (pmt != null) {
            pmt = StringUtils.replaceEach(pmt, REPLACE_CHARS, REPLACEMENT);
        }
        return pmt == null ? code : pmt;
    }
}
