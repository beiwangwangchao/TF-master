/*
 *
 */

package com.lkkhpg.dsis.admin.inventory.service;

import java.text.ParseException;


/**
 * 批次预警接口.
 * 
 * @author liang.rao
 *
 */
public interface IAlertJobService {
    
    /**
     * 批次过期预警.
     * 
     * @throws ParseException String转Long异常.
     * @throws Exception 邮件接口可能抛出的异常.
     */
    void lotAlert() throws ParseException, Exception;
    
    /**
     * 库存不足预警.
     * 
     * @throws Exception 邮件接口可能抛出的异常.
     */
    void quantityAlert() throws Exception;
}
