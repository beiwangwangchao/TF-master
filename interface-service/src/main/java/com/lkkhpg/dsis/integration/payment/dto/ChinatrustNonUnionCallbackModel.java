/*
 *
 */
package com.lkkhpg.dsis.integration.payment.dto;

/**
 * 中国信托非银联回调.
 * 
 * @author shiliyan
 *
 */
public class ChinatrustNonUnionCallbackModel extends PaymentTransactionModel {
    public ChinatrustNonUnionCallbackModel() {
        super();
    }

    private boolean checkMPIOutMac;
    // 驗證字串中,檢查特店身份驗證壓碼(OutMac)值是否正 確
    private boolean checkURLOutMac;

    // 取得授權金額。
    private String authAmt;
    // 取得解密後字串中的 AuthCode。
    private String authCode;

    private String authResURL;
    // 本次賺取點數。
    private String awardedPoint;

    private String cavv;
    // 取得解密後字串中的隱碼卡號(654321******1234)。
    private String cardNumber;
    // 解密值。 参数拼接的结果
    private String dec;
    // 當執行狀態不為空值時,可利用 getDecErrCode 函式取得此次解密的錯誤代碼。
    // 錯誤代碼,請參閱 13.2「POSAPI_MAC 函數錯誤回傳值」。
    private int decErrCode;

    private String eci;
    // 傳回值錯誤訊息。(-1代表交易正常, 無錯誤代碼 ; 其餘狀況回傳錯誤的描述訊息)
    // 取得解密後字串中的 errDesc。
    private String errDesc;
    // 傳回值 錯誤代碼,長度為 2 的字串,請參閱附錄二「POSAPI_TXN POS 錯誤回傳
    // 值」。
    // 取得解密後字串中的 Errcode。
    private String errcode;

    private String errorCode;

    private String errorMessage;

    private String expiry;

    private String feeCharge;
    // 末四碼卡號。
    private String last4digitPAN;

    private String lidm;

    private String merID;
    // 取得分期期數。
    private String numberOfPay;
    // 折抵金額。
    private String offsetAmt;
    // 原始訂單金額。
    private String originalAmt;
    // 特店身份驗證壓碼。
    private String outMac;

    private String payerBankId;
    // 目前點數餘額。
    private String pointBalance;
    // 產品代碼。
    private String prodCode;
    // 取得解密後字串中的 status。
    // 取得授權結果分類碼。
    private String status;
    // 調閱序號。
    private String termSeq;
    // 本次兌換點數。
    private String utilizedPoint;

    private String webATMAcct;
//    // 交易序號。
    private String _XID;
    // 交易序號。
    private String xid;

    public boolean isCheckMPIOutMac() {
        return checkMPIOutMac;
    }

    public boolean isCheckURLOutMac() {
        return checkURLOutMac;
    }

    public String getAuthAmt() {
        return authAmt;
    }

    public String getAuthCode() {
        return authCode;
    }

    public String getAuthResURL() {
        return authResURL;
    }

    public String getAwardedPoint() {
        return awardedPoint;
    }

