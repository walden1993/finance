//llz  验证,tab切换
var type=-1;//用户注册类型1企业用户，2个人用户
var t;
function setTab(king){
	ClearInput();
	if(king=='q'){
		type='1';
		window.location.href="reg.mht?type=1"
	}else{
		type='2';
		window.location.href="reg.mht"
	}
}
function setOrgTab(source){
	ClearInput();
	window.location.href="reg.mht?type=1&source="+source;
}
function setComTab(source){
	ClearInput();
	window.location.href="reg.mht?source="+source;
}
//更改tab时清空提示和值
function ClearInput(){
	clearTimeout(t);
	document.getElementById("codeImg").style.display='inline';
	document.getElementById("codeSpan").innerHTML="";
	i=60;
	
	document.getElementById('email').value="";
	
	document.getElementById('password').value="";
	document.getElementById('confirmPassword').value="";
	//document.getElementById('refferee').value="";
	document.getElementById('orgName').value="";
	document.getElementById('userName').value="";
//	document.getElementById('code').value="";
	document.getElementById('cellphone').value="";
	document.getElementById('cellcode').value="";
	
	document.getElementById('s_email').innerHTML="";
	document.getElementById('s_password').innerHTML="";
	document.getElementById('s_confirmPassword').innerHTML="";
	//document.getElementById('s_refferee').innerHTML="";
	document.getElementById('s_orgName').innerHTML="";
	document.getElementById('s_userName').innerHTML="";
//	document.getElementById('s_code').innerHTML="";
	document.getElementById('s_cellphone').innerHTML="";
	document.getElementById('s_cellcode').innerHTML="";
}



//打开使用条款
function fff(){
	jQuery.jBox.open("iframe:querytips.mht", "使用条款", 600,400,{ buttons: { } });
}

//function ffff(){
	//ClosePop();
//}

//打开隐私条款
 function ffftip(){
	 jQuery.jBox.open("iframe:querytip.mht", "隐私条款", 600,400,{ buttons: { } });
}
 
//初始化
function switchCode(){
	var timenow = new Date();
	$("#codeNum").attr("src","admin/imageCode.mht?pageId=userregister&d="+timenow);
}

//回车登录
document.onkeydown=function(e){
  if(!e)e=window.event;
  if((e.keyCode||e.which)==13){
    register();
  }
}


$(function(){
//样式选中
	$("#zh_hover").attr('class','nav_first');
	$('.tabmain').find('li').click(function(){
		$('.tabmain').find('li').removeClass('on');
		$(this).addClass('on');
		$('.lcmain_l').children('div').hide();
   		$('.lcmain_l').children('div').eq($(this).index()).show();
	})
})

