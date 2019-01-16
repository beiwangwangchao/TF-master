/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.constant;

import com.lkkhpg.dsis.platform.constant.BaseConstants;

import java.math.BigDecimal;

/**
 * 订单模块常量.
 * 
 * @author chenjingxiong
 */
public class OrderConstants extends BaseConstants {

    /**
     * 批次号流水位数.
     */
    public static final int BATCH_DIGIT = 2;

    /**
     * autoShip订单号流水位数.
     */
    public static final int AUTOSHIP_DIGIT = 3;

    /**
     * 销售订单号流水位数.
     */
    public static final int SALESORDER_DIGIT = 4;

    /**
     * 生成流水号默认位数.
     */
    public static final int DEFAULT_DIGIT = 5;

    /**
     * 生成流水号起始数字.
     */
    public static final Long BEGIN_NUM = 1L;

    /**
     * 生成编码的默认日期格式.
     */
    public static final String GENERATECODE_DATE_FORMAT = "yyMMdd";

    /**
     * 订单类型为autoship.
     */
    public static final String AUTOSHIP = "AUTOS";

    /**
     * 订单类型为普通.
     */
    public static final String MANUAL = "MANUL";
    /**
     * 订单类型为MWS.
     */
    public static final String ORDER_SOURCE_TYPE_MWS = "MWS";

    /**
     * 待支付.
     */
    public static final String WAIT_PAT = "WPAY";

    /**
     * 默认autoship订单类型.
     */
    public static final String DEFAULT_AUTOSHIP_ORDER_TYPE = "STDP";

    /**
     * 默认autoship的订单销售渠道.
     */
    public static final String AUTOSHIP_CHANNEL = "AUTOS";

    /**
     * autoship状态-激活.
     */
    public static final String AUTOSHIP_STATUS_ACV = "ACV";

    /**
     * autoship状态-暂停.
     */
    public static final String AUTOSHIP_STATUS_SUS = "SUS";

    /**
     * autoship状态-终止.
     */
    public static final String AUTOSHIP_STATUS_TER = "TER";

    /**
     * 奖金月份格式.
     */
    public static final String DEFAULT_PERIOD_FORMAT = "yyyyMM";

    /**
     * 信用卡展示的位数.
     */
    public static final int CREDITCARD_SHOW_DIGIT = 4;

    /**
     * 0.
     */
    public static final String ZERO = "0";

    /**
     * 订单类型-积分兑换.
     */
    public static final String ORDER_TYPE_RDEM = "RDEM";

    /**
     * 支付方式-货到付款.
     */
    public static final String PAYMENT_METHOD_DELIV = "PODEL";

    /**
     * 支付方式-ecupon.
     */
    public static final String PAYMENT_METHOD_ECUP = "ECUP";

    /**
     * 支付方式-现金.
     */
    public static final String PAYMENT_METHOD_CASH = "CASH";

    /**
     * 支付方式-支票.
     */
    public static final String PAYMENT_METHOD_CHEQU = "CHEQU";

    /**
     * 支付方式-汇款单.
     */
    public static final String PAYMENT_METHOD_REMIT = "REMIT";

    /**
     * 支付方式-信用卡-POS.
     */
    public static final String PAYMENT_METHOD_CREDI = "CREDI";

    /**
     * 支付方式-借记卡-POS.
     */
    public static final String PAYMENT_METHOD_DBCRD = "DBCRD";

    /**
     * 支付方式-EBPAY.
     */
    public static final String PAY_METHOD_MODIFY_EBPAY = "EBPAY";

    /**
     * 支付方式-RBPAY.
     */
    public static final String PAY_METHOD_MODIFY_RBPAY = "RBPAY";

    /**
     * 支付方式-快速编码.
     */
    public static final String OM_PAY_METHOD_MODIFY = "OM.PAY_METHOD_MODIFY";

    /**
     * ipoint 用户订单支付方式标识.
     */
    public static final String IPOINT_PAY_METHOD_FLAG_Y = "Y";

    /**
     * 订单状态-待付款.
     */
    public static final String SALES_STATUS_WPAY = "WPAY";

    /**
     * 订单状态-审核中.
     */
    public static final String SALES_STATUS_CHECK = "CHECK";

    /**
     * 订单状态-待确认.
     */
    public static final String SALES_STATUS_CONF = "CONF";
    /**
     * 订单状态-付款失败.
     */
    public static final String SALES_STATUS_FAIL = "FAIL";

