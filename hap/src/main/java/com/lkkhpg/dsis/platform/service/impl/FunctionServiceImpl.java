/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.platform.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.lkkhpg.dsis.platform.cache.impl.HashStringRedisCacheGroup;
import com.lkkhpg.dsis.platform.core.ILanguageProvider;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.impl.RequestHelper;
import com.lkkhpg.dsis.platform.dto.menu.MenuItem;
import com.lkkhpg.dsis.platform.dto.system.Function;
import com.lkkhpg.dsis.platform.dto.system.FunctionDisplay;
import com.lkkhpg.dsis.platform.dto.system.FunctionResource;
import com.lkkhpg.dsis.platform.dto.system.Language;
import com.lkkhpg.dsis.platform.dto.system.Resource;
import com.lkkhpg.dsis.platform.mapper.system.FunctionMapper;
import com.lkkhpg.dsis.platform.mapper.system.FunctionResourceMapper;
import com.lkkhpg.dsis.platform.mapper.system.RoleFunctionMapper;
import com.lkkhpg.dsis.platform.service.IFunctionService;
import com.lkkhpg.dsis.platform.service.IResourceService;
import com.lkkhpg.dsis.platform.service.IRoleFunctionService;

/**
 * 功能服务接口实现.
 * 
 * @author wuyichu
 */
@Transactional
@Service
public class FunctionServiceImpl implements IFunctionService {

    @Autowired
    private FunctionMapper functionMapper;

    @Autowired
    private FunctionResourceMapper functionResourceMapper;

    @Autowired
    private RoleFunctionMapper roleFunctionMapper;

    @Autowired
    private IResourceService resourceService;

    @Autowired
    private IRoleFunctionService roleFunctionService;

    @Autowired
    @Qualifier("functionCache")
    private HashStringRedisCacheGroup<Function> functionCache;

    @Autowired
    private ILanguageProvider languageProvider;

    /**
     * 新增功能.
     * 
     * @param request
     *            上下文请求
     * @param function
     *            功能
     * @return 返回新增后的功能信息
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Function createFunction(IRequest request, Function function) {
        if (function == null) {
            return null;
        }
        functionMapper.insert(function);
        reloadFunctionCache(function.getFunctionId());
        return function;
    }

    /**
     * 功能修改.
     * 
     * @param request
     *            上下文请求
     * @param function
     *            功能
     * @param updateResource
     *            是否修改资源
     * @return 修改后的功能信息
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Function updateFunction(IRequest request, Function function, boolean updateResource) {
        if (function == null) {
            return null;
        }
        functionMapper.updateByPrimaryKey(function);
        reloadFunctionCache(function.getFunctionId());
        List<Resource> resources = function.getResources();
        if (updateResource) {
            self().updateFunctionResources(request, function, resources);
        }
        return function;
    }

    private void reloadFunctionCache(Long functionId) {
        IRequest old = RequestHelper.getCurrentRequest();
        try {
            IRequest iRequest = RequestHelper.newEmptyRequest();
            RequestHelper.setCurrentRequest(iRequest);
            for (Language lang : languageProvider.getSupportedLanguages()) {
                iRequest.setLocale(lang.getLangCode());
                Function function = functionMapper.selectByPrimaryKey(functionId);
                if (function != null) {
                    functionCache.getGroup(lang.getLangCode()).setValue(functionId.toString(), function);
                }
            }
        } finally {
            RequestHelper.setCurrentRequest(old);
        }
    }

    /**
     * 根据ID查询功能.
     * 
     * @param request
     *            上下文请求
     * @param functionId
     *            ID
     * @return 功能信息
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Function selectFunctionById(IRequest request, Long functionId) {
        if (functionId == null) {
            return null;
        }
        Function result = functionMapper.selectByPrimaryKey(functionId);
        if (request != null) {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("function", result);
            List<Resource> resources = functionMapper.selectExitResourcesByFunction(params);
            result.setResources(resources);
        }
        return result;
    }

    /**
     * 删除功能.
     * 
     * @param request
     *            上下文请求
     * @param function
     *            功能
     * @return 删除的行数
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int deleteFunction(IRequest request, Function function) {
        if (function.getFunctionId() == null) {
            return 0;
        }
        functionResourceMapper.deleteByFunctionId(function.getFunctionId());
        roleFunctionMapper.deleteByFunction(function);
        for (Language language : languageProvider.getSupportedLanguages()) {
            functionCache.getGroup(language.getLangCode()).remove(function.getFunctionId().toString());
        }
        return functionMapper.deleteByPrimaryKey(function);
    }

    /**
     * 批量新增或修改.
     * 
     * @param request
     *            上下文请求
     * @param functions
     *            功能集合
     * @return 修改或新增过后的功能信息集合
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<Function> batchUpdate(IRequest request, List<Function> functions) {
        if (functions == null || functions.isEmpty()) {
            return functions;
        }
        for (Function function : functions) {
            if (function.getFunctionId() == null) {
                self().createFunction(request, function);
            } else {
                self().updateFunction(request, function, Boolean.FALSE);
            }
        }
        return functions;
    }

    /**
     * 批量删除.
     * 
     * @param request
     *            上下文请求
     * @param functions
     *            功能集合
     * @return 删除的函数
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int batchDelete(IRequest request, List<Function> functions) {
        int result = 0;
        if (functions == null || functions.isEmpty()) {
            return result;
        }

        for (Function function : functions) {
            self().deleteFunction(request, function);
            result++;
        }
        return result;
    }

    /**
     * 根据功能条件查询.
     * 
     * @param request
     *            上下文请求
     * @param example
     *            请求参数
     * @param page
     *            页码
     * @param pageSize
     *            页数
     * @return 满足条件的功能
     */
    @Override
    public List<FunctionDisplay> selectFunction(IRequest request, Function example, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        List<FunctionDisplay> list = functionMapper.selectFunctions(example);
        List<Function> allFunctions = functionCache.getGroup(request.getLocale()).getAll();
        int len = allFunctions.size();
        list.forEach((function) -> {
            for (int i = 0; i < len; i++) {
                Function parent = allFunctions.get(i);
                if (parent.getFunctionId().equals(function.getParentFunctionId())) {
                    function.setParentFunctionName(parent.getFunctionName());
                    break;
                }
            }
        });
        return list;
    }

