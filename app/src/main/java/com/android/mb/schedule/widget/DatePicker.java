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
 * 自定义的日期选择器
 * @author sxzhang
 *
 */
public class DatePicker extends LinearLayout {

	private Calendar calendar = Calendar.getInstance();
	private WheelView newDays;
	private ArrayList<DateObject> dateList ;
	private OnChangeListener onChangeListener; //onChangeListener
	private final int MARGIN_RIGHT = 20;
	private DateObject dateObject;		//日期数据对象
	private int day1;
	private int year,month,day;
	//Constructors
	public DatePicker(Context context) {
		super(context);
		init(context);
	}

	public DatePicker(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	/**
	 * 初始化
	 * @param context
	 */
	private void init(Context context){
		year = calendar.get(Calendar.YEAR);
		month = calendar.get(Calendar.MONTH) + 1;
		day = calendar.get(Calendar.DAY_OF_MONTH);
		int week = calendar.get(Calendar.DAY_OF_WEEK);
		dateList = new ArrayList<DateObject>();
		dateObject = new DateObject(year, month, day, week);
		dateList.add(dateObject);
		for (int i = 0; i <150; i++) {
			if ( day >= calDays(year,month)){
				month++;
				if (month > 12){
					year++;
					month = 1;
				}
				day = day - calDays(year,month);
				if(day < 2){
					day = 1;
				}
			}else {
				day = day + 1;
			}
			dateObject = new DateObject(year, month, day, week + 1 + (i%7));
			dateList.add(dateObject);
		}
		newDays = new WheelView(context);
		LayoutParams newDays_param = new LayoutParams(300,LayoutParams.WRAP_CONTENT);
		newDays_param.setMargins(0, 0, MARGIN_RIGHT, 0);
		newDays.setLayoutParams(newDays_param);
		newDays.setAdapter(new StringWheelAdapter(dateList, 7));
		newDays.setVisibleItems(7);
		newDays.setCyclic(false);
		newDays.addChangingListener(onDaysChangedListener);
		addView(newDays);
	}

	/**
	 * 滑动改变监听器
	 */
	private OnWheelChangedListener onDaysChangedListener = new OnWheelChangedListener(){
		@Override
		public void onChanged(WheelView mins, int oldValue, int newValue) {
			calendar.set(Calendar.DAY_OF_MONTH, newValue + 1);
			change();
		}
	};

	/**
	 * 滑动改变监听器回调的接口
	 */
	public interface OnChangeListener {
		void onChange(int year, int month, int day, int day_of_week);
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
			onChangeListener.onChange(
					dateList.get(newDays.getCurrentItem()).getYear(),
					dateList.get(newDays.getCurrentItem()).getMonth(),
					dateList.get(newDays.getCurrentItem()).getDay(),
					dateList.get(newDays.getCurrentItem()).getWeek());
		}
	}


	/**
	 * 根据day_of_week得到汉字星期
	 * @return
	 */
	public static String getDayOfWeekCN(int day_of_week){
		String result = null;
		switch(day_of_week){
			case 1:
				result = "周日";
				break;
			case 2:
				result = "周一";
				break;
			case 3:
				result = "周二";
				break;
			case 4:
				result = "周三";
				break;
			case 5:
				result = "周四";
				break;
			case 6:
				result = "周五";
				break;
			case 7:
				result = "周六";
				break;
			default:
				break;
		}
		return result;
	}


	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	/**
	 * 计算每月多少天
	 *
	 * @param month1
	 * @param year1
	 */
	public int calDays(int year1, int month1) {
		boolean learYear = false;
		if (year % 4 == 0 && year % 100 != 0) {
			learYear = true;
		} else {
			learYear = false;
		}
		for (int i = 1; i <= 12; i++) {
			switch (month) {
				case 1:
				case 3:
				case 5:
				case 7:
				case 8:
				case 10:
				case 12:
					this.day1 = 31;
					break;
				case 2:
					if (learYear) {
						this.day1 = 29;
					} else {
						this.day1 = 28;
					}
					break;
				case 4:
				case 6:
				case 9:
				case 11:
					this.day1 = 30;
					break;
			}
		}
		return day1;
	}
}
