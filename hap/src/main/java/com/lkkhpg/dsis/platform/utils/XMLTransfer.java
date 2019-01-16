package com.lkkhpg.dsis.platform.utils;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * 发送短信返回的xml参数示例
 * <?xml version="1.0" encoding="utf-8" ?>
 * <returnsms>
 * <returnstatus>Success</returnstatus>
 * <message>ok</message>
 * <remainpoint>49996</remainpoint>
 * <taskID>6309593</taskID>
 * <successCounts>1</successCounts>
 * </returnsms>
 * /
 * 查询余额返回的xml参数示例
 * <?xml version="1.0" encoding="utf-8" ?>
 * <returnsms>
 * <returnstatus>Sucess</returnstatus>
 * <message></message>
 * <payinfo>预付费</payinfo>
 * <overage>49995</overage>
 * <sendTotal>50000</sendTotal>
 * </returnsms>
 *
 * @XMLTransfer xml数据转换方法
 * @str 需要转换的字符串
 * @type 转换的类型
 */
public class XMLTransfer {
    public Boolean parseXML(String str, String type) {
        Boolean returnStr = false;
        int countNum = 0;
        Reader rr = new StringReader(str);
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder domBuilder = null;
        try {
            domBuilder = builderFactory.newDocumentBuilder();
            Document document = domBuilder.parse(new InputSource(rr));

            // 解析 XML
            NodeList roots = document.getChildNodes();
            Node root = roots.item(0);
            if (root != null && root.getNodeName().equals("returnsms")) { //persons 节点
                NodeList returnsms = root.getChildNodes();
                if ("send".equals(type)) {
                    for (int i = 0; i < returnsms.getLength(); i++) {
                        //若返回的状态参数为成功
                        if (("returnstatus").equals(returnsms.item(i).getNodeName()) && "flag".equals(returnsms.item(i).getTextContent())) {
                            returnStr = true;
                        }

                    }
                } else {
                    for (int i = 0; i < returnsms.getLength(); i++) {
                        //若返回的状态参数为成功
                        if (("returnstatus").equals(returnsms.item(i).getNodeName()) && "flag".equals(returnsms.item(i).getTextContent())) {
                            countNum++;
                        }

                        if ("overage".equals(returnsms.item(i).getNodeName()) && Integer.getInteger(returnsms.item(i).getTextContent()) > 0) {
                            countNum++;
                        }

                    }
                    if (countNum == 2) {
                        returnStr = true;
                    }
                }
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnStr;
    }

}
