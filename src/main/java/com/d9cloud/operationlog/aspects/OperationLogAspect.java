package com.d9cloud.operationlog.aspects;

import com.d9cloud.operationlog.annotation.OperationLog;
import com.d9cloud.operationlog.entity.po.OperationLogPO;
import com.d9cloud.operationlog.utils.OperationUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Date;

/**
 * Description:
 *
 * @author: litianyi
 * @date: Created on 2022/05/17
 */
@Aspect
@Component
public class OperationLogAspect {

    @Around("execution(* com.d9cloud.*.*.controller..*.*(..))")
    public Object logOperation(ProceedingJoinPoint pjp) throws Throwable {
        Object result = pjp.proceed();
        // 获取类上的注解
        Class<?> clazz = pjp.getTarget().getClass();
        OperationLog classOperation = clazz.getAnnotation(OperationLog.class);
        if (classOperation == null) {
            return result;
        }
        String classOperationName = classOperation.name();

        // 获取方法上的注解
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        OperationLog methodOperation = method.getAnnotation(OperationLog.class);
        if (methodOperation == null) {
            return result;
        }
        String methodOperationName = methodOperation.name();

        OperationLogPO operationLogPO = new OperationLogPO();
        operationLogPO.setOperateType(classOperationName);
        operationLogPO.setOperateDesc(methodOperationName);
        operationLogPO.setOperateDate(new Date());
        operationLogPO.setUserId(OperationUtils.getUserId());
        // @todo 发送到mq

        return result;
    }

}
