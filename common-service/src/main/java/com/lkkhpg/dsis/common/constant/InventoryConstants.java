/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.constant;

import com.lkkhpg.dsis.platform.constant.BaseConstants;

/**
 * 常量.
 * 
 * @author chenjingxiong
 */
public class InventoryConstants extends BaseConstants {

    /**
     * 库存事务处理类型.
     */
    public static final String TRX_TYPE = "INV.TRANSACTION_TYPE";

    /**
     * 库存事务处理来源类型.
     */
    public static final String TRX_SOURCE_TYPE = "INV.TRANSACTION_SOURCE_TYPE";

    /**
     * 事务处理类型 - 入库.
     */
    public static final String TRANSACTION_TYPE_STOCK_IN = "STKIN";

    /**
     * 事务处理类型 - 出库.
     */
    public static final String TRANSACTION_TYPE_STOCK_OUT = "STKOT";

    /**
     * 事务处理类型 - 转入.
     */
    public static final String TRANSACTION_TYPE_TRANSFER_IN = "TRFIN";

    /**
     * 事务处理类型 - 转出.
     */
    public static final String TRANSACTION_TYPE_TRANSFER_OUT = "TRFOT";

    /**
     * 事务处理类型 - 组合-商品包入库.
     */
    public static final String TRANSACTION_TYPE_COMBINE_COMPLETE = "COMCP";

    /**
     * 事务处理类型 - 组合-商品扣减.
     */
    public static final String TRANSACTION_TYPE_COMBINE_CONSUME = "COMCS";

    /**
     * 事务处理类型 - 分解-商品包出库.
     */
    public static final String TRANSACTION_TYPE_DECOMPOSE_COMPLETE = "DCMCP";

    /**
     * 事务处理类型 - 分解-商品增加.
     */
    public static final String TRANSACTION_TYPE_DECOMPOSE_CONSUME = "DCMCS";

    /**
     * 事务处理类型 - 退货.
     */
    public static final String TRANSACTION_TYPE_RETURN = "RETRN";

    /**
     * 事务处理类型 - 发运.
     */
    public static final String TRANSACTION_TYPE_DELIVERY = "DELVY";

    /**
     * 时间 - 1天时间毫秒数.
     */
    public static final int SECONDS = 86400000;

    /**
     * 出入库 - 出库代码.
     */
    public static final String STOCKIO_OUT_CODE = "STKOT";

    /**
     * 出入库 - 出入库明细表表名.
     */
    public static final String STOCKIO_DETAIL_TABLE_NAME = "INV_STOCK_TRX_DETAIL";

    /**
     * 出入库 - 出入库明细表主键字段名.
     */
    public static final String STOCKIO_DETAIL_TABLE_KEY = "TRX_DETAIL_ID";

    /**
     * 出入库 - 出入库单据编号跨度.
     */
    public static final Integer STOCKIO_SEQ_LENGTH = 4;

    /**
     * 出入库 - 出入库单据编号起始.
     */
    public static final Long STOCKIO_START_NUMBER = 0001L;

    /**
     * 出入库 - 包装类型单件代码.
     */
    public static final String STOCKIO_SINGAL_TYPE = "EA";

    /**
     * 出入库 - 批次号启用标志.
     */
    public static final String STOCKIO_ENABLE_FLAG = "Y";

    /**
     * 出入库 - 入库代码.
     */
    public static final String STOCKIO_IN_CODE = "STKIN";

    /**
     * 出入库 - 采购入库代码.
     */
    public static final String PSTOCKI_IN_CODE = "PSTIN";

    /**
     * 出入库 - 退货入库代码.
     */
    public static final String RTURN_IN_CODE = "RTUIN";

    /**
     * 出入库 - 采购出库代码.
     */
    public static final String PSTOCKI_OT_CODE = "PSTOT";


    /**
     * 出入库 - 盘盈代码(类型).
     */
    public static final String STOCKIO_STADJ_TYPE_CODE = "STADJ";

    /**
     * 出入库 - 盘盈代码(序列).
     */
    public static final String STOCKIO_STKAD_SEQ_CODE = "STKAD";

    /**
     * 出入库 - 出库失效原因代码.
     */
    public static final String STOCKIO_OPER_REASON = "EXPR";

    /**
     * 出入库 - 出入库单完成状态代码.
     */
    public static final String STOCKIO_COMPLETE_STATUS = "COMP";

    /**
     * 组合-商品包入库.
     */
    public static final String COMBINE_COMPLETE = "COMCP";

    /**
     * 组合-商品扣减.
     */
    public static final String COMBINE_CONSUME = "COMCS";

    /**
     * 分解-商品包出库.
     */
    public static final String DECOMPOSE_COMPLETE = "DCMCP";

    /**
     * 分解-商品增加.
     */
    public static final String DECOMPOSE_CONSUME = "DCMCS";

    /**
     * 事务处理来源类型.
     */
    public static final String INV_REPACK_TRX = "INV_REPACK_TRX";

    /**
     * 事务处理来源键值.
     */
    public static final String TRX_ID = "TRX_ID";

    /**
     * 事务处理来源类型.
     */
    public static final String INV_REPACK_TRX_DETAIL = "INV_REPACK_TRX_DETAIL";

    /**
     * 事务处理来源键值.
     */
    public static final String TRX_DETAIL_ID = "TRX_DETAIL_ID";

    /**
     * 组合.
     */
    public static final String OPER_TYPE_COMPS = "COMPS";

