package com.android.mb.schedule.view.interfaces;

import com.android.mb.schedule.base.BaseMvpView;
import com.android.mb.schedule.entitys.LoginData;

/**
 * Created by cgy on 2018/2/11 0011.
 */
public interface ILoginView extends BaseMvpView {
    void loginSuccess(LoginData result);
}
