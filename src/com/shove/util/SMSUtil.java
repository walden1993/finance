package com.shove.util;

import java.io.IOException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;
import javax.xml.rpc.ServiceException;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import com.allinpay.ets.client.StringUtil;
import com.shove.Convert;
import com.shove.data.DataSet;
import com.shove.data.dao.MySQL;
import com.sp2p.database.Dao;
import com.sp2p.service.SysparService;
import com.sp2p.service.admin.SendSMSService;
import com.sp2p.sms.util.Client;
import com.sp2p.sms.util.SingletonClient;
import com.sp2p.util.DateUtil;
import com.sp2p.util.SpringUtil;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
/**
 * 短信接口，对短信接口地址进行拼接，提供公用
 * 
 * @author Administrator
 * 
 */
public class SMSUtil{

	public final static String  username = "AjkDbl08Ujc=";
	public final static String password = "AkQDP11/UkZXZlBlUz9XYQ==";
	public final static String endpoint = "http://118.195.131.79:8090/services/Sms";

	public static Log log = LogFactory.getLog(SMSUtil.class);
	
	/**   
	 * @throws ServiceException 
	 * @throws SQLException 
	 * @MethodName: sendSMS  
	 * @Param: SMSUtil  
	 * @Author: gang.lv
	 * @Date: 2013-5-30 下午04:04:13
	 * @Return:    
	 * @Descb: 发送短信
	 * @Throws:
	*/
	public static String sendSMS(String userName, String password, String content,
			String phone,String randomCode,String pathtype) throws ServiceException, SQLException {
			String status = "";
//			log.info("~~~~~~~~~~~~~~~~~："+randomCode);
			try {
				try {
					status = sendHrSms(content, phone, randomCode, pathtype);
				} catch (JSONException e) {
					e.printStackTrace();
					return "Fail";
				}
			} catch (MalformedURLException e) {
				
				e.printStackTrace();
				return "Fail";
			} catch (RemoteException e) {
				
				e.printStackTrace();
				return "Fail";
			}
			
			return getReturn(status);
		
	}
	
	public static String getReturn(String status){
	    switch (Integer.parseInt(status)) {
        case 0:
            status="Sucess";
            break;
        case 3:
            break;
        default:
            status="Fail";
            break;
        }
	    return status;
	}
	
	public static String sendSMS(String userName, String password, String content,
			String phone,String randomCode) throws ServiceException, SQLException {
			String status = "";
//			log.info("~~~~~~~~~~~~~~~~~："+randomCode);
			try {
				try {
					status = sendHrSms(content, phone, randomCode,null);
				} catch (JSONException e) {
					e.printStackTrace();
					return "Fail";
				}
			} catch (MalformedURLException e) {
				
				e.printStackTrace();
				return "Fail";
			} catch (RemoteException e) {
				
				e.printStackTrace();
				return "Fail";
			}
			return getReturn(status);
		
	}
	
