package com.lkkhpg.dsis.common.controllers;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.lkkhpg.dsis.admin.member.service.IMemberService;
import com.lkkhpg.dsis.common.discount.dto.DiscountTrxHeadDto;
import com.lkkhpg.dsis.common.discount.dto.DiscountTrxQuery;
import com.lkkhpg.dsis.common.discount.exception.DiscountException;
import com.lkkhpg.dsis.common.discount.service.IDiscountTrxHeadService;
import com.lkkhpg.dsis.common.discount.service.IDiscountTrxLineService;
import com.lkkhpg.dsis.common.discount.service.IDiscountTrxQueryService;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class DiscountController extends BaseController {

    @Autowired
    private IDiscountTrxHeadService discountTrxHeadService;

    @Autowired
    private IDiscountTrxLineService discountTrxLineService;

    @Autowired
    private IDiscountTrxQueryService discountTrxQueryService;

    @Autowired
    private IMemberService memberService;
    //added by 11816 at 2018/01/16 10:25

    /**
     * 保存事务
     *
     * @param request
     * @param stockTrxs
     * @param result
     * @return
     * @throws DiscountException
     */
    @RequestMapping(value = "/dc/discount/create", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData createDiscountTrxs(HttpServletRequest request, @RequestBody List<DiscountTrxHeadDto> stockTrxs,
                                           BindingResult result) throws DiscountException {

        getValidator().validate(stockTrxs, result);
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }

        //see StockTrxContrller :118
        HttpSession session = request.getSession();
        IRequest requestContext = createRequestContext(request);
        requestContext.setAttribute("createdBy", Long.parseLong(String.valueOf(session.getAttribute("userId"))));
        requestContext.setAttribute("lastUpdatedBy", Long.parseLong(String.valueOf(session.getAttribute("userId"))));

        DiscountTrxHeadDto discountTrxHeadDto = stockTrxs.get(0);
        requestContext.setAttribute("creationDate", discountTrxHeadDto.getCreationDate());
        requestContext.setAttribute("lastUpdateDate", discountTrxHeadDto.getCreationDate());

        return new ResponseData(discountTrxHeadService.createUPTrx(requestContext, discountTrxHeadDto));

    }

    /**
     * 只提交事务
     * @param request
     * @param stockTrxs
     * @param result
     * @return
     * @throws DiscountException
     */
    @RequestMapping(value = "dc/discount/submit", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData submitDiscountTrx(HttpServletRequest request, @RequestBody List<DiscountTrxHeadDto> stockTrxs,
                                          BindingResult result) throws DiscountException {
        getValidator().validate(stockTrxs, result);
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }

        //see StockTrxContrller :118
        HttpSession session = request.getSession();
        IRequest requestContext = createRequestContext(request);

        requestContext.setAttribute("lastUpdatedBy", Long.parseLong(String.valueOf(session.getAttribute("userId"))));

        DiscountTrxHeadDto discountTrxHeadDto = stockTrxs.get(0);
        requestContext.setAttribute("lastUpdateDate", discountTrxHeadDto.getCreationDate());

        return new ResponseData(discountTrxHeadService.submitDiscountTrx(requestContext, discountTrxHeadDto));
    }

    /**
     * 保存并提交事务
     * @param request
     * @param stockTrxs
     * @param result
     * @return
     * @throws DiscountException
     */
    @RequestMapping(value = "dc/discount/submitTrx", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData submitDiscountTransaction(HttpServletRequest request, @RequestBody List<DiscountTrxHeadDto> stockTrxs,
                                                  BindingResult result) throws DiscountException {
        getValidator().validate(stockTrxs, result);
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }
        HttpSession session = request.getSession();
        IRequest requestContext = createRequestContext(request);
        requestContext.setAttribute("createdBy", Long.parseLong(String.valueOf(session.getAttribute("userId"))));
        requestContext.setAttribute("lastUpdatedBy", Long.parseLong(String.valueOf(session.getAttribute("userId"))));

        DiscountTrxHeadDto discountTrxHeadDto = stockTrxs.get(0);


        requestContext.setAttribute("creationDate", discountTrxHeadDto.getCreationDate());
        requestContext.setAttribute("lastUpdateDate", discountTrxHeadDto.getCreationDate());
        return new ResponseData(discountTrxHeadService.submitDiscountTransaction(requestContext, discountTrxHeadDto));
    }

    @RequestMapping(value = "/dc/discount/queryTrx", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData queryBasic(HttpServletRequest request, DiscountTrxQuery discountTrxQuery,
                                   @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                   @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize
                                   ) {
        IRequest requestConatext = createRequestContext(request);

        String status = discountTrxQuery.getAdjustReason();
        if (StringUtils.isNotEmpty(status)) {
            List<String> list = new ArrayList<>();
            String[] selStatus = status.split(";");
            for (int i = 0; i < selStatus.length; i++) {
                list.add(i, selStatus[i]);
            }
            discountTrxQuery.setReasonList(list);
        }

        return new ResponseData(discountTrxQueryService.queryBasic(requestConatext, discountTrxQuery, page, pagesize));

    }


    @RequestMapping(value = "dc/discount/queryDiscountTrxHead", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData queryDiscountTrxHead(HttpServletRequest request, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                             @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize,
                                             DiscountTrxHeadDto discountTrxHeadDto) {
        IRequest requestContext = createRequestContext(request);

        String status = discountTrxHeadDto.getAdjustReason();
        if (StringUtils.isNotEmpty(status)) {
            List<String> list = new ArrayList<>();
            String[] selStatus = status.split(";");
            for (int i = 0; i < selStatus.length; i++) {
                list.add(i, selStatus[i]);
            }
            discountTrxHeadDto.setReasonList(list);
        }

        return new ResponseData(discountTrxHeadService.queryDiscountTrxHead(requestContext, discountTrxHeadDto, page, pagesize));

    }

    @RequestMapping(value = "dc/discounttrx/get")
    @ResponseBody
    public ResponseData getDiscountTrx(HttpServletRequest httpServletRequest, Long discountTrxHeadId) throws DiscountException {
        IRequest request = createRequestContext(httpServletRequest);

        if (null == (discountTrxHeadId)) {
            return new ResponseData();
        }
        List<DiscountTrxHeadDto> discountTrxHeadDtos = new ArrayList<>();

        DiscountTrxHeadDto discountTrxHeadDto = discountTrxHeadService.getDiscountTrx(request, discountTrxHeadId);

        if (discountTrxHeadDto == null) {
            discountTrxHeadDtos.add(new DiscountTrxHeadDto());
        } else {
            discountTrxHeadDtos.add(discountTrxHeadDto);
        }

        return new ResponseData(discountTrxHeadDtos);

    }
}
