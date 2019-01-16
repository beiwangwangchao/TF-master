/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.github.pagehelper.PageHelper;
import com.lkkhpg.dsis.common.config.dto.SpmCurrency;
import com.lkkhpg.dsis.common.config.dto.SpmMarket;
import com.lkkhpg.dsis.common.config.dto.SpmSalesOrganization;
import com.lkkhpg.dsis.common.config.mapper.SpmCurrencyMapper;
import com.lkkhpg.dsis.common.config.mapper.SpmMarketMapper;
import com.lkkhpg.dsis.common.constant.OrderConstants;
import com.lkkhpg.dsis.common.enums.SequenceType;
import com.lkkhpg.dsis.common.exception.CommOrderException;
import com.lkkhpg.dsis.common.member.dto.MemCard;
import com.lkkhpg.dsis.common.member.dto.MemSite;
import com.lkkhpg.dsis.common.member.dto.Member;
import com.lkkhpg.dsis.common.member.mapper.MemSiteMapper;
import com.lkkhpg.dsis.common.member.mapper.MemberMapper;
import com.lkkhpg.dsis.common.order.dto.AutoshipLine;
import com.lkkhpg.dsis.common.order.dto.AutoshipOrder;
import com.lkkhpg.dsis.common.order.dto.SalesLogistics;
import com.lkkhpg.dsis.common.order.dto.SalesSites;
import com.lkkhpg.dsis.common.order.mapper.AutoshipLineMapper;
import com.lkkhpg.dsis.common.order.mapper.AutoshipOrderMapper;
import com.lkkhpg.dsis.common.order.mapper.CreditCardMapper;
import com.lkkhpg.dsis.common.order.mapper.SalesLogisticsMapper;
import com.lkkhpg.dsis.common.order.mapper.SalesSitesMapper;
import com.lkkhpg.dsis.common.service.ICommAutoshipOrderService;
import com.lkkhpg.dsis.common.service.ICommMemberService;
import com.lkkhpg.dsis.common.service.ICommSalesOrderService;
import com.lkkhpg.dsis.common.service.ISalesSitesService;
import com.lkkhpg.dsis.common.service.ISpmSalesOrganizationService;
import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;

/**
 * 自动订货单查询实现类.
 * 
 * @author HuangJiaJing
 * @author wuyichu
 * 
 */
@Service
@Transactional
public class CommAutoshipOrderServiceImpl implements ICommAutoshipOrderService {

    @Autowired
    private AutoshipOrderMapper autoshipOrderMapper;
    @Autowired
    private AutoshipLineMapper autoshipLineMapper;
    @Autowired
    private SalesLogisticsMapper salesLogisticsMapper;
    @Autowired
    private CreditCardMapper creditCardMapper;
    @Autowired
    private MemSiteMapper memSiteMapper;
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private SpmMarketMapper spmMarketMapper;
    @Autowired
    private ISpmSalesOrganizationService spmSalesOrganizationService;
    @Autowired
    private ICommSalesOrderService commSalesOrderService;
    @Autowired
    private ISalesSitesService salesSitesService;
    @Autowired
    private ICommMemberService commMemberService;
    @Autowired
    private SalesSitesMapper salesSitesMapper;
    @Autowired
    private SpmCurrencyMapper spmCurrencyMapper;

