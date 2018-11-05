package com.android.mb.schedule.presenter;

import android.text.TextUtils;

import com.android.mb.schedule.api.ScheduleMethods;
import com.android.mb.schedule.base.BaseMvpPresenter;
import com.android.mb.schedule.entitys.TreeData;
import com.android.mb.schedule.entitys.UserBean;
import com.android.mb.schedule.presenter.interfaces.IPersonPresenter;
import com.android.mb.schedule.presenter.interfaces.ITagPresenter;
import com.android.mb.schedule.retrofit.http.exception.ApiException;
import com.android.mb.schedule.view.interfaces.IPersonView;
import com.android.mb.schedule.view.interfaces.ITagPersonView;

import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by cgy on 2018/2/11 0011.
 */

public class TagPersonPresenter extends BaseMvpPresenter<ITagPersonView> implements ITagPresenter {

    @Override
    public void getTagList(Map<String, Object> requestMap) {

    }
}
