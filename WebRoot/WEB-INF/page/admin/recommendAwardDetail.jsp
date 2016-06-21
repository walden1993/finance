<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>

<html lang="en">
<head>
<title>推荐投资明细</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
</head>
<body>
<input value="导出" type="button" onclick="exportExcel()" style="float: right;" / >
<div id="divList">
	
</div>
<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../script/jquery.dispatch.js"></script>
<script type="text/javascript">
$(function(){
	var param  = window.location.search;
	$.dispatchPost("recommendAwardDetail.mht"+param,null,initCallBack);
});	

function initListInfo(data){
	var param  = window.location.search;
	$.dispatchPost("recommendAwardDetail.mht"+param,data,initCallBack);
}

function exportExcel(){
	var param  = window.location.search;
	window.location.href="recommendAwardDetailExcel.mht"+param;
}

function initCallBack(data){
	$("#divList").html(data);
}
</script>
</body>
</html>















