package com.alphabeta.platform.web.controller;

import com.alibaba.fastjson.support.spring.annotation.FastJsonFilter;
import com.alibaba.fastjson.support.spring.annotation.FastJsonView;
import com.alphabeta.platform.base.common.Const;
import com.alphabeta.platform.base.common.PrivType;
import com.alphabeta.platform.base.common.StatusType;
import com.alphabeta.platform.base.domain.model.SysPriv;
import com.alphabeta.platform.base.domain.model.SysRole;
import com.alphabeta.platform.base.domain.model.SysUser;
import com.alphabeta.platform.base.domain.model.SysUserSession;
import com.alphabeta.platform.base.util.RequestUtils;
import com.alphabeta.platform.base.util.SessionUtil;
import com.alphabeta.platform.core.annotation.RequiresPermissions;
import com.alphabeta.platform.core.domain.BaseParam;
import com.alphabeta.platform.core.domain.BaseResult;
import com.alphabeta.platform.core.exception.BaseAppException;
import com.alphabeta.platform.core.exception.ExceptionHandler;
import com.alphabeta.platform.core.util.EqualsUtil;
import com.alphabeta.platform.core.util.ListUtil;
import com.alphabeta.platform.core.util.ObjectUtil;
import com.alphabeta.platform.core.util.RSAUtil;
import com.alphabeta.platform.web.result.model.LoginModel;
import com.alphabeta.platform.web.result.model.MenuModel;
import com.alphabeta.platform.web.service.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.alphabeta.platform.base.common.ErrorCode.USER_NOT_LOGIN;
import static com.alphabeta.platform.web.common.ErrorCode.INVALID_PARAMS_ERROR;
import static com.alphabeta.platform.web.common.ErrorCode.INVALID_PARAMS_FORMAT_ERROR;

/**
 * 登录相关
 *
 * @author deng.qiming
 * @date 2016年11月8日 上午10:26:57
 */
@RestController
@RequestMapping("/v1/sys")
public class SysLoginController extends BaseController {

    @Resource
    private SysUserSessionService sysUserSessionService;

    @Resource
    private SysLoginService sysLoginService;

    @Resource
    private SysPrivService sysPrivService;

    @Resource
    private SysRoleService sysRoleService;

    @Resource
    private SysUserService sysUserService;

    @Value("${rsa.sign}")
    private boolean sign;

    @Value("${rsa.privateKey}")
    private String privateKeyStr;

    @Value("${session.expirationTime}")
    private Integer sessionExpireTime;

    /**
     * 判断是否登录
     *
     * @param request  req
     * @param response resp
     * @return
     * @throws BaseAppException
     */
    @FastJsonView(
        exclude = {
            @FastJsonFilter(clazz = SysUser.class, props = {"createTime", "updateTime", "password", "salt"}),
            @FastJsonFilter(clazz = SysPriv.class, props = {"createTime", "updateTime", "description"}),
            @FastJsonFilter(clazz = MenuModel.class, props = {"description"}),
        })
    @RequestMapping(value = "/isLogin", method = RequestMethod.POST)
    public BaseResult isLogin(HttpServletRequest request, HttpServletResponse response, @RequestBody BaseParam param) throws BaseAppException {

        BaseResult result = new BaseResult();
        LoginModel loginModel = new LoginModel();


        HttpSession session = request.getSession();
        Object obj = session.getAttribute(Const.SESSION_LOGIN_USER);

        if (obj == null) {
            loginModel.setLogin(false);
        } else {
            SysUser sysUser = (SysUser) obj;
            SysUser realUser = sysUserService.getUser(sysUser.getUserId());
            if (realUser.getStatus().intValue() == StatusType.LOCK.getValue()
                || !sysUser.getPassword().equals(realUser.getPassword())) {
                loginModel.setLogin(false);
            } else {
                loginModel.setLogin(true);
            }
            loginModel.setToken(session.getId());
            loginModel.setProfile(realUser);
            loginModel.setMenus(getMenu(sysUser));
            loginModel.setPermissions(getPermission(sysUser));
        }

        result.setResult(loginModel);

        return result;
    }

    /**
     * 获取当前用户信息
     *
     * @param request  req
     * @param response resp
     * @return
     * @throws BaseAppException
     */
    @FastJsonView(
        exclude = {
            @FastJsonFilter(clazz = SysUser.class, props = {"createTime", "updateTime", "password", "salt"}),
            @FastJsonFilter(clazz = SysPriv.class, props = {"createTime", "updateTime", "description"}),
            @FastJsonFilter(clazz = MenuModel.class, props = {"description"}),
        })
    @RequestMapping(value = "/profile/view", method = RequestMethod.POST)
    @RequiresPermissions
    public BaseResult profileView(HttpServletRequest request, HttpServletResponse response, @RequestBody BaseParam param) throws BaseAppException {

        BaseResult result = new BaseResult();
        LoginModel loginModel = new LoginModel();
        HttpSession session = request.getSession();
        Object obj = session.getAttribute(Const.SESSION_LOGIN_USER);

        if (obj != null) {
            SysUser sysUser = (SysUser) obj;
            loginModel.setLogin(true);
            loginModel.setToken(session.getId());
            loginModel.setProfile(sysUser);
            loginModel.setMenus(getMenu(sysUser));
            loginModel.setPermissions(getPermission(sysUser));
            result.setResult(loginModel);
        }

        return result;
    }

