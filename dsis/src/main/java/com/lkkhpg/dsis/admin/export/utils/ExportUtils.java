/**
 * #{copyright}#
 */
package com.lkkhpg.dsis.admin.export.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.lkkhpg.dsis.common.user.dto.IpointUser;
import com.lkkhpg.dsis.platform.report.Report;
import com.lkkhpg.dsis.platform.report.util.ReportUtil;

/**
 * @author runbai.chen
 */
public class ExportUtils {
    public static final String EXPORT_HEADER = "exportHeader";
    public static final String EXPORT_INDEX = "exportIndex";
    public static final String EXPORT_LIST_DATA = "exportListData";
    public static final String EXPORT_CONTENT = "exportContent";

    /**
     * 导出excel
     *
     * @param request
     * @param response
     * @param list     数据内容
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws IOException
     */
    public static void export(HttpServletRequest request, HttpServletResponse response, List list)
            throws IllegalArgumentException, IllegalAccessException, IOException {

        // 构造头部参数
        Map<String, Object> exportHeader = new Gson().fromJson(request.getParameter(EXPORT_HEADER).toString(),
                new TypeToken<HashMap<String, Object>>() {
                }.getType());

        List<String> exportIndex = new Gson().fromJson(request.getParameter(EXPORT_INDEX).toString(), List.class);

        Map<String, Map<String, Object>> exportListData = new Gson()
                .fromJson(request.getParameter(EXPORT_LIST_DATA).toString(), Map.class);

        List<Map<String, Object>> exportContent = new ArrayList<Map<String, Object>>();

        for (Object record : list) {
            exportContent.add(ExportUtils.object2Map(record));
        }
        ExportUtils.export(response, exportIndex, exportHeader, exportListData, exportContent);

    }

    /**
     * @param response
     * @param exportIndex
     * @param exportHeader
     * @param exportListData
     * @param exportContent
     * @throws IOException
     */
    public static void export(HttpServletResponse response, List<String> exportIndex, Map<String, Object> exportHeader,

                              Map<String, Map<String, Object>> exportListData, List<Map<String, Object>> exportContent)
            throws IOException {

        String fileName = UUID.randomUUID().toString() + ".xls";

        Report report = new Report();

        report.setContentType("application/vnd.ms-excel;charset=UTF-8");
        report.setFileName(fileName);
        report.setReportData(
                ExportUtils.structExcel(exportIndex, exportHeader, exportListData, exportContent).toByteArray());
        // report.setReportData(exportHeader.get("loginName").toString().getBytes());
        ReportUtil.viewReport(response, report);
    }

    /**
     * @param exportIndex
     * @param exportHeader
     * @param exportListData
     * @param exportContent
     * @return 字节流
     * @throws IOException
     */
    public static ByteArrayOutputStream structExcel(List<String> exportIndex, Map<String, Object> exportHeader,
                                                    Map<String, Map<String, Object>> exportListData, List<Map<String, Object>> exportContent)
            throws IOException {
        // String excelString = new String();

        HSSFWorkbook wb = new HSSFWorkbook();// 建立新HSSFWorkbook对象
        HSSFSheet sheet = wb.createSheet("new sheet");// 建立新的sheet对象

        int rowIndex = 0;
        int columnIndex = 0;
        HSSFRow excelHeadRow = sheet.createRow(rowIndex);// 建立新行
        rowIndex++;
        // excel header
        for (String tmpIndex : exportIndex) {

            HSSFCell cell = excelHeadRow.createCell(columnIndex);// 建立新cell

            if (exportHeader.get(tmpIndex) != null && !StringUtils.isEmpty(exportHeader.get(tmpIndex).toString())) {
                cell.setCellValue(exportHeader.get(tmpIndex).toString());// 设置cell浮点类型的值
            } else {
                cell.setCellValue("");// 设置cell浮点类型的值
            }
            columnIndex++;
        }
        // excel data line
        for (Map<String, Object> row : exportContent) {
            HSSFRow excelLineRow = sheet.createRow(rowIndex);// 建立新行
            columnIndex = 0;
            DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
            for (String tmpIndex : exportIndex) {

                HSSFCell cell = excelLineRow.createCell(columnIndex);// 建立新cell
                if (row.get(tmpIndex) != null && !StringUtils.isEmpty(row.get(tmpIndex).toString())) {// 判断如果行数据不为空
                    if (exportListData.get(tmpIndex) != null) {// 如果列需要查询list值
                        // 判断如果是double类型。转换成String
                        if (row.get(tmpIndex).getClass().equals(Double.class)) {// 如果是double类型数据，转换成String
                            cell.setCellValue(exportListData.get(tmpIndex)
                                    .get(String.valueOf(new Double(row.get(tmpIndex).toString()).longValue()))
                                    .toString());
                        } else {
                            cell.setCellValue(
                                    exportListData.get(tmpIndex).get(row.get(tmpIndex).toString()).toString());
                        }

                    } else {
                        if (row.get(tmpIndex).getClass().equals(java.util.Date.class)) {// 如果是double类型数据，转换成String
                            cell.setCellValue(fmt.format(row.get(tmpIndex)));
                        } else {
                            cell.setCellValue(row.get(tmpIndex).toString());
                        }

                    }
                } else {
                    cell.setCellValue("");

                }
                columnIndex++;

            }
            rowIndex++;
        }
        ByteArrayOutputStream fileOut = new ByteArrayOutputStream();
        wb.write(fileOut);
        fileOut.close();
        return fileOut;
    }

