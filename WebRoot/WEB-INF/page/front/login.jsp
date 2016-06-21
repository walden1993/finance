<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
  <head>
    <c:set value="用户登录" scope="request" var="title"/>
	<jsp:include page="/include/head.jsp" />
	<link rel="stylesheet" type="text/css" href="css/login.css">
	<script src='js/uaredirect.js?v=1' type="text/javascript" ></script>
	<script type="text/javascript">uaredirect("www/index.html#/user/login");</script>
</head>
<body>
<!-- 引用头部公共部分 -->
<jsp:include page="/include/top.jsp"></jsp:include>


<div class="wrap"  style="margin:50px 0;background-color:#FFF;">
	<div class="w950" style="max-height:350px;">
    	<div ><img src="images/log_pic.png" height="340px;" /></div>
        <div class="login">
        	<form action="logining.mht" method="post">
            	<div class="username mb15">
                	<input  type="text"  id="email" tabindex="1" autocomplete="off" placeholder="请输入您的用户名/手机号/邮箱" name="paramMap.email" />
                </div>
                <div class="email-info mb10 ml10 hidden">
                	<span class="ico"></span><span class="msg">用户名/邮箱/手机号不能为空</span>
                </div>
                <div class="password mb15">
                	<input type="password" id="password"  autocomplete="off"  tabindex="2"   name="paramMap.password"  placeholder="请输入登录密码" />
                </div>
                <div class="mb10 ml10">
                	<div class="r"><a href="forgetpassword.mht">忘记密码？</a></div>
                	<div class="password-info mt10 mb10 hidden"><span class="ico"></span><span class="msg"></span></div>
                </div>
                <div class="clearfix"></div>
                <div class="code mb15">
                	<input type="text" tabindex="3"  autocomplete="off"  id="code" placeholder="请输入验证码" maxlength=4 />
                    <img src="imageCode.mht?pageId=userlogin&time=<%=new java.util.Date().getTime() %>" title="点击更换验证码" style="cursor: pointer;" id="codeNum" width="76" height="44" onclick="javascript:switchCode()">
                    <span onclick="switchCode();" style="height:44px; text-align:center; margin-left:8px; font-size:15px; color:#999; cursor:pointer;">换一换？</span>
               	</div>
                
                <div class="code-info mb10 ml10 hidden">
                	<span class="ico"></span><span class="msg">验证码输入错误</span>
                </div>
                
                <div class="submit mt20">
                	<button class="login_button" onclick="login();" type="button" tabindex="4">登录</button>
               	 </div>
                 
                 <div class="text-center mt10 mb20">
                 	<span>还没有三好资本账号？<a href="reg.mht" class="blue">立即注册</a></span>
                 </div>
                 
                 <div class="bottom clearfix">
                    <span>您还可以使用合作账号登录</span>
                    <a id="partner-qq" class="partner" href="qqlogin.mht">腾讯账号</a>
                    <a id="partner-weibo" class="partner" href="https://api.weibo.com/oauth2/authorize?client_id=3447802665&amp;redirect_uri=https%3A%2F%2Fwww.sanhaodai.com%2FafterLoginRedirect.mht&forcelogin=true">新浪微博</a>
              	 </div>
            </form>
        </div>
    </div>
</div>

<%-- <div class="ny_content clearfix">
<div class="reg_matter clearfix">
  <div class="fl reg_con_info">
  <div class="news_log_tit" ><h3 >会员登录</h3></div>
  <form action="logining.mht" method="post">
  <div class="reg_con_info clearfix mt30" id="con_one_1"></div>
  <div class="reg_con_info clearfix mt30" id="con_one_2" style="display:none;">
      <div class="cell clearfix">
                <div class="bt01"><span class="red">*</span>企业全称：</div>
                <div class="info">
                    <input name="input2" type="text" class="text" />
                    <span class="success_tip"></span>
                </div>
      </div>
   </div>
   <div class="reg_con_info">
            <div class="cell clearfix">
                <div class="bt01"><span class="red">*</span>邮箱/手机/账号：</div>
                <div class="info">
                    <input name="paramMap.email" tabindex="4" type="text" class="text" id="email"/>
                    <span id="email_" style="display: none;"><a id="retake_email"  href="javascript:reSendEmail();">发送激活邮件</a>
					</span>
					<br/><span style="color: red;" id="s_email" class="formtips"></span>
                </div>
      		</div>
             <div class="cell clearfix">
                 <div class="bt01"><span class="red">*</span>密码：</div>
                 <div class="info">
                    <input type="password" tabindex="5" name="paramMap.password" class="text" id="password" />&nbsp;&nbsp;<a  href="forgetpassword.mht" style="color: blue;font-size:12px;">忘记密码?</a>
                 	<br/><span style="color: red;" id="s_password" class="formtips"></span>
                 </div>
             </div> 
             <div class="cell clearfix">
                 <div class="bt01"><span class="red">*</span>验证码：</div>
                 <div class="info">
                    <input name="paramMap.code" tabindex="6" class="text2" id="code"/>&nbsp;<span class="ml5"><img src="imageCode.mht?pageId=userlogin&time=<%=new java.util.Date().getTime() %>" title="点击更换验证码"
											style="cursor: pointer;" id="codeNum" width="46" height="18"
					onclick="javascript:switchCode()" /></span>&nbsp;&nbsp;&nbsp;<span><a href="javascript:void()" onclick="switchCode()" style="color: blue;">换一换?</a></span>
             		<br/><span style="color: red;" id="s_code" class="formtips"></span>
             	</div>
             </div>            
             <div class="cell clearfix">
                  <div class="bt01">&nbsp;</div>
                  <div class="info f14">
                    <p class="pt10">还没加入三好资本？<a href="reg.mht" class="reg_blue ml5 mr5">立即注册</a></p>
                    <p class="pt20"><input type="button" tabindex="7" class="log_btn" value="登 录" id="btn_login"/></p>
               </div>
             </div>
    </div>
    </form>
    <div class="thirdlogin">
	    <div class="pd50">
	      <h4>用第三方帐号直接登录</h4>
	      <ul>
	        <li id="sinal"><a href="https://api.weibo.com/oauth2/authorize?client_id=3447802665&amp;response_type=code&amp;redirect_uri=https%3A%2F%2Fwww.hrjr.com%2Fsp2p_web%2FafterLoginRedirect.mht">微博账号登录</a></li>
	        <li id="qql"><a href="qqlogin.mht">QQ账号登录</a></li>
	        <div class="clear"></div>
	      </ul>
	      <div class="clear"></div>
	    </div>
  	</div>
 </div>
 <div class="fr log_right_pic"></div>  