    /**
     * 查询用户所有权限
     *
     * @param param
     * @return
     * @throws BaseAppException
     */
    @FastJsonView(
        exclude = {@FastJsonFilter(clazz = SysPriv.class, props = {"createTime", "updateTime", "description"})})
    @RequestMapping(value = "/profile/privs", method = RequestMethod.POST)
    @RequiresPermissions
    public BaseResult getUserPrivs(@RequestBody BaseParam param) throws BaseAppException {
        Map params = parseParams(param);
        SysUser sysUser = (SysUser) SessionUtil.getSessionUser();

        BaseResult result = new BaseResult();
        List<SysPriv> sysPrivList = sysPrivService.queryPrivsByUserId(sysUser.getUserId());
        result.setResult(sysPrivList);

        return result;
    }

    /**
     * 修改密码
     *
     * @param param
     * @return
     * @throws BaseAppException
     */
    @RequestMapping(value = "/profile/changePwd", method = RequestMethod.POST)
    @RequiresPermissions
    public BaseResult changePwd(@RequestBody BaseParam param) throws BaseAppException {

        Map params = parseParams(param);

        if (ObjectUtil.isNull(params.get("oldPwd")) || ObjectUtil.isNull(params.get("newPwd"))) {
            ExceptionHandler.publish(INVALID_PARAMS_ERROR);
        }

        String oldPwd = null;
        String newPwd = null;

        try {
            oldPwd = (String) params.get("oldPwd");
            newPwd = (String) params.get("newPwd");
        } catch (Exception e) {
            ExceptionHandler.publish(INVALID_PARAMS_FORMAT_ERROR);
        }

        //判断是否需要解密密码
        if (sign) {
            try {
                PrivateKey privateRSAKey = RSAUtil.getPrivateRSAKey(privateKeyStr);
                oldPwd = RSAUtil.decryptToString(oldPwd, privateRSAKey);
                newPwd = RSAUtil.decryptToString(newPwd, privateRSAKey);
            } catch (Exception e) {
                ExceptionHandler.publish(INVALID_PARAMS_ERROR);
            }
        }

        SysUser sysUser = (SysUser) SessionUtil.getSessionUser();

        BaseResult result = new BaseResult();
        sysUserService.changePwd(sysUser.getUserId(), oldPwd, newPwd);

        return result;
    }

    /**
     * 登录
     *
     * @param request  req
     * @param response resp
     * @param param
     * @return
     * @throws BaseAppException
     */
    @FastJsonView(
        exclude = {
            @FastJsonFilter(clazz = SysUser.class, props = {"createTime", "updateTime", "password", "salt"}),
            @FastJsonFilter(clazz = SysPriv.class, props = {"createTime", "updateTime", "description"}),
            @FastJsonFilter(clazz = MenuModel.class, props = {"description"}),
        })
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public BaseResult login(HttpServletRequest request, HttpServletResponse response, @RequestBody BaseParam param) throws BaseAppException {

        Map params = parseParams(param);

        if (ObjectUtil.isNull(params.get("phone")) || ObjectUtil.isNull(params.get("password"))) {
            ExceptionHandler.publish(INVALID_PARAMS_ERROR);
        }

        String phone = null;
        String password = null;

        try {
            phone = (String) params.get("phone");
            password = (String) params.get("password");
        } catch (Exception e) {
            ExceptionHandler.publish(INVALID_PARAMS_FORMAT_ERROR);
        }

        //判断是否需要解密密码
        if (sign) {
            try {
                PrivateKey privateRSAKey = RSAUtil.getPrivateRSAKey(privateKeyStr);
                password = RSAUtil.decryptToString(password, privateRSAKey);
            } catch (Exception e) {
                ExceptionHandler.publish(INVALID_PARAMS_ERROR);
            }
        }

        SysUser sysUser = sysLoginService.login(phone, password);

        //更新上一次登录时间
        sysLoginService.updateLastLoginTime(sysUser);

        BaseResult result = new BaseResult();
        LoginModel loginModel = new LoginModel();

        HttpSession session = request.getSession();
        logger.debug("sessionId={}", session.getId());

        if (sysUser == null) {
            logger.debug("sysUser is invalid, please check!");
            loginModel.setLogin(false);
            ExceptionHandler.publish(USER_NOT_LOGIN);
        } else {
            logger.debug("sysUser is valid, then will generator token");

            String ua = RequestUtils.getHeader(request, Const.HTTP_HEADER_USER_AGENT, "No User Agent");
            SysUserSession sysUserSession = sysUserSessionService.addSession(sysUser.getUserId(), sysUser.getPhone(), ua, session.getId());

            session.setAttribute(Const.SESSION_LOGIN_USER, sysUser);
            session.setAttribute(Const.SESSION_LOGIN_USER_SESSION, sysUserSession);
            session.setMaxInactiveInterval(sessionExpireTime.intValue());

            loginModel.setLogin(true);
            loginModel.setToken(session.getId());
            loginModel.setProfile(sysUser);
            loginModel.setMenus(getMenu(sysUser));
            loginModel.setPermissions(getPermission(sysUser));
        }

        result.setResult(loginModel);

        return result;
    }


    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public BaseResult logout(HttpServletRequest request, HttpServletResponse response, @RequestBody BaseParam param) throws BaseAppException {
        logger.debug("logout begin");
        HttpSession session = request.getSession();

        if (session != null) {
            Object userObj = session.getAttribute(Const.SESSION_LOGIN_USER);
            if (userObj != null) {
                sysLoginService.logout((SysUser) userObj);

                session.removeAttribute(Const.SESSION_LOGIN_USER);
                session.removeAttribute(Const.SESSION_LOGIN_USER_SESSION);
                session.invalidate();
            }
        }
        return new BaseResult(true, null);
    }


