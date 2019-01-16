package com.lkkhpg.dsis.admin.bill.controllers;

import com.lkkhpg.dsis.admin.bill.dto.BillDto;
import com.lkkhpg.dsis.admin.bill.service.IBillDocument;
import com.lkkhpg.dsis.common.config.dto.SpmCompany;
import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.common.service.ISpmCompanyService;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.ResponseData;


import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperRunManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class BillDocumentController extends BaseController {

    @Autowired
    private IBillDocument billDocument;

    @Autowired
    private ISpmCompanyService spmCompanyService;

    /**
     * @param request
     * @param
     */
    @RequestMapping(value = "/bill/get")
    @ResponseBody
    public ResponseData getBill(HttpServletRequest request, BillDto billDto,
                                @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {

        IRequest requestContext = createRequestContext(request);
        SpmCompany company=  new SpmCompany();
        company.setCompanyId(billDocument.queryCompany(requestContext,requestContext.getAttribute(SystemProfileConstants.MARKET_ID)));
        if(billDto.getOrder_date()!=null)
        billDto.setOrder_process_date( new SimpleDateFormat("yyyyMMdd").format(billDto.getOrder_date()));
        List<SpmCompany>values=spmCompanyService.queyBrNo(company);
        List<BillDto>billDtos=new ArrayList<BillDto>();
        if(values.size()!=0){
            company=values.get(0);
            if(company.getParter()!=null){
                billDtos=billDocument.selectUnit(requestContext,billDto,company.getParter(),company.getPrivateSubparter(),company.getPublicSubparter(), page,pagesize);
            }
        }
     return new ResponseData(billDtos);

    }
    @RequestMapping(value = "/bill/export")
    @ResponseBody
    public ResponseData exportHtml(HttpServletRequest request, HttpServletResponse response)throws  Exception{
        Connection conn=null;
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String url = "jdbc:oracle:thin:@//47.104.17.194:1521/ORCl";
            String user = "dsis";
            String password = "dsis";
            conn = DriverManager.getConnection(url, user, password);
        }catch (ClassNotFoundException e){
            throw  e;
        }
        String path=BillDocumentController.class.getClassLoader().getResource("").toString();
        path=path.replace('/', '\\');
        path=path.replace("file:", "");
        path=path.replace("classes\\", "");
        path=path.substring(1);
        path+="jasperfiles"+File.separator+"demo.jasper";
        Map<String,Object> params= new HashMap<String,Object>();
        params.put("REPORT_CONNECTION",conn);
        File f= new File(path);
        if(f.exists()||conn==null){
            JasperPrint jasperPrint = JasperFillManager.fillReport(f.getPath(), params);
            JasperExportManager.exportReportToPdfFile(jasperPrint,  path+"demo"+".pdf");
        }
        return new ResponseData();
    }

}
