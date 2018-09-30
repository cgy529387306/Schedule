package com.android.mb.schedule.entitys;

import java.io.Serializable;

public class ScheduleRequest implements Serializable{

    private long id;

    private String title;

    private String description;

    private String summary;

    private String address;

    private long start;

    private long end;

    private int allDay;

    /**
     * 重复类型 1 - 一次性活动，2 - 每天重复，3 - 周重复，4 月重复
     */
    private int repeattype;

    /**
     * 提醒: 0 不提醒,1 10分钟前，2 15分钟前，3 30分钟前，4 1小时前，5 2小时前，6 24小时前，7 2天前
     */
    private int remind;

    private int important;

    /**
     * id用逗号隔开
     */
    private String related;
    /**
     * id用逗号隔开
     */
    private String share;

    private long fid;

    private int type;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public int getAllDay() {
        return allDay;
    }

    public void setAllDay(int allDay) {
        this.allDay = allDay;
    }

    public int getRepeattype() {
        return repeattype;
    }

    public void setRepeattype(int repeattype) {
        this.repeattype = repeattype;
    }

    public int getRemind() {
        return remind;
    }

    public void setRemind(int remind) {
        this.remind = remind;
    }

    public int getImportant() {
        return important;
    }

    public void setImportant(int important) {
        this.important = important;
    }

    public String getRelated() {
        return related;
    }

    public void setRelated(String related) {
        this.related = related;
    }

    public String getShare() {
        return share;
    }

    public void setShare(String share) {
        this.share = share;
    }

    public long getFid() {
        return fid;
    }

    public void setFid(long fid) {
        this.fid = fid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
