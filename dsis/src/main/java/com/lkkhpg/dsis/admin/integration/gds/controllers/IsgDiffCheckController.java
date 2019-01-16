/*
 *
 */
package com.lkkhpg.dsis.admin.integration.gds.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lkkhpg.dsis.admin.export.utils.ExportUtils;
import com.lkkhpg.dsis.admin.integration.gds.dto.IsgDiffCheck;
import com.lkkhpg.dsis.admin.integration.gds.exception.IntegrationException;
import com.lkkhpg.dsis.admin.integration.gds.service.IDeleteDealerService;
import com.lkkhpg.dsis.admin.integration.gds.service.IDiffCheckService;
import com.lkkhpg.dsis.admin.integration.gds.service.IGdsUtilService;
import com.lkkhpg.dsis.common.member.dto.MemStatusChange;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.ResponseData;

/**
 * 差异查询Controller.
 * 
 * @author shenqb.
 */
@Controller
public class IsgDiffCheckController extends BaseController {

    @Autowired
    private IDiffCheckService diffCheckService;

    @Autowired
    private IDeleteDealerService deleteDealerService;

    @Autowired
    private IGdsUtilService gdsUtilService;

    /**
     * 获取差异查询列表.
     * 
     * @param request
     *            请求上下文.
     * @param page
     *            页
     * @param pagesize
     *            页数
     * @param isgDiffCheck
     *            差异查询DTO
     * @return 差异查询列表
     * @throws IOException 
     * @throws IllegalAccessException 
     * @throws IllegalArgumentException 
     */
    @RequestMapping(value = "/integration/gds/diffcheck/query")
    public ResponseData queryDiffChecks(HttpServletRequest request, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize, IsgDiffCheck isgDiffCheck, HttpServletResponse response) throws IllegalArgumentException, IllegalAccessException, IOException {
        IRequest iRequest = createRequestContext(request);
        
        List<IsgDiffCheck> isgDiffChecks = diffCheckService.queryDiffChecks(iRequest, page, pagesize, isgDiffCheck);
        if (request.getParameter("isExport2Excel") != null && request.getParameter("isExport2Excel").equals("Y")) {
            ExportUtils.export(request, response, isgDiffChecks);
            return null;
        } else {
            return new ResponseData(isgDiffChecks);
        }
        

    }

    /**
     * 删除会员.
     * 
     * @param request
     *            请求上下文
     * @param checkOrgCode
     *            组织编码
     * @param adviseNo
     *            通知号
     * @param checkEntityNo
     *            卡号
     * @return ResponseData 结果
     * @throws IntegrationException
     *             异常
     */
    @RequestMapping(value = "/integration/gds/deleteDealers", method = RequestMethod.POST)
    public ResponseData deleteDealers(HttpServletRequest request, String checkOrgCode, String adviseNo,
            String checkEntityNo) throws IntegrationException {
        IRequest requestContext = createRequestContext(request);
        if (StringUtils.isEmpty(checkOrgCode)) {
            checkOrgCode = gdsUtilService.getCurrentOrgCode(requestContext);
        }
        boolean isSuccess = deleteDealerService.deleteDealers(checkOrgCode, adviseNo, checkEntityNo);
        ResponseData responseData = new ResponseData();
        responseData.setSuccess(isSuccess);
        return responseData;
    }
}
