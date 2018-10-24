package com.android.mb.schedule.presenter;

import android.text.TextUtils;

import com.android.mb.schedule.base.BaseMvpPresenter;
import com.android.mb.schedule.entitys.SearchBean;
import com.android.mb.schedule.presenter.interfaces.ISearchPresenter;
import com.android.mb.schedule.retrofit.http.exception.ApiException;
import com.android.mb.schedule.retrofit.http.exception.NoNetWorkException;
import com.android.mb.schedule.api.ScheduleMethods;
import com.android.mb.schedule.view.interfaces.ISearchView;

import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by cgy on 2018/2/11 0011.
 */

public class SearchPresenter extends BaseMvpPresenter<ISearchView> implements ISearchPresenter {


    @Override
    public void searchPeople(Map<String, Object> requestMap) {
        Observable observable = ScheduleMethods.getInstance().searchPeople(requestMap);
        toSubscribe(observable,  new Subscriber<List<SearchBean>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if(mMvpView!=null){
                    if (e instanceof ApiException && !TextUtils.isEmpty(e.getMessage())){
                        mMvpView.showToastMessage(e.getMessage());
                    }else if (e instanceof NoNetWorkException && !TextUtils.isEmpty(e.getMessage())){
                        mMvpView.showToastMessage(e.getMessage());
                    }
                }
            }

            @Override
            public void onNext(List<SearchBean> result) {
                if (mMvpView!=null){
                    mMvpView.searchPeople(result);
                }
            }
        });
    }
}
