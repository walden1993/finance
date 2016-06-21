<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>三好资本</title>
   <jsp:include page="/include/head.jsp"></jsp:include>
   <script type="text/javascript">var menuIndex = 4;</script>
</head>

<body>
<jsp:include page="/include/top.jsp"></jsp:include>	
<div class="wrap">
<div class="w950">
<ol class="breadcrumb mb0 bg-none">
		  <li><a href="home.mht">我的账户 </a></li>
		  <li class="active">找回密码</li>
	  </ol>
<div class="ny_content bg-white mb20">
<div class="reg_matter clearfix">
  <div class="pub_tb bottom">
		  <h3>找回密码</h3>
		  <div class="clearfix"></div>
		  </div>
  <div class="pw_tab clearfix">
        <ul>
          <li id="one1" onclick="change(1)"  class="hover">邮箱</li>
          <li id="one2" onclick="change(2)" >手机</li>
        </ul>
  </div>
  <div class="clearfix mt30" id="con_one_1">
    <div class="pw_info clearfix">
        <div class="cell clearfix">
                <div class="bt01"><span class="red">*</span>E-mail：</div>
                <div class="info">
                    <input name="input2" type="text"  id="email"    class="text"  placeholder="请输入您注册时用的邮箱" /><br/><span style="color: red; float: none;" id="s_email" class="formtips"></span>
                </div>
        </div>
        <div class="cell clearfix  pt20">
                <div class="bt01">&nbsp;</div>
                <div class="info"><input type="button" class="red_goon" value="发 送"  id="send_password"/></div>
       </div>
      <!--发送成功--><div class="tc ny_tips" id="ok"></div>
   </div>
  </div>
  <div class="clearfix mt30" id="con_one_2" style="display:none;">
     <div class="pw_info clearfix">
        <div class="cell clearfix">
                <div class="bt01"><span class="red">*</span>手机号码：</div>
                <div class="info">
                    <input name="input2" type="text" class="text"  maxlength="11"  id="cellphone"/>
                </div>
        </div>
        <div class="cell clearfix">
                <div class="bt01"><span class="red">*</span>验证码：</div>
                <div class="info">
                    <input name="input2" type="text" class="text1"  maxlength="4"  id="cellcode"  />
                    <input type="button" id="codeImg"     value="发送手机验证码" class="blue_btn"  style="width: 130px;"   />
                     <p style="color: #666;margin-top: 15px;">收不到短信验证码? 点击这里<a onclick="checkCode('codeImg','vioce')"  style="cursor: pointer;" class="orange" >获取语音验证码</a></p>  
                    <p id="vioce" style="display: none;"><font color="red">获取语音验证码需要呼叫您的手机，请保持手机畅通，</font>注意接听 400-6000583 的来电。</p>
                    <br/><span style="color:red;margin: 20px 0px;"  id="s_cellphone"></span>
                </div>
        </div>
   </div>
   <!--发送成功-->
   <div  class="pw_info clearfix" id="pwdDiv" style="display: none">
        <div class="cell clearfix">
            <div class="bt01">新密码：<span class="fred">*&nbsp;&nbsp;</span></div>
            <div class="info">
                <input type="password" class="inp202"  id="newPassword"/>
                <br/><span id="s_newPassword" class="txt" style="color: #pcc;">密码长度不少于6个字符</span></span>
            </div>
         </div>
         <div class="cell clearfix">
            <div class="bt01">确认新密码：<span class="fred">*&nbsp;&nbsp;</span></div>
            <div class="info">
                <input type="password" class="inp202" id="confirmpassword" />
                <br/><span id="s_confirmpassword" class="txt" style="color: red;"></span>
            </div>
        </div>
        <div class="cell clearfix">
         <div class="bt01">&nbsp;</div>
            <div class="info">
                <input type="button" value="提交" class="bcbtn" id="bt_yes"/>
            </div>
        </div>
      </div>
  </div>
  <div class="text-center mt80 mb20 f14">若您无法使用上述方法找回，请联系客服${sitemap.servicePhone }</div>
 </div>
</div>
</div></div>
<!-- 引用底部公共部分 -->     
<jsp:include page="/include/footer.jsp"></jsp:include>
<script>

$(function(){
	$("#codeImg").click(function(){
		checkCode('codeImg');
	})
	
	$("#cellcode").keyup(function(){
		if(this.value.length==4){//已输入验证码完毕
			param["paramMap.cellphone"] = $("#cellphone").val();
	        param["paramMap.cellcode"] = $("#cellcode").val();
			$.post("ajaxCheckRegister.mht",param,function(data){
		        if(data==33){
		            if(isPhone==1){
		            	$("#s_cellphone").css("color","green");
		                $("#s_cellphone").html("√  验证码匹配");
		                $("#pwdDiv").css("display","block");
		                isCode=1;
		            }else if(isPhone==-1){
		            	$("#s_cellphone").css("color","red");
		            	$("#s_cellphone").html("手机号码不存在！");
		            }
		        }else if(data==34){
		        	$("#s_cellphone").css("color","red");
		            $("#s_cellphone").html("验证码不匹配");
		            $("#pwdDiv").css("display","none");
		            isCode=-1;
		        }
		    });
		}
	})
})


