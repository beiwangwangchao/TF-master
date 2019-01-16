/*
 *
 */

package com.lkkhpg.dsis.integration.gds.exception;

/**
 * ESB 返回错误的异常.
 * <p>
 * HTTP 400,401,500等错误,都通过此异常表达
 * 
 * @author shengyang.zhou@hand-china.com
 */
public class ESBException extends RuntimeException {
    private int errorCode;
    private String errorResponse;

    /**
     * 
     * @param code
     *            http code(400,401,500 etc)
     * @param message
     *            message contains in errorResponse(may be null)
     * @param errorResponse
     *            full response (json)
     * @param cause
     *            original exception(WebApplicationException)
     */
    public ESBException(int code, String message, String errorResponse, Throwable cause) {
        super(message, cause);
        this.errorCode = code;
        this.errorResponse = errorResponse;
    }

    /**
     * 取得Http code.
     * 
     * @return http code
     */
    public int getErrorCode() {
        return errorCode;
    }

    /**
     * 错误状态下的返回数据.
     * 
     * @return json
     */
    public String getErrorResponse() {
        return errorResponse;
    }
}
