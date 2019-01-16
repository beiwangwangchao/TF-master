/*
 *
 */
package com.lkkhpg.dsis.platform.controllers;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lkkhpg.dsis.platform.cache.Cache;
import com.lkkhpg.dsis.platform.cache.CacheManager;
import com.lkkhpg.dsis.platform.cache.impl.HashStringRedisCache;
import com.lkkhpg.dsis.platform.cache.impl.SysCodeCache;
import com.lkkhpg.dsis.platform.core.ILanguageProvider;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.impl.DefaultTlTableNameProvider;
import com.lkkhpg.dsis.platform.dto.DTOClassInfo;
import com.lkkhpg.dsis.platform.dto.ResponseData;
import com.lkkhpg.dsis.platform.dto.system.Code;
import com.lkkhpg.dsis.platform.dto.system.Language;
import com.lkkhpg.dsis.platform.mapper.system.MultiLanguageMapper;
import com.lkkhpg.dsis.platform.service.ILovService;
import com.lkkhpg.dsis.platform.service.IProfileService;

/**
 * 通用的 Controller,用来获取公共数据.
 *
 * @author shengyang.zhou@hand-china.com
 * @author njq.niu@hand-china.com
 */
@RestController
public class CommonController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(CommonController.class);

    @Autowired
    private MultiLanguageMapper multiLanguageMapper;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private ILovService commonLovService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ILanguageProvider languageProvider;

    @Autowired
    private IProfileService profileService;

    /**
     * 获取通用数据.
     * 
     * @param resource
     *            资源类型
     * @param params
     *            参数
     * @param request
     *            HttpServletRequest
     * @return json值
     * @throws JsonProcessingException
     *             对象转 JSON 可能出现的异常
     */
    @RequestMapping(value = "/common/{resource}", produces = "application/javascript;charset=utf8")
    @ResponseBody
    public String getCommonData(@PathVariable String resource, @RequestParam Map<String, String> params,
            HttpServletRequest request) throws JsonProcessingException {
        Locale locale = RequestContextUtils.getLocale(request);
        String lang = locale == null ? null : locale.toString();
        Cache<?> cache = cacheManager.getCache(resource);
        String var = params.get("var");
        StringBuilder sb = new StringBuilder();
        if (cache instanceof SysCodeCache) {
            params.forEach((k, v) -> {
                Code code = ((SysCodeCache) cache).getValue(v + "." + lang);
                try {
                    if (code == null) {
                        toJson(sb, k, Collections.EMPTY_LIST);
                    } else {
                        toJson(sb, k, code.getCodeValues());
                    }
                    sb.append("\n");
                } catch (JsonProcessingException e) {
                    if (logger.isErrorEnabled()) {
                        logger.error(e.getMessage(), e);
                    }
                }
            });
        } else if (cache instanceof HashStringRedisCache) {
            List<?> data = ((HashStringRedisCache<?>) cache).getAll();
            toJson(sb, var, data);
        }
        return sb.toString();
    }

    /**
     * 取 快码 专用(仅限一个).
     * <p>
     * 仅返回code 的内容(作为数组),没有额外的内容.
     * 
     * @param code
     *            快码 code
     * @param request
     *            request
     * @return json array
     * @throws JsonProcessingException
     *             对象转 JSON 可能出现的异常
     */
    @RequestMapping(value = "/common/code/{code}/", produces = "application/javascript;charset=utf8")
    @ResponseBody
    public String getCommonCode(@PathVariable String code, HttpServletRequest request) throws JsonProcessingException {
        Locale locale = RequestContextUtils.getLocale(request);
        SysCodeCache codeCache = (SysCodeCache) (Cache) cacheManager.getCache("code");
        Code code2 = codeCache.getValue(code + "." + locale);

        if (code2 == null || code2.getCodeValues() == null) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder();
        toJson(sb, null, code2.getCodeValues());
        return sb.toString();
    }

    /**
     * 通用LOV的查询url.
     * 
     * @param id
     *            lovId
     * @param page
     *            起始页
     * @param pagesize
     *            分页大小
     * @param params
     *            参数
     * @param request
     *            HttpServletRequest
     * @return ResponseData ResponseData
     */
    @RequestMapping(value = "/common/lov/{id}")
    @ResponseBody
    public ResponseData getLovDatas(@PathVariable String id, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize, @RequestParam Map<String, String> params,
            HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(commonLovService.selectDatas(requestContext, id, params, page, pagesize));
    }

    @RequestMapping(value = "/{folder1}/{name}.html")
    public ModelAndView renderFolder1View(@PathVariable String folder1, @PathVariable String name, Model model) {
        return new ModelAndView(
                new StringBuilder(getViewPath()).append("/").append(folder1).append("/").append(name).toString());
    }

    @RequestMapping(value = "/{folder1}/{folder2}/{name}.html")
    public ModelAndView renderFolder2View(@PathVariable String folder1, @PathVariable String folder2,
            @PathVariable String name, Model model) {
        return new ModelAndView(new StringBuilder(getViewPath()).append("/").append(folder1).append("/").append(folder2)
                .append("/").append(name).toString());
    }

    @RequestMapping(value = "/{folder1}/{folder2}/{folder3}/{name}.html")
    public ModelAndView renderFolder3View(@PathVariable String folder1, @PathVariable String folder2,
            @PathVariable String folder3, @PathVariable String name, Model model) {
        return new ModelAndView(new StringBuilder(getViewPath()).append("/").append(folder1).append("/").append(folder2)
                .append("/").append(folder3).append("/").append(name).toString());
    }

    @RequestMapping(value = "/{folder1}/{folder2}/{folder3}/{folder4}/{name}.html")
    public ModelAndView renderFolder4View(@PathVariable String folder1, @PathVariable String folder2,
            @PathVariable String folder3, @PathVariable String folder4, @PathVariable String name, Model model) {
        return new ModelAndView(new StringBuilder(getViewPath()).append("/").append(folder1).append("/").append(folder2)
                .append("/").append(folder3).append("/").append(folder4).append("/").append(name).toString());
    }

    @RequestMapping(value = "/{name}.html")
    public ModelAndView renderView(@PathVariable String name, Model model) {
        return new ModelAndView(name);
    }

    protected void toJson(StringBuilder sb, String var, Object data) throws JsonProcessingException {
        boolean hasVar = var != null && var.length() > 0;
        if (hasVar) {
            sb.append("var ").append(var).append('=');
        }
        sb.append(objectMapper.writeValueAsString(data));
        if (hasVar) {
            sb.append(';');
        }
    }

    /**
     * 处理多语言字段.
     *
     * @param request
     *            HttpServletRequest
     * @param id
     *            主键值
     * @param dto
     *            dto全名
     * @param field
     *            多语言字段名称(dto中的属性名)
     * @return 视图
     */
    @RequestMapping(value = "sys/sys_multilanguage_editor.html")
    public ModelAndView loadMultiLanguageFields(HttpServletRequest request, @RequestParam String id,
            @RequestParam String dto, @RequestParam String field) {
        ModelAndView view = new ModelAndView(getViewPath() + "/sys/sys_multilanguage_editor");
        if (StringUtils.isNotEmpty(id) && StringUtils.isNotEmpty(dto) && StringUtils.isNotEmpty(field)) {
            Class<?> clazz;
            Column idCol = null, tlCol = null;
            try {
                clazz = Class.forName(dto);
                Table table = clazz.getAnnotation(Table.class);
                for (Field f : DTOClassInfo.getIdFields(clazz)) {
                    if (f.getAnnotation(Column.class) != null) {
                        idCol = f.getAnnotation(Column.class);
                        break;
                    }
                }
                Field tlField = ReflectionUtils.findField(clazz, field);
                if (tlField != null) {
                    tlCol = tlField.getAnnotation(Column.class);
                }
                if (table != null && idCol != null && tlCol != null) {
                    Map<String, String> map = new HashMap<>();
                    map.put("table", DefaultTlTableNameProvider.getInstance().getTlTableName(table.name()));
                    map.put("idName", idCol.name());
                    map.put("tlName", tlCol.name());
                    map.put("id", id);
                    List list = multiLanguageMapper.select(map);
                    view.addObject("list", list);
                }
            } catch (Exception e) {
                if (logger.isErrorEnabled()) {
                    logger.error(e.getMessage(), e);
                }
            }
        } else {
            List<Language> list = languageProvider.getSupportedLanguages();
            list.sort((a, b) -> {
                return a.getLangCode().compareTo(b.getLangCode());
            });
            view.addObject("list", list);
        }
        return view;
    }

    /**
     * 获取配置文件.
     * 
     * @param params
     *            参数
     * @param request
     *            HttpServletRequest
     * @return json值
     * @throws JsonProcessingException
     *             对象转 JSON 可能出现的异常
     */
    @RequestMapping(value = "/common/profile", produces = "application/javascript;charset=utf8")
    @ResponseBody
    public String getProfile(@RequestParam Map<String, String> params,
            HttpServletRequest request) throws JsonProcessingException {
        IRequest requestContext = createRequestContext(request);

        StringBuilder sb = new StringBuilder();
        params.forEach((k, v) -> {
            String value = profileService.getProfileValue(requestContext, v);
            try {
                toJson(sb, k, value);
                sb.append("\n");
            } catch (JsonProcessingException e) {
                if (logger.isErrorEnabled()) {
                    logger.error(e.getMessage(), e);
                }
            }
        });
        
        return sb.toString();
    }
}
