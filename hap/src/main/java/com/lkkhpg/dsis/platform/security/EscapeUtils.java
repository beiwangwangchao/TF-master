/*
 *
 */
package com.lkkhpg.dsis.platform.security;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.util.HtmlUtils;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * @author njq.niu@hand-china.com
 *
 *         2016年7月6日
 */
public final class EscapeUtils {

    private static Logger logger = LoggerFactory.getLogger(EscapeUtils.class);

    private static final Map<Class<?>, Field[]> CLASS_FIELD_MAPPING = new HashMap<>();

    private EscapeUtils() {
    }

    private static synchronized void analysis(Class<?> clazz) {
        if (CLASS_FIELD_MAPPING.get(clazz) == null) {
            List<Field> fields = new ArrayList<>();
            ReflectionUtils.doWithFields(clazz, fields::add);
            for (Field f : fields) {
                f.setAccessible(true);
            }
            CLASS_FIELD_MAPPING.put(clazz, fields.toArray(new Field[fields.size()]));
        }
    }

    /**
     * @param dto
     */
    public static void escapeObject(Object dto) {
        if (CLASS_FIELD_MAPPING.get(dto.getClass()) == null) {
            analysis(dto.getClass());
        }
        try {
            Field[] fields = CLASS_FIELD_MAPPING.get(dto.getClass());
            for (Field f : fields) {
                Class<?> clazz = f.getType();
                if (String.class.equals(clazz)) {
                    String str = (String) f.get(dto);
                    if (str != null && str.length() > 0) {
                        str = HtmlUtils.htmlEscape(str);
                        f.set(dto, str);
                    }
                } else if (BaseDTO.class.isAssignableFrom(clazz)) {
                    BaseDTO d = (BaseDTO) f.get(dto);
                    escapeObject(d);
                }
            }
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
        }
    }
    
    /**
     * @param list
     */
    public static void escapeList(List<?> list) {
        list.forEach(a -> {
            escapeObject(a);
        });
    }

}
