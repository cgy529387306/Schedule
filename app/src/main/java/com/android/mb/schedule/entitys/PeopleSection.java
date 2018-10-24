package com.android.mb.schedule.entitys;


import com.chad.library.adapter.base.entity.SectionEntity;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class PeopleSection extends SectionEntity<UserBean> {
    public PeopleSection(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public PeopleSection(UserBean t) {
        super(t);
    }

}