    protected List<SysPriv> getPermission(SysUser sysUser) throws BaseAppException {
        List<SysPriv> sysPrivList = sysPrivService.queryPrivsByUserId(sysUser.getUserId());
        return sysPrivList;
    }

    protected List<MenuModel> getMenu(SysUser sysUser) throws BaseAppException {
        //查询用户所有的角色
        List<SysRole> sysRoles = sysRoleService.queryRolesByUserId(sysUser.getUserId());

        if (ListUtil.isEmpty(sysRoles)) {
            logger.warn("userId={},phone={} has no role", sysUser.getUserId(), sysUser.getPhone());
            return null;
        }

        //查询所有角色的权限
        List<Long> roleIds = new ArrayList<>(sysRoles.size());
        for (SysRole sysRole : sysRoles) {
            roleIds.add(sysRole.getRoleId());
        }

        List<SysPriv> sysPrivs = sysPrivService.queryPrivsByRoleIds(roleIds);
        if (ListUtil.isEmpty(sysPrivs)) {
            logger.warn("userId={},phone={} has no sysPrivs", sysUser.getUserId(), sysUser.getPhone());
            return null;
        }

        //组装成菜单
        List<MenuModel> menus = makeMenu(sysPrivs);
        return menus;
    }


    /**
     * 菜单权限
     *
     * @param sysPrivs
     * @return
     */
    private List<MenuModel> makeMenu(List<SysPriv> sysPrivs) {
        return makePriv(sysPrivs, true);
    }

    /**
     * 所有权限
     *
     * @param sysPrivs
     * @param filterDataPriv 是否要过滤数据权限
     * @return
     */
    private List<MenuModel> makePriv(List<SysPriv> sysPrivs, Boolean filterDataPriv) {
        if (filterDataPriv == null) {
            filterDataPriv = false;
        }
        List<MenuModel> menus = new ArrayList<>(sysPrivs.size());

        for (SysPriv sysPriv : sysPrivs) {
            logger.debug("sysPriv={}", sysPriv);
            MenuModel menuModel = new MenuModel();

            menuModel.setId(sysPriv.getPrivId());
            menuModel.setCode(sysPriv.getPrivCode());
            menuModel.setName(sysPriv.getPrivName());
            menuModel.setType(sysPriv.getType());
            menuModel.setUrl(sysPriv.getUrl());
            menuModel.setPath(sysPriv.getPath());
            menuModel.setDescription(sysPriv.getDescription());
            menuModel.setState(sysPriv.getState());

            if (EqualsUtil.equals(sysPriv.getType().intValue(), PrivType.DIR.getValue())) {
                List<MenuModel> subMenus = new ArrayList<>();
                menuModel.setChildren(subMenus);
                menus.add(menuModel);

            } else if (EqualsUtil.equals(sysPriv.getType().intValue(), PrivType.MENU.getValue())) {
                //如果是菜单则查找父层目录，如果没有，则添加到列表末尾
                boolean hit = false;
                for (MenuModel menu : menus) {
                    if (EqualsUtil.equals(menu.getId().longValue(), sysPriv.getParentPrivId().longValue())) {
                        menu.getChildren().add(menuModel);
                        hit = true;
                        break;
                    }
                }
                if (!hit) {
                    menus.add(menuModel);
                }
            } else if (EqualsUtil.equals(sysPriv.getType().intValue(), PrivType.DATA.getValue())) {
                if (!filterDataPriv) {
                    menus.add(menuModel);
                }
            } else {
                logger.warn("unkown type sysPriv,{}", sysPriv);
            }

        }

        return menus;
    }
}
