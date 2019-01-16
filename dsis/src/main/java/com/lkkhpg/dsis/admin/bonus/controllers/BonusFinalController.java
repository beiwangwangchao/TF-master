/*
 *
 */
package com.lkkhpg.dsis.admin.bonus.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lkkhpg.dsis.admin.bonus.service.IBonusFinalService;
import com.lkkhpg.dsis.admin.bonus.service.IBonusService;
import com.lkkhpg.dsis.admin.export.utils.ExportUtils;
import com.lkkhpg.dsis.common.bonus.dto.BonusFinal;
import com.lkkhpg.dsis.common.bonus.dto.BonusReleaseCombine;
import com.lkkhpg.dsis.common.config.dto.SpmPeriod;
import com.lkkhpg.dsis.common.exception.CommBonusException;
import com.lkkhpg.dsis.common.exception.CommMemberException;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.ResponseData;

/**
 * 最终奖金controller.
 * 
 * @author gulin
 *
 */
@Controller
public class BonusFinalController extends BaseController {
    public static final String MSG_ERROR_BM_WRONG_PAY = "msg.error.bm_wrong_pay";

    public static final String MSG_ERROR_BM_WRONG_PAIED = "msg.error.bm_wrong_paied";

    public static final String MSG_ERROR_BM_WRONG_PAY_UNLOCK = "msg.error.bm_wrong_pay_unlock";

    public static final String MSG_ERROR_BM_WRONG_NO_PAIED = "msg.error.bm_wrong_no_paied";

    @Autowired
    private IBonusFinalService bonusFinalService;

    @Autowired
    private IBonusService bonusService;

    /**
     * 查询最终奖金.
     * 
     * @param request
     *            统一上下文.
     * @param response
     * @param bonusFinal
     *            查询条件.
     * @param page
     *            页码.
     * @param pagesize
     *            页面size.
     * @return 返回最终奖金集合.
     * @throws IOException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    @RequestMapping(value = "bm/bonusfinal/query")
    @ResponseBody
    public ResponseData queryBonusFinal(HttpServletRequest request, HttpServletResponse response, BonusFinal bonusFinal,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize)
            throws IllegalArgumentException, IllegalAccessException, IOException {
        IRequest iRequest = createRequestContext(request);
        List<BonusFinal> bonus = bonusFinalService.queryBonusFinal(iRequest, bonusFinal, page, pagesize);
        if (request.getParameter("isExport2Excel") != null && request.getParameter("isExport2Excel").equals("Y")) {
            ExportUtils.export(request, response, bonus);
            return null;
        } else {
            return new ResponseData(bonus);
        }
    }

    /**
     * 最终奖金支付失败.
     * 
     * @param request
     *            统一上下文.
     * @param bonusFinal
     *            进行操作的奖金期间数据.
     * @return 操作成功，返回success，失败返回error
     * @throws CommMemberException
     *             抛出账务事务处理异常
     */
    @RequestMapping(value = "bm/payfail")
    @ResponseBody
    public ResponseData payFail(HttpServletRequest request, @RequestBody BonusFinal bonusFinal)
            throws CommMemberException {
        IRequest iRequest = createRequestContext(request);
        List<String> result = bonusFinalService.checkBonusAndUpdate(iRequest, bonusFinal);
        ResponseData data = new ResponseData(result);
        return data;
    }

    /**
     * 生成最终奖金文件.
     * 
     * @param request
     *            统一上下文.
     * @param response
     *            响应.
     * @param periodId
     *            奖金区间id.
     * @param bonusType
     *            奖金类型.
     * @param marketId
     *            市场id.
     * @throws CommBonusException
     *             文件生成异常.
     */
    @RequestMapping(value = "/bm/bonusfinal/download")
    public void downloadFile(HttpServletRequest request, HttpServletResponse response, Long periodId, String bonusType,
            Long marketId) throws CommBonusException {
        IRequest iRequest = createRequestContext(request);
        bonusFinalService.downloadFinalBonus(iRequest, response, periodId, bonusType, marketId);
    }

    /**
     * 获取已关闭的奖金区间.
     * 
     * @param request
     *            统一上下文.
     * @param marketId
     *            市场id.
     * @return 返回奖金区间集合.
     */
    @RequestMapping(value = "/bm/bonusfinal/getPeriod")
    @ResponseBody
    public List<SpmPeriod> getBonusPeriod(HttpServletRequest request, @RequestParam("marketId") Long marketId) {
        return bonusService.getCloseSpmPeriod(createRequestContext(request), marketId);
    }

    /**
     * 检查是否有奖金记录.
     * 
     * @param request
     *            统一上下文.
     * @param periodId
     *            奖金期间ID.
     * @param bonusType
     *            奖金类型.
     * @param marketId
     *            市场id.
     * @return 返回校验信息.
     */
    @RequestMapping(value = "bm/downfile/checkempty")
    @ResponseBody
    public ResponseData checkBonusRecord(HttpServletRequest request, Long periodId, String bonusType, Long marketId) {
        List<String> result = bonusFinalService.checkBonusRecord(createRequestContext(request), periodId, bonusType,
                marketId);
        ResponseData data = new ResponseData(result);
        return data;
    }

