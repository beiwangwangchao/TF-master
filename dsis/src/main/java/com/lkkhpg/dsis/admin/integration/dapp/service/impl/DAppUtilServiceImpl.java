/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.integration.dapp.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.StringUtil;
import com.lkkhpg.dsis.admin.integration.dapp.constant.IntegrationConstant;
import com.lkkhpg.dsis.admin.integration.dapp.exception.IntegrationException;
import com.lkkhpg.dsis.admin.integration.dapp.service.IDAppUtilService;
import com.lkkhpg.dsis.common.config.dto.SpmTax;
import com.lkkhpg.dsis.common.config.mapper.SpmTaxMapper;
import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.common.service.IMarketParamService;
import com.lkkhpg.dsis.common.service.IParamService;
import com.lkkhpg.dsis.integration.dapp.exception.DAppException;
import com.lkkhpg.dsis.platform.constant.BaseConstants;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.impl.RequestHelper;
import com.lkkhpg.dsis.platform.dto.system.Account;
import com.lkkhpg.dsis.platform.service.IAccountService;
import com.lkkhpg.dsis.platform.service.ICodeService;

/**
 * D-App接口工具实现类.
 * 
 * @author frank.li
 *
 */
@Service
@Transactional
public class DAppUtilServiceImpl implements IDAppUtilService {

    private final Logger logger = LoggerFactory.getLogger(DAppUtilServiceImpl.class);
    @Autowired
    private ICodeService codeService;
    @Autowired
    private IAccountService accountService;
    @Autowired
    private IParamService paramService;
    @Autowired
    private IMarketParamService marketParamService;
    @Autowired
    private SpmTaxMapper spmTaxMapper;

    @Override
    public String getOrgCode(String gdsOrgCode) throws DAppException {
        IRequest request = RequestHelper.newEmptyRequest();
        request.setLocale(BaseConstants.DEFAULT_LANG);
        String orgCode = codeService.getCodeMeaningByValue(request, IntegrationConstant.GDS_ORG_CODE_MAPPING,
                gdsOrgCode);
        if (orgCode == null) {
            throw new DAppException(IntegrationException.MSG_ERROR_ORG_CODE_NO_MAPPING,
                    IntegrationConstant.STATUS_CODE_SYSTEM, "gdsOrgCode=" + gdsOrgCode);
        }
        return orgCode;
    };

    @Override
    public String getGdsOrgCode(String orgCode) throws DAppException {
        IRequest request = RequestHelper.newEmptyRequest();
        request.setLocale(BaseConstants.DEFAULT_LANG);
        String gdsOrgCode = codeService.getCodeValueByMeaning(request, IntegrationConstant.GDS_ORG_CODE_MAPPING,
                orgCode);
        if (gdsOrgCode == null) {
            throw new DAppException(IntegrationException.MSG_ERROR_ORG_CODE_NO_MAPPING,
                    IntegrationConstant.STATUS_CODE_SYSTEM, "orgCode=" + orgCode);
        }
        return gdsOrgCode;
    };

    @Override
    public String getGdsLang(String lang) throws DAppException {
        IRequest request = RequestHelper.newEmptyRequest();
        request.setLocale(BaseConstants.DEFAULT_LANG);
        // 支持大小写输入
        lang = lang.toUpperCase();
        String gdsLang = codeService.getCodeMeaningByValue(request, IntegrationConstant.GDS_LANG_MAPPING, lang);
        if (gdsLang == null) {
            throw new DAppException(IntegrationException.MSG_ERROR_LANG_NO_MAPPING,
                    IntegrationConstant.STATUS_CODE_SYSTEM, "lang=" + lang);
        }
        return gdsLang;
    };

