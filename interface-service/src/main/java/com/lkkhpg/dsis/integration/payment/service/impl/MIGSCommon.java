/*
 *
 */
package com.lkkhpg.dsis.integration.payment.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.Socket;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

import com.lkkhpg.dsis.integration.payment.configration.MIGSConfigration;
import com.lkkhpg.dsis.integration.payment.dto.MIGSCallbackModel;

/**
 * @author shiliyan
 *
 */
public class MIGSCommon {

    public static X509TrustManager s_x509TrustManager = null;
    public static SSLSocketFactory s_sslSocketFactory = null;

    static {
        s_x509TrustManager = new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[] {};
            }

            public boolean isClientTrusted(X509Certificate[] chain) {
                return true;
            }

            public boolean isServerTrusted(X509Certificate[] chain) {
                return true;
            }

            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

            }
        };

        java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        try {
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, new X509TrustManager[] { s_x509TrustManager }, null);
            s_sslSocketFactory = context.getSocketFactory();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    // ----------------------------------------------------------------------------

    /**
     * This method is for performing a Form POST operation from input data
     * parameters.
     *
     * @param vpc_Host
     *            - is a String containing the vpc URL
     * @param data
     *            - is a String containing the post data key value pairs
     * @param useProxy
     *            - is a boolean indicating if a Proxy Server is involved in the
     *            transfer
     * @param proxyHost
     *            - is a String containing the IP address of the Proxy to send
     *            the data to
     * @param proxyPort
     *            - is an integer containing the port number of the Proxy socket
     *            listener
     * @throws IOException
     * 
     * @return - is body data of the POST data response
     */
    public static String doPost(String vpc_Host, String data, boolean useProxy, String proxyHost, int proxyPort)
            throws IOException {

        InputStream is;
        OutputStream os;
        int vpc_Port = 443;
        String fileName = "";
        boolean useSSL = false;

        // determine if SSL encryption is being used
        if (vpc_Host.substring(0, 8).equalsIgnoreCase("HTTPS://")) {
            useSSL = true;
            // remove 'HTTPS://' from host URL
            vpc_Host = vpc_Host.substring(8);
            // get the filename from the last section of vpc_URL
            fileName = vpc_Host.substring(vpc_Host.lastIndexOf("/"));
            // get the IP address of the VPC machine
            vpc_Host = vpc_Host.substring(0, vpc_Host.lastIndexOf("/"));
        }

        // use the next block of code if using a proxy server
        if (useProxy) {
            Socket s = new Socket(proxyHost, proxyPort);
            os = s.getOutputStream();
            is = s.getInputStream();
            // use next block of code if using SSL encryption
            if (useSSL) {
                String msg = "CONNECT " + vpc_Host + ":" + vpc_Port + " HTTP/1.0\r\n"
                        + "User-Agent: HTTP Client\r\n\r\n";
                os.write(msg.getBytes());
                byte[] buf = new byte[4096];
                int len = is.read(buf);
                String res = new String(buf, 0, len);

                // check if a successful HTTP connection
                if (res.indexOf("200") < 0) {
                    throw new IOException("Proxy would now allow connection - " + res);
                }

                // write output to VPC
                SSLSocket ssl = (SSLSocket) s_sslSocketFactory.createSocket(s, vpc_Host, vpc_Port, true);
                ssl.startHandshake();
                os = ssl.getOutputStream();
                // get response data from VPC
                is = ssl.getInputStream();
                // use the next block of code if NOT using SSL encryption
            } else {
                fileName = vpc_Host;
            }
            // use the next block of code if NOT using a proxy server
        } else {
            // use next block of code if using SSL encryption
            if (useSSL) {
                Socket s = s_sslSocketFactory.createSocket(vpc_Host, vpc_Port);
                os = s.getOutputStream();
                is = s.getInputStream();
                // use next block of code if NOT using SSL encryption
            } else {
                Socket s = new Socket(vpc_Host, vpc_Port);
                os = s.getOutputStream();
                is = s.getInputStream();
            }
        }

        String req = "POST " + fileName + " HTTP/1.0\r\n" + "User-Agent: HTTP Client\r\n"
                + "Content-Type: application/x-www-form-urlencoded\r\n" + "Content-Length: " + data.length()
                + "\r\n\r\n" + data;

        os.write(req.getBytes());
        String res = new String(readAll(is));

        // check if a successful connection
        if (res.indexOf("200") < 0) {
            throw new IOException("Connection Refused - " + res);
        }

        if (res.indexOf("404 Not Found") > 0) {
            throw new IOException("File Not Found Error - " + res);
        }

        int resIndex = res.indexOf("\r\n\r\n");
        String body = res.substring(resIndex + 4, res.length());
        return body;
    }

    // ----------------------------------------------------------------------------

    /**
     * This method is for creating a byte array from input stream data.
     *
     * @param is
     *            - the input stream containing the data
     * @throws IOException
     * @return is the byte array of the input stream data
     * 
     */
    public static byte[] readAll(InputStream is) throws IOException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];

        while (true) {
            int len = is.read(buf);
            if (len < 0) {
                break;
            }
            baos.write(buf, 0, len);
        }
        return baos.toByteArray();
    }

    // ----------------------------------------------------------------------------

    /**
     * This method is for creating a URL POST data string.
     *
     * @param fields
     *            is the input parameters from the order page
     * @return is the output String containing POST data key value pairs
     */
    public static String createPostDataFromMap(Map fields) {
        StringBuffer buf = new StringBuffer();

        String ampersand = "";

        // append all fields in a data string
        for (Iterator i = fields.keySet().iterator(); i.hasNext();) {

            String key = (String) i.next();
            String value = (String) fields.get(key);

            if ((value != null) && (value.length() > 0)) {
                // append the parameters
                buf.append(ampersand);
                buf.append(URLEncoder.encode(key));
                buf.append('=');
                buf.append(URLEncoder.encode(value));
            }
            ampersand = "&";
        }

        // return string
        return buf.toString();
    }

    // ----------------------------------------------------------------------------

    /**
     * This method is for creating a URL POST data string.
     *
     * @param queryString
     *            is the input String from POST data response
     * @return is a Hashmap of Post data response inputs
     */
    public static Map createMapFromResponse(String queryString) {
        Map map = new HashMap();
        StringTokenizer st = new StringTokenizer(queryString, "&");
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            int i = token.indexOf('=');
            if (i > 0) {
                try {
                    String key = token.substring(0, i);
                    String value = URLDecoder.decode(token.substring(i + 1, token.length()));
                    map.put(key, value);
                } catch (Exception ex) {
                    // Do Nothing and keep looping through data
                }
            }
        }
        return map;
    }
    // ----------------------------------------------------------------------------

    /*
     * This method takes a data String and returns a predefined value if empty
     * If data Sting is null, returns string "No Value Returned", else returns
     * input
     *
     * @param in String containing the data String
     * 
     * @return String containing the output String
     */
    public static String null2unknown(String in, Map responseFields) {
        if (in == null || in.length() == 0 || (String) responseFields.get(in) == null) {
            return "No Value Returned";
        } else {
            return (String) responseFields.get(in);
        }
    } // null2unknown()

    // ----------------------------------------------------------------------------

    /*
     * This function uses the returned status code retrieved from the Digital
     * Response and returns an appropriate description for the code
     *
     * @param vResponseCode String containing the vpc_TxnResponseCode
     * 
     * @return description String containing the appropriate description
     */
    public static String getResponseDescription(String vResponseCode) {

        String result = "";

        // check if a single digit response code
        if (vResponseCode.length() != 1) {

            // Java cannot switch on a string so turn everything to a char
            char input = vResponseCode.charAt(0);

            switch (input) {
            case '0':
                result = "Transaction Successful";
                break;
            case '1':
                result = "Unknown Error";
                break;
            case '2':
                result = "Bank Declined Transaction";
                break;
            case '3':
                result = "No Reply from Bank";
                break;
            case '4':
                result = "Expired Card";
                break;
            case '5':
                result = "Insufficient Funds";
                break;
            case '6':
                result = "Error Communicating with Bank";
                break;
            case '7':
                result = "Payment Server System Error";
                break;
            case '8':
                result = "Transaction Type Not Supported";
                break;
            case '9':
                result = "Bank declined transaction (Do not contact Bank)";
                break;
            case 'A':
                result = "Transaction Aborted";
                break;
            case 'C':
                result = "Transaction Cancelled";
                break;
            case 'D':
                result = "Deferred transaction has been received and is awaiting processing";
                break;
            case 'F':
                result = "3D Secure Authentication failed";
                break;
            case 'I':
                result = "Card Security Code verification failed";
                break;
            case 'L':
                result = "Shopping Transaction Locked (Please try the transaction again later)";
                break;
            case 'N':
                result = "Cardholder is not enrolled in Authentication Scheme";
                break;
            case 'P':
                result = "Transaction has been received by the Payment Adaptor and is being processed";
                break;
            case 'R':
                result = "Transaction was not processed - Reached limit of retry attempts allowed";
                break;
            case 'S':
                result = "Duplicate SessionID (OrderInfo)";
                break;
            case 'T':
                result = "Address Verification Failed";
                break;
            case 'U':
                result = "Card Security Code Failed";
                break;
            case 'V':
                result = "Address Verification and Card Security Code Failed";
                break;
            case '?':
                result = "Transaction status is unknown";
                break;
            default:
                result = "Unable to be determined";
            }

            return result;
        } else {
            return "No Value Returned";
        }
    } // getResponseDescription()
      // This is an array for creating hex chars

    private static final char[] HEX_TABLE = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B',
            'C', 'D', 'E', 'F' };

    public static String shaHashAllFields(Map<String, String> fields, MIGSConfigration migsConfigration)
            throws NoSuchAlgorithmException, InvalidKeyException, IllegalStateException, UnsupportedEncodingException {

        // create a list and sort it
        List<String> fieldNames = new ArrayList<String>(fields.keySet());
        Collections.sort(fieldNames);

        // create a buffer for the SHA256 input
        StringBuffer buf = new StringBuffer();

        // iterate through the list and add the remaining field values
        Iterator<String> itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) fields.get(fieldName);
            // fieldValue = this.urlDecode(fieldValue);
            // hashKeys += fieldName + ", ";
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                buf.append(fieldName + "=" + fieldValue);
                if (itr.hasNext()) {
                    buf.append('&');
                }
            }
        }
        // hashedFields = buf.toString();
        byte[] mac = null;
        String secureHashSecret = migsConfigration.getSecureHashSecret();
        String secureType = migsConfigration.getSecureType();
        byte[] b = new BigInteger(secureHashSecret, 16).toByteArray();
        SecretKey key = new SecretKeySpec(b, secureType);
        Mac m = Mac.getInstance(secureType);
        m.init(key);
        // String values = new String(buf.toString(), "UTF-8");
        m.update(buf.toString().getBytes("UTF-8"));
        // "ISO-8859-1"
        mac = m.doFinal();

        String hashValue = hex(mac);
        return hashValue;

    } // end hashAllFields()

    // ----------------------------------------------------------------------------

    /**
     * Returns Hex output of byte array.
     */
    private static String hex(byte[] input) {
        // create a StringBuffer 2x the size of the hash array
        StringBuffer sb = new StringBuffer(input.length * 2);

        // retrieve the byte array data, convert it to hex
        // and add it to the StringBuffer
        for (int i = 0; i < input.length; i++) {
            sb.append(HEX_TABLE[(input[i] >> 4) & 0xf]);
            sb.append(HEX_TABLE[input[i] & 0xf]);
        }
        return sb.toString();
    }

    public static boolean decode(MIGSCallbackModel model, Map<String, String> fields, MIGSConfigration migsConfigration)
            throws InvalidKeyException, NoSuchAlgorithmException, IllegalStateException, UnsupportedEncodingException {

        if (fields.containsKey(MIGSConfigration.VPC_SECURE_HASH)) {
            fields.remove(MIGSConfigration.VPC_SECURE_HASH);
        }
        if (fields.containsKey(MIGSConfigration.VPC_SECURE_HASH_TYPE)) {
            fields.remove(MIGSConfigration.VPC_SECURE_HASH_TYPE);
        }

        String secureHash = shaHashAllFields(fields, migsConfigration);
        return secureHash.equals(model.getVpc_SecureHash());

    }
}
