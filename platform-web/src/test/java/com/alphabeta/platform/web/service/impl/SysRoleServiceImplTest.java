package com.alphabeta.platform.web.service.impl;

import com.alphabeta.platform.core.exception.BaseAppException;
import com.alphabeta.platform.base.domain.model.SysRole;
import com.alphabeta.platform.web.common.ConstTest;
import com.alphabeta.platform.web.service.BaseServiceTest;
import com.alphabeta.platform.web.service.SysRoleService;
import com.github.pagehelper.Page;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class SysRoleServiceImplTest extends BaseServiceTest {

    @Resource
    SysRoleService sysRoleService;

    @Test
    public void addRole() {
        try {

            for (String[] role : ConstTest.ROLES) {
                sysRoleService.addRole(role[0], role[1], role[2]);
            }
        } catch (BaseAppException e) {
            e.printStackTrace();
            Assert.fail("添加角色异常: " + e.getCode());
        }
    }

    @Test
    public void queryRoles() {
        try {
            // 分页查询
            List<SysRole> sysRoleList = sysRoleService.queryRoles(1, 10);
            Page page = (Page) sysRoleList;
            assertThat(page.getResult().size(), equalTo(3));
            assertThat(page.getTotal(), equalTo(3L));

            // 第2页数据
            sysRoleList = sysRoleService.queryRoles(2, 10);
            page = (Page) sysRoleList;
            assertThat(page.getResult().size(), equalTo(3));
            assertThat(page.getPageNum(), equalTo(1));
            assertThat(page.getTotal(), equalTo(3L));
        } catch (BaseAppException e) {
            e.printStackTrace();
            Assert.fail("查询角色异常: " + e.getCode());
        }
    }

    @Test
    public void queryRolesByUserId() {
        try {
            // 查询用户角色
            List<SysRole> sysRoleList = sysRoleService.queryRolesByUserId(1L);
            assertThat(sysRoleList.size(), equalTo(1));

            // 查询用户角色
            sysRoleList = sysRoleService.queryRolesByUserId(2L);
            assertThat(sysRoleList.size(), equalTo(2));
        } catch (BaseAppException e) {
            e.printStackTrace();
            Assert.fail("查询角色异常: " + e.getCode());
        }
    }
}
