package com.liko.base.http;

/**
 * @author Liko
 * @Date 17/10/28 上午8:56
 * @Description 返回数据基类
 */

public class HttpResponse<T> {
    /**
     * 返回Code
     */
    private int code;
    /**
     * 错误信息
     */
    private String error;
    /**
     * 返回结果
     */
    private T result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    //111
    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
