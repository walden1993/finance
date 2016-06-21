package com.shove.web.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.cer.sign.SignMessageDetachByP7;
import com.cer.sign.VerifyMessageDetachByP7;
import com.opensymphony.xwork2.util.finder.ClassFinder.Info;
import com.shove.Convert;
import com.shove.config.XinfupayConfig;
import com.shove.util.FormUtil;
import com.shove.util.httpClient.HttpResponse;
import com.shove.web.util.GopayUtils;
import com.shove.web.util.TdMerSign;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.User;
import com.sp2p.service.RechargeDetailService;
import com.sp2p.service.RechargeService;
import com.sp2p.service.UserService;
import com.sp2p.system.exception.FrontHelpMessageException;
import com.sp2p.util.WebUtil;

/**
 * 融E付(新付支付)在线充值
 * @author Administrator
 *
 */
public class XinfuPaymentAction extends BasePageAction {

	private static Log log = LogFactory.getLog(GoPaymentAction.class);

	private UserService userService;
	
	private RechargeDetailService rechargeDetailService;
	private RechargeService rechargeService;
	
	private String urlParam = "";// 接口拼接的参数

	
	public UserService getUserService() {
		return userService;
	}


	public String xinfupayPayment() throws Exception {
		
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		User user = (User) session(IConstants.SESSION_USER);
		if (user == null) {// 未登陆
			return IConstants.ADMIN_AJAX_LOGIN;
		}
		
		String bankCode = request.getString("xinfuCode");
		String money = request.getString("money");
		String typeCode = request.getString("typeCode");
		if (StringUtils.isBlank(money)) {// 判断是否为空
			return INPUT;
		}
		BigDecimal moneyDecimal = null;
		try {
			moneyDecimal = new BigDecimal(money);
			if ("2".equals(IConstants.ISDEMO)) {//正式环境
			    if (moneyDecimal.toString().contains(".")) {
	                getOut().print("<script>alert('充值金额不能含有小数，请重新输入！');window.location.href ='rechargeInit.do';</script>");
	                return null;
	            }
            }
		} catch (RuntimeException e) {
			return INPUT;
		}
		int temp = moneyDecimal.compareTo(new BigDecimal("0.01"));// 最小金额为0.01元
		if (temp < 0) {
			return INPUT;
		}
		
		long userId = this.getUserId();
		// 生成订单
		paramMap.put("rechargeMoney", moneyDecimal + "");
		paramMap.put("userId", userId + "");
		paramMap.put("result", "0");
		SimpleDateFormat sf = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss"); 
		String date = sf.format(new Date());
		paramMap.put("addTime", date);
		paramMap.put("bankCode", bankCode+"_"+typeCode);
		Map<String, String> result = rechargeService.addRecharge(paramMap, 8);
		long nunber = Convert.strToInt(result.get("result"), -1);
		if (nunber != -1) {
			
			log.info("Input Money:" + moneyDecimal.toString());

			String html = createxinfupayUrl("在线充值", nunber, userId, bankCode,
					date, moneyDecimal);// paymentId_orderId_userId:支付类型(在线支付/在线充值)_订单编号/_用户编号
			log.info(html);
			sendHtml(html);
			return null;
		} else {
			createHelpMessage("支付失败！" + result.get("description"), "返回首页",
					"index.do");
			return null;
		}
	}
	
	
	public String xinfupayQuery() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		User user = (User) session(IConstants.SESSION_USER);
		if (user == null) {// 未登陆
			return IConstants.ADMIN_AJAX_LOGIN;
		}

