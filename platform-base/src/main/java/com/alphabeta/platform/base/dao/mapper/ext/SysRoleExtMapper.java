package com.alphabeta.platform.base.dao.mapper.ext;

import com.alphabeta.platform.base.domain.model.SysRole;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SysRoleExtMapper extends Mapper<SysRole> {


    Boolean checkRoleCode(@Param("roleCode") String roleCode, @Param("roleId") Long roleId);

    List<SysRole> queryRolesByUserId(Long userId);
}
