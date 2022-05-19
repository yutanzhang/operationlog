package com.d9cloud.operationlog.aspects;

import com.d9cloud.operationlog.annotation.OperationLog;
import com.d9cloud.operationlog.entity.constants.OperationLogConstant;
import com.d9cloud.operationlog.entity.po.OperationLogPO;
import com.d9cloud.operationlog.utils.OperationLogUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Date;

/**
 * Description:
 *
 * @author: litianyi
 * @date: Created on 2022/05/17
 */
@Aspect
public class OperationLogAspect {

    private Logger LOG = LoggerFactory.getLogger(OperationLogAspect.class);

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    @Around("execution(* com.d9cloud..controller..*.*(..))")
    public Object logOperation(ProceedingJoinPoint pjp) throws Throwable {
        Object result;
        result = pjp.proceed();
        try {
            OperationLogPO operationLogPO = new OperationLogPO();
            operationLogPO.setOperateDate(new Date());
            operationLogPO.setUserId(OperationLogUtils.getUserId());

            // 获取操作菜单
            Class<?> clazz = pjp.getTarget().getClass();
            OperationLog classOperation = clazz.getAnnotation(OperationLog.class);
            if (classOperation == null) {
                return result;
            }
            String operationTag = classOperation.value();
            operationLogPO.setOperateTag(operationTag);

            // 获取操作描述
            MethodSignature signature = (MethodSignature) pjp.getSignature();
            Method method = signature.getMethod();
            OperationLog methodOperation = method.getAnnotation(OperationLog.class);
            if (methodOperation == null) {
                return result;
            }
            String operationDesc = methodOperation.value();
            operationLogPO.setOperateDesc(operationDesc);

            operationLogPO.setTarget(methodOperation.target());
            Object targetId = getTargetValue(methodOperation.field(), method.getParameters(), pjp.getArgs());
            operationLogPO.setTargetValue(targetId);
            LOG.info("采集到操作日志：{}", operationLogPO);

            // 发送到redis队列中
            ListOperations<String, Object> listOperations = redisTemplate.opsForList();
            listOperations.leftPush(OperationLogConstant.LOG_QUEUE, operationLogPO);
        } catch (Exception e) {
            LOG.error("采集操作日志异常:", e);
        }
        return result;
    }

    private Object getTargetValue(String paramFullName, Parameter[] parameters, Object[] params) throws Exception {
        if ("".equals(paramFullName)) {
            return null;
        }
        String[] paramNames = paramFullName.split("\\.");
        if (paramNames.length == 1) {
            for (int i = 0; i < parameters.length; i++) {
                if (paramNames[0].equals(parameters[i].getName())) {
                    return  params[i];
                }
            }
        } else if (paramNames.length == 2) {
            for (int i = 0; i < parameters.length; i++) {
                if (paramNames[0].equals(parameters[i].getName())) {
                    Class<?> parameterClazz = parameters[i].getType();
                    Field targetField = parameterClazz.getDeclaredField(paramNames[1]);
                    targetField.setAccessible(true);
                    return targetField.get(params[i]);
                }
            }
        }
        return null;
    }

}
