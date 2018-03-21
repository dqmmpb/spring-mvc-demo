package com.alphabeta.platform.base.exception;

import com.alphabeta.platform.base.common.ErrorCode;
import com.alphabeta.platform.core.exception.BaseAppException;

import java.lang.reflect.InvocationTargetException;

/**
 * 抛出异常处理类
 *
 * @author deng.qiming
 * @date 2016年10月27日 上午11:14:03
 */
public class ExceptionHandler extends com.alphabeta.platform.core.exception.ExceptionHandler {

    /**
     * 抛出异常
     *
     * @param errorCode
     * @return
     * @throws BaseAppException
     */
    public static BaseAppException publish(ErrorCode errorCode) throws BaseAppException {
        return publish(errorCode, null);
    }

    public static BaseAppException publish(ErrorCode errorCode, String msg) throws BaseAppException {
        return publish(errorCode.getCodeString(), msg, null);
    }

}
