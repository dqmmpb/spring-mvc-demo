package com.alphabeta.platform.web.common;

public enum ErrorCode {

    I18N_MESSAGE_TEST(101, "测试国际化"),
    I18N_MESSAGE_TEST_PARAMS(102, "测试国际化-参数化"),

    // 业务异常（5位异常代码，前3位为模块代码，后2位为模块内部代码）
    // 基本业务异常(100)
    PARAMS_SIGN_FAILED_ERROR(10001, "参数签名错误"),
    INVALID_PARAMS_ERROR(10002, "参数错误"),
    INVALID_PARAMS_FORMAT_ERROR(10003, "参数格式错误"),
    ENCRYPT_ERROR(10004, "参数加密失败"),;

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
