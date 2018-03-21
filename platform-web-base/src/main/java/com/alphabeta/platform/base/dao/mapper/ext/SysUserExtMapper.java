package com.alphabeta.platform.base.dao.mapper.ext;

import com.alphabeta.platform.base.domain.model.SysUser;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SysUserExtMapper extends Mapper<SysUser> {

    /**
     * 检查用户是否存在
     *
     * @param phone
     * @param userId
     * @return
     */
    Boolean checkPhone(@Param("phone") String phone, @Param("userId") Long userId);

    /**
     * 通过roleId查询用户
     *
     * @param roleId
     * @return
     */
    List<SysUser> getUsersByRoleId(Long roleId);

}
