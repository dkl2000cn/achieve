package com.easygoal.achieve;

import java.io.File;
import java.io.IOException;

import android.database.Cursor;
import android.os.Environment;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;  
  
public class ExportToExcelBackup {  
   
    private String mTb;
    private Cursor cur; 
  
  
    public ExportToExcelBackup(String tablename,Cursor cursor) {  
       // mDb = db;  
       // mDestXmlFilename = destXml; 
        mTb=tablename;
        cur=cursor;
    }  
  
	public void exportData() {  
  
        try {  
  
        //  Log.i("mdb", mDb.getPath());  
            // get the tables out of the given sqlite database  
           // String sql = "SELECT * FROM "+mTb;  
  
            //Cursor cur = mDb.rawQuery(sql, new String[0]);  
            cur.moveToFirst();  
  
            String tableName;  
            writeExcel(mTb);
           /* while (cur.getPosition() < cur.getCount()) {  
                tableName = cur.getString(cur.getColumnIndex("name"));  
  
                // don't process these two tables since they are used  
                // for metadata  
                if (!tableName.equals("android_metadata")  
                        && !tableName.equals("sqlite_sequence")) {  
                    writeExcel(tableName);  
                }  
  
                cur.moveToNext();  
            }  */
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
  
    /** 
     * 生成一个Excel文件 
     *  
     * @param fileName 
     *            要生成的Excel文件名 
     * @return 
     */  
    public File writeExcel(String tableName) {  
        WritableWorkbook wwb = null;  
        String fileName; 
        File sdFileDir=Environment.getExternalStorageDirectory();
        fileName = sdFileDir+"/DCIM/" + tableName + ".xls";  
        File outexcelfile=new File(fileName);
       // File outputfile = new File(fileName);
        int r = 0;  
  
       // String sql = "select * from " + tableName;  
       // Cursor cur = mDb.rawQuery(sql, new String[0]);  
        int numcols = cur.getColumnCount();  
        int numrows = cur.getCount();  
        // Log.i("row", numrows + "");  
        // Log.i("col", numcols + "");  
  
        String records[][] = new String[numrows + 1][numcols];// 存放答案，多一行标题行  
  
        if (cur.moveToFirst()) {  
            while (cur.getPosition() < cur.getCount()) {  
                for (int c = 0; c < numcols; c++) {  
                    if (r == 0) {  
                        records[r][c] = cur.getColumnName(c);  
                        records[r + 1][c] = cur.getString(c);  
                    } else {  
                        records[r + 1][c] = cur.getString(c);  
                    }  
                //  Log.i("value" + r + " " + c, records[r][c]);  
                }  
                cur.moveToNext();  
                r++;  
            }  
  
            cur.close();  
        }  
        
        try {  
            // 首先要使用Workbook类的工厂方法创建一个可写入的工作薄(Workbook)对象  
            
        	wwb = Workbook.createWorkbook(outexcelfile); 
            
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
       
        if (wwb != null) {  
            // 创建一个可写入的工作表  
            // Workbook的createSheet方法有两个参数，第一个是工作表的名称，第二个是工作表在工作薄中的位置  
            WritableSheet ws = wwb.createSheet("sheet1", 0);  
  
            // 下面开始添加单元格  
            for (int i = 0; i < numrows + 1; i++) {  
                for (int j = 0; j < numcols; j++) {  
                    // 这里需要注意的是，在Excel中，第一个参数表示列，第二个表示行  
                    Label labelC = new Label(j, i, records[i][j]);  
            //      Log.i("Newvalue" + i + " " + j, records[i][j]);  
                    try {  
                        // 将生成的单元格添加到工作表中  
                        ws.addCell(labelC);  
                    } catch (RowsExceededException e) {  
                        e.printStackTrace();  
                    } catch (WriteException e) {  
                        e.printStackTrace();  
                    }  
  
                }  
            }  
  
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
        return outexcelfile;
    }  
} 