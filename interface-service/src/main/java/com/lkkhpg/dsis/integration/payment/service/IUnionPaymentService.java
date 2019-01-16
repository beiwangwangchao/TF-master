package com.lkkhpg.dsis.integration.payment.service;

import com.lkkhpg.dsis.integration.payment.dto.TianFuUnion;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;


/**
 * Created by li.liu06@hand-china.com on 2017/11/13.
 *
 */
public interface IUnionPaymentService {
     /**
      * 线程请求聚合支付接口
      */
     void Pay(String post_params ,String post_url);


     String getBackStr();

     /**
      * 返回请求聚合支付的信息
      */
     Map<String,String> getMaps();
}
