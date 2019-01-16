/*
 *
 */

package com.lkkhpg.dsis.integration.gds.service;

import java.util.List;

import com.lkkhpg.dsis.integration.gds.resource.dealers.batchDelete.model.BatchDeletePOSTBody;
import com.lkkhpg.dsis.integration.gds.resource.dealers.batchDelete.model.BatchDeletePOSTResponse;
import com.lkkhpg.dsis.integration.gds.resource.dealers.dealerNo.model.DealerNoPUTBody;
import com.lkkhpg.dsis.integration.gds.resource.dealers.dealerNo.model.DealerNoPUTResponse;
import com.lkkhpg.dsis.integration.gds.resource.dealers.dealerNo.sponsorVerify.model.SponsorVerifyPOSTResponse;
import com.lkkhpg.dsis.integration.gds.resource.dealers.model.DealersPOSTBody;
import com.lkkhpg.dsis.integration.gds.resource.dealers.model.DealersPOSTResponse;
import com.lkkhpg.dsis.integration.gds.resource.gtreeAlters.applications.appNo.status.model.StatusGETResponse;
import com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferIn.applications.appID.audit.model.AuditPOSTBody;
import com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferIn.applications.appID.audit.model
        .AuditPOSTResponse;
import com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferOut.applications.appID.model.AppIDDELETEBody;
import com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferOut.applications.appID.model.AppIDDELETEResponse;
import com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferOut.applications.model.ApplicationsPOSTBody;
import com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferOut.applications.model.ApplicationsPOSTResponse;
import com.lkkhpg.dsis.integration.gds.resource.orgList.model.OrgListGETResponse;
import com.lkkhpg.dsis.integration.gds.resource.periods.close.model.ClosePOSTResponse;

/**
 * Auto Generated Code.
 * 
 * @author shengyang.zhou@hand-china.com
 *
 *
 */
public interface IGdsService {

    /**
     * 日/月 差異結果查詢.
     * <p>
     * 在查詢異常處理結果時調用該方法,根據組織代碼從 UPL_CHECK_RESULT 中查詢出最近 3 天前 1000
     * 條差異讀取狀態為"N"的差異結果集,並且將 UPL_CHECK_RESULT 的"讀取標誌"設為"Y"
     * <p>
     * uri:/checkResults <br>
     * method:GET
     * 
     * @param adviseNo
     *            GDS調度時傳遞的號碼
     * @param orgCode
     *            調用GDS传递的OrgCode
     *
     *
     */
    void queryCheckResult(String adviseNo, String orgCode);

    /**
     * 關閉機構月份.
     * <p>
     * 根據月份和機構代號從 PERIOD_CTL_DETAIL 查詢出月份區間控制明細資訊記錄,如果該記錄為空,則返回包含失敗資訊的
     * GdsReturnMessage,否則繼續設置該記錄物件的機構關閉標誌為Y,保存該記錄物件
     * <p>
     * uri:/periods/close <br>
     * method:POST
     * 
     * @param orgCode
     *            調用GDS传递的OrgCode
     *
     *
     */
    ClosePOSTResponse closePeriod(String orgCode, String period);

    /**
     * 即時會員資料保存.
     * <p>
     * POS 新增會員、更改重要資料、移線、資格狀態變更(非中止資格)等操作,引起資料變化時調用該介面即時自動更新 GDS系統的會員資料庫。GDS
     * 將DS/POS上傳的會員資料 gDealer 更新到資料表 GDEALER、 GDEALER_PERSONAL_INFO 及
     * GDEALER_FAMILY_INFO 中,同時判斷身份狀態是否有改變,有改變則根據規則生 成 GDEALER_TYPE_STATUS
     * 記錄。如果資料變化是從非普通消費者變到普通消費者(如資格中止),則通過資格中 止介面處理。
     * <p>
     * uri:/dealers <br>
     * method:POST
     * 
     * @param orgCode
     *            調用GDS传递的OrgCode
     *
     *
     */
    DealersPOSTResponse saveDealer(String orgCode, DealersPOSTBody body);

