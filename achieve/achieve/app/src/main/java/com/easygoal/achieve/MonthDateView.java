package com.easygoal.achieve;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

public class MonthDateView extends View {
	private static final int NUM_COLUMNS = 7;
	private static final int NUM_ROWS = 6;
	private Paint mPaint;
	//�Ϻ�����ɫ
	private int mTopLineColor = Color.parseColor("#CCE4F2");
	//�º�����ɫ
	private int mBottomLineColor = Color.parseColor("#CCE4F2");
	//��һ���������ɫ
	private int mWeedayColor = Color.parseColor("#1FC2F3");
	//���������յ���ɫ
	private int mWeekendColor = Color.parseColor("#fa4451");
	//�ߵĿ��
	private int mDayColor = Color.parseColor("#696969");
	private int mSelectDayColor = Color.parseColor("#FFFFFF");
	private int mSelectBGColor = Color.parseColor("#FFC0CB");
	private int mCurrentColor = Color.parseColor("#F4A460");
	private int mCurrYear,mCurrMonth,mCurrDay;
	private int mSelYear,mSelMonth,mSelDay;
	private int mColumnSize,mRowSize;
	private DisplayMetrics mDisplayMetrics;
	private int mDaySize = 14;
	private TextView tv_date,tv_week;
	private int weekRow;
	private int [][] daysString;
	private int mCircleRadius = 14;
	private DateClick dateClick;
	private int mCircleColor = Color.parseColor("#FF8247");
	private List<Integer> daysHasThingList;
	private String[] weekString = new String[]{"��","һ","��","��","��","��","��"};

