package com.android.mb.schedule.entitys;

import com.android.mb.schedule.db.Office;
import com.android.mb.schedule.db.User;

import java.util.List;

/**
 * Created by cgy on 18/10/26.
 */

public class OfficeSyncData {

    private List<Office> upd;
    private List<Long> del;


    public List<Long> getDel() {
        return del;
    }

    public void setDel(List<Long> del) {
        this.del = del;
    }

    public List<Office> getUpd() {
        return upd;
    }

    public void setUpd(List<Office> upd) {
        this.upd = upd;
    }
}
