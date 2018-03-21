package com.alphabeta.platform.base.common;

/**
 * 权限类型
 */
public enum PrivType {
    DIR("目录", 0),
    MENU("菜单", 1),
    DATA("数据", 2);

    private int value;
    private String name;

    PrivType(String name, int value) {
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