    /**
     * 订单状态-已完成.
     */
    public static final String SALES_STATUS_COMP = "COMP";

    /**
     * 订单行发运状态 - 已发运.
     */
    public static final String LINE_DELIVERY_STATUS_SHIPP = "SHIPP";

    /**
     * 订单行发运状态 - 未发运.
     */
    public static final String LINE_DELIVERY_STATUS_USHIP = "USHIP";

    /**
     * 订单行发运状态 - 部分发运.
     */
    public static final String LINE_DELIVERY_STATUS_PSHIP = "PSHIP";

    /**
     * 订单状态 - 新建.
     */
    public static final String SALES_STATUS_NEW = "NEW";

    /**
     * 订单状态 - 已保存.
     */
    public static final String SALES_STATUS_SAVED = "SAV";

    /**
     * 订单状态 - 待付款.
     */
    public static final String SALES_STATUS_WAIT_PAYMENT = "WPAY";

    /**
     * 订单状态 - 待确认.
     */
    public static final String SALES_STATUS_TO_BE_CONFIRM = "CONF";

    /**
     * 订单状态 - 已完成.
     */
    public static final String SALES_STATUS_COMPELETED = "COMP";

    /**
     * 订单状态--拒绝退款
     */
    public static final String SALES_STATUS_REFUSE="REFU";
    /**
     * 订单状态--已退款
     */
    public static final String SALES_STATUS_REFUND="REF";
    /**
     * 订单状态--退款失败
     */
    public static final String SALES_STATUS_REFUND_FAIL="REFF";
    /**
     * 订单状态-退款中
     */
    public static final String SALES_STATUS_REFUNDING="REFUN";
    /**
     * 订单状态 - 付款失败.
     */
    public static final String SALES_STATUS_PAY_FAILURE = "FAIL";

    /**
     * 订单状态 - 已取消.
     */
    public static final String SALES_STATUS_CANCELED = "CANCL";

    /**
     * 订单状态 - 已失效.
     */
    public static final String SALES_STATUS_VOIDED = "VOID";

    /**
     * 支付调整类型 - Exchange_Balance.
     */
    public static final String ADJUSTMENT_TYPE_EB = "EB";

    /**
     * 支付调整类型 - Remaining_Balance.
     */
    public static final String ADJUSTMENT_TYPE_RB = "RB";

    /**
     * 支付调整类型- SalesPoint.
     */
    public static final String ADJUSTMENT_TYPE_SP = "SP";

    /**
     * 支付调整-人工调整.
     */
    public static final String ADJUSTMENT_TYPE_AD = "AD";

    /**
     * 支付调整-优惠券.
     */
    public static final String ADJUSTMENT_TYPE_CP = "CP";

    /**
     * 退款来源类型-订单.
     */
    public static final String REFUND_SOURCE_TYPE_ORDER = "ORDER";

    /**
     * 默认奖金月份ID.
     */
    public static final Long DEFAULT_PERIOD_ID = 1L;

    /**
     * 支付状态-新建.
     */
    public static final String PAYMENT_STATUS_NEW = "NEW";

    /**
     * 订单行数量最小值.
     */
    public static final BigDecimal MIN_QUANTITY = BigDecimal.ONE;

    /**
     * 订单行数量最大值.
     */
    public static final BigDecimal MAX_QUANTITY = new BigDecimal("99999");

    /**
     * 退款所属市场组织参数.
     */
    public static final String ORDER_SPM_REFUND_TO_RB = "SPM.REFUND_TO_RB";

    /**
     * 订单行状态- 未发运.
     */
    public static final String SALESLINE_STATUS_UNSHIPPED = "USHIP";

    /**
     * 订单配送类型为自提.
     */
    public static final String ORDER_DELIVERY_PCKUP = "PCKUP";

    /**
     * 订单配送类型-物流.
     */
    public static final String ORDER_DELIVERY_SHIPP = "SHIPP";

    /**
     * 订单行库存不足.
     */
    public static final String SALE_LINE_INV_QUANTITY_CHECK_OUT = "out";

    /**
     * 授权文件获取特店编号.
     */
    public static final String CSV_CREATECSVFILE_PARAMNAME = "SPM.MERID";

    /**
     * 授权文件-第0个特店编号-0.
     */
    public static final int CSV_PARAMNAME_0 = 0;

