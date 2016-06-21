<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>三好资本</title>
		<jsp:include page="/include/head.jsp"></jsp:include>
		<link href="css/inside.css" rel="stylesheet" type="text/css" />
		<link id="skin" rel="stylesheet" href="css/jbox/Gray/jbox.css" />
		<script language="javascript" type="text/javascript"
			src="My97DatePicker/WdatePicker.js"></script>
	</head>

	<body>
		<!-- 引用头部公共部分 -->
		<jsp:include page="/include/top.jsp"></jsp:include>
		<div class="nymain">
			<div class="wdzh">
				<div class="l_nav">
					<!--  div class="box"> -->
         <!-- 引用我的帐号主页左边栏 -->
         <%@include file="/include/left.jsp" %>
      <!-- /div -->
				</div>
				<div class="r_main">
					<div class="tabtil">
						<ul>
							<li onclick="jumpUrl('owerInformationInit.mht');">
								详细信息
							</li>
							<%-- 	
							<li onclick="loadWorkInit('queryWorkInit.mht');">
								工作认证信息
							</li>
							--%>
							<li class="on" onclick="jumpUrl('updatePasswordAndDealpwd.mht');">
								修改密码
							</li>
							<li id="li_bp" onclick="bindingPhoneLoad('boundcellphone.mht');">
								更换手机
							</li>
							<li onclick="jumpUrl('szform.mht');">
								通知设置
							</li>
							<li id="li_tx" onclick="loadBankInfo('yhbound.mht');">
								银行卡设置
							</li>
							<!-- 
							<s:if test="#request.isApplyPro==1">
								<li onclick="jumpUrl('queryQuestion.mht'); ">
									申请密保设置
								</li>
							</s:if>
							<s:if test="#request.isApplyPro==2">
								<li onclick=jump('answerPwdQuestion.mht');;
