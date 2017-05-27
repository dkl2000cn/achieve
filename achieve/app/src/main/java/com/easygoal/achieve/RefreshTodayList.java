package com.easygoal.achieve;


import android.database.Cursor;
import android.os.AsyncTask;

class RefreshTodayList extends AsyncTask<Void, Void ,Cursor>{

    private Cursor sCursor;

    protected Cursor doInBackground(Void... params) {
        sCursor = TaskData.adapter_todaytasks.getCursor();
        return sCursor;
    }

    protected void onPostExecute(Cursor cursor) {
        cursor.close();
        cursor = sCursor;
    }

}
