<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>安全中心</title>
<link href="css/inside.css"  rel="stylesheet" type="text/css" />
<link id="skin" rel="stylesheet" href="css/jbox/Gray/jbox.css" />
<link href="css/common.css"  rel="stylesheet" type="text/css" />
<link href="css/user.css"  rel="stylesheet" type="text/css" />
   <link rel="stylesheet" type="text/css"
	href="script/bootstrap/css/bootstrap.min.css">
</head>

<body>
<!-- 引用头部公共部分 -->
<jsp:include page="header.jsp"></jsp:include>	
	<div class="r_main">
         <div class="uesr_border personal">
            <div class="pub_tb bottom"><i></i>安全中心</div>
            <form id="form1" name="form1" method="post" action="">
            <ul class="safety">  
                   <!--登录密码-->         
                  <li class="list">
                   <div class="clearfix top_tit">
                    <s:if test="#request.password != null">
                      <span class="success"></span>
					</s:if>
                    <s:else>
                      <span class="plaint"></span>
                    </s:else>
                       <div class="f20 fl">登录密码</div>                       
                       <div class="tir fr" ><a href="javascript:void(0)" myvalue="1" class="blue" id="fixFun1">设置</a></div>                                          
                       <p class="clear"></p>
                       <p style="line-height:22px;text-indent:2.8em;">定期更换密码可以让你的账户更加安全</p>
                   </div>
                  
                   <div id="box1" class="mt15"  style="display:none">
                       <ul class="safe_list">
                       <li class="clearfix">
                            <div class="til"><span class="red">*</span>原密码：</div>
                            <div class="info"><input type="password" id="oldPassword" class="input_border w200" /><span class="ml5 gray6">输入您现在的账号登录密码</span></div>
                       </li>
                       <li class="clearfix">
                            <div class="til"><span class="red">*</span>新密码：</div>
                            <div class="info"><input type="password" id="newPassword" class="input_border w200" /><span class="ml5 gray6">输入您的新密码</span></div>
                       </li>
                       <li class="clearfix">
                            <div class="til"><span class="red">*</span>确认新密码：</div>
                            <div class="info"><input type="password" id="confirmPassword" class="input_border w200" /><span class="ml5 gray6">请再次输入您的新密码</span></div>
                       </li>
                       <li class="clearfix">
                            <div class="til">&nbsp;</div>
                            <div class="info"><input id="loginSubmit" onclick="updateLoginPassword();" type="button" value="提 交" class="ui_btn01 usebtn" /> </div>
                       </li>
                       <li class="clearfix">
                       		<div class="til">&nbsp;</div>
                            <div class="info" ><span style="color:red; float: none; text-align:left; font-size:16px" id="s_tip" class="formtips"></span></div>
                       </li>
                       </ul>                        
                   </div>
                   </li> 
                   <!--交易密码--> 
                   <li class="list">
                   <div class="clearfix top_tit">
                       <!--未验证-->
                       <s:if test="#request.dealpwd != null && #request.dealpwd !=''">
                       <span class="success"></span>
                       </s:if>
                       <s:else>
                         <span class="plaint"></span>
                       </s:else>
                       
                       <div class="f20 fl">交易密码</div>                       
                       <div class="tir fr" >
                       		<a href="javascript:void(0)"  myvalue="1" class="blue" id="fixFun2">设置</a>
                       </div>                                          
                       <p class="clear"></p>
                       <s:if test="#request.dealpwd != null && #request.dealpwd !=''">
                         <!--验证成功--><p style="line-height:22px;text-indent:2.8em;" class="gray3">投资或提现时输入，保护账户资金安全</p>
                       </s:if>
                       <s:else>
                         <!--未验证--><p style="line-height:22px;text-indent:2.8em;" class="info gray6">投资或提现时输入，保护账户资金安全，您还未设置</p>
                       </s:else>
                   </div>
                        <div id="box2" class="mt15"  style="display:none">
                           <ul class="safe_list">
                           <li class="clearfix">
                            <div class="til"><span class="red">*</span>原密码：</div>
                            <div class="info"><input type="password" id="oldDealpwd" class="input_border w200" /><span class="ml5 gray6">输入您现在的账号登录密码</span></div>
                       </li>
                       <li class="clearfix">
                            <div class="til"><span class="red">*</span>新密码：</div>
                            <div class="info"><input type="password" id="newDealpwd" class="input_border w200" /><span class="ml5 gray6">输入您的新密码</span></div>
                       </li>
                       <li class="clearfix">
                            <div class="til"><span class="red">*</span>确认新密码：</div>
                            <div class="info"><input type="password" id="confirmDealpwd" class="input_border w200" /><span class="ml5 gray6">请再次输入您的新密码</span></div>
                       </li>
                       <li class="clearfix">
                            <div class="til">&nbsp;</div>
                            <div class="info"><input onclick="updateDealPassword();" id="loginSubmit" type="button" value="提 交" class="ui_btn01 usebtn" />&nbsp;&nbsp;
                             <a href="javascript:searchDealPwd()">忘记密码?</a> 
                            </div>
                       </li>
                       <li class="clearfix">
                       		<div class="til">&nbsp;</div>
                            <div class="info" ><span style="color:red; float: none; text-align:left; font-size:16px" id="d_tip" class="formtips"></span></div>
                       </li>
                       </ul>                        
                       </div>
                   </li>
                   <!-- 实名认证 -->                 
                   <li class="list"  <s:if test="#session.user.userType==2">style="display:none"</s:if>  >
                   <div class="clearfix top_tit">
                        <s:if test="#request.authCardName == 0">
						 <span class="success"></span>
						</s:if>
						<s:else>
						 <span class="plaint"></span>
						</s:else>
					   <div class="f20 fl">实名认证</div>                       
                       <s:if test="#request.authCardName == 0">
						   <div class="tir fr" >
	                       	<a href="javascript:void(0)" myvalue="1" class="blue" id="fixFun3">查看</a>
	                       </div>                                          
	                       <p class="clear"></p>
	                       <p style="line-height:22px;text-indent:2.8em;" class=" gray3">您已通过实名认证</p>
						</s:if>
						<s:else>
						 <!--未验证-->
						   <div class="tir fr" >
	                       	<a href="javascript:void(0)" myvalue="1" class="blue" id="fixFun3">认证</a>
	                       </div>                                          
	                       <p class="clear"></p>
	                       <p style="line-height:22px;text-indent:2.8em;" class=" gray6">您尚未通过实名认证，为了您的账户安全请及时认证</p>
						</s:else>
                   </div>
                   <s:if test="#request.authCardName == 0">
                   		<div id="box3" class="mt15" style="display:none">
                           <ul class="safe_list">
                           <!--验证成功--> 
                           <li class="sfz">
                                <div class="tianchong">
                                    <p class="mt20">真实姓名：<shove:sub value="#request.realName" size="1" suffix="**"/></p>
                                    <p>身份证号码：<shove:sub value="#request.idNo" size="2" suffix="**** **** **** ****"/></p>
                                    <p class="red">恭喜，您已通过实名认证！</p>
                                </div>
                           </li>
                           </ul>                        
                       </div>
                   </s:if>
				   <s:else>
				   <div id="box3" class="mt15" style="display:none">
                           <ul class="safe_list">
                           <li class="clearfix">
                                <div class="til"><span class="red">*</span>真实姓名：</div>
                                <div class="info"><input id="realName" type="text" class="input_border w200" />
                                <span style="color: red; float: none;" id="u_realName" class="formtips"></span>
                                </div>
                           </li>
                           <li class="clearfix">
                                <div class="til"><span class="red">*</span>身份证号码：</div>
                                <div class="info"><input id="idNo" type="text" class="input_border w200" />
                                <span style="color: red; float: none;" id="u_idNo" class="formtips"></span>
                                </div>
                           </li>
                           <li class="clearfix">
                                <div class="til">&nbsp;</div>
                                <div class="info"> <input id="identifierSubmit" onclick="identifier();" type="button" value="提 交" class="ui_btn01 usebtn"/></div>
                           </li>
                           <li class="clearfix">
								<div class="til">&nbsp;</div>
								<div id="identifierOK" style="color: red; height: 20px;font-size:16px" >${paramMap.msg }</div>
						   </li>
                           </ul>                        
                       </div>
				   </s:else>
                   </li>
                   <!--绑定手机-->                 
                   <li class="list">
                   <div class="clearfix top_tit">
                       <s:if test="#request.mobilePhone != null && #request.mobilePhone !=''">
                       <span class="success"></span>
                       </s:if>
                       <s:else>
                         <span class="plaint"></span>
                       </s:else>
                       
                       <div class="f20 fl">绑定手机</div>  
                       
                       <div class="tir fr" >
                       		<a href="javascript:void(0)"  myvalue="1" class="blue" id="fixFun4">设置</a>
                       </div>                                          
                       <p class="clear"></p>
                       
                       <s:if test="#request.mobilePhone != null && #request.mobilePhone !=''">
                       <!--验证成功-->
                       	  <p style="line-height:22px;text-indent:2.8em;" class="gray3">您当前绑定的手机：<b class="ml5">
                         ${mobilePhone}
                         </b></p>
                       </s:if>
                       <s:else>
                         <!--未验证--><p style="line-height:22px;text-indent:2.8em;" class="gray6">您还未绑定手机</p>
                       </s:else>
                   </div>
                        <div id="box4" class="mt15" style="display:none;">
                           <ul class="safe_list">
                           <li class="clearfix">
                                <div class="til"><span class="red">*</span> 手机号码：</div>
                                <div class="info"><input type="text" id="mobile2" class="input_border w200"  maxlength="11"/>
                                <input type="button" id="codeImg" value="发送手机验证码" class="blue_btn"/>
                                </div>
                           </li>
                           <li class="clearfix">
                                <div class="til"></div>
                                <div class="info">
                                    <p style="color: #666;">收不到短信验证码? 点击这里<a id="codeImg_a"  style="cursor: pointer;" class="orange" >获取语音验证码</a></p>  
                                    <p id="vioce" style="display: none;"><font color="red">获取语音验证码需要呼叫您的手机，请保持手机畅通，</font><br/>注意接听 400-6000583 的来电。</p>
                                </div>
                           </li>
                           <li class="clearfix">
                                <div class="til"><span class="red">*</span> 验证码：</div>
                                <div class="info"><input type="text" id="code2" class="input_border w100"  maxlength="4" /><span class="ml5 gray6">请填写手机收到的验证码</span></div>
                           </li>
                           <li class="clearfix">
                                <div class="til"><span class="red">*</span> 交易密码：</div>
                                <div class="info"><input type="password" id="content2" class="input_border w200" maxlength="19"/>
                                <a href="javascript:searchDealPwd()">忘记交易密码?</a></div>
                           </li>
                           <li class="clearfix">
                                <div class="til">&nbsp;</div>
                                <div class="info"><input onclick="addChangeBindingMobile();" id="smsChange" type="button" value="提 交" class="ui_btn01 usebtn"/></div>
                           </li>
                           <li class="clearfix">
                                <div class="til">&nbsp;</div>
                                <div class="info"><span style="color: red; float: none;font-size:16px" id="mobile2_tip" class="formtips"></span></div>
                           </li>
                           </ul>                        
                       </div>
                   </li>
                   <!--绑定邮箱-->                 
                   <li class="list">
                   <div class="clearfix top_tit">
                       <s:if test="#request.email != null && #request.email !=''">
                       <span class="success"></span>
                       </s:if>
                       <s:else>
                         <span class="plaint"></span>
                       </s:else>
                       
                       <div class="f20 fl">绑定邮箱</div>                       
                       <div class="tir fr" ><a href="javascript:void(0)" myvalue="1" class="blue" id="fixFun5">设置</a></div>                                          
                       <p class="clear"></p>
                       <s:if test="#request.email != null && #request.email !=''">
                         <!--验证成功-->
                         <p style="line-height:22px;text-indent:2.8em;" class=" gray3">您当前绑定的邮箱：<b class="ml5">${email }</b></p>
                       </s:if>
                       <s:else>
                         <!--未验证--><p style="line-height:22px;text-indent:2.8em;" class=" gray6">您还未绑定邮箱</p>
                       </s:else>
                   </div>
                        <div id="box5" class="mt15" style="display:none;">
                           <ul class="safe_list">
                           <li class="clearfix">
                                <div class="til"><span class="red">*</span> 新邮箱地址：</div>
                                <div class="info"><input id="mails" type="text" class="input_border w200" /><input type="button" id="sendRamEmail" value="发送验证邮件" class="blue_btn"/></div>
                           </li>
                           <li class="clearfix">
                                <div class="til"><span class="red">*</span> 验证码：</div>
                                <div class="info"><input type="text" id="checkCode" class="input_border w200" /><span class="ml5 gray6">请填写邮件中的验证码</span></div>
                           </li>
                           <li class="clearfix">
                                <div class="til"><span class="red">*</span> 交易密码：</div>
                                <div class="info"><input type="password" id="edealpwd" class="input_border w200" maxlength="19"/>
                                <a href="javascript:searchDealPwd()">忘记交易密码?</a> </div>
                           </li>
                           <li class="clearfix">
                                <div class="til">&nbsp;</div>
                                <div class="info"><input id="emailSubmit" onclick="emailSubSave();" type="button" value="提 交" class="ui_btn01 usebtn"/></div>
                           </li>
                           <li class="clearfix">
                                <div class="til">&nbsp;</div>
                                <div id="ok" style="color: red; height: 20px;font-size:16px" >${paramMap.msg }</div>
                           </li>
                           
                           </ul>
                       </div>
                   </li>
                   <!--系统通知-->                 
                   <li class="list">
                   <div class="clearfix top_tit">
                       <span class="success"></span>
                       
                       <div class="f20 fl">系统通知</div>                       
                       <div class="tir fr" ><a href="javascript:void(0)" myvalue="1" class="blue" id="fixFun6">设置</a></div>                                          
                       <p class="clear"></p>
                       <p style="line-height:22px;text-indent:2.8em;">您在三好资本网的相关操作，系统会以站内信、邮件、短信的方式通知您</p>
                   </div>
                        <div id="box6" style="display:none;" class="mt15">
                           <ul class="safe_list">
                           <li class="clearfix">
                               <span class="mr50 ml30"><input id="message"  type="checkbox" value="" class="mr5" onclick="checkAll_MG(this);"/>站内信通知</span>
                               <span class="mr5"><input id="messageReceive" name="mg" type="checkbox" value="" class="mr5" onclick="mgcheck1(this);"/>收到还款</span>
                               <span class="mr5"><input id="messageDeposit" name="mg" type="checkbox" value="" class="mr5" onclick="mgcheck1(this);"/>提现成功</span>  
                               <span class="mr5"><input id="messageBorrow" name="mg" type="checkbox" value="" class="mr5" onclick="mgcheck1(this);"/>借款成功</span>  
                               <span class="mr5"><input id="messageRecharge" name="mg" type="checkbox" value="" class="mr5" onclick="mgcheck1(this);"/>充值成功</span>
                               <span class="mr5"><input id="messageChange" name="mg" type="checkbox" value="" class="mr5" onclick="mgcheck1(this);"/>资金变化</span>   
                           </li>
                           <li class="clearfix">
                               <span class="mr50 ml30"><input id="mail"  type="checkbox" value="" class="mr5" onclick="checkAll_ML(this);"/>邮件通知</span>
                               <span class="ml15 mr5"><input id="mailReceive"  name="ml" type="checkbox" value="" class="mr5" onclick="mlcheck1(this);"/>收到还款</span>
                               <span class="mr5"><input id="mailDeposit" name="ml" type="checkbox" value="" class="mr5" onclick="mlcheck1(this);"/>提现成功</span>  
                               <span class="mr5"><input id="mailBorrow" name="ml" type="checkbox" value="" class="mr5" onclick="mlcheck1(this);"/>借款成功</span>  
                               <span class="mr5"><input id="mailRecharge" name="ml" type="checkbox" value="" class="mr5" onclick="mlcheck1(this);"/>充值成功</span>
                               <span class="mr5"><input id="mailChange" name="ml" type="checkbox" value="" class="mr5" onclick="mlcheck1(this);"/>资金变化</span>   
                           </li>
                           <li class="clearfix">
                               <span class="mr50 ml30"><input id="note" type="checkbox" value="" class="mr5" onclick="checkAll_NT(this); "/>短信通知</span>
                               <span class="mr5 ml15"><input id="noteReceive"  name="nt" type="checkbox" value="" class="mr5" onclick="ntcheck1(this);"/>收到还款</span>
                               <span class="mr5"><input id="noteDeposit" name="nt" type="checkbox" value="" class="mr5" onclick="ntcheck1(this);"/>提现成功</span>  
                               <span class="mr5"><input id="noteBorrow" name="nt" type="checkbox" value="" class="mr5" onclick="ntcheck1(this);"/>借款成功</span>  
                               <span class="mr5"><input id="noteRecharge" name="nt" type="checkbox" value="" class="mr5" onclick="ntcheck1(this);"/>充值成功</span>
                               <span class="mr5"><input id="noteChange" name="nt" type="checkbox" value="" class="mr5" onclick="ntcheck1(this);"/>资金变化</span>   
                           </li>
                           <li class="clearfix mt20 ">
                                <div class="til">&nbsp;</div>
                                <div class="info"><input name="input" type="button" value="提 交" class="ui_btn01 usebtn" onclick="addNoteSetting();"/> </div>
                           </li>
                           </ul>                        
                       </div>
                   </li>
                </ul> 
                 </form>                 
	
  </div>
