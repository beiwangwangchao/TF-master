/*
 *
 */
package com.lkkhpg.dsis.mws.dto;

/**
 * 首页轮播图片、首页图片、页眉图片DTO.
 * 
 * @author xiawang.liu
 *
 */
public class IndexImage {

    /*
     * 图片文件ID
     */
    private Long marketId;

    /*
     * 图片文件ID
     */
    private Long fileId;

    /*
     * 图片文件导航指向URL
     */
    private String url;

    /*
     * 页眉所在页面CODE
     */
    private String moduleCode;

    private String filePath;

    public Long getMarketId() {
        return marketId;
    }

    public void setMarketId(Long marketId) {
        this.marketId = marketId;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
