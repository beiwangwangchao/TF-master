/*
 *
 */
package com.lkkhpg.dsis.admin.inventory.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.lkkhpg.dsis.admin.inventory.service.IDoHeaderService;
import com.lkkhpg.dsis.common.config.dto.SpmMarket;
import com.lkkhpg.dsis.common.config.mapper.SpmMarketMapper;
import com.lkkhpg.dsis.common.inventory.lading.dto.DoHeader;
import com.lkkhpg.dsis.common.inventory.lading.dto.DoLine;
import com.lkkhpg.dsis.common.inventory.lading.mapper.DoHeaderMapper;
import com.lkkhpg.dsis.common.inventory.lading.mapper.DoLineMapper;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.DTOStatus;
import com.lkkhpg.dsis.platform.dto.system.DocSequence;
import com.lkkhpg.dsis.platform.service.IDocSequenceService;

/**
 * 查询提货单impl.
 * @author liuxuan
 *
 */
@Service
@Transactional
public class DoHeaderServiceImpl implements IDoHeaderService {
    public static final int FOUR = 4;
    
    public static final Long ONE = 1L;
    @Autowired
    private DoHeaderMapper doheaderMapper;
    @Autowired
    private DoLineMapper doLineMapper;
    @Autowired
    private IDocSequenceService docSequanceService;
    @Autowired
    private SpmMarketMapper spmMarketMapper;
    
    @Override
    public List<DoHeader> queryDoHeader(IRequest request, DoHeader doHeader, int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
        List<DoHeader> list = doheaderMapper.queryByDoHeader(doHeader);
        return list;
    }

    @Override
    public List<DoHeader> saveDoHeader(IRequest request, List<DoHeader> doHeaders) {
        for (DoHeader doHeader : doHeaders) {
            if (DTOStatus.ADD.equals(doHeader.get__status())) {
               SpmMarket spmMarket = spmMarketMapper.selectByPrimaryKey(doHeader.getMarketId());
                String marketCode = spmMarket.getCode();
                String ye = new Date().toString();
                String year = ye.substring(ye.length() - 2, ye.length());
                DocSequence docSequence = new DocSequence();
                docSequence.setDocType("DO_HEADER");
                docSequence.setPk1Value(marketCode);
                docSequence.setPk2Value(year);
                String var = docSequanceService.getSequence(request, docSequence, marketCode + year, FOUR, ONE);
                doHeader.setDoNumber(var);
                doheaderMapper.insertSelective(doHeader);
                if (!doHeader.getDoLines().isEmpty()) {
                    processDoLine(doHeader.getDoLines(), doHeader.getDoHeaderId());
                }
            } else if (DTOStatus.UPDATE.equals(doHeader.get__status())) {
                doheaderMapper.updateByPrimaryKeySelective(doHeader);
                if (!doHeader.getDoLines().isEmpty()) {
                    processDoLine(doHeader.getDoLines(), doHeader.getDoHeaderId());
                }
            }
        }
        return doHeaders;
    }

    /**
     * 行表中判断增加或修改.
     * 
     * @param poLines
     *            行表集合
     */
    private void processDoLine(List<DoLine> doLines, Long doHeaderId) {
        for (DoLine line : doLines) {
            if (DTOStatus.ADD.equals(line.get__status())) {
                line.setDoHeaderId(doHeaderId);
                doLineMapper.insertSelective(line);
            } else if (DTOStatus.UPDATE.equals(line.get__status())) {
                doLineMapper.updateByPrimaryKeySelective(line);
            } else if (DTOStatus.DELETE.equals(line.get__status())) {
                doLineMapper.deleteByPrimaryKey(line.getDoLineId());
            }
        }
    }

    @Override
    public List<DoLine> queryDoLine(IRequest request, DoLine doLine) {
        List<DoLine> list = doLineMapper.queryByDoLine(doLine);
        return list;
    }


}
