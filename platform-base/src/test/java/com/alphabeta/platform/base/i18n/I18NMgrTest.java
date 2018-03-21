package com.alphabeta.platform.base.i18n;

import com.alphabeta.platform.base.BaseTest;
import com.alphabeta.platform.core.exception.BaseAppException;
import com.alphabeta.platform.core.exception.ExceptionHandler;
import com.alphabeta.platform.core.i18n.I18NMgr;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

import static com.alphabeta.platform.base.common.ErrorCode.PRIV_CODE_HAS_EXIST;
import static com.alphabeta.platform.core.common.ErrorCode.ERROR_SYS_EXCEPTION;


public class I18NMgrTest extends BaseTest {

    @Test
    public void testGetMessageEn() {
        try {
            ExceptionHandler.publish(PRIV_CODE_HAS_EXIST);
        } catch (BaseAppException e) {
            Assert.assertEquals("Priv code has exist", I18NMgr.getMessage(e.getCode()));
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
            ExceptionHandler.publish(PRIV_CODE_HAS_EXIST);
        } catch (BaseAppException e) {
            Assert.assertEquals("权限编码已存在", I18NMgr.getMessage(e.getCode(), Locale.CHINESE));
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
            ExceptionHandler.publish(PRIV_CODE_HAS_EXIST);
        } catch (BaseAppException e) {
            Assert.assertEquals("权限编码已存在", I18NMgr.getMessage(e.getCode()));
        }
        try {
            LocaleContextHolder.setLocale(Locale.ENGLISH);
            ExceptionHandler.publish(PRIV_CODE_HAS_EXIST);
        } catch (BaseAppException e) {
            Assert.assertEquals("Priv code has exist", I18NMgr.getMessage(e.getCode()));
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
