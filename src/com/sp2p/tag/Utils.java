/**
 * Project Name:sp2p_web
 * File Name:Uitls.java
 * Package Name:com.sp2p.tag
 * Date:2015-7-21下午4:37:38
 * Copyright (c) 2015, chenzhou1025@126.com All Rights Reserved.
 *
*/

package com.sp2p.tag;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import com.shove.util.StringUtils;

/**
 * ClassName: Utils <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2015-7-21 下午4:52:51 <br/>
 *
 * @author Administrator
 * @version 
 * @since JDK 1.6
 */
public class Utils extends TagSupport   {
	private static final long serialVersionUID = 1L;
	private int start,end;
	private String text,defaultValue;
	
	
	@Override
	public int doStartTag() throws JspException {
		try {
			if (org.apache.commons.lang.StringUtils.isBlank(text)) {
				return 1;
			}
			text = StringUtils.formatStr(text, start, end);
			this.pageContext.getOut().print(text);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public int getStart() {
		return start;
	}



	public void setStart(int start) {
		this.start = start;
	}



	public int getEnd() {
		return end;
	}



	public void setEnd(int end) {
		this.end = end;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	
	
	
}

