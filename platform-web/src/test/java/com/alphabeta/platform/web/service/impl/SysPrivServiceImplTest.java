package com.alphabeta.platform.web.service.impl;

import com.alphabeta.platform.base.common.PrivType;
import com.alphabeta.platform.base.domain.model.SysPriv;
import com.alphabeta.platform.core.exception.BaseAppException;
import com.alphabeta.platform.core.i18n.I18NMgr;
import com.alphabeta.platform.web.common.ConstTest;
import com.alphabeta.platform.web.service.BaseServiceTest;
import com.alphabeta.platform.web.service.SysPrivService;
import com.github.pagehelper.Page;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;

public class SysPrivServiceImplTest extends BaseServiceTest {

    @Resource
    SysPrivService sysPrivService;

    @Test
    public void delPriv() {
        try {
            List<SysPriv> sysPrivList = sysPrivService.queryPrivs();
            int beforeSize = sysPrivList.size();
            SysPriv sysPriv = sysPrivService.getPriv(1L);
            Assert.assertNotNull(sysPriv);
            sysPrivService.delPriv(1L);
            sysPrivList = sysPrivService.queryPrivs();
            int afterSize = sysPrivList.size();
            assertThat(afterSize, equalTo(beforeSize - 1));
        } catch (BaseAppException e) {
            e.printStackTrace();
            Assert.fail("删除权限异常: " + e.getCode());
        }
    }

    @Test
    public void updatePriv() {
        try {
            SysPriv sysPriv = sysPrivService.getPriv(1L);
            Assert.assertNotNull(sysPriv);
            String oldPrivName = sysPriv.getPrivName();
            sysPriv.setPrivName("这是一个测试");
            sysPrivService.updatePriv(sysPriv);
            sysPriv = sysPrivService.getPriv(1L);
            Assert.assertNotNull(sysPriv);
            Assert.assertEquals("这是一个测试", sysPriv.getPrivName());
            sysPriv.setPrivName(oldPrivName);
            sysPrivService.updatePriv(sysPriv);
            sysPriv = sysPrivService.getPriv(1L);
            Assert.assertNotNull(sysPriv);
            Assert.assertEquals(oldPrivName, sysPriv.getPrivName());
        } catch (BaseAppException e) {
            e.printStackTrace();
            Assert.fail("更新权限异常: " + e.getCode());
        }
    }

    @Test
    public void addPriv() {
        try {
            for (String[] priv : ConstTest.PRIVS) {
                SysPriv sysPriv = new SysPriv();
                PrivType privType = PrivType.valueOf(priv[0]);

                sysPriv.setType(privType.getValue());
                sysPriv.setPrivCode(priv[1]);
                sysPriv.setPrivName(priv[2]);
                sysPriv.setDescription(priv[3]);

                switch (privType) {
                    case DIR:
                        sysPriv.setUrl(priv[4]);
                        sysPriv.setPath(priv[5]);
                        break;
                    case MENU:
                        sysPriv.setUrl(priv[4]);
                        sysPriv.setPath(priv[5]);
                        break;
                    case DATA:
                        break;
                    default:
                        break;
                }

                if (priv.length > 6) {
                    String parentPrivCode = priv[6];

                    SysPriv parentPriv = sysPrivService.getPriv(parentPrivCode);

                    Assert.assertNotNull(parentPriv);

                    sysPriv.setParentPrivId(parentPriv.getPrivId());
                }

                sysPrivService.addPriv(sysPriv);
            }
        } catch (BaseAppException e) {
            e.printStackTrace();
            Assert.fail("添加权限异常: " + e.getCode() + "; " + I18NMgr.getMessage(e.getCode()));
        }
    }

    @Test
    public void queryPriv() {
        try {
            //  查询权限
            SysPriv sysPriv = sysPrivService.getPriv(1L);
            Assert.assertEquals(ConstTest.PRIVS[0][1], sysPriv.getPrivCode());
        } catch (BaseAppException e) {
            e.printStackTrace();
            Assert.fail("查询权限异常: " + e.getCode());
        }
    }

