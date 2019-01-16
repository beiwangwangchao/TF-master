/*
 *
 */

package com.lkkhpg.dsis.admin.promotion.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lkkhpg.dsis.common.exception.CommVoucherException;
import com.lkkhpg.dsis.common.promotion.dto.PdmVoucherGift;
import com.lkkhpg.dsis.common.promotion.dto.PdmVoucherItem;
import com.lkkhpg.dsis.common.promotion.dto.PdmVoucherMember;
import com.lkkhpg.dsis.common.promotion.dto.Voucher;
import com.lkkhpg.dsis.common.service.IVoucherService;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.ResponseData;

/**
 * 优惠券Controller.
 * 
 * @author hanrui.huang
 */
@Controller
public class VoucherController extends BaseController {

    @Autowired
    private IVoucherService voucherService;

    /**
     * 保存优惠券.
     * 
     * @param voucher
     *            优惠券List
     * @param result
     *            数据绑定结果
     * @param request
     *            请求上下文
     * @return ResponseData 响应数据
     * @throws CommVoucherException
     *             系统配置统一异常
     */
    @RequestMapping(value = "/pdm/voucher/save", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData saveVoucher(@RequestBody Voucher voucher, BindingResult result, HttpServletRequest request)
            throws CommVoucherException {
        getValidator().validate(voucher, result);
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(voucherService.saveVoucher(requestContext, voucher));
    }

    /**
     * 优惠券查询.
     * 
     * @param voucher
     *            优惠券DTO
     * @param page
     *            页
     * @param pagesize
     *            页大小
     * @param request
     *            请求上下文
     * @return 响应数据
     */
    @RequestMapping(value = "/pdm/voucher/query")
    @ResponseBody
    public ResponseData queryVoucher(Voucher voucher, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(voucherService.queryVoucher(requestContext, voucher, page, pagesize));
    }

    /**
     * 发放优惠券.
     * 
     * @param voucher
     *            优惠券List
     * @param result
     *            数据绑定结果
     * @param request
     *            请求上下文
     * @return ResponseData 响应数据
     * @throws CommVoucherException
     *             系统配置统一异常
     */
    @RequestMapping(value = "/pdm/assign/save", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData saveVoucherAssign(@RequestBody Voucher voucher, BindingResult result,
            HttpServletRequest request) throws CommVoucherException {
        getValidator().validate(voucher, result);
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(voucherService.saveVoucherAssign(requestContext, voucher));
    }

    /**
     * 删除优惠券会员.
     * 
     * @param pdmVoucherMembers
     *            优惠券分配会员DTO
     * @param request
     *            请求上下文
     * @return 响应数据
     */
    @RequestMapping(value = "/pdm/member/remove", method = RequestMethod.POST)
    public ResponseData removeMember(@RequestBody List<PdmVoucherMember> pdmVoucherMembers,
            HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        voucherService.removeMember(requestContext, pdmVoucherMembers);
        return new ResponseData();
    }

    /**
     * 删除优惠券商品.
     * 
     * @param pdmVoucherItems
     *            优惠券商品DTO
     * @param request
     *            请求上下文
     * @return 响应数据
     */
    @RequestMapping(value = "/pdm/item/remove", method = RequestMethod.POST)
    public ResponseData removeItem(@RequestBody List<PdmVoucherItem> pdmVoucherItems, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        voucherService.removeItem(requestContext, pdmVoucherItems);
        return new ResponseData();
    }

    /**
     * 删除优惠券赠品.
     * 
     * @param pdmVouchergifts
     *            赠品DTO
     * @param request
     *            请求上下文
     * @return 响应数据
     */
    @RequestMapping(value = "/pdm/gift/remove", method = RequestMethod.POST)
    public ResponseData removeGift(@RequestBody List<PdmVoucherGift> pdmVouchergifts, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        voucherService.removeGift(requestContext, pdmVouchergifts);
        return new ResponseData();
    }

    /**
     * 优惠券赠品查询.
     * 
     * @param voucherId
     *            优惠券ID
     * @param page
     *            页
     * @param pagesize
     *            页大小
     * @param request
     *            请求上下文
     * @return 响应数据
     */
    @RequestMapping(value = "/pdm/gift/query")
    @ResponseBody
    public ResponseData queryGift(Long voucherId, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(voucherService.queryPdmVoucherGift(requestContext, voucherId, page, pagesize));
    }

    /**
     * 优惠券商品查询.
     * 
     * @param voucherId
     *            优惠券ID
     * @param page
     *            页
     * @param pagesize
     *            页大小
     * @param request
     *            请求上下文
     * @return 响应数据
     */
    @RequestMapping(value = "/pdm/item/query")
    @ResponseBody
    public ResponseData queryItem(Long voucherId, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(voucherService.queryPdmVoucherItem(requestContext, voucherId, page, pagesize));
    }

    /**
     * 优惠券会员查询.
     * 
     * @param voucherId
     *            优惠券ID
     *            页大小
     * @param request
     *            请求上下文
     * @return 响应数据
     */
    @RequestMapping(value = "/pdm/member/query")
    @ResponseBody
    public ResponseData queryMember(Long voucherId,
            //@RequestParam(defaultValue = DEFAULT_PAGE) int page,
            //@RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize, 
            HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        
        return new ResponseData(voucherService.queryPdmVoucherMember(requestContext, voucherId));
    }

    /**
     * 优惠券查询.
     * 
     * @param voucherId
     *            优惠券ID
     * @param request
     *            请求上下文
     * @return 响应数据
     */
    @RequestMapping(value = "/pdm/voucher/get")
    @ResponseBody
    public ResponseData getVoucher(Long voucherId, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(voucherService.getVoucher(requestContext, voucherId));
    }

    /**
     * 获取会员可用的优惠券.
     * 
     * @param request
     *            统一上下文
     * @param memberId
     *            会员ID
     * @param salesOrgId 
     * @return 响应数据
     */
    @RequestMapping(value = "/pdm/voucher/getByMember")
    @ResponseBody
    public ResponseData getMemberVoucher(HttpServletRequest request, Long memberId,Long salesOrgId) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(voucherService.getMemberVouchers(requestContext, memberId,salesOrgId));
    }
    
    /**
     * 初始化保存后数据.
     * @param request 统一上下文
     * @param voucherId 优惠券id
     * @return 详情页面相关信息
     
    @RequestMapping(value = "/pdm/voucher/initSaveAfter")
    public ResponseData initSaveAfter(HttpServletRequest request, Long voucherId){
        IRequest requestContext = createRequestContext(request);
        return new ResponseData();
    }*/
}