    public String getCavv() {
        return cavv;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getDec() {
        return dec;
    }

    public int getDecErrCode() {
        return decErrCode;
    }

    public String getEci() {
        return eci;
    }

    public String getErrDesc() {
        return errDesc;
    }

    public String getErrcode() {
        return errcode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getExpiry() {
        return expiry;
    }

    public String getFeeCharge() {
        return feeCharge;
    }

    public String getLast4digitPAN() {
        return last4digitPAN;
    }

    public String getLidm() {
        return lidm;
    }

    public String getMerID() {
        return merID;
    }

    public String getNumberOfPay() {
        return numberOfPay;
    }

    public String getOffsetAmt() {
        return offsetAmt;
    }

    public String getOriginalAmt() {
        return originalAmt;
    }

    public String getOutMac() {
        return outMac;
    }

    public String getPayerBankId() {
        return payerBankId;
    }

    public String getPointBalance() {
        return pointBalance;
    }

    public String getProdCode() {
        return prodCode;
    }

    public String getStatus() {
        return status;
    }

    public String getTermSeq() {
        return termSeq;
    }

    public String getUtilizedPoint() {
        return utilizedPoint;
    }

    public String getWebATMAcct() {
        return webATMAcct;
    }

    public String get_XID() {
        return _XID;
    }

    public String getXid() {
        return xid;
    }

    public void setCheckMPIOutMac(boolean checkMPIOutMac) {
        this.checkMPIOutMac = checkMPIOutMac;
    }

    public void setCheckURLOutMac(boolean checkURLOutMac) {
        this.checkURLOutMac = checkURLOutMac;
    }

    public void setAuthAmt(String authAmt) {
        this.authAmt = authAmt;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public void setAuthResURL(String authResURL) {
        this.authResURL = authResURL;
    }

    public void setAwardedPoint(String awardedPoint) {
        this.awardedPoint = awardedPoint;
    }

    public void setCavv(String cavv) {
        this.cavv = cavv;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setDec(String dec) {
        this.dec = dec;
    }

    public void setDecErrCode(int decErrCode) {
        this.decErrCode = decErrCode;
    }

    public void setEci(String eci) {
        this.eci = eci;
    }

    public void setErrDesc(String errDesc) {
        this.errDesc = errDesc;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }

    public void setFeeCharge(String feeCharge) {
        this.feeCharge = feeCharge;
    }

    public void setLast4digitPAN(String last4digitPAN) {
        this.last4digitPAN = last4digitPAN;
    }

    public void setLidm(String lidm) {
        this.lidm = lidm;
    }

    public void setMerID(String merID) {
        this.merID = merID;
    }

    public void setNumberOfPay(String numberOfPay) {
        this.numberOfPay = numberOfPay;
    }

    public void setOffsetAmt(String offsetAmt) {
        this.offsetAmt = offsetAmt;
    }

    public void setOriginalAmt(String originalAmt) {
        this.originalAmt = originalAmt;
    }

    public void setOutMac(String outMac) {
        this.outMac = outMac;
    }

    public void setPayerBankId(String payerBankId) {
        this.payerBankId = payerBankId;
    }

    public void setPointBalance(String pointBalance) {
        this.pointBalance = pointBalance;
    }

    public void setProdCode(String prodCode) {
        this.prodCode = prodCode;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTermSeq(String termSeq) {
        this.termSeq = termSeq;
    }

    public void setUtilizedPoint(String utilizedPoint) {
        this.utilizedPoint = utilizedPoint;
    }

    public void setWebATMAcct(String webATMAcct) {
        this.webATMAcct = webATMAcct;
    }

    public void set_XID(String xID) {
        _XID = xID;
    }

    public void setXid(String xid) {
        this.xid = xid;
    }

    public ChinatrustNonUnionCallbackModel(boolean checkMPIOutMac, boolean checkURLOutMac, String authAmt,
            String authCode, String authResURL, String awardedPoint, String cavv, String cardNumber, String dec,
            int decErrCode, String eci, String errDesc, String errcode, String errorCode, String errorMessage,
            String expiry, String feeCharge, String last4digitPAN, String lidm, String merID, String numberOfPay,
            String offsetAmt, String originalAmt, String outMac, String payerBankId, String pointBalance,
            String prodCode, String status, String termSeq, String utilizedPoint, String webATMAcct, String xID,
            String xid2) {
        super();
        this.checkMPIOutMac = checkMPIOutMac;
        this.checkURLOutMac = checkURLOutMac;
        this.authAmt = authAmt;
        this.authCode = authCode;
        this.authResURL = authResURL;
        this.awardedPoint = awardedPoint;
        this.cavv = cavv;
        this.cardNumber = cardNumber;
        this.dec = dec;
        this.decErrCode = decErrCode;
        this.eci = eci;
        this.errDesc = errDesc;
        this.errcode = errcode;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.expiry = expiry;
        this.feeCharge = feeCharge;
        this.last4digitPAN = last4digitPAN;
        this.lidm = lidm;
        this.merID = merID;
        this.numberOfPay = numberOfPay;
        this.offsetAmt = offsetAmt;
        this.originalAmt = originalAmt;
        this.outMac = outMac;
        this.payerBankId = payerBankId;
        this.pointBalance = pointBalance;
        this.prodCode = prodCode;
        this.status = status;
        this.termSeq = termSeq;
        this.utilizedPoint = utilizedPoint;
        this.webATMAcct = webATMAcct;
        _XID = xID;
        xid = xid2;
    }

}
