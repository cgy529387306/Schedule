package com.android.mb.schedule.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.mb.schedule.R;
import com.android.mb.schedule.adapter.MyFragmentPagerAdapter;
import com.android.mb.schedule.base.BaseMvpActivity;
import com.android.mb.schedule.constants.ProjectConstants;
import com.android.mb.schedule.db.GreenDaoManager;
import com.android.mb.schedule.db.Office;
import com.android.mb.schedule.db.Schedule;
import com.android.mb.schedule.db.User;
import com.android.mb.schedule.entitys.CurrentUser;
import com.android.mb.schedule.entitys.LoginData;
import com.android.mb.schedule.fragment.MonthFragment;
import com.android.mb.schedule.fragment.WeekFragment;
import com.android.mb.schedule.greendao.OfficeDao;
import com.android.mb.schedule.greendao.ScheduleDao;
import com.android.mb.schedule.greendao.UserDao;
import com.android.mb.schedule.keeplive.KeepLiveManager;
import com.android.mb.schedule.presenter.HomePresenter;
import com.android.mb.schedule.rxbus.Events;
import com.android.mb.schedule.service.LongRunningService;
import com.android.mb.schedule.service.SyncOtherService;
import com.android.mb.schedule.service.SyncService;
import com.android.mb.schedule.utils.AppHelper;
import com.android.mb.schedule.utils.Helper;
import com.android.mb.schedule.utils.ImageUtils;
import com.android.mb.schedule.utils.JsonHelper;
import com.android.mb.schedule.utils.NavigationHelper;
import com.android.mb.schedule.utils.NetworkHelper;
import com.android.mb.schedule.utils.PreferencesHelper;
import com.android.mb.schedule.utils.ProgressDialogHelper;
import com.android.mb.schedule.utils.ProjectHelper;
import com.android.mb.schedule.utils.ToastHelper;
import com.android.mb.schedule.view.interfaces.IHomeView;
import com.android.mb.schedule.widget.CircleImageView;
import com.android.mb.schedule.widget.FragmentViewPager;
import com.pgyersdk.update.PgyUpdateManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import rx.functions.Action1;

/**
 * 首页
 */
public class MainActivity extends BaseMvpActivity<HomePresenter,IHomeView> implements IHomeView, View.OnClickListener{

    private DrawerLayout mDrawerLayout;
    private TabLayout mTabLayout;
    private ImageView mIvOpenDrawerLayout;
    private FragmentViewPager mFragmentViewPager;
    private ArrayList<Fragment> mFragmentArrayList;
    private MonthFragment mMonthFragment;
    private WeekFragment mWeekFragment;
    private CircleImageView mIvHead; //头像
    private TextView mTvName;  // 名字
    private TextView mTvJob;  //职位
    private TextView mTvTitle; // 标题
    private ImageView mIvRefresh; // 右边图标
    private ImageView mIvToday; // 右边图标
    private LinearLayout mLlyMenu;
    private CoordinatorLayout mCoordinatorLayout;
    @Override
    protected void loadIntent() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initTitle() {
        hideActionBar();
    }

