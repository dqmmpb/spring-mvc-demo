package com.alphabeta.platform.core.web.util;

import com.alibaba.fastjson.JSON;
import com.alphabeta.platform.core.log.desensitizer.LogProxy;
import com.alphabeta.platform.core.util.ExceptionUtil;
import com.alphabeta.platform.core.web.domain.WrapperResponse;
import com.alphabeta.platform.core.web.spring.SpringContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * HttpServletResponse帮助类
 *
 * @author deng.qiming
 * @date 2017-01-10 17:46
 */
public final class ResponseUtils {
    public static final Logger log = LoggerFactory.getLogger(ResponseUtils.class);

    /**
     * 发送文本。使用UTF-8编码。
     *
     * @param response HttpServletResponse
     * @param text     发送的字符串
     */
    public static void renderText(HttpServletResponse response, String text) {
        render(response, "text/plain;charset=UTF-8", text);
    }

    /**
     * 发送json。使用UTF-8编码。
     *
     * @param response HttpServletResponse
     * @param text     发送的字符串
     */
    public static void renderJson(HttpServletResponse response, String text) {
        render(response, "application/json;charset=UTF-8", text);
    }

    public static void renderJson2(HttpServletRequest request, HttpServletResponse response, Object jsonObj,
                                   boolean supportJsonpCallback) {
        String value = JSON.toJSONString(jsonObj);
        renderResponse(request, response, value, supportJsonpCallback);
    }

    public static void renderJson(HttpServletRequest request, HttpServletResponse response, Object jsonObj,
                                  boolean supportJsonpCallback) {

        try {
            // 获取bean，用于日志切面
            LogProxy logProxy = SpringContextHolder.getBean("logProxy");
            // 方法日志记录, 因为aop不能拦截静态方法，所以在这里中转下
            logProxy.data(request, jsonObj);
        } catch (Exception exception) {
            ExceptionUtil.logError(log, "renderJson - logModel.data", exception);
        }

        String jsonStr = JSON.toJSONString(jsonObj);
        //输出
        renderResponse(request, response, jsonStr, supportJsonpCallback);
    }

    /**
     * 发送xml。使用UTF-8编码。
     *
     * @param response HttpServletResponse
     * @param text     发送的字符串
     */
    public static void renderXml(HttpServletResponse response, String text) {
        render(response, "text/xml;charset=UTF-8", text);
    }

    /**
     * 发送内容。使用UTF-8编码。
     *
     * @param response
     * @param contentType
     * @param text
     */
    public static void render(HttpServletResponse response, String contentType, String text) {
        response.setContentType(contentType);
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        try {
            response.getWriter().write(text);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    private static void renderResponse(HttpServletRequest request, HttpServletResponse response, String value,
                                       boolean supportJsonpCallback) {
        if (!supportJsonpCallback) {
            render(response, "application/json;charset=UTF-8", value);
            return;
        }

        String callback = request.getParameter("jsonpCallback");
        if (callback == null || callback.trim().equals("")) {
            render(response, "application/json;charset=UTF-8", value);
            return;
        }

        value = callback + "(" + value + ")";
        render(response, "application/json;charset=UTF-8", value);
    }

    public static String getResponseContent(HttpServletResponse response) {

        WrapperResponse responseWrapper = new WrapperResponse(response);
        return responseWrapper.getContent();
    }

}
