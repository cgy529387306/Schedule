package com.android.mb.schedule.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.mb.schedule.R;
import com.android.mb.schedule.adapter.MyFragmentPagerAdapter;
import com.android.mb.schedule.base.BaseMvpActivity;
import com.android.mb.schedule.constants.ProjectConstants;
import com.android.mb.schedule.entitys.CurrentUser;
import com.android.mb.schedule.entitys.LoginData;
import com.android.mb.schedule.fragment.MonthFragment;
import com.android.mb.schedule.fragment.WeekFragment;
import com.android.mb.schedule.presenter.HomePresenter;
import com.android.mb.schedule.rxbus.Events;
import com.android.mb.schedule.utils.Helper;
import com.android.mb.schedule.utils.ImageUtils;
import com.android.mb.schedule.utils.NavigationHelper;
import com.android.mb.schedule.utils.ProjectHelper;
import com.android.mb.schedule.utils.ToastHelper;
import com.android.mb.schedule.view.interfaces.IHomeView;
import com.android.mb.schedule.widget.CircleImageView;
import com.android.mb.schedule.widget.FragmentViewPager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import rx.functions.Action1;

/**
 * 首页
 */
public class MainActivity extends BaseMvpActivity<HomePresenter,IHomeView> implements IHomeView, NavigationView.OnNavigationItemSelectedListener ,View.OnClickListener{

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private TabLayout mTabLayout;
    private boolean isDrawer=false;
    private CoordinatorLayout mCoordinatorLayout;
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
        mPresenter.getUserInfo();
        regiestEvent(ProjectConstants.EVENT_UPDATE_USER_INFO, new Action1<Events<?>>() {
            @Override
            public void call(Events<?> events) {
               initUserInfo(CurrentUser.getInstance());
            }
        });
    }

    @Override
    protected void setListener() {
        mNavigationView.setNavigationItemSelectedListener(this);
        mIvOpenDrawerLayout.setOnClickListener(this);
        mCoordinatorLayout.setOnTouchListener(mOnTouchListener);
        mDrawerLayout.setDrawerListener(mDrawerListener);
        findViewById(R.id.tv_add).setOnClickListener(this);
        mIvRefresh.setOnClickListener(this);
        mIvToday.setOnClickListener(this);
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition()<2){
                    //月和周
                    mFragmentViewPager.setCurrentItem(tab.getPosition(),true);
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
        mTvTitle = findViewById(R.id.tv_main_title);
        mIvRefresh = findViewById(R.id.iv_refresh);
        mIvToday = findViewById(R.id.iv_today);
        mTabLayout = findViewById(R.id.tab_layout);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mNavigationView = findViewById(R.id.nav_view);
        mCoordinatorLayout = findViewById(R.id.right);
        mIvOpenDrawerLayout = findViewById(R.id.iv_open_drawerlayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout,R.mipmap.icon_search , R.string.open, R.string.close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        mFragmentViewPager = findViewById(R.id.fragmentViewPager);
        View headerView = mNavigationView.getHeaderView(0);
        mIvHead = headerView.findViewById(R.id.iv_head);
        mTvName =  headerView.findViewById(R.id.tv_name);
        mTvJob =  headerView.findViewById(R.id.tv_job);
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


    View.OnTouchListener mOnTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (isDrawer) {
                return mNavigationView.dispatchTouchEvent(motionEvent);
            } else {
                return false;
            }
        }
    };
    DrawerLayout.DrawerListener mDrawerListener = new DrawerLayout.DrawerListener() {
        @Override
        public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
            isDrawer=true;
            //获取屏幕的宽高
            WindowManager manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
            Display display = manager.getDefaultDisplay();
            //设置右面的布局位置  根据左面菜单的right作为右面布局的left   左面的right+屏幕的宽度（或者right的宽度这里是相等的）为右面布局的right
            mCoordinatorLayout.layout(mNavigationView.getRight(), 0, mNavigationView.getRight() + display.getWidth(), display.getHeight());
        }
        @Override
        public void onDrawerOpened(View drawerView) {}
        @Override
        public void onDrawerClosed(View drawerView) {
            isDrawer=false;
        }
        @Override
        public void onDrawerStateChanged(int newState) {}
    };

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            backPressed();
        }
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_my_share){ //我的分享
            NavigationHelper.startActivity(MainActivity.this,ScheduleShareActivity.class,null,false);
        }else if (id == R.id.nav_other_share){  //他人分享
            NavigationHelper.startActivity(MainActivity.this,ScheduleShareOtherActivity.class,null,false);
        }
//        else if (id == R.id.nav_subordinate_log){  // 下属日志
//
//        }
        else if (id == R.id.nav_relatedme_log){  // 与我相关的日志
            NavigationHelper.startActivity(MainActivity.this,ScheduleRelateActivity.class,null,false);
        }else if (id == R.id.nav_setting){  //设置
            NavigationHelper.startActivity(MainActivity.this,SettingActivity.class,null,false);
        }else if (id == R.id.nav_exit) {  //退出
            CurrentUser.getInstance().loginOut();
            NavigationHelper.startActivity(MainActivity.this, LoginActivity.class, null, true);
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
            if (mFragmentViewPager.getCurrentItem()==0 && mMonthFragment!=null){
                String time = Helper.date2String(new Date(),"HH:mm:ss");
                String date = mMonthFragment.mSelectDate+" "+time;
                bundle.putString("date",date);
            }
            NavigationHelper.startActivity(MainActivity.this,ScheduleAddActivity.class,bundle,false);
        }else if (id == R.id.iv_refresh){

        }else if (id == R.id.iv_today){
            if (mFragmentViewPager.getCurrentItem()==0 && mMonthFragment!=null){
                mMonthFragment.toToday();
            }
            if (mFragmentViewPager.getCurrentItem()==1 && mWeekFragment!=null){
                mWeekFragment.toToday();
            }
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
}
