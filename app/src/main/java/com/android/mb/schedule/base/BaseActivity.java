package com.android.mb.schedule.base;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.mb.schedule.R;
import com.android.mb.schedule.rxbus.Events;
import com.android.mb.schedule.rxbus.RxBus;
import com.android.mb.schedule.service.SyncService;
import com.android.mb.schedule.utils.AppHelper;
import com.android.mb.schedule.utils.KeyBoardUtils;
import com.android.mb.schedule.utils.NetworkHelper;
import com.android.mb.schedule.utils.ProjectHelper;
import com.android.mb.schedule.utils.StatusBarUtil;
import com.android.mb.schedule.utils.ToastUtils;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


/**
 * <pre>
 *     author: cgy
 *     time  : 2017/7/21
 *     desc  : BaseFragment
 * </pre>
 */
public abstract class BaseActivity extends AppCompatActivity{

    protected Context mContext;
    public static final String BROADCAST_KEY_CONNECTIVITY_CHANGE = "android.net.conn.CONNECTIVITY_CHANGE";
    /**
     * 管理Rxjava。
     */
    private CompositeSubscription mCompositeSubscription;

    private LinearLayout mLlRoot;

    private ImageView mIvBack;

    private TextView mTvTitle;

    private TextView mTvAction;

    private ImageView mIvAction;

    private FrameLayout mActionBarLayout;

