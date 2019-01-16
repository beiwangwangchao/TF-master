/*
 *
 */
package com.lkkhpg.dsis.admin.inventory.controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.lkkhpg.dsis.common.inventory.dto.Storage;
import com.lkkhpg.dsis.integration.payment.dto.PayRefundRequest;
import com.lkkhpg.dsis.integration.payment.mapper.PayRefundRequestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.lkkhpg.dsis.admin.inventory.exception.InventoryException;
import com.lkkhpg.dsis.admin.inventory.service.IStockTrxService;
import com.lkkhpg.dsis.admin.product.product.service.IInvItemPropertyService;
import com.lkkhpg.dsis.common.constant.InventoryConstants;
import com.lkkhpg.dsis.common.inventory.dto.InvTransaction;
import com.lkkhpg.dsis.common.inventory.dto.StockTrx;
import com.lkkhpg.dsis.common.inventory.dto.StockTrxDetail;
import com.lkkhpg.dsis.common.inventory.mapper.StockTrxDetailMapper;
import com.lkkhpg.dsis.common.product.dto.InvItemProperty;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.ResponseData;

/**
 * 出入库Controller.
 *
 * @author mclin
 */
@Controller
public class StockTrxController extends BaseController {

    @Autowired
    private IStockTrxService stockTrxService;
    @Autowired
    private IInvItemPropertyService invItemPropertyService;
    @Autowired
    private StockTrxDetailMapper stockTrxDetailMapper;
    @Autowired
    private PayRefundRequestMapper payRefundRequestMapper;

    /**
     * 根据页面输入字段查询出入库记录.
     *
     * @param stockTrx
     *            出入库Dto
     * @param page
     *            页
     * @param pagesize
     *            分页大小
     * @param request
     *            请求上下文
     * @return responseData 响应数据
     */
    @RequestMapping(value = "/im/stocktrx/query")
    @ResponseBody
    public ResponseData queryStockTrxs(StockTrx stockTrx, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                       @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        if (stockTrx.getOrganizationId() == null) {
            return new ResponseData();
        }
        String status = stockTrx.getStatus();
        if (StringUtils.isNotEmpty(status)) {
            List<String> list = new ArrayList<>();
            String[] selStatus = status.split(";");
            for (int i = 0; i < selStatus.length; i++) {
                list.add(i, selStatus[i]);
            }
            stockTrx.setSelStatus(list);
        }
        return new ResponseData(stockTrxService.queryStockTrxs(requestContext, stockTrx, page, pagesize));
    }

    /**
     * 出入库报表查询
     * @param requestContext
     * @param page
     * @param pageSize
     * @param storage
     * @return
     */
    @RequestMapping(value = "/im/stocktrx/storage")
    @ResponseBody
    public ResponseData queryStorage(HttpServletRequest requestContext, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                      @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, Storage storage) {
        IRequest request = createRequestContext(requestContext);

        return new ResponseData(stockTrxService.queryStorage(request, storage, page, pageSize));
    }



    /**
     * 批量删除页面选中的出入库记录.
     *
     * @param request
     *            请求上下文
     * @param stockTrxs
     *            出入库List
     * @return responseData 响应数据
     * @throws InventoryException
     *             库存统一异常
     */
    @RequestMapping(value = "/im/stocktrx/delete", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData deleteStockTrxs(@RequestBody List<StockTrx> stockTrxs, HttpServletRequest request)
            throws InventoryException {
        IRequest requestContext = createRequestContext(request);
        stockTrxService.batchDelete(requestContext, stockTrxs);
        return new ResponseData();
    }

    /**
     * 创建出入库单据.
     *
     * @param request
     *            请求上下文
     * @param stockTrxs
     *            出入库List
     * @param result
     *            数据绑定结果
     * @return responseData 响应数据
     * @throws InventoryException
     *             库存统一异常
     */
    @RequestMapping(value = "/im/stocktrx/create", method = RequestMethod.POST)
    public ResponseData createStockTrxs(HttpServletRequest request, @RequestBody List<StockTrx> stockTrxs,
                                        BindingResult result) throws InventoryException {
        getValidator().validate(stockTrxs, result);
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }

        HttpSession session = request.getSession();
        IRequest requestContext = createRequestContext(request);
        requestContext.setAttribute("createdBy", Long.parseLong(String.valueOf(session.getAttribute("userId"))));

        StockTrx stockTrx = stockTrxs.get(0);

        requestContext.setAttribute("creationDate", stockTrx.getCreationDate());


        return new ResponseData(stockTrxService.createStockTrx(requestContext, stockTrx));
    }

