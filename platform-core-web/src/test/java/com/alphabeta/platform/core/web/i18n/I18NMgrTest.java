package com.alphabeta.platform.core.web.i18n;

import com.alphabeta.platform.core.exception.BaseAppException;
import com.alphabeta.platform.core.exception.ExceptionHandler;
import com.alphabeta.platform.core.i18n.I18NMgr;
import com.alphabeta.platform.core.web.BaseTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

import static com.alphabeta.platform.core.common.ErrorCode.ERROR_SYS_EXCEPTION;
import static com.alphabeta.platform.core.web.common.ErrorCode.ERROR_SESSION_TIMEOUT;


public class I18NMgrTest extends BaseTest {

    @Test
    public void testGetMessageEn() {
        try {
            ExceptionHandler.publish(ERROR_SESSION_TIMEOUT);
        } catch (BaseAppException e) {
            Assert.assertEquals("Session timeout", I18NMgr.getMessage(e.getCode()));
        }

        try {
            ExceptionHandler.publish(ERROR_SYS_EXCEPTION);
        } catch (BaseAppException e) {
            Assert.assertEquals("System error", I18NMgr.getMessage(e.getCode()));
        }
    }

    @Test
    public void testGetMessageZh() {
        try {
            ExceptionHandler.publish(ERROR_SESSION_TIMEOUT);
        } catch (BaseAppException e) {
            Assert.assertEquals("Session过期", I18NMgr.getMessage(e.getCode(), Locale.CHINESE));
        }

        try {
            ExceptionHandler.publish(ERROR_SYS_EXCEPTION);
        } catch (BaseAppException e) {
            Assert.assertEquals("系统异常错误", I18NMgr.getMessage(e.getCode(), Locale.CHINESE));
        }
    }

    @Test
    public void testGetMessageChange() {
        try {
            LocaleContextHolder.setLocale(Locale.CHINESE);
            ExceptionHandler.publish(ERROR_SESSION_TIMEOUT);
        } catch (BaseAppException e) {
            Assert.assertEquals("Session过期", I18NMgr.getMessage(e.getCode()));
        }
        try {
            LocaleContextHolder.setLocale(Locale.ENGLISH);
            ExceptionHandler.publish(ERROR_SESSION_TIMEOUT);
        } catch (BaseAppException e) {
            Assert.assertEquals("Session timeout", I18NMgr.getMessage(e.getCode()));
        }

        try {
            LocaleContextHolder.setLocale(Locale.CHINESE);
            ExceptionHandler.publish(ERROR_SYS_EXCEPTION);
        } catch (BaseAppException e) {
            Assert.assertEquals("系统异常错误", I18NMgr.getMessage(e.getCode()));
        }
        try {
            LocaleContextHolder.setLocale(Locale.ENGLISH);
            ExceptionHandler.publish(ERROR_SYS_EXCEPTION);
        } catch (BaseAppException e) {
            Assert.assertEquals("System error", I18NMgr.getMessage(e.getCode()));
        }
    }

}
