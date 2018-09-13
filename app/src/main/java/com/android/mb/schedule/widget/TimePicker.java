package com.android.mb.schedule.widget;

import java.util.ArrayList;
import java.util.Calendar;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.android.mb.schedule.widget.wheelview.DateObject;
import com.android.mb.schedule.widget.wheelview.OnWheelChangedListener;
import com.android.mb.schedule.widget.wheelview.StringWheelAdapter;
import com.android.mb.schedule.widget.wheelview.WheelView;

/**
 * 自定义的时间选择器
 * @author sxzhang
 *
 */
public class TimePicker extends LinearLayout{
	
	private Calendar calendar = Calendar.getInstance(); 
	private WheelView hours, minute; //Wheel picker
	private OnChangeListener onChangeListener; //onChangeListener
	private ArrayList<DateObject> hourList,minuteList;
	private DateObject dateObject;		//时间数据对象
	//Constructors
	public TimePicker(Context context) {
		super(context);
		init(context);
	}
	
	public TimePicker(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	/**
	 * 初始化
	 * @param context
	 */
	private void init(Context context){
		hourList = new ArrayList<DateObject>();
		minuteList = new ArrayList<DateObject>();
		for (int i = 0; i < 24; i++) {
			dateObject = new DateObject(i,-1,true);
			hourList.add(dateObject);
		}
		for (int j = 0; j < 12; j++) {
			dateObject = new DateObject(-1,(5*j),false);
			minuteList.add(dateObject);
		}
		//小时选择器
		hours = new WheelView(context);
		LayoutParams hourParams = new LayoutParams(80,LayoutParams.WRAP_CONTENT);
		hourParams.setMargins(0, 0, 0, 0);
		hours.setLayoutParams(hourParams);
		hours.setAdapter(new StringWheelAdapter(hourList, 24));
		hours.setVisibleItems(7);
		hours.setCyclic(true);
		hours.addChangingListener(onHoursChangedListener);		
		addView(hours);		
	
		//分钟选择器
		minute = new WheelView(context);
		minute.setLayoutParams(new LayoutParams(80,LayoutParams.WRAP_CONTENT));
		minute.setAdapter(new StringWheelAdapter(minuteList,60));
		minute.setVisibleItems(7);
		minute.setCyclic(true);
		minute.addChangingListener(onMinChangedListener);
		addView(minute);
	}
	
	
	
	//listeners
	private OnWheelChangedListener onHoursChangedListener = new OnWheelChangedListener(){
		@Override
		public void onChanged(WheelView hours, int oldValue, int newValue) {
			calendar.set(Calendar.HOUR_OF_DAY, newValue);
			change();
		}
	};
	private OnWheelChangedListener onMinChangedListener = new OnWheelChangedListener(){
		@Override
		public void onChanged(WheelView minute, int oldValue, int newValue) {
			calendar.set(Calendar.MINUTE, newValue);
			change();
		}
	};
	
	/**
	 * 滑动改变监听器回调的接口
	 */
	public interface OnChangeListener {
		void onChange(int hour, int minute);
	}
	
	/**
	 * 设置滑动改变监听器
	 * @param onChangeListener
	 */
	public void setOnChangeListener(OnChangeListener onChangeListener){
		this.onChangeListener = onChangeListener;
	}
	
	/**
	 * 滑动最终调用的方法
	 */
	private void change(){
		if(onChangeListener!=null){
			onChangeListener.onChange(getHourOfDay(), getMinute());
		}
	}
	
	
	/**
	 * 获取小时
	 * @return
	 */
	public int getHourOfDay(){
		return hourList.get(hours.getCurrentItem()).getHour();
	}
	
	/**
	 * 获取分钟
	 * @return
	 */
	public int getMinute(){
		return minuteList.get(minute.getCurrentItem()).getMinute();
	}


		
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	public void setCurrentTime(Calendar calendar){
		int hour = calendar.get(Calendar.HOUR_OF_DAY)+1;
		int realHour = hour % 24;
		if (hourList.size()>realHour && hours!=null){
			hours.setCurrentItem(realHour);
		}
	}
}