    /**
     * 删除出入库事务明细行.
     *
     * @param request
     *            请求上下文
     * @param stockTrxDetails
     *            出入库明细List
     * @return responseData 响应数据
     * @throws InventoryException
     *             库存统一异常
     */
    @RequestMapping(value = "/im/stocktrxdetails/delete", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData deleteStockTrxsDetails(HttpServletRequest request,
                                               @RequestBody List<StockTrxDetail> stockTrxDetails) throws InventoryException {
        IRequest requestContext = createRequestContext(request);
        stockTrxService.batchDeleteDetails(requestContext, stockTrxDetails);
        return new ResponseData();
    }

    /**
     * 删除出入库事务单据.
     *
     * @param request
     *            请求上下文
     * @param stockTrxs
     *            出入库事务List
     * @return responseData 响应数据
     * @throws InventoryException
     *             库存统一异常
     */
    @RequestMapping(value = "/im/stocktrx/allDelete", method = RequestMethod.POST)
    public ResponseData deleteAll(HttpServletRequest request, @RequestBody List<StockTrx> stockTrxs)
            throws InventoryException {
        IRequest requestContext = createRequestContext(request);
        if (stockTrxs == null || stockTrxs.isEmpty() || stockTrxs.get(0).getTrxNumber() == null) {
            return new ResponseData();
        }
        stockTrxService.deleteAll(requestContext, stockTrxs.get(0));
        return new ResponseData();
    }

    /**
     * 保存并提交出入库事务.
     *
     * @param request
     *            请求上下文
     * @param stockTrxs
     *            出入库List
     * @param result
     *            数据绑定结果
     * @return responseData 响应数据
     * @throws InventoryException
     *             库存统一异常
     */
    @RequestMapping(value = "/im/stocktrx/saveSubmit", method = RequestMethod.POST)
    public ResponseData saveAndSubmitStockTrxs(HttpServletRequest request, @RequestBody List<StockTrx> stockTrxs,
                                               BindingResult result) throws InventoryException {

        getValidator().validate(stockTrxs, result);
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }

        HttpSession session = request.getSession();
        IRequest requestContext = createRequestContext(request);
        requestContext.setAttribute("createdBy", Long.parseLong(String.valueOf(session.getAttribute("userId"))));

        StockTrx stockTrx = stockTrxs.get(0);
        /* 保存出入库 */

        // 库存组织ID
        Long orgId = stockTrx.getOrganizationId();
        // 创建人ID
        Long createdBy = Long.parseLong(request.getParameter("userId"));
        // 事务处理日期
        Date trxDate = stockTrx.getTrxDate();
        // 事务处理
        String trxType = stockTrx.getTrxType();
        // 头备注
        String remark = stockTrx.getRemark();
        //退货单号
        String outRefundNo=null;
        /*退货因为lov的原因 只能放交易编号  现根据交易编号找到对应的退货单号
        * updated by 15750 at 2018/02/09*/
        String transactionId=stockTrx.getTransactionId();
        if(transactionId !=null) {
            PayRefundRequest payRefundRequest = new PayRefundRequest();
            payRefundRequest.setTransactionId(transactionId);
            List<PayRefundRequest> payRefundRequests = payRefundRequestMapper.queryPayRefundLov(payRefundRequest);
            outRefundNo =payRefundRequests.get(0).getOutRefundNo();
        }
        // 是否为出库
        boolean flag = InventoryConstants.STOCKIO_OUT_CODE.equals(trxType);
        //是否为调整(-)
        String stadd="STADD";
        boolean flag1 = stadd.equals(trxType);
        //是否为采购出库
        String pstot="PSTOT";
        boolean flag2=pstot.equals(trxType);
        // 明细行
        List<StockTrxDetail> stockTrxDetails = stockTrx.getStockTrxDetails();
        // 提交出入库事务
        List<InvTransaction> invTrxs = new ArrayList<>();
        // 构造出入库事务List
        for (StockTrxDetail detail : stockTrxDetails) {
            InvTransaction trx = new InvTransaction();
            // 库存组织ID
            trx.setOrganizationId(orgId);
            //退货单号
            trx.setOutRefundNo(outRefundNo);
            // 子字库ID,TODO:现在未涉及到，后边迭代修改
            // trx.setSubinventoryId(detail.getSubinventoryId());
            trx.setSubinventoryId(1L);
            // 货位ID,TODO:现在未涉及到，后边迭代修改
            // trx.setLocatorId(detail.getLocatorId());
            trx.setLocatorId(1L);
            // 商品ID
            trx.setItemId(detail.getItemId());
            // 批次
            if (StringUtils.isEmpty(detail.getLotNumber())) {
                trx.setLotNumber(null);
            } else {
                trx.setLotNumber(detail.getLotNumber());
            }
            // 创建人ID
            trx.setCreatedBy(createdBy);
            // 事务处理日期
            trx.setTrxDate(trxDate);
            // 事务处理数量
             /*如果为出库  调整(-) 采购出库 数量为负*/
            if (flag) {
                trx.setTrxQty(detail.getQuantity().negate());
            } else if(flag1){
                trx.setTrxQty(detail.getQuantity().negate());
            }else if(flag2){
                trx.setTrxQty(detail.getQuantity().negate());
            } else {
                trx.setTrxQty(detail.getQuantity());
            }
            // 事务处理类型
            trx.setTrxType(trxType);
            // 事务处理原因
            trx.setTrxReason(stockTrx.getOperReasonCode());
            // 事务处理来源类型
            trx.setTrxSourceType(InventoryConstants.STOCKIO_DETAIL_TABLE_NAME);
            // 事务处理来源键值
            trx.setTrxSourceKey(InventoryConstants.STOCKIO_DETAIL_TABLE_KEY);
            // 到期日
            trx.setExpiryDate(detail.getExpiryDate());
            if (StringUtils.isEmpty(remark) && StringUtils.isEmpty(detail.getRemark())) {
                trx.setRemark(null);
            } else {
                trx.setRemark((remark == null ? "" : remark + ";") + detail.getRemark());
            }
            invTrxs.add(trx);
        }
        return new ResponseData(stockTrxService.submitTransaction(requestContext, stockTrx, invTrxs, true));
    }

