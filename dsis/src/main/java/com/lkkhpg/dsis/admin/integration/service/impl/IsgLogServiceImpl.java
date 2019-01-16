/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.integration.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageHelper;
import com.lkkhpg.dsis.admin.integration.service.IIsgLogService;
import com.lkkhpg.dsis.integration.recorder.dto.InterfaceRecord;
import com.lkkhpg.dsis.integration.recorder.dto.ListenerRecord;
import com.lkkhpg.dsis.integration.recorder.mapper.InterfaceRecordMapper;
import com.lkkhpg.dsis.integration.recorder.mapper.ListenerRecordMapper;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 接口集成日志实现类.
 * 
 * @author frank.li
 *
 */
@Service
@Transactional
public class IsgLogServiceImpl implements IIsgLogService {

    private final Logger logger = LoggerFactory.getLogger(IsgLogServiceImpl.class);

    @Autowired
    private InterfaceRecordMapper interfaceRecordMapper;

    @Autowired
    private ListenerRecordMapper listenerRecordMapper;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Override
    public List<InterfaceRecord> getInterfaceRecordLog(IRequest request, Map<String, Object> params, int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
        List<InterfaceRecord> interfaceRecords = interfaceRecordMapper.selectByRequestDate(params);

        return interfaceRecords;
    }

    @Override
    public List<ListenerRecord> getInterfaceListenerLog(IRequest request, ListenerRecord params, int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
        List interfaceRecords = listenerRecordMapper.select(params);
        return interfaceRecords;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<? extends BaseDTO> queryInterfaceDatas(IRequest request, String mapper,
                                                       @RequestParam Map<String, Object> params, int page, int pagesize) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            PageHelper.startPage(page, pagesize);
            return sqlSession.selectList(mapper, params);
        } catch (Throwable e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
        }
        return null;
    }
}