    @Override
    protected void bindViews() {
        initView();
        initTabViewPager();
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        ProjectHelper.syncSchedule(mContext,false);
        startService(new Intent(this, SyncOtherService.class));
        startService(new Intent(this, LongRunningService.class));
        KeepLiveManager.getInstance().registerKeepLifeReceiver(this);
        PgyUpdateManager.setIsForced(false); //设置是否强制更新。true为强制更新；false为不强制更新（默认值）。
        PgyUpdateManager.register(this);
        mPresenter.getUserInfo();
        regiestEvent(ProjectConstants.EVENT_UPDATE_USER_INFO, new Action1<Events<?>>() {
            @Override
            public void call(Events<?> events) {
               initUserInfo(CurrentUser.getInstance());
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        KeepLiveManager.getInstance().unregisterKeepLiveReceiver(this);
        PgyUpdateManager.unregister();
    }

    @Override
    protected void setListener() {
        regiestEvent(ProjectConstants.EVENT_SYNC_SUCCESS, new Action1<Events<?>>() {
            @Override
            public void call(Events<?> events) {
               ProgressDialogHelper.dismissProgressDialog();
            }
        });
        findViewById(R.id.nav_my_share).setOnClickListener(this);
        findViewById(R.id.nav_other_share).setOnClickListener(this);
        findViewById(R.id.nav_subordinate_log).setOnClickListener(this);
        findViewById(R.id.nav_relatedme_log).setOnClickListener(this);
        findViewById(R.id.nav_notify).setOnClickListener(this);
        findViewById(R.id.nav_setting).setOnClickListener(this);
        findViewById(R.id.nav_exit).setOnClickListener(this);

        findViewById(R.id.tv_add).setOnClickListener(this);
        mIvOpenDrawerLayout.setOnClickListener(this);
        mDrawerLayout.addDrawerListener(mDrawerListener);
        mIvRefresh.setOnClickListener(this);
        mIvToday.setOnClickListener(this);
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition()==0){
                    //月视图
                    mFragmentViewPager.setCurrentItem(tab.getPosition(),true);
                    if (mMonthFragment!=null){
                        mMonthFragment.scrollToSelectDate();
                    }
                }else if (tab.getPosition()==1){
                    //周视图
                    mFragmentViewPager.setCurrentItem(tab.getPosition(),true);
                    if (mWeekFragment!=null){
                        mWeekFragment.scrollToSelectDate();
                    }
                }else if (tab.getPosition()==3){
                    //我的日程
                    NavigationHelper.startActivity(MainActivity.this,ScheduleUserActivity.class,null,false);
                    TabLayout.Tab tab1 = mTabLayout.getTabAt(mFragmentViewPager.getCurrentItem());
                    if (tab1!=null){
                        tab1.select();
                    }
                }else if (tab.getPosition()==4){
                    //与我相关
                    NavigationHelper.startActivity(MainActivity.this,ScheduleRelateActivity.class,null,false);
                    TabLayout.Tab tab1 = mTabLayout.getTabAt(mFragmentViewPager.getCurrentItem());
                    if (tab1!=null){
                        tab1.select();
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    private void initView() {
        mCoordinatorLayout = findViewById(R.id.coordinator);
        mLlyMenu = findViewById(R.id.lly_menu);
        mTvTitle = findViewById(R.id.tv_main_title);
        mIvRefresh = findViewById(R.id.iv_refresh);
        mIvToday = findViewById(R.id.iv_today);
        mTabLayout = findViewById(R.id.tab_layout);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mIvOpenDrawerLayout = findViewById(R.id.iv_open_drawerlayout);
        mFragmentViewPager = findViewById(R.id.fragmentViewPager);
        mIvHead = findViewById(R.id.iv_head);
        mTvName =  findViewById(R.id.tv_name);
        mTvJob =  findViewById(R.id.tv_job);
    }

    private void initTabViewPager(){
        mFragmentArrayList = new ArrayList<>();
        mMonthFragment = new MonthFragment();
        mWeekFragment = new WeekFragment();
        mFragmentArrayList.add(mMonthFragment);
        mFragmentArrayList.add(mWeekFragment);
        mFragmentViewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), mFragmentArrayList));
        mFragmentViewPager.setOffscreenPageLimit(mFragmentArrayList.size());
        mFragmentViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        setViewTabs();
    }

    /**
     * @description: 设置添加Tab
     */
    private void setViewTabs(){
        int[] tabTitles = new int[]{R.string.tab_month,R.string.tab_week,R.string.tab_add,R.string.tab_day,R.string.tab_user};
        int[] tabImages = new int[]{R.drawable.btn_tab_month,R.drawable.btn_tab_week,R.color.transparent,R.drawable.btn_tab_day,R.drawable.btn_tab_user};
        for (int i = 0; i < tabImages.length; i++) {
            TabLayout.Tab tab = mTabLayout.newTab();
            View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.custom_tab,null);
            tab.setCustomView(view);

            TextView tvTitle = view.findViewById(R.id.tv_tab);
            tvTitle.setText(tabTitles[i]);
            ImageView imgTab =  view.findViewById(R.id.iv_tab);
            imgTab.setImageResource(tabImages[i]);
            mTabLayout.addTab(tab);
        }
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(Gravity.LEFT);
        } else {
            backPressed();
        }
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.iv_open_drawerlayout){
            if (!mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
                mDrawerLayout.openDrawer(Gravity.LEFT);
            } else {
                mDrawerLayout.closeDrawer(Gravity.LEFT);
            }
        }else if (id == R.id.tv_add){
            Bundle bundle = new Bundle();
            bundle.putInt("type",0);
            if (mMonthFragment!=null && Helper.isNotEmpty(mMonthFragment.mSelectDate)){
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.HOUR_OF_DAY,1);
                String time = Helper.date2String(calendar.getTime(),"HH:00:00");
                String date = mMonthFragment.mSelectDate+" "+time;
                bundle.putString("date",date);
            }
            NavigationHelper.startActivity(MainActivity.this,ScheduleAddActivity.class,bundle,false);
        }else if (id == R.id.iv_refresh){
            if (NetworkHelper.isNetworkAvailable(mContext)){
                long lastUpdateTime = PreferencesHelper.getInstance().getLong(ProjectConstants.KEY_LAST_SYNC+ CurrentUser.getInstance().getId(),0);
                if (lastUpdateTime==0){
                    showToastMessage("正在后台同步中，请稍等...");
                }else {
                    ProjectHelper.syncSchedule(mContext,true);
                }
            }else{
                showToastMessage("当前网络已断开，无法同步");
            }

        }else if (id == R.id.iv_today){
            if (mFragmentViewPager.getCurrentItem()==0 && mMonthFragment!=null){
                mMonthFragment.toToday();
            }
            if (mFragmentViewPager.getCurrentItem()==1 && mWeekFragment!=null){
                mWeekFragment.toToday();
            }
        }else if (id == R.id.nav_my_share){ //我的分享
            NavigationHelper.startActivity(MainActivity.this,ScheduleShareActivity.class,null,false);
            mDrawerLayout.closeDrawer(Gravity.LEFT);
        }else if (id == R.id.nav_other_share){  //他人分享
            NavigationHelper.startActivity(MainActivity.this,ScheduleShareOtherActivity.class,null,false);
            mDrawerLayout.closeDrawer(Gravity.LEFT);
        } else if (id == R.id.nav_subordinate_log){  // 下属日志
            NavigationHelper.startActivity(MainActivity.this,UnderActivity.class,null,false);
            mDrawerLayout.closeDrawer(Gravity.LEFT);
        }
        else if (id == R.id.nav_relatedme_log){  // 与我相关的日志
            NavigationHelper.startActivity(MainActivity.this,ScheduleRelateActivity.class,null,false);
            mDrawerLayout.closeDrawer(Gravity.LEFT);
        }else if (id == R.id.nav_setting){  //设置
            NavigationHelper.startActivity(MainActivity.this,SettingActivity.class,null,false);
            mDrawerLayout.closeDrawer(Gravity.LEFT);
        }else if (id == R.id.nav_exit) {  //退出
            CurrentUser.getInstance().loginOut();
            NavigationHelper.startActivity(MainActivity.this, LoginActivity.class, null, true);
            mDrawerLayout.closeDrawer(Gravity.LEFT);
        } else if (id == R.id.nav_notify) {  //通知
            NavigationHelper.startActivity(MainActivity.this, WeekReportActivity.class, null, false);
            mDrawerLayout.closeDrawer(Gravity.LEFT);
        }
    }

    private void initUserInfo(CurrentUser currentUser){
        if (currentUser!=null){
            mTvName.setText(ProjectHelper.getCommonText(currentUser.getNickname()));
            mTvJob.setText(ProjectHelper.getCommonText(currentUser.getOffice_name()));
            ImageUtils.displayAvatar(this,currentUser.getAvatar(),mIvHead);
        }
    }

    public void setTitle(String title){
        if (mTvTitle!=null && !TextUtils.isEmpty(title)){
            mTvTitle.setText(title);
        }
    }

    private static final long DOUBLE_CLICK_INTERVAL = 2000;
    private long mLastClickTimeMills = 0;

    private void backPressed() {
        if (System.currentTimeMillis() - mLastClickTimeMills > DOUBLE_CLICK_INTERVAL) {
            ToastHelper.showToast("再按一次返回退出");
            mLastClickTimeMills = System.currentTimeMillis();
            return;
        }
        finish();
    }

    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter();
    }

    @Override
    public void getUserInfoSuccess(LoginData result) {
        if (result!=null && result.getUserinfo()!=null){
            CurrentUser.getInstance().login(result.getUserinfo(),false);
            initUserInfo(result.getUserinfo());
        }
    }

    private void doGetDbData(){
        ScheduleDao scheduleDao = GreenDaoManager.getInstance().getNewSession().getScheduleDao();
        Long userId = CurrentUser.getInstance().getId();
        List<Schedule> dataList = scheduleDao.queryBuilder().where(ScheduleDao.Properties.Create_by.eq(userId)).list();
        Log.e("scheduleList:", JsonHelper.toJson(dataList));

        UserDao userDao = GreenDaoManager.getInstance().getNewSession().getUserDao();
        List<User> userList = userDao.queryBuilder().list();
        Log.e("userList:", JsonHelper.toJson(userList));

        OfficeDao officeDao = GreenDaoManager.getInstance().getNewSession().getOfficeDao();
        List<Office> officeList = officeDao.queryBuilder().list();
        Log.e("officeList:", JsonHelper.toJson(officeList));
    }


    private DrawerLayout.DrawerListener mDrawerListener = new DrawerLayout.DrawerListener() {
        @Override
        public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
            mCoordinatorLayout.layout(mLlyMenu.getRight(), 0, mLlyMenu.getRight() + AppHelper.getScreenWidth(), AppHelper.getScreenHeight());
        }
        @Override
        public void onDrawerOpened(View drawerView) {}
        @Override
        public void onDrawerClosed(View drawerView) {
        }
        @Override
        public void onDrawerStateChanged(int newState) {}
    };

}
