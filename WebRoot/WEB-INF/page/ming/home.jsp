<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE HTML>
<html>
<head>
<base href="<%=basePath%>">
<title>标的列表</title>
<jsp:include page="html5meta.jsp"></jsp:include>
<link rel="stylesheet" type="text/css"
	href="script/bootstrap/css/bootstrap.min.css">
</head>
<body>
	<div class="list-group">
	<s:iterator  value="pageBean.page" var="finance">
	  <a href="mingBorrowDetail.mht?id=${finance.id}" class="list-group-item" >
	    <h4 class="list-group-item-heading">${finance.borrowTitle}</h4>
	    <div class="table-responsive" style="border: none">
		    <table class="table">
		    	<tr>
		    		<td style="border: none">总额</td>
		    		<td style="border: none">年利率</td>
		    		<td style="border: none">期限</td>
		    	</tr>
		    	<tr>
		    		<td style="border: none">${finance.borrowAmount}</td>
		    		<td style="border: none">${finance.annualRate}%</td>
		    		<td style="border: none"><s:property value="#finance.deadline" default="0"/><s:if test="%{#finance.isDayThe ==1}">个月</s:if><s:else>天</s:else></td>
		    	</tr>
		    	<tr>
		    		<td colspan="2" style="border: none">
		    			<div class="progress">
						  <div class="progress-bar progress-bar-danger" role="progressbar" aria-valuenow="<s:property value="#finance.schedules" default="0"/>" aria-valuemin="0" aria-valuemax="100" style="width: <s:property value="#finance.schedules" default="0"/>%;">
						    <s:property value="#finance.schedules" default="0"/>%
						  </div>
						</div>
		    		</td>
		    		<td colspan="1" style="border: none"><button type="button" class="btn  btn-danger btn-xs btn-lg">立即投标</button></td>
		    	</tr>
		    </table>
	    </div>
	  </a>
	  </s:iterator>
	</div>
	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>