    /**
     * 将对象，转换成Map
     *
     * @param obj
     * @return Map对象
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public static Map object2Map(Object obj) throws IllegalArgumentException, IllegalAccessException {
        if (obj == null) {
            return null;
        }

        return new org.apache.commons.beanutils.BeanMap(obj);
    }


    /**
     * @param response
     * @param exportIndex
     * @param exportHeader
     * @param exportListData
     * @param exportContent
     * @throws IOException
     * @throws DocumentException
     */
    public static void exportPdf(HttpServletResponse response, List<String> exportIndex, Map<String, Object> exportHeader,

                                 Map<String, Map<String, Object>> exportListData, List<Map<String, Object>> exportContent)
            throws IOException, DocumentException {

        String fileName = UUID.randomUUID().toString() + ".pdf";

        Report report = new Report();

        report.setContentType("application/pdf;charset=UTF-8");
        report.setFileName(fileName);
        report.setReportData(
                ExportUtils.structPdf(exportIndex, exportHeader, exportListData, exportContent).toByteArray());
        ReportUtil.viewReport(response, report);
    }


    /**
     * @param exportIndex
     * @param exportHeader
     * @param exportListData
     * @param exportContent
     * @return 字节流
     * @throws IOException
     * @throws DocumentException
     */
    public static ByteArrayOutputStream structPdf(List<String> exportIndex, Map<String, Object> exportHeader,
                                                  Map<String, Map<String, Object>> exportListData, List<Map<String, Object>> exportContent)
            throws IOException, DocumentException {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        //设置纸张大小
        int columnWidths = exportIndex.size();
        Rectangle pageSize = PageSize.A4;
        if (columnWidths > 8) {
            pageSize = PageSize.A4.rotate();
        }
        Document document = new Document(pageSize);

        //定义输出位置,把文档对象装入输出对象中
        PdfWriter pdfWriter = PdfWriter.getInstance(document, byteArrayOutputStream);
        // 使用iTextAsian.jar中的字体,解决iText无法显示中文问题
        BaseFont baseFont = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        int fontSize = 10;
        Font font = new Font(baseFont, fontSize);
        // 设置页眉页脚
        PdfExportHeaderFooterUtils headerFooter = new PdfExportHeaderFooterUtils();
        headerFooter.setBaseFont(baseFont);
        headerFooter.setPageSize(pageSize);
        headerFooter.setFontSize(fontSize);
        pdfWriter.setPageEvent(headerFooter);

        // 打开document
        document.open();

        // 动态生成 (columnWidths*3)列表格
        PdfPTable table = new PdfPTable(columnWidths * 3);
        table.setWidthPercentage(100);

        //表头部分
        for (int i = 0; i < columnWidths; i++) {
            PdfPCell cell = new PdfPCell(new Phrase(exportHeader.get(exportIndex.get(i)).toString(), font));
            cell.setBackgroundColor(new GrayColor(0.75f));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setMinimumHeight(20);
            table.addCell(setColspanForFinalBonus(cell, columnWidths, i));
        }
        table.setHeaderRows(1);//设置header
        table.getDefaultCell().setUseAscender(true);
        table.getDefaultCell().setUseDescender(true);
        table.getDefaultCell().setBackgroundColor(GrayColor.GRAYWHITE);
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);

        //表体部分
        String tempCell;
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        //对打印内容为空的情况做处理
        if (exportContent.size() == 0) {
            PdfPCell cell = new PdfPCell(new Phrase("", font));
            cell.setColspan(columnWidths * 3);
            table.addCell(cell);
        }
        for (int j = 0; j < exportContent.size(); j++) {
            for (int i = 0; i < columnWidths; i++) {
                String column = exportIndex.get(i);
                if (null != exportContent.get(j).get(column)) {
                    // 如果是Date类型数据，转换成String
                    if ((exportContent.get(j).get(column).getClass()).equals(java.util.Date.class)) {
                        tempCell = fmt.format(exportContent.get(j).get(column)).toString();
                    } else {
                        tempCell = exportContent.get(j).get(column).toString();
                    }
                    if (null != exportListData.get(column)) {
                        Map<String, Object> exportListMap = exportListData.get(column);
                        if (null != exportListMap.get(tempCell)) {
                            tempCell = exportListMap.get(tempCell).toString();
                        }
                    }
                } else {
                    tempCell = "";
                }
                PdfPCell pdfCell = new PdfPCell(new Phrase(tempCell, font));
                table.addCell(setColspanForFinalBonus(pdfCell, columnWidths, i));
            }
        }
        document.add(table);
        // 关闭document
        document.close();
        return byteArrayOutputStream;
    }


    /**
     * 设置导出PDF的格式(个性化)-最终奖金页面
     *
     * @param pdfCell      table的cell单元
     * @param columnWidths table的列数
     * @param index        table的第几列
     * @return pdfCell
     * table的cell单元
     */
    public static PdfPCell setColspanForFinalBonus(PdfPCell pdfCell, int columnWidths, int index) {
        if (index % columnWidths == 3) {
            pdfCell.setColspan(4);
        } else if ((index + 1) % columnWidths == 0) {
            pdfCell.setColspan(2);
        } else {
            pdfCell.setColspan(3);
        }
        return pdfCell;
    }


}
