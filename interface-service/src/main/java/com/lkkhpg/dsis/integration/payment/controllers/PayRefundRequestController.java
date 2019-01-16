package com.lkkhpg.dsis.integration.payment.controllers;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lkkhpg.dsis.common.constant.OrderConstants;
import com.lkkhpg.dsis.common.constant.TFInterfaceConstants;
import com.lkkhpg.dsis.common.member.dto.Member;
import com.lkkhpg.dsis.common.order.dto.PaymentRefund;
import com.lkkhpg.dsis.common.order.dto.SalesOrder;
import com.lkkhpg.dsis.common.service.ICommMemberService;
import com.lkkhpg.dsis.common.service.ICommSalesOrderService;
import com.lkkhpg.dsis.integration.payment.dto.PayRefundRequest;
import com.lkkhpg.dsis.integration.payment.dto.ReportRefunds;
import com.lkkhpg.dsis.integration.payment.service.IPayRefundRequestService;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.ResponseData;
import com.lkkhpg.dsis.platform.service.IProfileService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.entity.StringEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.*;

import static com.lkkhpg.dsis.common.constant.OrderConstants.SALES_STATUS_REFUND;

/**
 * Created by miaoyifan on 2017/12/6.
 * 退款退货控制层
 */
@Controller
public class PayRefundRequestController extends BaseController {
//    public static String URL = "http://172.28.22.53:22203/tianfupay/pay/frontRefund";
    @Autowired
    private IProfileService fileService;
    //    private SalesOrderService salesOrderService;
    @Autowired
    private ICommSalesOrderService commSalesOrderService;
    @Autowired
    private ICommMemberService iCommMemberService;
    @Autowired
    private IPayRefundRequestService service;
    private final Logger logger = LoggerFactory.getLogger(PayRefundRequestController.class);

    /**
     * 退货退款查询
     * 9633
     * @param dto
     * @param page
     * @param pageSize
     * @param request
     * @param response
     * @return
     * @throws IOException
     * @throws IllegalAccessException
     */
    @RequestMapping(value = "/tf/pay/refund/request/query")
    @ResponseBody
    public ResponseData query(PayRefundRequest dto,
                              @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize,
                              HttpServletRequest request,
                              HttpServletResponse response) throws IOException, IllegalAccessException {
        IRequest requestContext = createRequestContext(request);
        List<PayRefundRequest> list = service.select(requestContext, dto, page, pageSize);
        if (logger.isDebugEnabled()) {
            logger.debug(list.toString());
        }
        for(int i=0;i<list.size();i++){
            String orderNumber=list.get(i).getOutTradeNo();
            List<SalesOrder>salesOrderList=commSalesOrderService.selectDiscountTrx(orderNumber);
            BigDecimal discountAmt=salesOrderList.get(0).getDiscountAmt();
//            PayRefundRequest payRefundRequest= new PayRefundRequest();
//            payRefundRequest.setDiscountAmt(discountAmt);
            list.get(i).setDiscountAmt(discountAmt);
            BigDecimal OldTotalFee =salesOrderList.get(0).getOrderAmt();
            list.get(i).setOldTotalFee(OldTotalFee);
        }
        ResponseData data = new ResponseData(list);
        if (request.getParameter("isExport2Excel") != null && request.getParameter("isExport2Excel").equals("Y")) {
            ExportUtils.export(request, response,list);
            return null;
        } else {
            return data;
        }

    }

