package com.lkkhpg.dsis.integration.payment.service.impl;

import com.lkkhpg.dsis.platform.cache.Cache;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 获取本机ip
 * Created by hand on 2017/11/20.
 */
public class GetNativeIP {
    /**
     * 知识的补充
     *
     * InetAddress 继承自 java.lang.Object类
     * 它有两个子类：Inet4Address 和 Inet6Address
     * 此类表示互联网协议 (IP) 地址。
     *
     * IP 地址是 IP 使用的 32 位或 128 位无符号数字，
     * 它是一种低级协议，UDP 和 TCP 协议都是在它的基础上构建的。
     *
     * ************************************************
     * 主机名就是计算机的名字（计算机名），网上邻居就是根据主机名来识别的。
     * 这个名字可以随时更改，从我的电脑属性的计算机名就可更改。
     *  用户登陆时候用的是操作系统的个人用户帐号，这个也可以更改，
     *  从控制面板的用户界面里改就可以了。这个用户名和计算机名无关。
     */

    /**
     * 获取本机的IP
     * @return Ip地址
     */
    public static String getLocalHostIP() {
        String ip;
        try {
            /**返回本地主机。*/
            InetAddress addr = InetAddress.getLocalHost();
            /**返回 IP 地址字符串（以文本表现形式）*/
            ip = addr.getHostAddress();
        } catch(Exception ex) {
            ip = "";
        }

        return ip;
    }

    /**
     * 或者主机名：
     * @return
     */
    public static String getLocalHostName() {
        String hostName;
        try {
            /**返回本地主机。*/
            InetAddress addr = InetAddress.getLocalHost();
            /**获取此 IP 地址的主机名。*/
            hostName = addr.getHostName();
        }catch(Exception ex){
            hostName = "";
        }

        return hostName;
    }

    /**
     * 获得本地所有的IP地址
     * @return
     */
    public static String[] getAllLocalHostIP() {

        String[] ret = null;
        try {
            /**获得主机名*/
            String hostName = getLocalHostName();
            if(hostName.length()>0) {
                /**在给定主机名的情况下，根据系统上配置的名称服务返回其 IP 地址所组成的数组。*/
                InetAddress[] addrs = InetAddress.getAllByName(hostName);
                if(addrs.length>0) {
                    ret = new String[addrs.length];
                    for(int i=0 ; i< addrs.length ; i++) {
                        /**.getHostAddress()   返回 IP 地址字符串（以文本表现形式）。*/
                        ret[i] = addrs[i].getHostAddress();
                    }
                }
            }

        }catch(Exception ex) {
            ret = null;
        }

        return ret;
    }

    public static void main(String[] args) throws UnknownHostException {

//        String check="\n" +
//                "<html>\n" +
//                "<head>\n" +
//                "<title></title>\n" +
//                "</head>\n" +
//                "<body onload=\"document.forms[0].submit();\">\n" +
//                "<form  method='post' action='http://test.cpcn.com.cn/BankSimulator/InterfaceI'  id='form1' ><input type='hidden' name='message' value='PFJlcXVlc3Q+PEhlYWQ+PFR4Q29kZT4xMTAxPC9UeENvZGU+PC9IZWFkPjxCb2R5PjxJbnN0aXR1dGlvbklEPjExMTExMTExPC9JbnN0aXR1dGlvbklEPjxPcmRlck5vPjE4MDExOTc0MjEyODc2MzwvT3JkZXJObz48T3JkZXJUaW1lPjIwMTgwMTE5MTU0ODQ1PC9PcmRlclRpbWU+PEFtb3VudD44MDkwMDwvQW1vdW50PjxSZW1hcms+PC9SZW1hcms+PE5vdGlmaWNhdGlvblVSTD5odHRwOi8vdGVzdC5jcGNuLmNvbS5jbi9ibXIvc2ltdWxhdG9yX2IyYzwvTm90aWZpY2F0aW9uVVJMPjxOb3RpZmljYXRpb25CYWNrZ3JvdW5kVVJMPmh0dHA6Ly90ZXN0LmNwY24uY29tLmNuL2Jtci9iYW5rNzAwX2IyY19iYWNrZ3JvdW5kPC9Ob3RpZmljYXRpb25CYWNrZ3JvdW5kVVJMPjwvQm9keT48L1JlcXVlc3Q+'/></form>\n" +
//                "</body>\n" +
//                "</html>";
//
//        String sort="form";
//        String s="submit";
//        String s1="method";
//        String str="action";
//        int a=check.indexOf(sort);
//        System.out.println(a);
//        System.out.println("本机IP：" + getLocalHostIP());
//        System.out.println("本地主机名字为：" + getLocalHostName());
//
//        String[] localIP = getAllLocalHostIP();
//        for (int i = 0; i < localIP.length; i++) {
//            System.out.println(localIP[i]);
//        }
//
//        InetAddress baidu = InetAddress.getByName("www.baidu.com");
//        System.out.println("baidu : " + baidu);
//        System.out.println("baidu IP: " + baidu.getHostAddress());
//        System.out.println("baidu HostName: " + baidu.getHostName());
//        Date now=new Date();
//        try {
//           System.out.println( (toShortDateString(now)));
//        }catch (Exception e){
//            e.printStackTrace();
//        }

//
//        Calendar cal=Calendar.getInstance();
////System.out.println(Calendar.DATE);//5
//        cal.add(Calendar.DATE,-1);
//        Date time=cal.getTime();
//        System.out.println(new SimpleDateFormat("yyyyMMdd").format(time));


//        BigDecimal decimal = new BigDecimal("1.5");
//
//        BigDecimal setScale = decimal.setScale(2,BigDecimal.ROUND_DOWN);
//        System.out.println(setScale);
//
//        BigDecimal setScale1 = decimal.setScale(2,BigDecimal.ROUND_HALF_UP);
//        System.out.println(setScale1);

    }

    public static String toShortDateString(Date dt){
        SimpleDateFormat myFmt=new SimpleDateFormat("yyyyMMdd");
        return myFmt.format(dt);
    }

}
