package com.easygoal.achieve;

import java.io.File;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.litepal.crud.DataSupport;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

public class ExportUtils {

	private SQLiteDatabase mDb;
	private String mTb;
	private Cursor cur;
	private Cursor cur2;
	private String[] titlename;
	private String[] columnname;

	public ExportUtils(String tablename) {
		// mDb = db;
		// mDestXmlFilename = destXml;
		mTb=tablename;
		//cur=cursor;
		// cur2=cursor2;
		//this.titlename=titlename;
		//this.columnname=columnname;
	}


	public static void exportToReportBymail(Context context,int reporttype){

		final String[] taskitemlist={
				TaskData.TdDB.TASK_NAME,
				TaskData.TdDB.TASK_USERNICKNAME,
				TaskData.TdDB.TASK_PRIORITY,
				TaskData.TdDB.TASK_CREATEDTIME,
				TaskData.TdDB.TASK_STARTEDTIME,
				TaskData.TdDB.TASK_DEADLINE,
				TaskData.TdDB.TASK_DURATION,
				TaskData.TdDB.TASK_PROGRESS,
				TaskData.TdDB.TASK_STATUS,
				TaskData.TdDB.TASK_REMARKS
				//TaskData.TdDB.TASK_STATUS,
				//TaskData.TdDB.TASK_FINISHED,
				//TaskData.TdDB.TASK_DELAYED
		};

		final String[] titlenamelist={
				//"任务???","责任???","智能分类","智能排序","创建时间","???始时???","???后期???","预计用时(hr)","完成%","完成状???","备注"
				"任务名称","责任人","智能分类","创建时间","开始时间","最后期限","预计用时(hr)","完成%","完成状态","备注"
		};

		SimpleDateFormat formatter = new SimpleDateFormat ("yy-MM-dd HH:mm");
		Date curDate = new Date();//获取当前时间
		String curTime = formatter.format(curDate);

		final Calendar cal=Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int week = cal.get(Calendar.WEEK_OF_YEAR);
		//int weekofmonth= cal.get(Calendar.mo);
		int weekday= cal.get(Calendar.DAY_OF_WEEK);
		int day=cal.get(Calendar.DAY_OF_MONTH);

		long countTimeData = 0;
		String reportname = null;
		String str_today = year + "-" + (month+1) + "-" + day+" "+"23"+":"+"59";
		String str_thismonth = year + "-" + (month+1) + "-" + 1+" "+"00"+":"+"00";
		String str_thisweek;
		if (weekday==1){
			str_thisweek=year + "-"+ (month+1) + "-"+(day-6)+" "+"00"+":"+"00";
		}else{
			str_thisweek=year + "-"+ (month+1) + "-"+(day-weekday)+" "+"00"+":"+"00";
		}
		Log.d("this week", str_thisweek);
		long todayTimeData = TimeData.changeStrToTime_YYYY(str_today);
		long thismonthTimeData = TimeData.changeStrToTime_YYYY(str_thismonth);
		long thisweekTimeData = TimeData.changeStrToTime_YYYY(str_thisweek);

		switch (reporttype){
			case 1: countTimeData=todayTimeData;
				reportname="日报"+year + "年" + (month+1) + "月" + day+"日";
				Log.d("reportname", reportname);
				break;
			case 2: countTimeData=thisweekTimeData;
				reportname="周报"+year + "年" +week+"周";
				Log.d("reportname", reportname);
				break;
			case 3: countTimeData=thismonthTimeData;
				reportname="月报"+year + "年" + (month+1)+"月";
				Log.d("reportname", reportname);
				break;
			default:break;

		}

		Cursor c = TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+
						" where "+TaskData.TdDB.TASK_USER+"=? and "+
						TaskData.TdDB.TASK_DEADLINETIMEDATA+">?",
				new String[]{TaskData.user,String.valueOf(countTimeData)});

