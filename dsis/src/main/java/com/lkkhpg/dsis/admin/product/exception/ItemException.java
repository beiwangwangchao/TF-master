/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.product.exception;

import com.lkkhpg.dsis.common.exception.CommItemException;

/**
 * 商品统一异常.
 * 
 * @author chenjingxiong
 */
public class ItemException extends CommItemException {

    
    /**
     * 构造方法.
     * 
     * @param descriptionKey
     *            消息代码.
     * @param parameters
     *            消息参数.
     */
    public ItemException(String descriptionKey, Object[] parameters) {
        super(descriptionKey, parameters);
    }

}
