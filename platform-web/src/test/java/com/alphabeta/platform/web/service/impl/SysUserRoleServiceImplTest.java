package com.alphabeta.platform.web.service.impl;

import com.alphabeta.platform.base.domain.model.SysUser;
import com.alphabeta.platform.base.domain.model.SysRole;
import com.alphabeta.platform.core.exception.BaseAppException;
import com.alphabeta.platform.web.common.ConstTest;
import com.alphabeta.platform.web.service.BaseServiceTest;
import com.alphabeta.platform.web.service.SysUserRoleService;
import com.alphabeta.platform.web.service.SysUserService;
import com.alphabeta.platform.web.service.SysRoleService;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resource;

public class SysUserRoleServiceImplTest extends BaseServiceTest {

    @Resource
    SysRoleService sysRoleService;

    @Resource
    SysUserService sysUserService;

    @Resource
    SysUserRoleService sysUserRoleService;

    @Test
    public void addUserRole() {
        try {
            // 13819493700 SuperAdmin
            SysRole sysRole = sysRoleService.getRole(ConstTest.ROLES[0][0]);
            SysUser sysUser = sysUserService.getUser(1L);

            sysUserRoleService.addUserRole(sysUser.getUserId(), sysRole.getRoleId());

            // 13819493701 Admin
            sysRole = sysRoleService.getRole(ConstTest.ROLES[1][0]);
            sysUser = sysUserService.getUser(2L);

            sysUserRoleService.addUserRole(sysUser.getUserId(), sysRole.getRoleId());

            // 13819493702 User
            sysRole = sysRoleService.getRole(ConstTest.ROLES[2][0]);
            sysUser = sysUserService.getUser(3L);

            sysUserRoleService.addUserRole(sysUser.getUserId(), sysRole.getRoleId());

        } catch (BaseAppException e) {
            e.printStackTrace();
            Assert.fail("分配角色异常: " + e.getCode());
        }
    }
}
