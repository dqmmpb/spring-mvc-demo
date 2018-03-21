package com.alphabeta.platform.web.service.impl;

import com.alphabeta.platform.core.domain.model.XmPriv;
import com.alphabeta.platform.core.domain.model.XmRole;
import com.alphabeta.platform.core.exception.BaseAppException;
import com.alphabeta.platform.web.common.ConstTest;
import com.alphabeta.platform.web.service.BaseServiceTest;
import com.alphabeta.platform.web.service.XmPrivService;
import com.alphabeta.platform.web.service.XmRolePrivService;
import com.alphabeta.platform.web.service.XmRoleService;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

public class XmRolePrivServiceImplTest extends BaseServiceTest {

    @Resource
    XmRoleService xmRoleService;

    @Resource
    XmPrivService xmPrivService;

    @Resource
    XmRolePrivService xmRolePrivService;

    @Test
    public void addRolePriv() {
        try {
            // 超级管理员，分配所有权限
            XmRole xmRole = xmRoleService.getRole(ConstTest.ROLES[0][0]);
            for (String[] priv : ConstTest.PRIVS) {
                System.out.println(priv[1]);
                XmPriv xmPriv = xmPrivService.getPriv(priv[1]);
                xmRolePrivService.addRolePriv(xmRole.getRoleId(), xmPriv.getPrivId());
            }

            // 管理员，分配用户管理权限
            xmRole = xmRoleService.getRole(ConstTest.ROLES[1][0]);

            for (String[] priv : ConstTest.PRIVS) {
                // 正则表达是过滤掉某些权限
                if (!priv[1].matches("sys:((priv)|(role)|(manager)):.*")) {
                    System.out.println(priv[1]);
                    XmPriv xmPriv = xmPrivService.getPriv(priv[1]);
                    xmRolePrivService.addRolePriv(xmRole.getRoleId(), xmPriv.getPrivId());
                }

            }

            // 普通用户，分配查看个人信息和修改密码权限
            xmRole = xmRoleService.getRole(ConstTest.ROLES[2][0]);

            for (String[] priv : ConstTest.PRIVS) {
                // 过滤掉某些权限
                if (priv[1].matches("sys:profile:.*")) {
                    System.out.println(priv[1]);
                    XmPriv xmPriv = xmPrivService.getPriv(priv[1]);
                    xmRolePrivService.addRolePriv(xmRole.getRoleId(), xmPriv.getPrivId());
                }

            }
        } catch (BaseAppException e) {
            e.printStackTrace();
            Assert.fail("分配权限异常: " + e.getCode());
        }
    }

    @Test
    public void allocateRolePriv() {
        try {
            List<Long> privIds = new ArrayList<Long>();
            privIds.add(10L);
            privIds.add(12L);
            privIds.add(14L);
            xmRolePrivService.allocateRolePriv(4L, privIds);

        } catch (BaseAppException e) {
            e.printStackTrace();
            Assert.fail("分配权限异常: " + e.getCode());
        }
    }
}
