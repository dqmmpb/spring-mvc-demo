package com.alphabeta.platform.web.service;

import com.alphabeta.platform.base.domain.model.SysUser;
import com.alphabeta.platform.core.exception.BaseAppException;

/**
 * 登录服务
 *
 * @author deng.qiming
 * @date 2016-12-26 10:05
 */
public interface SysLoginService {

    /**
     * 登录服务
     *
     * @param phone
     * @param pwd
     * @return
     * @throws BaseAppException
     */
    SysUser login(String phone, String pwd) throws BaseAppException;


    /**
     * 注销接口
     *
     * @param loginUser 登录用户
     * @throws BaseAppException
     */
    void logout(SysUser loginUser) throws BaseAppException;

    void updateLastLoginTime(SysUser sysUser) throws BaseAppException;
}
