package com.sp2p.system.interceptor;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.shove.web.util.JSONUtils;
import com.sp2p.entity.Response;
import com.sp2p.pay.llpay.client.utils.Md5Algorithm;
import com.sp2p.service.UserService;

@SuppressWarnings("serial")
public class AppSignInterceptor implements Interceptor {
	// private String deviceType = "-1";
	private final static String APP_KEY = "wDwdKd27d0Qj1wdEa536yiuPE96Ofds3L";
	private final static Log logger = LogFactory.getLog(AppSignInterceptor.class);

	public void destroy() {

	}

	public void init() {

	}

	protected String request(String key) {
		return request().getParameter(key);
	}

	protected Map<String, String> getAppAuthMap() {
		return getRequestMap("auth");

	}

	protected Map<String, String> getAppInfoMap() {
		return getRequestMap("info");
	}

	protected HttpServletRequest request() {
		return ServletActionContext.getRequest();
	}

	protected HttpSession session() {
		return ServletActionContext.getRequest().getSession();
	}

	@SuppressWarnings("unchecked")
	private Map<String, String> getRequestMap(String requestAttr) {
		HttpServletRequest request = request();
		// Map<String, String> jsonMap = new HashMap<String, String>();
		System.out.println("request url==>" + request.getContextPath());
		Map<String, Object> paraMap = request.getParameterMap();
		System.out.println("session==>" + session());
		Set<String> keySet = paraMap.keySet();
		System.out.println("=============request value start===============");
		for (String key : keySet) {
			Object val = paraMap.get(key);
			if (val instanceof String[]) {
				System.out.println(key + "==>"
						+ Arrays.toString((String[]) val));
			} else {
				System.out.println(key + "==>" + val);
			}
		}
		System.out.println("=============request value end===============");
		String json = request(requestAttr);
		Map<String, String> map = (Map<String, String>) JSONObject.toBean(
				JSONObject.fromObject(json), HashMap.class);
		if (map == null) {
			map = new HashMap<String, String>();
		}
		return map;

	}

	protected long getUserId(String phone, UserService userService) {
		Map<String, String> userMap;
		long userId = -1;
		try {
			userMap = userService.queryUserByPhone(phone);
			if (userMap != null) {
				userId = MapUtils.getLongValue(userMap, "id", -1L);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userId;
	}

	public String intercept(ActionInvocation invocation) throws Exception {
		Response response = new Response();
		// 验证签名
		// Map<String, String> jsonMap = new HashMap<String, String>();
		try {
			Map<String, String> authMap = getJson();
			String sign = authMap.get("sign");
			logger.debug("------sign = " + sign);
			String server_sign = getserversign(authMap);
			logger.debug("----------server_sign-----------" + server_sign);

			if (!Md5Algorithm.getInstance().doCheck(server_sign, sign.toLowerCase())) {
				response.failure("签名不正确");
				JSONUtils.printObject(response);
				return null;
			} else {
				return invocation.invoke();
			}
		} catch (Exception e) {
			response.failure("服务器异常");
			JSONUtils.printObject(response);
			logger.error(e.getMessage());
			return null;
		}

		/*
		 * HttpServletRequest request = ServletActionContext.getRequest();
		 * License.update(request, IConstants.LICENSE); //配置拦截器 注册码拦截 if
		 * (!License.getAndoridAllow(request)&&!License.getiOSAllow(request)) {
		 * jsonMap.put("error", "1"); jsonMap.put("msg", "");
		 * JSONUtils.printObject(jsonMap); return null; }
		 */
		/**
		 * 校验令牌是否正确
		 * 
		 * @param token
		 * @param userId
		 * @return
		 */

		

		// Map<String, String> appInfoMap = getAppInfoMap();
		// String userId = appInfoMap.get("uid");
		// token = appInfoMap.get("token");
		// String des = token.substring(0, token.length()-8);
		// String md5 = com.shove.security.Encrypt.MD5(des
		// + IConstants.PASS_KEY);
		// if (token.substring(token.length()-8).equals(md5.substring(0, 8))) {
		// String temp = com.shove.security.Encrypt.decrypt3DES(des,
		// IConstants.PASS_KEY);
		// String[] array = temp.split(",");
		// System.out.println("XXXXXXXXXXXXXXXXXXXXXX");
		// if (array.length>1 && array[1].equals(userId)) {
		// return invocation.invoke();
		// }
		// } else {
		// jsonMap.put("error", "1");
		// jsonMap.put("msg", "非法操作");
		// JSONUtils.printObject(jsonMap);
		// return null;
		// }
		//
		// return null;
	}

	private String getserversign(Map<String, String> authMap) {
		StringBuffer buffer = new StringBuffer();
		String ignoreKey = "sign";
		if (authMap != null) {
			SortedMap<String, String> sortMap = new  TreeMap<String, String>(authMap);//默认升序排序
			Iterator<String> its = sortMap.keySet().iterator();
			while (its.hasNext()) {
				String key = its.next();
				if (!ignoreKey.contains(key)) {
					buffer.append(authMap.get(key)).append("&");
				}
			}
			buffer.append(APP_KEY);
		}
		logger.debug("==================content============="+buffer.toString());
		logger.debug("==================server_sign============="+Md5Algorithm.getInstance().md5Digest(buffer.toString().getBytes()));
		return buffer.toString();
	}

	private Map<String, String> getJson() {
		InputStream inputStream = null;
		try {
			inputStream = request().getInputStream();
			byte[] buffer = new byte[512];
			int len = -1;
			StringBuffer stringBuffer = new StringBuffer();
			while ((len = inputStream.read(buffer)) != -1) {
				String s = new String(buffer, 0, len, "utf-8");
				stringBuffer.append(s);
				s = null;
			}
			JSONObject obj = JSONObject.fromObject(stringBuffer.toString());
			logger.info("json===========" + obj);
			@SuppressWarnings("unchecked")
			Map<String, String> map = (Map<String, String>) JSONObject.toBean(
					obj, HashMap.class);
			request().setAttribute("json", map);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if (inputStream!=null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
}

// boolean flag = true;
// String domain = map.get("domain");
// deviceType = map.get("deviceType");
// try {
// String sesPWD = Encrypt.decryptSES(IConstants.sesPWd, IConstants.sesKey);
//
// flag = sesPWD.contains(domain);
// sesPWD = sesPWD.replaceAll("'", "");
// String [] array = sesPWD.split(",");
// if(!deviceType.equals(array[0])&&!array[0].equals("0")){
// flag = false;
// }
// } catch (Exception e) {
// e.printStackTrace();
// }
// if(!flag){
// jsonMap.put("error", "1");
// jsonMap.put("msg", "非法操作");
// JSONUtils.printObject(jsonMap);
// return null;
// }else{
// return invocation.invoke();
// }

