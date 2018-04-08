package com.alphabeta.platform.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.alphabeta.platform.base.domain.model.SysUser;
import com.alphabeta.platform.core.domain.BaseParam;
import com.alphabeta.platform.core.exception.BaseAppException;
import com.alphabeta.platform.core.web.util.SessionUtil;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

import static com.alphabeta.platform.core.web.common.Const.SESSION_LOGIN_USER;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class SysLoginControllerTest extends BaseControllerTest {

    @Test
    public void isLogin() throws Exception {
        String url = "/v1/sys/isLogin";

        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders
            .post(url)
            .session((MockHttpSession) getSession())
        );
        MvcResult mvcResult = resultActions.andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        assertNotNull(result);
        JSONObject jsonResult = JSONObject.parseObject(result);
        JSONObject sysUserLogin = jsonResult.getJSONObject("result");
        assertThat(sysUserLogin.getBoolean("login"), instanceOf(Boolean.class));
        assertThat(sysUserLogin.getBooleanValue("login"), anyOf(equalTo(true), equalTo(false)));
        assertThat(sysUserLogin.getBooleanValue("login"), equalTo(true));
    }

    @Test
    public void login() throws Exception {
        String url = "/v1/sys/login";

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("phone", "13819493701");
        params.put("password", "123456");
        BaseParam param = new BaseParam();
        param.setParams(params);
        String requestJson = JSONObject.toJSONString(param);

        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders
            .post(url)
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson)
        );
        MvcResult mvcResult = resultActions.andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        assertNotNull(result);
        JSONObject jsonResult = JSONObject.parseObject(result);
        JSONObject sysUserLogin = jsonResult.getJSONObject("result");
        assertNotNull(sysUserLogin);
        assertEquals("13819493701", sysUserLogin.getString("phone"));
//        SysUser sysUser = SessionUtil.getSessionUser();
//        assertNotNull(sysUser);
//        assertEquals("13819493701", sysUser.getPhone());
    }

    @Test
    public void logout() throws Exception {
        String url = "/v1/sys/logout";
        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders
            .post(url)
            .session((MockHttpSession) getSession())
        );
        MvcResult mvcResult = resultActions.andReturn();
        HttpSession httpSession = mvcResult.getRequest().getSession();
        String result = mvcResult.getResponse().getContentAsString();
        assertNotNull(result);
        JSONObject jsonResult = JSONObject.parseObject(result);
        JSONObject sysUserLogin = jsonResult.getJSONObject("result");
        assertNull(sysUserLogin);
        Object userObj = httpSession.getAttribute(SESSION_LOGIN_USER);
        assertNull(userObj);
        try {
            SysUser sysUser = (SysUser) SessionUtil.getSessionUser();
        } catch (BaseAppException be) {
            assertEquals("用户登录过期", be.getDesc());
        }
    }
}