    /**
     * 小批量同步會員資料.
     * <p>
     * 對於會員的新增、修改、刪除,可支援通過後臺批量同步的方式上傳到 GDS
     * <p>
     * uri:/dealers/batchSave <br>
     * method:POST
     * 
     * @param adviseNo
     *            GDS調度時傳遞的號碼
     * @param orgCode
     *            調用GDS传递的OrgCode
     *
     *
     */
    void batchSaveDealer(String adviseNo, String orgCode);

    /**
     * 刪除會員資料.
     * <p>
     * 主动调用
     * <p>
     * GDS每晚HealthCheck將GDS比DS/POS多出的會員資料記錄，保存在UPL_CHECK_RESULT表中，GDS定時發起刪除批令，
     * DS調用，GDS做刪除處理，刪除確認的會員在SGDEALER、GDEALER_PERSONAL_INFO、
     * GDEALER_FAMILY_INFO及GDEALER_TYPE_STATUS中對應的記錄
     * <p>
     * uri:/dealers/batchDelete <br>
     * method:POST
     * 
     * @param adviseNo
     *            GDS調度時傳遞的號碼
     * @param orgCode
     *            調用GDS传递的OrgCode
     *
     *
     */
    List<BatchDeletePOSTResponse> deleteDealer(String adviseNo, String orgCode, List<BatchDeletePOSTBody> body);

    /**
     * 刪除會員資料.
     * <p>
     * 被动调用.
     * <p>
     * GDS每晚HealthCheck將GDS比DS/POS多出的會員資料記錄，保存在UPL_CHECK_RESULT表中，GDS定時發起刪除批令，
     * DS調用，GDS做刪除處理，刪除確認的會員在SGDEALER、GDEALER_PERSONAL_INFO、
     * GDEALER_FAMILY_INFO及GDEALER_TYPE_STATUS中對應的記錄
     * <p>
     * uri:/dealers/batchDelete <br>
     * method:POST
     * 
     * @param adviseNo
     *            GDS調度時傳遞的號碼
     * @param orgCode
     *            調用GDS传递的OrgCode
     *
     *
     */
    void deleteDealer(String adviseNo, String orgCode);

    /**
     * 小批量下載海外推薦人資料.
     * <p>
     * 在查詢海外推薦人時調用該介面方法 從 Gdealer 查詢出所屬機構代號為參數的全部會員號集合,從中排除一般會員即為海外推薦人
     * <p>
     * uri:/dealers/foreignSponsors <br>
     * method:GET
     * 
     * @param adviseNo
     *            GDS調度時傳遞的號碼
     * @param orgCode
     *            調用GDS传递的OrgCode
     *
     *
     */
    void findForeignSponsors(String adviseNo, String orgCode);

    /**
     * 即時會員資料保存.
     * <p>
     * 當POS系統成功新增會員後，把會員資料即時上傳到GDS系統。
     * <p>
     * uri:/dealers/{dealerNo} <br>
     * method:PUT
     * 
     * @param dealerNo
     *            {dealerNo}
     * @param orgCode
     *            調用GDS传递的OrgCode
     *
     *
     */
    DealerNoPUTResponse saveDealer(String dealerNo, String orgCode, DealerNoPUTBody body);

    /**
     * 推薦人即時鑒別.
     * <p>
     * 推薦人即時鑒別 (由 POS 系統的會員申請介面發起介面查詢請求,連接到 GDS 即時查詢推薦人資料。)
     * <p>
     * uri:/dealers/{dealerNo}/sponsorVerify <br>
     * method:POST
     * 
     * @param dealerNo
     *            {dealerNo}
     * @param orgCode
     *            調用GDS传递的OrgCode
     *
     *
     */
    SponsorVerifyPOSTResponse sponsorVerify(String dealerNo, String orgCode);

    /**
     * 下載移線會員資料.
     * <p>
     * uri:/gtreeAlters/moveLine/results <br>
     * method:GET
     * 
     * @param adviseNo
     *            GDS調度時傳遞的號碼
     * @param orgCode
     *            調用GDS传递的OrgCode
     *
     *
     */
    void findMoveSponsor(String adviseNo, String orgCode);

    /**
     * 移線處理通知 GDS.
     * <p>
     * uri:/gtreeAlters/moveLine/notify <br>
     * method:POST
     * 
     * @param adviseNo
     *            GDS調度時傳遞的號碼
     * @param orgCode
     *            調用GDS传递的OrgCode
     *
     *
     */
    com.lkkhpg.dsis.integration.gds.resource.gtreeAlters.moveLine.notify.model.NotifyPOSTResponse treeAlterPrecess(
            String adviseNo, String orgCode,
            com.lkkhpg.dsis.integration.gds.resource.gtreeAlters.moveLine.notify.model.NotifyPOSTBody body);

