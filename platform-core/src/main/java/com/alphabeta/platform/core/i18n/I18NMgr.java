package com.alphabeta.platform.core.i18n;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Locale;

/**
 * 文案方案
 *
 * @author deng.qiming
 * @date 2016年11月8日 下午2:26:07
 */
@Component
public class I18NMgr {

    private static final Logger logger = LoggerFactory.getLogger(I18NMgr.class);

    @Resource
    private MessageSource resources;

    public static I18NMgr i18NMgr;

    @PostConstruct
    public void init() {
        i18NMgr = this;
        i18NMgr.resources = this.resources;
    }

    public static synchronized I18NMgr getInstance() {
        return i18NMgr;
    }

    public static String getMessage(String key, Object[] params, String defaultMessage, Locale locale) {
        return i18NMgr.resources.getMessage(key, params, defaultMessage, locale);
    }

    public static String getMessage(String key, String defaultMessage, Locale locale) {
        return i18NMgr.resources.getMessage(key, null, defaultMessage, locale);
    }

    public static String getMessage(String key, Object[] params, Locale locale) {
        return i18NMgr.resources.getMessage(key, params, locale);
    }

    public static String getMessage(String key, Locale locale) {
        return i18NMgr.resources.getMessage(key, null, locale);
    }

    public static String getMessage(String key, Object[] params) {
        // 使用LocaleContextHolder获取上下文的Locale信息
        Locale locale = LocaleContextHolder.getLocale();
        return i18NMgr.resources.getMessage(key, params, locale);
    }

    public static String getMessage(String key) {
        // 使用LocaleContextHolder获取上下文的Locale信息
        Locale locale = LocaleContextHolder.getLocale();
        return i18NMgr.resources.getMessage(key, null, locale);
    }
}
