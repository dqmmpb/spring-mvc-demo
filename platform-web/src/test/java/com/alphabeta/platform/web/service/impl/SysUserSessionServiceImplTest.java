package com.alphabeta.platform.web.service.impl;

import com.alphabeta.platform.core.exception.BaseAppException;
import com.alphabeta.platform.base.domain.model.SysUserSession;
import com.alphabeta.platform.web.service.BaseServiceTest;
import com.alphabeta.platform.web.service.SysUserSessionService;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resource;

import static org.junit.Assert.assertNotNull;

public class SysUserSessionServiceImplTest extends BaseServiceTest {

    @Resource
    SysUserSessionService sysUserSessionService;

    @Test
    public void addSession() {

        try {
            String ua = "No User Agent";
            SysUserSession sysUserSession = sysUserSessionService.addSession(1L, "13819493701", ua, "abc123");
            assertNotNull(sysUserSession);

        } catch (BaseAppException e) {
            e.printStackTrace();
            Assert.fail("添加用户Session异常: " + e.getCode());
        }

    }
}
