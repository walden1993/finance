package com.sp2p.pay.llpay.client.config;

import java.sql.SQLException;

import com.sp2p.util.PublicFunction;

/**
* 商户配置信息
* @author hjh
* @date:2013-6-25 下午01:45:40
* @version :1.0
*
*/
public class PartnerConfig{
	
	private PartnerConfig(){};
    // 银通公钥
    public static String YT_PUB_KEY     = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCSS/DiwdCf/aZsxxcacDnooGph3d2JOj5GXWi+q3gznZauZjkNP8SKl3J2liP0O6rU/Y/29+IUe+GTMhMOFJuZm1htAtKiu5ekW0GlBMWxf4FPkYlQkPE0FtaoMP3gYfh+OwI+fIRrpW3ySn3mScnc6Z700nU/VYrRkfcSCbSnRwIDAQAB";
    // 商户私钥
    public static String TRADER_PRI_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAOilN4tR7HpNYvSBra/DzebemoAiGtGeaxa+qebx/O2YAdUFPI+xTKTX2ETyqSzGfbxXpmSax7tXOdoa3uyaFnhKRGRvLdq1kTSTu7q5s6gTryxVH2m62Py8Pw0sKcuuV0CxtxkrxUzGQN+QSxf+TyNAv5rYi/ayvsDgWdB3cRqbAgMBAAECgYEAj02d/jqTcO6UQspSY484GLsL7luTq4Vqr5L4cyKiSvQ0RLQ6DsUG0g+Gz0muPb9ymf5fp17UIyjioN+ma5WquncHGm6ElIuRv2jYbGOnl9q2cMyNsAZCiSWfR++op+6UZbzpoNDiYzeKbNUz6L1fJjzCt52w/RbkDncJd2mVDRkCQQD/Uz3QnrWfCeWmBbsAZVoM57n01k7hyLWmDMYoKh8vnzKjrWScDkaQ6qGTbPVL3x0EBoxgb/smnT6/A5XyB9bvAkEA6UKhP1KLi/ImaLFUgLvEvmbUrpzY2I1+jgdsoj9Bm4a8K+KROsnNAIvRsKNgJPWd64uuQntUFPKkcyfBV1MXFQJBAJGs3Mf6xYVIEE75VgiTyx0x2VdoLvmDmqBzCVxBLCnvmuToOU8QlhJ4zFdhA1OWqOdzFQSw34rYjMRPN24wKuECQEqpYhVzpWkA9BxUjli6QUo0feT6HUqLV7O8WqBAIQ7X/IkLdzLa/vwqxM6GLLMHzylixz9OXGZsGAkn83GxDdUCQA9+pQOitY0WranUHeZFKWAHZszSjtbe6wDAdiKdXCfig0/rOdxAODCbQrQs7PYy1ed8DuVQlHPwRGtokVGHATU=";
    // MD5 KEY
    public static String MD5_KEY        =  "201408071000001546_test_20140815";
    // 接收异步通知地址
    public static String NOTIFY_URL     = "http://ip:port/wepdemo/notify.htm";
    // 支付结束后返回地址
    public static String URL_RETURN     = "http://ip:port/wepdemo/urlReturn.jsp";
    // 商户编号
    public static String OID_PARTNER    = "201408071000001546";
    // 签名方式 RSA或MD5
    public static String SIGN_TYPE      = "MD5";
    // 接口版本号，固定1.0
    public static String VERSION        = "1.0";
    //订单名称
    public static String GOODSNAME 		= "三好资本充值订单";
    // 业务类型，连连支付根据商户业务为商户开设的业务类型； （101001：虚拟商品销售、109001：实物商品销售、108001：外部账户充值）
    public static String BUSI_PARTNER   = "101001";
    
    static{
    	try {
			MD5_KEY = PublicFunction.GetOption("llpay_gopay_md5key");
			OID_PARTNER = PublicFunction.GetOption("llpay_ipspay_usernumber");
			NOTIFY_URL = PublicFunction.GetOption("llpay_notify_url");
			GOODSNAME = PublicFunction.GetOption("llpay_goodsname");
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
}