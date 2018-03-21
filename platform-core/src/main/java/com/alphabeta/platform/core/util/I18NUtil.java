package com.alphabeta.platform.core.util;

import com.alphabeta.platform.core.i18n.I18NMgr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    public static String getMessage(Enum<?> errorCode, Locale locale) {
        try {
            Method method = errorCode.getClass().getMethod("getCodeString");
            Object errorCodeString = method.invoke(errorCode);
            return getMessage((String) errorCodeString, locale);
        } catch (Exception e) {
            try {
                Method method = errorCode.getClass().getMethod("getCode");
                Object errorCodeInteger = method.invoke(errorCode);
                return getMessage("" + errorCodeInteger, locale);
            } catch (Exception ie) {
                return getMessage(ERROR_SYS_EXCEPTION, locale);
            }
        }
    }

    public static String getMessage(String key, Locale locale) {
        return I18NMgr.getMessage(key, locale);
    }

    public static String getMessage(String key) {
        return I18NMgr.getMessage(key);
    }

}