		//Cursor c=TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+TaskData.TdDB.TASK_STATUS+"=?"+" order by "+TaskData.TdDB.TASK_SEQUENCENO+" asc", new String[]{"open"});
		// TODO Auto-generated method stub
		if (c.getCount()>0){

			File outfile=new ExportListToExcelReport(context,
					TaskData.TdDB.TABLE_NAME_TaskMain,
					c,
					titlenamelist,
					taskitemlist)
					.writeExcel(reportname);
			Intent intent2 = new Intent(Intent.ACTION_SEND);
			intent2.setType("text/*");
			intent2.putExtra(android.content.Intent.EXTRA_EMAIL,reportname);
			//设置标题内容
			intent2.putExtra(android.content.Intent.EXTRA_SUBJECT,reportname);
			//设置邮件文本内容
			intent2.putExtra(android.content.Intent.EXTRA_TEXT,reportname);
			if (outfile != null && outfile.exists()) {
				Log.d("file1", outfile.toString());

				Uri u = Uri.fromFile(outfile);
				Log.d("uri", u.toString());
				intent2.putExtra(Intent.EXTRA_STREAM, u);
			} else{
				Log.d("file1", "not exist");
			}
			//intent.putExtra("BITMAP", screenshot.takeScreenShot(MainActivity_1.this));
			// intent2.putExtra(Intent.EXTRA_SUBJECT, "opentasks");
			//  intent2.putExtra(Intent.EXTRA_TEXT, "opentasks");
			// intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			Log.d("export file1", "ready");
			context.startActivity(Intent.createChooser(intent2, reportname));
			Log.d("export file1", "done");

		}else{
			Toast.makeText(context, "记录为空", Toast.LENGTH_SHORT).show();
		}
	}

	public static void exportListToFile(Context context,Cursor c,String listname){

		final String[] taskitemlist={
				TaskData.TdDB.TASK_NAME,
				TaskData.TdDB.TASK_USERNICKNAME,
				TaskData.TdDB.TASK_PRIORITY,

				TaskData.TdDB.TASK_CREATEDTIME,
				TaskData.TdDB.TASK_STARTEDTIME,
				TaskData.TdDB.TASK_DEADLINE,
				TaskData.TdDB.TASK_DURATION,
				TaskData.TdDB.TASK_PROGRESS,
				TaskData.TdDB.TASK_STATUS,
				TaskData.TdDB.TASK_REMARKS
				//TaskData.TdDB.TASK_STATUS,
				//TaskData.TdDB.TASK_FINISHED,
				//TaskData.TdDB.TASK_DELAYED
		};

		final String[] titlenamelist={
				//"任务???","责任???","智能分类","智能排序","创建时间","???始时???","???后期???","预计用时(hr)","完成%","完成状???","备注"
				"任务名称","责任人","智能分类","创建时间","开始时间","最后期限","预计用时(hr)","完成%","完成状态","备注"
		};

		SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd mm:ss");
		String reportname ="[目标达]"+listname+formatter.format(new Date());

		if (c.getCount()>0){

			File outfile=new ExportListToExcel(context,TaskData.TdDB.TABLE_NAME_TaskMain,
					c,
					titlenamelist,
					taskitemlist)
					.writeExcel(reportname);
			Intent intent2 = new Intent(Intent.ACTION_SEND);
			intent2.setType("text/*");

			if (outfile != null && outfile.exists()) {
				Log.d("file1", outfile.toString());

				Uri u = Uri.fromFile(outfile);
				Log.d("uri", u.toString());
				intent2.putExtra(Intent.EXTRA_STREAM, u);
			} else{
				Log.d("file1", "not exist");
			}

			context.startActivity(Intent.createChooser(intent2, listname));

		}
	}

	public static void exportListToFile_NewApplication(Context context,Cursor c,String listname){

		final String[] taskitemlist={
				TaskData.TdDB.TASK_NAME,
				//TaskData.TdDB.TASK_USER,
				TaskData.TdDB.TASK_PRIORITY,
				TaskData.TdDB.TASK_SEQUENCENO,
				TaskData.TdDB.TASK_CREATEDTIME,
				TaskData.TdDB.TASK_STARTEDTIME,
				TaskData.TdDB.TASK_DEADLINE,
				TaskData.TdDB.TASK_DURATION,
				TaskData.TdDB.TASK_PROGRESS,
				TaskData.TdDB.TASK_STATUS,
				TaskData.TdDB.TASK_REMARKS
				//TaskData.TdDB.TASK_STATUS,
				//TaskData.TdDB.TASK_FINISHED,
				//TaskData.TdDB.TASK_DELAYED
		};

		final String[] titlenamelist={
				//"任务???","责任???","智能分类","智能排序","创建时间","???始时???","???后期???","预计用时(hr)","完成%","完成状???","备注"
				"任务名称","智能分类","智能排序","创建时间","开始时间","最后期限","预计用时(hr)","完成%","完成状态","备注"
		};

		SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd mm:ss");
		String reportname  =listname+formatter.format(new Date());

		if (c.getCount()>0){

			File outfile=new ExportListToExcel(context,TaskData.TdDB.TABLE_NAME_TaskMain,
					c,
					titlenamelist,
					taskitemlist)
					.writeExcel(reportname);
			Intent intent2 = new Intent(Intent.ACTION_SEND);
			intent2.setType("text/*");
			intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			if (outfile != null && outfile.exists()) {
				Log.d("file1", outfile.toString());

				Uri u = Uri.fromFile(outfile);
				Log.d("uri", u.toString());
				intent2.putExtra(Intent.EXTRA_STREAM, u);
			} else{
				Log.d("file1", "not exist");
			}

			context.startActivity(Intent.createChooser(intent2, listname));

		}
	}

	public static void exportToReport(Context context,int reporttype){
		final String[] taskitemlist={
				TaskData.TdDB.TASK_NAME,
				TaskData.TdDB.TASK_USERNICKNAME,
				TaskData.TdDB.TASK_ASSESSMENT,
				//TaskData.TdDB.TASK_SEQUENCENO,
				TaskData.TdDB.TASK_CREATEDTIME,
				TaskData.TdDB.TASK_STARTEDTIME,
				TaskData.TdDB.TASK_DEADLINE,
				TaskData.TdDB.TASK_DURATION,
				TaskData.TdDB.TASK_PROGRESS,
				TaskData.TdDB.TASK_STATUS
				//TaskData.TdDB.TASK_STATUS,
				//TaskData.TdDB.TASK_FINISHED,
				//TaskData.TdDB.TASK_DELAYED
		};

		final String[] titlenamelist={
				"任务名称","责任人","重要紧急","创建时间","开始时间","最后期限","预计用时(hr)","完成%","完成状态"
		};

		SimpleDateFormat formatter = new SimpleDateFormat ("yy-MM-dd HH:mm");
		Date curDate = new Date();//获取当前时间
		String curTime = formatter.format(curDate);

		final Calendar cal=Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int week = cal.get(Calendar.WEEK_OF_YEAR);
		//int weekofmonth= cal.get(Calendar.mo);
		int weekday= cal.get(Calendar.DAY_OF_WEEK);
		int day=cal.get(Calendar.DAY_OF_MONTH);

		long countTimeData = 0;
		String reportname = null;
		String str_today = year + "-" + (month+1) + "-" + day+" "+"00"+":"+"00";
		String str_thismonth = year + "-" + (month+1) + "-" + 1+" "+"00"+":"+"00";
		String str_thisweek;
		if (weekday==1){
			str_thisweek=year + "-"+ (month+1) + "-"+(day-6)+" "+"00"+":"+"00";
		}else{
			str_thisweek=year + "-"+ (month+1) + "-"+(day-weekday)+" "+"00"+":"+"00";
		}
		Log.d("this week", str_thisweek);
		long todayTimeData = TimeData.changeStrToTime_YYYY(str_today);
		long thismonthTimeData = TimeData.changeStrToTime_YYYY(str_thismonth);
		long thisweekTimeData = TimeData.changeStrToTime_YYYY(str_thisweek);

		switch (reporttype){
			case 1: countTimeData=todayTimeData;
				reportname="日报"+year + "年" + (month+1) + "月" + day+"日";
				Log.d("reportname", reportname);
				break;
			case 2: countTimeData=thisweekTimeData;
				reportname="周报"+year + "年" +week+"周";
				Log.d("reportname", reportname);
				break;
			case 3: countTimeData=thismonthTimeData;
				reportname="月报"+year + "年" + (month+1)+"月";
				Log.d("reportname", reportname);
				break;
			default:break;

		}


		Cursor c = TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+
						" where "+TaskData.TdDB.TASK_USER+"=? and "+
						TaskData.TdDB.TASK_DEADLINETIMEDATA+">?",
				new String[]{TaskData.user,String.valueOf(countTimeData)});

		//Cursor c=TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+TaskData.TdDB.TASK_STATUS+"=?"+" order by "+TaskData.TdDB.TASK_SEQUENCENO+" asc", new String[]{"open"});
		// TODO Auto-generated method stub
		if (c.getCount()>0){

			File outfile=new ExportListToExcelReport(
					context,
					TaskData.TdDB.TABLE_NAME_TaskMain,
					c,
					titlenamelist,
					taskitemlist)
					.writeExcel(reportname);
			Intent intent2 = new Intent(Intent.ACTION_SEND);
			intent2.setType("text/*");

			if (outfile != null && outfile.exists()) {
				Log.d("file1", outfile.toString());

				Uri u = Uri.fromFile(outfile);
				Log.d("uri", u.toString());
				intent2.putExtra(Intent.EXTRA_STREAM, u);
			} else{
				Log.d("file1", "not exist");
			}
			//intent.putExtra("BITMAP", screenshot.takeScreenShot(MainActivity_1.this));
			// intent2.putExtra(Intent.EXTRA_SUBJECT, "opentasks");
			//  intent2.putExtra(Intent.EXTRA_TEXT, "opentasks");
			// intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			Log.d("export file1", "ready");
			context.startActivity(Intent.createChooser(intent2, reportname));
			Log.d("export file1", "done");

		}else{
			Toast.makeText(context, "记录为空", Toast.LENGTH_SHORT).show();
		}
	}

	public File exportToFile(){
		File outfile=createOutputFile(mTb);
		WritableWorkbook wwb = createWritableWorkbook(outfile);
		writeToWritableWorkBook(wwb);
		closeWritableWorkbook(wwb);
		return outfile;
	}

	public void writeToWritableWorkBook(WritableWorkbook wwb) {


		final String[] recorditemlist_m={
				//TaskData.TdDB.RECORD_TASKNAME,
				"username",
				"name",
				"content",
				"createdtime",
				"deadlinetime"
		};

		final String[] titlenamelist_m={
				"责任人","备忘名","备忘内容","创建时间","最后期限"
		};


		int r_m = 0;
		//String seekSN = cur.getString(cur.getColumnIndex(TaskData.TdDB.TASK_SN));
		//writeExcel(mTb);

		Cursor cur_m = DataSupport.findBySQL("select * from memo"+" where "+"username"+"="+"'"+TaskData.user+"';");;
		cur_m.moveToFirst();

		int numcols_m = recorditemlist_m.length;
		int numrows_m = cur_m.getCount();

		int colWidth_m[]={1,1,1,1,1};
		String records_m[][] = new String[numrows_m + 1][numcols_m];// 存放答案，多一行标题行

		do{

			for (int c_m = 0; c_m < numcols_m; c_m++) {
				if (r_m == 0) {
					records_m[r_m][c_m] = titlenamelist_m[c_m];
					records_m[r_m + 1][c_m] = cur_m.getString(cur_m.getColumnIndex(recorditemlist_m[c_m]));
					Log.d("pos", "row"+r_m+" col "+c_m);
					int cw = titlenamelist_m[c_m].length()+getChineseNum(titlenamelist_m[c_m]);
					if (cw>colWidth_m[c_m]){
						colWidth_m[c_m]=cw;
					}
				} else {
					String value=cur_m.getString(cur_m.getColumnIndex(recorditemlist_m[c_m]));
					records_m[r_m + 1][c_m] = value;
					Log.d("pos", "row"+r_m+" col "+c_m+" value:"+value);
					//records_m[r_m + 1][c_m] = cur_m.getString(cur_m.getColumnIndex(recorditemlist_m[c_m]));
					if (value!=null){
						int cw = value.length()+getChineseNum(value);
						if (cw>colWidth_m[c_m]){
							colWidth_m[c_m]=cw;
						}
					}
				}

			}

			r_m++;

			//cur_m.close();
		} while (cur_m.moveToNext());

		if (wwb != null) {
			// 创建一个可写入的工作表
			// Workbook的createSheet方法有两个参数，第一个是工作表的名称，第二个是工作表在工作薄中的位置
			WritableSheet ws = wwb.createSheet("备忘录", 0);

			// 下面开始添加单元格
			for (int i = 0; i < numrows_m + 1; i++) {
				for (int j = 0; j < numcols_m; j++) {
					// 这里需要注意的是，在Excel中，第一个参数表示列，第二个表示行
					Label labelC = new Label(j, i, records_m[i][j]);
					//      Log.i("Newvalue" + i + " " + j, records[i][j]);
					try {
						// 将生成的单元格添加到工作表中
						ws.addCell(labelC);
						ws.setColumnView(j, colWidth_m[j]);
					} catch (RowsExceededException e) {
						e.printStackTrace();
					} catch (WriteException e) {
						e.printStackTrace();
					}

				}
			}
		}
	}

	public File createOutputFile(String tableName) {
		String fileName;
		File sdFileDir=Environment.getExternalStorageDirectory();
		fileName = sdFileDir+"/DCIM/" + tableName + ".xls";

		//fileName = FileUtils.getDiskCacheDirPath()+"/DCIM/" + tableName + ".xls";
		File outexcelfile=new File(fileName);
		return outexcelfile;
	}

	public WritableWorkbook createWritableWorkbook(File outexcelfile) {
		WritableWorkbook wwb = null;

		try {
			// 首先要使用Workbook类的工厂方法创建一个可写入的工作薄(Workbook)对象
			wwb = Workbook.createWorkbook(outexcelfile);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return wwb;
	}

	public void closeWritableWorkbook(WritableWorkbook wwb) {
		try {
			// 从内存中写入文件中
			wwb.write();
			// 关闭资源，释放内存
			wwb.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		}
	}

	int getChineseNum(String context){    ///统计context中是汉字的个数
		int lenOfChinese=0;
		Pattern p = Pattern.compile("[\u4e00-\u9fa5]");    //汉字的Unicode编码范围
		Matcher m = p.matcher(context);
		while(m.find()){
			lenOfChinese++;
		}
		return lenOfChinese;
	}

}