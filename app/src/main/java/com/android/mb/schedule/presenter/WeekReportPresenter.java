package com.android.mb.schedule.presenter;

import android.text.TextUtils;

import com.android.mb.schedule.api.ScheduleMethods;
import com.android.mb.schedule.base.BaseMvpPresenter;
import com.android.mb.schedule.entitys.ReportData;
import com.android.mb.schedule.presenter.interfaces.IWeekReportPresenter;
import com.android.mb.schedule.retrofit.http.exception.ApiException;
import com.android.mb.schedule.view.interfaces.IWeekReprtView;

import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by cgy on 2018/2/11 0011.
 */
public class WeekReportPresenter extends BaseMvpPresenter<IWeekReprtView> implements IWeekReportPresenter {

    @Override
    public void getReport(Map<String, Object> requestMap) {
        Observable observable = ScheduleMethods.getInstance().getWeekReport(requestMap);
        toSubscribe(observable,  new Subscriber<List<ReportData>>() {
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
            public void onNext(List<ReportData> result) {
                if (mMvpView!=null){
                    mMvpView.getSuccess(result);
                }
            }
        });
    }

}
