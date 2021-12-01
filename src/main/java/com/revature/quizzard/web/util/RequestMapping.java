package com.revature.quizzard.web.util;

import java.util.Objects;

public class RequestMapping {

    private final String httpMethod;
    private final String uriPath;

    public RequestMapping(String httpMethod, String uriPath) {
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
        RequestMapping that = (RequestMapping) o;
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
