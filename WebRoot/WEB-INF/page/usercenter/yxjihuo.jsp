<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>三好资本</title>
   <link href="css/css.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="script/nav.js"></script>
		<script type="text/javascript" src="common/date/calendar.js"></script>
		<script src="script/jquery-2.0.js" type="text/javascript"></script>
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
 <jsp:include page="/include/head.jsp"></jsp:include>
</head>
<jsp:include page="/include/top.jsp"></jsp:include>
<body>


<div class="nymain">
  <div class="bigbox">
  <div class="til">邮箱激活</div>
  <div class="sqdk" style="padding-top:55px; padding-bottom:80px; padding-left:280px; background:none;">
    <div class="logintab">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <th align="center" class="jihuo">
       邮件已经发送到你的邮箱,请<a style="cursor: pointer; color: red;" href="http://<%=session.getAttribute("mail")%>" target="_blank">登录</a>到你的邮箱验证
    </th>
  
    </tr>
</table>

    </div>
  </div>
  </div>
</div>
<!-- 引用底部公共部分 -->     
<jsp:include page="/include/footer.jsp"></jsp:include>
<script type="text/javascript" src="script/nav-zh.js"></script>
<script type="text/javascript" src="css/popom.js"></script>

<script>
$(function(){
    //样式选中
     $("#zh_hover").attr('class','nav_first');
	 $("#zh_hover div").removeClass('none');
});
</script>
</body>
</html>
