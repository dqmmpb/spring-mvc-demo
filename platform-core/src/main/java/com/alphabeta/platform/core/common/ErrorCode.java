package com.alphabeta.platform.core.common;

public enum ErrorCode {

    // 系统核心异常（4位异常代码，前2位为模块代码，后2位为模块内部代码）

    // 系统异常
    ERROR_SYS_EXCEPTION(1001, "系统异常错误"),
    ERROR_INVALID_REQUEST(1002, "非法请求"),

    PRIV_CODE_HAS_EXIST(4302, "权限编码已存在"),
    PRIV_HAS_ROLE_PRIV_REF(4303, "存在引用该权限的角色"),
    NO_PERMISSION(4304, "无权限"),;

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

    public String getCodeString() {
        return "" + code;
    }
}