    /**
     * 查询function挂靠的resource.
     * 
     * @param request
     *            上下文请求
     * @param function
     *            功能
     * @param resource
     *            资源
     * @param page
     *            页码
     * @param pageSize
     *            页数
     * @return 满足条件的resource集合
     */
    @Override
    public List<Resource> selectExitResourcesByFunction(IRequest request, Function function, Resource resource,
            int page, int pageSize) {
        if (function == null || function.getFunctionId() == null) {
            return null;
        }
        PageHelper.startPage(page, pageSize);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("function", function);
        params.put("resource", resource);
        return functionMapper.selectExitResourcesByFunction(params);
    }

    /**
     * 查询function没有挂靠的resource.
     * 
     * @param request
     *            上下文请求
     * @param function
     *            功能
     * @param resource
     *            资源
     * @param page
     *            页码
     * @param pageSize
     *            页数
     * @return 返回满足条件的资源
     */
    @Override
    public List<Resource> selectNotExitResourcesByFunction(IRequest request, Function function, Resource resource,
            int page, int pageSize) {
        if (function == null || function.getFunctionId() == null) {
            return null;
        }
        PageHelper.startPage(page, pageSize);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("function", function);
        params.put("resource", resource);
        return functionMapper.selectNotExitResourcesByFunction(params);
    }

