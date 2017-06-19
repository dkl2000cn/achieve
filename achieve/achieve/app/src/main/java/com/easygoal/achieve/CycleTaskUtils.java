package com.easygoal.achieve;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Acer on 2017/4/20.
 */

public class CycleTaskUtils {
    private Cursor c;
    private String t_sn;
   // private String Tags="|CycleTaskUtils|";
    public CycleTaskUtils(Cursor c) {
        this.c=c;
        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            t_sn = c.getString(c.getColumnIndex(TaskData.TdDB.TASK_SN));
            Log.d("CycleTaskUtils",t_sn);
        }
    }



    public void addLastestTaskCycle(){
        String Tags = " addLastestTaskCycle";
        String cycle_unit;
        String deadline_str;
        String curTime_str = TaskTool.getCurTime();
        //Cursor cursor=TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain,null);

        if (c!=null&&c.getCount()>0) {
                 c.moveToFirst();
            //Log.d(Tags ,""+c.getCount());

                cycle_unit = c.getString(c.getColumnIndex(TaskData.TdDB.TASK_CYCLEUNIT));
                deadline_str = c.getString(c.getColumnIndex(TaskData.TdDB.TASK_DEADLINE));
                Log.d(Tags ,""+cycle_unit+" "+deadline_str);
                if (TimeData.TimeGap_YYMMDDHHSS(curTime_str, deadline_str) < 0) {

                    if (cycle_unit != null && cycle_unit.equals("单次")) {

                    } else {
                        if (cycle_unit != null&& !cycle_unit.equals("单次")) {
                            try {
                                Log.d(Tags ,""+c.getPosition());
                                copy2LastestTaskByCycleUnit();


                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

        }
        //c.requery();
        TaskData.adapterUpdate();
        Log.d(Tags , "done");
    }


    public void cancelCurrentTask(){
        String Tags = " AutoCancelCurrentTask";

        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            String taskhistory = c.getString(c.getColumnIndex(TaskData.TdDB.TASK_HISTORY));
            String tasklesson = c.getString(c.getColumnIndex(TaskData.TdDB.TASK_LESSON));
            String taskstatus = c.getString(c.getColumnIndex(TaskData.TdDB.TASK_STATUS));
            String t_id=c.getString(c.getColumnIndex(TaskData.TdDB.TASK_ID));
            Log.d(Tags,"old status"+taskstatus);
            String where = TaskData.TdDB.TASK_ID + " = ?";
            String[] whereValue = { t_id};

            if (tasklesson != null && !org.apache.http.util.TextUtils.isEmpty(tasklesson)) {
                tasklesson = tasklesson.trim() + "\n" + "到期未完成自动取消" + TaskData.tag_tasklesson;
            } else {
                tasklesson = "到期未完成自动取消" + TaskData.tag_tasklesson;
            }

            if (taskhistory != null && !org.apache.http.util.TextUtils.isEmpty(taskhistory)) {
                taskhistory = taskhistory.trim() + "\n" + "到期未完成自动取消" + TaskData.tag_taskhistory;
            } else {
                taskhistory = "到期未完成自动取消" + TaskData.tag_taskhistory;
            }
             ContentValues cv = new ContentValues();

            cv.put(TaskData.TdDB.TASK_LESSON, tasklesson);
            cv.put(TaskData.TdDB.TASK_HISTORY, taskhistory);
            cv.put(TaskData.TdDB.TASK_STATUS, "cancelled");
            cv.put(TaskData.TdDB.TASK_MODIFIED, TaskTool.getCurTime());
            cv.put(TaskData.TdDB.TASK_SEQUENCE, "");
            cv.put(TaskData.TdDB.TASK_SEQUENCENO,"");
            Log.d(Tags,cv.keySet().toString());
            long a = TaskData.db_TdDB.update(TaskData.TdDB.TABLE_NAME_TaskMain, cv, where, whereValue);
            Log.d(Tags,"cancelled done"+a+" t_id"+t_id);
        }else{
            Log.d(Tags,"cancelled"+c.getCount()+"not done");
        }
        c.requery();
        queryTaskDetail(c);
        //TaskData.adapterUpdate();

    }


    public void queryTaskDetail(Cursor c){
        String Tags = "queryTaskDetail";
        String cycle_unit;
        String deadline_str;
        String curTime_str = TaskTool.getCurTime();
        //Cursor cursor=TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain,null);

        if (c!=null&&c.getCount()>0) {
            c.moveToFirst();
            //Log.d(Tags ,""+c.getCount());
            for (int i=0;i<c.getColumnCount();i++){
                Log.d("colum"+i, c.getColumnName(i)+c.getString(i));
            }
        }
    }



    public void copy2LastestTaskByCycleUnit() throws ParseException {
        String Tags = "copy2LastestTaskByCycleUnit";

        if (c != null && c.getCount() > 0) {
            c.moveToFirst();

            final String[] oldcolumn_task = {
                    TaskData.TdDB.TASK_NAME,
                    TaskData.TdDB.TASK_DESCRIPTION,
                    TaskData.TdDB.TASK_IMPORTANCE,
                    TaskData.TdDB.TASK_IMPORTANCEDETAIL,
                    TaskData.TdDB.TASK_URGENCY,
                    TaskData.TdDB.TASK_URGENCYDETAIL,
                    TaskData.TdDB.TASK_ASSESSMENT,
                    TaskData.TdDB.TASK_PRIORITY,
                    TaskData.TdDB.TASK_PASSION,
                    TaskData.TdDB.TASK_PASSIONDETAIL,
                    TaskData.TdDB.TASK_DIFFICULTY,
                    TaskData.TdDB.TASK_DIFFICULTYDETAIL,
                    TaskData.TdDB.TASK_DURATION,
                    TaskData.TdDB.TASK_DURATIONUNIT,
                    TaskData.TdDB.TASK_DEADLINE,
                    TaskData.TdDB.TASK_CYCLEUNIT,
                    TaskData.TdDB.TASK_RECORDCOUNT,
                    TaskData.TdDB.TASK_STATUS,
                    TaskData.TdDB.TASK_USER,
                    TaskData.TdDB.TASK_USERNICKNAME,
                    TaskData.TdDB.TASK_CREATOR,
                    TaskData.TdDB.TASK_CREATORNICKNAME,
                    TaskData.TdDB.TASK_OWNER,
                    TaskData.TdDB.TASK_OWNERNICKNAME,
                    TaskData.TdDB.TASK_USERGROUP

            };
            List value = new ArrayList();

            if (c != null && c.getCount() > 0) {
                for (int i = 0; i < oldcolumn_task.length; i++) {
                    Map map=new HashMap();
                    map.put("key",oldcolumn_task[i]);
                    map.put("value",c.getString(c.getColumnIndex(oldcolumn_task[i])));
                    value.add(map);
                    //insertstr_column = insertstr_column+column_task[i]+",";
                    //cv.put(column_task[i], value[i]);
                    //a=a+" "+et_task[i].getText().toString();
                    Log.d(Tags + "|copy data|", "value" + i);

                }
            }

            insertLastestTaskByCycleUnit(value);
            cancelCurrentTask();
            //insertNewTaskByCycleUnit(value);
            Log.d(Tags + "|copy data|insert task|", "ok");
        }

    }

    public void copy2NewTaskByCycleUnit() throws ParseException {
        String Tags = "copy2NewTaskByCycleUnit";
        Log.d(Tags + "|copy data|", "ready");

        if (c != null && c.getCount() > 0) {
            c.moveToFirst();

            final String[] oldcolumn_task = {
                    TaskData.TdDB.TASK_NAME,
                    TaskData.TdDB.TASK_DESCRIPTION,
                    TaskData.TdDB.TASK_IMPORTANCE,
                    TaskData.TdDB.TASK_IMPORTANCEDETAIL,
                    TaskData.TdDB.TASK_URGENCY,
                    TaskData.TdDB.TASK_URGENCYDETAIL,
                    TaskData.TdDB.TASK_ASSESSMENT,
                    TaskData.TdDB.TASK_PRIORITY,
                    TaskData.TdDB.TASK_PASSION,
                    TaskData.TdDB.TASK_PASSIONDETAIL,
                    TaskData.TdDB.TASK_DIFFICULTY,
                    TaskData.TdDB.TASK_DIFFICULTYDETAIL,
                    TaskData.TdDB.TASK_DURATION,
                    TaskData.TdDB.TASK_DURATIONUNIT,
                    TaskData.TdDB.TASK_DEADLINE,
                    TaskData.TdDB.TASK_CYCLEUNIT,
                    TaskData.TdDB.TASK_RECORDCOUNT,
                    TaskData.TdDB.TASK_REMINDER,
                    TaskData.TdDB.TASK_STATUS,
                    TaskData.TdDB.TASK_USER,
                    TaskData.TdDB.TASK_OWNER,
                    TaskData.TdDB.TASK_USERGROUP

            };
            List value = new ArrayList();

            if (c != null && c.getCount() > 0) {
                for (int i = 0; i < oldcolumn_task.length; i++) {
                    Map map=new HashMap();
                    map.put("key",oldcolumn_task[i]);
                    map.put("value",c.getString(c.getColumnIndex(oldcolumn_task[i])));
                    value.add(map);
                    //insertstr_column = insertstr_column+column_task[i]+",";
                    //cv.put(column_task[i], value[i]);
                    //a=a+" "+et_task[i].getText().toString();
                    Log.d(Tags + "|copy data|", "value" + i);

                }
            }
            cancelCurrentTask();
            insertNewTaskByCycleUnit(value);

            //insertNewTaskByCycleUnit(value);
            Log.d(Tags + "|copy data|insert task|", "ok"+" t_sn"+t_sn);
        }

    }

    public void copy2NewTaskRecordByCycleUnit(String old_tsn,String new_tsn) throws ParseException {
        String Tags = "copy2NewTaskRecordByCycleUnit";
        int r_no=1;

        //Cursor c;
        Log.d(Tags + "|copy data|", "ready");
        final String[] oldcolumn_record = {
                TaskData.TdDB.RECORD_TASKID_NO,
                TaskData.TdDB.TASK_USER,
                TaskData.TdDB.RECORD_AUTHOR,
                TaskData.TdDB.TASK_OWNER,
                TaskData.TdDB.RECORD_COMMENTS,
                TaskData.TdDB.RECORD_WEIGHT,
                TaskData.TdDB.RECORD_TYPE
        };
        //Cursor c=TaskData.db_TdDB.rawQuery("select * from " + TaskData.TdDB.TABLE_NAME_TaskRecord+ " where " + TaskData.TdDB.TASK_ID + "=" +"'"+TaskData.selTaskID+"'", null);
        Cursor c=TaskData.db_TdDB.rawQuery("select * from " + TaskData.TdDB.TABLE_NAME_TaskRecord+ " where " + TaskData.TdDB.TASK_SN + "=" +"'"+old_tsn+"'", null);
        if (c != null && c.getCount() > 0) {
            c.moveToFirst();

            do {
                List value = new ArrayList();

                for (int i = 0; i < oldcolumn_record.length; i++) {
                    Map map = new HashMap();
                    map.put("key", oldcolumn_record[i]);
                    map.put("value", c.getString(c.getColumnIndex(oldcolumn_record[i])));
                    value.add(map);
                    //insertstr_column = insertstr_column+column_task[i]+",";
                    //cv.put(column_task[i], value[i]);
                    //a=a+" "+et_task[i].getText().toString();
                    Log.d(Tags + "|copy data|", "value" + i);

                }
                insertNewTaskRecordByCycleUnit(value,old_tsn,new_tsn,r_no);
                Log.d(Tags + "|copy data|insert task|", "ok");
                r_no++;
            }while (c.moveToNext());
        }
        c.requery();
    }

    public void insertNewTaskRecordByCycleUnit(List<Map> value,String old_tsn,String new_tsn,int r_no){
        String Tags="insertNewTaskByCycleUnit";
        String newTime=null;
        String cycle_unit;
        long newTimeData=0;
        String old_deadline=null;
        String insertstr_column = " (";
        for (int i=0;i<value.size()-1;i++){
            insertstr_column = insertstr_column+value.get(i).get("key").toString()+",";
            //cv.put(column_task[i], value[i]);
            //a=a+" "+et_task[i].getText().toString();

        }

        insertstr_column=insertstr_column+value.get(value.size()-1).get("key")+") ";
        //Log.d("time compare", "deadline"+taskdeadlinedate+"system"+new Date().getTime());
        //Toast.makeText(getActivity(), insertstr_column, Toast.LENGTH_LONG);
        String aaa = "  values (";
        String insertstr_value="";
        for (int i=0;i<value.size();i++){
            if (value.get(i).get("value")!=null) {
                insertstr_value = insertstr_value + "'" + value.get(i).get("value") + "'" + ",";
                //cv.put(column_task[i], value[i]);
                //a=a+" "+et_task[i].getText().toString();
                Log.d(Tags + "|insert data|", "value" + i + " " + value.get(i).get("value"));
            }else{
                insertstr_value = insertstr_value  + value.get(i).get("value")  + ",";
                //cv.put(column_task[i], value[i]);
                //a=a+" "+et_task[i].getText().toString();
                Log.d(Tags + "|insert data|", "value" + i + " " + value.get(i).get("value"));
            }
        }
        insertstr_value=aaa+insertstr_value.substring(0,((insertstr_value.length())-1))+");";
        Log.d(Tags+"|insert value|", insertstr_column+insertstr_value);
        TaskData.db_TdDB.execSQL("insert into "+TaskData.TdDB.TABLE_NAME_TaskRecord+insertstr_column+insertstr_value);

        Cursor cur= TaskData.db_TdDB.rawQuery("select LAST_INSERT_ROWID() from "+TaskData.TdDB.TABLE_NAME_TaskRecord,null);
        cur.moveToFirst();
        int lastRowId=cur.getInt(0);
        //Cursor c= TaskData.db_TdDB.rawQuery("select * from " + TaskData.TdDB.TABLE_NAME_TaskRecord + " where " + TaskData.TdDB.TASK_ID + "=" + TaskData.selTaskID, null);
        //Log.d("cursor", String.valueOf(c.getCount()) + "position" + String.valueOf(c.getPosition()));
		/*
		if (c != null && c.getCount() > 0) {
			c.moveToFirst();
			cycle_unit = c.getString(c.getColumnIndex(TaskData.TdDB.TASK_CYCLEUNIT));
			old_deadline = c.getString(c.getColumnIndex(TaskData.TdDB.TASK_DEADLINE));
			Log.d(Tags, "sss" + old_deadline + cycle_unit);
			try {
				newTime = TimeData.getNewTime(old_deadline, cycle_unit);
				newTimeData = TimeData.getNewTimeData(old_deadline, cycle_unit);
				Log.d(Tags, "sss" + newTime + newTimeData);
			} catch (ParseException e) {
				e.printStackTrace();
			}*/

            Log.d(Tags + "|new t_sn|", ""+new_tsn);
            ContentValues cv3 = new ContentValues();
            cv3.put(TaskData.TdDB.RECORD_CREATEDTIME,TaskTool.getCurTime());
            //cv3.put(TaskData.TdDB.RECORD_TASKID,lastRowId);
            cv3.put(TaskData.TdDB.TASK_SN,new_tsn);
            cv3.put(TaskData.TdDB.RECORD_SN,  new_tsn+TaskTool.addZero(r_no,4));
            cv3.put(TaskData.TdDB.RECORD_CREATEDTIME ,TaskTool.getCurTime());
            cv3.put(TaskData.TdDB.RECORD_PROGRESS,0);
            cv3.put(TaskData.TdDB.RECORD_DONE,"false");
            cv3.put(TaskData.TdDB.RECORD_DEADLINE, newTime);
            String where_record=TaskData.TdDB.RECORD_ID+"=?";
            String[] where_recordid={String.valueOf(lastRowId)};
            int updateresult = TaskData.db_TdDB.update(TaskData.TdDB.TABLE_NAME_TaskRecord, cv3, where_record, where_recordid);
            //TaskData.getSequenceNo();
            Log.d(Tags + "|new r_sn|", ""+new_tsn+TaskTool.addZero(r_no,4));
            Log.d(Tags + "|insert task|", "update ok"+lastRowId+" "+updateresult);

    }


    public String getInitSequnceNo(){
        String Tags="getSequenceNo";
        String sn_sequence=null;
        String sn_rank=null;
        int value_taskimportance;
        int value_taskurgency;
        String taskdeadline;
        String  value_taskduration;
        String value_taskdurationunit;

        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            value_taskurgency = c.getInt(c.getColumnIndex(TaskData.TdDB.TASK_URGENCY));
            value_taskimportance = c.getInt(c.getColumnIndex(TaskData.TdDB.TASK_IMPORTANCE));
            taskdeadline = c.getString(c.getColumnIndex(TaskData.TdDB.TASK_DEADLINE));
            value_taskduration = c.getString(c.getColumnIndex(TaskData.TdDB.TASK_DURATION));
            value_taskdurationunit = c.getString(c.getColumnIndex(TaskData.TdDB.TASK_DURATIONUNIT));
            if (value_taskimportance >= 2 && value_taskurgency >= 2) {
                if (value_taskimportance > 2 && value_taskurgency > 2) {
                    sn_rank = "1";
                } else {
                    sn_rank = "2";
                }
            }
            if (value_taskimportance == 1 && value_taskurgency >= 2) {
                sn_rank = "3";

            }
            if (value_taskimportance >= 2 && value_taskurgency == 1) {
                sn_rank = "4";
            }
            if (value_taskimportance == 1 && value_taskurgency == 1) {
                sn_rank = "5";

            }

            SimpleDateFormat df = new SimpleDateFormat("yy-MM-dd");
            long taskdeadlinedate = 0;
            try {
                taskdeadlinedate = (long) df.parse(taskdeadline).getTime() / (1000 * 60);
                long dd = (long) ((new Date().getTime()) / (1000 * 60));
                //tv_newtaskheader.setText(""+taskdeadlinedate+"system"+dd);
                // tv_newtaskheader.setText(""+dd);

            } catch (ParseException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }

            double plannedtime = 0.0;
            double hr_timeunit;
            switch (Integer.parseInt(value_taskdurationunit)) {

                case 1:
                    hr_timeunit = 8;
                    plannedtime = Double.parseDouble(value_taskduration) * hr_timeunit;
                    Log.d(Tags, "plannedtime:" + plannedtime);
                    break;
                case 2:
                    hr_timeunit = 1;
                    plannedtime = Double.parseDouble(value_taskduration) * hr_timeunit;
                    Log.d(Tags, "plannedtime:" + plannedtime);
                    break;
                case 3:
                    hr_timeunit = (double) 1 / 60;
                    plannedtime = Double.parseDouble(value_taskduration) * hr_timeunit;
                    Log.d(Tags, "plannedtime:" + plannedtime);
                    break;
                default:
                    break;
            }

            //taskdeadline=et15_taskdeadline.getText().toString();
            //date1=dg_time.GetDate();
            //Toast.makeText(getActivity(), deadline+"\n"+date1, Toast.LENGTH_LONG).show();
            //taskdeadlineTimeData=TimeData.changeStrToTime_YY(taskdeadline);
            //taskdeadlinetimestamp=Timestamp.valueOf(deadline);
            String taskdeadlineDate = TimeData.convertDataNoTime_YY(taskdeadline);

            String sn_deadline = TimeData.convertShortDate_YYMdtoYYYYMMdd(taskdeadlineDate);
            //DecimalFormat df_sn = new DecimalFormat("0.0000");//格式化小数，.后跟几个零代表几位小数
            //float dt=((float)plannedtime/10000);
            //sn_duration=df_sn.format(dt);;
            String sn_duration = TaskTool.addZero((int) (plannedtime * 10), 6);
            String sn_importance = null;
            switch (value_taskimportance) {
                case 3:
                    sn_importance = "1";
                    break;
                case 2:
                    sn_importance = "2";
                    break;
                case 1:
                    sn_importance = "3";
                    break;
                default:
                    break;
            }
            String sn_urgency = null;
            switch (value_taskurgency) {
                case 3:
                    sn_urgency = "1";
                    break;
                case 2:
                    sn_urgency = "2";
                    break;
                case 1:
                    sn_urgency = "3";
                    break;
                default:
                    break;
            }

            //sn_sequence=sn_deadline+sn_importance+sn_urgency+sn_duration;
            sn_sequence = sn_deadline + sn_rank + sn_importance + sn_urgency + sn_duration;

        }

        return sn_sequence;
    }

    public void insertLastestTaskByCycleUnit(List<Map> value){
                String Tags="insertLastestTaskByCycleUnit";
                String newTime=null;
                String old_tsn=null;
                String new_tsn;
                String cycle_unit;
                long newTimeData=0;
                String old_deadline=null;
                String insertstr_column = " (";
                for (int i=0;i<value.size()-1;i++){
                    insertstr_column = insertstr_column+value.get(i).get("key").toString()+",";
                    //cv.put(column_task[i], value[i]);
                    //a=a+" "+et_task[i].getText().toString();

                }
                insertstr_column=insertstr_column+value.get(value.size()-1).get("key")+") ";
                //Log.d("time compare", "deadline"+taskdeadlinedate+"system"+new Date().getTime());
                //Toast.makeText(getActivity(), insertstr_column, Toast.LENGTH_LONG);
                String aaa = "  values (";
                String insertstr_value="";
                for (int i=0;i<value.size();i++){
                    if (value.get(i).get("value")!=null) {
                        insertstr_value = insertstr_value + "'" + value.get(i).get("value") + "'" + ",";
                        //cv.put(column_task[i], value[i]);
                        //a=a+" "+et_task[i].getText().toString();
                        Log.d(Tags + "|insert data|", "value" + i + " " + value.get(i).get("value"));
                    }else{
                        insertstr_value = insertstr_value  + value.get(i).get("value")  + ",";
                        //cv.put(column_task[i], value[i]);
                        //a=a+" "+et_task[i].getText().toString();
                        Log.d(Tags + "|insert data|", "value" + i + " " + value.get(i).get("value"));
                    }
                }
                insertstr_value=aaa+insertstr_value.substring(0,((insertstr_value.length())-1))+");";
                Log.d(Tags+"|insert value|", insertstr_column+insertstr_value);
                TaskData.db_TdDB.execSQL("insert into "+TaskData.TdDB.TABLE_NAME_TaskMain+insertstr_column+insertstr_value);

                Cursor cur= TaskData.db_TdDB.rawQuery("select LAST_INSERT_ROWID()",null);
                cur.moveToFirst();
                int lastRowId=cur.getInt(0);

                if (c != null && c.getCount() > 0) {
                    c.moveToFirst();
                    old_tsn = c.getString(c.getColumnIndex(TaskData.TdDB.TASK_SN));
                    cycle_unit = c.getString(c.getColumnIndex(TaskData.TdDB.TASK_CYCLEUNIT));
                    old_deadline = c.getString(c.getColumnIndex(TaskData.TdDB.TASK_DEADLINE));
                    Log.d(Tags, "sss" + old_deadline + cycle_unit);
                    try {
                        newTime = TimeData.getLastestTime(old_deadline, cycle_unit);
                        newTimeData = TimeData.getLastestTimeData(old_deadline, cycle_unit);
                        Log.d(Tags, "sss" + newTime + newTimeData);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                ContentValues cv3 = new ContentValues();
                String tag_taskhistory = "(" + TaskTool.getCurTime() + " " + TaskData.usernickname + ")" + "\n";
                new_tsn = TaskData.user + "0" + TaskTool.addZero(lastRowId, 10);
                cv3.put(TaskData.TdDB.TASK_CREATEDTIME, TaskTool.getCurTime());
                cv3.put(TaskData.TdDB.TASK_CREATEDTIMEDATA, new Date().getTime()/60/1000);
                cv3.put(TaskData.TdDB.TASK_STARTEDTIME, TaskTool.getCurTime());
                cv3.put(TaskData.TdDB.TASK_PROGRESS, 0);
                cv3.put(TaskData.TdDB.SUM_ACCOMPLISHED, 0);
                cv3.put(TaskData.TdDB.SUM_ACHIEVED, 0);
                cv3.put(TaskData.TdDB.SUM_ENJOYMENT, 0);
                cv3.put(TaskData.TdDB.SUM_EXPERIENCE, 0);
                cv3.put(TaskData.TdDB.TASK_SEQUENCE, getInitSequnceNo());
                cv3.put(TaskData.TdDB.TASK_DEADLINE, newTime);
                cv3.put(TaskData.TdDB.TASK_DEADLINETIMEDATA, newTimeData);
                cv3.put(TaskData.TdDB.TASK_DEADLINEDATE, TimeData.convertDataNoTime_YY(newTime));
                cv3.put(TaskData.TdDB.TASK_HISTORY,"任务创建"+tag_taskhistory);
                cv3.put("_sn", TaskData.user + "0" + TaskTool.addZero(lastRowId, 10));
                String where_task = TaskData.TdDB.TASK_ID + "=?";
                String[] where_taskid = {String.valueOf(lastRowId)};
                TaskData.db_TdDB.update(TaskData.TdDB.TABLE_NAME_TaskMain, cv3, where_task, where_taskid);


                try {
                    copy2NewTaskRecordByCycleUnit(old_tsn, new_tsn);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                TaskData.getSequenceNo();
                c.requery();
                Log.d(Tags + "insert task|", "insert ok" + lastRowId);
    }

    public void insertNewTaskByCycleUnit(List<Map> value){
        String Tags="insertNewTaskByCycleUnit";
        String newTime=null;
        String old_tsn=null;
        String new_tsn;
        String cycle_unit;
        long newTimeData=0;
        String old_deadline=null;
        String insertstr_column = " (";
        for (int i=0;i<value.size()-1;i++){
            insertstr_column = insertstr_column+value.get(i).get("key").toString()+",";
            //cv.put(column_task[i], value[i]);
            //a=a+" "+et_task[i].getText().toString();

        }
        insertstr_column=insertstr_column+value.get(value.size()-1).get("key")+") ";
        //Log.d("time compare", "deadline"+taskdeadlinedate+"system"+new Date().getTime());
        //Toast.makeText(getActivity(), insertstr_column, Toast.LENGTH_LONG);
        String aaa = "  values (";
        String insertstr_value="";
        for (int i=0;i<value.size();i++){
            if (value.get(i).get("value")!=null) {
                insertstr_value = insertstr_value + "'" + value.get(i).get("value") + "'" + ",";
                //cv.put(column_task[i], value[i]);
                //a=a+" "+et_task[i].getText().toString();
                Log.d(Tags + "|insert data|", "value" + i + " " + value.get(i).get("value"));
            }else{
                insertstr_value = insertstr_value  + value.get(i).get("value")  + ",";
                //cv.put(column_task[i], value[i]);
                //a=a+" "+et_task[i].getText().toString();
                Log.d(Tags + "|insert data|", "value" + i + " " + value.get(i).get("value"));
            }
        }
        insertstr_value=aaa+insertstr_value.substring(0,((insertstr_value.length())-1))+");";
        Log.d(Tags+"|insert value|", insertstr_column+insertstr_value);
        TaskData.db_TdDB.execSQL("insert into "+TaskData.TdDB.TABLE_NAME_TaskMain+insertstr_column+insertstr_value);

        Cursor cur= TaskData.db_TdDB.rawQuery("select LAST_INSERT_ROWID()",null);
        cur.moveToFirst();
        int lastRowId=cur.getInt(0);

        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            old_tsn = c.getString(c.getColumnIndex(TaskData.TdDB.TASK_SN));
            cycle_unit = c.getString(c.getColumnIndex(TaskData.TdDB.TASK_CYCLEUNIT));
            old_deadline = c.getString(c.getColumnIndex(TaskData.TdDB.TASK_DEADLINE));
            Log.d(Tags, "sss" + old_deadline + cycle_unit);
            try {
                newTime = TimeData.getNewTime(old_deadline, cycle_unit);
                newTimeData = TimeData.getNewTimeData(old_deadline, cycle_unit);
                Log.d(Tags, "sss" + newTime + newTimeData);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        ContentValues cv3 = new ContentValues();
        String tag_taskhistory = "(" + TaskTool.getCurTime() + " " + TaskData.usernickname + ")" + "\n";
        new_tsn = TaskData.user + "0" + TaskTool.addZero(lastRowId, 10);
        cv3.put(TaskData.TdDB.TASK_CREATEDTIME, TaskTool.getCurTime());
        cv3.put(TaskData.TdDB.TASK_CREATEDTIMEDATA, new Date().getTime());
        cv3.put(TaskData.TdDB.TASK_STARTEDTIME, TaskTool.getCurTime());
        cv3.put(TaskData.TdDB.TASK_PROGRESS, 0);
        cv3.put(TaskData.TdDB.SUM_ACCOMPLISHED, 0);
        cv3.put(TaskData.TdDB.SUM_ACHIEVED, 0);
        cv3.put(TaskData.TdDB.SUM_ENJOYMENT, 0);
        cv3.put(TaskData.TdDB.SUM_EXPERIENCE, 0);
        cv3.put(TaskData.TdDB.TASK_SEQUENCE, getInitSequnceNo());
        cv3.put(TaskData.TdDB.TASK_DEADLINE, newTime);
        cv3.put(TaskData.TdDB.TASK_DEADLINETIMEDATA, newTimeData-5);
        cv3.put(TaskData.TdDB.TASK_DEADLINEDATE, TimeData.convertDataNoTime_YY(newTime));
        cv3.put(TaskData.TdDB.TASK_HISTORY,"任务创建"+tag_taskhistory);
        cv3.put("_sn", TaskData.user + "0" + TaskTool.addZero(lastRowId, 10));
        String where_task = TaskData.TdDB.TASK_ID + "=?";
        String[] where_taskid = {String.valueOf(lastRowId)};
        TaskData.db_TdDB.update(TaskData.TdDB.TABLE_NAME_TaskMain, cv3, where_task, where_taskid);


        try {
            copy2NewTaskRecordByCycleUnit(old_tsn, new_tsn);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        TaskData.getSequenceNo();
        c.requery();
        Log.d(Tags + "insert task|", "insert ok" + lastRowId);
    }


    public void copy2NewTask() {
        String Tags = "copy2NewTask";


        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            final String[] oldcolumn_task = {
                    TaskData.TdDB.TASK_NAME,
                    TaskData.TdDB.TASK_DESCRIPTION,
                    TaskData.TdDB.TASK_IMPORTANCE,
                    TaskData.TdDB.TASK_IMPORTANCEDETAIL,
                    TaskData.TdDB.TASK_URGENCY,
                    TaskData.TdDB.TASK_URGENCYDETAIL,
                    TaskData.TdDB.TASK_ASSESSMENT,
                    TaskData.TdDB.TASK_PRIORITY,
                    TaskData.TdDB.TASK_PASSION,
                    TaskData.TdDB.TASK_PASSIONDETAIL,
                    TaskData.TdDB.TASK_DIFFICULTY,
                    TaskData.TdDB.TASK_DIFFICULTYDETAIL,
                    TaskData.TdDB.TASK_DURATION,
                    TaskData.TdDB.TASK_DURATIONUNIT,
                    TaskData.TdDB.TASK_DEADLINE,
                    TaskData.TdDB.TASK_CYCLEUNIT,
                    TaskData.TdDB.TASK_DEADLINETIMEDATA,
                    TaskData.TdDB.TASK_DEADLINEDATE,
                    TaskData.TdDB.TASK_RECORDCOUNT,
                    TaskData.TdDB.TASK_REMINDER,
                    TaskData.TdDB.TASK_STATUS,
                    TaskData.TdDB.TASK_USER,
                    TaskData.TdDB.TASK_OWNER,
                    TaskData.TdDB.TASK_USERGROUP

            };
            List value = new ArrayList();
            if (c != null && c.getCount() > 0) {
                for (int i = 0; i < oldcolumn_task.length; i++) {
                    Map map=new HashMap();
                    map.put("key",oldcolumn_task[i]);
                    map.put("value",c.getString(c.getColumnIndex(oldcolumn_task[i])));
                    value.add(map);
                    //insertstr_column = insertstr_column+column_task[i]+",";
                    //cv.put(column_task[i], value[i]);
                    //a=a+" "+et_task[i].getText().toString();
                    Log.d(Tags + "|copy data|", "value" + i);

                }
            }
            insertNewTask(value);
            Log.d(Tags + "|copy data|insert task|", "ok");
        }
    }



    public void insertNewTask(List<Map> value){
        String Tags="InsertTask";

        String insertstr_column = " (";
        for (int i=0;i<value.size()-1;i++){
            insertstr_column = insertstr_column+value.get(i).get("key").toString()+",";
            //cv.put(column_task[i], value[i]);
            //a=a+" "+et_task[i].getText().toString();

        }

        insertstr_column=insertstr_column+value.get(value.size()-1).get("key")+") ";
        //Log.d("time compare", "deadline"+taskdeadlinedate+"system"+new Date().getTime());
        //Toast.makeText(getActivity(), insertstr_column, Toast.LENGTH_LONG);
        String aaa = "  values (";
        String insertstr_value="";
        for (int i=0;i<value.size();i++){
            if (value.get(i).get("value")!=null) {
                insertstr_value = insertstr_value + "'" + value.get(i).get("value") + "'" + ",";
                //cv.put(column_task[i], value[i]);
                //a=a+" "+et_task[i].getText().toString();
                Log.d(Tags + "|insert data|", "value" + i + " " + value.get(i).get("value"));
            }else{
                insertstr_value = insertstr_value  + value.get(i).get("value")  + ",";
                //cv.put(column_task[i], value[i]);
                //a=a+" "+et_task[i].getText().toString();
                Log.d(Tags + "|insert data|", "value" + i + " " + value.get(i).get("value"));
            }
        }
        insertstr_value=aaa+insertstr_value.substring(0,((insertstr_value.length())-1))+");";
        Log.d(Tags+"|insert value|", insertstr_column+insertstr_value);
        TaskData.db_TdDB.execSQL("insert into "+TaskData.TdDB.TABLE_NAME_TaskMain+insertstr_column+insertstr_value);

        Cursor cur= TaskData.db_TdDB.rawQuery("select LAST_INSERT_ROWID()",null);
        cur.moveToFirst();
        int lastRowId=cur.getInt(0);

        ContentValues cv3 = new ContentValues();
        cv3.put(TaskData.TdDB.TASK_CREATEDTIME,TaskTool.getCurTime());
        cv3.put(TaskData.TdDB.TASK_CREATEDTIMEDATA,new Date().getTime());
        cv3.put(TaskData.TdDB.TASK_PROGRESS,0);
        cv3.put(TaskData.TdDB.SUM_ACCOMPLISHED,0);
        cv3.put(TaskData.TdDB.SUM_ACHIEVED,0);
        cv3.put(TaskData.TdDB.SUM_ENJOYMENT,0);
        cv3.put(TaskData.TdDB.SUM_EXPERIENCE,0);
        cv3.put("_sn",TaskData.user+"0"+TaskTool.addZero(lastRowId,10));

        String where_task=TaskData.TdDB.TASK_ID+"=?";
        String[] where_taskid={String.valueOf(lastRowId)};
        TaskData.db_TdDB.update(TaskData.TdDB.TABLE_NAME_TaskMain,cv3, where_task, where_taskid);
        getInitSequnceNo();
        TaskData.getSequenceNo();
        c.requery();

    }

    public static void insertNewTask(String[] column_task,List value){
        String Tags="InsertTask";

        String insertstr_column = " (";
        for (int i=0;i<column_task.length-1;i++){
            insertstr_column = insertstr_column+column_task[i]+",";
            //cv.put(column_task[i], value[i]);
            //a=a+" "+et_task[i].getText().toString();

        }

        insertstr_column=insertstr_column+column_task[column_task.length-1]+") ";
        //Log.d("time compare", "deadline"+taskdeadlinedate+"system"+new Date().getTime());
        //Toast.makeText(getActivity(), insertstr_column, Toast.LENGTH_LONG);
        String aaa = "  values (";
        String insertstr_value="";
        for (int i=0;i<value.size();i++){

            insertstr_value = insertstr_value+"'"+value.get(i).toString()+"'"+",";
            //cv.put(column_task[i], value[i]);
            //a=a+" "+et_task[i].getText().toString();
            Log.d(Tags+"|insert data|", "value"+i+" "+value.get(i).toString());
        }
        insertstr_value=aaa+insertstr_value.substring(0,((insertstr_value.length())-1))+");";
        Log.d(Tags+"|insert value|", insertstr_column+insertstr_value);
        TaskData.db_TdDB.execSQL("insert into "+TaskData.TdDB.TABLE_NAME_TaskMain+insertstr_column+insertstr_value);

        Cursor cur= TaskData.db_TdDB.rawQuery("select LAST_INSERT_ROWID()",null);
        cur.moveToFirst();
        int lastRowId=cur.getInt(0);

        ContentValues cv3 = new ContentValues();
        cv3.put("_sn",TaskData.user+"0"+TaskTool.addZero(lastRowId,10));
        String where_task=TaskData.TdDB.TASK_ID+"=?";
        String[] where_taskid={String.valueOf(lastRowId)};
        TaskData.db_TdDB.update(TaskData.TdDB.TABLE_NAME_TaskMain,cv3, where_task, where_taskid);
        TaskData.getSequenceNo();

    }


    public void insertNewTask(String[] column_task,Object[] value){
        String Tags="InsertTask";

        String insertstr_column = " (";
        for (int i=0;i<column_task.length-1;i++){

            insertstr_column = insertstr_column+column_task[i]+",";
            //cv.put(column_task[i], value[i]);
            //a=a+" "+et_task[i].getText().toString();
            Log.d(Tags+"|insert data|", "value"+i+" "+value[i]);
        }

        insertstr_column=insertstr_column+column_task[column_task.length-1]+") ";
        //Log.d("time compare", "deadline"+taskdeadlinedate+"system"+new Date().getTime());
        //Toast.makeText(getActivity(), insertstr_column, Toast.LENGTH_LONG);
        String aaa = "  values (";
        String insertstr_value="";
        for (int i=0;i<value.length;i++){

            insertstr_value = insertstr_value+"'"+value[i]+"'"+",";
            //cv.put(column_task[i], value[i]);
            //a=a+" "+et_task[i].getText().toString();
            Log.d(Tags+"|insert data|", "value"+i+" "+value[i]);
        }
        insertstr_value=aaa+insertstr_value.substring(0,((insertstr_value.length())-1))+");";

        TaskData.db_TdDB.execSQL("insert into "+TaskData.TdDB.TABLE_NAME_TaskMain+insertstr_column+insertstr_value);

        Cursor cur= TaskData.db_TdDB.rawQuery("select LAST_INSERT_ROWID()",null);
        cur.moveToFirst();
        int lastRowId=cur.getInt(0);

        ContentValues cv3 = new ContentValues();
        cv3.put("_sn",TaskData.user+"0"+TaskTool.addZero(lastRowId,10));
        String where_task=TaskData.TdDB.TASK_ID+"=?";
        String[] where_taskid={String.valueOf(lastRowId)};
        TaskData.db_TdDB.update(TaskData.TdDB.TABLE_NAME_TaskMain,cv3, where_task, where_taskid);
        TaskData.getSequenceNo();
        Log.d(Tags + "|copy data|insert task|", "insert ok"+lastRowId);

    }
   /*
	public static void insertTask(){
		String Tags="InsertTask";
		final String[] column_task={
				TaskData.TdDB.TASK_NAME,
				TaskData.TdDB.TASK_CREATEDTIME,
				TaskData.TdDB.TASK_CREATEDTIMEDATA,
				TaskData.TdDB.TASK_DESCRIPTION,
				TaskData.TdDB.TASK_IMPORTANCE,
				TaskData.TdDB.TASK_IMPORTANCEDETAIL,
				TaskData.TdDB.TASK_URGENCY,
				TaskData.TdDB.TASK_URGENCYDETAIL,
				TaskData.TdDB.TASK_ASSESSMENT,
				TaskData.TdDB.TASK_PRIORITY,
				TaskData.TdDB.TASK_PASSION,
				TaskData.TdDB.TASK_PASSIONDETAIL,
				TaskData.TdDB.TASK_DIFFICULTY,
				TaskData.TdDB.TASK_DIFFICULTYDETAIL,
				TaskData.TdDB.TASK_DURATION,
				TaskData.TdDB.TASK_DURATIONUNIT,
				TaskData.TdDB.TASK_STARTEDTIME,
				TaskData.TdDB.TASK_DEADLINE,
				TaskData.TdDB.TASK_CYCLEUNIT,
				TaskData.TdDB.TASK_PROGRESS,
				TaskData.TdDB.TASK_REMINDER,
				TaskData.TdDB.TASK_STATUS,
				TaskData.TdDB.TASK_FINISHED,
				TaskData.TdDB.TASK_DELAYED,
				TaskData.TdDB.SUM_ACCOMPLISHED,
				TaskData.TdDB.SUM_ACHIEVED,
				TaskData.TdDB.SUM_ENJOYMENT,
				TaskData.TdDB.SUM_EXPERIENCE,
				TaskData.TdDB.SUM_HORNOR,
				TaskData.TdDB.TASK_DEADLINETIMEDATA,
				TaskData.TdDB.TASK_DEADLINEDATE,
				TaskData.TdDB.TASK_DELAYEDDAYS,
				TaskData.TdDB.TASK_RECORDCOUNT,
				TaskData.TdDB.TASK_SEQUENCE,
				TaskData.TdDB.TASK_USER,
				TaskData.TdDB.TASK_OWNER,
				TaskData.TdDB.TASK_REMARKS,
				TaskData.TdDB.TASK_HISTORY,

		};

		final Object[] value={
				et1_taskname.getText().toString(),
				curTime,
				curTimeData,
				et3_taskdescription.getText().toString(),
				String.valueOf(list_imp.get(spin4_taskimportance.getSelectedItemPosition()).getNo()),
				String.valueOf(list_imp.get(spin4_taskimportance.getSelectedItemPosition()).getValue()),
				String.valueOf(list_urg.get(spin5_taskurgency.getSelectedItemPosition()).getNo()),
				String.valueOf(list_urg.get(spin5_taskurgency.getSelectedItemPosition()).getValue()),
				item6_taskassess,
				item7_taskprority,
				String.valueOf(list_passion.get(spin8_taskpassion.getSelectedItemPosition()).getNo()),
				String.valueOf(list_passion.get(spin8_taskpassion.getSelectedItemPosition()).getValue()),
				String.valueOf(list_difficulty.get(spin9_taskdifficulty.getSelectedItemPosition()).getNo()),
				String.valueOf(list_difficulty.get(spin9_taskdifficulty.getSelectedItemPosition()).getValue()),
				et10_taskduration.getText().toString(),
				String.valueOf(spin_timeunit.getSelectedItemPosition()+1),
				taskstartedtime,
				taskdeadline,
				cycle_unit_model2,
				"0",
				taskreminder,
				"open",
				"no",
				"0",
				"0",
				"0",
				"0",
				"0",
				"0",
				taskdeadlineTimeData,
				taskdeadlineDate,
				1,
				0,
				sn_sequence,
				TaskData.user,
				TaskData.user,
				"",
				"任务创建"+TaskData.tag_taskhistory
		};

		String insertstr_column = " (";
		for (int i=0;i<column_task.length-1;i++){

			insertstr_column = insertstr_column+column_task[i]+",";
			//cv.put(column_task[i], value[i]);
			//a=a+" "+et_task[i].getText().toString();
			Log.d(Tags+"|insert data|", "value"+i+" "+value[i]);
		}

		insertstr_column=insertstr_column+column_task[column_task.length-1]+") ";
		//Log.d("time compare", "deadline"+taskdeadlinedate+"system"+new Date().getTime());
		//Toast.makeText(getActivity(), insertstr_column, Toast.LENGTH_LONG);
		String aaa = "  values (";
		String insertstr_value="";
		for (int i=0;i<value.length;i++){

			insertstr_value = insertstr_value+"'"+value[i]+"'"+",";
			//cv.put(column_task[i], value[i]);
			//a=a+" "+et_task[i].getText().toString();
			Log.d(Tags+"|insert data|", "value"+i+" "+value[i]);
		}
		insertstr_value=aaa+insertstr_value.substring(0,((insertstr_value.length())-1))+");";

		TaskData.db_TdDB.execSQL("insert into "+TaskData.TdDB.TABLE_NAME_TaskMain+insertstr_column+insertstr_value);

		Cursor cur= TaskData.db_TdDB.rawQuery("select LAST_INSERT_ROWID()",null);
		cur.moveToFirst();
		int lastRowId=cur.getInt(0);

		ContentValues cv3 = new ContentValues();
		cv3.put("_sn",TaskData.user+"0"+TaskTool.addZero(lastRowId,10));
		String where_task=TaskData.TdDB.TASK_ID+"=?";
		String[] where_taskid={String.valueOf(lastRowId)};
		TaskData.db_TdDB.update(TaskData.TdDB.TABLE_NAME_TaskMain,cv3, where_task, where_taskid);
		TaskData.getSequenceNo();
	}*/
}
