/*
 *
 */

package com.lkkhpg.dsis.platform.controllers.sys;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lkkhpg.dsis.platform.constant.BaseConstants;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.ResponseData;
import com.lkkhpg.dsis.platform.dto.system.Language;
import com.lkkhpg.dsis.platform.service.ILanguageService;

/**
 * @author shengyang.zhou@hand-china.com
 */
@Controller
@RequestMapping("/sys/language")
public class LanguageController extends BaseController {

    @Autowired
    private ILanguageService languageService;

    @Autowired
    private BeanFactory beanFactory;

    private Logger logger = LoggerFactory.getLogger(LanguageController.class);

    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public @ResponseBody ResponseData query(HttpServletRequest request, Language example,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
        IRequest iRequest = createRequestContext(request);
        return new ResponseData(languageService.select(iRequest, example, page, pagesize));
    }

    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData submit(HttpServletRequest request, @RequestBody List<Language> languageList,
            BindingResult result) {
        getValidator().validate(languageList, result);
        if (result.hasErrors()) {
            ResponseData rs = new ResponseData(false);
            rs.setMessage(getErrorMessage(result, request));
            return rs;
        }
        IRequest iRequest = createRequestContext(request);
        return new ResponseData(languageService.batchUpdate(iRequest, languageList));
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<Language> languageList) {
        languageService.batchDelete(languageList);
        return new ResponseData();
    }

    @RequestMapping(value = "/init")
    public ResponseData initLanguage(HttpServletRequest request, @RequestParam String newLang,
            @RequestParam(defaultValue = BaseConstants.DEFAULT_LANG) String seedLang) throws Exception {
        Object dataSource = beanFactory.getBean("dataSource");
        Map<String, Object> resMap = new HashMap<>();
        if (!(dataSource instanceof DataSource)) {
            return new ResponseData(false);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("start language init, {} -> {}", seedLang, newLang);
        }
        try (Connection conn = ((DataSource) dataSource).getConnection()) {
            DatabaseMetaData metaData = conn.getMetaData();
            // step 1, get all TL tables.
            ArrayList<String> tlTables = new ArrayList<>();
            try (ResultSet rs = metaData.getTables(conn.getCatalog(), conn.getSchema(), "%_TL",
                    new String[] { "TABLE" })) {
                while (rs.next()) {
                    String tableName = rs.getString("TABLE_NAME");
                    // check again, '%_TL' maybe match something like 'TTL'
                    if (tableName.endsWith("_TL")) {
                        tlTables.add(tableName);
                    }
                }
            }
            if (logger.isDebugEnabled()) {
                logger.debug("total {} tables to init.", tlTables.size());
            }
            ArrayList<String> columns = new ArrayList<>();
            ArrayList<String> pks = new ArrayList<>();
            StringBuilder sqlBuilder = new StringBuilder();
            // step 2, do for all TL tables.
            int idx = 0;
            for (String table : tlTables) {
                columns.clear();
                pks.clear();
                sqlBuilder.setLength(0);
                idx++;
                try {
                    // step 2.1 get all columns of a table
                    try (ResultSet rs = metaData.getColumns(conn.getCatalog(), conn.getSchema(), table, "%")) {
                        while (rs.next()) {
                            columns.add(rs.getString("COLUMN_NAME"));
                        }
                    }
                    try (ResultSet rs = metaData.getPrimaryKeys(conn.getCatalog(), conn.getSchema(), table)) {
                        while (rs.next()) {
                            String pk = rs.getString("COLUMN_NAME");
                            if (!pk.equalsIgnoreCase("LANG")) {
                                pks.add(pk);
                            }
                        }
                    }
                    String columnJoin = StringUtils.join(columns, ",");
                    // System.out.println(columnJoin);
                    // step 2.2 generate sql
                    sqlBuilder.append("INSERT INTO ").append(table).append(" (").append(columnJoin).append(")");
                    for (int i = 0; i < columns.size(); i++) {
                        if (columns.get(i).equalsIgnoreCase("LANG")) {
                            columns.set(i, "?"); // :1 newLang
                        } else {
                            columns.set(i, "t0." + columns.get(i));
                        }
                    }
                    sqlBuilder.append(" SELECT ").append(StringUtils.join(columns, ","));
                    sqlBuilder.append(" FROM ").append(table).append(" t0 WHERE ");
                    sqlBuilder.append("t0.LANG=?"); // :2 seedLang
                    if (!pks.isEmpty()) {
                        sqlBuilder.append(" AND NOT EXISTS (");
                        sqlBuilder.append("SELECT 1 FROM ").append(table).append(" t1 WHERE ");
                        sqlBuilder.append("t1.LANG=?"); // :3 newLang
                        for (String p : pks) {
                            sqlBuilder.append(" AND t1.").append(p).append("=t0.").append(p);
                        }
                        sqlBuilder.append(")");
                    }
                    if (logger.isDebugEnabled()) {
                        logger.debug("{}/{}, execute: {}", idx, tlTables.size(), sqlBuilder.toString());
                    }

                    // step 2.3 execute sql
                    // try (PreparedStatement ps =
                    // conn.prepareStatement(sqlBuilder.toString())) {
                    // ps.setString(1, newLang);
                    // ps.setString(2, seedLang);
                    // if (!pks.isEmpty()) {
                    // ps.setString(3, newLang);
                    // }
                    // ps.execute();
                    // int c = ps.getUpdateCount();
                    // if (logger.isDebugEnabled()) {
                    // logger.debug("insert rows:{}", table, seedLang, newLang,
                    // c);
                    // }
                    // resMap.put(table, c);
                    // }
                } catch (Exception e) {
                    if (logger.isErrorEnabled()) {
                        logger.error("error while copy {} data from {} to {}", table, seedLang, newLang);
                    }
                    resMap.put(table, e.getMessage());
                }

            }
        }
        return new ResponseData(Collections.singletonList(resMap));
    }

}
