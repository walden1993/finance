<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>三好资本-注册</title>
	<meta http-equiv="pragma" content="no-cache"/>
	<meta http-equiv="cache-control" content="no-cache"/>
	<meta http-equiv="expires" content="0"/>    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3"/>
	<meta http-equiv="description" content="This is my page"/>
	<link href="css/css.css" rel="stylesheet" type="text/css" />
	<link id="skin" rel="stylesheet" href="css/jbox/Gray/jbox.css" />
    <style type="text/css">
      ul,li{margin:0;padding:0}
      #scrollDiv{height:182px;overflow:hidden}
    </style>
</head>
<body>
<!-- 引用头部公共部分 -->
<jsp:include page="/include/top.jsp"></jsp:include>	
<!-- 会员注册主体 -->
<div class="nymain">
<div class="bigbox">
<div class="til">会员注册</div>
<div class="sqdk" style="padding-top:55px; padding-bottom:80px; padding-left:60px;">
  <div style="width: 422px; height: 45px;background-image:url('images/llz/bi.png'); ">
  	<a id="gerenA" href="#"><img id="gerenImg" onclick="UserKind(this.id)" src="images/llz/gerenred.png" style="width: 118px; height: 29px;top: 12px;left: 120px;position: relative;"/></a>
  	<a id="qiyeA" href="#"><img id="qiyeImg" onclick="UserKind(this.id)" src="images/llz/qiye.png" style="width: 118px; height: 29px;top: 12px;left: 120px;position: relative;"/></a>
  </div>
  <!--个人、企业用户单选按钮  -->
  <div style="left:120px;position: relative;">
  <table width="100%" border="0" cellspacing="0" cellpadding="0"> 
    <tr>  
    <th align="right"></th>
    <td>
	    <label><input id='gerenemailZc' style="width: auto;" type="radio" onclick="onMode(1)" class="inp202" checked="checked"  name="paramMap.mode" value="邮箱注册"/>&nbsp;邮箱注册</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	    <label><input id='gerennumberZc' style="width: auto;" type="radio" onclick="onMode(2)" class="inp202"  name="paramMap.mode" value="手机注册"/>&nbsp;手机注册</label>
    </td>
  </tr>
  </table>
  </div>

<div id='bodyDiv' class="logintab">
<form action="register.mht" method="post">      
<s:hidden name="paramMap.param" id="param"  />
  <!--企业全称 -->
<div id='orgNameDiv' style="display: none; left: 10px;position: relative;">
<table width="100%" border="0" cellspacing="0" cellpadding="0"> 
	<tr >
		<th align="right" style="width: 100px;">企业全称：<span class="fred">*&nbsp;&nbsp;</span></th>
		<td><input type="text" class="inp202"  name="paramMap.orgName" id="orgName"/></td>
	</tr>
	<tr>
		<th align="right"></th>
		<td> <span style="color: red" id="s_orgName" class="formtips" ></span></td>
	</tr>
</table>
</div>
 <!--手机号码 -->
<div id='phoneDiv' style="display: none; left: -15px;position: relative;"> 
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr><th></th><td><input type="hidden" value="1" name='paramMap.king' id='kingA'/> </td></tr>
	<tr>
		<th align="right">手机号码：<span class="fred">*&nbsp;&nbsp;</span></th>
		<td><input type="text" class="inp202" name="paramMap.cellphone" id="cellphone"/></td>
	</tr>
	<tr>
		<th align="right"></th>
		<td> <span style="color: red" id="s_cellphone" class="formtips"></span></td>
	</tr>
	<tr>
	    <th align="right">手机验证码：<span class="fred">*&nbsp;&nbsp;</span></th>
	    <td >
	    	<input style="width: 110px;" type="text" class="inp202" id="cellcode"  name="paramMap.cellcode" />&nbsp;&nbsp;
	    	<img id="codeImg" src="images/llz/sjYzm1.jpg" title="获取手机验证码"  style="cursor: pointer;" width="90" height="25" onclick="checkCode(this.id,'codeImg2')" />
	    	<span id="codeSpan" style="color: blue;"></span>
	    	<!-- <a href="" onclick=""></a><img src="images/llz/sjYzm1.jpg" alt="获取手机验证码"  style="width: 90px;height: 25px;" />
	    	 <input type="image" style="width: 90px;height: 25px;" src="images/llz/sjYzm1.jpg" class="inp202" name="paramMap.IphoneYzm"  id="ImgCellcode"/>-->
	    </td>
	</tr>
	<tr>
		<th align="right"></th>
		<td> <span style="color: red" id="s_cellcode" class="formtips"></span></td>
	</tr>
