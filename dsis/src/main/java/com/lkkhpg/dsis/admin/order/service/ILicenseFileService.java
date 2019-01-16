/*
 *
 */
package com.lkkhpg.dsis.admin.order.service;

import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lkkhpg.dsis.common.exception.CommLicenseFileException;
import com.lkkhpg.dsis.common.order.dto.LicenseFile;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;
import com.lkkhpg.dsis.platform.upload.FileInfo;

/**
 * 授权文件CSV服务接口.
 *
 * @author xiawang.liu@hand-china.com
 */
public interface ILicenseFileService extends ProxySelf<ILicenseFileService> {

    /**
     * 下载生成CSV文件.
     * @param servletRequest  HttpServletRequest
     * @param request     统一上下文
     * @param response    HttpServletResponse
     * @param licenseFile LicenseFile
     * @return 错误提示信息
     * @throws CommLicenseFileException
     */
    String downloadCsvFile(HttpServletRequest servletRequest, IRequest request, HttpServletResponse response,
    		LicenseFile licenseFile) throws CommLicenseFileException;

    /**
     * 上传CSV文件并解析.
     *
     * @param request  HttpServletRequest
     * @param irequest 统一上下文
     * @param fileInfo FileInfo
     * @return 信息
     */
    Object uploadCsvFile(HttpServletRequest request, IRequest irequest, FileInfo fileInfo);
    
    /**
     * 生成CSV文件.
     * 
     * @param request
     *            统一上下文
     * @param out
     *            OutputStream
     * @param licenseFile
     *            LicenseFile
     * @param salesOrgId
     *            salesOrgId
     * @param orderCount
     *            订单行数量
     * @param terminalCode
     *            端末代号
     * @param rShopId
     *            特店代号
     * @throws CommLicenseFileException
     */
    void createCSVFile(IRequest request, OutputStream out, LicenseFile licenseFile, long salesOrgId,
            long orderCount, String terminalCode, String rShopId) throws CommLicenseFileException;
}