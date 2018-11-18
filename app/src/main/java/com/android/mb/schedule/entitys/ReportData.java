package com.android.mb.schedule.entitys;

/**
 * Created by cgy on 18/11/18.
 */

public class ReportData {
    private ReportNormalBean normal;
    private ReportAdminBean admin;
    private int is_admin;


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
}
