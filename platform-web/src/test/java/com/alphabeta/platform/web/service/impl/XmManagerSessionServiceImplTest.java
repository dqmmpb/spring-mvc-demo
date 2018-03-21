package com.alphabeta.platform.web.service.impl;

import com.alphabeta.platform.core.domain.model.XmManagerSession;
import com.alphabeta.platform.core.exception.BaseAppException;
import com.alphabeta.platform.web.service.BaseServiceTest;
import com.alphabeta.platform.web.service.XmManagerSessionService;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resource;

import static org.junit.Assert.assertNotNull;

public class XmManagerSessionServiceImplTest extends BaseServiceTest {

    @Resource
    XmManagerSessionService xmManagerSessionService;

    @Test
    public void addSession() {

        try {
            String ua = "No Manager Agent";
            XmManagerSession xmManagerSession = xmManagerSessionService.addSession(1L, "13819493701", ua, "abc123");
            assertNotNull(xmManagerSession);

        } catch (BaseAppException e) {
            e.printStackTrace();
            Assert.fail("添加用户Session异常: " + e.getCode());
        }

    }
}
