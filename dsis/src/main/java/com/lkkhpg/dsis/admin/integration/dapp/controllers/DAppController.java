/*
 * Copyright LKK Health Products Group Limited
 */

package com.lkkhpg.dsis.admin.integration.dapp.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lkkhpg.dsis.admin.integration.dapp.constant.IntegrationConstant;
import com.lkkhpg.dsis.admin.integration.dapp.dto.BatchOrdersSubmissionRequest;
import com.lkkhpg.dsis.admin.integration.dapp.dto.BatchOrdersSubmissionResponse;
import com.lkkhpg.dsis.admin.integration.dapp.dto.DistributorsVerificationRequest;
import com.lkkhpg.dsis.admin.integration.dapp.dto.GetAddressRequest;
import com.lkkhpg.dsis.admin.integration.dapp.dto.GetAddressResponse;
import com.lkkhpg.dsis.admin.integration.dapp.dto.GetCouponsRequest;
import com.lkkhpg.dsis.admin.integration.dapp.dto.GetCouponsResponse;
import com.lkkhpg.dsis.admin.integration.dapp.dto.GetDistributorRequest;
import com.lkkhpg.dsis.admin.integration.dapp.dto.GetDistributorResponse;
import com.lkkhpg.dsis.admin.integration.dapp.dto.GetDistributorsRequest;
import com.lkkhpg.dsis.admin.integration.dapp.dto.GetDistributorsResponse;
import com.lkkhpg.dsis.admin.integration.dapp.dto.GetOrderDetailRequest;
import com.lkkhpg.dsis.admin.integration.dapp.dto.GetOrderDetailsBase;
import com.lkkhpg.dsis.admin.integration.dapp.dto.GetOrderListRequest;
import com.lkkhpg.dsis.admin.integration.dapp.dto.GetOrderListResponse;
import com.lkkhpg.dsis.admin.integration.dapp.dto.GetProductCategoriesRequest;
import com.lkkhpg.dsis.admin.integration.dapp.dto.GetProductCategoriesResponse;
import com.lkkhpg.dsis.admin.integration.dapp.dto.GetProductListRequest;
import com.lkkhpg.dsis.admin.integration.dapp.dto.GetProductListResponse;
import com.lkkhpg.dsis.admin.integration.dapp.dto.GetVipPurchaseAmountRequest;
import com.lkkhpg.dsis.admin.integration.dapp.dto.GetVipPurchaseAmountResponse;
import com.lkkhpg.dsis.admin.integration.dapp.dto.GetVipPvRequest;
import com.lkkhpg.dsis.admin.integration.dapp.dto.GetVipPvResponse;
import com.lkkhpg.dsis.admin.integration.dapp.dto.OmkAcctBalanceRequest;
import com.lkkhpg.dsis.admin.integration.dapp.dto.OmkAcctBalanceResponse;
import com.lkkhpg.dsis.admin.integration.dapp.dto.OmkDealerDetallRequest;
import com.lkkhpg.dsis.admin.integration.dapp.dto.OmkDealerDetallResponse;
import com.lkkhpg.dsis.admin.integration.dapp.dto.OmkDownlinePerforRequest;
import com.lkkhpg.dsis.admin.integration.dapp.dto.OmkDownlinePerforResponse;
import com.lkkhpg.dsis.admin.integration.dapp.dto.OmkTeamBalanceRequest;
import com.lkkhpg.dsis.admin.integration.dapp.dto.OmkTeamBalanceResponse;
import com.lkkhpg.dsis.admin.integration.dapp.dto.OmkTeamStructureRequest;
import com.lkkhpg.dsis.admin.integration.dapp.dto.OmkTeamStructureResponse;
import com.lkkhpg.dsis.admin.integration.dapp.dto.OnlineRegistrationRequest;
import com.lkkhpg.dsis.admin.integration.dapp.dto.OrderCancelRequest;
import com.lkkhpg.dsis.admin.integration.dapp.dto.OrderCancelResponse;
import com.lkkhpg.dsis.admin.integration.dapp.dto.OrderPaymentUpdate;
import com.lkkhpg.dsis.admin.integration.dapp.dto.OrderPaymentUpdateResponse;
import com.lkkhpg.dsis.admin.integration.dapp.dto.OrderSubmission;
import com.lkkhpg.dsis.admin.integration.dapp.dto.SponsorVerificationRequest;
import com.lkkhpg.dsis.admin.integration.dapp.dto.SponsorVerificationResponse;
import com.lkkhpg.dsis.admin.integration.dapp.dto.UpdateDistributorRequest;
import com.lkkhpg.dsis.admin.integration.dapp.exception.IntegrationException;
import com.lkkhpg.dsis.admin.integration.dapp.service.IBatchOrdersSubmissionService;
import com.lkkhpg.dsis.admin.integration.dapp.service.IDistributorVerificationService;
import com.lkkhpg.dsis.admin.integration.dapp.service.IGetAddressService;
import com.lkkhpg.dsis.admin.integration.dapp.service.IGetCouponsService;
import com.lkkhpg.dsis.admin.integration.dapp.service.IGetDistributorsService;
import com.lkkhpg.dsis.admin.integration.dapp.service.IGetOrderDetailService;
import com.lkkhpg.dsis.admin.integration.dapp.service.IGetOrderListService;
import com.lkkhpg.dsis.admin.integration.dapp.service.IGetProductService;
import com.lkkhpg.dsis.admin.integration.dapp.service.IGetVipPurchaseAmtService;
import com.lkkhpg.dsis.admin.integration.dapp.service.IGetVipPvService;
import com.lkkhpg.dsis.admin.integration.dapp.service.IOmkAcctBalanceService;
import com.lkkhpg.dsis.admin.integration.dapp.service.IOmkDealerDetallService;
import com.lkkhpg.dsis.admin.integration.dapp.service.IOmkDownlinePerforService;
import com.lkkhpg.dsis.admin.integration.dapp.service.IOmkTeamBalanceService;
import com.lkkhpg.dsis.admin.integration.dapp.service.IOmkTeamStructureService;
import com.lkkhpg.dsis.admin.integration.dapp.service.IOnlineRegistrationService;
import com.lkkhpg.dsis.admin.integration.dapp.service.IOrderCancelService;
import com.lkkhpg.dsis.admin.integration.dapp.service.IOrderPaymentUpdateService;
import com.lkkhpg.dsis.admin.integration.dapp.service.IOrderSubmissionService;
import com.lkkhpg.dsis.admin.integration.dapp.service.ISponsorVerificationService;
import com.lkkhpg.dsis.admin.integration.dapp.service.IUpdateDistributorService;
import com.lkkhpg.dsis.common.exception.CommMemberException;
import com.lkkhpg.dsis.integration.dapp.adaptor.FeedJson;
import com.lkkhpg.dsis.integration.dapp.controllers.DAppBaseController;
import com.lkkhpg.dsis.integration.dapp.dto.DAppResponse;
import com.lkkhpg.dsis.integration.dapp.exception.DAppException;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.impl.RequestHelper;