/*//页面input失去焦点时验证
$(document).ready(function(){
	//失去焦点
	$("form :input").blur(function(){
		//email
		if($(this).attr("id")=="email"){
			if(this.value!=""){
				if(!/^([a-zA-Z0-9_-])+((\.(([a-zA-Z0-9_-])+)){0,1})+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/.test(this.value)){
					$("#s_email").attr("class", "formtips onError") 
					$("#s_email").html("邮箱格式错误");
				}else{
					$("#s_email").html("");
					checkRegister('email');
					$("#s_email").attr("class", "formtips") 
				}
			}else{
				$("#s_email").html("");
				checkRegister('email');
				$("#s_email").attr("class", "formtips") 
			}
		}


		//userName
		if($(this).attr("id")=="userName"){
			if(this.value==""){
				$("#s_userName").attr("class", "formtips onError");
				$("#s_userName").html("请输入登录用户名");
			}else if(this.value.length<2){ 
				$("#s_userName").attr("class", "formtips onError");
				$("#s_userName").html(" 用户名长度为2-20个字符"); 
			}else if(!/^[\u4E00-\u9FA5A-Za-z0-9_]+$/.test(this.value)){
				$("#s_userName").attr("class", "formtips onError");
				$("#s_userName").html("用户名不能含有特殊字符"); 
			}else{
				$("#s_userName").html(""); 
				checkRegister('userName');
				$("#s_userName").attr("class", "formtips");
			}
		}
		//企业用户 注册时验证企业全称
		if(type==1){
			//orgName 企业全称
			if($(this).attr("id")=="orgName"){
				if(this.value==""){
					$("#s_orgName").attr("class", "formtips onError");
					$("#s_orgName").html("请输入企业全称");
				}else if(this.value.length<2 || this.value.length>100){ 
					$("#s_orgName").attr("class", "formtips onError");
					$("#s_orgName").html("企业全称长度为2-100个字符"); 
				}else if(!/^[\u4E00-\u9FA5A-Za-z0-9_]+$/.test(this.value)){
					$("#s_orgName").attr("class", "formtips onError");
					$("#s_orgName").html("企业全称不能含有特殊字符"); 
				} else{
					$("#s_orgName").html(""); 
					checkRegister('orgName');
					$("#s_orgName").attr("class", "formtips");
				}
			}
		}

		//手机注册时验证手机号码
		//手机号码
		if($(this).attr("id")=="cellphone"){
			if(this.value==""){
				$("#s_cellphone").attr("class", "formtips onError");
				$("#s_cellphone").html("请输入手机号码");
			}else if((!/^\d{11}$/.test(this.value))){
				$("#s_cellphone").attr("class", "formtips onError");
				$("#s_cellphone").html("手机号码格式错误"); 
			}else{
				$("#s_cellphone").attr("class", "formtips");
				checkRegister('cellphone');
				$("#s_cellphone").html(""); 
			}
		}
		//手机验证码
		if($(this).attr("id")=="cellcode"){
			if($(this).val()==""){
				$("#s_cellcode").attr("class", "formtips onError");
				 document.getElementById("s_cellcode").style.color='red';
				$("#s_cellcode").html("验证码不能为空"); 
			}else if($(this).val()==""){
				$("#s_cellcode").attr("class", "formtips onError");
				 document.getElementById("s_cellcode").style.color='red';
				$("#s_cellcode").html("验证码不能为空"); 
			}else{
				$("#s_cellcode").attr("class", "formtips");
				checkRegister("cellcode");
				$("#s_cellcode").html(""); 
			}
		}
		//password
		if($(this).attr("id")=="password"){
			pwd=this.value; 
			if(this.value==""){
				$("#s_password").attr("class", "formtips onError");
				//alert("请设置您的密码");
				$("#s_password").html("请设置您的密码");  
			}else if(this.value.length<6){ 
				$("#s_password").attr("class", "formtips onError");
				//alert("密码长度为6-20个字符");
				$("#s_password").html("密码长度为6-20个字符"); 
			}else{
				$("#s_password").html(""); 
				checkRegister('password');
				$("#s_password").attr("class", "formtips");
			}
		}
		
		//confirmPassword
		if($(this).attr("id")=="confirmPassword"){
			if(this.value==""){
				$("#s_confirmPassword").attr("class", "formtips onError");
				//alert("请再次输入密码确认");
				$("#s_confirmPassword").html("请再次输入密码确认"); 
			}else if(this.value!=pwd){ 
				$("#s_confirmPassword").attr("class", "formtips onError");
				$("#s_confirmPassword").html("两次输入的密码不一致"); 
			}else{
				$("#s_confirmPassword").attr("class", "formtips");
				$("#s_confirmPassword").html(""); 
			}
		}
		
		//====
		//----add by houli  推荐人 refferee
		if($(this).attr("id")=="refferee"){
			if(this.value!=""){//如果推荐人不为null，则进行判断，判断经纪人是否有效
				$.post("queryValidRecommer.mht",{refferee:this.value},function(data){
					if(data == 1){
						$("#s_refferee11").html("推荐人不存在");
					}else{
						$("#s_refferee11").html("");
					}
				});
			} 
		}


	});

});*/
//发送验证码间隔60秒
var i=60;
function timedCount(){
	var codeImg=$("#codeImg");
    if(i>0){
    	codeImg.attr("disabled","true");
    	codeImg.removeClass("blue_btn");
    	codeImg.addClass("hui_btn");
    	codeImg.text(i+"秒后重发短信");
        i--;
        setTimeout(timedCount,1000);
    }else{
    	codeImg.removeAttr("disabled");
    	codeImg.removeClass("hui_btn");
    	codeImg.addClass("blue_btn");
    	codeImg.text("重新发送");
        i=60;
    }
}

