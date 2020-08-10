package com.huabo.gkb.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	public static final String DATE_JFP_STR="yyyyMM";
	public static final String DATE_FULL_STR = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_SMALL_STR = "yyyy-MM-dd";
	public static final String DATE_KEY_STR = "yyMMddHHmmss";
	public static final String DATE_KEY_ALLSTR = "YYYYMMddHHmmss"; 
	    
	public static Timestamp testTimestampToString(String date) {  
		 Timestamp ts = Timestamp.valueOf(date);  
		 return ts;
	}  

	/**
     * 将指定的日期转换成Unix时间戳
     * @param String date 需要转换的日期 yyyy-MM-dd HH:mm:ss
     * @return long 时间戳
     */
    public static long dateToUnixTimestamp(String date) {
        long timestamp = 0;
        try {
            timestamp = new SimpleDateFormat(DATE_FULL_STR).parse(date).getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return timestamp;
    }
    
    /**
     * 将指定的日期转换成Unix时间戳
     * @param String date 需要转换的日期 yyyy-MM-dd
     * @return long 时间戳
     */
    public static long dateToUnixTimestamp(String date, String dateFormat) {
        long timestamp = 0;
        try {
            timestamp = new SimpleDateFormat(dateFormat).parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timestamp;
    }
     
    /**
     * 将当前日期转换成Unix时间戳
     * @return long 时间戳
     */
    public static long dateToUnixTimestamp() {
        long timestamp = new Date().getTime();
        return timestamp;
    }
    
    /**
	 * 获取时间的简写
	 * 传入参数 
	 * date  Date 类型	转换时间
	 * pattern  String 类型		转换格式
	 */
	public static String getIntDateStr(Date date,String pattern){
		if(date == null){
			return "";
		}
		DateFormat dft = new SimpleDateFormat(pattern);
		String str = dft.format(date);
		return str;
	}
}