</div></div> --%>
<!-- 引用底部公共部分 -->     
<jsp:include page="/include/footer.jsp"></jsp:include>
<script src='js/emailauto.js?v=1' type="text/javascript" ></script>

<script>
$(function(){
	/* if(isMobile()){
		window.location.href="www/index.html#/user/login";
	} */

	$.AutoComplete('#email');

	$("body").bind('keyup', function(event){
			   if (event.keyCode=="13"){
			      login();  
			   }
			});	
	});
	
		//初始化
		function switchCode(){
			var timenow = new Date();
			$("#codeNum").attr("src","admin/imageCode.mht?pageId=userlogin&d="+timenow);
		}
		/* $(document).ready(function(){
		//===========input失去焦点
		     $("form :input").blur(function(){
		     	 var id = "."+$(this).attr("id")+"-info";
		       //email
                 if($(this).attr("id")=="email"){ 
		            if(this.value==""){   
		                $(id).removeClass("hidden") ;
		                $(".email-info .msg").html("*请输入用户名/手机号/邮箱");
		                return;
		            }else if(/^([a-zA-Z0-9_-])+((\.(([a-zA-Z0-9_-])+)){0,1})+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/.test(this.value)){ //判断用户输入的是否是邮箱地址
		            	checkRegister('email',id);
		            }else if((/^1[3,5,8]\d{9}$/.test(this.value))){
		                checkRegister('cellphone',id);
		            }else{ 
		            	checkRegister('userName',id);
		            }   
		            $(id).addClass("hidden");
	        	}  
		     //password
		   	   if($(this).attr("id")=="password"){
			     if(this.value==""){
			      	$(id).removeClass("hidden") ;
			        $(".password-info .msg").html("*请輸入您的密码");  
			     }else if(this.value.length<6){ 
			      	$(id).removeClass("hidden");
			      	$(".password-info .msg").html("*密码长度为6-20个字符"); 
			     }else{
			     	$(id).addClass("hidden");
			     }
		   	  }
		   
		   	  //验证码
		   	   if($(this).attr("id")=="code"){
				     if(this.value==""){
				     	$(id).removeClass("hidden");
				      	$(".code-info .msg").html("*请输入验证码"); 
				     }else{   
		        		$(id).addClass("hidden");
		            } 
		   		}
		       
		      }).focus(function(){ 
		      	 var id = "."+$(this).attr("id")+"-info";
		      	 if($(id).css("display")!='none')
		      	 $(id).addClass("hidden");
    		});
    	}) */
		
		
		//===验证数据
       function checkRegister(str,id){
          var param = {};
          	if(str == "userName"){
				param["paramMap.email"]  = "";
				param["paramMap.userName"] = $("#email").val();
			}else if(str=="email"){
				param["paramMap.email"] = $("#email").val();
				param["paramMap.userName"] = "";
			}else{
				param["paramMap.email"] = "";
				param["paramMap.userName"] = "";
				param["paramMap.cellphone"] = $("#email").val();
			}
			$.post("ajaxCheckLog.mht",param,function(data){
	              if(data == 2 && str == "userName"){
	              	 $(id).removeClass("hidden");
	                 $(".email-info .msg").html("*无效用户！");
	              }else if(data == 3 && str == "userName"){
	              	 $(id).removeClass("hidden");
	                 $(".email-info .msg").html("*该用户还没激活！");
	              }else if(data == 4&& str == "userName"){
	              	 $(id).addClass("hidden");
	              }
	              if(data == 0 && str == "email"){
	              	 $(id).removeClass("hidden");
	                 $(".email-info .msg").html("*无效邮箱！");
	              }else if(data == 1 && str == "email"){
	              	 $(id).removeClass("hidden");
	                 $(".email-info .msg").html("*该邮箱用户还没激活！");
	              }else if(data == 4&& str == "email"){
	              	$(id).addClass("hidden");
	              } 
		          if(data == 5 && str == "cellphone"){
		          	 $(id).removeClass("hidden");
	                 $(".email-info .msg").html("*用户不存在！");
	              }else if(data == 4 && str == "cellphone"){
	                $(id).addClass("hidden");
	              }
			});
       }
       
       
       
       
