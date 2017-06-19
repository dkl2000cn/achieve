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
				//"����???","����???","���ܷ���","��������","����ʱ��","???ʼʱ???","???����???","Ԥ����ʱ(hr)","���%","���״???","��ע"
				"��������","������","���ܷ���","����ʱ��","��ʼʱ��","�������","Ԥ����ʱ(hr)","���%","���״̬","��ע"
		};

		SimpleDateFormat formatter = new SimpleDateFormat ("yy-MM-dd HH:mm");
		Date curDate = new Date();//��ȡ��ǰʱ��
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
				reportname="�ձ�"+year + "��" + (month+1) + "��" + day+"��";
				Log.d("reportname", reportname);
				break;
			case 2: countTimeData=thisweekTimeData;
				reportname="�ܱ�"+year + "��" +week+"��";
				Log.d("reportname", reportname);
				break;
			case 3: countTimeData=thismonthTimeData;
				reportname="�±�"+year + "��" + (month+1)+"��";
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
			//���ñ�������
			intent2.putExtra(android.content.Intent.EXTRA_SUBJECT,reportname);
			//�����ʼ��ı�����
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
			Toast.makeText(context, "��¼Ϊ��", Toast.LENGTH_SHORT).show();
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
				//"����???","����???","���ܷ���","��������","����ʱ��","???ʼʱ???","???����???","Ԥ����ʱ(hr)","���%","���״???","��ע"
				"��������","������","���ܷ���","����ʱ��","��ʼʱ��","�������","Ԥ����ʱ(hr)","���%","���״̬","��ע"
		};

		SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd mm:ss");
		String reportname ="[Ŀ���]"+listname+formatter.format(new Date());

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
				//"����???","����???","���ܷ���","��������","����ʱ��","???ʼʱ???","???����???","Ԥ����ʱ(hr)","���%","���״???","��ע"
				"��������","���ܷ���","��������","����ʱ��","��ʼʱ��","�������","Ԥ����ʱ(hr)","���%","���״̬","��ע"
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
				"��������","������","��Ҫ����","����ʱ��","��ʼʱ��","�������","Ԥ����ʱ(hr)","���%","���״̬"
		};

		SimpleDateFormat formatter = new SimpleDateFormat ("yy-MM-dd HH:mm");
		Date curDate = new Date();//��ȡ��ǰʱ��
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
				reportname="�ձ�"+year + "��" + (month+1) + "��" + day+"��";
				Log.d("reportname", reportname);
				break;
			case 2: countTimeData=thisweekTimeData;
				reportname="�ܱ�"+year + "��" +week+"��";
				Log.d("reportname", reportname);
				break;
			case 3: countTimeData=thismonthTimeData;
				reportname="�±�"+year + "��" + (month+1)+"��";
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
			Toast.makeText(context, "��¼Ϊ��", Toast.LENGTH_SHORT).show();
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
				"������","������","��������","����ʱ��","�������"
		};


		int r_m = 0;
		//String seekSN = cur.getString(cur.getColumnIndex(TaskData.TdDB.TASK_SN));
		//writeExcel(mTb);

		Cursor cur_m = DataSupport.findBySQL("select * from memo"+" where "+"username"+"="+"'"+TaskData.user+"';");;
		cur_m.moveToFirst();

		int numcols_m = recorditemlist_m.length;
		int numrows_m = cur_m.getCount();

		int colWidth_m[]={1,1,1,1,1};
		String records_m[][] = new String[numrows_m + 1][numcols_m];// ��Ŵ𰸣���һ�б�����

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
			// ����һ����д��Ĺ�����
			// Workbook��createSheet������������������һ���ǹ���������ƣ��ڶ����ǹ������ڹ������е�λ��
			WritableSheet ws = wwb.createSheet("����¼", 0);

			// ���濪ʼ��ӵ�Ԫ��
			for (int i = 0; i < numrows_m + 1; i++) {
				for (int j = 0; j < numcols_m; j++) {
					// ������Ҫע����ǣ���Excel�У���һ��������ʾ�У��ڶ�����ʾ��
					Label labelC = new Label(j, i, records_m[i][j]);
					//      Log.i("Newvalue" + i + " " + j, records[i][j]);
					try {
						// �����ɵĵ�Ԫ����ӵ���������
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
			// ����Ҫʹ��Workbook��Ĺ�����������һ����д��Ĺ�����(Workbook)����
			wwb = Workbook.createWorkbook(outexcelfile);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return wwb;
	}

	public void closeWritableWorkbook(WritableWorkbook wwb) {
		try {
			// ���ڴ���д���ļ���
			wwb.write();
			// �ر���Դ���ͷ��ڴ�
			wwb.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		}
	}

	int getChineseNum(String context){    ///ͳ��context���Ǻ��ֵĸ���
		int lenOfChinese=0;
		Pattern p = Pattern.compile("[\u4e00-\u9fa5]");    //���ֵ�Unicode���뷶Χ
		Matcher m = p.matcher(context);
		while(m.find()){
			lenOfChinese++;
		}
		return lenOfChinese;
	}

}