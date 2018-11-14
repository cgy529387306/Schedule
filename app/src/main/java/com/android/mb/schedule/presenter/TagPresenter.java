package com.android.mb.schedule.presenter;

import android.text.TextUtils;

import com.android.mb.schedule.api.ScheduleMethods;
import com.android.mb.schedule.app.MBApplication;
import com.android.mb.schedule.base.BaseMvpPresenter;
import com.android.mb.schedule.db.GreenDaoManager;
import com.android.mb.schedule.db.Schedule;
import com.android.mb.schedule.entitys.CurrentUser;
import com.android.mb.schedule.entitys.RelatedBean;
import com.android.mb.schedule.entitys.TagBean;
import com.android.mb.schedule.entitys.TagData;
import com.android.mb.schedule.entitys.TreeData;
import com.android.mb.schedule.greendao.ScheduleDao;
import com.android.mb.schedule.presenter.interfaces.IRelatedPresenter;
import com.android.mb.schedule.presenter.interfaces.ITagPresenter;
import com.android.mb.schedule.retrofit.http.exception.ApiException;
import com.android.mb.schedule.utils.NetworkHelper;
import com.android.mb.schedule.utils.ProjectHelper;
import com.android.mb.schedule.view.interfaces.IRelatedView;
import com.android.mb.schedule.view.interfaces.ITagView;

import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by cgy on 2018/2/11 0011.
 */
public class TagPresenter extends BaseMvpPresenter<ITagView> implements ITagPresenter {

    @Override
    public void getTagList(Map<String, Object> requestMap) {
        Observable observable = ScheduleMethods.getInstance().getTagList(requestMap);
        toSubscribe(observable,  new Subscriber<TagData>() {
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
            public void onNext(TagData result) {
                if (mMvpView!=null){
                    if (result!=null){
                        mMvpView.getSuccess(result.getTags());
                    }
                }
            }
        });
    }
}