    /**
     * 授权文件-获取流水号使用-连接流水号.
     */
    public static final String CSV_HYPHEN = ".";

    /**
     * 授权文件-获取流水号使用-DOCTYPE-CSV.
     */
    public static final String CSV_DOCTYPE = "CSV";

    /**
     * 授权文件-表头-记录型别.
     */
    public static final String CSV_HEAD_RECORDTYPE = "FHD";

    /**
     * 授权文件-表行-记录型别.
     */
    public static final String CSV_ROW_RECORDTYPE = "FDT";

    /**
     * 授权文件-表行-代理行.
     */
    public static final String CSV_ROW_AGENT = "882";

    /**
     * 授权文件-表行-信用卡有效年月.
     */
    public static final String CSV_ROW_CREDITCARD = "yyyyMM";

    /**
     * 授权文件-表行-端末代号.
     */
    public static final String CSV_ROW_TERMINALID = "SPM.TERMINALID";

    /**
     * 授权文件-表行-特店代号.
     */
    public static final String CSV_ROW_SHOPID = "SPM.MERCHANTID";

    /**
     * 授权文件-后缀-.csv.
     */
    public static final String CSV_TYPE = ".csv";

    /**
     * 授权文件-标记位-N.
     */
    public static final String CSV_FLAG_N = "N";

    /**
     * 授权文件-标记位-A.
     */
    public static final String CSV_FLAG_A = "A";

    /**
     * 授权文件-单次上传数量-1.
     */
    public static final int CSV_UPLOADNUMBER_1 = 1;

    /**
     * 授权文件-字符串长度-1024.
     */
    public static final int CSV_LENGTH_1024 = 1024;

    /**
     * 授权文件-信用卡有效年份前补上20-20.
     */
    public static final int CSV_VALIDYEARS_20 = 20;

    /**
     * 授权文件-字符串长度-8.
     */
    public static final int CSV_LENGTH_8 = 8;

    /**
     * 授权文件-字符串长度-15.
     */
    public static final int CSV_LENGTH_15 = 15;

    /**
     * 授权文件-字符串长度-183.
     */
    public static final int CSV_LENGTH_183 = 183;

    /**
     * 授权文件-字符串长度-2.
     */
    public static final int CSV_LENGTH_2 = 2;

    /**
     * 授权文件-字符串长度-3.
     */
    public static final int CSV_LENGTH_3 = 3;

    /**
     * 授权文件-字符串长度-12.
     */
    public static final int CSV_LENGTH_12 = 12;

    /**
     * 授权文件-字符串长度-13.
     */
    public static final int CSV_LENGTH_13 = 13;

    /**
     * 授权文件-字符串长度-6.
     */
    public static final int CSV_LENGTH_6 = 6;

    /**
     * 授权文件-字符串长度-26.
     */
    public static final int CSV_LENGTH_26 = 26;

    /**
     * 授权文件-字符串长度-19.
     */
    public static final int CSV_LENGTH_19 = 19;

    /**
     * 授权文件-字符串长度-16.
     */
    public static final int CSV_LENGTH_16 = 16;

    /**
     * 授权文件-扩大倍数-100.
     */
    public static final int CSV_LENGTH_100 = 100;

    /**
     * 授权文件类型后缀-application/csv.
     */
    public static final String CSV_FILE_TYPE = "application/csv";

    /**
     * 上传授权文件交易状态-cap.
     */
    public static final String CSV_FILE_TRADINGSTATUS = "cap";

    /**
     * 上传授权文件支付类型-信用卡-CDAUT.
     */
    public static final String CSV_FILE_PAYMENTMETHOD = "CDCRD";

    /**
     * 上传授权文件地址-upload/tmp.
     */
    public static final String CSV_FILE_UPLOADPATH = "upload/tmp";

    /**
     * 授权文件-截取字符串起始位置-18.
     */
    public static final int CSV_SUBSTRING_18 = 18;

    /**
     * 授权文件-截取字符串起始位置-37.
     */
    public static final int CSV_SUBSTRING_37 = 37;

    /**
     * 授权文件-截取字符串起始位置-40.
     */
    public static final int CSV_SUBSTRING_41 = 41;

    /**
     * 授权文件-截取字符串起始位置-53.
     */
    public static final int CSV_SUBSTRING_53 = 53;

    /**
     * 授权文件-截取字符串起始位置-54.
     */
    public static final int CSV_SUBSTRING_54 = 54;

