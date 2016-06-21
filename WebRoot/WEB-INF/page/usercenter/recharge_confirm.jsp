<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>三好资本</title>
<link href="css/css.css" rel="stylesheet" type="text/css" />
<style>
.nymain p{padding: 0;text-indent: 0}</style>
</head>
<body>
<div class="nymain" style="width: 560px;margin: 0 auto;line-height: 22px;text-align: justify;" >
    <h2 style="text-align:center;font-size: 32px;padding-bottom: 80px;padding-top:50px;">
    	此次充值操作是否成功？</h2>
    <div class="zw">
    
    </div>
    
    <div class="btn" id="btn_submit" style="position:absolute;">
        <a href="javascript:window.parent.callBackRecharge(1)" class="bcbtn"  style="margin:0px 100px 0px 150px;">成功付款</a><a href="javascript:window.parent.callBackRecharge(0)" class="bcbtn">付款出现异常</a>
    </div>
</div>
</body>
</html>
