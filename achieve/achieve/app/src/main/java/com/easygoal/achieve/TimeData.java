package com.easygoal.achieve;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


import android.text.TextUtils;
import android.util.Log;

public class TimeData {

	public TimeData() {
		// TODO Auto-generated constructor stub
	}

	

	 public static String changeLongToTimeStrNoSecond(long ms) {
		  int ss = 1000;  
         int mi = ss * 60;  
         int hh = mi * 60;  
         int dd = hh * 24;  
        
         long day = (long) (ms / dd);  
         long hour =  (long) ((ms - day * dd) / hh);  
         long minute =  (long) ((ms - day * dd - hour * hh) / mi);  
         long second = (long) ((ms - day * dd - hour * hh - minute * mi) / ss);  
         long milliSecond = (long) (ms - day * dd - hour * hh - minute * mi - second * ss);  

         String strDay = day < 10 ? "0" + day : "" + day; //天  
         String strHour = hour < 10 ? "0" + hour : "" + hour;//小时  
         String strMinute = minute < 10 ? "0" + minute : "" + minute;//分钟  
         String strSecond = second < 10 ? "0" + second : "" + second;//秒  
         String strMilliSecond = milliSecond < 10 ? "0" + milliSecond : "" + milliSecond;//毫秒  
         strMilliSecond = milliSecond < 100 ? "0" + strMilliSecond : "" + strMilliSecond;  
         
         //long remaintime=ms-new Date().getTime();
         
         if (ms>0){
           String fulltimestr = ""+day+" 天  "+hour+" 小时  "+minute + " 分钟 ";
           return fulltimestr;  
         }else{
        	String fulltimestr = ""+day+" 天  "+hour+" 小时  "+minute + " 分钟 ";
            return fulltimestr; 	 
         }
         
         
	    }

	public static String getTimeStrfromCalendar(SimpleDateFormat sdf,Calendar cal){
       return sdf.format(cal.getTime());
	};

	public static String getSpecificNewTime(String curTime,String cycleunit) throws java.text.ParseException {
		SimpleDateFormat sdf1 = new SimpleDateFormat ("yy-MM-dd HH:mm");
		Date date_curTime=sdf1.parse(curTime);
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date_curTime);
		int year;
		int month ;
		int date;
		int week ;
		int weekday;
		int hour ;
		int min ;
		/*
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int date = calendar.get(Calendar.DATE);
		int week = calendar.get(Calendar.WEEK_OF_YEAR);
		int weekday = calendar.get(Calendar.DAY_OF_WEEK);
		int hour = calendar.get(Calendar.HOUR);
		int min = calendar.get(Calendar.MINUTE);
		*/

		String newSpecificTimeStr=null;

