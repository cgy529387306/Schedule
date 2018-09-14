package com.android.mb.schedule.presenter.interfaces;

import com.android.mb.schedule.entitys.ScheduleRequest;

import java.io.File;

public interface ISchedulePresenter {
    void addSchedule(ScheduleRequest request);

    void editSchedule(ScheduleRequest request);

    void uploadFile(File file);
}
