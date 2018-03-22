package com.alphabeta.platform.core.util;

import com.alphabeta.platform.core.i18n.I18NMgr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;

/**
 * Session Util
 *
 * @author deng.qiming
 * @date 2017-01-10 17:46
 */
public class I18NUtil {

    private static final Logger logger = LoggerFactory.getLogger(I18NMgr.class);

    public static String getMessage(String key, Object[] params, String defaultMessage, Locale locale) {
        return I18NMgr.getMessage(key, params, defaultMessage, locale);
    }

    public static String getMessage(String key, String defaultMessage, Locale locale) {
        return I18NMgr.getMessage(key, null, defaultMessage, locale);
    }

    public static String getMessage(String key, Object[] params, Locale locale) {
        return I18NMgr.getMessage(key, params, locale);
    }

    public static String getMessage(String key, Object[] params) {
        return I18NMgr.getMessage(key, params);
    }

    public static String getMessage(String key, Locale locale) {
        if (locale == null) {
            return getMessage(key);
        } else {
            return I18NMgr.getMessage(key, locale);
        }
    }

    public static String getMessage(String key) {
        return I18NMgr.getMessage(key);
    }

    public static String getMessage(Enum<?> errorCode, Object[] params, String defaultMessage, Locale locale) {
        String errorCodeString = CodeUtil.getCodeToString(errorCode);
        return I18NMgr.getMessage(errorCodeString, params, defaultMessage, locale);
    }

    public static String getMessage(Enum<?> errorCode, String defaultMessage, Locale locale) {
        String errorCodeString = CodeUtil.getCodeToString(errorCode);
        return I18NMgr.getMessage(errorCodeString, null, defaultMessage, locale);
    }

    public static String getMessage(Enum<?> errorCode, Object[] params, Locale locale) {
        String errorCodeString = CodeUtil.getCodeToString(errorCode);
        return I18NMgr.getMessage(errorCodeString, params, locale);
    }

    public static String getMessage(Enum<?> errorCode, Object[] params) {
        String errorCodeString = CodeUtil.getCodeToString(errorCode);
        return getMessage(errorCodeString, params);
    }

    public static String getMessage(Enum<?> errorCode, Locale locale) {
        String errorCodeString = CodeUtil.getCodeToString(errorCode);
        return getMessage(errorCodeString, locale);
    }

    public static String getMessage(Enum<?> errorCode) {
        String errorCodeString = CodeUtil.getCodeToString(errorCode);
        return getMessage(errorCodeString);
    }

}
