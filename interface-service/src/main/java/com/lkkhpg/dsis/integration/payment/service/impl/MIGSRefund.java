/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.integration.payment.service.impl;

import java.util.HashMap;
import java.util.Map;

import com.lkkhpg.dsis.integration.payment.configration.MIGSConfigration;
import com.lkkhpg.dsis.integration.payment.dto.MIGSCallbackModel;
import com.lkkhpg.dsis.integration.payment.dto.MIGSRefundModel;

/**
 * @author shiliyan
 *
 */
public class MIGSRefund extends MIGSCommon {

    public static MIGSRefundModel doRefund(MIGSCallbackModel model, MIGSConfigration config) {
        boolean useProxy = false;
        String proxyHost = "192.168.21.13";
        int proxyPort = 80;

        // These fields are not returned in receipt for an error condition
        // String transactionNo = request.getParameter("vpc_TransNo");
        // String merchTxnRef = request.getParameter("vpc_MerchTxnRef");

        // retrieve all the parameters into a hash map
        Map<String, String> requestFields = new HashMap<String, String>();
        // for (Enumeration enum = request.getParameterNames();
        // enum.hasMoreElements();) {
        // String fieldName = (String) enum.nextElement();
        // String fieldValue = request.getParameter(fieldName);
        // if ((fieldValue != null) && (fieldValue.length() > 0)) {
        // requestFields.put(fieldName, fieldValue);
        // }
        // }

        String virtualPaymentClientURL = config.getVirtualPaymentClientURL();
        String vpc_Version = model.getVpc_Version();
        String vpc_Command = MIGSConfigration.VPC_COMMAND_REFUND;
        String vpc_AccessCode = config.getAccessCode();
        String vpc_Merchant = config.getMerchantId();
        String vpc_MerchTxnRef = model.getVpc_OrderInfo();
//        config.getMerchtxnRef();
        String vpc_User = config.getVpcUser();
        String vpc_Password = config.getVpcPassword();
        String vpc_TransNo = model.getVpc_TransactionNo();
        String vpc_Amount = model.getVpc_Amount();

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
        //// fields.put("vpc_SecureHashType", "SHA256");
        // fields.put("vpc_TransactionNo", "2000000047");
        requestFields.put("vpc_TransNo", vpc_TransNo);
        requestFields.put("vpc_Amount", vpc_Amount);

        // no need to send the vpcURL, Title and Submit Button to the vpc
        String vpcURL = (String) requestFields.remove("virtualPaymentClientURL");
        String title = (String) requestFields.remove("Title");
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
        //System.out.println(message);

        // Standard Receipt Data
        String amount = null2unknown("vpc_Amount", responseFields);
        String locale = null2unknown("vpc_Locale", responseFields);
        String batchNo = null2unknown("vpc_BatchNo", responseFields);
        String command = null2unknown("vpc_Command", responseFields);
        String version = null2unknown("vpc_Version", responseFields);
        String cardType = null2unknown("vpc_Card", responseFields);
        String orderInfo = null2unknown("vpc_OrderInfo", responseFields);
        String receiptNo = null2unknown("vpc_ReceiptNo", responseFields);
        String merchantID = null2unknown("vpc_Merchant", responseFields);
        String merchTxnRef = null2unknown("vpc_MerchTxnRef", responseFields);
        String authorizeID = null2unknown("vpc_AuthorizeId", responseFields);
        String transactionNr = null2unknown("vpc_TransactionNo", responseFields);
        String acqResponseCode = null2unknown("vpc_AcqResponseCode", responseFields);
        String txnResponseCode = null2unknown("vpc_TxnResponseCode", responseFields);

        // Capture Data
        String shopTransNo = null2unknown("vpc_ShopTransactionNo", responseFields);
        String authorisedAmount = null2unknown("vpc_AuthorisedAmount", responseFields);
        String capturedAmount = null2unknown("vpc_CapturedAmount", responseFields);
        String refundedAmount = null2unknown("vpc_RefundedAmount", responseFields);
        String ticketNumber = null2unknown("vpc_TicketNo", responseFields);

        String error = "";
        // Show this page as an error page if error condition
        if (txnResponseCode.equals("7") || txnResponseCode.equals("No Value Returned")) {
            error = "Error ";
        }
        if (!txnResponseCode.equals("7") && !txnResponseCode.equals("No Value Returned")) {
        }

        MIGSRefundModel migsRefundModel = new MIGSRefundModel(message, batchNo, cardType, orderInfo, receiptNo,
                authorizeID, transactionNr, acqResponseCode, txnResponseCode, shopTransNo, authorisedAmount,
                capturedAmount, refundedAmount, ticketNumber, error);
        migsRefundModel.setVpc_Amount(amount);
        migsRefundModel.setVpc_Merchant(merchantID);
        migsRefundModel.setVpc_Locale(locale);
        migsRefundModel.setVpc_Command(command);
        migsRefundModel.setVpc_Version(version);
        migsRefundModel.setVpc_MerchTxnRef(merchTxnRef);
        return migsRefundModel;

    }

}
