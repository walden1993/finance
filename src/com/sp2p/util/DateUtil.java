/**
 * 
 */
package com.sp2p.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.shove.Convert;

/**
 * 时间工具类
 * @author Administrator
 *
 */
public class DateUtil {
		
	public final static DateFormat YYYY_MM_DD_MM_HH_SS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public final static DateFormat YYYY_MM_DD = new SimpleDateFormat("yyyy-MM-dd");
	
	public final static DateFormat YYYYMMDDPoint = new SimpleDateFormat("yyyy.MM.dd");
	
	public final static DateFormat YYYYMMDDMMHHSSSSS = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	
	public final static DateFormat YYYYMMDDHHMMSS = new SimpleDateFormat("yyyyMMddHHmmss");

	public static final DateFormat YYYYMMDD = new SimpleDateFormat("yyyyMMdd");
	/**
	 * 时间转换为yyyy-MM-dd HH:mm:ss格式的字符串
	 * @param date
	 * @return
	 */
	public static String dateToString(Date date){
		return YYYY_MM_DD_MM_HH_SS.format(date);
	}
	
	public static Date strToDate(String dateString){
		Date date = null;
		try {
			date = YYYY_MM_DD_MM_HH_SS.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	public static Date strToYYMMDDDate(String dateString){
		Date date = null;
		try {
			date = YYYY_MM_DD.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 * 计算两个时间之间相差的天数
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static long diffDays(Date startDate,Date endDate){
		long days = 0;
		long start = startDate.getTime();
		long end = endDate.getTime();
		//一天的毫秒数1000 * 60 * 60 * 24=86400000
		days = (end - start) / 86400000;
		return days;
	}
	
	/**
	 * 日期加上月数的时间
	 * @param date
	 * @param month
	 * @return
	 */
	public static Date dateAddMonth(Date date,int month){
		return add(date,Calendar.MONTH,month);
	}
	
	/**
	 * 日期加上天数的时间
	 * @param date
	 * @param month
	 * @return
	 */
	public static Date dateAddDay(Date date,int day){
		return add(date,Calendar.DAY_OF_YEAR,day);
	}
	
	/**
	 * 日期加上年数的时间
	 * @param date
	 * @param year
	 * @return
	 */
	public static Date dateAddYear(Date date,int year){
		return add(date,Calendar.YEAR,year);
	}
	
	 /** 
     * 计算剩余时间 (多少天多少时多少分)
     * @param startDateStr 
     * @param endDateStr 
     * @return 
     */  
    public static String remainDateToString(Date startDate, Date endDate){  
    	StringBuilder result = new StringBuilder();
    	if(endDate == null ){
    		return "过期";
    	}
    	long times = endDate.getTime() - startDate.getTime();
    	if(times < -1){
    		result.append("过期");
    	}else{
    		long temp = 1000 * 60 * 60 *24;
    		//天数
    		long d = times / temp;

    		//小时数
    		times %= temp;
    		temp  /= 24;
    		long m = times /temp;
    		//分钟数
    		times %= temp;
    		temp  /= 60;
    		long s = times /temp;
    		
    		result.append(d);
    		result.append("天");
    		result.append(m);
    		result.append("小时");
    		result.append(s);
    		result.append("分");
    	}
    	return result.toString();
    }  
    
	private static Date add(Date date,int type,int value){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(type, value);
		return calendar.getTime();
	}
	
	
	/**   
	 * @MethodName: getLinkUrl  
	 * @Param: DateUtil  
	 * flag ： true 转换  false 不转换
	 * @Author: gang.lv
	 * @Date: 2013-5-8 下午02:52:44
	 * @Return:    
	 * @Descb: 
	 * @Throws:
	*/
	public static String getLinkUrl(boolean flag,String content,String id){
		if(flag){
			content = "<a href='finance.do?id="+id+"'>"+content+"</a>";
		}
		return content;
	}
	
	/**
	 * 时间转换为时间戳
	 * @param format
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static long getTimeCur(String format,String date) throws ParseException{
		SimpleDateFormat sf = new SimpleDateFormat(format);
		return  sf.parse(sf.format(date)).getTime();
	}
	/**
	 * 时间转换为时间戳
	 * @param format
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static long getTimeCur(String format,Date date) throws ParseException{
		SimpleDateFormat sf = new SimpleDateFormat(format);
		return  sf.parse(sf.format(date)).getTime();
	}
	
	/**
	 * 将时间戳转为字符串 
	 * @param cc_time
	 * @return
	 */
	public static String getStrTime(String cc_time) { 
	 String re_StrTime = null; 
	 SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss"); 
	 long lcc_time = Long.valueOf(cc_time); 
	 re_StrTime = sdf.format(new Date(lcc_time * 1000L)); 
	 return re_StrTime; 
	 } 
	
	/**
	* 时间转换为时间戳
	*
	* @param format
	* @param date
	* @return
	* @throws ParseException
	*/
	public static String getTimeCurS(String format, Date date) throws ParseException {
	        SimpleDateFormat sf = new SimpleDateFormat(format);
	return Convert.strToStr(sf.parse(sf.format(date)).getTime() + "", "");
	}
	
	/**
	 * 获取某一天是星期几
	 * @Title: getWeekDayString 
	 * @param @param date
	 * @param @return 设定文件 
	 * @return int 返回类型 
	 * @author hjh
	 * @throws
	 */
	public static int getWeekDayString(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date); 
		return  calendar.get(Calendar.DAY_OF_WEEK);
	}
	
	public static void main(String[] args) {
		System.out.println(getWeekDayString(new Date()));
	}
}
