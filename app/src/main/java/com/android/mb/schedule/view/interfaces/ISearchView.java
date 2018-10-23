package com.android.mb.schedule.view.interfaces;

import com.android.mb.schedule.base.BaseMvpView;
import com.android.mb.schedule.entitys.SearchBean;
import com.android.mb.schedule.entitys.TreeData;
import com.android.mb.schedule.entitys.UserBean;

import java.util.List;

/**
 * Created by cgy on 2018/2/11 0011.
 */
public interface ISearchView extends BaseMvpView {
    void searchPeople(List<SearchBean> result);
}
