/*
 *
 */

package com.lkkhpg.dsis.platform.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 多语言字段注解.
 * @author shengyang.zhou@hand-china.com
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MultiLanguageField {
}