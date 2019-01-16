/*
 *
 */
package com.lkkhpg.dsis.admin.config.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.lkkhpg.dsis.admin.config.service.ISpmTaxService;
import com.lkkhpg.dsis.common.config.dto.SpmTax;
import com.lkkhpg.dsis.common.config.mapper.SpmTaxMapper;
import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.common.exception.CommSystemProfileException;
import com.lkkhpg.dsis.platform.core.IRequest;

/**
 * 地址Service接口实现.
 *
 * @author frank.li
 */
@Service
@Transactional
public class SpmTaxServiceImpl implements ISpmTaxService {

    private final Logger logger = LoggerFactory.getLogger(SpmTaxServiceImpl.class);

    @Autowired
    private SpmTaxMapper spmTaxMapper;

    /**
     * 保存税率.
     *
     * @param request
     *            请求上下文
     * @param taxes
     *            税率List
     * @return 税率List
     * @throws CommSystemProfileException
     *             系统配置统一异常
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<SpmTax> saveTax(IRequest request, List<SpmTax> taxes) throws CommSystemProfileException {
        for (SpmTax spmTax : taxes) {
            if (spmTax.getTaxId() == null) {
                // 新增
                if (spmTaxMapper.getCountTaxByCode(spmTax.getTaxCode()) > 0) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("msg.error.config.tax_code_unique: {}", new Object[] { spmTax.getTaxCode() });
                    }
                    throw new CommSystemProfileException(CommSystemProfileException.MSG_ERROR_SPM_TAX_CODE_UNIQUE,
                            new Object[] { spmTax.getTaxCode() });
                } else {
                    spmTax.setEnabledFlag(SystemProfileConstants.YES);
                    spmTaxMapper.insert(spmTax);
                }
            } else {
                // 更改
                spmTaxMapper.updateByPrimaryKeySelective(spmTax);
            }
        }

        return taxes;
    }

    /**
     * 删除税率.
     *
     * @param request
     *            请求上下文
     * @param taxes
     *            税率List
     * @return boolean
     */
    @Override
    public boolean deleteTax(IRequest request, List<SpmTax> taxes) {
        for (SpmTax tax : taxes) {

            String status = tax.get__status();
            if (logger.isDebugEnabled()) {
                logger.debug("status: {0}", status);
            }

            spmTaxMapper.deleteByPrimaryKey(tax.getTaxId());
        }

        return true;
    }

    /**
     * 查询税率.
     *
     * @param request
     *            请求上下文
     * @param tax
     *            税率DTO
     * @return 税率List
     */
    @Override
    public List<SpmTax> queryTax(IRequest request, SpmTax tax, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return spmTaxMapper.queryByTax(tax);
    }

    @Override
    public List<SpmTax> queryForOrder(IRequest request, SpmTax tax) {
        // TODO Auto-generated method stub
        return spmTaxMapper.queryForOrder(tax);
    }

}
