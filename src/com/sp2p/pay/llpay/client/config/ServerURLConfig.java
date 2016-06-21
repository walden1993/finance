package com.sp2p.pay.llpay.client.config;

/**
 * 支付配置信息
 * 
 * @author guoyx e-mail:guoyx@lianlian.com
 * @date:2013-6-25 下午01:45:06
 * @version :1.0
 * 
 */
public interface ServerURLConfig {
	/** 连连支付WEB收银台支付服务地址 */
	String PAY_URL = "https://yintong.com.cn/payment/bankgateway.htm";
	/** 用户已绑定银行卡列表查询 */
	String QUERY_USER_BANKCARD_URL = "https://yintong.com.cn/traderapi/userbankcard.htm";
	/** 银行卡卡bin信息查询 */
	String QUERY_BANKCARD_URL = "https://yintong.com.cn/traderapi/bankcardquery.htm";
	/** 用户签约信息查询 */
	String QUERY_BANKCARD_IN_PROTOCOL = "https://yintong.com.cn/traderapi%20/userbankcard.htm";
	/** 用户解约 */
	String QUERY_BANKCARD_OUT_PROTOCOL = "https://yintong.com.cn/traderapi/bankcardunbind.htm";
}