    @Test
    public void queryPrivs() {
        try {
            // 分页查询
            List<SysPriv> sysPrivList = sysPrivService.queryPrivs(1, 10);
            Page page = (Page) sysPrivList;
            Assert.assertEquals(10, page.getResult().size());
            assertThat(page.getTotal(), greaterThan(12L));

            // 第2页数据
            sysPrivList = sysPrivService.queryPrivs(2, 10);
            page = (Page) sysPrivList;
            assertThat(page.getResult().size(), equalTo(10));
            assertThat(page.getTotal(), greaterThan(12L));
        } catch (BaseAppException e) {
            e.printStackTrace();
            Assert.fail("查询权限异常: " + e.getCode());
        }
    }

    @Test
    public void queryPrivsByRoleIds() {
        try {
            // 根据roleId数组查询权限
            Long[] roleIds = new Long[]{1L};
            List<SysPriv> sysPrivList = sysPrivService.queryPrivsByRoleIds(Arrays.asList(roleIds));
            assertThat(sysPrivList.size(), greaterThan(20));

            // 查询Admin对应的权限
            roleIds = new Long[]{2L};
            sysPrivList = sysPrivService.queryPrivsByRoleIds(Arrays.asList(roleIds));
            Assert.assertEquals(11, sysPrivList.size());

            // 查询User对应的权限
            roleIds = new Long[]{3L};
            sysPrivList = sysPrivService.queryPrivsByRoleIds(Arrays.asList(roleIds));
            Assert.assertEquals(2, sysPrivList.size());

            // 查询Admin和User权限的并集
            roleIds = new Long[]{2L, 3L};
            sysPrivList = sysPrivService.queryPrivsByRoleIds(Arrays.asList(roleIds));
            Assert.assertEquals(11, sysPrivList.size());

            // 根据类型查询权限
            // 查询Admin对应的权限
            roleIds = new Long[]{2L};
            sysPrivList = sysPrivService.queryPrivsByRoleIds(Arrays.asList(roleIds), PrivType.DATA.getValue());
            Assert.assertEquals(6, sysPrivList.size());
            roleIds = new Long[]{2L};
            sysPrivList = sysPrivService.queryPrivsByRoleIds(Arrays.asList(roleIds), PrivType.DIR.getValue());
            Assert.assertEquals(1, sysPrivList.size());
            roleIds = new Long[]{2L};
            sysPrivList = sysPrivService.queryPrivsByRoleIds(Arrays.asList(roleIds), PrivType.MENU.getValue());
            for (SysPriv sysPriv : sysPrivList) {
                System.out.println(sysPriv.getPrivCode());
            }
            Assert.assertEquals(4, sysPrivList.size());

        } catch (BaseAppException e) {
            e.printStackTrace();
            Assert.fail("查询权限异常: " + e.getCode());
        }
    }

    @Test
    public void queryPrivsByRoleId() {
        try {
            // 查询SuperAdmin对应的权限
            List<SysPriv> sysPrivList = sysPrivService.queryPrivsByRoleId(1L);
            assertThat(sysPrivList.size(), greaterThan(20));

            // 查询Admin对应的权限
            sysPrivList = sysPrivService.queryPrivsByRoleId(2L);
            Assert.assertEquals(11, sysPrivList.size());

            // 查询User对应的权限
            sysPrivList = sysPrivService.queryPrivsByRoleId(3L);
            Assert.assertEquals(2, sysPrivList.size());
        } catch (BaseAppException e) {
            e.printStackTrace();
            Assert.fail("查询权限异常: " + e.getCode());
        }
    }


    @Test
    public void queryPrivsByUserId() {
        try {
            // 查询用户对应的权限
            List<SysPriv> sysPrivList = sysPrivService.queryPrivsByUserId(1L);
            assertThat(sysPrivList.size(), equalTo(28));

            // 查询用户对应的权限
            sysPrivList = sysPrivService.queryPrivsByUserId(2L);
            assertThat(sysPrivList.size(), equalTo(11));

        } catch (BaseAppException e) {
            e.printStackTrace();
            Assert.fail("查询权限异常: " + e.getCode());
        }
    }


}
