<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html>
<head  lang="zh-cn">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>三好资本-修改交易密码</title>
<script>
	function send(){
		var type = $("input[name='type']:checked").val();
		if(type==2){
			$("#con_one_2").css("display","block");
		}else{
		   var param = {};
	       param["paramMap.email"] = '${email}';
	       $.post("updatetranpassword.mht",param,function(data){
	        if(data.mailAddress=='0'){
	          $("#mail_ok1").html("邮箱不能为空");
	        }else if(data.mailAddress=='1'){
	          $("#mail_ok1").html("该邮箱不存在");
	        }else if(data.mailAddress==3){
	          $("#mail_ok1").html("邮箱填写错误！");
	        }else{
	          $("#mail_ok1").css("display","block");
	          $("#mail_ok1>div:eq(0)").html("密码找回邮件已经发送到您已绑定的邮箱中,请<a href=http://"+data.mailAddress+" target='_blank'><font color='red'> 登录</font></a>到你的邮箱验证");
	        }
	       });
		}
		$("#step1").css("display","none");
	}
	
	function bind(type){
		if(type==1){//手机
			window.parent.location.href="securityCent.mht?isOn=4";
		}else if(type==2){//邮箱
			window.parent.location.href="securityCent.mht?isOn=5";
		}else{
			$.fancybox.close();
		}
	}
	
</script>

</head>
<body>
    
    <div class="clearfix mt30" id="con_one_2" style="display:none;">
     <div class="pw_info clearfix">
        <div class="cell clearfix">
                <div class="bt01"><span class="red">*</span>手机号码：</div>
                <div class="info mt5">
                    ${mphone}
                </div>
        </div>
        <div class="cell clearfix">
                <div class="bt01"><span class="red">*</span>验证码：</div>
                <div class="info">
                    <input name="input2" type="text" class="text1"  maxlength="4"  id="cellcode1"  />
                    <input type="button" id="codeImg1"   value="发送手机验证码" class="button w140"/>
                    <p style="color: #666;">收不到短信验证码? 点击这里<a onclick="checkCode('codeImg1','vioce')"  style="cursor: pointer;" class="orange" >获取语音验证码</a></p>  
                    <p id="vioce1" style="display: none;"><font color="red">获取语音验证码需要呼叫您的手机，请保持手机畅通，</font>注意接听 400-6000583 的来电。</p>
                    <br/><span style="color:red;margin: 20px 0px;"  id="s_cellphone1"></span>
                </div>
        </div>
   </div>
   <!--发送成功-->
   <div  class="pw_info clearfix" id="pwdDiv1" style="display: none">
        <div class="cell clearfix">
            <div class="bt01">新密码：<span class="fred">*&nbsp;&nbsp;</span></div>
            <div class="info">
                <input type="password" class="input w160"  id="newPassword1"  maxlength="20"/>
                <br/><span id="s_newPassword1" class="txt" style="color: #pcc;">密码长度6-20个字符</span></span>
            </div>
         </div>
         <div class="cell clearfix">
            <div class="bt01">确认新密码：<span class="fred">*&nbsp;&nbsp;</span></div>
            <div class="info">
                <input type="password" class="input w160" id="confirmpassword1"   maxlength="20"/>
                <br/><span style="color: red; margin: 20px 0px;" id="s_tip1"></span>
            </div>
        </div>
        <div class="cell clearfix">
         <div class="bt01" >&nbsp;</div>
            <div class="info">
                <button type="button" class="btn btn-warning mr20 pr40 pl40"   id="bt_yes1">修改</button>
                <button type="button" class="btn btn-inverse pr40 pl40"   onclick="bind(3)">取消</button>
            </div>
        </div>
      </div>
  </div>
    
    <div class="text-center mt50"  style="display: none"   id="mail_ok1">
            <div></div>
            <div class="mt50">
                <button class="button_over w100" onclick="bind(3)">关闭</button>
            </div>
    </div>
    
    
  <form class="form-horizontal"   style="width: 300px;margin: auto;"  id="step1">
      <div>
        <h3 class="title">请选择密码找回方式：</h3>
      </div>
    <div class="control-group">
          <label class="control-label"></label>
          <div class="controls w">
		      <!-- Multiple Radios -->
		      <label class="radio">
		        <s:if test="#request.email==null">
		          <input type="radio" value="1" name="type"   disabled='disabled'  >通过邮箱找回<a href="javascript:bind(2)"   style="color: #F93">(未绑定)</a>
		        </s:if>
		        <s:else>
                    <input type="radio" value="1" name="type"  checked="checked"  >通过邮箱找回<a href="javascript:void(0)"  style="color: #0C9">(已绑定:<span class="red">${memail}</span>)</a>
                </s:else>
		      </label>
        </div>
         <div class="controls">
		      <!-- Multiple Radios -->
		      <label class="radio">
		        <s:if test="#request.phone==null">
		          <input type="radio" value="2" name="type"   disabled='disabled'  >通过手机找回<a href="javascript:bind(1)"   style="color: #F93">(未绑定)</a>
		        </s:if>
		        <s:else>
		          <input type="radio" value="2" name="type"  >通过手机找回<a href="javascript:void(0)"  style="color: #0C9">(已绑定:<span class="red">${mphone}</span>)</a>
		        </s:else>
		      </label>
        </div>
        </div>
    <div class="control-group mt20">
            <button type="button" class="button w100 mr20 "  onclick="send()">确定</button>
            <button type="button"  class="button_over w100" onclick="bind(3)">取消</button>
      </div>

  </form>