		switch (cycleunit){
			case "每日":
				//calendar.add(calendar.DATE,+1);
				month = calendar.get(Calendar.MONTH);
				date = calendar.get(Calendar.DATE);
				newSpecificTimeStr=(month+1)+"月"+date+"日";
				//newTimeStr = year + "-" + (month+1) + "-" + (date+1)+" "+hour+":"+min;
				//newTimeData = TimeData.changeStrToTime_YYYY(newTimeStr);
				break;
			case "每周":
				//calendar.add(calendar.WEEK_OF_YEAR,+1);
				week = calendar.get(Calendar.WEEK_OF_YEAR);
				newSpecificTimeStr="No"+week+"周";
				//newTimeStr = year + "-" + (month+1) + "-" + (date+1)+" "+hour+":"+min;
				//newTimeData = TimeData.changeStrToTime_YYYY(newTimeStr);
				break;
			case "每月":
				//calendar.add(calendar.MONTH,+1);
				month = calendar.get(Calendar.MONTH);
				newSpecificTimeStr=""+(month+1)+"月";
				//nextweek=week+1;
				//newTimeStr = year + "-" + (month+2) + "-" + date+" "+hour+":"+min;
				//newTimeData = TimeData.changeStrToTime_YYYY(newTimeStr);
				break;
			case "每季":
				//calendar.add(calendar.MONTH,+3);
				month = calendar.get(Calendar.MONTH);
				switch (month+1){
					case 1:	newSpecificTimeStr="Q1";break;
					case 2:	newSpecificTimeStr="Q1";break;
					case 3:	newSpecificTimeStr="Q1";break;
					case 4:	newSpecificTimeStr="Q2";break;
					case 5:	newSpecificTimeStr="Q2";break;
					case 6:	newSpecificTimeStr="Q2";break;
					case 7:	newSpecificTimeStr="Q3";break;
					case 8:	newSpecificTimeStr="Q3";break;
					case 9:	newSpecificTimeStr="Q3";break;
					case 10:	newSpecificTimeStr="Q4";break;
					case 11:	newSpecificTimeStr="Q4";break;
					case 12:	newSpecificTimeStr="Q4";break;
					default:break;

				}
				//newTimeStr = (year+1)+ "-" + (month+1) + "-" + date+" "+hour+":"+min;
				//newTimeData = TimeData.changeStrToTime_YYYY(newTimeStr);
				break;
			case "每年":
				//calendar.add(calendar.YEAR,+1);
				year = calendar.get(Calendar.YEAR);
				newSpecificTimeStr=""+year+"年";
				//newTimeStr = (year+1)+ "-" + (month+1) + "-" + date+" "+hour+":"+min;
				//newTimeData = TimeData.changeStrToTime_YYYY(newTimeStr);
				break;

			default:break;
		}
		return newSpecificTimeStr;
	}

	public static String getLastestTime(String oldDeadline,String cycleunit) throws java.text.ParseException {
		SimpleDateFormat sdf1 = new SimpleDateFormat ("yy-MM-dd HH:mm");
		Date date_oldDeadline=sdf1.parse(oldDeadline);
		Date date_now=new Date();
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date_oldDeadline);
		Calendar cal_now=Calendar.getInstance();
		cal_now.setTime(date_now);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int date = calendar.get(Calendar.DATE);
		int week = calendar.get(Calendar.WEEK_OF_YEAR);
		int weekday = calendar.get(Calendar.DAY_OF_WEEK);
		int hour = calendar.get(Calendar.HOUR);
		int min = calendar.get(Calendar.MINUTE);
		int nextweek;
		String newTimeStr=null;
		long newTimeData;
		switch (cycleunit){
			case "每日":
				while (calendar.before(cal_now)) {
					calendar.add(calendar.DATE, +1);
				}		;
				newTimeStr=getTimeStrfromCalendar(sdf1,calendar);
				//newTimeStr = year + "-" + (month+1) + "-" + (date+1)+" "+hour+":"+min;
				//newTimeData = TimeData.changeStrToTime_YYYY(newTimeStr);
				break;
			case "每周":
				while (calendar.before(cal_now)) {
					calendar.add(calendar.WEEK_OF_YEAR, +1);
				}
				newTimeStr=getTimeStrfromCalendar(sdf1,calendar);
				//newTimeStr = year + "-" + (month+1) + "-" + (date+1)+" "+hour+":"+min;
				//newTimeData = TimeData.changeStrToTime_YYYY(newTimeStr);
				break;
			case "每月":
				while (calendar.before(cal_now)) {
					calendar.add(calendar.MONTH,+1);
				}
				newTimeStr=getTimeStrfromCalendar(sdf1,calendar);
				//nextweek=week+1;
				//newTimeStr = year + "-" + (month+2) + "-" + date+" "+hour+":"+min;
				//newTimeData = TimeData.changeStrToTime_YYYY(newTimeStr);
				break;
			case "每季":
				while (calendar.before(cal_now)) {
					calendar.add(calendar.MONTH, +3);
				}
				newTimeStr=getTimeStrfromCalendar(sdf1,calendar);
				//newTimeStr = (year+1)+ "-" + (month+1) + "-" + date+" "+hour+":"+min;
				//newTimeData = TimeData.changeStrToTime_YYYY(newTimeStr);
				break;
			case "每年":
				while (calendar.before(cal_now)) {
					calendar.add(calendar.YEAR, +1);
				}
				newTimeStr=getTimeStrfromCalendar(sdf1,calendar);
				//newTimeStr = (year+1)+ "-" + (month+1) + "-" + date+" "+hour+":"+min;
				//newTimeData = TimeData.changeStrToTime_YYYY(newTimeStr);
				break;

			default:break;
		}
		return newTimeStr;
	}


	public static long getLastestTimeData(String oldDeadline,String cycleunit) throws java.text.ParseException {
		SimpleDateFormat sdf1 = new SimpleDateFormat ("yy-MM-dd HH:mm");
		Date date_oldDeadline=sdf1.parse(oldDeadline);
		Date date_now=new Date();
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date_oldDeadline);
		Calendar cal_now=Calendar.getInstance();
		cal_now.setTime(date_now);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int date = calendar.get(Calendar.DATE);
		int week = calendar.get(Calendar.WEEK_OF_YEAR);
		int weekday = calendar.get(Calendar.DAY_OF_WEEK);
		int hour = calendar.get(Calendar.HOUR);
		int min = calendar.get(Calendar.MINUTE);
		int nextweek;
		String newTimeStr=null;
		long newTimeData=0;
		switch (cycleunit){
			case "单次":
				//calendar.add(calendar.DATE,0);
				while (calendar.before(cal_now)) {
					newTimeData = calendar.getTimeInMillis();
				}
				//newTimeStr = year + "-" + (month+1) + "-" + (date+1)+" "+hour+":"+min;
				//newTimeData = TimeData.changeStrToTime_YYYY(newTimeStr);
				break;
			case "每日":
				while (calendar.before(cal_now)) {
					calendar.add(calendar.DATE, +1);
				}
				newTimeData=calendar.getTimeInMillis();
				//newTimeStr = year + "-" + (month+1) + "-" + (date+1)+" "+hour+":"+min;
				//newTimeData = TimeData.changeStrToTime_YYYY(newTimeStr);
				break;
			case "每周":
				while (calendar.before(cal_now)) {
					calendar.add(calendar.WEEK_OF_YEAR,+1);
				}
				newTimeData=calendar.getTimeInMillis();
				//newTimeStr = year + "-" + (month+1) + "-" + (date+1)+" "+hour+":"+min;
				//newTimeData = TimeData.changeStrToTime_YYYY(newTimeStr);
				break;
			case "每月":
				while (calendar.before(cal_now)) {
					calendar.add(calendar.MONTH,+1);
				}
				newTimeData=calendar.getTimeInMillis();
				//nextweek=week+1;
				//newTimeStr = year + "-" + (month+2) + "-" + date+" "+hour+":"+min;
				//newTimeData = TimeData.changeStrToTime_YYYY(newTimeStr);
				break;
			case "每季":
				while (calendar.before(cal_now)) {
					calendar.add(calendar.MONTH,+3);
				}
				newTimeData=calendar.getTimeInMillis();
				//newTimeStr = (year+1)+ "-" + (month+1) + "-" + date+" "+hour+":"+min;
				//newTimeData = TimeData.changeStrToTime_YYYY(newTimeStr);
				break;
			case "每年":
				while (calendar.before(cal_now)) {
					calendar.add(calendar.YEAR,+1);
				}
				newTimeData=calendar.getTimeInMillis();
				//newTimeStr = (year+1)+ "-" + (month+1) + "-" + date+" "+hour+":"+min;
				//newTimeData = TimeData.changeStrToTime_YYYY(newTimeStr);
				break;

			default:break;
		}
		return newTimeData/60/1000;
	}

	public static String getNewTime(String curTime,String cycleunit) throws java.text.ParseException {
		SimpleDateFormat sdf1 = new SimpleDateFormat ("yy-MM-dd HH:mm");
		Date date_curTime=sdf1.parse(curTime);
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date_curTime);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int date = calendar.get(Calendar.DATE);
		int week = calendar.get(Calendar.WEEK_OF_YEAR);
		int weekday = calendar.get(Calendar.DAY_OF_WEEK);
		int hour = calendar.get(Calendar.HOUR);
		int min = calendar.get(Calendar.MINUTE);
		int nextweek;
		String newTimeStr=null;
		long newTimeData;
		switch (cycleunit){
			 case "每日":
				 calendar.add(calendar.DATE,+1);
				 newTimeStr=getTimeStrfromCalendar(sdf1,calendar);
				 //newTimeStr = year + "-" + (month+1) + "-" + (date+1)+" "+hour+":"+min;
				 //newTimeData = TimeData.changeStrToTime_YYYY(newTimeStr);
				           break;
			case "每周":
				calendar.add(calendar.WEEK_OF_YEAR,+1);
				newTimeStr=getTimeStrfromCalendar(sdf1,calendar);
				//newTimeStr = year + "-" + (month+1) + "-" + (date+1)+" "+hour+":"+min;
				//newTimeData = TimeData.changeStrToTime_YYYY(newTimeStr);
				break;
			case "每月":
				calendar.add(calendar.MONTH,+1);
				newTimeStr=getTimeStrfromCalendar(sdf1,calendar);
				//nextweek=week+1;
				//newTimeStr = year + "-" + (month+2) + "-" + date+" "+hour+":"+min;
				//newTimeData = TimeData.changeStrToTime_YYYY(newTimeStr);
				break;
			case "每季":
				calendar.add(calendar.MONTH,+3);
				newTimeStr=getTimeStrfromCalendar(sdf1,calendar);
				//newTimeStr = (year+1)+ "-" + (month+1) + "-" + date+" "+hour+":"+min;
				//newTimeData = TimeData.changeStrToTime_YYYY(newTimeStr);
				break;
			case "每年":
				calendar.add(calendar.YEAR,+1);
				newTimeStr=getTimeStrfromCalendar(sdf1,calendar);
				//newTimeStr = (year+1)+ "-" + (month+1) + "-" + date+" "+hour+":"+min;
				//newTimeData = TimeData.changeStrToTime_YYYY(newTimeStr);
				break;

			 default:break;
		 }
		 return newTimeStr;
	}

	public static long getNewTimeData(String curTime,String cycleunit) throws java.text.ParseException {
		SimpleDateFormat sdf1 = new SimpleDateFormat ("yy-MM-dd HH:mm");
		Date date_curTime=sdf1.parse(curTime);
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date_curTime);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int date = calendar.get(Calendar.DATE);
		int week = calendar.get(Calendar.WEEK_OF_YEAR);
		int weekday = calendar.get(Calendar.DAY_OF_WEEK);
		int hour = calendar.get(Calendar.HOUR);
		int min = calendar.get(Calendar.MINUTE);
		int nextweek;
		String newTimeStr=null;
		long newTimeData=0;
		switch (cycleunit){
			case "单次":
				//calendar.add(calendar.DATE,0);
				newTimeData=calendar.getTimeInMillis();
				//newTimeStr = year + "-" + (month+1) + "-" + (date+1)+" "+hour+":"+min;
				//newTimeData = TimeData.changeStrToTime_YYYY(newTimeStr);
				break;
			case "每日":
				calendar.add(calendar.DATE,+1);
				newTimeData=calendar.getTimeInMillis();
				//newTimeStr = year + "-" + (month+1) + "-" + (date+1)+" "+hour+":"+min;
				//newTimeData = TimeData.changeStrToTime_YYYY(newTimeStr);
				break;
			case "每周":
				calendar.add(calendar.WEEK_OF_YEAR,+1);
				newTimeData=calendar.getTimeInMillis();
				//newTimeStr = year + "-" + (month+1) + "-" + (date+1)+" "+hour+":"+min;
				//newTimeData = TimeData.changeStrToTime_YYYY(newTimeStr);
				break;
			case "每月":
				calendar.add(calendar.MONTH,+1);
				newTimeData=calendar.getTimeInMillis();
				//nextweek=week+1;
				//newTimeStr = year + "-" + (month+2) + "-" + date+" "+hour+":"+min;
				//newTimeData = TimeData.changeStrToTime_YYYY(newTimeStr);
				break;
			case "每季":
				calendar.add(calendar.MONTH,+3);
				newTimeData=calendar.getTimeInMillis();
				//newTimeStr = (year+1)+ "-" + (month+1) + "-" + date+" "+hour+":"+min;
				//newTimeData = TimeData.changeStrToTime_YYYY(newTimeStr);
				break;
			case "每年":
				calendar.add(calendar.YEAR,+1);
				newTimeData=calendar.getTimeInMillis();
				//newTimeStr = (year+1)+ "-" + (month+1) + "-" + date+" "+hour+":"+min;
				//newTimeData = TimeData.changeStrToTime_YYYY(newTimeStr);
				break;

			default:break;
		}
		return newTimeData/60/1000;
	}


	 public static String changeLongToTimeStr(double ms) {
		  int ss = 1000;  
          int mi = ss * 60;  
          int hh = mi * 60;  
          int dd = hh * 24;  
         
          long day = (long) (ms / dd);  
          long hour =  (long) ((ms - day * dd) / hh);  
          long minute =  (long) ((ms - day * dd - hour * hh) / mi);  
          long second = (long) ((ms - day * dd - hour * hh - minute * mi) / ss);  
          long milliSecond = (long) (ms - day * dd - hour * hh - minute * mi - second * ss);  

          String strDay = day < 10 ? "0" + day : "" + day; //天  
          String strHour = hour < 10 ? "0" + hour : "" + hour;//小时  
          String strMinute = minute < 10 ? "0" + minute : "" + minute;//分钟  
          String strSecond = second < 10 ? "0" + second : "" + second;//秒  
          String strMilliSecond = milliSecond < 10 ? "0" + milliSecond : "" + milliSecond;//毫秒  
          strMilliSecond = milliSecond < 100 ? "0" + strMilliSecond : "" + strMilliSecond;  
          
          String fulltimestr = ""+day+" 天"+hour+" 小时"+minute + " 分钟 " + second + " 秒";
          return fulltimestr;  
	    }

	public static String yyMMddHHmm2yyMMdd(String time){
		SimpleDateFormat formatter1 = new SimpleDateFormat ("yy-MM-dd HH:mm");
		SimpleDateFormat formatter2 = new SimpleDateFormat ("yy-MM-dd");
		// Date curDate = new Date(System.currentTimeMillis());//获取当前时间
		Date date1 = null;

		try {
			date1 = formatter1.parse(time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return formatter2.format(date1);
	}

	public static String yyMdHHmm2yyMMdd(String time){
			 SimpleDateFormat formatter1 = new SimpleDateFormat ("yy-M-d HH:mm");
			 SimpleDateFormat formatter2 = new SimpleDateFormat ("yy-MM-dd");
				// Date curDate = new Date(System.currentTimeMillis());//获取当前时间   
		     Date date1 = null;

			try {
                date1 = formatter1.parse(time);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
			return formatter2.format(date1);
			} 
	 
	public static String convertDataNoTime_YY(String time){
		 SimpleDateFormat formatter1 = new SimpleDateFormat ("yy-MM-dd HH:mm");
		 SimpleDateFormat formatter2 = new SimpleDateFormat ("yy-MM-dd");
			// Date curDate = new Date(System.currentTimeMillis());//获取当前时间   
	     Date date1 = null;

		try {
            date1 = formatter1.parse(time);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		return formatter2.format(date1);
		}
		
		public String convertDataWithTime_YY(String time){
			 SimpleDateFormat formatter1 = new SimpleDateFormat ("yy-MM-dd");
				// Date curDate = new Date(System.currentTimeMillis());//获取当前时间
		     SimpleDateFormat formatter2 = new SimpleDateFormat ("yy-MM-dd HH:mm");
		     Date date1 = null;

					
					try {
						date1 = formatter1.parse(time);
					} catch (java.text.ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

		     return formatter2.format(date1);
			}
		
    public int compareDate_YY(String time1,String time2){
			 SimpleDateFormat formatter1 = new SimpleDateFormat ("yy-MM-dd");
				// Date curDate = new Date(System.currentTimeMillis());//获取当前时间
		     //SimpleDateFormat formatter1 = new SimpleDateFormat ("yy-MM-dd HH:mm");
			Date date1 = null;
			Date date2 = null;
			try {
				date1 = formatter1.parse(time1);
				date2 = formatter1.parse(time2);	
			} catch (java.text.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
		     return date1.compareTo(date2);
			}
		
	public static long changeStrToTime_YYYY(String str_time1){
	    	
	    	//String str2 = et15_taskdeadline.getText().toString();  //"yyyyMMdd"格式 如 20131022
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-M-d HH:mm");//输入日期的格式 
			Date date1 = null;
			Date date2 = null;
			
			
			try {
				date1= simpleDateFormat.parse(str_time1);
			} catch (java.text.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				//Toast.makeText(getbasecontext(), "parse error", Toast.LENGTH_LONG);
			}
			
			//GregorianCalendar cal1 = new GregorianCalendar();  
		 
			//GregorianCalendar cal1 = new GregorianCalendar();
			//cal1.setTime(date1);  
			
			
			long caltime = date1.getTime()/(1000*60);
						
	    	return caltime;
	    };
public static long changeStrToTime_YY(String str_time1){
	    	
	    	//String str2 = et15_taskdeadline.getText().toString();  //"yyyyMMdd"格式 如 20131022
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd HH:mm");//输入日期的格式 
			Date date1 = null;
			Date date2 = null;
			
			
			try {
				date1= simpleDateFormat.parse(str_time1);
			} catch (java.text.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				//Toast.makeText(getbasecontext(), "parse error", Toast.LENGTH_LONG);
			}
			
			//GregorianCalendar cal1 = new GregorianCalendar();  
		 
			//GregorianCalendar cal1 = new GregorianCalendar();
			//cal1.setTime(date1);  
			
			
			long caltime = date1.getTime()/(1000*60);
						
	    	return caltime;
	    };
	    
public static String changeStrTime_YYToTime_YYYY(String str_time1){
	    	
	    	//String str2 = et15_taskdeadline.getText().toString();  //"yyyyMMdd"格式 如 20131022
			SimpleDateFormat sdf1 = new SimpleDateFormat("yy-MM-dd HH:mm");//输入日期的格式 
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Date date1 = null;
			//Date date2 = null;
			
			
			try {
				date1= sdf1.parse(str_time1);
			} catch (java.text.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				//Toast.makeText(getbasecontext(), "parse error", Toast.LENGTH_LONG);
			}
			
			//GregorianCalendar cal1 = new GregorianCalendar();  
		 
			//GregorianCalendar cal1 = new GregorianCalendar();
			//cal1.setTime(date1);  
	    	return sdf2.format(date1);
	    };	    
	    
public static long changeStrToMillisTime_YY(String str_time1){
	    	
	    	//String str2 = et15_taskdeadline.getText().toString();  //"yyyyMMdd"格式 如 20131022
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd HH:mm");//输入日期的格式 
			Date date1 = null;
			//Date date2 = null;
			
			try {
				date1= simpleDateFormat.parse(str_time1);
			} catch (java.text.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				//Toast.makeText(getbasecontext(), "parse error", Toast.LENGTH_LONG);
			}
			
			//GregorianCalendar cal1 = new GregorianCalendar();  
		 
			//GregorianCalendar cal1 = new GregorianCalendar();
			//cal1.setTime(date1);  
			
		
			long caltime = date1.getTime();
						
	    	return caltime;
	    };    
	    
	    
		public static double changeToGregoTime_YYYY(String str_time1){
	    	
	    	//String str2 = et15_taskdeadline.getText().toString();  //"yyyyMMdd"格式 如 20131022
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");//输入日期的格式 
			Date date1 = null;
			Date date2 = null;
			try {
			
			date1= simpleDateFormat.parse(str_time1);
			} catch (ParseException e) {
			e.printStackTrace();
			}
			//GregorianCalendar cal1 = new GregorianCalendar();  
		 
			GregorianCalendar cal1 = new GregorianCalendar();
			//cal1.setTime(date1);  
			
			cal1.setTime(date1);
			double caltime = cal1.getTimeInMillis()/(1000*3600*24);
						
	    	return caltime;
	    };
		
	    
		 public static String convertDate_YYYYtoYY(String time){
			 SimpleDateFormat formatter2 = new SimpleDateFormat ("yy-MM-dd");
				// Date curDate = new Date(System.currentTimeMillis());//获取当前时间
		     SimpleDateFormat formatter1 = new SimpleDateFormat ("yyyy-MM-dd");
		     Date date1 = null;

					
					try {
						date1 = formatter1.parse(time);
					} catch (java.text.ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

		     return formatter2.format(date1);
			}
		
		 public static String convertDate_YYtoYYYY(String time){
			 SimpleDateFormat formatter2 = new SimpleDateFormat ("yyyy-MM-dd");
				// Date curDate = new Date(System.currentTimeMillis());//获取当前时间
		     SimpleDateFormat formatter1 = new SimpleDateFormat ("yy-MM-dd");
		     Date date1 = null;

					
					try {
						date1 = formatter1.parse(time);
					} catch (java.text.ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

		     return formatter2.format(date1);
			}
		 
		 
		 public static String convertShortDate_YYYYMdtoYYYYMMdd(String time){
			 SimpleDateFormat formatter2 = new SimpleDateFormat ("yyyyMMdd");
				// Date curDate = new Date(System.currentTimeMillis());//获取当前时间
		     SimpleDateFormat formatter1 = new SimpleDateFormat ("yyyy-M-d");
		     Date date1 = null;

					
					try {
						date1 = formatter1.parse(time);
					} catch (java.text.ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

		     return formatter2.format(date1);
			}
		 
		 public static String convertShortDate_YYMdtoYYYYMMdd(String time){
			 SimpleDateFormat formatter2 = new SimpleDateFormat ("yyyyMMdd");
				// Date curDate = new Date(System.currentTimeMillis());//获取当前时间
		     SimpleDateFormat formatter1 = new SimpleDateFormat ("yy-MM-dd");
		     Date date1 = null;

					
					try {
						date1 = formatter1.parse(time);
					} catch (java.text.ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

		     return formatter2.format(date1);
			}
		 
		 public static String convertDate_YYYYMtoYYMM(String time){
			 SimpleDateFormat formatter2 = new SimpleDateFormat ("yy-MM-dd");
				// Date curDate = new Date(System.currentTimeMillis());//获取当前时间
		     SimpleDateFormat formatter1 = new SimpleDateFormat ("yyyy-M-d");
		     Date date1 = null;

					
					try {
						date1 = formatter1.parse(time);
					} catch (java.text.ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

		     return formatter2.format(date1);
			}
		 
		 
		 public static String convertTime_YYYYtoYY(String time){
			 SimpleDateFormat formatter2 = new SimpleDateFormat ("yy-MM-dd HH:mm");
				// Date curDate = new Date(System.currentTimeMillis());//获取当前时间
		     SimpleDateFormat formatter1 = new SimpleDateFormat ("yyyy-M-d HH:mm");
		     Date date1 = null;

					
					try {
						date1 = formatter1.parse(time);
					} catch (java.text.ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

		     return formatter2.format(date1);
			}
			
		 public static String convertTime_YYMMMDDHHSStoHHSS(String time){
			 SimpleDateFormat formatter2 = new SimpleDateFormat ("HH:mm");
				// Date curDate = new Date(System.currentTimeMillis());//获取当前时间
		     SimpleDateFormat formatter1 = new SimpleDateFormat ("yy-MM-dd HH:mm");
		     Date date1 = null;

					
					try {
						date1 = formatter1.parse(time);
					} catch (java.text.ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

		     return formatter2.format(date1);
			}
		 
		 public static String convertTime_YYYYTIMEtoYYTIME(String time){
			 SimpleDateFormat formatter2 = new SimpleDateFormat ("yy-MM-dd HH:mm");
				// Date curDate = new Date(System.currentTimeMillis());//获取当前时间
		     SimpleDateFormat formatter1 = new SimpleDateFormat ("yyyy-M-d HH:mm");
		     Date date1 = null;

					try {
						date1 = formatter1.parse(time);
					} catch (java.text.ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

		     return formatter2.format(date1);
			} 
	    
	    public static double CompareTime_YY(String str_time1,String str_time2){
	    	
	    	//String str2 = et15_taskdeadline.getText().toString();  //"yyyyMMdd"格式 如 20131022
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd HH:mm");//输入日期的格式 
			Date date1 = null;
			Date date2 = null;
			try {
			date2 = simpleDateFormat.parse(str_time2);
			date1= simpleDateFormat.parse(str_time1);
			} catch (ParseException e) {
			e.printStackTrace();
			}
			//GregorianCalendar cal1 = new GregorianCalendar();  
			GregorianCalendar cal2 = new GregorianCalendar(); 
			GregorianCalendar cal1 = new GregorianCalendar();
			//cal1.setTime(date1);  
			cal2.setTime(date2);  
			cal1.setTime(date1);
			double dayCount = (cal2.getTimeInMillis()-cal1.getTimeInMillis())/(1000*3600*24);
						
	    	return dayCount;
	    };
	    
	   
	    public static int day_diff(int year_start, int month_start, int day_start
	    		   , int year_end, int month_end, int day_end)
	    		{
	    		 int y2, m2, d2;
	    		 int y1, m1, d1;
	    		 
	    		 m1 = (month_start + 9) % 12;
	    		 y1 = year_start - m1/10;
	    		 d1 = 365*y1 + y1/4 - y1/100 + y1/400 + (m1*306 + 5)/10 + (day_start - 1);

	    		 m2 = (month_end + 9) % 12;
	    		 y2 = year_end - m2/10;
	    		 d2 = 365*y2 + y2/4 - y2/100 + y2/400 + (m2*306 + 5)/10 + (day_end - 1);
	    		 
	    		 return (d2 - d1);
	    		}
	    
	    
	   
	    public static int day_gap(String str_time1,String str_time2)
	      {
	    	SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:SS");//输入日期的格式 
	    	SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:SS");
	    	Date date1 = null;
			Date date2 = null;
			try {
			date2 = sdf2.parse(str_time2);
			date1= sdf2.parse(str_time1);
			} catch (ParseException e) {
			e.printStackTrace();
			}
	    	     int year_start=date1.getYear();
	    	     int month_start=date1.getMonth();
	    	     int day_start=date1.getDate();
	    	     int year_end=date2.getYear();
	    	     int month_end=date2.getMonth();
	    	     int day_end=date2.getDate();
	    		 int y2, m2, d2;
	    		 int y1, m1, d1;
	    		 
	    		 m1 = (month_start + 9) % 12;
	    		 y1 = year_start - m1/10;
	    		 d1 = 365*y1 + y1/4 - y1/100 + y1/400 + (m1*306 + 5)/10 + (day_start - 1);

	    		 m2 = (month_end + 9) % 12;
	    		 y2 = year_end - m2/10;
	    		 d2 = 365*y2 + y2/4 - y2/100 + y2/400 + (m2*306 + 5)/10 + (day_end - 1);
	    		 Log.d("year", "y1:"+y1+" y2:"+y2);
	    		 Log.d("month", "m1:"+m1+" m2:"+m2);
	    		 Log.d("day", "d1:"+d1+" d2:"+d2);
	    		 return (d2 - d1);
	    		}
	    
	    public static double TimeGap_YYMMDDHHSS(String str_time1,String str_time2){
	    	
	    	//String str2 = et15_taskdeadline.getText().toString();  //"yyyyMMdd"格式 如 20131022
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd HH:SS");//输入日期的格式 
			Date date1 = null;
			Date date2 = null;
			try {
			date2 = simpleDateFormat.parse(str_time2);
			date1= simpleDateFormat.parse(str_time1);
			} catch (ParseException e) {
			e.printStackTrace();
			}
			//GregorianCalendar cal1 = new GregorianCalendar();  
			GregorianCalendar cal2 = new GregorianCalendar(); 
			GregorianCalendar cal1 = new GregorianCalendar();
			//cal1.setTime(date1);  
			cal2.setTime(date2);  
			cal1.setTime(date1);
			double dayCount = cal2.getTimeInMillis()-cal1.getTimeInMillis();
						
	    	return dayCount;
	    };
	    
		 public static double DaySpace_YY(String str_time1,String str_time2){
		    	
		    	//String str2 = et15_taskdeadline.getText().toString();  //"yyyyMMdd"格式 如 20131022
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd");//输入日期的格式 
				Date date1 = null;
				Date date2 = null;
				try {
				date2 = simpleDateFormat.parse(str_time2);
				date1= simpleDateFormat.parse(str_time1);
				} catch (ParseException e) {
				e.printStackTrace();
				}
				//GregorianCalendar cal1 = new GregorianCalendar();  
				GregorianCalendar cal2 = new GregorianCalendar(); 
				GregorianCalendar cal1 = new GregorianCalendar();
				//cal1.setTime(date1);  
				cal2.setTime(date2);  
				cal1.setTime(date1);
				double dayCount =(double)((cal2.getTimeInMillis()-cal1.getTimeInMillis())/(1000*3600*24));
							
		    	return dayCount;
		    };
		    
         public static long todayEndTimeData(){
		    	
		        String todayEndTimeStr=new SimpleDateFormat("yy-MM-dd").format(new Date())+" 23:59";
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd HH:mm");//输入日期的格式 
				Date date1 = null;
				
				try {
				   date1= simpleDateFormat.parse(todayEndTimeStr);
				} catch (ParseException e) {
				   e.printStackTrace();
				}
				long todayendtimedata=date1.getTime()/(1000*60);
				Log.d("TimeData|todayendtimedata|", ""+todayendtimedata);
		    	return todayendtimedata;
		    };    
		    
		    public static String getTodayDate(){
				 SimpleDateFormat formatter = new SimpleDateFormat ("yy-MM-dd");
				 String curDate = formatter.format(new Date());
				 return curDate;
				}
		    
		    
		   public static String TimeStamp2TimeStrYY(String timestampstr){ 
		     
			   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");//定义日期类型格式
			   Date date = null;
			 if (timestampstr!=null&&!TextUtils.isEmpty(timestampstr)){   
				 try {
					date = sdf.parse(timestampstr);
				 } catch (java.text.ParseException e) {
					 // TODO Auto-generated catch block
					 e.printStackTrace();
				 }
				 System.out.println(date);  
				 return sdf.format(date);
			 }else{
				 return "";
		     
			 } 
		  }	
}
