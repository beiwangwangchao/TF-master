/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.platform.json;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.lkkhpg.dsis.platform.util.TimeZoneUtil;

/**
 * 时间序反列化类.
 * 
 * @author njq.niu@hand-china.com
 *
 *         2016年3月16日
 */
public class DateTimeDeserializer extends JsonDeserializer<Date> implements ContextualDeserializer {

    private static Logger log = LoggerFactory.getLogger(DateTimeDeserializer.class);
    private String pattern = "yyyy-MM-dd";

    /**
     * @return the pattern
     */
    public String getPattern() {
        return pattern;
    }

    /**
     * @param pattern
     *            the pattern to set
     */
    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public Date deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        Date date = null;
        try {
            // TODO:优化!
            SimpleDateFormat sb = new SimpleDateFormat(getPattern());
            sb.setTimeZone(TimeZoneUtil.getTimeZone());
            date = sb.parse(jp.getText());
        } catch (ParseException e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage(), e);
            }
        }
        return date;
    }

    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property)
            throws JsonMappingException {
        DateTimeDeserializer deserializer = new DateTimeDeserializer();
        JsonFormat jf = property.getMember().getAnnotation(JsonFormat.class);
        if (jf != null && !"".equals(jf.pattern())) {
            deserializer.setPattern(jf.pattern());
        }
        return deserializer;
    }

}
