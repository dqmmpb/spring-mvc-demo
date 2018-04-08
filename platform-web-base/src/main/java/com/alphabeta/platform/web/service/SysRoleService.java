package com.alphabeta.platform.web.service;

import com.alphabeta.platform.base.domain.model.SysRole;
import com.alphabeta.platform.core.exception.BaseAppException;

import java.util.List;

/**
 * 角色服务
 *
 * @author deng.qiming
 * @date 2016-12-22 15:02
 */
public interface SysRoleService {

    /**
     * 添加角色
     *
     * @param roleCode
     * @param roleName
     * @param desc
     * @return
     * @throws BaseAppException
     */
    int addRole(String roleCode, String roleName, String desc) throws BaseAppException;

    /**
     * 删除Role
     *
     * @param roleId
     * @return
     * @throws BaseAppException
     */
    int delRole(Long roleId) throws BaseAppException;

    /**
     * 更新Role
     *
     * @param sysRole
     * @return
     * @throws BaseAppException
     */
    int updateRole(SysRole sysRole) throws BaseAppException;

    /**
     * 禁用role
     *
     * @param roleId
     * @return
     * @throws BaseAppException
     */
    int disableRole(Long roleId) throws BaseAppException;

    /**
     * 查询Role
     *
     * @param roleId
     * @return
     * @throws BaseAppException
     */
    SysRole getRole(Long roleId) throws BaseAppException;

    /**
     * 查询Role
     *
     * @param roleCode
     * @return
     * @throws BaseAppException
     */
    SysRole getRole(String roleCode) throws BaseAppException;

    /**
     * 启用role
     *
     * @param roleId
     * @return
     * @throws BaseAppException
     */
    int enableRole(Long roleId) throws BaseAppException;

    /**
     * 检查角色编码
     *
     * @param roleCode
     * @return
     * @throws BaseAppException
     */
    Boolean checkRoleCode(String roleCode) throws BaseAppException;

    /**
     * 查询列表,扁平列表，所有
     *
     * @return
     * @throws BaseAppException
     */
    List<SysRole> queryRoles() throws BaseAppException;


    /**
     * 查询列表
     *
     * @param pageNum
     * @param pageSize
     * @return
     * @throws BaseAppException
     */
    List<SysRole> queryRoles(Integer pageNum, Integer pageSize) throws BaseAppException;

    /**
     * 查询用户对应的角色
     *
     * @param userId
     * @return
     * @throws BaseAppException
     */
    List<SysRole> queryRolesByUserId(Long userId) throws BaseAppException;

}
