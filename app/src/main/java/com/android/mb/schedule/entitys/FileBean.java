package com.android.mb.schedule.entitys;

import java.io.Serializable;

/**
 * Created by cgy on 18/10/26.
 */

public class FileBean implements Serializable {
    private long id;
    private String url;
    private String filename;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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