package com.revature.quizzard.web.util;

import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HandlerMapping {

    Map<RequestContext, RequestHandle> handlerMap = new Hashtable<>();

    public RequestHandle getHandler(RequestContext requestContext) {
        return handlerMap.get(requestContext);
    }

    public void addHandler(RequestContext requestMapping, RequestHandle requestHandle) {
        handlerMap.put(requestMapping, requestHandle);
    }

    public void addHandler(Handler handler) {

        Class<? extends Handler> handlerType = handler.getClass();

        List<RequestHandle> requestHandleList = Arrays.stream(handlerType.getDeclaredMethods())
                                                      .filter(handlerMethod -> handlerMethod.isAnnotationPresent(RequestMapping.class))
                                                      .map(handlerMethod -> new RequestHandle(handler, handlerMethod))
                                                      .collect(Collectors.toList());

        List<RequestContext> requestContextList = Arrays.stream(handlerType.getDeclaredMethods())
                                                        .filter(handlerMethod -> handlerMethod.isAnnotationPresent(RequestMapping.class))
                                                        .map(handlerMethod -> {
                                                            RequestMapping mappingMetadata = handlerMethod.getAnnotation(RequestMapping.class);
                                                            return new RequestContext(mappingMetadata.method().toString(), mappingMetadata.value());
                                                        })
                                                        .collect(Collectors.toList());

        for (int i = 0; i < requestContextList.size(); i++) {
            RequestHandle shouldBeNull = handlerMap.put(requestContextList.get(i), requestHandleList.get(i));
            if (shouldBeNull != null) {
                throw new RuntimeException("Duplicate mapping found for request context: " + requestContextList.get(i));
            }
        }

    }

}