/**
 * D-App接口Controller.
 * 
 * @author frank.li
 */
@Controller
@RequestMapping("/restful/dapp/api/" + IntegrationConstant.VERSION)
public class DAppController extends DAppBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IDistributorVerificationService appDistributorVerificationService;

    @Autowired
    private IGetDistributorsService getDistributorsService;

    @Autowired
    private IOnlineRegistrationService onlineRegistrationService;

    @Autowired
    private IOrderPaymentUpdateService orderPaymentUpdateService;

    @Autowired
    private IOrderSubmissionService orderSubmissionService;

    @Autowired
    private IGetProductService getProductServiceImpl;

    @Autowired
    private IBatchOrdersSubmissionService batchOrdersSubmissionService;

    @Autowired
    private IOmkAcctBalanceService omkAcctBalanceService;

    @Autowired
    private IOmkDealerDetallService omkDealerDetallService;

    @Autowired
    private IOrderCancelService orderCancelService;

    @Autowired
    private IGetAddressService getAddressService;

    @Autowired
    private IOmkTeamBalanceService omkTeamBalanceService;

    @Autowired
    private IUpdateDistributorService updateDistributorService;

    @Autowired
    private IGetOrderDetailService getOrderDetailService;

    @Autowired
    private IGetOrderListService getOrderListService;

    @Autowired
    private IOmkTeamStructureService omkTeamStructureService;

    @Autowired
    private IOmkDownlinePerforService omkDownlinePerforService;

    @Autowired
    private IGetCouponsService getCouponsService;

    @Autowired
    private IGetVipPvService getVipPvService;

    @Autowired
    private ISponsorVerificationService sponsorVerificationService;
    
    @Autowired
    private IGetVipPurchaseAmtService getVipPurchaseAmtService;

    /**
     * 获取业务员列表 Get Distributors.
     * 
     * @param request
     *            请求上下文
     * @param getDistributorsRequest
     *            响应DTO
     * @param pageNo
     *            页码
     * @param maxPerpage
     *            每页记录数
     * @return DApp 返回数据
     * @throws DAppException
     *             DApp接口异常
     */
    @RequestMapping(value = "/getDistributors", method = RequestMethod.GET)
    @ResponseBody
    public DAppResponse<GetDistributorsResponse> getDistributors(HttpServletRequest request,
            GetDistributorsRequest getDistributorsRequest,
            @RequestParam(defaultValue = IntegrationConstant.PAGE_NO) int pageNo,
            @RequestParam(defaultValue = IntegrationConstant.MAX_PERPAGE) int maxPerpage) throws DAppException {
        validate(getDistributorsRequest);
        GetDistributorsResponse distributors = getDistributorsService.getDistributors(getDistributorsRequest, pageNo,
                maxPerpage);

        return new DAppResponse<>(distributors);
    }

    /**
     * 获取业务员详细信息 Get Distributor.
     * 
     * @param request
     *            请求上下文
     * @param getDistributorRequest
     *            入参DTO
     * @return DApp 返回数据
     * @throws DAppException
     *             DApp接口异常
     */
    @RequestMapping(value = "/getDistributor", method = RequestMethod.GET)
    @ResponseBody
    public DAppResponse<GetDistributorResponse> getDistributor(HttpServletRequest request,
            GetDistributorRequest getDistributorRequest) throws DAppException {
        validate(getDistributorRequest);
        GetDistributorResponse distributor = getDistributorsService.getDistributor(getDistributorRequest);

        return new DAppResponse<>(distributor);
    }

    /**
     * 业务员登陆校验 Distributor verification.
     * 
     * @param request
     *            请求上下文
     * @param distributorsVerificationRequest
     *            登陆验证参数
     * @return DApp 返回数据
     * @throws DAppException
     *             DApp接口异常
     */
    @RequestMapping(value = "/verifyDistributor", method = RequestMethod.GET)
    @ResponseBody
    public DAppResponse<Map<String, Object>> verifyDistributor(
            @ModelAttribute DistributorsVerificationRequest distributorsVerificationRequest, HttpServletRequest request)
                    throws DAppException {
        validate(distributorsVerificationRequest);
        Map<String, Object> map = appDistributorVerificationService.loadVerification(distributorsVerificationRequest);
        DAppResponse<Map<String, Object>> mapResponse = new DAppResponse<Map<String, Object>>(map);
        if (map.get("result").toString().equals("-1")) {
            DAppResponse.Status status = new DAppResponse.Status();
            status.setValue("distributorNumber=" + distributorsVerificationRequest.getDistributorNumber() + "&market="
                    + distributorsVerificationRequest.getMarket() + "&password=******");
            status.setCode(IntegrationConstant.STATUS_CODE_MEMBER);
            status.setMessage(map.get("message").toString());
            mapResponse.setStatus(status);
        }

        return mapResponse;
    }

    /**
     * 业务员线上入会 Online Registration.
     * 
     * @param request
     *            请求上下文
     * @param onlineRegistrationRequest
     *            线上入会请求数据DTO
     * @return DApp 返回数据
     * @throws DAppException
     *             DApp接口异常
     * @throws CommMemberException
     *             会员统一异常
     */
    @RequestMapping(value = "/addDistributor", method = RequestMethod.POST)
    @ResponseBody
    public DAppResponse<Map<String, Object>> addDistributor(HttpServletRequest request,
            @FeedJson @RequestBody OnlineRegistrationRequest onlineRegistrationRequest)
                    throws DAppException, CommMemberException {
        Map<String, Object> addDistributor = onlineRegistrationService.addDistributor(onlineRegistrationRequest);

        return new DAppResponse<>(addDistributor);
    }

    /**
     * 业务员更新 Update Distributor.
     * 
     * @param request
     *            请求上下文
     * @param updateDistributorRequest
     *            更新业务员请求DTO
     * @return DApp 返回数据
     * @throws DAppException
     *             DApp接口异常
     * @throws CommMemberException
     *             会员统一异常
     */
    @RequestMapping(value = "/updateDistributor", method = RequestMethod.POST)
    @ResponseBody
    public DAppResponse<Map<String, Object>> updateDistributor(HttpServletRequest request,
            @FeedJson @RequestBody UpdateDistributorRequest updateDistributorRequest)
                    throws DAppException, CommMemberException {
        Map<String, Object> updateDistributor = updateDistributorService.updateDistributor(updateDistributorRequest);

        return new DAppResponse<>(updateDistributor);
    }

    /**
     * 产品类别查询 Get Product Categories.
     * 
     * @param request
     *            请求上下文
     * @param getProductCategoriesRequest
     *            请求参数
     * @return DApp 返回数据
     * @throws DAppException
     *             DApp接口异常
     */
    @RequestMapping(value = "/getProductCategories", method = RequestMethod.GET)
    @ResponseBody
    public DAppResponse<List<GetProductCategoriesResponse>> getProductCategories(HttpServletRequest request,
            GetProductCategoriesRequest getProductCategoriesRequest) throws DAppException {
        validate(getProductCategoriesRequest);
        List<GetProductCategoriesResponse> data = getProductServiceImpl
                .getProductCategories(getProductCategoriesRequest);

        return new DAppResponse<List<GetProductCategoriesResponse>>(data);
    }

    /**
     * 产品列表查询 Get Product List.
     * 
     * @param request
     *            请求上下文
     * @param getProductListRequest
     *            请求参数
     * @return DApp 返回数据
     * @throws DAppException
     *             DApp接口异常
     */
    @RequestMapping(value = "/getProducts", method = RequestMethod.GET)
    @ResponseBody
    public DAppResponse<List<GetProductListResponse>> getProducts(HttpServletRequest request,
            GetProductListRequest getProductListRequest) throws DAppException {
        validate(getProductListRequest);
        IRequest requestContext = RequestHelper.newEmptyRequest();
        List<GetProductListResponse> data = getProductServiceImpl.getProductList(requestContext, getProductListRequest);

        return new DAppResponse<List<GetProductListResponse>>(data);
    }

    /**
     * 订单提交 Order Submission.
     * 
     * @param request
     *            请求上下文
     * @param orderSubmission
     *            订单提交入参DTO.
     * @return DApp 返回数据
     * @throws DAppException
     *             DApp接口异常
     */
    @RequestMapping(value = "/addOrder", method = RequestMethod.POST)
    @ResponseBody
    public DAppResponse<Map<String, Object>> addOrder(HttpServletRequest request,
            @FeedJson @RequestBody OrderSubmission orderSubmission) throws DAppException {
        IRequest requestContext = RequestHelper.newEmptyRequest();
        requestContext.setAccountId(-1L);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("orderNumber", orderSubmissionService.orderSubmit(requestContext, orderSubmission));

        return new DAppResponse<>(map);
    }

    /**
     * 订单列表查询 Get Order List.
     * 
     * @param request
     *            请求上下文
     * @param getOrderListRequest
     *            订单列表查询DTO
     * @param pageNo
     *            页码
     * @param maxPerpage
     *            页行数
     * @return DApp 返回数据
     * @throws DAppException
     *             DApp接口异常
     */
    @RequestMapping(value = "/getOrders", method = RequestMethod.GET)
    @ResponseBody
    public DAppResponse<?> getOrders(HttpServletRequest request, GetOrderListRequest getOrderListRequest,
            @RequestParam(defaultValue = IntegrationConstant.PAGE_NO) Integer pageNo,
            @RequestParam(defaultValue = IntegrationConstant.MAX_PERPAGE) Integer maxPerpage) throws DAppException {
        validate(getOrderListRequest);

        List<GetOrderListResponse> orderList = getOrderListService.getOrderList(getOrderListRequest, pageNo,
                maxPerpage);

        return new DAppResponse<>(orderList);
    }

    /**
     * 获取订单详情 Get Order Detail.
     * 
     * @param request
     *            请求上下文
     * @param getOrderDetailRequest
     *            获取订单详情请求DTO
     * @return DApp 返回数据
     * @throws DAppException
     *             DApp接口异常
     */
    @RequestMapping(value = "/getOrder", method = RequestMethod.GET)
    @ResponseBody
    public DAppResponse<GetOrderDetailsBase> getOrder(HttpServletRequest request,
            GetOrderDetailRequest getOrderDetailRequest) throws DAppException {
        validate(getOrderDetailRequest);
        GetOrderDetailsBase salesOrder = getOrderDetailService.getOrderDetail(getOrderDetailRequest);

        return new DAppResponse<>(salesOrder);
    }

    /**
     * 获取优惠券 Get Coupons.
     * 
     * @param request
     *            请求上下文
     * @param getCouponsRequest
     *            优惠券请求信息
     * @return DApp 返回数据
     * @throws DAppException
     *             DApp接口异常
     */
    @RequestMapping(value = "/getCoupons", method = RequestMethod.GET)
    @ResponseBody
    public DAppResponse<List<GetCouponsResponse>> getCoupons(HttpServletRequest request,
            GetCouponsRequest getCouponsRequest) throws DAppException {
        validate(getCouponsRequest);
        List<GetCouponsResponse> coupons = getCouponsService.getCoupons(getCouponsRequest);

        return new DAppResponse<>(coupons);
    }

    /**
     * 订单作废 Order Cancel.
     * 
     * @param request
     *            请求上下文
     * @param orderCancelRequestList
     *            请求参数
     * @return DApp 返回数据
     * @throws DAppException
     *             DApp接口异常
     */
    @RequestMapping(value = "/cancelOrder", method = RequestMethod.POST)
    @ResponseBody
    public DAppResponse<List<OrderCancelResponse>> cancelOrder(HttpServletRequest request,
            @FeedJson @RequestBody List<OrderCancelRequest> orderCancelRequestList) throws DAppException {
        validate(orderCancelRequestList);
        List<OrderCancelResponse> orderCancelResponseList = orderCancelService.orderCancel(orderCancelRequestList);
        DAppResponse<List<OrderCancelResponse>> dAppResponse = new DAppResponse<List<OrderCancelResponse>>(
                orderCancelResponseList);
        for (OrderCancelResponse orderCancelResponse : orderCancelResponseList) {
            if (orderCancelResponse.getResult().equals("-1")) {
                DAppResponse.Status status = new DAppResponse.Status();
                status.setValue(null);
                status.setCode(IntegrationConstant.ERROR_30007);
                status.setMessage(IntegrationException.MSG_ERROR_ORDER_CANCEL_ERROR);
                dAppResponse.setStatus(status);
                break;
            }
        }

        return dAppResponse;
    }

    /**
     * 订单支付 Order Payment Update.
     * 
     * @param request
     *            请求上下文
     * @param orderPaymentUpdates
     *            订单支付list
     * @return DApp 返回数据
     * @throws DAppException
     *             DApp接口异常
     */
    @RequestMapping(value = "/settleOrder", method = RequestMethod.POST)
    @ResponseBody
    public DAppResponse<List<OrderPaymentUpdateResponse>> settleOrder(HttpServletRequest request,
            @FeedJson @RequestBody List<OrderPaymentUpdate> orderPaymentUpdates) throws DAppException {
        List<OrderPaymentUpdateResponse> responses = new ArrayList<OrderPaymentUpdateResponse>();
        for (OrderPaymentUpdate orderPaymentUpdate : orderPaymentUpdates) {
            try {
                OrderPaymentUpdateResponse response = orderPaymentUpdateService.updatePaymentStatus(orderPaymentUpdate);
                responses.add(response);

                // 捕获运行时异常
            } catch (Exception e) {
                OrderPaymentUpdateResponse errorResponse = new OrderPaymentUpdateResponse();
                errorResponse.setOrderNumber(orderPaymentUpdate.getOrderNumber());
                errorResponse.setResult(-1L);
                errorResponse.setDescription(e.getMessage());
                responses.add(errorResponse);
            }
        }

        return new DAppResponse<List<OrderPaymentUpdateResponse>>(responses);
    }

    /**
     * 送货地址同步 Get Address.
     * 
     * @param request
     *            请求上下文
     * @param getAddressRequest
     *            请求参数
     * @return DApp 返回数据
     * @throws DAppException
     *             DApp接口异常
     */
    @RequestMapping(value = "/getAddress", method = RequestMethod.GET)
    @ResponseBody
    public DAppResponse<List<GetAddressResponse>> getAddress(HttpServletRequest request,
            GetAddressRequest getAddressRequest) throws DAppException {
        validate(getAddressRequest);
        List<GetAddressResponse> getAddressResponse = getAddressService.getAddress(getAddressRequest);

        return new DAppResponse<>(getAddressResponse);
    }

    /**
     * 校验优惠券 Verify Coupons.
     * 
     * @param request
     *            请求上下文
     * @return DApp 返回数据
     * @throws DAppException
     *             DApp接口异常
     */
    @RequestMapping(value = "/verifyCoupons", method = RequestMethod.POST)
    @ResponseBody
    public DAppResponse<?> verifyCoupons(HttpServletRequest request) throws DAppException {
        // TODO 添加请求参数， DTO类型参数设置@NotNull注解则自动进行必输校验，非DTO类型参数需要校验必输
        // TODO 调用各个接口的Servic
        // TODO 设置DAppResponse返回类型和传值
        return new DAppResponse<>(null);
    }

    /**
     * 锁定优惠券 Lock Coupons.
     * 
     * @param request
     *            请求上下文
     * @return DApp 返回数据
     * @throws DAppException
     *             DApp接口异常
     */
    @RequestMapping(value = "/lockCoupons", method = RequestMethod.POST)
    @ResponseBody
    public DAppResponse<?> lockCoupons(HttpServletRequest request) throws DAppException {
        // TODO 添加请求参数， DTO类型参数设置@NotNull注解则自动进行必输校验，非DTO类型参数需要校验必输
        // TODO 调用各个接口的Servic
        // TODO 设置DAppResponse返回类型和传值
        return new DAppResponse<>(null);
    }

    /**
     * 释放优惠券 Release Coupons.
     * 
     * @param request
     *            请求上下文
     * @return DApp 返回数据
     * @throws DAppException
     *             DApp接口异常
     */
    @RequestMapping(value = "/releaseCoupons", method = RequestMethod.POST)
    @ResponseBody
    public DAppResponse<?> releaseCoupons(HttpServletRequest request) throws DAppException {
        // TODO 添加请求参数， DTO类型参数设置@NotNull注解则自动进行必输校验，非DTO类型参数需要校验必输
        // TODO 调用各个接口的Servic
        // TODO 设置DAppResponse返回类型和传值
        return new DAppResponse<>(null);
    }

    /**
     * 批量同步订单 Batch Orders Submission.
     * 
     * @param request
     *            请求上下文
     * @param batchOrdersSubmissionRequest
     *            批量同步订单请求DTO
     * @return DApp 返回数据
     * @throws DAppException
     *             DApp接口异常
     */
    @RequestMapping(value = "/addOrders", method = RequestMethod.POST)
    @ResponseBody
    public DAppResponse<Map<String, List<BatchOrdersSubmissionResponse>>> addOrders(HttpServletRequest request,
            @FeedJson @RequestBody BatchOrdersSubmissionRequest batchOrdersSubmissionRequest) throws DAppException {
        IRequest requestContext = RequestHelper.newEmptyRequest();
        Map<String, List<BatchOrdersSubmissionResponse>> map = new HashMap<>();
        // 根据源编号，决定事务处理方式
        if (StringUtils.isEmpty(batchOrdersSubmissionRequest.getSourceOrderNo())) {
            map.put("results",
                    batchOrdersSubmissionService.batchOrdersSubmission(requestContext, batchOrdersSubmissionRequest));
        } else {
            map.put("results", batchOrdersSubmissionService.batchOrdersSubmissionWithOneTransaction(requestContext,
                    batchOrdersSubmissionRequest));
        }

        return new DAppResponse<>(map);
    }

    /**
     * 会员账号余额查询 ACCOUNT BALANCE.
     * 
     * @param request
     *            请求上下文
     * @param id
     *            业务员Id
     * @param omkAcctBalanceRequest
     *            会员余额请求DTO
     * @return DApp 返回数据
     * @throws DAppException
     *             DApp接口异常
     */
    @RequestMapping(value = "/distributors/{id}/balance", method = RequestMethod.GET)
    @ResponseBody
    public DAppResponse<OmkAcctBalanceResponse> acctBalance(HttpServletRequest request, @PathVariable String id,
            OmkAcctBalanceRequest omkAcctBalanceRequest) throws DAppException {
        if (logger.isDebugEnabled()) {
            logger.debug("id: {}", id);
        }

        omkAcctBalanceRequest.setDistributorId(id);
        validate(omkAcctBalanceRequest);
        OmkAcctBalanceResponse acctBalanceResponse = omkAcctBalanceService.getAcctBalance(omkAcctBalanceRequest);

        return new DAppResponse<>(acctBalanceResponse);
    }

    /**
     * 会员账号余额月查询 ACCOUNT BALANCE.
     * 
     * @param request
     *            请求上下文
     * @param distributorId
     *            业务员Id
     * @param month
     *            月份
     * @param omkAcctBalanceRequest
     *            会员余额请求DTO
     * @return DApp 返回数据
     * @throws DAppException
     *             DApp接口异常
     */
    @RequestMapping(value = "/distributors/{distributorId}/months/{month}/balance", method = RequestMethod.GET)
    @ResponseBody
    public DAppResponse<OmkAcctBalanceResponse> acctBalanceMonth(HttpServletRequest request,
            @PathVariable String distributorId, @PathVariable String month, OmkAcctBalanceRequest omkAcctBalanceRequest)
                    throws DAppException {
        if (logger.isDebugEnabled()) {
            logger.debug("distributorId: {}", distributorId);
            logger.debug("month: {}", month);
        }

        omkAcctBalanceRequest.setDistributorId(distributorId);
        omkAcctBalanceRequest.setMonth(month);
        validate(omkAcctBalanceRequest);
        OmkAcctBalanceResponse acctBalanceResponse = omkAcctBalanceService.getAcctBalance(omkAcctBalanceRequest);

        return new DAppResponse<>(acctBalanceResponse);
    }

    /**
     * 獲取會員詳細信息.
     * 
     * @param request
     *            请求上下文
     * @param id
     *            会员Id
     * @param omkDealerDetallRequest
     *            請求的數據集
     * @return DApp 返回数据
     * @throws DAppException
     *             DApp接口异常
     */
    @RequestMapping(value = "/distributors/{id}", method = RequestMethod.GET)
    @ResponseBody
    public DAppResponse<OmkDealerDetallResponse> dealerDetail(HttpServletRequest request, @PathVariable String id,
            OmkDealerDetallRequest omkDealerDetallRequest) throws DAppException {
        if (logger.isDebugEnabled()) {
            logger.debug("id: {}", id);
        }

        omkDealerDetallRequest.setDistributorId(id);
        validate(omkDealerDetallRequest);
        OmkDealerDetallResponse dealerDetallResponse = omkDealerDetallService.getDealerDetall(omkDealerDetallRequest);

        return new DAppResponse<>(dealerDetallResponse);
    }

    /**
     * 獲取团队余额信息.
     * 
     * @param request
     *            请求上下文
     * @param id
     *            会员Id
     * @param omkTeamBalanceRequest
     *            请求的数据集 会员卡号
     * @return DApp 返回数据
     * @throws DAppException
     *             DApp接口异常
     */
    @RequestMapping(value = "/distributors/{id}/team/balance", method = RequestMethod.GET)
    @ResponseBody
    public DAppResponse<OmkTeamBalanceResponse> teamBalance(HttpServletRequest request, @PathVariable String id,
            OmkTeamBalanceRequest omkTeamBalanceRequest) throws DAppException {
        if (logger.isDebugEnabled()) {
            logger.debug("id: {}", id);
        }

        omkTeamBalanceRequest.setDistributorId(id);
        validate(omkTeamBalanceRequest);
        OmkTeamBalanceResponse teamBalanceResponse = omkTeamBalanceService.getTeamBalance(omkTeamBalanceRequest);

        return new DAppResponse<>(teamBalanceResponse);
    }

    /**
     * 獲取团队余额信息.
     * 
     * @param request
     *            请求上下文
     * @param id
     *            会员Id
     * @param month
     *            计分月期间
     * @param omkTeamBalanceRequest
     *            请求的数据集 会员卡号
     * @return DApp 返回数据
     * @throws DAppException
     *             DApp接口异常
     */
    @RequestMapping(value = "/distributors/{id}/team/balance/months/{month}", method = RequestMethod.GET)
    @ResponseBody
    public DAppResponse<OmkTeamBalanceResponse> teamBalanceMonths(HttpServletRequest request, @PathVariable String id,
            @PathVariable String month, OmkTeamBalanceRequest omkTeamBalanceRequest) throws DAppException {
        if (logger.isDebugEnabled()) {
            logger.debug("id: {}", id);
            logger.debug("month: {}", month);
        }

        omkTeamBalanceRequest.setDistributorId(id);
        omkTeamBalanceRequest.setPeriod(month);
        validate(omkTeamBalanceRequest);
        OmkTeamBalanceResponse teamBalanceResponse = omkTeamBalanceService.getTeamBalance(omkTeamBalanceRequest);

        return new DAppResponse<>(teamBalanceResponse);
    }

    /**
     * 根据月份和ID获取Team Structure.
     * 
     * @param request
     *            请求上下文
     * @param id
     *            会员Id
     * @param month
     *            计分月期间
     * @param omkTeamStructureRequest
     *            请求的数据集 会员卡号
     * @return DApp 返回数据
     * @throws DAppException
     *             DApp接口异常
     */
    @RequestMapping(value = "/distributors/{id}/team/months/{month}", method = RequestMethod.GET)
    @ResponseBody
    public OmkTeamStructureResponse getTeamStructureByIdAndMonth(HttpServletRequest request,
            @PathVariable(value = "id") String id, @PathVariable(value = "month") String month,
            OmkTeamStructureRequest omkTeamStructureRequest) throws DAppException {
        if (logger.isDebugEnabled()) {
            logger.debug("id: {}", id);
            logger.debug("month: {}", month);
        }

        omkTeamStructureRequest.setDistributorId(id);
        if ("{month}".equals(month)) {
            omkTeamStructureRequest.setPeriod(null);
        } else {
            omkTeamStructureRequest.setPeriod(month);
        }
        validate(omkTeamStructureRequest);
        OmkTeamStructureResponse teamStructure = omkTeamStructureService.getTeamStructure(omkTeamStructureRequest);

        return teamStructure;
    }

    /**
     * 根据ID获取Team Structure.
     * 
     * @param request
     *            请求上下文
     * @param id
     *            会员Id
     * @param omkTeamStructureRequest
     *            请求的数据集 会员卡号
     * @return DApp 返回数据
     * @throws DAppException
     *             DApp接口异常
     */
    @RequestMapping(value = "/distributors/{id}/team", method = RequestMethod.GET)
    @ResponseBody
    public OmkTeamStructureResponse getTeamStructureById(HttpServletRequest request,
            @PathVariable(value = "id") String id, OmkTeamStructureRequest omkTeamStructureRequest)
                    throws DAppException {
        if (logger.isDebugEnabled()) {
            logger.debug("id: {}", id);
        }

        omkTeamStructureRequest.setDistributorId(id);
        validate(omkTeamStructureRequest);
        OmkTeamStructureResponse teamStructure = omkTeamStructureService.getTeamStructure(omkTeamStructureRequest);

        return teamStructure;
    }

    /**
     * 获取下线绩效列表.
     * 
     * @param request
     *            请求上下文
     * @param id
     *            会员Id
     * @param omkDownlinePerforRequest
     *            请求参数 获取下线绩效列表请求DTO.
     * @return DApp 返回数据
     * @throws DAppException
     *             DApp接口异常
     */
    @RequestMapping(value = "/distributors/{id}/downline", method = RequestMethod.GET)
    @ResponseBody
    public DAppResponse<List<OmkDownlinePerforResponse>> omkDownlinePerfor(HttpServletRequest request,
            @PathVariable(value = "id") String id, OmkDownlinePerforRequest omkDownlinePerforRequest)
                    throws DAppException {
        if (logger.isDebugEnabled()) {
            logger.debug("id: {}", id);
        }

        omkDownlinePerforRequest.setDistributorId(id);

        validate(omkDownlinePerforRequest);
        List<OmkDownlinePerforResponse> omkDownlinePerfor = omkDownlinePerforService
                .getOmkDownlinePerfor(omkDownlinePerforRequest);

        return new DAppResponse<>(omkDownlinePerfor);
    }

    /**
     * 获取VIP会员pv Get VIP PV.
     * 
     * @param request
     *            请求上下文
     * @param getVipPvRequest
     *            入参DTO
     * @return DApp 返回数据
     * @throws DAppException
     *             DApp接口异常
     */
    @RequestMapping(value = "/getVipPV", method = RequestMethod.GET)
    @ResponseBody
    public DAppResponse<GetVipPvResponse> getOrder(HttpServletRequest request, GetVipPvRequest getVipPvRequest)
            throws DAppException {
        validate(getVipPvRequest);
        GetVipPvResponse getVipPvResponse = getVipPvService.getVipPv(getVipPvRequest);

        return new DAppResponse<>(getVipPvResponse);
    }

    /**
     * 推荐人鉴别.
     * 
     * @param request
     *            请求上下文
     * @param sponsorVerificationRequest
     *            推荐人鉴别请求数据
     * @return 推荐人鉴别DAPP接口响应数据
     * @throws DAppException
     *             Dapp异常
     * @throws com.lkkhpg.dsis.admin.integration.gds.exception.
     *             IntegrationException gds接口异常
     */
    @RequestMapping(value = "/verifySponsor", method = RequestMethod.GET)
    @ResponseBody
    public DAppResponse<SponsorVerificationResponse> verifySponsor(HttpServletRequest request,
            SponsorVerificationRequest sponsorVerificationRequest)
                    throws DAppException, com.lkkhpg.dsis.admin.integration.gds.exception.IntegrationException {
        validate(sponsorVerificationRequest);
        SponsorVerificationResponse sponsorVerfication = sponsorVerificationService
                .sponsorVerfication(sponsorVerificationRequest);
        return new DAppResponse<>(sponsorVerfication);
    }
    
    /**
     * 获取VIP会员消费总额.
     * 
     * @param request
     *            请求上下文
     * @param getVipPvRequest
     *            入参DTO
     * @return DApp 返回数据
     * @throws DAppException
     *             DApp接口异常
     */
    @RequestMapping(value = "/getVipPurchaseAmt", method = RequestMethod.GET)
    @ResponseBody
    public DAppResponse<GetVipPurchaseAmountResponse> getVipPurchaseAmt(HttpServletRequest request, GetVipPurchaseAmountRequest getVipPurchaseAmountRequest)
            throws DAppException {
        validate(getVipPurchaseAmountRequest);
        GetVipPurchaseAmountResponse getVipPurchaseAmountResponse = getVipPurchaseAmtService.getVipPurchaseAmt(getVipPurchaseAmountRequest);

        return new DAppResponse<>(getVipPurchaseAmountResponse);
    }
}
