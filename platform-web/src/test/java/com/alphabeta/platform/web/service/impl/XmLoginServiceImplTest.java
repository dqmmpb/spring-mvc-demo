package com.alphabeta.platform.web.service.impl;

import com.alphabeta.platform.core.domain.model.XmManager;
import com.alphabeta.platform.core.exception.BaseAppException;
import com.alphabeta.platform.web.service.BaseServiceTest;
import com.alphabeta.platform.web.service.XmLoginService;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resource;

import static org.junit.Assert.assertNotNull;

public class XmLoginServiceImplTest extends BaseServiceTest {

    @Resource
    XmLoginService xmLoginService;

    @Test
    public void login() {
        try {
            XmManager xmManager = xmLoginService.login("13819493701", "123456");
            assertNotNull(xmManager);
        } catch (BaseAppException e) {
            e.printStackTrace();
            Assert.fail("用户登录异常: " + e.getCode());
        }
    }

    @Test
    public void logout() {
    }
}
