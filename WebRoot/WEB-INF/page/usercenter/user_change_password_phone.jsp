<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>三好资本</title>
<link href="css/css.css" rel="stylesheet" type="text/css" />
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
<script>
/*
  $(function(){
  
    $("#send_password").click(function(){
       var param = {};
       param["paramMap.email"] = $("#email").val();
       //alert(param["paramMap.email"]);
       $.post("forgetpasswordsenEml.mht",param,function(data){
        if(data==0){
          $("#s_email").html("邮箱不能为空");
        }else if(data==2){
          $("#ok").html("邮件已将发送到你的邮箱，请登录你邮箱");
        }else{
          $("#s_email").html("该邮箱不存在");
        }
       });
    });
  
  
  });
*/

</script>
   <jsp:include page="/include/head.jsp"></jsp:include>
</head>

<body>
<jsp:include page="/include/top.jsp"></jsp:include>	


<div class="nymain">
	<div class="bigbox">
	<div class="til">修改会员登录密码</div>
	<div class="sqdk" style="padding-top:55px; padding-bottom:80px; padding-left:300px; background:none;">
	<div>
	<span style="font-size: 16px; text-align: center;height:80px;" >手机找回密码&nbsp;&nbsp;-&nbsp;&nbsp;(<a style="color: blue;" href="forgetpassword.mht">邮箱找回密码</a>)</span>
	</div>
	
	<div id="s_tip" align="left" style="color: red;"></div>
	<div class="logintab" id="ok">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<th align="right">手机号码：<span class="fred">*&nbsp;&nbsp;</span></th>
			<td><input type="text" class="inp202" name="paramMap.cellphone" id="cellphone"  maxlength="11"/></td>
		</tr>
		<tr>
			<th align="right"></th>
			<td> <span style="color: red" id="s_cellphone" class="formtips"></span></td>
		</tr>
		<tr>
		    <th align="right">手机验证码：<span class="fred">*&nbsp;&nbsp;</span></th>
		    <td >
		    	<input style="width: 110px;" type="text" class="inp202" id="cellcode" onblur="checkRegister(this.id)"  maxlength="4"  name="paramMap.cellcode" />&nbsp;&nbsp;
		    	<img id="codeImg" src="images/llz/sjYzm1.jpg" title="获取手机验证码"  style="cursor: pointer;" width="90" height="25" onclick="checkCode(this.id)" />
		    	<span id="codeSpan" style="color: blue;"></span>
		    </td>
		</tr>
		<tr>
			<th align="right"></th>
			<td> <span style="color: red" id="s_cellcode" class="formtips"></span></td>
		</tr>
	</table>
	  <!-- 手机找回 -->
	  <div id="pwdDiv" style="display: none">
	  <table width="100%" border="0" cellspacing="0" cellpadding="0">
		  <tr>
		    <th align="right">新密码：<span class="fred">*&nbsp;&nbsp;</span></th>
		    <td><input type="password" class="inp202"  id="newPassword"/></td>
		  </tr>
		  <tr>
			<th align="right"></th>
			<td><span id="s_newPassword" class="txt" style="color: #pcc;">密码长度不少于6个字符</span></td>
		 </tr>
		  <tr>
		    <th align="right">确认新密码：<span class="fred">*&nbsp;&nbsp;</span></th>
		    <td><input type="password" class="inp202" id="confirmpassword" /></td>
		  </tr>
		  <tr>
			<th align="right"></th>
			<td> <span id="s_confirmpassword" class="txt" style="color: red;"></span></td>
		 </tr>
		 <tr>
		    <td align="right">&nbsp;</td>
		    <td style="padding-top:10px;"><input type="button" value="提交" class="bcbtn" id="bt_yes"/></td>
		  </tr> 
	  </table>
	  </div>
	</div>
  </div>
  </div>
</div>

<!-- 引用底部公共部分 -->     
<jsp:include page="/include/footer.jsp"></jsp:include>
<script type="text/javascript" src="script/nav-zh.js"></script>
<script type="text/javascript" src="css/popom.js"></script>

<script>


