/*
 *
 */
package com.lkkhpg.dsis.admin.integration.gds.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lkkhpg.dsis.admin.integration.gds.exception.IntegrationException;
import com.lkkhpg.dsis.admin.integration.gds.service.IFindSaleBranchListService;
import com.lkkhpg.dsis.common.config.dto.SpmMarket;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;

/**
 * 获取分公司列表.
 * 
 * @author chuangsheng.zhang.
 */
@Controller
public class FindSaleBranchListController extends BaseController {

    @Autowired
    private IFindSaleBranchListService findSaleBranchListService;

    /**
     * 获取分公司列表.
     * 
     * @param request
     *            请求上下文.
     * @param toMarketId
     *            原分公司ID
     * @return 分公司列表
     * @throws IntegrationException
     *             接口异常
     */
    @RequestMapping(value = "/integration/gds/findSaleBranchList")
    public List<SpmMarket> findSaleBranchList(HttpServletRequest request, Long toMarketId) throws IntegrationException {
        IRequest iRequest = createRequestContext(request);
        return findSaleBranchListService.findSaleBranchList(iRequest, toMarketId);

    }
}
