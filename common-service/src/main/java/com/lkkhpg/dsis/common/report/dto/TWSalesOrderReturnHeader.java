/*
 * #{copyright}#
 */
package com.lkkhpg.dsis.common.report.dto;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 台湾营业人销货退回单头数据返回DTO.
 * 
 * @author zyhe
 *
 */
public class TWSalesOrderReturnHeader {

    private String companyName;
    private String brNo;
    private String address;
    private String printDate;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getBrNo() {
        return brNo;
    }

    public void setBrNo(String brNo) {
        this.brNo = brNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPrintDate() {
        return printDate;
    }

    public void setPrintDate(String printDate) {
        this.printDate = printDate;
    }

}
