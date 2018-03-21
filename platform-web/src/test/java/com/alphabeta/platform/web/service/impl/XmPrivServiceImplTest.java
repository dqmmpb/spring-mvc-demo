package com.alphabeta.platform.web.service.impl;

import com.alphabeta.platform.core.common.PrivType;
import com.alphabeta.platform.core.domain.model.XmPriv;
import com.alphabeta.platform.core.exception.BaseAppException;
import com.alphabeta.platform.core.i18n.I18NMgr;
import com.alphabeta.platform.web.common.ConstTest;
import com.alphabeta.platform.web.service.BaseServiceTest;
import com.alphabeta.platform.web.service.XmPrivService;
import com.github.pagehelper.Page;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;

public class XmPrivServiceImplTest extends BaseServiceTest {

    @Resource
    XmPrivService xmPrivService;

    @Test
    public void delPriv() {
        try {
            List<XmPriv> xmPrivList = xmPrivService.queryPrivs();
            int beforeSize = xmPrivList.size();
            XmPriv xmPriv = xmPrivService.getPriv(1L);
            Assert.assertNotNull(xmPriv);
            xmPrivService.delPriv(1L);
            xmPrivList = xmPrivService.queryPrivs();
            int afterSize = xmPrivList.size();
            assertThat(afterSize, equalTo(beforeSize - 1));
        } catch (BaseAppException e) {
            e.printStackTrace();
            Assert.fail("删除权限异常: " + e.getCode());
        }
    }

    @Test
    public void updatePriv() {
        try {
            XmPriv xmPriv = xmPrivService.getPriv(1L);
            Assert.assertNotNull(xmPriv);
            String oldPrivName = xmPriv.getPrivName();
            xmPriv.setPrivName("这是一个测试");
            xmPrivService.updatePriv(xmPriv);
            xmPriv = xmPrivService.getPriv(1L);
            Assert.assertNotNull(xmPriv);
            Assert.assertEquals("这是一个测试", xmPriv.getPrivName());
            xmPriv.setPrivName(oldPrivName);
            xmPrivService.updatePriv(xmPriv);
            xmPriv = xmPrivService.getPriv(1L);
            Assert.assertNotNull(xmPriv);
            Assert.assertEquals(oldPrivName, xmPriv.getPrivName());
        } catch (BaseAppException e) {
            e.printStackTrace();
            Assert.fail("更新权限异常: " + e.getCode());
        }
    }

    @Test
    public void addPriv() {
        try {
            for (String[] priv : ConstTest.PRIVS) {
                XmPriv xmPriv = new XmPriv();
                PrivType privType = PrivType.valueOf(priv[0]);

                xmPriv.setType(privType.getValue());
                xmPriv.setPrivCode(priv[1]);
                xmPriv.setPrivName(priv[2]);
                xmPriv.setDescription(priv[3]);

                switch (privType) {
                    case DIR:
                        xmPriv.setUrl(priv[4]);
                        xmPriv.setPath(priv[5]);
                        break;
                    case MENU:
                        xmPriv.setUrl(priv[4]);
                        xmPriv.setPath(priv[5]);
                        break;
                    case DATA:
                        break;
                    default:
                        break;
                }

                if (priv.length > 6) {
                    String parentPrivCode = priv[6];

                    XmPriv parentPriv = xmPrivService.getPriv(parentPrivCode);

                    Assert.assertNotNull(parentPriv);

                    xmPriv.setParentPrivId(parentPriv.getPrivId());
                }

                xmPrivService.addPriv(xmPriv);
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
            XmPriv xmPriv = xmPrivService.getPriv(1L);
            Assert.assertEquals(ConstTest.PRIVS[0][1], xmPriv.getPrivCode());
        } catch (BaseAppException e) {
            e.printStackTrace();
            Assert.fail("查询权限异常: " + e.getCode());
        }
    }

    @Test
    public void queryPrivs() {
        try {
            // 分页查询
            List<XmPriv> xmPrivList = xmPrivService.queryPrivs(1, 10);
            Page page = (Page) xmPrivList;
            Assert.assertEquals(10, page.getResult().size());
            assertThat(page.getTotal(), greaterThan(12L));

            // 第2页数据
            xmPrivList = xmPrivService.queryPrivs(2, 10);
            page = (Page) xmPrivList;
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
            List<XmPriv> xmPrivList = xmPrivService.queryPrivsByRoleIds(Arrays.asList(roleIds));
            assertThat(xmPrivList.size(), greaterThan(20));

            // 查询Admin对应的权限
            roleIds = new Long[]{2L};
            xmPrivList = xmPrivService.queryPrivsByRoleIds(Arrays.asList(roleIds));
            Assert.assertEquals(11, xmPrivList.size());

            // 查询Manager对应的权限
            roleIds = new Long[]{3L};
            xmPrivList = xmPrivService.queryPrivsByRoleIds(Arrays.asList(roleIds));
            Assert.assertEquals(2, xmPrivList.size());

            // 查询Admin和Manager权限的并集
            roleIds = new Long[]{2L, 3L};
            xmPrivList = xmPrivService.queryPrivsByRoleIds(Arrays.asList(roleIds));
            Assert.assertEquals(11, xmPrivList.size());

            // 根据类型查询权限
            // 查询Admin对应的权限
            roleIds = new Long[]{2L};
            xmPrivList = xmPrivService.queryPrivsByRoleIds(Arrays.asList(roleIds), PrivType.DATA.getValue());
            Assert.assertEquals(6, xmPrivList.size());
            roleIds = new Long[]{2L};
            xmPrivList = xmPrivService.queryPrivsByRoleIds(Arrays.asList(roleIds), PrivType.DIR.getValue());
            Assert.assertEquals(1, xmPrivList.size());
            roleIds = new Long[]{2L};
            xmPrivList = xmPrivService.queryPrivsByRoleIds(Arrays.asList(roleIds), PrivType.MENU.getValue());
            for (XmPriv xmPriv : xmPrivList) {
                System.out.println(xmPriv.getPrivCode());
            }
            Assert.assertEquals(4, xmPrivList.size());

        } catch (BaseAppException e) {
            e.printStackTrace();
            Assert.fail("查询权限异常: " + e.getCode());
        }
    }

    @Test
    public void queryPrivsByRoleId() {
        try {
            // 查询SuperAdmin对应的权限
            List<XmPriv> xmPrivList = xmPrivService.queryPrivsByRoleId(1L);
            assertThat(xmPrivList.size(), greaterThan(20));

            // 查询Admin对应的权限
            xmPrivList = xmPrivService.queryPrivsByRoleId(2L);
            Assert.assertEquals(11, xmPrivList.size());

            // 查询Manager对应的权限
            xmPrivList = xmPrivService.queryPrivsByRoleId(3L);
            Assert.assertEquals(2, xmPrivList.size());
        } catch (BaseAppException e) {
            e.printStackTrace();
            Assert.fail("查询权限异常: " + e.getCode());
        }
    }


    @Test
    public void queryPrivsByManagerId() {
        try {
            // 查询用户对应的权限
            List<XmPriv> xmPrivList = xmPrivService.queryPrivsByManagerId(1L);
            assertThat(xmPrivList.size(), equalTo(28));

            // 查询用户对应的权限
            xmPrivList = xmPrivService.queryPrivsByManagerId(2L);
            assertThat(xmPrivList.size(), equalTo(11));

        } catch (BaseAppException e) {
            e.printStackTrace();
            Assert.fail("查询权限异常: " + e.getCode());
        }
    }


}
