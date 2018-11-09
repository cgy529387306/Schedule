package com.android.mb.schedule.view;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.mb.schedule.R;
import com.android.mb.schedule.base.BaseActivity;
import com.android.mb.schedule.base.BaseMvpActivity;
import com.android.mb.schedule.constants.ProjectConstants;
import com.android.mb.schedule.entitys.CurrentUser;
import com.android.mb.schedule.entitys.LoginData;
import com.android.mb.schedule.presenter.LoginPresenter;
import com.android.mb.schedule.rxbus.Events;
import com.android.mb.schedule.utils.AppHelper;
import com.android.mb.schedule.utils.ImageUtils;
import com.android.mb.schedule.utils.NavigationHelper;
import com.android.mb.schedule.utils.NetworkHelper;
import com.android.mb.schedule.utils.PreferencesHelper;
import com.android.mb.schedule.utils.ProgressDialogHelper;
import com.android.mb.schedule.utils.ProjectHelper;
import com.android.mb.schedule.view.interfaces.ILoginView;
import com.android.mb.schedule.widget.CircleImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;
import rx.functions.Action1;

/**
 *  设置
 * Created by cgy on 2018\8\20 0020.
 */

public class SettingActivity extends BaseMvpActivity<LoginPresenter,ILoginView> implements ILoginView, View.OnClickListener{

    private CircleImageView mIvHead;
    private TextView mTvName;  // 名字
    private TextView mTvJob;  //职位
    private TextView mTvVersion;
    private TextView mBtnSetPwd;
    private ImageView mIvRing;
    private ImageView mIvVibrate;
    private boolean isRemind = false;

    @Override
    protected void loadIntent() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initTitle() {
        setTitleText("设置");
    }

    @Override
    protected void bindViews() {
        mIvHead = findViewById(R.id.iv_head);
        mTvName = findViewById(R.id.tv_name);
        mTvJob = findViewById(R.id.tv_job);
        mTvVersion = findViewById(R.id.tv_version);
        mBtnSetPwd = findViewById(R.id.tv_set_pwd);
        mIvRing = findViewById(R.id.iv_ring);
        mIvVibrate = findViewById(R.id.iv_vibrate);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        initData();
        initUserInfo();
        regiestEvent(ProjectConstants.EVENT_UPDATE_USER_INFO, new Action1<Events<?>>() {
            @Override
            public void call(Events<?> events) {
                initUserInfo();
            }
        });
    }

    @Override
    protected void setListener() {
        mBtnSetPwd.setOnClickListener(this);
        mIvRing.setOnClickListener(this);
        mIvVibrate.setOnClickListener(this);
        mTvVersion.setOnClickListener(this);
        findViewById(R.id.lly_head).setOnClickListener(this);
        findViewById(R.id.tv_change).setOnClickListener(this);
        findViewById(R.id.lly_download).setOnClickListener(this);
    }

    private void initData(){
        mTvVersion.setText("版本"+ AppHelper.getCurrentVersionName());
        isRemind = PreferencesHelper.getInstance().getBoolean(ProjectConstants.KEY_IS_VIBRATE,true);
        mIvVibrate.setImageResource(isRemind?R.mipmap.ic_vibrate_open:R.mipmap.ic_vibrate_close);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.tv_set_pwd){
            NavigationHelper.startActivity(SettingActivity.this,SetPwdActivity.class,null,false);
        }else if (id == R.id.iv_ring){
            NavigationHelper.startActivity(SettingActivity.this,RingActivity.class,null,false);
        }else if (id == R.id.iv_vibrate){
            isRemind = !isRemind;
            mIvVibrate.setImageResource(isRemind?R.mipmap.ic_vibrate_open:R.mipmap.ic_vibrate_close);
            PreferencesHelper.getInstance().putBoolean(ProjectConstants.KEY_IS_VIBRATE,isRemind);
        }else if (id == R.id.lly_head){
            NavigationHelper.startActivity(SettingActivity.this,PersonalSettingActivity.class,null,false);
        }else if (id == R.id.tv_change){
            doWxLogin();
        }else if (id == R.id.lly_download){
            if (NetworkHelper.isNetworkAvailable(mContext)){
                long lastUpdateTime = PreferencesHelper.getInstance().getLong(ProjectConstants.KEY_LAST_SYNC+ CurrentUser.getInstance().getId(),0);
                if (lastUpdateTime==0){
                    showToastMessage("正在后台同步中，请稍等...");
                }else {
                    ProgressDialogHelper.showProgressDialog(SettingActivity.this,"正在下载云端数据，请稍等...");
                    ProjectHelper.syncSchedule(mContext,true);
                }
            }else{
                showToastMessage("当前网络已断开，无法同步");
            }
        }else if (id == R.id.tv_version){
            
        }
    }

    private void initUserInfo(){
        if (CurrentUser.getInstance()!=null){
            mTvName.setText(ProjectHelper.getCommonText(CurrentUser.getInstance().getNickname()));
            mTvJob.setText(ProjectHelper.getCommonText(CurrentUser.getInstance().getOffice_name()));
            ImageUtils.displayAvatar(this,CurrentUser.getInstance().getAvatar(),mIvHead);
        }
    }

    @Override
    public void loginSuccess(LoginData result) {

    }

    @Override
    public void bindSuccess(Object result) {
        showToastMessage("绑定成功");
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    private void doWxLogin(){
        Platform platform = ShareSDK.getPlatform(Wechat.NAME);
        platform.removeAccount(true);
        platform.setPlatformActionListener(new PlatformActionListener() {

            @Override
            public void onError(Platform arg0, int arg1, Throwable arg2) {
                arg2.printStackTrace();
            }

            @Override
            public void onComplete(Platform arg0, int arg1, HashMap<String, Object> arg2) {
                getPlatInfo(arg0);
            }

            @Override
            public void onCancel(Platform arg0, int arg1) {

            }
        });
        platform.authorize();
    }


    private void getPlatInfo(Platform platform){
        if (platform!=null && platform.getDb()!=null && !TextUtils.isEmpty(platform.getDb().exportData())){
            String userInfo = platform.getDb().exportData();
            try {
                JSONObject jsonObject = new JSONObject(userInfo);
                wxLoginComplete(jsonObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private void wxLoginComplete(JSONObject jsonObject){
        try {
            String nickname = jsonObject.getString("nickname");
            String unionid = jsonObject.getString("unionid");
            String headingurl = jsonObject.getString("icon");
            String openid = jsonObject.getString("openid");
            Map<String,Object> requestMap = new HashMap<>();
            requestMap.put("nickname",nickname);
            requestMap.put("unionid",unionid);
            requestMap.put("headimgurl",headingurl);
            requestMap.put("openid",openid);
            mPresenter.bindWx(requestMap);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
