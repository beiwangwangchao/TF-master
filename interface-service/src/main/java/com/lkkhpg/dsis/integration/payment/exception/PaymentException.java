/*
 *
 */
package com.lkkhpg.dsis.integration.payment.exception;

/**
 * @author shiliyan
 *
 */
public class PaymentException extends Exception {
    private static final String MSG_ERROR_EXCEPTION = "msg.error.payment.exception.";

    public static final String CODE = "PAYMENT_EXCEPTION";

    public PaymentException(String descriptionKey, Object[] parameters) {
        this(CODE, MSG_ERROR_EXCEPTION + descriptionKey, parameters);
    }

    public PaymentException(String descriptionKey) {
        this(descriptionKey, null);
    }

    private static final long serialVersionUID = 1L;

    // e.g. ORDER_FROZEN
    private String code;

    private String descriptionKey;

    private Object[] parameters;

    public static final String UNION_CALLBACK_MAC_ERR = MSG_ERROR_EXCEPTION + "chinatrust.union.callback.mac.err";

    public static final String NON_UNION_CALLBACK_MAC_ERR = MSG_ERROR_EXCEPTION
            + "chinatrust.non.union.callback.checkurloutmac.err {}";
    public static final String NON_UNION_CALLBACK_MAC_M = MSG_ERROR_EXCEPTION
            + "chinatrust.non.union.callback.checkurloutmac.err {0}";

    public static final String MIGS_PAY_ENCODE_ERR = MSG_ERROR_EXCEPTION + "migs.pay.encode.err";
    public static final String MIGS_PAY_CALLBACK_DECODE_ERR = MSG_ERROR_EXCEPTION + "migs.pay.decode.err {}";
    public static final String MIGS_PAY_CALLBACK_DECODE_M = MSG_ERROR_EXCEPTION + "migs.pay.decode.err {0}";

    /**
     * 
     * @param code
     *            异常 code,通常与模块 CODE 对应
     * @param descriptionKey
     *            异常消息代码,系统描述中定义
     * @param parameters
     *            如果没有参数,可以传 null
     */
    public PaymentException(String code, String descriptionKey, Object[] parameters) {
        super(descriptionKey);
        this.code = code;
        this.descriptionKey = descriptionKey;
        this.parameters = parameters;
    }

    public String getCode() {
        return code;
    }

    public String getDescriptionKey() {
        return descriptionKey;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setDescriptionKey(String descriptionKey) {
        this.descriptionKey = descriptionKey;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }

}
