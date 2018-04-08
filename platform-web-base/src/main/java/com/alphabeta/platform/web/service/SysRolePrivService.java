package com.alphabeta.platform.web.service;

import com.alphabeta.platform.core.exception.BaseAppException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * SysRolePriv Service
 *
 * @author deng.qiming
 * @date 2016-12-25 15:51
 */
public interface SysRolePrivService {
    /**
     * 添加角色权限
     *
     * @param roleId
     * @param privId
     * @return
     * @throws BaseAppException
     */
    @Transactional(readOnly = false)
    int addRolePriv(Long roleId, Long privId) throws BaseAppException;

    /**
     * 批量添加角色权限
     *
     * @param roleId
     * @param privIds
     * @return
     * @throws BaseAppException
     */
    @Transactional(readOnly = false)
    int addRolePriv(Long roleId, List<Long> privIds) throws BaseAppException;

    /**
     * 禁用角色权限
     *
     * @param roleId
     * @param privId
     * @return
     * @throws BaseAppException
     */
    @Transactional(readOnly = false)
    int disableRolePriv(Long roleId, Long privId) throws BaseAppException;

    /**
     * 启用角色权限
     *
     * @param roleId
     * @param privId
     * @return
     * @throws BaseAppException
     */
    @Transactional(readOnly = false)
    int enableRolePriv(Long roleId, Long privId) throws BaseAppException;

    /**
     * 删除角色权限
     *
     * @param roleId
     * @param privId
     * @return
     * @throws BaseAppException
     */
    @Transactional(readOnly = false)
    int delRolePriv(Long roleId, Long privId) throws BaseAppException;

    /**
     * 删除角色权限
     *
     * @param roleId
     * @param privIds
     * @return
     * @throws BaseAppException
     */
    @Transactional(readOnly = false)
    int delRolePriv(Long roleId, List<Long> privIds) throws BaseAppException;

    /**
     * 分配角色权限
     *
     * @param roleId
     * @param privIds
     * @return
     * @throws BaseAppException
     */
    @Transactional(readOnly = false)
    int allocateRolePriv(Long roleId, List<Long> privIds) throws BaseAppException;
}
