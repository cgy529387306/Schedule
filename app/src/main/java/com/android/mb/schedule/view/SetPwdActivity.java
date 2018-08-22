package com.android.mb.schedule.view;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.mb.schedule.R;
import com.android.mb.schedule.base.BaseActivity;
import com.android.mb.schedule.utils.NavigationHelper;
import com.android.mb.schedule.widget.CircleImageView;

/**
 * Created by Administrator on 2018\8\20 0020.
 */

public class SetPwdActivity extends BaseActivity implements View.OnClickListener{

    private TextView mBtnSetPwd;
    private EditText mEdtOldPwd;
    private EditText mEdtNewPwd;
    private EditText mEdtConfirmPwd;

    @Override
    protected void loadIntent() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setpwd;
    }

    @Override
    protected void initTitle() {
        setTitleText("修改密码");
    }

    @Override
    protected void bindViews() {
        mBtnSetPwd = findViewById(R.id.tv_setpwd);
        mEdtOldPwd = findViewById(R.id.edt_oldpwd);
        mEdtNewPwd = findViewById(R.id.edt_newpwd);
        mEdtConfirmPwd = findViewById(R.id.edt_confirmpwd);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
    }

    @Override
    protected void setListener() {
        mBtnSetPwd.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.tv_setpwd){
        }
    }
}
