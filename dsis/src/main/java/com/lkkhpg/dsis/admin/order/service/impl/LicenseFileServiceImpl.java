/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.order.service.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.lkkhpg.dsis.admin.order.service.ILicenseFileService;
import com.lkkhpg.dsis.common.constant.OrderConstants;
import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.common.exception.CommDeliveryException;
import com.lkkhpg.dsis.common.exception.CommLicenseFileException;
import com.lkkhpg.dsis.common.exception.CommMemberException;
import com.lkkhpg.dsis.common.exception.CommOrderException;
import com.lkkhpg.dsis.common.exception.CommSystemProfileException;
import com.lkkhpg.dsis.common.member.dto.MemCard;
import com.lkkhpg.dsis.common.member.mapper.MemCardMapper;
import com.lkkhpg.dsis.common.order.dto.AutoshipOrder;
import com.lkkhpg.dsis.common.order.dto.LicenseFile;
import com.lkkhpg.dsis.common.order.dto.OrderPayment;
import com.lkkhpg.dsis.common.order.dto.SalesOrder;
import com.lkkhpg.dsis.common.order.mapper.AutoshipOrderMapper;
import com.lkkhpg.dsis.common.order.mapper.LicenseFileMapper;
import com.lkkhpg.dsis.common.order.mapper.SalesOrderMapper;
import com.lkkhpg.dsis.common.service.ICommOrderPaymentService;
import com.lkkhpg.dsis.common.service.IParamService;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.security.service.IAESClientService;
import com.lkkhpg.dsis.platform.upload.FileInfo;

/**
 * 授权文件CSV服务接口实现.
 * 
 * @author xiawang.liu@hand-china.com
 */
@Service
@Transactional
public class LicenseFileServiceImpl implements ILicenseFileService {

    private final Logger logger = LoggerFactory.getLogger(LicenseFileServiceImpl.class);

    @Autowired
    private IParamService paramService;

    @Autowired
    private SalesOrderMapper salesOrderMapper;

    @Autowired
    private LicenseFileMapper licenseFileMapper;

    @Autowired
    private ICommOrderPaymentService commOrderPaymentService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private IAESClientService iaesClientService;
    @Autowired
    private AutoshipOrderMapper autoshipOrderMapper;
    @Autowired
    private MemCardMapper memCardMapper;

