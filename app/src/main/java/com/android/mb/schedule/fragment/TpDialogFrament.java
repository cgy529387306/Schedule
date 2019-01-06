package com.android.mb.schedule.fragment;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.mb.schedule.R;
import com.android.mb.schedule.utils.ToastHelper;
import com.bigkoo.pickerview.adapter.MinuteWheelAdapter;
import com.bigkoo.pickerview.adapter.NumericWheelAdapter;
import com.contrarywind.listener.OnItemSelectedListener;
import com.contrarywind.view.WheelView;

/**
 * Created by cgy on 19/1/6.
 */

public class TpDialogFrament extends DialogFragment implements View.OnClickListener{

    private TextView mTvBack,mTvFinish;
    private WheelView mHour,mMin;
    private String mTime;

    public static TpDialogFrament newInstance(String time) {
        TpDialogFrament f = new TpDialogFrament();
        Bundle args = new Bundle();
        args.putString("time", time);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(false);
        int style = DialogFragment.STYLE_NO_TITLE;
        int theme =android.R.style.Theme_Holo_Light_Dialog;
        setStyle(style,theme);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frg_time_picker, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initListener();
        initData();
    }

    private void initView(View view){
        mHour = view.findViewById(R.id.hour);
        mMin = view.findViewById(R.id.min);
        mTvBack = view.findViewById(R.id.tv_back);
        mTvFinish = view.findViewById(R.id.tv_finish);
    }

    private void initListener(){
        mTvBack.setOnClickListener(this);
        mTvFinish.setOnClickListener(this);
        mHour.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {

            }
        });
        mMin.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {

            }
        });
    }

    private void initData(){
        //时
        mHour.setTextColorCenter(getResources().getColor(R.color.base_blue));
        mHour.setTextColorOut(getResources().getColor(R.color.text_color));
        mHour.setTextSize(38);
        mHour.setAdapter(new NumericWheelAdapter(0, 23));
        mHour.setCurrentItem(0);
        mHour.setGravity(Gravity.CENTER);
        //分
        mMin.setTextColorCenter(getResources().getColor(R.color.base_blue));
        mMin.setTextColorOut(getResources().getColor(R.color.text_color));
        mMin.setTextSize(38);
        mMin.setAdapter(new MinuteWheelAdapter(0, 55));
        mMin.setCurrentItem(0);
        mMin.setGravity(Gravity.CENTER);

        Bundle bundle = getArguments();
        if (bundle != null) {
            mTime = bundle.getString("time");
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.tv_back){
            dismiss();
        }else if (id == R.id.tv_finish){
            String hour = (String) mHour.getAdapter().getItem(mHour.getCurrentItem());
            String min = (String) mMin.getAdapter().getItem(mMin.getCurrentItem());
            ToastHelper.showLongToast(hour+":"+min);
            dismiss();
        }
    }
}
