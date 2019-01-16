/*
 *
 */
package com.lkkhpg.dsis.common.report.service.impl;

import java.util.List;

import org.apache.commons.lang.LocaleUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.common.exception.CommSystemProfileException;
import com.lkkhpg.dsis.common.report.service.ReportProgramService;
import com.lkkhpg.dsis.common.system.dto.SysReportParams;
import com.lkkhpg.dsis.common.system.dto.SysReportProgram;
import com.lkkhpg.dsis.common.system.mapper.SysReportParamsMapper;
import com.lkkhpg.dsis.common.system.mapper.SysReportProgramMapper;
import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.DTOStatus;

/**
 * 报表程序接口实现类.
 * 
 * @author wangc
 */
@Service
@Transactional
public class ReportProgramServiceImpl implements ReportProgramService {

    @Autowired
    private SysReportProgramMapper sysReportProgramMapper;
    @Autowired
    private SysReportParamsMapper sysReportParamsMapper;
    
    @Autowired
    private MessageSource messageSource;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<SysReportProgram> saveReportProgram(IRequest request, @StdWho List<SysReportProgram> reportPrograms)
            throws CommSystemProfileException {
        if (reportPrograms == null) {
            return null;
        }
        for (SysReportProgram reportProgram : reportPrograms) {
            List<SysReportParams> reportParams = reportProgram.getReportParams();
            validateReportParams(reportParams);
            Long programId = reportProgram.getReportProgramId();
            if (programId == null) {
                // 新增时校验是否有参数信息
                if (reportParams == null) {
                    throw new CommSystemProfileException(CommSystemProfileException.MSG_ERROR_REPORT_PROGRAM_NO_PARAMS,
                            null);
                }
                // 校验编码，名称是否重复
                int checkReportCodeRepeat = sysReportProgramMapper
                        .checkReportCodeRepeat(reportProgram.getProgramCode());
                if (checkReportCodeRepeat > 0) {
                    throw new CommSystemProfileException(
                            CommSystemProfileException.MSG_ERROR_REPORT_PROGRAM_CODE_REPEAT, null);
                }
                int checkReportNameRepeat = sysReportProgramMapper
                        .checkReportNameRepeat(reportProgram.getProgramName());
                if (checkReportNameRepeat > 0) {
                    throw new CommSystemProfileException(
                            CommSystemProfileException.MSG_ERROR_REPORT_PROGRAM_NAME_REPEAT, null);
                }
                sysReportProgramMapper.insert(reportProgram);
                for (SysReportParams reportParam : reportParams) {
                    if (DTOStatus.ADD.equals(reportParam.get__status())) {
                        reportParam.setReportProgramId(reportProgram.getReportProgramId());
                        self().insertReportParams(request, reportParam);
                    }
                }
            } else {
                sysReportProgramMapper.updateByPrimaryKey(reportProgram);
                if (reportParams != null) {
                    for (SysReportParams reportParam : reportParams) {
                        if (DTOStatus.ADD.equals(reportParam.get__status())) {
                            reportParam.setReportProgramId(reportProgram.getReportProgramId());
                            self().insertReportParams(request, reportParam);
                        }
                        if (DTOStatus.UPDATE.equals(reportParam.get__status())) {
                            reportParam.setReportProgramId(reportProgram.getReportProgramId());
                            self().updateReportParams(request, reportParam);
                        }
                    }
                }
            }
        }
        return reportPrograms;
    }

