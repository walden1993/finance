<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags"  prefix="s"%>
<%
response.setHeader("Pragma","No-cache"); 
response.setHeader("Cache-Control","no-cache"); 
response.setDateHeader("Expires", 0); 
response.flushBuffer();

%> 
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title>资金记录</title>
   <link id="skin" rel="stylesheet" href="css/jbox/Gray/jbox.css" />
   <link rel="stylesheet" type="text/css" href="css/user.css" />
   <link rel="stylesheet" type="text/css" href="css/common.css" />
</head>
<body>
<div class="tab_ec clearfix">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
		  <tr class="leve_titbj">
		    <td>时间</td>
		    <td>类型</td>
		    <td>收入/支出金额</td>
		    <td width="20%">备注</td>
		  </tr>
		  
		  <s:if test="pageBean.page!=null && pageBean.page.size>0">
		  <s:set name="counts" value="#request.pageNum"/>
            <s:iterator value="pageBean.page"  var="bean" status="s">
			    <tr>
				    <td>${bean.recordTime }</td>
				   	<td>${bean.fundMode }</td>
				    <s:if test="#bean.income!=0 && #bean.income!=null"><td class="green">+${bean.income }</td></s:if>
				    <s:else ><td class="red">-${bean.spending }</td></s:else>
				    <td>${bean.remarks }</td>
		  		</tr>
			  </s:iterator>
			  
			  
		  </s:if>
		  <s:else>
		    <tr><td colspan="11" align="center">暂无信息</td></tr>
		  </s:else>
	</table>
	<div class="page">
	<p>
	<shove:page curPage="${pageBean.pageNum}" showPageCount="6" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
    </p>
</div>

</div> 
</body>
</html>
