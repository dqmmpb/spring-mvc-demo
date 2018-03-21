package com.alphabeta.platform.base.common;

/**
 * web常量
 *
 * @author deng.qiming
 * @date 2017-01-10 17:46
 */
public interface Constants {
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

    /***
     * 与GlobalConstant中定义的值重复，因为codi-base不能引入codi-bus-base
     */
    // Map - Key - Request
    String KEY_REQUEST = "_request";
    // Map - Key - Response
    String KEY_RESPONSE = "_response";
    // Map - Key - API
    String KEY_API = "_api";
}