</div>
<!-- 引用底部公共部分 -->     
<script type="text/javascript" src="script/jbox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript" src="script/jbox/jquery.jBox-zh-CN.js"></script>
<script type="text/javascript" src="js/searchDealPwd.js"></script>
<script>

var sendFlag = 0;
function GetRequest() {
    var url = location.search; //获取url中"?"符后的字串
    var theRequest = new Object();
    if (url.indexOf("?") != -1) {
       var str = url.substr(1);
       strs = str.split("&");
       for(var i = 0; i < strs.length; i ++) {
          theRequest[strs[i].split("=")[0]]=unescape(strs[i].split("=")[1]);
       }
    }
    return theRequest;
 }

$(function(){
 var isOn = parseInt(GetRequest()['isOn']);//当前打开的
 switch (isOn) {
 case 1:
     loginPassword();
     break;
     case 2:
          transPassword();
     break;
     case 3:
         nameAuthen();
 break;
 case 4:
     bindSms();
 break;
 case 5:
     bindEmail();
 break;
 case 6:
     systemNote();
     break;
 default:
     break;
 }
	
//样式选中
        //$("#zh_hover").attr('class','nav_first');
	    //$("#zh_hover div").removeClass('none');
	    //$('#li_4').addClass('on');
	$('#securCenterNew').addClass('on');
	/**$('.tabmain').find('li').click(function(){
	   $('.tabmain').find('li').removeClass('on');
	   $(this).addClass('on');
	   $('.lcmain_l').children('div').hide();
           $('.lcmain_l').children('div').eq($(this).index()).show();
        })
        **/

    $('#fixFun1').bind("click", loginPassword);
    $('#fixFun2').bind("click", transPassword);
    $('#fixFun3').bind("click", nameAuthen);
    $('#fixFun4').bind("click", bindSms);
    $('#fixFun5').bind("click", bindEmail);
    $('#fixFun6').bind("click", systemNote);


    var rtimers=90;
	var rtipId;

    $("#codeImg").click(function(){
    	codeImg();
    });
    
    $("#codeImg_a").click(function(){
    	if($("#codeImg").attr("disabled") && $("#codeImg").attr("disabled")=="disabled"){
            $("#mobile2_tip").html("您的验证码已发送，请稍候再试...");
            setTimeout(function(){
                $("#mobile2_tip").html("");
            },1000)
            return
        };
        codeImg('vioce');
    });
    
    function codeImg(vioce){
    	var phone=$("#mobile2").val();
        //验证手机号码是
        if($("#mobile2").val()==""){
           $("#mobile2_tip").attr("class", "formtips onError");
           $("#mobile2_tip").html("请填写手机号码！");
        }else if((!/^1[3,5,8,4]\d{9}$/.test($("#mobile2").val()))){ 
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
                            $.post("phoneCheck.mht","phone="+phone,function(datas){
                                if(datas.ret != 1){
                                    return;
                                }
                                phone = datas.phone;
                                var test={};
                                if(vioce){
                                	test["paramMap.vioce"] = true;
                                }
                                test["paramMap.phone"] = phone;
                                $.post("sendSMS.mht",test,function(data){
                                    if(data == "virtual"){
                                        window.location.href = "noPermission.mht";
                                        return ;
                                    }
                                    if(data==1){
                                       rtimers=180;
                                       rtipId=window.setInterval(rtimer_,1000);
                                       if(vioce){
                                           $("#vioce").css("display","block");
                                           setTimeout(function(){
                                               $("#vioce").css("display","none");
                                           },60000)
                                       }
                                    }else if(data==3){
                                        alert("对不起，您今日的短信发送量以上限");
                                    }else{
                                        alert("手机验证码发送失败");
                                    }

                                });

                            });
                }
             });
        }
    }
    
   //定时
   function rtimer_(){
       if(rtimers>=0){
         $("#codeImg").val("验证码获取："+rtimers+"秒");
         $("#codeImg").addClass("hui_btn")
         $("#codeImg").removeClass("blue_btn"); 
         $("#codeImg").attr("disabled", true);
         rtimers--;
       }else{
          window.clearInterval(rtipId);
           $("#codeImg").val("重新发送验证码");
           $("#codeImg").addClass("blue_btn")
           $("#codeImg").removeClass("hui_btn"); 
           $("#codeImg").attr("disabled", false);
           $.post("removeCode.mht","",function(data){});
       }
   }


    //提交邮箱添加
	$("#sendRamEmail").click(function(){
	   if (sendFlag == 1){
		   $("#ok").html("请到邮箱" + $("#mails").val() + "查看验证码。");
		   return;
	   }
	   var param = {};
       param["paramMap.email"] = $("#mails").val();
       if(/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/.test($("#mails").val())){
	        $.post("SendUserEmailSet.mht",param,function(data){
	        if(data.mailAddress=='0'){
	             alert("邮箱不能为空");
	        }else if(data.mailAddress=='1'){
	          $("#ok").html("该邮箱不存在");
	        }else if (data.mailAddress=='4'){
	        	 $("#ok").html("该邮箱已被绑定,请重新输入");
		    }else{
	          $("#ok").html("邮件已经发送到你的邮箱,请<a href=http://"+data.mailAddress+"  target='_blank'>登录</a>到你的邮箱验证");
	          
	          setTimeout('endTime()', 600000);//定时器，10分钟后失效
	          sendFlag = 1;
	          timedCount();
	        }
	       });
       }else{
    	   $("#ok").html("请输入正确的邮箱");
       }
	});




	$.dispatchPost("queryNotesSettingInit.mht",null,function(data){
	    if(data == 1){
	      return ;
	    }
	    var message = false,mail = false,notes = false;
	     
	    for(var i = 0; i < data.length; i ++){
	      if(i == 0){
	         message = data[i].message;
	         mail = data[i].mail;
	         notes = data[i].note;
	      }
	      if(data[i].noticeMode =="1"){//邮件通知
	         if(data[i].reciveRepayEnable =="2"){
	         	$("#mailReceive").attr("checked","checked")
	         }if(data[i].showSucEnable =="2"){
	         	$("#mailDeposit").attr("checked","checked")
	         }if(data[i].loanSucEnable =="2"){
	         	$("#mailBorrow").attr("checked","checked")
	         }if(data[i].rechargeSucEnable =="2"){
	         	$("#mailRecharge").attr("checked","checked")
	         }if(data[i].capitalChangeEnable =="2"){
	         	$("#mailChange").attr("checked","checked")
	         }
	      }else if(data[i].noticeMode =="2"){//站内信通知
	      	if(data[i].reciveRepayEnable =="2"){
	         	$("#messageReceive").attr("checked","checked")
	         }if(data[i].showSucEnable =="2"){
	         	$("#messageDeposit").attr("checked","checked")
	         }if(data[i].loanSucEnable =="2"){
	         	$("#messageBorrow").attr("checked","checked")
	         }if(data[i].rechargeSucEnable =="2"){
	         	$("#messageRecharge").attr("checked","checked")
	         }if(data[i].capitalChangeEnable =="2"){
	         	$("#messageChange").attr("checked","checked")
	         }
	      }else{//短信通知
	      	if(data[i].reciveRepayEnable =="2"){
	         	$("#noteReceive").attr("checked","checked")
	         }if(data[i].showSucEnable =="2"){
	         	$("#noteDeposit").attr("checked","checked")
	         }if(data[i].loanSucEnable =="2"){
	         	$("#noteBorrow").attr("checked","checked")
	         }if(data[i].rechargeSucEnable =="2"){
	         	$("#noteRecharge").attr("checked","checked")
	         }if(data[i].capitalChangeEnable =="2"){
	         	$("#noteChange").attr("checked","checked")
	         }
	      }
	    }
	    if(message){//只要分类有一个被选中，则父类就选中
	      $("#message").attr("checked","checked");
	      //$(".mg").attr("checked","checked");
	    }
	    if(mail){
	      $("#mail").attr("checked","checked");
	      //$(".ml").attr("checked","checked");
	    }
	    if(notes){
	      $("#note").attr("checked","checked");
	      //$(".nt").attr("checked","checked");
	    }
  });





	
})


