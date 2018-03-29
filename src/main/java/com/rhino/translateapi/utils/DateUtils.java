package com.rhino.translateapi.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtils {
	public static Long dateString2Long(String date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date thisDate = null;
		try {
			thisDate = format.parse(date);
		} catch (ParseException e) {
			return null;
		}
		return new Long(thisDate.getTime());
	}
	
	public static Long dateTimeString2Long(String date,String formatString) {
		SimpleDateFormat format = new SimpleDateFormat(formatString);
		Date thisDate = null;
		try {
			thisDate = format.parse(date);
		} catch (ParseException e) {
			return null;
		}
		return new Long(thisDate.getTime());
	}
	
	public static Long dateTimeString2Long(String date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date thisDate = null;
		try {
			thisDate = format.parse(date);
		} catch (ParseException e) {
			return null;
		}
		return new Long(thisDate.getTime());
	}
	
	public static Long dateTimeString2LongNotss(String date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date thisDate = null;
		try {
			thisDate = format.parse(date);
		} catch (ParseException e) {
			return null;
		}
		return new Long(thisDate.getTime());
	}
	
	public static Long dateTime2Long(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String thisDate = null;
		long dateTime = 0;
		try {
			thisDate = format.format(date);
			if(thisDate!=null){
				dateTime = dateTimeString2Long(thisDate);
			}
		} catch (Exception e) {
			return 0L;
		}
		return dateTime;
	}
	
	public static Long dateTime2LongNotS(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String thisDate = null;
		long dateTime = 0;
		try {
			thisDate = format.format(date);
			if(thisDate!=null){
				dateTime = dateTimeString2Long(thisDate);
			}
		} catch (Exception e) {
			return 0L;
		}
		return dateTime;
	}
	
	
	public static Long dateTime2Long(Date date,String formatString) {
		SimpleDateFormat format = new SimpleDateFormat(formatString);
		String thisDate = null;
		long dateTime = 0;
		try {
			thisDate = format.format(date);
			if(thisDate!=null){
				dateTime = dateTimeString2Long(thisDate,formatString);
			}
		} catch (Exception e) {
			return 0L;
		}
		return dateTime;
	}
	
	public static Date stringTime2Date(String date){
		long longDate = dateTimeString2Long(date);
		return new Date(longDate);
	}
	
	public static Date stringTime2DateNotss(String date){
		long longDate = dateTimeString2LongNotss(date);
		return new Date(longDate);
	}
	
	public static Date stringDay2Date(String date){
		long longDate = dateString2Long(date);
		return new Date(longDate);
	}
	
	public static String dateTime2String(Date date){
		long longdate = dateTime2Long(date);
		return long2TimeString(longdate);
	}
	
	
	public static String dateTime2StringNotS(Date date){
		long longdate = dateTime2LongNotS(date);
		return long2TimeStringNotS(longdate);
	}
	
	/**
	 * 日期转换为去除秒后的字符串
	 * @param date 要转换的日期
	 * @return 转换后的
	 */
	
	public static String dateTime2String(Date date,String formatString) {
		long time = dateTime2Long(date, formatString);
		return long2TimeString(time, formatString);
	}
	
	
	public static String dateTimeNotS2String(Date date) {
		String stringdate = dateTime2String(date);
		long stringDate = dateTimeNotSString2Long(stringdate);
		return long2TimeStringNotS(stringDate);
	}
	
	public static Date stringTimeNotS2Date(String date){
		long longDate = dateTimeNotSString2Long(date);
		return new Date(longDate);
	}
	
	public static Long dateTimeNotSString2Long(String date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date thisDate = null;
		try {
			thisDate = format.parse(date);
		} catch (ParseException e) {
			return null;
		}
		return new Long(thisDate.getTime());
	}
	public static String long2DateString(Long time){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		if (time!=null){
			if (time.longValue()!=0){
				return format.format(new Date(time.longValue()));
			}
		}
		return null;
	}
	public static String long2MonthString(Long time){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		if (time!=null){
			if (time.longValue()!=0){
				return format.format(new Date(time.longValue()));
			}
		}
		return null;
	}
	public static String long2DateTimeString(Long time){
		SimpleDateFormat format = new SimpleDateFormat("HH:mm");
		if (time!=null){
			if (time.longValue()!=0){
				return format.format(new Date(time.longValue()));
			}
		}
		return null;
	}

	public static String long2TimeString(Long time,String formatString){
		SimpleDateFormat format = new SimpleDateFormat(formatString);
		if (time!=null){
			if (time.longValue()!=0){
				return format.format(new Date(time.longValue()));
			}
		}
		return null;
	}
	
	public static String long2TimeString(Long time){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (time!=null){
			if (time.longValue()!=0){
				return format.format(new Date(time.longValue()));
			}
		}
		return null;
	}
	
	public static String long2TimeStringNotS(Long time){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		if (time!=null){
			if (time.longValue()!=0){
				return format.format(new Date(time.longValue()));
			}
		}
		return null;
	}

	/**
	 * 月份左右移动某几个月
	 * @param time
	 * @param step
	 * @return
	 */
		public static long getMoveMonthDate(long time,int step){
			Calendar end = Calendar.getInstance();
			end.setTimeInMillis(time);
			end.roll(Calendar.MONTH,step);
			long endTime = end.getTimeInMillis();
			return endTime;
		}
		
		/**
		 * 时间左右移动几年
		 */
		public static long getMoveYearDate(long time,int step){
			Calendar end = Calendar.getInstance();
			end.setTimeInMillis(time);
			end.roll(Calendar.YEAR,step);
			long endTime = end.getTimeInMillis();
			return endTime;
		}
		/**
		 * 某时间左右移动某几天
		 * @param time
		 * @param step
		 * @return
		 */
		public static long getEndValidDate4Day(long time,int step){
			Calendar end = Calendar.getInstance();
			end.setTimeInMillis(time);
			end.add(Calendar.DATE,step);
			long endTime = end.getTimeInMillis();
			return endTime;
		}
		
		
		public static Date getEndValidDate4Day(Date date,int step){
			Calendar end = Calendar.getInstance();
			end.setTime(date);
			end.add(Calendar.DATE,step);
			return end.getTime();
		}
		
		public static Long getDayLong2ValidLong(Long day){
			day = day==null?new Long(new Date().getTime()):day;
			String str = long2DateString(day);
			Long result = dateString2Long(str);
			return result;
		}
		
		/**
		 * 得到本周周一的时间值
		 * @param time
		 * @return
		 */
		public static long getMondayDayOfWeek(long time){
			time = getDayLong2ValidLong(new Long(time)).longValue();
	        Calendar  day = new GregorianCalendar();
			day.setTimeInMillis(time);
			day.set(GregorianCalendar.DAY_OF_WEEK,GregorianCalendar.MONDAY);
			long validTime = day.getTimeInMillis();
			return validTime;
		}
		
		/**
		 * 格式化手机时间
		 * @param str 要格式的时间
		 * @return 格式后的时间
		 */
		public static String phoneDateFormat(String str) {
			if(str.equals("")) {
				return str;
			} else {
				str = str.replaceAll("[^0-9:-]", " ");
			}
			return str;
		}
		
		public static String getDate2() {
			Date d = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			return sdf.format(d);
		}
		
		public static String getDate4() {
			Date d = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			return sdf.format(d);
		}

		public static String getDate5() {
			Date d = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:hh");
			return sdf.format(d);
		}

		public static String getDate6() {
			Date d = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmhh");
			return sdf.format(d);
		}

		public static String getDate3(int i) {
			Calendar cal=Calendar.getInstance();
	        cal.add(Calendar.DATE,i);
	        Date time = cal.getTime();
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			return sdf.format(time);
		}
		
		public static boolean beform(String date) {
			Date dat = DateUtils.stringDay2Date(date);
			//System.out.println(dat);
			boolean falg = dat.before(new Date());
			return falg;
		}
		
		public static void main(String[] args) {
			/*long l1 = 1381393933000l;
			long l2=1381393933219l;
			String s1 = long2TimeString(l1);
			String s2 = long2TimeString(l2);
			System.out.println(s1+" : "+s2);*/
			//String res = dateTime2String(new Date(),"MM月dd日HH:mm");
			//boolean res = beform("2014-12-11");
			String mp_number = "18500232054";
			mp_number = mp_number.substring(0, 7);
			System.out.println(mp_number);
		}
}
