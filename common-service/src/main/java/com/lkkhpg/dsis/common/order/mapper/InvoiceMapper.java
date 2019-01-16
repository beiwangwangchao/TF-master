/*
 *
 */
package com.lkkhpg.dsis.common.order.mapper;

import java.util.List;
import java.util.Map;

import com.lkkhpg.dsis.common.order.dto.Invoice;

/**
 * . 发票接口
 * 
 * @author pengli
 *
 */
public interface InvoiceMapper {
    int insertInvoice(Invoice invoice);

    int updateInvoice(Invoice invoice);

    List<Invoice> selectInvoice(Invoice invoice);

    int selectInvoiceCount(Invoice invoice);

    /**
     * 根据发票部分条件查询.
     * 
     * @param invoice
     *            查询条件dto.
     * @return 返回符合条件的集合.
     */
    List<Invoice> selectByInvoice(Invoice invoice);

    Map<String, String> selectInvoiceReport(Long marketId);

    /**
     * 通过订单Id 查找发票信息.
     * 
     * @param orderId
     *            订单ID
     * @return 发票信息
     */
    Invoice selectInvoiceByOrderId(Long orderId);
}
