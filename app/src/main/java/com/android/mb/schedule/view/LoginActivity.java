package com.android.mb.schedule.view;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.mb.schedule.R;
import com.android.mb.schedule.base.BaseActivity;
import com.android.mb.schedule.utils.NavigationHelper;

/**
 * Created by Administrator on 2018\8\20 0020.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener{

    private TextView mTvLogin;


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
            NavigationHelper.startActivity(LoginActivity.this,MainActivity.class,null,true);
        }
    }

}
