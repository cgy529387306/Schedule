package com.android.mb.schedule.view.interfaces;

import com.android.mb.schedule.base.BaseMvpView;
import com.android.mb.schedule.entitys.FileData;

public interface IScheduleView extends BaseMvpView{
    void addSuccess(Object result);

    void editSuccess(Object result);

    void uploadSuccess(FileData result);

    void updateFail();
}