function endTime(){
	//发个AJAX,清理session中的数据
	$.dispatchPost("removeEmailTime.mht", param, function(data) {
		sendFlag=2;
	});
}



function loginPassword(){

	if ($("#fixFun1").attr("myvalue")=='1'){
		$("#box1").css('display','block');
		$("#fixFun1").text("关闭");
		$("#fixFun1").attr("myvalue","2");
		closeOther("1");
	} else {
		$("#box1").css('display','none');
		$("#fixFun1").text("设置");
		$("#fixFun1").attr("myvalue","1");
		
	}
}

function transPassword(){
	if ($("#fixFun2").attr("myvalue")=='1'){
		$("#box2").css('display','block');
		$("#fixFun2").text("关闭");
		$("#fixFun2").attr("myvalue","2");
		closeOther("2");
	} else {
		$("#box2").css('display','none');
		$("#fixFun2").text("设置");
		$("#fixFun2").attr("myvalue","1");
	}
}

function nameAuthen(){
	if ($("#fixFun3").attr("myvalue")=='1'){
		$("#box3").css('display','block');
		$("#fixFun3").text("关闭");
		$("#fixFun3").attr("myvalue","2");
		closeOther('3');
	} else {
		$("#box3").css('display','none');
		if(${authCardName}==0){
			$("#fixFun3").text("查看");
		}else{
			$("#fixFun3").text("认证");
		}
		$("#fixFun3").attr("myvalue","1");
	}
}