</table>
</div>
 <!--常用邮箱 -->
<div id='emailDiv' style="left: -8px;position: relative;">
<table width="100%" border="0" cellspacing="0" cellpadding="0"> 
	<tr>  
		<th align="right">常用邮箱：<span class="fred">*&nbsp;&nbsp;</span></th>
		<td><input type="text" class="inp202"  name="paramMap.email" id="email"/></td>
	</tr>
	<tr>
		<th align="right"></th>
		<td> <span style="color: red" id="s_email" class="formtips"></span></td>
	</tr>
</table>
</div>
<table width="100%" border="0" cellspacing="0" cellpadding="0"> 
	<tr>
		<th align="right">用户名：<span class="fred">*&nbsp;&nbsp;</span></th>
		<td ><input type="text" class="inp202" name="paramMap.userName"  id="userName"/></td>
	</tr>
	<tr>
		<th align="right"></th>
		<td> <span  style="color: red" id="s_userName" class="formtips"></span></td>
	</tr>
	<tr>
		<th align="right">密码：<span class="fred">*&nbsp;&nbsp;</span></th>
		<td><input  type="password" class="inp202" name="paramMap.password" id="password"/></td>
	</tr>
	<tr>
		<th align="right"></th>
		<td> <span  style="color: red" id="s_password" class="formtips"></span></td>
	</tr>
	<tr>
		<th align="right">确认密码：<span class="fred">*&nbsp;&nbsp;</span></th>
		<td ><input type="password" class="inp202" name="paramMap.confirmPassword" id="confirmPassword"/></td>
	</tr>
	<tr>
		<th align="right"></th>
		<td><span  style="color: red" id="s_confirmPassword" class="formtips"></span>
		</td>
	</tr>
	<tr>
		<th align="right">推荐人：&nbsp;&nbsp;&nbsp;</th>
		<td>
			<s:if test="paramMap.refferee==''">
				<input type="text" class="inp202" name="paramMap.refferee" id="refferee" value="${paramMap.refferee }"/>
			</s:if>
			<s:else>
				<input type="text" class="inp202" name="paramMap.refferee" id="refferee" value="${paramMap.refferee }"  />
			</s:else>
			<span id="s_refferee11" class="fred"></span>
		</td>
	</tr>
	<tr>
		<th align="right"></th>
		<td><span id="s_refferee" class="formtips"></span>
		</td>
	</tr>
	<tr>
		<th align="right">验证码：<span class="fred">*&nbsp;&nbsp;</span></th>
		<td><input type="text" class="inp100"  name="paramMap.code" id="code"/>
		<img src="admin/imageCode.mht?pageId=userregister" title="点击更换验证码" style="cursor: pointer;" id="codeNum" width="46" height="18" onclick="javascript:switchCode(1)" />
		<a href="javascript:void()" onclick="switchCode(1)" style="color: blue;">换一换?</a>		
		</td>
	</tr>
	<tr>
		<th align="right"></th>
		<td><span  style="color: red" id="s_code" class="formtips"></span></td>
	</tr>
	<tr>
		<th align="right">&nbsp;</th>
		<td class="tyzc"><input type="checkbox" id="agre" checked="checked" />&nbsp; 我已经阅读并同意<a style="cursor: pointer;" onclick="fff()">使用条款</a>和<a style="cursor: pointer;" onclick="ffftip()">隐私条款</a></td>
	</tr>
	<tr>
		<th align="right">&nbsp;</th>
		<td><input type="button" id="btn_register" value="免费注册" class="zcbtn" style="cursor: pointer;"/></td>
	</tr>
</table>
</form> 
</div>
<div class="tip">
	<ul>
		<li>帮助他人 快乐自己 收获利息</li>
		<li>助您创业 资金周转 分期偿还</li>
		<li>收益稳定可靠回报高</li>
		<li>交易安全快捷有保障</li>
	</ul>
	<div class="loginbtn">
		<a href="login.mht" class="dlbtn">马上登录</a>
	</div>
</div>
<div class="renpic" style="top:50px;">
</div>
</div>
  <!-- 手机用户注册结束 -->
  </div>
</div>
<!-- 引用底部公共部分 -->     
<jsp:include page="/include/footer.jsp"></jsp:include>
<script type="text/javascript" src="script/nav-zh.js"></script>
<script type="text/javascript" src="css/popom.js"></script>
<script type="text/javascript" src="script/jbox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript" src="script/jbox/jquery.jBox-zh-CN.js"></script>
<script type="text/javascript" src="script/zhucheJS.js"></script><!-- 引用验证js -llz -->

</body>
</html>