    /**
     * 提交出入库事务.
     *
     * @param request
     *            请求上下文
     * @param stockTrxs
     *            出入库List
     * @param result
     *            校验结果
     * @return responseData 响应数据
     * @throws InventoryException
     *             库存统一异常
     */
    @RequestMapping(value = "/im/stocktrx/submit", method = RequestMethod.POST)
    public ResponseData submitStockTrxs(HttpServletRequest request, @RequestBody List<StockTrx> stockTrxs,
                                        BindingResult result) throws InventoryException {

        IRequest requestContext = createRequestContext(request);
        HttpSession session = request.getSession();
        requestContext.setAttribute("createdBy", Long.parseLong(String.valueOf(session.getAttribute("userId"))));

        StockTrx stockTrx = stockTrxs.get(0);
        /* 保存出入库 */

        // 单据编号
        String trxNumber = stockTrx.getTrxNumber();
        // 库存组织ID
        Long orgId = stockTrx.getOrganizationId();
        // 创建人ID
        Long createdBy = Long.parseLong(request.getParameter("userId"));
        // 事务处理日期
        Date trxDate = stockTrx.getTrxDate();
        // 事务处理
        String trxType = stockTrx.getTrxType();
        //退货单号
        String outRefundNo=stockTrx.getOutRefundNo();
        // 是否为出库
        boolean flag = InventoryConstants.STOCKIO_OUT_CODE.equals(trxType);
        //是否为调整(-)
        String stadd="STADD";
        boolean flag1 = stadd.equals(trxType);
        //是否为采购出库
        String pstot="PSTOT";
        boolean flag2=pstot.equals(trxType);
        // 头备注
        String remark = stockTrx.getRemark();
        // 明细行
        List<StockTrxDetail> stockTrxDetails = stockTrx.getStockTrxDetails();

        // 提交出入库事务
        List<InvTransaction> invTrxs = new ArrayList<>();
        // 构造出入库事务List
        for (StockTrxDetail detail : stockTrxDetails) {
            InvTransaction trx = new InvTransaction();
            trx.setTrxSourceReference(trxNumber);
            // 库存组织ID
            trx.setOrganizationId(orgId);
            //退货单号
            trx.setOutRefundNo(outRefundNo);
            // 字库ID
            trx.setSubinventoryId(detail.getSubinventoryId());
            // 货位ID
            trx.setLocatorId(detail.getLocatorId());
            // 商品ID
            trx.setItemId(detail.getItemId());
            // 批次
            if (StringUtils.isEmpty(detail.getLotNumber())) {
                trx.setLotNumber(null);
            } else {
                trx.setLotNumber(detail.getLotNumber());
            }
            // 创建人ID
            trx.setCreatedBy(createdBy);
            // 事务处理日期
            trx.setTrxDate(trxDate);
            // 事务处理数量
            /*if (flag) {
                trx.setTrxQty(detail.getQuantity().negate());
            } else {
                trx.setTrxQty(detail.getQuantity());
            }*/
            /*如果为出库  调整(-) 采购出库 数量为负 */
            if (flag) {
                trx.setTrxQty(detail.getQuantity().negate());
            } else if(flag1){
                trx.setTrxQty(detail.getQuantity().negate());
            }else if(flag2){
                trx.setTrxQty(detail.getQuantity().negate());
            } else {
                trx.setTrxQty(detail.getQuantity());
            }
            // 事务处理类型
            trx.setTrxType(trxType);
            // 事务处理原因
            trx.setTrxReason(stockTrx.getOperReasonCode());
            // 事务处理来源类型
            trx.setTrxSourceType(InventoryConstants.STOCKIO_DETAIL_TABLE_NAME);
            // 事务处理来源键值(明细行主键值)
            trx.setTrxSourceKey(detail.getTrxDetailId().toString());
            // 到期日
            trx.setExpiryDate(detail.getExpiryDate());
            if (StringUtils.isEmpty(remark) && StringUtils.isEmpty(detail.getRemark())) {
                trx.setRemark(null);
            } else {
                trx.setRemark((remark == null ? "" : remark + ";") + detail.getRemark());
            }
            invTrxs.add(trx);
        }

        return new ResponseData(stockTrxService.submitTransaction(requestContext, stockTrx, invTrxs, false));
    }

