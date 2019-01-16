/*
 *
 */
package com.lkkhpg.dsis.platform.report;

/**
 * Birt Url解析.
 * @author chenjingxiong
 */
public class BirtUrlResolver {

    private static String birtServerUrl;

    public static String getOutputUrl() {
        if (!birtServerUrl.endsWith("/")) {
            birtServerUrl = birtServerUrl + "/";
        }

        return birtServerUrl + "output";
    }


    public static String getBirtServerUrl() {
        return birtServerUrl;
    }

    public static void setBirtServerUrl(String birtServerUrl) {
        BirtUrlResolver.birtServerUrl = birtServerUrl;
    }
}
