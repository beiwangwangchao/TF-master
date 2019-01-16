/*
 *
 */
package com.lkkhpg.dsis.admin.inventory.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lkkhpg.dsis.admin.export.utils.ExportUtils;
import com.lkkhpg.dsis.admin.inventory.exception.InventoryException;
import com.lkkhpg.dsis.admin.inventory.service.ITransferTrxService;
import com.lkkhpg.dsis.common.constant.InventoryConstants;
import com.lkkhpg.dsis.common.inventory.dto.TransferTrx;
import com.lkkhpg.dsis.common.inventory.dto.TransferTrxDetail;
import com.lkkhpg.dsis.common.inventory.dto.TransferTrxDetailQuery;
import com.lkkhpg.dsis.common.inventory.dto.TransferTrxQuery;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.ResponseData;

/**
 * 
 * 库存管理 转出.
 * 
 * @author zhangchuangsheng
 *
 */
@Controller
public class TransferTrxController extends BaseController {

    @Autowired
    private ITransferTrxService transferTrxService;

    /**
     * 查询转出单.
     * 
     * @param transferTrxQuery
     *            查询条件dto
     * @param page
     *            页
     * @param pagesize
     *            页大小
     * @param request
     *            请求上下文
     * @return ResponseData 数据返回对象
     */
    @RequestMapping(value = "/im/transferOutTrx/query")
    public Object queryTransferOutTrx(TransferTrxQuery transferTrxQuery, HttpServletResponse response,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize, HttpServletRequest request)
            throws IllegalArgumentException, IllegalAccessException, IOException {

        IRequest requestContext = createRequestContext(request);
        transferTrxQuery.setTransferType(InventoryConstants.TRANSFER_TYPE_OUT);

        List<TransferTrx> list = transferTrxService.selectTransferTrxs(requestContext, transferTrxQuery, page,
                pagesize);

        if (request.getParameter("isExport2Excel") != null && request.getParameter("isExport2Excel").equals("Y")) {
            ExportUtils.export(request, response, list);
            return null;
        } else {
            return new ResponseData(list);
        }
    }

    /**
     * 查询转入单.
     * 
     * @param transferTrxQuery
     *            转入单查询dto.
     * @param page
     *            起始页
     * @param pagesize
     *            页大小
     * @param request
     *            请求上下文
     * @return ResponseData 数据返回对象
     */
    @RequestMapping(value = "/im/transferInTrx/query")
    public ResponseData queryTransferInTrx(TransferTrxQuery transferTrxQuery, HttpServletResponse response,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize, HttpServletRequest request)
            throws IllegalArgumentException, IllegalAccessException, IOException {

        IRequest requestContext = createRequestContext(request);
        transferTrxQuery.setTransferType(InventoryConstants.TRANSFER_TYPE_IN);
        
        List<TransferTrxQuery> list = transferTrxService.selectInTransferTrxs(requestContext, transferTrxQuery, page,
                pagesize);
        if (request.getParameter("isExport2Excel") != null && request.getParameter("isExport2Excel").equals("Y")) {
            ExportUtils.export(request, response, list);
            return null;
        } else {
            return new ResponseData(list);
        }
    }

    /**
     * 
     * 查询转出单详情.
     * 
     * @param transferTrxDetail
     *            转出行dto
     * @param page
     *            页
     * @param pagesize
     *            页大小
     * @param request
     *            请求上下文
     * @return ResponseData 数据返回对象
     */
    @RequestMapping(value = "/im/transferOutDetail/query")
    public ResponseData queryTransferOutTrxDetail(TransferTrxDetail transferTrxDetail,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize, HttpServletRequest request) {

        IRequest requestContext = createRequestContext(request);
        return new ResponseData(transferTrxService.selectTransferTrxDetails(requestContext, transferTrxDetail));
    }

