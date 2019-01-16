package com.lkkhpg.dsis.common.discount.constract;

/**
 * Project: pos2
 * Package: com.lkkhpg.dsis.common.discount.discount.constract
 * User: 11816
 * Date: 2018/1/15
 * Time: 15:00
 */
public class DiscountConstract {

    /**
     * transaction type
     */
    public static final String TRX_TYPE = "DC.ADJUSTMENT_TYPE";

    /**
     * transaction source type
     */
    public static final String TRX_SOURCE_TYPE = "DC.ADJUSTMENT_REASON";

    /**
     *
     */
    public static final String TRX_SOURCE_STATUS = "DC.ADJUSTMENT_STATUS";

    /**
     * detail transaction type -  new
     */
    public static final String TRX_TYPE_DCIN = "DCIN";

    /**
     * detail transaction type  - ADD
     */
    public static final String TRX_TYPE_ADIN = "ADIN";

    /**
     * detail transaction type - REDUCE
     */
    public static final String TRX_TYPE_ADDE = "ADDE";

    /**
     * 订单消耗
     */
    public static final String TRX_TYPE_USED = "USED";

    /**
     * detail transaction adjust reason - rebate
     */
    public static final String TRX_ADJ_REASON_REBATE = "REBATE";

    /**
     * detail transaction adjust reason - return
     */
    public static final String TRX_ADJ_REASON_RETURN = "RETURN";

    /**
     * detail transaction adjust reason - increase
     */
    public static final String TRX_ADJ_REASON_INCREASE = "INCREASE";

    /**
     * detail transaction adjust reason - decrease
     */
    public static final String TRX_ADJ_REASON_DECREASE = "DECREASE";

    /**
     * for order
     */
    public static final String TRX_ADJ_REASON_ORDER = "ORDS";

    /**
     * detail transaction adjust reason - other
     */
    public static final String TRX_ADJ_REASON_OTHER = "OTHER";

    /**
     * transaction adjust status - new
     */
    public static final String TRX_ADJ_STATUS_NEW = "NEW";

    /**
     * transaction adjust status - complete
     */
    public static final String TRX_ADJ_STATUS_COMP = "COMP";


    /**
     * 出入库 - 出入库单据编号跨度.
     */
    public static final Integer STOCKIO_SEQ_LENGTH = 4;

    /**
     * 出入库 - 出入库单据编号起始.
     */
    public static final Long STOCKIO_START_NUMBER = 0001L;
}
