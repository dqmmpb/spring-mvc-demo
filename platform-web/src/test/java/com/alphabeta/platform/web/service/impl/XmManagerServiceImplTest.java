package com.alphabeta.platform.web.service.impl;

import com.alphabeta.platform.core.common.Const;
import com.alphabeta.platform.core.common.DisableStatus;
import com.alphabeta.platform.core.domain.model.XmManager;
import com.alphabeta.platform.core.exception.BaseAppException;
import com.alphabeta.platform.core.util.EncryptUtil;
import com.alphabeta.platform.core.util.EqualsUtil;
import com.alphabeta.platform.web.common.ConstTest;
import com.alphabeta.platform.web.service.BaseServiceTest;
import com.alphabeta.platform.web.service.XmManagerService;
import com.github.pagehelper.Page;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class XmManagerServiceImplTest extends BaseServiceTest {

    @Resource
    XmManagerService xmManagerService;

    @Test
    public void delManager() {
        try {
            String phone = "13819493701";
            XmManager xmManager = xmManagerService.getManager(phone);

            Assert.assertNotNull(xmManager);
            xmManagerService.delManager(xmManager.getManagerId());
            xmManager = xmManagerService.getManager(phone);
            Assert.assertEquals(Const.STATE_X, xmManager.getState());

        } catch (BaseAppException e) {
            e.printStackTrace();
            Assert.fail("删除用户异常: " + e.getCode());
        }
    }

    @Test
    public void addManager() {
        try {
            for (String[] manager : ConstTest.MANAGERS) {
                String phone = manager[0];
                XmManager xmManager = xmManagerService.getManager(phone);
                Assert.assertNull(xmManager);

                // 新建用户
                xmManager = new XmManager();
                xmManager.setPhone(phone);
                xmManager.setPassword(manager[1]);
                xmManagerService.addManager(xmManager);
            }

        } catch (BaseAppException e) {
            e.printStackTrace();
            Assert.fail("添加用户异常: " + e.getCode());
        }
    }

    @Test
    public void updateManager() {
        try {
            String phone = "13819493701";
            XmManager xmManager = xmManagerService.getManager(phone);
            Assert.assertNotNull(xmManager);

            if (Const.STATE_A.equals(xmManager.getState())) {
                xmManager.setState(Const.STATE_X);
            } else if (Const.STATE_X.equals(xmManager.getState())) {
                xmManager.setState(Const.STATE_A);
            }

            xmManagerService.updateManager(xmManager);

        } catch (BaseAppException e) {
            e.printStackTrace();
            Assert.fail("更新用户异常: " + e.getCode());
        }
    }

    @Test
    public void resetPwd() {
        try {
            String phone = "13819493701";
            XmManager xmManager = xmManagerService.getManager(phone);
            Assert.assertNotNull(xmManager);

            String salt = xmManager.getSalt();
            xmManagerService.resetPwd(xmManager.getManagerId());

            xmManager = xmManagerService.getManager(phone);
            Assert.assertEquals(salt, xmManager.getSalt());

            EncryptUtil.Encrypt encrypt = EncryptUtil.encrypt(phone, salt);

            Assert.assertEquals(encrypt.getEncrypt(), xmManager.getPassword());

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
            XmManager xmManager = xmManagerService.getManager(phone);
            Assert.assertNotNull(xmManager);

            String salt = xmManager.getSalt();
            xmManagerService.resetPwd(xmManager.getManagerId());
            xmManagerService.changePwd(xmManager.getManagerId(), phone, newPassword);

            xmManager = xmManagerService.getManager(phone);
            Assert.assertEquals(salt, xmManager.getSalt());

            EncryptUtil.Encrypt encrypt = EncryptUtil.encrypt(newPassword, salt);

            Assert.assertEquals(encrypt.getEncrypt(), xmManager.getPassword());

        } catch (BaseAppException e) {
            e.printStackTrace();
            Assert.fail("修改密码异常: " + e.getCode());
        }
    }

    @Test
    public void lockManager() {
        try {
            String phone = "13819493701";
            XmManager xmManager = xmManagerService.getManager(phone);
            Assert.assertNotNull(xmManager);

            if (!EqualsUtil.equals(DisableStatus.LOCK.getValue(), xmManager.getDisableStatus().intValue())) {
                xmManagerService.lockManager(xmManager.getManagerId(), 1L);
            }
            xmManager = xmManagerService.getManager(xmManager.getManagerId());
            Assert.assertEquals(DisableStatus.LOCK.getValue(), xmManager.getDisableStatus().intValue());

        } catch (BaseAppException e) {
            e.printStackTrace();
            Assert.fail("锁定用户异常: " + e.getCode());
        }
    }

    @Test
    public void unlockManager() {
        try {
            String phone = "13819493701";
            XmManager xmManager = xmManagerService.getManager(phone);
            Assert.assertNotNull(xmManager);

            if (!EqualsUtil.equals(DisableStatus.UNLOCK.getValue(), xmManager.getDisableStatus().intValue())) {
                xmManagerService.unlockManager(xmManager.getManagerId());
            }
            xmManager = xmManagerService.getManager(xmManager.getManagerId());
            Assert.assertEquals(DisableStatus.UNLOCK.getValue(), xmManager.getDisableStatus().intValue());

        } catch (BaseAppException e) {
            e.printStackTrace();
            Assert.fail("解锁用户异常: " + e.getCode());
        }
    }


    @Test
    public void queryManagers() {
        try {
            String filter = "邓";
            // 分页查询
            List<XmManager> xmManagerList = xmManagerService.queryManagers(filter, 1, 10);
            Page page = (Page) xmManagerList;
            assertThat(page.getResult().size(), equalTo(2));
            assertThat(page.getTotal(), equalTo(2L));

            // 第2页数据
            xmManagerList = xmManagerService.queryManagers(filter, 2, 10);
            page = (Page) xmManagerList;
            assertThat(page.getResult().size(), equalTo(2));
            assertThat(page.getPageNum(), equalTo(1));
            assertThat(page.getTotal(), equalTo(2L));
        } catch (BaseAppException e) {
            e.printStackTrace();
            Assert.fail("查询用户异常: " + e.getCode());
        }
    }

    @Test
    public void queryManagersByRoleId() {
        try {
            // 查询角色用户
            List<XmManager> xmManagerList = xmManagerService.queryManagersByRoleId(1L);
            assertThat(xmManagerList.size(), equalTo(1));

            // 查询角色用户
            xmManagerList = xmManagerService.queryManagersByRoleId(2L);
            assertThat(xmManagerList.size(), equalTo(1));

            // 查询角色用户
            xmManagerList = xmManagerService.queryManagersByRoleId(3L);
            assertThat(xmManagerList.size(), equalTo(1));
        } catch (BaseAppException e) {
            e.printStackTrace();
            Assert.fail("查询用户异常: " + e.getCode());
        }
    }

}
