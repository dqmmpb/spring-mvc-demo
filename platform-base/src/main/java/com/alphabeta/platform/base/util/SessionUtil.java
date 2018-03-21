package com.alphabeta.platform.base.util;

import com.alphabeta.platform.base.common.Const;
import com.alphabeta.platform.core.exception.BaseAppException;
import com.alphabeta.platform.core.exception.ExceptionHandler;
import com.alphabeta.platform.core.util.ObjectUtil;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

import static com.alphabeta.platform.base.common.ErrorCode.USER_SESSION_TIMEOUT;

/**
 * Session Util
 *
 * @author deng.qiming
 * @date 2017-01-10 17:46
 */
public class SessionUtil {

    /**
     * 获取当前登录用户
     *
     * @return
     */
    public static Object getSessionUser() throws BaseAppException {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession();
        Object sessionUserObj = session.getAttribute(Const.SESSION_LOGIN_USER);

        if (ObjectUtil.isNull(sessionUserObj)) {
            ExceptionHandler.publish(USER_SESSION_TIMEOUT);
        }
        return sessionUserObj;
    }
}
