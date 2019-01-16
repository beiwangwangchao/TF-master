/*
 *
 */
package com.lkkhpg.dsis.admin.integration.gds.service;

import java.util.Date;
import java.util.List;

import com.lkkhpg.dsis.admin.integration.gds.exception.IntegrationException;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 申请号接口类.
 * 
 * @author frank.li
 */
public interface IGdsUtilService extends ProxySelf<IGdsUtilService> {
    /**
     * 生成申请号.
     * 
     * @param request
     *            请求上下文
     * @return appNo 申请号
     */
    String getAppNo(IRequest request);

    /**
     * 根据GDS系统OrgCode获取POS系统映射后默认的OrgCode.
     * 
     * @param request
     *            请求上下文
     * @param gdsOrgCode
     *            GDS系统OrgCode
     * @return orgCode POS系统OrgCode
     * @throws IntegrationException
     *             接口统一异常
     */
    String getOrgCode(IRequest request, String gdsOrgCode) throws IntegrationException;

    /**
     * 根据GDS系统OrgCode获取POS系统映射后默认的OrgCode.
     * 
     * @param gdsOrgCode
     *            GDS系统OrgCode
     * @return orgCode POS系统OrgCode
     * @throws IntegrationException
     *             接口统一异常
     */
    String getOrgCode(String gdsOrgCode) throws IntegrationException;

    /**
     * 根据GDS系统OrgCode获取POS系统映射后的OrgCode列表.
     * 
     * @param gdsOrgCode
     *            GDS系统OrgCode
     * @return orgCodes POS系统OrgCode列表
     * @throws IntegrationException
     *             接口统一异常
     */
    List<String> getOrgCodes(String gdsOrgCode) throws IntegrationException;

    /**
     * 根据GDS系统OrgCode获取POS系统映射后取默认的OrgCode对应的marketId.
     * 
     * @param gdsOrgCode
     *            GDS系统OrgCode
     * @return marketId POS系统marketId
     * @throws IntegrationException
     *             接口统一异常
     */
    Long getMarketId(String gdsOrgCode) throws IntegrationException;

    /**
     * 根据GDS系统OrgCode获取POS系统映射后的marketId列表.
     * 
     * @param gdsOrgCode
     *            GDS系统OrgCode
     * @return marketIds POS系统marketId列表
     * @throws IntegrationException
     *             接口统一异常
     */
    List<Long> getMarketIds(String gdsOrgCode) throws IntegrationException;

    /**
     * 根据POS系统OrgCode获取GDS系统映射后的OrgCode.
     * 
     * @param request
     *            请求上下文
     * @param orgCode
     *            POS系统OrgCode
     * @return orgCode GDS系统OrgCode
     * @throws IntegrationException
     *             接口统一异常
     */
    String getGdsOrgCode(IRequest request, String orgCode) throws IntegrationException;

    /**
     * 根据POS系统OrgCode获取GDS系统映射后的OrgCode.
     * 
     * @param request
     *            请求上下文
     * @param orgCode
     *            POS系统OrgCode
     * @return appNo 申请号
     * @throws IntegrationException
     *             接口统一异常
     */
    String getGdsOrgCode(String orgCode) throws IntegrationException;

    /**
     * 根据POS系统orgCode获取关联的所有orgCode列表,
     * 如HK、MO、HKIGI皆映射到HKG，则根据HK可以获取到HK、MO、HKIGI.
     * 
     * @param orgCode
     *            POS系统orgCode
     * @return orgCodes POS系统orgCode列表
     * @throws IntegrationException
     *             接口统一异常
     */
    List<String> getRefOrgCodes(String orgCode) throws IntegrationException;

    /**
     * 根据POS系统orgCode获取关联的所有marketId列表,
     * 如HK、MO、HKIGI皆映射到HKG，则根据HK可以获取到HK、MO、HKIGI.
     * 
     * @param orgCode
     *            POS系统orgCode
     * @return marketIds POS系统marketId列表
     * @throws IntegrationException
     *             接口统一异常
     */
    List<Long> getRefMarketIds(String orgCode) throws IntegrationException;

    /**
     * 界面触发接口获取当前POS系统OrgCode.
     * 
     * @param request
     *            请求上下文
     * @return appNo 申请号
     * @throws IntegrationException
     *             接口统一异常
     */
    String getCurrentOrgCode(IRequest request) throws IntegrationException;

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
     * @throws IntegrationException
     *             接口异常
     */
    Date dateTimeStringToDate(String dateTime) throws IntegrationException;

    /**
     * 转为日期字符串格式日期.
     * 
     * @param date
     *            日期
     * @return dataStr 时间
     * @throws IntegrationException
     *             接口异常
     */
    Date dateStringToDate(String date) throws IntegrationException;
}