    /**
     * 确认退款
     *
     * @param request
     * @param response
     * @param payRefundRequest
     * @param listNo
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/tf/pay/refund/request/submit", method = RequestMethod.POST)
    public ResponseData refund(HttpServletRequest request, HttpServletResponse response, PayRefundRequest payRefundRequest
            , @RequestBody List<Map<String, String>> listNo) {
//          List<PayRefundRequest> payRefundRequestList = new ArrayList<PayRefundRequest>();
        Boolean Post = false;
        int NO = 0;
        List<String> out = new ArrayList<String>();
        ResponseData responseData = new ResponseData();
        try {
            for (Map<String, String> map : listNo) {
                Map<String, String> map1 = new HashMap<String, String>();
                map1.put("sign_type", "MD5");
                map1.put("transaction_id", map.get("transactionId"));
                map1.put("trans_channel", map.get("transChannel"));
                map1.put("total_fee", map.get("totalFee"));
                map1.put("out_refund_no", map.get("outRefundNo"));
                map1.put("refund_fee", map.get("refundFee"));
                map1.put("out_trade_no", map.get("outTradeNo"));
                map1.put("sign", map.get("md5Encode"));
                map1.put("input_charset", "UTF-8");
                map1.put("service_version", "1.0");
                map1.put("service", "refund_service");
                map1.put("partner", map.get("partner"));
                map1.put("refund_reason", map.get("redundReason"));
                map1.put("subpartner", map.get("subpartner"));
                JSONObject jsonMap = JSONObject.fromObject(map1);
                if("0".equals(map.get("refundFee"))){
                    PayRefundRequest payRefundRequest1 = new PayRefundRequest();
                    payRefundRequest1.setIsrefund(OrderConstants.SALES_STATUS_REFUND);
                    payRefundRequest1.setRefundId( map.get("outRefundNo"));
                    payRefundRequest1.setOutTradeNo(map.get("outTradeNo"));
                    service.payRefundResult(payRefundRequest1);
                    SalesOrder salesOrder1 = new SalesOrder();
                    salesOrder1.setOrderNumber(map.get("outTradeNo"));
                    salesOrder1.setOrderStatus(OrderConstants.SALES_STATUS_REFUND);
                    commSalesOrderService.updateOrderStatusRefund(salesOrder1);
                    responseData.setCode("true");
                    out.add(map.get("outTradeNo"));
                }else {
                    String URL=fileService.getProfileValue(TFInterfaceConstants.REFUND_URL).trim();
                    Post = this.Post(jsonMap, URL, request,response);
                    responseData.setCode(String.valueOf(Post));
                    if(Post==true){
                        out.add(map.get("outTradeNo"));
                    }
                    responseData.setRows(out);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        return responseData;
    }

    public boolean Post(JSONObject json, String URL, HttpServletRequest request,HttpServletResponse response) {
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(URL);
        post.setHeader("Content-Type", "application/json");
        String result = "";
        try {
            StringEntity s = new StringEntity(json.toString(), "utf-8");
            s.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
                    "application/json"));
            post.setEntity(s);

            // 发送请求
            HttpResponse httpResponse = client.execute(post);

            //获取响应输入流
            InputStream inStream = httpResponse.getEntity().getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, "utf-8"));
            StringBuilder strber = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null)
                strber.append(line + "\n");
            inStream.close();

            result = strber.toString();
            System.out.println(s);
            System.out.println(result);

            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                System.out.println("请求服务器成功");
            } else {
                System.out.println("请求服务器失败");
            }

        } catch (Exception E) {
            System.out.println("请求异常");
            throw new RuntimeException(E);

        }
        /*
         *验证银行发送过来的报文；签名验证
         */
        boolean flag = false;
        boolean flag1 = false;

