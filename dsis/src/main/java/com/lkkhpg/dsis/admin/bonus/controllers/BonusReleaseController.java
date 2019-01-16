/*
 *
 */
package com.lkkhpg.dsis.admin.bonus.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lkkhpg.dsis.admin.bonus.constant.BonusConstants;
import com.lkkhpg.dsis.admin.bonus.exception.BonusException;
import com.lkkhpg.dsis.admin.bonus.service.IBonusFinalService;
import com.lkkhpg.dsis.admin.bonus.service.IBonusReleaseService;
import com.lkkhpg.dsis.admin.bonus.service.IBonusRetransferService;
import com.lkkhpg.dsis.admin.bonus.service.IBonusService;
import com.lkkhpg.dsis.admin.export.utils.ExportUtils;
import com.lkkhpg.dsis.common.bonus.dto.BonusFinal;
import com.lkkhpg.dsis.common.bonus.dto.BonusRelease;
import com.lkkhpg.dsis.common.bonus.dto.BonusReleaseCombine;
import com.lkkhpg.dsis.common.bonus.dto.BonusRetransfer;
import com.lkkhpg.dsis.common.config.dto.SpmMarket;
import com.lkkhpg.dsis.common.config.dto.SpmPeriod;
import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.common.exception.CommMemberException;
import com.lkkhpg.dsis.common.member.dto.MemWithdraw;
import com.lkkhpg.dsis.common.service.ISpmMarketService;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.ResponseData;

/**
 * 奖金发放.
 * 
 * @author li.peng@hand-china.com
 *
 */
@Controller
public class BonusReleaseController extends BaseController {

    @Autowired
    private IBonusReleaseService bonusReleaseService;

    @Autowired
    private IBonusService bonusService;

    @Autowired
    private ISpmMarketService spmMarketService;

    @Autowired
    private IBonusFinalService bonusFinalService;

    @Autowired
    private IBonusRetransferService retransferService;

    /**
     * 奖金发放查询.
     * 
     * @param request
     *            上下文请求
     * @param response 
     * @param bonuRelease
     *            奖金发放查询
     * @param page
     *            页码
     * @param pagesize
     *            页面size
     * @return 奖金发放列表
     * @throws IOException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    @RequestMapping(value = "/bm/release/query")
    @ResponseBody
    public ResponseData queryBonusRelease(HttpServletRequest request, HttpServletResponse response,
            BonusRelease bonuRelease, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize)
            throws IllegalArgumentException, IllegalAccessException, IOException {
        List<BonusRelease> list = bonusReleaseService.queryBonusRelease(createRequestContext(request), bonuRelease,
                page, pagesize);
        if (request.getParameter("isExport2Excel") != null && request.getParameter("isExport2Excel").equals("Y")) {
            ExportUtils.export(request, response, list);
            return null;
        } else {
            return new ResponseData(list);
        }
    }

    /**
     * 奖金发放.
     * 
     * @param request
     *            上下文请求
     * @param combine
     *            发放条件
     * @return 响应
     */
    @RequestMapping(value = "/bm/release/release")
    @ResponseBody
    public ResponseData releaseBonus(HttpServletRequest request, @RequestBody BonusReleaseCombine combine) {
        ResponseData result = new ResponseData();
        IRequest iRequest = createRequestContext(request);
        String bonusType = combine.getBonusType();
        Long marketId = combine.getMarketId();
        SpmPeriod spmPeriod;
        spmPeriod = bonusService.getSpmPeriodByType(createRequestContext(request), bonusType, "issue", marketId);

        if (null == spmPeriod) {
            result.setSuccess(false);
            result.setMessage(nls(request, BonusException.MSG_ERROR_BM_WRONG_RELEASE_TIME));
            result.setCode(BonusException.MSG_ERROR_BM_WRONG_RELEASE_TIME);
            return result;
        }
        BonusRelease bonusRelease = new BonusRelease();
        // if (iRequest.getAttribute(SystemProfileConstants.MARKET_ID) != null)
        // {
        // bonusRelease
        // .setMarketId(Long.parseLong(iRequest.getAttribute(SystemProfileConstants.MARKET_ID).toString()));
        // }
        bonusRelease.setMarketId(marketId);
        bonusRelease.setPeriodId(spmPeriod.getPeriodId());
        bonusRelease.setStatus(BonusConstants.BONUS_RELEASE_STATUS_N);
        bonusRelease.setBonusType(bonusType);
        // 获取当月release记录条目数
        int count = bonusReleaseService.countBonusRelease(iRequest, bonusRelease);
        // 获取状态为new的retransfer记录条目数
        BonusRetransfer retransferOrder = new BonusRetransfer();
        // if (iRequest.getAttribute(SystemProfileConstants.MARKET_ID) != null)
        // {
        // retransferOrder
        // .setMarketId(Long.parseLong(iRequest.getAttribute(SystemProfileConstants.MARKET_ID).toString()));
        // }
        retransferOrder.setMarketId(marketId);
        retransferOrder.setStatus("NEW");
        List<BonusRetransfer> retransferList = retransferService.queryRetransfer(iRequest, retransferOrder, 1, -1);
        if (count <= 0 && retransferList.isEmpty()) {
            result.setSuccess(false);
            result.setMessage(nls(request, BonusException.MSG_ERROR_BM_WRONG_RELEASE_TIME));
            result.setCode(BonusException.MSG_ERROR_BM_WRONG_RELEASE_TIME);
        } else {
            List<BonusReleaseCombine> combines = new ArrayList<BonusReleaseCombine>();
            combine.setPeriodId(spmPeriod.getPeriodId());
            combines.add(combine);
            result.setRows(combines);
        }
        return result;
    }

