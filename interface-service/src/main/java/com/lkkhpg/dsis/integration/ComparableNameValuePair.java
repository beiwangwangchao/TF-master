/*
 *
 */

package com.lkkhpg.dsis.integration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 参数键值对.
 * <p>
 * 可排序,按照 key 排序
 * 
 * @author shengyang.zhou@hand-china.com
 */
public class ComparableNameValuePair implements Comparable<ComparableNameValuePair> {

    private String name;
    private String value;

    public ComparableNameValuePair(String name, String value) {
        this();
        this.name = name;
        this.value = value;
    }

    public ComparableNameValuePair(Map.Entry<String, String> entry) {
        this(entry.getKey(), entry.getValue());
    }

    public ComparableNameValuePair() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public int compareTo(ComparableNameValuePair o) {
        return this.name.compareTo(o.name);
    }

    public static List<ComparableNameValuePair> toList(Map<String, String> map) {
        List<ComparableNameValuePair> list = new ArrayList<>();
        map.forEach((k, v) -> {
            list.add(new ComparableNameValuePair(k, v));
        });
        return list;
    }
}