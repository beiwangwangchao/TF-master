/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.integration.dapp.service;

import java.util.List;

import com.lkkhpg.dsis.admin.integration.dapp.dto.BatchOrdersSubmissionRequest;
import com.lkkhpg.dsis.admin.integration.dapp.dto.BatchOrdersSubmissionResponse;
import com.lkkhpg.dsis.integration.dapp.exception.DAppException;
import com.lkkhpg.dsis.platform.core.IRequest;

/**
 * 批量提交订单接口Service.
 * 
 * @author shenqb
 *
 */
public interface IBatchOrdersSubmissionService {

    /**
     * 批量提交订单 - 独立事务.
     * 
     * @param request
     *            请求上下文
     * @param batchOrdersSubmissionRequest
     *            批量提交订单请求DTO
     * @return 批量提交订单结果
     * @throws DAppException
     *             dapp异常
     */
    List<BatchOrdersSubmissionResponse> batchOrdersSubmission(IRequest request,
            BatchOrdersSubmissionRequest batchOrdersSubmissionRequest) throws DAppException;

    /**
     * 批量提交订单 - 同个事务.
     * 
     * @param request
     *            请求上下文
     * @param batchOrdersSubmissionRequest
     *            批量提交订单请求DTO
     * @return 批量提交订单结果
     * @throws DAppException
     *             dapp统一异常
     */
    List<BatchOrdersSubmissionResponse> batchOrdersSubmissionWithOneTransaction(IRequest request,
            BatchOrdersSubmissionRequest batchOrdersSubmissionRequest) throws DAppException;
}
