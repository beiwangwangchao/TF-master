/**
 * #{copyright}#
 */
package com.lkkhpg.dsis.platform.report.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author runbai.chen
 */
public class XML2CSV extends DefaultHandler {
    OutputStreamWriter out = null;
    public ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    boolean isWriteFlag = false;
    public static String CSV_CHARSET_NANME = "BIG5";
    public static String CSV_LINE_END_FLAG = "\r\n";

    @Override
    public void startDocument() throws SAXException {
        try {
            out = new OutputStreamWriter(outStream, CSV_CHARSET_NANME);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void endDocument() throws SAXException {
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equalsIgnoreCase("data")) {
            isWriteFlag = true;
        } else {
            isWriteFlag = false;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase("row")) {
            try {
                out.write(CSV_LINE_END_FLAG);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (qName.equalsIgnoreCase("cell")) {
            try {
                out.write(",");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void characters(char ch[], int start, int length) throws SAXException {
        String s = new String(ch, start, length);
        if (!s.trim().isEmpty() && (isWriteFlag == true)) {
            try {
                out.write(s);
                isWriteFlag = false;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) throws Exception, SAXException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        Date before = new Date();
        byte[] source = null;
        XML2CSV xml2csv = new XML2CSV();
        // parser.parse(new ByteArrayInputStream(source), xml2csv);
        parser.parse(new File("C:\\ProgramData\\e8440f38-8026-45a9-892e-43158b232518.xls"), xml2csv);
        ByteArrayOutputStream outStream = xml2csv.outStream;

        String outString = outStream.toString(CSV_CHARSET_NANME);
        String outputString = outString.substring(0,
                outString.substring(0, outString.length() - CSV_LINE_END_FLAG.length()).lastIndexOf(CSV_LINE_END_FLAG));

//        System.out.println(new String(outStream.toByteArray()).length() - CSV_LINE_END_FLAG.length());

        FileOutputStream fout = null;
        try {

            File file = new File("C:\\ProgramData\\e8440f38-8026-45a9-892e-43158b232518.CSV");
            fout = new FileOutputStream(file);
            fout.write(outputString.getBytes(CSV_CHARSET_NANME));

        } finally {
            if (fout != null) {
                fout.close();
            }
        }
        Date after = new Date();
//        System.out.println("it takes " + (after.getTime() - before.getTime()) + "ms");
    }
}
