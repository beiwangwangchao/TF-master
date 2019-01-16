/*
 *
 */
package com.lkkhpg.dsis.admin.inventory.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lkkhpg.dsis.admin.inventory.service.IAlertJobService;
import com.lkkhpg.dsis.admin.system.DsisServiceRequest;
import com.lkkhpg.dsis.common.constant.InventoryConstants;
import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.common.inventory.dto.InvLot;
import com.lkkhpg.dsis.common.inventory.dto.ItemAlert;
import com.lkkhpg.dsis.common.inventory.mapper.AlertJobMapper;
import com.lkkhpg.dsis.common.inventory.mapper.InvLotMapper;
import com.lkkhpg.dsis.common.service.IManualMessageService;
import com.lkkhpg.dsis.common.service.IParamService;
import com.lkkhpg.dsis.common.system.dto.SysMsMessage;
import com.lkkhpg.dsis.common.system.dto.SysMsMessageAssign;
import com.lkkhpg.dsis.platform.dto.system.MessageReceiver;
import com.lkkhpg.dsis.platform.dto.system.User;
import com.lkkhpg.dsis.platform.mail.MessageTypeEnum;
import com.lkkhpg.dsis.platform.mail.ReceiverTypeEnum;
import com.lkkhpg.dsis.platform.mapper.system.UserMapper;
import com.lkkhpg.dsis.platform.service.IMessageService;

/**
 * 批次预警实现类.
 * 
 * @author liang.rao
 *
 */
@Service
public class LotAlertJobServiceImpl implements IAlertJobService {

    private Logger logger = LoggerFactory.getLogger(LotAlertJobServiceImpl.class);

    @Autowired
    private AlertJobMapper alertjobMapper;
    @Autowired
    private InvLotMapper invlotMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private IMessageService messageService;
    @Autowired
    private IParamService paramService;
    @Autowired
    private IManualMessageService manualMessageService;

    private static final Long MILL = 1000L;

    private static final Long HOUR = 24L;

    private static final Long MIN = 60L;