	public MonthDateView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mDisplayMetrics = getResources().getDisplayMetrics();
		Calendar calendar = Calendar.getInstance();
		mPaint = new Paint();
		mCurrYear = calendar.get(Calendar.YEAR);
		mCurrMonth = calendar.get(Calendar.MONTH);
		mCurrDay = calendar.get(Calendar.DATE);
		setSelectYearMonth(mCurrYear,mCurrMonth,mCurrDay);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);

		int heightSize = MeasureSpec.getSize(heightMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);

		if(heightMode == MeasureSpec.AT_MOST){
			heightSize = mDisplayMetrics.densityDpi * 30;
		}
		if(widthMode == MeasureSpec.AT_MOST){
			widthSize = mDisplayMetrics.densityDpi * 300;
		}
		setMeasuredDimension(widthSize, heightSize);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		int width = getWidth();
		int height = getHeight();
        int headerHeight=60;
		int contentHeight=80;
		initSize();
        /*
		int columnWidth = width / 7;
		for(int i=0;i < weekString.length;i++) {
			String text = weekString[i];
			int fontWidth = (int) mPaint.measureText(text);
			int startX = columnWidth * i + (columnWidth - fontWidth) / 2;
			int startY = (int) (headerHeight- (mPaint.ascent() + mPaint.descent()) / 2);
			if (text.indexOf("��") > -1 || text.indexOf("��") > -1) {
				mPaint.setColor(mWeekendColor);
			} else {
				mPaint.setColor(mWeedayColor);
			}

			canvas.drawText(text, startX, startY, mPaint);
		}*/
		daysString = new int[6][7];
		mPaint.setTextSize(mDaySize*mDisplayMetrics.scaledDensity);
		String dayString;
		int mMonthDays = DateUtils.getMonthDays(mSelYear, mSelMonth);
		int weekNumber = DateUtils.getFirstDayWeek(mSelYear, mSelMonth);
		Log.d("DateView", "DateView:" + mSelMonth+"��1����" + weekNumber);

		for(int day = 0;day < mMonthDays;day++){
			dayString = (day + 1) + "";
			int column = (day+weekNumber - 1) % 7;
			int row = (day+weekNumber - 1) / 7;
			daysString[row][column]=day + 1;
			int startX = (int) (mColumnSize * column + (mColumnSize - mPaint.measureText(dayString))/2);
			int startY=(int) (mRowSize * row + mRowSize/2 - (mPaint.ascent() + mPaint.descent())/2);

			if(dayString.equals(mSelDay+"")){
				//���Ʊ���ɫ����
				int startRecX = mColumnSize * column;
				int startRecY = mRowSize * row;
				int endRecX = startRecX + mColumnSize;
				int endRecY = startRecY + mRowSize;
				mPaint.setColor(mSelectBGColor);
				canvas.drawRect(startRecX, startRecY, endRecX, endRecY, mPaint);
				//canvas.drawCircle(startRecX, startRecY, 60, mPaint);
				//��¼�ڼ��У����ڼ���
				weekRow = row + 1;
			}
			//��������Բ�α�־
			drawCircle(row,column,day + 1,canvas);
			if(dayString.equals(mSelDay+"")){
				mPaint.setColor(mSelectDayColor);
			}else if(dayString.equals(mCurrDay+"") && mCurrDay != mSelDay && mCurrMonth == mSelMonth){
				//�����£�ѡ���������ڣ������Ϊ��ɫ
				mPaint.setColor(mCurrentColor);
			}else{
				mPaint.setColor(mDayColor);
			}
			canvas.drawText(dayString, startX, startY, mPaint);

			if(tv_date != null){
				tv_date.setText(mSelYear + "��" + (mSelMonth + 1) + "��");
			}
			
			if(tv_week != null){
				tv_week.setText("��" + weekRow  +"��");
			}
		}
	}
	
	private void drawCircle(int row,int column,int day,Canvas canvas){
		if(daysHasThingList != null && daysHasThingList.size() >0){
			if(!daysHasThingList.contains(day))return;
			mPaint.setColor(mCircleColor);
			float circleX = (float) (mColumnSize * column +	mColumnSize*0.8);
			float circley = (float) (mRowSize * row + mRowSize*0.2);
			canvas.drawCircle(circleX, circley, mCircleRadius, mPaint);
		}
	}
	@Override
	public boolean performClick() {
		return super.performClick();
	}

	private int downX = 0,downY = 0;
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int eventCode=  event.getAction();
		switch(eventCode){
		case MotionEvent.ACTION_DOWN:
			downX = (int) event.getX();
			downY = (int) event.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			break;
		case MotionEvent.ACTION_UP:
			int upX = (int) event.getX();
			int upY = (int) event.getY();
			if(Math.abs(upX-downX) < 10 && Math.abs(upY - downY) < 10){//����¼�
				performClick();
				doClickAction((upX + downX)/2,(upY + downY)/2);
			}
			break;
		}
		return true;
	}

	/**
	 * ��ʼ���п��и�
	 */
	private void initSize(){
		mColumnSize = getWidth() / NUM_COLUMNS;
		mRowSize = getHeight() / NUM_ROWS;

	}
	
	/**
	 * ��������
	 * @param year
	 * @param month
	 */
	private void setSelectYearMonth(int year,int month,int day){
		mSelYear = year;
		mSelMonth = month;
		mSelDay = day;
	}
	/**
	 * ִ�е���¼�
	 * @param x
	 * @param y
	 */
	private void doClickAction(int x,int y){
		int row = y / mRowSize;
		int column = x / mColumnSize;
		setSelectYearMonth(mSelYear,mSelMonth,daysString[row][column]);
		invalidate();
		//ִ��activity���͹����ĵ�������¼�
		if(dateClick != null){
			dateClick.onClickOnDate();
		}
	}


	/**
	 * �������������ҳ
	 */
	public void onLeftClick(){
		int year = mSelYear;
		int month = mSelMonth;
		int day = mSelDay;
		if(month == 0){//������1�·ݣ�����12�·�
			year = mSelYear-1;
			month = 11;
		}else if(DateUtils.getMonthDays(year, month) == day){
			//�����ǰ����Ϊ�������һ�㣬����ǰ�Ƶ�ʱ�򣬾���Ҫ�ı�ѡ�е�����
			month = month-1;
			day = DateUtils.getMonthDays(year, month);
		}else{
			month = month-1;
		}
		setSelectYearMonth(year,month,day);
		invalidate();

	}
	
	/**
	 * �ҵ����������ǰ��ҳ
	 */
	public void onRightClick(){
		int year = mSelYear;
		int month = mSelMonth;
		int day = mSelDay;
		if(month == 11){//������12�·ݣ�����1�·�
			year = mSelYear+1;
			month = 0;
		}else if(DateUtils.getMonthDays(year, month) == day){
			//�����ǰ����Ϊ�������һ�㣬����ǰ�Ƶ�ʱ�򣬾���Ҫ�ı�ѡ�е�����
			month = month + 1;
			day = DateUtils.getMonthDays(year, month);
		}else{
			month = month + 1;
		}
		setSelectYearMonth(year,month,day);
		invalidate();

	}
	
	/**
	 * ��ȡѡ������
	 * @return
	 */
	public int getmSelYear() {
		return mSelYear;
	}
	/**
	 * ��ȡѡ����·�
	 * @return
	 */
	public int getmSelMonth() {
		return mSelMonth;
	}
	/**
	 * ��ȡѡ�������
	 * @param mSelDay
	 */
	public int getmSelDay() {
		return this.mSelDay;
	}
	/**
	 * ��ͨ���ڵ�������ɫ��Ĭ�Ϻ�ɫ
	 * @param mDayColor
	 */
	public int getmLastDay() {
		return this.mSelDay;
	}
	/**
	 * ��ͨ���ڵ�������ɫ��Ĭ�Ϻ�ɫ
	 * @param mDayColor
	 */

	public void setmDayColor(int mDayColor) {
		this.mDayColor = mDayColor;
	}
	
	/**
	 * ѡ�����ڵ���ɫ��Ĭ��Ϊ��ɫ
	 * @param mSelectDayColor
	 */
	public void setmSelectDayColor(int mSelectDayColor) {
		this.mSelectDayColor = mSelectDayColor;
	}

	/**
	 * ѡ�����ڵı�����ɫ��Ĭ����ɫ
	 * @param mSelectBGColor
	 */
	public void setmSelectBGColor(int mSelectBGColor) {
		this.mSelectBGColor = mSelectBGColor;
	}
	/**
	 * ��ǰ���ڲ���ѡ�е���ɫ��Ĭ�Ϻ�ɫ
	 * @param mCurrentColor
	 */
	public void setmCurrentColor(int mCurrentColor) {
		this.mCurrentColor = mCurrentColor;
	}

	/**
	 * ���ڵĴ�С��Ĭ��18sp
	 * @param mDaySize
	 */
	public void setmDaySize(int mDaySize) {
		this.mDaySize = mDaySize;
	}
	/**
	 * ������ʾ��ǰ���ڵĿؼ�
	 * @param tv_date
	 * 		��ʾ����
	 * @param tv_week
	 * 		��ʾ��
	 */
	public void setTextView(TextView tv_date,TextView tv_week){
		this.tv_date = tv_date;
		this.tv_week = tv_week;
		invalidate();
	}

	/**
	 * ������������
	 * @param daysHasThingList
	 */
	public void setDaysHasThingList(List<Integer> daysHasThingList) {
		this.daysHasThingList = daysHasThingList;
	}

	/***
	 * ����ԲȦ�İ뾶��Ĭ��Ϊ6
	 * @param mCircleRadius
	 */
	public void setmCircleRadius(int mCircleRadius) {
		this.mCircleRadius = mCircleRadius;
	}
	
	/**
	 * ����ԲȦ�İ뾶
	 * @param mCircleColor
	 */
	public void setmCircleColor(int mCircleColor) {
		this.mCircleColor = mCircleColor;
	}
	
	/**
	 * �������ڵĵ���ص��¼�
	 * @author shiwei.deng
	 *
	 */
	public interface DateClick{
		public void onClickOnDate();
	}
	public interface refreshView{
		public void onRefreshView();
	}
	/**
	 * �������ڵ���¼�
	 * @param dateClick
	 */
	public void setDateClick(DateClick dateClick) {
		this.dateClick = dateClick;
	}
	
	/**
	 * ��ת������
	 */
	public void setTodayToView(){
		setSelectYearMonth(mCurrYear,mCurrMonth,mCurrDay);
		invalidate();
	}
}
