/*
 *
 */
package com.lkkhpg.dsis.platform.report;

/**
 * 导出类型.
 * 
 * @author chenjingxiong
 */
public enum ExportType {
    EXCEL("EXCEL"), HTML("HTML"), PDF("PDF"), CSV("CSV");

    private String code;

    ExportType(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }
}
