package com.alphabeta.platform.web.service.impl;

import com.alphabeta.platform.base.dao.mapper.ext.SysRolePrivExtMapper;
import com.alphabeta.platform.base.domain.model.SysRolePriv;
import com.alphabeta.platform.core.domain.BaseService;
import com.alphabeta.platform.core.exception.BaseAppException;
import com.alphabeta.platform.core.exception.ExceptionHandler;
import com.alphabeta.platform.web.service.SysRolePrivService;
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
import static com.alphabeta.platform.base.common.ErrorCode.ROLEPRIV_HAS_EXIST;

/**
 * 系统角色权限关联
 *
 * @author deng.qiming
 * @date 2016-12-25 15:53
 */
@Service
public class SysRolePrivServiceImpl extends BaseService implements SysRolePrivService {

    @Resource
    private SysRolePrivExtMapper sysRolePrivExtMapper;

    @Override
    @Transactional(readOnly = false)
    public int addRolePriv(Long roleId, Long privId) throws BaseAppException {
        // 检查编码是否重复
        boolean exist = sysRolePrivExtMapper.checkRolePrivExist(roleId, privId);
        if (exist) {
            ExceptionHandler.publish(ROLEPRIV_HAS_EXIST);
        }

        SysRolePriv sysRolePriv = new SysRolePriv();
        sysRolePriv.setRoleId(roleId);
        sysRolePriv.setPrivId(privId);

        // 其他信息
        Date now = new Date();
        sysRolePriv.setCreateTime(now);
        sysRolePriv.setUpdateTime(now);
        sysRolePriv.setState(STATE_A);

        return sysRolePrivExtMapper.insert(sysRolePriv);
    }

    @Override
    @Transactional(readOnly = false)
    public int addRolePriv(Long roldId, List<Long> privIds) throws BaseAppException {
        //TODO 如果存在则pass；如果不存在则添加
        int count = 0;
        for (Long privId : privIds) {
            count += addRolePriv(roldId, privId);
        }
        return count;
    }

    @Override
    @Transactional(readOnly = false)
    public int disableRolePriv(Long roleId, Long privId) throws BaseAppException {
        SysRolePriv sysRolePriv = new SysRolePriv();
        sysRolePriv.setRoleId(roleId);
        sysRolePriv.setPrivId(privId);
        sysRolePriv.setState(STATE_X);
        return sysRolePrivExtMapper.updateByPrimaryKeySelective(sysRolePriv);
    }

    @Override
    @Transactional(readOnly = false)
    public int enableRolePriv(Long roleId, Long privId) throws BaseAppException {
        SysRolePriv sysRolePriv = new SysRolePriv();
        sysRolePriv.setRoleId(roleId);
        sysRolePriv.setPrivId(privId);
        sysRolePriv.setState(STATE_A);
        return sysRolePrivExtMapper.updateByPrimaryKeySelective(sysRolePriv);
    }

    @Override
    @Transactional(readOnly = false)
    public int delRolePriv(Long roleId, Long privId) throws BaseAppException {
        SysRolePriv sysRolePriv = new SysRolePriv();
        sysRolePriv.setRoleId(roleId);
        sysRolePriv.setPrivId(privId);
        return sysRolePrivExtMapper.deleteByPrimaryKey(sysRolePriv);
    }

    @Override
    @Transactional(readOnly = false)
    public int delRolePriv(Long roleId, List<Long> privIds) throws BaseAppException {
        int count = 0;
        for (Long privId : privIds) {
            count += delRolePriv(roleId, privId);
        }
        return count;
    }

    @Override
    @Transactional(readOnly = false)
    public int allocateRolePriv(Long roleId, List<Long> privIds) throws BaseAppException {
        Example example = new Example(SysRolePriv.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("roleId", roleId);
        List<SysRolePriv> oldRolePrivList = sysRolePrivExtMapper.selectByExample(example);
        List<Long> oldPrivIds = new ArrayList<Long>();
        for (SysRolePriv sysRolePriv : oldRolePrivList) {
            oldPrivIds.add(sysRolePriv.getPrivId());
        }

        HashSet<Long> oldSet = new HashSet<Long>();
        oldSet.addAll(oldPrivIds);
        HashSet<Long> newSet = new HashSet<Long>();
        newSet.addAll(privIds);

        HashSet<Long> deleteSet = new HashSet<Long>();
        deleteSet.addAll(oldPrivIds);
        deleteSet.removeAll(newSet);

        HashSet<Long> addSet = new HashSet<Long>();
        addSet.addAll(privIds);
        addSet.removeAll(oldSet);

        this.delRolePriv(roleId, new ArrayList<Long>(deleteSet));
        this.addRolePriv(roleId, new ArrayList<Long>(addSet));

        return 0;
    }
}
