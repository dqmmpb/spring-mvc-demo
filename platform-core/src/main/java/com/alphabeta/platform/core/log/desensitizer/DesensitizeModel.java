package com.alphabeta.platform.core.log.desensitizer;

import java.io.Serializable;

public class DesensitizeModel implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String expression;

    public String getExpression() {
        return this.expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    private String replacement;

    public String getReplacement() {
        return this.replacement;
    }

    public void setReplacement(String replacement) {
        this.replacement = replacement;
    }

    private String fieldName;

    public String getFieldName() {
        return this.fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    // 字段类型：密码，银行卡..
    private String fieldType;

    public String getFieldType() {
        return this.fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }
}
