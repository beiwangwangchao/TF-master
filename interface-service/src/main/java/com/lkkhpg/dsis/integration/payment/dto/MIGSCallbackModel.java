/*
 *
 */
package com.lkkhpg.dsis.integration.payment.dto;

/**
 * MIGS回调.
 * @author shiliyan
 *
 */
public class MIGSCallbackModel extends PaymentTransactionModel{
    private String vpc_3DSECI;
    private String vpc_3DSXID;
    private String vpc_3DSenrolled;
    private String vpc_3DSstatus;
    private String vpc_AVSRequestCode;
    private String vpc_AVSResultCode;
    private String vpc_AcqAVSRespCode;
    private String vpc_AcqCSCRespCode;
    private String vpc_AcqResponseCode;
    private String vpc_Amount;
    private String vpc_AuthorizeId;
    private String vpc_BatchNo;
    private String vpc_CSCResultCode;
    private String vpc_Card;
    private String vpc_Command;
    private String vpc_Locale;
    private String vpc_MerchTxnRef;
    private String vpc_Merchant;
    private String vpc_Message;
    private String vpc_ReceiptNo;
    private String vpc_SecureHash;
    private String vpc_SecureHashType;
    private String vpc_TransactionNo;
    private String vpc_TxnResponseCode;
    private String vpc_VerSecurityLevel;
    private String vpc_VerStatus;
    private String vpc_VerToken;
    private String vpc_VerType;
    private String vpc_Version;

    private String vpc_OrderInfo;

    private String vpc_Currency;

    public String getVpc_3DSECI() {
        return vpc_3DSECI;
    }

    public String getVpc_3DSXID() {
        return vpc_3DSXID;
    }

    public String getVpc_3DSenrolled() {
        return vpc_3DSenrolled;
    }

    public String getVpc_3DSstatus() {
        return vpc_3DSstatus;
    }

    public String getVpc_AVSRequestCode() {
        return vpc_AVSRequestCode;
    }

    public String getVpc_AVSResultCode() {
        return vpc_AVSResultCode;
    }

    public String getVpc_AcqAVSRespCode() {
        return vpc_AcqAVSRespCode;
    }

    public String getVpc_AcqCSCRespCode() {
        return vpc_AcqCSCRespCode;
    }

    public String getVpc_AcqResponseCode() {
        return vpc_AcqResponseCode;
    }

    public String getVpc_Amount() {
        return vpc_Amount;
    }

    public String getVpc_AuthorizeId() {
        return vpc_AuthorizeId;
    }

    public String getVpc_BatchNo() {
        return vpc_BatchNo;
    }

    public String getVpc_CSCResultCode() {
        return vpc_CSCResultCode;
    }

    public String getVpc_Card() {
        return vpc_Card;
    }

    public String getVpc_Command() {
        return vpc_Command;
    }

    public String getVpc_Locale() {
        return vpc_Locale;
    }

    public String getVpc_MerchTxnRef() {
        return vpc_MerchTxnRef;
    }

    public String getVpc_Merchant() {
        return vpc_Merchant;
    }

    public String getVpc_Message() {
        return vpc_Message;
    }

    public String getVpc_ReceiptNo() {
        return vpc_ReceiptNo;
    }

    public String getVpc_SecureHash() {
        return vpc_SecureHash;
    }

    public String getVpc_SecureHashType() {
        return vpc_SecureHashType;
    }

    public String getVpc_TransactionNo() {
        return vpc_TransactionNo;
    }

    public String getVpc_TxnResponseCode() {
        return vpc_TxnResponseCode;
    }

    public String getVpc_VerSecurityLevel() {
        return vpc_VerSecurityLevel;
    }

    public String getVpc_VerStatus() {
        return vpc_VerStatus;
    }

    public String getVpc_VerToken() {
        return vpc_VerToken;
    }

    public String getVpc_VerType() {
        return vpc_VerType;
    }

    public String getVpc_Version() {
        return vpc_Version;
    }

    public void setVpc_3DSECI(String vpc_3dseci) {
        vpc_3DSECI = vpc_3dseci;
    }

    public void setVpc_3DSXID(String vpc_3dsxid) {
        vpc_3DSXID = vpc_3dsxid;
    }

