package com.lkkhpg.dsis.integration.payment.service;

import com.lkkhpg.dsis.integration.payment.dto.QueryPaymentStatus;

import java.util.Map;

/**
 * Created by li.liu06@hand-china.com on 2018/1/4.
 */
public interface IUnionQueryPaymentStatusService {
     /**
      * 查询账单的状态
      */
     void queryStatus(String status,String bill_url);
     /**
      * 获取查询账单的信息
      */
     Map<String,String> getMaps();

     String getBackStr();
}
