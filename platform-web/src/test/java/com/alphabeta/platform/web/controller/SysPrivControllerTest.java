package com.alphabeta.platform.web.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alphabeta.platform.base.common.PrivType;
import com.alphabeta.platform.base.domain.model.SysPriv;
import com.alphabeta.platform.core.domain.BaseParam;
import com.alphabeta.platform.web.result.model.MenuModel;
import com.github.pagehelper.PageInfo;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class SysPrivControllerTest extends BaseControllerTest {

    @Test
    public void addPriv() throws Exception {
        String url = "/v1/sys/priv/add";

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("privName", "新建权限测试管理");
        params.put("privCode", "sys:test:dir:manage");
        params.put("type", PrivType.DIR.getValue());
        params.put("description", "这是一个测试");
        params.put("url", "/test");
        params.put("path", "/test");
        BaseParam param = new BaseParam();
        param.setParams(params);
        String requestJson = JSONObject.toJSONString(param);

        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders
            .post(url)
            .session((MockHttpSession) getSuperAdminSession())
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson)
        );
        MvcResult mvcResult = resultActions.andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        assertNotNull(result);
        JSONObject jsonResult = JSONObject.parseObject(result);
        assertEquals(true, jsonResult.getBoolean("success"));
    }

    @Test
    public void editPriv() throws Exception {
        String url = "/v1/sys/priv/edit";

        // privCode和createTime在编辑的时候，不允许修改
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("privId", 32);
        params.put("description", "这是一个已经编辑过的测试");

        BaseParam param = new BaseParam();
        param.setParams(params);
        String requestJson = JSONObject.toJSONString(param);

        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders
            .post(url)
            .session((MockHttpSession) getSuperAdminSession())
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson)
        );
        MvcResult mvcResult = resultActions.andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        assertNotNull(result);
        JSONObject jsonResult = JSONObject.parseObject(result);
        assertEquals(true, jsonResult.getBoolean("success"));
    }

    @Test
    public void delPriv() throws Exception {
        String url = "/v1/sys/priv/delete";

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("privId", 32);
        BaseParam param = new BaseParam();
        param.setParams(params);
        String requestJson = JSONObject.toJSONString(param);

        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders
            .post(url)
            .session((MockHttpSession) getSuperAdminSession())
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson)
        );
        MvcResult mvcResult = resultActions.andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        assertNotNull(result);
        JSONObject jsonResult = JSONObject.parseObject(result);
        assertEquals(true, jsonResult.getBoolean("success"));
    }

    @Test
    public void getPriv() throws Exception {
        String url = "/v1/sys/priv/get";

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("privId", 29);
        BaseParam param = new BaseParam();
        param.setParams(params);
        String requestJson = JSONObject.toJSONString(param);

        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders
            .post(url)
            .session((MockHttpSession) getSession())
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson)
        );
        MvcResult mvcResult = resultActions.andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        assertNotNull(result);
        JSONObject jsonResult = JSONObject.parseObject(result);
        JSONObject sysPrivJsonObj = jsonResult.getJSONObject("result");
        assertNotNull(sysPrivJsonObj);
        SysPriv sysPriv = sysPrivJsonObj.toJavaObject(SysPriv.class);
        assertEquals("重置密码", sysPriv.getPrivName());
        assertEquals("sys:user:resetPwd", sysPriv.getPrivCode());
    }

    @Test
    public void getRolePrivs() throws Exception {
        String url = "/v1/sys/priv/rolepriv";

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("roleId", 1);
        params.put("pageNum", 1);
        params.put("pageSize", 10);
        BaseParam param = new BaseParam();
        param.setParams(params);
        String requestJson = JSONObject.toJSONString(param);

        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders
            .post(url)
            .session((MockHttpSession) getSession())
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson)
        );
        MvcResult mvcResult = resultActions.andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        assertNotNull(result);
        JSONObject jsonResult = JSONObject.parseObject(result);
        JSONObject rolePrivsJsonObj = jsonResult.getJSONObject("result");
        PageInfo page = rolePrivsJsonObj.toJavaObject(PageInfo.class);
        assertEquals(31, page.getTotal());
        assertEquals(10, page.getList().size());

        // 第2页
        params.put("pageNum", 2);
        param = new BaseParam();
        param.setParams(params);
        requestJson = JSONObject.toJSONString(param);
        resultActions = this.mockMvc.perform(MockMvcRequestBuilders
            .post(url)
            .session((MockHttpSession) getSession())
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson)
        );
        mvcResult = resultActions.andReturn();
        result = mvcResult.getResponse().getContentAsString();
        assertNotNull(result);
        jsonResult = JSONObject.parseObject(result);
        rolePrivsJsonObj = jsonResult.getJSONObject("result");
        page = rolePrivsJsonObj.toJavaObject(PageInfo.class);
        assertEquals(31, page.getTotal());
        assertEquals(10, page.getList().size());

        // 第3页
        params.put("pageNum", 3);
        param = new BaseParam();
        param.setParams(params);
        requestJson = JSONObject.toJSONString(param);
        resultActions = this.mockMvc.perform(MockMvcRequestBuilders
            .post(url)
            .session((MockHttpSession) getSession())
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson)
        );
        mvcResult = resultActions.andReturn();
        result = mvcResult.getResponse().getContentAsString();
        assertNotNull(result);
        jsonResult = JSONObject.parseObject(result);
        rolePrivsJsonObj = jsonResult.getJSONObject("result");
        page = rolePrivsJsonObj.toJavaObject(PageInfo.class);
        assertEquals(31, page.getTotal());
        assertEquals(10, page.getList().size());

        // 第4页
        params.put("pageNum", 4);
        param = new BaseParam();
        param.setParams(params);
        requestJson = JSONObject.toJSONString(param);
        resultActions = this.mockMvc.perform(MockMvcRequestBuilders
            .post(url)
            .session((MockHttpSession) getSession())
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson)
        );
        mvcResult = resultActions.andReturn();
        result = mvcResult.getResponse().getContentAsString();
        assertNotNull(result);
        jsonResult = JSONObject.parseObject(result);
        rolePrivsJsonObj = jsonResult.getJSONObject("result");
        page = rolePrivsJsonObj.toJavaObject(PageInfo.class);
        assertEquals(31, page.getTotal());
        assertEquals(1, page.getList().size());
    }

    @Test
    public void getUserPrivsByManagerId() throws Exception {
        String url = "/v1/sys/priv/userpriv";

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", 2);
        BaseParam param = new BaseParam();
        param.setParams(params);
        String requestJson = JSONObject.toJSONString(param);

        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders
            .post(url)
            .session((MockHttpSession) getSession())
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson)
        );
        MvcResult mvcResult = resultActions.andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        assertNotNull(result);
        JSONObject jsonResult = JSONObject.parseObject(result);
        JSONArray sysPrivJsonArray = jsonResult.getJSONArray("result");
        assertNotNull(sysPrivJsonArray);
        List<SysPriv> sysPrivList = sysPrivJsonArray.toJavaList(SysPriv.class);
        assertEquals(2, sysPrivList.size());
    }

    @Test
    public void getMenu() throws Exception {
        String url = "/v1/sys/priv/menu";
        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders
            .post(url)
            .session((MockHttpSession) getSession())
        );
        MvcResult mvcResult = resultActions.andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        assertNotNull(result);
        JSONObject jsonResult = JSONObject.parseObject(result);
        JSONArray sysMenuModelJsonArray = jsonResult.getJSONArray("result");
        assertNotNull(sysMenuModelJsonArray);
        List<MenuModel> menuModelList = sysMenuModelJsonArray.toJavaList(MenuModel.class);
        assertEquals(1, menuModelList.size());
    }

    @Test
    public void getPrivs() throws Exception {
        String url = "/v1/sys/priv/privs";

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("pageNum", 1);
        params.put("pageSize", 10);
        BaseParam param = new BaseParam();
        param.setParams(params);
        String requestJson = JSONObject.toJSONString(param);

        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders
            .post(url)
            .session((MockHttpSession) getSession())
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson)
        );
        MvcResult mvcResult = resultActions.andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        assertNotNull(result);
        JSONObject jsonResult = JSONObject.parseObject(result);
        JSONObject privsJsonObj = jsonResult.getJSONObject("result");
        PageInfo page = privsJsonObj.toJavaObject(PageInfo.class);
        assertEquals(31, page.getTotal());
        assertEquals(10, page.getList().size());

        // 第2页
        params.put("pageNum", 2);
        param = new BaseParam();
        param.setParams(params);
        requestJson = JSONObject.toJSONString(param);
        resultActions = this.mockMvc.perform(MockMvcRequestBuilders
            .post(url)
            .session((MockHttpSession) getSession())
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson)
        );
        mvcResult = resultActions.andReturn();
        result = mvcResult.getResponse().getContentAsString();
        assertNotNull(result);
        jsonResult = JSONObject.parseObject(result);
        privsJsonObj = jsonResult.getJSONObject("result");
        page = privsJsonObj.toJavaObject(PageInfo.class);
        assertEquals(31, page.getTotal());
        assertEquals(10, page.getList().size());

        // 第3页
        params.put("pageNum", 3);
        param = new BaseParam();
        param.setParams(params);
        requestJson = JSONObject.toJSONString(param);
        resultActions = this.mockMvc.perform(MockMvcRequestBuilders
            .post(url)
            .session((MockHttpSession) getSession())
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson)
        );
        mvcResult = resultActions.andReturn();
        result = mvcResult.getResponse().getContentAsString();
        assertNotNull(result);
        jsonResult = JSONObject.parseObject(result);
        privsJsonObj = jsonResult.getJSONObject("result");
        page = privsJsonObj.toJavaObject(PageInfo.class);
        assertEquals(31, page.getTotal());
        assertEquals(10, page.getList().size());

        // 第4页
        params.put("pageNum", 4);
        param = new BaseParam();
        param.setParams(params);
        requestJson = JSONObject.toJSONString(param);
        resultActions = this.mockMvc.perform(MockMvcRequestBuilders
            .post(url)
            .session((MockHttpSession) getSession())
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson)
        );
        mvcResult = resultActions.andReturn();
        result = mvcResult.getResponse().getContentAsString();
        assertNotNull(result);
        jsonResult = JSONObject.parseObject(result);
        privsJsonObj = jsonResult.getJSONObject("result");
        page = privsJsonObj.toJavaObject(PageInfo.class);
        assertEquals(31, page.getTotal());
        assertEquals(1, page.getList().size());
    }
}
