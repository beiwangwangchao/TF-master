/*
 *
 */
package com.lkkhpg.dsis.admin.integration.gds.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lkkhpg.dsis.admin.integration.gds.dto.IsgGdsProcedureParam;
import com.lkkhpg.dsis.admin.integration.gds.exception.IntegrationException;
import com.lkkhpg.dsis.admin.integration.gds.mapper.IsgGdsProcedureMapper;
import com.lkkhpg.dsis.admin.integration.gds.service.IClosePeriodService;
import com.lkkhpg.dsis.admin.integration.gds.service.IGdsUtilService;
import com.lkkhpg.dsis.admin.member.service.IMemberJobService;
import com.lkkhpg.dsis.common.exception.CommMemberException;
import com.lkkhpg.dsis.common.member.dto.Member;
import com.lkkhpg.dsis.common.service.ICommMemberService;
import com.lkkhpg.dsis.common.service.IVoucherService;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.impl.RequestHelper;
import com.lkkhpg.dsis.platform.dto.ResponseData;

/**
 * 测试主动调用.
 * 
 * @author frank.li
 */
@Controller
public class TestGdsController extends BaseController {

    @Autowired
    private IClosePeriodService closePeriodService;


    @Autowired
    private IGdsUtilService gdsUtilService;

    @Autowired
    private IsgGdsProcedureMapper isgGdsProcedureMapper;
    @Autowired
    private ICommMemberService commMemberService;
    @Autowired
    private IMemberJobService memberJobService;
    @Autowired
    private IVoucherService voucherService;

    private Logger logger = LoggerFactory.getLogger(TestGdsController.class);
    private BeanFactory beanFactory;

    /**
     * 测试关闭期间接口.
     * 
     * @param request
     *            请求上下文
     * @param orgCode
     *            请求参数orgCode
     * @param period
     *            请求关闭的月份
     * @return 返回响应信息
     * @throws IntegrationException
     *             抛出统一接口异常
     */
    @RequestMapping(value = "/integration/gds/closePeriod", method = RequestMethod.POST)
    public ResponseData test(HttpServletRequest request, String orgCode, String period) throws IntegrationException {
        IRequest requestContext = createRequestContext(request);
        if (StringUtils.isEmpty(orgCode)) {
            orgCode = gdsUtilService.getCurrentOrgCode(requestContext);
        }
        if (StringUtils.isEmpty(period)) {
            period = "201512";
        }
        closePeriodService.closePeriod(requestContext, orgCode, period);
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("period", period);
        list.add(map);
        return new ResponseData(list);
    }

    /**
     * 测试GDS接口存储过程调用.
     * 
     * @param request
     *            请求上下文
     * @param isgGdsProcedureParam
     *            请求参数
     * @return 返回响应信息
     * @throws IntegrationException
     *             抛出统一接口异常
     */
    @RequestMapping(value = "/integration/gds/jobProcTest", method = RequestMethod.POST)
    public ResponseData jobProcTest(HttpServletRequest request, @RequestBody IsgGdsProcedureParam isgGdsProcedureParam)
            throws IntegrationException {
        int result = isgGdsProcedureMapper.invokeGdsProcedure(isgGdsProcedureParam);

        if (logger.isDebugEnabled()) {
            logger.debug("result: {}", new Object[] { result });
            logger.debug("returnStatus: {}", new Object[] { isgGdsProcedureParam.getReturnStatus() });
            logger.debug("returnMessage: {}", new Object[] { isgGdsProcedureParam.getReturnMessage() });
        }

        List<IsgGdsProcedureParam> isgGdsProcedureParams = new ArrayList<IsgGdsProcedureParam>();
        isgGdsProcedureParams.add(isgGdsProcedureParam);
        return new ResponseData(isgGdsProcedureParams);
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public ResponseData testForAll()
            throws IntegrationException, CommMemberException {
        IRequest iRequest = RequestHelper.newEmptyRequest();
//        commMemberService.validateForVIPToDIS(iRequest, 11044067L);
//        memberJobService.vipToDistributor();
        Member member = new Member();
        member.setMemberId(11003031L);
        voucherService.invalidMemberVoucher(iRequest, member);
        return new ResponseData();
    }
}
