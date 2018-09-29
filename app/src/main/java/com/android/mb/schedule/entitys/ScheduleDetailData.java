package com.android.mb.schedule.entitys;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018\9\17 0017.
 */

public class ScheduleDetailData implements Serializable{
    private ScheduleDetailBean info;
    private List<UserBean> related;
    private List<FileBean> file;
    private List<UserBean> share;

    public ScheduleDetailBean getInfo() {
        return info;
    }

    public void setInfo(ScheduleDetailBean info) {
        this.info = info;
    }

    public List<UserBean> getRelated() {
        if (related == null) {
            return new ArrayList<>();
        }
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
        if (file == null) {
            return new ArrayList<>();
        }
        return file;
    }

    public void setFile(List<FileBean> file) {
        this.file = file;
    }

    public static class FileBean implements Serializable{
        private String url;
        private String filename;

        public String getUrl() {
            return url == null ? "" : url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getFilename() {
            return filename == null ? "" : filename;
        }

        public void setFilename(String filename) {
            this.filename = filename;
        }
    }
}
