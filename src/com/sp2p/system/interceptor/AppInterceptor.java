package com.sp2p.system.interceptor;

import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.shove.Convert;
import com.shove.security.Encrypt;
import com.shove.web.util.JSONUtils;
import com.sp2p.entity.Response;
import com.sp2p.pay.llpay.client.utils.Md5Algorithm;
import com.sp2p.service.UserService;
import com.sp2p.util.SpringUtil;

@SuppressWarnings("serial")
public class AppInterceptor implements Interceptor {
	// private String deviceType = "-1";
	private final static String APP_KEY = "wDwdKd27d0Qj1wdEa536yiuPE96Ofds3L";
	private final static Log logger = LogFactory.getLog(AppInterceptor.class);

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

			String token = authMap.get("token");
			String phone = authMap.get("mobile_tel");
			logger.debug("------token = " + token);


			if (StringUtils.isNotBlank(phone)) {
				// Token校验
				UserService userService = (UserService) SpringUtil
						.getBean("userService");
				long userId = getUserId(phone, userService);
				;
				Map<String, String> map = userService.checkToken(userId,
						token);
				if (map == null) {
					response.failure("请重新登录").toLogin();
					JSONUtils.printObject(response);
					return null;
				} else {
					String map_userid = map.get("userid");
					String map_token = map.get("token");
					long map_checktime = Convert.strToLong(
							map.get("checktime"), 0L);
					try {
						if (!map_userid.equals(String.valueOf(map_userid))
								|| !map_token.equals(token)
								|| System.currentTimeMillis()
										- map_checktime > 1000 * 60 * 60 * 24) {
							// 超过一天的。令牌失效
							response.failure("请重新登录").toLogin();
							JSONUtils.printObject(response);
							return null;
						}
					} catch (Exception e) {
						response.failure("请重新登录").toLogin();
						JSONUtils.printObject(response);
						return null;
					}
				}
			} else {
				response.failure("请重新登录").toLogin();
				JSONUtils.printObject(response);
				return null;
			}
		
		} catch (Exception e) {
			response.failure("服务器异常");
			JSONUtils.printObject(response);
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

		return invocation.invoke();

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

	private Map<String, String> getJson() {
		return (Map<String, String>) request().getAttribute("json");
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

