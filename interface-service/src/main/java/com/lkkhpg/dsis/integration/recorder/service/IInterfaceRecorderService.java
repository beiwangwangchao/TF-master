/*
 *
 */

package com.lkkhpg.dsis.integration.recorder.service;

import java.util.Date;

import com.lkkhpg.dsis.integration.recorder.dto.InterfaceRecord;
import com.lkkhpg.dsis.integration.recorder.dto.ListenerRecord;

/**
 * 接口调用记录Service.
 * 
 * @author shengyang.zhou@hand-china.com
 */
public interface IInterfaceRecorderService {

    String SUCCESS = "success";
    String FAILED = "failed";

    /**
     * 记录成功的调用.
     * 
     * @param url
     *            接口地址
     * @param queryString
     *            url 参数
     * @param postBody
     *            post 数据
     * @param result
     *            返回结果
     * @param beginTime
     *            请求开始时间
     * @param elapse
     *            请求好事 ,毫秒
     */
    void recordSuccess(String url, String queryString, String postBody, String result, Date beginTime, long elapse);

    /**
     * 记录成功的调用.
     * 
     * @param url
     *            接口地址
     * @param queryString
     *            url 参数
     * @param postBody
     *            post 数据
     * @param result
     *            返回结果
     * @param throwable
     *            异常
     * @param beginTime
     *            请求开始时间
     * @param elapse
     *            请求好事 ,毫秒
     */
    void recordFail(String url, String queryString, String postBody, String result, Throwable throwable, Date beginTime,
            long elapse);

    /**
     * 保存交互记录请求参数.
     * 
     * @param record
     *            record
     * @return recordId 
     *            记录ID
     */
    Long insertRecordBody(InterfaceRecord record);

    /**
     * 记录监听器收到的请求.
     * 
     * @param record
     *            请求详细信息
     */
    void listenerRecord(ListenerRecord record);

    /**
     * 获取自定义返回数据.
     * 
     * @param methodName
     *            接口方法名
     * @return 预定义JSON数据
     */
    String getFakeData(String methodName);

    String getFakeRequestData(String methodName);
}
