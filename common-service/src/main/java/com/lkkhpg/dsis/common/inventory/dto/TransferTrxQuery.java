/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.inventory.dto;

import java.util.Date;
import java.util.List;


/**
 * TransferTrx 查询的方法.
 * 
 * @author chuangsheng.zhang
 */
public class TransferTrxQuery extends TransferTrx {
    private static final long serialVersionUID = 1L;

    // 转入单编号
    private String inTrxNumber;

    // 转出单编号
    private String outTrxNumber;

    // 转入状态集合
    private List<String> inStatuses;

    // 转出状态集合
    private List<String> outStatuses;

    // 转移状态集合
    private String overallStatusQ;

    // 转移状态集合
    private List<String> overallStatuses;

    // 转入状态
    private String inStatus;

    // 转出状态
    private String outStatus;

    private Date createDateFrom;

    private Date createDateTo;

    private Date trxDateFrom;

    private Date trxDateTo;

    public String getInTrxNumber() {
        return inTrxNumber;
    }

    public void setInTrxNumber(String inTrxNumber) {
        this.inTrxNumber = inTrxNumber;
    }

    public String getOutTrxNumber() {
        return outTrxNumber;
    }

    public void setOutTrxNumber(String outTrxNumber) {
        this.outTrxNumber = outTrxNumber;
    }

    public List<String> getInStatuses() {
        return inStatuses;
    }

    public void setInStatuses(List<String> inStatuses) {
        this.inStatuses = inStatuses;
    }

    public List<String> getOutStatuses() {
        return outStatuses;
    }

    public void setOutStatuses(List<String> outStatuses) {
        this.outStatuses = outStatuses;
    }

    public String getInStatus() {
        return inStatus;
    }

    public void setInStatus(String inStatus) {
        this.inStatus = inStatus;
    }

    public String getOutStatus() {
        return outStatus;
    }

    public void setOutStatus(String outStatus) {
        this.outStatus = outStatus;
    }

    public Date getCreateDateFrom() {
        return createDateFrom;
    }

    public void setCreateDateFrom(Date createDateFrom) {
        this.createDateFrom = createDateFrom;
    }

    public Date getCreateDateTo() {
        return createDateTo;
    }

    public void setCreateDateTo(Date createDateTo) {
        this.createDateTo = createDateTo;
    }

    public Date getTrxDateFrom() {
        return trxDateFrom;
    }

    public void setTrxDateFrom(Date trxDateFrom) {
        this.trxDateFrom = trxDateFrom;
    }

    public Date getTrxDateTo() {
        return trxDateTo;
    }

    public void setTrxDateTo(Date trxDateTo) {
        this.trxDateTo = trxDateTo;
    }

    public List<String> getOverallStatuses() {
        return overallStatuses;
    }

    public void setOverallStatuses(List<String> overallStatuses) {
        this.overallStatuses = overallStatuses;
    }

    public String getOverallStatusQ() {
        return overallStatusQ;
    }

    public void setOverallStatusQ(String overallStatusQ) {
        this.overallStatusQ = overallStatusQ;
    }

}
