package com.revature.quizzard.web.util;

import java.util.Objects;

public class RequestContext {

    private final String httpMethod;
    private final String uriPath;

    public RequestContext(String httpMethod, String uriPath) {
        this.httpMethod = httpMethod;
        this.uriPath = uriPath;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public String getUriPath() {
        return uriPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestContext that = (RequestContext) o;
        return Objects.equals(httpMethod, that.httpMethod) && Objects.equals(uriPath, that.uriPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(httpMethod, uriPath);
    }

    @Override
    public String toString() {
        return "RequestMapping{" +
                "httpMethod='" + httpMethod + '\'' +
                ", uriPath='" + uriPath + '\'' +
                '}';
    }

}
