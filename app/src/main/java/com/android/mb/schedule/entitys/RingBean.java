package com.android.mb.schedule.entitys;

import android.media.Ringtone;

/**
 * Created by Administrator on 2018/8/22.
 */

public class RingBean {

    private String name;
    private String path;
    private Ringtone ringtone;

//    public RingBean(String name, String path, Ringtone ringtone) {
//        this.name = name;
//        this.path = path;
//        this.ringtone = ringtone;
//    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Ringtone getRingtone() {
        return ringtone;
    }

    public void setRingtone(Ringtone ringtone) {
        this.ringtone = ringtone;
    }
}
