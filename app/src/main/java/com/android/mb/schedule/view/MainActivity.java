package com.android.mb.schedule.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.mb.schedule.R;
import com.android.mb.schedule.adapter.MyFragmentPagerAdapter;
import com.android.mb.schedule.fragment.MonthFragment;
import com.android.mb.schedule.fragment.NewScheduleFragment;
import com.android.mb.schedule.fragment.RelatedMeFragment;
import com.android.mb.schedule.fragment.ScheduleFragment;
import com.android.mb.schedule.fragment.WeekFragment;
import com.android.mb.schedule.utils.NavigationHelper;
import com.android.mb.schedule.widget.CircleImageView;
import com.android.mb.schedule.widget.FragmentViewPager;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener ,View.OnClickListener{

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private boolean isDrawer=false;
    private CoordinatorLayout mCoordinatorLayout;
    private ImageView mIvOpenDrawerLayout;
    private FragmentViewPager mFragmentViewPager;
    private ArrayList<Fragment> mFragmentArrayList;
    private RadioGroup mRgMenu;
    private RadioButton mRdMonth;
    private RadioButton mRdWeek;
    private RadioButton mRdNewSchedule;
    private RadioButton mRdSchedule;
    private RadioButton mRdRelatedMe;
    private CircleImageView mIvHead; //头像
    private TextView mTvName;  // 名字
    private TextView mTvJob;  //职位
    private TextView mTvTitle; // 标题
    private ImageView mIvRight; // 右边图标

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListener();
    }

    private void initView() {
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mNavigationView = findViewById(R.id.nav_view);
        mCoordinatorLayout = findViewById(R.id.right);
        mIvOpenDrawerLayout = findViewById(R.id.iv_open_drawerlayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout,R.mipmap.icon_search , R.string.open, R.string.close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        mFragmentViewPager = findViewById(R.id.fragmentViewPager);
        mRgMenu = findViewById(R.id.rg_menu);
        mRdMonth = findViewById(R.id.rd_month);
        mRdWeek = findViewById(R.id.rd_week);
        mRdNewSchedule = findViewById(R.id.rd_new_schedule);
        mRdSchedule = findViewById(R.id.rd_schedule);
        mRdRelatedMe = findViewById(R.id.rd_related_me);
        mIvHead = findViewById(R.id.iv_head);
        mTvName = findViewById(R.id.tv_name);
        mTvJob = findViewById(R.id.tv_job);
        mTvTitle = findViewById(R.id.tv_title);
        mIvRight = findViewById(R.id.iv_right);
        mFragmentArrayList = new ArrayList<>();
        mFragmentArrayList.add(new MonthFragment());
        mFragmentArrayList.add(new WeekFragment());
        mFragmentArrayList.add(new NewScheduleFragment());
        mFragmentArrayList.add(new ScheduleFragment());
        mFragmentArrayList.add(new RelatedMeFragment());
        mFragmentViewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), mFragmentArrayList));
        mFragmentViewPager.setOffscreenPageLimit(mFragmentArrayList.size());
        showNewScheduleFragment();
    }

    private void initListener() {
        mNavigationView.setNavigationItemSelectedListener(this);
        mIvOpenDrawerLayout.setOnClickListener(this);
        mCoordinatorLayout.setOnTouchListener(mOnTouchListener);
        mDrawerLayout.setDrawerListener(mDrawerListener);
        mRgMenu.setOnCheckedChangeListener(mOnCheckedChangeListener);
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

    RadioGroup.OnCheckedChangeListener mOnCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int id) {
            switch (id){
                case R.id.rd_month:
                    showMonthFragment();
                    break;
                case R.id.rd_week:
                    showWeekFragment();
                    break;
                case R.id.rd_new_schedule:
                    showNewScheduleFragment();
                    break;
                case R.id.rd_schedule:
                    showScheduleFragment();
                    break;
                case R.id.rd_related_me:
                    showRelatedMeFragment();
                    break;
                    default:
                        break;
            }
        }
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
        }else if (id == R.id.nav_other_share){  //他人分享
        }else if (id == R.id.nav_subordinate_log){  // 下属日志
        }else if (id == R.id.nav_relatedme_log){  // 与我相关的日志
        }else if (id == R.id.nav_setting){  //设置
            NavigationHelper.startActivity(MainActivity.this,SettingActivity.class,null,false);
        }else if (id == R.id.nav_exit){  //退出
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
        }
    }
    /**
     * 显示月
     */
    private void showMonthFragment() {
        mRdMonth.setSelected(true);
        mRdWeek.setSelected(false);
        mRdNewSchedule.setSelected(false);
        mRdSchedule.setSelected(false);
        mRdRelatedMe.setSelected(false);
        mFragmentViewPager.setCurrentItem(0);
    }

    /**
     * 显示周
     */
    private void showWeekFragment() {
        mRdMonth.setSelected(false);
        mRdWeek.setSelected(true);
        mRdNewSchedule.setSelected(false);
        mRdSchedule.setSelected(false);
        mRdRelatedMe.setSelected(false);
        mFragmentViewPager.setCurrentItem(1);
    }
    /**
     * 显示新建日程
     */
    private void showNewScheduleFragment() {
        mRdMonth.setSelected(false);
        mRdWeek.setSelected(false);
        mRdNewSchedule.setSelected(true);
        mRdSchedule.setSelected(false);
        mRdRelatedMe.setSelected(false);
        mFragmentViewPager.setCurrentItem(2);
    }
    /**
     * 显示日程
     */
    private void showScheduleFragment() {
        mRdMonth.setSelected(false);
        mRdWeek.setSelected(false);
        mRdNewSchedule.setSelected(false);
        mRdSchedule.setSelected(true);
        mRdRelatedMe.setSelected(false);
        mFragmentViewPager.setCurrentItem(3);
    }
    /**
     * 显示与我相关
     */
    private void showRelatedMeFragment() {
        mRdMonth.setSelected(false);
        mRdWeek.setSelected(false);
        mRdNewSchedule.setSelected(false);
        mRdSchedule.setSelected(false);
        mRdRelatedMe.setSelected(true);
        mFragmentViewPager.setCurrentItem(4);
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
