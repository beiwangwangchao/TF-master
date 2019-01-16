/*
 *
 */
package com.lkkhpg.dsis.mws.exception;

import com.lkkhpg.dsis.platform.exception.BaseException;

/**
 * mws账户信息异常.
 * 
 * @author guanghui.liu
 */
public class OrderException extends BaseException {

    private static final long serialVersionUID = 1L;

    private static final String EXCEPTION_CODE = "com.lkkhpg.dsis.mws.order";

    /**
     * 未登录.
     */
    public static final String SHOPPING_CART_ERROR = "msg.error.mws.shippingcart.empty";

    /**
     * 构造方法.
     * 
     * @param descriptionKey
     *            消息代码.
     * @param parameters
     *            消息参数.
     */
    public OrderException(String descriptionKey, Object[] parameters) {
        super(EXCEPTION_CODE, descriptionKey, parameters);
    }

}
