package com.shove.config;


/**
 *
 * 融E付（新付支付）配置
 * @author Administrator
 *
 */
public class XinfupayConfig {

	public static String  MD5key  = ""; //私钥
	public static String  MerNo = "";   //商户ID为您在 融E付（新付支付）注册后所得
	public static String	Signtype = ""; //加密方式 目前只为M-MD5
	public static String	bizType = ""; //商户业务类型为您在 融E付（新付支付）注册时候设定
	public static String	Prdordnam = "三好资本商品订单"; //商品名称
	public static String MERCHANT_NAME ="三好资本";//商户名称
	public static String	TranType = ""; //交易类型  
	public static String Paytype = "";  //支付方式
	public static String ThirdParty = "";  //第三方支付平台
	public static String  xinfupay_gateway = "";//新付支付网关地址
	
	public static String	Return_url = ""; //同步通知url
	public static String	Notify_url  = ""; //[必填]支付完成后，后台接收支付结果，可用来更新数据库值
	
	public static String xinfupay_query_url="";
	public static String xinfupay_query_url_test="";
	
	public static String xinfupay_quick_sms="";//快捷短信
	public static String xinfupay_quick_pay="";//快捷支付
	 
	static{
		com.shove.io.file.PropertyFile pf = new com.shove.io.file.PropertyFile();
		MD5key = pf.read("xinfu_MD5key");
		MerNo = pf.read("xinfu_MerNo");
		Signtype = pf.read("xinfu_Signtype");
		bizType = pf.read("xinfu_bizType");
		TranType = pf.read("xinfu_TranType");
		ThirdParty = pf.read("xinfu_ThirdParty");
		Paytype = pf.read("xinfu_Paytype");
		xinfupay_gateway = pf.read("xinfupay_gateway");
		Return_url = pf.read("xinfu_Return_url");
		Notify_url = pf.read("xinfu_Notify_url");
		xinfupay_query_url = pf.read("xinfu_Query_url");
		xinfupay_query_url_test = pf.read("xinfu_Query_url_test");
		xinfupay_quick_sms = pf.read("xinfupay_quick_sms");
		xinfupay_quick_pay = pf.read("xinfupay_quick_pay");
	}
	   
}
