/*
 *
 */
package com.lkkhpg.dsis.integration.payment.dto;

import com.lkkhpg.dsis.integration.payment.configration.ChinaTrustNonUnionConfigration;

/**
 * 中国信托非银联支付查询.
 * 
 * @author shiliyan
 *
 */
public class ChinatrustNonUnionPayQueryModel extends PaymentTransactionModel {
    // 幣別,目前僅支援新台幣。 固定為:901
    private int currency = ChinaTrustNonUnionConfigration.DEFAULT_CURRENCY;
    // 選擇性欄位,交易識別碼,可輔助系統快速查詢唯一一筆交
    // 易資料。當不使用此欄位值必須帶空字串””。
    // 最長為 40 個位元文字串
    private String xid = ChinaTrustNonUnionConfigration.QUERY_DEFAULT_XID;
    // 交易金額,單位為元。
    private int purchAmt;
    // 選擇性欄位,完整的信用卡卡號。為 16 個位元文字串 默认:""
    private String pan = ChinaTrustNonUnionConfigration.QUERY_DEFAULT_PAN;
    // 當 TxTyep=“TX_INSTMT_AUTH”時,必須設定此分期付款
    // 期數;其他交易型別則必須設定為 0。最長長度為 2
    private int recur_num = ChinaTrustNonUnionConfigration.QUERY_RECUR_NUM;
    // 當 TxTyep=“TX_REDEEM_AUTH”時,必須設定此產品代
    // 碼;其他交易型別則必須設定為空字串””。長度為 2
    private String in_prodcode = ChinaTrustNonUnionConfigration.QUERY_IN_PRODCODE;

    private int exponent = ChinaTrustNonUnionConfigration.DEFAULT_EXPONENT;

    // 請參閱附錄 API 錯誤回傳值
    private String inqAuthRet;
    // “TX_AUTH” 表一次付清
    // “TX_INSTMT_AUTH” 表網路分期
    // “TX_REDEEM_AUTH” 表紅利交易
    private String txAttribute = ChinaTrustNonUnionConfigration.QUERY_TX_ATTRIBUTE;
    // 訂單查詢的結果:
    // 0 表找不到符合條件的訂單
    // 1 表查詢成功(唯一一筆資料)
    // 2 表查詢成功(多筆符合條件,僅顯示交易時間最後一筆的授權資料,建議採 用 xid 查詢)
    private int queryCode;
    private String lidm;

    public ChinatrustNonUnionPayQueryModel() {

    }

    public void setAuthAmt(int authAmt) {
        this.authAmt = authAmt;
    }

    public int getCurrency() {
        return currency;
    }

    public void setCurrency(int currency) {
        this.currency = currency;
    }

    public int getPurchAmt() {
        return purchAmt;
    }

    public void setPurchAmt(int purchAmt) {
        this.purchAmt = purchAmt;
    }

    public int getRecur_num() {
        return recur_num;
    }

    public void setRecur_num(int recur_num) {
        this.recur_num = recur_num;
    }

    public String getIn_prodcode() {
        return in_prodcode;
    }

    public void setIn_prodcode(String in_prodcode) {
        this.in_prodcode = in_prodcode;
    }

