/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.exception;

import com.lkkhpg.dsis.platform.exception.BaseException;

/**
 * 授权文件统一异常.
 * 
 * @author LiuXiaWang
 */
public class CommLicenseFileException extends BaseException {

	private static final long serialVersionUID = 1L;

	private static final String EXCEPTION_CODE = "com.lkkhpg.dsis.admin.order.csv";

    /**
     * 授权文件-下载文件错误-文件生成失败.
     */
    public static final String CSV_DOWNLOAD_ERROR_FILE_FAIL = "msg.csv.download.error.filefail";
    
    /**
     * 授权文件-下载文件错误-不存在该批次号.
     */
    public static final String CSV_DOWNLOAD_ERROR_NOT_ITEM = "msg.csv.download.error.notitem";
    
    /**
     * 授权文件-下载文件错误-特店编号不存在.
     */
    public static final String CSV_DOWNLOAD_ERROR_NOT_HSHOPID = "msg.csv.download.error.nothshopid";
    /**
     * 授权文件-下载文件错误-端末代号不存在.
     */
    public static final String CSV_DOWNLOAD_ERROR_NOT_TERMINALCODE = "msg.csv.download.error.notterminalcode";
    /**
     * 授权文件-下载文件错误-特店代号不存在.
     */
    public static final String CSV_DOWNLOAD_ERROR_NOT_RSHOPID = "msg.csv.download.error.notrshopid";

    /**
     * 授权文件-上传文件错误-上传文件为空.
     */
    public static final String CSV_UPLOAD_ERROR_FILE_NULL = "msg.csv.upload.error.file.null";
    
    /**
     * 授权文件-上传文件错误-上传文件不是授权文件.
     */
    public static final String CSV_UPLOAD_ERROR_FILE_NOTCSV = "msg.csv.upload.error.file.notcsv";
    
    /**
     * 授权文件-上传文件错误-上传文件已存在.
     */
    public static final String CSV_UPLOAD_ERROR_FILE_EXISTS = "msg.csv.upload.error.file.exists";
    
    /**
     * 授权文件-上传文件错误-上传文件解析错误.
     */
    public static final String CSV_UPLOAD_ERROR_FILE_ERROR = "msg.csv.upload.error.file.error";
    
    /**
     * 授权文件-上传文件错误-上传文件类型不允许.
     */
    public static final String CSV_UPLOAD_ERROR_FILE_TYPE_ERROR = "msg.csv.upload.error.file.type.error";
    
    /**
     * 构造方法.
     * 
     * @param descriptionKey
     *            消息代码.
     * @param parameters
     *            消息参数.
     */
    public CommLicenseFileException(String descriptionKey, Object[] parameters) {
        super(EXCEPTION_CODE, descriptionKey, parameters);
    }

}
