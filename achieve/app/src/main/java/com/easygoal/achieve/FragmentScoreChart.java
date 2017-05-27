package com.easygoal.achieve;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


public class FragmentScoreChart extends Fragment {
	
	
	  XYMultipleSeriesDataset  dataset;
	  XYMultipleSeriesRenderer renderer; 
	  int MaxDays;
	  LinearLayout layout;
	  View chart;
	  String Tags="FragmentScoreChart";
	  
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		List  x =  new   ArrayList();  //点集的x坐标 
	    List  y =  new   ArrayList();  //点集的y坐标 
		
	   switch (TaskData.dataperiod){
	   case 1:MaxDays=30;
	          break;
	   case 2:MaxDays=90;
              break;
	   case 3:MaxDays=180;
              break;
       default:break;       
	   }
	   MaxDays=TaskData.dataperiod;
      SimpleDateFormat formatter2 = new SimpleDateFormat ("yyMMdd");
      String curDate2 = formatter2.format(new Date());
      long closedate = Integer.parseInt(curDate2);
       double[] x1 = new double[MaxDays+1];
       double[] y1 = new double[MaxDays+1];
       double[] x2 = new double[MaxDays+1];
       double[] y2 = new double[MaxDays+1];
       double[] x3 = new double[MaxDays+1];
       double[] y3 = new double[MaxDays+1];
       String str_y = null;
       String str_x;
       String str_setdate = null;
       Cursor c;
       int year;
       int month;
       int day;
       String str_today;
       long setdate = 0;
       Calendar calendar = new GregorianCalendar();
       SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm");
       year=calendar.get(Calendar.YEAR);
       month=calendar.get(Calendar.MONTH);
       day=calendar.get(Calendar.DAY_OF_MONTH); 
       str_today = year + "-" + (month+1) + "-" + (day)+" 23:59";
       //String curdate = sdf.format(new Date());	 
	   Date today;
	try {
		today = sdf.parse(str_today);
		 calendar.setTime(today);
		  //Toast.makeText(getActivity(),"today"+today.toString(), Toast.LENGTH_LONG).show();
	} catch (java.text.ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		 // Toast.makeText(getActivity(), "calendar failed", Toast.LENGTH_LONG).show();
	}
	  
       
       for (int i=MaxDays;i>=0;i--){
    	   x1[i]=i;
    	   x2[i]=i;
 		   x3[i]=i;	       
 		   String putDate = sdf.format(calendar.getTime()); 
           setdate=TimeData.changeStrToTime_YY(putDate);
           Log.d(Tags,"setdate"+setdate);
           c = TaskData.db_TdDB.rawQuery("select * from "+
 		     	   TaskData.TdDB.TABLE_NAME_TaskMain+" where "+
 		     	   TaskData.TdDB.TASK_CLOSEDTIMEDATA+"<? and "+
 		     	   TaskData.TdDB.TASK_USER+"=?", 
 		     	   new String[]{String.valueOf(setdate),TaskData.user});
           Log.d(Tags,"setdate"+c.getCount());
             y1[i]=TaskData.field_sum(c, TaskData.TdDB.SUM_ACHIEVED);
             y2[i]=TaskData.field_sum(c, TaskData.TdDB.SUM_EXPERIENCE);
             y3[i]=TaskData.field_sum(c, TaskData.TdDB.SUM_ENJOYMENT);
             calendar.add(calendar.DATE,-1);
          }
		//Toast.makeText(getActivity(), str_y, Toast.LENGTH_LONG).show();
	    // Toast.makeText(getActivity(), str_setdate, Toast.LENGTH_LONG).show();
		 String[] titles = new String[] { "执行力","经验","快乐"};  
		 x.add(x1);
		 x.add(x2);
		 x.add(x3);
		 y.add(y1);
		 y.add(y2);
		 y.add(y3);
		 
		 dataset = buildDataset(titles, x, y);  
         int color1=getResources().getColor(R.color.darkgreen);
         int color2=getResources().getColor(R.color.darkorange);
         int color3=getResources().getColor(R.color.orchid);
        int [] colors =  new   int [] {color1,color2,color3};
        PointStyle [] styles = 
        new   PointStyle []{ PointStyle.CIRCLE , PointStyle.DIAMOND,PointStyle.TRIANGLE };
       
       renderer =  buildRenderer(colors, styles, true); 
   
