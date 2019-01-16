/*
 * Copyright LKK Health Products Group Limited
 */

package com.lkkhpg.dsis.admin.system.exception;

import com.lkkhpg.dsis.platform.exception.BaseException;

/**
 * @author shengyang.zhou@hand-china.com
 */
public class UploadException extends BaseException {
    public UploadException(String code) {
        super(code, code, null);
    }
}
