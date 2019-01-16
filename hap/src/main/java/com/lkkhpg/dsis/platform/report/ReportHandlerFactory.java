/*
 *
 */
package com.lkkhpg.dsis.platform.report;

import com.lkkhpg.dsis.platform.report.service.IReportHandler;
import com.lkkhpg.dsis.platform.report.service.impl.CsvReportHandler;
import com.lkkhpg.dsis.platform.report.service.impl.ExcelReportHandler;
import com.lkkhpg.dsis.platform.report.service.impl.HtmlReportHandler;
import com.lkkhpg.dsis.platform.report.service.impl.PdfReportHandler;

/**
 * 导出类型工厂类.
 * 
 * @author chenjingxiong
 */
public final class ReportHandlerFactory {

    private static IReportHandler excelHandler = new ExcelReportHandler();

    private static IReportHandler pdfHandler = new PdfReportHandler();

    private static IReportHandler htmlHandler = new HtmlReportHandler();
    private static IReportHandler csvHandler = new CsvReportHandler();

    private ReportHandlerFactory() {
    }

    /**
     * 根据类型获取不同的处理器.
     * 
     * @param type
     *            输出类型.
     * @return 处理器.
     */
    public static IReportHandler getHandler(String type) {
        if (ExportType.EXCEL.getCode().equals(type)) {
            return excelHandler;
        } else if (ExportType.PDF.getCode().equals(type)) {
            return pdfHandler;
        } else if (ExportType.HTML.getCode().equals(type)) {
            return htmlHandler;
        } else if (ExportType.CSV.getCode().equals(type)) {
            return csvHandler;
        } else {
            return null;
        }
    }

}
