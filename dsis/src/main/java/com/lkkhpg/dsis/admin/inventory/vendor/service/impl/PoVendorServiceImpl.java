/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.inventory.vendor.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.lkkhpg.dsis.admin.inventory.exception.InventoryException;
import com.lkkhpg.dsis.admin.inventory.vendor.service.IPoVendorService;
import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.common.constant.UserConstants;
import com.lkkhpg.dsis.common.exception.CommSystemProfileException;
import com.lkkhpg.dsis.common.inventory.dto.StockTrx;
import com.lkkhpg.dsis.common.inventory.mapper.StockTrxMapper;
import com.lkkhpg.dsis.common.inventory.vendor.dto.PoVendor;
import com.lkkhpg.dsis.common.inventory.vendor.mapper.PoVendorMapper;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.system.DocSequence;
import com.lkkhpg.dsis.platform.service.IDocSequenceService;

/**
 * 供应商管理实现类.
 * 
 * @author liang.rao
 *
 */
@Service
public class PoVendorServiceImpl implements IPoVendorService {

    @Autowired
    private PoVendorMapper poVendorMapper;
    @Autowired
    private StockTrxMapper stockTrxMapper;
    @Autowired
    private IDocSequenceService docSequenceService;
    
    private static final String STOCKTRXSTATUS = "NEW";
    
    private static final String DOC_TYPE = "VENDOR_CODE";
    
    private static final String DOC_PREFIX = "VP";
    
    private static final int CODE_LENGTH_FIVE = 5;
    
    private static final Long INIT_SEQUENCE = 10000L;
    
    public static final Long MILL = 1000L;
    
    public static final Long HOUR = 24L;
    
    public static final Long MIN = 60L;
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public PoVendor saveVendor(IRequest request, PoVendor vendor) 
            throws CommSystemProfileException, InventoryException {
        if (!StringUtils.isEmpty(vendor.getEmail())) {
            // 邮箱格式验证
            if (!vendor.getEmail().matches(UserConstants.EMAIL_REGEX)) {
                throw new CommSystemProfileException(CommSystemProfileException.EMAIL_FORMAT, new Object[] {});
            }
        }
        PoVendor pvd = new PoVendor();
        pvd.setName(vendor.getName());
        PoVendor pvdl = poVendorMapper.selectByName(pvd);
        if (vendor.getVendorId() != null) {
            if (StringUtils.isEmpty(vendor.getCode())) {
                throw new InventoryException(InventoryException.MSG_ERROR_PO_VENDOR_CODE_EMPTY, new Object[] {});
            }
            if (StringUtils.isEmpty(vendor.getEnabledFlag())) {
                throw new InventoryException(InventoryException.MSG_ERROR_PO_VENDOR_STATUS_EMPTY, new Object[] {});
            }
            poVendorMapper.updateByRecord(vendor);
        } else {
            if (pvdl != null) {
                throw new InventoryException(InventoryException.MSG_ERROR_PO_VENDOR_NAME_UNIQUE, new Object[] {});
            }
            //自动生成供应商编号
            vendor.setCode(docSequenceService.getSequence(request,
                    new DocSequence(DOC_TYPE, DOC_PREFIX, null, null, null, null), DOC_PREFIX,
                    CODE_LENGTH_FIVE, INIT_SEQUENCE));
            vendor.setEnabledFlag(SystemProfileConstants.YES);
            poVendorMapper.insertByRecord(vendor);
        }
        return vendor;
    }

    @Override
    public List<PoVendor> queryVendor(IRequest request, PoVendor vendor, int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
        return poVendorMapper.selectByRecord(vendor);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteVendor(IRequest request, PoVendor vendor) throws InventoryException {
            //若出入库单中有新建的使用到则不能失效
            List<StockTrx> stockTrxs = stockTrxMapper.selectByVendorId(vendor.getVendorId());
            for (StockTrx stockTrx : stockTrxs) {
                if (stockTrx.getStatus().equals(STOCKTRXSTATUS)) {
                    throw new InventoryException(InventoryException.MSG_ERROR_VENDOR_INUSE, new Object[] {});
                }
            }
            vendor.setEnabledFlag(SystemProfileConstants.NO);
            poVendorMapper.updateByRecord(vendor);
    }

}
