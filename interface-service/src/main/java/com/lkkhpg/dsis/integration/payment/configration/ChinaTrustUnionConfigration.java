/*
 *
 */
package com.lkkhpg.dsis.integration.payment.configration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

/**
 * @author shiliyan
 *
 */
public class ChinaTrustUnionConfigration implements InitializingBean, IPaymentLogger {
    // public static String KEY = "r6bGMbggPW7tJxTLtTQ1nZwe";
    //
    // public static int MERID = 3457;
    // public static String ACTION =
    // "https://testepos.ctbcbank.com/UPOP/unionPayAuth.do";
    //
    // public static String QUERY_SERVER = "https://testepos.ctbcbank.com";
    //
    // public static String REFUND_SERVER = "https://testepos.ctbcbank.com";
    //
    // // 00 成功交易
    // public static String SUCCESS_RESP_CODE = "00";
    // // 000
    // // 成功
    // public static String SUCCESS_RTN_CODE = "000";

    private boolean isCheckAES;
    private Logger logger = LoggerFactory.getLogger(ChinaTrustUnionConfigration.class);

    private static final String CHINATRUST_UNION_PAY_CONFIGRATION = "msg.chinatrust.union.pay.configration.";
    private static final String ORDER_STATUS = CHINATRUST_UNION_PAY_CONFIGRATION + "order.status.";
    private static final String RTN_CODE = CHINATRUST_UNION_PAY_CONFIGRATION + "rtn.code";
    private static final String RESP_CODE = CHINATRUST_UNION_PAY_CONFIGRATION + "resp.code";

    private String key;
    // = "r6bGMbggPW7tJxTLtTQ1nZwe";

    private int merid;
    // = 3457;
    private String action;
    // =
    // "https://testepos.ctbcbank.com/UPOP/unionPayAuth.do";

    private String queryServer;
    // = "https://testepos.ctbcbank.com";

    private String refundServer;
    // = "https://testepos.ctbcbank.com";

    // 00 成功交易
    private String successRespCode;
    // = "00";
    // 000
    // 成功
    private String successRtnCode;
    // = "000";

    private String frontCallbackUrl;

    private String backCallbackUrl;

    // <property name="orderStatusS" value="23" />
    private String orderStatusS;
    // <property name="orderStatusF" value="24" />
    private String orderStatusF;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getMerid() {
        return merid;
    }

