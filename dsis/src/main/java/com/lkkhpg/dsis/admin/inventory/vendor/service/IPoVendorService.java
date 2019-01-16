/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.inventory.vendor.service;

import java.util.List;

import com.lkkhpg.dsis.admin.inventory.exception.InventoryException;
import com.lkkhpg.dsis.common.exception.CommSystemProfileException;
import com.lkkhpg.dsis.common.inventory.vendor.dto.PoVendor;
import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;

/**
 * 供应商管理接口.
 * 
 * @author liang.rao
 *
 */
public interface IPoVendorService {

    /**
     * 保存供应商信息.
     * 
     * @param request
     *            请求上下文.
     * @param vendor
     *            供应商信息.
     * @return 供应商信息.
     * @throws CommSystemProfileException
     *             系统配置统一异常.
     * @throws InventoryException
     *             库存统一异常.
     */
    PoVendor saveVendor(IRequest request, @StdWho PoVendor vendor)
            throws CommSystemProfileException, InventoryException;

    /**
     * 查询供应商信息.
     * 
     * @param request
     *            请求上下文.
     * @param vendor
     *            供应商信息.
     * @param page
     *            页.
     * @param pagesize
     *            分页大小.
     * @return 供应商信息集合.
     */
    List<PoVendor> queryVendor(IRequest request, PoVendor vendor, int page, int pagesize);

    /**
     * 失效供应商信息.
     * 
     * @param request
     *            请求上下文.
     * @param vendor
     *            供应商信息.
     * @throws InventoryException
     *             库存统一异常.
     */
    void deleteVendor(IRequest request, @StdWho PoVendor vendor) throws InventoryException;
}