<script type="text/javascript" src="script/jquery.cookie.js"></script>
<script type="text/javascript" >



var isCode=0;//标志验证码是否匹配 ，0代表不用修改手机号码，1代表匹配，-1代表不匹配
var isPhone=1;//标志手机号码是否存在，存在1，不存在-1
//ajax验证手机验证码是否正确
function checkRegister(str){
    if(isBlank(str)){
        return;
    }
    if(str=="cellphone"){
        param["paramMap.cellphone"] = $("#cellphone1").val();
    }else{
        param["paramMap.cellcode"] = $("#cellcode1").val();
    }
    $.dispatchPost("ajaxCheckRegister.mht",param,function(data){
        if(data==0){
            $("#s_cellphone1").html("该手机号码不存在,请重新输入!");
            $("#s_cellphone1").css("color","red");
            $("#codeImg1").attr("disabled","true");
        }
        if(data==32){
            $("#s_cellphone1").html("该手机号码存在,请获取验证码!");
            $("#s_cellphone1").css("color","green");
            isPhone=1;
            $("#codeImg1").removeAttr("disabled");
        }
        if(data==33){
            if(isPhone==1){
                $("#s_cellphone1").css("color","green");
                $("#s_cellcode1").html("√  验证码匹配");
                document.getElementById("pwdDiv").style.display="";
                isCode=1;
            }else if(isPhone==-1){
                $("#s_cellphone1").html("该手机号码不存在,请重新输入!");
                $("#s_cellphone1").css("color","red");
            }
        }else if(data==34){
            $("#s_cellphone1").css("color","red");
            $("#s_cellcode1").html("验证码不匹配");
            document.getElementById("pwdDiv1").style.display="none";
            isCode=-1;
        }
        
    });
}