//ajax发送验证码
function checkCode(vioce){
	var param = {};
	if(!$("#cellphone").val()){
		//$(".code-info .red").html("请输入您的手机号码");
		alert("请输入您的手机号码");
		return;
	};
	param["paramMap.userName"] = $("#userName").val();
	param["paramMap.cellphone"] = $("#cellphone").val();
	param["paramMap.key"] = key;
	if(vioce){
		param["paramMap.vioce"] = true;
	}
	
	if($("#codeImg").attr("disabled") && $("#codeImg").attr("disabled")=="disabled"){
		//$(".code-info .msg").html("您的验证码已发送，请稍候再试...");
		alert("您的验证码已发送，请稍候再试...");
		setTimeout(function(){
			$(".code-info .red").html("");
		},1000)
		return
	}
	$.post("sendSMSReg.mht?q"+new Date().getTime(),param,function(data){
		if(data.result=="-1"){
			//$(".code-info .red").html("验证码发送失败,请重试");
			alert("验证码发送失败,请重试");
		}else if(data.result=="-2"){
			//alert("请输入您的手机号码");
			//$("#s_cellphone").html("请输入您的手机号码");
		}else if(data.result=="-3"){
			//alert("您输入的手机号码已存在，请重试");
			//$("#s_cellphone").html("该手机已注册");
		}else if(data.result=="-5"){
			//$(".code-info .red").html("您的验证码发送太过频繁，请稍候...");
			alert("您的验证码已发送，请稍候再试...");
		}else if(data.result=="1"){
			key = data.key;
			timedCount();
			if(vioce){
				$("#vioce").css("display","block");
				setTimeout(function(){
					$("#vioce").css("display","none");
				},60000)
			}
		}else if(data=="3"){
			//$(".code-info .red").html("您今日的验证码发送量已上限");
			alert("您今日的验证码发送量已上限");
		}
	});
}

//ajax验证验证码
function FoutCode(x){
	var param = {};
	param["paramMap.userName"] = $("#userName").val();
	param["paramMap.cellphone"] = $("#cellphone").val();
	$.post("ajaxSendSMS.mht",param,function(data){
		if(data=="-1"){
			alert("验证码发送失败,请重试");
		}else if(data=="-2"){
			$("#s_cellphone").html("请输入您的手机号码");
		}else if(data=="1"){
			document.getElementById("codeImg").style.display='none';
			timedCount();
		}
		
	});
}

//ajax验证用户名密码是否已存在
function checkRegister(str){
	var param = {};
	if(str == "userName"){
		param["paramMap.userName"] = $("#userName").val();
	}else if(str=="orgName"){
		param["paramMap.orgName"] = $("#orgName").val();
	}else if(str=="email"){
		param["paramMap.email"] = $("#email").val();
	}else if(str=="cellphone"){
		param["paramMap.cellphone"] = $("#cellphone").val();
	}else{
		param["paramMap.cellcode"] = $("#cellcode").val();
		param["paramMap.cellphone"] = $("#cellphone").val();
	}
	$.post("ajaxCheckRegister.mht",param,function(data){
		if(data == 3 || data == 4|| data==31 || data==32){
			if(str=="userName"){
				$("#s_userName").html("该用户已存在");
			}else if(str=="orgName"){
				$("#s_orgName").html("该企业名称已存在");
			}else if(str=="cellphone"){
				$("#s_cellphone").html("该手机已注册");
			}else if(str=="email"){
				$("#s_email").html("该邮箱已注册");
			}
		}else if(str=="cellcode"){
			if(data==33){
				document.getElementById("s_cellcode").style.color='green';
				$("#s_cellcode").html("√  验证码匹配");
			}else if(data==34){
				document.getElementById("s_cellcode").style.color='red';
				$("#s_cellcode").html("验证码不匹配");
			}else if(data==35){
				document.getElementById("s_cellcode").style.color='red';
				$("#s_cellcode").html("验证码过期");
			}
			
		}
	});
}

//回车调用注册时验证函数
$("#btn_register").click(function(){
	register();
});

