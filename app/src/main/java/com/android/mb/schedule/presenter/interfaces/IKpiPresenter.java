package com.android.mb.schedule.presenter.interfaces;

import com.android.mb.schedule.entitys.KpiRequest;
import com.android.mb.schedule.entitys.ScheduleRequest;

import java.io.File;

public interface IKpiPresenter {
    void addKpi(KpiRequest request);

    void editKpi(KpiRequest request);

    void viewKpi(KpiRequest request);

}
