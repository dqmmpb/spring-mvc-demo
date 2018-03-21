package com.alphabeta.platform.web.service.impl;

import com.alphabeta.platform.base.common.Const;
import com.alphabeta.platform.base.common.StatusType;
import com.alphabeta.platform.base.dao.mapper.ext.SysUserExtMapper;
import com.alphabeta.platform.base.dao.mapper.ext.SysPrivExtMapper;
import com.alphabeta.platform.core.domain.BaseService;
import com.alphabeta.platform.base.domain.model.SysUser;
import com.alphabeta.platform.core.exception.BaseAppException;
import com.alphabeta.platform.base.exception.ExceptionHandler;
import com.alphabeta.platform.core.util.EncryptUtil;
import com.alphabeta.platform.core.util.EqualsUtil;
import com.alphabeta.platform.base.util.RegExpUtil;
import com.alphabeta.platform.core.util.StringUtil;
import com.alphabeta.platform.web.service.SysUserService;
import com.alphabeta.platform.web.service.SysPrivService;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

import static com.alphabeta.platform.base.common.ErrorCode.*;
import static com.alphabeta.platform.web.common.ErrorCode.ERROR_INVALID_PARAMS;

/**
 * 系统用户
 *
 * @author deng.qiming
 * @date 2016年11月8日 上午11:14:17
 */
@Service
@Transactional(readOnly = true)
public class SysUserServiceImpl extends BaseService implements SysUserService {

    @Resource
    private SysUserExtMapper sysUserExtMapper;

    @Resource
    private SysPrivExtMapper sysPrivExtMapper;

    @Resource
    private SysPrivService sysPrivService;

    @Override
    @Transactional(readOnly = false)
    public int addUser(SysUser sysUser) throws BaseAppException {
        Assert.notNull(sysUser, "sysUser obj is null, please check!");
        Assert.notNull(sysUser.getPhone(), "sysUser phone is null, please check!");

        if (StringUtils.isBlank(sysUser.getName())) {
            ExceptionHandler.publish(ERROR_INVALID_PARAMS.getCodeString());
        }

        String phone = sysUser.getPhone();

        //校验手机号格式
        if (!RegExpUtil.isPhone(phone)) {
            ExceptionHandler.publish(PHONE_FORMAT_ERROR);
        }

        //校验手机号是否已存在
        boolean exist = sysUserExtMapper.checkPhone(phone, null);
        if (exist) {
            ExceptionHandler.publish(USER_NAME_HAS_EXIST);
        }

        //默认使用手机号后六位作为密码
        String password = StringUtil.substringLast(phone, 6);
        EncryptUtil.Encrypt encrypt = getEncryptPwd(password, null);
        sysUser.setPassword(encrypt.getEncrypt());
        sysUser.setSalt(encrypt.getSalt());

        // 其他信息
        Date now = new Date();
        sysUser.setCreateTime(now);
        sysUser.setUpdateTime(now);
        sysUser.setState(Const.STATE_A);
        sysUser.setStatus(StatusType.UNLOCK.getValue());

        return sysUserExtMapper.insert(sysUser);
    }

    @Override
    @Transactional(readOnly = false)
    public int updateUser(SysUser sysUser) throws BaseAppException {
        logger.debug("sysUser={}", sysUser);

        // phone不能更新，密码、Salt同样不能更新
        sysUser.setPhone(null);
        sysUser.setPassword(null);
        sysUser.setSalt(null);
        sysUser.setCreateTime(null);

        return sysUserExtMapper.updateByPrimaryKeySelective(sysUser);
    }

    @Override
    @Transactional(readOnly = false)
    public int delUser(Long userId) throws BaseAppException {
        //注意，不能删除用户
        SysUser sysUser = new SysUser();
        sysUser.setUserId(userId);
        sysUser.setState(Const.STATE_X);

        return sysUserExtMapper.updateByPrimaryKeySelective(sysUser);
    }

    @Override
    @Transactional(readOnly = false)
    public int lockUser(Long userId, Long adminUserId) throws BaseAppException {
        if (EqualsUtil.equals(userId, adminUserId)) {
            ExceptionHandler.publish(USER_LOCK_SELF_ERROR);
        }

        SysUser sysUser = new SysUser();
        sysUser.setUserId(userId);
        sysUser.setStatus(StatusType.LOCK.getValue());

        return sysUserExtMapper.updateByPrimaryKeySelective(sysUser);
    }

