package com.alphabeta.platform.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.alphabeta.platform.core.domain.BaseParam;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class SysUserRoleControllerTest extends BaseControllerTest {

    @Test
    public void addManagerRole() throws Exception {
        String url = "/v1/sys/userrole/add";

        Map params = new HashMap();
        params.put("userId", 3);
        params.put("roleId", 2);
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
    public void delManagerRole() throws Exception {
        String url = "/v1/sys/userrole/delete";

        Map params = new HashMap();
        params.put("userId", 3);
        params.put("roleId", 2);
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
}
