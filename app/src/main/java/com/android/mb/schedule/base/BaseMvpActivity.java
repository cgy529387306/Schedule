package com.android.mb.schedule.base;

import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.os.Bundle;
import android.view.KeyEvent;

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
    public void showToastMessage(String message) {
        ToastUtils.showShort(this,message);
    }

    @Override
    public void showProgressDialog() {
//        if(mProgressDialog == null) {
//            mProgressDialog = new CustomProgressDialog(this);
//            mProgressDialog.setCancelable(false);
//        }
//
//        mProgressDialog.show();
//        //为进度条添加一个事件.
//        mProgressDialog.setOnKeyListener(onKeyListener);
    }

    @Override
    public void dismissProgressDialog() {
        if (isFinishing()) {
            return;
        }
//        if (null != mProgressDialog && mProgressDialog.isShowing()) {
//            mProgressDialog.dismiss();
//        }
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