    /**
     * 授权文件-截取字符串起始位置-66.
     */
    public static final int CSV_SUBSTRING_66 = 66;
    
    /**
     * 授权文件-截取字符串起始位置-82.
     */
    public static final int CSV_SUBSTRING_82 = 82;

    /**
     * 授权文件-上传文件完成-{0}笔订单支付成功{1}笔订单支付失败.
     */
    public static final String CSV_UPLOAD_ERROR_FILE_EXISTS = "msg.csv.upload.success.msg";

    /**
     * 订单类型-总监购买.
     */
    public static final String ORDER_TYPE_DIRP = "DIRP";

    /**
     * 订单类型-员工购买.
     */
    public static final String ORDER_TYPE_STFP = "STFP";

    /**
     * 订单类型-非会员购买.
     */
    public static final String ORDER_TYPE_CONP = "CONP";

    /**
     * 订单类型-非会员购买2.
     */
    public static final String ORDER_TYPE_CONPT = "CONPT";

    /**
     * 订单类型-生日礼物.
     */
    public static final String ORDER_TYPE_BIGF = "BIGF";

    /**
     * 订单类型-生日礼物.
     */
    public static final String ORDER_TYPE_VIPP = "VIPP";

    /**
     * 订单类型-员工购买2.
     */
    public static final String ORDER_TYPE_STFPT = "STFPT";

    /**
     * 退货单-退货单状态-新建.
     */
    public static final String RETURN_STATUS_NEW = "NEW";

    /**
     * 退货单-退货单状态-已退款.
     */
    public static final String RETURN_STATUS_REFED = "REFED";

    /**
     * 退货单-退货单状态-已换货.
     */
    public static final String RETURN_STATUS_EXGED = "EXGED";

    /**
     * 退货单-退货单状态-退款-月份关闭.
     */
    public static final String RETURN_STATUS_RECLM = "RECLM";

    /**
     * 退货单-退货单状态-换货-月份关闭.
     */
    public static final String RETURN_STATUS_EXCLM = "EXCLM";

    /**
     * 退货单-退货单编号-流水号位数.
     */
    public static final int RETURN_DIGIT = 6;

    /**
     * 退货单-账款单编号-流水号位数.
     */
    public static final int CREDIT_NOTE_DIGIT = 6;

    /**
     * 退货单-退货单编号-初始数.
     */
    public static final Long RETURN_DIGIT_BEGIN_NUM = 100000L;

    /**
     * 退货单-账款单编号-初始数.
     */
    public static final Long CREDIT_NOTE_BEGIN_NUM = 000001L;

    /**
     * 销售订单-基础数据-市场.
     */
    public static final String BASIC_MARKET = "market";

    /**
     * 销售订单-基础数据-销售组织.
     */
    public static final String BASIC_SALES_ORG = "salesOrg";

    /**
     * 销售订单-基础数据-库存组织集合.
     */
    public static final String BASIC_INV_ORGS = "invOrgs";

    /**
     * 销售订单-基础数据-本位币.
     */
    public static final String BASIC_CURRENCY = "currency";

    /**
     * 销售订单-基础数据-会员.
     */
    public static final String BASIC_MEMBER = "member";

    /**
     * 退货单-退货类型-退款.
     */
    public static final String RETURN_TYPE_REFD = "REFD";

    /**
     * 退货单-退货类型-换货.
     */
    public static final String RETURN_TYPE_EXCH = "EXCH";

    /**
     * 退货单-当前销售组织“是否启用税”组织参数名称.
     */
    public static final String SPM_ENABLE_TAX = "SPM.ENABLE_TAX";

    /**
     * 退货单-账款单编号规则.
     */
    public static final String RM_CREDIT_NOTED_NO = "RM.CREDIT_NOTED_NO";

    /**
     * 退货单-是否返回库存-是.
     */
    public static final String RETURN_INV_FLAG_Y = "Y";

    /**
     * 退货单-是否返回库存-否.
     */
    public static final String RETURN_INV_FLAG_N = "N";

    /**
     * 关闭状态 关闭 Y.
     */
    public static final String CLOSING_STATUS_Y = "Y";

    /**
     * 关闭状态 未关闭 N.
     */
    public static final String CLOSING_STATUS_N = "N";
    /**
     * 是否计算库存 Y.
     */
    public static final String COUNT_STOCK_FLAG_Y = "Y";