    /**
     * 修改功能挂靠的resource.
     * 
     * @param request
     *            上下文请求
     * @param function
     *            功能
     * @param resources
     *            资源集合
     * @return 修改后的功能信息
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Function updateFunctionResources(IRequest request, Function function, List<Resource> resources) {
        if (function != null) {
            functionResourceMapper.deleteByFunctionId(function.getFunctionId());
            if (resources != null && !resources.isEmpty()) {
                for (Resource resource : resources) {
                    FunctionResource functionResource = new FunctionResource();
                    functionResource.setResourceId(resource.getResourceId());
                    functionResource.setFunctionId(function.getFunctionId());
                    functionResource.setObjectVersionNumber(1L);
                    functionResource.setCreatedBy(request.getAccountId());
                    functionResource.setCreationDate(new Date());
                    functionResource.setLastUpdateDate(new Date());
                    functionResource.setLastUpdatedBy(request.getAccountId());
                    functionResourceMapper.insert(functionResource);
                }
            }
        }
        return null;
    }

    public List<MenuItem> selectAllMenus(IRequest request) {
        List<Function> functions = functionCache.getGroupAll(request.getLocale());
        MenuItem root = castFunctionsToMenuItem(functions);
        return root.getChildren();
    }

    private MenuItem castFunctionsToMenuItem(List<Function> functions) {
        MenuItem root = new MenuItem();
        List<MenuItem> children = new ArrayList<>();
        root.setChildren(children);
        Map<Long, MenuItem> map = new HashMap<>();
        Iterator<Function> iterator = functions.iterator();
        while (iterator.hasNext()) {
            Function function = iterator.next();
            if (function.getParentFunctionId() == null) {
                MenuItem rootChild = createMenuItem(function);
                map.put(function.getFunctionId(), rootChild);
                children.add(rootChild);
                iterator.remove();
            }
        }

        processFunctions(map, functions);
        map.forEach((k, v) -> {
            if (v.getChildren() != null) {
                Collections.sort(v.getChildren());
            }
        });
        Collections.sort(children);
        return root;
    }

    private void processFunctions(Map<Long, MenuItem> map, List<Function> functions) {
        Iterator<Function> iterator = functions.iterator();
        while (iterator.hasNext()) {
            Function function = iterator.next();
            MenuItem parent = map.get(function.getParentFunctionId());
            if (parent != null) {
                MenuItem item = createMenuItem(function);
                map.put(function.getFunctionId(), item);
                List<MenuItem> children = parent.getChildren();
                if (children == null) {
                    children = new ArrayList<>();
                    parent.setChildren(children);
                }
                children.add(item);
                iterator.remove();
            }
        }
        if (!functions.isEmpty()) {
            processFunctions(map, functions);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see IFunctionService#selectMenus(com.lkkhpg.dsis.platform.core.
     * IRequest)
     */
    @Override
    public List<MenuItem> selectRoleFunctions(IRequest request) {
        List<Function> functions = functionCache.getGroupAll(request.getLocale());
        Long[] ids = roleFunctionService.getRoleFunctionById(request.getRoleId());

        Map<Long, Function> funcMap = new HashMap<>();
        if (functions != null) {
            for (Function f : functions) {
                funcMap.put(f.getFunctionId(), f);
            }
        }
        Map<Long, MenuItem> menuMap = new HashMap<>();
        if (ids != null) {
            for (Long fId : ids) {
                createMenuRecursive(menuMap, funcMap, fId);
            }
        }
        List<MenuItem> itemList = new ArrayList<>();
        menuMap.forEach((k, v) -> {
            if (v.getParent() == null) {
                itemList.add(v);
            }
            if (v.getChildren() != null) {
                Collections.sort(v.getChildren());
            }
        });
        Collections.sort(itemList);
        return itemList;
    }

    private MenuItem createMenuRecursive(Map<Long, MenuItem> menuMap, Map<Long, Function> funcMap, Long funcId) {
        MenuItem mi = menuMap.get(funcId);
        if (mi == null) {
            Function func = funcMap.get(funcId);
            if (func == null) {
                // role has a function that dose not exists.
                return null;
            }
            mi = createMenuItem(func);
            menuMap.put(funcId, mi);
            // create parent mi
            Long parentId = func.getParentFunctionId();
            if (parentId != null) {
                MenuItem miParent = createMenuRecursive(menuMap, funcMap, parentId);
                if (miParent != null) {
                    List<MenuItem> children = miParent.getChildren();
                    if (children == null) {
                        children = new ArrayList<>();
                        miParent.setChildren(children);
                    }
                    mi.setParent(miParent);
                    children.add(mi);
                }
            }
        }
        return mi;
    }

    private MenuItem createMenuItem(Function function) {
        MenuItem menu = new MenuItem();
        menu.setText(function.getFunctionName());
        menu.setIcon(function.getFunctionIcon());
        menu.setFunctionCode(function.getFunctionCode());
        if (function.getResourceId() != null) {
            Resource resource = resourceService.selectResourceById(function.getResourceId());
            if (resource != null) {
                menu.setUrl(resource.getUrl());
            }
        }
        menu.setId(function.getFunctionId());
        menu.setScore(function.getFunctionSequence() != null ? function.getFunctionSequence() : 1);
        return menu;
    }

}
