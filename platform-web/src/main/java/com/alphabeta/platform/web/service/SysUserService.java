package com.alphabeta.platform.web.service;

import com.alphabeta.platform.base.domain.model.SysUser;
import com.alphabeta.platform.core.exception.BaseAppException;

import java.util.List;

/**
 * 用户服务
 *
 * @author deng.qiming
 * @date 2016年11月8日 上午10:55:44
 */
public interface SysUserService {
    /**
     * 添加用户
     *
     * @param sysUser 用户信息
     * @return
     * @throws BaseAppException
     */
    int addUser(SysUser sysUser) throws BaseAppException;

    /**
     * 更新
     *
     * @param sysUser
     * @return
     * @throws BaseAppException
     */
    int updateUser(SysUser sysUser) throws BaseAppException;

    /**
     * 删除用户
     *
     * @param userId
     * @return
     * @throws BaseAppException
     */
    int delUser(Long userId) throws BaseAppException;

    /**
     * 锁定用户
     *
     * @param userId
     * @param adminUserId
     * @return
     * @throws BaseAppException
     */
    int lockUser(Long userId, Long adminUserId) throws BaseAppException;

    /**
     * 解锁用户
     *
     * @param userId
     * @return
     * @throws BaseAppException
     */
    int unlockUser(Long userId) throws BaseAppException;

    /**
     * 重置用户密码
     *
     * @param userId
     * @return
     * @throws BaseAppException
     */
    int resetPwd(Long userId) throws BaseAppException;

    /**
     * 通过ID查询用户
     *
     * @param userId
     * @return
     * @throws BaseAppException
     */
    SysUser getUser(Long userId) throws BaseAppException;

    /**
     * 通过用户名查询用户
     *
     * @param phone 用户名
     * @return
     * @throws BaseAppException
     */
    SysUser getUser(String phone) throws BaseAppException;

    /**
     * 分页获取用户列表
     *
     *
     * @param filter
     * @param pageNum
     * @param pageSize
     * @return
     * @throws BaseAppException
     */
    List<SysUser> queryUsers(String filter, Integer pageNum, Integer pageSize) throws BaseAppException;

    /**
     * 通过roleId查询用户
     *
     * @param roleId
     * @return
     * @throws BaseAppException
     */
    List<SysUser> queryUsersByRoleId(Long roleId) throws BaseAppException;

    /**
     * 修改密码
     *
     * @param userId
     * @param oldPwdText
     * @param newPwdText
     * @return
     */
    int changePwd(Long userId, String oldPwdText, String newPwdText) throws BaseAppException;

    boolean validatePwd(String password, String salt, String pwdText) throws BaseAppException;

//    /**
//     * 判断用户是否有权限菜单
//     *
//     * @param userId
//     * @param path   页面路径 （浏览器上地址，不是磁盘上地址）
//     * @return
//     * @throws BaseAppException
//     */
//    Boolean checkUserMenuPriv(Long userId, String path) throws BaseAppException;

}
