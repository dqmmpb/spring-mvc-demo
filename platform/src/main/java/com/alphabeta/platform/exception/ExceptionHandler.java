package com.alphabeta.platform.exception;

import com.alphabeta.platform.common.ErrorCode;

import java.lang.reflect.InvocationTargetException;

/**
 * 抛出异常处理类
 *
 * @author deng.qiming
 * @date 2016年10月27日 上午11:14:03
 */
public final class ExceptionHandler {

    // private static Logger logger =
    // LoggerFactory.getLogger(ExceptionHandler.class);

    private ExceptionHandler() {
    }

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
