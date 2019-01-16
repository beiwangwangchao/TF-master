/*
 *
 */
package com.lkkhpg.dsis.integration.payment.dto;

/**
 * 中国信托非银联退款.
 * 
 * @author shiliyan
 *
 */

public class ChinatrustNonUnionRefundModel extends PaymentTransactionModel{

    // 附錄1 POSAPI_Txn API 錯誤回傳值
    // 0表示操作成功
    private int ret;
    // 附錄二「POSAPI_TXN
    // POS 錯誤回傳值」。
    // 取得交易的執行狀態。執行狀態,0 表執行成功,非 0 為其它錯誤狀態請參閱附錄二「POSAPI_TXN
    // POS 錯誤回傳值」。(3.7)
    private int errStatus;
    // 取得交易的錯誤代碼。錯誤代碼,長度為 2 的字串,請參閱附錄二「POSAPI_TXN POS 錯誤回傳
    // 值」。
    private String errCode;
    // 取得退貨取消金額。
    private int amount;
    // 批次關閉提示(目前此 Function 不需使用,由系統自動觸發)。 (參考 Auth.getBatchclose 說明)
    // int batchClose = capRev.getBatchClose();
    // 取得退貨交易批次編號。
    private int batchID;
    // 取得退貨交易批次序號。
    private int batchSeq;
    // 901
    private String currency;
    // 錯誤訊息。傳回值錯誤訊息。(-1代表交易正常, 無錯誤代碼 ; 其餘狀況回傳錯誤的描述訊息)
    private String errDesc;
    // 取得幣值指數。
    // 新台幣為 0。為幣值指數,
    // (如美金 1.23 元,purchAmt 給 123 而 amtExp 則給-2)
    private int exponent;
    // 取得交易之調閱編號。調閱編號是主機用來對應交易封包, 同一筆交易的 Request 和 Response 有相同的調閱編號,用來
    // 確認交易相關性。(參考 Auth.getRetrref 說明)
    private String retrref;
    // 取得版本修訂日期。
    private String swRev;
    // 取得訊息規格版本。
    private String ver;

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public int getErrStatus() {
        return errStatus;
    }

    public void setErrStatus(int errStatus) {
        this.errStatus = errStatus;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getBatchID() {
        return batchID;
    }

    public void setBatchID(int batchID) {
        this.batchID = batchID;
    }

    public int getBatchSeq() {
        return batchSeq;
    }

    public void setBatchSeq(int batchSeq) {
        this.batchSeq = batchSeq;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getErrDesc() {
        return errDesc;
    }

    public void setErrDesc(String errDesc) {
        this.errDesc = errDesc;
    }

    public int getExponent() {
        return exponent;
    }

    public void setExponent(int exponent) {
        this.exponent = exponent;
    }

    public String getRetrref() {
        return retrref;
    }

    public void setRetrref(String retrref) {
        this.retrref = retrref;
    }

    public String getSwRev() {
        return swRev;
    }

    public void setSwRev(String swRev) {
        this.swRev = swRev;
    }

    public String getVer() {
        return ver;
    }

    public void setVer(String ver) {
        this.ver = ver;
    }

    public ChinatrustNonUnionRefundModel(int ret, int errStatus, String errCode, int amount, int batchID, int batchSeq,
            String currency, String errDesc, int exponent, String retrref, String swRev, String ver) {
        super();
        this.ret = ret;
        this.errStatus = errStatus;
        this.errCode = errCode;
        this.amount = amount;
        this.batchID = batchID;
        this.batchSeq = batchSeq;
        this.currency = currency;
        this.errDesc = errDesc;
        this.exponent = exponent;
        this.retrref = retrref;
        this.swRev = swRev;
        this.ver = ver;
    }

}
