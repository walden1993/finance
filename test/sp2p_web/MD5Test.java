package sp2p_web;

import com.sp2p.pay.llpay.client.utils.Md5Algorithm;

public class MD5Test {
	public static void main(String[] args) {
		Md5Algorithm md5Algorithm = Md5Algorithm.getInstance();
		String sign1 = md5Algorithm.sign("1.0&123456&Android&13800138000&abcdefg", "wDwdKd27d0Qj1wdEa536yiuPE96Ofds3L");
		System.out.println(sign1);
		String sign2 = md5Algorithm.sign("123456&Android&13800138000&abcdefg&1.0", "wDwdKd27d0Qj1wdEa536yiuPE96Ofds3L");
		System.out.println(sign2);
		
		System.out.println(70099L>600*1000L);
	}
}	
