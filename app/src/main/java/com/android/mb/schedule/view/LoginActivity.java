package com.android.mb.schedule.view;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.mb.schedule.R;
import com.android.mb.schedule.base.BaseActivity;
import com.android.mb.schedule.base.BaseMvpActivity;
import com.android.mb.schedule.presenter.LoginPresenter;
import com.android.mb.schedule.utils.Helper;
import com.android.mb.schedule.utils.NavigationHelper;
import com.android.mb.schedule.view.interfaces.ILoginView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018\8\20 0020.
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
            String account = mEtAccount.getText().toString().trim();
            String pwd = mEtPwd.getText().toString().trim();
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
            mPresenter.userLogin(requestMap);

//            NavigationHelper.startActivity(LoginActivity.this,MainActivity.class,null,true);
        }
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    @Override
    public void loginSuccess() {

    }
}
