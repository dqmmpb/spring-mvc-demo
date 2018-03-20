package com.alphabeta.platform.util;

import com.alphabeta.platform.i18n.I18NMgr;
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
        return I18NMgr.getMessage(key, locale);
    }

    public static String getMessage(String key) {
        return I18NMgr.getMessage(key);
    }

}
