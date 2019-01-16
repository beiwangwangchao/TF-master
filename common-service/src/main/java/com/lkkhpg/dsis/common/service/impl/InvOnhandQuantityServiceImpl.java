/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.service.impl;

import com.github.pagehelper.PageHelper;
import com.lkkhpg.dsis.common.config.dto.SpmCompany;
import com.lkkhpg.dsis.common.config.mapper.SpmCompanyMapper;
import com.lkkhpg.dsis.common.config.mapper.SpmSupplyMapper;
import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.common.inventory.dto.InvOnhandQuantity;
import com.lkkhpg.dsis.common.inventory.mapper.InvOnhandQuantityMapper;
import com.lkkhpg.dsis.common.member.dto.Member;
import com.lkkhpg.dsis.common.service.ICommMemberService;
import com.lkkhpg.dsis.common.service.IInvOnhandQuantityService;
import com.lkkhpg.dsis.common.service.ISpmMarketService;
import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * 库存现有量Service接口实现.
 *
 * @author hanrui.huang
 */
@Service
@Transactional
public class InvOnhandQuantityServiceImpl implements IInvOnhandQuantityService {

    private static final String CHECK_STATUS_Y = "Y";

    @Autowired
    private InvOnhandQuantityMapper invOnhandQuantityMapper;

    @Autowired
    private SpmCompanyMapper spmCompanyMapper;

    @Autowired
    private ICommMemberService commMemberService;

    @Autowired
    private ISpmMarketService spmMarketService;

    @Autowired
    private SpmSupplyMapper spmSupplyMapper;

    @Override
    public InvOnhandQuantity getOnhandQuantity(IRequest request, InvOnhandQuantity invOnhandQuantity) {

        return invOnhandQuantityMapper.selectByItemId(invOnhandQuantity);
    }

    @Override
    public BigDecimal queryOnhandQuantity(IRequest request, InvOnhandQuantity criteria) {
        // 根据查询条件汇总出满足条件的库存现有量
        List<InvOnhandQuantity> invOnhandQuantities = invOnhandQuantityMapper.queryOnhandQuantity(criteria);
        BigDecimal quantity = BigDecimal.ZERO;
        // 汇总查询数量
        for (InvOnhandQuantity invOnhandQuantity : invOnhandQuantities) {
            quantity = quantity.add(invOnhandQuantity.getQuantity());
        }

        return quantity;
    }

    @Override
    public InvOnhandQuantity createInvOnhandQuantity(IRequest request, @StdWho InvOnhandQuantity invOnhandQuantity) {
        invOnhandQuantityMapper.insert(invOnhandQuantity);
        return invOnhandQuantity;
    }

    ;

    @Override
    public List<InvOnhandQuantity> queryOnhandQty(IRequest request, InvOnhandQuantity invOnhandQuantity, int page,
                                                  int pagesize) {

        /*modified by furong.tang 20181.2.5 18:43 */
        Long marketId = request.getAttribute(SystemProfileConstants.MARKET_ID);
        String isCheck = spmSupplyMapper.queryIsCheckByMarketId(marketId) == null ? "N" : spmSupplyMapper.queryIsCheckByMarketId(marketId);

        PageHelper.startPage(page, pagesize);
        if (isCheck.equals(CHECK_STATUS_Y)) {
            return invOnhandQuantityMapper.queryOnhandQty(invOnhandQuantity);
        }
        return invOnhandQuantityMapper.queryOnhandQty2(invOnhandQuantity);

    }

    @Override
    public BigDecimal getAvailableQuantity(IRequest request, InvOnhandQuantity criteria) {

        /*modified by furong.tang 2018.01.26 根据公司是否需要审批中状态获取不同的可用量*/
        String isCheck = null;
        Long memberId = request.getAttribute(Member.FIELD_MEMBER_ID);
        if (memberId != null ) {
            Member member = commMemberService.getMember(request, memberId);
            SpmCompany company = new SpmCompany();
            company.setCompanyId(member.getCompanyId());
            SpmCompany spmCompany = spmCompanyMapper.selectByPrimaryKey(member.getCompanyId());
            isCheck = spmCompany.getAttribute3() == null ? "N" : spmCompany.getAttribute3();
        } else {
            Long marketId = request.getAttribute(SystemProfileConstants.MARKET_ID);
            isCheck = spmSupplyMapper.queryIsCheckByMarketId(marketId) == null ? "N" : spmSupplyMapper.queryIsCheckByMarketId(marketId);
        }

        if (isCheck.equals(CHECK_STATUS_Y)) {
            return invOnhandQuantityMapper.getAvailableQuantity2(criteria);
        }
        return invOnhandQuantityMapper.getAvailableQuantity3(criteria);
    }

    @Override
    public BigDecimal getQuantity(IRequest request, InvOnhandQuantity criteria){
        return invOnhandQuantityMapper.getQuantity(criteria);
    }
}
