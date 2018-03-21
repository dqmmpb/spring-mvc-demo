package com.alphabeta.platform.base.annotation;

import java.lang.annotation.*;

/**
 * 参考shiro权限
 *
 * @author deng.qiming
 * @date 2017-01-10 15:03
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequiresPermissions {
    String value() default "";
}
