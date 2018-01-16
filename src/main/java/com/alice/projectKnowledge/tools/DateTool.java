package com.alice.projectKnowledge.tools;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class DateTool {

	public static Date getCurrentDate() {
		return new Date();
	}
	
	/**
	 * 比较时间先后
	 * @param date 一个时间点
	 * @param other 另一个时间点
	 * @return 1大于 0等于 -1小于
	 * @throws ParseException 
	 */
	public static int compareDate(String dateStr, String otherStr, String pattern) throws ParseException {
		Date date = FormatTool.stringToDate(dateStr, pattern);
		Date other = FormatTool.stringToDate(otherStr, pattern);
		return date.compareTo(other);
	}
	
	/**
	 * 增加月份
	 * @param date 日期
	 * @param number 月数
	 * @return 增加月份后的时间
	 */
	public static Date addMonth(Date date,int number){
		Calendar calender = Calendar.getInstance();
        calender.setTime(date);
        calender.add(Calendar.MONTH, number);
        return calender.getTime();
	}
	
	public static void main(String[] args) {
		String dateStr = "2017-12-12";
		String otherStr = "2017-12-12";
		try {
			System.out.println(compareDate(dateStr, otherStr, FormatTool.YYYYMMDD));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
