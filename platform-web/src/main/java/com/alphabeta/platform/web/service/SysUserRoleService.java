package com.alphabeta.platform.web.service;

import com.alphabeta.platform.core.exception.BaseAppException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 模块名
 *
 * @author deng.qiming
 * @date 2016-12-22 15:30
 */
public interface SysUserRoleService {

    /**
     * 添加用户角色
     *
     * @param userId
     * @param roleId
     * @return
     * @throws BaseAppException
     */
    @Transactional(readOnly = false)
    int addUserRole(Long userId, Long roleId) throws BaseAppException;

    /**
     * 批量添加用户角色
     *
     * @param userId
     * @param roleIds
     * @return
     * @throws BaseAppException
     */
    @Transactional(readOnly = false)
    int addUserRole(Long userId, List<Long> roleIds) throws BaseAppException;

    /**
     * 禁用用户角色
     *
     * @param userId
     * @param roleId
     * @return
     * @throws BaseAppException
     */
    @Transactional(readOnly = false)
    int disableUserRole(Long userId, Long roleId) throws BaseAppException;

    /**
     * 启用用户角色
     *
     * @param userId
     * @param roleId
     * @return
     * @throws BaseAppException
     */
    @Transactional(readOnly = false)
    int enableUserRole(Long userId, Long roleId) throws BaseAppException;

    /**
     * 删除用户角色
     *
     * @param userId
     * @param roleId
     * @return
     * @throws BaseAppException
     */
    @Transactional(readOnly = false)
    int delUserRole(Long userId, Long roleId) throws BaseAppException;

    @Transactional(readOnly = false)
    int delUserRole(Long userId, List<Long> roleIds) throws BaseAppException;

    /**
     * 分配用户角色
     *
     * @param userId
     * @param roleIds
     * @return
     * @throws BaseAppException
     */
    @Transactional(readOnly = false)
    int allocateUserRole(Long userId, List<Long> roleIds) throws BaseAppException;
}
