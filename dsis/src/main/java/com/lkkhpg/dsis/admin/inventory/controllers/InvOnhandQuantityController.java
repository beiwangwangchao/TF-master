/*
 *
 */

package com.lkkhpg.dsis.admin.inventory.controllers;

import com.lkkhpg.dsis.common.inventory.dto.InvOnhandQuantity;
import com.lkkhpg.dsis.common.service.IInvOnhandQuantityService;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 库存量Controller.
 * 
 * @author qiubin.shen
 */
@Controller
public class InvOnhandQuantityController extends BaseController {

    @Autowired
    private IInvOnhandQuantityService invOnhandQuantityService;



    /**
     * 查询库存量.
     * 
     * @param invOnhandQuantity
     *            库存量DTO
     * @param page
     *            页
     * @param pagesize
     *            总页数
     * @param request
     *            请求上下文
     * @return ResponseData结果集
     */
    @RequestMapping(value = "/im/onhand/query")
    @ResponseBody
    public ResponseData queryItemNumbers(InvOnhandQuantity invOnhandQuantity,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize, HttpServletRequest request) {
        IRequest iRequest = createRequestContext(request);




        List<InvOnhandQuantity> list = invOnhandQuantityService.queryOnhandQty(createRequestContext(request),
                invOnhandQuantity, page, pagesize);
        /*if (invOnhandQuantity.getOrganizationId() == null) {
            return new ResponseData();
        }*/
        return new ResponseData(list);
    }
}
