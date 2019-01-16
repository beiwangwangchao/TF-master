/*
 *
 */
package com.lkkhpg.dsis.integration.payment.configration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

/**
 * 
 * @author shiliyan
 *
 */
public class MIGSConfigration implements InitializingBean, IPaymentLogger {
    // public static String MERCHANT_ID = "Test00000493197";
    //
    // public static String MERCHTXN_REF = "EvanTest1234";
    //
    // public static String SECURE_HASH_SECRET =
    // "145D73685AE7A5AD35817CCA4980985E";// 145D73685AE7A5AD35817CCA4980985E
    // // C6CC6F469BFE7E7D53662D34DBEB44E9
    //
    // public static String ACCESS_CODE = "1605FD1D";
    //
    // public static String VPC_RETURN_URL =
    // "https://dsis-sit.infinitus-int.com/dsis/login.html";
    //
    // public static String VPC_SECURE_HASH_TYPE = "SHA256";
    //
    // public static String SECURE_TYPE = "HmacSHA256";
    //
    // public static String ACTION = "https://migs.mastercard.com.au/vpcpay";

    private boolean isCheckAES;
    private Logger logger = LoggerFactory.getLogger(MIGSConfigration.class);
    public static final String VPC_COMMAND_PAY = "pay";
    public static final String VPC_COMMAND_QUERY = "queryDR";
    public static final String VPC_COMMAND_REFUND = "refund";
    public static final String VPC_DEFAULT_LOCALE = "en";
    public static final String VPC_DEFAULT_VERSION = "1";

    // <property name="txnResponseCode" value="0" />
    private String txnResponseCode;
    // <property name="acqResponseCode" value="00" />
    private String acqResponseCode;

    private String virtualPaymentClientURL = "https://migs.mastercard.com.au/vpcdps";

    private String vpcUser = "EvanTest";
    private String vpcPassword = "LKK@123456";

    public String getVpcUser() {
        return vpcUser;
    }

    public void setVpcUser(String vpcUser) {
        this.vpcUser = vpcUser;
    }

    public String getVpcPassword() {
        return vpcPassword;
    }

    public void setVpcPassword(String vpcPassword) {
        this.vpcPassword = vpcPassword;
    }

    private String merchantId;

    private String merchtxnRef;

    private String secureHashSecret;

    private String accessCode;

    private String vpcReturnUrl;

    private String vpcSecureHashType;

    private String secureType;

    private String action;
    public static final String VPC_ACCESS_CODE = "vpc_AccessCode";
    public static final String VPC_AMOUNT = "vpc_Amount";
    public static final String VPC_COMMAND = "vpc_Command";
    public static final String VPC_LOCALE = "vpc_Locale";
    public static final String VPC_MERCHANT = "vpc_Merchant";
    public static final String VPC_MERCH_TXN_REF = "vpc_MerchTxnRef";
    public static final String VPC_RETURN_URL = "vpc_ReturnURL";
    public static final String VPC_VERSION = "vpc_Version";
    public static final String VPC_ORDER_INFO = "vpc_OrderInfo";
    public static final String VPC_SECURE_HASH_TYPE = "vpc_SecureHashType";
    public static final String VPC_SECURE_HASH = "vpc_SecureHash";

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

//    public String getMerchtxnRef() {
//        return merchtxnRef;
//    }

//    public void setMerchtxnRef(String merchtxnRef) {
//        this.merchtxnRef = merchtxnRef;
//    }

    public String getSecureHashSecret() {
        return secureHashSecret;
    }

    public void setSecureHashSecret(String secureHashSecret) {
        this.secureHashSecret = secureHashSecret;
    }

    public String getAccessCode() {
        return accessCode;
    }

    public void setAccessCode(String accessCode) {
        this.accessCode = accessCode;
    }

    public String getVpcReturnUrl() {
        return vpcReturnUrl;
    }

    public void setVpcReturnUrl(String vpcReturnUrl) {
        this.vpcReturnUrl = vpcReturnUrl;
    }

    public String getVpcSecureHashType() {
        return vpcSecureHashType;
    }

    public void setVpcSecureHashType(String vpcSecureHashType) {
        this.vpcSecureHashType = vpcSecureHashType;
    }

    public String getSecureType() {
        return secureType;
    }

    public void setSecureType(String secureType) {
        this.secureType = secureType;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (isCheckAES) {
            Assert.notNull(vpcPassword, "The vpcPassword must not be null");
            Assert.notNull(secureHashSecret, "The secureHashSecret must not be null");
        } else {
            if (vpcPassword == null) {
                this.error("The vpcPassword must not be null", logger);
            }
            if (secureHashSecret == null) {
                this.error("The secureHashSecret must not be null", logger);
            }
        }
    }

    public String getVirtualPaymentClientURL() {
        return virtualPaymentClientURL;
    }

    public void setVirtualPaymentClientURL(String virtualPaymentClientURL) {
        this.virtualPaymentClientURL = virtualPaymentClientURL;
    }

    public String getTxnResponseCode() {
        return txnResponseCode;
    }

    public void setTxnResponseCode(String txnResponseCode) {
        this.txnResponseCode = txnResponseCode;
    }

    public String getAcqResponseCode() {
        return acqResponseCode;
    }

    public void setAcqResponseCode(String acqResponseCode) {
        this.acqResponseCode = acqResponseCode;
    }

    public boolean isCheckAES() {
        return isCheckAES;
    }

    public void setCheckAES(boolean isCheckAES) {
        this.isCheckAES = isCheckAES;
    }

}
