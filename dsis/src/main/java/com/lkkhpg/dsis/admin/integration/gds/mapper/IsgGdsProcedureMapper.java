/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.integration.gds.mapper;

import com.lkkhpg.dsis.admin.integration.gds.dto.IsgGdsProcedureParam;

/**
 * GDS接口存储过程MAPPER.
 * 
 * @author frank.li
 *
 */
public interface IsgGdsProcedureMapper {

    /**
     * 调用GDS接口存储过程.
     * 
     * @param isgGdsProcedureParam
     *            存储过程参数
     * @return isgGdsProcedureParam 存储过程参数
     */
    int invokeGdsProcedure(IsgGdsProcedureParam isgGdsProcedureParam);

    /**
     * 调用GDS接口存储过程.
     * 
     * @param isgGdsProcedureParam
     *            存储过程参数
     * @return isgGdsProcedureParam 存储过程参数
     */
    int invokeGdsProcedureTest(IsgGdsProcedureParam isgGdsProcedureParam);
}