    /**
     * 奖金回退.
     * 
     * @param request
     *            统一上下文.
     * @param combine
     *            回退条件.
     * @return 返回结果.
     */
    @RequestMapping(value = "/bm/release/rollback")
    @ResponseBody
    public ResponseData rollbackRelease(HttpServletRequest request, @RequestBody BonusReleaseCombine combine) {
        ResponseData result = new ResponseData();
        IRequest iRequest = createRequestContext(request);
        String bonusType = combine.getBonusType();
        Long marketId = combine.getMarketId();
        // 获取对应奖金类型的最近关闭的奖金期间id
        SpmPeriod spmPeriod;
        spmPeriod = bonusService.getSpmPeriodByType(createRequestContext(request), bonusType, "issueBack", marketId);
        if (null == spmPeriod) {
            result.setSuccess(false);
            result.setMessage(nls(request, BonusException.MSG_ERROR_BM_WRONG_RELEASE_ROLLBACK));
            result.setCode(BonusException.MSG_ERROR_BM_WRONG_RELEASE_ROLLBACK);
            return result;
        }
        // 判断是否存在最终奖金记录回退，若没有则提示
        BonusFinal bonusFinal = new BonusFinal();
        bonusFinal.setPeriodId(spmPeriod.getPeriodId());
        bonusFinal.setBonusType(bonusType);
        bonusFinal.setMarketId(marketId);
        List<BonusFinal> finalBonus = bonusFinalService.queryBonusFinal(iRequest, bonusFinal, 1, -1);
        if (0 >= finalBonus.size()) {
            result.setSuccess(false);
            result.setMessage(nls(request, BonusException.MSG_ERROR_BM_WRONG_RELEASE_ROLLBACK));
            result.setCode(BonusException.MSG_ERROR_BM_WRONG_RELEASE_ROLLBACK);
            return result;
        } else {
            // 判断是否已经执行过自动支付失败操作
            if (BonusConstants.BONUS_FLAG_Y.equals(finalBonus.get(0).getAutoFaliledFlag())) {
                // 已经执行过自动支付失败操作，无法回退
                result.setSuccess(false);
                result.setMessage(nls(request, BonusException.MSG_ERROR_BM_WRONG_ROLLBACK_AUTO));
                result.setCode(BonusException.MSG_ERROR_BM_WRONG_ROLLBACK_AUTO);
                return result;
            } else {
                bonusReleaseService.rollbackRelease(iRequest, spmPeriod, bonusType);
                result.setSuccess(true);
                return result;
            }
        }
    }

    /**
     * combine奖金发放.
     * 
     * @param request
     *            上下文请求
     * @param combine
     *            奖金combine
     * @return 响应
     * @throws CommMemberException
     *             抛出账务事务处理异常
     */
    @RequestMapping(value = "/bm/release/export")
    @ResponseBody
    public ResponseData export(HttpServletRequest request, @RequestBody BonusReleaseCombine combine)
            throws CommMemberException {
        ResponseData result = new ResponseData();
        IRequest iRequest = createRequestContext(request);
        if (iRequest.getAttribute(SystemProfileConstants.MARKET_ID) != null) {
            combine.setMarketId(Long.parseLong(iRequest.getAttribute(SystemProfileConstants.MARKET_ID).toString()));
        }
        bonusReleaseService.exportBonus(iRequest, combine);
        return result;
    }

    /**
     * 冲销节余查询.
     * 
     * @param request
     *            上下文请求
     * @param memWithdraw
     *            查询条件
     * @return 冲销节余列表
     */
    @RequestMapping(value = "/bm/release/withdraw")
    @ResponseBody
    public ResponseData getWithdraws(HttpServletRequest request, MemWithdraw memWithdraw) {
        ResponseData result = new ResponseData();
        IRequest iRequest = createRequestContext(request);
        if (iRequest.getAttribute(SystemProfileConstants.MARKET_ID) != null) {
            memWithdraw.setMarketId(Long.parseLong(iRequest.getAttribute(SystemProfileConstants.MARKET_ID).toString()));
        }
        List<MemWithdraw> memWithdraws = bonusReleaseService.getMemWithdraw(iRequest, memWithdraw);
        result.setRows(memWithdraws);
        return result;
    }

