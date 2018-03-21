package com.alphabeta.platform.core.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static com.alphabeta.platform.core.common.ErrorCode.ERROR_SYS_EXCEPTION;

/**
 * 抛出异常处理类
 *
 * @author deng.qiming
 * @date 2016年10月27日 上午11:14:03
 */
public class ExceptionHandler {

    private static Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);

    /**
     * 抛出异常
     *
     * @param errorCode
     * @return
     * @throws BaseAppException
     */
    public static BaseAppException publish(Enum<?> errorCode) throws BaseAppException {
        return publish(errorCode, null);
    }


    /**
     * 抛出异常
     *
     * @param errorCode
     * @param msg
     * @return
     * @throws BaseAppException
     */
    public static BaseAppException publish(Enum<?> errorCode, String msg) throws BaseAppException {
        try {
            Method method = errorCode.getClass().getMethod("getCodeString");
            Object errorCodeString = method.invoke(errorCode);
            return publish((String) errorCodeString, msg);
        } catch (Exception e) {
            try {
                Method method = errorCode.getClass().getMethod("getCode");
                Object errorCodeInteger = method.invoke(errorCode);
                return publish("" + errorCodeInteger, msg);
            } catch (Exception ie) {
                return publish(ERROR_SYS_EXCEPTION);
            }
        }
    }

    /**
     * 抛出异常
     *
     * @param errorCode
     * @return
     * @throws BaseAppException
     */
    public static BaseAppException publish(String errorCode) throws BaseAppException {
        return publish(errorCode, null);
    }

    public static BaseAppException publish(String errorCode, String msg) throws BaseAppException {
        return publish(errorCode, msg, null);
    }

    /**
     * 抛出异常
     *
     * @param errorCode String 错误码
     * @param msg       String 消息
     * @param t         Throwable
     * @return BaseAppException
     * @throws BaseAppException
     */
    public static BaseAppException publish(String errorCode, String msg, Throwable t) throws BaseAppException {

        BaseAppException baseAppException;
        if (t instanceof BaseAppException) {
            baseAppException = (BaseAppException) t;
        } else if (t instanceof InvocationTargetException) {
            // 仅仅对此情况进行处理，不能进行深层检查！
            Throwable cause = t.getCause();
            if (cause instanceof BaseAppException) {
                baseAppException = (BaseAppException) cause;
            } else {
                baseAppException = new BaseAppException(errorCode, msg);
            }
        } else {
            baseAppException = new BaseAppException(errorCode, msg);
        }

        throw baseAppException;
    }

}
