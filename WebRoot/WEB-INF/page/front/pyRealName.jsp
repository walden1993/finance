<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link rel="stylesheet" type="text/css" href="css/common.css" />
<link rel="stylesheet" type="text/css" href="css/index.css" />
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" />
<link rel="stylesheet" href="css/lightbox.css" type="text/css" media="screen" />
<jsp:include page="/include/head_gywm.jsp"></jsp:include>
<link rel="stylesheet" type="text/css" href="css/new_inside.css" />   
</head>

<body>
<!-- 引用头部公共部分 -->
<jsp:include page="/include/top.jsp"></jsp:include>

<div style="text-align:center;margin: 0px auto;">
	<br />
	<s:actionerror />
	<s:actionmessage />
	<br />
	<form action="pyRealName.mht">
		<div>真实姓名：<input type="text" name="realName"  /></div>
		<br />
		<div>身份证：<input type="text" name="cardId" /></div>
		<br />
		<input type="submit" value="提交"/>&nbsp;&nbsp;&nbsp;
		<input type="reset"  value="重置"/>
	</form>
</div>

<!--footer-->
<jsp:include page="/include/footer.jsp"></jsp:include>
<script type="text/javascript" src="js/js.js"></script>
<script type="text/javascript">jQuery(".tabsList").slide({ titCell:".tit",triggerTime:0 });</script>
</body>
</html>
