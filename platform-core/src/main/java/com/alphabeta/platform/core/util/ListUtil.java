package com.alphabeta.platform.core.util;

import java.util.List;

/**
 * ListUtil
 *
 * @author deng.qiming
 * @date 2017-01-10 17:46
 */
public final class ListUtil {
    /**
     * 判断是否空
     *
     * @param list list
     * @return boolean
     */
    @SuppressWarnings("rawtypes")
    public static boolean isEmpty(List list) {
        return list == null || list.isEmpty();
    }

    /**
     * 判断是否不为空
     *
     * @param list list
     * @return boolean
     */
    @SuppressWarnings("rawtypes")
    public static boolean isNotEmpty(List list) {
        return list != null && !list.isEmpty();
    }
}
