/*
 *
 */
package com.lkkhpg.dsis.integration.payment.configration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

/**
 * @author shiliyan F
 */
public class ChinaTrustNonUnionConfigration implements InitializingBean, IPaymentLogger {
    // public static String KEY = "KGzpxKBsUF2Zo5Asgs3zwvrr";
    // public static String MERID = "3435";
    // public static String MERCHANT_ID = "8220276806551";
    // public static String MERCHANT_NAME = "INFINITUS";
    // public static String TERMINAL_ID = "90008333";
    // public static String AUTH_RES_URL =
    // "https://dsis-sit.infinitus-int.com/dsis/login.html";
    // // public static String action =
    // // "https://testepos.ctbcbank.com/auth/SSLAuthUI.jsp";
    //
    // // query
    // public static String QUERY_SERVER = "https://testepos.ctbcbank.com:2011";
    // public static String REFUND_SERVER =
    // "https://testepos.ctbcbank.com:2011";

    private String useAuthCode = "Y";
    private boolean isCheckAES;

    private Logger logger = LoggerFactory.getLogger(ChinaTrustNonUnionConfigration.class);

    // customize 刷卡頁可顯示特定語系或客制化頁面。 1–繁體中文
    // 2–簡體中文 3–英文 5–客制化頁面
    // 為特店是否使用上傳客製化授權頁。
    // 测试 代码我为0 why？
    public static final String DEFAULT_CUSTOMIZE = "0";
    // 付款交易方式。 0–一般
    // 1–分期 2–紅利 3–紅利分期
    public static final String DEFAULT_TX_TYPE = "0";
    // 設定分期期數。(選填,僅適用在分期交易及紅利分期交易)
    public static final String DEFAULT_NUMBER_OF_PAY = "1";
    // autoCap 選填,授權成功後是否自動轉入請款檔。 0–不自動轉入請款檔
    // 1–自動轉入請款檔
    public static final String DEFAULT_AUTO_CAP = "1";
    // 純數字欄位,依交易方式不同填入不同的資料,說明如下: 􏰁 一般交易請填「1」。
    // 􏰁 分期交易請填一到兩碼的分期期數。
    // 紅利交易請填固定兩碼的產品代碼。
    // 􏰁 紅利分期交易請填第一至二碼固定為產品代碼,
    // 第三碼或三至四碼為分期期數。
    public static final String DEFAULT_OPTION = "1";
    // 為幣值指數,新台幣為 0。(如美金 1.23 元,purchAmt 給 123 而 amtExp 則給-2)
    public static final int DEFAULT_EXPONENT = 0;

    // 默认币种
    public static final int DEFAULT_CURRENCY = 901;
    // 默认xid
    public static final String QUERY_DEFAULT_XID = "";
    // 默认pan
    public static final String QUERY_DEFAULT_PAN = "";
    // 一次性付清
    public static final String QUERY_TX_ATTRIBUTE = "TX_AUTH";
    // 默认分期数
    public static final int QUERY_RECUR_NUM = 0;

    // REFUND

    public static final String QUERY_IN_PRODCODE = "";

    private String key;
    // = "KGzpxKBsUF2Zo5Asgs3zwvrr";
    private String merid;
    // = "3435";
    private String merchantId;
    // = "8220276806551";
    private String merchantName;
    // = "INFINITUS";
    private String terminalId;
    // = "90008333";
    private String authResUrl;
    // = "https://dsis-sit.infinitus-int.com/dsis/login.html";
    private String action;
    // = "https://testepos.ctbcbank.com/auth/SSLAuthUI.jsp";
    private String refundServer;
    // = "https://testepos.ctbcbank.com:2011";

    private String queryServer;
    // = "https://testepos.ctbcbank.com:2011";

    // <property name="successRespCode" value="0" />
    private String successRespCode;
    // <property name="errCode" value="00" />
    private String errCode;
    // <property name="currentStateS" value="12" />
    private String currentStateS;
    // <property name="currentStateF" value="13" />
    private String currentStateF;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMerid() {
        return merid;
    }

    public void setMerid(String merid) {
        this.merid = merid;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public String getAuthResUrl() {
        return authResUrl;
    }

    public void setAuthResUrl(String authResUrl) {
        this.authResUrl = authResUrl;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getRefundServer() {
        return refundServer;
    }

    public void setRefundServer(String refundServer) {
        this.refundServer = refundServer;
    }

    public String getQueryServer() {
        return queryServer;
    }

    public void setQueryServer(String queryServer) {
        this.queryServer = queryServer;
    }

    public String getSuccessRespCode() {
        return successRespCode;
    }

    public void setSuccessRespCode(String successRespCode) {
        this.successRespCode = successRespCode;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getCurrentStateS() {
        return currentStateS;
    }

    public void setCurrentStateS(String currentStateS) {
        this.currentStateS = currentStateS;
    }

    public String getCurrentStateF() {
        return currentStateF;
    }

    public void setCurrentStateF(String currentStateF) {
        this.currentStateF = currentStateF;
    }

    public void afterPropertiesSet() throws Exception {
        if (isCheckAES) {
            Assert.notNull(key, "The key must not be null");
        } else {
            if (key == null) {
                this.error("The key must not be null", logger);
            }
        }
    }

    public boolean isCheckAES() {
        return isCheckAES;
    }

    public void setCheckAES(boolean isCheckAES) {
        this.isCheckAES = isCheckAES;
    }

    public String getUseAuthCode() {
        return useAuthCode;
    }

    public void setUseAuthCode(String useAuthCode) {
        this.useAuthCode = useAuthCode;
    }

    public boolean isUseAuthCode() {
        return "Y".equalsIgnoreCase(this.getUseAuthCode());
    }
    // public static void main(String[] args) {
    // ChinaTrustNonUnionConfigration chinaTrustNonUnionConfigration = new
    // ChinaTrustNonUnionConfigration();
    // //System.out.println(chinaTrustNonUnionConfigration.isCheckAES);
    // try {
    // chinaTrustNonUnionConfigration.afterPropertiesSet();
    // } catch (Exception e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }
    // }
}