    @Override
    public List<AutoshipOrder> selectAutoshipOrderParas(IRequest request, AutoshipOrder autoshipOrder, int page,
            int pageSize) {
        PageHelper.startPage(page, pageSize);
        List<AutoshipOrder> list = autoshipOrderMapper.selectAutoshipOrdersByParas(autoshipOrder);
        return list;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public AutoshipOrder submitAutoshipOrder(final IRequest request, final AutoshipOrder autoshipOrder)
            throws CommOrderException {
        if (autoshipOrder == null) {
            return null;
        }
        // 设置执行状态
        if (autoshipOrder.getExecuteStatus() == null) {
            autoshipOrder.setExecuteStatus(OrderConstants.AUTO_AUROSHIP_EXECUTE_STATUS_NE);
        }

        if (autoshipOrder.getSalesScore() == null) {
            autoshipOrder.setSalesScore(BigDecimal.ZERO);
        }
        Long id = autoshipOrderMapper.checkByMemberId(autoshipOrder.getMemberId());
        if (id != null) {
            autoshipOrder.setAutoshipId(id);
            autoshipOrderMapper.updateByPrimaryKey(autoshipOrder);
        } else {
            autoshipOrder.setAutoshipNumber(
                    commSalesOrderService.generateCode(request, SequenceType.AUTOSHIP, autoshipOrder));
            autoshipOrder.setAutoshipStatus(OrderConstants.AUTOSHIP_STATUS_ACV);
            autoshipOrderMapper.insert(autoshipOrder);
        }

        processAutoshipLine(request, autoshipOrder);

        SalesLogistics logistics = autoshipOrder.getLogistics();

        if (logistics != null) {
            if (logistics.getLogisticsId() != null) {
                logistics.setHeaderId(autoshipOrder.getAutoshipId());
                logistics.setAutoshipFlag(OrderConstants.YES);
                salesLogisticsMapper.updateByPrimaryKey(logistics);
            } else {
                logistics.setHeaderId(autoshipOrder.getAutoshipId());
                logistics.setAutoshipFlag(OrderConstants.YES);
                salesLogisticsMapper.insert(logistics);
            }
        } else {
            salesLogisticsMapper.deleteByHeaderId(autoshipOrder.getAutoshipId(), OrderConstants.YES);
        }
        if (autoshipOrder.getSalesSites() != null) {
            List<SalesSites> salesSites = autoshipOrder.getSalesSites();
            salesSitesMapper.deleteByHeaderIdAndAutoshipFlag(OrderConstants.YES, autoshipOrder.getAutoshipId());
            for (SalesSites sites : salesSites) {
                sites.setSalesSiteId(null);
                sites.setHeaderId(autoshipOrder.getAutoshipId());
                sites.setAutoshipFlag(OrderConstants.YES);
                salesSitesService.submit(request, sites);
            }
        }
        return autoshipOrder;
    }

    /**
     * 处理autoship订单行.
     * 
     * @param request
     *            请求的基础数据
     * @param autoshipOrder
     *            autoship订单
     */
    private void processAutoshipLine(final IRequest request, @StdWho final AutoshipOrder autoshipOrder) {
        List<AutoshipLine> lines = autoshipOrder.getLines();
        if (lines == null || lines.isEmpty()) {
            return;
        }
        for (AutoshipLine line : lines) {
            if (line.getLineId() != null) {
                line.setSalesOrgId(autoshipOrder.getSalesOrgId());
                autoshipLineMapper.updateByPrimaryKey(line);
            } else {
                line.setSalesOrgId(autoshipOrder.getSalesOrgId());
                line.setAutoshipId(autoshipOrder.getAutoshipId());
                autoshipLineMapper.insert(line);
            }
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int updateOrderStatus(IRequest request, Long autoShipId, String status) throws CommOrderException {
        if (autoShipId == null || StringUtils.isEmpty(status)) {
            return 0;
        }
        if (OrderConstants.AUTOSHIP_STATUS_ACV.equals(status) || OrderConstants.AUTOSHIP_STATUS_SUS.equals(status)) {
            Long id = autoshipOrderMapper.checkById(autoShipId);
            if (id != null) {
                throw new CommOrderException(CommOrderException.MSG_ERROR_AUTOSHIP_DUPLICATE_ERROR,
                        new Object[] { autoShipId, status, id });
            }
        }
        return autoshipOrderMapper.updateStatusByPrimaryKey(autoShipId, status);
    }

    @Override
    public AutoshipOrder getDetail(IRequest request, Long autoshipId) {
        AutoshipOrder order = autoshipOrderMapper.selectByPrimaryKey(autoshipId);
        if (order == null) {
            return null;
        }
        List<AutoshipLine> lines = autoshipLineMapper.selectByHeaderId(autoshipId);
        order.setLines(lines);
        SalesLogistics logistics = salesLogisticsMapper.selectByHeaderId(autoshipId, OrderConstants.YES);
        order.setLogistics(logistics);
        /*
         * CreditCard creditCard =
         * creditCardMapper.selectByPrimaryKey(order.getCreditCardId());
         * order.setCreditCard(creditCard);
         */

        // 信用卡
        List<MemCard> cards = commMemberService.autoQueryCard(request, order.getMemberId());
        order.setMemCard(cards);

        Member member = memberMapper.selectByPrimaryKey(order.getMemberId());
        List<MemSite> memSites = memSiteMapper.selectByMemberId(member.getMemberId());
        member.setMemSites(memSites);
        order.setMember(member);
        // SpmCurrency currency =
        // spmCurrencyMapper.selectByPrimaryKey(order.getCurrency());
        SpmSalesOrganization organization = spmSalesOrganizationService.getSalesOrganizationDetail(request,
                order.getSalesOrgId());
        organization.setCurrency(order.getCurrency());
        SpmMarket market = spmMarketMapper.selectByPrimaryKey(order.getMarketId());
        order.setSpmMarket(market);
        order.setSpmSalesOrganization(organization);
        List<SalesSites> salesSites = salesSitesService.getSitesByHeaderId(request, autoshipId, OrderConstants.YES);
        order.setSalesSites(salesSites);
        SpmCurrency currency = spmCurrencyMapper.selectByPrimaryKey(order.getCurrency());
        order.setSpmCurrency(currency);
        return order;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteLine(IRequest request, List<AutoshipLine> lines) {
        if (lines == null || lines.isEmpty()) {
            return;
        }
        for (AutoshipLine line : lines) {
            if (line.getLineId() != null) {
                autoshipLineMapper.deleteByPrimaryKey(line.getLineId());
            }
        }

    }

    @Override
    public Long checkMemberAutoship(IRequest request, Long memberId) {
        return autoshipOrderMapper.checkByMemberId(memberId);
    }

}