    /**
     * 转出单状态：新建.
     */
    public static final String OUT_NEW = "NEW";

    /**
     * 转出单状态：完成.
     */
    public static final String OUT_COMPLETED = "COMP";

    /**
     * 转入状态：转入待定.
     */
    public static final String IN_PENDING_FOR_TI = "PDTI";

    /**
     * 转入状态：完成.
     */
    public static final String IN_COMPLETE = "COMP";

    /**
     * 转移状态：新建.
     */
    public static final String NEW = "NEW";
    /**
     * 转移状态：转入待定.
     */
    public static final String PDTI = "PDTI";
    /**
     * 转移状态：未完成.
     */
    public static final String ICOMP = "ICOMP";
    /**
     * 转移状态：完成.
     */
    public static final String COMP = "COMP";

    /**
     * 转入单行状态 :有效.
     */
    public static final String ACTIVE = "ACTV";

    /**
     * 转入单行状态 :有效.
     */
    public static final String CANCELED = "CANCL";

    /**
     * 转移类型 :转入.
     */
    public static final String TRANSFER_TYPE_IN = "TRFIN";

    /**
     * 转移类型 :转出.
     */
    public static final String TRANSFER_TYPE_OUT = "TRFOT";

    /**
     * 移库行表名.
     */
    public static final String TRANSFER_TRX_DETAIL = "TRANSFER_TRX_DETAIL";

    /**
     * 批次控制.
     */
    public static final String BATCHCTRL = "batchctrl";

    /**
     * 移库序列号数字部分长度.
     */
    public static final int SEQ_LENGTH = 7;

    /**
     * 移库序列号起始值.
     */
    public static final Long INIT_VALUE = 1000000L;

    /**
     * 移库序列号:转出字母部分.
     */
    public static final String TO = "TO";

    /**
     * 移库序列号:转入字母部分.
     */
    public static final String TI = "TI";

    /**
     * 商品包类型.
     */
    public static final String PACKAGE = "PACKG";

    /**
     * 重新分包状态：完成.
     */
    public static final String REPACK_STATUS_COMP = "COMP";

    /**
     * 流水号类型：重新分包.
     */
    public static final String DOC_SEQ_TYPE_REPACK = "REPACK";

    /**
     * 流水号类型：出入库.
     */
    public static final String DOC_SEQ_TYPE_STOCK = "STOCK";

    /**
     * 流水号类型：移库.
     */
    public static final String DOC_SEQ_TYPE_TRANSFER = "TRANSFER";

    /**
     * 流水号前缀：重新分包.
     */
    public static final String DOC_SEQ_PREFIX_REPACK = "RP_";

    /**
     * 流水号前缀：出入库.
     */
    public static final String DOC_SEQ_PREFIX_STOCK = "STK_";

    /**
     * 流水号前缀：移库.
     */
    public static final String DOC_SEQ_PREFIX_TRANSFER = "TRANSFER_";

    /**
     * 流水号前缀：移库.
     */
    public static final int MAX_LENGTH_REPACK = 6;

    /**
     * 流水号前缀：移库.
     */
    public static final Long MAX_LENGTH_INIT_VALUE = 100000L;

    /**
     * 批次过期预警邮件发送模板Code.
     */
    public static final String LOT_MAIL_MOULD = "EMAIL_LOT_ALERT";

    /**
     * 库存量预警邮件发送模板Code.
     */
    public static final String QUANTITY_MAIL_MOULD = "EMAIL_QUANTITY_ALERT";

    /**
     * 组织参数-库存预警中通知仓管员.
     */
    public static final String SPM_NOTED_WAREHOUSEMAN = "SPM.NOTED_WAREHOUSEMAN";

    /**
     * 系统邮件发送账号Code.
     */
    public static final String SPM_ALERT_EMAIL_ACCOUNT = "PASSWORD";

    /**
     * 分包批次行 注释 分隔符.
     */
    public static final String REPACKTRX_REMARK_SEPARATOR = ";";

    /**
     * 成本记录序列.
     */
    public static final String COST_DOC_TYPE = "COST";

    /**
     * 成本记录号前缀.
     */
    public static final String COST_CST = "CST";

    /**
     * 成本记录号序列长度.
     */
    public static final int COST_NUMBER_LENGTH = 4;

    /**
     * 成本记录号步长.
     */
    public static final Long COST_NUMBER_STEP = 1L;

    /**
     * 组织参数_成本计算方式.
     */
    public static final String SPM_COST_METHOD = "SPM.COST_METHOD";

    /**
     * 成本计算方式_先进先出.
     */
    public static final String COST_CAL_FIFO = "FIFO";

    /**
     * 成本计算方式_加权平均.
     */
    public static final String COST_CAL_AVRAG = "AVRAG";

    /**
     * 成本记录状态_已处理.
     */
    public static final String COST_STATUS_P = "P";

    /**
     * 成本记录状态_未处理.
     */
    public static final String COST_STATUS_N = "N";

    /**
     * 成本记录状态_已取消.
     */
    public static final String COST_STATUS_C = "C";

    /**
     * 12月份.
     */
    public static final Integer COST_MONTH_NOV = 12;
    /**
     * 1月份.
     */
    public static final Integer COST_MONTH_JAN = 1;

    public static final String PACKAGE_COUNT_STOCK_FLAG_ENABLE = "Y";
    public static final String ITEM_COUNT_STOCK_FLAG_ENABLE = "O";

}