    /**
     * 格式化字符串.
     * 
     * @param str
     *            需要格式化的字符串
     * @param strLength
     *            格式化长度
     * @param flag
     *            格式化类型
     * @return 格式化后的字符串
     */
    public static String addZeroOrNullForStr(String str, int strLength, String flag) {
        if (OrderConstants.CSV_FLAG_N.equals(flag)) {
            return StringUtils.leftPad(str, strLength, "0");
        } else {
            return StringUtils.rightPad(str, strLength);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void createCSVFile(IRequest request, OutputStream out, LicenseFile licenseFile, long salesOrgId,
            long orderCount, String terminalCode, String rShopId) throws CommLicenseFileException {
        try (BufferedWriter csvFileOutputStream = new BufferedWriter(new OutputStreamWriter(out),
                OrderConstants.CSV_LENGTH_1024)) {
            if (orderCount == 0) {
                throw new CommLicenseFileException(CommLicenseFileException.CSV_DOWNLOAD_ERROR_FILE_FAIL, null);
            }
            String orderNum = addZeroOrNullForStr(String.valueOf(orderCount), OrderConstants.CSV_LENGTH_8,
                    OrderConstants.CSV_FLAG_N);
            // 订单总金额
            BigDecimal OP = salesOrderMapper.queryOrdersPayByBatchNumber(licenseFile.getBatchNumber());
            String OrdersPay = addZeroOrNullForStr(String.valueOf(OP == null ? "0" : OP), OrderConstants.CSV_LENGTH_15,
                    OrderConstants.CSV_FLAG_N);
            // 头保留字段
            String hRetain = addZeroOrNullForStr("", OrderConstants.CSV_LENGTH_183, OrderConstants.CSV_FLAG_A);
            // 表头 = 记录型别 + 系统日期 + 流水号 + 订单笔数 +订单金额综合＋保留栏位
            StringBuilder head = new StringBuilder();
            head.append(OrderConstants.CSV_HEAD_RECORDTYPE);
            head.append(licenseFile.getBatchNumber());
            head.append(orderNum);
            head.append(OrdersPay);
            head.append(hRetain);
            csvFileOutputStream.write(head.toString());
            csvFileOutputStream.newLine();

            // 写入文件内容
            // 3行保留字段
            String rRetainA = addZeroOrNullForStr("", OrderConstants.CSV_LENGTH_12, OrderConstants.CSV_FLAG_A);
            // 5分期期数
            String installment = addZeroOrNullForStr("", OrderConstants.CSV_LENGTH_2, OrderConstants.CSV_FLAG_A);
            // 6行保留字段
            String rRetainB = addZeroOrNullForStr("", OrderConstants.CSV_LENGTH_2, OrderConstants.CSV_FLAG_A);
            // 7交易状态
            String tradingStatus = addZeroOrNullForStr("", OrderConstants.CSV_LENGTH_12, OrderConstants.CSV_FLAG_A);
            // 10CVC2
            String cvc = addZeroOrNullForStr("", OrderConstants.CSV_LENGTH_3, OrderConstants.CSV_FLAG_A);
            // 12订单资讯
            String orderInformation = addZeroOrNullForStr("", OrderConstants.CSV_LENGTH_26, OrderConstants.CSV_FLAG_A);
            // 15授权码
            String authorizationCode = addZeroOrNullForStr("", OrderConstants.CSV_LENGTH_6, OrderConstants.CSV_FLAG_A);
            // 16结果码
            String resultCode = addZeroOrNullForStr("", OrderConstants.CSV_LENGTH_2, OrderConstants.CSV_FLAG_A);
            // 17错误回复码
            String errorCode = addZeroOrNullForStr("", OrderConstants.CSV_LENGTH_2, OrderConstants.CSV_FLAG_A);
            // 18产品代码
            String productCode = addZeroOrNullForStr("", OrderConstants.CSV_LENGTH_2, OrderConstants.CSV_FLAG_A);
            // 19折抵金额
            String discountAmount = addZeroOrNullForStr("", OrderConstants.CSV_LENGTH_13, OrderConstants.CSV_FLAG_A);
            // 20原始金额
            String originalAmount = addZeroOrNullForStr("", OrderConstants.CSV_LENGTH_13, OrderConstants.CSV_FLAG_A);
            // 21兑换点数
            String exchangeNum = addZeroOrNullForStr("", OrderConstants.CSV_LENGTH_13, OrderConstants.CSV_FLAG_A);
            // 22赚取点数
            String earnNum = addZeroOrNullForStr("", OrderConstants.CSV_LENGTH_13, OrderConstants.CSV_FLAG_A);
            // 23剩余点数
            String surplusNum = addZeroOrNullForStr("", OrderConstants.CSV_LENGTH_13, OrderConstants.CSV_FLAG_A);
            StringBuilder row = new StringBuilder();
            List<LicenseFile> licenseFiles = licenseFileMapper.selectByBatchNumber(licenseFile);
            if (licenseFiles.isEmpty()) {
                throw new CommLicenseFileException(CommLicenseFileException.CSV_DOWNLOAD_ERROR_FILE_FAIL, null);
            }
            for (LicenseFile licenseFileC : licenseFiles) {
                row.delete(0, row.length());
                // 4订单编号
                String orderNumber = addZeroOrNullForStr(licenseFileC.getOrderNumber(), OrderConstants.CSV_LENGTH_19,
                        OrderConstants.CSV_FLAG_A);
                // 8订单金额
                String orderAmount = addZeroOrNullForStr(
                        String.valueOf(
                                (int) (licenseFileC.getActrualPayAmt().doubleValue() * OrderConstants.CSV_LENGTH_100)),
                        OrderConstants.CSV_LENGTH_13, OrderConstants.CSV_FLAG_N);
                // 9信用卡号
                String creditCardNumDecrypt = iaesClientService.decrypt(licenseFileC.getCreditCardNum());
                // String creditCardNumDecrypt =
                // licenseFileC.getCreditCardNum();

                String creditCardNum = addZeroOrNullForStr(creditCardNumDecrypt, OrderConstants.CSV_LENGTH_16,
                        OrderConstants.CSV_FLAG_N);
                // 11有效年月
                String validYear = iaesClientService.decrypt(licenseFileC.getValidYear());
                String validMonth = iaesClientService.decrypt(licenseFileC.getValidMonth());
                String validYears = /* OrderConstants.CSV_VALIDYEARS_20 + */ validYear
                        + addZeroOrNullForStr(validMonth, OrderConstants.CSV_LENGTH_2, OrderConstants.CSV_FLAG_N);
                /*
                 * String validYear = licenseFileC.getValidYear(); String
                 * validMonth = licenseFileC.getValidMonth(); String validYears
                 * = validYear + addZeroOrNullForStr(validMonth,
                 * OrderConstants.CSV_LENGTH_2, OrderConstants.CSV_FLAG_N);
                 */
                // 表行 = 1 + ... + 23
                row.append(OrderConstants.CSV_ROW_RECORDTYPE);
                String agent = rShopId.substring(0, 3);
                //row.append(OrderConstants.CSV_ROW_AGENT);
                row.append(agent);
                row.append(rRetainA);
                row.append(orderNumber);
                row.append(installment);
                row.append(rRetainB);
                row.append(tradingStatus);
                row.append(orderAmount);
                row.append(creditCardNum);
                row.append(cvc);
                row.append(validYears);
                row.append(orderInformation);
                row.append(terminalCode);
                row.append(rShopId);
                row.append(authorizationCode);
                row.append(resultCode);
                row.append(errorCode);
                row.append(productCode);
                row.append(discountAmount);
                row.append(originalAmount);
                row.append(exchangeNum);
                row.append(earnNum);
                row.append(surplusNum);
                csvFileOutputStream.write(row.toString());
                csvFileOutputStream.newLine();
            }
            csvFileOutputStream.flush();
        } catch (IOException e) {
            throw new CommLicenseFileException(CommLicenseFileException.CSV_DOWNLOAD_ERROR_FILE_FAIL, null);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String downloadCsvFile(HttpServletRequest servletRequest, IRequest request, HttpServletResponse response,
            LicenseFile licenseFile) throws CommLicenseFileException {
        Locale locale = RequestContextUtils.getLocale(servletRequest);
        String lineSeparator = System.getProperty(OrderConstants.LINE_SEPARATOR_CODE);
        System.setProperty(OrderConstants.LINE_SEPARATOR_CODE, OrderConstants.LINE_SEPARATOR);
        // 订单行数
        long orderCount = salesOrderMapper.queryOrdersNumByBatchNumber(licenseFile.getBatchNumber());
        // 获取销售组织id
        Long salesOrgId;
        if (null != licenseFile.getSalesOrgId()) {
            salesOrgId = licenseFile.getSalesOrgId();
        } else {
            salesOrgId = (Long) request.getAttribute(SystemProfileConstants.SALES_ORG_ID);
        }
        // 特店编号
        List<String> hShopId = paramService.getParamValues(request, OrderConstants.CSV_CREATECSVFILE_PARAMNAME,
                SystemProfileConstants.ORG_TYPE_SALES, salesOrgId);
        if (hShopId.isEmpty()) {
            return messageSource.getMessage(CommLicenseFileException.CSV_DOWNLOAD_ERROR_NOT_HSHOPID, null, locale);
        }
        // 端末代号
        List<String> terminalCodes = paramService.getParamValues(request, OrderConstants.CSV_ROW_TERMINALID,
                SystemProfileConstants.ORG_TYPE_SALES, salesOrgId);
        if (terminalCodes.isEmpty()) {
            return messageSource.getMessage(CommLicenseFileException.CSV_DOWNLOAD_ERROR_NOT_TERMINALCODE, null, locale);
        }
        String terminalCode = addZeroOrNullForStr(terminalCodes.get(0), OrderConstants.CSV_LENGTH_8,
                OrderConstants.CSV_FLAG_N);
        // 特店代号
        List<String> rShopIds = paramService.getParamValues(request, OrderConstants.CSV_ROW_SHOPID,
                SystemProfileConstants.ORG_TYPE_SALES, salesOrgId);
        if (rShopIds.isEmpty()) {
            return messageSource.getMessage(CommLicenseFileException.CSV_DOWNLOAD_ERROR_NOT_RSHOPID, null, locale);
        }
        String rShopId = addZeroOrNullForStr(rShopIds.get(0), OrderConstants.CSV_LENGTH_15, OrderConstants.CSV_FLAG_A);
        // 批次号
        String batchNumber = licenseFile.getBatchNumber();
        if (batchNumber.length() < 6) {
            batchNumber = addZeroOrNullForStr(batchNumber, OrderConstants.CSV_LENGTH_8, OrderConstants.CSV_FLAG_N);
        }
        StringBuffer sb = new StringBuffer(batchNumber);
        // csv文件名 = 特店编号 + yyMMdd + . + 流水号
        String serial = sb.insert(6, OrderConstants.CSV_HYPHEN).toString();
        StringBuilder csvFileName = new StringBuilder();
        csvFileName.append(hShopId.get(0));
        csvFileName.append(serial);
        //csvFileName.append(OrderConstants.CSV_TYPE);
        response.setContentType("application/csv;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=" + csvFileName.toString());
        try (OutputStream out = response.getOutputStream()) {
            self().createCSVFile(request, out, licenseFile, salesOrgId, orderCount, terminalCode, rShopId);
            // 以\r\n形式结束行
            System.setProperty(OrderConstants.LINE_SEPARATOR_CODE, lineSeparator);
            return null;
        } catch (IOException e) {
            // 以\r\n形式结束行
            System.setProperty(OrderConstants.LINE_SEPARATOR_CODE, lineSeparator);
            return null;
            // throw new
            // CommLicenseFileException(CommLicenseFileException.CSV_DOWNLOAD_ERROR_FILE_FAIL,
            // null);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object uploadCsvFile(HttpServletRequest httpServletRequest, IRequest request, FileInfo fileInfo) {
        String str = null;
        int successNumber = 0;
        int failNumber = 0;
        String msg = null;
        Locale locale = RequestContextUtils.getLocale(httpServletRequest);
        String lineSeparator = System.getProperty(OrderConstants.LINE_SEPARATOR_CODE);
        System.setProperty(OrderConstants.LINE_SEPARATOR_CODE, OrderConstants.LINE_SEPARATOR);
        try (BufferedReader reader = new BufferedReader(new FileReader(fileInfo.getFile()))) {
            // 跳过第一行
            reader.readLine();
            while ((str = reader.readLine()) != null) {
            	if(!str.trim().equals("")){
                    if (OrderConstants.CSV_FILE_TRADINGSTATUS.equals(
                            str.substring(OrderConstants.CSV_SUBSTRING_41, OrderConstants.CSV_SUBSTRING_53).trim())) {
                        SalesOrder salesOrder = salesOrderMapper.queryByOrderNumber(
                                str.substring(OrderConstants.CSV_SUBSTRING_18, OrderConstants.CSV_SUBSTRING_37).trim());
                        List<OrderPayment> orderPayments = new ArrayList<OrderPayment>();
                        OrderPayment orderPayment = new OrderPayment();
                        orderPayment.setOrderHeaderId(salesOrder.getHeaderId());
                        orderPayment.setSalesOrgId(salesOrder.getSalesOrgId());
                        orderPayment.setPaymentMethod(OrderConstants.PAYMENT_METHOD_CREDI);
                        // 获取原来自动订货单
                        AutoshipOrder singleAutoshipOrder = autoshipOrderMapper
                                .selectByAutoshipNumber(salesOrder.getSourceKey());
                        MemCard card = memCardMapper.selectByPrimaryKey(singleAutoshipOrder.getCreditCardId());
                        orderPayment.setBankCode(card.getBankCode());
                        orderPayment.setCreditCardType(card.getCardSubType());
                        orderPayment.setTailNumber(
                                card.getMaskedCardNumber().substring(card.getMaskedCardNumber().length() - 4));
                        orderPayment.setTransactionNumber(str.substring(OrderConstants.CSV_SUBSTRING_66, OrderConstants.CSV_SUBSTRING_82).trim());
                        double paymentAmount = Double.valueOf(
                                str.substring(OrderConstants.CSV_SUBSTRING_53, OrderConstants.CSV_SUBSTRING_66).trim())
                                / OrderConstants.CSV_LENGTH_100;
                        java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
                        orderPayment.setPaymentAmount(new BigDecimal(df.format(paymentAmount)));
                        orderPayments.add(orderPayment);
                        commOrderPaymentService.createOrderPayment(request, orderPayments, salesOrder.getHeaderId());
                        salesOrderMapper.updateOrderStatusByOrderNumber(
                                str.substring(OrderConstants.CSV_SUBSTRING_18, OrderConstants.CSV_SUBSTRING_37).trim());
                        successNumber++;
                    } else {
                        failNumber++;
                    }
            	}
            }
        } catch (IllegalStateException | IOException | CommOrderException | CommDeliveryException | CommMemberException
                | CommSystemProfileException e) {
        	TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            msg = messageSource.getMessage(CommLicenseFileException.CSV_UPLOAD_ERROR_FILE_ERROR, null, locale);
            // 以\r\n形式结束行
            // 异常日志
            ByteArrayOutputStream baos = new ByteArrayOutputStream();  
            e.printStackTrace(new PrintStream(baos));  
            String exception = baos.toString();  
            if (logger.isDebugEnabled()) {
                logger.debug(exception);
            }
            System.setProperty(OrderConstants.LINE_SEPARATOR_CODE, lineSeparator);
            return "<script>window.parent.uploadMessage('" + msg + "');</script>";
        }
        msg = messageSource.getMessage(OrderConstants.CSV_UPLOAD_ERROR_FILE_EXISTS,
                new Integer[] { successNumber, failNumber }, locale);
        // 以\r\n形式结束行
        System.setProperty(OrderConstants.LINE_SEPARATOR_CODE, lineSeparator);
        return "<script>window.parent.uploadMessage('" + msg + "');</script>";
    }
}