var isCode=0;//标志验证码是否匹配 ，0代表不用修改手机号码，1代表匹配，-1代表不匹配
var isPhone=0;//标志手机号码是否存在，存在1，不存在-1
//ajax验证手机验证码是否正确
function checkRegister(str){
	var param = {};
	if(str=="cellphone"){
		param["paramMap.cellphone"] = $("#cellphone").val();
	}else{
		param["paramMap.cellcode"] = $("#cellcode").val();
	}
	$.post("ajaxCheckRegister.mht",param,function(data){
		if(data==32){
			document.getElementById("s_cellphone").style.color='green';
			$("#s_cellphone").html("该手机存在，可以获取验证码");
			isPhone=1;
		}
		 
		
		if(data==33){
			if(isPhone==1){
				document.getElementById("s_cellcode").style.color='green';
				$("#s_cellcode").html("√  验证码匹配");
				document.getElementById("pwdDiv").style.display="";
				isCode=1;
			}else if(isPhone==-1){
				alert("手机号码不存在！");
			}
		}else if(data==34){
			document.getElementById("s_cellcode").style.color='red';
			$("#s_cellcode").html("验证码不匹配");
			document.getElementById("pwdDiv").style.display="none";
			isCode=-1;
		}
		
		
		
	});
}

//发送验证码间隔60秒
var i=180;
function timedCount(){
	i=i-1;
	document.getElementById("codeSpan").innerHTML=i+"秒后重新获取";
	document.getElementById("codeImg").style.display='none';
	if(i<=0){
		document.getElementById("codeImg").style.display='inline';
		document.getElementById("codeSpan").innerHTML="";
		i=180;
	}else{
		setTimeout("timedCount()",1000);
	}
}
//ajax发送验证码
function checkCode(x){
	if(isPhone==1){
		var param = {};
		if(x=="codeImg"){
			param["paramMap.userName"] = '';
			param["paramMap.cellphone"] = $("#cellphone").val();
			$.post("ajaxSendSMS1.mht",param,function(data){
				if(data=="-1"){
					alert("验证码发送失败,请重试");
				}else if(data=="-2"){
					document.getElementById("s_cellphone").style.color='red';
					$("#s_cellphone").html("请输入您的手机号码");
				}else if(data=="1"){
					timedCount();
				}
			});
		}
	
	}else{
		document.getElementById("s_cellcode").style.color='red';
		alert("您输入的手机号码不存在");
		document.getElementById("s_cellphone").style.color='red';
		
		$("#s_cellphone").html("您输入的手机号码不存在，请重试！");
	}
}

$(function(){
    //样式选中
     $("#zh_hover").attr('class','nav_first');
	 $("#zh_hover div").removeClass('none');
});
</script>
<script>
$(function(){
   $("#cellphone").blur(function(){
     if($("#cellphone").val()==""){
       document.getElementById("s_cellphone").style.color='red';
       $("#s_cellphone").html("手机号码不能为空");
     }else if(!/^\d{11}$/.test($("#cellphone").val())){
     	document.getElementById("s_cellphone").style.color='red';
     	$("#s_cellphone").html("手机号码格式错误");
     }else{
     	$("#s_cellphone").html("");
     	checkRegister('cellphone');
     }
   });

   $("#newPassword").blur(function(){
     if($("#newPassword").val()==""){
       document.getElementById("s_newPassword").style.color='red';
       $("#s_newPassword").html("新密码不能为空");
     }else if($("#newPassword").val().length<6  && $("#newPassword").val().length>20){
       document.getElementById("s_newPassword").style.color='red';
       $("#s_newPassword").html("密码长度范围为6-20");
     }else{
       $("#s_newPassword").html("");
     }
   });
   
   $("#confirmpassword").blur(function(){
      if($("#newPassword").val()!=$("#confirmpassword").val()){
        $("#s_confirmpassword").html("两次密码不相同");
      }else{
       $("#s_confirmpassword").html("");
      }
   });
  $("#bt_yes").click(function(){
  	if($("#newPassword").val()==null){
  		alert('新密码不能为空');
  		return null;
  	}else if($("#newPassword").val().length<6 && $("#newPassword").val().length>20){
  		$("#s_newPassword").html='密码长度范围为6-20';
  	}else if($("#newPassword").val()!=$("#confirmpassword").val()){
  		$("#s_confirmpassword").html='两次输入的密码不匹配';
  	}
  	
    var param = {};
    param["paramMap.newPassword"] =  $("#newPassword").val();
    param["paramMap.userId"] = '${userId}';
    param["paramMap.confirmpassword"] = $("#confirmpassword").val();
    param["paramMap.phone"] = $("#cellphone").val();
    $.post("updatePasswordPhone.mht",param,function(data){
     if(data==1){
     alert("修改密码成功");
     window.location.href='login.mht';
     }else if(data==3){
       alert("新密码不能为空");
     }else if(data==4){
       alert('出现未知问题，请联系客服');
     }else if(data==0){
     alert("修改密码失败,请重新填写");
     }else if(data==5){
        alert("两次输入密码不相同,请从新输入");
     }else if(data==6){
        alert("密码长度范围为6-20");
     }else if(date==7){
     	alert("请输入邮箱或者电话号码修改");
     }
    })
  });

});

</script>


</body>
</html>
