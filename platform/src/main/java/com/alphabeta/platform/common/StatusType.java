package com.alphabeta.platform.common;

/**
 * 启用禁用类型
 */
public enum StatusType {
    UNLOCK("启用", 1),
    LOCK("禁用", 2);

    private int value;
    private String name;

    StatusType(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

}
