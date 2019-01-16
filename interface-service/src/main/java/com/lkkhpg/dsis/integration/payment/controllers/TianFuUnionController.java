package com.lkkhpg.dsis.integration.payment.controllers;

import com.google.gson.Gson;
import com.lkkhpg.dsis.common.config.dto.SpmCompany;
import com.lkkhpg.dsis.common.constant.OrderConstants;
import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.common.constant.TFInterfaceConstants;
import com.lkkhpg.dsis.common.delivery.dto.OrderDelivery;
import com.lkkhpg.dsis.common.discount.dto.DiscountTrxHeadDto;
import com.lkkhpg.dsis.common.discount.dto.DiscountTrxLineDto;
import com.lkkhpg.dsis.common.discount.exception.DiscountException;
import com.lkkhpg.dsis.common.discount.service.IDiscountTrxHeadService;
import com.lkkhpg.dsis.common.order.dto.SalesOrder;
import com.lkkhpg.dsis.common.service.ICommSalesOrderService;
import com.lkkhpg.dsis.common.service.ISpmCompanyService;
import com.lkkhpg.dsis.integration.payment.constant.PaymentConstant;
import com.lkkhpg.dsis.integration.payment.dto.*;
import com.lkkhpg.dsis.integration.payment.service.IPayRefundRequestService;
import com.lkkhpg.dsis.integration.payment.service.IPaymentConfigService;
import com.lkkhpg.dsis.integration.payment.service.ITianFuPaymentTransationService;
import com.lkkhpg.dsis.integration.payment.service.IUnionPaymentService;
import com.lkkhpg.dsis.integration.payment.service.impl.ImageUtils;
import com.lkkhpg.dsis.integration.payment.service.impl.UnionPaymentServiceImpl;
import com.lkkhpg.dsis.integration.payment.service.impl.unionQueryPaymentStatusServiceImpl;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.ILanguageProvider;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.ResponseData;
import com.lkkhpg.dsis.platform.dto.system.Language;
import com.lkkhpg.dsis.platform.exception.TokenException;
import com.lkkhpg.dsis.platform.service.IProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by li.liu06@hand-china.com on 2017/11/13.
 */
@SuppressWarnings("ALL")
@Controller
@Scope("prototype")
public class TianFuUnionController extends BaseController {
    private IUnionPaymentService unionPaymentService;
    private static final String sale_Status_SALE = "ON_SALE";
    private static final String sale_Status_REFUND = "ON_REFUND";
    private static final String SOURCE_TYPE = "ORDER";

    private static final String ORDER_PAYMENT_SUCCESS = "/payment/pay/payment_success";

    private static  final String ORDER_PAYMENT_QR_CODE="/payment/pay/payment_qr_code";

    private static  final String ORDER_PAYMENT_ERROR="/payment/pay/payment_error";

    private static final String ORDER_PAYMENT_RESULT = "/payment/pay/payment_result";

    private static  final String ORDER_PAYMENT_TOKEN="order_confirm_token";


    @Autowired
    private ICommSalesOrderService commSalesOrderService;
    @Autowired
    private ITianFuPaymentTransationService iTianFuPaymentTransationService;
    @Autowired
    private IPayRefundRequestService iPayRefundRequestService;
    @Autowired
    private IProfileService fileService;
    @Autowired
    private ICommSalesOrderService salesOrderService;
    @Autowired
    private IPaymentConfigService paymentConfigService;
    @Autowired
    private ISpmCompanyService spmCompanyService;
    @Autowired
    private ILanguageProvider languageProvider;
    @Autowired
    IDiscountTrxHeadService discountTrxHeadService;

    private final Logger logger = LoggerFactory.getLogger(TianFuUnionController.class);

