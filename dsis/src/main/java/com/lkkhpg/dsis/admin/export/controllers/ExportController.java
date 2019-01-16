/**
 * #{copyright}#
 */
package com.lkkhpg.dsis.admin.export.controllers;

import java.io.IOException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.lkkhpg.dsis.admin.export.utils.ExportUtils;
import com.lkkhpg.dsis.platform.controllers.BaseController;

/**
 * @author runbai.chen
 */
@Controller
public class ExportController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(ExportController.class);

    /**
     * 
     * @param request
     * @param response
     * @param allRequestParams
     * @throws IOException
     */
    @RequestMapping("/export/excel")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response,
            @RequestParam Map<String, Object> allRequestParams) throws IOException {

        String fileName = UUID.randomUUID().toString() + ".xls";
        // 构造头部参数
        Map<String, Object> exportHeader = new Gson().fromJson(
                allRequestParams.get(ExportUtils.EXPORT_HEADER).toString(), new TypeToken<HashMap<String, Object>>() {
                }.getType());

        List<Map<String, Object>> exportContent = new Gson()
                .fromJson(allRequestParams.get(ExportUtils.EXPORT_CONTENT).toString(), List.class);
        // 解决所有 数值类型 被默认转化为Double问题
        // List<Map<String, Object>> exportContent = new
        // Gson().fromJson(allRequestParams.get("exportContent").toString(),
        // new TypeToken<List<Map<String, String>>>() {
        // }.getType());
        List<String> exportIndex = new Gson().fromJson(allRequestParams.get(ExportUtils.EXPORT_INDEX).toString(),
                List.class);

        Map<String, Map<String, Object>> exportListData = new Gson()
                .fromJson(allRequestParams.get(ExportUtils.EXPORT_LIST_DATA).toString(), Map.class);

        ExportUtils.export(response, exportIndex, exportHeader, exportListData, exportContent);
    }


    /**
     *
     * @param request
     * @param response
     * @param allRequestParams
     * @throws IOException
     * @throws DocumentException
     */
    @RequestMapping("/export/pdf")
    public void exportPdf(HttpServletRequest request, HttpServletResponse response,
                          @RequestParam Map<String, Object> allRequestParams) throws IOException, DocumentException{

        // 构造头部参数
        Map<String, Object> exportHeader = new Gson().fromJson(
                allRequestParams.get(ExportUtils.EXPORT_HEADER).toString(), new TypeToken<HashMap<String, Object>>() {
                }.getType());
        List<Map<String, Object>> exportContent = new Gson()
                .fromJson(allRequestParams.get(ExportUtils.EXPORT_CONTENT).toString(), List.class);
        List<String> exportIndex = new Gson().fromJson(allRequestParams.get(ExportUtils.EXPORT_INDEX).toString(),
                List.class);

        Map<String, Map<String, Object>> exportListData = new Gson()
                .fromJson(allRequestParams.get(ExportUtils.EXPORT_LIST_DATA).toString(), Map.class);

        ExportUtils.exportPdf(response, exportIndex, exportHeader, exportListData, exportContent);
    }



}
