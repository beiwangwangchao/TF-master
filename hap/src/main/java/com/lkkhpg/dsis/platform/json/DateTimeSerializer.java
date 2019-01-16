/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.platform.json;

import java.io.IOException;
import java.util.Date;

import org.apache.commons.lang.time.DateFormatUtils;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.lkkhpg.dsis.platform.util.TimeZoneUtil;

/**
 * 时间序列化类.
 * 
 * @author njq.niu@hand-china.com
 *
 *         2016年3月16日
 */
public class DateTimeSerializer extends JsonSerializer<Date> implements ContextualSerializer {

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
    public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider aSerializerProvider)
            throws IOException, JsonProcessingException {
        jsonGenerator.writeString(DateFormatUtils.format(date, getPattern(), TimeZoneUtil.getTimeZone()));
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property)
            throws JsonMappingException {
        DateTimeSerializer serializer = new DateTimeSerializer();
        JsonFormat jf = property.getMember().getAnnotation(JsonFormat.class);
        if (jf != null && !"".equals(jf.pattern())) {
            serializer.setPattern(jf.pattern());
        }
        return serializer;
    }
}
