/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.integration.dapp.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lkkhpg.dsis.admin.integration.dapp.dto.GetCouponsRequest;
import com.lkkhpg.dsis.admin.integration.dapp.dto.GetCouponsResponse;
import com.lkkhpg.dsis.admin.integration.dapp.service.IDAppUtilService;
import com.lkkhpg.dsis.admin.integration.dapp.service.IGetCouponsService;
import com.lkkhpg.dsis.common.config.dto.SpmMarket;
import com.lkkhpg.dsis.common.config.mapper.SpmMarketMapper;
import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.common.promotion.dto.Voucher;
import com.lkkhpg.dsis.common.promotion.mapper.VoucherMapper;
import com.lkkhpg.dsis.common.service.IMarketParamService;
import com.lkkhpg.dsis.integration.dapp.exception.DAppException;
import com.lkkhpg.dsis.platform.constant.BaseConstants;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.impl.RequestHelper;

/**
 * @author songyuanhuang 获取优惠券实现类
 */
@Service
@Transactional
public class GetCouponsServiceImpl implements IGetCouponsService {

    @Autowired
    private VoucherMapper voucherMapper;

    @Autowired
    private IMarketParamService marketParamService;

    @Autowired
    private SpmMarketMapper spmMarketMapper;
    
    @Autowired
    private IDAppUtilService dAppUtilService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<GetCouponsResponse> getCoupons(GetCouponsRequest getCouponsRequest) throws DAppException {

        Map<String, Object> map = new HashMap<String, Object>();
        List<String> voucherCodeList = new ArrayList<String>();
        map.put("market", getCouponsRequest.getMarket());
        map.put("memberCode", getCouponsRequest.getDistributorNumber());
        // CouponCodes入参可能以逗号分隔传多个
        if (null != getCouponsRequest.getCouponCodes()) {
            if (getCouponsRequest.getCouponCodes().contains(",")) {
                String[] couponCodes = getCouponsRequest.getCouponCodes().split(",");
                for (String couponCode : couponCodes) {
                    voucherCodeList.add(couponCode);
                }
            } else {
                voucherCodeList.add(getCouponsRequest.getCouponCodes());
            }
            if (voucherCodeList.size() > 0) {
                map.put("voucherCodes", voucherCodeList);
            }
        }

        List<Voucher> voucherList = voucherMapper.selectVIPForDapp(map);

        IRequest iRequest = RequestHelper.newEmptyRequest();
        SpmMarket market = spmMarketMapper.selectMarketWithoutEnabled(getCouponsRequest.getMarket());
        iRequest.setLocale(BaseConstants.DEFAULT_LANG);
        iRequest.setAccountId(dAppUtilService.getDappAccountId());
        iRequest.setAttribute(SystemProfileConstants.MARKET_ID, market.getMarketId());
        // 获取组织参数 - 币种
        String currency = marketParamService.getParamValue(iRequest, SystemProfileConstants.SPM_CURRENCY,
                SystemProfileConstants.MARKET, market.getMarketId().toString(), SystemProfileConstants.ORG_TYPE_MARKET);

        List<GetCouponsResponse> getCouponsResponses = new ArrayList<GetCouponsResponse>();
        for (Voucher voucher : voucherList) {
            GetCouponsResponse getCouponsResponse = new GetCouponsResponse();
            getCouponsResponse.setCouponCode(voucher.getVoucherCode());
            getCouponsResponse.setCouponName(voucher.getName());
            getCouponsResponse.setCurrency(currency);
            String status = null;
            String usedFlag = null;
            if ("Y".equals(voucher.getEnabledFlag())) {
                status = "ACTV";
            } else {
                status = "TERMI";
            }
            if (voucher.getOrderNumber() != null) {
                usedFlag = "Y";
            } else {
                usedFlag = "N";
            }
            getCouponsResponse.setStatus(status);
            getCouponsResponse.setCouponAmt(voucher.getOrderAmount().doubleValue());
            getCouponsResponse.setEffectDateFrom(voucher.getStartActiveDate());
            getCouponsResponse.setEffectDateTo(voucher.getEndActiveDate());
            getCouponsResponse.setDistributorNumber(voucher.getMemberCode());
            getCouponsResponse.setUsedFlag(usedFlag);
            getCouponsResponse.setOrderNumber(voucher.getOrderNumber());
            getCouponsResponses.add(getCouponsResponse);
        }
        return getCouponsResponses;
    }

}