function bindSms(){
	if ($("#fixFun4").attr("myvalue")=='1'){
		$("#box4").css('display','block');
		$("#fixFun4").text("关闭");
		$("#fixFun4").attr("myvalue","2");
		closeOther('4');
	} else {
		$("#box4").css('display','none');
		$("#fixFun4").text("设置");
		$("#fixFun4").attr("myvalue","1");
	}
}

function bindEmail(){
	if ($("#fixFun5").attr("myvalue")=='1'){
		$("#box5").css('display','block');
		$("#fixFun5").text("关闭");
		$("#fixFun5").attr("myvalue","2");
		closeOther('5');
	} else {
		$("#box5").css('display','none');
		$("#fixFun5").text("设置");
		$("#fixFun5").attr("myvalue","1");
	}
}

function systemNote(){
	if ($("#fixFun6").attr("myvalue")=='1'){
		$("#box6").css('display','block');
		$("#fixFun6").text("关闭");
		$("#fixFun6").attr("myvalue","2");
		closeOther('6');
	} else {
		$("#box6").css('display','none');
		$("#fixFun6").text("设置");
		$("#fixFun6").attr("myvalue","1");
	}
	
}

function closeOther(bdex){
	if ($("#fixFun1").attr("myvalue")=='2' && bdex != '1'){
		$("#fixFun1").click();
	}
	if ($("#fixFun2").attr("myvalue")=='2' && bdex != '2'){
		$("#fixFun2").click();
	}
	if ($("#fixFun3").attr("myvalue")=='2' && bdex != '3'){
		$("#fixFun3").click();
	}
	if ($("#fixFun4").attr("myvalue")=='2' && bdex != '4'){
		$("#fixFun4").click();
	}
	if ($("#fixFun5").attr("myvalue")=='2' && bdex != '5'){
		$("#fixFun5").click();
	}
	if ($("#fixFun6").attr("myvalue")=='2' && bdex != '6'){
		$("#fixFun6").click();
	}
	
}

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
				window.location.href = "securityCent.mht";
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
			window.location.href = "securityCent.mht";
		}
	});
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
    $("#mobile2_tip").html("交易密码不能为空");
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
//$("#u_cellPhone").html("手机号码长度不对！")
alert("手机号码与获取验证码手机号不一致！");
return;
}
if(data==12){
//$("#u_cellPhone").html("手机号码长度不对！")
alert("请填写验证码");
return;
}
if(data==11){
//$("#u_cellPhone").html("手机号码长度不对！")
alert("请重新发送验证码");
return;
}

