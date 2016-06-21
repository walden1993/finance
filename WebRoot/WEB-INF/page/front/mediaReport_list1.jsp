<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="pub_tb bottom"><h3>互联网金融</h3></div><div class="clearfix"></div>
<div class="m20">
			<s:if test="pageBean.page==null || pageBean.page.size==0">
			   <div align="center">
				  <span>暂无数据</span>
			   </div>
		</s:if>
   	 <s:else>
	   <s:iterator value="pageBean.page" var="bean">
	       <li><a href="frontMedialinkId.mht?id=${bean.id}" target="_blank"><span class="lbt">${bean.title}</span><span class="ldt">${fn:split(bean.publishTime,' ')[0]}</span></a></li>
		</s:iterator>
     </s:else>
</div>