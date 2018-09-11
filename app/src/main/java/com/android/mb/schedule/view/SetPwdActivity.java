package com.android.mb.schedule.view;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.mb.schedule.R;
import com.android.mb.schedule.base.BaseMvpActivity;
import com.android.mb.schedule.constants.ProjectConstants;
import com.android.mb.schedule.presenter.SetPwdPresenter;
import com.android.mb.schedule.utils.Helper;
import com.android.mb.schedule.utils.NavigationHelper;
import com.android.mb.schedule.utils.PreferencesHelper;
import com.android.mb.schedule.utils.ProjectHelper;
import com.android.mb.schedule.view.interfaces.ISetPwdView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018\8\20 0020.
 */

public class SetPwdActivity extends BaseMvpActivity<SetPwdPresenter,ISetPwdView> implements ISetPwdView,View.OnClickListener{

    private TextView mTvSetPwd;
    private EditText mEdtOldPwd;
    private EditText mEdtNewPwd;
    private EditText mEdtConfirmPwd;

    @Override
    protected void loadIntent() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_set_pwd;
    }

    @Override
    protected void initTitle() {
        setTitleText("设置");
    }

    @Override
    protected void bindViews() {
        mTvSetPwd = findViewById(R.id.tv_set_pwd);
        mEdtOldPwd = findViewById(R.id.et_old_pwd);
        mEdtNewPwd = findViewById(R.id.et_new_pwd);
        mEdtConfirmPwd = findViewById(R.id.et_confirm_pwd);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
    }

    @Override
    protected void setListener() {
        mTvSetPwd.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.tv_set_pwd){
            ProjectHelper.disableViewDoubleClick(view);
            setPwd();
        }
    }

    private void setPwd(){
        String oldPwd = mEdtOldPwd.getText().toString().trim();
        String newPwd = mEdtNewPwd.getText().toString().trim();
        String confirmPwd = mEdtConfirmPwd.getText().toString().trim();
        if (Helper.isEmpty(oldPwd)){
            showToastMessage("请输入原账户密码");
            return;
        }
        if (Helper.isEmpty(newPwd)){
            showToastMessage("请输入新密码");
            return;
        }
        if (Helper.isEmpty(confirmPwd)){
            showToastMessage("请再次输入新密码");
            return;
        }
        if (!ProjectHelper.isPwdValid(oldPwd)){
            showToastMessage("原账户密码格式错误");
            return;
        }
        if (!ProjectHelper.isPwdValid(newPwd)){
            showToastMessage("密码格式错误，6-16个字符");
            return;
        }
        if (!newPwd.equals(confirmPwd)){
            showToastMessage("密码不一致，请重新确认输入");
            return;
        }
        Map<String,Object> requestMap = new HashMap<>();
        requestMap.put("pwd",oldPwd);
        requestMap.put("newpwd",newPwd);
        mPresenter.setPwd(requestMap);
    }

    @Override
    public void setSuccess() {
        showToastMessage("修改成功，请重新登录");
        PreferencesHelper.getInstance().putBoolean(ProjectConstants.KEY_IS_LOGIN,false);
        PreferencesHelper.getInstance().putLong(ProjectConstants.KEY_TOKEN_ID,0);
        PreferencesHelper.getInstance().putString(ProjectConstants.KEY_TOKEN_ID,null);
        NavigationHelper.startActivity(SetPwdActivity.this, LoginActivity.class, null, true);
    }

    @Override
    protected SetPwdPresenter createPresenter() {
        return new SetPwdPresenter();
    }
}