//发送验证码间隔60秒
var j=60;
function timedCount(){
    var codeImg=$("#codeImg1");
    if(j>0){
        codeImg.attr("disabled","true");
        codeImg.removeClass("button");
        codeImg.addClass("button_over");
        codeImg.val(j+"秒后重发短信");
        j--;
        setTimeout(timedCount,1000);
    }else{
        codeImg.removeAttr("disabled");
        codeImg.removeClass("button_over");
        codeImg.addClass("button");
        codeImg.val("重新发送");
        j=60;
    }
}
//ajax发送验证码
function checkCode(x,vioce){
		<%-- var cookieValue = $.cookie("<%=application.getAttribute("basePath")%>+searchDealPwd");
		var options = { expires: 1, domain: false, path: false };
	    if(!isBlank(cookieValue)){
	    	cookieValue = parseInt(cookieValue);
	    	if(cookieValue>3){
	            //$("#s_cellphone").html(" 您今日的交易密码找回短信发送已上限;</br>如需处理请联系客服:${sitemap.servicePhone }");
	            //$("#s_cellphone").css("color","red");
	            //return;
	        }else{
	        	cookieValue = cookieValue+1;
	        	$.cookie("<%=application.getAttribute("basePath")%>+searchDealPwd",cookieValue, options);
	        }
	    }else{
	    	$.cookie("<%=application.getAttribute("basePath")%>+searchDealPwd",1,options);
	    } --%>
		
       var param = {};
       if(x=="codeImg1"){
    	    if($("#codeImg1").attr("disabled") && $("#codeImg1").attr("disabled")=="disabled"){
    	        $("#s_cellphone1").css("color","red");
    	        $("#s_cellphone1").html("您的验证码已发送，请稍候再试...");
    	        setTimeout(function(){
    	            $("#s_cellphone1").html("");
    	        },1000)
    	        return
    	    }
    	    if(vioce){
                param["paramMap.vioce"] = true;
            }
           param["paramMap.userName"] = '';
           param["paramMap.cellphone"] = '${phone}';
           $.post("ajaxSendSMS1.mht",param,function(data){
               if(data=="-1"){
                   alert("验证码发送失败,请重试");
               }else if(data=="-2"){
                   $("#s_cellphone1").css("color","red");
                   $("#s_cellphone1").html("请输入您的手机号码");
               }else if(data=="1"){
                   j=60;
                   timedCount();
                   if(vioce){
                       $("#vioce1").css("display","block");
                       setTimeout(function(){
                           $("#vioce1").css("display","none");
                       },60000)
                   }
               }else if(data=="3"){
            	   $("#s_cellphone1").css("color","red");
                   $("#s_cellphone1").html("对不起，您今日的短信发送量已上限。");
               }
           });
        }
}

$(function(){
    $("#codeImg1").click(function(){
        checkCode('codeImg1');
    });
    
    $("#cellcode1").keyup(function(){
        if(this.value.length==4){//已输入验证码完毕
            param["paramMap.cellphone"] = '${phone}';
            param["paramMap.cellcode"] = $("#cellcode1").val();
            $.post("ajaxCheckRegister.mht",param,function(data){
                if(data==33){
                        $("#s_cellphone1").css("color","green");
                        $("#s_cellphone1").html("√  验证码匹配");
                        $("#pwdDiv1").css("display","block");
                        isCode=1;
                }else if(data==34){
                    $("#s_cellphone1").css("color","red");
                    $("#s_cellphone1").html("验证码不匹配");
                    $("#pwdDiv1").css("display","none");
                    isCode=-1;
                }
            });
        }else{
        	$("#s_cellphone1").html("");
        	$("#pwdDiv1").css("display","none");
            isCode=-1;
        }
        
    });
    
    $("#newPassword1").blur(function(){
     if(isBlank($("#newPassword1").val())){
       $("#s_tip1").html("新密码不能为空");
     }else{
       $("#s_tip1").html("");
     }
   });
   
   $("#confirmpassword1").blur(function(){
      if($("#newPassword1").val()!=$("#confirmpassword1").val()){
        $("#s_tip1").html("两次密码不相同");
      }else{
       $("#s_tip1").html("");
      }
   });

  $("#bt_yes1").click(function(){
	if(!isBlank($("#s_tip1").html())){
		return;
	}
    var param = {};
    param["paramMap.newPassword"] =  $("#newPassword1").val();
    param["paramMap.userId"] = '${session.user.id}';
    param["paramMap.phone"] = 'phone';
    param["paramMap.confirmpassword"] = $("#confirmpassword1").val();
    $.post("updateTrancePassword.mht",param,function(data){
     if(data==1){
     alert("交易密码修改成功");
     $.fancybox.close();
     j=0;
     }else if(data==3){
       $("#s_tip1").html("新密码不能为空");
     }else if(data==4){
    	 $("#s_tip1").html("出错了");
     }else if(data==0){
     $("#s_tip1").html("修改密码失败,请重新填写");
     }else if(data==5){
        $("#s_tip1").html("两次输入密码不相同,请从新输入");
     }else if(data==6){
       $("#s_tip1").html("输入密码长度错误，长度范围为6-20位");
     }else if(data==10){
       $("#s_tip1").html("交易密码不能与登录密码相同，请重新输入");
     }else if(data ==11){
    	 $("#s_tip1").html("操作异常");
     }
    })
  });
})

</script>

</body>
</html>