		String bankCode = request("bankCode");
		String money = request("money");
		if (StringUtils.isBlank(money)) {// 判断是否为空
			return INPUT;
		}
		BigDecimal moneyDecimal = null;
		try {
			moneyDecimal = new BigDecimal(money);
		} catch (RuntimeException e) {
			return INPUT;
		}
		int temp = moneyDecimal.compareTo(new BigDecimal("0.01"));// 最小金额为0.01元
		if (temp < 0) {
			return INPUT;
		}
		long userId = this.getUserId();
		SimpleDateFormat sf = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss"); 
		String date = sf.format(new Date());
		String html = createGopayQuery("在线充值", 1, userId, bankCode,
				date, moneyDecimal);// paymentId_orderId_userId:支付类型(在线支付/在线充值)_订单编号/_用户编号
		sendHtml(html);
		return null;

	}
	
	private String createxinfupayUrl(String body, long recharId, long userId,
			String bankCode, String tranDateTime, BigDecimal money)
			throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		//JSONObject obj = new JSONObject();
		 // 组装接口参数，并进行加密
		 SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		 String Orddate = df.format(new Date());//下单日期 [必填]交易时间：YYYYMMDD
		 String Prdordno =  rechargeDetailService.getIn_orderNo(recharId,userId); //订单编号 orderNo = nowTime + "_" + recharId +"_" + userId;
		 String Ordamt = money + "";
//		 Ordamt ="" + (int)(Convert.strToDouble(Ordamt, 0));
		 Ordamt ="" + (int)(Convert.strToDouble(Ordamt, 0) * 100);
		 
		 String inMsg = ""; //MD5加密后的字符串
		 
		 log.info("Ordamt:" + Ordamt);
			
		 Map<String, String> map = new HashMap<String, String>();
		 map.put("Merno", XinfupayConfig.MerNo);
		 map.put("Signtype", XinfupayConfig.Signtype);
		 map.put("Prdordno", Prdordno);
		 map.put("bizType", XinfupayConfig.bizType);
		 map.put("Prdordnam", XinfupayConfig.Prdordnam);
		 map.put("Ordamt", Ordamt);
		 map.put("Orddate", Orddate);
		 map.put("TranType", XinfupayConfig.TranType);
		 map.put("Paytype", XinfupayConfig.Paytype);
		 map.put("bankCode ",bankCode);
		 map.put("Return_url", XinfupayConfig.Return_url);
		 
