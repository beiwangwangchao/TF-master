/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.integration.gds.service;

import java.util.List;

import com.lkkhpg.dsis.integration.gds.resource.dealers.foreignSponsors.model.ForeignSponsorsGETResponse;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 小批量下载海外推荐人资料service接口.
 * 
 * @author gulin
 */
public interface IFindForeignSponsorsService extends ProxySelf<IFindForeignSponsorsService>{

    /**
     * 接受海外推荐人资料插入海外推荐人临时表.
     * 
     * @param adviseNo
     *            通知号.
     * @param orgCode
     *            所属机构code.
     * @param response
     *            接收到的海外推荐人资料集合
     * @param exception
     *            异常
     */
    void insertSponsors(String adviseNo, String orgCode, List<ForeignSponsorsGETResponse> response,
            Exception exception);

}
