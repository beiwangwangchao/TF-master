/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.lkkhpg.dsis.common.config.dto.SpmTax;
import com.lkkhpg.dsis.common.config.mapper.SpmTaxMapper;
import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.common.service.IParamService;
import com.lkkhpg.dsis.common.service.ITaxService;
import com.lkkhpg.dsis.platform.core.IRequest;

/**
 * 税率Service实现类.
 * 
 * @author chenjingxiong
 */
@Service
@Transactional
public class TaxServiceImpl implements ITaxService {

    private Logger logger = LoggerFactory.getLogger(TaxServiceImpl.class);

    @Autowired
    private SpmTaxMapper spmTaxMapper;
    @Autowired
    private IParamService paramService;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public SpmTax getTax(IRequest request) {
        Long salesOrgId = request.getAttribute(SystemProfileConstants.SALES_ORG_ID);
        List<String> enableTaxs = paramService.getSalesParamValues(request, SystemProfileConstants.SPM_ENABLE_TAX,
                salesOrgId);
        // 不启用税时，直接返回空.
        if (enableTaxs == null || enableTaxs.isEmpty()
                || SystemProfileConstants.NO.equals(enableTaxs.iterator().next())) {
            return null;
        }

        // 税码查询.
        List<String> taxCodes = paramService.getSalesParamValues(request, SystemProfileConstants.SPM_TAX_CODE,
                salesOrgId);

        if (taxCodes == null || taxCodes.isEmpty()) {
            return null;
        }

        SpmTax spmTax = spmTaxMapper.getTaxByCode(taxCodes.iterator().next());

        // 价格是否含税查询.

        List<String> priceIncludes = paramService.getSalesParamValues(request,
                SystemProfileConstants.SPM_PRICE_INCLUDE_TAX, salesOrgId);
        if (priceIncludes == null || priceIncludes.isEmpty()
                || SystemProfileConstants.NO.equals(priceIncludes.iterator().next())) {
            spmTax.setPriceInclueTax(Boolean.FALSE);
        } else {
            spmTax.setPriceInclueTax(Boolean.TRUE);
        }
        return spmTax;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public SpmTax getTaxBySalesOrg(IRequest request, Long salesOrgId) {
        List<String> enableTaxs = paramService.getSalesParamValues(request, SystemProfileConstants.SPM_ENABLE_TAX,
                salesOrgId);
        // 不启用税时，直接返回空.
        if (enableTaxs == null || enableTaxs.isEmpty()
                || SystemProfileConstants.NO.equals(enableTaxs.iterator().next())) {
            return null;
        }

        // 税码查询.
        List<String> taxCodes = paramService.getSalesParamValues(request, SystemProfileConstants.SPM_TAX_CODE,
                salesOrgId);

        if (taxCodes == null || taxCodes.isEmpty()) {
            return null;
        }

        SpmTax spmTax = spmTaxMapper.getTaxByCode(taxCodes.iterator().next());

        // 价格是否含税查询.

        List<String> priceIncludes = paramService.getSalesParamValues(request,
                SystemProfileConstants.SPM_PRICE_INCLUDE_TAX, salesOrgId);
        if (priceIncludes == null || priceIncludes.isEmpty()
                || SystemProfileConstants.NO.equals(priceIncludes.iterator().next())) {
            spmTax.setPriceInclueTax(Boolean.FALSE);
        } else {
            spmTax.setPriceInclueTax(Boolean.TRUE);
        }
        return spmTax;
    }

    @Override
    public BigDecimal getTaxAmount(IRequest request, BigDecimal amount) {
        SpmTax spmTax = self().getTax(request);
        return getTaxAmount(request, amount, spmTax);
    }

    @Override
    public List<SpmTax> queryTax(IRequest request, SpmTax tax) {
        return spmTaxMapper.queryByTax(tax);
    }

    @Override
    public List<SpmTax> queryTaxPercentByParams(IRequest request, String paramName, String orgType, Long orgId) {
        // 获取税码
        List<String> params = paramService.getParamValues(request, paramName, orgType, orgId);
        if (params.isEmpty() || StringUtils.isEmpty(params.get(0))) {
            if (logger.isDebugEnabled()) {
                logger.debug("Tax code not found. SalesOrgId : {}", new Object[] { orgId });
                return null;
            }
        }
        // 根据税码获取税率
        SpmTax spmTax = new SpmTax();
        spmTax.setTaxCode(params.get(0));
        List<SpmTax> spmTaxs = self().queryTax(request, spmTax);
        if (spmTaxs.isEmpty()) {
            if (logger.isDebugEnabled()) {
                logger.debug("Tax percent not found. TaxCode : {}", new Object[] { spmTax.getTaxCode() });
                return null;
            }
        }
        return spmTaxs;
    }

    @Override
    public BigDecimal getTaxAmount(IRequest request, BigDecimal amount, SpmTax tax) {
        BigDecimal taxAmount = BigDecimal.ZERO;
        // 不计税
        if (tax == null) {
            return taxAmount;
        }
        BigDecimal taxRate = tax.getTaxPercent().divide(SystemProfileConstants.ONE_HUNDRED,
                SystemProfileConstants.BIGDECIMAL_SCALE, BigDecimal.ROUND_HALF_UP);
        if (tax.getPriceInclueTax() != null && tax.getPriceInclueTax()) {
            // 单价已含税
            // 税额=商品价格 - (商品价格 / (1+ 税率))
            BigDecimal noTaxAmount = amount.divide(BigDecimal.ONE.add(taxRate), SystemProfileConstants.BIGDECIMAL_SCALE,
                    BigDecimal.ROUND_HALF_UP);
            taxAmount = amount.subtract(noTaxAmount);
        } else {
            // 单价为含税
            // 税额=商品价格 *税率
            taxAmount = amount.multiply(taxRate);
        }
        return taxAmount;
    }

}