       renderer.setChartTitleTextSize(32);
       //renderer.setApplyBackgroundColor(false);
       renderer.setGridColor(getResources().getColor(R.color.red));
       renderer.setBackgroundColor(getResources().getColor(R.color.black));
       //renderer.setFitLegend(true);
       //renderer.setShowGrid(true);
       //renderer.setShowGridX(true);
       renderer.setAxesColor(getResources().getColor(R.color.orange));
       renderer.setMarginsColor(getResources().getColor(R.color.white));  
       renderer.setChartTitle( "执行力" );  
       renderer.setXTitle( "x轴-天" );  
       renderer.setYTitle( "y值-点数" );  
       renderer.setXAxisMin(-1);  
       renderer.setXAxisMax(30);  
       renderer.setYAxisMin(-1);  
       renderer.setYAxisMax(4000);
       renderer.setAxisTitleTextSize(28);
       // 控制横纵轴的值大小
       renderer.setChartTitleTextSize(32);
       renderer.setLabelsTextSize(28);
       renderer.setFitLegend(true);
       renderer.setLegendTextSize(32);
       renderer.setPointSize(5f);
       renderer.setMargins(new int[]{ 50, 80, 60, 40 });
      
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
	 View v=inflater.inflate(R.layout.subfg_scoretab_toptab3_scorechart, container, false);	  
	      layout = (LinearLayout) v.findViewById(R.id.ll_chart);
	      chart = ChartFactory.getLineChartView(getActivity(), dataset, renderer); 
	      layout.addView(chart);
       return v;
	}  
	
	  protected XYMultipleSeriesRenderer buildRenderer(int[] colors, PointStyle[] styles, boolean fill)   
	    {   
	        XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();   
	        int length = colors.length;   
	        for (int i = 0; i < length; i++)   
	        {   
	            XYSeriesRenderer r = new XYSeriesRenderer();   
	            r.setColor(colors[i]);   
	            r.setPointStyle(styles[i]);   
	            r.setFillPoints(fill);   
	            renderer.addSeriesRenderer(r);   
	        }   
	        return renderer;   
	    }  
	
	
	protected XYMultipleSeriesDataset buildDataset(String[] titles,   
              List x,   
              List y)   
    {   
         XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();  

         int length = titles.length;                  //有几条线   
         for (int i = 0; i < length; i++)   
          {   
             XYSeries series = new XYSeries(titles[i]);    //根据每条线的名称创建   
             double[] xV = (double[]) x.get(i);                 //获取第i条线的数据   
             double[] yV = (double[]) y.get(i);   
             int seriesLength = xV.length;                 //有几个点  

          for (int k = 0; k < seriesLength; k++)        //每条线里有几个点   
          {   
               series.add(xV[k], yV[k]);   
          }  

               dataset.addSeries(series);   
           }  

               return dataset;   
     }  
	  /*
	 private XYMultipleSeriesDataset getBarDemoDataset() {  
	        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();  
	        final int nr = 10;  
	        Random r = new Random();  
	        for ( int i = 0; i < nr ; i++) {  
	          CategorySeries series = new CategorySeries( "Demo series " + (i + 1));  
	          for ( int k = 0; k < nr; k++) {  
	            series.add(100 + r.nextInt() % 100);  
	          }  
	          dataset.addSeries(series.toXYSeries());  
	        }  
	        String[] titles = new String[] { "Crete", "Corfu", "Thassos",  
            "Skiathos" };  
    // x轴的值  
    List<double[]> x = new ArrayList<double[]>();  
    for (int i = 0; i < titles.length; i++) {  
        x.add(new double[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 });  
    }  
    // y轴的值  
    List<double[]> values = new ArrayList<double[]>();  
    values.add(new double[] { 12.3, 12.5, 13.8, 16.8, 20.4, 24.4, 26.4,  
            26.1, 23.6, 20.3, 17.2, 13.9 });  
	        return dataset;  
	      }  
	    public XYMultipleSeriesRenderer getBarDemoRenderer() {  
	        XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();  
	        SimpleSeriesRenderer r = new SimpleSeriesRenderer();  
	        r.setColor(Color. BLUE );  
	        renderer.addSeriesRenderer(r);    
	        setChartSettings(renderer);  
	        return renderer;  
	      }  
	   
	       private void setChartSettings(XYMultipleSeriesRenderer renderer) {  
	        renderer.setChartTitle( "Chart demo" );  
	        renderer.setXTitle( "x values" );  
	        renderer.setYTitle( "y values" );  
	        renderer.setXAxisMin(0.5);  
	        renderer.setXAxisMax(10.5);  
	        renderer.setYAxisMin(0);  
	        renderer.setYAxisMax(210);  
	      }  
	 
	 
	*/
	
}
