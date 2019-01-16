/*
 * #{copyright}#
 */
package com.lkkhpg.dsis.common.report.dto;

import java.math.BigDecimal;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 台湾营业人销货退回单footDTO.
 * 
 * @author zhenyang.he
 *
 */
public class TWSalesOrderReturnfoot {
    private String memberName;
    private String vbrNember;
    //人工调整金额
    private BigDecimal adjustmentAmount;
    //运费
    private BigDecimal shippingFee;
    
    public BigDecimal getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(BigDecimal shippingFee) {
        this.shippingFee = shippingFee;
    }

    public BigDecimal getAdjustmentAmount() {
        return adjustmentAmount;
    }

    public void setAdjustmentAmount(BigDecimal adjustmentAmount) {
        this.adjustmentAmount = adjustmentAmount;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getVbrNember() {
        return vbrNember;
    }

    public void setVbrNember(String vbrNember) {
        this.vbrNember = vbrNember;
    }

}
