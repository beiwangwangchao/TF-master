/*
 *
 */
package com.lkkhpg.dsis.common.report.dto;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * PACKING-LIST-HK headerDto.
 * 
 * @author zyhe
 *
 */
public class PackingListHkSalesHeader extends BaseDTO {
    private String distributorName;
    private String distributorNo;
    private String receiptNo;
    private String printDate;
    private String salesOrg;

    public String getDistributorName() {
        return distributorName;
    }

    public void setDistributorName(String distributorName) {
        this.distributorName = distributorName;
    }

    public String getDistributorNo() {
        return distributorNo;
    }

    public void setDistributorNo(String distributorNo) {
        this.distributorNo = distributorNo;
    }

    public String getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(String receiptNo) {
        this.receiptNo = receiptNo;
    }

    public String getPrintDate() {
        return printDate;
    }

    public void setPrintDate(String printDate) {
        this.printDate = printDate;
    }

    public String getSalesOrg() {
        return salesOrg;
    }

    public void setSalesOrg(String salesOrg) {
        this.salesOrg = salesOrg;
    }

}