//注册时验证函数
function regg(){
              var falg = true;
              if(falg){
              falg = false;
              var errornum=$("form .onError").length;
             //用于区分是否是企业用户
             type = $("#rgeType").val();
               if(type==1){       
            	    if($("#orgName").val()==""){
		             $("#s_orgName").html("请输入您的企业全称");
		               falg = true;
		               return false;
            	       type=1;
                    }
               }
              
               
		         if($("#cellphone").val()==""){
		            $("#s_cellphone").html("请输入您的手机号码");
		            falg = true;
		            return false;
		         }else if($("#cellcode").val()==""){
			           document.getElementById("s_cellcode").style.color='red';
			           $("#s_cellcode").html("请输入手机验证码");
			           falg = true;
			           return false;
		         }
//		         else if(king=='qiyeImg'){
//		         	if($("#orgName").val()==""){
//			           $("#s_orgName").html("请输入企业全称");
//			           falg = true;
//			           return false;
//			         }
//		         }
		         else{
		         	 if($("#userName").val()==""){
			           $("#s_userName").html("请输入登录用户名");
			           falg = true;
			           return false;
			         }else if($("#password").val()==""){
			         $("#s_password").html("请设置您的密码"); 
			         falg = true;
			         return false;
			         }
			         else if($("#confirmPassword").val()==""){
			           $("#s_confirmPassword").html("请再次输入密码确认"); 
			           falg = true;
			           return false;
			         }
//			         else if($("#code").val()==""){
//			         $("#s_code").html("请输入验证码"); 
//			         falg = true;
//			         return false;
//			        }
			         else if(errornum > 0){   
			        	alert('请正确填写注册信息');
			        	falg = true;
			            return false;
		            }else{
			           if(!$("#agre2").attr("checked")){
			            alert("请勾选我已阅读并同意《使用条款》和《隐私条款》");
			            falg = true;
			           return false;
		           }
		           }
		         
		         }
		         var param = {};
		         param["paramMap.pageId"] = "userregister";
		         param["paramMap.email"] = $("#email").val();
		         param["paramMap.userName"] = $("#userName").val();
		         param["paramMap.orgName"] = $("#orgName").val();
		         param["paramMap.cellphone"] = $("#cellphone").val();
		         param["paramMap.cellcode"] = $("#cellcode").val();
		         param["paramMap.type"] = type;
		         param["paramMap.password"] = $("#password").val();
		         param["paramMap.confirmPassword"] = $("#confirmPassword").val();
		         param["paramMap.refferee"] = $("#refferee").val();
		         param["paramMap.code"] = $("#code").val();
		         param["paramMap.param"] = $("#param").val();
		         param["paramMap.source"] = $("#source").val();
		         $.post("registerOne.mht",param,function(data){
		           if(data.mailAddress=='0'){
		             $("#s_code").html("验证码输入有误，请重新输入");
		              $('#btn_register').attr('value','免费注册');
		              falg = true;
		             switchCode();
		           }else if(data.mailAddress=='1'){
		            $("#s_confirmPassword").html("两次输入的密码不一致"); 
		            falg = true;
		             switchCode();
		           }else if(data.mailAddress=='2'){
		            $("#s_userName").html("该用户已存在");
		            falg = true;
		             switchCode();
		           }else if(data.mailAddress=='3'){
		            $("#s_email").html("该邮箱已注册");
		            falg = true;
		             switchCode();
		           }else if(data.mailAddress=='52'){
		            $("#s_cellphone").html("该手机号码已注册");
		            falg = true;
		             switchCode();
		           }else if(data.mailAddress=='51'){
		            $("#s_orgName").html("该企业全称已注册");
		            falg = true;
		             switchCode();
		           }else if(data.mailAddress=='4'){
		            alert("注册失败！");
		             falg = true;
		             switchCode();
		           }else if(data.mailAddress=='5'){
		            falg = true;
		           	alert("推荐人填写错误或者该推荐人不存在！");
		           	switchCode();
		           }
		           else if(data.mailAddress=='13'){
		            falg = true;
		              $("#s_userName").html("请输入用户名");
		               switchCode();
		           }else if(data.mailAddress=='54'){
		            falg = true;
		             $("#s_cellphone").html("请输入您的手机号码");
		              switchCode();
		           }else if(data.mailAddress=='55'){
		            falg = true;
		              $("#s_orgName").html("请输入企业全称");
		               switchCode();
		           }else if(data.mailAddress=='56'){
		            falg = true;
		              $("#s_orgName").html("企业全称长度为2-20个字符");
		               switchCode();
		           }else if(data.mailAddress=='57'){
		            falg = true;
		              $("#s_orgName").html("企业全称不能含有特殊字符");
		               switchCode();
		           }else if(data.mailAddress=='58'){
		            falg = true;
		              $("#s_orgName").html("企业全称不能以下划线开头");
		               switchCode();
		           }else if(data.mailAddress=='14'){
		              $("#s_password").html("请设置您的密码"); 
		              falg = true;
		               switchCode();
		           }else if(data.mailAddress=='15'){
		             $("#s_confirmPassword").html("请再次输入密码确认"); 
		             falg = true;
		              switchCode();
		           }else if(data.mailAddress=='16'){
		            falg = true;
		            alert("注册失败");
		             switchCode();
		           }else if(data.mailAddress=='18'){
		            $("#s_userName").html("用户名长度为2-20个字符"); 
		            falg = true;
		             switchCode();
		           }else if(data.mailAddress=='20'){
		           $("#s_userName").html("用户名不能含有特殊字符"); 
		            falg = true;
		             switchCode();
		           }else if(data.mailAddress=='21'){
		            $("#s_userName").html("用户名第一个字符不能是下划线"); 
		            falg = true;
		             switchCode();
		           }else if(data.mailAddress=='123'){//邮箱发送失败
		            alert("邮件发送失败，请联系客服");
		               falg = true;
		             switchCode();
		           }else if(data.mailAddress=='61'){
		        	   alert("验证码错误，请重试");
			               falg = true;
			             switchCode();
		           }else if(data.mailAddress=='60'){
		        	   alert("验证码输入错误");
			               falg = true;
			             switchCode();
		           }else if(data.mailAddress=='70'){
		        	   alert("验证码不正确");
			               falg = true;
			             switchCode();
		           }else if(data.mailAddress=='71'){
		        	   alert("您当前的手机号码和获取验证码的号码不一致，请重新获取验证码");
		        	   document.getElementById("s_cellcode").style.color='red';
		        	   $("#s_cellcode").html("您当前的手机号码和获取验证码的号码不一致，请重新获取验证码");
		        	   document.getElementById("codeImg").style.display='inline';
		        	   document.getElementById("codeSpan").innerHTML="";
		        	   i=60;
		        	   falg = true;
		        	   switchCode();
		           }else if(data.mailAddress=='99'){
				           window.location.href="registerTwoInit.mht";
//		        	   if(type=='e'){
//		         	  //测试--取消邮箱验证 
//				          alert("注册成功e");
//				          window.location.href="login.mht";
//		        	   }else{
//		        		  alert("注册成功p");
//				          window.location.href="index.jsp";
//		        	   }
			       }else if(data.mailAddress=='-500'){
             		alert("您的用户名中包含敏感字");
//             		$('#btn_register').attr('value','免费注册');
               		falg = true;
             		switchCode();
           		   }
			       else if(data.mailAddress=='69'){
	             		alert("用户名不能是纯数字");
//	             		$('#btn_register').attr('value','免费注册');
	               		falg = true;
	             		switchCode();
	           		   }else{
			    	   alert("注册失败，请联系客服");
//           				if(type=='e'){
//  		         	  //测试--取消邮箱验证 
//  				          alert("注册成功，请登录邮箱激活用户");
//  				          window.location.href="msgtip.mht";
//  		        	   }else{
//  		        		  alert("注册成功,请登录");
//  				          window.location.href="login.mht";
//  		        	   }
		           }
		         });
		       }
}