    /**
     * 是否计算库存 否 N.
     */
    public static final String COUNT_STOCK_FLAG_N = "N";

    /**
     * 订单渠道-服务中心.
     */
    public static final String ORDER_CHANNEL_SRVC = "SRVC";

    /**
     * 订单渠道-店铺.
     */
    public static final String ORDER_CHANNEL_STORE = "STORE";

    /**
     * 订单渠道-iPoint Center.
     */
    public static final String ORDER_CHANNEL_IPTC = "IPTC";

    /**
     * 订单渠道-自动订货.
     */
    public static final String ORDER_CHANNEL_AUTOS = "AUTOS";

    /**
     * 订单渠道-传真.
     */
    public static final String ORDER_CHANNEL_FAX = "FAX";

    /**
     * 订单渠道-经销商Web.
     */
    public static final String ORDER_CHANNEL_DISWB = "DISWB";

    /**
     * 订单渠道-经销商APP.
     */
    public static final String ORDER_CHANNEL_DISAP = "DISAP";

    /**
     * 订单类型-标准购买.
     */
    public static final String ORDER_TYPE_STDP = "STDP";

    /**
     * 订单类型-跨市场购买.
     */
    public static final String ORDER_TYPE_NMDP = "NMDP";

    /**
     * 会员角色-DIS.
     */
    public static final String MEMBER_ROLE_DIS = "DIS";

    /**
     * 会员状态-NEW.
     */
    public static final String MEMBER_STATUS_NEW = "NEW";

    /**
     * 订单行购买方式-购买.
     */
    public static final String LINE_SALETYPE_PURC = "PURC";

    /**
     * 订单行购买方式-换货.
     */
    public static final String LINE_SALETYPE_EXCH = "EXCH";

    /**
     * 订单行购买方式-免费.
     */
    public static final String LINE_SALETYPE_FREE = "FREE";

    /**
     * 订单行购买方式-礼物.
     */
    public static final String LINE_SALETYPE_GIFT = "GIFT";

    /**
     * 订单行购买方式-积分兑换.
     */
    public static final String LINE_SALETYPE_REDE = "REDE";

    /**
     * 订单行商品类型-商品.
     */
    public static final String LINE_ITEM_TYPE_ITEM = "ITEM";

    /**
     * 订单行商品类型-商品包.
     */
    public static final String LINE_ITEM_TYPE_PACKG = "PKG";

    /**
     * 订单行商品类型-虚拟商品包不计算库存.
     */
    public static final String LINE_ITEM_TYPE_VIRTUAL_PACKG_NOT_COUNT = "VN";

    /**
     * 订单行商品类型-虚拟商品包不计算库存-子行.
     */
    public static final String LINE_ITEM_TYPE_VIRTUAL_PACKG_NOT_COUNT_CHILDREN = "VNL";

    /**
     * 订单行商品类型-虚拟商品包计算库存.
     */
    public static final String LINE_ITEM_TYPE_VIRTUAL_PACKG_COUNT = "VY";

    /**
     * 订单行商品类型-虚拟商品包计算库存-子行.
     */
    public static final String LINE_ITEM_TYPE_VIRTUAL_PACKG_COUNT_CHILDREN = "VYL";

    /**
     * 订单行商品类型-虚拟商品包计算库存-子行商品不计算库存.
     */
    public static final String LINE_ITEM_TYPE_VIRTUAL_PACKG_COUNT_CHILDREN_NOT_COUNT = "VI";

    /**
     * 订单地址类型-账单.
     */
    public static final String ORDER_SITES_BILL = "BILL";

    /**
     * 订单地址类型-配送.
     */
    public static final String ORDER_SITES_SHIP = "SHIP";
    /**
     * 订单状态-支付中.
     */
    public static final String SALES_STATUS_PAYIN_PAYMENT = "PAYIN";

    /**
     * 自动订货单执行状态-成功.
     */
    public static final String AUTO_AUROSHIP_EXECUTE_STATUS_Y = "Y";

    /**
     * 自动订货单执行状态-失败.
     */
    public static final String AUTO_AUROSHIP_EXECUTE_STATUS_N = "N";
    /**
     * 自动订货单执行状态-未执行.
     */
    public static final String AUTO_AUROSHIP_EXECUTE_STATUS_NE = "NE";

