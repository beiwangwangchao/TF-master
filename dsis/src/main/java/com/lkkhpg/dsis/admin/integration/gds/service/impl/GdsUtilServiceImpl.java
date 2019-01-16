/*
 *
 */
package com.lkkhpg.dsis.admin.integration.gds.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lkkhpg.dsis.admin.integration.gds.constant.IntegrationConstant;
import com.lkkhpg.dsis.admin.integration.gds.exception.IntegrationException;
import com.lkkhpg.dsis.admin.integration.gds.service.IGdsUtilService;
import com.lkkhpg.dsis.common.config.dto.SpmMarket;
import com.lkkhpg.dsis.common.config.dto.SpmSalesOrganization;
import com.lkkhpg.dsis.common.config.mapper.SpmMarketMapper;
import com.lkkhpg.dsis.common.config.mapper.SpmSalesOrganizationMapper;
import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.platform.constant.BaseConstants;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.impl.RequestHelper;
import com.lkkhpg.dsis.platform.dto.system.CodeValue;
import com.lkkhpg.dsis.platform.dto.system.DocSequence;
import com.lkkhpg.dsis.platform.service.ICodeService;
import com.lkkhpg.dsis.platform.service.IDocSequenceService;

/**
 * 申请号接口实现类.
 * 
 * @author frank.li
 *
 */
@Service
@Transactional
public class GdsUtilServiceImpl implements IGdsUtilService {

    private final Logger logger = LoggerFactory.getLogger(GdsUtilServiceImpl.class);

    @Autowired
    private IDocSequenceService docSequenceService;

    @Autowired
    private ICodeService codeService;

    @Autowired
    private SpmSalesOrganizationMapper spmSalesOrganizationMapper;

    @Autowired
    private SpmMarketMapper spmMarketMapper;

