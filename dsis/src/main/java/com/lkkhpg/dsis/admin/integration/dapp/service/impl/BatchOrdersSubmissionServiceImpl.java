/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.integration.dapp.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lkkhpg.dsis.admin.integration.dapp.constant.IntegrationConstant;
import com.lkkhpg.dsis.admin.integration.dapp.dto.BatchOrdersSubmissionRequest;
import com.lkkhpg.dsis.admin.integration.dapp.dto.BatchOrdersSubmissionResponse;
import com.lkkhpg.dsis.admin.integration.dapp.dto.OrderSubmission;
import com.lkkhpg.dsis.admin.integration.dapp.service.IBatchOrdersSubmissionService;
import com.lkkhpg.dsis.admin.integration.dapp.service.IDAppUtilService;
import com.lkkhpg.dsis.admin.integration.dapp.service.IOrderSubmissionService;
import com.lkkhpg.dsis.integration.dapp.exception.DAppException;
import com.lkkhpg.dsis.platform.core.IRequest;

/**
 * 批量提交订单接口实现类.
 * 
 * @author shenqb
 *
 */
@Service
public class BatchOrdersSubmissionServiceImpl implements IBatchOrdersSubmissionService {

    private Logger logger = LoggerFactory.getLogger(BatchOrdersSubmissionServiceImpl.class);

    @Autowired
    private IOrderSubmissionService orderSubmissionService;
    
    @Autowired
    private IDAppUtilService dAppUtilService;

    @Override
    public List<BatchOrdersSubmissionResponse> batchOrdersSubmission(IRequest request,
            BatchOrdersSubmissionRequest batchOrdersSubmissionRequest) throws DAppException {
        request.setLocale(IntegrationConstant.DEFAULT_LANG);
        request.setAccountId(dAppUtilService.getDappAccountId());
        List<BatchOrdersSubmissionResponse> responseList = new ArrayList<BatchOrdersSubmissionResponse>();
        if (logger.isInfoEnabled()) {
            logger.info("dapp batchOrdersSubmission start:");
        }
        for (OrderSubmission order : batchOrdersSubmissionRequest.getOrders()) {
            BatchOrdersSubmissionResponse response = new BatchOrdersSubmissionResponse();
            try {
                String orderNumber = orderSubmissionService.orderSubmit(request, order);
                response.setOrderNo(orderNumber);
                response.setAppNo(order.getAppNo());
                response.setResult(1);
                response.setDescription(null);
            } catch (Exception e) {
                if (logger.isErrorEnabled()) {
                    logger.error("申请号" + order.getAppNo() + "的订单同步失败：" + e.getMessage());
                }
                response.setAppNo(order.getAppNo());
                response.setResult(-1);
                response.setDescription(String.valueOf(IntegrationConstant.ERROR_30009) + ":" + "申请号" + order.getAppNo()
                        + "的订单同步失败：" + e.getMessage());
            }
            responseList.add(response);
        }
        return responseList;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<BatchOrdersSubmissionResponse> batchOrdersSubmissionWithOneTransaction(IRequest request,
            BatchOrdersSubmissionRequest batchOrdersSubmissionRequest) throws DAppException {
        request.setLocale(IntegrationConstant.DEFAULT_LANG);
        request.setAccountId(dAppUtilService.getDappAccountId());
        List<BatchOrdersSubmissionResponse> responseList = new ArrayList<BatchOrdersSubmissionResponse>();
        if (logger.isInfoEnabled()) {
            logger.info("dapp batchOrdersSubmission start:");
        }
        for (OrderSubmission order : batchOrdersSubmissionRequest.getOrders()) {
            BatchOrdersSubmissionResponse response = new BatchOrdersSubmissionResponse();
            order.setSourceOrderNo(batchOrdersSubmissionRequest.getSourceOrderNo());
            try {
                String orderNumber = orderSubmissionService.orderSubmit(request, order);
                response.setOrderNo(orderNumber);
                response.setAppNo(order.getAppNo());
                response.setResult(1);
                response.setDescription(null);
            } catch (Exception e) {
                if (logger.isErrorEnabled()) {
                    logger.error("申请号" + order.getAppNo() + "的订单同步失败：" + e.getMessage());
                }
                response.setAppNo(order.getAppNo());
                response.setResult(-1);
                response.setDescription(String.valueOf(IntegrationConstant.ERROR_30009) + ":" + "申请号" + order.getAppNo()
                        + "的订单同步失败：" + e.getMessage());
                throw new DAppException(IntegrationConstant.CALL_ORDER_API_FAILED, IntegrationConstant.ERROR_30002,
                        "appNo = " + order.getAppNo() + ":" + e.getMessage());
            }
            responseList.add(response);
        }
        return responseList;
    }

}
