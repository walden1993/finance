package com.sp2p.entity;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 响应对象
 * 
 * @author he
 * @since 1.0.0
 */
public class Response {
	Log log = LogFactory.getLog(Response.class);
	public final static String CODE_ERROR = "01";
	public final static String CODE_SUCCESS = "00";
	public final static String CODE_LOGIN = "02";//重新登陆

	private Object data;
	//private boolean result;

	/*public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}*/
	
	public void toLogin(){
		this.code = CODE_LOGIN;
	}

	public Response failure(String message) {
		this.code = CODE_ERROR;
		this.message = message;
		this.data = null;
		//this.result = false;
		return this;
	}

	public Response success(Object data) {
		this.code = CODE_SUCCESS;
		this.message = "success";
		this.data = data;
		//this.result = true;
		return this;
	}

	public Response success(Object data, String message) {
		this.code = CODE_SUCCESS;
		this.message = message;
		this.data = data;
		return this;
	}

	public Response success() {
		this.code = CODE_SUCCESS;
		this.message = "success";
		//this.result = true;
		return this;
	}


	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	private String code;
	private String message;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	@Override
	public String toString() {
		log.debug("-------------code--------"+code);
		log.debug("-------------message--------"+message);
		log.debug("-------------data--------"+data.toString());
		return super.toString();
	}

}
