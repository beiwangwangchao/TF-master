/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.integration.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lkkhpg.dsis.admin.integration.gds.dto.IsgEmulator;
import com.lkkhpg.dsis.admin.integration.gds.service.IIsgEmulatorService;
import com.lkkhpg.dsis.admin.integration.service.IIsgLogService;
import com.lkkhpg.dsis.integration.dapp.exception.DAppException;
import com.lkkhpg.dsis.integration.recorder.dto.ListenerRecord;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.BaseDTO;
import com.lkkhpg.dsis.platform.dto.ResponseData;

/**
 * 接口日志Controller.
 * 
 * @author frank.li.
 */
@Controller
public class IsgLogController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(IsgLogController.class);

    @Autowired
    private IIsgLogService isgLogService;
    
    @Autowired
    private IIsgEmulatorService isgEmulatorService;

    /**
     * 获取接口交互行日志.
     * 
     * @param request
     *            请求上下文
     * @param page
     *            页
     * @param pagesize
     *            页数
     * @param requestDateFrom
     *            请求日期从
     * @param requestDateTo
     *            请求日期至
     * @param success
     *            成功状态
     * @param url
     *            路径
     * @param body
     *            参数body
     * @param result
     *            结果
     * @return 差异查询列表
     */
    @RequestMapping(value = "/integration/log/record/query")
    public ResponseData getInterfaceRecordLog(HttpServletRequest request,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize, Date requestDateFrom, Date requestDateTo,
            String success, String url, String body, String result) {
        IRequest iRequest = createRequestContext(request);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("requestDateFrom", requestDateFrom);
        params.put("requestDateTo", requestDateTo);
        params.put("success", success);
        params.put("url", url);
        params.put("body", body);
        params.put("result", result);
        return new ResponseData(isgLogService.getInterfaceRecordLog(iRequest, params, page, pagesize));

    }


    @RequestMapping(value = "/integration/log/listener/query")
    public ResponseData getInterfaceListenerLog(HttpServletRequest request,
                                                @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                                @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize, ListenerRecord params) {
        IRequest iRequest = createRequestContext(request);
        return new ResponseData(isgLogService.getInterfaceListenerLog(iRequest, params, page, pagesize));

    }

    /**
     * 接口表数据查询
     * 
     * @param request
     *            请求上下文
     * @param params
     *            参数
     * @param page
     *            页码
     * @param pagesize
     *            每页最大条数
     * @return list 数据列表
     */
    @RequestMapping(value = "/integration/intefaceData/query")
    public ResponseData queryIntefaceDatas(HttpServletRequest request,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize, @RequestBody Map<String, Object> params) {
        IRequest requestContext = createRequestContext(request);

        // 根据表名匹配MAPPER，只允许执行下列接口表查询
        String mapper = null;
        String tableName = params.get("tableName").toString();
        if ("ISG_DIFF_CHECK".equals(tableName)) {
            mapper = "com.lkkhpg.dsis.admin.integration.gds.mapper.IsgDiffCheckMapper.selectByParams";
        } else if ("ISG_MEMBER_SYNC".equals(tableName)) {
            mapper = "com.lkkhpg.dsis.admin.integration.gds.mapper.IsgMemberSyncMapper.selectByParams";
        } else if ("ISG_MEMBER_INFO_SYNC".equals(tableName)) {
            mapper = "com.lkkhpg.dsis.admin.integration.gds.mapper.IsgMemberInfoSyncMapper.selectByParams";
        } else if ("ISG_MEMBER_REL_SYNC".equals(tableName)) {
            mapper = "com.lkkhpg.dsis.admin.integration.gds.mapper.IsgMemberRelSyncMapper.selectByParams";
        } else if ("ISG_UPSTREAM_CHANGE".equals(tableName)) {
            mapper = "com.lkkhpg.dsis.admin.integration.gds.mapper.IsgUpstreamChangeMapper.selectByParams";
        } else if ("ISG_UPSTREAM_CHANGE_DETAIL".equals(tableName)) {
            mapper = "com.lkkhpg.dsis.admin.integration.gds.mapper.IsgUpstreamChangeDetailMapper.selectByParams";
        } else if ("ISG_MEMBER_DELETE".equals(tableName)) {
            mapper = "com.lkkhpg.dsis.admin.integration.gds.mapper.IsgMemberDeleteMapper.selectByParams";
        } else if ("ISG_MEMBER_DELETE_DETAIL".equals(tableName)) {
            mapper = "com.lkkhpg.dsis.admin.integration.gds.mapper.IsgMemberDeleteDetailMapper.selectByParams";
        } else if ("ISG_MARKET_CHANGE".equals(tableName)) {
            mapper = "com.lkkhpg.dsis.admin.integration.gds.mapper.IsgMarketChangeMapper.selectByParams";
        } else if ("ISG_OVERSEAS_SPONSOR".equals(tableName)) {
            mapper = "com.lkkhpg.dsis.admin.integration.gds.mapper.IsgOverseasSponsorMapper.selectByParams";
        } else if ("ISG_MARKET_CHANGE_DELETE".equals(tableName)) {
            mapper = "com.lkkhpg.dsis.admin.integration.gds.mapper.IsgMarketChangeDeleteMapper.selectByParams";
        } else if ("ISG_MARKET_CHANGE_LIST".equals(tableName)) {
            mapper = "com.lkkhpg.dsis.admin.integration.gds.mapper.IsgMarketChangeListMapper.selectByParams";
        } else if ("ISG_MARKET_CHANGE_OUT_APPLY".equals(tableName)) {
            mapper = "com.lkkhpg.dsis.admin.integration.gds.mapper.IsgMarketChangeOutApplyMapper.selectByParams";
        } else if ("ISG_MARKET_CHANGE_IN_APPROVE".equals(tableName)) {
            mapper = "com.lkkhpg.dsis.admin.integration.gds.mapper.IsgMarketChangeInApproveMapper.selectByParams";
        } else if ("ISG_SALE_BRANCH_LIST".equals(tableName)) {
            mapper = "com.lkkhpg.dsis.admin.integration.gds.mapper.IsgSaleBranchListMapper.selectByParams";
        } else if ("ISG_SO_HEADER".equals(tableName)) {
            mapper = "com.lkkhpg.dsis.admin.integration.gds.mapper.IsgSoHeaderMapper.selectByParams";
        } else if ("ISG_SO_LINE".equals(tableName)) {
            mapper = "com.lkkhpg.dsis.admin.integration.gds.mapper.IsgSoLineMapper.selectByParams";
        } else if ("ISG_PERIOD_CLOSE".equals(tableName)) {
            mapper = "com.lkkhpg.dsis.admin.integration.gds.mapper.IsgSaleBranchListMapper.selectByParams";
        }
        /*
         * 接口表清单 {"intTable": "ISG_DIFF_CHECK", "intTableDesc": "差异结果查询"},
         * {"intTable": "ISG_MEMBER_SYNC", "intTableDesc": "会员同步"}, {"intTable":
         * "ISG_MEMBER_INFO_SYNC", "intTableDesc": "会员同步个人信息行"}, {"intTable":
         * "ISG_MEMBER_REL_SYNC", "intTableDesc": "会员同步相关人信息行"}, {"intTable":
         * "ISG_UPSTREAM_CHANGE", "intTableDesc": "下载移线会员"}, {"intTable":
         * "ISG_UPSTREAM_CHANGE_DETAIL", "intTableDesc": "下载移线会员明细"},
         * {"intTable": "ISG_MEMBER_DELETE", "intTableDesc": "下载删除会员"},
         * {"intTable": "ISG_MEMBER_DELETE_DETAIL", "intTableDesc": "下载删除会员明细"},
         * {"intTable": "ISG_MARKET_CHANGE", "intTableDesc": "下载市场转移(转出/转入)会员"},
         * {"intTable": "ISG_OVERSEAS_SPONSOR", "intTableDesc": "小批量下载海外推荐人"},
         * {"intTable": "ISG_MARKET_CHANGE_DELETE", "intTableDesc": "删除移线申请"},
         * {"intTable": "ISG_MARKET_CHANGE_LIST", "intTableDesc":
         * "获取市场转移（转入待审核或转出所有）列表"}, {"intTable": "ISG_MARKET_CHANGE_OUT_APPLY",
         * "intTableDesc": "保存转出本市场申请"}, {"intTable":
         * "ISG_MARKET_CHANGE_IN_APPROVE", "intTableDesc": "保存转入本市场审核"},
         * {"intTable": "ISG_SALE_BRANCH_LIST", "intTableDesc": "获取销售资料列表"},
         * {"intTable": "ISG_SO_HEADER", "intTableDesc": "小批量同步销售资料"},
         * {"intTable": "ISG_SO_LINE", "intTableDesc": "小批量同步销售行资料"},
         * {"intTable": "ISG_PERIOD_CLOSE", "intTableDesc": "关闭机构月份"}];
         */

        params.put("mapper", mapper);

        // 获取日期访问
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date processDateFrom = formatter.parse(params.get("processDateFrom").toString());
            params.put("processDateFrom", processDateFrom);

            if (params.get("processDateTo") != null) {
                Date processDateTo = formatter.parse(params.get("processDateTo").toString());
                params.put("processDateTo", processDateTo);
            }
        } catch (ParseException e) {
            if (logger.isErrorEnabled()) {
                logger.error("SimpleDateFormat.parse error");
            }
        }

        List<? extends BaseDTO> interfaceDatas = isgLogService.queryInterfaceDatas(requestContext, mapper, params, page,
                pagesize);
        return new ResponseData(interfaceDatas);
    }
    
    @RequestMapping(value = "/distributors/queryIsgEmulator", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData queryIsgEmulator(HttpServletRequest request,@RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize,
            IsgEmulator isgEmulator)
                    throws DAppException {
        IRequest iRequest = createRequestContext(request);
        List<IsgEmulator> isgEmulators = isgEmulatorService.selectAll(iRequest, page, pagesize);
        return new ResponseData(isgEmulators);
    }
    
    @RequestMapping(value = "/distributors/updateIsgEmulator", method = RequestMethod.POST)
    public ResponseData updateIsgEmulator(HttpServletRequest request, @RequestBody List<IsgEmulator> isgEmulators)
                    throws DAppException {
        IRequest iRequest = createRequestContext(request);
        for (IsgEmulator isgEmulator : isgEmulators) {
            isgEmulatorService.updateIsgEmulator(iRequest ,isgEmulator);
        }
        return new ResponseData();
    }
}
