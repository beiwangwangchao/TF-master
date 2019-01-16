package com.lkkhpg.dsis.integration.payment.configration;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.lkkhpg.dsis.integration.payment.dto.PaymentConfig;
import com.lkkhpg.dsis.integration.payment.mapper.PaymentConfigMapper;
import com.lkkhpg.dsis.platform.message.IMessageConsumer;
import com.lkkhpg.dsis.platform.message.TopicMonitor;
import com.lkkhpg.dsis.platform.security.service.IAESClientService;

@TopicMonitor(channel = "payment_config")
public class PaymentConfigrationManager implements InitializingBean, IMessageConsumer<PaymentConfig> {

    public static final String PAYMENT_CONFIG = "payment_config";

    private PaymentConfigMapper configMapper;

    public static final String UNION = "UNION";

    public static final String MIGS = "MIGS";
    public static final String NONUNION = "NONUNION";

    private ChinaTrustUnionConfigration chinaTrustUnionConfigration = new ChinaTrustUnionConfigration();
    private ChinaTrustNonUnionConfigration chinaTrustNonUnionConfigration = new ChinaTrustNonUnionConfigration();
    private MIGSConfigration migsConfigration = new MIGSConfigration();

    private Logger logger = LoggerFactory.getLogger(PaymentConfigrationManager.class);

    @Autowired
    private IAESClientService aesService;

    @Autowired
    private BeanFactory beanFactory;

    public MIGSConfigration migs() {
        return migsConfigration;
    }

    public ChinaTrustUnionConfigration union() {
        return chinaTrustUnionConfigration;
    }

    public ChinaTrustNonUnionConfigration noUnion() {
        return chinaTrustNonUnionConfigration;
    }

    public PaymentConfigMapper getConfigMapper() {
        return configMapper;
    }

