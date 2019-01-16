/*
 *
 */
package com.lkkhpg.dsis.admin.integration.gds.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 小批量同步销售行资料.
 * 
 * @author wuyichu
 */
public class IsgSoLine extends BaseDTO {
    private Long interfaceDetailId;

    private String interfaceId;

    private String gsoMaster;

    private String productCode;

    private Double productPrice;

    private Double productPoint;

    private Double localSalePrice;

    private Double localSalePoint;

    private Double localSaleRebate;

    private Long localSaleQty;

    private Integer lineNo;

    private String adviseNo;

    private String processStatus;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date processDate;

    private String processMessage;

    public Long getInterfaceDetailId() {
        return interfaceDetailId;
    }

    public void setInterfaceDetailId(Long interfaceDetailId) {
        this.interfaceDetailId = interfaceDetailId;
    }

    public String getInterfaceId() {
        return interfaceId;
    }

    public void setInterfaceId(String interfaceId) {
        this.interfaceId = interfaceId == null ? null : interfaceId.trim();
    }

    public String getGsoMaster() {
        return gsoMaster;
    }

    public void setGsoMaster(String gsoMaster) {
        this.gsoMaster = gsoMaster == null ? null : gsoMaster.trim();
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode == null ? null : productCode.trim();
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public Double getProductPoint() {
        return productPoint;
    }

    public void setProductPoint(Double productPoint) {
        this.productPoint = productPoint;
    }

    public Double getLocalSalePrice() {
        return localSalePrice;
    }

    public void setLocalSalePrice(Double localSalePrice) {
        this.localSalePrice = localSalePrice;
    }

    public Double getLocalSalePoint() {
        return localSalePoint;
    }

    public void setLocalSalePoint(Double localSalePoint) {
        this.localSalePoint = localSalePoint;
    }

    public Double getLocalSaleRebate() {
        return localSaleRebate;
    }

    public void setLocalSaleRebate(Double localSaleRebate) {
        this.localSaleRebate = localSaleRebate;
    }

    public Long getLocalSaleQty() {
        return localSaleQty;
    }

    public void setLocalSaleQty(Long localSaleQty) {
        this.localSaleQty = localSaleQty;
    }

    public Integer getLineNo() {
        return lineNo;
    }

    public void setLineNo(Integer lineNo) {
        this.lineNo = lineNo;
    }

    public String getAdviseNo() {
        return adviseNo;
    }

    public void setAdviseNo(String adviseNo) {
        this.adviseNo = adviseNo == null ? null : adviseNo.trim();
    }

    public String getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(String processStatus) {
        this.processStatus = processStatus == null ? null : processStatus.trim();
    }

    public Date getProcessDate() {
        return processDate;
    }

    public void setProcessDate(Date processDate) {
        this.processDate = processDate;
    }

    public String getProcessMessage() {
        return processMessage;
    }

    public void setProcessMessage(String processMessage) {
        this.processMessage = processMessage == null ? null : processMessage.trim();
    }
}