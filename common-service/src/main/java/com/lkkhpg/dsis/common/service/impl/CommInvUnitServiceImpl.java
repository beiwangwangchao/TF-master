/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.github.pagehelper.PageHelper;
import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.common.exception.CommSystemProfileException;
import com.lkkhpg.dsis.common.product.dto.InvUnitConvert;
import com.lkkhpg.dsis.common.product.dto.InvUnitOfMeasureB;
import com.lkkhpg.dsis.common.product.mapper.InvUnitConvertMapper;
import com.lkkhpg.dsis.common.product.mapper.InvUnitOfMeasureBMapper;
import com.lkkhpg.dsis.common.service.ICommInvUnitService;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.DTOStatus;

/**
 * 单位实现类.
 * 
 * @author houmin
 *
 */
@Service
@Transactional
public class CommInvUnitServiceImpl implements ICommInvUnitService {

    @Autowired
    private InvUnitOfMeasureBMapper invUnitOfMeasureBMapper;
    @Autowired
    private InvUnitConvertMapper invUnitConvertMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<InvUnitOfMeasureB> queryUnitNamesOfUomManage(IRequest request, InvUnitOfMeasureB invUomb, int pageNum,
            int pageSize) throws CommSystemProfileException {
        PageHelper.startPage(pageNum, pageSize);
        return invUnitOfMeasureBMapper.queryUnitNamesOfUomManage(invUomb);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<InvUnitOfMeasureB> batchUpdateUoms(IRequest request, List<InvUnitOfMeasureB> invUnitOfMeasureBs)
            throws CommSystemProfileException {
        InvUnitOfMeasureB invUnitOfMeasureB;
        for (InvUnitOfMeasureB obj : invUnitOfMeasureBs) {
            // 检验单位代码和单位名称不能为空
            valiInvUnitOfMeasureB(obj);

            if (DTOStatus.ADD.equals(obj.get__status())) {
                // 校验单位代码或单位名称是否唯一
                invUnitOfMeasureB = invUnitOfMeasureBMapper.selectByUomCodeIsExist(obj.getUomCode());
                if (invUnitOfMeasureB != null) {
                    throw new CommSystemProfileException(CommSystemProfileException.UNIT_CODE_UNIQUE_EXCEPTION, null);
                }
                Integer queryByName = invUnitOfMeasureBMapper.selectByUomNameIsExist(obj.getName());
                if (queryByName > 0) {
                    throw new CommSystemProfileException(CommSystemProfileException.UNIT_NAME_UNIQUE_EXCEPTION,
                            new Object[] {});
                }
                invUnitOfMeasureBMapper.insertUnitOfMeasureB(obj);
            } else if (DTOStatus.UPDATE.equals(obj.get__status())) {
                InvUnitOfMeasureB countCode = invUnitOfMeasureBMapper.selectByPrimaryKey(obj.getUomCode());
                if (!countCode.getName().equals(obj.getName())) {
                    int queryByNames = invUnitOfMeasureBMapper.selectByUomNameIsExist(obj.getName());
                   /* if (queryByNames > 0) {
                        throw new CommSystemProfileException(CommSystemProfileException.UNIT_NAME_UNIQUE_EXCEPTION,
                                new Object[] {});
                    }*/
                }
                invUnitOfMeasureBMapper.updateUnitOfMeasureB(obj);
            }
        }
        return invUnitOfMeasureBs;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<InvUnitConvert> batchUpdateInvUnitConverts(IRequest request, List<InvUnitConvert> invUnitConverts)
            throws CommSystemProfileException {
        for (InvUnitConvert obj : invUnitConverts) {
            if (obj.getConvertId() == null) {
                invUnitConvertMapper.insertSelective(obj);
            } else {
                invUnitConvertMapper.updateUomConvertByItemId(obj);
            }
        }
        return invUnitConverts;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<InvUnitConvert> queryInvUnitConverts(IRequest request, Long itemId, int pageNum, int pageSize)
            throws CommSystemProfileException {
        PageHelper.startPage(pageNum, pageSize);
        return invUnitConvertMapper.queryInvUnitConverts(itemId);
    }

    /**
     * 校验单位代码和单位名称不能为空.
     * 
     * @param invUnitOfMeasureB
     *            单位对象信息
     * @throws CommSystemProfileException
     *             系统配置统一异常
     */
    private void valiInvUnitOfMeasureB(InvUnitOfMeasureB invUnitOfMeasureB) throws CommSystemProfileException {
        if (StringUtils.isEmpty(invUnitOfMeasureB.getUomCode()) || StringUtils.isEmpty(invUnitOfMeasureB.getName())) {
            throw new CommSystemProfileException(CommSystemProfileException.UNIT_CODE_NOT_EMPTY_EXCEPTION, null);
        } else if (invUnitOfMeasureB.getUomCode().length() > SystemProfileConstants.INV_UNIT_CODE_LENGTH) {
            throw new CommSystemProfileException(CommSystemProfileException.UNIT_CODE_LARGER_LENGTH_EXCEPTION, null);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public InvUnitOfMeasureB queryInvUnitConvertByCode(String uomCode) {
        // TODO Auto-generated method stub
        if (org.apache.commons.lang.StringUtils.isEmpty(uomCode)) {
            return null;
        }
        return invUnitOfMeasureBMapper.queryUomByCode(uomCode);
    }

}
