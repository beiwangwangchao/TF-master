/*
 *
 */
package com.lkkhpg.dsis.common.inventory.mapper;

        import com.lkkhpg.dsis.common.inventory.dto.InvOnhandQuantity;
        import com.lkkhpg.dsis.common.inventory.dto.StockTrx;

        import java.math.BigDecimal;
        import java.util.List;
        import java.util.Map;

/**
 * 库存现有量Mapper.
 *
 * @author hanrui.huang
 */
public interface InvOnhandQuantityMapper {
    int deleteByPrimaryKey(Long onhandId);

    int insert(InvOnhandQuantity record);

    int insertSelective(InvOnhandQuantity record);

    InvOnhandQuantity selectByPrimaryKey(Long onhandId);

    int updateByPrimaryKeySelective(InvOnhandQuantity record);

    int updateByPrimaryKey(InvOnhandQuantity record);

    List<InvOnhandQuantity> selectForLock(InvOnhandQuantity record);

    List<InvOnhandQuantity> queryOnhandQuantity(InvOnhandQuantity record);

    InvOnhandQuantity selectByItemId(InvOnhandQuantity record);

    List<InvOnhandQuantity> getItemId(InvOnhandQuantity record);

    List<InvOnhandQuantity> queryTotalQuantity(StockTrx stockTrx);

    List<InvOnhandQuantity> queryOnhandQty(InvOnhandQuantity record);

    /*add by furong.tang*/
    List<InvOnhandQuantity> queryOnhandQty2(InvOnhandQuantity record);

    BigDecimal getAvailableQuantity(InvOnhandQuantity record);

    /**
     * add by furong.tang
     * 库存实际数量
     * @param record
     * @return
     */
    BigDecimal getQuantity(InvOnhandQuantity record);

    /*add by furong.tang*/
    BigDecimal getAvailableQuantity2(InvOnhandQuantity record);

    /*add by furong.tang*/
    BigDecimal getAvailableQuantity3(InvOnhandQuantity record);

    List<InvOnhandQuantity> getPendingQuantity(InvOnhandQuantity record);

    BigDecimal getQuantityByItemAndLotNumber(Map<String, Object> map);
}