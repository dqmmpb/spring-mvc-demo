package com.alphabeta.platform.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.support.spring.annotation.FastJsonFilter;
import com.alibaba.fastjson.support.spring.annotation.FastJsonView;
import com.alphabeta.platform.base.domain.model.SysPriv;
import com.alphabeta.platform.core.annotation.RequiresPermissions;
import com.alphabeta.platform.core.domain.BaseParam;
import com.alphabeta.platform.core.domain.BaseResult;
import com.alphabeta.platform.core.domain.PageParam;
import com.alphabeta.platform.core.exception.BaseAppException;
import com.alphabeta.platform.core.util.ObjectUtil;
import com.alphabeta.platform.web.service.SysPrivService;
import com.alphabeta.platform.web.service.SysRoleService;
import com.github.pagehelper.Page;
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
 * 权限
 *
 * @author deng.qiming
 * @date 2016-12-26 9:39
 */
@RestController
@RequestMapping("/v1/sys/priv")
public class SysPrivController extends BaseController {


    @Resource
    private SysPrivService sysPrivService;


    @Resource
    private SysRoleService sysRoleService;

    /**
     * 新增权限
     *
     * @param param
     * @return
     * @throws BaseAppException
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    @RequiresPermissions("sys:priv:add")
    public BaseResult addPriv(@RequestBody BaseParam param) throws BaseAppException {

        logger.debug("add priv begin");

        Map params = parseParams(param);
        String paramsJSONString = JSONObject.toJSONString(params);
        SysPriv sysPriv = JSONObject.parseObject(paramsJSONString, SysPriv.class);

        BaseResult result = new BaseResult();
        sysPrivService.addPriv(sysPriv);

        return result;
    }

    /**
     * 编辑权限
     *
     * @param param
     * @return
     * @throws BaseAppException
     */
    @RequestMapping(value = "edit", method = RequestMethod.POST)
    @RequiresPermissions("sys:priv:edit")
    public BaseResult editPriv(@RequestBody BaseParam param) throws BaseAppException {

        logger.debug("edit priv begin");

        Map params = parseParams(param);
        String paramsJSONString = JSONObject.toJSONString(params);
        SysPriv sysPriv = JSONObject.parseObject(paramsJSONString, SysPriv.class);

        BaseResult result = new BaseResult();
        sysPrivService.updatePriv(sysPriv);

        return result;
    }

    /**
     * 删除权限
     *
     * @param param
     * @return
     * @throws BaseAppException
     */
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @RequiresPermissions("sys:priv:delete")
    public BaseResult delPriv(@RequestBody BaseParam param) throws BaseAppException {

        logger.debug("delete priv begin");

        Map params = parseParams(param);
        Long privId = ((Number) params.get("privId")).longValue();

        BaseResult result = new BaseResult();
        sysPrivService.delPriv(privId);

        return result;
    }

    /**
     * 查询权限
     *
     * @param param
     * @return
     * @throws BaseAppException
     */
    @FastJsonView(
        exclude = {@FastJsonFilter(clazz = SysPriv.class, props = {"createTime", "updateTime"})})
    @RequestMapping(value = "get", method = RequestMethod.POST)
    @RequiresPermissions("sys:priv:view")
    public BaseResult getPriv(@RequestBody BaseParam param) throws BaseAppException {
        Map params = parseParams(param);
        Long privId = ((Number) params.get("privId")).longValue();

        BaseResult result = new BaseResult();
        result.setResult(sysPrivService.getPriv(privId));

        return result;
    }


    /**
     * 获取指定Role下的权限
     *
     * @param param
     * @return
     * @throws BaseAppException
     */
    @FastJsonView(
        include = {
            @FastJsonFilter(clazz = PageInfo.class, props = {"pageNum", "total", "pageSize", "pages", "list"}),
        },
        exclude = {@FastJsonFilter(clazz = SysPriv.class, props = {"createTime", "updateTime"})})
    @RequestMapping(value = "rolepriv", method = RequestMethod.POST)
    @RequiresPermissions("sys:priv:view")
    public BaseResult getRolePrivs(@RequestBody BaseParam param) throws BaseAppException {
        Map params = parseParams(param);
        PageParam pageParam = parsePage(params);

        BaseResult result = new BaseResult();
        List<SysPriv> sysPrivList = new Page<SysPriv>(pageParam.getPageNum(), pageParam.getPageSize());
        try {
            Long roleId = ((Number) params.get("roleId")).longValue();
            sysPrivList = sysPrivService.queryPrivsByRoleId(roleId, pageParam.getPageNum(), pageParam.getPageSize());
        } catch (Exception e) {
            logger.info("privs list get error, will return empty list");
        }
        PageInfo page = new PageInfo(sysPrivList);
        result.setResult(page);
        return result;

    }

