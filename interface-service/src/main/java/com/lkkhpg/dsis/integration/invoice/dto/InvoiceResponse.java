/*
 * Copyright LKK Health Products Group Limited
 */

package com.lkkhpg.dsis.integration.invoice.dto;

/**
 * @param <T>
 *            data 类型
 * @author shengyang.zhou@hand-china.com
 */
public class InvoiceResponse<T> {

    private T data;

    private Status status = new Status();

    public InvoiceResponse() {

    }

    public InvoiceResponse(T data) {
        this();
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public static class Status {
        private String value;
        private int code = 0;
        private String message = "success";

        public Status() {

        }

        public Status(String value, int code, String message) {
            this.value = value;
            this.code = code;
            this.message = message;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