//注册时验证函数
function register(){
              var falg = true;
              if(falg){
              falg = false;
              var errornum=$("form .onError").length;
             //用于区分是否是企业用户
             type = $("#rgeType").val();
               if(type==1){       
            	    if($("#orgName").val()==""){
		             $("#s_orgName").html("请输入您的企业全称");
		               falg = true;
		               return false;
            	       type=1;
                    }
               }
              
               
		         if($("#cellphone").val()==""){
		            $("#s_cellphone").html("请输入您的手机号码");
		            falg = true;
		            return false;
		         }else if($("#cellcode").val()==""){
			           document.getElementById("s_cellcode").style.color='red';
			           $("#s_cellcode").html("请输入手机验证码");
			           falg = true;
			           return false;
		         }
//		         else if(king=='qiyeImg'){
//		         	if($("#orgName").val()==""){
//			           $("#s_orgName").html("请输入企业全称");
//			           falg = true;
//			           return false;
//			         }
//		         }
		         else{
		         	 if($("#userName").val()==""){
			           $("#s_userName").html("请输入登录用户名");
			           falg = true;
			           return false;
			         }else if($("#password").val()==""){
			         $("#s_password").html("请设置您的密码"); 
			         falg = true;
			         return false;
			         }
			         else if($("#confirmPassword").val()==""){
			           $("#s_confirmPassword").html("请再次输入密码确认"); 
			           falg = true;
			           return false;
			         }
//			         else if($("#code").val()==""){
//			         $("#s_code").html("请输入验证码"); 
//			         falg = true;
//			         return false;
//			        }
			         else if(errornum > 0){   
			        	alert('请正确填写注册信息');
			        	falg = true;
			            return false;
		            }else{
			           if(!$("#agre2").attr("checked")){
			            alert("请勾选我已阅读并同意《使用条款》和《隐私条款》");
			            falg = true;
			           return false;
		           }
		           }
		         
		         }
		         var param = {};
		         param["paramMap.pageId"] = "userregister";
		         param["paramMap.email"] = $("#email").val();
		         param["paramMap.userName"] = $("#userName").val();
		         param["paramMap.orgName"] = $("#orgName").val();
		         param["paramMap.cellphone"] = $("#cellphone").val();
		         param["paramMap.cellcode"] = $("#cellcode").val();
		         param["paramMap.type"] = type;
		         param["paramMap.password"] = $("#password").val();
		         param["paramMap.confirmPassword"] = $("#confirmPassword").val();
		         param["paramMap.refferee"] = $("#refferee").val();
		         param["paramMap.code"] = $("#code").val();
		         param["paramMap.param"] = $("#param").val();
		         param["paramMap.source"] = $("#source").val();
		         $.post("registerOne.mht",param,function(data){
		           if(data.mailAddress=='0'){
		             $("#s_code").html("验证码输入有误，请重新输入");
		              $('#btn_register').attr('value','免费注册');
		              falg = true;
		             switchCode();
		           }else if(data.mailAddress=='1'){
		            $("#s_confirmPassword").html("两次输入的密码不一致"); 
		            falg = true;
		             switchCode();
		           }else if(data.mailAddress=='2'){
		            $("#s_userName").html("该用户已存在");
		            falg = true;
		             switchCode();
		           }else if(data.mailAddress=='3'){
		            $("#s_email").html("该邮箱已注册");
		            falg = true;
		             switchCode();
		           }else if(data.mailAddress=='52'){
		            $("#s_cellphone").html("该手机号码已注册");
		            falg = true;
		             switchCode();
		           }else if(data.mailAddress=='51'){
		            $("#s_orgName").html("该企业全称已注册");
		            falg = true;
		             switchCode();
		           }else if(data.mailAddress=='4'){
		            alert("注册失败！");
		             falg = true;
		             switchCode();
		           }else if(data.mailAddress=='5'){
		            falg = true;
		           	alert("推荐人填写错误或者该推荐人不存在！");
		           	switchCode();
		           }
		           else if(data.mailAddress=='13'){
		            falg = true;
		              $("#s_userName").html("请输入用户名");
		               switchCode();
		           }else if(data.mailAddress=='54'){
		            falg = true;
		             $("#s_cellphone").html("请输入您的手机号码");
		              switchCode();
		           }else if(data.mailAddress=='55'){
		            falg = true;
		              $("#s_orgName").html("请输入企业全称");
		               switchCode();
		           }else if(data.mailAddress=='56'){
		            falg = true;
		              $("#s_orgName").html("企业全称长度为2-20个字符");
		               switchCode();
		           }else if(data.mailAddress=='57'){
		            falg = true;
		              $("#s_orgName").html("企业全称不能含有特殊字符");
		               switchCode();
		           }else if(data.mailAddress=='58'){
		            falg = true;
		              $("#s_orgName").html("企业全称不能以下划线开头");
		               switchCode();
		           }else if(data.mailAddress=='14'){
		              $("#s_password").html("请设置您的密码"); 
		              falg = true;
		               switchCode();
		           }else if(data.mailAddress=='15'){
		             $("#s_confirmPassword").html("请再次输入密码确认"); 
		             falg = true;
		              switchCode();
		           }else if(data.mailAddress=='16'){
		            falg = true;
		            alert("注册失败");
		             switchCode();
		           }else if(data.mailAddress=='18'){
		            $("#s_userName").html("用户名长度为2-20个字符"); 
		            falg = true;
		             switchCode();
		           }else if(data.mailAddress=='20'){
		           $("#s_userName").html("用户名不能含有特殊字符"); 
		            falg = true;
		             switchCode();
		           }else if(data.mailAddress=='21'){
		            $("#s_userName").html("用户名第一个字符不能是下划线"); 
		            falg = true;
		             switchCode();
		           }else if(data.mailAddress=='123'){//邮箱发送失败
		            alert("邮件发送失败，请联系客服");
		               falg = true;
		             switchCode();
		           }else if(data.mailAddress=='61'){
		        	   alert("验证码错误，请重试");
			               falg = true;
			             switchCode();
		           }else if(data.mailAddress=='60'){
		        	   alert("验证码输入错误");
			               falg = true;
			             switchCode();
		           }else if(data.mailAddress=='70'){
		        	   alert("验证码不正确");
			               falg = true;
			             switchCode();
		           }else if(data.mailAddress=='71'){
		        	   alert("您当前的手机号码和获取验证码的号码不一致，请重新获取验证码");
		        	   document.getElementById("s_cellcode").style.color='red';
		        	   $("#s_cellcode").html("您当前的手机号码和获取验证码的号码不一致，请重新获取验证码");
		        	   document.getElementById("codeImg").style.display='inline';
		        	   document.getElementById("codeSpan").innerHTML="";
		        	   i=60;
		        	   falg = true;
		        	   switchCode();
		           }else if(data.mailAddress=='99'){
		        	   registerTwoBtn();
//		        	   if(type=='e'){
//		         	  //测试--取消邮箱验证 
//				          alert("注册成功e");
//				          window.location.href="login.mht";
//		        	   }else{
//		        		  alert("注册成功p");
//				          window.location.href="index.jsp";
//		        	   }
			       }else if(data.mailAddress=='-500'){
             		alert("您的用户名中包含敏感字");
//             		$('#btn_register').attr('value','免费注册');
               		falg = true;
             		switchCode();
           		   }
			       else if(data.mailAddress=='69'){
	             		alert("用户名不能是纯数字");
//	             		$('#btn_register').attr('value','免费注册');
	               		falg = true;
	             		switchCode();
	           		   }else{
			    	   alert("注册失败，请联系客服");
//           				if(type=='e'){
//  		         	  //测试--取消邮箱验证 
//  				          alert("注册成功，请登录邮箱激活用户");
//  				          window.location.href="msgtip.mht";
//  		        	   }else{
//  		        		  alert("注册成功,请登录");
//  				          window.location.href="login.mht";
//  		        	   }
		           }
		         });
		       }
}
function registerTwoBtn(){
    //var falg = true;
    //if(falg){
       //falg = false;
            
        /*if($("#tradPassWord").val()==""){
                  $("#s_tradPassWord").html("请输入您的交易密码");
                  falg = true;
                  return false;
        }
        
         if($("#reTradPassWord").val()==""){
                  $("#s_reTradPassWord").html("请输入您的确认交易密码");
                  falg = true;
                  return false;
        }
         
         if($("#tradPassWord").val()!=$("#reTradPassWord").val()){
                  $("#s_reTradPassWord").html("交易密码和确认交易密码不一致");
                  falg = true;
                  return false;
        }*/
//        if(!$("#agre2").attr("checked")){
//           alert("请勾选我已阅读并同意《使用条款》和《隐私条款》");
//           falg = true;
//          return false;
//        }
         
           var param = {};
               param["paramMap.tradPassWord"] = $("#tradPassWord").val();
               param["paramMap.reTradPassWord"] = $("#reTradPassWord").val();
               
          $.post("registerTwo.mht",param,function(data){
               if(data.mailAddress=='1'){
                   $("#s_tradPassWord").html("交易密码不能为空");             
                    falg = true;
                 
                 }else if(data.mailAddress=='2'){            
                   $("#s_reTradPassWord").html("确认交易密码不能为空"); 
                   falg = true;
                 
                 }else if(data.mailAddress=='3'){            
                   $("#s_reTradPassWord").html("交易密码和确认交易密码不一致"); 
                   falg = true;
                 
                 }else if(data.mailAddress=='4'){            
                   $("#s_reTradPassWord").html("注册失败"); 
                   falg = true;
                 
                 }else if(data.mailAddress=='5'){            
                   $("#s_reTradPassWord").html("交易密码不能和登录密码一致"); 
                   falg = true;
                 
                 }else if(data.mailAddress=='注册成功'){             
                   
                   falg = true;
                   if(data.isMail==1){
                       window.location.href="registerThirdInit.mht?mail="+data.mail+"&isMail="+data.isMail;
                   }else{
                       window.location.href="registerThirdInit.mht?isMail="+data.isMail;
                   }
                   
                 }
              
          });
            
   //}
  
}