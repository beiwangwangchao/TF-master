/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.order.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.lkkhpg.dsis.common.order.mapper.SalesOrderMapper;
import com.lkkhpg.dsis.platform.cache.CacheSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.lkkhpg.dsis.admin.order.service.IOrderQueryService;
import com.lkkhpg.dsis.common.order.dto.QueryOrder;
import com.lkkhpg.dsis.common.order.mapper.QueryOrderMapper;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.system.User;
import com.lkkhpg.dsis.platform.mapper.system.UserMapper;

/**
 * 订单查询service接口实现类.
 * 
 * @author gulin
 */
@Service
@Transactional
public class OrderQueryServiceImpl implements IOrderQueryService {

    @Autowired
    private QueryOrderMapper queryOrderMapper;

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private SalesOrderMapper salesOrderMapper;

    @Override
    @Transactional
    public List<QueryOrder> selectOrders(IRequest iRequest, QueryOrder queryOrder, int page, int pagesize) {
        Long userId = iRequest.getAttribute(User.FILED_USER_ID);
        User user = userMapper.selectByPrimaryKey(userId);
        String orderStatus = queryOrder.getOrderStatus();
//        String role = queryOrder.getMemberRole();
        List<String> statusList = new ArrayList<String>();
        // List<String> roleList = new ArrayList<String>();
        if (orderStatus != null) {
            Collections.addAll(statusList, orderStatus.split(";"));
        }
        // if (role != null) {
        // Collections.addAll(roleList, role.split(";"));
        // }
        PageHelper.startPage(page, pagesize);
        List<QueryOrder> list = queryOrderMapper.selectOrders(queryOrder, statusList, user.getUserType());
        return list;
    }

    @Override
    public List<QueryOrder> batchUpdate(IRequest request, List<QueryOrder> queryOrders) {

        for (QueryOrder p : queryOrders) {

//            if (p.getPromptId() == null) {
//                self().createPrompt(p);
//            } else {
//                self().updatePrompt(p);
//                // updatePrompt(p);
//            }
              String attribute15= p.getAttribute15();
              String orderNumber= p.getOrderNumber();
            //updateQueryOrder(attribute15,orderNumber);
            salesOrderMapper.updateQueryOrder(attribute15, orderNumber);
           // queryOrderMapper.updateQueryOrder(attribute15, orderNumber);
        }

        return queryOrders;
    }

//    @Override
//    @CacheSet(cache = "queryOrder")
//    public boolean updateQueryOrder(String attribute15, String orderNumber) {
//        return queryOrderMapper.updateQueryOrder(attribute15, orderNumber);
//    }
}