>
									修改密保设置
								</li>
							</s:if>
							 -->
						</ul>
					</div>
					<div class="box">
						<div class="boxmain2" style="padding-top: 10px;">
							<div class="biaoge2" style="margin-top: 0px;">
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<th colspan="2" align="left" style="padding-top: 0px;">
											会员<b class="red">登录密码</b>修改
										</th>
									</tr>
									<tr>
										<td width="18%" align="right">
											原密码：
										</td>
										<td width="83%">
											<input type="password" class="inp188" id="oldPassword" />
											<span class="txt">输入您现在的帐号登录密码</span>
										</td>
									</tr>
									<tr>
										<td align="right">
											新密码：
										</td>
										<td>
											<input type="password" class="inp188" id="newPassword" />
											<span class="txt">输入您的新密码</span>
										</td>
									</tr>
									<tr>
										<td align="right">
											确认新密码：
										</td>
										<td>
											<input type="password" class="inp188" id="confirmPassword" />
											<span class="txt">请再次输入您的新密码</span>
										</td>
									</tr>
									<tr>
										<td align="right">
											&nbsp;
										</td>
										<td style="padding-top: 10px;">
											<a href="javascript:void(0);" class="bcbtn"
												onclick=javascript:updateLoginPassword();;>提交</a>
										</td>
									</tr>
									<tr>
										<td colspan="2">
											<span style="color: red; float: none;" id="s_tip"
												class="formtips"></span>
										</td>
									</tr>
								</table>

							</div>
							<div class="biaoge2">
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<th colspan="2" align="left" style="padding-top: 0px;">
											会员<b class="red">交易密码</b>修改
										</th>
									</tr>
									<tr>
										<td width="18%" align="right">
											原密码：
										</td>
										<td width="83%">
											<input type="password" class="inp188" id="oldDealpwd" />
											<span class="txt">输入您现在的交易密码</span>
										</td>
									</tr>
									<tr>
										<td align="right">
											新密码：
										</td>
										<td>
											<input type="password" class="inp188" id="newDealpwd" />
											<span class="txt">输入您的新密码</span>
										</td>
									</tr>
									<tr>
										<td align="right">
											确认新密码：
										</td>
										<td>
											<input type="password" class="inp188" id="confirmDealpwd" />
											<span class="txt">请再次输入您的新密码</span>
										</td>
									</tr>

									<tr>
										<td align="right">
											&nbsp;
										</td>
										<td style="padding-top: 10px;">
											<a href="javascript:void(0);" class="bcbtn"
												onclick=javascript:updateDealPassword();;>提交</a>
											<a href="querytraninput.mht">忘记密码?</a>
										</td>
									</tr>
									<tr>
										<td colspan="2">
											<span style="color: red; float: none;" id="d_tip"
												class="formtips"></span>
										</td>
									</tr>
								</table>

							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- 引用底部公共部分 -->
		<jsp:include page="/include/footer.jsp"></jsp:include>
		<script type="text/javascript" src="script/jquery-2.0.js"></script>
		<script type="text/javascript" src="script/jquery.dispatch.js"></script>
		<script type="text/javascript" src="script/nav-zh.js"></script>
		<script type="text/javascript" src="common/date/calendar.js"></script>
		<script type="text/javascript" src="css/popom.js"></script>
		<script>
	$(function() {
		dqzt(4);
		//$('#li_5').addClass('on');
		//displayDetail('3');
		$('#bankCardNew').addClass('on');
	});
	function jumpUrl(obj) {
		window.location.href = obj;
	}
	function jump(url) {
		
		alert("请先回答密保问题！");
		window.location.href = url;
}
	

	function bindingPhoneLoad(url) {
	    var bb = '${person}';//设置更换手机必须先填写个人资料
	    var userType = '${session.user.userType}';
		if (bb == "0") {
			if(userType == "1"){
				alert("请先完善个人详细信息！");
			}else{
				alert("请先完善企业详细信息！");
			}
			window.location.href="owerInformationInit.mht";
		} else {
			window.location.href=url;
		}
	}

	//加载该用户提现银行卡信息
	function loadBankInfo(url) {
		var bb = '${person}';//设置提现银行卡必须先填写个人资料
		var userType = '${session.user.userType}';
		if (bb == "0") {
			if(userType == "1"){
				alert("请先完善个人详细信息！");
			}else{
				alert("请先完善企业详细信息！");
			}
			window.location.href="owerInformationInit.mht";
		} else {
			window.location.href=url;
		}
	}
	
	//工作认证
	function loadWorkInit(url) {
		var bb = '${person}';//填写工作信息必须先填写个人资料
		if (bb == "0") {
			alert("请先完善个人信息");
			window.location.href = "owerInformationInit.mht";
		} else {
			window.location.href = url;
		}
	}

	function updatePwd(url) {
		var bb = '${isApplyPro}';
		if (bb == 1) { 
			alert("请先设置安全问题");
			window.location.href="queryQuestion.mht";
		}else if(bb == 2){
			alert("请先回答安全问题");
			window.location.href="getQuestion.mht";
		}
	}
	//登录密码修改
	function updateLoginPassword() {
		param["paramMap.oldPassword"] = $("#oldPassword").val();
		param["paramMap.newPassword"] = $("#newPassword").val();
		param["paramMap.confirmPassword"] = $("#confirmPassword").val();
		param["paramMap.type"] = "login";
		if ($("#oldPassword").val() == "" || $("#newPassword").val() == ""
				|| $("#confirmPassword").val() == "") {
			$("#s_tip").html("请完整填写密码信息");
			return;
		} else if ($("#newPassword").val().length < 6
				|| $("#newPassword").val().length > 20) {
			$("#s_tip").html("密码长度必须为6-20个字符");
			return;
		} else if ($("#newPassword").val() != $("#confirmPassword").val()) {
			$("#s_tip").html("两次密码不一致");
			return;
		}
		$.dispatchPost("updateLoginPass.mht", param, function(data) {
			if (data == 1) {
				alert("两次密码输入不一致");
				$("#newPassword").attr("value", "");
				$("#confirmPassword").attr("value", "");
			} else if (data == 3) {
				alert("新密码修改失败");
				$("#oldPassword").attr("value", "");
				$("#newPassword").attr("value", "");
				$("#confirmPassword").attr("value", "");
			} else if (data == 2) {
				alert("旧密码错误");
				$("#oldPassword").attr("value", "");
				$("#newPassword").attr("value", "");
				$("#confirmPassword").attr("value", "");
			} else if (data == 5) {
				alert("*修改失败！你的账号出现异常，请速与管理员联系！");
				$("#oldPassword").attr("value", "");
				$("#newPassword").attr("value", "");
				$("#confirmPassword").attr("value", "");
			}else if (data == 6) {
				alert("登录密码不能和交易密码一样！");
				$("#oldDealpwd").attr("value", "");
				$("#newDealpwd").attr("value", "");
				$("#confirmDealpwd").attr("value", "");
			} else {//密码修改成功，跳到登录页面
					alert("修改密码成功");
					$("#oldPassword").attr("value", "");
					$("#newPassword").attr("value", "");
					$("#confirmPassword").attr("value", "");
					window.location.href = "owerInformationInit.mht";
				}
			});
	}

	//交易密码修改
	function updateDealPassword() {
		param["paramMap.oldPassword"] = $("#oldDealpwd").val();
		param["paramMap.newPassword"] = $("#newDealpwd").val();
		param["paramMap.confirmPassword"] = $("#confirmDealpwd").val();
		param["paramMap.type"] = "deal";
		if ($("#oldDealpwd").val() == "" || $("#newDealpwd").val() == ""
				|| $("#confirmDealpwd").val() == "") {
			$("#d_tip").html("请完整填写密码信息");
			return;
		} else if ($("#newDealpwd").val().length < 6
				|| $("#newDealpwd").val().length > 20) {
			$("#d_tip").html("密码长度必须为6-20个字符");
			return;
		} else if ($("#newDealpwd").val() != $("#confirmDealpwd").val()) {
			$("#d_tip").html("两次密码不一致");
			return;
		}
		$.dispatchPost("updateLoginPass.mht", param, function(data) {
			if (data == 1) {
				alert("两次密码输入不一致");
				$("#newDealpwd").attr("value", "");
				$("#confirmDealpwd").attr("value", "");
			} else if (data == 3) {
				alert("新密码修改失败");
				$("#oldDealpwd").attr("value", "");
				$("#newDealpwd").attr("value", "");
				$("#confirmDealpwd").attr("value", "");
			} else if (data == 2) {
				alert("旧密码错误");
				$("#oldDealpwd").attr("value", "");
				$("#newDealpwd").attr("value", "");
				$("#confirmDealpwd").attr("value", "");
			} else if (data == 4) {
				//add by lw
				alert("密码长度输入错误,密码长度范围为6-20");
				$("#oldDealpwd").attr("value", "");
				$("#newDealpwd").attr("value", "");
				$("#confirmDealpwd").attr("value", "");
			} else if (data == 5) {
				alert("*修改失败！你的账号出现异常，请速与管理员联系！");
				$("#oldDealpwd").attr("value", "");
				$("#newDealpwd").attr("value", "");
				$("#confirmDealpwd").attr("value", "");
			}  else if (data == 7) {
				alert("交易密码不能和登录密码一样！");
				$("#oldDealpwd").attr("value", "");
				$("#newDealpwd").attr("value", "");
				$("#confirmDealpwd").attr("value", "");
			} else {//密码修改成功，跳到登录页面
				alert("修改密码成功");
					//alert("修改密码成功,新密码为:" + $("#newDealpwd").val());
					//window.location.href='login.mht';
				$("#oldDealpwd").attr("value", "");
				$("#newDealpwd").attr("value", "");
				$("#confirmDealpwd").attr("value", "");
				window.location.href = "owerInformationInit.mht";
			}
		});
	}
</script>
	</body>
</html>