    @Override
    public String toDateTimeString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        return sdf.format(date);
    }

    @Override
    public String toDateString(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(date);
    }

    @Override
    public String toPeriodString(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMM");
        return formatter.format(date);
    }

    @Override
    public Date dateTimeStringToDate(String dateTime) throws DAppException {
        // SimpleDateFormat sdf = new
        // SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        try {
            return sdf.parse(dateTime);
        } catch (ParseException e) {
            if (logger.isErrorEnabled()) {
                logger.error(IntegrationException.MSG_ERROR_DAPP_DATE_FORMAT_ERROR + e.getMessage(), e);
            }
            throw new DAppException(IntegrationException.MSG_ERROR_DAPP_DATE_FORMAT_ERROR,
                    IntegrationConstant.STATUS_CODE_SYSTEM, "dateTime=" + dateTime);
        }
    }

    @Override
    public Date dateStringToDate(String date) throws DAppException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            if (logger.isErrorEnabled()) {
                logger.error(IntegrationException.MSG_ERROR_DAPP_DATE_FORMAT_ERROR + e.getMessage(), e);
            }
            throw new DAppException(IntegrationException.MSG_ERROR_DAPP_DATE_FORMAT_ERROR,
                    IntegrationConstant.STATUS_CODE_SYSTEM, "date=" + date);
        }
    }

    @Override
    public Long getDappAccountId() throws DAppException {
        Account dappAccount = accountService.selectByLoginName(IntegrationConstant.DAPP_ACCOUNT_NAME);
        if (null == dappAccount || StringUtil.isEmpty(dappAccount.getLoginName())) {
            throw new DAppException(IntegrationConstant.ISG_ERROR_DAPP_USER_MISSING,
                    IntegrationConstant.STATUS_CODE_SYSTEM, IntegrationConstant.ISG_ERROR_DAPP_USER_MISSING);
        }
        return dappAccount.getAccountId();
    }

    @Override
    public BigDecimal getTaxBySalesOrg(IRequest request) {
        String enableTax = null;
        String priceIncludeTax = null;
        String taxCode = null;
        // 获取销售组织参数 - 是否启用税
        List<String> enableTaxs = paramService.getSalesParamValues(request, SystemProfileConstants.SPM_ENABLE_TAX,
                request.getAttribute(SystemProfileConstants.SALES_ORG_ID));
        // 获取销售组织参数 - 商品价格是否含税
        List<String> priceIncludeTaxs = paramService.getSalesParamValues(request,
                SystemProfileConstants.SPM_PRICE_INCLUDE_TAX,
                request.getAttribute(SystemProfileConstants.SALES_ORG_ID));
     // 获取销售组织参数 - 税码
        List<String> taxCodes = paramService.getSalesParamValues(request, SystemProfileConstants.SPM_TAX_CODE,
                request.getAttribute(SystemProfileConstants.SALES_ORG_ID));
        
        
        // 如果销售组织参数为空，则取市场组织参数
        Long marketId = request.getAttribute(SystemProfileConstants.MARKET_ID);
        if (null == enableTaxs || enableTaxs.size() == 0) {
            enableTax = marketParamService.getParamValue(request, SystemProfileConstants.SPM_ENABLE_TAX,
                    SystemProfileConstants.MARKET, String.valueOf(marketId),
                    SystemProfileConstants.ORG_TYPE_MARKET);
        } else {
            enableTax = enableTaxs.get(0);
        }

        if (null == priceIncludeTaxs || priceIncludeTaxs.size() == 0) {
            priceIncludeTax = marketParamService.getParamValue(request, SystemProfileConstants.SPM_PRICE_INCLUDE_TAX,
                    SystemProfileConstants.MARKET, String.valueOf(marketId),
                    SystemProfileConstants.ORG_TYPE_MARKET);
        } else {
            priceIncludeTax = priceIncludeTaxs.get(0);
        }
        
        if (null == taxCodes || taxCodes.size() == 0) {
            taxCode = marketParamService.getParamValue(request, SystemProfileConstants.SPM_TAX_CODE,
                    SystemProfileConstants.MARKET, String.valueOf(marketId),
                    SystemProfileConstants.ORG_TYPE_MARKET);
        } else {
            taxCode = taxCodes.get(0);
        }

        // 按马来的计算方式
        if (BaseConstants.YES.equals(enableTax) && BaseConstants.NO.equals(priceIncludeTax)) {
            if (taxCodes != null && taxCodes.size() > 0) {
                SpmTax tax = spmTaxMapper.getTaxByCode(taxCode);
                return tax.getTaxPercent();
            }
        }
        return null;
    }
}
