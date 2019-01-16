/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.integration.payment.configration;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * @author shiliyan
 *
 */
public class PaymentConfigration implements InitializingBean {

    private String unionPaySubmitUrl;
    private String nonUnionPaySubmitUrl;
    private String migsPaySubmitUrl;
    private String resultUrl;
    private String errorUrl;

    public String getUnionPaySubmitUrl() {
        return unionPaySubmitUrl;
    }

    public void setUnionPaySubmitUrl(String unionPaySubmitUrl) {
        this.unionPaySubmitUrl = unionPaySubmitUrl;
    }

    public String getNonUnionPaySubmitUrl() {
        return nonUnionPaySubmitUrl;
    }

    public void setNonUnionPaySubmitUrl(String nonUnionPaySubmitUrl) {
        this.nonUnionPaySubmitUrl = nonUnionPaySubmitUrl;
    }

    public String getMigsPaySubmitUrl() {
        return migsPaySubmitUrl;
    }

    public void setMigsPaySubmitUrl(String migsPaySubmitUrl) {
        this.migsPaySubmitUrl = migsPaySubmitUrl;
    }

    public String getResultUrl() {
        return resultUrl;
    }

    public void setResultUrl(String resultUrl) {
        this.resultUrl = resultUrl;
    }

    public String getErrorUrl() {
        return errorUrl;
    }

    public void setErrorUrl(String errorUrl) {
        this.errorUrl = errorUrl;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }

}
