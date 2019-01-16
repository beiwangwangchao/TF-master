/*
 *
 */

package com.lkkhpg.dsis.platform.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 修订,历史记录启用标记.
 * 
 * @author shengyang.zhou@hand-china.com
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface AuditEnabled {
}