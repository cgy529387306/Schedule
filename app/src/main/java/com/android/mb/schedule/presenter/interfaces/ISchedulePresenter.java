package com.android.mb.schedule.presenter.interfaces;

import com.android.mb.schedule.entitys.ScheduleRequest;

public interface ISchedulePresenter {
    void addSchedule(ScheduleRequest request);

    void editSchedule(ScheduleRequest request);
}
