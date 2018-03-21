package com.alphabeta.platform.core.exception;

import java.io.Serializable;

/**
 * 异常基类，所有异常需要继承此类
 *
 * @author deng.qiming
 * @date 2016年10月27日 下午1:54:11
 */
public class BaseAppException extends Exception implements Serializable {

    private static final long serialVersionUID = 1L;

    /***
     * 业务错误
     */
    private static final String ERROR_TYPE_BUSINESS = "BUS";

    private String code; // 编码
    private String desc; // 描述
    /***
     * 默认为业务错误，理论上业务错误不需要报警
     */
    private String errorType = ERROR_TYPE_BUSINESS;

    // 可扩展参数

    public BaseAppException() {
        super();
    }

    public BaseAppException(String code) {
        this.code = code;
    }

    public BaseAppException(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

}
