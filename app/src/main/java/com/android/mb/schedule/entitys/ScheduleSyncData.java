package com.android.mb.schedule.entitys;

import java.util.List;

/**
 * Created by cgy on 18/10/26.
 */

public class ScheduleSyncData {
    private int total;
    private List<ScheduleSync> upd;
    private List<Long> del;

    public List<ScheduleSync> getUpd() {
        return upd;
    }

    public void setUpd(List<ScheduleSync> upd) {
        this.upd = upd;
    }

    public List<Long> getDel() {
        return del;
    }

    public void setDel(List<Long> del) {
        this.del = del;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
