package com.android.mb.schedule.presenter.interfaces;

import java.util.Map;

public interface IDetailPresenter {
    void getSchedule(Map<String, Object> requestMap);

    void deleteSchedule(Map<String, Object> requestMap);

    void shareTo(Map<String, Object> requestMap);
}
