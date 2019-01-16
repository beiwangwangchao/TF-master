package com.lkkhpg.dsis.admin.bill.dto;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

import java.math.BigDecimal;
import java.util.Date;

public class BillDto extends BaseDTO {

    private String plat_transno;
    private String org_plat_transno;
    private String partnerNo;
    private String subpartnerNo;
    private BigDecimal total_fee;
    private String out_trade_no;
    private String CurrencyType;
    private String trade_state;
    private String trans_type;
    private BigDecimal platCharge;
    private BigDecimal subPlatCharge;
    private String order_process_date;
    private String publicSubPartner;
    private String privateSubPartner;
    private Date order_date;

    public Date getOrder_date() {
        return order_date;
    }

    public void setOrder_date(Date order_date) {
        this.order_date = order_date;
    }

    public String getPublicSubPartner() {
        return publicSubPartner;
    }

    public void setPublicSubPartner(String publicSubPartner) {
        this.publicSubPartner = publicSubPartner;
    }

    public String getPrivateSubPartner() {
        return privateSubPartner;
    }

    public void setPrivateSubPartner(String privateSubPartner) {
        this.privateSubPartner = privateSubPartner;
    }

    public String getPlat_transno() {
        return plat_transno;
    }

    public void setPlat_transno(String plat_transno) {
        this.plat_transno = plat_transno;
    }

    public String getOrg_plat_transno() {
        return org_plat_transno;
    }

    public void setOrg_plat_transno(String org_plat_transno) {
        this.org_plat_transno = org_plat_transno;
    }

    public String getPartnerNo() {
        return partnerNo;
    }

    public void setPartnerNo(String partnerNo) {
        this.partnerNo = partnerNo;
    }

    public String getSubpartnerNo() {
        return subpartnerNo;
    }

    public void setSubpartnerNo(String subpartnerNo) {
        this.subpartnerNo = subpartnerNo;
    }

    public BigDecimal getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(BigDecimal total_fee) {
        this.total_fee = total_fee;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getCurrencyType() {
        return CurrencyType;
    }

    public void setCurrencyType(String currencyType) {
        CurrencyType = currencyType;
    }

    public String getTrade_state() {
        return trade_state;
    }

    public void setTrade_state(String trade_state) {
        this.trade_state = trade_state;
    }

    public String getTrans_type() {
        return trans_type;
    }

    public void setTrans_type(String trans_type) {
        this.trans_type = trans_type;
    }

    public BigDecimal getPlatCharge() {
        return platCharge;
    }

    public void setPlatCharge(BigDecimal platCharge) {
        this.platCharge = platCharge;
    }

    public BigDecimal getSubPlatCharge() {
        return subPlatCharge;
    }

    public void setSubPlatCharge(BigDecimal subPlatCharge) {
        this.subPlatCharge = subPlatCharge;
    }

    public String getOrder_process_date() {
        return order_process_date;
    }

    public void setOrder_process_date(String order_process_date) {
        this.order_process_date = order_process_date;
    }
}