    /**
     * 查询市场.
     * 
     * @param request
     *            上下文请求
     * @param marketId
     *            市场id
     * @return 市场
     */
    @RequestMapping(value = "/bm/release/market")
    @ResponseBody
    public SpmMarket getMarket(HttpServletRequest request, Long marketId) {
        IRequest iRequest = createRequestContext(request);
        /*
         * Long marketId = 0L; if
         * (iRequest.getAttribute(SystemProfileConstants.MARKET_ID) != null) {
         * marketId =
         * Long.parseLong(iRequest.getAttribute(SystemProfileConstants.MARKET_ID
         * ).toString()); }
         */
        return spmMarketService.queryByMarketId(createRequestContext(request), marketId);
    }

    /**
     * 根据奖金类型获取奖金期间.
     * 
     * @param request
     *            统一上下文.
     * @param bonusType
     *            奖金类型.
     * @param queryType
     *            查询类型.
     * @return 返回结果.
     */
    @RequestMapping(value = "/bm/bonustype/period")
    @ResponseBody
    public ResponseData getPeriodByBonusType(HttpServletRequest request, String bonusType, String queryType,
            Long marketId) {
        IRequest iRequest = createRequestContext(request);
        SpmPeriod spmPeriod = bonusService.getSpmPeriodByType(iRequest, bonusType, queryType, marketId);
        List<SpmPeriod> list = new ArrayList<SpmPeriod>();
        if (null != spmPeriod) {
            list.add(spmPeriod);
        }
        ResponseData result = new ResponseData(list);
        return result;
    }

    /**
     * 自动combine retransfer数据和冲销节余数据.
     * 
     * @param request
     *            统一上下文.
     * @param combine
     *            奖金类型和奖金期间
     * @return 返回结果.
     * @throws CommMemberException
     *             抛出账务事务处理异常
     */
    @RequestMapping(value = "/bm/release/combine")
    @ResponseBody
    public ResponseData autoCombineRelease(HttpServletRequest request, @RequestBody BonusReleaseCombine combine)
            throws CommMemberException {
        IRequest iRequest = createRequestContext(request);
        ResponseData result = new ResponseData();
        Long marketId = combine.getMarketId();
        // 获取状态为new的retransfer
        BonusRetransfer retransferOrder = new BonusRetransfer();
        // if (iRequest.getAttribute(SystemProfileConstants.MARKET_ID) != null)
        // {
        // retransferOrder
        // .setMarketId(Long.parseLong(iRequest.getAttribute(SystemProfileConstants.MARKET_ID).toString()));
        // }
        retransferOrder.setMarketId(marketId);
        retransferOrder.setStatus("NEW");
        List<BonusRetransfer> retransferList = retransferService.queryRetransfer(iRequest, retransferOrder, 1, -1);
        // 获取状态为new的冲销节余记录
        // MemWithdraw memWithdraw = new MemWithdraw();
        // memWithdraw.setStatus("NEW");
        // if (iRequest.getAttribute(SystemProfileConstants.MARKET_ID) != null)
        // {
        // memWithdraw.setMarketId(Long.parseLong(iRequest.getAttribute(SystemProfileConstants.MARKET_ID).toString()));
        // }
        // List<MemWithdraw> memWithdraws =
        // bonusReleaseService.getMemWithdraw(iRequest, memWithdraw);
        // 组合combine
        List<MemWithdraw> memWithdraws = new ArrayList<MemWithdraw>();
        combine.setMemWithdraws(memWithdraws);
        combine.setRetransfers(retransferList);

        // if ("LOCAL".equals(combine.getBonusType())) {
        // combine.setMemWithdraws(memWithdraws);
        // } else {
        // List<MemWithdraw> memWithdrawsTemp = new ArrayList<MemWithdraw>();
        // combine.setMemWithdraws(memWithdrawsTemp);
        // }
        // List<BonusRetransfer> retransferTempList = new
        // ArrayList<BonusRetransfer>();
        // for (BonusRetransfer temp : retransferList) {
        // if (combine.getBonusType().equals(temp.getBonusType())) {
        // retransferTempList.add(temp);
        // }
        // }
        // combine.setRetransfers(retransferTempList);

        // if (iRequest.getAttribute(SystemProfileConstants.MARKET_ID) != null)
        // {
        // combine.setMarketId(Long.parseLong(iRequest.getAttribute(SystemProfileConstants.MARKET_ID).toString()));
        // }
        bonusReleaseService.exportBonus(iRequest, combine);
        return result;
    }

    /**
     * 根据最终奖金查询相关月度奖金.
     * 
     * @param request
     *            统一上下文.
     * @param bonusId
     *            最终奖金id.
     * @return 返回查询结果.
     */
    @RequestMapping(value = "/bm/release/querydetail")
    @ResponseBody
    public ResponseData queryDetailByFinal(HttpServletRequest request, @RequestParam("bonusId") String bonusId) {
        IRequest iRequest = createRequestContext(request);
        BonusFinal bonusFinal = new BonusFinal();
        bonusFinal.setBonusId(Long.parseLong(bonusId));
        List<BonusRelease> details = bonusReleaseService.queryDetailByFinal(iRequest, bonusFinal);
        ResponseData result = new ResponseData(details);
        return result;
    }
}
