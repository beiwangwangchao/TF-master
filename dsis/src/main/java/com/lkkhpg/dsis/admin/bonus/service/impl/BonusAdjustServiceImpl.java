/*
 *
 */
package com.lkkhpg.dsis.admin.bonus.service.impl;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.lkkhpg.dsis.admin.bonus.constant.BonusConstants;
import com.lkkhpg.dsis.admin.bonus.service.IBonusAdjustService;
import com.lkkhpg.dsis.common.bonus.dto.BonusAdjust;
import com.lkkhpg.dsis.common.bonus.mapper.BonusAdjustMapper;
import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.common.service.IParamService;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.system.DocSequence;
import com.lkkhpg.dsis.platform.service.IDocSequenceService;

/**
 * 奖金调整实现.
 * 
 * @author li.peng@hand-china.com
 *
 */
@Service
public class BonusAdjustServiceImpl implements IBonusAdjustService {

    @Autowired
    private BonusAdjustMapper bonusAdjustMapper;

    @Autowired
    private IParamService paramService;

    @Autowired
    private IDocSequenceService docSequenceService;

    private static final String DOC_TYPE = "BONUS_ADJUST";

    private static final String DOC_PREFIX = "BA";

    private static final Long INIT_SEQUENCE = 10000L;

    /**
     * 保存奖金调整.
     * 
     */
    @Override
    @Transactional
    public BonusAdjust saveBonusAdjust(IRequest request, BonusAdjust bonusAdjust) {
        if (bonusAdjust.getAdjId() == null) {
            bonusAdjust.setSyncFlag(BonusConstants.BONUS_ADJUST_SYNC_FLAG_DEDAULT);

            bonusAdjust.setAdjCode(docSequenceService.getSequence(request,
                    new DocSequence(DOC_TYPE, DOC_PREFIX, null, null, null, null), DOC_PREFIX,
                    BonusConstants.CODE_LENGTH_FIVE, INIT_SEQUENCE));
//            bonusAdjust.setMarketId(Long.parseLong(request.getAttribute(SystemProfileConstants.MARKET_ID).toString()));
//            Long marketId = Long.parseLong(request.getAttribute(SystemProfileConstants.MARKET_ID).toString());
            Long marketId = bonusAdjust.getMarketId();
            List<String> currencies = paramService.getMarketParamValues(request, SystemProfileConstants.SPM_CURRENCY, marketId);
            if (currencies != null && !currencies.isEmpty()) {
                bonusAdjust.setCurrencyCode(currencies.iterator().next());
            }

            if (bonusAdjustMapper.insertSelective(bonusAdjust) > 0) {
                return bonusAdjust;
            }
        } else {
            if (bonusAdjustMapper.updateByPrimaryKeySelective(bonusAdjust) > 0) {
                return bonusAdjust;
            }
        }

        return null;
    }

    @Override
    @Transactional
    public int deleteBonusAdjust(IRequest request, Long adjId) {
    	BonusAdjust record = new BonusAdjust();
    	record.setAdjId(adjId);
        return bonusAdjustMapper.deleteByPrimaryKey(record);
    }

    @Override
    @Transactional
    public List<BonusAdjust> queryBonusAdjust(IRequest request, BonusAdjust bonusAdjust, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        String createDateCode = bonusAdjust.getCreateDateCode();
        if (createDateCode != null) {
            Calendar calendar = Calendar.getInstance();
            switch (createDateCode) {
            case BonusConstants.BONUS_CREAT_TIME_10D:
                calendar.add(Calendar.DAY_OF_YEAR, BonusConstants.BONUS_CREAT_TIME_10D_VALUE);
                break;
            case BonusConstants.BONUS_CREAT_TIME_30D:
                calendar.add(Calendar.DAY_OF_YEAR, BonusConstants.BONUS_CREAT_TIME_30D_VALUE);
                break;
            case BonusConstants.BONUS_CREAT_TIME_6M:
                calendar.add(Calendar.MONTH, BonusConstants.BONUS_CREAT_TIME_6M_VALUE);
                break;
            case BonusConstants.BONUS_CREAT_TIME_1Y:
                calendar.add(Calendar.YEAR, BonusConstants.BONUS_CREAT_TIME_1Y_VALUE);
                break;
            default:
                break;
            }
            bonusAdjust.setCreationDate(calendar.getTime());
        }
        return bonusAdjustMapper.queryBonusAdjust(bonusAdjust);
    }

    @Override
    @Transactional
    public int delBatchBonusAdjust(IRequest request, List<BonusAdjust> bonusAdjusts) {
        
//        Map<String, Object> bonusAdjustMap = new HashMap<String, Object>();
//        bonusAdjustMap.put("bonusAdjusts", bonusAdjusts);
//        return bonusAdjustMapper.deleteBonusAdjust(bonusAdjustMap);
        //由于审计只能针对dto处理，所以进行以下改造.
        bonusAdjusts.forEach(b-> {
            bonusAdjustMapper.updateBonusStatus(b);
        });
        return bonusAdjusts.size();
    }

}
