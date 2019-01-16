package com.lkkhpg.dsis.admin.bill.service.impl;

import com.github.pagehelper.PageHelper;
import com.lkkhpg.dsis.common.constant.TFInterfaceConstants;
import com.lkkhpg.dsis.admin.bill.dto.BillDto;
import com.lkkhpg.dsis.admin.bill.mapper.BillDocumentMapper;
import com.lkkhpg.dsis.admin.bill.service.IBillDocument;
import com.lkkhpg.dsis.common.config.mapper.SpmCompanyMapper;
import com.lkkhpg.dsis.integration.payment.controllers.MD5Sign;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.service.IProfileService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional(rollbackFor = Exception.class)
public class BillDocumentServiceImpl implements IBillDocument {

    @Autowired
    private BillDocumentMapper billDocumentMapper;

    @Autowired
    private IProfileService profileService;

    @Autowired
    private SpmCompanyMapper spmCompanyMapper;

    private static Logger log = LoggerFactory.getLogger(BillDocumentServiceImpl.class);


    @Override
    public List<BillDto> selectUnit(IRequest request, BillDto billDto, String partner, String privateSubpartner, String publicPartner, int page, int pagesize){
        billDto.setPartnerNo(partner);
        if(privateSubpartner!=null){
            billDto.setPrivateSubPartner(privateSubpartner);
        }
        if(publicPartner!=null){
            billDto.setPublicSubPartner(publicPartner);
        }
        PageHelper.startPage(page,pagesize);
        List<BillDto>listAll= billDocumentMapper.selectUnit(billDto);
        return  listAll;
    }


    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public boolean insert(List<BillDto> billDto) {
        try {
            for(BillDto bill:billDto) {
                billDocumentMapper.insert(bill);
            }
        }catch (Exception e){
        //    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
        return true;
    }


    @Override
    public List<BillDto> queryData(String order_process_data) {
        return billDocumentMapper.selectByProcessDate(order_process_data.trim());
    }

   @Override
   public  Long queryCompany(IRequest request, Long marketId){
        return billDocumentMapper.queryMarket(marketId);
    }

    @Override
    public boolean listeningDownloadAccountList()throws Exception{
        Calendar cal=Calendar.getInstance();
        cal.add(Calendar.DATE,-1);
        Date time=cal.getTime();
        String date=new SimpleDateFormat("yyyyMMdd").format(time);
        String maxDate=billDocumentMapper.selectMaxDate();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
        String bill_url = profileService.getProfileValue(TFInterfaceConstants.BILL_URL);
        String app_key = profileService.getProfileValue(TFInterfaceConstants.APP_KEY);
        Calendar rightNow = Calendar.getInstance();
        if(maxDate!=null){
             //假设停电后事务没有回滚，就在验证此时的账单是否完全(假设是能够回滚的）
           //proceeAccountLists(maxDate, bill_url);
            Long needDate=Long.parseLong(date);
            while (needDate>Long.parseLong(maxDate)){
                try{
                    Date dt=sdf.parse(maxDate);
                    rightNow.setTime(dt);
                    rightNow.add(Calendar.DAY_OF_YEAR,1);
                    Date dt1=rightNow.getTime();
                    maxDate = sdf.format(dt1);
                    proceeAccountLists(maxDate, bill_url, app_key);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }else {
            proceeAccountLists(date,bill_url, app_key);
        }

        return true;
    }

    private boolean proceeAccountLists(String date , String bill_url,String app_key)throws Exception{
        int count=0;
        boolean flag;
        Map<String, String> requestParameter=new HashMap<String,String>();
        requestParameter.put("order_date", date);
        requestParameter.put("order_type", "0001");
        requestParameter.put("service", "account_download_service");//接口服务名
        requestParameter.put("service_version", "1.0");//借口版本
        requestParameter.put("input_charset", "UTF-8");//字符集
        requestParameter.put("sign_type", "MD5");//签名类型
        requestParameter.put("pageSize","1000");//每頁的記錄
        List<String> listPartenrs=spmCompanyMapper.queryPartner();
        for(String item:listPartenrs) {
            if(item!=null&&item.substring(0,3).equals("120")){
                requestParameter.put("partner", item.trim());//商户号
                int accountNumber=-1;
                try{
                    do{
                        flag=false;
                        count+=1;
                        requestParameter.put("pageNo",String.valueOf(count));
                        //密钥KEY 也需要动态配置去获取。。。。。。。。。。待改善！！！
                        String sing = "input_charset=" + requestParameter.get("input_charset")
                                + "&order_date=" + requestParameter.get("order_date")
                                + "&order_type=" + requestParameter.get("order_type")
                                + "&pageNo=" +requestParameter.get("pageNo")
                                + "&pageSize="+requestParameter.get("pageSize")
                                + "&partner=" + requestParameter.get("partner")
                                + "&service=" + requestParameter.get("service")
                                + "&service_version=" + requestParameter.get("service_version")
                                + app_key.trim();
                        MD5Sign md5Sign = new MD5Sign();
                        String md5s = md5Sign.md5(sing);
                        requestParameter.put("sign", md5s);//签名
                        JSONObject jsonMap = JSONObject.fromObject(requestParameter);
                        String result=null;
                        try{
                            result =Post(jsonMap, bill_url);
                        }catch (Exception e){
                            count-=1;
                            flag=true;
                        }
                        if(result!=null  && !result.equals("")){
                            JSONObject jsonObject;
                            jsonObject = JSONObject.fromObject(result);
                            if(jsonObject.getString("retCode").equals("0000")){
                                if (jsonObject.getString("list") != null ||
                                        "".equals(jsonObject.getString("list")) == false) {

                                    JSONArray array = jsonObject.getJSONArray("list");
                                    List<BillDto> billDtos = new ArrayList<BillDto>();
                                    List<BillDto>bills=billDocumentMapper.selectByProcessDate(date);
                                    for (int i = 0; i < array.size(); i++) {
                                        boolean blank=false;
                                        JSONObject object = (JSONObject) array.get(i);
                                        BillDto bill = (BillDto) JSONObject.toBean(object, BillDto.class);
                                        bill.setOrder_process_date(date);
                                        if(bills.size()!=0){
                                            for(BillDto dataItem:bills){
                                                if(dataItem.getPlat_transno().equals(bill.getPlat_transno()))
                                                    blank=true ;break;
                                            }
                                            if(!blank){
                                                billDtos.add(bill);
                                            }
                                        }else {
                                            billDtos.add(bill);
                                        }
                                    }
                                    if(billDtos.size()!=0){
                                        insert(billDtos);
                                    }
                                    accountNumber=array.size();
                                }
                            }
                        }
                        if(flag){
                            try {
                                Thread.currentThread().sleep(10000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                    }while (accountNumber==1000||flag);

                }catch (Exception e){
                    throw e;
                }
            }
        }
        return  true;
    }


    private String Post(JSONObject json, String URL)throws IOException {
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(URL);
        InputStream inStream=null;
        BufferedReader reader=null;
        post.setHeader("Content-Type", "application/json");
        String result = null;
        try {
            StringEntity s = new StringEntity(json.toString(), "UTF-8");
            s.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
                    "application/json"));
            post.setEntity(s);
            HttpResponse httpResponse = client.execute(post);
            inStream = httpResponse.getEntity().getContent();
            reader = new BufferedReader(new InputStreamReader(inStream, "utf-8"));
            StringBuilder strber = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null)
                strber.append(line + "\n");
            inStream.close();
            reader.close();
            result = strber.toString();
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

            }
        } catch (IOException E) {
          if(inStream !=null)
              inStream.close();
          if(reader!=null)
              reader.close();
            throw E;
        }
        return result;
    }


}
