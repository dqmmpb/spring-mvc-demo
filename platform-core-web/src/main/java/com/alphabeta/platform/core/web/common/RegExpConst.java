package com.alphabeta.platform.core.web.common;

/**
 * Created with IntelliJ IDEA.
 * User: Lelia
 * Datetime: 2018/3/2 15:39
 * Description:
 */
public class RegExpConst {
    public final static String REGEXP_PHONE = "^1\\d{10}$"; // 手机号
    public final static String REGEXP_EMAIL = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";//邮箱
    /**
     * 字母、数字或符号的组合
     */
    public final static String REGEXP_PASSWORD = "^[\\x21-\\x7E]{6,20}$"; //密码
}