    /**
     * 向后台保存退款单
     * @param request
     * @param response
     * @param tianFuUnionRefund
     * @return
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping(value = "payment/pay/refundSave")
    @ResponseBody
    public ResponseData save(HttpServletRequest request, HttpServletResponse response, TianFuUnionRefund tianFuUnionRefund) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        String marketId=request.getParameter("marketId");
        String salesOrgId=request.getParameter("salesOrgId");
        String refund_reason = request.getParameter("refundReason");
        String out_trade_no = request.getParameter("orderNumber");
        String serviceType = request.getParameter("serviceType");
        String refund_fee = request.getParameter("refundNum");
        String refundExplain = request.getParameter("refundExplain");
        String transportFee=request.getParameter("transportFee");
        String transaction_id;
        BigDecimal total_fee;
        String trans_channel;
           if("0".equals(refund_fee)) {
                 transaction_id= request.getParameter("orderNumber");
                        total_fee= new BigDecimal(request.getParameter("refundNum"));
                     trans_channel = "wx_qr_p";
           }else {
               List<TianFuPaymentResult> tianFuList = iTianFuPaymentTransationService.selectUnit(out_trade_no);
                transaction_id = tianFuList.get(0).getTransaction_id(); //平台订单号
                total_fee = new BigDecimal(tianFuList.get(0).getTotal_fee());
              trans_channel = tianFuList.get(0).getTrans_channel();
           }
        String input_charset = "UTF-8";
        String service = "refund_service";//接口名称  "service": "pay_service",
        String service_version = "1.0";//接口版本  "service_version": "1.1",
      //  List<SpmCompany>spmCompanyList =spmCompanyService.selectPartner(marketId);
        String outTradeNo=out_trade_no;
        String partner;
        String subpartner;
        if("0".equals(refund_fee)){
            partner ="12038387";
            subpartner="12038710";
        }else {
            List<TianFuPaymentTransaction> tfList = iTianFuPaymentTransationService.selectPartner(outTradeNo);
             partner = tfList.get(0).getPartner();//商户号 "partner": "12038387",
            subpartner = tfList.get(0).getSubPartner();
        }
        String sign_type = "MD5"; //签名方式  "sign_type": "MD5",
        String sign; //签名 "sign": "3aa86341420152988a60fe521d50702f",
        String out_refund_no;
        //SerialNumberTool serialNumberTool = new SerialNumberTool();
        out_refund_no = "TK" + new SimpleDateFormat("yyyyMMddHHmm").format(new Date()) + SerialNumberTool.getInstance().generaterNextNumber(4);
        String appKey= fileService.getProfileValue(TFInterfaceConstants.APP_KEY);
        // String Subject ="";
        String sing = "input_charset=UTF-8&out_refund_no=" + out_refund_no + "&out_trade_no=" + out_trade_no + "&partner="+ partner +"&refund_fee=" + refund_fee +
                "&refund_reason=" + refund_reason + "&service=refund_service&service_version=1.0&subpartner="+ subpartner +"&total_fee=" + total_fee + "&trans_channel=" + trans_channel +
                "&transaction_id=" + transaction_id + appKey;
        System.out.println(sing);
        String md5s = MD5Sign.md5(sing);
        System.out.println(md5s);
        PayRefundRequest payRefundRequest = new PayRefundRequest();
        payRefundRequest.setOutRefundNo(out_refund_no);
        payRefundRequest.setTransactionId(transaction_id);
        payRefundRequest.setPartner(partner);
        payRefundRequest.setOutTradeNo(out_trade_no);
        payRefundRequest.setServiceType(serviceType);
        payRefundRequest.setSubpartner(subpartner);
        payRefundRequest.setTransportFee(new BigDecimal(transportFee));
        // payRefundRequest.setSubject("");
        payRefundRequest.setRefundFee(new BigDecimal(refund_fee));
        payRefundRequest.setTotalFee(total_fee);
        //   payRefundRequest.setTransportFee(new BigDecimal(""));
        payRefundRequest.setRedundReason(refund_reason);
        payRefundRequest.setRefundExplain(refundExplain);
        payRefundRequest.setTransChannel(trans_channel);
        payRefundRequest.setMd5Encode(md5s);
        payRefundRequest.setIsrefund(OrderConstants.SALES_STATUS_REFUNDING);
        payRefundRequest.setSalesOrgId(salesOrgId);
//        Date  date = new Date("yyyy-MM-dd");
//        //SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
//        payRefundRequest.setReturnDateFrom(date);
        String str="";
            try {
                str=iPayRefundRequestService.payRefundquest(payRefundRequest);
                boolean flag=false;
                SalesOrder salesOrder = new SalesOrder();
                salesOrder.setOrderNumber(out_trade_no);
                salesOrder.setOrderStatus(OrderConstants.SALES_STATUS_REFUNDING);
                flag=commSalesOrderService.updateOrderStatusRefund(salesOrder);

            } catch (Exception e) {
                e.printStackTrace();
            }
        ResponseData responseData =new ResponseData();

        //System.out.println("------------");
//        System.out.println(str);

            if("订单已存在，请勿重复提交".equals(str)){
                responseData.setMessage("订单已存在，请勿重复提交");
                responseData.setSuccess(false);
            }else if("订单提交失败".equals(str)){
                responseData.setMessage("订单提交失败");
                responseData.setSuccess(false);
            }else{
                responseData.setMessage("退款申请提交成功");
                responseData.setSuccess(true);
            }
            return responseData;
    }

    @RequestMapping(value="/payment/autoShip")
    @ResponseBody
    public ResponseData autoShip(HttpServletRequest servletRequest,HttpServletResponse servletResponse, String orderNumber)throws Exception{

        IRequest request = createRequestContext(servletRequest);
        boolean flag = iTianFuPaymentTransationService.autoShip(request, orderNumber);
        ResponseData responseData = new ResponseData();
        responseData.setSuccess(flag);
        return new ResponseData();
    }



    /**
     * 用户MWS取消退款
     * @param request
     * @param response
     * @param orderNo
     * @return
     * @throws Exception
     */
    @RequestMapping(value ="/tf/pay/refunds")
    @ResponseBody
    public ResponseData refunds(HttpServletRequest request, HttpServletResponse response,String orderNumber)throws Exception{
        System.out.println(orderNumber);
        String out_trade_no=orderNumber;
        String OrderNumber=orderNumber;
        String orderStatus =commSalesOrderService.selectOrderStatus(OrderNumber);
        ResponseData responseData = new ResponseData();
        if("REF".equals(orderStatus)){
            responseData.setSuccess(false);
           }else {
            boolean flag = false;
            // flag=iTianFuPaymentTransationService.deleteRefund(out_trade_no);
            flag = iPayRefundRequestService.deleteRefund(out_trade_no);
            boolean flag1 = false;
            flag1 = commSalesOrderService.updateOrderStatusRefunds(OrderNumber);
            responseData.setSuccess(flag1);
        }
            return responseData;

    }



