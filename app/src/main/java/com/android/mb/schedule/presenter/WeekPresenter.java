package com.android.mb.schedule.presenter;

import android.text.TextUtils;

import com.android.mb.schedule.base.BaseMvpPresenter;
import com.android.mb.schedule.entitys.ScheduleData;
import com.android.mb.schedule.presenter.interfaces.IWeekPresenter;
import com.android.mb.schedule.retrofit.http.exception.ApiException;
import com.android.mb.schedule.retrofit.http.exception.NoNetWorkException;
import com.android.mb.schedule.api.ScheduleMethods;
import com.android.mb.schedule.view.interfaces.IWeekView;

import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by cgy on 2018/2/11 0011.
 */
public class WeekPresenter extends BaseMvpPresenter<IWeekView> implements IWeekPresenter {


    @Override
    public void getWeekSchedule(Map<String, Object> requestMap) {
        Observable observable = ScheduleMethods.getInstance().getWeekSchedule(requestMap);
        toSubscribe(observable,  new Subscriber<List<ScheduleData>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if(mMvpView!=null){
                    if (e instanceof ApiException && !TextUtils.isEmpty(e.getMessage())){
                        mMvpView.showToastMessage(e.getMessage());
                    }
                }
            }

            @Override
            public void onNext(List<ScheduleData> result) {
                if (mMvpView!=null){
                    mMvpView.getSuccess(result);
                }
            }
        });
    }
}
