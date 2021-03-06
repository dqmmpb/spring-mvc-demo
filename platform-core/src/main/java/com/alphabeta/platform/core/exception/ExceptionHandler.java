package com.alphabeta.platform.core.exception;

import com.alphabeta.platform.core.util.CodeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;

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
        String errorCodeString = CodeUtil.getCodeToString(errorCode);
        return publish(errorCodeString, msg);
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

    /**
     * 抛出异常
     *
     * @param errorCode
     * @param msg
     * @return
     * @throws BaseAppException
     */
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