    /**
     * 會員移線申請.
     * <p>
     * POS 系統處理會員移線資料後,即時自動更新 GDS 系統的會員資料庫。同時也須具備有批次整批傳送的功能介面,
     * 可過濾特定會員編號起迄區間的資料再上傳
     * <p>
     * uri:/gtreeAlters/moveLine/applications/{appNo} <br>
     * method:PUT
     * 
     * @param appNo
     *            {appNo}
     * @param orgCode
     *            調用GDS传递的OrgCode
     *
     *
     */
    com.lkkhpg.dsis.integration.gds.resource.gtreeAlters.moveLine.applications.appNo.model.AppNoPUTResponse applyMoveLine(
            String appNo, String orgCode,
            com.lkkhpg.dsis.integration.gds.resource.gtreeAlters.moveLine.applications.appNo.model.AppNoPUTBody body);

    /**
     * 下載批刪會員資料.
     * <p>
     * uri:/gtreeAlters/terminate/results <br>
     * method:GET
     * 
     * @param adviseNo
     *            GDS調度時傳遞的號碼
     * @param orgCode
     *            調用GDS传递的OrgCode
     *
     *
     */
    void findExpelDealer(String adviseNo, String orgCode);

    /**
     * 批刪處理通知 GDS.
     * <p>
     * uri:/gtreeAlters/terminate/notify <br>
     * method:POST
     * 
     * @param adviseNo
     *            GDS調度時傳遞的號碼
     * @param orgCode
     *            調用GDS传递的OrgCode
     *
     *
     */
    com.lkkhpg.dsis.integration.gds.resource.gtreeAlters.terminate.notify.model.NotifyPOSTResponse treeAlterPrecess(
            String adviseNo, String orgCode,
            com.lkkhpg.dsis.integration.gds.resource.gtreeAlters.terminate.notify.model.NotifyPOSTBody body);

    /**
     * 會員申請資格中止.
     * <p>
     * uri:/gtreeAlters/terminate/applications/{appNo} <br>
     * method:PUT
     * 
     * @param appNo
     *            {appNo}
     * @param orgCode
     *            調用GDS传递的OrgCode
     *
     *
     */
    com.lkkhpg.dsis.integration.gds.resource.gtreeAlters.terminate.applications.appNo.model.AppNoPUTResponse appEligibleSuspend(
            String appNo, String orgCode,
            com.lkkhpg.dsis.integration.gds.resource.gtreeAlters.terminate.applications.appNo.model.AppNoPUTBody body);

    /**
     * 會員移線、停權申請狀態查詢.
     * <p>
     * uri:/gtreeAlters/applications/{appNo}/status <br>
     * method:GET
     * 
     * @param appNo
     *            {appNo}
     * @param orgCode
     *            調用GDS传递的OrgCode
     *
     *
     */
    StatusGETResponse applyStatusQuery(String appNo, String orgCode);

    /**
     * 小批量同步銷售資料.
     * <p>
     * 在批量同步銷售資料時調用該介面方法在每天固定時間 GDS 向 DS/POS 發出指令,通知 DS/POS
     * 將需要未同步的銷售資料(包括新增、修改、刪除[寫刪除標 誌]的銷售單)上傳到 GDS,DS/POS 接指令後掃描未同步的銷售資料,調用 GDS
     * 的該介面上傳資料,並根據返回的結 果更新同步標誌,如果有物理刪除的記錄,晚上健康檢查出來後,找出多出的會員記錄,DS/POS 根據 GDS
     * 的調度指 令,從 GDS 獲取差異結果,對於 GDS 多出記錄的差異結果,DS/POS 自動檢索在本系統中是否存在該記錄,沒有則向 GDS
     * 確認,GDS 從庫中刪除相應記錄。
     * <p>
     * uri:/orders/batchSave <br>
     * method:POST
     * 
     * @param adviseNo
     *            GDS調度時傳遞的號碼
     * @param orgCode
     *            調用GDS传递的OrgCode
     *
     *
     */
    void batchSaveSo(String adviseNo, String orgCode);

