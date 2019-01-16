/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.member.util;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.fileupload.FileItem;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataFormatter;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.lkkhpg.dsis.common.constant.MemberConstants;
import com.lkkhpg.dsis.common.member.dto.MemberList;

/**
 * 读取导入会员列表Excel文件.
 *
 * @author mclin
 */
public class ReadExcelUtil {

    private static final String code = "memberCode";

    /**
     * read the Excel file.
     *
     * @param fileItem FileItem
     * @param ext      文件后缀
     * @return list 会员列表list
     * @throws IOException 文件写入写出异常
     */
    public List<MemberList> readExcel(FileItem fileItem, String ext) throws IOException {
        if (fileItem == null) {
            return null;
        } else {
            if (MemberConstants.MEMLIST_CONTENT_EXT[0].equals(ext)) {
                return readXls(fileItem);
            } else if (MemberConstants.MEMLIST_CONTENT_EXT[1].equals(ext)) {
                return readXlsx(fileItem);
            }
        }
        return null;
    }

    /**
     * Read the Excel 2010.
     *
     * @param fileItem FileItem
     * @return list 会员列表Dto
     * @throws IOException 读取文件
     */
    private List<MemberList> readXlsx(FileItem fileItem) throws IOException {
        try (InputStream is = fileItem.getInputStream(); XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is)) {
            List<String> list = new ArrayList<String>();
            // Read the Sheet
            for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
                XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
                if (xssfSheet == null) {
                    continue;
                }
                // Read the Row
                for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
                    XSSFRow xssfRow = xssfSheet.getRow(rowNum);
                    if (xssfRow != null) {
                        XSSFCell cell = xssfRow.getCell(0);
                        String id = getValue(cell);
                        if (id != null) {
                            list.add(id);
                        }
                    }
                }
            }
            return removeDuplicate(list);
        }
    }

    /**
     * Read the Excel 2003-2007.
     *
     * @param fileItem FileItem
     * @return list 会员列表Dto
     * @throws IOException 读取文件
     */
    private List<MemberList> readXls(FileItem fileItem) throws IOException {
        try (InputStream is = fileItem.getInputStream(); HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is)) {
            List<String> list = new ArrayList<String>();
            // Read the Sheet
            for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
                HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
                if (hssfSheet == null) {
                    continue;
                }
                // Read the Row
                for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                    HSSFRow hssfRow = hssfSheet.getRow(rowNum);
                    if (hssfRow != null) {
                        HSSFCell cell = hssfRow.getCell(0);
                        String id = getValue(cell);
                        if (id != null) {
                            list.add(id);
                        }
                    }
                }
            }
            return removeDuplicate(list);
        }
    }

    /**
     * 获取单元格内容Excel 2010.
     *
     * @param xssfRow Excel单元格对象
     */
    private String getValue(XSSFCell xssfRow) {
        int cellType = xssfRow.getCellType();
        String result = null;
        DecimalFormat df = new DecimalFormat("0");
        switch (cellType) {
            case XSSFCell.CELL_TYPE_BLANK:
                break;
            case XSSFCell.CELL_TYPE_BOOLEAN:
                result = String.valueOf(xssfRow.getBooleanCellValue());
                break;
            case XSSFCell.CELL_TYPE_FORMULA:
                result = xssfRow.getCellFormula();
                break;
            case XSSFCell.CELL_TYPE_STRING:
                result = xssfRow.getStringCellValue();
                // result = df.format(stringCellValue);
                break;
            case XSSFCell.CELL_TYPE_NUMERIC:
                result = df.format(xssfRow.getNumericCellValue());
                // 防止整型数据有小数点
                result = result.replaceAll("\\.0$", "");
                result = result.replaceAll("\\.0+$", "");
                break;
            default:
                break;
        }
        if (result != null) {
            result = result.trim();
        }
        return result;
    }

    /**
     * 获取单元格内容Excel 2003-2007.
     *
     * @param hssfCell Excel单元格对象
     */
    private String getValue(HSSFCell hssfCell) {
        int cellType = hssfCell.getCellType();
        String result = null;
        HSSFDataFormatter format = new HSSFDataFormatter();
        switch (cellType) {
            case HSSFCell.CELL_TYPE_BLANK:
                break;
            case HSSFCell.CELL_TYPE_BOOLEAN:
                result = String.valueOf(hssfCell.getBooleanCellValue());
                break;
            case HSSFCell.CELL_TYPE_FORMULA:
                result = hssfCell.getCellFormula();
                break;
            case HSSFCell.CELL_TYPE_STRING:
                result = hssfCell.getStringCellValue();
                break;
            case HSSFCell.CELL_TYPE_NUMERIC:
                result = format.formatCellValue(hssfCell);
                break;
            default:
                break;
        }
        if (result != null) {
            result = result.trim();
        }
        return result;
    }

    /**
     * 取出List中重复的对象.
     *
     * @param list 会员Dto的List
     * @return List 会员Dto的List
     */
    private List<MemberList> removeDuplicate(List<String> list) {
        Set<String> set = new HashSet<>();
        List<MemberList> newList = new ArrayList<>();
        MemberList member;
        for (String element : list) {
            if (set.add(element)) {
                member = new MemberList();
                member.setMemberCode(element);
                newList.add(member);
            }
        }
        return newList;
    }

    /**
     * read the Excel file 只读取sheet0.
     *
     * @param fileItem FileItem
     * @param ext      文件后缀
     * @return list 会员列表list
     * @throws IOException 文件写入写出异常
     */
    public List<MemberList> readExcelBySheet(FileItem fileItem, String ext) throws IOException {
        if (fileItem == null) {
            return null;
        } else {
            if (MemberConstants.MEMLIST_CONTENT_EXT[0].equals(ext)) {
                return readXlsBySheet(fileItem);
            } else if (MemberConstants.MEMLIST_CONTENT_EXT[1].equals(ext)) {
                return readXlsxBySheet(fileItem);
            }
        }
        return null;
    }

    /**
     * Read the Excel 2010 只读取sheet0.
     *
     * @param fileItem FileItem
     * @return list 会员列表Dto
     * @throws IOException 读取文件
     */
    private List<MemberList> readXlsxBySheet(FileItem fileItem) throws IOException {
        try (InputStream is = fileItem.getInputStream(); XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is)) {
            List<String> list = new ArrayList<String>();
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
            //A1单元格的值必须=memberCode
            String memberCode = getValue(xssfSheet.getRow(0).getCell(0));
            if (code.equals(memberCode)) {
                for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
                    XSSFRow xssfRow = xssfSheet.getRow(rowNum);
                    if (xssfRow != null) {
                        XSSFCell cell = xssfRow.getCell(0);
                        String id = getValue(cell);
                        if (id != null) {
                            list.add(id);
                        }
                    }
                }
            } else {
                String reasonCode = MemberConstants.MSG_ERROR_VALUE_OF_THE_CELL_MUST_BE_MEMBERCODE;
                list.add(reasonCode);
            }
            return removeDuplicate(list);
        }
    }

    /**
     * Read the Excel 2003-2007 只读取sheet0.
     *
     * @param fileItem FileItem
     * @return list 会员列表Dto
     * @throws IOException 读取文件
     */
    private List<MemberList> readXlsBySheet(FileItem fileItem) throws IOException {
        try (InputStream is = fileItem.getInputStream(); HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is)) {
            List<String> list = new ArrayList<String>();
            HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
            //A1单元格的值必须=memberCode
            String memberCode = getValue(hssfSheet.getRow(0).getCell(0));
            if (code.equals(memberCode)) {
                for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                    HSSFRow hssfRow = hssfSheet.getRow(rowNum);
                    if (hssfRow != null) {
                        HSSFCell cell = hssfRow.getCell(0);
                        String id = getValue(cell);
                        if (id != null) {
                            list.add(id);
                        }
                    }
                }
            } else {
                String reasonCode = MemberConstants.MSG_ERROR_VALUE_OF_THE_CELL_MUST_BE_MEMBERCODE;
                list.add(reasonCode);
            }

            return removeDuplicate(list);
        }
    }
}
