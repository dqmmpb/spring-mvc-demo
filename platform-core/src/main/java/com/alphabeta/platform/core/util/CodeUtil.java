package com.alphabeta.platform.core.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static com.alphabeta.platform.core.common.ErrorCode.ERROR_SYS_EXCEPTION;

public class CodeUtil {

    public static String getCodeToString(Enum<?> errorCode) {
        try {
            Method method = errorCode.getClass().getMethod("getCode");
            Object errorCodeInteger = method.invoke(errorCode);
            return "" + errorCodeInteger;
        } catch (NoSuchMethodException e) {
            return CodeUtil.getCodeToString(ERROR_SYS_EXCEPTION);
        } catch (IllegalAccessException e) {
            return CodeUtil.getCodeToString(ERROR_SYS_EXCEPTION);
        } catch (InvocationTargetException e) {
            return CodeUtil.getCodeToString(ERROR_SYS_EXCEPTION);
        }
    }

}
