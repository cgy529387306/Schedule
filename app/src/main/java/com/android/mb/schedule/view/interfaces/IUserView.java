package com.android.mb.schedule.view.interfaces;

import com.android.mb.schedule.base.BaseMvpView;
import com.android.mb.schedule.entitys.LoginData;

public interface IUserView extends BaseMvpView {
    void getUserInfoSuccess(LoginData result);

    void setSuccess();
}