	public static String sendHrSms(String content,
			String phone, String randomCode,String pathtype) throws ServiceException, MalformedURLException, RemoteException, JSONException, SQLException{
		
		String smsuser="",smspwd="",smsendpoint="";
		int businessId=-1;//当前运营商
		Connection conn = MySQL.getConnection();
		try
		{
			String condition = "status=1 and isMarket=0 and id!=4";
			if (!StringUtil.isEmpty(pathtype)){
				condition = " isMarket=1 AND status=1 ";
			}
			Dao.Tables.t_sms tsms = new Dao().new Tables().new t_sms();
			DataSet ds = tsms.open(conn, " * ", condition, "id", -1, -1);
			ds.tables.get(0).rows.genRowsMap();
			
			smsuser = username;
			smspwd = password;
			smsendpoint = endpoint;
			if (ds.tables.get(0).rows.getCount() > 0)
			{
				smsuser = ds.tables.get(0).rows.get(0).get("Account").toString();
				smspwd = ds.tables.get(0).rows.get(0).get("Password").toString();
				smsendpoint = ds.tables.get(0).rows.get(0).get("url").toString();
				businessId = Convert.strToInt(ds.tables.get(0).rows.get(0).get("id").toString(), 0);
			}
		}
		catch (Exception e)
		{
		}
		finally
		{
			conn.close();
		}
		
		
		if(StringUtils.isNotBlank(randomCode)){
            content += "（"+randomCode+"）";
        }
		
		 SendSMSService sendSMSService = (SendSMSService) SpringUtil.getBean("sendSMSService");
        
        SysparService sysparService = (SysparService) SpringUtil.getBean("sysparService");
        
        try {
           
            String time = DateUtil.YYYY_MM_DD.format(new Date());
            //数量验证
            String beginTime =time+ " 00:00:00";
            String endTime = time+ " 23:59:59";
            Map<String, String> map_acount = sysparService.querySysparChild("selectValue", "SMSACOUNT", "", -1, -1);
            if (map_acount!=null) {
                int useacount = Convert.strToInt(map_acount.get("selectValue"), -1);//可发数量
                int smsacount = sendSMSService.querySendSMSPage(beginTime, endTime, phone);//已发数量
                if (smsacount>=useacount) {
                    log.info("~~~~~~~~~~~~~~~~~~~~~~~~~~发送已上限~~~~~~~~~~~~~~~~~~~~~~~");
                    return "3";
                }
            }
            //插入数据库
            sendSMSService.SendSMSs(content,"", phone);
            
            Map<String, String> map = sysparService.querySysparChild("deleted", "SMS", "", -1, -1);
            if (map!=null ) {
                int deleted = Convert.strToInt(map.get("deleted"), -1);
                if (deleted==2) {//标识不启用,直接返回成功，验证码自己在后台看
                    return "0";
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            log.error(e);
        }
        
		
		log.info("smsuser:"+smsuser + ";smspwd:" + smspwd + ";smsendpoint:" + smsendpoint);

		//log.info(PropertyResourceBundle.getBundle("config").getString("softwareSerialNo"));
		//加入亿美短信发送
		if (businessId==1) {
                Client client = SingletonClient.getClient(smsuser, smspwd);
                log.info("【华融金融】"+content);
                int code = client.sendSMS(new String[] { phone }, "【华融金融】"+content, "",5);
                log.info(code==0?"亿美短信发送成功！":"亿美短信发送失败!");
                return code+"";
        }
		
		//新加的短信方式 http://58.83.147.92:8080/qxt/smssenderv2
		if (businessId == 5 || businessId == 6){
			Map<String,String> message = new java.util.HashMap<String,String>();
			message.put("user", smsuser);
			message.put("password", smspwd);
			message.put("tele", phone);
			message.put("msg", "【三好资本】"+content);
			message.put("extno", "95279527");
			message.put("addseqno", "1");
			
			String ret  = transRequest(smsendpoint, message);
			log.info("sms_ret:"+ret);
			if (ret.startsWith("ok")){
				return "0";
			} else{
				return "1";
			}
		} 
		
		Service service = new Service();
		Call call = null;

		call = (Call) service.createCall();
		
		//采用AZDG方式加密
		//采用AZDG方式加密		
		
		//发送短信
		call.setOperationName("InsertDownSms");
//		call.setTargetEndpointAddress(new java.net.URL(endpoint));
		call.setTargetEndpointAddress(new java.net.URL(smsendpoint));
		String boay = "<sendbody><message><orgaddr></orgaddr><mobile>"+phone+"</mobile><content>" + content + "</content><sendtime></sendtime><needreport>0</needreport></message><publicContent></publicContent></sendbody>";
//		String ret1 = (String) call.invoke(new Object[] { username, password, "", boay });
		String ret1 = (String) call.invoke(new Object[] { smsuser, smspwd, "", boay });
		ret1.length();		
		JSONObject jsonObj = XML.toJSONObject(ret1);
		JSONObject response = (JSONObject) jsonObj.get("response");
		JSONObject head = (JSONObject) response.get("head");
		String code = (String) head.get("code");
		System.out.println(code);
		return code;
		
	}
	private static String byteToString(byte[] bByte) {
        StringBuffer sBuffer = new StringBuffer();
        for (int i = 0; i < bByte.length; i++) {
            sBuffer.append(byteToArrayString(bByte[i]));
        }
        return sBuffer.toString();
    }
	// 返回形式为数字跟字符串
    private static String byteToArrayString(byte bByte) {
    	String[] strDigits = { "0", "1", "2", "3", "4", "5",
             "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
        int iRet = bByte;
        // System.out.println("iRet="+iRet);
        if (iRet < 0) {
            iRet += 256;
        }
        int iD1 = iRet / 16;
        int iD2 = iRet % 16;
        return strDigits[iD1] + strDigits[iD2];
    }
    
	/**
	 *功能：
	 * @param url
	 * @param message
	 * @return
	 */
	public static String transRequest(String url, Map<String,String> message) {
		// 响应内容
		String result = "error";

		// 定义http客户端对象--httpClient
		HttpClient httpClient = new HttpClient();

		// 定义并实例化客户端链接对象-postMethod
		PostMethod postMethod = new PostMethod(url);
		String strObj = message.get("password");
		try {
			//密码加密
			String resultString = null;
			try {
				resultString = new String(strObj);
				MessageDigest md = MessageDigest.getInstance("MD5");
				// md.digest() 该函数返回值为存放哈希值结果的byte数组
				resultString = byteToString(md.digest(strObj.getBytes()));
			} catch (NoSuchAlgorithmException ex) {
				ex.printStackTrace();
			}
			
			// 设置http的头
			postMethod.setRequestHeader("ContentType", "application/x-www-form-urlencoded;charset=GBK");
			postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "gbk");
			// 填入各个表单域的值
			NameValuePair[] data = {
				new NameValuePair("user", message.get("user")), 
				new NameValuePair("password", resultString),
				new NameValuePair("tele", message.get("tele")),
				new NameValuePair("msg", message.get("msg")),
				new NameValuePair("extno", message.get("extno")),
				new NameValuePair("addseqno", message.get("addseqno"))
			};
//			log.info("----message:" + message);
//			log.info("====url:"+url);
//			log.info("====resultString:"+resultString);
			// 将表单的值放入postMethod中
			postMethod.setRequestBody(data);

			// 定义访问地址的链接状态
			int statusCode = 0;
			try {
				// 客户端请求url数据
				statusCode = httpClient.executeMethod(postMethod);
			} catch (Exception e) {
				e.printStackTrace();
			}

			// 请求成功状态-200
			if (statusCode == HttpStatus.SC_OK) {
				try {
					result = postMethod.getResponseBodyAsString();
				} catch (IOException e) {
					e.printStackTrace();
					result = "error";
				}
			} else {
				log.error("请求返回状态：" + statusCode);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			result = "error";
		} finally {
			// 释放链接
			postMethod.releaseConnection();
			httpClient.getHttpConnectionManager().closeIdleConnections(0);
		}
		return result;
	}
	
	
}
