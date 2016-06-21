package com.sp2p.util;

import java.io.File;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sp2p.constants.IConstants;

public class WebUtil {

	public static String getBasePath() {
		return getWebPath();

	}

	/**
	 * @MethodName: getWebPath
	 * @Param: WebUtil
	 * @Author: gang.lv
	 * @Date: 2013-5-12 下午10:57:47
	 * @Return:
	 * @Descb: 获取web路径
	 * @Throws:
	 */
	public static String getWebPath() {
		return IConstants.WEB_URL;
	}
	
	/**
	 * 获取文件路径
	 * getPackagePath:
	 * @author Administrator
	 * @param filename
	 * @param isTest
	 * @return
	 * @since JDK 1.6
	 */
	public static String getPackagePath(String filename,boolean isTest,URL urlpath)  
    {  
        String result=null;  
        String path=urlpath.toString();  
        if(path.startsWith("file"))  
        {  
            path=path.substring(6);  
        }  
        //System.out.println(path);
        path.replace("/", File.separator);  
        result=path+filename;  
        if (!isTest) {
        	result = "/"+result;
		}
        System.out.println(result);
        return result;  
    }  
	
}