if(data==13){
//$("#u_cellPhone").html("手机号码长度不对！")
alert("请输入正确的验证码");
return;
}
if(data==14){
	//$("#u_cellPhone").html("手机号码长度不对！")
	alert("交易密码错误，请重新输入");
	$("#content2").val("");
	$("#content2").focus();
	return;
}
if(data==35){
	//$("#u_cellPhone").html("手机号码长度不对！")
	alert("验证码密码过期，请重新获取");
	$("#content2").val("");
	$("#content2").focus();
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
alert("已经成功申请变更");
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
window.location.href = "securityCent.mht";
window.clearInterval(tipId);
   $("#codeImg").html("发送手机验证码");
});
}

//提交身份认证信息验证
function identifier(){
	$("#identifierOK").html("");
	$("identifierSubmit").attr("disabled", true);
	if($("#realName").val()==""){
	    alert("请填写真实姓名！");
	    return false;
    }
    if($("#idNo").val()==""){
	    alert("请填写身份证号码！");
	    return false;
    }

    if($("#idNo").val().length!=18){
	    alert("请填写18位身份证号码！");
	    return false;
    }
    
    $('#identifierSubmit').attr('value','身份认证中...');
	param["paramMap.realName"] = $("#realName").val();
	param["paramMap.idNo"] = $("#idNo").val();

	if(param["paramMap.idNo"]!=null &&param["paramMap.idNo"]!='' &&  !IdCardValidate(param["paramMap.idNo"])){
		alert("请输入正确的身份证号码");
		$('#identifierSubmit').attr('value','提交');
		return false;
	}
	
	$.post("identifier.mht",param,function(data){
		if(data.msg=="认证成功"){
			alert("您的身份已认证成功，请返回！");
			window.location.href = "securityCent.mht";
	    }else{
	         if(data.msg=="请正确填写真实名字"){
	         	alert("请填写真实姓名！");
	         	$('#identifierSubmit').attr('value','提交');
	         	$("identifierSubmit").attr("disabled", false);
	       	 }
	         if(data.msg=="真实姓名的长度为不小于2和大于30"){
	            alert("真实姓名的长度为不小于2和大于30！");
	            $('#identifierSubmit').attr('value','提交');
	            $("identifierSubmit").attr("disabled", false);
	         }
	         if(data.msg=="真实姓名输入有误"){
		        alert("真实姓名输入有误！");
		        $('#identifierSubmit').attr('value','提交');
		        $("identifierSubmit").attr("disabled", false);
		     }
	         if(data.msg=="请明确身份证号码"){
	         	alert("请正确输入你的身份证号码！");
	         	$('#identifierSubmit').attr('value','提交');
	         	$("identifierSubmit").attr("disabled", false);
	       	 }
	         if(data.msg=="身份证号码不合法"){
		        alert("身份证号码不合法！");
		        $('#identifierSubmit').attr('value','提交');
		        $("identifierSubmit").attr("disabled", false);
		     }
	         if(data.msg=="身份证已注册"){
		        alert("身份证已注册！");
		        $('#identifierSubmit').attr('value','提交');
		        $("identifierSubmit").attr("disabled", false);
		     }
	         if(data.msg=="认证身份信息已更新至您的详细信息里"){
			    alert("认证身份信息已更新至您的详细信息里！");
			    $('#identifierSubmit').attr('value','提交');
			    window.location.href = "securityCent.mht";
			 }
			 if(data.msg=="认证信息已添加进您的详细信息里"){
		        alert("认证信息已添加进您的详细信息里！");
		        $('#identifierSubmit').attr('value','提交');
		        window.location.href = "securityCent.mht";
		     }
			 if(data.msg=="认证身份信息不通过"){
		        alert("认证身份信息不通过！");
		        $('#identifierSubmit').attr('value','提交');
		        window.location.href = "securityCent.mht";
		     }
			 if(data.msg=="网络异常"){
		        alert("认证通道异常，请联系客服！");
		        $('#identifierSubmit').attr('value','提交');
		        window.location.href = "securityCent.mht";
		     }
			 if(data.msg=="该身份证已认证过"){
		        alert("该身份证已认证过，不允许重复认证！");
		        $('#identifierSubmit').attr('value','提交');
		        window.location.href = "securityCent.mht";
		     }
			 if(data.msg=="您已三次认证"){
		        alert("您已经尝试三次身份认证，再次认证请联系客服！");
		        $('#identifierSubmit').attr('value','提交');
		        window.location.href = "securityCent.mht";
		     }
		     if(data=="noLogin"){
		     	alert("您的登录信息已失效，请重新登录！");
		        $('#identifierSubmit').attr('value','提交');
		        window.location.href = "login.mht";
		     }
	    }
	});
}