    /**
     * 验证报表参数.
     * 
     * @param reportParams
     *            报表参数dto
     * @throws CommSystemProfileException
     *             系统配置统一异常.
     */
    private void validateReportParams(List<SysReportParams> reportParams) throws CommSystemProfileException {
        if (reportParams != null) {
            for (SysReportParams reportParam : reportParams) {
                String fieldType = reportParam.getFieldType();
                String fieldSelectSource = reportParam.getFieldSelectSource();
                String fieldSelectUrl = reportParam.getFieldSelectUrl();
                String fieldSelectVf = reportParam.getFieldSelectVf();
                String fieldSelectTf = reportParam.getFieldSelectTf();
                String fieldSelectCode = reportParam.getFieldSelectCode();
                String fieldTextfield = reportParam.getFieldTextfield();
                String fieldLovCode = reportParam.getFieldLovCode();
                if (SystemProfileConstants.REPORT_PARAMS_FILE_TYPE_SELECT.equals(fieldType)) {
                    if (fieldSelectSource == null || "".equals(fieldSelectSource)) {
                        throw new CommSystemProfileException(
                                CommSystemProfileException.MSG_ERROR_REPORT_PARAMS_NO_SELECT_SOURCE, null);
                    }
                    if (SystemProfileConstants.REPORT_PARAMS_FILE_TYPE_SELECT_URL.equals(fieldSelectSource)) {
                        if (fieldSelectUrl == null || "".equals(fieldSelectUrl)) {
                            throw new CommSystemProfileException(
                                    CommSystemProfileException.MSG_ERROR_REPORT_PARAMS_NO_SELECT_SOURCE, null);
                        }
                        if (fieldSelectVf == null || "".equals(fieldSelectVf)) {
                            throw new CommSystemProfileException(
                                    CommSystemProfileException.MSG_ERROR_REPORT_PARAMS_NO_SELECT_VF, null);
                        }
                        if (fieldSelectTf == null || "".equals(fieldSelectTf)) {
                            throw new CommSystemProfileException(
                                    CommSystemProfileException.MSG_ERROR_REPORT_PARAMS_NO_SELECT_TF, null);
                        }
                    }
                    if (SystemProfileConstants.REPORT_PARAMS_FILE_TYPE_SELECT_CODE.equals(fieldSelectSource)) {
                        if (fieldSelectCode == null || "".equals(fieldSelectCode)) {
                            throw new CommSystemProfileException(
                                    CommSystemProfileException.MSG_ERROR_REPORT_PARAMS_NO_SELECT_CODE, null);
                        }
                    }
                } else if (SystemProfileConstants.REPORT_PARAMS_FILE_TYPE_POPUP.equals(fieldType)) {
                    if (fieldTextfield == null || "".equals(fieldTextfield)) {
                        throw new CommSystemProfileException(
                                CommSystemProfileException.MSG_ERROR_REPORT_PARAMS_NO_LOV_TF, null);
                    }
                    if (fieldLovCode == null || "".equals(fieldLovCode)) {
                        throw new CommSystemProfileException(
                                CommSystemProfileException.MSG_ERROR_REPORT_PARAMS_NO_LOV_CODE, null);
                    }
                }
            }
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void insertReportParams(IRequest request, SysReportParams reportParams) {
        sysReportParamsMapper.insert(reportParams);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateReportParams(IRequest request, SysReportParams reportParams) {
        sysReportParamsMapper.updateByPrimaryKey(reportParams);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<SysReportParams> deleteReportParams(IRequest request, List<SysReportParams> reportParams) {
        if (reportParams == null) {
            return null;
        }
        for (SysReportParams sysReportParam : reportParams) {
                sysReportParamsMapper.deleteByPrimaryKey(sysReportParam.getReportParamsId());
        }
        return reportParams;
    }

    @Override
    public SysReportProgram getReportProgram(IRequest request, Long reportProgramId) {
        SysReportProgram reportProgram = sysReportProgramMapper.selectByPrimaryKey(reportProgramId);
        List<SysReportParams> reportParams = sysReportParamsMapper.queryByReportProgramId(reportProgramId);
        if (reportParams != null) {
            reportProgram.setReportParams(reportParams);
        }
        return reportProgram;
    }

    @Override
    public List<SysReportProgram> queryReportProgram(IRequest request, SysReportProgram sysReportProgram, int page,
            int pagesize) {
        PageHelper.startPage(page, pagesize);
        return sysReportProgramMapper.querySysReportProgram(sysReportProgram);
    }

    @Override
    public List<SysReportParams> getReportParams(IRequest request, Long reportProgramId) {
        return sysReportParamsMapper.queryByReportProgramId(reportProgramId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Long deleteReportProgram(IRequest request, Long reportProgramId) {
        sysReportProgramMapper.deleteByPrimaryKey(reportProgramId);
        return reportProgramId;
    }

    @Override
    public SysReportProgram getReportProgramByCode(IRequest request, String reportProgramCode) {
        SysReportProgram reportProgram = sysReportProgramMapper.selectProgramByCode(reportProgramCode);
        List<SysReportParams> reportParams = sysReportParamsMapper
                .queryByReportProgramId(reportProgram.getReportProgramId());

        if (reportParams != null) {
            for(SysReportParams reportParam : reportParams){
                String display = messageSource.getMessage(reportParam.getDisplay(), null, LocaleUtils.toLocale(request.getLocale()));
                reportParam.setDisplay(display);
            }
            reportProgram.setReportParams(reportParams);
        } 
        return reportProgram;
    }

}