</script>
<script>

function login(){
             
   	  if($("#email").val()==""){
   	  	  $(".password-info").removeClass("hidden");
   		  $(".password-info .msg").html("*请输入用户名/手机号/邮箱");   
          return;
       }
      if($("#password").val()==""){   
     		 $(".password-info").removeClass("hidden");
     		 $(".password-info .msg").html("*请輸入您的密码");   
      		return;
       }
       if($("#code").val()==""){
       	     $(".password-info").removeClass("hidden");
       	     $(".password-info .msg").html("*请输入验证码");      
      		return;
       }  
    $('.login_button').text('登录中...');
    $(".login_button").attr('disabled',true);
    
    var param = {};
	param["paramMap.pageId"] = "userlogin";
	param["paramMap.email"] = $("#email").val();
	param["paramMap.password"] = $("#password").val();
	param["paramMap.code"] = $("#code").val();		
	param["paramMap.afterLoginUrl"]="${session.afterLoginUrl}";
       $.post("logining.mht",param,function(data){
       	$('.login_button').text('登录');
       	$(".login_button").attr("disabled",false); 
      	if(data == 1){
   			window.location.href='${afterLoginUrl}' || 'home.mht';
   			return;
		}else if (data == 2) {
			/* $(".code-info").removeClass("hidden");
			$(".code-info .msg").html("*验证码错误！"); */
			$(".password-info").removeClass("hidden");
   		    $(".password-info .msg").html("*验证码错误！");
		} else if (data == 3) {
			/* $(".email-info").removeClass("hidden");
			$(".email-info .msg").html("*用户名或密码错误！");  */
			$(".password-info").removeClass("hidden");
   		    $(".password-info .msg").html("*用户名或密码错误！");
		} else if (data == 4) {
			/* $(".email-info").removeClass("hidden");
			$(".email-info .msg").html("*该用户已被禁用！");*/
			$(".password-info").removeClass("hidden");
   		    $(".password-info .msg").html("*该用户已被禁用！");
		}else if (data == 8) {
			//$(".email-info").removeClass("hidden");
			//$(".email-info .msg").html("*用户名或密码错误!还有2次机会,<br/>您可以尝试<a style='color:#FC9320;font-weight:bold;text-decoration: underline;' href ='forgetpassword.mht'>找回密码</a>！"); 
			$(".password-info").removeClass("hidden");
   		    $(".password-info .msg").html("*用户名或密码错误!还有2次机会,<br/>您可以尝试<a style='color:#FC9320;font-weight:bold;text-decoration: underline;' href ='forgetpassword.mht'>找回密码</a>！");
		}else if (data == 9) {
			//$(".email-info").removeClass("hidden");
            //$(".email-info .msg").html("*用户名或密码错误!还有1次机会,<br/>您可以尝试<a style='color:#FC9320;font-weight:bold;text-decoration: underline;' href ='forgetpassword.mht'>找回密码</a>！"); 
			$(".email-info").removeClass("hidden");
			$(".email-info .msg").html("*用户名或密码错误!还有1次机会,<br/>您可以尝试<a style='color:#FC9320;font-weight:bold;text-decoration: underline;' href ='forgetpassword.mht'>找回密码</a>！"); 
		}else if (data == 5) {
			/* $(".email-info").removeClass("hidden");
            $(".email-info .msg").html("*该用户已被限制登录，<br/>请于三小时以后登录！");  */
            $(".password-info").removeClass("hidden");
   		    $(".password-info .msg").html("*该用户已被限制登录，<br/>请于三小时以后登录！");
		} else if (data == 7) {
			/* $(".email-info").removeClass("hidden");
            $(".email-info .msg").html("*该用户账号出现异常，请速与管理员联系！"); */ 
            $(".password-info").removeClass("hidden");
   		    $(".password-info .msg").html("*该用户已被限制登录，<br/>请于三小时以后登录！");
		}
		switchCode();
      });

}

function reSendEmail(){
   if($("#email").val()==""){
     alert("请输入邮箱");
     return;
   }
   window.location.href = "reActivateEmail.mht?email="+$("#email").val();
}


</script>
</body>
</html>