//提交email修改
function emailSubSave(){
	$("#ok").html("");
	$("emailSubmit").attr("disabled", true);
	param["paramMap.mails"] = $("#mails").val();
	param["paramMap.checkCode"] = $("#checkCode").val();
	param["paramMap.edealpwd"] = $("#edealpwd").val();
	
	if(isBlank(param["paramMap.mails"])){
		$("#ok").html("请输入你要绑定的邮箱。");
        return;
	}
	if(!validateEmail(param["paramMap.mails"])){
        $("#ok").html("请输入正确的邮箱。");
        return;
    }
    if(isBlank(param["paramMap.checkCode"])){
    	$("#ok").html("请输入你收到的验证码。");
        return;
    }
    if(isBlank(param["paramMap.edealpwd"])){
    	$("#ok").html("请输入交易密码。");
        return;
    }
	$.dispatchPost("bangEmailByCode.mht",param,function(data){
		//手机验证码
		if(data==1){
			$("#ok").html("数据错误，绑定失败。");
			return;
		} else if(data==2){
			$("#ok").html("验证码输入错误，请重新输入。");
			return;
		} else if(data==3){
			$("#ok").html("邮箱格式错误。");
			return;
		} else if(data==4){
			$("#ok").html("该邮箱已被绑定,请重新输入。");
			return;
		} else if(data==5){
			$("#ok").html("邮箱绑定失败。");
			return;
		} else if(data==6){
			$("#ok").html("邮箱绑定失败，请联系管理员");
			return;
		} else if(data==8){
			$("#ok").html("交易密码错误。");
			return;
		} else if(data==7){
			$("#ok").html("邮箱绑定成功。");
			window.location.href = "securityCent.mht";
		} 
	});
}




	function checkAll_MG(e){
		if(e.checked){
			$("input[name='mg']").attr("checked","checked");
			
		}else{
			$("input[name='mg']").removeAttr("checked");
		}
	}
		
	function checkAll_ML(e){
		if(e.checked){
			$("input[name='ml']").attr("checked","checked");
		}else{
			$("input[name='ml']").removeAttr("checked");
		}
	}
		
	function checkAll_NT(e){
		if(e.checked){
			$("input[name='nt']").attr("checked","checked");
		}else{
			$("input[name='nt']").removeAttr("checked");
		}
	}
	
	function mgcheck1(e){
	  var len = $("input[name='mg']:checked").length;
	  if(len>0){
	    $("#message").attr("checked","checked");
	  }else{
	    $("#message").removeAttr("checked");
	  }
	}
	
	function mlcheck1(e){
	  var len = $("input[name='ml']:checked").length;
	  if(len>0){
	    $("#mail").attr("checked","checked");
	  }else{
	    $("#mail").removeAttr("checked");
	  }
	}
	
	function ntcheck1(e){
	  var len = $("input[name='nt']:checked").length;
	  if(len>0){
	    $("#note").attr("checked","checked");
	  }else{
	    $("#note").removeAttr("checked");
	  }
	}

