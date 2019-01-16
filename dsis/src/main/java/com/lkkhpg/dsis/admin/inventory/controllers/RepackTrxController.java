/*
 *
 */
package com.lkkhpg.dsis.admin.inventory.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lkkhpg.dsis.admin.inventory.exception.InventoryException;
import com.lkkhpg.dsis.admin.inventory.exception.RepackTrxException;
import com.lkkhpg.dsis.admin.inventory.service.IRepackService;
import com.lkkhpg.dsis.admin.member.exception.MemberException;
import com.lkkhpg.dsis.admin.product.category.service.IInvCategoryService;
import com.lkkhpg.dsis.common.constant.InventoryConstants;
import com.lkkhpg.dsis.common.inventory.dto.InvOnhandQuantity;
import com.lkkhpg.dsis.common.inventory.dto.InvTransaction;
import com.lkkhpg.dsis.common.inventory.dto.RepackTrx;
import com.lkkhpg.dsis.common.inventory.dto.RepackTrxDetail;
import com.lkkhpg.dsis.common.product.dto.InvItem;
import com.lkkhpg.dsis.common.product.dto.InvItemHierarchy;
import com.lkkhpg.dsis.common.service.IInvOnhandQuantityService;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.ResponseData;

/**
 * 查询分包记录 Controller.
 * 
 * @author hanrui.huang
 */
@Controller
public class RepackTrxController extends BaseController {

    /**
     * 重新载入分包单 状态Key.
     */
    private static final String STATUS_NAME = "status";
    /**
     * 重新载入分包单 返回成功.
     */
    private static final String STATUS_SUCCESS = "success";
    /**
     * 找不到对应的分包单 返回失败.
     */
    private static final String STATUS_FAILURE = "failure";
    /**
     * 包单 Key.
     */
    private static final String REPACKTRX_NAME = "repackTrx";
    /**
     * onhandQuantity Key.
     */
    private static final String ONHANDQUANTITY_NAME = "onhandQuantity";

    @Autowired
    private IRepackService repackService;

    @Autowired
    private IInvOnhandQuantityService invOnhandQuantityService;

    @Autowired
    private IInvCategoryService invCategoryService;

    /**
     * 查询分包记录.
     * 
     * @param request
     *            上下文请求
     * 
     * @param repackTrx
     *            重新分包DTO
     * @param page
     *            页
     * @param pagesize
     *            分页大小
     * @return responseData 响应数据
     */
    @RequestMapping(value = "/im/repack/query")
    @ResponseBody
    public ResponseData queryRepack(RepackTrx repackTrx, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        String status = repackTrx.getStatus();
        if (repackTrx.getOrganizationId() == null) {
            return new ResponseData();
        }
        if (status != null && !StringUtils.isEmpty(status)) {
            String[] selStatus = status.split(";");
            if (selStatus.length > 1) {
                repackTrx.setStatus(null);
            }
        }
        return new ResponseData(repackService.queryRepack(requestContext, repackTrx, page, pagesize));
    }

    /**
     * 删除重新分包事务.
     * 
     * @param request
     *            上下文请求
     * @param repackTrxs
     *            重新分包DTO
     * @return responseData 响应数据
     * @throws RepackTrxException
     *             已完成的不允许删除异常
     */
    @RequestMapping(value = "/im/repack/remove", method = RequestMethod.POST)
    public ResponseData remove(@RequestBody List<RepackTrx> repackTrxs, HttpServletRequest request)
            throws RepackTrxException {
        IRequest requestContext = createRequestContext(request);
        boolean isAllowd = true;
        for (RepackTrx trx : repackTrxs) {
            if (org.apache.commons.lang3.StringUtils.isNotBlank(trx.getStatus())
                    && trx.getStatus().equals(InventoryConstants.REPACK_STATUS_COMP)) {
                isAllowd = false;
                break;
            }
        }
        if (isAllowd) {
            repackService.batchDelete(requestContext, repackTrxs);
        } else {
            throw new RepackTrxException(RepackTrxException.COMPLETE_NOT_ALLOWD_DELETE, null);
        }

        return new ResponseData();
    }

    /**
     * 查询库存组织下的商品包.
     * 
     * @param request
     *            上下文请求
     * @param invItem
     *            商品dto
     * @param page
     *            页
     * @param pagesize
     *            分页大小
     * @return ResponseData 响应数据
     */
    @RequestMapping(value = "/im/repack/queryItems")
    @ResponseBody
    public ResponseData queryItem(InvItem invItem, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(repackService.queryItemsByOrganizationId(requestContext, invItem, page, pagesize));
    }

