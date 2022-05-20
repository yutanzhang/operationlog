package com.d9cloud.operationlog.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Description:
 *
 * @author: litianyi
 * @date: Created on 2022/05/17
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface OperationLog {

    /**
     * 操作类型
     * @return
     */
    String tag() default "";

    /**
     * 操作描述
     * @return
     */
    String desc() default "";

    /**
     * 操作类型+操作描述
     * @return
     */
    String value() default "";

}
