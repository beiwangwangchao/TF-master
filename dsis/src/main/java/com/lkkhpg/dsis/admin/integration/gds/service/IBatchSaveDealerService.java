/*
 *
 */
package com.lkkhpg.dsis.admin.integration.gds.service;

import java.util.List;
import java.util.Map;

import com.lkkhpg.dsis.admin.integration.gds.dto.IsgMemberInfoSync;
import com.lkkhpg.dsis.admin.integration.gds.dto.IsgMemberRelSync;
import com.lkkhpg.dsis.admin.integration.gds.dto.IsgMemberSync;
import com.lkkhpg.dsis.integration.gds.resource.dealers.batchSave.model.BatchSavePOSTBody;
import com.lkkhpg.dsis.integration.gds.resource.dealers.batchSave.model.BatchSavePOSTResponse;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 小批量同步会员资料.
 * 
 * @author linyuheng
 */
public interface IBatchSaveDealerService extends ProxySelf<IBatchSaveDealerService> {

    /**
     * 获取gDealers列表.
     * 
     * @param adviseNo
     *            通知号
     * @param orgCode
     *            组织参数
     * @return 小批量同步请求参数列表
     */
    List<BatchSavePOSTBody> getDealer(String adviseNo, String orgCode);

    /**
     * 更新会员表的同步标记和接口信息.
     * 
     * @param adviseNo
     *            通知号
     * @param requestData
     *            请求数据
     * @param response
     *            GDS返回数据
     * @param exception
     *            异常
     */
    void updateDealer(String adviseNo, List<BatchSavePOSTBody> requestData, List<BatchSavePOSTResponse> response,
            Exception exception);

    /**
     * 新开事务，插入接口表.
     * 
     * @param isgMemberSync
     *            会员资料同步DTO
     * @param isgMemberInfoSyncs
     *            会员个人信息数组
     * @param isgMemberRelSyncs
     *            会员相关人信息数组
     */
    void insertIsgMemberSyncInterface(IsgMemberSync isgMemberSync, List<IsgMemberInfoSync> isgMemberInfoSyncs,
            List<IsgMemberRelSync> isgMemberRelSyncs);

    /**
     * 更新会员表同步状态标记.
     * 
     * @param map
     *            字段映射
     */
    void updateSynFlag(Map<String, Object> map);

}