    /**
     *  查询订单的发运状态
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/payment/pay/queryDeliveryStatus")
    @ResponseBody
    public ResponseData queryDeliveryStatus(HttpServletRequest request, HttpServletResponse response,String orderNumber) throws Exception {
      List<OrderDelivery> deliveryList=iPayRefundRequestService.queryDeliveryStatus(orderNumber);
        return new ResponseData(deliveryList);
    }


    /**
     * 同步通知的接口
     * @param request
     * @param response
     * @param paymentResult
     * @return ModelAndView  返回给用户支付的信息
     */
    @RequestMapping(value = "/payment/pay/callback/returnNotify" , method = RequestMethod.POST)
    public ModelAndView returnNotify(HttpServletRequest request, HttpServletResponse response, TianFuPaymentResult paymentResult){

        ModelAndView view= new ModelAndView();
        String appKey=iTianFuPaymentTransationService.selectList(TFInterfaceConstants.APP_CODE);
        boolean flag = false;
        try {
            /**
             *将银行的反馈信息进行签名认证
             **/
            flag = MD5Sign.returnVerify(appKey.trim(), request);
        } catch (Exception E) {
           logger.debug(E.getMessage());
        }
        /*
        * 当数据效验成功时，开始得到交易反馈回来的订单支付状态
        * 查询本地的交易信息，获取实际付款金额
        * **/
       if(flag){
           String tradeState= request.getParameter("trade_state");
           String orderNumber= paymentResult.getOut_trade_no();
           List<Language> languages = languageProvider.getSupportedLanguages();
           view.addObject("languages", languages);
           TianFuPaymentTransaction t=iTianFuPaymentTransationService.queryPaymentRecord(orderNumber,paymentResult.getTrans_channel());
           switch (tradeState){
               case "TRADE_SUCCESS":
                case "0":{
                    /**
                     * 银行反馈信息验证通过后，需要再一次进行查单处理，并反馈信息给用户
                     * */
                    boolean lag=queryStatus(orderNumber,paymentResult.getPartner(),t.getSubPartner());
                    if(lag){
                        view.setViewName(getViewPath() + ORDER_PAYMENT_RESULT);
                        view.addObject("headerId", commSalesOrderService.querByOrderNumber(orderNumber));
                        view.addObject("status","0");
                        view.addObject("orderNumber", orderNumber);
                        view.addObject("actrualPayAmt",t.getTotalSum());
                        break;
                    }else {
                        view.setViewName(getViewPath() + ORDER_PAYMENT_RESULT);
                        view.addObject("headerId", commSalesOrderService.querByOrderNumber(orderNumber));
                        view.addObject("status","3");
                        view.addObject("orderNumber", orderNumber);
                        view.addObject("actrualPayAmt", t.getTotalSum());
                        break;
                    }
                }
                case "1":{
                    view.setViewName(getViewPath() + ORDER_PAYMENT_RESULT);
                    view.addObject("headerId", commSalesOrderService.querByOrderNumber(orderNumber));
                    view.addObject("status","1");
                    view.addObject("orderNumber", orderNumber);
                    view.addObject("actrualPayAmt", t.getTotalSum());
                    break;
                }
                case "3":{
                    view.setViewName(getViewPath() + ORDER_PAYMENT_RESULT);
                    view.addObject("headerId", commSalesOrderService.querByOrderNumber(orderNumber));
                    view.addObject("status","3");
                    view.addObject("orderNumber", orderNumber);
                    view.addObject("actrualPayAmt", t.getTotalSum());
                    break;
                }
                default:
                    logger.debug("支付的状态为："+tradeState);
                    view.setViewName(getViewPath()+ORDER_PAYMENT_ERROR);
                    view.addObject("retMsg","支付处于其它的支付方式状态为"+tradeState);
            }
        }else {
            view.setViewName(getViewPath()+ORDER_PAYMENT_ERROR);
            view.addObject("retMsg","非法参数");
        }
        return view;
    }


