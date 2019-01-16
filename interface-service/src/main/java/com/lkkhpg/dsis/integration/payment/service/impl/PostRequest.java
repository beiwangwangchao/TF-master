package com.lkkhpg.dsis.integration.payment.service.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class PostRequest {

    private  static Logger logger = LoggerFactory.getLogger(PostRequest.class);
    /**
     *  异步通知的接口
     * @param params 请求的参数
     * @param post_url 请求地址
     * @return String 请求返回相应的信息
     */
    public static String readContentFromPost(String params,String post_url) throws IOException {
        DataOutputStream out=null;
        HttpURLConnection connection=null;
        BufferedReader reader=null;
        String line="";
        String result="";

        logger.info(post_url);

        logger.info(params);
/**
 * 程序异常出错，打开连接请求超时
 */
        try{
            URL  postUrl = new URL(post_url);
            connection = (HttpURLConnection) postUrl.openConnection();
        }catch (IOException e1){
            if (connection != null) {
                connection.disconnect();
            }
            throw e1;
        }


/**
 * 程序异常出错，post请求超时
 */
        try{
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
        }catch (IOException e2){
            if (connection != null) {
                connection.disconnect();
            }
            throw e2;
        }


/**
 * 程序异常出错，连接时请求超时
 */
        try {
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.connect();
        }catch (IOException e3){
            if (connection != null) {
                connection.disconnect();
            }
            throw e3;
        }


/**
 * 程序异常出错，读取数据时请求超时
 */
        try{
            out = new DataOutputStream(connection.getOutputStream());
            out.writeBytes(params);
            out.flush();
            out.close();
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
            while ((line = reader.readLine()) != null) {
                line = new String(line.getBytes());
                result+=line;
            }

        }catch (IOException e4){

            if (connection != null) {
                connection.disconnect();
            }
            if(out !=null){
                out.close();
            }
            if (reader != null) {
                reader.close();
            }
            throw e4;
        }

        return  result;
    }

}