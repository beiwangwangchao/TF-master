/*
 *
 */
package com.lkkhpg.dsis.admin.config.service.impl;

import java.util.*;

import com.lkkhpg.dsis.common.config.dto.SpmMarket;
import com.lkkhpg.dsis.common.config.mapper.SpmMarketMapper;
import com.lkkhpg.dsis.common.constant.OrderConstants;
import com.lkkhpg.dsis.integration.invoice.dto.EinvoiceTrackcodeRule;
import com.lkkhpg.dsis.integration.invoice.dto.InvoiceResponse;
import com.lkkhpg.dsis.platform.dto.system.CodeValue;
import com.lkkhpg.dsis.platform.dto.system.MessageReceiver;
import com.lkkhpg.dsis.platform.dto.system.User;
import com.lkkhpg.dsis.platform.mail.ReceiverTypeEnum;
import com.lkkhpg.dsis.platform.mapper.system.CodeValueMapper;
import com.lkkhpg.dsis.platform.mapper.system.UserMapper;
import com.lkkhpg.dsis.platform.service.IMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.lkkhpg.dsis.admin.config.exception.SystemProfileException;
import com.lkkhpg.dsis.admin.config.service.ISpmInvNumberingService;
import com.lkkhpg.dsis.common.config.dto.SpmInvNumbering;
import com.lkkhpg.dsis.common.config.mapper.SpmInvNumberingMapper;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.DTOStatus;

/**
 * 发票规则Service.
 *
 * @author chenjingxiong
 */
@Service
@Transactional
public class SpmInvNumberingServiceImpl implements ISpmInvNumberingService {
    @Autowired
    private SpmMarketMapper spmMarketMapper;
    @Autowired
    private SpmInvNumberingMapper spmInvNumberingMapper;

    @Autowired
    private CodeValueMapper codeValueMapper;

    @Autowired
    private IMessageService messageService;

