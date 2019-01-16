/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.integration.payment.dto;

import com.lkkhpg.dsis.integration.payment.configration.MIGSConfigration;

/**
 * MIGS参数.
 * 
 * @author shiliyan
 *
 */
public class MIGSModel extends PaymentTransactionModel{

    private String vpc_AccessCode;
    private String vpc_MerchTxnRef;
    private String vpc_Merchant;
    private String vpc_ReturnURL;
    private String vpc_SecureHashType;

    public String getVpc_AccessCode() {
        return vpc_AccessCode;
    }

    public void setVpc_AccessCode(String vpc_AccessCode) {
        this.vpc_AccessCode = vpc_AccessCode;
    }

    public String getVpc_MerchTxnRef() {
        return vpc_MerchTxnRef;
    }

    public void setVpc_MerchTxnRef(String vpc_MerchTxnRef) {
        this.vpc_MerchTxnRef = vpc_MerchTxnRef;
    }

    public String getVpc_Merchant() {
        return vpc_Merchant;
    }

    public void setVpc_Merchant(String vpc_Merchant) {
        this.vpc_Merchant = vpc_Merchant;
    }

    public String getVpc_ReturnURL() {
        return vpc_ReturnURL;
    }

    public void setVpc_ReturnURL(String vpc_ReturnURL) {
        this.vpc_ReturnURL = vpc_ReturnURL;
    }

    public String getVpc_SecureHashType() {
        return vpc_SecureHashType;
    }

    public void setVpc_SecureHashType(String vpc_SecureHashType) {
        this.vpc_SecureHashType = vpc_SecureHashType;
    }

    private String vpc_Amount;
    private String vpc_Version = MIGSConfigration.VPC_DEFAULT_VERSION;
    private String vpc_Locale = MIGSConfigration.VPC_DEFAULT_LOCALE;
    private String vpc_Command;
    private String vpc_SecureHash;

    private String vpc_OrderInfo;

    private String vpc_Currency;

    private String action;
    public MIGSModel() {
    }

    public void setVpc_Amount(String vpc_Amount) {
        this.vpc_Amount = vpc_Amount;
    }

    public void setVpc_Version(String vpc_Version) {
        this.vpc_Version = vpc_Version;
    }

    public void setVpc_Command(String vpc_Command) {
        this.vpc_Command = vpc_Command;
    }

    public void setVpc_Locale(String vpc_Locale) {
        this.vpc_Locale = vpc_Locale;
    }

    public void setVpc_SecureHash(String vpc_SecureHash) {
        this.vpc_SecureHash = vpc_SecureHash;
    }

    public String getVpc_Amount() {
        return vpc_Amount;
    }

    public String getVpc_Version() {
        return vpc_Version;
    }

    public String getVpc_Command() {
        return vpc_Command;
    }

    public String getVpc_Locale() {
        return vpc_Locale;
    }

    public String getVpc_SecureHash() {
        return vpc_SecureHash;
    }

    public String getVpc_OrderInfo() {
        return vpc_OrderInfo;
    }

    public void setVpc_OrderInfo(String vpc_OrderInfo) {
        this.vpc_OrderInfo = vpc_OrderInfo;
    }

    public String getVpc_Currency() {
        return vpc_Currency;
    }

    public void setVpc_Currency(String vpc_Currency) {
        this.vpc_Currency = vpc_Currency;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

}
