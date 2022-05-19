package com.d9cloud.operationlog.utils;

import com.d9cloud.operationlog.entity.constants.OperationLogConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
public class OperationLogUtils {

    private static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    private static HttpSession getSession() {
        return getRequest().getSession();
    }

    public static Integer getUserId(){
        Integer userId = (Integer) getSession().getAttribute(OperationLogConstant.USER_ID);
        if(null == userId){
            return -1;
        }
        return userId;
    }

}
