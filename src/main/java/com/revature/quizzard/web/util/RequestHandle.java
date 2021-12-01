package com.revature.quizzard.web.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class RequestHandle {
    
    private final Handler handlerInstance;
    private final Method handlerMethod;

    public RequestHandle(Handler handlerInstance, Method handlerMethod) {
        this.handlerInstance = handlerInstance;
        this.handlerMethod = handlerMethod;
    }

    public Handler getHandlerInstance() {
        return handlerInstance;
    }

    public Method getHandlerMethod() {
        return handlerMethod;
    }
    
    public Object invokeHandlerMethod(HttpServletRequest req, HttpServletResponse resp) throws InvocationTargetException, IllegalAccessException {
        return handlerMethod.invoke(handlerInstance, req, resp);
    }
}
