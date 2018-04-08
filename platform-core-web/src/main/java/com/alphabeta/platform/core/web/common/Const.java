package com.alphabeta.platform.core.web.common;

/**
 * 常量
 *
 * @author deng.qiming
 * @date 2016年11月8日 下午1:57:36
 */
public interface Const {

    /* User-Agent*/
    String HTTP_HEADER_USER_AGENT = "User-Agent";

    /*登录用户标识*/
    String SESSION_LOGIN_USER = "SESSION_LOGIN_USER";
    /*登录用户session*/
    String SESSION_LOGIN_USER_SESSION = "SESSION_LOGIN_USER_SESSION";

    /*JSESSIONID*/
    String JSESSIONID = "JSESSIONID";

    /*redis端key前缀*/
    String COMMON_SESSION_PREFIX = "PORTAL_SESSION";

    /**
     * 路径分隔符
     */
    String SPT = "/";
    /**
     * 索引页
     */
    String INDEX = "index";
    /**
     * 默认模板
     */
    String DEFAULT = "default";
    /**
     * UTF-8编码
     */
    String UTF8 = "UTF-8";
    /**
     * 提示信息
     */
    String MESSAGE = "message";
    /**
     * cookie中的JSESSIONID名称
     */
    String JSESSION_COOKIE = "JSESSIONID";
    /**
     * url中的jsessionid名称
     */
    String JSESSION_URL = "jsessionid";
    /**
     * HTTP POST请求
     */
    String POST = "POST";
    /**
     * HTTP GET请求
     */
    String GET = "GET";

    /**
     * 全文检索索引路径
     */
    String LUCENE_PATH = "/WEB-INF/lucene";
    String X_AUTH_MODE = "client_auth";
    String UPLOAD_MODE = "pic";

    // Map - Key - Request
    String KEY_REQUEST = "_request";
    // Map - Key - Response
    String KEY_RESPONSE = "_response";
    // Map - Key - API
    String KEY_API = "_api";

}
