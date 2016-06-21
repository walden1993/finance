package com.shove.util;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public class CookieUtils {
    
    /**
     * 将cookie封装到Map里面
     * @param request
     * @return
     */
    private static  Map<String,Cookie> ReadCookieMap(HttpServletRequest request){  
        Map<String,Cookie> cookieMap = new HashMap<String,Cookie>();
        Cookie[] cookies = request.getCookies();
        if(null!=cookies){
            for(Cookie cookie : cookies){
                cookieMap.put(cookie.getName(), cookie);
            }
        }
        return cookieMap;
    }

    /**
     *添加cookie
     * @param request 
     * @param compName 键
     * @return
     */
    public static void setKey(HttpServletRequest request,HttpServletResponse response,String key,String value){
        Map<String,Cookie> cookieMap = ReadCookieMap(request);
        Cookie cookie = null;
        if(cookieMap.containsKey(key)){
            cookie = (Cookie)cookieMap.get(key);
            cookie.setValue(value);
        }else {
            cookie = new Cookie(key, value);
        }
        cookie.setMaxAge(600);
        response.addCookie(cookie);
    }
    
	/**
	 * 从Cookie里获取指定的值
	 * @param request 
	 * @param compName 键
	 * @return
	 */
	public static Object getKey(HttpServletRequest request, String compName){
		Cookie[] ck = request.getCookies();//返回一个数组，该数组包含这个请求中当前的所有cookie
		for (Cookie cookie : ck) {
			String key = cookie.getName();
			if(compName.equals(key)){//对比
				return cookie.getValue();
			}
		}
		return null;
	}
}
