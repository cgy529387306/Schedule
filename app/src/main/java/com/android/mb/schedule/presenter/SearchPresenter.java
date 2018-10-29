package com.android.mb.schedule.presenter;

import android.text.TextUtils;

import com.android.mb.schedule.api.ScheduleMethods;
import com.android.mb.schedule.app.MBApplication;
import com.android.mb.schedule.base.BaseMvpPresenter;
import com.android.mb.schedule.db.GreenDaoManager;
import com.android.mb.schedule.db.Office;
import com.android.mb.schedule.db.User;
import com.android.mb.schedule.entitys.CurrentUser;
import com.android.mb.schedule.entitys.SearchBean;
import com.android.mb.schedule.greendao.OfficeDao;
import com.android.mb.schedule.greendao.UserDao;
import com.android.mb.schedule.presenter.interfaces.ISearchPresenter;
import com.android.mb.schedule.retrofit.http.exception.ApiException;
import com.android.mb.schedule.utils.CommonUtils;
import com.android.mb.schedule.utils.Helper;
import com.android.mb.schedule.utils.NetworkHelper;
import com.android.mb.schedule.utils.ProjectHelper;
import com.android.mb.schedule.view.interfaces.ISearchView;

import java.util.ArrayList;
import java.util.Comparator;
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
        if (NetworkHelper.isNetworkAvailable(MBApplication.getInstance())){
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
        }else{
            getDataFromLocal(requestMap);
        }
    }

    private void getDataFromLocal(Map<String, Object> requestMap) {
        final UserDao userDao = GreenDaoManager.getInstance().getNewSession().getUserDao();
        String keyword = (String) requestMap.get("kw");
        String like = "%"+keyword+"%";
        Observable observable = userDao.queryBuilder().where(UserDao.Properties.Nickname.like(like)).rx().list();
        toSubscribe(observable,  new Subscriber<List<User>>() {
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
            public void onNext(List<User> result) {
                if (mMvpView!=null){
                    List<SearchBean> searchResult = new ArrayList<>();
                    OfficeDao officeDao = GreenDaoManager.getInstance().getNewSession().getOfficeDao();
                    if (Helper.isNotEmpty(result)){
                        List<List<User>> list2 = CommonUtils.dividerList(result, new Comparator<User>() {

                            @Override
                            public int compare(User o1, User o2) {
                                return o1.getOffice_id() == o2.getOffice_id() ? 0:-1;
                            }
                        });
                        for (int i=0;i<list2.size();i++){
                            List<User> userList = list2.get(i);
                            if (Helper.isNotEmpty(userList)){
                                User user = userList.get(0);
                                SearchBean searchBean = new SearchBean();
                                Office office = officeDao.loadByRowId(user.getOffice_id());
                                if (office!=null){
                                    searchBean.setId(office.getId());
                                    searchBean.setName(office.getName());
                                }
                                searchBean.setList(ProjectHelper.transToUserBeanList(userList));
                                searchResult.add(searchBean);
                            }
                        }
                    }
                    mMvpView.searchPeople(searchResult);
                }
            }
        });
    }
}
