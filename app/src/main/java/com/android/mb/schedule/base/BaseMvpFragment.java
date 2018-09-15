package com.android.mb.schedule.base;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.android.mb.schedule.utils.Helper;
import com.android.mb.schedule.utils.ProgressDialogHelper;
import com.android.mb.schedule.utils.ToastUtils;


/**
 * <pre>
 *     author: cgy
 *     time  : 2017/7/20
 *     desc  :
 * </pre>
 *
 */

public abstract class BaseMvpFragment<P extends Presenter<V>,V extends BaseMvpView>
        extends BaseFragment implements BaseMvpView{


    protected P mPresenter;

//    private CustomProgressDialog mProgressDialog;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mPresenter = createPresenter();
        mPresenter.attachView((V)this);
        super.onViewCreated(view, savedInstanceState);
    }

    protected abstract P createPresenter();

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void back() {

    }

    @Override
    public void showToastMessage(String message) {
        ToastUtils.showShort(getActivity(),message);
    }

    @Override
    public void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }

    @Override
    public void showProgressDialog(String message) {
        ProgressDialogHelper.showProgressDialog(getActivity(), Helper.isEmpty(message)?"加载中...":message);
    }

    @Override
    public void dismissProgressDialog() {
        if (getActivity()==null || getActivity().isFinishing()) {
            return;
        }
        ProgressDialogHelper.dismissProgressDialog();
    }

    /**
     * add a keylistener for progress dialog
     */
    private DialogInterface.OnKeyListener onKeyListener = new DialogInterface.OnKeyListener() {
        @Override
        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
                dismissProgressDialog();
            }
            return false;
        }
    };

}
