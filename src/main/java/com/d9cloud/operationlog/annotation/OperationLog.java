package com.d9cloud.operationlog.annotation;

import com.d9cloud.operationlog.entity.enums.OperateTarget;

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

    public String value();

    public OperateTarget target() default OperateTarget.NONE;

    public String field() default "";

}
