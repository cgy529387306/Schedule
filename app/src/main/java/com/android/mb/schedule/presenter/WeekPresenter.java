package com.android.mb.schedule.presenter;

import android.text.TextUtils;

import com.android.mb.schedule.app.MBApplication;
import com.android.mb.schedule.base.BaseMvpPresenter;
import com.android.mb.schedule.db.GreenDaoManager;
import com.android.mb.schedule.db.Schedule;
import com.android.mb.schedule.entitys.CurrentUser;
import com.android.mb.schedule.entitys.ScheduleBean;
import com.android.mb.schedule.entitys.ScheduleData;
import com.android.mb.schedule.greendao.ScheduleDao;
import com.android.mb.schedule.presenter.interfaces.IWeekPresenter;
import com.android.mb.schedule.retrofit.http.exception.ApiException;
import com.android.mb.schedule.retrofit.http.exception.NoNetWorkException;
import com.android.mb.schedule.api.ScheduleMethods;
import com.android.mb.schedule.utils.Helper;
import com.android.mb.schedule.utils.NetworkHelper;
import com.android.mb.schedule.utils.ProjectHelper;
import com.android.mb.schedule.view.interfaces.IWeekView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
        if (NetworkHelper.isNetworkAvailable(MBApplication.getInstance())){
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
        }else{
            getDataFromLocal(requestMap);
        }
    }

    private void getDataFromLocal(Map<String, Object> requestMap) {
        ScheduleDao scheduleDao = GreenDaoManager.getInstance().getNewSession().getScheduleDao();
        long userId = CurrentUser.getInstance().getId();
        final String dateStr = (String) requestMap.get("date");
        Observable observable = scheduleDao.queryBuilder().where(ScheduleDao.Properties.Create_by.eq(userId)).where(ScheduleDao.Properties.Time_s.le(getWeekEndTime(dateStr)))
                .orderDesc(ScheduleDao.Properties.Time_s).orderDesc(ScheduleDao.Properties.Id).rx().list();
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
                if (mMvpView!=null && result!=null){
                    setData(dateStr,result);
                }
            }
        });
    }

    private void setData(final String dateStr, final List<Schedule> result){
        Observable observable = Observable.create(new Observable.OnSubscribe<List<ScheduleData>>() {
            @Override
            public void call(Subscriber<? super  List<ScheduleData>> subscriber) {
                List<ScheduleData> dataList = getScheduleDateList(dateStr);
                for (Schedule schedule:result) {
                    if (schedule.getRepeattype() == 2){
                        //每天重复
                        for (ScheduleData scheduleData:dataList){
                            Date date = Helper.string2Date(scheduleData.getDate(),"yyyy-MM-dd");
                            if (ProjectHelper.getDayEndTime(scheduleData.getDate())>=schedule.getTime_s()){
                                if (schedule.getClose_time()==0 || schedule.getClose_time()>date.getTime()/1000){
                                    List<ScheduleBean> scheduleBeanList = scheduleData.getList();
                                    scheduleBeanList.add(ProjectHelper.transToScheduleBean(schedule,scheduleData.getDate()));
                                    scheduleData.setList(scheduleBeanList);
                                }
                            }
                        }

                    }else if (schedule.getRepeattype() == 3){
                        //周重复
                        for (ScheduleData scheduleData:dataList){
                            Date date = Helper.string2Date(scheduleData.getDate(),"yyyy-MM-dd");
                            if (ProjectHelper.getDayEndTime(scheduleData.getDate())>=schedule.getTime_s() && ProjectHelper.isSameWeekNum(date,Helper.long2Date(schedule.getTime_s()*1000))){
                                if (schedule.getClose_time()==0 || schedule.getClose_time()>date.getTime()/1000){
                                    List<ScheduleBean> scheduleBeanList = scheduleData.getList();
                                    scheduleBeanList.add(ProjectHelper.transToScheduleBean(schedule,scheduleData.getDate()));
                                    scheduleData.setList(scheduleBeanList);
                                }
                            }
                        }

                    }else if (schedule.getRepeattype() == 4){
                        //月重复
                        for (ScheduleData scheduleData:dataList){
                            Date date = Helper.string2Date(scheduleData.getDate(),"yyyy-MM-dd");
                            if (ProjectHelper.getDayEndTime(scheduleData.getDate())>=schedule.getTime_s() && ProjectHelper.isSameMonthNum(date,Helper.long2Date(schedule.getTime_s()*1000))){
                                if (schedule.getClose_time()==0 || schedule.getClose_time()>date.getTime()/1000){
                                    List<ScheduleBean> scheduleBeanList = scheduleData.getList();
                                    scheduleBeanList.add(ProjectHelper.transToScheduleBean(schedule,scheduleData.getDate()));
                                    scheduleData.setList(scheduleBeanList);
                                }
                            }
                        }

                    }else if (schedule.getRepeattype() == 1){
                        //一次性
                        for (ScheduleData scheduleData:dataList){
                            Date date = Helper.string2Date(scheduleData.getDate(),"yyyy-MM-dd");
                            if (ProjectHelper.isSameDay(date,Helper.long2Date(schedule.getTime_s()*1000))){
                                List<ScheduleBean> scheduleBeanList = scheduleData.getList();
                                scheduleBeanList.add(ProjectHelper.transToScheduleBean(schedule,scheduleData.getDate()));
                                scheduleData.setList(scheduleBeanList);
                            }
                        }
                    }
                }
                subscriber.onNext(ProjectHelper.sortScheduleData(dataList));
            }
        });
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
                if (mMvpView!=null && result!=null){
                    mMvpView.getSuccess(result);
                }
            }
        });
    }

    private long getWeekEndTime(String dateStr){
        try {
            Date date = Helper.string2Date(dateStr,"yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_MONTH, 6);
            String endTimeStr = Helper.date2String(calendar.getTime(),"yyyy-MM-dd")+" 23:59:59";
            return Helper.dateString2Long(endTimeStr)/1000;
        }catch (Exception e){
            e.getStackTrace();
            return 0;
        }
    }

    private List<ScheduleData> getScheduleDateList(String dateStr){
        try {
            List<ScheduleData> dataList = new ArrayList<>();
            List<String> dateList = getWeekDateList(dateStr);
            for (String date:dateList){
                ScheduleData scheduleData = new ScheduleData();
                scheduleData.setDate(date);
                scheduleData.setList(new ArrayList<ScheduleBean>());
                dataList.add(scheduleData);
            }
            return dataList;
        }catch (Exception e){
            e.getStackTrace();
            return new ArrayList<>();
        }
    }

    private List<String> getWeekDateList(String dateStr){
        try {
            List<String> dateList = new ArrayList<>();
            Date date = Helper.string2Date(dateStr,"yyyy-MM-dd");
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            for (int i = 0; i < 7; i++, cal.add(Calendar.DATE, 1)) {
                Date d = cal.getTime();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String df = simpleDateFormat.format(d);
                dateList.add(df);
            }
            return dateList;
        }catch (Exception e){
            e.getStackTrace();
            return new ArrayList<>();
        }
    }
}
