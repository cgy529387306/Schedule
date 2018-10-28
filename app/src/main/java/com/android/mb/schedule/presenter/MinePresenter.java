package com.android.mb.schedule.presenter;

import android.text.TextUtils;

import com.android.mb.schedule.app.MBApplication;
import com.android.mb.schedule.base.BaseMvpPresenter;
import com.android.mb.schedule.db.GreenDaoManager;
import com.android.mb.schedule.db.Schedule;
import com.android.mb.schedule.entitys.CurrentUser;
import com.android.mb.schedule.entitys.MyScheduleBean;
import com.android.mb.schedule.greendao.ScheduleDao;
import com.android.mb.schedule.presenter.interfaces.IMinePresenter;
import com.android.mb.schedule.retrofit.http.exception.ApiException;
import com.android.mb.schedule.retrofit.http.exception.NoNetWorkException;
import com.android.mb.schedule.api.ScheduleMethods;
import com.android.mb.schedule.utils.Helper;
import com.android.mb.schedule.utils.NetworkHelper;
import com.android.mb.schedule.utils.ProjectHelper;
import com.android.mb.schedule.view.interfaces.IMineView;

import org.greenrobot.greendao.query.WhereCondition;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by cgy on 2018/2/11 0011.
 */
public class MinePresenter extends BaseMvpPresenter<IMineView> implements IMinePresenter {

    @Override
    public void getMine(Map<String, Object> requestMap) {
        if (NetworkHelper.isNetworkAvailable(MBApplication.getInstance())){
            Observable observable = ScheduleMethods.getInstance().getMine(requestMap);
            toSubscribe(observable,  new Subscriber<List<MyScheduleBean>>() {
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
                public void onNext(List<MyScheduleBean> result) {
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
        long userId = CurrentUser.getInstance().getId();
        int type = (int) requestMap.get("type");
        int page = (int) requestMap.get("page");
        int offset = (page-1)*20;
        Observable observable;
        if (type == 1){
            observable = scheduleDao.queryBuilder().where(ScheduleDao.Properties.Create_by.eq(userId)).where(ScheduleDao.Properties.Date.ge(Helper.date2String(new Date(),"yyyy-MM-dd")))
                    .orderDesc(ScheduleDao.Properties.Time_s).orderDesc(ScheduleDao.Properties.Id).limit(20).offset(offset).rx().list();
        }else{
            observable = scheduleDao.queryBuilder().where(ScheduleDao.Properties.Create_by.eq(userId)).where(ScheduleDao.Properties.Date.lt(Helper.date2String(new Date(),"yyyy-MM-dd")))
                    .orderDesc(ScheduleDao.Properties.Time_s).orderDesc(ScheduleDao.Properties.Id).limit(20).offset(offset).rx().list();
        }
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
                    mMvpView.getSuccess(ProjectHelper.transToMyScheduleList(result));
                }
            }
        });
    }
}