        /*
         *验证成功后，进行数据的保存和记录；改变订单的状态。
         */

//        if(flag=true){
        try {
            String Json = result;
            PayRefundRequest payRefundRequest = new PayRefundRequest();
            JSONObject jsonObject = JSONObject.fromObject(Json);
            //              System.out.println(jsonObject);

            String temp_string = jsonObject.toString();
//            if (temp_string.indexOf("returnSerNo") == -1) {
            if((String) jsonObject.get("refund_status")!=null) {
                int n = Integer.parseInt((String) jsonObject.get("refund_status"));
                SalesOrder salesOrder = new SalesOrder();
                if (n == 0) {
                    //0：退款成功。
                    payRefundRequest.setRefundId((String) jsonObject.getString("returnSerNo"));
                    payRefundRequest.setIsrefund(OrderConstants.SALES_STATUS_REFUND);
                    payRefundRequest.setOutTradeNo((String) jsonObject.getString("out_trade_no"));
//               payRefundRequest.setReturnDateTo(new Date("yyyy-MM-dd"));
                    flag = service.payRefundResult(payRefundRequest);

                    salesOrder.setOrderNumber((String) jsonObject.getString("out_trade_no"));
                    salesOrder.setOrderStatus(OrderConstants.SALES_STATUS_REFUND);
                    flag1 = commSalesOrderService.updateOrderStatusRefund(salesOrder);


                } else {
                    switch (n) {
                        //1：退款失败。
                        case 1:
                            payRefundRequest.setAttribute15("1");
                            //3：退款处理中。
                        case 3:
                            payRefundRequest.setAttribute15("3");
                            //4：未确定，需要商户原退款单号重新发起。
                        case 4:
                            payRefundRequest.setAttribute15("4");
                            //5：转入代发，退款到银行发现用户的卡作废或者冻结了，导致原路退款银行卡失败，资金回流到商户的现金帐号，需要商户人工干预，通过线下或者平台转账的方式进行退款。
                        case 5:
                            payRefundRequest.setAttribute15("5");
                    }
                    payRefundRequest.setRefundId((String) jsonObject.getString("returnSerNo"));
                    payRefundRequest.setIsrefund(OrderConstants.SALES_STATUS_REFUND_FAIL);
                    payRefundRequest.setOutTradeNo((String) jsonObject.getString("out_trade_no"));
                    service.payRefundResult(payRefundRequest);

                    salesOrder.setOrderNumber((String) jsonObject.getString("out_trade_no"));
                    salesOrder.setOrderStatus(OrderConstants.SALES_STATUS_REFUND_FAIL);

                    String orderNumber = (String) jsonObject.getString("out_trade_no");
                    List<Member> memberList = iCommMemberService.selectMember(orderNumber);
                    Long marketId = memberList.get(0).getMarketId();
                    String memberId = memberList.get(0).getMemberId().toString();
                    String member_code = memberList.get(0).getMemberCode();
                    commSalesOrderService.pulishSysRefuseMessage(orderNumber, marketId, memberId, member_code);
                    return flag = false;
                }
                }else {
                System.out.println(jsonObject.get("retMsg"));
                logger.debug((String) jsonObject.get("retMsg"));
            }
//               response.sendRedirect("");

//            } else {
//
//            }
        } catch (Exception e) {
//                System.out.println("the notify insert payment result is error!!");
            e.printStackTrace();
        }
        //       }
        System.out.println(result);
        return flag;
    }

    /**
     * 拒绝退款
     *
     * @param listNo
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/tf/pay/refund/refuse", method = RequestMethod.POST)
    public ResponseData refuse(@RequestBody List<Map<String, String>> listNo) throws Exception {
        List<String> arrayList1 = new ArrayList<String>();
        for (Map<String, String> map : listNo) {
            System.out.println(map);
            arrayList1.add(map.get("outTradeNo"));
            String orderNumber = map.get("outTradeNo");
            List<Member> memberList = iCommMemberService.selectMember(orderNumber);
            System.out.println(memberList);
            Long marketId = memberList.get(0).getMarketId();
            String memberId = memberList.get(0).getMemberId().toString();
            String member_code = memberList.get(0).getMemberCode();
            boolean flag2 = false;
            flag2 = commSalesOrderService.pulishSysRefuseMessage(orderNumber, marketId, memberId, member_code);
        }
        System.out.println(arrayList1);
        PayRefundRequest payRefundRequest = new PayRefundRequest();
        payRefundRequest.setIsrefund(OrderConstants.SALES_STATUS_REFUSE);
        payRefundRequest.setOutTradeNo(arrayList1.get(0));
        boolean flag = false;
        flag = service.payRefundResult(payRefundRequest);
        SalesOrder salesOrder = new SalesOrder();
        salesOrder.setOrderStatus(OrderConstants.SALES_STATUS_REFUSE);
        salesOrder.setOrderNumber(arrayList1.get(0));
        boolean flag1 = false;
        flag1 = commSalesOrderService.updateOrderStatusRefund(salesOrder);
        ResponseData responseData = new ResponseData();
        responseData.setCode(String.valueOf(flag1));
        System.out.println(responseData.getCode());

        return responseData;
    }

    @RequestMapping(value = "om/order/reportRefunds")
    @ResponseBody
    public ResponseData reportRefunds(HttpServletRequest request, ReportRefunds reportRefunds, int page, int pagesize) {

        List<ReportRefunds> reportRefundsList = service.reportRefunds(createRequestContext(request), reportRefunds, page, pagesize);

        return new ResponseData(reportRefundsList);
    }





    public String Post(JSONObject json, String URL) throws IOException {
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(URL);
        post.setHeader("Content-Type", "application/json");
        String result = "";

        try {
            StringEntity s = new StringEntity(json.toString(), "utf-8");
            s.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
                    "application/json"));
            post.setEntity(s);

            // 发送请求
            HttpResponse httpResponse = client.execute(post);

            //获取响应输入流
            InputStream inStream = httpResponse.getEntity().getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, "utf-8"));
            StringBuilder strber = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null)
                strber.append(line + "\n");
            inStream.close();

            result = strber.toString();
            System.out.println(result);

            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                System.out.println("请求服务器成功");
            } else {
                System.out.println("请求服务器失败");
            }

        } catch (IOException E) {
            System.out.println("请求异常");
            throw E;

        }

        return result;
    }
}