    /**
     * 查询转入单行.
     * 
     * @param transferTrxDetailQuery
     *            转入单行查询dto.
     * @param page
     *            页
     * @param pagesize
     *            页大小
     * @param request
     *            请求上下文
     * @return ResponseData 数据返回对象
     */
    @RequestMapping(value = "/im/transferInDetail/query")
    public ResponseData queryTransferInTrxDetail(TransferTrxDetailQuery transferTrxDetailQuery,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize, HttpServletRequest request) {

        IRequest requestContext = createRequestContext(request);
        return new ResponseData(transferTrxService.selectInTransferTrxDetails(requestContext, transferTrxDetailQuery));
    }

    /**
     * 保存转出单.
     * 
     * @param transferTrx
     *            转出单
     * @param request
     *            请求上下文
     * @return ResponseData 数据返回对象
     * @throws InventoryException
     *             库存统一异常
     */
    @RequestMapping(value = "/im/transferOut/save", method = RequestMethod.POST)
    public ResponseData saveTransferOuts(@RequestBody TransferTrx transferTrx, HttpServletRequest request)
            throws InventoryException {

        IRequest requestContext = createRequestContext(request);
        TransferTrx trx = transferTrxService.saveTransferOutTrx(requestContext, transferTrx);
        List<TransferTrx> trxs = new ArrayList<TransferTrx>();
        trxs.add(trx);
        ResponseData responseData = new ResponseData(trxs);
        responseData.setMessage(trx.getTrxId().toString());
        return responseData;
    }

    /**
     * 提交转出单.
     * 
     * @param transferTrx
     *            转出单.
     * @param request
     *            请求上下文
     * @return ResponseData 数据返回对象
     * @throws InventoryException
     *             库存统一异常
     */
    @RequestMapping(value = "/im/transferOut/submit", method = RequestMethod.POST)
    public ResponseData submitTransferOuts(@RequestBody TransferTrx transferTrx, HttpServletRequest request)
            throws InventoryException {

        IRequest requestContext = createRequestContext(request);
        TransferTrx trx;
        ResponseData responseData;
        trx = transferTrxService.submitTransferOutTrx(requestContext, transferTrx);

        List<TransferTrx> trxs = new ArrayList<TransferTrx>();
        trxs.add(trx);
        responseData = new ResponseData(trxs);
        responseData.setMessage(trx.getTrxId().toString());
        return responseData;

    }

    /**
     * 保存转入单.
     * 
     * @param transferTrx
     *            转入单
     * @param request
     *            请求上下文
     * @return ResponseData 数据返回对象
     * @throws InventoryException
     *             库存统一异常
     */
    @RequestMapping(value = "/im/transferInTrx/save", method = RequestMethod.POST)
    public ResponseData saveTransferInTrx(@RequestBody TransferTrx transferTrx, HttpServletRequest request)
            throws InventoryException {

        IRequest requestContext = createRequestContext(request);
        TransferTrx trx = transferTrxService.saveTransferInTrx(requestContext, transferTrx);
        List<TransferTrx> trxs = new ArrayList<TransferTrx>();
        trxs.add(trx);
        ResponseData responseData = new ResponseData(trxs);
        responseData.setMessage(trx.getTrxId().toString());
        return responseData;
    }

    /**
     * 提交转入单.
     * 
     * @param transferTrx
     *            转入单.
     * @param request
     *            请求上下文
     * @return ResponseData 数据返回对象
     * @throws InventoryException
     *             库存统一异常
     */

    @RequestMapping(value = "/im/transferInTrx/submit", method = RequestMethod.POST)
    public ResponseData submitTransferInTrx(@RequestBody TransferTrx transferTrx, HttpServletRequest request)
            throws InventoryException {

        IRequest requestContext = createRequestContext(request);
        TransferTrx trx;
        ResponseData responseData;
        trx = transferTrxService.submitTransferInTrx(requestContext, transferTrx);

        List<TransferTrx> trxs = new ArrayList<TransferTrx>();
        trxs.add(trx);
        responseData = new ResponseData(trxs);
        responseData.setMessage(trx.getTrxId().toString());
        return responseData;
    }

    /**
     * 删除转出单.
     * 
     * @param transferTrxs
     *            转出单
     * @param request
     *            请求上下文
     * @return ResponseData 数据返回对象
     */
    @RequestMapping(value = "/im/transferOut/remove", method = RequestMethod.POST)
    public ResponseData removeTransferouts(@RequestBody List<TransferTrx> transferTrxs, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        transferTrxService.batchDelete(requestContext, transferTrxs);
        return new ResponseData();
    }

