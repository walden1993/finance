package com.shove.web.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpConnectionParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang.StringUtils;

import com.shove.config.GopayConfig;

public class GopayUtils {
	
	/**
	 * 获取国付宝服务器时间 用于时间戳
	 * @return 格式YYYYMMDDHHMMSS
	 */
	public static String getGopayServerTime() {
		HttpClient httpClient = new HttpClient();
		httpClient.getParams().setCookiePolicy(CookiePolicy.RFC_2109);
		httpClient.getParams().setIntParameter(HttpConnectionParams.SO_TIMEOUT, 10000); 
		GetMethod getMethod = new GetMethod(GopayConfig.gopay_server_time_url);
		getMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"GBK");  
		// 执行getMethod
		int statusCode = 0;
		try {
			statusCode = httpClient.executeMethod(getMethod);			
			if (statusCode == HttpStatus.SC_OK){
				String respString = StringUtils.trim((new String(getMethod.getResponseBody(),"GBK")));
				return respString;
			}			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			getMethod.releaseConnection();
		}
		return null;
	}
	
	/**
     * Convenience method to get the IP Address from client.
     * 
     * @param request the current request
     * @return IP to application
     */
    public static String getIpAddr(HttpServletRequest request) { 
    	if (request == null) return "";
    	
        String ip = request.getHeader("X-Forwarded-For"); 
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getHeader("Proxy-Client-IP"); 
        } 
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getHeader("WL-Proxy-Client-IP"); 
        } 
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getHeader("HTTP_CLIENT_IP"); 
        } 
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getHeader("HTTP_X_FORWARDED_FOR"); 
        } 
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getRemoteAddr(); 
        } 
        return ip; 
    } 
    
    /**
     * 对字符串进行MD5签名
     * 
     * @param text
     *            明文
     * 
     * @return 密文
     */
    public static String md5(String text) {
        return DigestUtils.md5Hex(getContentBytes(text, GopayConfig.gopay_input_charset));
    }
    
    public static final String sign(String src) throws UnsupportedEncodingException, NoSuchAlgorithmException{
    	final int i4 = 4;
    	final int i0xf = 0xf;
    	char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9','a', 'b', 'c', 'd', 'e', 'f' };

    	byte[] strTemp = src.getBytes("UTF-8");
    	MessageDigest mdTemp = MessageDigest.getInstance("MD5");
    	mdTemp.update(strTemp);
    	byte[] md = mdTemp.digest();
    	/* 转换为16进制 */
    	int j = md.length;
    	char[] str = new char[j * 2];
    	int k = 0;
    	for (int i = 0; i < j; i++) {
    		byte byte0 = md[i];
    		str[k++] = hexDigits[byte0 >>> i4 & i0xf];
    		str[k++] = hexDigits[byte0 & i0xf];
    	}
    	return new String(str);
    }
    public static void main(String[] args) throws UnsupportedEncodingException, NoSuchAlgorithmException {
		String str = sign("tranCode=[4020]merchantID=[0000003358]merOrderNum=[1000001]tranAmt=[]ticketAmt=[]tranDateTime=[20131106145701]currencyType=[]merURL=[11111111]customerEMail=[]authID=[]orgOrderNum=[1000001]orgtranDateTime=[20131106135701]orgtranAmt=[]orgTxnType=[]orgTxnStat=[]msgExt=[]virCardNo=[]virCardNoIn=[]tranIP=[211.88.12.132]isLocked=[]feeAmt=[]respCode=[]VerficationCode=[12345678]");
		System.out.println(str);
    }
    /**
     * @param content
     * @param charset
     * @return
     * @throws SignatureException
     * @throws UnsupportedEncodingException 
     */
    private static byte[] getContentBytes(String content, String charset) {
        if (charset == null || "".equals(charset)) {
            return content.getBytes();
        }

        try {
            return content.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
        }
    }
}
