package com.android.mb.schedule.presenter;

import android.text.TextUtils;

import com.android.mb.schedule.app.MBApplication;
import com.android.mb.schedule.base.BaseMvpPresenter;
import com.android.mb.schedule.db.GreenDaoManager;
import com.android.mb.schedule.db.Schedule;
import com.android.mb.schedule.entitys.CurrentUser;
import com.android.mb.schedule.entitys.ShareBean;
import com.android.mb.schedule.greendao.ScheduleDao;
import com.android.mb.schedule.presenter.interfaces.IMySharePresenter;
import com.android.mb.schedule.retrofit.http.exception.ApiException;
import com.android.mb.schedule.retrofit.http.exception.NoNetWorkException;
import com.android.mb.schedule.api.ScheduleMethods;
import com.android.mb.schedule.utils.NetworkHelper;
import com.android.mb.schedule.utils.ProjectHelper;
import com.android.mb.schedule.view.interfaces.IMyShareView;

import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by cgy on 2018/2/11 0011.
 */
public class MySharePresenter extends BaseMvpPresenter<IMyShareView> implements IMySharePresenter {

    @Override
    public void getMyShare(Map<String, Object> requestMap) {
        if (NetworkHelper.isNetworkAvailable(MBApplication.getInstance())){
            Observable observable = ScheduleMethods.getInstance().getIShare(requestMap);
            toSubscribe(observable,  new Subscriber<List<ShareBean>>() {
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
                public void onNext(List<ShareBean> result) {
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
        long uid = CurrentUser.getInstance().getId();
        int page = (int) requestMap.get("page");
        int offset = (page-1)*20;
        Observable  observable = scheduleDao.queryBuilder().where(ScheduleDao.Properties.Create_by.eq(uid)).where(ScheduleDao.Properties.Share.isNotNull()).where(ScheduleDao.Properties.Share.notEq("[]"))
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
                    mMvpView.getSuccess(ProjectHelper.transToShareScheduleList(result));
                }
            }
        });
    }
}