    /**
     * 保存创建分包信息.
     * 
     * @param request
     *            上下文请求
     * @param result
     *            校验结果
     * @param repackTrxs
     *            重新分包List
     * @return ResponseData 响应数据
     * @throws RepackTrxException 分包异常
     */
    @RequestMapping(value = "/im/repack/save", method = RequestMethod.POST)
    public ResponseData saveRepack(@RequestBody List<RepackTrx> repackTrxs, BindingResult result,
            HttpServletRequest request) throws RepackTrxException {
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(repackService.batchUpdate(requestContext, repackTrxs));
    }

    /**
     * 查询当前商品包库存现有量.
     * 
     * @param request
     *            请求上下文
     * @param invOnhandQuantity
     *            库存现有量DTO
     * @return ResponseData 响应数据
     */
    @RequestMapping(value = "/im/repack/getOnhandQuantity")
    @ResponseBody
    public ResponseData getquantity(InvOnhandQuantity invOnhandQuantity, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        InvOnhandQuantity onhandQuatity = invOnhandQuantityService.getOnhandQuantity(requestContext, invOnhandQuantity);
        List<InvOnhandQuantity> list = new ArrayList<InvOnhandQuantity>();
        if (onhandQuatity != null) {
            list.add(onhandQuatity);
            return new ResponseData(list);
        }
        return new ResponseData();
    }

    /**
     * 获取商品明细-库存现有量中的批次信息(用于组合).
     * 
     * @param request
     *            请求上下文
     * @param invOnhandQuantity
     *            库存现有量DTO
     * @return OnhandQuatitys 库存现有量List
     */
    @RequestMapping(value = "/im/repack/queryOnhandLot")
    @ResponseBody
    public ResponseData queryOnhandLot(InvOnhandQuantity invOnhandQuantity, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        List<InvOnhandQuantity> onhandQuatityList = invOnhandQuantityService
                .queryOnhandQty(requestContext, invOnhandQuantity, 1, Integer.MAX_VALUE);
        return new ResponseData(onhandQuatityList);
    }

    /**
     * 获取商品明细-组合包中的批次信息(用于分解).
     * 
     * @param request
     *            请求上下文
     * @param invOnhandQuantity
     *            库存现有量DTO
     * @return OnhandQuatitys 库存现有量List
     */
    @RequestMapping(value = "/im/repack/queryComposeLot")
    @ResponseBody
    public ResponseData queryComposeLot(InvOnhandQuantity invOnhandQuantity, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        List<InvOnhandQuantity> onhandQuatityList = repackService.queryComposeLot(requestContext, invOnhandQuantity);
        return new ResponseData(onhandQuatityList);
    }

    /**
     * 查询库存组织下商品包的商品明细.
     * 
     * @param request
     *            上下文请求
     * 
     * @param packageItemId
     *            商品包ID
     * @param organizationId 
     *             库存组织ID
     * @param page
     *            页
     * @param pagesize
     *            分页大小
     * @return responseData 响应数据
     */
    @RequestMapping(value = "/im/repack/queryItemsDetail")
    @ResponseBody
    public ResponseData queryItems(Long packageItemId, Long organizationId,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        InvItemHierarchy invItemHierarchy = new InvItemHierarchy();
        invItemHierarchy.setPackageItemId(packageItemId);
        invItemHierarchy.setOrganizationId(organizationId);
        return new ResponseData(repackService.queryItem(requestContext, invItemHierarchy, page, pagesize));
    }

    /**
     * 保存并提交出入库事务.
     * 
     * @param request
     *            请求上下文
     * @param repackTrxs
     *            重新分包List
     * @param result
     *            数据绑定结果
     * @return responseData 响应数据
     * @throws MemberException
     *             会员统一异常
     * @throws InventoryException
     *             库存统一异常
     * @throws RepackTrxException 分包异常
     */
    @RequestMapping(value = "/im/repacktrx/saveSubmit", method = RequestMethod.POST)
    public ResponseData saveRepackTrxs(HttpServletRequest request, @RequestBody List<RepackTrx> repackTrxs,
            BindingResult result) throws MemberException, InventoryException, RepackTrxException {
        getValidator().validate(repackTrxs, result);
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(repackService.submitTransaction(requestContext, repackTrxs, true));
    }

