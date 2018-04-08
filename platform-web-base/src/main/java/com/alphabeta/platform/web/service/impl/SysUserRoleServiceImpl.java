package com.alphabeta.platform.web.service.impl;

import com.alphabeta.platform.base.dao.mapper.ext.SysUserRoleExtMapper;
import com.alphabeta.platform.base.domain.model.SysUserRole;
import com.alphabeta.platform.core.domain.BaseService;
import com.alphabeta.platform.core.exception.BaseAppException;
import com.alphabeta.platform.core.exception.ExceptionHandler;
import com.alphabeta.platform.web.service.SysUserRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import static com.alphabeta.platform.base.common.Const.STATE_A;
import static com.alphabeta.platform.base.common.Const.STATE_X;
import static com.alphabeta.platform.base.common.ErrorCode.USERROLE_HAS_EXIST;

/**
 * 系统用户角色关联
 *
 * @author deng.qiming
 * @date 2016-12-25 13:02
 */
@Service
@Transactional(readOnly = true)
public class SysUserRoleServiceImpl extends BaseService implements SysUserRoleService {

    @Resource
    private SysUserRoleExtMapper sysUserRoleExtMapper;

    @Override
    @Transactional(readOnly = false)
    public int addUserRole(Long userId, Long roleId) throws BaseAppException {
        //判断是否已经绑定
        boolean exist = sysUserRoleExtMapper.checkUserRoleExist(userId, roleId);
        if (exist) {
            ExceptionHandler.publish(USERROLE_HAS_EXIST);
        }

        SysUserRole sysUserRole = new SysUserRole();
        sysUserRole.setUserId(userId);
        sysUserRole.setRoleId(roleId);
        sysUserRole.setState(STATE_A);

        Date now = new Date();
        sysUserRole.setCreateTime(now);
        sysUserRole.setUpdateTime(now);

        return sysUserRoleExtMapper.insert(sysUserRole);
    }

    @Override
    @Transactional(readOnly = false)
    public int addUserRole(Long userId, List<Long> roleIds) throws BaseAppException {
        // 如果存在则pass；如果不存在则添加
        int count = 0;
        for (Long roleId : roleIds) {
            count += addUserRole(userId, roleId);
        }
        return count;
    }

    @Override
    @Transactional(readOnly = false)
    public int disableUserRole(Long userId, Long roleId) throws BaseAppException {
        logger.debug("disableUserRole userId={},roleId={},state={}", userId, roleId, STATE_X);
        SysUserRole sysUserRole = new SysUserRole();
        sysUserRole.setUserId(userId);
        sysUserRole.setRoleId(roleId);
        sysUserRole.setState(STATE_X);
        return sysUserRoleExtMapper.updateByPrimaryKeySelective(sysUserRole);
    }

    @Override
    @Transactional(readOnly = false)
    public int enableUserRole(Long userId, Long roleId) throws BaseAppException {
        logger.debug("enableUserRole userId={},roleId={},state={}", userId, roleId, STATE_A);
        SysUserRole sysUserRole = new SysUserRole();
        sysUserRole.setUserId(userId);
        sysUserRole.setRoleId(roleId);
        sysUserRole.setState(STATE_A);
        return sysUserRoleExtMapper.updateByPrimaryKeySelective(sysUserRole);
    }

    @Override
    @Transactional(readOnly = false)
    public int delUserRole(Long userId, Long roleId) throws BaseAppException {
        logger.debug("delete user role userId={},roleId={}", userId, roleId);
        SysUserRole sysUserRole = new SysUserRole();
        sysUserRole.setUserId(userId);
        sysUserRole.setRoleId(roleId);
        return sysUserRoleExtMapper.deleteByPrimaryKey(sysUserRole);
    }

    @Override
    @Transactional(readOnly = false)
    public int delUserRole(Long userId, List<Long> roleIds) throws BaseAppException {
        int count = 0;
        for (Long roleId : roleIds) {
            count += delUserRole(userId, roleId);
        }
        return count;
    }

    @Override
    @Transactional(readOnly = false)
    public int allocateUserRole(Long userId, List<Long> roleIds) throws BaseAppException {
        Example example = new Example(SysUserRole.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", userId);
        List<SysUserRole> oldUserRoleList = sysUserRoleExtMapper.selectByExample(example);
        List<Long> oldRoleIds = new ArrayList<Long>();
        for (SysUserRole sysUserRole : oldUserRoleList) {
            oldRoleIds.add(sysUserRole.getRoleId());
        }

        HashSet<Long> oldSet = new HashSet<Long>();
        oldSet.addAll(oldRoleIds);
        HashSet<Long> newSet = new HashSet<Long>();
        newSet.addAll(roleIds);

        HashSet<Long> deleteSet = new HashSet<Long>();
        deleteSet.addAll(oldRoleIds);
        deleteSet.removeAll(newSet);

        HashSet<Long> addSet = new HashSet<Long>();
        addSet.addAll(roleIds);
        addSet.removeAll(oldSet);

        this.delUserRole(userId, new ArrayList<Long>(deleteSet));
        this.addUserRole(userId, new ArrayList<Long>(addSet));

        return 0;
    }
}
