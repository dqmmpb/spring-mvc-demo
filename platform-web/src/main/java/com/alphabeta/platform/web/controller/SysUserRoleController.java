package com.alphabeta.platform.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.alphabeta.platform.core.annotation.RequiresPermissions;
import com.alphabeta.platform.core.domain.BaseParam;
import com.alphabeta.platform.core.domain.BaseResult;
import com.alphabeta.platform.base.domain.model.SysUserRole;
import com.alphabeta.platform.core.exception.BaseAppException;
import com.alphabeta.platform.web.service.SysUserRoleService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 用户角色
 *
 * @author deng.qiming
 * @date 2017-01-05 13:10
 */
@RestController
@RequestMapping("/v1/sys/userrole")
public class SysUserRoleController extends BaseController {

    @Resource
    private SysUserRoleService sysUserRoleService;

    /**
     * 绑定用户和角色
     *
     * @param param
     * @return
     * @throws BaseAppException
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    @RequiresPermissions("sys:user:allocate")
    public BaseResult addUserRole(@RequestBody BaseParam param) throws BaseAppException {

        Map params = parseParams(param);
        String paramsJSONString = JSONObject.toJSONString(params);
        SysUserRole sysUserRole = JSONObject.parseObject(paramsJSONString, SysUserRole.class);

        sysUserRoleService.addUserRole(sysUserRole.getUserId(), sysUserRole.getRoleId());

        BaseResult result = new BaseResult();
        return result;
    }

    /**
     * 删除用户角色权限
     *
     * @param param
     * @return
     * @throws BaseAppException
     */
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @RequiresPermissions("sys:user:allocate")
    public BaseResult delUserRole(@RequestBody BaseParam param) throws BaseAppException {

        Map params = parseParams(param);
        String paramsJSONString = JSONObject.toJSONString(params);
        SysUserRole sysUserRole = JSONObject.parseObject(paramsJSONString, SysUserRole.class);

        sysUserRoleService.delUserRole(sysUserRole.getUserId(), sysUserRole.getRoleId());

        BaseResult result = new BaseResult();
        return result;
    }

    /**
     * 绑定用户和角色
     *
     * @param param
     * @return
     * @throws BaseAppException
     */
    @RequestMapping(value = "allocate", method = RequestMethod.POST)
    @RequiresPermissions("sys:role:allocate")
    public BaseResult allocateUserRole(@RequestBody BaseParam param) throws BaseAppException {

        Map params = parseParams(param);
        Long userId = ((Number) params.get("userId")).longValue();
        String paramsJSONString = JSONObject.toJSONString(params.get("roleIds"));
        List<Long> roleIds = JSONObject.parseArray(paramsJSONString, Long.class);

        sysUserRoleService.allocateUserRole(userId, roleIds);

        BaseResult result = new BaseResult();
        return result;
    }

}