var isCode=0;//标志验证码是否匹配 ，0代表不用修改手机号码，1代表匹配，-1代表不匹配
var isPhone=0;//标志手机号码是否存在，存在1，不存在-1
//ajax验证手机验证码是否正确
function checkRegister(str){
	if(!str){
		$("#s_cellphone").html("请输入手机号码!");
		$("#s_cellphone").css("color","red");
		return;
	}
    if(str=="cellphone"){
        param["paramMap.cellphone"] = $("#cellphone").val();
    }else{
        param["paramMap.cellcode"] = $("#cellcode").val();
    }
    $.dispatchPost("ajaxCheckRegister.mht",param,function(data){
    	if(data==0){
    		$("#s_cellphone").html("该手机号码不存在,请重新输入!");
    		$("#s_cellphone").css("color","red");
    		$("#codeImg").attr("disabled","true");
    	}
        if(data==32){
        	$("#s_cellphone").html("该手机号码存在,请获取验证码!");
            $("#s_cellphone").css("color","green");
            isPhone=1;
            $("#codeImg").removeAttr("disabled");
        }
        if(data==33){
            if(isPhone==1){
            	$("#s_cellphone").css("color","green");
                $("#s_cellcode").html("√  验证码匹配");
                document.getElementById("pwdDiv").style.display="";
                isCode=1;
            }else if(isPhone==-1){
                $("#s_cellphone").html("该手机号码不存在,请重新输入!");
                $("#s_cellphone").css("color","red");
            }
        }else if(data==34){
        	$("#s_cellphone").css("color","red");
            $("#s_cellcode").html("验证码不匹配");
            document.getElementById("pwdDiv").style.display="none";
            isCode=-1;
        }
    });
}

//发送验证码间隔60秒
var j=60;
function timedCount(){
	var codeImg=$("#codeImg");
    if(j>0){
    	codeImg.attr("disabled","true");
    	codeImg.removeClass("blue_btn");
    	codeImg.addClass("hui_btn");
    	codeImg.val(j+"秒后重发短信");
        j--;
        setTimeout(timedCount,1000);
    }else{
    	codeImg.removeAttr("disabled");
    	codeImg.removeClass("hui_btn");
    	codeImg.addClass("blue_btn");
    	codeImg.val("重新发送");
        j=60;
    }
}
//ajax发送验证码
function checkCode(x,vioce){
	if(!$("#cellphone").val()){
        $("#s_cellphone").css("color","red");
        $("#s_cellphone").html("请输入手机号码");
        return;
    }
    
    if($("#codeImg").attr("disabled") && $("#codeImg").attr("disabled")=="disabled"){
    	$("#s_cellphone").css("color","red");
        $("#s_cellphone").html("您的验证码已发送，请稍候再试...");
        setTimeout(function(){
            $("#s_cellphone").html("");
        },1000)
        return
    }	
    if(isPhone==1){
        var param = {};
        if(x=="codeImg"){
        	if(vioce){
                param["paramMap.vioce"] = true;
            }
            param["paramMap.userName"] = '';
            param["paramMap.cellphone"] = $("#cellphone").val();
            $.post("ajaxSendSMS1.mht",param,function(data){
                if(data=="-1"){
                    alert("验证码发送失败,请重试");
                }else if(data=="-2"){
                	$("#s_cellphone").css("color","red");
                    $("#s_cellphone").html("请输入您的手机号码");
                }else if(data=="1"){
                	j=60;
                    timedCount();
                    if(vioce){
                        $("#vioce").css("display","block");
                        setTimeout(function(){
                            $("#vioce").css("display","none");
                        },60000)
                    }
                }else if(data=="3"){
                	$("#s_cellphone").css("color","red");
                    $("#s_cellphone").html("对不起,您今日的短信发送量已上限。");
                }
            });
        }
    
    }else{
    	$("#s_cellphone").css("color","red");
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
     if(this.value==""){
    	 $("#s_cellphone").css("color","red");
       $("#s_cellphone").html("手机号码不能为空");
     }else if(!validatePhone(this.value)){
    	 $("#s_cellphone").css("color","red");
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
     }else if(data==7){
        alert("请输入邮箱或者电话号码修改");
     }else if(data==10){
    	 alert("登录不能与交易密码相同，请重新输入");
     }
    })
  });

});

</script>
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
var i ;
  $(function(){
  
    $("#send_password").click(function(){
    	
       var param = {};
       param["paramMap.email"] = $("#email").val();
       if(!validateEmail(param["paramMap.email"])){
    	   $("#s_email").html("请输入正确的邮箱");
    	   return;
       }
       //alert(param["paramMap.email"]);
       $.post("forgetpasswordsenEml.mht",param,function(data){
        if(data.mailAddress=='0'){
          $("#s_email").html("邮箱不能为空");
        }else if(data.mailAddress=='1'){
          $("#s_email").html("该邮箱不存在");
        }else{
       	  i=60;
          setTime();
          $("#ok").html("邮件已经发送到你的邮箱,请<a href=http://"+data.mailAddress+"  target='_blank'><font color='red'> 登录</font></a>到你的邮箱验证");
        }
       });
    });
  });

  function setTime(){
	   var email=$("#send_password");
	  if(i>0){
		  email.attr("disabled","true");
		  email.removeClass("red_goon");
		  email.addClass("gray_wait");
		  email.val(i+"秒后重发邮件");
		  i--;
		  setTimeout(setTime,1000);
	  }else{
		  email.removeAttr("disabled");
		  email.removeClass("gray_wait");
          email.addClass("red_goon");
          email.val("重新发送");
		  i=60;
	  }
  }
  
  function  change(i){
	  if(i==1){//邮箱
		  $("#con_one_1").css("display","block");
		  $("#con_one_2").css("display","none");
		  $("#one2").removeClass("hover");
          $("#one1").addClass("hover");
	  }else{//手机
		  $("#con_one_1").css("display","none");
          $("#con_one_2").css("display","block");
          $("#one1").removeClass("hover");
          $("#one2").addClass("hover");
	  }
  }
  
</script>

</body>
</html>