    // 傳回值 Electronic Commerce Indicator:
    // 0 -MasterCard Pure SSL
    // 1 -MasterCard 持卡人無 3D 驗證
    // 2 -MasterCard 持卡人 3D 密碼驗證成功
    // 5 -Visa 持卡人 3D 密碼驗證成功
    // 6 -Visa 持卡人無 3D 驗證
    // 7 -Visa Pure SSL
    private String eci;
    // 訂單授權的金額,單位為元。
    private int authAmt;
    // 訂單授權的執行狀態。
    private int respCode;
    // 訂單授權的錯誤代碼。
    private String errCode;
    // 當 err_code=0 且 err_status=”00”時才會有授權碼,最大長度為六個字元。
    private String authCode;
    // 授權交易的調閱序號。
    private int termSeq;
    // 授權交易的調閱編號。調閱編號是主機用來對應交易封包,同一筆交易的
    // Request 和 Response 有相同的調閱編號,用來確認交易相關性。
    private String retrRef;
    // 取得該筆訂單在查詢時的交易狀態。
    // 語法 String getCurrentState();
    // 參數 無。
    // 傳回值 該筆訂單在查詢時的交易狀態:
    // 􏰂 -1 授权失败 -授權失敗
    // 􏰂 0 订单已取消 -訂單已取消
    // 􏰂 1 授权成功 -授權成功
    // 􏰂 10 已经请款 -已請款
    // 􏰂 11 已经请款(请款结账中) -已請款(請款結帳中)
    // 􏰂 12-已請款(請款成功) 􏰂 13-已請款(請款失敗) 􏰂 20-已退款
    // 􏰂 21-已退款(退款結帳中) 􏰂 22-已退款(退款成功)
    // 􏰂 23-已退款(退款失敗)
    private String currentState;
    // 當 TxTyep=“TX_REDEEM_AUTH”且授權已成功時,才會有此批次編號。
    private int batchID;
    // 當 TxTyep=“TX_REDEEM_AUTH”且授權已成功時,才會有此批次序號。
    private int batchSeq;
    // 當 TxTyep=“TX_REDEEM_AUTH”且授權已成功時,才會有此該交易折抵金
    // 額。
    private int offsetAmt;
    // 當 TxTyep=“TX_REDEEM_AUTH”且授權已成功時,才會有此該交易原始消
    // 費金額。
    private int originalAmt;
    // 當 TxTyep=“TX_REDEEM_AUTH”且授權已成功時,才會有此該交易的兌換
    // 點數。
    private int utilizedPoint;
    // 當 TxTyep=“TX_REDEEM_AUTH”且授權已成功時,才會有此該交易所賺取
    // 點數。
    private int awardedPoint;
    // 當 TxTyep=“TX_REDEEM_AUTH”且授權已成功時,才會有此該交易後點
    // 數餘額。
    private int pointBalance;

    public String getInqAuthRet() {
        return inqAuthRet;
    }

    public int getQueryCode() {
        return queryCode;
    }

    public String getXid() {
        return xid;
    }

    public String getPan() {
        return pan;
    }

    public String getEci() {
        return eci;
    }

    public int getAuthAmt() {
        return authAmt;
    }

    public int getRespCode() {
        return respCode;
    }

    public String getErrCode() {
        return errCode;
    }

    public String getAuthCode() {
        return authCode;
    }

    public int getTermSeq() {
        return termSeq;
    }

    public String getRetrRef() {
        return retrRef;
    }

    public String getCurrentState() {
        return currentState;
    }

    public int getBatchID() {
        return batchID;
    }

    public int getBatchSeq() {
        return batchSeq;
    }

    public int getOffsetAmt() {
        return offsetAmt;
    }

    public int getOriginalAmt() {
        return originalAmt;
    }

    public int getUtilizedPoint() {
        return utilizedPoint;
    }

    public int getAwardedPoint() {
        return awardedPoint;
    }

    public int getPointBalance() {
        return pointBalance;
    }

    public void setInqAuthRet(String inqAuthRet) {
        this.inqAuthRet = inqAuthRet;
    }

    public void setQueryCode(int queryCode) {
        this.queryCode = queryCode;
    }

    public void setXid(String xid) {
        this.xid = xid;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public void setEci(String eci) {
        this.eci = eci;
    }

    public void setRespCode(int respCode) {
        this.respCode = respCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public void setTermSeq(int termSeq) {
        this.termSeq = termSeq;
    }

    public void setRetrRef(String retrRef) {
        this.retrRef = retrRef;
    }

    public void setCurrentState(String currentState) {
        this.currentState = currentState;
    }

    public void setBatchID(int batchID) {
        this.batchID = batchID;
    }

    public void setBatchSeq(int batchSeq) {
        this.batchSeq = batchSeq;
    }

    public void setOffsetAmt(int offsetAmt) {
        this.offsetAmt = offsetAmt;
    }

    public void setOriginalAmt(int originalAmt) {
        this.originalAmt = originalAmt;
    }

    public void setUtilizedPoint(int utilizedPoint) {
        this.utilizedPoint = utilizedPoint;
    }

    public void setAwardedPoint(int awardedPoint) {
        this.awardedPoint = awardedPoint;
    }

    public void setPointBalance(int pointBalance) {
        this.pointBalance = pointBalance;
    }

    public String getLidm() {
        return lidm;
    }

    public void setLidm(String lidm) {
        this.lidm = lidm;
    }

    public String getTxAttribute() {
        return txAttribute;
    }

    public void setTxAttribute(String txAttribute) {
        this.txAttribute = txAttribute;
    }

    public int getExponent() {
        return exponent;
    }

    public void setExponent(int exponent) {
        this.exponent = exponent;
    }

}
