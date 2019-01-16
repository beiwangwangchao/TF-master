/*
 *
 */
package com.lkkhpg.dsis.integration.payment.service.impl;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lkkhpg.dsis.integration.payment.configration.IPaymentLogger;
import com.lkkhpg.dsis.integration.payment.configration.MIGSConfigration;
import com.lkkhpg.dsis.integration.payment.dto.MIGSCallbackModel;
import com.lkkhpg.dsis.integration.payment.dto.MIGSModel;
import com.lkkhpg.dsis.integration.payment.dto.MIGSQueryModel;
import com.lkkhpg.dsis.integration.payment.dto.MIGSRefundModel;
import com.lkkhpg.dsis.integration.payment.exception.PaymentException;
import com.lkkhpg.dsis.integration.payment.service.IMIGSPaymentService;

/**
 * MIGS支付Service.
 * 
 * @author shiliyan
 *
 */
@Service
public class MIGSPaymentServiceImpl implements IMIGSPaymentService, IPaymentLogger {

    private static final String PAYMENT_MIGS_SERVICE_PAY_ENCODE = "payment.migs.service.pay.encode {}";
    private static final String PAYMENT_MIGS_SERVICE_PAY_ENCODE_ERROR = "payment.migs.service.pay.encode.error {}";
    private static final String PAYMENT_MIGS_SERVICE_PAY_QUERY = "payment.migs.service.pay.query {}";
    private static final String PAYMENT_MIGS_SERVICE_PAY_CALLBACK_DECODE = "payment.migs.service.pay.callback.decode {}";
    private static final String PAYMENT_MIGS_SERVICE_PAY_CALLBACK_DECODE_ERROR = "payment.migs.service.pay.callback.decode.error {}";
    private Logger logger = LoggerFactory.getLogger(MIGSPaymentServiceImpl.class);

    @Autowired
    private MIGSConfigration migsConfigration;

    @Override
    public MIGSModel pay(MIGSModel model) {
        this.info(PAYMENT_MIGS_SERVICE_PAY_ENCODE, logger, model.getVpc_OrderInfo());
        try {
            model = encode(model, migsConfigration);
        } catch (Exception e) {
            this.info(PAYMENT_MIGS_SERVICE_PAY_ENCODE_ERROR, logger, model.getVpc_OrderInfo());
            return null;
        }
        return model;
    }

    @Override
    public MIGSQueryModel query(MIGSModel model) {
        this.info(PAYMENT_MIGS_SERVICE_PAY_QUERY, logger, model.getVpc_OrderInfo());

        MIGSQueryModel r = doQuery(model);

        return r;
    }

    @Override
    public MIGSCallbackModel callback(MIGSCallbackModel model, Map<String, String> fields) throws PaymentException {
        try {
            this.info(PAYMENT_MIGS_SERVICE_PAY_CALLBACK_DECODE, logger, model.getVpc_OrderInfo());
            boolean decode = MIGSCommon.decode(model, fields, migsConfigration);
            if (decode) {
                return model;
            }
        } catch (InvalidKeyException | NoSuchAlgorithmException | IllegalStateException
                | UnsupportedEncodingException e) {
        }
        this.info(PAYMENT_MIGS_SERVICE_PAY_CALLBACK_DECODE_ERROR, logger, model.getVpc_OrderInfo());
        throw new PaymentException(PaymentException.MIGS_PAY_CALLBACK_DECODE_ERR);

    }

    private MIGSModel encode(MIGSModel model, MIGSConfigration migsConfigration)
            throws InvalidKeyException, NoSuchAlgorithmException, IllegalStateException, UnsupportedEncodingException {
        Map<String, String> fields = new HashMap<String, String>();
        String secureType = migsConfigration.getSecureType();

        String vpc_AccessCode = migsConfigration.getAccessCode();
        String vpc_Merchant = migsConfigration.getMerchantId();
        //1、  vpc_OrderInfo字段改为用 vpc_MerchTxnRef；
        //String vpc_MerchTxnRef = migsConfigration.getMerchtxnRef();
        String vpc_MerchTxnRef = model.getVpc_MerchTxnRef(); 
        String vpc_ReturnURL = migsConfigration.getVpcReturnUrl();
        String vpc_Command = MIGSConfigration.VPC_COMMAND_PAY;
        String vpc_Version = model.getVpc_Version();
        String vpc_Locale = model.getVpc_Locale();

        fields.put(MIGSConfigration.VPC_ACCESS_CODE, vpc_AccessCode);
        fields.put(MIGSConfigration.VPC_AMOUNT, model.getVpc_Amount());
        fields.put(MIGSConfigration.VPC_COMMAND, vpc_Command);
        fields.put(MIGSConfigration.VPC_LOCALE, vpc_Locale);
        fields.put(MIGSConfigration.VPC_MERCHANT, vpc_Merchant);
        fields.put(MIGSConfigration.VPC_MERCH_TXN_REF, vpc_MerchTxnRef);
        fields.put(MIGSConfigration.VPC_RETURN_URL, vpc_ReturnURL);
        fields.put(MIGSConfigration.VPC_VERSION, vpc_Version);
        // fields.put("vpc_Currency", model.getVpc_Currency());
        fields.put(MIGSConfigration.VPC_ORDER_INFO, model.getVpc_OrderInfo());
        String secureHash = MIGSCommon.shaHashAllFields(fields, migsConfigration);
        fields.put(MIGSConfigration.VPC_SECURE_HASH, secureHash);
        fields.put(MIGSConfigration.VPC_SECURE_HASH_TYPE, secureType);

        model.setVpc_SecureHash(secureHash);

        return model;
    }

    private MIGSQueryModel doQuery(MIGSModel model) {
        MIGSQueryModel query = MIGSQuery.query(model, this.migsConfigration);
        return query;
    }

    @Override
    public MIGSRefundModel refund(MIGSCallbackModel model) {

        MIGSRefundModel doRefund = MIGSRefund.doRefund(model, migsConfigration);
        return doRefund;
    }
}
