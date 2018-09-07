package com.android.mb.schedule.view;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
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
import com.android.mb.schedule.fragment.MonthFragment;
import com.android.mb.schedule.fragment.RelatedMeFragment;
import com.android.mb.schedule.fragment.ScheduleFragment;
import com.android.mb.schedule.fragment.WeekFragment;
import com.android.mb.schedule.utils.NavigationHelper;
import com.android.mb.schedule.utils.StatusBarUtil;
import com.android.mb.schedule.widget.CircleImageView;
import com.android.mb.schedule.widget.FragmentViewPager;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener ,View.OnClickListener{

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private TabLayout mTabLayout;
    private boolean isDrawer=false;
    private CoordinatorLayout mCoordinatorLayout;
    private ImageView mIvOpenDrawerLayout;
    private FragmentViewPager mFragmentViewPager;
    private ArrayList<Fragment> mFragmentArrayList;
    private CircleImageView mIvHead; //头像
    private TextView mTvName;  // 名字
    private TextView mTvJob;  //职位
    private TextView mTvTitle; // 标题
    private ImageView mIvRight; // 右边图标

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initStatusBar();
        initView();
        initListener();
    }

    private void initView() {
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
        mIvHead = findViewById(R.id.iv_head);
        mTvName = findViewById(R.id.tv_name);
        mTvJob = findViewById(R.id.tv_job);
        mTvTitle = findViewById(R.id.tv_title);
        mIvRight = findViewById(R.id.iv_right);
        mFragmentArrayList = new ArrayList<>();
        mFragmentArrayList.add(new MonthFragment());
        mFragmentArrayList.add(new WeekFragment());
        mFragmentArrayList.add(new ScheduleFragment());
        mFragmentArrayList.add(new RelatedMeFragment());
        mFragmentViewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), mFragmentArrayList));
        mFragmentViewPager.setOffscreenPageLimit(mFragmentArrayList.size());
        mFragmentViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        setTabs();
    }

    private void initStatusBar(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            StatusBarUtil.StatusBarIconManager.MIUI(this, StatusBarUtil.StatusBarIconManager.TYPE.BLACK);
            StatusBarUtil.StatusBarIconManager.Flyme(this, StatusBarUtil.StatusBarIconManager.TYPE.BLACK);
        }
    }


    /**
     * @description: 设置添加Tab
     */
    private void setTabs(){
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

    private void initListener() {
        mNavigationView.setNavigationItemSelectedListener(this);
        mIvOpenDrawerLayout.setOnClickListener(this);
        mCoordinatorLayout.setOnTouchListener(mOnTouchListener);
        mDrawerLayout.setDrawerListener(mDrawerListener);
        findViewById(R.id.tv_add).setOnClickListener(this);
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mFragmentViewPager.setCurrentItem(tab.getPosition(),true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
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
            super.onBackPressed();
        }
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_my_share){ //我的分享
            NavigationHelper.startActivity(MainActivity.this,ScheduleShareActivity.class,null,false);
        }else if (id == R.id.nav_other_share){  //他人分享
            NavigationHelper.startActivity(MainActivity.this,ScheduleRelateActivity.class,null,false);
        }else if (id == R.id.nav_subordinate_log){  // 下属日志

        }else if (id == R.id.nav_relatedme_log){  // 与我相关的日志
            NavigationHelper.startActivity(MainActivity.this,ScheduleRelateActivity.class,null,false);
        }else if (id == R.id.nav_setting){  //设置
            NavigationHelper.startActivity(MainActivity.this,SettingActivity.class,null,false);
        }else if (id == R.id.nav_exit) {  //退出
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
            NavigationHelper.startActivity(MainActivity.this,NewScheduleActivity.class,null,false);
        }
    }

    public void setTitle(String title){
        if (mTvTitle!=null && !TextUtils.isEmpty(title)){
            mTvTitle.setText(title);
        }
    }
    public void setRightImage(int resId) {
        if (mIvRight != null && mIvRight.getVisibility()==View.GONE)
            mIvRight.setVisibility(View.VISIBLE);
        mIvRight.setImageResource(resId);
    }
}
