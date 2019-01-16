/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lkkhpg.dsis.common.constant.OrderConstants;
import com.lkkhpg.dsis.common.exception.CommOrderException;
import com.lkkhpg.dsis.common.order.dto.SalesSites;
import com.lkkhpg.dsis.common.order.mapper.SalesSitesMapper;
import com.lkkhpg.dsis.common.service.ISalesSitesService;
import com.lkkhpg.dsis.platform.core.IRequest;

/**
 * 销售订单地址service实现类.
 * 
 * @author wuyichu
 */
@Service
@Transactional
public class SalesSitesServiceImpl implements ISalesSitesService {

    private Logger log = LoggerFactory.getLogger(SalesSitesServiceImpl.class);

    @Autowired
    private SalesSitesMapper salesSitesMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SalesSites submit(IRequest request, SalesSites salesSites) throws CommOrderException {
        if (salesSites == null
                || !(OrderConstants.ORDER_SITES_BILL.equals(salesSites.getSiteType())
                        || OrderConstants.ORDER_SITES_SHIP.equals(salesSites.getSiteType()))
                || salesSites.getHeaderId() == null) {
            if (log.isErrorEnabled()) {
                log.error("order sites type error or order id is null");
            }
            throw new CommOrderException(CommOrderException.MSG_ERROR_OM_SITES_ERROR,
                    new Object[] { request, salesSites });
        }
        if (StringUtils.isEmpty(salesSites.getName())  || StringUtils.isEmpty(salesSites.getCountryCode())
                || StringUtils.isEmpty(salesSites.getStateCode()) || StringUtils.isEmpty(salesSites.getCityCode())
                || StringUtils.isEmpty(salesSites.getAddress1())) {
            throw new CommOrderException(CommOrderException.MSG_ERROR_MEMBERSHIP_INFORMATION_IS_NOT_COMPLETE,
                    new Object[] { request, salesSites });
        }
        if (salesSites.getSalesSiteId() == null) {
            salesSitesMapper.insert(salesSites);
        } else {
            salesSitesMapper.updateByPrimaryKey(salesSites);
        }
        return salesSites;
    }

    @Override
    public int deleteSite(IRequest request, Long siteId) {
        if (siteId == null) {
            return 0;
        }
        return salesSitesMapper.deleteByPrimaryKey(siteId);
    }

    @Override
    public List<SalesSites> getSitesByHeaderId(IRequest request, Long headerId, String isAutoship) {
        return salesSitesMapper.selectSitesByHeaderIdAndAutoshipFlag(isAutoship, headerId);
    }

}
