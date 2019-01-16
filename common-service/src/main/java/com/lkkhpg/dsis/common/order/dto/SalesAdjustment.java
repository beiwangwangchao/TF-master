/*
 *
 */
package com.lkkhpg.dsis.common.order.dto;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 销售订单支付调整.
 * 
 * @author wuyichu
 */
@Table(name = "OM_SALES_ADJUSTMENT")
public class SalesAdjustment extends BaseDTO {

    @Id
    @Column(name = "SALES_ADJUSTMENT_ID", nullable = false, unique = true)
    private Long salesAdjustmentId;

    private Long headerId;

    @NotEmpty
    private String adjustmentType;

    @NotNull
    private BigDecimal adjustmentAmount;

    private String remark;

    private String adjustmentTypeSign;
    
    public Long getSalesAdjustmentId() {
        return salesAdjustmentId;
    }

    public void setSalesAdjustmentId(Long salesAdjustmentId) {
        this.salesAdjustmentId = salesAdjustmentId;
    }

    public String getAdjustmentType() {
        return adjustmentType;
    }

    public void setAdjustmentType(String adjustmentType) {
        this.adjustmentType = adjustmentType == null ? null : adjustmentType.trim();
    }

    public BigDecimal getAdjustmentAmount() {
        return adjustmentAmount;
    }

    public void setAdjustmentAmount(BigDecimal adjustmentAmount) {
        this.adjustmentAmount = adjustmentAmount;
    }

    public Long getHeaderId() {
        return headerId;
    }

    public void setHeaderId(Long headerId) {
        this.headerId = headerId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

	public String getAdjustmentTypeSign() {
		return adjustmentTypeSign;
	}

	public void setAdjustmentTypeSign(String adjustmentTypeSign) {
        this.adjustmentTypeSign = adjustmentTypeSign == "RB" ? "-" : adjustmentTypeSign.trim();

	}

}