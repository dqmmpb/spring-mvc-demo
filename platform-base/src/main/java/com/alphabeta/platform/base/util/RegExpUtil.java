package com.alphabeta.platform.base.util;

import com.alphabeta.platform.base.common.RegExpConst;

import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: Lelia
 * Datetime: 2018/3/2 15:40
 * Description:
 */
public class RegExpUtil {
    /**
     * 校验手机号
     *
     * @param phone
     * @return
     */
    public static boolean isPhone(String phone) {
        return Pattern.matches(RegExpConst.PHONE, phone);
    }

    /**
     * 校验邮箱
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        return Pattern.matches(RegExpConst.EMAIL, email);
    }

    /**
     * 校验密码：字母、数字或符号的组合
     *
     * @param password
     * @return
     */
    public static boolean isPassword(String password) {
        return Pattern.matches(RegExpConst.PASSWORD, password);
    }
}
