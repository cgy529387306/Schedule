package com.android.mb.schedule.view;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.mb.schedule.R;
import com.android.mb.schedule.base.BaseMvpActivity;
import com.android.mb.schedule.constants.ProjectConstants;
import com.android.mb.schedule.entitys.CurrentUser;
import com.android.mb.schedule.entitys.LoginData;
import com.android.mb.schedule.presenter.LoginPresenter;
import com.android.mb.schedule.utils.AppHelper;
import com.android.mb.schedule.utils.Helper;
import com.android.mb.schedule.utils.NavigationHelper;
import com.android.mb.schedule.utils.PreferencesHelper;
import com.android.mb.schedule.utils.ProjectHelper;
import com.android.mb.schedule.utils.ToastHelper;
import com.android.mb.schedule.view.interfaces.ILoginView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;

/**
 * 登录
 * Created by cgy on 2018\8\20 0020.
 */

public class LoginActivity extends BaseMvpActivity<LoginPresenter,ILoginView> implements ILoginView, View.OnClickListener{

    private TextView mTvLogin;
    private EditText mEtAccount;
    private EditText mEtPwd;

    @Override
    protected void loadIntent() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initTitle() {
        hideActionBar();
    }

    @Override
    protected void bindViews() {
        mTvLogin = findViewById(R.id.tv_login);
        mEtAccount = findViewById(R.id.et_account);
        mEtPwd = findViewById(R.id.et_pwd);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @Override
    protected void setListener() {
        mTvLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_login){
            ProjectHelper.disableViewDoubleClick(v);
            doLogin();
        }
    }

    private void doLogin(){
        String account = mEtAccount.getText().toString().trim();
        String pwd = mEtPwd.getText().toString().trim();
        String rid = PreferencesHelper.getInstance().getString(ProjectConstants.KEY_REGISTRATION_ID);
        if (Helper.isEmpty(rid)){
            rid = JPushInterface.getRegistrationID(getApplicationContext());
        }
        if (Helper.isEmpty(account)){
            showToastMessage("请输入用户名");
            return;
        }
        if (Helper.isEmpty(pwd)){
            showToastMessage("请输入密码");
            return;
        }
        Map<String,Object> requestMap = new HashMap<>();
        requestMap.put("username",account);
        requestMap.put("password",pwd);
        requestMap.put("registerid",rid);
        mPresenter.userLogin(requestMap);
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    @Override
    public void loginSuccess(LoginData result) {
        if (result!=null && result.getUserinfo()!=null){
            AppHelper.hideSoftInputFromWindow(mEtAccount);
            CurrentUser.getInstance().login(result.getUserinfo(),true);
            if (result.getUserinfo().getIs_bind_wx()==0){
                doWxLogin();
            }else{
                NavigationHelper.startActivity(LoginActivity.this,MainActivity.class,null,true);
            }
        }
    }

    @Override
    public void bindSuccess(Object result) {
        if (result!=null){
            showToastMessage("登录成功");
            NavigationHelper.startActivity(LoginActivity.this,MainActivity.class,null,true);
        }
    }

    private static final long DOUBLE_CLICK_INTERVAL = 2000;
    private long mLastClickTimeMills = 0;

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - mLastClickTimeMills > DOUBLE_CLICK_INTERVAL) {
            ToastHelper.showToast("再按一次返回退出");
            mLastClickTimeMills = System.currentTimeMillis();
            return;
        }
        finish();
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
