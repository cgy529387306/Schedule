package com.android.mb.schedule.entitys;

import java.io.Serializable;

/**
 * Created by Administrator on 2018\9\17 0017.
 */

public class ScheduleDetailBean implements Serializable {
    private long id;
    private String title;
    private String description;
    private String summary;
    private String address;
    private long time_s;
    private long time_e;
    private String date;
    private String startTime;
    private String endTime;
    private int allDay;
    private int repeattype;//;//'重复类型 1 - 一次性活动，2 - 每天重复，3 - 周重复，4 月重复'
    private int remind;////提醒 0 不提醒,1 10分钟前，2 15分钟前，3 30分钟前，4 1小时前，5 2小时前，6 24小时前，7 2天前
    private int important;
    private long create_by;
    private long update_by;
    private String create_date;
    private String update_date;
    private long createtime;
    private long updatetime;
    private String not_remind_related;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title == null ? "" : title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description == null ? "" : description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSummary() {
        return summary == null ? "" : summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getAddress() {
        return address == null ? "" : address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime == null ? "" : startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime == null ? "" : endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
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

    public long getCreate_by() {
        return create_by;
    }

    public void setCreate_by(long create_by) {
        this.create_by = create_by;
    }

    public long getUpdate_by() {
        return update_by;
    }

    public void setUpdate_by(long update_by) {
        this.update_by = update_by;
    }

    public String getCreate_date() {
        return create_date == null ? "" : create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getUpdate_date() {
        return update_date == null ? "" : update_date;
    }

    public void setUpdate_date(String update_date) {
        this.update_date = update_date;
    }

    public long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(long createtime) {
        this.createtime = createtime;
    }

    public long getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(long updatetime) {
        this.updatetime = updatetime;
    }


    public String getNot_remind_related() {
        return not_remind_related == null ? "" : not_remind_related;
    }

    public void setNot_remind_related(String not_remind_related) {
        this.not_remind_related = not_remind_related;
    }

}
