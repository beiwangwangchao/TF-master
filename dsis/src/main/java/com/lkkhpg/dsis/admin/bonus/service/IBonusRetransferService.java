/*
 *
 */
package com.lkkhpg.dsis.admin.bonus.service;

import java.util.List;

import com.lkkhpg.dsis.common.bonus.dto.BonusRetransfer;
import com.lkkhpg.dsis.platform.core.IRequest;

/**
 * re_transfer 接口.
 * 
 * @author li.peng@hand-china.com
 *
 */
public interface IBonusRetransferService {

    /**
     * 查询re_transfer.
     * 
     * @param request
     *            上下文请求
     * @param retransferOrder 
     * @param BonusRetransfer
     *            re_transfer查询条件
     * @param page
     *            页码
     * @param pagesize
     *            页数
     * @return re_transfer列表
     */
    List<BonusRetransfer> queryRetransfer(IRequest request, BonusRetransfer retransferOrder, int page, int pagesize);
    
    /**
     * 插入re_transfer记录.
     * 
     * @param request
     *            统一上下文.
     * @param retransfer
     *            插入记录.
     * @return 返回影响行数.
     */
    int insertRetransfer(IRequest request, BonusRetransfer retransfer);
}