    /**
     * 獲取銷售組織列表..
     * <p>
     * uri:/orgList <br>
     * method:GET
     * 
     * @param saleOrgCode
     *            saleOrgCode
     * @param languageCode
     *            languageCode
     * @param orgCode
     *            調用GDS传递的OrgCode
     *
     *
     */
    java.util.List<OrgListGETResponse> findSaleOrgInfoList(String saleOrgCode, String languageCode, String orgCode);

    /**
     * 下載轉出會員清單.
     * <p>
     * uri:/marketChanges/transferOut/results <br>
     * method:GET
     * 
     * @param adviseNo
     *            GDS調度時傳遞的號碼
     * @param orgCode
     *            調用GDS传递的OrgCode
     *
     *
     */
    void findTransOutList(String adviseNo, String orgCode);

    /**
     * 上傳轉出會員處理清單.
     * <p>
     * uri:/marketChanges/transferOut/notify <br>
     * method:POST
     * 
     * @param adviseNo
     *            GDS調度時傳遞的號碼
     * @param orgCode
     *            調用GDS传递的OrgCode
     *
     *
     */
    void transOut(String adviseNo, String orgCode,
            java.util.List<com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferOut.notify.model.NotifyPOSTBody> body);

    /**
     * 獲取本市場（源市場）轉出申請的列表.
     * <p>
     * uri:/marketChanges/transferOut/applications <br>
     * method:GET
     * 
     * @param subOrg
     *            subOrg
     * @param orgCode
     *            調用GDS传递的OrgCode
     *
     *
     */
    java.util.List<com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferOut.applications.model.ApplicationsGETResponse> findGdealerChgOrgAppList(
            String subOrg, String orgCode);

    /**
     * 保存轉出本市場（源市場）的申請.
     * <p>
     * uri:/marketChanges/transferOut/applications <br>
     * method:POST
     * 
     * @param orgCode
     *            調用GDS传递的OrgCode
     *
     *
     */
    ApplicationsPOSTResponse saveGdealerChgOrgApp(String orgCode, ApplicationsPOSTBody body);

    /**
     * 刪除轉出本市場（源市場）的申請.
     * <p>
     * uri:/marketChanges/transferOut/applications/{appID} <br>
     * method:DELETE
     * 
     * @param appID
     *            {appID}
     * @param orgCode
     *            調用GDS传递的OrgCode
     *
     *
     */
    AppIDDELETEResponse deleteGdealerChgOrgApp(String appID, String orgCode, AppIDDELETEBody body);

    /**
     * 下載轉入會員清單.
     * <p>
     * uri:/marketChanges/transferIn/results <br>
     * method:GET
     * 
     * @param adviseNo
     *            GDS調度時傳遞的號碼
     * @param orgCode
     *            調用GDS传递的OrgCode
     *
     *
     */
    void findTransInList(String adviseNo, String orgCode);

    /**
     * 上傳轉入會員處理清單.
     * <p>
     * uri:/marketChanges/transferIn/notify <br>
     * method:POST
     * 
     * @param adviseNo
     *            GDS調度時傳遞的號碼
     * @param orgCode
     *            調用GDS传递的OrgCode
     *
     *
     */
    void transIn(String adviseNo, String orgCode,
            java.util.List<com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferIn.notify.model.NotifyPOSTBody> body);

    /**
     * 獲取其他市場轉入本市場（目標市場）申請的待審批列表.
     * <p>
     * uri:/marketChanges/transferIn/applications <br>
     * method:GET
     * 
     * @param subOrg
     *            subOrg
     * @param orgCode
     *            調用GDS传递的OrgCode
     *
     *
     */
    java.util.List<com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferIn.applications.model.ApplicationsGETResponse> findGdealerChgOrgAppAuditList(
            String subOrg, String orgCode);

    /**
     * 保存其他市場轉入本市場（目標市場）申請的審核結果.
     * <p>
     * uri:/marketChanges/transferIn/applications/{appID}/audit <br>
     * method:POST
     * 
     * @param appID
     *            {appID}
     * @param orgCode
     *            調用GDS传递的OrgCode
     *
     *
     */
    AuditPOSTResponse saveGdealerChgOrgAppAudit(String appID, String orgCode, AuditPOSTBody body);

}
