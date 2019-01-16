/*
 *
 */
package com.lkkhpg.dsis.integration.payment.service.impl;

import java.util.HashMap;
import java.util.Map;

import com.lkkhpg.dsis.integration.payment.configration.MIGSConfigration;
import com.lkkhpg.dsis.integration.payment.dto.MIGSModel;
import com.lkkhpg.dsis.integration.payment.dto.MIGSQueryModel;

/**
 * @author shiliyan
 *
 */
public class MIGSQuery extends MIGSCommon {

    public static MIGSQueryModel query(MIGSModel model, MIGSConfigration config) {

        // START OF MAIN PROGRAM
        // *******************************************

        // Define Variables
        // If using a proxy server you must set the following variables
        // If NOT using a proxy server then set the 'useProxy' to false
        boolean useProxy = false;

        // You would need to add you own proxy server URL and port here
        String proxyHost = "192.168.21.13";
        int proxyPort = 80;

        // retrieve all the parameters into a hash map
        Map<String, String> requestFields = new HashMap<String, String>();
        // for (Enumeration e = request.getParameterNames();
        // e.hasMoreElements();) {
        // String fieldName = (String) e.nextElement();
        // String fieldValue = request.getParameter(fieldName);
        // if ((fieldValue != null) && (fieldValue.length() > 0)) {
        // requestFields.put(fieldName, fieldValue);
        // }
        // }

        String virtualPaymentClientURL = config.getVirtualPaymentClientURL();
        String vpc_Version = model.getVpc_Version();
        String vpc_Command = MIGSConfigration.VPC_COMMAND_QUERY;
        String vpc_AccessCode = config.getAccessCode();
        String vpc_Merchant = config.getMerchantId();
        ///1、   vpc_OrderInfo字段改为用 vpc_MerchTxnRef；
        //String vpc_MerchTxnRef = config.getMerchtxnRef();
        String vpc_MerchTxnRef = model.getVpc_MerchTxnRef();
        String vpc_User = config.getVpcUser();
        String vpc_Password = config.getVpcPassword();
        String vpc_OrderInfo = model.getVpc_OrderInfo();
        // Admin Account: Admin/MY@B31973
        // Test Account: EvanTest/LKK@123456
        // Test Account: EvanTest/LKK@12345

        // String vpc_OrderInfo = "migs20160331110";
        // String vpc_Amount = "10000";
        // String vpc_Locale="en";

        requestFields.put("virtualPaymentClientURL", virtualPaymentClientURL);
        requestFields.put("vpc_Version", vpc_Version);
        requestFields.put("vpc_Command", vpc_Command);
        requestFields.put("vpc_AccessCode", vpc_AccessCode);
        requestFields.put("vpc_Merchant", vpc_Merchant);
        requestFields.put("vpc_MerchTxnRef", vpc_MerchTxnRef);
        requestFields.put("vpc_User", vpc_User);
        requestFields.put("vpc_Password", vpc_Password);
        requestFields.put("vpc_OrderInfo", vpc_OrderInfo);

        // no need to send the vpcURL, Title and Submit Button to the vpc
        String vpcURL = (String) requestFields.remove("virtualPaymentClientURL");
        String title = (String) requestFields.remove("Title");
        // <td><select name="ReceiptType">
        // <option value="">Please Select</option>
        // <option value="">2/3-Party Transaction</option>
        // <option value="ama">Refund/Capture Transaction</option>
        // </select>
        // </td>

        String receiptType = (String) requestFields.remove("ReceiptType");
        receiptType = "";
        requestFields.remove("SubButL");

        // Retrieve the order page URL from the incoming order page. This is
        // only
        // here to give the user the easy ability to go back to the Order page.
        // This would not be required in a production system.
        // String againLink = request.getHeader("Referer");

        // create the post data string to send
        String postData = createPostDataFromMap(requestFields);

        String resQS = "";
        String message = "";

        try {
            // create a URL connection to the Virtual Payment Client
            resQS = doPost(vpcURL, postData, useProxy, proxyHost, proxyPort);

        } catch (Exception ex) {
            // The response is an error message so generate an Error Page
            message = ex.toString();
        } // try-catch

        // create a hash map for the response data
        Map responseFields = createMapFromResponse(resQS);

        // Extract the available receipt fields from the VPC Response
        // If not present then let the value be equal to 'No Value returned'
        // Not all data fields will return values for all transactions.

        // don't overwrite message if any error messages detected
        if (message.length() == 0) {
            message = null2unknown("vpc_Message", responseFields);
        }
//        System.out.println("vpc_user : " + vpc_User);
//        System.out.println("vpc_password : " + vpc_Password);
//        System.out.println(message);

        // Standard Receipt Data
        // merchTxnRef not always returned in response if no receipt so get
        // input
        // String merchTxnRef = request.getParameter("vpc_MerchTxnRef");

        String amount = MIGSCommon.null2unknown("vpc_Amount", responseFields);
        String locale = null2unknown("vpc_Locale", responseFields);
        String batchNo = null2unknown("vpc_BatchNo", responseFields);
        String command = null2unknown("vpc_Command", responseFields);
        String version = null2unknown("vpc_Version", responseFields);
        String cardType = null2unknown("vpc_Card", responseFields);
        String orderInfo = null2unknown("vpc_OrderInfo", responseFields);
        String receiptNo = null2unknown("vpc_ReceiptNo", responseFields);
        String merchantID = null2unknown("vpc_Merchant", responseFields);
        String authorizeID = null2unknown("vpc_AuthorizeId", responseFields);
        String transactionNo = null2unknown("vpc_TransactionNo", responseFields);
        String acqResponseCode = null2unknown("vpc_AcqResponseCode", responseFields);
        String txnResponseCode = null2unknown("vpc_TxnResponseCode", responseFields);

        // CSC Receipt Data
        String cscResultCode = null2unknown("vpc_CSCResultCode", responseFields);
        String cscRequestCode = null2unknown("vpc_CSCRequestCode", responseFields);
        String cscACQRespCode = null2unknown("vpc_AcqCSCRespCode", responseFields);

        // AVS Receipt Data
        String avs_City = null2unknown("vpc_AVS_City", responseFields);
        String avs_Country = null2unknown("vpc_AVS_Country", responseFields);
        String avs_Street01 = null2unknown("vpc_AVS_Street01", responseFields);
        String avs_PostCode = null2unknown("vpc_AVS_PostCode", responseFields);
        String avs_StateProv = null2unknown("vpc_AVS_StateProv", responseFields);
        String avsResultCode = null2unknown("vpc_AVSResultCode", responseFields);
        String avsRequestCode = null2unknown("vpc_AVSRequestCode", responseFields);
        String avsACQRespCode = null2unknown("vpc_AcqAVSRespCode", responseFields);

        // 3-D Secure Data
        String transType3DS = null2unknown("vpc_VerType", responseFields);
        String verStatus3DS = null2unknown("vpc_VerStatus", responseFields);
        String token3DS = null2unknown("vpc_VerToken", responseFields);
        String secureLevel3DS = null2unknown("vpc_VerSecurityLevel", responseFields);
        String enrolled3DS = null2unknown("vpc_3DSenrolled", responseFields);
        String xid3DS = null2unknown("vpc_3DSXID", responseFields);
        String eci3DS = null2unknown("vpc_3DSECI", responseFields);
        String status3DS = null2unknown("vpc_3DSstatus", responseFields);

        // Financial Transaction Data
        String shopTransNo = null2unknown("vpc_ShopTransactionNo", responseFields);
        String authorisedAmount = null2unknown("vpc_AuthorisedAmount", responseFields);
        String capturedAmount = null2unknown("vpc_CapturedAmount", responseFields);
        String refundedAmount = null2unknown("vpc_RefundedAmount", responseFields);
        String ticketNumber = null2unknown("vpc_TicketNo", responseFields);

        // Specific QueryDR Data
        String drExists = null2unknown("vpc_DRExists", responseFields);
        String multipleDRs = null2unknown("vpc_FoundMultipleDRs", responseFields);

        String error = "";
        // Show this page as an error page if error condition
        if (txnResponseCode.equals("7") || txnResponseCode.equals("No Value Returned")) {
            error = "Error ";
        }

        // only display aAMA fields if a Capture or Refund transaction
        boolean amaTransaction = false;
        if (receiptType.equalsIgnoreCase("ama")) {
            amaTransaction = true;
        }
        MIGSQueryModel migsQueryModel = new MIGSQueryModel(virtualPaymentClientURL, message, batchNo, cardType,
                orderInfo, receiptNo, authorizeID, transactionNo, acqResponseCode, txnResponseCode, cscResultCode,
                cscRequestCode, cscACQRespCode, avs_City, avs_Country, avs_Street01, avs_PostCode, avs_StateProv,
                avsResultCode, avsRequestCode, avsACQRespCode, transType3DS, verStatus3DS, token3DS, secureLevel3DS,
                enrolled3DS, xid3DS, eci3DS, status3DS, shopTransNo, authorisedAmount, capturedAmount, refundedAmount,
                ticketNumber, drExists, multipleDRs, error, amaTransaction);
        migsQueryModel.setVpc_Amount(amount);
        migsQueryModel.setVpc_Merchant(merchantID);
        migsQueryModel.setVpc_Locale(locale);
        migsQueryModel.setVpc_Command(command);
        migsQueryModel.setVpc_Version(vpc_Version);
        migsQueryModel.setVpc_MerchTxnRef(vpc_MerchTxnRef);
        return migsQueryModel;
    }

}
