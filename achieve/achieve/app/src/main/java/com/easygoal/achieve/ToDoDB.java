package com.easygoal.achieve;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class ToDoDB extends SQLiteOpenHelper {

	public static String DATABASE_NAME = "todo_db";
	private final static int DATABASE_VERSION = 3;
	/* 建立table2 */
	public final String TABLE_NAME_TaskMain = "task_main";
	public final static String TASK_ID = "_id";
	public final static String TASK_SN = "_sn";
	public final static String TASK_USER = "username";
	public final static String TASK_USERNICKNAME = "nickname";
	public final static String TASK_CREATOR = "task_creator";
	public final static String TASK_CREATORNICKNAME = "nickname_creator";
	public final static String TASK_OWNER = "task_owner";
	public final static String TASK_OWNERNICKNAME = "nickname_owner";
	public final static String TASK_USERGROUP = "task_group";
	public final static String TASK_NAME = "task_name";
	public final static String TASK_CREATEDTIME = "task_createdtime";
	public final static String TASK_CREATEDTIMEDATA = "task_createdtimedata";
	public final static String TASK_DESCRIPTION = "task_description";
	public final static String TASK_IMPORTANCE = "task_importance";
	public final static String TASK_URGENCY = "task_urgency";
	public final static String TASK_ASSESSMENT = "task_assessment";
	public final static String TASK_PRIORITY = "task_priority";
	public final static String TASK_PASSION = "task_passion";
	public final static String TASK_DIFFICULTY = "task_difficulty";
	public final static String TASK_DURATION = "task_duration";
	public final static String TASK_DURATIONUNIT = "task_durationunit";
	public final static String TASK_REVENUE = "task_revenue";
	public final static String TASK_COST = "task_cost";
	public final static String TASK_BONUS = "task_bonus";
	public final static String TASK_CURRENCYUNIT = "task_currencyunit";
	public final static String TASK_STARTEDTIME = "task_startedtime";
	public final static String TASK_DEADLINE = "task_deadline";
	public final static String TASK_DEADLINETIMEDATA = "task_deadlinetimedata";
	public final static String TASK_DEADLINEDATE = "task_deadlinedate";
	public final static String TASK_DEADLINETIMESTAMP = "task_deadlinetimestamp";

	public final static String TASK_CYCLEUNIT = "task_cycleunit";
	/*
   public final static String TASK_WEEKDAYCYCLE = "task_weekdaycycle";
   public final static String TASK_DATECYCLE = "task_datecycle";
   */
	public final static String TASK_PROGRESS = "task_progress";
	public final static String TASK_REMINDER = "task_reminder";
	public final static String TASK_STATUS = "task_status";
	public final static String TASK_FINISHED = "task_finished";
	public final static String TASK_DELAYED = "task_delayed";
	public final static String TASK_DELAYEDDAYS = "task_delayeddays";
	public final static String TASK_USEDTIME = "task_usedtime";
	public final static String TASK_CLOSEDTIME = "task_closedtime";
	public final static String TASK_CLOSEDATE = "task_closeddate";
	public final static String TASK_CLOSEDTIMEDATA = "task_closedtimedata";
	public final static String TASK_IMPORTANCEDETAIL = "task_importancedetail";
	public final static String TASK_URGENCYDETAIL = "task_urgencydetail";
	public final static String TASK_PASSIONDETAIL = "task_passiondetail";
	public final static String TASK_DIFFICULTYDETAIL = "task_difficultydetail";
	public final static String SUM_CONTRIBUTION = "sum_contribution";
	public final static String SUM_ACCOMPLISHED = "sum_accomplished";
	public final static String SUM_ACHIEVED = "sum_achieved";
	public final static String SUM_ENJOYMENT = "sum_enjoyment";
	public final static String SUM_EXPERIENCE = "sum_experience";
	public final static String SUM_HORNOR = "sum_hornor";
	public final static String TASK_RESULTSATISFICATION = "task_resultsatisfication";
	public final static String TASK_RESULTCOMMENT = "task_resultcomment";
	public final static String TASK_CLOSEDCOMMENT = "task_closedcomment";
	public final static String TASK_REMARKS = "task_remarks";
	public final static String TASK_LESSON = "task_lesson";
	public final static String TASK_HISTORY = "task_history";
	public final static String TASK_RECORDCOUNT = "task_recordcount";
	public final static String TASK_SEQUENCE = "task_sequence";
	public final static String TASK_SEQUENCENO = "task_sequenceno";
	public final static String TASK_REMINDERNO = "task_reminderno";
	public final static String TASK_MODIFIED = "task_modified";
	/* 建立table2 */
	public final static String TABLE_NAME_TaskRecord = "task_rec";
	public final static String RECORD_ID = "id";
	public final static String RECORD_SN = "r_sn";
	public final static String RECORD_TASKNAME = "task_name";
	public final static String RECORD_CONTENT = "record_content";
	public final static String RECORD_TASKID = "_id";
	public final static String RECORD_TASKID_NO = "_taskid_no";
	public final static String RECORD_TIME = "record_time";
	public final static String RECORD_AUTHOR = "record_author";
	public final static String RECORD_CREATEDTIME = "record_createdtime";
	public final static String RECORD_DEADLINE = "record_deadline";
	public final static String RECORD_CLOSEDTIME = "record_closedtime";
	public final static String RECORD_WEIGHT = "record_weight";
	public final static String RECORD_PROGRESS = "record_progress";
	public final static String RECORD_COMMENTS = "record_comments";
	public final static String RECORD_PARENT = "record_parent";
	public final static String RECORD_PREDECESSOR = "record_predecessor";
	public final static String RECORD_SUBLEVEL = "record_sublevel";
	public final static String RECORD_USEDTIME = "record_usedtime";
	public final static String RECORD_TIMEUNIT = "record_timeunit";
	public final static String RECORD_CHANGED = "record_changed";
	public final static String RECORD_DETAILS = "record_details";
	public final static String RECORD_STATUS = "record_status";
	public final static String RECORD_DONE = "record_done";
	public final static String RECORD_ACHIEVED = "record_achieved";
	public final static String RECORD_ENJOYMENT = "record_enjoyment";
	public final static String RECORD_EXPERIENCE = "record_experience";
	public final static String RECORD_TOTALCOUNT = "record_totalcount";
	public final static String RECORD_TYPE = "record_type";
	public final static String RECORD_REMARKS = "record_remarks";
	public final static String RECORD_MODIFIED = "record_modified";

	String Tags = "ToDoDB";

	public ToDoDB(Context context, String name, CursorFactory factory,
				  int version) {
		super(context, name, factory, DATABASE_VERSION);
		DATABASE_NAME = name;
		Log.d(Tags, "数据库成功启动" + name);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		//db.execSQL("PRAGMA foreign_keys=ON");
		
		/* 建立table */
		String sql_main = "CREATE TABLE " +
				TABLE_NAME_TaskMain + " (" +
				TASK_ID + " INTEGER primary key  autoincrement, " +
				TASK_SN + " text  , " +
				TASK_NAME + " text , " +
				TASK_USER + " text , " +
				TASK_USERNICKNAME + " text , " +
				TASK_CREATOR + " text , " +
				TASK_CREATORNICKNAME + " text , " +
				TASK_OWNER + " text , " +
				TASK_OWNERNICKNAME + " text , " +
				TASK_USERGROUP + " text , " +
				TASK_CREATEDTIME + " text, " +
				TASK_CREATEDTIMEDATA + " long, " +
				TASK_DESCRIPTION + " text, " +
				TASK_IMPORTANCE + " text, " +
				TASK_IMPORTANCEDETAIL + " text, " +
				TASK_URGENCY + " text, " +
				TASK_URGENCYDETAIL + " text, " +
				TASK_ASSESSMENT + " text, " +
				TASK_PRIORITY + " text, " +
				TASK_PASSION + " text, " +
				TASK_PASSIONDETAIL + " text, " +
				TASK_DIFFICULTY + " text, " +
				TASK_DIFFICULTYDETAIL + " text, " +
				TASK_DURATION + " text," +
				TASK_DURATIONUNIT + " text," +
				TASK_REVENUE + " text, " +
				TASK_COST + " text, " +
				TASK_BONUS + " text, " +
				TASK_CURRENCYUNIT + " text, " +
				TASK_STARTEDTIME + " text, " +
				TASK_CYCLEUNIT + " text, " +
				TASK_DEADLINE + " text, " +
				TASK_DEADLINEDATE + " text, " +
				TASK_DEADLINETIMEDATA + " long, " +
				TASK_PROGRESS + " text, " +
				TASK_USEDTIME + " text, " +
				TASK_REMINDER + " text, " +
				TASK_STATUS + " text, " +
				TASK_FINISHED + " text, " +
				TASK_DELAYED + " text, " +
				TASK_DELAYEDDAYS + " integer, " +
				SUM_CONTRIBUTION + " text, " +
				SUM_ACCOMPLISHED + " text, " +
				SUM_ACHIEVED + " text, " +
				SUM_ENJOYMENT + " text, " +
				SUM_EXPERIENCE + " text, " +
				SUM_HORNOR + " text, " +
				TASK_CLOSEDTIME + " text, " +
				TASK_CLOSEDATE + " long, " +
				TASK_CLOSEDTIMEDATA + " long, " +
				TASK_CLOSEDCOMMENT + " text, " +
				TASK_RESULTSATISFICATION + " text, " +
				TASK_RESULTCOMMENT + " text, " +
				TASK_RECORDCOUNT + " integer, " +
				TASK_SEQUENCE + " integer, " +
				TASK_SEQUENCENO + " integer, " +
				TASK_REMINDERNO + " integer, " +
				TASK_MODIFIED + " text, " +
				TASK_REMARKS + " text, " +
				TASK_LESSON + " text,  " +
				TASK_HISTORY + " text  " +
				//TASK_REMARKS2_ + " text "+
				//TASK_REMARKS + " text "+
				");";

		if (IsTableExist(db, TaskData.TdDB.TABLE_NAME_TaskMain)) {
			String sql = "DROP TABLE IF EXISTS " + TABLE_NAME_TaskMain;
			//db.execSQL(sql);
			//db.execSQL(sql_main);

			//Log.d(Tags,"rebuild main table");
		} else {
			db.execSQL(sql_main);
			Log.d(Tags, "create main table done");
		}
		/* 建立table */
		String sql_createRecord = "CREATE TABLE  " +
				TABLE_NAME_TaskRecord + " (" +
				RECORD_ID + " INTEGER primary key autoincrement, " +
				RECORD_SN + " text, " +
				TASK_SN + " text, " +
				RECORD_TASKID + " INTEGER " + " REFERENCES " + TABLE_NAME_TaskMain + " (" + TASK_ID + " ) ON DELETE CASCADE,  " +
				RECORD_TASKID_NO + " text, " +
				RECORD_TIME + " text, " +
				RECORD_AUTHOR + " text, " +
				TASK_USER + " text , " +
				TASK_OWNER + " text , " +
				TASK_USERGROUP + " text , " +
				RECORD_CREATEDTIME + " text, " +
				RECORD_DEADLINE + " text, " +
				RECORD_CLOSEDTIME + " text, " +
				RECORD_WEIGHT + " text, " +
				RECORD_CONTENT + " text, " +
				RECORD_PROGRESS + " text, " +
				RECORD_COMMENTS + " text, " +
				RECORD_STATUS + " text, " +
				RECORD_DONE + " text, " +
				RECORD_PARENT + " text, " +
				RECORD_PREDECESSOR + " text, " +
				RECORD_SUBLEVEL + " text, " +
				RECORD_USEDTIME + " text, " +
				RECORD_TIMEUNIT + " text, " +
				RECORD_CHANGED + " text, " +
				RECORD_DETAILS + " text, " +
				RECORD_ACHIEVED + " text, " +
				RECORD_ENJOYMENT + " text, " +
				RECORD_EXPERIENCE + " text, " +
				RECORD_TOTALCOUNT + " text, " +
				RECORD_TYPE + " text, " +
				RECORD_MODIFIED + " text, " +
				RECORD_REMARKS + " text " +
				//RECORD_REMARKS_C     + " text "+
				//RECORD_REMARKS_M     + " text "+
				//"FOREIGN KEY ("+TASK_SN+") REFERENCES "+TABLE_NAME_TaskMain+" ("+TASK_SN +")" +
				");";

		//"FOREIGN KEY" +" ("+RECORD_TASKID +")" + " REFERENCES " + TABLE_NAME_TaskMain+" ("+TASK_ID+" ) ON UPDATE CASCADE ON DELETE CASCADE  "+	 ");";
		String sql_trigger_delete = "CREATE TRIGGER aaa AFTER DELETE ON " +
				TABLE_NAME_TaskMain +
				" FOR EACH ROW BEGIN " +
				"DELETE from taskrec WHERE _id = OLD._id; " +
				"End;";
		String sql_note = "PRAGMA foreign_keys=ON;";
		//db.execSQL("select * from "+TaskData.TdDB.TABLE_NAME_TaskRecord);
		//Log.d("create record table", "creat record table done");
		if (IsTableExist(db, TaskData.TdDB.TABLE_NAME_TaskRecord)) {
			String sql = "DROP TABLE IF EXISTS " + TABLE_NAME_TaskRecord;
			//db.execSQL(sql);
			//db.execSQL(sql_createRecord);
			//db.execSQL(sql_note);
			//db.execSQL(sql_trigger_delete);
			//Log.d(Tags,"rebuild recordtable");
			//Log.d(Tags,"find "+db.findEditTable("task_main")+"  "+db.findEditTable("task_rec"));  

		} else {
			db.execSQL(sql_createRecord);

			Log.d(Tags, "creat record table done\n" + sql_note);

		}

	}

	public static boolean IsTableExist(SQLiteDatabase db, String tablename) {
		boolean result = false;
		if (tablename == null) {
			return false;
		}

		Cursor cursor = null;
		try {
			String sql = "select count(*) as c from Sqlite_master where type ='table' and name ='" + tablename.trim() + "'";
			cursor = db.rawQuery(sql, null);
			if (cursor.moveToNext()) {
				int count = cursor.getInt(0);
				if (count > 0) {
					result = true;
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return result;
	}


	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		Log.i( "tag", " onUpgrade db" +oldVersion+newVersion);
        //获取到旧表中的字段【也就是新表中保留的字段】
		  reCreate(db);
		  TaskData.initflag=2;
		/*
		{
			String columns1 = getColumnNames(db, TaskData.TdDB.TABLE_NAME_TaskMain);
			//升级
			updateTable(db, TaskData.TdDB.TABLE_NAME_TaskMain, columns1);
			Log.i("tag", " onUpgrade db" + TaskData.TdDB.TABLE_NAME_TaskMain + "done");
			String columns2 = getColumnNames(db, TaskData.TdDB.TABLE_NAME_TaskRecord);
			//升级
			updateTable(db, TaskData.TdDB.TABLE_NAME_TaskRecord, columns2);
			Log.i("tag", " onUpgrade db" + TaskData.TdDB.TABLE_NAME_TaskRecord + "done");
		}*/

	}

	private void reCreate(SQLiteDatabase db){
		String sql_main = "CREATE TABLE " +
				TABLE_NAME_TaskMain + " (" +
				TASK_ID + " INTEGER primary key  autoincrement, " +
				TASK_SN + " text  , " +
				TASK_NAME + " text , " +
				TASK_USER + " text , " +
				TASK_USERNICKNAME + " text , " +
				TASK_CREATOR + " text , " +
				TASK_CREATORNICKNAME + " text , " +
				TASK_OWNER + " text , " +
				TASK_OWNERNICKNAME + " text , " +
				TASK_USERGROUP + " text , " +
				TASK_CREATEDTIME + " text, " +
				TASK_CREATEDTIMEDATA + " long, " +
				TASK_DESCRIPTION + " text, " +
				TASK_IMPORTANCE + " text, " +
				TASK_IMPORTANCEDETAIL + " text, " +
				TASK_URGENCY + " text, " +
				TASK_URGENCYDETAIL + " text, " +
				TASK_ASSESSMENT + " text, " +
				TASK_PRIORITY + " text, " +
				TASK_PASSION + " text, " +
				TASK_PASSIONDETAIL + " text, " +
				TASK_DIFFICULTY + " text, " +
				TASK_DIFFICULTYDETAIL + " text, " +
				TASK_DURATION + " text," +
				TASK_DURATIONUNIT + " text," +
				TASK_REVENUE + " text, " +
				TASK_COST + " text, " +
				TASK_BONUS + " text, " +
				TASK_CURRENCYUNIT + " text, " +
				TASK_STARTEDTIME + " text, " +
				TASK_CYCLEUNIT + " text, " +
				TASK_DEADLINE + " text, " +
				TASK_DEADLINEDATE + " text, " +
				TASK_DEADLINETIMEDATA + " long, " +
				TASK_PROGRESS + " text, " +
				TASK_USEDTIME + " text, " +
				TASK_REMINDER + " text, " +
				TASK_STATUS + " text, " +
				TASK_FINISHED + " text, " +
				TASK_DELAYED + " text, " +
				TASK_DELAYEDDAYS + " integer, " +
				SUM_CONTRIBUTION + " text, " +
				SUM_ACCOMPLISHED + " text, " +
				SUM_ACHIEVED + " text, " +
				SUM_ENJOYMENT + " text, " +
				SUM_EXPERIENCE + " text, " +
				SUM_HORNOR + " text, " +
				TASK_CLOSEDTIME + " text, " +
				TASK_CLOSEDATE + " long, " +
				TASK_CLOSEDTIMEDATA + " long, " +
				TASK_CLOSEDCOMMENT + " text, " +
				TASK_RESULTSATISFICATION + " text, " +
				TASK_RESULTCOMMENT + " text, " +
				TASK_RECORDCOUNT + " integer, " +
				TASK_SEQUENCE + " integer, " +
				TASK_SEQUENCENO + " integer, " +
				TASK_REMINDERNO + " integer, " +
				TASK_MODIFIED + " text, " +
				TASK_REMARKS + " text, " +
				TASK_LESSON + " text,  " +
				TASK_HISTORY + " text  " +
				//TASK_REMARKS2_ + " text "+
				//TASK_REMARKS + " text "+
				");";

		if (IsTableExist(db, TaskData.TdDB.TABLE_NAME_TaskMain)) {
			String sql = "DROP TABLE IF EXISTS " + TABLE_NAME_TaskMain;
			db.execSQL(sql);
			db.execSQL(sql_main);

			//Log.d(Tags,"rebuild main table");
		} else {
			db.execSQL(sql_main);
			Log.d(Tags, "create main table done");
		}
		/* 建立table */
		String sql_createRecord = "CREATE TABLE  " +
				TABLE_NAME_TaskRecord + " (" +
				RECORD_ID + " INTEGER primary key autoincrement, " +
				RECORD_SN + " text, " +
				TASK_SN + " text, " +
				RECORD_TASKID + " INTEGER " + " REFERENCES " + TABLE_NAME_TaskMain + " (" + TASK_ID + " ) ON DELETE CASCADE,  " +
				RECORD_TASKID_NO + " text, " +
				RECORD_TIME + " text, " +
				RECORD_AUTHOR + " text, " +
				TASK_USER + " text , " +
				TASK_OWNER + " text , " +
				TASK_USERGROUP + " text , " +
				RECORD_CREATEDTIME + " text, " +
				RECORD_DEADLINE + " text, " +
				RECORD_CLOSEDTIME + " text, " +
				RECORD_WEIGHT + " text, " +
				RECORD_CONTENT + " text, " +
				RECORD_PROGRESS + " text, " +
				RECORD_COMMENTS + " text, " +
				RECORD_STATUS + " text, " +
				RECORD_DONE + " text, " +
				RECORD_PARENT + " text, " +
				RECORD_PREDECESSOR + " text, " +
				RECORD_SUBLEVEL + " text, " +
				RECORD_USEDTIME + " text, " +
				RECORD_TIMEUNIT + " text, " +
				RECORD_CHANGED + " text, " +
				RECORD_DETAILS + " text, " +
				RECORD_ACHIEVED + " text, " +
				RECORD_ENJOYMENT + " text, " +
				RECORD_EXPERIENCE + " text, " +
				RECORD_TOTALCOUNT + " text, " +
				RECORD_TYPE + " text, " +
				RECORD_MODIFIED + " text, " +
				RECORD_REMARKS + " text " +
				//RECORD_REMARKS_C     + " text "+
				//RECORD_REMARKS_M     + " text "+
				//"FOREIGN KEY ("+TASK_SN+") REFERENCES "+TABLE_NAME_TaskMain+" ("+TASK_SN +")" +
				");";

		//"FOREIGN KEY" +" ("+RECORD_TASKID +")" + " REFERENCES " + TABLE_NAME_TaskMain+" ("+TASK_ID+" ) ON UPDATE CASCADE ON DELETE CASCADE  "+	 ");";
		String sql_trigger_delete = "CREATE TRIGGER aaa AFTER DELETE ON " +
				TABLE_NAME_TaskMain +
				" FOR EACH ROW BEGIN " +
				"DELETE from taskrec WHERE _id = OLD._id; " +
				"End;";
		String sql_note = "PRAGMA foreign_keys=ON;";
		//db.execSQL("select * from "+TaskData.TdDB.TABLE_NAME_TaskRecord);
		//Log.d("create record table", "creat record table done");
		if (IsTableExist(db, TaskData.TdDB.TABLE_NAME_TaskRecord)) {
			String sql = "DROP TABLE IF EXISTS " + TABLE_NAME_TaskRecord;
			db.execSQL(sql);
			db.execSQL(sql_createRecord);
			//db.execSQL(sql_note);
			//db.execSQL(sql_trigger_delete);
			//Log.d(Tags,"rebuild recordtable");
			//Log.d(Tags,"find "+db.findEditTable("task_main")+"  "+db.findEditTable("task_rec"));

		} else {
			db.execSQL(sql_createRecord);
			Log.d(Tags, "creat record table done\n" + sql_note);

		}
	}


	@Override
	public void onOpen(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		super.onOpen(db);
		if (!db.isReadOnly()) { // Enable foreign key constraints
			db.execSQL("PRAGMA foreign_keys=ON;");
			Log.d(Tags, "open database set PRAGMA foreign_keys=ON;");
		}

	}
/*
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
		db.execSQL(sql);
		onCreate(db);
	}

	public Cursor select() {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.
				query(TABLE_NAME, null, null, null, null, null, null);
		return cursor;
	}*/


	private void updateTable(SQLiteDatabase db, String tableName, String columns) {

		try {

			db.beginTransaction();
			String reColumn = columns.substring(0, columns.length() - 1);
// rename the table
			String tempTable = tableName + "texp_temptable";
			String sql = "alter table " + tableName + " rename to " + tempTable;
			db.execSQL(sql);

// drop the oldtable
			String dropString = "drop table if exists " + tableName;
			db.execSQL(dropString);
// creat table
			String ss = "create table if not exists " + tableName + "(name text,pwd text,rpwd text)";
			db.execSQL(ss);
// load data
			String newStr = "rpwd";
			String newreColumn = reColumn + "," + newStr;
			String ins = "insert into " + tableName + " (" + newreColumn + ") " + "select " + reColumn + "" + " " + " from "
					+ tempTable;
			db.equals(ins);
			db.setTransactionSuccessful();
		} catch (Exception e) {
// TODO: handle exception
			Log.i("tag", e.getMessage());
		} finally {
			db.endTransaction();
		}


	}

	// 获取升级前表中的字段
	protected String getColumnNames(SQLiteDatabase db, String tableName) {
		StringBuffer sb = null;
		Cursor c = null;
		try {
			c = db.rawQuery("PRAGMA table_info(" + tableName + ")", null);
			if (null != c) {
				int columnIndex = c.getColumnIndex("name");
				if (-1 == columnIndex) {
					return null;
				}


				int index = 0;
				sb = new StringBuffer(c.getCount());
				for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
					sb.append(c.getString(columnIndex));
					sb.append(",");
					index++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (c != null) {
				c.close();
			}
		}


		return sb.toString();
	}
}
/*
	public long insert(String text) {    
		SQLiteDatabase db = this.getWritableDatabase();    //将新增的值放入ContentValues  
		ContentValues cv = new ContentValues();    
		cv.put(FIELD_TEXT, text);    
		long row = db.insert(TABLE_NAME, null, cv);    
		return row;  }    
	public void delete(int id) {    
		SQLiteDatabase db = this.getWritableDatabase();    
		String where = FIELD_id + " = ?";    
		String[] whereValue = { Integer.toString(id) };    
		db.delete(TABLE_NAME, where, whereValue);  }    
	public void update(int id, String text) {    
		SQLiteDatabase db = this.getWritableDatabase();    
		String where = FIELD_id + " = ?";    
		String[] whereValue = { Integer.toString(id) };       
		ContentValues cv = new ContentValues();    
		cv.put(FIELD_TEXT, text);    
		db.update(TABLE_NAME, cv, where, whereValue);  }}
		*/