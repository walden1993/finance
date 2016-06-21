package com.sp2p.service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;

import com.shove.web.util.TdMerSign;
import common.Logger;

public class HSFundService {
	private static Logger logger = Logger.getLogger(HSFundService.class);
	public String hr_fund(Map<String, String> data,String token) throws Exception{
		String base_url = "http://172.16.3.157:8888/api/v1.0/";
		//String base_url = "http://172.16.10.207:8080/api/v1.0/";
		String access_key = "082e568f-fe11-49f0-925c-259e0b210015";
		String md5key="909*&EDFDD";
		String deskey="#87EWSE_";
		String url = data.get("method");//要请求的方法
		data.put("[access_key]", access_key);
		String ticket = "";//登录获取的令牌
		if (StringUtils.isNotBlank(token)) {
			ticket = token;//登录获取的令牌
		}
		data.put("[ticket]", ticket);
		String [] sign_data = sign(data, md5key, deskey);
		
		try {
			return invokeHttpRepeater(base_url+url, sign_data[1].getBytes("UTF-8"), "application/json",
					sign_data[0], ticket, access_key, "POST");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//加签加密
	public String[] sign(Map<String, String> authMap,String md5Key,String desKey) throws Exception{
		StringBuffer sign = new StringBuffer();
		Map<String, String> data = new HashMap<String, String>();
		if (authMap != null && !authMap.isEmpty()) {
			SortedMap<String, String> sortMap = new  TreeMap<String, String>(authMap);//默认升序排序
			Iterator<String> its = sortMap.keySet().iterator();
			while (its.hasNext()) {
				String key = its.next();
				//先加密
				String desvalue = sortMap.get(key);
				//if (StringUtils.isNotBlank(desvalue)) {
					if (key.contains("[*")) {
						byte [] bytes = TdMerSign.desCrypto(desvalue.getBytes(), desKey);
						desvalue = new String(bytes); 
					}
					//加签串
					if (key.contains("[") && key.contains("]")) {
						sign.append(desvalue).append("&");
					}
				//}
				key = key.replaceAll("[\\[*$\\]]", "");
				data.put(key, desvalue);
			}
			sign.deleteCharAt(sign.length()-1);
			sign.append(md5Key);
		}
		logger.debug("sign----------"+sign.toString());
		String signMD5 = TdMerSign.MD5Sign(sign.toString(),"UTF-8");
		logger.debug("signMD5----------"+signMD5);
		String [] strs = new String[]{signMD5,com.alibaba.fastjson.JSONObject.toJSON(data).toString()};
		return strs;
	}
	
	//远程调用
	public String invokeHttpRepeater(String url, byte[] data,
			String contentType, String sign, String ticket, String access_key,
			String method) {
		String result = "";
		if (url == null || "".equals(url)) {
			System.out.println("Please check the post url.");
			return result;
		}
		if (method == null) {
			method = "POST";
		}
		try {
			URL _url = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) _url
					.openConnection();
			connection.setRequestMethod(method);
			connection.setUseCaches(false);
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setRequestProperty("sign", sign);
			connection.setRequestProperty("ticket", ticket);
			connection.setRequestProperty("access_key", access_key);
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			if (StringUtils.isNotBlank(contentType)) {
				connection.setRequestProperty("Content-Type", contentType);
			}
			connection.setConnectTimeout(10000);
			connection.setReadTimeout(10000);
			connection.setAllowUserInteraction(true);
			connection.connect();

			if (data != null) {
				OutputStream out = connection.getOutputStream();
				if (out != null) {
					out.write(data);
					out.flush();
					out.close();
				}
			}

			InputStream in = connection.getInputStream();
			if (in != null) {
				ByteArrayOutputStream streams = new ByteArrayOutputStream();
				int r;
				byte[] buf = new byte[1024];
				while ((r = in.read(buf, 0, buf.length)) != -1) {
					streams.write(buf, 0, r);
				}
				streams.flush();
				streams.close();
				result = new String(streams.toByteArray(), "UTF-8");
			}
			in.close();
			System.out.println("Network callback: " + result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