//		 if ("HZB".equalsIgnoreCase(bankCode))
//		 {
//			 map.put("ThirdParty", "PLA");
//		 }
//		 else
//		 {
//			 map.put("ThirdParty", XinfupayConfig.ThirdParty);
//		 }
		 String payWay = rechargeDetailService.payWayQuery(bankCode);
		 log.info("============================payWayQuery:" + payWay);
		 payWay =  (payWay== "" ? "LLP" : payWay);
		 String company = request("company");
		 User user = (User) session().getAttribute(IConstants.SESSION_USER);
		 if (user==null) {
            return "noLogin";
        }
		 
		 if (StringUtils.isNotBlank(company) && "2".equals(company) && user!=null && 2==user.getUserType()) {
		     payWay = "CFCA";
        }
		 
		 map.put("ThirdParty", payWay);
		 
		 
		 map.put("Notify_url", XinfupayConfig.Notify_url);
		
		 //加密字符串  
		    String md5src = XinfupayConfig.MerNo +"&"+ XinfupayConfig.Signtype +"&"+ Prdordno +"&"+ 
		    	Ordamt  +"&"+ Orddate +"&"+ XinfupayConfig.TranType +"&"+ XinfupayConfig.Paytype +"&"+ XinfupayConfig.Notify_url+"&"+ XinfupayConfig.MD5key;
		    //MD5 md5 = new MD5();
		    //String md5srcUP = TdMerSign.MD5Sign(md5src);//MD5检验结果     
		    //inMsg = md5srcUP.toUpperCase();
		    
		    
		    inMsg = sign(md5src);
		    
		    log.info("----------inMsg"+inMsg);
		    map.put("inMsg",inMsg);
		    return FormUtil.buildHtmlForm(map, XinfupayConfig.xinfupay_gateway, "post");		
	}
	
	//加签
	public static  String sign(String signMess) throws UnsupportedEncodingException{
		 //需要签名的字符串
		//signMess = "00000000000021&M&201511261941_26339_1304&易付通商品订单&400&20151126&20102&01&https://www.sanhaodai.com/backgroundMerUrl.mht&wbsX15nPzEc6";
		 //签名私钥证书路径
        String signPath = WebUtil.getPackagePath("cert/sanhaodai.pfx", false, XinfuPaymentAction.class.getResource(""));
        //String signPath= "TestData/cert/sanhaodai.pfx";
        //签名私钥证书密码
        String signPass = "sanhaodai";
        
        return  SignMessageDetachByP7.signMessDetachByP7(signMess,signPath,signPass);
		// System.out.println("签名后的结果："+str);
	}
	
	//验签
	public static boolean verify(String signData,String sourData){
		 //签名原数据
		 //String sourData = "00000000000104&M&P20151124113732&TEST&12000&2050&20102&01&http://www.hrongpay.com/test.php&wbsX15nPzEc6";
		 
		//sourData = "Merno=00000000000078&Prdordno=201511251848_1703_108&settleDate=20151125&ordStatus=1&notifyTyp=0&payOrdNo=null&Ordamt=500&signType=M"+"&"+ XinfupayConfig.Notify_url+"&"+ XinfupayConfig.MD5key;
		//对方签名数据
		 //String signData = "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";
		
		//signData = "MIIFBQYJKoZIhvcNAQcCoIIE9jCCBPICAQExDzANBglghkgBZQMEAgEFADALBgkqhkiG9w0BBwGgggO MIIDujCCAqKgAwIBAgIFEAJgZBQwDQYJKoZIhvcNAQEFBQAwWDELMAkGA1UEBhMCQ04xMDAuBgNVBAoTJ0NoaW5hIEZpbmFuY2lhbCBDZXJ0aWZpY2F0aW9uIEF1dGhvcml0eTEXMBUGA1UEAxMOQ0ZDQSBURVNUIE9DQTEwHhcNMTUxMTIwMDgyMDExWhcNMjAxMTIwMDgyMDExWjB9MQswCQYDVQQGEwJDTjENMAsGA1UEChMET0NBMTERMA8GA1UECxMIQ0ZDQSBMUkExFTATBgNVBAsTDEluZGl2aWR1YWwtMTE1MDMGA1UEAwwsQ0ZDQSBMUkFA5Y2O6J6N5rWL6K V5LiT55SoQFp0ZXN0MjAxNTExMjAwQDEwgZ8wDQYJKoZIhvcNAQEBBQADgY0AMIGJAoGBAKd22x95tvhySJU4SHM91j74rpb24GiEAizbU kBCnPElOpRZgdV8xdjHEU eu gWLpTZipfS1oV0lm1XXH7at3XQUghkZRrQnw8T3LWta5bFofoVkerYQxnvolaGdW1oodpPX1oXFRPY7s81ifiHMtiG Uf14wmlXuw0p1afL9ZAgMBAAGjgekwgeYwHwYDVR0jBBgwFoAUz3CdYeudfC6498sCQPcJnf4zdIAwSAYDVR0gBEEwPzA9BghggRyG7yoBATAxMC8GCCsGAQUFBwIBFiNodHRwOi8vd3d3LmNmY2EuY29tLmNuL3VzL3VzLTE0Lmh0bTA4BgNVHR8EMTAvMC2gK6AphidodHRwOi8vdWNybC5jZmNhLmNvbS5jbi9SU0EvY3JsNDQxNS5jcmwwCwYDVR0PBAQDAgPoMB0GA1UdDgQWBBQZyusqIOhOyAxckjdrf5TjVU37ezATBgNVHSUEDDAKBggrBgEFBQcDAjANBgkqhkiG9w0BAQUFAAOCAQEACKGxMV8wRPJyc6D6f5Z /g73DTtCMbRgQ0EOvbDE7HaDWwqoHg/UDMaD1/rIUipYWFisy1xzrcU0BvboQMotuzA gsdqyr35PK1hU2FTJvpZupVVJI/4CgPwkjQxxbXYKGGi/WvM3rCh/k9j2noqaDclQBkec4aLhf73QOu1xG9iV4GqvWnJhf1GjlJB97zLuNWMApYfAYWs3XU3Rhr jO9ugU7E7Fqoriutg0gzMC3xRMhBd4H8wB1ZgAe39lh36HNmu5jet8s7d2AFlwghOl2Me7N7qoEwHyCVDDRdoSKV624wlBj0oa 3ZYUUnYHW728YqzM00FZXMENe7K U4TGCAQswggEHAgEBMGEwWDELMAkGA1UEBhMCQ04xMDAuBgNVBAoTJ0NoaW5hIEZpbmFuY2lhbCBDZXJ0aWZpY2F0aW9uIEF1dGhvcml0eTEXMBUGA1UEAxMOQ0ZDQSBURVNUIE9DQTECBRACYGQUMA0GCWCGSAFlAwQCAQUAMA0GCSqGSIb3DQEBAQUABIGAEKT7ywIoqcCO3176YT7wQitFCwuS6/PJA49 3nuYk5hVqhBJLdCFrVSRNBZFxUj CJPjF94zq53mtuQ5XcyP8nYrJb9H4ornY0/UiPd/cIZZD6ox8WZ7MjPqZjECKNOncTqHffVepF6wZ5cL4Vcj5NZlVsTi7BBBE5Cedk0Jc4o=&Paychannel=XFZF";
		
		//00000000000078&M&201511251901_1705_108&易付通商品订单&500&20151125&20102&01&http://test.sanhaodai.com:8080/test/backgroundMerUrl.do&9fKfnmt9zga1
		
		//00000000000078&M&201511251901_1705_108&易付通商品订单&500&20151125&20102&01&http://test.sanhaodai.com:8080/test/backgroundMerUrl.do&9fKfnmt9zga1
		
		
		//sourData = "00000000000021&M&201511261941_26339_1304&易付通商品订单&400&20151126&20102&01&https://www.sanhaodai.com/backgroundMerUrl.mht&wbsX15nPzEc6";
		 //signData ="MIIFxQYJKoZIhvcNAQcCoIIFtjCCBbICAQExDzANBglghkgBZQMEAgEFADALBgkqhkiG9w0BBwGgggQ0MIIEMDCCAxigAwIBAgIFEDYmCRQwDQYJKoZIhvcNAQEFBQAwITELMAkGA1UEBhMCQ04xEjAQBgNVBAoTCUNGQ0EgT0NBMTAeFw0xNTExMjYwNzQyMThaFw0yMDExMjYwNzQyMThaMIGmMQswCQYDVQQGEwJDTjESMBAGA1UEChMJQ0ZDQSBPQ0ExMREwDwYDVQQLEwhocm9uZ3BheTEZMBcGA1UECxMQT3JnYW5pemF0aW9uYWwtMTFVMFMGA1UEAwxMaHJvbmdwYXlA5rex5Zyz5biC5piT5LuY6YCa6YeR6J6N5L+h5oGv5oqA5pyv5pyJ6ZmQ5YWs5Y+4QDg0NDAzMDExMDQ1MDc0NzlAMTCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBAI+bo/vvkrnNtG6o+YPFMEuIFdUhPK7w0WCCNFfO330HQ1MNOCCNyKB0o8cKs857DV/yVPcinmy/dXUlHN3Tdejvq5VifnGjL4OKsKtxSIJSVRrVUvXPLJl8mIRBTZAL+E8Kd3iEVtDQShpMWLEmMMKi6Moxnx6u9HFOZ9gSuMwRCI3Tr0lr3WhCjqYJxQauLnDYwLFTnj+Dsyco8DOvLXU1h7rppBzAUcs3oWNjS+6wqH7r914zV0YDO6vqE7vUdJwXfhuj6uqQYqoPHJiM17KxmwQnJlxEGNdb0x98l/xFyzMXo3E4BOeIsArZhnFNFFMpGFYb4gccUZG/zK836mECAwEAAaOB6DCB5TAfBgNVHSMEGDAWgBTR2+mIguXdGo9MqgCMvnzyqxv22TBIBgNVHSAEQTA/MD0GCGCBHIbvKgEBMDEwLwYIKwYBBQUHAgEWI2h0dHA6Ly93d3cuY2ZjYS5jb20uY24vdXMvdXMtMTQuaHRtMDcGA1UdHwQwMC4wLKAqoCiGJmh0dHA6Ly9jcmwuY2ZjYS5jb20uY24vUlNBL2NybDI0MDIuY3JsMAsGA1UdDwQEAwID6DAdBgNVHQ4EFgQUjsuiRciReSvXZEs0jR9CPusQ+60wEwYDVR0lBAwwCgYIKwYBBQUHAwIwDQYJKoZIhvcNAQEFBQADggEBAD6IRAXkN/jf3wFRp+TIgxTVl5OL62SGqb4TaGxjuiR+WJkUk2Zkis72HH39LWSjeocz7tvz2tsbVdMyOBUT2k2dEYwILQV0yeVfVE31/N3SmiW7RLXHB2+jgZA1nLo5lDIbJFHSfGnmdF2XqPbliffTFLYJ9LQsVH1Fs1Jo++OuDvLmj9ZkjPRXqGGObWizx8lxZoAFT/XSKjHfSLyDhbibTmLZP+SHzSDmD7M5Lsnb/cBj7MDwzbCDqV08B1oaqKePX5Gy5QAiQW+75hrhIg4YkWrq9HFMVjrzOMIeIC9qYLOfT3RNip97PJQTk9D3Ebs0mb3ObhDnO0N36QuWjJYxggFVMIIBUQIBATAqMCExCzAJBgNVBAYTAkNOMRIwEAYDVQQKEwlDRkNBIE9DQTECBRA2JgkUMA0GCWCGSAFlAwQCAQUAMA0GCSqGSIb3DQEBAQUABIIBADQrDa3Z/7FxpMZRyok1MRODBn3Zv+Smv0FoffrJZit1AFOLqmMkJ5W0tvdTOIXcm1kJvVTuRJXXjulFGLKuIhAejdOLnDvfT5ozTzSTs57BKH31c3H+vpCjJOi9Wua/0VwNMUUnPoRo00R4AlWNPb/gFoGRBTOdnpmRRmK7j5B86xuDodiIzP0IOAfCPNFPi/EXEP+CTikHkWkfJEJbenJOJ11dzdT15akhups+uVpSCB6iEolAMJ/hU+2BrdNiFJ4Ot75NirfXF1jwZUJh6GPS6tM7CflKJqQgyPQ1S2Q0bdfu94d3UueJT+Cd/OXSV0DWb0mTVLzJ7XR/RV7iNVs=";
		//公钥文件路径
		 String trustRSACertFilePath = WebUtil.getPackagePath("cert/cfcaoca1.cer", false, XinfuPaymentAction.class.getResource(""));
		 //证书序列号
		 String serialStr = "1036217312";
		 String result  = VerifyMessageDetachByP7.verifyMessDetachByP7(sourData,signData,serialStr,trustRSACertFilePath);
		 
		 log.debug(result);
		 
		 return Boolean.valueOf(result);
		 
		 //String result = VerifyMessageDetachByP7.verifyMessDetachByP7(sourData,signData,serialStr,trustRSACertFilePath);
		 //System.out.println("验签结果:->"+result);
	}

	
	private String createGopayQuery(String body, long recharId, long userId,
			String bankCode, String tranDateTime, BigDecimal money)
			throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		 String Orddate = df.format(new Date());//下单日期 [必填]交易时间：YYYYMMDD
		 String Prdordno =  rechargeDetailService.getIn_orderNo(recharId,userId); //订单编号 
		 String Ordamt = money + "";
		 Ordamt ="" + (Convert.strToInt(Ordamt, 0) * 100);
		 String inMsg = ""; //MD5加密后的字符串
			
		 Map<String, String> map = new HashMap<String, String>();
		 map.put("Merno", XinfupayConfig.MerNo);
		 map.put("Signtype", XinfupayConfig.Signtype);
		 map.put("Prdordno", Prdordno);
		 map.put("bizType", XinfupayConfig.bizType);
		 map.put("Prdordnam", XinfupayConfig.Prdordnam);
		 map.put("Ordamt", Ordamt);
		 map.put("Orddate", Orddate);
		 map.put("TranType", XinfupayConfig.TranType);
		 map.put("Paytype", XinfupayConfig.Paytype);
		 map.put("bankCode ", bankCode);
		 map.put("Return_url", XinfupayConfig.Return_url);
		 map.put("Notify_ur", XinfupayConfig.Notify_url);
		
		 //加密字符串  
		    String md5src = XinfupayConfig.MerNo +"&"+ XinfupayConfig.Signtype +"&"+ Prdordno +"&"+ 
		    	Ordamt  +"&"+ Orddate +"&"+ XinfupayConfig.TranType +"&"+ XinfupayConfig.Paytype +"&"+ XinfupayConfig.Notify_url+"&"+ XinfupayConfig.MD5key;
		    //MD5 md5 = new MD5();
		    String md5srcUP = TdMerSign.MD5Sign(md5src);//MD5检验结果     
		    inMsg = md5srcUP.toUpperCase();
		    map.put("inMsg",inMsg);
		    
		    log.info("buildHtmlForm-start");
		    String returnStr = FormUtil.buildHtmlForm(map, XinfupayConfig.xinfupay_gateway, "post");
		    log.info("buildHtmlForm-over:" + returnStr);
		    
		    return returnStr;
	}
	
	
	/**
	 * 跳转拦截
	 * 
	 * @param title
	 * @param msg
	 * @param url
	 * @throws FrontHelpMessageException
	 */
	public void createHelpMessage(String title, String msg, String url)
			throws FrontHelpMessageException {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		/* helpMessage.setTitle("用户不存在"); */
		helpMessage.setMsg(new String[] { "返回首页" });
		helpMessage.setUrl(new String[] { "index" });
		helpMessage.setTitle(title);
		/*
		 * helpMessage.setMsg(new String[]{msg}); helpMessage.setUrl(new
		 * String[]{url});
		 */
		throw new FrontHelpMessageException();
	}

	/**
	 * 前台调用函数
	 * 
	 * @return
	 * @throws Exception
	 */
	public void frontMerUrl() throws Exception {

		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		log.info("1--frontMerUrl");
	
		String ordStatus = request("ordStatus");// 
		log.info("2--" + ordStatus);
		if (!"1".equals(ordStatus)) {
			log.info("3--" + ordStatus);
			createHelpMessage("充值失败！", "返回首页", "index.do");
		}
		if ("1".equals(ordStatus)) {
			log.info("4--return success page");
			createHelpMessage("充值成功！", "返回首页", "index.do");
		}
		String Merno = request("Merno");// 商户号
		log.info("4--" + Merno);
		if (!validateSign()) {
			log.info("5--validate sign fail");
			createHelpMessage("充值失败！", "返回首页", "index.do");
		}
		
//		return "message";
	}
	
	
	
	
	
	public String frontRechargeConfirm() throws Exception {
//		paramMap = agreementService.getAgreementDetail(1, 2);
//		paramMap = agreementService.getMessageByTypeId(32);
		return SUCCESS;
	}
	
	
	
	
	

