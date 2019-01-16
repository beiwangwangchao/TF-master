/*
 *
 */
package com.lkkhpg.dsis.platform.report.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.StringUtil;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.attachment.AttachCategory;
import com.lkkhpg.dsis.platform.dto.attachment.Attachment;
import com.lkkhpg.dsis.platform.dto.attachment.SysFile;
import com.lkkhpg.dsis.platform.report.BirtUrlResolver;
import com.lkkhpg.dsis.platform.report.ExportType;
import com.lkkhpg.dsis.platform.report.Report;
import com.lkkhpg.dsis.platform.report.ReportHandlerFactory;
import com.lkkhpg.dsis.platform.report.constant.ReportConstant;
import com.lkkhpg.dsis.platform.report.exception.ReportException;
import com.lkkhpg.dsis.platform.report.html.HtmlGenerator;
import com.lkkhpg.dsis.platform.report.service.IReportDataService;
import com.lkkhpg.dsis.platform.report.service.IReportHandler;
import com.lkkhpg.dsis.platform.report.service.IReportService;
import com.lkkhpg.dsis.platform.report.util.ReportUtil;
import com.lkkhpg.dsis.platform.report.util.XML2CSV;
import com.lkkhpg.dsis.platform.service.attachment.IAttachCategoryService;
import com.lkkhpg.dsis.platform.service.attachment.ISysFileService;
import com.lkkhpg.dsis.platform.util.PathResolver;

/**
 * 报表Service.
 * 
 * @author chenjingxiong
 */
@Service
public class ReportServiceImpl implements IReportService {

    private static Logger logger = LoggerFactory.getLogger(ReportServiceImpl.class);

    @Autowired
    private HtmlGenerator htmlGenerator;

    @Autowired
    private IAttachCategoryService attachCategoryService;

    @Autowired
    private ISysFileService sysFileService;

    @Override
    public Report generateReport(IRequest request, IReportDataService dataService, Map<String, Object> param,
            String template, String exportType, String fileName, boolean saveOutput) throws ReportException {
        Report report = new Report();

        // 获取报表数据
        Map<String, Object> reportData = dataService.process(request, param);
        reportData.put("springMacroRequestContext", request.getAttribute("springMacroRequestContext"));
        // 获取模板生成静态html
        String html = null;
        try {
            html = htmlGenerator.generate(template, reportData);
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error("Html generate error!", e);
            }
            throw new ReportException(ReportException.MSG_ERROR_REPORT_HTML_ERROR, null, e);
        }

        // 转化成流
        ByteArrayInputStream bis = new ByteArrayInputStream(html.getBytes());

