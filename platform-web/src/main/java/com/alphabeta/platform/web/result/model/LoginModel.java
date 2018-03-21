package com.alphabeta.platform.web.result.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.alphabeta.platform.base.domain.model.SysUser;
import com.alphabeta.platform.base.domain.model.SysPriv;

import java.util.List;

/**
 * 登录返回结果
 *
 * @author deng.qiming
 * @date 2017-01-09 10:15
 */
public class LoginModel {

    @JSONField(ordinal = -1004)
    private boolean isLogin;

    @JSONField(ordinal = -1003)
    private String token;

    @JSONField(ordinal = -1002)
    private SysUser profile;

    @JSONField(ordinal = -1001)
    private List<MenuModel> menus;

    @JSONField(ordinal = -1000)
    private List<SysPriv> permissions;


    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public SysUser getProfile() {
        return profile;
    }

    public void setProfile(SysUser profile) {
        this.profile = profile;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<MenuModel> getMenus() {
        return menus;
    }

    public void setMenus(List<MenuModel> menus) {
        this.menus = menus;
    }

    public List<SysPriv> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<SysPriv> permissions) {
        this.permissions = permissions;
    }
}
