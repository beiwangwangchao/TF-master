/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.platform.service;

import java.util.List;

import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;
import com.lkkhpg.dsis.platform.dto.menu.MenuItem;
import com.lkkhpg.dsis.platform.dto.system.Function;
import com.lkkhpg.dsis.platform.dto.system.FunctionDisplay;
import com.lkkhpg.dsis.platform.dto.system.Resource;

/**
 * 功能服务接口.
 * 
 * @author wuyichu
 */
public interface IFunctionService extends ProxySelf<IFunctionService> {

    /**
     * 查询所有菜单.
     * 
     * @param request
     *            上下文请求
     * @return 返回所有的菜单
     */
    List<MenuItem> selectAllMenus(IRequest request);

    /**
     * 查询当前角色的菜单.
     * 
     * @param request
     *            上下文请求
     * @return 返回当前角色的菜单集合
     */
    List<MenuItem> selectRoleFunctions(IRequest request);

    /**
     * 新增功能.
     * 
     * @param request
     *            上下文请求
     * @param function
     *            功能
     * @return 返回新增后的功能信息
     */
    Function createFunction(IRequest request, @StdWho Function function);

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
    Function updateFunction(IRequest request, @StdWho Function function, boolean updateResource);

    /**
     * 根据ID查询功能.
     * 
     * @param request
     *            上下文请求
     * @param functionId
     *            ID
     * @return 功能信息
     */
    Function selectFunctionById(IRequest request, Long functionId);

    /**
     * 根据功能条件查询.
     * 
     * @param request
     *            上下文请求
     * @param function
     *            请求参数
     * @param page
     *            页码
     * @param pageSize
     *            页数
     * @return 满足条件的功能
     */
    List<FunctionDisplay> selectFunction(IRequest request, Function function, int page, int pageSize);

    /**
     * 删除功能.
     * 
     * @param request
     *            上下文请求
     * @param function
     *            功能
     * @return 删除的行数
     */
    int deleteFunction(IRequest request, Function function);

    /**
     * 批量新增或修改.
     * 
     * @param request
     *            上下文请求
     * @param functions
     *            功能集合
     * @return 修改或新增过后的功能信息集合
     */
    List<Function> batchUpdate(IRequest request, @StdWho List<Function> functions);

    /**
     * 批量删除.
     * 
     * @param request
     *            上下文请求
     * @param functions
     *            功能集合
     * @return 删除的函数
     */
    int batchDelete(IRequest request, List<Function> functions);

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
    List<Resource> selectExitResourcesByFunction(IRequest request, Function function, Resource resource, int page,
            int pageSize);

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
    List<Resource> selectNotExitResourcesByFunction(IRequest request, Function function, Resource resource, int page,
            int pageSize);

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
    Function updateFunctionResources(IRequest request, Function function, List<Resource> resources);
}
