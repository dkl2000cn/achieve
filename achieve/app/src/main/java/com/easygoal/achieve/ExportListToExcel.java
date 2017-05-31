package com.easygoal.achieve;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;  
  
public class ExportListToExcel {  

    private String mTb;
    private Cursor cur; 
    private Context mContext;
    private String[] titlename;
    private String[] columnname;
  
    public ExportListToExcel(Context context,String tablename,Cursor cursor,String[] titlename,String[] columnname) {  
       // mDb = db;  
       // mDestXmlFilename = destXml;
    	mContext=context;
        mTb=tablename;
        cur=cursor;
       // cur2=cursor2;
        this.titlename=titlename;
        this.columnname=columnname;
    }

    public File writeExcel(String tableName) {  
        WritableWorkbook wwb = null;  
        String fileName; 
        //File sdFileDir=Environment.getExternalStorageDirectory();
        //fileName = sdFileDir+"/DCIM/" + tableName + ".xls";  
        fileName = FileUtils.getDiskCacheDirPath(mContext)+ tableName + ".xls"; 
        File outexcelfile=new File(fileName);
       // File outputfile = new File(fileName);
        int r = 0;  

        int numcols = titlename.length;  
        int numrows = cur.getCount();  


        int taskcolWidth[]={1,1,1,1,1,1,1,1,1,1,1};
        String records[][] = new String[numrows + 1][numcols];// ��Ŵ𰸣���һ�б�����
        Log.d("cur", ""+cur.getCount()+" col "+cur.getColumnCount());

        if (cur.getCount()>0){
            cur.moveToFirst();

            do{
                for (int c = 0; c < numcols; c++) {
                    if (r == 0) {
                        records[r][c] = titlename[c];
                        records[r + 1][c] = cur.getString(cur.getColumnIndex(columnname[c]));
                        int cw =  titlename[c].length()+getChineseNum( titlename[c]);
                        if (cw>taskcolWidth[c]){
                            taskcolWidth[c]=cw+1;
                        }
                    } else {

                        String valuestr=cur.getString(cur.getColumnIndex(columnname[c]));
                        records[r + 1][c] = valuestr;
                        int cw=0;
                        if (valuestr!=null){
                            cw = valuestr.length() + getChineseNum(valuestr) + 1;
                        }
                        if (cw>taskcolWidth[c]){
                            taskcolWidth[c]=cw;
                        }

                    }
                    //  Log.i("value" + r + " " + c, records[r][c]);
                }
                r++;
            }while (cur.moveToNext());

            cur.close();  
        }  
        
        try {  
            // ����Ҫʹ��Workbook��Ĺ�����������һ����д��Ĺ�����(Workbook)����  
            
        	wwb = Workbook.createWorkbook(outexcelfile); 
            
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
       
        if (wwb != null) {  
            // ����һ����д��Ĺ�����  
            // Workbook��createSheet������������������һ���ǹ���������ƣ��ڶ����ǹ������ڹ������е�λ��  
            WritableSheet ws = wwb.createSheet("�����嵥", 0);  
  
            // ���濪ʼ��ӵ�Ԫ��  
            for (int i = 0; i < numrows + 1; i++) {  
                for (int j = 0; j < numcols; j++) {  
                    // ������Ҫע����ǣ���Excel�У���һ��������ʾ�У��ڶ�����ʾ��  
                    Label labelC = new Label(j, i, records[i][j]);  
            //      Log.i("Newvalue" + i + " " + j, records[i][j]);  
                    try {  
                        // �����ɵĵ�Ԫ����ӵ���������  
                        ws.addCell(labelC);
                        ws.setColumnView(j, taskcolWidth[j]);
                    } catch (RowsExceededException e) {  
                        e.printStackTrace();  
                    } catch (WriteException e) {  
                        e.printStackTrace();  
                    }  
  
                }  
            } 
        }


        if (wwb != null) {
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
        return outexcelfile;
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