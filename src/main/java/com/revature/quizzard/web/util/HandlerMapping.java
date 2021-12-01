package com.revature.quizzard.web.util;

import java.util.Hashtable;
import java.util.Map;

public class HandlerMapping {

    Map<RequestMapping, RequestHandle> handlerMap = new Hashtable<>();

    public RequestHandle getHandler(RequestMapping requestMapping) {
        return handlerMap.get(requestMapping);
    }

    public void addHandler(RequestMapping requestMapping, RequestHandle requestHandle) {
        handlerMap.put(requestMapping, requestHandle);
    }

}