        // 获取处理器
        IReportHandler reportHandler = ReportHandlerFactory.getHandler(exportType);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        if (reportHandler != null) {
            // 转化格式
            try {
                reportHandler.process(bis, out);
                // 随机文件名
                // 随机文件名
                if (fileName == null) {
                    fileName = UUID.randomUUID().toString();
                }

                report.setContentType(reportHandler.getContentType());
                report.setFileName(fileName + "." + reportHandler.getExtension());
                report.setReportData(out.toByteArray());
                if (saveOutput) {
                    saveAttachment(request, report);
                }

                return report;

            } catch (IOException e) {
                if (logger.isErrorEnabled()) {
                    logger.error("error occur when processing transafer document！", e);
                }
                throw new ReportException(ReportException.MSG_ERROR_REPORT_HTML_ERROR, null, e);
            } finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                } catch (IOException e) {
                    if (logger.isErrorEnabled()) {
                        logger.error("error occur when closing out:", e);
                    }
                    throw new ReportException(ReportException.MSG_ERROR_REPORT_HTML_ERROR, null, e);
                }
            }
        } else {
            throw new ReportException(ReportException.MSG_ERROR_REPORT_TYPE_NOT_SUPPORT, null);
        }
    }

    @Override
    public Report generateReport(IRequest request, Map<String, Object> param, String template, String exportType,
            String fileName, boolean saveOutput) throws ReportException {
        Report report = new Report();

        List<NameValuePair> paramList = new ArrayList<>();
        paramList.add(new BasicNameValuePair("__report", template));
        paramList.add(new BasicNameValuePair("__pageoverflow", "0"));
        paramList.add(new BasicNameValuePair("__overwrite", "false"));

        // 获取request数据
        Map<String, Object> requestMap = request.getAttributeMap();
        requestMap.keySet().forEach(key -> {
            String realKey = key.startsWith("_") ? key.substring(1) : key;
            realKey = realKey.startsWith("MDC.") ? realKey.substring(4) : realKey;
            if (param.get(realKey) == null && requestMap.get(key) != null) {
                paramList.add(new BasicNameValuePair(realKey, requestMap.get(key).toString()));
            }
        });

        param.keySet().forEach(key -> {
            if (param.get(key) != null) {
                paramList.add(new BasicNameValuePair(key, param.get(key).toString()));
            }
        });

        IReportHandler reportHandler = ReportHandlerFactory.getHandler(exportType);

        if (reportHandler == null) {
            throw new ReportException(ReportException.MSG_ERROR_REPORT_TYPE_NOT_SUPPORT, null);
        }

        paramList.add(new BasicNameValuePair("__format", reportHandler.getExtension()));

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            ReportUtil.httpGet(BirtUrlResolver.getOutputUrl(), paramList, out);

            // 随机文件名
            if (fileName == null) {
                fileName = UUID.randomUUID().toString();
            }
            report.setContentType(reportHandler.getContentType());

            if (exportType.equals(ExportType.CSV.getCode())) {

                SAXParserFactory factory = SAXParserFactory.newInstance();
                SAXParser parser = factory.newSAXParser();
                XML2CSV xml2csv = new XML2CSV();
                parser.parse(new ByteArrayInputStream(out.toByteArray()), xml2csv);
                ByteArrayOutputStream outStream = xml2csv.outStream;
                String outString = outStream.toString(XML2CSV.CSV_CHARSET_NANME);
                String outputString = new String();
                if (StringUtil.isNotEmpty(outString)&&outString.substring(0, outString.length() - XML2CSV.CSV_LINE_END_FLAG.length())
                        .lastIndexOf(XML2CSV.CSV_LINE_END_FLAG)>0) {
                    outputString = outString.substring(0,
                            outString.substring(0, outString.length() - XML2CSV.CSV_LINE_END_FLAG.length())
                                    .lastIndexOf(XML2CSV.CSV_LINE_END_FLAG));
                }

                report.setFileName(fileName + "." + ExportType.CSV.getCode());
                report.setReportData(outputString.getBytes(XML2CSV.CSV_CHARSET_NANME));
            } else {
                report.setFileName(fileName + "." + reportHandler.getExtension());
                report.setReportData(out.toByteArray());
            }

            if (saveOutput) {
                saveAttachment(request, report);
            }

            return report;
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error("error occur when submit report request！", e);
            }
            throw new ReportException(ReportException.MSG_ERROR_REPORT_HTML_ERROR, null, e);
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                if (logger.isErrorEnabled()) {
                    logger.error("error occur when closing out:", e);
                }
                throw new ReportException(ReportException.MSG_ERROR_REPORT_HTML_ERROR, null, e);
            }
        }

    }

    private Report saveAttachment(IRequest request, Report report) throws IOException {
        // 保存输出文件.
        AttachCategory category = attachCategoryService.selectAttachByCode(request, ReportConstant.OUTPUT_SOURCE_TYPE);
        if (category == null) {
            // 没有定义附件类型，不存储到文件系统
            if (logger.isDebugEnabled()) {
                logger.debug("Category Not Defined, Cannot save output file, skip.");
            }
        } else {
            SysFile sysFile = createAttachment(request, category, report);
            if (sysFile != null) {
                report.setFileId(sysFile.getFileId());
            }
        }
        return report;
    }

    /**
     * 创建附件.
     * 
     * @param request
     *            统一上下文.
     * @param category
     *            附件类别.
     * @param report
     *            报表对象.
     * @return 附件文件.
     * @throws IOException
     *             IO异常.
     */
    private SysFile createAttachment(IRequest request, AttachCategory category, Report report) throws IOException {
        String path = category.getCategoryPath();
        String basePath = request.getAttribute("basePath");
        if (!StringUtils.isEmpty(basePath)) {
            path = PathResolver.getRealPath(basePath, path);
        }
        // 保存文件.
        File file = writeToPath(path, report.getFileName(), report.getReportData());
        // 清除内容.
        report.setReportData(null);

        Date current = new Date();
        Attachment attachment = new Attachment();
        attachment.setCategoryId(category.getCategoryId());
        attachment.setSourceType(ReportConstant.OUTPUT_SOURCE_TYPE);
        attachment.setName(ReportConstant.OUTPUT_SOURCE_TYPE);
        attachment.setSourceKey("-1");
        attachment.setStatus("1");
        attachment.setStartActiveDate(current);

        SysFile sysFile = new SysFile();
        sysFile.setUploadDate(current);
        sysFile.setFileName(report.getFileName());
        sysFile.setFileType(report.getContentType());
        sysFile.setFileSize(file.getTotalSpace());
        sysFile.setFilePath(file.getAbsolutePath());
        sysFile = sysFileService.insertFileAndAttach(request, attachment, sysFile);
        return sysFile;
    }

    /**
     * 将文件写入文件系统中.
     * 
     * @param path
     *            文件路径.
     * @param fileName
     *            文件名称.
     * @param fileContent
     *            文件内容.
     * @return 文件路径.
     * @throws IOException
     *             IO异常.
     */
    private File writeToPath(String path, String fileName, byte[] fileContent) throws IOException {
        FileOutputStream fout = null;
        try {
            File dir = new File(path + File.separator);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(dir, fileName);
            fout = new FileOutputStream(file);
            fout.write(fileContent);

            return file;
        } finally {
            if (fout != null) {
                fout.close();
            }
        }
    }
}