//	public void backgroundMerUrl() throws Exception {
//		log.info("2--backgroundMerUrl");		
//		HttpServletResponse response = ServletActionContext.getResponse();
//		response.setCharacterEncoding("UTF-8");
////		response.setContentType("text/xml;charset=utf-8");
////		response.setHeader("Cache-Control", "no-cache");
////		PrintWriter out = response.getWriter();
////		out.write(xml);
//		ServletOutputStream out = response.getOutputStream();
//		out.write(xml.getBytes());
//		out.flush();
//		out.close();
//		
////		return "message";
//	}

	/**
	 * 融E付(新付支付)回调函数
	 * 
	 * @return
	 * @throws Exception
	 */
	public void backgroundMerUrl() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		log.info("xf:2-----backgroundMerUrl");

	    String Merno = request("Merno");
	    String Prdordno = request("Prdordno");
	    String settleDate = request("settleDate");
	    String ordStatus = request("ordStatus");
	    String notifyTyp = request("notifyTyp");
	    String payOrdNo = request("payOrdNo");
	    String Ordamt = request("Ordamt");
	    String signType = request("signType");
	    String signature = request("signature");
	    String Paychannel = request("Paychannel");
	    
	    String responseStatus = "1";
		
		log.info("1-----backgroundMerUrl.do?Merno=" + Merno
				+ "&Prdordno=" + Prdordno
				+ "&settleDate=" + settleDate
				+ "&ordStatus=" + ordStatus
				+ "&notifyTyp=" + notifyTyp
				+ "&payOrdNo=" + payOrdNo
				+ "&Ordamt=" + Ordamt
				+ "&signType=" + signType
				+ "&signature=" + signature
				+ "&Paychannel=" + Paychannel);
		
		Integer errCounter = 0;
		
		log.info("2--" + ordStatus);
		if (!"1".equals(ordStatus)) {
			log.info("3--" + ordStatus);
//			createHelpMessage("扣款失败！", "返回首页", "index.do");
			errCounter += 1;
		}
		
