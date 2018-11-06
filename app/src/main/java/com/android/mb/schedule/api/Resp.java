package com.android.mb.schedule.api;

public class Resp {

    /**
     * code : 402
     * msg : Sign is wrong
     * time : 1536565503
     * data : null
     */

    private int code;
    private String msg;
    private String time;
    private Object data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