    public void setVpc_3DSenrolled(String vpc_3dSenrolled) {
        vpc_3DSenrolled = vpc_3dSenrolled;
    }

    public void setVpc_3DSstatus(String vpc_3dSstatus) {
        vpc_3DSstatus = vpc_3dSstatus;
    }

    public void setVpc_AVSRequestCode(String vpc_AVSRequestCode) {
        this.vpc_AVSRequestCode = vpc_AVSRequestCode;
    }

    public void setVpc_AVSResultCode(String vpc_AVSResultCode) {
        this.vpc_AVSResultCode = vpc_AVSResultCode;
    }

    public void setVpc_AcqAVSRespCode(String vpc_AcqAVSRespCode) {
        this.vpc_AcqAVSRespCode = vpc_AcqAVSRespCode;
    }

    public void setVpc_AcqCSCRespCode(String vpc_AcqCSCRespCode) {
        this.vpc_AcqCSCRespCode = vpc_AcqCSCRespCode;
    }

    public void setVpc_AcqResponseCode(String vpc_AcqResponseCode) {
        this.vpc_AcqResponseCode = vpc_AcqResponseCode;
    }

    public void setVpc_Amount(String vpc_Amount) {
        this.vpc_Amount = vpc_Amount;
    }

    public void setVpc_AuthorizeId(String vpc_AuthorizeId) {
        this.vpc_AuthorizeId = vpc_AuthorizeId;
    }

    public void setVpc_BatchNo(String vpc_BatchNo) {
        this.vpc_BatchNo = vpc_BatchNo;
    }

    public void setVpc_CSCResultCode(String vpc_CSCResultCode) {
        this.vpc_CSCResultCode = vpc_CSCResultCode;
    }

    public void setVpc_Card(String vpc_Card) {
        this.vpc_Card = vpc_Card;
    }

    public void setVpc_Command(String vpc_Command) {
        this.vpc_Command = vpc_Command;
    }

    public void setVpc_Locale(String vpc_Locale) {
        this.vpc_Locale = vpc_Locale;
    }

    public void setVpc_MerchTxnRef(String vpc_MerchTxnRef) {
        this.vpc_MerchTxnRef = vpc_MerchTxnRef;
    }

    public void setVpc_Merchant(String vpc_Merchant) {
        this.vpc_Merchant = vpc_Merchant;
    }

    public void setVpc_Message(String vpc_Message) {
        this.vpc_Message = vpc_Message;
    }

    public void setVpc_ReceiptNo(String vpc_ReceiptNo) {
        this.vpc_ReceiptNo = vpc_ReceiptNo;
    }

    public void setVpc_SecureHash(String vpc_SecureHash) {
        this.vpc_SecureHash = vpc_SecureHash;
    }

    public void setVpc_SecureHashType(String vpc_SecureHashType) {
        this.vpc_SecureHashType = vpc_SecureHashType;
    }

    public void setVpc_TransactionNo(String vpc_TransactionNo) {
        this.vpc_TransactionNo = vpc_TransactionNo;
    }

    public void setVpc_TxnResponseCode(String vpc_TxnResponseCode) {
        this.vpc_TxnResponseCode = vpc_TxnResponseCode;
    }

    public void setVpc_VerSecurityLevel(String vpc_VerSecurityLevel) {
        this.vpc_VerSecurityLevel = vpc_VerSecurityLevel;
    }

    public void setVpc_VerStatus(String vpc_VerStatus) {
        this.vpc_VerStatus = vpc_VerStatus;
    }

    public void setVpc_VerToken(String vpc_VerToken) {
        this.vpc_VerToken = vpc_VerToken;
    }

    public void setVpc_VerType(String vpc_VerType) {
        this.vpc_VerType = vpc_VerType;
    }

    public void setVpc_Version(String vpc_Version) {
        this.vpc_Version = vpc_Version;
    }

    public String getVpc_Currency() {
        return vpc_Currency;
    }

    public void setVpc_Currency(String vpc_Currency) {
        this.vpc_Currency = vpc_Currency;
    }

    public String getVpc_OrderInfo() {
        return vpc_OrderInfo;
    }

    public void setVpc_OrderInfo(String vpc_OrderInfo) {
        this.vpc_OrderInfo = vpc_OrderInfo;
    }

}
