/*
 *
 */
package com.lkkhpg.dsis.admin.bonus.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.lkkhpg.dsis.admin.bonus.constant.BonusConstants;
import com.lkkhpg.dsis.admin.bonus.service.IBonusRetransferService;
import com.lkkhpg.dsis.common.bonus.dto.BonusRetransfer;
import com.lkkhpg.dsis.common.bonus.mapper.BonusRetransferMapper;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.system.DocSequence;
import com.lkkhpg.dsis.platform.service.IDocSequenceService;

/**
 * re_transfer 接口实现.
 * 
 * @author li.peng@hand-china.com
 *
 */
@Service
public class BonusRestranferServiceImpl implements IBonusRetransferService {

    @Autowired
    private BonusRetransferMapper retransferOrderMapper;

    @Autowired
    private IDocSequenceService docSequenceService;

    @Override
    public List<BonusRetransfer> queryRetransfer(IRequest request, BonusRetransfer retransferOrder, int page,
            int pagesize) {
        if (pagesize != -1) {
            PageHelper.startPage(page, pagesize);
        }
        return retransferOrderMapper.selectBonusRetransfer(retransferOrder);
    }

    @Override
    public int insertRetransfer(IRequest request, BonusRetransfer retransfer) {
        DocSequence docSequence = new DocSequence(BonusConstants.BONUS_RETRANSFER_SEQ,
                BonusConstants.BONUS_RETRANSFER_SEQ_HEAD, null, null, null, null);
        String retansferSequence = docSequenceService.getSequence(request, docSequence,
                BonusConstants.BONUS_RETRANSFER_SEQ_HEAD, BonusConstants.CODE_LENGTH_FIVE, 1L);
        retransfer.setRetransCode(retansferSequence);
        return retransferOrderMapper.insertSelective(retransfer);
    }

}
