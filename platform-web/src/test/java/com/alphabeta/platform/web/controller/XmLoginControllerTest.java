package com.alphabeta.platform.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.alphabeta.platform.core.common.Const;
import com.alphabeta.platform.core.domain.BaseParam;
import com.alphabeta.platform.core.domain.model.XmManager;
import com.alphabeta.platform.core.exception.BaseAppException;
import com.alphabeta.platform.core.util.SessionUtil;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

import static com.alphabeta.platform.core.common.ErrorCode.USER_SESSION_TIMEOUT;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class XmLoginControllerTest extends BaseControllerTest {

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
        JSONObject xmManagerLogin = jsonResult.getJSONObject("result");
        assertThat(xmManagerLogin.getBoolean("login"), instanceOf(Boolean.class));
        assertThat(xmManagerLogin.getBooleanValue("login"), anyOf(equalTo(true), equalTo(false)));
        assertThat(xmManagerLogin.getBooleanValue("login"), equalTo(true));
    }

    @Test
    public void login() throws Exception {
        String url = "/v1/sys/login";

        Map params = new HashMap();
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
        JSONObject xmManagerLogin = jsonResult.getJSONObject("result");
        assertNotNull(xmManagerLogin);
        assertEquals("13819493701", xmManagerLogin.getString("phone"));
//        XmManager xmManager = SessionUtil.getSessionUser();
//        assertNotNull(xmManager);
//        assertEquals("13819493701", xmManager.getPhone());
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
        JSONObject xmManagerLogin = jsonResult.getJSONObject("result");
        assertNull(xmManagerLogin);
        Object managerObj = httpSession.getAttribute(Const.SESSION_LOGIN_USER);
        assertNull(managerObj);
        try {
            XmManager xmManager = SessionUtil.getSessionUser();
        } catch (BaseAppException be) {
            assertEquals(USER_SESSION_TIMEOUT.getCodeString(), be.getCode());
        }
    }
}
