package com.android.mb.schedule.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by cgy on 18/11/1.
 */
@Entity
public class Add {
    @Id(autoincrement = true)
    private Long id;
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
    private long close_time;
    private long createtime;
    private long updatetime;
    private int st_del;
    private String related;
    private String share;
    private String file;
    @Generated(hash = 1549220595)
    public Add(Long id, long create_by, String title, String description,
            String date, long time_s, long time_e, String address, String startTime,
            String endTime, int allDay, int repeattype, int remind, int important,
            String summary, int not_remind_related, long close_time,
            long createtime, long updatetime, int st_del, String related,
            String share, String file) {
        this.id = id;
        this.create_by = create_by;
        this.title = title;
        this.description = description;
        this.date = date;
        this.time_s = time_s;
        this.time_e = time_e;
        this.address = address;
        this.startTime = startTime;
        this.endTime = endTime;
        this.allDay = allDay;
        this.repeattype = repeattype;
        this.remind = remind;
        this.important = important;
        this.summary = summary;
        this.not_remind_related = not_remind_related;
        this.close_time = close_time;
        this.createtime = createtime;
        this.updatetime = updatetime;
        this.st_del = st_del;
        this.related = related;
        this.share = share;
        this.file = file;
    }
    @Generated(hash = 798142234)
    public Add() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public long getCreate_by() {
        return this.create_by;
    }
    public void setCreate_by(long create_by) {
        this.create_by = create_by;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return this.description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getDate() {
        return this.date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public long getTime_s() {
        return this.time_s;
    }
    public void setTime_s(long time_s) {
        this.time_s = time_s;
    }
    public long getTime_e() {
        return this.time_e;
    }
    public void setTime_e(long time_e) {
        this.time_e = time_e;
    }
    public String getAddress() {
        return this.address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getStartTime() {
        return this.startTime;
    }
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
    public String getEndTime() {
        return this.endTime;
    }
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
    public int getAllDay() {
        return this.allDay;
    }
    public void setAllDay(int allDay) {
        this.allDay = allDay;
    }
    public int getRepeattype() {
        return this.repeattype;
    }
    public void setRepeattype(int repeattype) {
        this.repeattype = repeattype;
    }
    public int getRemind() {
        return this.remind;
    }
    public void setRemind(int remind) {
        this.remind = remind;
    }
    public int getImportant() {
        return this.important;
    }
    public void setImportant(int important) {
        this.important = important;
    }
    public String getSummary() {
        return this.summary;
    }
    public void setSummary(String summary) {
        this.summary = summary;
    }
    public int getNot_remind_related() {
        return this.not_remind_related;
    }
    public void setNot_remind_related(int not_remind_related) {
        this.not_remind_related = not_remind_related;
    }
    public long getClose_time() {
        return this.close_time;
    }
    public void setClose_time(long close_time) {
        this.close_time = close_time;
    }
    public long getCreatetime() {
        return this.createtime;
    }
    public void setCreatetime(long createtime) {
        this.createtime = createtime;
    }
    public long getUpdatetime() {
        return this.updatetime;
    }
    public void setUpdatetime(long updatetime) {
        this.updatetime = updatetime;
    }
    public int getSt_del() {
        return this.st_del;
    }
    public void setSt_del(int st_del) {
        this.st_del = st_del;
    }
    public String getRelated() {
        return this.related;
    }
    public void setRelated(String related) {
        this.related = related;
    }
    public String getShare() {
        return this.share;
    }
    public void setShare(String share) {
        this.share = share;
    }
    public String getFile() {
        return this.file;
    }
    public void setFile(String file) {
        this.file = file;
    }
}