    /**
     * 奖金支付.
     * 
     * @param request
     *            统一上下文.
     * @param combine
     *            支付时间及类型.
     * @return 支付结果.
     * @throws CommMemberException
     *             抛出会员账务异常.
     */
    @RequestMapping(value = "/bm/bonusfinal/pay")
    @ResponseBody
    public ResponseData bonusPay(HttpServletRequest request, @RequestBody BonusReleaseCombine combine)
            throws CommMemberException {
        ResponseData result = new ResponseData();
        IRequest iRequest = createRequestContext(request);
        String bonusType = combine.getBonusType();
        Long marketId = combine.getMarketId();
        // 获取对应奖金类型的最近关闭的奖金期间id
        SpmPeriod spmPeriod;
        spmPeriod = bonusService.getSpmPeriodByType(createRequestContext(request), bonusType, "pay", marketId);
        if (null == spmPeriod) {
            result.setSuccess(false);
            result.setMessage(nls(request, MSG_ERROR_BM_WRONG_PAY));
            result.setCode(MSG_ERROR_BM_WRONG_PAY);
            return result;
        }
        combine.setPeriodId(spmPeriod.getPeriodId());
        List<String> payResult = bonusFinalService.bonusPay(iRequest, combine);
        if ("empty".equals(payResult.get(0))) {
            result.setSuccess(false);
            result.setMessage(nls(request, MSG_ERROR_BM_WRONG_PAY));
            result.setCode(MSG_ERROR_BM_WRONG_PAY);
            return result;
        } else if ("error".equals(payResult.get(0))) {
            result.setSuccess(false);
            result.setMessage(nls(request, MSG_ERROR_BM_WRONG_PAIED));
            result.setCode(MSG_ERROR_BM_WRONG_PAIED);
            return result;
        }
        result.setSuccess(true);
        return result;
    }

    /**
     * 奖金支付解锁.
     * 
     * @param request
     *            统一上下文.
     * @param combine
     *            解锁条件.
     * @return 返回结果.
     */
    @RequestMapping(value = "/bm/bonusfinal/unlock")
    @ResponseBody
    public ResponseData bonusPayUnlock(HttpServletRequest request, @RequestBody BonusReleaseCombine combine) {
        ResponseData result = new ResponseData();
        IRequest iRequest = createRequestContext(request);
        String bonusType = combine.getBonusType();
        Long marketId = combine.getMarketId();
        // 获取对应奖金类型的最近关闭的奖金期间id
        SpmPeriod spmPeriod;
        spmPeriod = bonusService.getSpmPeriodByType(createRequestContext(request), bonusType, "payBack", marketId);
        if (null == spmPeriod) {
            result.setSuccess(false);
            result.setMessage(nls(request, MSG_ERROR_BM_WRONG_PAY_UNLOCK));
            result.setCode(MSG_ERROR_BM_WRONG_PAY_UNLOCK);
            return result;
        }
        combine.setPeriodId(spmPeriod.getPeriodId());
        List<String> unlockResult = bonusFinalService.unlockBonusFinal(iRequest, combine);
        if ("empty".equals(unlockResult.get(0))) {
            result.setSuccess(false);
            result.setMessage(nls(request, MSG_ERROR_BM_WRONG_PAY_UNLOCK));
            result.setCode(MSG_ERROR_BM_WRONG_PAY_UNLOCK);
            return result;
        } else if ("error".equals(unlockResult.get(0))) {
            result.setSuccess(false);
            result.setMessage(nls(request, MSG_ERROR_BM_WRONG_NO_PAIED));
            result.setCode(MSG_ERROR_BM_WRONG_NO_PAIED);
            return result;
        }
        result.setSuccess(true);
        return result;
    }

    /**
     * 奖金补发逻辑.
     * 
     * @param request
     *            统一上下文.
     * @param bonusFinal
     *            补发的奖金记录.
     * @return 返回补发结果.
     */
    @RequestMapping(value = "/bm/bonusfinal/reissue")
    @ResponseBody
    public ResponseData bonusReissue(HttpServletRequest request, @RequestBody BonusFinal bonusFinal) {
        IRequest iRequest = createRequestContext(request);
        List<String> result = bonusFinalService.bonusCheckAndReissue(iRequest, bonusFinal);
        ResponseData data = new ResponseData(result);
        return data;
    }

    /**
     * 更新最终奖金记录备注.
     * 
     * @param request
     *            统一上下文.
     * @param bonusId
     *            奖金id.
     * @param comments
     *            备注.
     * @return 返回结果.
     */
    @RequestMapping(value = "/bm/bonusfinal/updatecomments")
    @ResponseBody
    public ResponseData updateComments(HttpServletRequest request, Long bonusId, String comments) {
        IRequest iRequest = createRequestContext(request);
        BonusFinal bonusFinal = new BonusFinal();
        bonusFinal.setBonusId(bonusId);
        bonusFinal.setComments(comments);
        int num = bonusFinalService.updateComments(iRequest, bonusFinal);
        if (num > 0) {
            return new ResponseData(true);
        } else {
            return new ResponseData(false);
        }
    }
}