    /**
     * 异步通知的接口
     * @param request
     * @param response
     * @param paymentResult
     * @return success 与 failed
     */
    @RequestMapping(value = "/payment/pay/callback/notify" , method = RequestMethod.POST)
    public String unionPay(HttpServletRequest request, HttpServletResponse response, TianFuPaymentResult paymentResult) {

        /*
         *验证银行发送过来的报文；签名验证
         * &&param flag :true 表示银行返回的报文
         */
        String appKey= fileService.getProfileValue(TFInterfaceConstants.APP_KEY);
        appKey=iTianFuPaymentTransationService.selectList(TFInterfaceConstants.APP_CODE);
        boolean flag = false;
        try {
            flag = MD5Sign.verify(appKey.trim(), request);
        } catch (Exception E) {
            return  "fail";
        }
        /*
         *验证成功后，进行数据的保存和记录；改变订单的状态。
         * bank_transno:交易银行的类型,支付成功应返回的值
         * pay_info：支付信息；支付未成功应返回的值
         * trade_state:0 未支付 1 完成  3 处理中；
         */
        if(flag){
            String payInfo= request.getParameter("pay_info");
            String tradeState= request.getParameter("trade_state");
            switch (tradeState){
                case "TRADE_SUCCESS":
                case "0":
                    try{
                        paymentResult.setTrade_status(0);
                        boolean payresult= processPaymentNotify(request,paymentResult);
                        if(payresult) {return "success";}
                    }catch (Exception e){
                        e.printStackTrace();
                        return "fail";
                    }
                case "1":
                case "3":
                default:
                    logger.debug("支付的信息为："+payInfo+";支付的状态为："+tradeState);
                    return "fail";
            }
        }else return "fail";

    }
    /**
     * 异步通知处理
     * @param request
     * @param paymentResult 支付后处理异步通知的信息，并将信息保存到数据库
     * @return true 与 false  数据反馈并保存成功返回true
     */
    private boolean processPaymentNotify(HttpServletRequest request,TianFuPaymentResult paymentResult)throws Exception{

        /*
        *解析字符串时间为date类型
        * flag 交易结果插入大数据库是否成功
        */
        IRequest RequestContext = createRequestContext(request);
        Date payDate = null;
        boolean flag=false;
        try {
            //20180517
            String pro_fee = paymentResult.getProduct_fee();
            if (pro_fee == null ){
                paymentResult.setProduct_fee("0");
            };

            String[] str = paymentResult.getTime_end().split(" ");
            String ymdHms = str[0].substring(0, 4) + "-" + str[0].substring(4, 6) + "-" + str[0].substring(6, 8) + str[1];
            payDate = new SimpleDateFormat("yy-MM-ddHH:mm:ss").parse(ymdHms);
            paymentResult.setPayDate(payDate);

        } catch (Exception e) {
            e.printStackTrace();
        }
        /**
         * resultCount 查询是否已经有支付成功的信息，有就返回true
         *没有信息，则进行插入到数据库，并跟新相应的订单的状态
         * */
        try {
            int resultCount=iTianFuPaymentTransationService.selectUnit(request.getParameter("out_trade_no")
                    .trim()).size();
            if(resultCount>0){
                return true;
            }else {
                flag = iTianFuPaymentTransationService.paymentResult(paymentResult);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        if (flag) {
            Map<String, String> maps = new HashMap<String, String>();
            Map<String, Object> map = new HashMap<String, Object>();
            String trade_no = paymentResult.getOut_trade_no();
            //待改善，如果是支付一半则用 2或者2以外的字符。
            maps.put("status", "1");//支付全款
            maps.put("outTradeNo", trade_no);
            maps.put("transChannel", paymentResult.getTrans_channel());

            SalesOrder order = new SalesOrder();
            order.setOrderNumber(trade_no);
            order.setOrderStatus("COMP");
            order.setPayDate(payDate);
            order.setDappSyncFlag("N");

                /*
                 * 此处采用map的类来跟新订单的信息跟上面功能一致
                 */
//                  map.put("orderStatus","COMP");
//                  map.put("payDate",payDate);
//                  map.put("dappSyncFlag","N");

            if (paymentResult.getTotal_fee().equalsIgnoreCase(paymentResult.getProduct_fee())) {
                map.put("freeShipping", "N");
                order.setFreeShipping("N");
            } else {
                map.put("freeShipping", "Y");
                order.setFreeShipping("Y");
            }
            if (iTianFuPaymentTransationService.updatePaymentRecord(maps)
                    && iTianFuPaymentTransationService.updateSalesOrder(RequestContext,order)
                //                         && iTianFuPaymentTransationService.updateSalesOrderStatus(map,trade_no)
                    ) {

                logger.debug("the updatePaymentRecord and salesRecord is successful!!");
                logger.debug("支付银行的类型为:"+request.getParameter("bank_transno"));
            }
            return true;

        }else {
            return  false;
        }

    }

    /**
     *  调用银行网关的接口
     * @param request
     * @param response
     * @param TianFuUnion 提交需要支付订单的信息
     * @param salesOrder  销售订单的数据
     * @param token  token值
     * @return ModelAndView  并返回进入网关的页面
     */
    @RequestMapping(value = {"/payment/pay/tfpay", "/payment/pay/unionpay"}, method = RequestMethod.POST)
    public ModelAndView TFPaymentUnion(HttpServletRequest request, HttpServletResponse response,TianFuUnion union
                                             ,SalesOrder salesOrder,String token) throws  Exception,TokenException{

        /**
         * 验证token，验证订单实际金额防止crsf攻击
         */
        ModelAndView modelAndView=new ModelAndView();
        IRequest contextRequest = createRequestContext(request);
        /**
         *  先检查访问页面时是否有token
         *  再进行数据的订单金额的检验，对比是否被串改
         **/
        salesOrder.set_token(token);
        checkToken(request, salesOrder);
        //获取交易的商户号，并友情提示该商户是否能够交易
        SpmCompany company=spmCompanyService.selectPartner(contextRequest.getAttribute(SystemProfileConstants.MARKET_ID));
        String partner=company.getParter();
        String privatePartner=company.getPrivateSubparter();
        String  publicPartner=company.getPublicSubparter();
        if (partner==null || privatePartner==null || publicPartner==null){
            modelAndView.setViewName(getViewPath()+ORDER_PAYMENT_ERROR);
            modelAndView.addObject("retMsg","该商户不能交易");
            return modelAndView;
        }
        union.setPartner(partner);
        union.setSubpartner(privatePartner);
        if(union.getBankcode().equalsIgnoreCase("b2b")){
            union.setSubpartner(publicPartner);
        }
        //检验订单支付的实际价格，并且要求productamt + transportAmt =checkActAmt 是否被串改
        try {
            salesOrder=salesOrderService.getOrder(createRequestContext(request),salesOrder.getHeaderId(),false,false);
            BigDecimal decimal = new BigDecimal(String.valueOf(salesOrder.getActrualPayAmt()));
            BigDecimal checkActAmt = decimal.setScale(2,BigDecimal.ROUND_DOWN);
            decimal=new BigDecimal(union.getTotal_fee());
            BigDecimal actAmt= decimal.setScale(2,BigDecimal.ROUND_DOWN);
            BigDecimal productAmt= new BigDecimal(union.getProduct_fee());
            BigDecimal transportAmt=new BigDecimal(union.getTransport_fee());
            if(checkActAmt.compareTo(actAmt)!=0 && checkActAmt.compareTo(productAmt.add(transportAmt))!=0){
                modelAndView.setViewName(getViewPath()+ORDER_PAYMENT_ERROR);
                modelAndView.addObject("retMsg","非法参数");
                return modelAndView;
            }
        }catch (Exception e){
            throw new Exception("illegal arguments");
        }
        /*
        * *查询改订单是否重新被提交；如果是，则直接查询数据库返回支付网关的页面
        *第一次提交，则请求银行，接受参数，并保存相应的参数
        * */
        TianFuPaymentTransaction transaction = iTianFuPaymentTransationService.queryPaymentRecord(union.getOut_trade_no().trim(),
                union.getTrans_channel().trim());
        if (transaction == null) {
            /**
             *由于请求银行的接口，可能会出现异常，或者用户操作错误；则有反馈信息
             * 根据后台线程请求数据，为空则请求超时
             * 不为空，则处理是否返回的信息正常，正常retCode 为0000 否则为其他异常则把错误信息显示到页面提醒用户
             * */
            Map<String,String>maps = processPaymentThread(union);
            if(maps==null){
                modelAndView.setViewName(getViewPath()+ORDER_PAYMENT_ERROR);
                modelAndView.addObject("retMsg","支付请求超时请稍后重试");
            }else {
                if(maps.get("retCode").equalsIgnoreCase("0000")){
                    String string=maps.get("returnMsg");
                    String sort="form";
                    String s="submit";
                    String s1="method";
                    String str="action";
                    int check1=string.indexOf(sort);
                    int check2=string.indexOf(s);
                    int check3=string.indexOf(s1);
                    int check4=string.indexOf(str);
                    if(check1>1 && check2>1 && check3>1 && check4>1){
                        processPaymentRecord(union, null, null, string);
                    }
                    processPayment(response, string);

                    //折扣/信用事务提交 update up 15750 at 2018/04/19
                    long memberId=contextRequest.getAttribute("memberId");
                    String orderNumber=salesOrder.getOrderNumber();
                     List<SalesOrder>salesOrderList= salesOrderService.selectDiscountTrx(orderNumber);
                      BigDecimal discountAmt= salesOrderList.get(0).getDiscountAmt();
                      BigDecimal orderAmt=salesOrderList.get(0).getOrderAmt();
                      if(discountAmt.compareTo(BigDecimal.ZERO)==1&&discountAmt.compareTo(orderAmt)==-1){
                          submitDiscountTrx(contextRequest,memberId,orderNumber);
                      }

                } else {
                    String str=maps.get("retMsg");
                    if(str.length()>10){
                        str=str.substring(0,10);
                    }
                    modelAndView.setViewName(getViewPath()+ORDER_PAYMENT_ERROR);
                    modelAndView.addObject("retMsg",str);
            }
            }
        }else {
            processPayment(response, transaction.getPayMessage());
        }
        return modelAndView;
    }

    /**
     *  调用银行网关的接口
     * @param request
     * @param response
     * @param TianFuUnion 提交需要支付订单的信息
     * @param salesOrder  销售订单的数据
     * @param token  token值
     * @return ModelAndView  并返回进入二维码支付的页面
     */
    @RequestMapping(value = {"/payment/pay/alipay", "/payment/pay/weixinpay"} ,method = RequestMethod.POST)
    public ModelAndView TFPaymentALIPay(HttpServletRequest request, HttpServletResponse response ,TianFuUnion union
                                           , SalesOrder salesOrder,String token) throws  Exception,TokenException{

        /**
         * 验证token，验证订单实际金额防止crsf攻击
         */
        ModelAndView modelAndView=new ModelAndView();
        IRequest contextRequest = createRequestContext(request);
        // 先检查访问页面时是否有token
        salesOrder.set_token(token);
        checkToken(request, salesOrder);
        //获取交易的商户号，并友情提示该商户是否能够交易，若同时没维护对公对私二级商户号就会进入error页面
        SpmCompany company=spmCompanyService.selectPartner(contextRequest.getAttribute(SystemProfileConstants.MARKET_ID));
        String partner=company.getParter();
        String privatePartner=company.getPrivateSubparter();
        String  publicPartner=company.getPublicSubparter();
        if (partner==null || privatePartner==null || publicPartner==null){
            modelAndView.setViewName(getViewPath()+ORDER_PAYMENT_ERROR);
            modelAndView.addObject("retMsg","该商户不能交易");
            return modelAndView;
        }
        union.setPartner(partner);
        union.setSubpartner(privatePartner);
        //公对公，就传对公的二级商户号
        if(union.getBankcode().equalsIgnoreCase("b2b")){
            union.setSubpartner(publicPartner);
        }
        //检验订单支付的实际价格，并且要求productamt + transportAmt =checkActAmt 是否被串改
        try {
            salesOrder=salesOrderService.getOrder(createRequestContext(request),salesOrder.getHeaderId(),false,false);
            BigDecimal decimal = new BigDecimal(String.valueOf(salesOrder.getActrualPayAmt()));
            BigDecimal checkActAmt = decimal.setScale(2,BigDecimal.ROUND_DOWN);
            decimal=new BigDecimal(union.getTotal_fee());
            BigDecimal actAmt= decimal.setScale(2,BigDecimal.ROUND_DOWN);
            BigDecimal productAmt= new BigDecimal(union.getProduct_fee());
            BigDecimal transportAmt=new BigDecimal(union.getTransport_fee());
            if(checkActAmt.compareTo(actAmt)!=0 && checkActAmt.compareTo(productAmt.add(transportAmt))!=0){
                modelAndView.setViewName(getViewPath()+ORDER_PAYMENT_ERROR);
                modelAndView.addObject("retMsg","非法参数");
                return modelAndView;
            }
        }catch (Exception e){
            throw new Exception("illegal arguments");
        }
        //查询交易是否重复被提交
        TianFuPaymentTransaction transaction = iTianFuPaymentTransationService.queryPaymentRecord(union.getOut_trade_no().trim(),
                union.getTrans_channel().trim());
        if (transaction == null) {
            //请求银行，返回二维码图片，并解析图片
            modelAndView=processImage(request,response,union);
        } else {
            //重复提交订单，并且已经获得二维码，则检验二维码是否存在
            File file = new File(transaction.getImagePath() + transaction.getImageType());
            if(file.exists()) {
                modelAndView.setViewName(getViewPath()+ORDER_PAYMENT_QR_CODE);
                modelAndView.addObject("orderNumber", union.getOut_trade_no());
                modelAndView.addObject("actrualPayAmt",union.getTotal_fee());
                modelAndView.addObject("transChannel", union.getTrans_channel());

            }else {
                //如果二维码不存在，则重新请求
                iTianFuPaymentTransationService.deletePaymentRecord(union.getOut_trade_no().trim(),
                        union.getTrans_channel().trim());
               modelAndView=processImage(request,response,union);
            }
        }
        return modelAndView;
    }

    /**
     * 获取支付二维码的图片
     * @param request
     * @param response
     * @param orderNumber 提交的订单号
     * @param transChannel  支付的方式 微信或者支付宝
     * @return void
     */
    @RequestMapping(value = "/payment/pay/getImage")
    public void getImage(HttpServletRequest request, HttpServletResponse response, String orderNumber, String transChannel) {

        //查询数据库，加载二维码到HTML页面上
        TianFuPaymentTransaction transaction = iTianFuPaymentTransationService.queryPaymentRecord(orderNumber.trim(), transChannel.trim());
        try {
            if(transaction==null) return;
            File file = new File(transaction.getImagePath() + transaction.getImageType());
            if (file.exists()) {
                int fileLength = (int) file.length();
                response.setContentLength(fileLength);
                if (fileLength > 0) {
                    writeFileToResp(response, file);
                }
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

    }
    /**
     *  查询通过扫码支付的状态
     * @param request
     *  @param response
     *  @param orderNumber 提交的订单号
     *  @param transChannel  支付的方式 微信或者支付宝
     * @return ResponseData  查寻订单是否成功支付
     */
    @RequestMapping(value = "/payment/pay/queryStatus", method = RequestMethod.POST)
    public ResponseData queryStatus(HttpServletRequest request, String orderNumber, String transChannel) {
        IRequest requestContext = createRequestContext(request);
        TianFuPaymentResult paymentResult = iTianFuPaymentTransationService.queryStatus(requestContext,orderNumber.trim());
        TianFuPaymentTransaction transaction = iTianFuPaymentTransationService.queryPaymentRecord(orderNumber.trim(), transChannel.trim());
        //SalesOrder order=iTianFuPaymentTransationService.querySalesOrderStatus(orderNumber.trim());
        ResponseData rd = new ResponseData();
        if (paymentResult != null && paymentResult.getRetcode().trim().equals("0")) {
            rd.setSuccess(true);

            //折扣/信用事务提交 update up 15750 at 2018/04/19
            long memberId=requestContext.getAttribute("memberId");
            try {
                List<SalesOrder>salesOrderList= salesOrderService.selectDiscountTrx(orderNumber);
                BigDecimal discountAmt= salesOrderList.get(0).getDiscountAmt();
                BigDecimal orderAmt=salesOrderList.get(0).getOrderAmt();
                if(discountAmt.compareTo(BigDecimal.ZERO)==1&&discountAmt.compareTo(orderAmt)==-1){
                    submitDiscountTrx(requestContext,memberId,orderNumber);
                }

            } catch (DiscountException e) {
                e.printStackTrace();
            }
            try {
                    File file = new File(transaction.getImagePath() + transaction.getImageType());
                    if (file.exists()) {
                        file.delete();
                    }
            }  catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        } else rd.setSuccess(false);
        return rd;
    }
    /**
     *  查询通过扫码支付成功的状态
     * @param request
     *  @param response
     *  @param orderNumber 提交的订单号
     * @return ModelAndView  支付成功则返回支付成功的页面
     */
    @RequestMapping(value = ORDER_PAYMENT_SUCCESS)
    public ModelAndView paymentSuccess(HttpServletRequest request, String orderNumber)throws Exception {
        ModelAndView model = new ModelAndView();

        IRequest requestContext = createRequestContext(request);

        TianFuPaymentResult paymentResult = iTianFuPaymentTransationService.queryStatus(requestContext,orderNumber.trim());
        if (paymentResult != null && paymentResult.getRetcode().trim().equals("0")) {

            /**
             * todo modify this function to add a function which can auto delivery orders
             * 20180208
             */
//            iTianFuPaymentTransationService.autoShip(requestContext,orderNumber);

            model.setViewName(getViewPath() + ORDER_PAYMENT_SUCCESS);
            model.addObject("headerId", commSalesOrderService.querByOrderNumber(orderNumber));
            model.addObject("orderNumber", orderNumber);
            model.addObject("actrualPayAmt", paymentResult.getTotalSum());
            model.addObject("sourceKey", commSalesOrderService.querByOrderNumber(orderNumber));


        }
        return model;
    }

    /**
     *  加载解析图片参数，并返回二维码支付页面
     * @param request
     *  @param response
     *  @param TianFuUnion 交易的信息
     * @return ModelAndView  支付成功则返回支付成功的页面
     */
    private  ModelAndView processImage(HttpServletRequest request,
                                 HttpServletResponse response,TianFuUnion union)throws Exception{

        /*
        * 线程请求二维码支付，并返回信息
        * **/
        Map<String, String> maps = processPaymentThread(union);
        ModelAndView modelAndView=new ModelAndView();
        if(maps!=null){
            if(maps.get("retCode")
                    .equalsIgnoreCase("0000")){
                //获取二维码参数，并解析成jpg格式的图片
                String string=maps.get("qr_code");
                String filePath = request.getSession().getServletContext().getRealPath("/")
                        + "resources" + File.separator + "img" + File.separator;
                //随机生成图片参数
                SerialNumberTool tool = new SerialNumberTool();
                String str = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
                String noticer = tool.getInstance().generaterNextNumber(10);
                String imageName = str + noticer + ".jpg";
                //解析图片
                ImageUtils.decodeBase64ToImage(string, filePath, imageName);
                //返回扫码支付的页面
                modelAndView.setViewName(getViewPath()+ORDER_PAYMENT_QR_CODE);
                modelAndView.addObject("orderNumber", union.getOut_trade_no());
                modelAndView.addObject("actrualPayAmt",union.getTotal_fee());
                modelAndView.addObject("transChannel", union.getTrans_channel());
                //将这条记录记录到数据库以及二维码的路径
                processPaymentRecord(union, filePath, imageName, null);
                return modelAndView;

            }else {
                String str=maps.get("retMsg");
                if(str.length()>10){
                    str=str.substring(0,10);
                }
                //请求回来的数据有异常
                modelAndView.setViewName(getViewPath()+ORDER_PAYMENT_ERROR);
                modelAndView.addObject("retMsg",str);
                return modelAndView;
            }
        }else {
            //支付请求超时
            modelAndView.setViewName(getViewPath()+ORDER_PAYMENT_ERROR);
            modelAndView.addObject("retMsg","支付请求超时请稍后重试");
            return modelAndView;
        }

    }

    /**
     * 向用户输出HTML，或者相关的错误信息
     * @param response
     * @param submitStr 将请求过后的信息以字符串的形式输出到页面
     * @return void   以输入输出流的方式向HTML页面输出信息
     */
    private void processPayment(HttpServletResponse response, String submitStr) {

        try {
            String str = new String(submitStr.getBytes("utf-8"), "utf-8");
            response.setContentType("text/html;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.println(str);
            out.flush();
            out.close();
        } catch (Exception w) {
            w.printStackTrace();
        }

    }

    /**
     * 向页面输出支付二维码
     * @param response
     * @param file 把图片以输出流的方式输出到页面
     * @return void   以输入输出流的方式向HTML页面输出图片信息
     */
    private void writeFileToResp(HttpServletResponse response, File file) throws Exception {
        byte[] buf = new byte[1024];
        try (InputStream inStream = new FileInputStream(file);
             ServletOutputStream outputStream = response.getOutputStream()) {
            int readLength;
            while (((readLength = inStream.read(buf)) != -1)) {
                outputStream.write(buf, 0, readLength);
            }
            outputStream.flush();
            outputStream.close();
        }
    }
    /**
     * 调用线程进行支付请求
     * @param union 请求的交易信息
     * @return Map   返回从银行请求到的信息
     */
    private  Map<String,String> processPaymentThread(TianFuUnion union) {
        String post_url=fileService.getProfileValue(TFInterfaceConstants.PAYMENT_URl).trim();
        //获取聚合支付相关的配置
        List<PaymentConfig>paymentConfigs=paymentConfigService.queryConfigsByType(PaymentConstant.TF_PAYMENT_TYPE_URL);
        String appKey= fileService.getProfileValue(TFInterfaceConstants.APP_KEY);
        String signType=null;
        for (PaymentConfig config:paymentConfigs){
            String value=config.getValue();
           switch (config.getKey()){
               case "service":union.setService(value);break;
               case "service_version":union.setService_version(value);break;
               case "input_charset":union.setInput_charset(value);break;
               case "show_url":union.setShow_url(value);break;
               case "notify_url":union.setNotify_url(value);break;
               case "return_url":union.setReturn_url(value);break;
               case "sign_type":signType=value;break;
               case "post_url":post_url=value;break;
               default:break;
           }
        }
        //获取签名密钥，并加密
        appKey=iTianFuPaymentTransationService.selectList(TFInterfaceConstants.APP_CODE);
        String post_params=union.toSign(appKey,signType);
        int requestCount = 0;
        int backFlag = 0;
        //进行线程请求
        unionPaymentService = new UnionPaymentServiceImpl();
        unionPaymentService.Pay(post_params,post_url);
        Map<String,String>maps= unionPaymentService.getMaps();
        while (maps == null) {
            if (backFlag == 2) {
                backFlag = 0;
                break;
            }
            if (requestCount == 10) {
                unionPaymentService.Pay(post_params,post_url);
                requestCount = 0;
                backFlag++;
            }
            try {
                Thread.currentThread().sleep(500*requestCount);
                maps= unionPaymentService.getMaps();
                requestCount++;
            } catch (InterruptedException e) {
                e.printStackTrace();
                return null;
            }
        }
        return maps;
    }
    /**
     * 向数据库保存交易信息
     * @param union 请求的交易信息
     * @param  path 交易信息是否有路径
     * @param  image 交易信息是否含有二维码
     * @parma  payMessage交易走网关支付
     * @return boolean   返回是否保存成功
     */
    private boolean processPaymentRecord(TianFuUnion union, String path, String image, String payMessage) {
        TianFuPaymentTransaction transaction = new TianFuPaymentTransaction();
        if (path != null && image != null) {
            transaction.setImagePath(path);
            transaction.setImageType(image);
        }
        if (payMessage != null) transaction.setPayMessage(payMessage);
        transaction.setStatus("0");//记录此订单支付的状态 0：未支付 ，1：支付完，2：预支付；
        transaction.setBankCode(union.getBankcode());
        transaction.setOutTradeNo(union.getOut_trade_no());
        transaction.setPartner(union.getPartner());
        transaction.setPayType(sale_Status_SALE);
        transaction.setTransportFee(union.getTransport_fee());

        //20180509 value too large for column
//        Object messages = new Gson().toJson(union).toString();
//        if (messages.toString().length()>4000){
//            transaction.setMessage(messages.toString().substring(0,4000));
//        }else {
//            transaction.setMessage(messages);
//        }
        transaction.setMessage(new Gson().toJson(union).toString());
        transaction.setProductFee(union.getProduct_fee());
        transaction.setTotalFee(union.getTotal_fee());
        transaction.setSubject(union.getSubject());
        transaction.setTransChannel(union.getTrans_channel());
        transaction.setSourceType(SOURCE_TYPE);
        transaction.setSubPartner(union.getSubpartner());
        try {
            iTianFuPaymentTransationService.paymentRecord(transaction);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
    /**
     *  查询订单支付的情况
     *  @param orderNumber 请求的交易信息
     * @param  partner 交易商户号
     * @param  subP 交易商户号
     * @return boolean   返回查询是否成功
     */
    private boolean queryStatus(String orderNumber,String partner,String subP){

        QueryPaymentStatus status= new QueryPaymentStatus();
        status.setInput_charset("UTF-8");
        status.setService("query_order_service");
        status.setService_version("1.0");
        status.setSubpartner(subP);
        status.setPartner(partner);
        status.setOut_trade_no(orderNumber);
        String appKey=iTianFuPaymentTransationService.selectList(TFInterfaceConstants.APP_CODE);
        String params= status.toSign(appKey,"MD5");
        unionQueryPaymentStatusServiceImpl paymentStatusService= new unionQueryPaymentStatusServiceImpl();
        paymentStatusService.queryStatus(params,fileService.getProfileValue(TFInterfaceConstants.BILL_URL).trim());
        Map<String,String> back=paymentStatusService.getMaps();
        int requestCount = 0;
        int backFlag = 0;
        while (back == null) {
            if (backFlag == 2) {
                backFlag = 0;
                break;
            }
            if (requestCount == 10) {
                paymentStatusService.queryStatus(params,fileService.getProfileValue(TFInterfaceConstants.BILL_URL).trim());
                requestCount = 0;
                backFlag++;
            }
            try {
                Thread.currentThread().sleep(500*requestCount);
                back=paymentStatusService.getMaps();
                requestCount++;
            } catch (InterruptedException e) {
                e.printStackTrace();
                return false;
            }
        }
        //获取的账单信息交易成功则改变订单的状态
       if(back.get("retCode").equalsIgnoreCase("0000")
               && back.get("trade_state").equalsIgnoreCase("0")){
           Date payDate=null;
           SimpleDateFormat myFmt=new SimpleDateFormat("yy-MM-ddHH:mm:ss");
           try{
               payDate= myFmt.parse(myFmt.format(new Date()));
           } catch (Exception e) {
               e.printStackTrace();
           }
           Map<String, Object> map = new HashMap<String, Object>();
           map.put("orderStatus","COMP");
           map.put("payDate",payDate);
           map.put("dappSyncFlag","N");
           if (back.get("total_fee").equalsIgnoreCase(back.get("product_fee"))  ) {
               map.put("freeShipping", "N");
           } else {
               map.put("freeShipping", "Y");
           }
         return   iTianFuPaymentTransationService.updateSalesOrderStatus(map,orderNumber);
       } else return false;
    }

    /**
     *  查询并提交折扣/信用事务（不保存）
     *  @param orderNumber 请求的交易信息
     * @param  memberId 会员
     * @return void
     * update up 15750 at 2018/04/19
     */
    private void submitDiscountTrx(IRequest request,Long memberId,String orderNumber) throws DiscountException {
        //折扣/信用事务头
        DiscountTrxHeadDto discountTrxHeadDtoList =discountTrxHeadService.queryByMemOrd(memberId,orderNumber);
        //折扣/信用事务所有信息
        Long discountTrxHeadId=discountTrxHeadDtoList.getDiscountTrxHeadId();
        discountTrxHeadDtoList=discountTrxHeadService.getDiscountTrx(request, discountTrxHeadId);

        //提交事务
        discountTrxHeadService.submitDiscountTrx(request,discountTrxHeadDtoList);
    }
}
