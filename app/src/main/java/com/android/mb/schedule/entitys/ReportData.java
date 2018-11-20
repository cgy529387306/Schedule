package com.android.mb.schedule.entitys;

import java.io.Serializable;

/**
 * Created by cgy on 18/11/18.
 */

public class ReportData implements Serializable{
    private ReportNormalBean normal;
    private ReportAdminBean admin;
    private int is_admin;
    private long show_date;

    public ReportNormalBean getNormal() {
        return normal;
    }

    public void setNormal(ReportNormalBean normal) {
        this.normal = normal;
    }

    public ReportAdminBean getAdmin() {
        return admin;
    }

    public void setAdmin(ReportAdminBean admin) {
        this.admin = admin;
    }

    public int getIs_admin() {
        return is_admin;
    }

    public void setIs_admin(int is_admin) {
        this.is_admin = is_admin;
    }

    public long getShow_date() {
        return show_date;
    }

    public void setShow_date(long show_date) {
        this.show_date = show_date;
    }
}
