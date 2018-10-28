package com.android.mb.schedule.entitys;

import java.util.List;

/**
 * Created by cgy on 18/10/26.
 */

public class ScheduleSync {
    /**
     * {
     "id": 13,
     "title": "集团办公",
     "description": "",
     "date": "2018-07-02",
     "time_s": 1530489600,
     "time_e": 1530504000,
     "address": "集团",
     "startTime": "08:00",
     "endTime": "12:00",
     "allDay": 0,
     "repeattype": 1,
     "remind": 0,
     "important": 0,
     "summary": "",
     "not_remind_related": 0,
     "close_time": 0,
     "createtime": 1530426920,
     "updatetime": 1530427254,
     "st_del": 0,
     "create_by": 617,
     "file": [],
     "related": [],
     "share": []
     }
     */
    private long id;
    private long create_by;
    private String title;
    private String description;
    private String date;
    private long time_s;
    private long time_e;
    private String address;
    private String startTime;
    private String endTime;
    private int allDay;
    private int repeattype;
    private int remind;
    private int important;
    private String summary;
    private int not_remind_related;
    private int close_time;
    private int createtime;
    private int updatetime;
    private int st_del;
    private List<UserBean> related;
    private List<UserBean> share;
    private List<FileBean> file;

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
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

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public int getNot_remind_related() {
        return not_remind_related;
    }

    public void setNot_remind_related(int not_remind_related) {
        this.not_remind_related = not_remind_related;
    }

    public int getClose_time() {
        return close_time;
    }

    public void setClose_time(int close_time) {
        this.close_time = close_time;
    }

    public int getCreatetime() {
        return createtime;
    }

    public void setCreatetime(int createtime) {
        this.createtime = createtime;
    }

    public int getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(int updatetime) {
        this.updatetime = updatetime;
    }

    public int getSt_del() {
        return st_del;
    }

    public void setSt_del(int st_del) {
        this.st_del = st_del;
    }

    public List<UserBean> getRelated() {
        return related;
    }

    public void setRelated(List<UserBean> related) {
        this.related = related;
    }

    public List<UserBean> getShare() {
        return share;
    }

    public void setShare(List<UserBean> share) {
        this.share = share;
    }

    public List<FileBean> getFile() {
        return file;
    }

    public void setFile(List<FileBean> file) {
        this.file = file;
    }

    public long getCreate_by() {
        return create_by;
    }

    public void setCreate_by(long create_by) {
        this.create_by = create_by;
    }
}
