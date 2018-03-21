package com.alphabeta.platform.core.log.desensitizer;

import com.alphabeta.platform.core.log.annotation.Loggable;

import javax.servlet.http.HttpServletRequest;

/**
 * 方法日志记录, 因为aop不能拦截静态方法，所以在这里中转下,会用在ResponseUtils和GlobalExceptionHandler
 *
 * @author deng.qiming
 * @date 2017-01-10 17:46
 */
public class LogProxy {

    @Loggable
    public void data(HttpServletRequest request, Object response) {
    }
}
