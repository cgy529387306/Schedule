package com.android.mb.schedule.view.interfaces;

import com.android.mb.schedule.base.BaseMvpView;
import com.android.mb.schedule.entitys.MyScheduleBean;
import com.android.mb.schedule.entitys.RelatedBean;

import java.util.List;

/**
 * Created by cgy on 2018/2/11 0011.
 */
public interface IMineView extends BaseMvpView {
    void getSuccess(List<MyScheduleBean> result);

}
