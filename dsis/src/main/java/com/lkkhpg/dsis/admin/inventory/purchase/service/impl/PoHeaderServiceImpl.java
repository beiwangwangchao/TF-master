/*
 *
 */
package com.lkkhpg.dsis.admin.inventory.purchase.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.lkkhpg.dsis.admin.inventory.exception.InventoryException;
import com.lkkhpg.dsis.admin.inventory.service.IInvTransactionService;
import com.lkkhpg.dsis.admin.inventory.service.IStockTrxService;
import com.lkkhpg.dsis.common.constant.InventoryConstants;
import com.lkkhpg.dsis.common.inventory.dto.InvTransaction;
import com.lkkhpg.dsis.common.inventory.dto.StockTrx;
import com.lkkhpg.dsis.common.inventory.dto.StockTrxDetail;
import com.lkkhpg.dsis.common.inventory.mapper.StockTrxDetailMapper;
import com.lkkhpg.dsis.common.inventory.mapper.StockTrxMapper;
import com.lkkhpg.dsis.common.inventory.vendor.dto.PoVendor;
import com.lkkhpg.dsis.common.inventory.vendor.mapper.PoVendorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.lkkhpg.dsis.admin.inventory.purchase.service.IPoHeaderService;
import com.lkkhpg.dsis.common.config.dto.SpmMarket;
import com.lkkhpg.dsis.common.config.mapper.SpmMarketMapper;
import com.lkkhpg.dsis.common.inventory.purchase.dto.PoHeader;
import com.lkkhpg.dsis.common.inventory.purchase.dto.PoLine;
import com.lkkhpg.dsis.common.inventory.purchase.mapper.PoHeaderMapper;
import com.lkkhpg.dsis.common.inventory.purchase.mapper.PoLineMapper;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.DTOStatus;
import com.lkkhpg.dsis.platform.dto.system.DocSequence;
import com.lkkhpg.dsis.platform.service.IDocSequenceService;

/**
 * 采购订单实现层.
 * 
 * @author HuangJiaJing
 *
 */
@Service
@Transactional
public class PoHeaderServiceImpl implements IPoHeaderService {

    private static final int PO_NUMBER_LENGTH = 4;
    
    private static final Long PO_NUMBER_STEP = 1L;
    
    private static final String PO_NUMBER_DOC_CODE = "PO_HEADER";
    
    @Autowired
    private PoHeaderMapper poHeaderMapper;

    @Autowired
    private PoLineMapper lineMapper;

    @Autowired
    private IDocSequenceService docSequanceService;

    @Autowired
    private SpmMarketMapper spmMarketMapper;

    @Autowired
    private StockTrxMapper stockTrxMapper;

    @Autowired
    private StockTrxDetailMapper stockTrxDetailMapper;

    @Autowired
    private PoVendorMapper poVendorMapper;

    @Autowired
    private IDocSequenceService docSequenceService;

    @Autowired
    private IInvTransactionService invTransactionService;

    @Autowired
    private IStockTrxService iStockTrxService;

    @Override
    public List<PoHeader> queryPoHeader(IRequest request, PoHeader poHeader, int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
        List<PoHeader> list = poHeaderMapper.queryByPoHeader(poHeader);
        return list;
    }

    @Override
    public List<PoHeader> savePoHeader(IRequest request, List<PoHeader> poHeaders) {
        for (PoHeader poHeader : poHeaders) {
            if (DTOStatus.ADD.equals(poHeader.get__status())) {
                SpmMarket spmMarket = spmMarketMapper.selectByPrimaryKey(poHeader.getMarketId());
                String marketCode = spmMarket.getCode();
                String ye = new Date().toString();
                String year = ye.substring(ye.length() - 2, ye.length());
                DocSequence docSequence = new DocSequence();
                docSequence.setDocType(PO_NUMBER_DOC_CODE);
                docSequence.setPk1Value(marketCode);
                docSequence.setPk2Value(year);
                String var = docSequanceService.getSequence(request, docSequence, marketCode + year, PO_NUMBER_LENGTH,
                        PO_NUMBER_STEP);
                poHeader.setPoNumber(var);
                poHeaderMapper.insert(poHeader);
                if (!poHeader.getPoLines().isEmpty()) {
                    processPoLine(poHeader.getPoLines(), poHeader.getPoHeaderId());
                }
            } else if (DTOStatus.UPDATE.equals(poHeader.get__status())) {
                poHeaderMapper.updateByPrimaryKeySelective(poHeader);
                if (!poHeader.getPoLines().isEmpty()) {
                    processPoLine(poHeader.getPoLines(), poHeader.getPoHeaderId());
                }
            }
        }
        return poHeaders;
    }

