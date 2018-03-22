package com.alphabeta.platform.web.service.impl;

import com.alphabeta.platform.base.common.StatusType;
import com.alphabeta.platform.base.domain.model.SysUser;
import com.alphabeta.platform.core.exception.BaseAppException;
import com.alphabeta.platform.core.util.EncryptUtil;
import com.alphabeta.platform.core.util.EqualsUtil;
import com.alphabeta.platform.web.common.ConstTest;
import com.alphabeta.platform.web.service.BaseServiceTest;
import com.alphabeta.platform.web.service.SysUserService;
import com.github.pagehelper.Page;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

import static com.alphabeta.platform.base.common.Const.STATE_A;
import static com.alphabeta.platform.base.common.Const.STATE_X;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class SysUserServiceImplTest extends BaseServiceTest {

    @Resource
    SysUserService sysUserService;

    @Test
    public void delUser() {
        try {
            String phone = "13819493701";
            SysUser sysUser = sysUserService.getUser(phone);

            Assert.assertNotNull(sysUser);
            sysUserService.delUser(sysUser.getUserId());
            sysUser = sysUserService.getUser(phone);
            Assert.assertEquals(STATE_X, sysUser.getState());

        } catch (BaseAppException e) {
            e.printStackTrace();
            Assert.fail("删除用户异常: " + e.getCode());
        }
    }

    @Test
    public void addUser() {
        try {
            for (String[] user : ConstTest.MANAGERS) {
                String phone = user[0];
                SysUser sysUser = sysUserService.getUser(phone);
                Assert.assertNull(sysUser);

                // 新建用户
                sysUser = new SysUser();
                sysUser.setPhone(phone);
                sysUser.setPassword(user[1]);
                sysUserService.addUser(sysUser);
            }

        } catch (BaseAppException e) {
            e.printStackTrace();
            Assert.fail("添加用户异常: " + e.getCode());
        }
    }

    @Test
    public void updateUser() {
        try {
            String phone = "13819493701";
            SysUser sysUser = sysUserService.getUser(phone);
            Assert.assertNotNull(sysUser);

            if (STATE_A.equals(sysUser.getState())) {
                sysUser.setState(STATE_X);
            } else if (STATE_X.equals(sysUser.getState())) {
                sysUser.setState(STATE_A);
            }

            sysUserService.updateUser(sysUser);

        } catch (BaseAppException e) {
            e.printStackTrace();
            Assert.fail("更新用户异常: " + e.getCode());
        }
    }

    @Test
    public void resetPwd() {
        try {
            String phone = "13819493701";
            SysUser sysUser = sysUserService.getUser(phone);
            Assert.assertNotNull(sysUser);

            String salt = sysUser.getSalt();
            sysUserService.resetPwd(sysUser.getUserId());

            sysUser = sysUserService.getUser(phone);
            Assert.assertEquals(salt, sysUser.getSalt());

            EncryptUtil.Encrypt encrypt = EncryptUtil.encrypt(phone, salt);

            Assert.assertEquals(encrypt.getEncrypt(), sysUser.getPassword());

        } catch (BaseAppException e) {
            e.printStackTrace();
            Assert.fail("重置密码异常: " + e.getCode());
        }
    }

    @Test
    public void changePwd() {
        try {
            String phone = "13819493701";
            String newPassword = "123456";
            SysUser sysUser = sysUserService.getUser(phone);
            Assert.assertNotNull(sysUser);

            String salt = sysUser.getSalt();
            sysUserService.resetPwd(sysUser.getUserId());
            sysUserService.changePwd(sysUser.getUserId(), phone, newPassword);

            sysUser = sysUserService.getUser(phone);
            Assert.assertEquals(salt, sysUser.getSalt());

            EncryptUtil.Encrypt encrypt = EncryptUtil.encrypt(newPassword, salt);

            Assert.assertEquals(encrypt.getEncrypt(), sysUser.getPassword());

        } catch (BaseAppException e) {
            e.printStackTrace();
            Assert.fail("修改密码异常: " + e.getCode());
        }
    }

    @Test
    public void lockUser() {
        try {
            String phone = "13819493701";
            SysUser sysUser = sysUserService.getUser(phone);
            Assert.assertNotNull(sysUser);

            if (!EqualsUtil.equals(StatusType.LOCK.getValue(), sysUser.getStatus().intValue())) {
                sysUserService.lockUser(sysUser.getUserId(), 1L);
            }
            sysUser = sysUserService.getUser(sysUser.getUserId());
            Assert.assertEquals(StatusType.LOCK.getValue(), sysUser.getStatus().intValue());

        } catch (BaseAppException e) {
            e.printStackTrace();
            Assert.fail("锁定用户异常: " + e.getCode());
        }
    }

    @Test
    public void unlockUser() {
        try {
            String phone = "13819493701";
            SysUser sysUser = sysUserService.getUser(phone);
            Assert.assertNotNull(sysUser);

            if (!EqualsUtil.equals(StatusType.UNLOCK.getValue(), sysUser.getStatus().intValue())) {
                sysUserService.unlockUser(sysUser.getUserId());
            }
            sysUser = sysUserService.getUser(sysUser.getUserId());
            Assert.assertEquals(StatusType.UNLOCK.getValue(), sysUser.getStatus().intValue());

        } catch (BaseAppException e) {
            e.printStackTrace();
            Assert.fail("解锁用户异常: " + e.getCode());
        }
    }


    @Test
    public void queryUsers() {
        try {
            String filter = "邓";
            // 分页查询
            List<SysUser> sysUserList = sysUserService.queryUsers(filter, 1, 10);
            Page page = (Page) sysUserList;
            assertThat(page.getResult().size(), equalTo(2));
            assertThat(page.getTotal(), equalTo(2L));

            // 第2页数据
            sysUserList = sysUserService.queryUsers(filter, 2, 10);
            page = (Page) sysUserList;
            assertThat(page.getResult().size(), equalTo(2));
            assertThat(page.getPageNum(), equalTo(1));
            assertThat(page.getTotal(), equalTo(2L));
        } catch (BaseAppException e) {
            e.printStackTrace();
            Assert.fail("查询用户异常: " + e.getCode());
        }
    }

    @Test
    public void queryUsersByRoleId() {
        try {
            // 查询角色用户
            List<SysUser> sysUserList = sysUserService.queryUsersByRoleId(1L);
            assertThat(sysUserList.size(), equalTo(1));

            // 查询角色用户
            sysUserList = sysUserService.queryUsersByRoleId(2L);
            assertThat(sysUserList.size(), equalTo(1));

            // 查询角色用户
            sysUserList = sysUserService.queryUsersByRoleId(3L);
            assertThat(sysUserList.size(), equalTo(1));
        } catch (BaseAppException e) {
            e.printStackTrace();
            Assert.fail("查询用户异常: " + e.getCode());
        }
    }

}
