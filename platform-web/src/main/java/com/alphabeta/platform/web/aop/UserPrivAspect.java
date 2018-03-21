package com.alphabeta.platform.web.aop;

import com.alphabeta.platform.base.common.Const;
import com.alphabeta.platform.base.common.PrivType;
import com.alphabeta.platform.base.common.StatusType;
import com.alphabeta.platform.base.domain.model.SysPriv;
import com.alphabeta.platform.base.domain.model.SysUser;
import com.alphabeta.platform.core.annotation.RequiresPermissions;
import com.alphabeta.platform.core.exception.ExceptionHandler;
import com.alphabeta.platform.core.util.EqualsUtil;
import com.alphabeta.platform.core.util.ListUtil;
import com.alphabeta.platform.core.util.StringUtil;
import com.alphabeta.platform.web.service.SysPrivService;
import com.alphabeta.platform.web.service.SysUserService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.List;

import static com.alphabeta.platform.base.common.ErrorCode.*;

/**
 * 模块名
 *
 * @author deng.qiming
 * @date 2017-01-10 15:06
 */
public class UserPrivAspect {

    private static Logger logger = LoggerFactory.getLogger(UserPrivAspect.class);

    @Resource
    private SysPrivService sysPrivService;

    @Resource
    private SysUserService sysUserService;

    public void before(JoinPoint joinPoint) throws Throwable {

        logger.debug("check user priv....");
        MethodSignature joinPointObject = (MethodSignature) joinPoint.getSignature();
        Method method = joinPointObject.getMethod();
        RequiresPermissions requiresPermissions = method.getAnnotation(RequiresPermissions.class);

        if (requiresPermissions != null) {
            String permissions = requiresPermissions.value();

            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            HttpSession session = request.getSession();

            Object userObj = session.getAttribute(Const.SESSION_LOGIN_USER);
            if (userObj == null) {
                logger.error("session user is null, plz check!");
                ExceptionHandler.publish(USER_SESSION_TIMEOUT);
            }

            SysUser sysUser = (SysUser) userObj;
            if (ObjectUtils.isEmpty(sysUser) || ObjectUtils.isEmpty(sysUser.getUserId())) {
                logger.error("user info is null, plz check");
                ExceptionHandler.publish(USER_SESSION_TIMEOUT);
            }

            //更新session中的管理员信息
            SysUser realUser = sysUserService.getUser(sysUser.getUserId());
            sysUser.setState(realUser.getState());
            sysUser.setStatus(realUser.getStatus());

            // 判断用户可用状态
            if (EqualsUtil.equals(sysUser.getState(), Const.STATE_X)) {
                logger.debug("user has been delete");
                ExceptionHandler.publish(USER_HAS_DELETED);
            }

            // 判断用户状态
            if (EqualsUtil.equals(sysUser.getStatus().intValue(), StatusType.LOCK.getValue())) {
                logger.debug("user has been locked");
                ExceptionHandler.publish(USER_HAS_LOCKED);
            }

            //判断用户密码是否被重置
            if (!sysUser.getPassword().equals(realUser.getPassword())) {
                ExceptionHandler.publish(USER_PASSWORD_RESET_ERROR);
            }

            // 无permissions注解参数的，不进行权限验证，只验证用户是否登录
            if (StringUtil.isNotEmpty(permissions)) {
                //用户权限列表
                List<SysPriv> sysPrivs = sysPrivService.queryPrivsByUserId(sysUser.getUserId(), PrivType.DATA.getValue());
                //TODO maybe cache is nice.

                Boolean hit = false;
                if (ListUtil.isNotEmpty(sysPrivs)) {
                    for (SysPriv sysPriv : sysPrivs) {
                        if (EqualsUtil.equals(sysPriv.getPrivCode(), permissions)) {
                            hit = true;
                            break;
                        }
                    }
                }

                if (!hit) {
                    ExceptionHandler.publish(NO_PERMISSION);
                }
            }

        } else {
            logger.warn("There is no @RequiresPermissions in method. Please check.");
        }
    }

}
