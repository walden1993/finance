<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>三好资本</title>
<link href="css/css.css" rel="stylesheet" type="text/css" />
<style>
.nymain p{padding: 0;text-indent: 0}</style>
<script src="script/jquery-2.0.js" type="text/javascript"></script>
<script type="text/javascript" src="script/jbox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript" src="script/jbox/jquery.jBox-zh-CN.js"></script>
<script>
$(function(){
	$('.tabmain').find('li').click(function(){
	$('.tabmain').find('li').removeClass('on');
	$(this).addClass('on');
	$('.lcmain_l').children('div').hide();
    $('.lcmain_l').children('div').eq($(this).index()).show();
	})
	})
</script>
</head>
<body>
<div class="nymain" style="width: 560px;margin: 0 auto;line-height: 22px;text-align: justify;" >
    <h2 style="text-align:center;font-size: 16px;padding-bottom: 10px;padding-top:20px;">${paramMap.title }</h2>
    <div class="zw">
    ${paramMap.content}1、投资体验是三好资本网贷平台为广大投资理财用户免费推出的投资体验产品。<br />
2、用户只需领取并激活投资体验券，在有效期内便可免费参加平台推出的投资体验产品。<br />
3、平台将根据用户加入体验项目时的体验金金额计算产品投资利率，按期返还利息收益给用户。<br />
4、平台在用户成功参与当期投资体验产品时，将体验金收回，用户在收到利息收益后，可提现至个人指定的银行账户。<br />
5、投资体验产品解释权归三好资本官方所有。<br />
    </div>
</div>
<!-- 引用底部公共部分 -->     
</body>
</html>