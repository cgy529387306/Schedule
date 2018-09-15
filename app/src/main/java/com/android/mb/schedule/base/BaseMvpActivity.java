package com.android.mb.schedule.base;

import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.os.Bundle;
import android.view.KeyEvent;

import com.android.mb.schedule.utils.Helper;
import com.android.mb.schedule.utils.ProgressDialogHelper;
import com.android.mb.schedule.utils.ToastUtils;


/**
 * @Description
 * @Created by cgy on 2017/7/19
 * @Version v1.0
 */

public abstract class BaseMvpActivity<P extends Presenter<V>,V extends BaseMvpView>
        extends BaseActivity implements BaseMvpView{

    protected P mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mPresenter = createPresenter();
        mPresenter.attachView((V)this);
        super.onCreate(savedInstanceState);
    }


    protected abstract P createPresenter();

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        mPresenter.detachView();
        ToastUtils.setView(null);
        super.onDestroy();
    }

    @Override
    public void back() {
        finish();
    }


    @Override
    public void showProgressDialog(String message) {
        ProgressDialogHelper.showProgressDialog(this, Helper.isEmpty(message)?"加载中...":message);
    }

    @Override
    public void dismissProgressDialog() {
        if (isFinishing()) {
            return;
        }
        ProgressDialogHelper.dismissProgressDialog();
    }

    /**
     * add a keylistener for progress dialog
     */
    private OnKeyListener onKeyListener = new OnKeyListener() {
        @Override
        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
                dismissProgressDialog();
            }
            return false;
        }
    };

}
