package com.lkkhpg.dsis.integration.payment.service.impl;



import java.net.URL;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;


public class HttpRequest {
	/**
	 *  异步通知的接口
	 * @param param 请求的参数
	 * @param url 请求地址
	 * @return String 请求返回相应的信息
	 */
	public static String sendPost(String url, String param)throws IOException {
		OutputStreamWriter out = null;
		BufferedReader in = null;
		String result ="";
		System.out.println(param+":::::"+url);
		try {
			URL realUrl = new URL(url);
			HttpURLConnection conn = null;

			conn = (HttpURLConnection) realUrl.openConnection();

			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestMethod("POST");    // POST方法

			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			conn.setRequestProperty("Content-Type", "application/json");

			conn.connect();

			// 获取URLConnection对象对应的输出流
			out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
			// 发送请求参数
			out.write(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream(),"UTF-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！"+e);
			//e.printStackTrace();
			throw e;
		}
		//使用finally块来关闭输出流、输入流
		finally{
			try{
				if(out!=null){
					out.close();
				}
				if(in!=null){
					in.close();
				}
			}
			catch(IOException ex){
				//ex.printStackTrace();
				throw ex;
			}
		}
		return result;
	}
}