//通知设置
	function addNoteSetting(){
	 //alert($("#message").attr("checked"));
	  //站内信
	  param["paramMap.message"] = $("#message").attr("checked");
	  param["paramMap.messageReceive"] = $("#messageReceive").attr("checked");
	  param["paramMap.messageDeposit"] = $("#messageDeposit").attr("checked");
	  param["paramMap.messageBorrow"] = $("#messageBorrow").attr("checked");
	  param["paramMap.messageRecharge"] = $("#messageRecharge").attr("checked");
	  param["paramMap.messageChange"] = $("#messageChange").attr("checked");
	  //邮件
	  param["paramMap.mail"] = $("#mail").attr("checked");
	  param["paramMap.mailReceive"] = $("#mailReceive").attr("checked");
	  param["paramMap.mailDeposit"] = $("#mailDeposit").attr("checked");
	  param["paramMap.mailBorrow"] = $("#mailBorrow").attr("checked");
	  param["paramMap.mailRecharge"] = $("#mailRecharge").attr("checked");
	  param["paramMap.mailChange"] = $("#mailChange").attr("checked");
	  //短信
	  param["paramMap.note"] = $("#note").attr("checked");
	  param["paramMap.noteReceive"] = $("#noteReceive").attr("checked");
	  param["paramMap.noteDeposit"] = $("#noteDeposit").attr("checked");
	  param["paramMap.noteBorrow"] = $("#noteBorrow").attr("checked");
	  param["paramMap.noteRecharge"] = $("#noteRecharge").attr("checked");
	  param["paramMap.noteChange"] = $("#noteChange").attr("checked");
	  
	  $.dispatchPost("addNotesSetting.mht",param,function(data){
	       if(data == 1){
	          alert("通知设置失败");
	       }else if (data==3) {
			alert("未绑定邮箱,通知设置失败");
			}else{
	          alert("通知设置成功");
	       }
	  });
	}

	var Wi = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1 ];    // 加权因子   
	var ValideCode = [ 1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2 ];            // 身份证验证位值.10代表X   
	function IdCardValidate(idCard) { 
	    idCard = trim(idCard.replace(/ /g, ""));               //去掉字符串头尾空格                     
	    if (idCard.length == 15) {   
	        return isValidityBrithBy15IdCard(idCard);       //进行15位身份证的验证    
	    } else if (idCard.length == 18) {   
	        var a_idCard = idCard.split("");                // 得到身份证数组   
	        if(isValidityBrithBy18IdCard(idCard)&&isTrueValidateCodeBy18IdCard(a_idCard)){   //进行18位身份证的基本验证和第18位的验证
	            return true;   
	        }else {   
	            return false;   
	        }   
	    } else {   
	        return false;   
	    }   
	}   
	/**  
	 * 判断身份证号码为18位时最后的验证位是否正确  
	 * @param a_idCard 身份证号码数组  
	 * @return  
	 */  
	function isTrueValidateCodeBy18IdCard(a_idCard) {   
	    var sum = 0;                             // 声明加权求和变量   
	    if (a_idCard[17].toLowerCase() == 'x') {   
	        a_idCard[17] = 10;                    // 将最后位为x的验证码替换为10方便后续操作   
	    }   
	    for ( var i = 0; i < 17; i++) {   
	        sum += Wi[i] * a_idCard[i];            // 加权求和   
	    }   
	    valCodePosition = sum % 11;                // 得到验证码所位置   
	    if (a_idCard[17] == ValideCode[valCodePosition]) {   
	        return true;   
	    } else {   
	        return false;   
	    }   
	}   
	/**  
	  * 验证18位数身份证号码中的生日是否是有效生日  
	  * @param idCard 18位书身份证字符串  
	  * @return  
	  */  
	function isValidityBrithBy18IdCard(idCard18){   
	    var year =  idCard18.substring(6,10);   
	    var month = idCard18.substring(10,12);   
	    var day = idCard18.substring(12,14);   
	    var temp_date = new Date(year,parseFloat(month)-1,parseFloat(day));   
	    // 这里用getFullYear()获取年份，避免千年虫问题   
	    if(temp_date.getFullYear()!=parseFloat(year)   
	          ||temp_date.getMonth()!=parseFloat(month)-1   
	          ||temp_date.getDate()!=parseFloat(day)){   
	            return false;   
	    }else{   
	        return true;   
	    }   
	}   
	  /**  
	   * 验证15位数身份证号码中的生日是否是有效生日  
	   * @param idCard15 15位书身份证字符串  
	   * @return  
	   */  
	  function isValidityBrithBy15IdCard(idCard15){   
	      var year =  idCard15.substring(6,8);   
	      var month = idCard15.substring(8,10);   
	      var day = idCard15.substring(10,12);   
	      var temp_date = new Date(year,parseFloat(month)-1,parseFloat(day));   
	      // 对于老身份证中的你年龄则不需考虑千年虫问题而使用getYear()方法   
	      if(temp_date.getYear()!=parseFloat(year)   
	              ||temp_date.getMonth()!=parseFloat(month)-1   
	              ||temp_date.getDate()!=parseFloat(day)){   
	                return false;   
	        }else{   
	            return true;   
	        }   
	  }   
	//去掉字符串头尾空格   
	function trim(str) {   
	    return str.replace(/(^\s*)|(\s*$)/g, "");   
	}
			/**  
	 * 通过身份证判断是男是女  
	 * @param idCard 15/18位身份证号码   
	 * @return 'female'-女、'male'-男  
	 */  
	function maleOrFemalByIdCard(idCard){   
	    idCard = trim(idCard.replace(/ /g, ""));        // 对身份证号码做处理。包括字符间有空格。   
	    if(idCard.length==15){   
	        if(idCard.substring(14,15)%2==0){   
	            return 'female';   
	        }else{   
	            return 'male';   
	        }   
	    }else if(idCard.length ==18){   
	        if(idCard.substring(14,17)%2==0){   
	            return 'female';   
	        }else{   
	            return 'male';   
	        }   
	    }else{   
	        return null;   
	    }   
	}

	var i=60;
	function timedCount(){
	    i=i-1;
	    document.getElementById("sendRamEmail").value=i+"秒后重新发送";
	    document.getElementById("sendRamEmail").setAttribute("class","hui_btn");
	    document.getElementById("sendRamEmail").setAttribute("class","hui_btn");
	    $("#sendRamEmail").attr("disabled", true);
	    if(i<=0){
	        clearTimeout();
	        sendFlag = 2;
	        $("#ok").html("");
	        $("#sendRamEmail").attr("disabled", false);
	        document.getElementById("sendRamEmail").setAttribute("class","blue_btn");
	        document.getElementById("sendRamEmail").value="发送验证邮件";
	        i=60;
	    }else{
	        setTimeout("timedCount()",1000)
	    }
	}
</script>

</body>
</html>
