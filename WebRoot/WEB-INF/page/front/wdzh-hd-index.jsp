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
							<%-- 	<li onclick="loadWorkInit('queryWorkInit.mht');">
								工作认证信息
							</li>
							--%>
							<li onclick="updatePwd('setPwd.mht');">
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
							<s:if test="#request.isApplyPro==1">
							<li class="on" onclick="jumpUrl('queryQuestion.mht'); ">
								申请密保设置
							</li>
							</s:if>
							<s:if test="#request.isApplyPro==2">
							<li class="on" onclick="jump('answerPwdQuestion.mht'); ">
								修改密保设置
							</li>
							</s:if>
						</ul>
					</div>
		<div class="box">
        <div class="boxmain2" style="padding-top:10px;">
         <div class="biaoge2" style="margin-top:0px;">
         <form id="answer1" action="checkUserAnswer.mht" method="post">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<th colspan="2" align="left" style="padding-top: 0px;">
								用户安全问题保护验证
							</th>
						</tr>
						<tr>
							<td width="18%" align="right">
								问题一：
							</td>
							<td width="83%">
								${questionOne}
							</td>
						</tr>
						<tr>
							<td width="18%" align="right">
								问题一答案：
							</td>
							<td>
								<input type="text" class="inp188" id="answerOne" name="answerOne"/>
							</td>
						</tr>

						<tr>
							<td width="18%" align="right">
								问题二：
							</td>
							<td width="83%">
								${questionTwo}
							</td>
						</tr>
						<tr>
							<td width="18%" align="right">
								问题二答案：
							</td>
							<td>
								<input type="text" class="inp188" id="answerTwo" name="answerTwo"/>
							</td>
						</tr>
						<tr>
							<td width="18%" align="right">
								问题三：
							</td>
							<td width="83%">
								${questionThree}
							</td>
						</tr>
						<tr>
							<td width="18%" align="right">
								问题三答案：
							</td>
							<td>
								<input type="text" class="inp188" id="answerThree" name="answerThree"/>
							</td>
						</tr>
						<tr>
							<td><span style="color: red; font-size:12px;"><s:fielderror fieldName="paramMap.code" /></span></td>
						</tr>
						
						<tr>
							<td align="right">
								&nbsp;
							</td>
							<td style="padding-top: 10px;">
								<a href="javascript:void(0);" class="bcbtn"
									onclick=javascript:checkUserAnswer();>提交</a>
									<a id="clickCode"   class="yzmbtn" href="javascript:void(0);">忘记密保？发送到手机</a>
     
      <span style="color: red; float: none;" id="code_tip" class="formtips">
      	<s:if test="#request.ISDEMO==1">* 演示站点随便输入答案</s:if>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<span style="color: red; float: none;" id="s_tip"
									class="formtips"></span>
							</td>
						</tr>
					</table>
			</form>
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
		$('#li_5').addClass('on');
	});
	function jumpUrl(obj) {
		window.location.href = obj;
	}
	function bindingPhoneLoad(url) {
		/**		var bb = '${person}';//申请手机绑定必须先填写个人资料
				var cc = '${pass}';
				if (bb == "0") {
					alert("请先完善个人信息");
					window.location.href='owerInformationInit.mht';
				} else if (cc != 3) {
					alert("请等待个人信息审核通过");
					return ;
				} else {*/
		window.location.href = url;
		//		}
	}
	//加载该用户提现银行卡信息
	function loadBankInfo(url) {
		var bb = '${person}';//设置提现银行卡必须先填写个人资料
		if (bb == "0") {
			alert("请先完善个人信息");
			window.location.href = "owerInformationInit.mht";
		} else {
			window.location.href = url;
		}

	}
	function jump(url) {
		
			alert("请先回答密保问题！");
			window.location.href = url;
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
		}else{
			alert("请先回答安全问题");
			window.location.href="getQuestion.mht";
		}
	}

	function checkUserAnswer() {
		if ($("#answerOne").val() == "" || $("#answerTwo").val() == ""
			|| $("#answerThree").val() == "") {
			$("#s_tip").html("请填写问题答案");
			return;
		}
		$("#answer1").submit();
	}
</script>
<script>
	var flags = false;
	  //手机号码绑定
		  var timers=60;
		  var tipId;
		 
		   $(function(){
		       $("#clickCode").click(function(){
		    	   var phone="${request.bindingPhone}"+"";
		    	   if(phone==""){
		    		   alert("您还没有绑定手机");
		    		   return;
			    	   }
	                 
	                 if($("#clickCode").html()=="重新发送"||$("#clickCode").html()=="忘记密保？发送到手机") {  
	                	 
	                	 $.post("phoneCheck.mht","phone="+phone,function(datas){

	                	     if(datas.ret != 1){
	                	         alert(datas.msg);
	                	     return;
	                	     }
	                	     phone = datas.phone;
	                	     var test={};
	                	     test["paramMap.phone"] = phone;
	                	     test["paramMap.a"] = "a";
	                	 
	                    $.post("sendSMS.mht",test,function(data){
	                    	if(data == "virtual"){
	     						window.location.href = "noPermission.mht";
	     						return ;
	     					}
	                       if(data==1){
	                          timers=60;
	                          tipId=window.setInterval(timer,1000);
	                       }else{
	                          alert("发送失败");
	                       }
	                     
	                  
	                    });
	                    
	                	 });
	                  }
                   
		       });
		   });
		   
		   //定时
		   function timer(){
		    
		       if(timers>=0){
		         $("#clickCode").html("信息获取："+timers+"秒");
		         timers--;
		       }else{
		          window.clearInterval(tipId);
		           $("#clickCode").html("重新发送");
		           $.post("removeSessionAnswer.mht","",function(data){});
		           
		       }
		   }
		</script>
	</body>
</html>