//		if ("1".equals(ordStatus)) {
//			log.info("4--return success page");
//			createHelpMessage("扣款成功！", "返回首页", "index.do");
//		}
		
		log.info("4--" + Merno);
		if (!validateSign()) {
			log.info("5--validate sign fail");
//			createHelpMessage("扣款失败！", "返回首页", "index.do");
			errCounter += 1;
		}else {
			if (errCounter==0) {//没有问题，加签也没问题、2015-10-09 09:44:12hjh修正异步通知不成功的情况，加钱却成功了

				String paybank = "";// 
				if (StringUtils.isBlank(paybank)) {// 无法查询那家银行支付
					//paybank = "新付支付充值";
					paybank = "融E付";
				}
				
				double ordamtDouble = Convert.strToDouble(Ordamt, 0) / 100;//金额
				
				
				String arrays[] = Prdordno.split("_");
				long userId = Convert.strToLong(arrays[2], 0);
				log.info(userId);
				Map<String, String> resultMap = rechargeService.addUseraddmoney(userId,
						ordamtDouble, arrays[1], paybank);
				String result = resultMap.get("result");
				String description = resultMap.get("description");

				userService.updateSign(userId);//更换校验码
				
				if (!"0".endsWith(result)) {
					log.info("6--result:" + result + ";description:" + description);
//					createHelpMessage("扣款失败！", "返回首页", "index.do");
					errCounter += 1;
				}
			}
		}
		
