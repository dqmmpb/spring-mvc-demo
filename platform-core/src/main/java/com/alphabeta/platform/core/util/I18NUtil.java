package com.alphabeta.platform.core.util;

import com.alphabeta.platform.core.i18n.I18NMgr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Locale;

import static com.alphabeta.platform.core.common.ErrorCode.ERROR_SYS_EXCEPTION;

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

    private static String getCodeToString(Enum<?> errorCode) {
        try {
            Method method = errorCode.getClass().getMethod("getCode");
            Object errorCodeInteger = method.invoke(errorCode);
            return "" + errorCodeInteger;
        } catch (NoSuchMethodException e) {
            return ERROR_SYS_EXCEPTION.getCodeString();
        } catch (IllegalAccessException e) {
            return ERROR_SYS_EXCEPTION.getCodeString();
        } catch (InvocationTargetException e) {
            return ERROR_SYS_EXCEPTION.getCodeString();
        }
    }

    private static String getCodeString(Enum<?> errorCode) {
        try {
            Method method = errorCode.getClass().getMethod("getCodeString");
            Object errorCodeString = method.invoke(errorCode);
            return (String) errorCodeString;
        } catch (NoSuchMethodException e) {
            return getCodeToString(errorCode);
        } catch (IllegalAccessException e) {
            return getCodeToString(errorCode);
        } catch (InvocationTargetException e) {
            return getCodeToString(errorCode);
        }
    }

    public static String getMessage(Enum<?> errorCode, Object[] params, String defaultMessage, Locale locale) {
        String errorCodeString = getCodeString(errorCode);
        return I18NMgr.getMessage(errorCodeString, params, defaultMessage, locale);
    }

    public static String getMessage(Enum<?> errorCode, String defaultMessage, Locale locale) {
        String errorCodeString = getCodeString(errorCode);
        return I18NMgr.getMessage(errorCodeString, null, defaultMessage, locale);
    }

    public static String getMessage(Enum<?> errorCode, Object[] params, Locale locale) {
        String errorCodeString = getCodeString(errorCode);
        return I18NMgr.getMessage(errorCodeString, params, locale);
    }

    public static String getMessage(Enum<?> errorCode, Object[] params) {
        String errorCodeString = getCodeString(errorCode);
        return getMessage(errorCodeString, params);
    }

    public static String getMessage(Enum<?> errorCode, Locale locale) {
        String errorCodeString = getCodeString(errorCode);
        return getMessage(errorCodeString, locale);
    }

    public static String getMessage(Enum<?> errorCode) {
        String errorCodeString = getCodeString(errorCode);
        return getMessage(errorCodeString);
    }

}
