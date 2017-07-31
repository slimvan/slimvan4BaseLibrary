package com.xingyun.slimvan.http;

/**
 * json返回信息实体类
 * Created by xingyun on 2016/8/25.
 */
public class JsonBean {
    /**
     * 状态码
     */
    private String error;
    /**
     * 提示消息
     */
    private String msg;
    /**
     * 内容
     */
    private String xy;
    private String iv;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getIv() {
        return iv;
    }

    public void setIv(String iv) {
        this.iv = iv;
    }

    public String getXy() {
        return xy;
    }

    public void setXy(String xy) {
        this.xy = xy;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
