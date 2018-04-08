package com.alphabeta.platform.web.service.impl;

import com.alphabeta.platform.base.domain.model.SysUser;
import com.alphabeta.platform.core.exception.BaseAppException;
import com.alphabeta.platform.web.service.BaseServiceTest;
import com.alphabeta.platform.web.service.SysLoginService;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resource;

import static org.junit.Assert.assertNotNull;

public class SysLoginServiceImplTest extends BaseServiceTest {

    @Resource
    SysLoginService sysLoginService;

    @Test
    public void login() {
        try {
            SysUser sysUser = sysLoginService.login("13819493701", "123456");
            assertNotNull(sysUser);
        } catch (BaseAppException e) {
            e.printStackTrace();
            Assert.fail("用户登录异常: " + e.getCode());
        }
    }

    @Test
    public void logout() {
    }
}
