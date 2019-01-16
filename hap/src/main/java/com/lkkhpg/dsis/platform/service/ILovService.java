/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.platform.service;

import java.util.List;
import java.util.Locale;

import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;
import com.lkkhpg.dsis.platform.dto.BaseDTO;
import com.lkkhpg.dsis.platform.dto.system.Lov;
import com.lkkhpg.dsis.platform.dto.system.LovItem;

/**
 * 通用lov的service.
 * 
 * @author njq.niu@hand-china.com
 *
 *         2016年1月31日
 */
public interface ILovService extends ProxySelf<ILovService> {

    /**
     * 批量更新lov配置.
     * 
     * @param request
     *            session信息
     * @param lovs
     *            lov参数
     * @return 更新结果
     */
    List<Lov> batchUpdate(IRequest request, @StdWho List<Lov> lovs);

    /**
     * 删除lov配置.
     * 
     * @param items
     *            删除参数
     * @return 删除结果
     */
    boolean batchDeleteLov(List<Lov> items);

    /**
     * 批量删除item.
     * 
     * @param items
     *            参数
     * @return 结果
     */
    boolean batchDeleteItems(List<LovItem> items);

    /**
     * 删除LovItem.
     * 
     * @param item
     *            参数
     * @return 结果
     */
    boolean deleteLovItem(LovItem item);

    /**
     * 加载lov配置.
     * 
     * @param lovId
     *            lovId
     * @return LOV
     */
    Lov loadLov(Long lovId);

    /**
     * 创建lov配置.
     * 
     * @param lov
     *            lov
     * @return LOV
     * 
     * 
     */
    Lov createLov(Lov lov);

    /**
     * 更新lov配置.
     * 
     * @param lov
     *            lov
     * @return LOV
     */
    Lov updateLov(Lov lov);

    /**
     * 查询lov配置.
     * 
     * @param request
     *            session
     * @param lov
     *            lov
     * @param page
     *            页码
     * @param pagesize
     *            数量
     * @return lov列表
     */
    List<Lov> selectLovs(IRequest request, Lov lov, int page, int pagesize);

    /**
     * 查询LovItem配置.
     * 
     * @param request
     *            sessio
     * @param lovItem
     *            lov配置
     * @return lov配置列表
     */
    List<LovItem> selectLovItems(IRequest request, LovItem lovItem);

    /**
     * 根据lovCode获取lov的配置.
     * 
     * @param contextPath
     *            contextPath
     * @param locale
     *            locale
     * @param lovCode
     *            lovCode
     * @return lov配置
     */
    String getLov(String contextPath, Locale locale, String lovCode);

    /**
     * 通用lov获取数据.
     * 
     * @param request
     *            session
     * @param code
     *            code
     * @param obj
     *            obj
     * @param page
     *            页码
     * @param pagesize
     *            数量
     * @return DTO数据
     */
    List<? extends BaseDTO> selectDatas(IRequest request, String code, Object obj, int page, int pagesize);
}
