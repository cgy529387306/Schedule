package com.android.mb.schedule.view.interfaces;

import com.android.mb.schedule.base.BaseMvpView;
import com.android.mb.schedule.entitys.ShareBean;
import com.android.mb.schedule.entitys.ShareData;

import java.util.List;

/**
 * Created by cgy on 2018/2/11 0011.
 */
public interface IMyShareView extends BaseMvpView {
    void getSuccess(List<ShareBean> result);

}
