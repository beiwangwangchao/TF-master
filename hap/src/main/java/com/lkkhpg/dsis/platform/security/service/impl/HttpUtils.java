/*
 *
 */
package com.lkkhpg.dsis.platform.security.service.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * .
 * 
 * @version 1.0
 * @author shiliyan
 *
 */
public final class HttpUtils {

    public static final int I_5000 = 5000;
    private static final int SIZE = 1024 * 1024;

    private HttpUtils() {
    }

    /**
     * GET METHOD.
     * 
     * @param strUrl
     *            String
     * @param text
     *            String
     * @throws IOException
     * @return String
     */
    public static List<String> urlGet(String strUrl, String text) throws IOException {
        String strtTotalURL = strUrl + text;
        URL url = new URL(strtTotalURL);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        // con.setUseCaches(false);
        // con.setFollowRedirects(true);
        con.setConnectTimeout(I_5000);
        con.setReadTimeout(I_5000);
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"), SIZE);
        List<String> result = new ArrayList<String>();
        while (true) {
            String line = in.readLine();
            if (line == null) {
                break;
            } else {
                result.add(line);
                // return line;
            }
        }
        in.close();
        return result;
        // return "NONE";
    }

    /**
     * POST METHOD.
     * 
     * @param strUrl
     *            String
     * @param text
     *            String
     * @throws IOException
     * @return String
     */
    public static String urlPost(String strUrl, String text, int timeOut) throws IOException {

        int indexOf = strUrl.indexOf("?");

        String substring = strUrl.substring(indexOf + 1);
        substring = substring + text;
        strUrl = strUrl.substring(0, indexOf);
        String content = substring;
        URL url = new URL(strUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        con.setConnectTimeout(timeOut);
        con.setReadTimeout(timeOut);
        con.setRequestProperty("magicCode", "FRxOdNYZbAojcohrvWMH9Sxy14P6pMBuiUK");
        con.setRequestProperty("from", "dsis");
        con.setDoInput(true);
        con.setDoOutput(true);
        con.setAllowUserInteraction(false);
        con.setUseCaches(false);
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
        // conn.connect();
        BufferedWriter bout = new BufferedWriter(new OutputStreamWriter(con.getOutputStream()));
        bout.write(content);
        bout.flush();
        bout.close();
        BufferedReader bin = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"), SIZE);
        List<String> result = new ArrayList<String>();
        while (true) {
            String line = bin.readLine();
            if (line == null) {
                break;
            } else {
                // result.add(line);
                return line;
            }
        }
        bin.close();
        // return result;
        return "NONE";
    }

}
