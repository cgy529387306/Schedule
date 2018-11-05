package com.android.mb.schedule.view.interfaces;

import com.android.mb.schedule.base.BaseMvpView;
import com.android.mb.schedule.entitys.RelatedBean;
import com.android.mb.schedule.entitys.TagBean;

import java.util.List;

/**
 * Created by cgy on 2018/2/11 0011.
 */
public interface ITagView extends BaseMvpView {
    void getSuccess(List<TagBean> result);

}