    /**
     * 获取指定Role下的权限
     *
     * @param param
     * @return
     * @throws BaseAppException
     */
    @FastJsonView(
        exclude = {@FastJsonFilter(clazz = SysPriv.class, props = {"createTime", "updateTime"})})
    @RequestMapping(value = "roleprivAll", method = RequestMethod.POST)
    @RequiresPermissions("sys:priv:view")
    public BaseResult getRolePrivsAll(@RequestBody BaseParam param) throws BaseAppException {
        Map params = parseParams(param);

        BaseResult result = new BaseResult();
        List<SysPriv> sysPrivList = new ArrayList<SysPriv>();
        try {
            Long roleId = ((Number) params.get("roleId")).longValue();
            sysPrivList = sysPrivService.queryPrivsByRoleId(roleId);
        } catch (Exception e) {
            logger.info("privs list get error, will return empty list");
        }
        result.setResult(sysPrivList);
        return result;

    }

    /**
     * 查询用户所有权限
     *
     * @param param
     * @return
     * @throws BaseAppException
     */
    @FastJsonView(
        exclude = {@FastJsonFilter(clazz = SysPriv.class, props = {"createTime", "updateTime"})})
    @RequestMapping(value = "userpriv", method = RequestMethod.POST)
    @RequiresPermissions("sys:priv:view")
    public BaseResult getUserPrivsByUserId(@RequestBody BaseParam param) throws BaseAppException {
        Map params = parseParams(param);
        Long userId = ((Number) params.get("userId")).longValue();

        BaseResult result = new BaseResult();
        List<SysPriv> sysPrivList = sysPrivService.queryPrivsByUserId(userId);
        result.setResult(sysPrivList);

        return result;
    }

    /**
     * 查询所有菜单【扁平列表】
     *
     * @param param
     * @return
     * @throws BaseAppException
     */
    @FastJsonView(
        include = {
            @FastJsonFilter(clazz = PageInfo.class, props = {"pageNum", "total", "pageSize", "pages", "list"}),
        },
        exclude = {@FastJsonFilter(clazz = SysPriv.class, props = {"createTime", "updateTime"})})
    @RequestMapping(value = "privs", method = RequestMethod.POST)
    @RequiresPermissions
    public BaseResult getPrivs(@RequestBody BaseParam param) throws BaseAppException {
        Map params = parseParams(param);
        PageParam pageParam = parsePage(params);

        BaseResult result = new BaseResult();
        List<SysPriv> sysPrivList = sysPrivService.queryPrivs(pageParam.getPageNum(), pageParam.getPageSize());
        PageInfo page = new PageInfo(sysPrivList);
        result.setResult(page);

        return result;
    }

    /**
     * 查询所有权限，【扁平列表】
     *
     * @param param
     * @return
     * @throws BaseAppException
     */
    @FastJsonView(
        exclude = {@FastJsonFilter(clazz = SysPriv.class, props = {"createTime", "updateTime"})})
    @RequestMapping(value = "privsAll", method = RequestMethod.POST)
    @RequiresPermissions("sys:priv:view")
    public BaseResult getPrivsAll(@RequestBody BaseParam param) throws BaseAppException {
        Map params = parseParams(param);

        List<SysPriv> sysPrivList = new ArrayList<SysPriv>();

        if (ObjectUtil.isNotNull(params.get("type"))) {
            try {
                Integer privType = ((Number) params.get("type")).intValue();
                sysPrivList = sysPrivService.queryPrivsByType(privType);
            } catch (Exception e) {
                logger.info("privs list get error, will return empty list");
            }
        } else {
            sysPrivList = sysPrivService.queryPrivs();
        }

        BaseResult result = new BaseResult();
        result.setResult(sysPrivList);

        return result;
    }

//    /**
//     * 获取所有权限【树形结构】
//     *
//     * @param request
//     * @return
//     * @throws BaseAppException
//     */
//    @RequestMapping("/privsTree")
//    public MenuResult getAllMenu(HttpServletRequest request) throws BaseAppException {
//        MenuResult result = new MenuResult();
//
//        List<SysPriv> sysPrivs = sysPrivService.queryPrivs(null, null);
//        if (ListUtil.isEmpty(sysPrivs)) {
//            logger.warn("there is no sysPrivs in system db.");
//            return result;
//        }
//
//        //组装成菜单
//        List<MenuModel> menus = makePriv(sysPrivs, false);
//        result.setResult(menus);
//
//        return result;
//    }

//    /**
//     * 获取根目录权限
//     *
//     * @return
//     */
//    @RequestMapping(value = "/rootPrivs", method = RequestMethod.GET)
//    public BaseResult getRootPrivs() throws BaseAppException {
//        BaseResult result = new BaseResult();
//        result.setResult(sysPrivService.queryRootPrivs());
//        return result;
//    }

}
