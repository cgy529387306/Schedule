package com.android.mb.schedule.view.interfaces;

import com.android.mb.schedule.base.BaseMvpView;
import com.android.mb.schedule.entitys.FileData;
import com.android.mb.schedule.entitys.KpiRequest;

public interface IKpiView extends BaseMvpView{
    void addSuccess(Object result);

    void editSuccess(Object result);

    void getSuccess(KpiRequest result);
}
