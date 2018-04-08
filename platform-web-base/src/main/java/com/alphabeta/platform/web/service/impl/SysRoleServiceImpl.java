package com.alphabeta.platform.web.service.impl;

import com.alphabeta.platform.base.dao.mapper.ext.SysRoleExtMapper;
import com.alphabeta.platform.base.dao.mapper.ext.SysRolePrivExtMapper;
import com.alphabeta.platform.base.dao.mapper.ext.SysUserRoleExtMapper;
import com.alphabeta.platform.base.domain.model.SysRole;
import com.alphabeta.platform.core.domain.BaseService;
import com.alphabeta.platform.core.exception.BaseAppException;
import com.alphabeta.platform.core.exception.ExceptionHandler;
import com.alphabeta.platform.web.service.SysRoleService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

import static com.alphabeta.platform.base.common.Const.STATE_A;
import static com.alphabeta.platform.base.common.Const.STATE_X;
import static com.alphabeta.platform.base.common.ErrorCode.*;

/**
 * 系统角色
 *
 * @author deng.qiming
 * @date 2016-12-22 15:33
 */
@Service
@Transactional(readOnly = true)
public class SysRoleServiceImpl extends BaseService implements SysRoleService {

    @Resource
    private SysRoleExtMapper sysRoleExtMapper;

    @Resource
    private SysUserRoleExtMapper sysUserRoleExtMapper;

    @Resource
    private SysRolePrivExtMapper sysRolePrivExtMapper;


    @Override
    @Transactional(readOnly = false)
    public int addRole(String roleCode, String roleName, String desc) throws BaseAppException {
        // 唯一性校验
        boolean exist = sysRoleExtMapper.checkRoleCode(roleCode, null);
        if (exist) {
            ExceptionHandler.publish(ROLE_CODE_HAS_EXIST);
        }

        SysRole sysRole = new SysRole();
        sysRole.setRoleCode(roleCode);
        sysRole.setRoleName(roleName);
        sysRole.setDescription(desc);

        // 其他信息
        Date now = new Date();
        sysRole.setCreateTime(now);
        sysRole.setUpdateTime(now);
        sysRole.setState(STATE_A);

        sysRoleExtMapper.insert(sysRole);
        return 0;
    }

    @Override
    @Transactional(readOnly = false)
    public int delRole(Long roleId) throws BaseAppException {
        //只有在改role不被user 和 priv引用时才可以删除
        Boolean exist = sysUserRoleExtMapper.checkRoleExist(roleId);
        if (exist) {
            ExceptionHandler.publish(ROLE_HAS_USER_ROLE_REF);
        }
        exist = sysRolePrivExtMapper.checkRoleExist(roleId);
        if (exist) {
            ExceptionHandler.publish(PRIV_HAS_ROLE_PRIV_REF);
        }

        return sysRoleExtMapper.deleteByPrimaryKey(roleId);
    }

    @Override
    @Transactional(readOnly = false)
    public int updateRole(SysRole sysRole) throws BaseAppException {
        if (sysRole == null) {
            logger.warn("sysRole obj is null, please check!");
            ExceptionHandler.publish(ROLE_IS_NULL);
        }

        if (sysRole.getRoleId() == null) {
            logger.warn("sysRole obj is null, please check!");
            ExceptionHandler.publish(ROLE_NOT_EXIST);
        }

        if (sysRole.getRoleCode() != null) {
            // 检查角色编码是否重复,排他性检查
            boolean exist = sysRoleExtMapper.checkRoleCode(sysRole.getRoleCode(), sysRole.getRoleId());
            if (exist) {
                ExceptionHandler.publish(ROLE_CODE_HAS_EXIST);
            }
        }

        sysRole.setUpdateTime(new Date());
        // 不更新CreateTime，State
        sysRole.setCreateTime(null);
        sysRole.setState(null);

        return sysRoleExtMapper.updateByPrimaryKeySelective(sysRole);
    }

    @Override
    @Transactional(readOnly = false)
    public int disableRole(Long roleId) throws BaseAppException {
        SysRole sysRole = new SysRole();
        sysRole.setRoleId(roleId);
        sysRole.setState(STATE_X);
        return sysRoleExtMapper.updateByPrimaryKeySelective(sysRole);
    }

    @Override
    public SysRole getRole(Long roleId) throws BaseAppException {
        return sysRoleExtMapper.selectByPrimaryKey(roleId);
    }

    @Override
    public SysRole getRole(String roleCode) throws BaseAppException {
        //通用Example查询
        Example example = new Example(SysRole.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("roleCode", roleCode);
        return sysRoleExtMapper.selectOneByExample(example);
    }

    @Override
    @Transactional(readOnly = false)
    public int enableRole(Long roleId) throws BaseAppException {
        SysRole sysRole = new SysRole();
        sysRole.setRoleId(roleId);
        sysRole.setState(STATE_A);
        return sysRoleExtMapper.updateByPrimaryKeySelective(sysRole);
    }

    @Override
    public Boolean checkRoleCode(String roleCode) throws BaseAppException {
        return sysRoleExtMapper.checkRoleCode(roleCode, null);
    }

    @Override
    public List<SysRole> queryRoles() throws BaseAppException {
        //通用Example查询
        Example example = new Example(SysRole.class);
        return sysRoleExtMapper.selectByExample(example);
    }

    @Override
    public List<SysRole> queryRoles(Integer pageNum, Integer pageSize) throws BaseAppException {
        RowBounds rowBounds = new RowBounds(pageNum, pageSize);
        //通用Example查询
        Example example = new Example(SysRole.class);
        return sysRoleExtMapper.selectByExampleAndRowBounds(example, rowBounds);
    }

    @Override
    public List<SysRole> queryRolesByUserId(Long userId) throws BaseAppException {
        return sysRoleExtMapper.queryRolesByUserId(userId);
    }


}
