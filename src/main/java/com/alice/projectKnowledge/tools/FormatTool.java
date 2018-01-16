package com.alice.projectKnowledge.tools;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatTool {
	
	public static final String YYYY = "yyyy";
	public static final String YYYYMM = "yyyy-MM";
	public static final String YYYYMMDD = "yyyy-MM-dd";
	public static final String YYYYMMDDHH = "yyyy-MM-dd hh";
	public static final String YYYYMMDDHHMM = "yyyy-MM-dd hh:mm";
	public static final String YYYYMMDDHHMMSS = "yyyy-MM-dd hh:mm:ss";

	public static Date stringToDate(String dateStr,String pattern) throws ParseException {
		DateFormat format = getDateFormat(1,pattern);
		return format.parse(dateStr);
	}
	
	public static String dateToString(Date date,String pattern) throws ParseException {
		DateFormat format = getDateFormat(1,pattern);
		return format.format(date);
	}
	
	private static DateFormat getDateFormat(int type,String pattern) {
		if(1 == type) {
			return new SimpleDateFormat(pattern);
		}
		return null;
	}
	
	public static void main(String[] args) {
		String dateStr = "2018-01-21";
		try {
			System.out.println(FormatTool.stringToDate(dateStr, YYYYMMDD));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Date date = new Date();
		try {
			System.out.println(FormatTool.dateToString(date, YYYYMMDDHHMMSS));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
