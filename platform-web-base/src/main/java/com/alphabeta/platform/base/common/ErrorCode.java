package com.alphabeta.platform.base.common;

public enum ErrorCode {

    // 业务核心异常（4位异常代码，前2位为模块代码，后2位为模块内部代码）

    // 用户异常
    USER_NAME_HAS_EXIST(4001, "帐号已存在"),
    USER_IS_NULL(4002, "用户为NULL"),
    USER_NOT_EXIST(4003, "用户不存在"),
    USER_NAME_OR_PWD_ERROR(4004, "用户名或密码错误"),
    USER_PWD_ERROR(4005, "密码错误"),
    USER_NOT_LOGIN(4006, "用户未登录"),
    USER_HAS_LOCKED(4007, "用户已禁用"),
    USER_HAS_DELETED(4008, "用户已删除"),
    USER_ORIGIN_PWD_ERROR(4009, "旧密码错误"),
    USER_PWD_LENGTH_ERROR(4010, "密码长度错误"),
    USER_CHANGE_PWD_ORIGIN_AND_NEW_ARE_SAME_ERROR(4011, "新密码不能和旧密码一致"),
    USER_LOCK_SELF_ERROR(4012, "用户不能禁用自己"),
    USER_SESSION_TIMEOUT(4013, "用户Session过期"),
    PHONE_FORMAT_ERROR(4014, "手机号格式错误"),
    PASSWORD_FORMAT_ERROR(4015, "密码格式错误"),
    VCODE_ERROR(4016, "验证码错误"),
    EXPIRE_VCODE(4017, "验证码已过期"),
    GET_VCODE_REPEATEDLY(4018, "重复获取验证码"),
    USER_NAME_FORMAT_ERROR(4019, "用户名不合法"),
    USER_AUDIT_NOT_PASS(4020, "用户审核未通过，请重新注册"),
    USER_PASSWORD_RESET_ERROR(4021, "用户密码被重置"),

    // 用户角色异常
    USERROLE_HAS_EXIST(4101, "用户角色已存在"),
    ROLE_CODE_HAS_EXIST(4102, "角色编码已存在"),
    ROLE_HAS_USER_ROLE_REF(4103, "存在引用该角色的用户"),
    ROLE_IS_NULL(4104, "角色为NULL"),
    ROLE_NOT_EXIST(4105, "角色不存在"),

    // 角色权限异常
    ROLEPRIV_HAS_EXIST(4201, "角色权限已存在"),

    // 权限异常
    PRIV_IS_NULL(4301, "权限为NULL"),
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
