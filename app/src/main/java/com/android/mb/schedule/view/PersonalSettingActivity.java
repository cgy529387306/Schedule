package com.android.mb.schedule.view;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.mb.schedule.R;
import com.android.mb.schedule.app.MBApplication;
import com.android.mb.schedule.base.BaseActivity;
import com.android.mb.schedule.base.BaseMvpActivity;
import com.android.mb.schedule.presenter.SetPwdPresenter;
import com.android.mb.schedule.presenter.UserPresenter;
import com.android.mb.schedule.view.interfaces.ISetPwdView;
import com.android.mb.schedule.view.interfaces.IUserView;
import com.android.mb.schedule.widget.BottomMenuDialog;
import com.android.mb.schedule.widget.CircleImageView;

import java.io.File;

/**
 * Created by Administrator on 2018\8\20 0020.
 */

public class PersonalSettingActivity extends BaseMvpActivity<UserPresenter,IUserView> implements IUserView, View.OnClickListener{

    private CircleImageView mIvHead;
    private TextView mTvName;  // 名字
    private TextView mTvJob;  //职位
    private BottomMenuDialog pickDialog;
    private String mTempFilePath = getBaseCachePath()
            .concat(String.valueOf(System.currentTimeMillis())).concat(".png");

    @Override
    protected void loadIntent() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_personal_setting;
    }

    @Override
    protected void initTitle() {
        setTitleText("个人设置");
    }

    @Override
    protected void bindViews() {
        mIvHead = findViewById(R.id.iv_head);
        mTvName = findViewById(R.id.tv_name);
        mTvJob = findViewById(R.id.tv_job);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        mPresenter.getUserInfo();
    }

    @Override
    protected void setListener() {
        mIvHead.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.iv_head){
            pickPhoto();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case 1:
                // 相册
                cropPickedImage(data.getData());
                break;
            case 2:
                // 拍照
                File file = new File(mTempFilePath);
                if (!file.exists()) {
                    return;
                }
                cropPickedImage(Uri.parse("file://".concat(mTempFilePath)));
                break;
            default:
                break;
        }
    }

    private void pickPhoto() {
        if (pickDialog == null) {
            pickDialog = new BottomMenuDialog.Builder(PersonalSettingActivity.this)
                    .addMenu("拍照", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            pickImageFromCamera();
                            pickDialog.dismiss();
                        }
                    }).addMenu("我的相册选择", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            pickImageFromGallery();
                            pickDialog.dismiss();
                        }
                    }).create();
        }
        pickDialog.show();
    }

    /**
     * 相册图片选取
     */
    private void pickImageFromGallery() {
        try {
            Intent intentFromGallery = new Intent();
            // 设置文件类型
            intentFromGallery.setType("image/*");
            intentFromGallery.setAction(Intent.ACTION_PICK);
            startActivityForResult(intentFromGallery, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 拍照获取图片
     */
    private void pickImageFromCamera() {
        try {
            Intent intent = new Intent();
            intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            // 图片缓存的地址
            mTempFilePath = getBaseCachePath()
                    .concat(String.valueOf(System.currentTimeMillis()))
                    .concat(".png");
            File file = new File(mTempFilePath);
            Uri uri = Uri.fromFile(file);
            // 设置图片的存放地址
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            startActivityForResult(intent, 2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 裁剪选取的图片
     *
     * @param uri
     */
    private void cropPickedImage(Uri uri) {
        try {
            Intent intent = new Intent("com.android.camera.action.CROP");
            intent.setDataAndType(uri, "image/*");
            intent.putExtra("crop", "true");// 可裁剪
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            intent.putExtra("outputX", 200);
            intent.putExtra("outputY", 200);
            intent.putExtra("scale", true);

            // 图片缓存的地址
            mTempFilePath = getBaseCachePath()
                    .concat(String.valueOf(System.currentTimeMillis()))
                    .concat(".png");
            File file = new File(mTempFilePath);
            Uri uriCache = Uri.fromFile(file);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uriCache);
            intent.putExtra("outputFormat", "JPEG");// 图片格式
            intent.putExtra("noFaceDetection", true);// 取消人脸识别
            intent.putExtra("return-data", false);
            startActivityForResult(intent, 3);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }
    /**
     * <p> 取得基本的缓存路径(无SD卡则使用RAM)
     *
     * @return 类似这样的路径 /mnt/sdcard/Android/data/demo.android/cache/ 或者 /data/data/demo.android/cache/
     */
    public static String getBaseCachePath() {
        String result = null;
        // 有些机型外部存储不按套路出牌的
        try {
            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                    && Environment.getExternalStorageDirectory().canWrite()) {
                result = MBApplication.getInstance().getExternalCacheDir().getAbsolutePath().concat(File.separator);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        if (TextUtils.isEmpty(result)) {
            result = MBApplication.getInstance().getCacheDir().getPath().concat(File.separator);
        }
        return result;
    }

    @Override
    protected UserPresenter createPresenter() {
        return new UserPresenter();
    }

    @Override
    public void getUserInfoSuccess() {

    }

    @Override
    public void setSuccess() {

    }
}
