/*
 *
 */
package com.lkkhpg.dsis.common.inventory.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lkkhpg.dsis.common.inventory.dto.InvLot;
import com.lkkhpg.dsis.common.inventory.dto.TransferTrxDetail;
import com.lkkhpg.dsis.common.inventory.dto.TransferTrxDetailQuery;

/**
 * 移库详细MAPPER.
 * 
 * @author chenjingxiong
 */
public interface TransferTrxDetailMapper {
    int deleteByPrimaryKey(Long trxDetailId);

    int insert(TransferTrxDetail record);

    int insertSelective(TransferTrxDetail record);

    TransferTrxDetail selectByPrimaryKey(Long trxDetailId);

    int updateByPrimaryKeySelective(TransferTrxDetail record);

    int updateByPrimaryKey(TransferTrxDetail record);

    List<TransferTrxDetail> selectTransferTrxDetails(TransferTrxDetail transferTrxDetail);

    List<TransferTrxDetailQuery> selectInTransferTrxDetails(TransferTrxDetailQuery transferTrxDetailQuery);

    int deleteTransferTrxId(TransferTrxDetail transferTrxDetail);

    /**
     * 查询转入单可新增的转入行.
     * 
     * @param outTrxId
     *            转出单id
     * @param inTrxId
     *            转入单id
     * @param outTrxdDetailIds
     *            转入单行id
     * @param itemNumber
     *            商品编码
     * @return 转入单行list
     */
    List<TransferTrxDetailQuery> queryAddTransferTrxDetails(@Param(value = "outTrxId") Long outTrxId,
            @Param(value = "inTrxId") Long inTrxId, @Param(value = "outTrxdDetailIds") List<String> outTrxdDetailIds,
            @Param(value = "itemNumber") String itemNumber);

    List<TransferTrxDetail> getRemainingIndAftCar(@Param("trxId") Long trxId, @Param("sourceTrxId") Long sourceTrxId,
            @Param("itemId") Long itemId, @Param("lotNumber") String lotNumber,
            @Param("packingType") String packingType);

    /**
     * 转出单提交校验.
     * 
     * @param trxId
     *            转出单号
     * @param organizationId
     *            转出org
     * @param transferOrgId
     *            转入org
     * @param itemId
     *            商品
     * @param lotNumber
     *            批次
     * @return 校验结果(校验的批次到期日是否出错,如果出错返回出错的批次信息)
     */
    TransferTrxDetail outTrxCheck(@Param("trxId") Long trxId, @Param("organizationId") Long organizationId,
            @Param("transferOrgId") Long transferOrgId, @Param("itemId") Long itemId,
            @Param("lotNumber") String lotNumber);
    
    /**
     * 转入单提交校验.
     * 
     * @param trxId
     *            转入单号
     * @param organizationId
     *            转出org
     * @param transferOrgId
     *            转入org
     * @param itemId
     *            商品
     * @param lotNumber
     *            批次
     * @return 校验结果(校验的批次到期日是否出错,如果出错返回出错的批次信息)
     */
    TransferTrxDetail inTrxCheck(@Param("trxId") Long trxId, @Param("organizationId") Long organizationId,
            @Param("transferOrgId") Long transferOrgId, @Param("itemId") Long itemId,
            @Param("lotNumber") String lotNumber);
}