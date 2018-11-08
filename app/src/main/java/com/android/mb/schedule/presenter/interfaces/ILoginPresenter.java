package com.android.mb.schedule.presenter.interfaces;

import java.util.Map;

public interface ILoginPresenter {
    void userLogin(Map<String,Object> requestMap);

    void bindWx(Map<String,Object> requestMap);
}
