package com.android.mb.schedule.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class MyTabPagerAdapter extends FragmentPagerAdapter{
    private List<Fragment> mFragmentList;
    //添加标题的集合
    private List<String> mTilteList;
    public MyTabPagerAdapter(FragmentManager fm, List<Fragment> list, List<String> tilteList) {
        super(fm);
        this.mFragmentList = list;
        this.mTilteList = tilteList;
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    //获取标题
    @Override
    public CharSequence getPageTitle(int position) {
        return mTilteList.get(position);
    }
}