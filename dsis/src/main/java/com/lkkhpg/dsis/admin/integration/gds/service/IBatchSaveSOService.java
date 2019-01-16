/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.integration.gds.service;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.cglib.beans.BeanCopier;

import com.lkkhpg.dsis.common.order.dto.SalesOrder;
import com.lkkhpg.dsis.integration.gds.resource.orders.batchSave.model.BatchSavePOSTBody;
import com.lkkhpg.dsis.integration.gds.resource.orders.batchSave.model.BatchSavePOSTResponse;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 批量同步销售资料.
 * 
 * @author wuyichu
 */
public interface IBatchSaveSOService extends ProxySelf<IBatchSaveSOService> {

    /**
     * 获取待同步的销售资料列表.
     * 
     * @param adviseNo
     *            通知号
     * @param orgCode
     *            组织代码
     * @return 待同步的销售资料结果集
     */
    List<BatchSavePOSTBody> getOrders(String adviseNo, String orgCode);

    /**
     * 小批量同步销售资料状态回写.
     * 
     * @param adviseNo
     *            GDS調度時傳遞的號碼
     * @param requestData
     *            发送的数据
     * @param response
     *            收到的数据
     * @param exception
     *            调用时发生的异常.null表示无异常
     */
    void updateOrders(String adviseNo, List<BatchSavePOSTBody> requestData, List<BatchSavePOSTResponse> response,
            Exception exception);

    /**
     * 修改销售订单同步状态.
     * 
     * @param salesOrder
     *            销售订单.
     */
    void updateSalesOrderSyncStaus(SalesOrder salesOrder);

    /**
     * 获取退货单.
     * 
     * @param adviseNo
     *            通知号
     * @param gdsOrgCode
     *            gds机构代码
     * @param result
     *            gds的body
     * @param commonDateFormat
     *            通用日期格式转换器
     * @param headerCopy
     *            订单头拷贝类
     * @param lineCopy
     *            订单行拷贝类
     */
    void getReturnOrder(String adviseNo, String gdsOrgCode, List<BatchSavePOSTBody> result,
            SimpleDateFormat commonDateFormat, BeanCopier headerCopy, BeanCopier lineCopy);
}
