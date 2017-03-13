package com.miapc.ipudong.constant;

/**
 * Created by wangwei on 2016/10/28.
 */
public enum DatePatten {
    /**
     * Yyyy mm date patten.
     */
    YYYY_MM("yyyy-MM"),
    /**
     * Yyyy mm dd date patten.
     */
    YYYY_MM_DD("yyyy-MM-dd"),
    /**
     * The Mm dd hh mm.
     */
    MM_DD_HH_MM("MM-dd HH:mm"),
    /**
     * The Mm dd hh mm ss.
     */
    MM_DD_HH_MM_SS("MM-dd HH:mm:ss"),
    /**
     * The Yyyy mm dd hh mm.
     */
    YYYY_MM_DD_HH_MM("yyyy-MM-dd HH:mm"),
    /**
     * The Yyyy mm dd hh mm ss.
     */
    YYYY_MM_DD_HH_MM_SS("yyyy-MM-dd HH:mm:ss");

    private String value;

    DatePatten(String value) {
        this.value = value;
    }

    /**
     * Gets value.
     *
     * @return the value
     */
    public String getValue() {
        return value;
    }
}
