/*
 *
 */
package com.lkkhpg.dsis.common.order.dto;

import java.math.BigDecimal;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 发票.
 * 
 * @author li.peng@hand-china.com
 *
 */
public class Invoice extends BaseDTO {

    private Long invoiceId;

    private Long marketId;

    private String invoiceNumber;

    private BigDecimal invoiceAmount;

    private Long orderId;

    private String printedFlag;

    private Long attachmentId; // 实际上存储的是sys_file的file_id

    private String programCode;

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Long getMarketId() {
        return marketId;
    }

    public void setMarketId(Long marketId) {
        this.marketId = marketId;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber == null ? null : invoiceNumber.trim();
    }

    public BigDecimal getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(BigDecimal invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getPrintedFlag() {
        return printedFlag;
    }

    public void setPrintedFlag(String printedFlag) {
        this.printedFlag = printedFlag == null ? null : printedFlag.trim();
    }

    public Long getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(Long attachmentId) {
        this.attachmentId = attachmentId;
    }

    public String getProgramCode() {
        return programCode;
    }

    public void setProgramCode(String programCode) {
        this.programCode = programCode;
    }

}