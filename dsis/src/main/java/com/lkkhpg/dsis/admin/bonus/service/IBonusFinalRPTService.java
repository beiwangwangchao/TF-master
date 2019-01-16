/*
 *
 */
package com.lkkhpg.dsis.admin.bonus.service;

import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import com.lkkhpg.dsis.common.exception.CommBonusException;
import com.lkkhpg.dsis.platform.core.IRequest;

/**
 * RPT-00172报表Service.
 * 
 * @author hanrui.huang
 *
 */
public interface IBonusFinalRPTService {

    /**
     * 下载生成txt文件名.
     * 
     * @param request
     *            统一上下文
     * @return 错误提示信息
     */
    String downloadFile(IRequest request);

    /**
     * 生成txt文件.
     * 
     * @param request
     *            请求上下文
     * @param response
     *            OutputStream
     * @param dateFrom
     *            所得给付起始年月
     * @param dateTo
     *            所得给付结束年月
     * @param memberCodeForm
     *            会员编号从
     * @param memberCodeTo
     *            会员编号至
     * @throws CommBonusException 统一异常处理
     */
    void createTXTFile(IRequest request, HttpServletResponse response, String dateFrom, String dateTo, String memberCodeForm,
            String memberCodeTo) throws CommBonusException;
}
