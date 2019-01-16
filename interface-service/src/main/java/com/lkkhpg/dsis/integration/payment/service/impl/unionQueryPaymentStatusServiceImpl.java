package com.lkkhpg.dsis.integration.payment.service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lkkhpg.dsis.common.order.dto.SalesOrder;
import com.lkkhpg.dsis.integration.payment.dto.QueryPaymentStatus;
import com.lkkhpg.dsis.integration.payment.mapper.TianFuPaymentTransactionMapper;
import com.lkkhpg.dsis.integration.payment.service.ITianFuPaymentTransationService;
import com.lkkhpg.dsis.integration.payment.service.IUnionQueryPaymentStatusService;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by hand on 2018/1/4.
 */
@Service
public class unionQueryPaymentStatusServiceImpl implements IUnionQueryPaymentStatusService {
    private  static Logger logger = LoggerFactory.getLogger(unionQueryPaymentStatusServiceImpl.class);
    private  static final String PAYMENT_UNION_SERVICE_QUERY = "payment.union.service.paymentQueryStatus{}";
    private  static int countNumber=1;//线程服务数
    private  HashMap<String,String> maps=null;
    private String backString="failed";

    @Autowired
    private TianFuPaymentTransactionMapper transactionMapper;

    @Override
    public void queryStatus(String status ,String bill_url){
        new TFPaymentQuery(status,bill_url).gotoWork();
        countNumber++;
    }

    @Override
    public String getBackStr(){

        return backString;

    }

    @Override
    public Map<String,String> getMaps(){ return  maps;}


    private class TFPaymentQuery extends Thread {

        private String union;

        private  String bill_url;

        public TFPaymentQuery(String union ,String bill_url) {
            this.union = union;
            this.bill_url=bill_url;
        }

        public synchronized void gotoWork() {

            this.setDaemon(true);
            this.start();
            logger.info("启用支付结果查询服务线程" + PAYMENT_UNION_SERVICE_QUERY + " 线程数：" + countNumber);

        }

        public void run() {
            Gson gson=new Gson();
            String backStr=null;
            int requestCount = 1;
            boolean flag=false;

            while(backStr==null && requestCount<=10){

                try{
                    backStr=  HttpRequest.sendPost(bill_url,union);
                    requestCount++;
                }catch(IOException e){
                    requestCount++;
                    flag=true;
                }

                if(flag){
                    try{
                        this.sleep(1000*(10-requestCount));
                    }catch (InterruptedException e) {
                        e.printStackTrace();
                        countNumber--;
                        return;
                    }
                }

            }

            maps=gson.fromJson(backStr,new TypeToken<Map<String, String>>() {
            }.getType());

            countNumber--;
        }

    }

}
