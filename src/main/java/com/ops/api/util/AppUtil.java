package com.ops.api.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class AppUtil {

	final static Logger logger = LoggerFactory.getLogger(AppUtil.class);
	
	public static String lastExecTime="";
	
	public static int iterationNumber=0;
	
		
	public static String getDateString(){
		Date date = new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		return ""+year+"-"+month+"-";
	}

	

	public static java.sql.Date getSqlDate(java.util.Date javaDate){
		java.sql.Date sqlDate = new java.sql.Date(javaDate.getTime()); 
		return sqlDate;
		
	}
	
	public static Timestamp getTimestampFromDateString(String dateString){
		DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Timestamp timeStampDate=null;		
		try {
			java.util.Date oldFormat = df1.parse(dateString);
			timeStampDate=new Timestamp(oldFormat.getTime());  
		} catch (ParseException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
		return timeStampDate;
	}
	public static String getFormatedDate(String dateString){
		DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Timestamp timeStampDate=null;	
		String formattedDate="";
		try {
			java.util.Date oldFormat = df1.parse(dateString);
			timeStampDate=new Timestamp(oldFormat.getTime());  
			formattedDate = df2.format(timeStampDate);
		} catch (ParseException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
		return formattedDate;
	}
	
	
	public static String makeDateStringFromSQLDate(Timestamp dateString){
		DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String javaDateString="";
		try {
			java.util.Date oldFormat = df2.parse(dateString.toString());
			javaDateString =  df1.format(oldFormat);
		} catch (ParseException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
		return javaDateString;
	}
	
		
}
