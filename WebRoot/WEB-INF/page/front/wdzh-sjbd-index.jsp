<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>三好资本</title>
	<jsp:include page="/include/head.jsp"></jsp:include>
    <link href="css/inside.css"  rel="stylesheet" type="text/css" />
    <link id="skin" rel="stylesheet" href="css/jbox/Gray/jbox.css" />
    <script language="javascript" type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
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
							<li onclick="jumpUrl('updatePasswordAndDealpwd.mht');">
								修改密码
							</li>
							<li id="li_bp"   class="on" onclick="bindingPhoneLoad('boundcellphone.mht');">
								更换手机
							</li>
							<li  onclick="jumpUrl('szform.mht');">
								通知设置
							</li>
							<li id="li_tx"  onclick="loadBankInfo('yhbound.mht');">
								银行卡设置
							</li>
							<!--
							<s:if test="#request.isApplyPro==1">
							<li onclick="jumpUrl('queryQuestion.mht'); ">
								申请密保设置
							</li> 
							</s:if>
							<s:if test="#request.isApplyPro==2">
							<li onclick="jump('answerPwdQuestion.mht'); ">
								修改密保设置
							</li>
							</s:if>
							 -->
        </ul>
     </div>
     
      <div class="box">
        <div class="boxmain2" style="padding-top:10px;">
         <div class="biaoge2" style="margin-top:0px;">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <th colspan="2" align="left" style="padding-top:0px;"> 手机绑定</th>
    </tr>
  <tr>
    <td width="18%" align="right" id="num">手机号码：</td>
    <td width="83%"> <span id="mobile"></span>
      <a id="clickCode_" class="yzmbtn" href="javascript:void(0);" >发送手机验证码</a></td>
  </tr>
	
  <tr>
    <td align="right">&nbsp;</td>
    <td style="padding-top:10px;"><a id="p_b" href="javascript:void(0);" class="bcbtn" onclick="javascript:addBindingMobile();">手机绑定</a></td>
  </tr>
  <tr>
    <td colspan="2"><span style="color: red; float: none;" id="mobile_tip" class="formtips"></span></td>
  </tr>
    </table>

    </div>
    
    <div id="mobileChange" class="biaoge2" style="margin-top:0px; display:none;" >
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <th colspan="2" align="left" style="padding-top:0px;"> 手机变更</th>
    </tr>
  <tr>
    <td width="18%" align="right" >手机号码：</td>
    <td width="83%"><input type="text" class="inp188" id="mobile2"/>
      <a id="reClickCode_" class="yzmbtn" href="javascript:void(0);">发送手机验证码</a></td>
  </tr>
  <tr>
    <td align="right">验证码：</td>
    <td><input type="text" class="inp188" id="code2"/>      <span class="txt">输入您获取的手机验证码</span></td>
    
    <s:if test="#session.DEMO==1">
  	<tr>
  	<td>&nbsp;</td>
	  	<td ><span style="color:red;font-size: 12px">* 演示站点不发送短信</span></td>
		</tr>
	</s:if>
    
  </tr>
  <tr>
    <td align="right">变更原因：</td>
    <td><textarea class="txt420" id="content2"></textarea></td>
  </tr>
  <tr>
    <td align="right">&nbsp;</td>
    <td style="padding-top:10px;"><a href="javascript:addChangeBindingMobile();" class="bcbtn" >手机变更</a></td>
  </tr>
  <tr>
    <td colspan="2"><span style="color: red; float: none;" id="mobile2_tip" class="formtips"></span></td>
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
  function jumpUrl(obj){
          window.location.href=obj;
       }
  function jump(url) {
		
		alert("请先回答密保问题！");
		window.location.href = url;
}
  
