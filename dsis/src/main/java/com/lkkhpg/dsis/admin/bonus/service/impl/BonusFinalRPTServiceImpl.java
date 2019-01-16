/*
 *
 */
package com.lkkhpg.dsis.admin.bonus.service.impl;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lkkhpg.dsis.admin.bonus.service.IBonusFinalRPTService;
import com.lkkhpg.dsis.common.bonus.dto.BonusFinal;
import com.lkkhpg.dsis.common.bonus.mapper.BonusFinalMapper;
import com.lkkhpg.dsis.common.constant.OrderConstants;
import com.lkkhpg.dsis.common.exception.CommBonusException;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.system.DocSequence;
import com.lkkhpg.dsis.platform.service.IDocSequenceService;

/**
 * RPT-00172报表ServiceImpl.
 * 
 * @author hanrui.huang
 *
 */
@Service
public class BonusFinalRPTServiceImpl implements IBonusFinalRPTService {

    private Logger logger = LoggerFactory.getLogger(BonusFinalRPTServiceImpl.class);

    @Autowired
    private BonusFinalMapper bonusFinalMapper;

    @Autowired
    private IDocSequenceService docSequenceService;

    private static final String DPR = "DPR";

    private static final String RPT_00172 = "RPT_00172";

    private static final int UNIFORM_NUMBER = 24239897;

    private static final String TXT = ".txt";

    private static final Long COST_NUMBER_STEP = 1L;

    private static final String COM = "laura.yen@infinitus-int.com";

    private static final String NAME = "無限極國際有限公司";

    private static final String PHONE = "0227038222#310";

    private static final String CONTACTS = "嚴世嘉";

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

    @Override
    public String downloadFile(IRequest request) {
        StringBuilder txtFileName = new StringBuilder();
        txtFileName.append(DPR);
        txtFileName.append(UNIFORM_NUMBER);
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        DocSequence docSequence = new DocSequence();
        docSequence.setDocType(RPT_00172);
        String number = docSequenceService.getSequence(request, docSequence, changeTWYear(format.format(date)), 3,
                COST_NUMBER_STEP);
        txtFileName.append(number);
        txtFileName.append(TXT);
        return txtFileName.toString();
    }

    /**
     * 将国际年月日转化为台湾民国年.
     * 
     * @param dateStr
     *            yyyyMMdd/ yyyyMM.
     */
    private String changeTWYear(String dateStr) {
        String yearStr = dateStr.substring(0, 4);
        Integer year = Integer.parseInt(yearStr) - 1911;
        String result = year.toString() + dateStr.substring(4);
        return result;
    }

