package com.android.mb.schedule.entitys;

import java.io.Serializable;

/**
 * Created by Administrator on 2018\9\15 0015.
 */

public class FileData implements Serializable {
    private String file;

    private long id;

    public String getFile() {
        return file == null ? "" : file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
