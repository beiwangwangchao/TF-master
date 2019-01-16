/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.integration.payment.dto;

import com.lkkhpg.dsis.integration.payment.configration.ChinaTrustNonUnionConfigration;

/**
 * 中国信托非银联支付.
 * 
 * @author shiliyan
 *
 */
public class ChinatrustNonUnionPayModel extends PaymentTransactionModel {
    // 加密值。
    private String urlEnc;
    //
    // <bean name="chinaTrustNonUnionConfigration"
    // class="com.lkkhpg.dsis.integration.payment.configration.ChinaTrustNonUnionConfigration">
    // <property name="key" value="KGzpxKBsUF2Zo5Asgs3zwvrr" />
    // <property name="refundServer" value="https://testepos.ctbcbank.com:2011"
    // />
    // <property name="queryServer" value="https://testepos.ctbcbank.com:2011"
    // />
    // <property name="action"
    // value="https://testepos.ctbcbank.com/auth/SSLAuthUI.jsp" />
    // </bean>

    // 特店網站之代碼 chinaTrustNonUnionConfigration.merid :3435
    private String merID;
    // 銀行授權使用的特店代號 chinaTrustNonUnionConfigration.merchantId :8220276806551
    // 銀行所授與的特店代號,純數字,固定 13 碼。
    private String merchantID;
    // 特店網站或公司名稱 chinaTrustNonUnionConfigration.merchantName :INFINITUS
    private String merchantName;
    // 收單銀行授權使用的機台代號 chinaTrustNonUnionConfigration.terminalId :90008333
    // 銀行所授與的終端機代號,純數字,固定 8 碼。
    private String terminalID;
    // 系統回傳結果參數到指定網址 chinaTrustNonUnionConfigration.authResUrl :
    private String authResURL;

    //pay action
    private String action;
    
    public String getMerID() {
        return merID;
    }

    public void setMerID(String merID) {
        this.merID = merID;
    }

    public String getMerchantID() {
        return merchantID;
    }

    public void setMerchantID(String merchantID) {
        this.merchantID = merchantID;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getTerminalID() {
        return terminalID;
    }

    public void setTerminalID(String terminalID) {
        this.terminalID = terminalID;
    }

    public String getAuthResURL() {
        return authResURL;
    }

    public void setAuthResURL(String authResURL) {
        this.authResURL = authResURL;
    }

    // 純數字欄位,依交易方式不同填入不同的資料,說明如下: 􏰁 一般交易請填「1」。
    // 􏰁 分期交易請填一到兩碼的分期期數。
    // 紅利交易請填固定兩碼的產品代碼。
    // 􏰁 紅利分期交易請填第一至二碼固定為產品代碼,
    // 第三碼或三至四碼為分期期數。
    private String option = ChinaTrustNonUnionConfigration.DEFAULT_OPTION;
    // 設定付款交易方式。(必填)
    // 付款交易方式。 0–一般
    // 1–分期 2–紅利 3–紅利分期
    private String txType = ChinaTrustNonUnionConfigration.DEFAULT_TX_TYPE;
    // 交易訂單編號
    // 為電子商場的應用程式所給予此筆交易的訂單編號,資料 型態為最長 19 個字元的文字串。訂單編號字串之字元僅
    // 接受一般英文字母、數字及底線’_’的組合,不可出現 其餘符號字元。
    private String lidm;
    // 欲授權金額
    private String purchAmt;
    // 刷卡頁可顯示特定語系或客制化頁面。 1–繁體中文
    // 2–簡體中文 3–英文 5–客制化頁面
    private String customize = ChinaTrustNonUnionConfigration.DEFAULT_CUSTOMIZE;
    // 設定分期期數。(選填,僅適用在分期交易及紅利分期交易)
    private String numberOfPay = ChinaTrustNonUnionConfigration.DEFAULT_NUMBER_OF_PAY;
    // autoCap 選填,授權成功後是否自動轉入請款檔。 0–不自動轉入請款檔
    // 1–自動轉入請款檔
    private String autoCap = ChinaTrustNonUnionConfigration.DEFAULT_AUTO_CAP;
    // 請參閱 9.2「POSAPI_MAC 函數錯誤回傳值」。
    // 加密函數的錯誤代碼。
    private int encErrCode;

    public void setUrlEnc(String urlEnc) {
        this.urlEnc = urlEnc;
    }
    //
    // public void setMerID(String merID) {
    // this.merID = merID;
    // }
    //
    // public void setMerchantID(String merchantID) {
    // this.merchantID = merchantID;
    // }
    //
    // public void setMerchantName(String merchantName) {
    // this.merchantName = merchantName;
    // }
    //
    // public void setTerminalID(String terminalID) {
    // this.terminalID = terminalID;
    // }

    public void setOption(String option) {
        this.option = option;
    }

    public void setTxType(String txType) {
        this.txType = txType;
    }

    public void setLidm(String lidm) {
        this.lidm = lidm;
    }

    public void setPurchAmt(String purchAmt) {
        this.purchAmt = purchAmt;
    }

    public void setCustomize(String customize) {
        this.customize = customize;
    }

    public void setNumberOfPay(String numberOfPay) {
        this.numberOfPay = numberOfPay;
    }

    public void setAutoCap(String autoCap) {
        this.autoCap = autoCap;
    }

    // public void setAuthResURL(String authResURL) {
    // this.authResURL = authResURL;
    // }

    public String getUrlEnc() {
        return urlEnc;
    }

    // public String getMerID() {
    // return merID;
    // }
    //
    // public String getMerchantID() {
    // return merchantID;
    // }
    //
    // public String getMerchantName() {
    // return merchantName;
    // }
    //
    // public String getTerminalID() {
    // return terminalID;
    // }

    public String getOption() {
        return option;
    }

    public String getTxType() {
        return txType;
    }

    public String getLidm() {
        return lidm;
    }

    public String getPurchAmt() {
        return purchAmt;
    }

    public String getCustomize() {
        return customize;
    }

    public String getNumberOfPay() {
        return numberOfPay;
    }

    public String getAutoCap() {
        return autoCap;
    }

    // public String getAuthResURL() {
    // return authResURL;
    // }

    public int getEncErrCode() {
        return encErrCode;
    }

    public void setEncErrCode(int encErrCode) {
        this.encErrCode = encErrCode;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

}
