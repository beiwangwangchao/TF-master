/*
 *
 */

package com.lkkhpg.dsis.integration.recorder.service.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lkkhpg.dsis.integration.recorder.dto.InterfaceRecord;
import com.lkkhpg.dsis.integration.recorder.dto.ListenerRecord;
import com.lkkhpg.dsis.integration.recorder.mapper.InterfaceRecordMapper;
import com.lkkhpg.dsis.integration.recorder.mapper.ListenerRecordMapper;
import com.lkkhpg.dsis.integration.recorder.service.IInterfaceRecorderService;

/**
 * 接口调用记录Service实现.
 * 
 * @author shengyang.zhou@hand-china.com
 */
@Service
public class InterfaceRecorderServiceImpl implements IInterfaceRecorderService {

    @Autowired
    private InterfaceRecordMapper interfaceRecordMapper;

    @Autowired
    private ListenerRecordMapper listenerRecordMapper;

    @Transactional(propagation = Propagation.REQUIRES_NEW, noRollbackFor = Exception.class)
    @Override
    public void recordSuccess(String url, String queryString, String postBody, String result, Date beginTime,
            long elapse) {
        InterfaceRecord record = new InterfaceRecord();
        record.setUrl(url);
        record.setQueryString(queryString);
        record.setBody(postBody);
        record.setResult(result);
        record.setSuccess(SUCCESS);
        record.setRequestDate(beginTime);
        record.setElapse(elapse);
        interfaceRecordMapper.insert(record);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, noRollbackFor = Exception.class)
    @Override
    public void recordFail(String url, String queryString, String postBody, String result, Throwable throwable,
            Date beginTime, long elapse) {
        InterfaceRecord record = new InterfaceRecord();
        record.setUrl(url);
        record.setQueryString(queryString);
        record.setBody(postBody);
        record.setResult(result);
        record.setSuccess(FAILED);
        record.setException(rootStackTrace(throwable));
        record.setRequestDate(beginTime);
        record.setElapse(elapse);
        interfaceRecordMapper.insert(record);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, noRollbackFor = Exception.class)
    @Override
    public Long insertRecordBody(InterfaceRecord record) {
        interfaceRecordMapper.insert(record);
        return record.getRecordId();
    }

    @Override
    @Transactional(noRollbackFor = Exception.class)
    public void listenerRecord(ListenerRecord record) {
        listenerRecordMapper.insert(record);
        MDC.put("listenerRequestId", String.valueOf(record.getRequestId()));
    }

    @Override
    public String getFakeData(String methodName) {
        return interfaceRecordMapper.getFakeData(methodName);
    }

    @Override
    public String getFakeRequestData(String methodName) {
        return interfaceRecordMapper.getFakeRequestData(methodName);
    }

    private String rootStackTrace(Throwable throwable) {
        if (throwable == null) {
            return null;
        }
        while (throwable.getCause() != null) {
            throwable = throwable.getCause();
        }
        StringWriter stringWriter = new StringWriter();
        throwable.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }
}