    // lkkhpg_tw_einvoice 2017-08-09 BEGIN
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<SpmInvNumbering> querySpmInvNumberings(IRequest request, SpmInvNumbering record, int page,
                                                       int pageSize) {
        PageHelper.startPage(page, pageSize);
        return spmInvNumberingMapper.queryNumbering(record);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<SpmInvNumbering> saveSpmInvNumberings(IRequest request, List<SpmInvNumbering> spmInvNumberings)
            throws SystemProfileException {
        List<SpmInvNumbering> result = new ArrayList<>();

        for (SpmInvNumbering spmInvNumbering : spmInvNumberings) {
            // 校验满足该市场在同一时间没有启用状态的编号规则
            if (!valiate(spmInvNumbering)) {
                throw new SystemProfileException(SystemProfileException.MSG_ERROR_OM_HAVE_INVOICE_RULE,
                        new Object[]{});
            }

            //发票结束日期的结束时间
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(spmInvNumbering.getEndActiveDate());
            calendar.set(Calendar.HOUR, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            calendar.set(Calendar.MILLISECOND, 999);
            spmInvNumbering.setEndActiveDate(calendar.getTime());

            if (DTOStatus.ADD.equals(spmInvNumbering.get__status())) {
                spmInvNumberingMapper.insertNumbering(spmInvNumbering);
                result.add(spmInvNumbering);
            } else if (DTOStatus.DELETE.equals(spmInvNumbering.get__status())) {
                if (spmInvNumbering.getRuleId() != null) {
                    spmInvNumberingMapper.deleteByPrimaryKey(spmInvNumbering.getRuleId());
                }
            } else {
                spmInvNumberingMapper.updateNumbering(spmInvNumbering);
                result.add(spmInvNumbering);
            }
        }
        return result;
    }

    /**
     * 校验规则.
     *
     * @param spmInvNumbering 待校验的规则.
     * @return 是否校验成功.
     */
    private boolean valiate(SpmInvNumbering spmInvNumbering) {
        int count = spmInvNumberingMapper.selectCountInDateRange(spmInvNumbering);
        return count == 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByRuleId(IRequest request, List<SpmInvNumbering> spmInvNumberings) {
        for (SpmInvNumbering sig : spmInvNumberings) {
            if ("N".equals(sig.getEnabledFlag())) {
                spmInvNumberingMapper.deleteByPrimaryKey(sig.getRuleId());
            }
        }
    }

    // lkkhpg_tw_einvoice 2017-08-09 BEGIN
    //updated by 11816 at 2017/12/26 11:00
    @Override
    public void monitorAvailableBlankTrackcode(String marketCode) throws Exception {
        SpmMarket spmMarket = spmMarketMapper.selectMarketByCode(marketCode);
//        InvoiceResponse<Integer> response = feignClient.connectInvoice().getBlankTrackCodeNum();
        InvoiceResponse<Integer> response = null;
        if (response == null || !"success".equals(response.getStatus().getMessage())) {
            throw new Exception("Fail to call API");
        }
        Integer blankTrackCodeNum = response.getData();
        // 若空白字轨数量少于100，则发送邮件给相关人
        if (blankTrackCodeNum < 100) {
            // 发送邮件
            List<CodeValue> codeValues = codeValueMapper.getUserNameByCode();
            List<String> loginNames = new ArrayList<>();
            //BEGIN motify by xinjia.yao 2017/11/06
            for (CodeValue codeValue : codeValues) {
                String loginName = codeValue.getMeaning();
                String[] loginNameList = loginName.split(";");
                for (int i = 0, len = loginNameList.length; i < len; i++) {
                    loginNames.add(loginNameList[i].toString());
                }
            }
            //END motify by xinjia.yao 2017/11/06
            List<User> userinfos = userMapper.queryUserInfoByLoginName(loginNames);
            for (User userinfo : userinfos) {
                Map<String, Object> data = new HashMap<String, Object>();
                List<MessageReceiver> receiverlist = new ArrayList<MessageReceiver>();
                MessageReceiver messageReceiver = new MessageReceiver();
                data.put("blankTrackCodeNum", blankTrackCodeNum);
                data.put("userName", userinfo.getUserName());
                messageReceiver.setReceiverId(userinfo.getAccountId());
                messageReceiver.setMessageAddress(userinfo.getEmail());
                messageReceiver.setMessageType(ReceiverTypeEnum.NORMAL.getCode());
                receiverlist.add(messageReceiver);
                // 邮件发送账号需确认
                try {
                    messageService.sendEmailMessage(-1L, spmMarket.getMarketId(), "EMAIL_BLANK_TRACKCODE_NUM",
                            OrderConstants.EMAIL_ORDER_INVOICE_ACCOUNT, data, receiverlist, null);
                } catch (Exception e) {
                    if (logger.isErrorEnabled()) {
                        logger.error("send Email Error", e);
                    }
                    throw new Exception("send Email Error : " + e.getMessage());
                }
            }
        }
    }


    //updated by 11816 at 2017/12/26 11:00
    @Override
    public void monitorBlankTrackcode(String marketCode) throws Exception {
        SpmMarket spmMarket = spmMarketMapper.selectMarketByCode(marketCode);
//        InvoiceResponse<List<EinvoiceTrackcodeRule>> response = feignClient.connectInvoice().unsyncBlankTrackcode();
        InvoiceResponse<List<EinvoiceTrackcodeRule>> response = null;
        if (response == null || !"success".equals(response.getStatus().getMessage())) {
            throw new Exception("Fail to call API");
        }
        List<EinvoiceTrackcodeRule> blankTrackCodeInfos = response.getData();
        // 若存在未上传空白字轨，则发送邮件给相关人
        if (blankTrackCodeInfos != null && !blankTrackCodeInfos.isEmpty()) {
            for (EinvoiceTrackcodeRule blankTrackCodeInfo : blankTrackCodeInfos) {
                // 发送邮件
                List<CodeValue> codeValues = codeValueMapper.getUserNameByCode();
                List<String> loginNames = new ArrayList<>();
                //BEGIN motify by xinjia.yao 2017/11/06
                for (CodeValue codeValue : codeValues) {
                    String loginName = codeValue.getMeaning();
                    String[] loginNameList = loginName.split(";");
                    for (int i = 0, len = loginNameList.length; i < len; i++) {
                        loginNames.add(loginNameList[i].toString());
                    }
                }
                //END motify by xinjia.yao 2017/11/06
                List<User> userinfos = userMapper.queryUserInfoByLoginName(loginNames);
                for (User userinfo : userinfos) {
                    Map<String, Object> data = new HashMap<String, Object>();
                    List<MessageReceiver> receiverlist = new ArrayList<MessageReceiver>();
                    MessageReceiver messageReceiver = new MessageReceiver();
                    data.put("invoiceCateCode", blankTrackCodeInfo.getInvoiceCateCode());
                    data.put("ruleType", blankTrackCodeInfo.getRuleType());
                    data.put("invoiceCateMeaning", blankTrackCodeInfo.getInvoiceCateMeaning());
                    data.put("period", blankTrackCodeInfo.getPeriod());
                    data.put("prefix", blankTrackCodeInfo.getPrefix());
                    data.put("initNumber", blankTrackCodeInfo.getInitNumber());
                    data.put("maxNumber", blankTrackCodeInfo.getMaxNumber());
                    data.put("userName", userinfo.getUserName());
                    messageReceiver.setReceiverId(userinfo.getAccountId());
                    messageReceiver.setMessageAddress(userinfo.getEmail());
                    messageReceiver.setMessageType(ReceiverTypeEnum.NORMAL.getCode());
                    receiverlist.add(messageReceiver);
                    // 邮件发送账号需确认
                    try {
                        messageService.sendEmailMessage(-1L, spmMarket.getMarketId(), "EMAIL_UNSYNC_BLANK_TRACK_CODE",
                                OrderConstants.EMAIL_ORDER_INVOICE_ACCOUNT, data, receiverlist, null);
                    } catch (Exception e) {
                        if (logger.isErrorEnabled()) {
                            logger.error("send Email Error", e);
                        }
                        throw new Exception("send Email Error : " + e.getMessage());
                    }
                }
            }
        }
    }
}