    @Override
    public void lotAlert() throws ParseException, Exception {
        Map<Long, List<Object>> alertMessageMap = new HashMap<Long, List<Object>>();
        List<ItemAlert> itemAlerts = alertjobMapper.queryLotAlert();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date nowDate = sdf.parse(sdf.format(new Date()));
        for (ItemAlert itemAlert : itemAlerts) {
            if (SystemProfileConstants.YES.equals(itemAlert.getEnabledFlag()) && itemAlert.getExpiryAlert() != null
                    && !"".equals(itemAlert.getExpiryAlert()) && itemAlert.getOrgId() != null) {
                // 失效时间
                Date expiry = sdf.parse(sdf.format(itemAlert.getExpiryDate()));
                // 预警时间
                Long expiryAlert = Long.parseLong(itemAlert.getExpiryAlert());
                Long between = (expiry.getTime() - nowDate.getTime()) / (HOUR * MIN * MIN * MILL);
                if (between <= 0) {
                    InvLot invlot = new InvLot();
                    invlot.setOrganizationId(itemAlert.getOrgId());
                    invlot.setItemId(itemAlert.getItemId());
                    invlot.setEnabledFlag(SystemProfileConstants.NO);
                    invlot.setLotNumber(itemAlert.getLotNumber());
                    invlotMapper.updateInvLot(invlot);
                }
                if (between <= expiryAlert) {
                    if (alertMessageMap.containsKey(itemAlert.getOrgId())) {
                        alertMessageMap.get(itemAlert.getOrgId()).add(itemAlert);
                        alertMessageMap.put(itemAlert.getOrgId(), alertMessageMap.get(itemAlert.getOrgId()));
                    } else {
                        List<Object> itemList = new ArrayList<Object>();
                        itemList.add(itemAlert);
                        alertMessageMap.put(itemAlert.getOrgId(), itemList);
                    }
                }
            }
        }
        if (!alertMessageMap.isEmpty()) {
            // 发送站内信和邮件
            Long orgId;
            List<User> users;
            Iterator<Long> it = alertMessageMap.keySet().iterator();
            while (it.hasNext()) {
                orgId = it.next();
                users = getMessageReceiver(orgId);
                if (users == null || users.isEmpty()) {
                    break;
                }
                if (sendMailMessage(users, alertMessageMap.get(orgId), "LOT")) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("Execute Lot Alert Job Ended!");
                    }
                }
                if (sendEmailMessage(users, alertMessageMap.get(orgId), "LOT")) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("Execute Lot Alert Job Ended!");
                    }
                }
            }
        }
    }

    @Override
    public void quantityAlert() throws Exception {
        Map<Long, List<Object>> alertMessageMap = new HashMap<Long, List<Object>>();
        List<ItemAlert> itemAlerts = alertjobMapper.queryQuantityAlert();
        for (ItemAlert itemAlert : itemAlerts) {
            if (SystemProfileConstants.YES.equals(itemAlert.getEnabledFlag()) && itemAlert.getQuantityAlert() != null
                    && itemAlert.getOrgId() != null) {
                if (itemAlert.getQuantity() != null) {
                    // 库存量
                    Long quantity = itemAlert.getQuantity().longValue();
                    // 预警数量
                    Long quantityAlert = itemAlert.getQuantityAlert();
                    if (quantity <= quantityAlert) {
                        if (alertMessageMap.containsKey(itemAlert.getOrgId())) {
                            alertMessageMap.get(itemAlert.getOrgId()).add(itemAlert);
                            alertMessageMap.put(itemAlert.getOrgId(), alertMessageMap.get(itemAlert.getOrgId()));
                        } else {
                            List<Object> itemList = new ArrayList<Object>();
                            itemList.add(itemAlert);
                            alertMessageMap.put(itemAlert.getOrgId(), itemList);
                        }
                    }
                }
            }
        }
        if (!alertMessageMap.isEmpty()) {
            // 发送站内信和邮件
            Long orgId;
            List<User> users;
            Iterator<Long> it = alertMessageMap.keySet().iterator();
            while (it.hasNext()) {
                orgId = it.next();
                users = getMessageReceiver(orgId);
                if (users.isEmpty()) {
                    break;
                }
                if (sendMailMessage(users, alertMessageMap.get(orgId), "INV")) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("Execute Stock Quantity Alert Job Ended!");
                    }
                }
                if (sendEmailMessage(users, alertMessageMap.get(orgId), "INV")) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("Execute Stock Quantity Alert Job Ended!");
                    }
                }
            }
        }
    }

    /**
     * 获取收件人信息.
     * 
     * @param orgId
     *            库存组织ID
     * @return 收件人信息(null-说明没有找到对应user信息)
     */
    private List<User> getMessageReceiver(Long orgId) {
        DsisServiceRequest request = new DsisServiceRequest();
        List<User> users = new ArrayList<User>();
        // 组织参数中维护的通知仓管员
        List<String> lists = paramService.getParamValues(request, InventoryConstants.SPM_NOTED_WAREHOUSEMAN,
                SystemProfileConstants.ORG_TYPE_INV, orgId);
        if (lists == null || lists.isEmpty()) {
            if (logger.isDebugEnabled()) {
                logger.debug("The ParameterName[SPM.NOTED_WAREHOUSEMAN] value not found! salesOrgId : {}", orgId);
            }
            return null;
        }
        Iterator<String> it = lists.iterator();
        User user = new User();
        while (it.hasNext()) {
            user.setUserId(Long.parseLong(it.next()));
            List<User> userList = userMapper.selectUsers(user);
            if (userList == null || userList.isEmpty() || userList.get(0).getAccountId() == null) {
                if (logger.isDebugEnabled()) {
                    logger.debug("The UserId or AccountId not exist! UserId : {}", new Object[] { user.getUserId() });
                }
                break;
            }
            users.add(userList.get(0));
        }
        return users;
    }

    /**
     * 发送站内信.
     *
     * @param users
     *            收件人信息
     * @param itemList
     *            预警商品信息
     * @param type
     *            INV-库存量预警；LOT-批次预警
     * @return true-发送成功；false-发送失败
     * @throws Exception
     *             统一异常
     */
    private boolean sendMailMessage(List<User> users, List<Object> itemList, String type) throws Exception {
        List<SysMsMessageAssign> sysMsMessageAssigns = new ArrayList<SysMsMessageAssign>();
        SysMsMessageAssign sysMsMessageAssign = new SysMsMessageAssign();
        Map<String, Object> data = new HashMap<String, Object>();
        // 组装站内信模板
        SysMsMessage sysMsMessage = new SysMsMessage();
        sysMsMessage.setPublishChannel(String.valueOf(MessageTypeEnum.DSIS));
        sysMsMessage.setSenderCode("-1");
        sysMsMessage.setSender(1L); // admin用户
        Iterator<User> it = users.iterator();
        while (it.hasNext()) {
            User user = it.next();
            sysMsMessageAssign.setAssignType(SysMsMessageAssign.USER);
            sysMsMessageAssign.setAssignValue(String.valueOf(user.getUserId()));
            sysMsMessageAssign.setAccountId(user.getAccountId());
            sysMsMessageAssigns.add(sysMsMessageAssign);
            data.put("loginName", user.getLoginName());
            data.put("itemAlert", itemList);
            if ("LOT".equals(type)) {
                manualMessageService.publishMessage(sysMsMessage, sysMsMessageAssigns,
                        InventoryConstants.LOT_MAIL_MOULD, data, null);
            } else if ("INV".equals(type)) {
                manualMessageService.publishMessage(sysMsMessage, sysMsMessageAssigns,
                        InventoryConstants.QUANTITY_MAIL_MOULD, data, null);
            }
        }

        return true;
    }

    /**
     * 发送邮件.
     * 
     * @param users
     *            收件人信息
     * @param itemList
     *            预警商品信息
     * @param type
     *            INV-库存量预警；LOT-批次预警
     * @return true-发送成功；false-发送失败
     * @throws Exception
     *             统一异常
     */
    private boolean sendEmailMessage(List<User> users, List<Object> itemList, String type) throws Exception {
        // 获取收件人
        MessageReceiver receiver = new MessageReceiver();
        List<MessageReceiver> receiverlist = new ArrayList<MessageReceiver>();
        // 设置邮件模板里的数据
        Map<String, Object> data = new HashMap<String, Object>();
        Iterator<User> it = users.iterator();
        while (it.hasNext()) {
            User user = it.next();
            receiver.setMessageAddress(user.getEmail());
            receiver.setMessageType(ReceiverTypeEnum.NORMAL.getCode());
            receiver.setReceiverId(user.getUserId());
            receiverlist.add(receiver);
            data.put("loginName", users.get(0).getLoginName());
            data.put("itemAlert", itemList);
            if ("LOT".equals(type)) {
                messageService.sendEmailMessage(-1L, null, InventoryConstants.LOT_MAIL_MOULD,
                        InventoryConstants.SPM_ALERT_EMAIL_ACCOUNT, data, receiverlist, null);
            } else if ("INV".equals(type)) {
                messageService.sendEmailMessage(-1L, null, InventoryConstants.QUANTITY_MAIL_MOULD,
                        InventoryConstants.SPM_ALERT_EMAIL_ACCOUNT, data, receiverlist, null);
            }
        }

        return true;
    }

}
