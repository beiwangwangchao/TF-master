/*
 *
 */
package com.lkkhpg.dsis.admin.config.service;

import java.util.List;

import com.lkkhpg.dsis.common.config.dto.SpmTax;
import com.lkkhpg.dsis.common.exception.CommSystemProfileException;
import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;

/**
 * 地址Service接口.
 * 
 * @author frank.li
 */
public interface ISpmTaxService {

    /**
     * 保存税率.
     * 
     * @param request
     *            请求上下文
     * @param taxes
     *            税率List
     * @return 税率List
     * @throws CommSystemProfileException
     */
    List<SpmTax> saveTax(IRequest request, @StdWho List<SpmTax> taxes) throws CommSystemProfileException;

    /**
     * 删除税率.
     * 
     * @param request
     *            请求上下文
     * @param taxes
     *            税率List
     * @return boolean
     */
    boolean deleteTax(IRequest request, List<SpmTax> taxes);

    /**
     * 查询税率.
     * 
     * @param request
     *            请求上下文
     * @param tax
     *            税率DTO
     * @param page
     *            页
     * @param pagesize
     *            页数
     * @return 税率List
     */
    List<SpmTax> queryTax(IRequest request, SpmTax tax, int page, int pagesize);

    /**
     * 查询税率.
     * 
     * @param request
     *            请求上下文
     * @param tax
     *            税率DTO
     * @return 税率List
     */
    List<SpmTax> queryForOrder(IRequest request, SpmTax tax);
}
