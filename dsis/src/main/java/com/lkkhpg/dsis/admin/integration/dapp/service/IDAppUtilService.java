/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.integration.dapp.service;

import java.math.BigDecimal;
import java.util.Date;

import com.lkkhpg.dsis.integration.dapp.exception.DAppException;
import com.lkkhpg.dsis.platform.core.IRequest;

/**
 * D-App接口工具接口类.
 * 
 * @author frank.li
 */
public interface IDAppUtilService {

    /**
     * 根据GDS系统OrgCode获取POS系统映射后的OrgCode.
     * 
     * @param request
     *            请求上下文
     * @param gdsOrgCode
     *            GDS系统OrgCode
     * @return orgCode POS系统OrgCode
     * @throws DAppException
     *             接口统一异常
     */
    String getOrgCode(String gdsOrgCode) throws DAppException;

    /**
     * 根据POS系统OrgCode获取GDS系统映射后的OrgCode.
     * 
     * @param request
     *            请求上下文
     * @param orgCode
     *            POS系统OrgCode
     * @return orgCode GDS系统OrgCode
     * @throws DAppException
     *             接口统一异常
     */
    String getGdsOrgCode(String orgCode) throws DAppException;

    /**
     * 根据POS系统lang获取GDS系统映射后的lang.
     * 
     * @param request
     *            请求上下文
     * @param lang
     *            POS系统lang
     * @return lang GDS系统lang
     * @throws DAppException
     *             接口统一异常
     */
    String getGdsLang(String lang) throws DAppException;

    /**
     * 转为时间格式字符串.
     * 
     * @param date
     *            日期
     * @return dataStr 时间
     */
    String toDateTimeString(Date date);

    /**
     * 转为日期格式字符串.
     * 
     * @param date
     *            日期
     * @return dataStr 时间
     */
    String toDateString(Date date);

    /**
     * 转为期间字符串yyyyMM.
     * 
     * @param date
     *            日期
     * @return periodStr 期间
     */
    String toPeriodString(Date date);

    /**
     * 转为时间字符日期.
     * 
     * @param dateTime
     *            日期
     * @return dataStr 时间
     * @throws DAppException
     *             接口异常
     */
    Date dateTimeStringToDate(String dateTime) throws DAppException;

    /**
     * 转为日期字符串格式日期.
     * 
     * @param date
     *            日期
     * @return dataStr 时间
     * @throws DAppException
     *             接口异常
     */
    Date dateStringToDate(String date) throws DAppException;

    /**
     * @return dapp账号ID
     * @throws DAppException
     *             接口异常
     */
    Long getDappAccountId() throws DAppException;

    /**
     * 根据“马来模式”取税率，启用税且价格不含税，则返回销售组织上的税率
     * 
     * @param request
     *            请求上下文，须包含marketId和salesOrgId
     * @return 税率
     */
    BigDecimal getTaxBySalesOrg(IRequest request);
}
