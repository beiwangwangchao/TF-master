/*
 *
 */
package com.lkkhpg.dsis.common.enums;

/**
 * 生成编码枚举.
 * 
 * @author wuyichu
 */
public enum SequenceType {

    AUTOSHIP("AU"), SALESORDER("SO"), AUTOSHIPBATCH("AUTOSHIPBATCH"), RETURNORDER("RM"), CREDITNOTE("CN");

    private String code;

    SequenceType(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return this.code;
    }

}
