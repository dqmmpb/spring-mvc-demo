package com.alphabeta.platform.web.service.impl;

import com.alphabeta.platform.core.domain.model.XmManager;
import com.alphabeta.platform.core.domain.model.XmRole;
import com.alphabeta.platform.core.exception.BaseAppException;
import com.alphabeta.platform.web.common.ConstTest;
import com.alphabeta.platform.web.service.BaseServiceTest;
import com.alphabeta.platform.web.service.XmManagerRoleService;
import com.alphabeta.platform.web.service.XmManagerService;
import com.alphabeta.platform.web.service.XmRoleService;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resource;

public class XmManagerRoleServiceImplTest extends BaseServiceTest {

    @Resource
    XmRoleService xmRoleService;

    @Resource
    XmManagerService xmManagerService;

    @Resource
    XmManagerRoleService xmManagerRoleService;

    @Test
    public void addManagerRole() {
        try {
            // 13819493700 SuperAdmin
            XmRole xmRole = xmRoleService.getRole(ConstTest.ROLES[0][0]);
            XmManager xmManager = xmManagerService.getManager(1L);

            xmManagerRoleService.addManagerRole(xmManager.getManagerId(), xmRole.getRoleId());

            // 13819493701 Admin
            xmRole = xmRoleService.getRole(ConstTest.ROLES[1][0]);
            xmManager = xmManagerService.getManager(2L);

            xmManagerRoleService.addManagerRole(xmManager.getManagerId(), xmRole.getRoleId());

            // 13819493702 User
            xmRole = xmRoleService.getRole(ConstTest.ROLES[2][0]);
            xmManager = xmManagerService.getManager(3L);

            xmManagerRoleService.addManagerRole(xmManager.getManagerId(), xmRole.getRoleId());

        } catch (BaseAppException e) {
            e.printStackTrace();
            Assert.fail("分配角色异常: " + e.getCode());
        }
    }
}
