package com.lkkhpg.dsis.integration.payment.service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lkkhpg.dsis.common.constant.TFInterfaceConstants;
import com.lkkhpg.dsis.integration.payment.configration.IPaymentLogger;
import com.lkkhpg.dsis.integration.payment.dto.TianFuUnion;
import com.lkkhpg.dsis.integration.payment.service.IUnionPaymentService;
import com.lkkhpg.dsis.platform.service.IProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

/**
 * Created by li.liu06@hand-china.com on 2017/11/13.
 */
@Service
public class UnionPaymentServiceImpl implements IUnionPaymentService,IPaymentLogger {
    private  static final String PAYMENT_UNION_SERVICE_PAY = "payment.union.service.pay {}";
    private  static Logger logger = LoggerFactory.getLogger(UnionPaymentServiceImpl.class);
    private  static int countNumber=1;//线程服务数
    Map<String,String> maps=null;
    private  String string=null;

    @Override
    public void Pay(String post_params,String post_url) {
        new TFPayment(post_params,post_url).gotoWork();
        countNumber++;
    }

    @Override
    public  String getBackStr(){return  string;}

    @Override
    public Map<String,String> getMaps(){ return  maps;}

    private class TFPayment extends Thread {

        private   String    POST_PARAMS;

        private  String  PAYMENT_URL;

        public TFPayment(String    post_params, String  payment_url){
            this.POST_PARAMS=post_params;
            this.PAYMENT_URL=payment_url;
        }

        /*
        * 此处可以优化，技术可以采用线程池结合CALLBACK 与 Future 来处理交易信息的请求
        * 开启后台线程去处理交易信息
        * **/
        public  synchronized void gotoWork() {

            this.setDaemon(true);
            this.start();
            logger.info("启用支付服务线程"+PAYMENT_UNION_SERVICE_PAY+" 线程数："+countNumber);

        }

        public void run(){

            Gson gson=new Gson();
            String backStr=null;
            //HttpPostRequest.sendPost("http://etest.tf.cn:8903/tianfupay/pay/frontPay",gson.toJson(union));
            int requestCount = 1;
            boolean flag=false;//出现请求异常的标志
            /*
            * 将签名的参数post发送到银行的聚合支付接口
            * 如果返回为空休眠并继续请求，直到有结果
            * **/
            while(backStr==null && requestCount<=10){

                    try{
                        backStr=  PostRequest.readContentFromPost(POST_PARAMS,PAYMENT_URL);
                        requestCount++;

                    }catch(IOException e){
                        requestCount++;
                        flag=true;
                    }

                    if(flag){
                        try{
                            this.sleep(800*(10-requestCount));
                        }catch (InterruptedException e) {
                            e.printStackTrace();
                            countNumber--;
                            return;
                        }
                    }

            }
            /*
            * 将返回的字符串解析成map 类型，并保存。
            * **/
            maps=gson.fromJson(backStr,new TypeToken<Map<String, String>>() {
            }.getType());

            countNumber--;
        }

    }
}
