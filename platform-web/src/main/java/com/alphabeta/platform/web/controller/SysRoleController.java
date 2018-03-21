package com.alphabeta.platform.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.support.spring.annotation.FastJsonFilter;
import com.alibaba.fastjson.support.spring.annotation.FastJsonView;
import com.alphabeta.platform.base.domain.model.SysRole;
import com.alphabeta.platform.core.annotation.RequiresPermissions;
import com.alphabeta.platform.core.domain.BaseParam;
import com.alphabeta.platform.core.domain.BaseResult;
import com.alphabeta.platform.core.domain.PageParam;
import com.alphabeta.platform.core.exception.BaseAppException;
import com.alphabeta.platform.web.service.SysRoleService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 角色
 *
 * @authordeng.qiming
 * @date 2016-12-26 9:39
 */
@RestController
@RequestMapping("/v1/sys/role")
public class SysRoleController extends BaseController {

    @Resource
    private SysRoleService sysRoleService;


    /**
     * 新增Role
     *
     * @param param
     * @return
     * @throws BaseAppException
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    @RequiresPermissions("sys:role:add")
    public BaseResult addRole(@RequestBody BaseParam param) throws BaseAppException {

        logger.debug("add role begin");

        Map params = parseParams(param);
        String paramsJSONString = JSONObject.toJSONString(params);
        SysRole sysRole = JSONObject.parseObject(paramsJSONString, SysRole.class);

        BaseResult result = new BaseResult();
        sysRoleService.addRole(sysRole.getRoleCode(), sysRole.getRoleName(), sysRole.getDescription());

        return result;
    }

    /**
     * 更新Role
     *
     * @param param
     * @return
     * @throws BaseAppException
     */
    @RequestMapping(value = "edit", method = RequestMethod.POST)
    @RequiresPermissions("sys:role:edit")
    public BaseResult editRole(@RequestBody BaseParam param) throws BaseAppException {

        logger.debug("edit role begin");

        Map params = parseParams(param);
        String paramsJSONString = JSONObject.toJSONString(params);
        SysRole sysRole = JSONObject.parseObject(paramsJSONString, SysRole.class);

        BaseResult result = new BaseResult();
        sysRoleService.updateRole(sysRole);

        return result;
    }

    /**
     * 删除角色
     *
     * @param param
     * @return
     * @throws BaseAppException
     */
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @RequiresPermissions("sys:role:delete")
    public BaseResult delRole(@RequestBody BaseParam param) throws BaseAppException {

        logger.debug("delete role begin");

        Map params = parseParams(param);
        Long roleId = ((Number) params.get("roleId")).longValue();

        BaseResult result = new BaseResult();
        sysRoleService.delRole(roleId);

        return result;
    }

    /**
     * 查询Role
     *
     * @param param
     * @return
     * @throws BaseAppException
     */
    @FastJsonView(
        exclude = {@FastJsonFilter(clazz = SysRole.class, props = {"createTime", "updateTime"})})
    @RequestMapping(value = "get", method = RequestMethod.POST)
    @RequiresPermissions("sys:role:view")
    public BaseResult getRole(@RequestBody BaseParam param) throws BaseAppException {

        Map params = parseParams(param);
        Long roleId = ((Number) params.get("roleId")).longValue();

        BaseResult result = new BaseResult();
        SysRole role = sysRoleService.getRole(roleId);
        result.setResult(role);

        return result;
    }


    /**
     * 通过userId查询用户角色列表
     *
     * @param param
     * @return
     * @throws BaseAppException
     */
    @FastJsonView(
        exclude = {@FastJsonFilter(clazz = SysRole.class, props = {"createTime", "updateTime"})})
    @RequestMapping(value = "userrole", method = RequestMethod.POST)
    @RequiresPermissions("sys:role:view")
    public BaseResult getRolesByUserId(@RequestBody BaseParam param) throws BaseAppException {
        Map params = parseParams(param);

        List<SysRole> sysRoleList = new ArrayList<SysRole>();
        try {
            Long userId = ((Number) params.get("userId")).longValue();
            sysRoleList = sysRoleService.queryRolesByUserId(userId);
        } catch (Exception e) {
            logger.info("roles list get error, will return empty list");
        }

        BaseResult result = new BaseResult();
        result.setResult(sysRoleList);
        return result;
    }

    /**
     * 查询角色列表,扁平列表
     *
     * @return
     * @throws BaseAppException
     */
    @FastJsonView(
        exclude = {@FastJsonFilter(clazz = SysRole.class, props = {"createTime", "updateTime"})})
    @RequestMapping(value = "rolesAll", method = RequestMethod.POST)
    @RequiresPermissions("sys:role:view")
    public BaseResult getRolesAll(@RequestBody BaseParam param) throws BaseAppException {
        Map params = parseParams(param);

        BaseResult result = new BaseResult();
        List<SysRole> sysRoleList = sysRoleService.queryRoles();
        result.setResult(sysRoleList);

        return result;
    }

    /**
     * 查询角色列表
     *
     * @return
     * @throws BaseAppException
     */
    @FastJsonView(
        include = {
            @FastJsonFilter(clazz = PageInfo.class, props = {"pageNum", "total", "pageSize", "pages", "list"}),
        },
        exclude = {@FastJsonFilter(clazz = SysRole.class, props = {"createTime", "updateTime"})}
    )
    @RequestMapping(value = "roles", method = RequestMethod.POST)
    @RequiresPermissions("sys:role:view")
    public BaseResult getRoles(@RequestBody BaseParam param) throws BaseAppException {
        Map params = parseParams(param);
        PageParam pageParam = parsePage(params);

        BaseResult result = new BaseResult();
        List<SysRole> sysRoleList = sysRoleService.queryRoles(pageParam.getPageNum(), pageParam.getPageSize());
        PageInfo page = new PageInfo(sysRoleList);
        result.setResult(page);

        return result;
    }

}
