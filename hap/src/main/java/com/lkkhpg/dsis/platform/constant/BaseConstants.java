/*
 *
 */
package com.lkkhpg.dsis.platform.constant;

/**
 * 常量基类.
 * 
 * @author chenjingxiong
 */
public class BaseConstants {

    protected BaseConstants() {
    }

    /**
     * 基本常量 - 是 标记.
     */
    public static final String YES = "Y";

    /**
     * 基本常量 - 否 标记.
     */
    public static final String NO = "N";

    public static final String SYSTEM_MAX_DATE = "9999/12/31 23:59:59";
    
    public static final String SYSTEM_DATE_FORMAT = "yyyy/MM/dd HH:mm:ss";
    
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final String DATE_FORMAT = "yyyy-MM-dd";

    /**
     * 缓存ID
     */
    public static final String CACHE_RESOURCE_URL = "resource_url";
    public static final String CACHE_RESOURCE_ID = "resource_id";
    
    public static final String ROLE_RESOURCE_CACHE = "role_resource";
    
    /**
     * 默认语言.
     */
    public static final String DEFAULT_LANG = "en_GB";

    public static final String TIME_ZONE = "timeZone";

}