    public void setMerid(int merid) {
        this.merid = merid;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getQueryServer() {
        return queryServer;
    }

    public void setQueryServer(String queryServer) {
        this.queryServer = queryServer;
    }

    public String getRefundServer() {
        return refundServer;
    }

    public void setRefundServer(String refundServer) {
        this.refundServer = refundServer;
    }

    public String getSuccessRespCode() {
        return successRespCode;
    }

    public void setSuccessRespCode(String successRespCode) {
        this.successRespCode = successRespCode;
    }

    public String getSuccessRtnCode() {
        return successRtnCode;
    }

    public void setSuccessRtnCode(String successRtnCode) {
        this.successRtnCode = successRtnCode;
    }

    //
    // 狀態代碼
    // 狀態說明
    // 11
    // 授權請求
    // 12
    // 授權中
    // 13
    // 授權成功
    // 14
    // 授權失敗
    // 22
    // 請款中
    // 23
    // 請款成功
    // 24
    // 請款失敗
    // 31
    // 取消授權請求
    // 32
    // 取消授權中
    // 33
    // 取消授權成功
    // 34
    // 取消授權失敗
    // 41
    // 退貨請求
    // 42
    // 退貨中
    // 43
    // 退貨成功
    // 44
    // 退貨失敗

    public static String orderStatusMsgCode(String status) {
        return new StringBuilder(ORDER_STATUS).append(status).toString();
    }

    // 附錄一、API 元件回傳值
    // 000
    // 成功
    // 901
    // 伺服器網址(server)未設定
    // 902
    // 特店編號(merId)未設定
    // 903
    // 訂單編號(lidm)未設定
    // 904
    // 交易金額(purchAmt)未設定
    // ￼
    // 905
    // 訂單識別碼(xid)未設定
    // 906
    // ￼
    // 壓碼鍵值(macKey)未設定
    // 907
    // HTTP 狀態碼錯誤
    // 908
    // 伺服器網址(server)必須為 HTTPS 協定
    // 909
    // 壓碼值(inMac)比對錯誤
    // 999
    // 系統錯誤
    public static String rtnCodeMsgCode(String rtnCode) {
        return new StringBuilder(RTN_CODE).append(rtnCode).toString();
    }

    // 附錄二、API 交易回應碼
    // 2001
    // 伺服器網址(server)未設定
    // 2002
    // 特店編號(merId)未設定
    // 2003
    // 訂單編號(lidm)未設定
    // 2004
    // 交易金額(purchAmt)未設定
    // 2005
    // 訂單識別碼(xid)未設定
    // 2006
    // 原始交易流水號(origqid)未設定
    // 2007
    // 特店身份驗證壓碼值(inMac)未設定
    // 2008
    // 壓碼鍵值(macKey)未設定
    // 2009
    // 查無此家特店資料
    // 2010
    // 交易金額格式錯誤
    // 2011
    // 特店身份驗證壓碼值比對錯誤
    // 2012
    // 特店類型不為銀聯卡網路特店
    // 2013
    // 特店狀態不為開通
    // 2014
    // 交易失敗-1
    // 2015
    // 交易失敗-3
    // 2016
    // 交易失敗-4
    // 2017
    // 訂單不存在
    // 2018 訂單編號比對錯誤
    // 2019 訂單狀態不為授權成功
    // 2020 訂單狀態不為請款成功、退貨成功、退貨失敗
    // 2021 累積退貨金額大於授權金額
    // 2022 訊息認證碼(signature)比對錯誤
    // 2023 交易失敗-2
    // 2024 交易失敗-6
    // 801 收單機構代碼(acqCode)未設定 804 持卡人 IP(customerIp)未設定
    // 806 特店名稱(merAbbr)未設定
    // 807 特店類型(merCode)未設定
    // 808 特店代碼(merId)未設定
    // 809 交易金額(orderAmount)未設定
    // 811 特店訂單號(orderNumber)未設定或長度小於 8 碼
    // 812 特店訂單號(orderNumber)長度大於 32 碼
    // 816 交易類型(transType)未設定
    // 818 交易類型(transType)內容錯誤
    // 819 原始交易流水號(origQid)錯誤
    // 00 成功交易
    // 01 交易異常,支付失敗
    // 02 您輸入的卡號無效,請確認後輸入
    // 03 發卡銀行不支持,支付失敗
    // 06 您的卡已經過期,請使用其他卡支付
    // 11 您卡上的餘額不足
    // 14 您的卡已過期或者是您輸入的有效期不正確,支付失敗
    // 15 您輸入的銀行卡密碼有誤,支付失敗
    // 20 您輸入的轉入卡卡號有誤,支付失敗
    // 21 您輸入的手機號或 CVN2 有誤,支付失敗
    // 25 退貨或取消交易找不到原交易
    // 30 報文格式錯誤
    // 36 交易金額超過網上銀行交易金額限制,支付失敗
    // 37 原始交易金額不正確
    // 39 您已連續多次輸入錯誤密碼
    // 40 請聯絡銀行
    // 41 銀行不支援認證支付(LitePay),請使用快捷支付(ProPay)
    // 42
    // 銀行不支援普通支付(CommonPay),請使用快捷支付(ProPay)
    // 56
    // 交易受限
    // 71
    // 交易無效,無法完成,支付失敗
    // 80
    // 內部錯誤
    // 81
    // 可疑報文
    // 82
    // 簽名驗證失敗
    // 83
    // 操作超時
    // 84
    // 訂單不存在
    // 94
    // 重複交易
    // 回應碼
    // 回應碼說明
    // 9001
    // 新增訂單資料失敗
    // 9002
    // 更新訂單資料失敗
    // 9003
    // 新增訂單明細資料失敗
    // 9004
    // ￼
    // 更新訂單明細資料失敗
    // 9005
    // 查詢訂單資料失敗
    // 9006
    // 查詢訂單明細資料失敗
    // 9999
    // 系統異常請稍後再試
    public static String respCodeMsgCode(String respCode) {
        return new StringBuilder(RESP_CODE).append(respCode).toString();
    }

    public String getBackCallbackUrl() {
        return backCallbackUrl;
    }

    public void setBackCallbackUrl(String backCallbackUrl) {
        this.backCallbackUrl = backCallbackUrl;
    }

    public String getFrontCallbackUrl() {
        return frontCallbackUrl;
    }

    public void setFrontCallbackUrl(String frontCallbackUrl) {
        this.frontCallbackUrl = frontCallbackUrl;
    }

    public String getOrderStatusF() {
        return orderStatusF;
    }

    public void setOrderStatusF(String orderStatusF) {
        this.orderStatusF = orderStatusF;
    }

    public String getOrderStatusS() {
        return orderStatusS;
    }

    public void setOrderStatusS(String orderStatusS) {
        this.orderStatusS = orderStatusS;
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
}
