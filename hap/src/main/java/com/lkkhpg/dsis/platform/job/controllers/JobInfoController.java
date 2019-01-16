/*
 * Copyright LKK Health Products Group Limited
 */

package com.lkkhpg.dsis.platform.job.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.ResponseData;
import com.lkkhpg.dsis.platform.job.dto.JobRunningInfoDto;
import com.lkkhpg.dsis.platform.job.service.impl.JobRunningInfoService;

/**
 * @author liyan.shi@hand-china.com
 */
@Controller
public class JobInfoController extends BaseController {
    @Autowired
    private JobRunningInfoService jobRunningInfoService;

    /**
     * 查询Job运行记录.
     * 
     * @param dto
     *            参数
     * @param page
     *            页码
     * @param pagesize
     *            每页数量
     * @param request
     *            HttpServletRequest
     * @return 运行记录结果
     */
    @RequestMapping(value = "/job/jobinfo/query")
    @ResponseBody
    public ResponseData queryJobRunningInfo(JobRunningInfoDto dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize, HttpServletRequest request) {
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(jobRunningInfoService.queryJobRunningInfo(requestCtx, dto, page, pagesize));
    }

}