//		msg = "充值成功";
//		log.info("success");
		log.info("7--");

//		createHelpMessage(msg + "", "返回首页", "index.do");
//		helpMessage.setMsg(new String[] { "返回首页" });
//		helpMessage.setUrl(new String[] { "index" });
//		helpMessage.setTitle(msg);

		if (errCounter > 0)
		{
			responseStatus = "2";
			log.info("充值失败");
		}
		else
		{
			log.info("充值成功");
		}
		
//		String responseStr = "<root><merchantRes "
		callResponse(Merno, Prdordno, signType, responseStatus);
		
//		return responseStr;
	}

	/**
	 * 回调融E付
	 * @param Merno
	 * @param Prdordno
	 * @param signType
	 * @param responseStatus
	 * @throws IOException
	 */
	private void callResponse(String Merno, String Prdordno, String signType,
			String responseStatus) throws IOException {
		String responseStr = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?><root><merchantRes "
			+ "Merno=\"" + Merno
			+ "\" Prdordno=\"" + Prdordno
			+ "\" status=\"" + responseStatus
			+ "\" cause=\"" + ""
			+ "\" signType=\"" + signType
			+ "\" signature=\"" + GopayUtils.md5(Merno + "&" + Prdordno + "&1&&" + signType + "&" + XinfupayConfig.MD5key)
			+ "\"/></root>";

		log.info("XFResponse:" + responseStr);
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		out.write(responseStr);
		out.flush();
		out.close();
	}
	
	/**
	 * 返回验证参数
	 * 
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public boolean validateSign() throws UnsupportedEncodingException {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		 //字符编码
	    String CharacterEncoding = "UTF-8";
	    request().setCharacterEncoding(CharacterEncoding);
	    String Merno = request().getParameter("Merno");
	    String Prdordno = request().getParameter("Prdordno");
	    String settleDate = request().getParameter("settleDate");
	    String ordStatus = request().getParameter("ordStatus");
	    String notifyTyp = request().getParameter("notifyTyp");
	    String payOrdNo = request().getParameter("payOrdNo");
	    String Ordamt = request().getParameter("Ordamt");
	    String signType = request().getParameter("signType");
	    String signature = request().getParameter("signature");
	    //String Paychannel = request().getParameter("Paychannel");
	    System.out.println(Merno+"---------------");
	    System.out.println(Prdordno+"------------");
	    System.out.println(settleDate+"----------");
	    System.out.println(ordStatus+"-----------");
	    System.out.println(notifyTyp+"-----------");
	    System.out.println(payOrdNo+"------------");
	    System.out.println(Ordamt+"-----------");
	    System.out.println(signType+"---------");
	    System.out.println(signature+"--------------");
	    String md5src = Merno +"&"+ Prdordno +"&"+ settleDate +"&"+ ordStatus +"&"+ notifyTyp +"&"+ 
	    	payOrdNo +"&"+ Ordamt +"&"+ signType +"&"+ XinfupayConfig.MD5key;
	    
	    System.out.println(md5src+"--------------------");
		String sign = GopayUtils.md5(md5src.toString());
		log.info("md5=" + sign);
		if (verify(signature,md5src)) {//先是使用证书验证
			return true;
		}else {
			return sign.equals(request("signature"));//md5校验
		}
		//System.out.println(sign.equals(request("signature")));	
		
		//return verify(signature,md5src);
	}
	
	public String merUrl() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		log.info("1-----backgroundMerUrl");

		String respCode = request("respCode");// 
		log.info("2--" + respCode);
		
		createHelpMessage(respCode + "", "返回首页", "index.do");
		return "message";
	}

	


	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public RechargeDetailService getRechargeDetailService() {
		return rechargeDetailService;
	}

	public void setRechargeDetailService(RechargeDetailService rechargeDetailService) {
		this.rechargeDetailService = rechargeDetailService;
	}

	public RechargeService getRechargeService() {
		return rechargeService;
	}

	public void setRechargeService(RechargeService rechargeService) {
		this.rechargeService = rechargeService;
	}

	public String getUrlParam() {
		return urlParam;
	}

	public void setUrlParam(String urlParam) {
		this.urlParam = urlParam;
	}
}
