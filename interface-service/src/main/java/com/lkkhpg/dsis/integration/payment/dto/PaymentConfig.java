package com.lkkhpg.dsis.integration.payment.dto;

import com.lkkhpg.dsis.platform.dto.BaseDTO;
import com.sun.istack.NotNull;

public class PaymentConfig extends BaseDTO {

    /**
     * 
     */
    private static final long serialVersionUID = 4296176854054082033L;
    private String configID;
    private String paymentType;
    @NotNull
    private String key;
    private String value;

    public String getConfigID() {
        return configID;
    }

    public void setConfigID(String configID) {
        this.configID = configID;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getKey() {
        return key == null ? key : key.trim();
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value == null ? value : value.trim();
    }

    public void setValue(String value) {
        this.value = value;
    }

}
