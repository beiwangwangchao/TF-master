/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.inventory.lot.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.lkkhpg.dsis.admin.inventory.lot.service.InvLotService;
import com.lkkhpg.dsis.common.inventory.dto.InvLot;
import com.lkkhpg.dsis.common.inventory.mapper.InvLotMapper;
import com.lkkhpg.dsis.common.order.dto.SalesOrder;
import com.lkkhpg.dsis.common.order.mapper.SalesOrderMapper;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.DTOStatus;

/**
 * 批次实现类.
 * 
 * @author HuangJiaJing
 *
 */
@Service
@Transactional
public class InvLotServiceImpl implements InvLotService {

    @Autowired
    private InvLotMapper invLotMapper;

    @Override
    public InvLot insertInvLot(IRequest request, InvLot invLot) {
        invLotMapper.insertInvLot(invLot);
        return invLot;
    }

    @Override
    public InvLot updateInvLot(IRequest request, InvLot invLot) {
        invLotMapper.updateInvLot(invLot);
        return invLot;
    }

    @Override
    public boolean deleteInvLot(IRequest request, InvLot invLot) {
        invLotMapper.deleteInvLot(invLot);
        return false;
    }

    @Override
    public List<InvLot> selectInvLots(IRequest request, InvLot invLot, int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
        List<InvLot> list = invLotMapper.selectInvLot(invLot);
        return list;
    }

    @Override
    public List<InvLot> selectInvLotsByParas(IRequest request, InvLot invLot, int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
        List<InvLot> list = invLotMapper.selectInvLotsByParas(invLot);
        return list;
    }

    @Override
    public boolean isExistInvLot(IRequest request, InvLot invLot) {
        int i = invLotMapper.queryCount(invLot);
        if (i <= 0) {
            return false;
        }
        return true;
    }

    @Autowired
    private SalesOrderMapper salesOrderMapper;
    @Override
    public List<InvLot> batchProcessInvLot(IRequest request, List<InvLot> invLots) {
        // 根据订单头ID获取订单信息
        // 订单行
        SalesOrder head = salesOrderMapper.selectSalesOrderByHeadId(11653L);
//        List<SalesLine> lines = salesLineMapper.querySalesLineByHeaderId(paramMap);
        for (InvLot singleIntLot : invLots) {
            // 存在
            if (self().isExistInvLot(request, singleIntLot)) {
                singleIntLot = self().updateInvLot(request, singleIntLot);
            } else {
                // 不存在
                singleIntLot = self().insertInvLot(request, singleIntLot);
            }
        }

        return invLots;
    }

    @Override
    public List<InvLot> queryItemLots(IRequest request, Long itemId, Long orgId) {
        return invLotMapper.queryItemLots(itemId, orgId);

    }

    @Override
    public int queryCount(IRequest request, InvLot invLot) {
        return invLotMapper.queryCount(invLot);
    }

    @Override
    public Boolean validateInvLot(IRequest request, List<InvLot> invLots) {
        for (InvLot invLot : invLots) {
            if (DTOStatus.ADD.equals(invLot.get__status())) {
                if (self().isExistInvLot(request, invLot)) {
                    return false;
                }
            }
        }

        return true;

    }
}
