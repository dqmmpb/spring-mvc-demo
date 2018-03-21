package com.alphabeta.platform.web.service.impl;

import com.alphabeta.platform.base.common.Const;
import com.alphabeta.platform.base.common.StatusType;
import com.alphabeta.platform.base.domain.model.SysUser;
import com.alphabeta.platform.core.domain.BaseService;
import com.alphabeta.platform.core.exception.BaseAppException;
import com.alphabeta.platform.core.exception.ExceptionHandler;
import com.alphabeta.platform.core.util.EqualsUtil;
import com.alphabeta.platform.web.service.SysLoginService;
import com.alphabeta.platform.web.service.SysUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

import static com.alphabeta.platform.base.common.ErrorCode.USER_HAS_LOCKED;
import static com.alphabeta.platform.base.common.ErrorCode.USER_NAME_OR_PWD_ERROR;

/**
 * Login Module
 *
 * @author deng.qiming
 * @date 2016-12-26 10:06
 */
@Service
@Transactional(readOnly = true)
public class SysLoginServiceImpl extends BaseService implements SysLoginService {

    @Resource
    private SysUserService sysUserService;

    @Override
    @Transactional(readOnly = false)
    public SysUser login(String phone, String pwd) throws BaseAppException {
        //TODO  校验UserCode

        SysUser sysUser = sysUserService.getUser(phone);
        if (sysUser == null) {
            logger.debug("sysUser [{}] not exist", phone);
            ExceptionHandler.publish(USER_NAME_OR_PWD_ERROR);
        }

        if (!EqualsUtil.equals(Const.STATE_A, sysUser.getState())) {
            logger.debug("sysUser [{}] not exist", phone);
            ExceptionHandler.publish(USER_NAME_OR_PWD_ERROR);
        }

        logger.debug("user={}", sysUser);

        // 判断用户状态
        if (EqualsUtil.equals(sysUser.getStatus().intValue(), StatusType.LOCK.getValue())) {
            logger.debug("user has been locked");
            ExceptionHandler.publish(USER_HAS_LOCKED);
        }

        // 验证密码
        if (!sysUserService.validatePwd(pwd, sysUser.getSalt(), sysUser.getPassword())) {
            logger.debug("sysUser[{}] pwd is wrong", sysUser.getPhone());
            ExceptionHandler.publish(USER_NAME_OR_PWD_ERROR);
        }

        //TODO 其他校验
        return sysUser;
    }

    @Override
    @Transactional(readOnly = false)
    public void logout(SysUser loginUser) throws BaseAppException {
        if (loginUser == null) {
            logger.warn("login user is null.");
            return;
        }
    }

    @Override
    @Transactional(readOnly = false)
    public void updateLastLoginTime(SysUser sysUser) throws BaseAppException {
        SysUser sysUserObj = new SysUser();
        sysUserObj.setUserId(sysUser.getUserId());
        sysUserObj.setLastLoginTime(new Date());
        sysUserService.updateUser(sysUserObj);
    }
}
