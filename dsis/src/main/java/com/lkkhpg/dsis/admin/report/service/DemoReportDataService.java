/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.report.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lkkhpg.dsis.admin.user.exception.UserException;
import com.lkkhpg.dsis.admin.user.service.IUserInfoService;
import com.lkkhpg.dsis.common.user.dto.IpointUser;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.report.service.IReportDataService;

/**
 * @author chenjingxiong
 */
@Service("demoReportDataService")
public class DemoReportDataService implements IReportDataService {

    @Autowired
    private IUserInfoService userInfoService;
    
    @Override
    public Map<String, Object> process(IRequest request, Map<String, Object> paramMap) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<IpointUser> list = userInfoService.getIpointUsers(request, new IpointUser(), 1, 10);
            result.put("users", list);
        } catch (UserException e) {
            // TODO: log error
        }
        return result;
    }

}
