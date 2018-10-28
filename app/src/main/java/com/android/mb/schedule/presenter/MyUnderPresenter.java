package com.android.mb.schedule.presenter;

import android.text.TextUtils;

import com.android.mb.schedule.app.MBApplication;
import com.android.mb.schedule.base.BaseMvpPresenter;
import com.android.mb.schedule.db.GreenDaoManager;
import com.android.mb.schedule.db.Schedule;
import com.android.mb.schedule.entitys.CurrentUser;
import com.android.mb.schedule.entitys.RelatedBean;
import com.android.mb.schedule.greendao.OfficeDao;
import com.android.mb.schedule.greendao.ScheduleDao;
import com.android.mb.schedule.presenter.interfaces.IMyUnderPresenter;
import com.android.mb.schedule.retrofit.http.exception.ApiException;
import com.android.mb.schedule.retrofit.http.exception.NoNetWorkException;
import com.android.mb.schedule.api.ScheduleMethods;
import com.android.mb.schedule.utils.Helper;
import com.android.mb.schedule.utils.NetworkHelper;
import com.android.mb.schedule.utils.ProjectHelper;
import com.android.mb.schedule.view.interfaces.IMyUnderView;

import java.util.Date;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by cgy on 2018/2/11 0011.
 */
public class MyUnderPresenter extends BaseMvpPresenter<IMyUnderView> implements IMyUnderPresenter {

    @Override
    public void getUnder(Map<String, Object> requestMap) {
        if (NetworkHelper.isNetworkAvailable(MBApplication.getInstance())){
            Observable observable = ScheduleMethods.getInstance().getUnder(requestMap);
            toSubscribe(observable,  new Subscriber<List<RelatedBean>>() {
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
                public void onNext(List<RelatedBean> result) {
                    if (mMvpView!=null){
                        mMvpView.getSuccess(result);
                    }
                }
            });
        }else{
            getDataFromLocal(requestMap);
        }
    }

    public void getDataFromLocal(Map<String, Object> requestMap) {
        ScheduleDao scheduleDao = GreenDaoManager.getInstance().getNewSession().getScheduleDao();
        long uid = (long) requestMap.get("uid");
        int page = (int) requestMap.get("page");
        int offset = (page-1)*20;
        Observable  observable = scheduleDao.queryBuilder().where(ScheduleDao.Properties.Create_by.eq(uid))
                .orderDesc(ScheduleDao.Properties.Time_s).orderDesc(ScheduleDao.Properties.Id).limit(20).offset(offset).rx().list();
        toSubscribe(observable,  new Subscriber<List<Schedule>>() {
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
            public void onNext(List<Schedule> result) {
                if (mMvpView!=null){
                    mMvpView.getSuccess(ProjectHelper.transToRelateScheduleList(result));
                }
            }
        });
    }


}
