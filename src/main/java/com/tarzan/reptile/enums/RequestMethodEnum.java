package com.tarzan.reptile.enums;

/**
 * Created by tarzan liu on 2020/5/5.
 */
public enum RequestMethodEnum {
    GET("GET"),
    POST("POST"),
    PUT("PUT"),
    DELETE("DELETE");

    private String methodName;

    RequestMethodEnum(String methodName) {
        this.methodName = methodName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
}
