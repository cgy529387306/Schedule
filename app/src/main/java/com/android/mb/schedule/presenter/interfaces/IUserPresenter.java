package com.android.mb.schedule.presenter.interfaces;

import java.io.File;
import java.util.Map;

public interface IUserPresenter {
    void getUserInfo();

    void setProfile(Map<String,Object> requestMap, File file);
}
