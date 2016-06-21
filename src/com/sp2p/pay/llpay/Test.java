package com.sp2p.pay.llpay;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sp2p.pay.llpay.client.config.PartnerConfig;
import com.sp2p.pay.llpay.client.config.ServerURLConfig;
import com.sp2p.pay.llpay.client.conn.HttpRequestSimple;
import com.sp2p.pay.llpay.client.utils.LLPayUtil;
import com.sp2p.pay.llpay.client.vo.PayOrder;
import com.sp2p.pay.llpay.client.vo.RetBean;
import com.sp2p.pay.llpay.client.vo.UserProtocol;

public class Test {

	/**
	 * @param 连连支付API查询测试
	 */
	public static void main(String[] args) {
		
		HttpRequestSimple request =  HttpRequestSimple.getInstance();
		JSONObject reqObj = new JSONObject();
		//卡bin查询
		/*reqObj.put("oid_partner", PartnerConfig.OID_PARTNER);
        reqObj.put("card_no", "6214836552379527");
        reqObj.put("sign_type", PartnerConfig.SIGN_TYPE);
        String sign = LLPayUtil.addSign(reqObj, PartnerConfig.TRADER_PRI_KEY,
                PartnerConfig.MD5_KEY);
        reqObj.put("sign", sign);
        String reqJSON = reqObj.toString();*/
		
		//签约查询
		reqObj.put("oid_partner", PartnerConfig.OID_PARTNER);
        reqObj.put("user_id", "");
        reqObj.put("offset", "0");
        reqObj.put("sign_type", PartnerConfig.SIGN_TYPE);
        String sign = LLPayUtil.addSign(reqObj, PartnerConfig.TRADER_PRI_KEY,
                PartnerConfig.MD5_KEY);
        reqObj.put("sign", sign);
        String reqJSON = reqObj.toString();
		
		//解约
		/*reqObj.put("oid_partner", PartnerConfig.OID_PARTNER);
        reqObj.put("user_id", "833S");
        reqObj.put("no_agree", "2015020234333978");
        reqObj.put("sign_type", PartnerConfig.SIGN_TYPE);
        String sign = LLPayUtil.addSign(reqObj, PartnerConfig.TRADER_PRI_KEY,
                PartnerConfig.MD5_KEY);
        reqObj.put("sign", sign);
        String reqJSON = reqObj.toString();*/
        
		String resultJson =  request.postSendHttp(ServerURLConfig.QUERY_BANKCARD_OUT_PROTOCOL, reqJSON);
		RetBean retBean = JSON.parseObject(resultJson, RetBean.class);
		System.out.println(resultJson);
	}

}
