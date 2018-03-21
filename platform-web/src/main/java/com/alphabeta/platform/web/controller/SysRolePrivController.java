package com.alphabeta.platform.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.alphabeta.platform.core.annotation.RequiresPermissions;
import com.alphabeta.platform.core.domain.BaseParam;
import com.alphabeta.platform.core.domain.BaseResult;
import com.alphabeta.platform.base.domain.model.SysRolePriv;
import com.alphabeta.platform.core.exception.BaseAppException;
import com.alphabeta.platform.web.service.SysRolePrivService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 角色权限
 *
 * @author deng.qiming
 * @date 2017-01-05 13:10
 */
@RestController
@RequestMapping("/v1/sys/rolepriv")
public class SysRolePrivController extends BaseController {

    @Resource
    private SysRolePrivService sysRolePrivService;

    /**
     * 绑定角色和权限
     *
     * @param param
     * @return
     * @throws BaseAppException
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    @RequiresPermissions("sys:role:allocate")
    public BaseResult addRolePriv(@RequestBody BaseParam param) throws BaseAppException {

        Map params = parseParams(param);
        String paramsJSONString = JSONObject.toJSONString(params);
        SysRolePriv sysRolePriv = JSONObject.parseObject(paramsJSONString, SysRolePriv.class);

        sysRolePrivService.addRolePriv(sysRolePriv.getRoleId(), sysRolePriv.getPrivId());
        BaseResult result = new BaseResult();
        return result;
    }

    /**
     * 删除角色和权限
     *
     * @param param
     * @return
     * @throws BaseAppException
     */
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @RequiresPermissions("sys:role:allocate")
    public BaseResult delRolePriv(@RequestBody BaseParam param) throws BaseAppException {

        Map params = parseParams(param);
        String paramsJSONString = JSONObject.toJSONString(params);
        SysRolePriv sysRolePriv = JSONObject.parseObject(paramsJSONString, SysRolePriv.class);

        sysRolePrivService.delRolePriv(sysRolePriv.getRoleId(), sysRolePriv.getPrivId());

        BaseResult result = new BaseResult();
        return result;
    }

    /**
     * 绑定角色和权限
     *
     * @param param
     * @return
     * @throws BaseAppException
     */
    @RequestMapping(value = "allocate", method = RequestMethod.POST)
    @RequiresPermissions("sys:role:allocate")
    public BaseResult allocateRolePriv(@RequestBody BaseParam param) throws BaseAppException {

        Map params = parseParams(param);
        Long roleId = ((Number) params.get("roleId")).longValue();
        String paramsJSONString = JSONObject.toJSONString(params.get("privIds"));
        List<Long> privIds = JSONObject.parseArray(paramsJSONString, Long.class);

        sysRolePrivService.allocateRolePriv(roleId, privIds);

        BaseResult result = new BaseResult();
        return result;
    }

}
