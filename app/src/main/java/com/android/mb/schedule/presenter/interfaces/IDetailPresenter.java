package com.android.mb.schedule.presenter.interfaces;

import com.android.mb.schedule.entitys.UserBean;

import java.util.List;
import java.util.Map;

public interface IDetailPresenter {
    void getSchedule(Map<String, Object> requestMap);

    void deleteSchedule(Map<String, Object> requestMap);

    void shareTo(long id, List<UserBean> shareList);
}