    /**
     * 自动订货单执行状态-信用卡过期.
     */
    public static final String AUTO_AUROSHIP_EXECUTE_STATUS_NCARD = "NCARD";

    /**
     * 生成发票后发送邮件模板.
     */
    public static final String EMAIL_ORDER_INVOICE = "EMAIL_ORDER_INVOICE";

    /**
     * 发票发送账户.
     */
    public static final String EMAIL_ORDER_INVOICE_ACCOUNT = "DO_NOT_REPLY";

    /**
     * 计算类型-PV.
     */
    public static final String CALCULATION_TYPE_PV = "PV";

    /**
     * 计算类型-AMOUN.
     */
    public static final String CALCULATION_TYPE_AMOUN = "AMOUN";

    /**
     * 赠品条件标识-Y.
     */
    public static final String AUTOSHIP_GIFT_FLAG_Y = "Y";

    /**
     * 赠品条件标识-N.
     */
    public static final String AUTOSHIP_GIFT_FLAG_N = "N";

    /**
     * 赠品条件标识-S.
     */
    public static final String AUTOSHIP_GIFT_FLAG_S = "S";

    /**
     * 赠品条件标识-I.
     */
    public static final String AUTOSHIP_GIFT_FLAG_I = "I";

    /**
     * 订单信息-新建页面.
     */
    public static final String ORDER_INFO_CREATE = "/om/om_order_create.html";

    /**
     * 订单信息-确认页面.
     */
    public static final String ORDER_INFO_CONFIRM = "/om/om_order_confirm.html";

    /**
     * 订单信息-详情页面.
     */
    public static final String ORDER_INFO_DETAIL = "/om/om_order_detail.html";

    /**
     * 订单信息-支付页面.
     */
    public static final String ORDER_INFO_PAY = "/om/om_order_payment.html";

    /**
     * 订单TAB ID - 新建/确认/支付.
     */
    public static final String TAB_ORDER_CREATE = "ORDER_CREATE";

    /**
     * 订单TAB ID - 详情.
     */
    public static final String TAB_ORDER_DETAIL = "ORDER_INFO";

    /**
     * 订单TAB - TAB ID.
     */
    public static final String ORDER_TAB_ID = "tabId";

    /**
     * 订单TAB - TAB URL.
     */
    public static final String ORDER_TAB_URL = "url";

    /**
     * 订单TAB - TAB TITLE.
     */
    public static final String ORDER_TAB_TITLE = "title";

    /**
     * 订单TAB - ORDER STATUS.
     */
    public static final String ORDER_TAB_STATUS = "status";

    /**
     * 订单TAB TITLE - 订单详情.
     */
    public static final String TAB_TITLE_INFO = "type.com.lkkhpg.dsis.common.order.dto.queryorder.orderinfo";

    /**
     * 订单TAB TITLE - 订单创建.
     */
    public static final String TAB_TITLE_CREATE = "type.com.lkkhpg.dsis.common.order.dto.salesorder.createorder";

    /**
     * 订单详情- 修改奖金月份权限.
     */
    public static final String ACCESS_MODIFY_BONUS_MONTH = "MODIFY_BONUS_MONTH";

    /**
     * 订单TAB TITLE - 订单支付.
     */
    public static final String TAB_TITLE_PAYMENT = "type.com.lkkhpg.dsis.common.order.dto.salesorder.orderpayment";

    /**
     * 登录会员类型.
     */
    public static final String MEMBER_TYPE_VIP = "VIP";

    /**
     * 提交订单 - 会员提交
     */
    public static final String SOURCE_TYPE_VIPP = "VIPP";

    /**
     * 会员属性-银行卡口令.
     */
    public static final String MEMBER_ATTRIBUTE_CARD_PASS = "card_pass";
    /**
     * window分行符号
     */
    public static final String LINE_SEPARATOR = "\r\n";
    /**
     * 分行编码
     */
    public static final String LINE_SEPARATOR_CODE = "line.separator";

    /**
     * 中文编码.
     */
    public static final String LANG_CODE_CN = "zh_CN";
    
    /**
     * SPM.市场与东八时区对应关系.
     */
    public static final String MARKET_TIME_ZONE = "SPM.MARKET_TIME_ZONE";

    /**
     * 公司代码-新天丰
     */
    public static final String SALES_ORDER_COM_CODE = "XTF001";

    public static final String SALES_ORDER_CHECK_Y = "Y";


}
