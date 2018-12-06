package com.android.mb.schedule.view.interfaces;

import com.android.mb.schedule.base.BaseMvpView;
import com.android.mb.schedule.entitys.KpiRequest;
import com.android.mb.schedule.entitys.ScheduleData;
import com.android.mb.schedule.entitys.ScheduleDetailData;

import java.util.List;

/**
 * Created by cgy on 2018/2/11 0011.
 */
public interface IDetailView extends BaseMvpView {
    void getSuccess(ScheduleDetailData result);

    void deleteSuccess(Object result);

    void shareSuccess(Object result);

    void getKpiSuccess(KpiRequest result);
}
