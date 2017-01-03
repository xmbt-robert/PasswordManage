package com.fx.passform.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.jfree.data.time.Year;

public class GetDate {
	
	/**
	 * 先转成Data类型，最后用Calendar进行日期的增加或减少
　　		再转成string
		这样就可以得到java获取向前多少天或向后n天的日期时间了
	 */
	
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	//获取当前系统日期
	public static String getDate(){
		
		return sdf.format(new Date()); //new Date()为获取当前系统时间
	}
	
	public static String getCurrentDate(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return sdf.format(new Date()); //精确到时分
	}
	
	//获取当前系统年
	public static Integer getYear1() {
			Calendar now = Calendar.getInstance();
			int getYear = now.get(Calendar.YEAR);
			return getYear;
		}
	
	
	public static String getYear() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		return sdf.format(new Date());
	}
	
	//得到系统日期之前的N个日期
	@SuppressWarnings("static-access")
	public static String getBeforeDate(int number){
		try {
			Date date = sdf.parse(getDate());
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			calendar.add(calendar.DATE, number); //把日期往前推一天.整数往后推,负数往前移动
			date = calendar.getTime(); //这个时间就是日期往前推一天的结果
			String beforeDate = sdf.format(date); //增加一天后的日期
			return beforeDate;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public static String[] getWeekDate(){
		String[] date = new String[7];
		Calendar calendar = new GregorianCalendar();
		for (int i = 0; i < 7; i++)
		{
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH)  + 1; // Jan = 0, not 1
		int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
		date[i] = (year + "-" + month + "-" + dayOfMonth);
		calendar.add(Calendar.DAY_OF_MONTH, -1); //得到当前日期之前在内的7天时间
		}
		return date;
	}
	
	/**
	 * 计算选择年月份的天数
	 * @return
	 */
	public static  int get_DAY_OF_MONTH(String year_Month) {

			Calendar rightNow = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM"); // 如果写成年月日的形式的话，要写小d，如："yyyy/MM/dd"
			try {
				rightNow.setTime(sdf.parse(year_Month)); 
			} catch (ParseException e) {
				e.printStackTrace();
			}
			int days = rightNow.getActualMaximum(Calendar.DAY_OF_MONTH);
			return days;
	}
	
}
