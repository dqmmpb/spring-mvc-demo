package com.alphabeta.platform.base.dao.mapper.ext;

import com.alphabeta.platform.base.domain.model.SysPriv;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SysPrivExtMapper extends Mapper<SysPriv> {

    /**
     * 判断权限编码是否存在
     *
     * @param privCode
     * @param privId
     * @return
     */
    Boolean checkPrivCode(@Param("privCode") String privCode, @Param("privId") Long privId);

    /**
     * 通过roleId查询权限
     *
     * @param roleIds
     * @param privType 权限类型
     * @return
     */
    List<SysPriv> queryPrivsByRoleIds(@Param("roleIds") List<Long> roleIds, @Param("privType") Integer privType);

    /**
     * 通过roleId查询权限
     *
     * @param roleIds
     * @param privType 权限类型
     * @return
     */
    List<SysPriv> queryPrivsByRoleIds(@Param("roleIds") List<Long> roleIds, @Param("privType") Integer privType, RowBounds rowBounds);

//    /**
//     * 通过userId查询权限列表
//     *
//     * @param userId
//     * @return
//     */
//    List<SysPriv> queryPrivsByUserId(Long userId);
}
