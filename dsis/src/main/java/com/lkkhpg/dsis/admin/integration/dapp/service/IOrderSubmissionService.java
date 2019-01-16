/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.integration.dapp.service;

import com.lkkhpg.dsis.admin.integration.dapp.dto.OrderSubmission;
import com.lkkhpg.dsis.common.order.dto.SalesOrder;
import com.lkkhpg.dsis.integration.dapp.exception.DAppException;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 订单提交接口Service.
 * 
 * @author shenqb
 *
 */
public interface IOrderSubmissionService extends ProxySelf<IOrderSubmissionService> {

    /**
     * 订单提交.
     * 
     * @param request
     *            请求上下文.
     * @param orderSubmission
     *            订单提交DTO
     * @return DAppResponse响应数据
     * @throws DAppException
     *             dapp统一异常
     */
    String orderSubmit(IRequest request, OrderSubmission orderSubmission) throws DAppException;

    /**
     * 保存地址.
     * 
     * @param countryCode
     *            国家
     * @param stateCode
     *            省州
     * @param cityCode
     *            城市
     * @param addressLine1
     *            地址1
     * @param addressLine2
     *            地址2
     * @param zipCode
     *            邮编
     * @return locationId 地址ID
     * @throws DAppException
     *             dapp统一异常
     */
    Long saveLocation(String countryCode, String stateCode, String cityCode, String addressLine1, String addressLine2,
            Long zipCode) throws DAppException;

    /**
     * 保存会员地址.
     * 
     * @param iRequest
     *            请求上下文
     * @param memberId
     *            会员表Id
     * @param locationId
     *            地址表Id
     * @param siteUseCode
     *            地址用途
     * @param orderSubmission
     *            订单提交DTO
     * @return 会员地址表Id
     * @throws DAppException
     *             dapp统一异常
     */
    Long saveMemSite(IRequest iRequest, Long memberId, Long locationId, String siteUseCode,
            OrderSubmission orderSubmission) throws DAppException;

    /**
     * 根据销售组织编码和市场ID获取组织ID.
     * 
     * @param orgCode
     *            销售市场编码
     * @param MarketId
     *            市场ID
     * @return 销售组织ID
     * @throws DAppException
     *             dapp统一异常
     */
    Long getSalesOrgIdByCode(String orgCode, Long MarketId) throws DAppException;

    /**
     * dapp参数转换为订单DTO.
     * 
     * @param iRequest
     *            请求上下文
     * @param orderSubmission
     *            dapp订单提交DTO
     * @return 订单DTO
     * @throws DAppException
     *             dapp统一异常
     */
    SalesOrder parseOrder(IRequest iRequest, OrderSubmission orderSubmission) throws DAppException;
}
