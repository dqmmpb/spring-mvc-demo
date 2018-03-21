package com.alphabeta.platform.web.service;

import com.alphabeta.platform.base.domain.model.SysPriv;
import com.alphabeta.platform.core.exception.BaseAppException;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * 权限服务
 *
 * @author deng.qiming
 * @date 2016-12-22 15:02
 */
public interface SysPrivService {

    /**
     * 添加权限
     *
     * @param sysPriv
     * @return
     * @throws BaseAppException
     */
    int addPriv(SysPriv sysPriv) throws BaseAppException;

    /**
     * 删除权限
     *
     * @param privId
     * @return
     * @throws BaseAppException
     */
    int delPriv(Long privId) throws BaseAppException;

    /**
     * 更新权限
     *
     * @param sysPriv
     * @return
     * @throws BaseAppException
     */
    int updatePriv(SysPriv sysPriv) throws BaseAppException;

    /**
     * 查询权限
     *
     * @param privId
     * @return
     * @throws BaseAppException
     */
    SysPriv getPriv(Long privId) throws BaseAppException;

    /**
     * 查询权限
     *
     * @param privCode
     * @return
     * @throws BaseAppException
     */
    SysPriv getPriv(String privCode) throws BaseAppException;

//    /**
//     * 获取跟目录权限
//     *
//     * @return
//     * @throws BaseAppException
//     */
//    List<SysPriv> queryRootPrivs() throws BaseAppException;

    /**
     * 查询所有权限
     *
     * @return
     * @throws BaseAppException
     */
    List<SysPriv> queryPrivs() throws BaseAppException;

    /**
     * 查询所有权限
     *
     * @param pageNum
     * @param pageSize
     * @return
     * @throws BaseAppException
     */
    List<SysPriv> queryPrivs(Integer pageNum, Integer pageSize) throws BaseAppException;

    /**
     * 查询对应Role下的所有权限
     *
     * @param roleIds
     * @return
     * @throws BaseAppException
     */
    List<SysPriv> queryPrivsByRoleIds(List<Long> roleIds) throws BaseAppException;

    /**
     * 查询对应Role下的权限
     *
     * @param roleIds
     * @param privType
     * @return
     * @throws BaseAppException
     */
    List<SysPriv> queryPrivsByRoleIds(List<Long> roleIds, Integer privType) throws BaseAppException;


    /**
     * 通过roleId 查询权限
     *
     * @param roleId
     * @return
     * @throws BaseAppException
     */
    List<SysPriv> queryPrivsByRoleId(Long roleId, Integer pageNum, Integer pageSize) throws BaseAppException;


    /**
     * 通过roleId 查询权限
     *
     * @param roleId
     * @return
     * @throws BaseAppException
     */
    List<SysPriv> queryPrivsByRoleId(Long roleId) throws BaseAppException;

    /**
     * 通过roleId 查询权限
     *
     * @param roleId
     * @return
     * @throws BaseAppException
     */
    List<SysPriv> queryPrivsByRoleId(Long roleId, Integer privType, RowBounds rowBounds) throws BaseAppException;

    /**
     * 通过roleId 查询权限
     *
     * @param roleId
     * @param privType
     * @return
     * @throws BaseAppException
     */
    List<SysPriv> queryPrivsByRoleId(Long roleId, Integer privType) throws BaseAppException;

    /**
     * 通过UserId查询权限列表
     *
     * @param userId
     * @return
     * @throws BaseAppException
     */
    List<SysPriv> queryPrivsByUserId(Long userId) throws BaseAppException;

    /**
     * 通过UserId查询权限列表
     *
     * @param userId
     * @param privType
     * @return
     * @throws BaseAppException
     */
    List<SysPriv> queryPrivsByUserId(Long userId, Integer privType) throws BaseAppException;

    /**
     * 通过type 查询权限
     *
     * @param privType
     * @return
     * @throws BaseAppException
     */
    List<SysPriv> queryPrivsByType(Integer privType) throws BaseAppException;

}
