package com.alphabeta.platform.web.util;

import com.alphabeta.platform.core.exception.BaseAppException;
import com.alphabeta.platform.core.exception.ExceptionHandler;
import com.alphabeta.platform.core.i18n.I18NMgr;
import com.alphabeta.platform.core.util.I18NUtil;
import com.alphabeta.platform.web.BaseTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.i18n.LocaleContextHolder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.alphabeta.platform.base.common.ErrorCode.PRIV_CODE_HAS_EXIST;
import static com.alphabeta.platform.web.common.ErrorCode.I18N_MESSAGE_TEST;
import static com.alphabeta.platform.web.common.ErrorCode.I18N_MESSAGE_TEST_PARAMS;

public class I18NUtilTest extends BaseTest {

    @Test
    public void getMessageEn() {
        LocaleContextHolder.setLocale(Locale.ENGLISH);
        try {
            ExceptionHandler.publish(PRIV_CODE_HAS_EXIST);
        } catch (BaseAppException e) {
//            e.printStackTrace();
            Assert.assertEquals("Priv Code Has Exist", I18NMgr.getMessage(e.getCode()));
        }
    }

    @Test
    public void getMessageZh() {
        LocaleContextHolder.setLocale(Locale.CHINESE);
        try {
            ExceptionHandler.publish(I18N_MESSAGE_TEST);
        } catch (BaseAppException e) {
//            e.printStackTrace();
            Assert.assertEquals("测试国际化", I18NMgr.getMessage(e.getCode()));
        }

        // 使用标准日期格式定义i18n中的日期格式化
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            // 测试参数化的Message
            ExceptionHandler.publish(I18N_MESSAGE_TEST_PARAMS,
                I18NUtil.getMessage(I18N_MESSAGE_TEST_PARAMS, new Object[]{
                    "deng.qiming",
                    now,
                }));
        } catch (BaseAppException e) {
//            e.printStackTrace();
            Assert.assertEquals("测试参数化。欢迎deng.qiming，今天是" + sdf.format(now) + "！", e.getDesc());
        }
    }
}