    /**
     * 删除转出单行.
     * 
     * @param transferTrxDetails
     *            转出行
     * @param request
     *            请求上下文
     * @return ResponseData 数据返回对象
     */
    @RequestMapping(value = "/im/transferOutDetail/remove", method = RequestMethod.POST)
    public ResponseData removeTransferTrxDetailOuts(@RequestBody List<TransferTrxDetail> transferTrxDetails,
            HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        transferTrxService.batchDeleteDetail(requestContext, transferTrxDetails);
        return new ResponseData();
    }

    /**
     * 删除转入单行.
     * 
     * @param transferTrxDetails
     *            转入单行
     * @param request
     *            请求上下文
     * @return ResponseData 数据返回对象
     */
    @RequestMapping(value = "/im/transferInDetailTrx/remove", method = RequestMethod.POST)
    public ResponseData removeTransferInTrxDetailOuts(@RequestBody List<TransferTrxDetail> transferTrxDetails,
            HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        transferTrxService.removeTransferInDetailTrx(requestContext, transferTrxDetails);
        return new ResponseData();
    }

    /**
     * 释放转入单行.
     * 
     * @param transferTrxDetails
     *            转出单行明细
     * @param request
     *            请求上下文
     * @return ResponseData 数据返回对象
     * @throws InventoryException
     *             异常
     */
    @RequestMapping(value = "/im/transferInDetailTrx/release", method = RequestMethod.POST)
    public ResponseData releaseTransferInTrxDetails(@RequestBody List<TransferTrxDetail> transferTrxDetails,
            HttpServletRequest request) throws InventoryException {
        IRequest requestContext = createRequestContext(request);
        transferTrxService.releaseTransferInTrx(requestContext, transferTrxDetails);
        return new ResponseData();
    }

    /**
     * 新增转入单行.
     * 
     * @param sourceTrxId
     *            转出单ID
     * @param trxId
     *            转入单ID
     * @param outTrxDetailIds
     *            已存在界面上的转出单行ID。
     * @param itemNumber
     *            商品编码
     * @param request
     *            请求上文
     * @return 转入单行
     */
    @RequestMapping(value = "/im/transferInDetail/add", method = RequestMethod.POST)
    public ResponseData addTransferInTrxDetail(Long sourceTrxId, Long trxId, String outTrxDetailIds, String itemNumber,
            HttpServletRequest request) {
        List<String> detailIds = new ArrayList<String>();
        if (!StringUtils.isEmpty(outTrxDetailIds)) {
            List<String> temps = Arrays.asList(outTrxDetailIds.split(","));
            for (String temp : temps) {
                if (!StringUtils.isEmpty(temp)) {
                    detailIds.add(temp);
                }
            }
        }

        IRequest requestContext = createRequestContext(request);

        List<TransferTrxDetailQuery> list = transferTrxService.addTransferInTrxDetail(requestContext, sourceTrxId,
                trxId, detailIds, itemNumber);
        return new ResponseData(list);
    }

    /**
     * 获取转入某行的转入剩余量.
     * 
     * @param trxId
     * @param sourceTrxId
     * @param itemId
     * @param lotNumber
     * @param packingType
     * @param request
     * @return 响应信息
     */
    @RequestMapping(value = "/im/transferInDetail/getRemainingIndAftCar", method = RequestMethod.POST)
    public ResponseData getRemainingIndAftCar(Long trxId, Long sourceTrxId, Long itemId, String lotNumber,
            String packingType, HttpServletRequest request) {
        List<TransferTrxDetail> list = new ArrayList<TransferTrxDetail>();
        IRequest requestContext = createRequestContext(request);
        TransferTrxDetail ttd = transferTrxService.getRemainingIndAftCar(requestContext, trxId, sourceTrxId, itemId,
                lotNumber, packingType);
        list.add(ttd);
        return new ResponseData(list);
    }
}