    // 网络状态
    private boolean mIsNetworkAvailable = false;
    private BroadcastReceiver mNetWorkStateChangeReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        mIsNetworkAvailable = NetworkHelper.isNetworkAvailable(this);
        registerNetWorkConnectivityChangeReceiver();
        initView(savedInstanceState);
        initStatusBar();
    }

    private void initStatusBar(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            StatusBarUtil.StatusBarIconManager.MIUI(this, StatusBarUtil.StatusBarIconManager.TYPE.BLACK);
            StatusBarUtil.StatusBarIconManager.Flyme(this, StatusBarUtil.StatusBarIconManager.TYPE.BLACK);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mNetWorkStateChangeReceiver);
        RxBus.getInstance().unSubscribe(this);
        onUnsubscribe();
    }

    public void showToastMessage(String message) {
        ToastUtils.showShort(this,message);
    }

    /**
     * 初始化界面
     */
    protected void initView(Bundle savedInstanceState) {
        loadIntent();
        initView();
        initTitle();
        bindViews();
        processLogic(savedInstanceState);
        setListener();
    }

    private void initView(){
        setContentView(R.layout.common_actionbar_back);
        mLlRoot = findViewById(R.id.ll_root);
        mIvBack = findViewById(R.id.iv_back);
        mTvTitle = findViewById(R.id.tv_title);
        mTvAction = findViewById(R.id.tv_action);
        mIvAction = findViewById(R.id.iv_action);
        mActionBarLayout = findViewById(R.id.fl_actionbar);
        View view = getLayoutInflater().inflate(getLayoutId(), null);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        mLlRoot.addView(view,lp);

        mIvBack.setOnClickListener(mTitleBarOnclick);
        mTvAction.setOnClickListener(mTitleBarOnclick);
        mIvAction.setOnClickListener(mTitleBarOnclick);
    }

    /**
     * 设置右边文字
     * @param c
     */
    public void setRightText(CharSequence c) {
        if (mTvAction != null && mTvAction.getVisibility()==View.GONE)
            mTvAction.setVisibility(View.VISIBLE);
        mTvAction.setText(c);
    }

    /**
     * 设置中间标题文字
     * @param c
     */
    public void setTitleText(CharSequence c) {
        if (mTvTitle != null && mTvTitle.getVisibility()==View.GONE)
            mTvTitle.setVisibility(View.VISIBLE);
        mTvTitle.setText(c);
    }

    /**
     * 设置中间标题文字
     * @param resId
     */
    public void setRightImage(int resId) {
        if (mIvAction != null && mIvAction.getVisibility()==View.GONE)
            mIvAction.setVisibility(View.VISIBLE);
        mIvAction.setImageResource(resId);
    }

    public void hideActionBar(){
        if (mActionBarLayout!=null && mActionBarLayout.getVisibility()==View.VISIBLE)
            mActionBarLayout.setVisibility(View.GONE);
    }


    private View.OnClickListener mTitleBarOnclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.iv_back){
                onLeftBack();
            }else if (id == R.id.iv_action){
                ProjectHelper.disableViewDoubleClick(v);
                onRightAction();
            }else if (id == R.id.tv_action){
                ProjectHelper.disableViewDoubleClick(v);
                onRightAction();
            }
        }
    };

    protected void onLeftBack(){
        if (mIvBack!=null && AppHelper.isShowKeyboard(mIvBack)){
            AppHelper.hideSoftInputFromWindow(mIvBack);
        }else{
            finish();
        }
    }

    protected void onRightAction(){
    }

    /**
     *
     * function: 网络变化接收
     *
     *
     * @ author:cgy 2014-12-26 上午10:12:37
     */
    private void registerNetWorkConnectivityChangeReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(BROADCAST_KEY_CONNECTIVITY_CHANGE);
        filter.setPriority(1000);

        mNetWorkStateChangeReceiver = new BroadcastReceiver() {


            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if(null != action && BROADCAST_KEY_CONNECTIVITY_CHANGE.equals(action)){
                    boolean temp = NetworkHelper.isNetworkAvailable(BaseActivity.this);
                    if(temp != mIsNetworkAvailable) {
                        mIsNetworkAvailable = temp;
                        if (mIsNetworkAvailable){
                            startService(new Intent(mContext, SyncService.class));
                        }else{
                            showToastMessage("网络已断开");
                        }
                    }
                    return;
                }
            }
        };

        registerReceiver(mNetWorkStateChangeReceiver, filter);
    }


    /**
     * 界面跳转
     *
     * @param tarActivity
     */
    protected void intent2Activity(Class<? extends Activity> tarActivity) {
        Intent intent = new Intent(mContext, tarActivity);
        startActivity(intent);
    }

    /**
     * 获取控件
     *
     * @param id  控件的id
     * @param <E>
     * @return
     */
    protected <E extends View> E get(int id) {
        return (E) findViewById(id);
    }

    /**
     * 跳转数据
     */
    protected abstract void loadIntent();

    /**
     * 加载布局
     */
    protected abstract int getLayoutId();

    /**
     * 初始化标题栏
     */
    protected abstract void initTitle();

    /**
     * find控件
     */
    protected abstract void bindViews();

    /**
     * 处理数据
     */
    protected abstract void processLogic(Bundle savedInstanceState);

    /**
     * 设置监听
     */
    protected abstract void setListener();

    /**
     * 注册事件.
     * @param event
     * @param onNext
     */
    public void regiestEvent(int event,Action1<Events<?>> onNext){
        RxBus.init()
                .setEvent(event)
                .onNext(onNext)
                .create(this);
    }

    /**
     * 发送事件.
     * @param event
     * @param o
     */
    public void sendMsg(int event,Object o){
        RxBus.getInstance().send(event,o);
    }

    /******************************************RxJava 统一管理操作 *************************************************/
    protected <T> void toSubscribe(Observable<T> o, Subscriber<T> s) {

        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }

        mCompositeSubscription.add(o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s));
    }

    //RXjava取消注册，以避免内存泄露
    protected void onUnsubscribe() {

        //CompositeSubscription 一旦解绑之后该CompositeSubscription就不能继续使用了。
        if (mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
            mCompositeSubscription.unsubscribe();

            //解绑完不置null第二次绑定会有问题。
            mCompositeSubscription = null;
        }
    }


    /********************************** 隐藏软件操作 start*********************************************/


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            if (isTouchView(filterViewByIds(), ev)) return super.dispatchTouchEvent(ev);
            if (hideSoftByEditViewIds() == null || hideSoftByEditViewIds().length == 0)
                return super.dispatchTouchEvent(ev);
            View v = getCurrentFocus();
            if (isFocusEditText(v, hideSoftByEditViewIds())) {
                if (isTouchView(hideSoftByEditViewIds(), ev))
                    return super.dispatchTouchEvent(ev);
                //隐藏键盘
                KeyBoardUtils.hideInputForce(this);
                clearViewFocus(v, hideSoftByEditViewIds());

            }
        }
        return super.dispatchTouchEvent(ev);

    }

    /**
     * 传入要过滤的View,
     * 过滤之后点击将不会有隐藏软键盘的操作.
     * @return id 的数组
     */
    public View[] filterViewByIds() {
        return null;
    }

    /**
     * 传入EditText的Id
     * 没有传入的EditText不做处理.
     * 子类需要复写
     *
     * @return id 数组
     */
    public int[] hideSoftByEditViewIds() {
        return null;
    }

    /**
     * 清除editText的焦点
     * @param v 焦点所在View
     * @param ids 输入框
     */
    public void clearViewFocus(View v, int... ids) {
        if (null != v && null != ids && ids.length > 0) {
            for (int id : ids) {
                if (v.getId() == id) {
                    v.clearFocus();
                    break;
                }
            }
        }
    }


    /**
     * 是否触摸在指定view上面,对某个控件过滤
     * @param views
     * @param ev
     * @return
     */
    private boolean isTouchView(View[] views, MotionEvent ev) {
        if (views == null || views.length == 0) return false;
        int[] location = new int[2];
        for (View view : views) {
            view.getLocationOnScreen(location);
            int x = location[0];
            int y = location[1];
            if (ev.getX() > x && ev.getX() < (x + view.getWidth())
                    && ev.getY() > y && ev.getY() < (y + view.getHeight())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否触摸在指定view上面,对某个控件过滤
     * @param ids
     * @param ev
     * @return
     */
    private boolean isTouchView(int[] ids, MotionEvent ev) {
        int[] location = new int[2];
        for (int id : ids) {
            View view = findViewById(id);
            if (view == null) continue;
            view.getLocationOnScreen(location);
            int x = location[0];
            int y = location[1];
            if (ev.getX() > x && ev.getX() < (x + view.getWidth())
                    && ev.getY() > y && ev.getY() < (y + view.getHeight())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 隐藏键盘
     * @param v 焦点所在View
     * @param ids 输入框
     * @return true代表焦点在edit上
     */
    private boolean isFocusEditText(View v, int... ids) {
        if (v instanceof EditText) {
            EditText tmp_et = (EditText) v;
            for (int id : ids) {
                if (tmp_et.getId() == id) {
                    return true;
                }
            }
        }
        return false;
    }

    /********************************** 隐藏软件操作 end *********************************************/

}
