package com.android.mb.schedule.view;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.android.mb.schedule.R;
import com.android.mb.schedule.base.BaseMvpActivity;
import com.android.mb.schedule.presenter.HomePresenter;
import com.android.mb.schedule.utils.NavigationHelper;
import com.android.mb.schedule.view.interfaces.IHomeView;

/**
 * Created by Administrator on 2018\8\20 0020.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView mTvLogin;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mTvLogin = findViewById(R.id.tv_login);
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
