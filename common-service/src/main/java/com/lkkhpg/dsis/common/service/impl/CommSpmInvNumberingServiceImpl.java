/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.service.impl;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lkkhpg.dsis.common.config.dto.SpmInvNumbering;
import com.lkkhpg.dsis.common.config.dto.SpmMarket;
import com.lkkhpg.dsis.common.config.mapper.SpmInvNumberingMapper;
import com.lkkhpg.dsis.common.config.mapper.SpmMarketMapper;
import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.common.exception.CommSystemProfileException;
import com.lkkhpg.dsis.common.order.dto.SalesOrder;
import com.lkkhpg.dsis.common.order.mapper.SalesOrderMapper;
import com.lkkhpg.dsis.common.service.ICommSpmInvNumberingService;
import com.lkkhpg.dsis.platform.core.IRequest;

/**
 * 发票编号发放接口.
 * 
 * @author li.peng@hand-china.com
 *
 */
@Service
@Transactional
public class CommSpmInvNumberingServiceImpl implements ICommSpmInvNumberingService {

    @Autowired
    private SalesOrderMapper salesOrderMapper;

    @Autowired
    private SpmMarketMapper spmMarketMapper;

    @Autowired
    private SpmInvNumberingMapper spmInvNumberingMapper;

    /**
     * 发票编号规则 -市场.
     */
    public static final String NUMBERING_BY_ORDER = "ORDER";

    /**
     * 发票编号规则 -标识.
     */
    public static final String NUMBERING_BY_ASSIGN = "SYSAS";

    /**
     * 启用标识.
     */
    public static final String ENABLED_FLAG = "Y";

    /**
     * 默认取值间隔.
     */
    public static final Long DEFAULT_STEP = 1L;

    /**
     * 编号初始值.
     */
    public static final Long DEFAULT_INIT_NUMBER = 0L;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String createInvoiceNumber(IRequest request, Long orderId) throws CommSystemProfileException {
        String number = null;
        SalesOrder salesOrder = salesOrderMapper.selectByPrimaryKey(orderId);
        if (salesOrder == null || salesOrder.getOrderNumber() == null) {
            throw new CommSystemProfileException(CommSystemProfileException.ORDER_EXCEPTION, new Object[] {});
        }
        SpmMarket market = spmMarketMapper.selectBySalesOrgId(salesOrder.getSalesOrgId());

        List<SpmInvNumbering> spmInvNumberingList = spmInvNumberingMapper
                .selectNumberRule(market.getMarketId().toString());

        if (spmInvNumberingList == null || spmInvNumberingList.size() != 1) {
            throw new CommSystemProfileException(CommSystemProfileException.NUMBERING_EXCEPTION, new Object[] {});
        }

        SpmInvNumbering spmInvNumbering = spmInvNumberingList.get(0);
        if (spmInvNumbering.getEnabledFlag() == null || !ENABLED_FLAG.equals(spmInvNumbering.getEnabledFlag())) {
            throw new CommSystemProfileException(CommSystemProfileException.NUMBERING_EXCEPTION, new Object[] {});
        }
        // 0元是否开票
        if (BigDecimal.ZERO.equals(salesOrder.getActrualPayAmt())
                && SystemProfileConstants.NO.equals(spmInvNumbering.getZeroFlag())) {
            return null;
        }
        
        switch (spmInvNumbering.getRuleType()) {
        case NUMBERING_BY_ORDER:
            number = salesOrder.getOrderNumber();
            break;
        case NUMBERING_BY_ASSIGN:
            number = createNumberByAssign(spmInvNumbering);
            break;
        default:
            break;
        }
       
        return number;
    }

    private String createNumberByAssign(SpmInvNumbering spmInvNumbering) throws CommSystemProfileException {
        String prefix = spmInvNumbering.getPrefix() == null ? "" : spmInvNumbering.getPrefix();
        String suffix = spmInvNumbering.getSuffix() == null ? "" : spmInvNumbering.getSuffix();
        if (spmInvNumbering.getMaxNumber().equals(spmInvNumbering.getCurrentNumber())) {
            throw new CommSystemProfileException(CommSystemProfileException.USEABLE_NUMBER_EXCEPTION, new Object[] {});
        }

        Long initNumber = spmInvNumbering.getInitNumber() == null ? DEFAULT_INIT_NUMBER
                : Long.parseLong(spmInvNumbering.getInitNumber());
        Long curNumber = spmInvNumbering.getCurrentNumber() == null ? initNumber
                : Long.parseLong(spmInvNumbering.getCurrentNumber());
        Long step = spmInvNumbering.getStepLen() == null ? DEFAULT_STEP : spmInvNumbering.getStepLen();
        
        Long invoiceNum = 0L;
        if (spmInvNumbering.getCurrentNumber() == null) {
            invoiceNum = initNumber;
        } else {
            invoiceNum = curNumber + step;
        }
        String invoiceNumber = invoiceNum.toString();
        // 编码长度规则
        if (spmInvNumbering.getMaxNumber() == null && spmInvNumbering.getInitNumber() == null) {
            throw new CommSystemProfileException(CommSystemProfileException.INITNUMBER_AND_MAXNUMBER_EXCEPTION,
                    new Object[] {});
        }
        int numberLength = spmInvNumbering.getMaxNumber() == null ? spmInvNumbering.getInitNumber().length()
                : spmInvNumbering.getMaxNumber().length();
        invoiceNumber = numberFormat(numberLength, invoiceNum);

        // 修改invoiceAssign 的currentNumber.
        spmInvNumbering.setCurrentNumber(invoiceNumber);
        spmInvNumberingMapper.updateNumbering(spmInvNumbering);
        return prefix + invoiceNumber + suffix;
    }

    /**
     * 格式化数字.
     * 
     * @param digits
     *            位数,
     * @param invoiceNum
     *            数字
     * @return String 补零后的数字
     */
    private String numberFormat(int digits, Long invoiceNum) {
        NumberFormat nf = NumberFormat.getInstance();
        // 设置是否使用分组
        nf.setGroupingUsed(false);
        // 设置最大整数位数
        nf.setMaximumIntegerDigits(digits);
        // 设置最小整数位数
        nf.setMinimumIntegerDigits(digits);
        // 输出测试语句
        return nf.format(invoiceNum);

    }

}