    @Override
    @Transactional(readOnly = false)
    public int unlockUser(Long userId) throws BaseAppException {
        SysUser sysUser = new SysUser();
        sysUser.setUserId(userId);
        sysUser.setStatus(StatusType.UNLOCK.getValue());

        return sysUserExtMapper.updateByPrimaryKeySelective(sysUser);
    }

    @Override
    @Transactional(readOnly = false)
    public int resetPwd(Long userId) throws BaseAppException {
        SysUser sysUser = sysUserExtMapper.selectByPrimaryKey(userId);

        Assert.notNull(sysUser, "sysUser obj is null, please check!");
        logger.debug("sysUser={}", sysUser);

        SysUser record = new SysUser();
        record.setUserId(sysUser.getUserId());
        String password = StringUtil.substringLast(sysUser.getPhone(), 6);
        EncryptUtil.Encrypt encrypt = getEncryptPwd(password, sysUser.getSalt());
        record.setPassword(encrypt.getEncrypt());
        record.setSalt(encrypt.getSalt());

        return sysUserExtMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public SysUser getUser(Long userId) throws BaseAppException {
        SysUser sysUser = sysUserExtMapper.selectByPrimaryKey(userId);
        // sysUser.setPassword(null); //禁止返回密码
        return sysUser;
    }

    @Override
    public SysUser getUser(String phone) throws BaseAppException {
        //通用Example查询
        Example example = new Example(SysUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("phone", phone);
        return sysUserExtMapper.selectOneByExample(example);
    }

    @Override
    public List<SysUser> queryUsers(String filter, Integer pageNum, Integer pageSize) throws BaseAppException {
        RowBounds rowBounds = new RowBounds(pageNum, pageSize);
        //通用Example查询
        Example example = new Example(SysUser.class);
        if (StringUtils.isNotBlank(filter)) {
            filter = "%" + filter + "%";
            Example.Criteria criteria1 = example.createCriteria();
            criteria1.andLike("phone", filter);
            Example.Criteria criteria2 = example.createCriteria();
            criteria2.andLike("name", filter);
            example.or(criteria2);
        }
        example.setOrderByClause("create_time desc");
        return sysUserExtMapper.selectByExampleAndRowBounds(example, rowBounds);
    }

    @Override
    public List<SysUser> queryUsersByRoleId(Long roleId) throws BaseAppException {
        return sysUserExtMapper.getUsersByRoleId(roleId);
    }

    @Override
    @Transactional(readOnly = false)
    public int changePwd(Long userId, String oldPwdText, String newPwdText) throws BaseAppException {

        SysUser sysUser = sysUserExtMapper.selectByPrimaryKey(userId);

        Assert.notNull(sysUser, "sysUser obj is null, please check!");
        logger.debug("sysUser={}", sysUser);

        //原密码不能过于简单（不能相同）
        if (EqualsUtil.equals(oldPwdText, newPwdText)) {
            ExceptionHandler.publish(USER_CHANGE_PWD_ORIGIN_AND_NEW_ARE_SAME_ERROR);
        }
        // 最基本的长度要求
        if (newPwdText.length() < 6 || newPwdText.length() > 20) {
            ExceptionHandler.publish(USER_PWD_LENGTH_ERROR);
        }

        //TODO 复杂度要求

        //验证原密码
        EncryptUtil.Encrypt encrypt = getEncryptPwd(oldPwdText, sysUser.getSalt());
        String encryptPwd = encrypt.getEncrypt();

        if (!EqualsUtil.equals(sysUser.getPassword(), encryptPwd)) {
            ExceptionHandler.publish(USER_ORIGIN_PWD_ERROR);
        }

        //更新新密码
        EncryptUtil.Encrypt newEncrypt = getEncryptPwd(newPwdText, sysUser.getSalt());
        String newEncryptPwd = newEncrypt.getEncrypt();

        SysUser newUser = new SysUser();
        newUser.setUserId(sysUser.getUserId());
        newUser.setPassword(newEncryptPwd);
        return sysUserExtMapper.updateByPrimaryKeySelective(newUser);

    }

    @Override
    public boolean validatePwd(String password, String salt, String pwdText) throws BaseAppException {
        //验证原密码
        EncryptUtil.Encrypt encrypt = getEncryptPwd(password, salt);
        String encryptPwd = encrypt.getEncrypt();

        if (!EqualsUtil.equals(pwdText, encryptPwd)) {
            ExceptionHandler.publish(USER_PWD_ERROR);
        }
        return true;
    }

}
