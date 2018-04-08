package com.alphabeta.platform.core.web.common;

public enum ErrorCode {

    // 核心异常（4位异常代码，前2位为模块代码，后2位为模块内部代码）
    ERROR_SESSION_TIMEOUT(2001, "Session过期");

    private int code;
    private String name;

    ErrorCode(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getCode() {
        return code;
    }
}