    /**
     * 根据出入库单号获取对应出入库记录.
     *
     * @param request
     *            请求上下文
     * @param trxId
     *            出入库ID
     * @return responseData 响应数据
     * @throws InventoryException
     *             库存统一异常
     */
    @RequestMapping(value = "/im/stocktrx/get")
    @ResponseBody
    public ResponseData getStockTrx(HttpServletRequest request, Long trxId, Long organizationId)
            throws InventoryException {

        IRequest requestContext = createRequestContext(request);
        if (trxId == null) {
            return new ResponseData();
        }
        List<StockTrx> stockTrxs = new ArrayList<>();
        StockTrx stockTrx = stockTrxService.getStockTrx(requestContext, trxId, organizationId);
        if (stockTrx == null) {
            stockTrxs.add(new StockTrx());
        } else {
            stockTrxs.add(stockTrx);
        }
        return new ResponseData(stockTrxs);
    }

    /**
     * 获取用户创建出入库的时候选择的商品是否启用批次控制.
     */
    @RequestMapping(value = "/im/stocktrx/isLotControl")
    @ResponseBody
    public ResponseData isLotControl(HttpServletRequest request, Long itemId, Long organizationId) {
        IRequest requestContext = createRequestContext(request);
        Long invOrgId = organizationId;
        // Long invOrgId = (Long)
        // request.getSession(false).getAttribute(SystemProfileConstants.INV_ORG_ID);
        boolean result = invItemPropertyService.isLotControl(requestContext, invOrgId, itemId);
        List<InvItemProperty> iips = new ArrayList<>();
        InvItemProperty iip = new InvItemProperty();
        iip.setLotControl(result);
        iips.add(iip);
        return new ResponseData(iips);
    }

    /**
     * 获取退货单号对应的商品及其相关信息.
     * updated by 15750 at 2018/02/09
     */
    @RequestMapping(value = "/im/stocktrx/getOutRefundItem")
    @ResponseBody
    public ResponseData getOutRefundItem(HttpServletRequest request, String outRefundNo) {
        IRequest requestContext = createRequestContext(request);


        List<StockTrxDetail> stockTrxDetails  = stockTrxService.getOutRefundItem(requestContext, outRefundNo);
        return new ResponseData(stockTrxDetails);
    }
}