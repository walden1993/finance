/**
 * @author Glan.duanyj
 * @date 2014-05-12
 * @project rest_demo
 */
package com.sp2p.vioce;

import java.io.IOException;
import java.sql.Connection;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.shove.Convert;
import com.shove.data.DataSet;
import com.shove.data.dao.MySQL;
import com.sp2p.database.Dao;
import com.sp2p.service.SysparService;
import com.sp2p.service.admin.SendSMSService;
import com.sp2p.util.DateUtil;
import com.sp2p.util.SpringUtil;
import com.sp2p.vioce.client.AbsRestClient;
import com.sp2p.vioce.client.JsonReqClient;
import com.sp2p.vioce.client.XmlReqClient;

public class RestUtil {
	private String accountSid;
	private String authToken;
	
	public String getAccountSid() {
		return accountSid;
	}
	public void setAccountSid(String accountSid) {
		this.accountSid = accountSid;
	}
	public String getAuthToken() {
		return authToken;
	}
	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}
	static AbsRestClient InstantiationRestAPI(boolean enable) {
		if(enable) {
			return new JsonReqClient();
		} else {
			return new XmlReqClient();
		}
	}

	public static boolean voiceCode(String verifyCode,String to){
	  /*  String accountSid="1449940e086f7e15ceb1fe9040f7078d";
        String authToken="38ccb0163788bb311c6eeab29555bf12";
        String appId="a22af5ddf74b41018be9088b0a13e868";*/
	    String accountSid="";
        String authToken="";
        String appId="";
        
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
                int smsacount = sendSMSService.querySendSMSPage(beginTime, endTime, to);//已发数量
                if (smsacount>=useacount) {
                   // log.info("~~~~~~~~~~~~~~~~~~~~~~~~~~发送已上限~~~~~~~~~~~~~~~~~~~~~~~");
                    return true;
                }
            }
            //插入数据库
            sendSMSService.SendSMSs("语音发送验证码："+verifyCode,"", to);
            
            Map<String, String> map = sysparService.querySysparChild("deleted", "SMS", "", -1, -1);
            if (map!=null ) {
                int deleted = Convert.strToInt(map.get("deleted"), -1);
                if (deleted==2) {//标识不启用,直接返回成功，验证码自己在后台看
                    return true;
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            //log.error(e);
        }
        
		try {
		    Connection conn = MySQL.getConnection();
	        try
	        {
	            Dao.Tables.t_sms tsms = new Dao().new Tables().new t_sms();
	            DataSet ds = tsms.open(conn, " * ", "id = 4 and status=1 ", "id", -1, -1);
	            ds.tables.get(0).rows.genRowsMap();
	            
	            if (ds.tables.get(0).rows.rowsMap.size() > 0)
	            {
	                appId = ds.tables.get(0).rows.get(0).get("UserID").toString();
	                accountSid = ds.tables.get(0).rows.get(0).get("Account").toString();
	                authToken = ds.tables.get(0).rows.get(0).get("Password").toString();
	                String result=InstantiationRestAPI(true).voiceCode(accountSid, authToken, appId, to, verifyCode);
	                if (StringUtils.isNotBlank(result)) {
	                   return result.contains("000000");
	                }
	            }
	        }
	        catch (Exception e)
	        {
	        }
	        finally
	        {
	            conn.close();
	        }
		} catch (Exception e) {
		    
		}
		return false;
	}
	public static void TemplateSMS(boolean json,String accountSid,String authToken,String appId,String templateId,String to,String param){
        try {
            String result=InstantiationRestAPI(json).templateSMS(accountSid, authToken, appId, templateId, to, param);
            System.out.println("Response content is: " + result);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
	/**
	 * 测试说明 参数顺序，请参照各具体方法参数名称
	 * 参数名称含义，请参考rest api 文档
	 * @author Glan.duanyj
	 * @date 2014-06-30
	 * @param params[]
	 * @return void
	 * @throws IOException 
	 * @method main
	 */
	public static void main(String[] args) throws IOException {
	    String to="18598241216";
        String verifyCode = "9586";
        System.out.println(voiceCode( verifyCode,to));
        //String templateId="";
        //testTemplateSMS(true, accountSid,token,appId, templateId,to,verifyCode);
	}
}
