package com.android.mb.schedule.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by cgy on 18/11/1.
 */
@Entity
public class Delete {
    @Id(autoincrement = true)
    private Long id;

    private long sid;

    @Generated(hash = 1972950274)
    public Delete(Long id, long sid) {
        this.id = id;
        this.sid = sid;
    }

    @Generated(hash = 759878511)
    public Delete() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getSid() {
        return this.sid;
    }

    public void setSid(long sid) {
        this.sid = sid;
    }

}
