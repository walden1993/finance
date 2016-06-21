package com.shove.web.util;

import java.security.MessageDigest;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.lang.StringUtils;

/**
 * 商户MD5签名
 * 
 * @author liuyanwei
 * 
 */
public class TdMerSign {
    /**
     * 商户MD5签名
     * @param inStr  签名原数据
     * @return 经过MD5加签后的数据
     * @throws Exception
     */
	public static String MD5Sign(String inStr) throws Exception {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
		char[] charArray = inStr.toCharArray();
		byte[] byteArray = new byte[charArray.length];

		for (int i = 0; i < charArray.length; ++i) {
			byteArray[i] = (byte) charArray[i];
		}
		byte[] md5Bytes = md5.digest(byteArray);

		StringBuffer hexValue = new StringBuffer();

		for (int i = 0; i < md5Bytes.length; ++i) {
			int val = md5Bytes[i] & 0xFF;
			if (val < 16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));
		}
		String outStr = "";
		outStr = hexValue.toString();

		return outStr;
	}
	
	/**
     * 商户MD5签名
     * @param inStr  签名原数据
     * @return 经过MD5加签后的数据
     * @throws Exception
     */
	public static String MD5Sign(String inStr,String charset) throws Exception {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
		byte[] byteArray = null ;
		if (StringUtils.isNotBlank(charset)) {
			byteArray = inStr.getBytes("UTF-8");
		}else {
			char[] charArray = inStr.toCharArray();
			byteArray = new byte[charArray.length];
			for (int i = 0; i < charArray.length; ++i) {
				byteArray[i] = (byte) charArray[i];
			}
		}
		
		byte[] md5Bytes = md5.digest(byteArray);

		StringBuffer hexValue = new StringBuffer();

		for (int i = 0; i < md5Bytes.length; ++i) {
			int val = md5Bytes[i] & 0xFF;
			if (val < 16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));
		}
		String outStr = "";
		outStr = hexValue.toString();

		return outStr;
	}
	
	/**
	 * DES加密
	 * @param datasource
	 * @param password
	 * @return
	 */
    public static byte[] desCrypto(byte[] datasource, String password) {            
        try{
        SecureRandom random = new SecureRandom();
        DESKeySpec desKey = new DESKeySpec(password.getBytes());
        //创建一个密匙工厂，然后用它把DESKeySpec转换成
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey securekey = keyFactory.generateSecret(desKey);
        //Cipher对象实际完成加密操作
        Cipher cipher = Cipher.getInstance("DES");
        //用密匙初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
        //现在，获取数据并加密
        //正式执行加密操作
        return cipher.doFinal(datasource);
        }catch(Throwable e){
                e.printStackTrace();
        }
        return null;
}
}