    /**
     * 处理big5中文字节问题.
     * 
     * @param str
     *            需要转化的字符串.
     * @param length
     *            转换后的长度.
     * @return 返回转换后的字符串.
     * @throws UnsupportedEncodingException
     */
    private String changeChinese(String str, int length) throws UnsupportedEncodingException {
        byte[] strByte = str.getBytes("BIG5");
        int strLength = strByte.length;
        StringBuffer strTemp = new StringBuffer(str);
        if (length < strLength) {
            byte[] tempByte = new byte[length];
            for (int i = 0; i < length; i++) {
                tempByte[i] = strByte[i];
            }
            return new String(tempByte, "BIG5");
        } else {
            for (int j = strLength; j < length; j++) {
                strTemp.append(" ");
            }
            return new String(strTemp);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void createTXTFile(IRequest request, HttpServletResponse response, String dateFrom, String dateTo,
            String memberCodeForm, String memberCodeTo) throws CommBonusException {
        try (PrintWriter txtFileOutputStream = response.getWriter()) {
            // 所得給付起始年月
            String dateFromStr = addZeroOrNullForStr(changeTWYear(dateFrom), 5, OrderConstants.CSV_FLAG_A);
            // 所得給付結束年月
            String dateToStr = addZeroOrNullForStr(changeTWYear(dateTo), 5, OrderConstants.CSV_FLAG_A);
            // 無總機構者(單一機構者)，則為空白。
            String str = addZeroOrNullForStr("", 8, OrderConstants.CSV_FLAG_A);
            // 扣費單位電子郵件信箱帳號
            String comStr = addZeroOrNullForStr(COM, 30, OrderConstants.CSV_FLAG_A);
            // 扣費義務人名稱
            String nameStr = changeChinese(NAME, 50);
            // 保留欄位
            String rRetainA = addZeroOrNullForStr("", 84, OrderConstants.CSV_FLAG_A);
            StringBuilder head = new StringBuilder();
            // 資料識别碼
            head.append(1);
            // 扣費單位統一編號
            head.append(UNIFORM_NUMBER);
            // 所得(收入)類別
            head.append(65);
            // 所得給付起始年月
            head.append(dateFromStr);
            // 所得給付結束年月
            head.append(dateToStr);
            Date date = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
            // 檔案製作日期
            head.append(changeTWYear(format.format(date)));
            // 總機構統一編號
            head.append(str);
            // 扣費單位電子郵件信箱帳號
            head.append(comStr);
            // 扣費義務人名稱
            head.append(nameStr);
            // 保留欄位
            head.append(rRetainA);
            head.append("\r\n");
            txtFileOutputStream.print(head.toString());
            StringBuilder row = new StringBuilder();
            // 报表明细
            BonusFinal bonusFinal = new BonusFinal();
            if (StringUtils.isNotEmpty(dateFrom)) {
                bonusFinal.setDateFrom(dateFrom);
            }
            if (StringUtils.isNotEmpty(dateTo)) {
                bonusFinal.setDateTo(dateTo);
            }
            if (StringUtils.isNotEmpty(memberCodeForm)) {
                bonusFinal.setMemberCodeForm(memberCodeForm);
            }
            if (StringUtils.isNotEmpty(memberCodeTo)) {
                bonusFinal.setMemberCodeTo(memberCodeTo);
            }
            List<BonusFinal> lines = bonusFinalMapper.queryDetails(bonusFinal);
            // 行序号
            int i = 0;
            // 所得(收入)给付总额
            BigDecimal sumremitNetAmt = new BigDecimal(0);
            // 扣繳補充保險費總額
            BigDecimal sumclearTaxAmt03 = new BigDecimal(0);
            // 申报总笔数
            int score = 0;
            for (BonusFinal line : lines) {
                if (line.getPreTaxAmt() != null) {
                    sumremitNetAmt = sumremitNetAmt.add(line.getPreTaxAmt());
                }
                if (line.getClearTaxAmt03() != null) {
                    sumclearTaxAmt03 = sumclearTaxAmt03.add(line.getClearTaxAmt03());
                }
                if (line.getPreTaxAmt() != null || line.getClearTaxAmt03() != null) {
                    score = score + 1;
                }
                row.delete(0, row.length());
                i = i + 1;
                // 流水序號
                String Serial = addZeroOrNullForStr(String.valueOf(i), 9, OrderConstants.CSV_FLAG_N);
                // 数据处理方式
                String attribute = "";
                if (line.getAttribute1() != null) {
                    attribute = line.getAttribute1();
                }
                String dataMode = addZeroOrNullForStr(attribute, 1, OrderConstants.CSV_FLAG_A);
                // 所得人身分證號
                String cdid = "";
                if (line.getIdNumber() != null) {
                    cdid = line.getIdNumber();
                }
                String idNumber = addZeroOrNullForStr(cdid, 10, OrderConstants.CSV_FLAG_A);
                // 申報編號(取当前月度奖金行上的的会员帐号+此月度奖金的对应的最终奖金数据的汇款日期)
                String remitDate = "";
                String strDate = "";
                if (line.getRemitDate() != null) {
                    // System.out.println(line.getRemitDate());
                    remitDate = changeTWYear(format.format(line.getRemitDate()));
                    // 所得給付日期 日期格式yyymmdd
                    // System.out.println(remitDate);
                    // if (remitDate.length() == 8) {
                    // strDate = remitDate.substring(remitDate.length()-7);
                    // }
                }
                String memberCode = "";
                if (line.getMemberCode() != null) {
                    memberCode = line.getMemberCode();
                }
                String declareNo = addZeroOrNullForStr(memberCode + format.format(line.getRemitDate()), 30,
                        OrderConstants.CSV_FLAG_A);
                String remitDate2 = addZeroOrNullForStr(remitDate, 7, OrderConstants.CSV_FLAG_A);
                // 所得(收入)給付金額
                String remitNetAmt = "";
                if (line.getPreTaxAmt() != null) {
                    remitNetAmt = String.valueOf(line.getPreTaxAmt());
                }
                String remitNetAmt2 = addZeroOrNullForStr(remitNetAmt, 14, OrderConstants.CSV_FLAG_N);
                // 扣繳補充保險費金額
                String clearTaxAmt = "";
                if (line.getClearTaxAmt03() != null) {
                    clearTaxAmt = String.valueOf(line.getClearTaxAmt03());
                }
                String amt = addZeroOrNullForStr(clearTaxAmt, 10, OrderConstants.CSV_FLAG_N);
                // 共用欄位區 默认空
                String SharedColumn = addZeroOrNullForStr("", 40, OrderConstants.CSV_FLAG_A);
                // 信託註記
                String trustNote = addZeroOrNullForStr("", 1, OrderConstants.CSV_FLAG_A);
                // 所得人姓名
                String memberName = "";
                if (line.getMemberName() != null) {
                    memberName = line.getMemberName();
                }
                String name = changeChinese(memberName, 50);
                // 資料註記
                String dataAnnotation = addZeroOrNullForStr("", 1, OrderConstants.CSV_FLAG_A);
                // 保留栏位
                String retain = addZeroOrNullForStr("", 16, OrderConstants.CSV_FLAG_A);
                // 資料識别碼,默认值是2
                row.append(2);
                // 扣費單位統一編號 默认值24239897
                row.append(UNIFORM_NUMBER);
                // 所得(收入)類別
                row.append(65);
                // 流水序號
                row.append(Serial);
                // 資料處理方式
                row.append(dataMode);
                // 所得給付日期 日期格式yyymmdd
                row.append(remitDate2);
                // 所得人身分證號
                row.append(idNumber);
                // 申報編號
                row.append(declareNo);
                // 所得(收入)給付金額
                row.append(remitNetAmt2);
                // 扣繳補充保險費金額
                row.append(amt);
                // 共用欄位區
                row.append(SharedColumn);
                // 信託註記
                row.append(trustNote);
                // 所得人姓名
                row.append(name);
                // 資料註記
                row.append(dataAnnotation);
                // 保留栏位
                row.append(retain);
                row.append("\r\n");
                txtFileOutputStream.print(row.toString());
            }
            // 联系电话
            String telephone = addZeroOrNullForStr(PHONE, 15, OrderConstants.CSV_FLAG_A);
            // 联系人
            String contacts = changeChinese(CONTACTS, 50);
            // 保留栏位
            String retain2 = addZeroOrNullForStr("", 79, OrderConstants.CSV_FLAG_A);
            // 申报总笔数
            String scoreStr = addZeroOrNullForStr(String.valueOf(score), 9, OrderConstants.CSV_FLAG_N);
            // 扣繳補充保險費總額
            String sumremitNetAmt2 = addZeroOrNullForStr(String.valueOf(sumremitNetAmt), 20, OrderConstants.CSV_FLAG_N);
            // 扣繳補充保險費總額
            String sumclearTaxAmt = addZeroOrNullForStr(String.valueOf(sumclearTaxAmt03), 16,
                    OrderConstants.CSV_FLAG_N);
            StringBuilder footer = new StringBuilder();
            // 資料識别碼
            footer.append(3);
            // 扣費單位統一編號
            footer.append(UNIFORM_NUMBER);
            // 所得(收入)類別
            footer.append(65);
            // 申报总笔数
            footer.append(scoreStr);
            // 所得(收入)给付总额
            footer.append(sumremitNetAmt2);
            // 扣繳補充保險費總額
            footer.append(sumclearTaxAmt);
            // 聯絡電話
            footer.append(telephone);
            // 聯絡人姓名
            footer.append(contacts);
            // 保留欄位
            footer.append(retain2);
            footer.append("\r\n");
            txtFileOutputStream.print(footer.toString());
            txtFileOutputStream.flush();
            txtFileOutputStream.close();
        } catch (IOException e) {
            throw new CommBonusException(CommBonusException.MSG_ERROR_REPORT_FILE_GENERATION_FAIL, null);
        } /*
           * finally { try { out.close(); } catch (IOException e) { if
           * (logger.isErrorEnabled()) { logger.error(e.getMessage(), e); } } }
           */
    }
}