    public void setConfigMapper(PaymentConfigMapper configMapper) {
        this.configMapper = configMapper;
    }

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        PaymentConfigMapper bean2 = beanFactory.getBean(PaymentConfigMapper.class);
        List<PaymentConfig> configs = bean2.selectByType(null);
        if (configs == null) {
            return;
        }
        for (PaymentConfig paymentConfig : configs) {
            configChanged(paymentConfig);
        }
    }

    public PaymentConfig encrypt(PaymentConfig paymentConfig) {
        return encryptDecrypt(paymentConfig, true);
    }

    private PaymentConfig encryptDecrypt(PaymentConfig paymentConfig, boolean encrypt) {
        String paymentType = paymentConfig.getPaymentType();
        String key = StringUtils.trim(paymentConfig.getKey());
        String value = StringUtils.trim(paymentConfig.getValue());
        boolean f = false;
        if (MIGS.equals(paymentType)) {
            switch (key) {
            case "vpcPassword":
                f = true;
                break;
            case "secureHashSecret":
                f = true;
                break;
            default:
                break;
            }
        }

        if (NONUNION.equals(paymentType) || UNION.equals(paymentType)) {
            switch (key) {
            case "key":
                f = true;
                break;
            default:
                break;
            }
        }
        if (f) {
            String v = encrypt ? aesService.encrypt(value) : aesService.decrypt(value);
            paymentConfig.setValue(v);
        }
        return paymentConfig;

    }

    public PaymentConfig decrypt(PaymentConfig paymentConfig) {
        return encryptDecrypt(paymentConfig, false);
    }

    public void configChanged(PaymentConfig paymentConfig) {
        // System.out.println(paymentConfig.getPaymentType());
        // System.out.println(paymentConfig.getValue());
        // System.out.println(paymentConfig.getKey());
        //
        paymentConfig = decrypt(paymentConfig);
        String paymentType = paymentConfig.getPaymentType();
        switch (paymentType) {
        case UNION:
            union(paymentConfig);
            break;
        case MIGS:
            migs(paymentConfig);
            break;
        case NONUNION:
            nonUnion(paymentConfig);
            break;
        default:
            break;
        }
        if (logger.isInfoEnabled()) {
            logger.info("PaymentConfigration Is Updated.");
        }

    }

    private void migs(PaymentConfig paymentConfig) {
        String key = paymentConfig.getKey();
        String value = paymentConfig.getValue();

        switch (key) {
        case "merchantId":
            migsConfigration.setMerchantId(value);
            break;
        case "merchtxnRef":
            // migsConfigration.setMerchtxnRef(value);
            break;
        case "accessCode":
            migsConfigration.setAccessCode(value);
            break;
        case "vpcReturnUrl":
            migsConfigration.setVpcReturnUrl(value);
            break;
        case "vpcSecureHashType":
            migsConfigration.setVpcSecureHashType(value);
            break;
        case "secureType":
            migsConfigration.setSecureType(value);
            break;
        case "action":
            migsConfigration.setAction(value);
            break;
        case "txnResponseCode":
            migsConfigration.setTxnResponseCode(value);
            break;
        case "acqResponseCode":
            migsConfigration.setAcqResponseCode(value);
            break;
        case "vpcUser":
            migsConfigration.setVpcUser(value);
            break;
        case "virtualPaymentClientURL":
            migsConfigration.setVirtualPaymentClientURL(value);
            break;
        case "secureHashSecret":
            migsConfigration.setSecureHashSecret(value);
            break;
        case "vpcPassword":
            migsConfigration.setVpcPassword(value);
            break;
        case "checkAES":
            // migsConfigration.setCheckAES(value);
            break;
        default:
            break;
        }
    }

    private void union(PaymentConfig paymentConfig) {
        String key = paymentConfig.getKey();
        String value = paymentConfig.getValue();
        switch (key) {
        case "key":
            chinaTrustUnionConfigration.setKey(value);
            break;
        case "merid":
            try {
                Integer valueOf = Integer.valueOf(value);
                chinaTrustUnionConfigration.setMerid(valueOf);
            } catch (Exception e) {
                if(this.logger.isInfoEnabled()){
                    logger.info("ERROR -- > PLEASE CHECK THE MERID OF UNION PAYMENT CONFIG.");
                }
            }
            break;
        case "successRespCode":
            chinaTrustUnionConfigration.setSuccessRespCode(value);
            break;
        case "successRtnCode":
            chinaTrustUnionConfigration.setSuccessRtnCode(value);
            break;
        case "refundServer":
            chinaTrustUnionConfigration.setRefundServer(value);
            break;
        case "queryServer":
            chinaTrustUnionConfigration.setQueryServer(value);
            break;
        case "action":
            chinaTrustUnionConfigration.setAction(value);
            break;
        case "frontCallbackUrl":
            chinaTrustUnionConfigration.setFrontCallbackUrl(value);
            break;
        case "backCallbackUrl":
            chinaTrustUnionConfigration.setBackCallbackUrl(value);
            break;
        case "orderStatusS":
            chinaTrustUnionConfigration.setOrderStatusS(value);
            break;
        case "orderStatusF":
            chinaTrustUnionConfigration.setOrderStatusF(value);
            break;

        default:
            break;
        }
    }

    private void nonUnion(PaymentConfig paymentConfig) {
        String key = paymentConfig.getKey();
        String value = paymentConfig.getValue();
        switch (key) {
        case "key":
            chinaTrustNonUnionConfigration.setKey(value);
            break;
        case "merid":
            chinaTrustNonUnionConfigration.setMerid(value);
            break;
        case "merchantId":
            chinaTrustNonUnionConfigration.setMerchantId(value);
            break;
        case "merchantName":
            chinaTrustNonUnionConfigration.setMerchantName(value);
            break;
        case "terminalId":
            chinaTrustNonUnionConfigration.setTerminalId(value);
            break;
        case "authResUrl":
            chinaTrustNonUnionConfigration.setAuthResUrl(value);
            break;
        case "refundServer":
            chinaTrustNonUnionConfigration.setRefundServer(value);
            break;
        case "queryServer":
            chinaTrustNonUnionConfigration.setQueryServer(value);
            break;
        case "action":
            chinaTrustNonUnionConfigration.setAction(value);
            break;
        case "errCode":
            chinaTrustNonUnionConfigration.setErrCode(value);
            break;
        case "successRespCode":
            chinaTrustNonUnionConfigration.setSuccessRespCode(value);
            break;
        case "currentStateS":
            chinaTrustNonUnionConfigration.setCurrentStateS(value);
            break;
        case "currentStateF":
            chinaTrustNonUnionConfigration.setCurrentStateF(value);
            break;
        case "useAuthCode":
            chinaTrustNonUnionConfigration.setUseAuthCode(value);
            break;
        default:
            break;
        }
    }

    @Override
    public void onMessage(PaymentConfig message, String pattern) {
        if (PAYMENT_CONFIG.equals(pattern)) {
            this.configChanged(message);
        }
    }

}
