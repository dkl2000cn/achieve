package com.easygoal.achieve;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import net.sf.json.JSONArray;

import org.litepal.crud.DataSupport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Acer on 2017/4/2.
 */

public class TeamTaskUtils {


    public static void queryUserGroup(Context context,String usergroup){

        //TaskData.cursor_team= DataSupport.findBySQL("select userId as _id,username,nickname,phoneNo,title from UserBean where "+
        //       TaskData.TdDB.TASK_USER+"="+"'"+TaskData.user+"'");
        Map<String, String> params = new HashMap<String, String>();
        params.put("organization", usergroup);
        TaskData.usergroupArray=null;
        new RetrofitUtils(context).QueryUser("UserServlet",params);
        TaskData.cursor_team= DataSupport.findBySQL("select userId as _id,username,nickname,phoneNo,title from UserBean");
        //List<UserBean> usergrouplist = (List) JSONArray.toCollection(JSONArray.fromObject(TaskData.usergroupArray), UserBean.class);
        //Toast.makeText(context,"team member count:"+TaskData.cursor_team.getCount(), Toast.LENGTH_SHORT).show();
    }


    public static void updateUserGroup(Context context,Map params){

        //TaskData.cursor_team= DataSupport.findBySQL("select userId as _id,username,nickname,phoneNo,title from UserBean where "+
        //       TaskData.TdDB.TASK_USER+"="+"'"+TaskData.user+"'");
        /*
        Map<String, String> params = new HashMap<String, String>();
        params.put("organization", usergroup);
        params.put("phoneNo", TaskData.user);
        params.put("username", "denton");
        */
        new RetrofitUtils(context).UpdateUserRequestPost("UserServlet",params);
        //TaskData.cursor_team= DataSupport.findBySQL("select userId as _id,username,nickname,phoneNo,title from UserBean");
    }

    public static void QueryTeam(Context context,String usergroup){

        //TaskData.cursor_team= DataSupport.findBySQL("select userId as _id,username,nickname,phoneNo,title from UserBean where "+
        //       TaskData.TdDB.TASK_USER+"="+"'"+TaskData.user+"'");
        Map<String, String> params = new HashMap<String, String>();
        params.put(TaskData.TdDB.TASK_USERGROUP, usergroup);
        //TaskData.usergroupArray=null;
        //new ConnMySQL(context).QueryTeamTask("TaskmainServlet",params);
        new RetrofitUtils(context).QueryTeamTask("TaskmainServlet",params);
        new RetrofitUtils(context).QueryTeamRec("TaskrecServlet",params);
        //Toast.makeText(context,"team data query done", Toast.LENGTH_SHORT).show();
        //TaskData.cursor_team= DataSupport.findBySQL("select userId as _id,username,nickname,phoneNo,title from UserBean");
        //List<UserBean> usergrouplist = (List) JSONArray.toCollection(JSONArray.fromObject(TaskData.usergroupArray), UserBean.class);
        //Toast.makeText(context,"team member count:"+TaskData.cursor_team.getCount(), Toast.LENGTH_SHORT).show();
    }

    public static void FindTeam(Context context,Map<String, String> params){

        new RetrofitUtils(context).QueryUser("UserServlet",params);
    }
}