    @Override
    public String getAppNo(IRequest request) {
        // 参数：第一维度流水号名称、第二维度、第三维度...
        DocSequence docSequence = new DocSequence(IntegrationConstant.SEQ_TYPE, null, null, null, null, null);

        // 参数：请求上下文、DocSequence、前缀、最大长度、起始值
        String appNo = docSequenceService.getSequence(request, docSequence, "", IntegrationConstant.SEQ_LENGTH,
                IntegrationConstant.SEQ_INIT);

        return appNo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String getOrgCode(IRequest request, String gdsOrgCode) throws IntegrationException {
        String orgCode = null;
        List<CodeValue> codeValues = codeService.getCodeValuesByMeaning(request,
                IntegrationConstant.GDS_ORG_CODE_MAPPING, gdsOrgCode);

        // 根据含义取的值，默认取描述为Y的记录，无默认描述Y的则取其他
        for (CodeValue codeValue : codeValues) {
            orgCode = codeValue.getValue();
            if (BaseConstants.YES.equals(codeValue.getDescription())) {
                if (logger.isDebugEnabled()) {
                    logger.debug("has mapping, orgCode: {}", orgCode);
                }

                break;
            }
        }

        if (orgCode == null) {
            if (logger.isErrorEnabled()) {
                logger.error("gds org code no mapping, gdsOrgCode: {}", gdsOrgCode);
            }
            throw new IntegrationException(IntegrationException.MSG_ERROR_ORG_CODE_NO_MAPPING,
                    new Object[] { "gdsOrgCode " + gdsOrgCode });
        }
        return orgCode;
    };

    @Override
    public String getOrgCode(String gdsOrgCode) throws IntegrationException {
        IRequest request = RequestHelper.newEmptyRequest();
        request.setLocale(BaseConstants.DEFAULT_LANG);

        String orgCode = self().getOrgCode(request, gdsOrgCode);

        return orgCode;
    };

    @Override
    public List<String> getOrgCodes(String gdsOrgCode) throws IntegrationException {
        IRequest request = RequestHelper.newEmptyRequest();
        request.setLocale(BaseConstants.DEFAULT_LANG);
        List<CodeValue> corgCodeValues = codeService.getCodeValuesByMeaning(request,
                IntegrationConstant.GDS_ORG_CODE_MAPPING, gdsOrgCode);

        List<String> orgCodes = new ArrayList<String>();
        for (CodeValue codeValue : corgCodeValues) {
            orgCodes.add(codeValue.getValue());

            if (logger.isDebugEnabled()) {
                logger.debug("has mapping, orgCode: {}", codeValue.getValue());
            }
        }

        if (orgCodes.size() == 0) {
            if (logger.isErrorEnabled()) {
                logger.error("gds org code no mapping, gdsOrgCode: {}", gdsOrgCode);
            }
            throw new IntegrationException(IntegrationException.MSG_ERROR_ORG_CODE_NO_MAPPING,
                    new Object[] { "gdsOrgCode " + gdsOrgCode });
        }

        return orgCodes;
    };

    @Override
    public Long getMarketId(String gdsOrgCode) throws IntegrationException {
        String orgCode = self().getOrgCode(gdsOrgCode);
        SpmMarket spmMarket = spmMarketMapper.selectMarketByCode(orgCode);

        if (spmMarket == null) {
            if (logger.isErrorEnabled()) {
                logger.error("gds org code {} has mapping {}, but not exists in spmMarket", gdsOrgCode, orgCode);
            }
            throw new IntegrationException(IntegrationException.MSG_ERROR_ORG_CODE_MAPPING_NOT_EXIST_MARKET,
                    new Object[] { "gdsOrgCode " + gdsOrgCode });
        }

        return spmMarket.getMarketId();
    };

    @Override
    public List<Long> getMarketIds(String gdsOrgCode) throws IntegrationException {
        List<String> orgCodes = self().getOrgCodes(gdsOrgCode);

        List<Long> marketIds = new ArrayList<Long>();
        for (String orgCode : orgCodes) {
            SpmMarket spmMarket = spmMarketMapper.selectMarketByCode(orgCode);

            if (spmMarket == null) {
                if (logger.isErrorEnabled()) {
                    logger.error("gds org code {} has mapping {}, but not exists in spmMarket", gdsOrgCode, orgCode);
                }
                throw new IntegrationException(IntegrationException.MSG_ERROR_ORG_CODE_MAPPING_NOT_EXIST_MARKET,
                        new Object[] { "gdsOrgCode " + gdsOrgCode });
            }

            marketIds.add(spmMarket.getMarketId());
        }

        return marketIds;
    };

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String getGdsOrgCode(IRequest request, String orgCode) throws IntegrationException {

        String gdsOrgCode = codeService.getCodeMeaningByValue(request, IntegrationConstant.GDS_ORG_CODE_MAPPING,
                orgCode);

        if (gdsOrgCode == null) {
            if (logger.isErrorEnabled()) {
                logger.error("gds org code no mapping, orgCode: {}", orgCode);
            }
            throw new IntegrationException(IntegrationException.MSG_ERROR_ORG_CODE_NO_MAPPING,
                    new Object[] { "orgCode " + orgCode });
        }
        if (logger.isDebugEnabled()) {
            logger.debug("has mapping, gdsOrgCode: {}", gdsOrgCode);
        }
        return gdsOrgCode;
    };

    @Override
    public String getGdsOrgCode(String orgCode) throws IntegrationException {
        IRequest request = RequestHelper.newEmptyRequest();
        request.setLocale(BaseConstants.DEFAULT_LANG);

        String gdsOrgCode = self().getGdsOrgCode(request, orgCode);

        return gdsOrgCode;
    };

    @Override
    public List<String> getRefOrgCodes(String orgCode) throws IntegrationException {
        IRequest request = RequestHelper.newEmptyRequest();
        request.setLocale(BaseConstants.DEFAULT_LANG);

        String gdsOrgCode = self().getGdsOrgCode(request, orgCode);

        List<CodeValue> corgCodeValues = codeService.getCodeValuesByMeaning(request,
                IntegrationConstant.GDS_ORG_CODE_MAPPING, gdsOrgCode);

        List<String> orgCodes = new ArrayList<String>();
        for (CodeValue codeValue : corgCodeValues) {
            orgCodes.add(codeValue.getValue());

            if (logger.isDebugEnabled()) {
                logger.debug("has mapping, orgCode: {}", codeValue.getValue());
            }
        }

        if (orgCodes.size() == 0) {
            if (logger.isErrorEnabled()) {
                logger.error("gds org code no mapping, gdsOrgCode: {}", gdsOrgCode);
            }
            throw new IntegrationException(IntegrationException.MSG_ERROR_ORG_CODE_NO_MAPPING,
                    new Object[] { "gdsOrgCode " + gdsOrgCode });
        }

        return orgCodes;
    };

    @Override
    public List<Long> getRefMarketIds(String orgCode) throws IntegrationException {
        List<String> orgCodes = self().getRefOrgCodes(orgCode);

        List<Long> marketIds = new ArrayList<Long>();
        for (String record : orgCodes) {
            SpmMarket spmMarket = spmMarketMapper.selectMarketByCode(record);

            if (spmMarket == null) {
                if (logger.isErrorEnabled()) {
                    logger.error("orgCode {} not exists in spmMarket", record);
                }
                throw new IntegrationException(IntegrationException.MSG_ERROR_ORG_CODE_MAPPING_NOT_EXIST_MARKET,
                        new Object[] { "orgCode " + record });
            }

            marketIds.add(spmMarket.getMarketId());
        }

        return marketIds;
    };

    @Override
    public String getCurrentOrgCode(IRequest request) throws IntegrationException {
        // 获取当前登陆销售组织Id
        Long salesOrgId = (Long) request.getAttribute(SystemProfileConstants.SALES_ORG_ID);
        if (salesOrgId == null) {
            if (logger.isErrorEnabled()) {
                logger.error("Current sales org id is null");
            }
            throw new IntegrationException(IntegrationException.MSG_ERROR_CURRENT_SALES_ORG_ID_NULL, null);
        }

        // 获取销售组织所属的市场
        SpmSalesOrganization spmSalesOrganization = spmSalesOrganizationMapper.queryBaseInfo(salesOrgId);
        if (spmSalesOrganization == null || spmSalesOrganization.getMarketCode() == null) {
            if (logger.isErrorEnabled()) {
                logger.error("Current sales org's market code is null, salesOrgId: {}", salesOrgId);
            }
            throw new IntegrationException(IntegrationException.MSG_ERROR_CURRENT_MARKET_CODE_NULL, null);
        }

        return spmSalesOrganization.getMarketCode();
    };

    @Override
    public String toDateTimeString(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        return formatter.format(date);
    }

    @Override
    public String toDateString(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(date);
    }

    @Override
    public String toPeriodString(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMM");
        return formatter.format(date);
    }

    @Override
    public Date dateTimeStringToDate(String dateTime) throws IntegrationException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        try {
            return sdf.parse(dateTime);
        } catch (ParseException e) {
            if (logger.isErrorEnabled()) {
                logger.error(IntegrationException.MSG_ERROR_ISG_DATE_FORMAT_ERROR + e.getMessage(), e);
            }
            throw new IntegrationException(IntegrationException.MSG_ERROR_ISG_DATE_FORMAT_ERROR,
                    new Object[] { dateTime });
        }
    }

    @Override
    public Date dateStringToDate(String date) throws IntegrationException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            if (logger.isErrorEnabled()) {
                logger.error(IntegrationException.MSG_ERROR_ISG_DATE_FORMAT_ERROR + e.getMessage(), e);
            }
            throw new IntegrationException(IntegrationException.MSG_ERROR_ISG_DATE_FORMAT_ERROR, new Object[] { date });
        }
    }
}
