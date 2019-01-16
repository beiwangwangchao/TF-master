package com.lkkhpg.dsis.admin.export.utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.IOException;

public class PdfExportHeaderFooterUtils extends PdfPageEventHelper {

	// 页眉
	public String header = "";
  
    //文档字体大小
    public int fontSize = 10;
  
    //文档页面大小，最好前面传入，否则默认为A4纸张
    public Rectangle pageSize = PageSize.A4;
  
    // 模板
    public PdfTemplate total;
  
    // 基础字体对象
    public BaseFont baseFont = null;
  
    // 利用基础字体生成的字体对象，一般用于生成中文文字
    public Font fontDetail = null;
  
    /**
     * 无参构造方法.
     */
    public PdfExportHeaderFooterUtils() {
    }
  
    /**
     *
     * 构造方法.
     *
     * @param header
     *            页眉字符串
     * @param presentFontSize
     *            数据体字体大小
     * @param pageSize
     *            页面文档大小，A4，A5，A6横转翻转等Rectangle对象
     */
    public PdfExportHeaderFooterUtils(String header, int fontSize, Rectangle pageSize) {
        this.header = header;
        this.fontSize = fontSize;
        this.pageSize = pageSize;
    }
  
	/**
     * 文档打开时创建模板
     */
    public void onOpenDocument(PdfWriter writer, Document document) {
        total = writer.getDirectContent().createTemplate(50, 50);// 共 页 的矩形的长宽高
    }
  
    /**
     * 关闭每页的时候，写入页眉，写入'第几页共'这几个字。
     */
    public void onEndPage(PdfWriter writer, Document document) {
        try {
            if (baseFont == null) {
            	baseFont = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            }
            if (fontDetail == null) {
                fontDetail = new Font(baseFont, fontSize, Font.NORMAL);// 数据体字体
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
  
        // 写入页眉
        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT, new Phrase(header, fontDetail), document.left(), document.top() + 20, 0);
         
        // 写入前半部分 的 当前页数
        int pageS = writer.getPageNumber();
        String foot1 = pageS + " / ";
        Phrase footer = new Phrase(foot1, fontDetail);
  
        // 计算前半部分foot1的长度
        float len = baseFont.getWidthPoint(foot1, fontSize);
  
        // 获取当前的PdfContentByte
        PdfContentByte cb = writer.getDirectContent();
  
        // 写入foot1,x轴就是(右margin+左margin + right() -left()- len)/2.0F 再给偏移20F ,y轴就是底边界-15,注意Y轴是从下往上累加的.
        ColumnText.showTextAligned(cb, Element.ALIGN_CENTER, footer, (document.rightMargin() + document.right() + document.leftMargin() - document.left() - len) / 2.0F, document.bottom() - 15, 0);
  
        // 写入foot2,计算模板的和Y轴,X=(右边界-左边界 - 前半部分的len值)/2.0F + len, y 轴和之前的保持一致,底边界-15
        cb.addTemplate(total, (document.rightMargin() + document.right() + document.leftMargin() - document.left()) / 2.0F, document.bottom() - 15); // 调节模版显示的位置
    }
  
    /**
     * 关闭文档时，替换模板，完成整个页眉页脚组件
     */
    public void onCloseDocument(PdfWriter writer, Document document) {
        // 关闭文档的时候，将模板替换成实际的“总页数”.
        total.beginText();
        total.setFontAndSize(baseFont, fontSize);// 生成的模版的字体、颜色
        String foot2 = " " + (writer.getPageNumber());
        total.showText(foot2);// 模版显示的内容
        total.endText();
        total.closePath();
    }
    
    public void setHeader(String header) {
        this.header = header;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }
  
    public void setPageSize(Rectangle pageSize) {
		this.pageSize = pageSize;
	}

	public void setTotal(PdfTemplate total) {
		this.total = total;
	}

	public void setBaseFont(BaseFont baseFont) {
		this.baseFont = baseFont;
	}

	public void setFontDetail(Font fontDetail) {
		this.fontDetail = fontDetail;
	}
}
