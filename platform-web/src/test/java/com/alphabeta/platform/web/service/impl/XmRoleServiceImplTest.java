package com.alphabeta.platform.web.service.impl;

import com.alphabeta.platform.core.domain.model.XmRole;
import com.alphabeta.platform.core.exception.BaseAppException;
import com.alphabeta.platform.web.common.ConstTest;
import com.alphabeta.platform.web.service.BaseServiceTest;
import com.alphabeta.platform.web.service.XmRoleService;
import com.github.pagehelper.Page;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class XmRoleServiceImplTest extends BaseServiceTest {

    @Resource
    XmRoleService xmRoleService;

    @Test
    public void addRole() {
        try {

            for (String[] role : ConstTest.ROLES) {
                xmRoleService.addRole(role[0], role[1], role[2]);
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
            List<XmRole> xmRoleList = xmRoleService.queryRoles(1, 10);
            Page page = (Page) xmRoleList;
            assertThat(page.getResult().size(), equalTo(3));
            assertThat(page.getTotal(), equalTo(3L));

            // 第2页数据
            xmRoleList = xmRoleService.queryRoles(2, 10);
            page = (Page) xmRoleList;
            assertThat(page.getResult().size(), equalTo(3));
            assertThat(page.getPageNum(), equalTo(1));
            assertThat(page.getTotal(), equalTo(3L));
        } catch (BaseAppException e) {
            e.printStackTrace();
            Assert.fail("查询角色异常: " + e.getCode());
        }
    }

    @Test
    public void queryRolesByManagerId() {
        try {
            // 查询用户角色
            List<XmRole> xmRoleList = xmRoleService.queryRolesByManagerId(1L);
            assertThat(xmRoleList.size(), equalTo(1));

            // 查询用户角色
            xmRoleList = xmRoleService.queryRolesByManagerId(2L);
            assertThat(xmRoleList.size(), equalTo(2));
        } catch (BaseAppException e) {
            e.printStackTrace();
            Assert.fail("查询角色异常: " + e.getCode());
        }
    }
}