</script>
	<script>
	   $(function(){
		   dqzt(4);
					 //$('#li_5').addClass('on');
					 //displayDetail('3');
					 $('#bankCardNew').addClass('on');
					 var bb = '${person}'; 
			 /*			if (bb == "0") {
			 				alert("请先完善个人信息");
			 				window.location.href="owerInformationInit.mht";
			 			}*/
			 			
       			     $.post("bindingMobileInit.mht",null,function(data){
		             if(data.map != ""){
		              $("#mobileChange").attr("style","margin-top:0px;");
		              $("#mobile").html(data.map.mobilePhone);
		              $("#content").attr("value",data.map.reason);
		              $("#mobile").attr('disabled','disabled');
		              $("#code").attr('disabled','disabled');
		              $("#content").attr('disabled','disabled');
		              $("#clickCode_").attr('style','display: none');
		              $("#p_b").attr('style','display: none');
		           }else{
		        	  $("#mobileChange").attr("style","margin-top:0px;");
		        	  $("#num").html("");
		        	  $("#mobile").html("请先填写个人基本资料");
		              $("#mobile").attr('disabled','disabled');
		              $("#code").attr('disabled','disabled');
		              $("#content").attr('disabled','disabled');
		              $("#clickCode_").attr('style','display: none');
		              $("#p_b").attr('style','display: none');
		              $("#mobileChange").html("");
			           }
            });
	   });
	</script>

	<script>
	
	  //手机号码绑定
		  var timers=90;
		  var tipId;
		 
		   $(function(){
		       $("#clickCode_").click(function(){
		           var phone=$("#mobile").val();
		           //验证手机号码是
		           if($("#mobile").val()==""){
                      $("#mobile_tip").attr("class", "formtips onError");
		              $("#mobile_tip").html("请填写手机号码！");
                   }else if((!/^1[3-8]\d{9}$/.test($("#mobile").val()))){ 
                      $("#mobile_tip").attr("class", "formtips onError");
	                  $("#mobile_tip").html("请正确填写手机号码！");
                   }else{

                   		$.post("cellphoneOnly.mht","cellphone="+phone,function(data){
                   			if(data.msg=="手机已存在"){
								 $("#mobile_tip").attr("class", "formtips onError");
	                        	 $("#mobile_tip").html("手机号码已存在！");
	                    	}else{
	                    		  $("#mobile_tip").attr("class", "formtips");
	                  			  $("#mobile_tip").html(""); 
	                 			  if($("#clickCode_").html()=="重新发送验证码"||$("#clickCode_").html()=="发送手机验证码") { 
		

	                 				    $.post("phoneCheck.mht","phone="+phone,function(datas){

	                 				        if(datas == "virtual"){

	                 				            window.location.href = "noPermission.mht";
	                 				            return ;

	                 				        }
	                 				        if(datas.ret != 1){

	                 				            alert(datas.msg);
	                 				            return;

	                 				        }
	                 				        phone = datas.phone;
	                 				        var test={};
	                 				        test["paramMap.phone"] = phone;
	                 				        $.post("sendSMS.mht",test,function(data){

	                 				            if(data==1){

	                 				                timers=180;

	                 				            tipId=window.setInterval(timer_,1000);
	                 				            }else{

	                 				                alert("手机验证码发送失败");

	                 				            }

	                 				        });

	                 				    });
	                    			  
	                    			  
	                    			  
	                 			}
	                    	}
	                    });
                     
                   }
   
		       });
		   });
		   
		   //定时
		   function timer_(){
		    
		       if(timers>=0){
		         $("#clickCode_").html("验证码获取："+timers+"秒");
		         timers--;
		       }else{
		          window.clearInterval(tipId);
		           $("#clickCode_").html("重新发送验证码");
		           $.post("removeCode.mht","",function(data){});
		           
		       }
		   }
		   
			//加载该用户提现银行卡信息
			function loadBankInfo(url) {
				var bb = '${person}';//设置提现银行卡必须先填写个人资料
				if (bb == "0") {
					alert("请先完善个人信息");
					window.location.href="owerInformationInit.mht";
				} else {
					window.location.href=url;
				}

			}

			//工作认证
	 		function loadWorkInit(url){
	 			var bb = '${person}';//填写工作信息必须先填写个人资料
	 			if (bb == "0") {
	 				alert("请先完善个人信息");
	 				window.location.href="owerInformationInit.mht";
	 			} else {
	 				window.location.href=url;
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
	 		function bindingPhoneLoad(url) {
	 			var bb = '${person}';//申请手机绑定必须先填写个人资料
	 			var cc = '${pass}';
	 			var userType = '${session.user.userType}';
	 			if (bb == "0") {
	 				if(userType == "1"){
	 					alert("请先完善个人详细信息！");
	 				}else{
	 					alert("请先完善企业详细信息！");
	 				}
	 				window.location.href='owerInformationInit.mht';
	 			} else if (cc != 3) {
	 				if(userType == "1"){
	 					alert("请等待个人信息审核通过！");
	 				}else{
	 					alert("请等待企业信息审核通过！");
	 				}
	 				return;
	 			} else {*/
	 				window.location.href=url;
	 			}
	 		}
		</script>
		
		
		<script>
		      //手机号码变更
		      
		  var rtimers=90;
		  var rtipId;
		 
        $(function(){
		         $("#reClickCode_").click(function(){
		           var phone=$("#mobile2").val();
		           //验证手机号码是
		           if($("#mobile2").val()==""){
                      $("#mobile2_tip").attr("class", "formtips onError");
		              $("#mobile2_tip").html("请填写手机号码！");
                   }else if((!/^1[3,5,8]\d{9}$/.test($("#mobile2").val()))){ 
                      $("#mobile2_tip").attr("class", "formtips onError");
	                  $("#mobile2_tip").html("请正确填写手机号码！");
                   }else{
                   		$.post("cellphoneOnly.mht","cellphone="+phone,function(data){
                   			if(data.msg=="手机已存在"){
								$("#mobile2_tip").attr("class", "formtips onError");
	                        	 $("#mobile2_tip").html("手机号码已存在！");
	                    	}else{
	                    		   $("#mobile2_tip").attr("class", "formtips");
	                  			   $("#mobile2_tip").html(""); 
	                 			   if($("#reClickCode_").html()=="重新发送验证码"||$("#reClickCode_").html()=="发送手机验证码") {  
	                 				   

	                 				    $.post("phoneCheck.mht","phone="+phone,function(datas){

	                 				        if(datas.ret != 1){

	                 				            alert(datas.msg);
	                 				            return;

	                 				        }
	                 				        phone = datas.phone;
	                 				        var test={};
	                 				        test["paramMap.phone"] = phone;
	                 				        $.post("sendSMS.mht",test,function(data){

	                 				            if(data == "virtual"){

	                 				                window.location.href = "noPermission.mht";

	                 				            return ;
	                 				            }
	                 				            if(data==1){
													
	                 				                rtimers=180;

	                 				            rtipId=window.setInterval(rtimer_,1000);
	                 				            }else{

	                 				                alert("手机验证码发送失败");

	                 				            }

	                 				        });

	                 				    });
	                 			}
	                    	}
	                    });
                   }
		       });
		   });
		   
		   //定时
		   function rtimer_(){
		    
		       if(rtimers>=0){
		         $("#reClickCode_").html("验证码获取："+rtimers+"秒");
		         rtimers--;
		       }else{
		          window.clearInterval(rtipId);
		           $("#reClickCode_").html("重新发送验证码");
		           $.post("removeCode.mht","",function(data){});
		           
		       }
		   }

		 	//变更手机绑定信息
		 	function addChangeBindingMobile(){
		 	   if($("#mobile2").val()==""){
		 	     $("#mobile2_tip").html("手机号码不能为空");
		 	      return;
		 	    }else if($("#code2").val()==""){
		 	       $("#mobile2_tip").html("验证码不能为空");
		 	       return;
			 	}else if($("#content2").val()==""){
		 	      $("#mobile2_tip").html("变更原因不能为空");
		 	      return;
		 	     }else{
			 	    $("#mobile2_tip").html("");
			 	 }
			 	 param["paramMap.mobile"] = $("#mobile2").val();
	         	 param["paramMap.code"] = $("#code2").val();
	         	 param["paramMap.content"] = $("#content2").val();
			 	 $.dispatchPost("addChangeBindingMobile.mht",param,function(data){
			 	      
		       //手机验证码
		       if(data==10){
		        // $("#u_cellPhone").html("手机号码长度不对！")
		         alert("手机号码与获取验证码手机号不一致！");
		         return;
		       }
		       if(data==12){
		        // $("#u_cellPhone").html("手机号码长度不对！")
		         alert("请填写验证码");
		          return;
		       }
		       if(data==11){
		        // $("#u_cellPhone").html("手机号码长度不对！")
		         alert("请重新发送验证码");
		          return;
		       }
		       
		        if(data==13){
		        // $("#u_cellPhone").html("手机号码长度不对！")
		         alert("请输入正确的验证码");
		          return;
		       }
		       
			 	   
			 	    if(data == 1){
			 	       $("#mobile2_tip").html("手机号码无效");
			 	       $("#mobile2").attr("value","");
			 	       return;
			 	    }else if(data == 2){
			 	       $("#mobile2_tip").html("验证码错误");
			 	       $("#code2").attr("value","");
			 	       return;
			 	    }else if(data == 3){//该用户可以进行手机号码绑定，但是绑定的手机号码已经被别人绑定了
			 	      alert("您还没有进行手机绑定，请先申请手机绑定");
			 	      $("#mobile2").attr("value","");
	 			      $("#code2").attr("value","");
	 			      $("#content2").attr("value","");
	 			      return;
			 	    }else if(data ==4){
			 	       alert("您已经申请的手机信息还在审核，请等待后台审核");
			 	       $("#mobile2").attr("value","");
	 			       $("#code2").attr("value","");
	 			       $("#content2").attr("value","");
	 			       return;
			 	    }else if(data == 5){
			 	      alert("已经成功申请变更，请等待后台审核");
			 	      $("#mobile2").attr("value","");
	 			       $("#code2").attr("value","");
	 			       $("#content2").attr("value","");
			 	      return;
			 	    }else if(data == 6){
			 	      alert("您变更的手机号码已经被绑定了，请重新输入变更的手机号码");
			 	      $("#mobile2").attr("value","");
	 			       $("#code2").attr("value","");
	 			       $("#content2").attr("value","");
			 	      return;
			 	    }else if(data == 7){
			 	      alert("您变更的手机号码已经在申请审核，请重新输入变更的手机号码");
			 	      $("#mobile2").attr("value","");
	 			       $("#code2").attr("value","");
	 			       $("#content2").attr("value","");
			 	      return;
			 	    }else if(data == 8){
			 	      alert("您的手机变更不通过，请联系客服人员");
			 	      $("#mobile").attr("value","");
	 			       $("#code").attr("value","");
	 			       $("#content").attr("value","");
			 	      return;
			 	    }else if(data == 9){
			 	      alert("您的手机变更不通过，请联系客服人员");
			 	      $("#mobile").attr("value","");
	 			       $("#code").attr("value","");
	 			       $("#content").attr("value","");
			 	      return;
			 	    }
	 			    alert("您要变更的手机号码为 "+$("#mobile2").val());
	 			    $("#mobile2").attr("value","");
	 			    $("#code2").attr("value","");
	 			    $("#content2").attr("value","");
	 			    
	 			    window.clearInterval(tipId);
			           $("#reClickCode_").html("发送手机验证码");
	 			 });
		 	}
		</script>
</body>
</html>