    @Override
    public List<PoHeader> submitPoHeader(IRequest request, List<PoHeader> poHeaders) {
        PoHeader poHeader = poHeaders.get(0);
        StockTrx stockTrx=new StockTrx();
        stockTrx.setCreatedBy(request.getAttribute("createdBy"));
        stockTrx.setCreationDate(new Date());
        stockTrx.setOrganizationId(request.getAttribute("orgId"));

        // 流水号段数设置
        DocSequence docSequence;
        // 库存组织
        String orgId = 0 + stockTrx.getOrganizationId().toString();
        String trxNumberStr;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String date = sdf.format(new Date());
        // 入库
        if(poHeader.getIncoterm()=="PSTIN"){
            docSequence = new DocSequence(InventoryConstants.STOCKIO_IN_CODE, orgId, date, null, null, null);
        }else{
            docSequence = new DocSequence(InventoryConstants.STOCKIO_OUT_CODE, orgId, date, null, null, null);
        }
        trxNumberStr = docSequenceService.getSequence(request, docSequence, date,
                InventoryConstants.STOCKIO_SEQ_LENGTH, InventoryConstants.STOCKIO_START_NUMBER);
        trxNumberStr = InventoryConstants.STOCKIO_IN_CODE + trxNumberStr;
        //出入库的id号
        Long trxId = stockTrxMapper.getTrxId();
        stockTrx.setTrxId(trxId);
        //设置单据编号
        stockTrx.setTrxNumber(trxNumberStr);
        //设置事务处理日期
        stockTrx.setTrxDate(new Date());
        //设置事务处理类型 如果采购类型为采购入库则事务类型为采购入库 否则为采购出库
        if(poHeader.getIncoterm().equals("PSTIN")){
            stockTrx.setTrxType("PSTIN");
        }else{
            stockTrx.setTrxType("PSTOT");
        }
        //设置供应商ID
        String vendorName=poHeader.getVendorName();
        PoVendor pvd = new PoVendor();
        pvd.setName(vendorName);
        pvd=poVendorMapper.selectByName(pvd);
        stockTrx.setVendorId(pvd.getVendorId());
        //设置状态为 ：新建
        stockTrx.setStatus("NEW");
        stockTrxMapper.insertStockTrx(stockTrx);;
        List<PoLine> poLines=poHeader.getPoLines();
        List<StockTrxDetail> details = new ArrayList<>();
        for(PoLine poline :poLines){
            StockTrxDetail detail = new StockTrxDetail();
            detail.setTrxId(stockTrx.getTrxId());
            // 库存组织ID
            detail.setOrganizationId(request.getAttribute("_invOrgId"));
            // 字库ID
            detail.setSubinventoryId(1L);
            // 货位ID
            detail.setLocatorId(1L);
            // 商品ID
            detail.setItemId(poline.getItemId());
            // 事务处理数量
            detail.setNumberOfCarton(BigDecimal.valueOf(poline.getQuantity()));
            detail.setQuantity(BigDecimal.valueOf(poline.getQuantity()));
            //事务处理类型  :如果采购类型为采购入库则事务类型为采购入库 否则为采购出库
            if(poHeader.getIncoterm().equals("PSTIN")){
                detail.setOperType("PSTIN");
            }else{
                detail.setOperType("PSTOT");
            }
            // 事务处理原因 :如果采购类型为采购入库则原因为进货 若为采购退库则原因为退货
            if(poHeader.getIncoterm().equals("PSTIN")){
                detail.setOperReasonCode("STKIN");
            }else{
                detail.setOperReasonCode("RETUR");
            }
            //包装类型取该商品所对应的包装类型
            detail.setPackingType(poline.getUomCode());
            detail.setCreatedBy(stockTrx.getCreatedBy());
            detail.setUnitPrice(poline.getUnitPrice());
            details.add(detail);
        }
        for(StockTrxDetail detail:details) {
            stockTrxDetailMapper.insertStockTrxDetail(detail);
        }
        poHeader.setTrxNumber(trxNumberStr);
        poHeader.setTrxId(trxId);
        poHeaderMapper.updateByPrimaryKeySelective(poHeader);
        return poHeaders;
    }

    /**
     * 行表中判断增加或修改.
     * 
     * @param poLines
     *            行表集合
     */
    private void processPoLine(List<PoLine> poLines, Long poHeaderId) {
        for (PoLine line : poLines) {
            if (DTOStatus.ADD.equals(line.get__status())) {
                line.setPoHeaderId(poHeaderId);
                /*设置单位/箱 为1*/
                line.setPackageQuantity(1l);
                 /*设置最小订货数量为1*/
                line.setMinQuantity(1l);
                lineMapper.insert(line);
            } else if (DTOStatus.UPDATE.equals(line.get__status())) {
                lineMapper.updateByPrimaryKeySelective(line);
            } else if (DTOStatus.DELETE.equals(line.get__status())) {
                lineMapper.deleteByPrimaryKey(line.getPoLineId());
            }
        }
    }

    @Override
    public List<PoLine> queryPoLine(IRequest request, PoLine poLine, int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
        List<PoLine> list = lineMapper.queryByPoLine(poLine);
        return list;
    }
    
    @Override
    public List<PoLine> queryPoLineNoPage(IRequest request, PoLine poLine) {
        List<PoLine> list = lineMapper.queryByPoLine(poLine);
        return list;
    }

}
