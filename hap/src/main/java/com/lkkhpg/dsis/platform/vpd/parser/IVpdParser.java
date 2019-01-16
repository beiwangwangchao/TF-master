/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.platform.vpd.parser;

import com.lkkhpg.dsis.platform.vpd.exception.VpdParseErrorException;

import net.sf.jsqlparser.JSQLParserException;

/**
 * VPD解析器.
 * @author chenjingxiong
 */
public interface IVpdParser {
    
    boolean canParse(String mapperId);
    
    String parseAndBuild(String mapperId, String sql) throws JSQLParserException, VpdParseErrorException;
}