    /**
     * 提交出入库事务.
     * 
     * @param request
     *            请求上下文
     * @param repackTrxs
     *            出入库List
     * @return responseData 响应数据
     * @throws MemberException
     *             会员统一异常
     * @throws InventoryException
     *             库存统一异常
     * @throws RepackTrxException 分包异常
     */
    @RequestMapping(value = "/im/repackTrx/submit", method = RequestMethod.POST)
    public ResponseData submitRepackTrxs(HttpServletRequest request, @RequestBody List<RepackTrx> repackTrxs)
            throws MemberException, InventoryException, RepackTrxException {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(repackService.submitTransaction(requestContext, repackTrxs, false));
    }

    /**
     * 查询商品类别.
     * 
     * @param request
     *            请求上下文
     * @param itemType
     *            商品类别
     * @param page
     *            页
     * @param pagesize
     *            分页大小
     * @return ResponseData 响应数据
     */
    @RequestMapping(value = "/im/repackTrx/queryBottomCategory", method = RequestMethod.POST)
    public ResponseData queryBottomCategory(HttpServletRequest request, String itemType,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(invCategoryService.queryBottomCategory(requestContext, itemType));
    }

    /**
     * 查询分包明细.
     * 
     * @param request
     *            上下文请求
     * 
     * @param repackTrxDetail
     *            分包明细DTO
     * @param page
     *            页
     * @param pagesize
     *            分页大小
     * @return responseData 响应数据
     */
    @RequestMapping(value = "/im/repackTrxDetail/query")
    @ResponseBody
    public ResponseData queryRepackDetail(RepackTrxDetail repackTrxDetail,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        if (repackTrxDetail.getOrganizationId() == null) {
            return new ResponseData();
        }
        return new ResponseData(repackService.selectDetail(requestContext, repackTrxDetail, page, pagesize));
    }

    /**
     * 批量删除分包明细.
     * 
     * @param request
     *            link HttpServletRequest
     * @param repackTrxDetails
     *            RepackTrxDetail类型list
     * @return ResponseData 结果对象
     */
    @RequestMapping(value = "/im/repackTrx/trxDetail/remove", method = RequestMethod.POST)
    public ResponseData delRepackDetail(HttpServletRequest request,
            @RequestBody List<RepackTrxDetail> repackTrxDetails) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(repackService.batchDeleteDetails(requestContext, repackTrxDetails));
    }

    /**
     * 根据TrxId载入.
     * @param request HttpServletRequest
     * @param trxId 分包单Id
     * @return Map 结果对象
     */
    @RequestMapping(value = "/im/repackTrx/loadById")
    @ResponseBody 
    public Map<String, Object> loadByTrxId(HttpServletRequest request, Long trxId) {
        Map<String, Object> map = new HashMap<String, Object>();
        IRequest requestContext = createRequestContext(request);
        // 查询头
        RepackTrx trx = repackService.selectById(requestContext, trxId);
        if (trx != null) {
            map.put(STATUS_NAME, STATUS_SUCCESS);
            // 查询批次行信息
            RepackTrxDetail params = new RepackTrxDetail();
            params.setTrxId(trx.getTrxId());
            List<RepackTrxDetail> details = repackService.selectDetails(requestContext, params);
            trx.setRepackTrxDetails(details);
            InvOnhandQuantity invParams = new InvOnhandQuantity();
            invParams.setPackageItemId(trx.getPackageItemId());
            invParams.setOrganizationId(trx.getOrganizationId());
            InvOnhandQuantity onhandQuatity = invOnhandQuantityService.getOnhandQuantity(requestContext, invParams);
            map.put(ONHANDQUANTITY_NAME, onhandQuatity);
            map.put(REPACKTRX_NAME, trx);

        } else {
            map.put(STATUS_NAME, STATUS_FAILURE);
        }
        return map;
    }

    /**
     * 获取所选择批次的组合数量.
     * @param request 请求上下文
     * @param invTransaction 库存事务
     * @return 所选择批次的组合数量
     */
    @RequestMapping(value = "/im/repackTrx/getLotQty")
    @ResponseBody 
    public int getLoTQuantity(HttpServletRequest request, InvTransaction invTransaction) {
        IRequest requestContext = createRequestContext(request);
        return repackService.getLoTQuantity(requestContext, invTransaction);
        
    }
}