package com.revature.quizzard.web.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.quizzard.web.util.HandlerMapping;
import com.revature.quizzard.web.util.RequestHandle;
import com.revature.quizzard.web.util.RequestContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DispatcherServlet extends HttpServlet {

    private final HandlerMapping handlerMapping;
    private final ObjectMapper mapper;

    public DispatcherServlet(HandlerMapping handlerMapping, ObjectMapper mapper) {
        this.handlerMapping = handlerMapping;
        this.mapper = mapper;
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        try {

            resp.setContentType("application/json");
            RequestContext requestMapping = new RequestContext(req.getMethod(), req.getRequestURI());
            RequestHandle requestHandle = handlerMapping.getHandler(requestMapping);
            System.out.println("DispatcherServlet line 32, requestHandle = " + requestHandle);
            Object payload = requestHandle.invokeHandlerMethod(req, resp);
            resp.getWriter().write(mapper.writeValueAsString(payload));

        } catch (Exception e) {
            resp.setStatus(500);
            e.printStackTrace();
        }
        
    }

}
