/*
 *
 */

package com.lkkhpg.dsis.integration.dapp.dto;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author shengyang.zhou@hand-china.com
 */
public class DAppCallbackBody {

    private String clientCallbackID;
    private String jobType;

    private MessageBody messageBody = new MessageBody();

    /**
     * auto generate and set clientCallbackID.
     * 
     * @return the id generated
     */
    @JsonIgnore
    public String generateClientCallbackID() {
        String id = UUID.randomUUID().toString();
        setClientCallbackID(id);
        return id;
    }

    public String getClientCallbackID() {
        return clientCallbackID;
    }

    public void setClientCallbackID(String clientCallbackID) {
        this.clientCallbackID = clientCallbackID;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public MessageBody getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(MessageBody messageBody) {
        this.messageBody = messageBody;
    }

    public static class MessageBody {
        private String action;
        private String source = "POS";
        private Object memberInfo;

        private Object orderInfo;

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public Object getMemberInfo() {
            return memberInfo;
        }

        public void setMemberInfo(Object memberInfo) {
            this.memberInfo = memberInfo;
        }

        public Object getOrderInfo() {
            return orderInfo;
        }

        public void setOrderInfo(Object orderInfo) {
            this.orderInfo = orderInfo;
        }
    }
}
