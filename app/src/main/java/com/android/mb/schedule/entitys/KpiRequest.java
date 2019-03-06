package com.android.mb.schedule.entitys;

import java.io.Serializable;

/**
 * Created by cgy on 18/11/5.
 */

public class KpiRequest implements Serializable{

    private long resid;
    private long sid;
    private String title;
    private String desc;
    private String res;
    private long res_time_s;
    private long res_time_e;
    private long time_s;
    private long time_e;
    private int performance;

    public long getSid() {
        return sid;
    }

    public void setSid(long sid) {
        this.sid = sid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getRes() {
        return res;
    }

    public void setRes(String res) {
        this.res = res;
    }

    public long getRes_time_s() {
        return res_time_s;
    }

    public void setRes_time_s(long res_time_s) {
        this.res_time_s = res_time_s;
    }

    public long getRes_time_e() {
        return res_time_e;
    }

    public void setRes_time_e(long res_time_e) {
        this.res_time_e = res_time_e;
    }

    public long getTime_s() {
        return time_s;
    }

    public void setTime_s(long time_s) {
        this.time_s = time_s;
    }

    public long getTime_e() {
        return time_e;
    }

    public void setTime_e(long time_e) {
        this.time_e = time_e;
    }

    public long getResid() {
        return resid;
    }

    public void setResid(long resid) {
        this.resid = resid;
    }

    public int getPerformance() {
        return performance;
    }

    public void setPerformance(int performance) {
        this.performance = performance;
    }
}
