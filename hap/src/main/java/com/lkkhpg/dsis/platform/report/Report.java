/*
 *
 */
package com.lkkhpg.dsis.platform.report;

/**
 * 报表类.
 * @author chenjingxiong
 */
public class Report {

    private String fileName;
    
    private String contentType;
    
    private byte[] reportData;
    
    private Long fileId;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public byte[] getReportData() {
        return reportData;
    }

    public void setReportData(byte[] reportData) {
        this.reportData = reportData;
    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }
    
}
