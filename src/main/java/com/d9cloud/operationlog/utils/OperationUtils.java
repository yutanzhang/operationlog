package com.d9cloud.operationlog.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
public class OperationUtils {

    private static final String USER_ID = "session_user_id";

    private static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    private static HttpSession getSession() {
        return getRequest().getSession();
    }

    public static Integer getUserId(){
        Integer userId = (Integer) getSession().getAttribute(USER_ID);
        if(null == userId){
            return -1;
        }
        return userId;
    }

}
