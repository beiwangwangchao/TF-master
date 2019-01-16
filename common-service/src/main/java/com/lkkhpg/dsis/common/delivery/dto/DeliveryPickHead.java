/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.delivery.dto;

import java.util.List;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 挑库单头dto.
 * 
 * @author Zhaoqi
 *
 */
public class DeliveryPickHead extends BaseDTO {
    private Long pickReleaseId;

    private Long orderId;

    private String remark;

    private List<DeliveryPickLine> lines;

    public Long getPickReleaseId() {
        return pickReleaseId;
    }

    public void setPickReleaseId(Long pickReleaseId) {
        this.pickReleaseId = pickReleaseId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public List<DeliveryPickLine> getLines() {
        return lines;
    }

    public void setLines(List<DeliveryPickLine> lines) {
        this.lines = lines;
    }

}