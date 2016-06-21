package com.sp2p.system.filter;

import java.io.IOException;  
import java.util.ArrayList;
import java.util.regex.Pattern;
import javax.servlet.Filter;  
import javax.servlet.FilterChain;  
import javax.servlet.FilterConfig;  
import javax.servlet.ServletException;  
import javax.servlet.ServletRequest;  
import javax.servlet.ServletResponse;  
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;  

public class HttpsRedirectFilter extends HttpServlet implements Filter{  

	private static final long serialVersionUID = 1L;
	
	private static String httpsPort = "";
	private static String httpPort = "";
	
	static{
		com.shove.io.file.PropertyFile pf = new com.shove.io.file.PropertyFile();
		httpsPort = pf.read("https_port");
		httpPort = pf.read("http_port");
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException{  
		
		if (!(request instanceof HttpServletRequest) || !(response instanceof HttpServletResponse)) {
			chain.doFilter(request, response);
			return;
		}
		
		//http请求直接放行
		if(!request.isSecure()){
			chain.doFilter(request, response);  
			return;
		}

		//https配置
		ArrayList<String> httpsList = new ArrayList<String>();
		httpsList.add("/reg.do");
		httpsList.add("/login.do");
		httpsList.add("/capitalEnsure.do");

		HttpServletRequest req = (HttpServletRequest)request;
		String uri = req.getRequestURI();
		String contextPath = req.getContextPath();
		String path = uri.replaceFirst(contextPath, "");
		if (httpsList.contains(path)) {
			chain.doFilter(request, response);
			return;
		}
		
		//js/css放行
		String regex = "(\\.js|\\.css)$";  
		if (Pattern.compile(regex).matcher(path).find()) {
			chain.doFilter(request, response);
			return;
		}
		
		//https切换回http
		String redirectTarget =  req.getRequestURL().toString().replaceFirst("https", "http");  
		redirectTarget =  redirectTarget.replaceFirst(":"+httpsPort, ":"+httpPort);
		((HttpServletResponse)response).sendRedirect(redirectTarget);
		
	}  

	@Override  
	public void destroy() { 
		super.destroy();     
	}  

	public void init (FilterConfig filterConfig)  {     
	} 
}
