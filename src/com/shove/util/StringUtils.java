package com.shove.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;




public class StringUtils {
    
    /**
     * 金额大写转换
     * digitUppercase
     * @auth hejiahua
     * @param n
     * @return 
     * String
     * @exception 
     * @date:2014-11-4 下午4:52:37
     * @since  1.0.0
     */
    public static String digitUppercase(double n){
        String fraction[] = {"角", "分"};
        String digit[] = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };
        String unit[][] = {{"元", "万", "亿"},
                     {"", "拾", "佰", "仟"}};
 
        String head = n < 0? "负": "";
        n = Math.abs(n);
         
        String s = "";
        for (int i = 0; i < fraction.length; i++) {
            s += (digit[(int)(Math.floor(n * 10 * Math.pow(10, i)) % 10)] + fraction[i]).replaceAll("(零.)+", "");
        }
        if(s.length()<1){
            s = "整";    
        }
        int integerPart = (int)Math.floor(n);
 
        for (int i = 0; i < unit[0].length && integerPart > 0; i++) {
            String p ="";
            for (int j = 0; j < unit[1].length && n > 0; j++) {
                p = digit[integerPart%10]+unit[1][j] + p;
                integerPart = integerPart/10;
            }
            s = p.replaceAll("(零.)*零$", "").replaceAll("^$", "零") + unit[0][i] + s;
        }
        return head + s.replaceAll("(零.)*零元", "元").replaceFirst("(零.)+", "").replaceAll("(零.)+", "零").replaceAll("^整$", "零元整");
    }
    
	public static final String[] number = new String[]{"十", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
	/**
	 * 数字转换成字母
	 * @param i
	 * @return
	 */
	public static String getChar(int i){
		return (char) (64 + i) + "";
	}
	
	/**
	 * 格式化字符串  *代替
	 * formatStr
	 * @auth hejiahua
	 * @param object
	 * @return 
	 * String
	 * @exception 
	 * @date:2014-11-3 下午6:18:32
	 * @since  1.0.0
	 */
	public static String formatStr(String object,int x){
	    try {
	        if (org.apache.commons.lang.StringUtils.isBlank(object)) {
	            return object;
	        }else {
	           char [] c = object.toCharArray();
	           for (int i = 0; i < c.length; i++) {
	               if (i>=x) {
	                   c[i] ='*';
	               }
	           }
	           return String.valueOf(c);
	        }
        }
        catch (Exception e) {
           return formatStr(object, 1);
        }
	}
	
	/**
     * 格式化字符串  *代替(包括结束跟开始位置),长度大于6以上才可以进行的
     * formatStr
     * @auth hejiahua
     * @param object
     * @return 
     * String
     * @exception 
     * @date:2014-11-3 下午6:18:32
     * @since  1.0.0
     */
    public static String formatStr(String object,int start,int end){
        if (org.apache.commons.lang.StringUtils.isBlank(object)) {
            return object;
        }else {
           try {
               char [] c = object.toCharArray();
               startWith:
               for (int i = 0; i < c.length; i++) {
                   if (start>c.length-1) {
                       i = 0;
                       start--;
                       continue startWith;
                   }
                   if (end>c.length-1) {
                       i = 0;
                       end--;
                       continue startWith;
                   }
                   
                   if (i>=start && i<=end) {
                       c[i] ='*';
                   }
               }
               return String.valueOf(c);
        }
        catch (Exception e) {
           return formatStr(object, 2);
        }
        }
    }
	
	
	/**
	 * 将此字符串转换为一个新的字符数组
	 * @param answer
	 * @return
	 */
	public static String[] strToArray(String answer){
		if(org.apache.commons.lang.StringUtils.isNotBlank(answer)){
			return answer.split("");
		}
		return null;
	}
	
	/**
	 * 返回栏目题号
	 * @return
	 */
	public static String getColumnNum(String num){
		char[] cr = num.toCharArray();
		int length = cr.length;
		int[] arr = new int[length];
		for(int i = 0; i < cr.length; i ++){
			arr[i] = Integer.parseInt(cr[i]+"");
		}
		StringBuffer strBuffer = new StringBuffer();
		switch (length) {
			case 1:
				strBuffer.append(number[arr[0]]);
				break;
			case 2:
				if("10".equals(num)){
					strBuffer.append(number[arr[1]]);
					break;
				}else if(arr[0] == 1){
					strBuffer.append(number[0]).append(number[arr[1]]);
					break;
				}else if(arr[1] == 0){
					strBuffer.append(number[arr[0]]).append(number[arr[1]]);
				}else{
					strBuffer.append(number[arr[0]]).append(number[0]).append(number[arr[1]]);
				}
				break;
			default:
				break;
		}
		return strBuffer.toString();
	}
	
	/**
     * 手机号码: 
     * 13[0-9], 14[5,7], 15[0, 1, 2, 3, 5, 6, 7, 8, 9], 17[6, 7, 8], 18[0-9], 170[0-9]
     * 移动号段: 134,135,136,137,138,139,150,151,152,157,158,159,182,183,184,187,188,147,178,1705
     * 联通号段: 130,131,132,155,156,185,186,145,176,1709
     * 电信号段: 133,153,180,181,189,177,1700
     */
    final static  String PATTER_MOBILE = "^1(3[0-9]|4[57]|5[0-35-9]|8[0-9]|70)\\d{8}$";
    /**
     * 中国移动：China Mobile
     * 134,135,136,137,138,139,150,151,152,157,158,159,182,183,184,187,188,147,178,1705
     */
    final static String PATTER_CM = "(^1(3[4-9]|4[7]|5[0-27-9]|7[8]|8[2-478])\\d{8}$)|(^1705\\d{7}$)";
    /**
     * 中国联通：China Unicom
     * 130,131,132,155,156,185,186,145,176,1709
     */
    final static  String PATTER_CU = "(^1(3[0-2]|4[5]|5[56]|7[6]|8[56])\\d{8}$)|(^1709\\d{7}$)";
    /**
     * 中国电信：China Telecom
     * 133,153,180,181,189,177,1700
     */
    final static String PATTER_CT = "(^1(33|53|77|8[019])\\d{8}$)|(^1700\\d{7}$)";
	
	/**
	 * 匹配手机号码的正则表达式-通用方法，后期正需修改正则表达式即可
	 * matchPhone
	 * @auth hejiahua
	 * @param phone
	 * @return 
	 * boolean  true  表示正确、false  表示错误
	 * @exception 
	 * @date:2015-1-20 上午11:55:51
	 * @since  1.0.0
	 */
	public static boolean matchPhone(String phone){
	    if (phone==null) {
            return false;
        }else {
            Pattern pattern = Pattern.compile(PATTER_MOBILE);
            Matcher m = pattern.matcher(phone);
            return m.matches();
        }
	}
	
	
	/**
	 * 邮箱验证的正则
	 * matchEmail
	 * @auth hejiahua
	 * @param email
	 * @return 
	 * boolean  true  表示正确、false  表示错误
	 * @exception 
	 * @date:2015-1-26 下午1:29:49
	 * @since  1.0.0
	 */
	public static boolean matchEmail(String email){
	    if (email == null) {
            return false;
        }else {
            Pattern pattern = Pattern.compile("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
            Matcher m = pattern.matcher(email);
            return m.matches();
        }
	}
	
}
