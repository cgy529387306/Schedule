package com.android.mb.schedule.entitys;

import java.io.Serializable;

/**
 * Created by cgy on 18/11/19.
 */

public class ReportAdminBean implements Serializable{

    /**
     * week_num : 45
     * worker_num : 1559
     * use_num : 4
     * start_time_stamp : 1541347200
     * end_time_stamp : 1541865600
     * start_time : 2018-11-05
     * end_time : 2018-11-11
     * un_use_num : 1555
     * use_pre : 25%
     * ring_ratio : 下降
     */

    private int week_num;
    private int worker_num;
    private int use_num;
    private long start_time_stamp;
    private long end_time_stamp;
    private String start_time;
    private String end_time;
    private int un_use_num;
    private String use_pre;
    private String ring_ratio;

    public int getWeek_num() {
        return week_num;
    }

    public void setWeek_num(int week_num) {
        this.week_num = week_num;
    }

    public int getWorker_num() {
        return worker_num;
    }

    public void setWorker_num(int worker_num) {
        this.worker_num = worker_num;
    }

    public int getUse_num() {
        return use_num;
    }

    public void setUse_num(int use_num) {
        this.use_num = use_num;
    }

    public long getStart_time_stamp() {
        return start_time_stamp;
    }

    public void setStart_time_stamp(long start_time_stamp) {
        this.start_time_stamp = start_time_stamp;
    }

    public long getEnd_time_stamp() {
        return end_time_stamp;
    }

    public void setEnd_time_stamp(long end_time_stamp) {
        this.end_time_stamp = end_time_stamp;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public int getUn_use_num() {
        return un_use_num;
    }

    public void setUn_use_num(int un_use_num) {
        this.un_use_num = un_use_num;
    }

    public String getUse_pre() {
        return use_pre;
    }

    public void setUse_pre(String use_pre) {
        this.use_pre = use_pre;
    }

    public String getRing_ratio() {
        return ring_ratio;
    }

    public void setRing_ratio(String ring_ratio) {
        this.ring_ratio = ring_ratio;
    }
}
