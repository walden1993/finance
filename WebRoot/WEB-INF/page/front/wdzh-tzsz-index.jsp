<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
  <head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  </head>

<body>
<!-- 引用头部公共部分 -->
    <div class="r_main">
      <div >
        <ul>
    					<%-- <li  onclick="jumpUrl('owerInformationInit.mht');">
								详细信息
							</li>
								
							<li onclick="loadWorkInit('queryWorkInit.mht');">
								工作认证信息
							</li> 
							
							<li onclick="jumpUrl('updatePasswordAndDealpwd.mht');">
								修改密码
							</li>
							<li id="li_bp" onclick="bindingPhoneLoad('boundcellphone.mht');">
								更换手机
							</li>
							<li class="on" onclick="jumpUrl('szform.mht');">
								通知设置
							</li>--%>
							<!-- <li id="li_tx"  onclick="loadBankInfo('yhbound.mht');">
								银行卡设置
							</li>
							
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
    <table width="100%" border="0" cellspacing="0" cellpadding="0" style="line-height:42px;">
  <tr>
    <th colspan="2" align="left" style="padding-top:0px;"> 选择通知方式</th>
    </tr>
  <tr>
    <td align="left" ><input type="checkbox" id="message" onclick="checkAll_MG(this); "/> 站内信通知</td>
    <td>
      <input type="checkbox" id="messageReceive" class="mg" onclick="mgcheck1(this);" />
      收到还款 
      <input type="checkbox" id="messageDeposit"  class="mg" onclick="mgcheck1(this);"/>
      提现成功 
      <input type="checkbox" id="messageBorrow" class="mg" onclick="mgcheck1(this);"/>
      借款成功 
      <input type="checkbox" id="messageRecharge"  class="mg" onclick="mgcheck1(this);"/>
      充值成功 
      <input type="checkbox" id="messageChange" class="mg" onclick="mgcheck1(this);"/>
      资金变化</td>
  </tr>
  <tr>
    <td align="left" >
    <input type="checkbox" id="mail" onclick="checkAll_ML(this); "/> 邮件通知</td>
    <td>
      <input type="checkbox" id="mailReceive"  class="ml" onclick="mlcheck1(this);"/>
      收到还款 
      <input type="checkbox" id="mailDeposit"  class="ml" onclick="mlcheck1(this);"/>
      提现成功 
      <input type="checkbox" id="mailBorrow" class="ml" onclick="mlcheck1(this);"/>
      借款成功 
      <input type="checkbox" id="mailRecharge" class="ml" onclick="mlcheck1(this);"/>
      充值成功 
      <input type="checkbox" id="mailChange" class="ml" onclick="mlcheck1(this);"/>
      资金变化</td>
  </tr>
  <tr>
    <td align="left" >
    <input type="checkbox" id="note" onclick="checkAll_NT(this); "/> 短信通知</td>
    <td>
      <input type="checkbox" id="noteReceive" class="nt" onclick="ntcheck1(this);"/>
      收到还款 
      <input type="checkbox" id="noteDeposit" class="nt" onclick="ntcheck1(this);"/>
      提现成功 
      <input type="checkbox" id="noteBorrow" class="nt" onclick="ntcheck1(this);"/>
      借款成功 
      <input type="checkbox" id="noteRecharge" class="nt" onclick="ntcheck1(this);"/>
      充值成功 
      <input type="checkbox" id="noteChange" class="nt" onclick="ntcheck1(this);"/>
      资金变化</td>
  </tr>
  <tr>
    <td align="left">&nbsp;</td>
    <td class="txt"><strong>注：尊敬的用户，您在三好资本网的相关操作，
    三好资本网会用以上三种方式<br />通知您，请您选择适合自己的通知方式！
    (系统默认以站内信通知)短信通知每条 ${smsFee} 元。</strong></td>
  </tr>
  <tr>
    <td align="left">&nbsp;</td>
    <td style="padding-top:10px;">
    <a href="javascript:void(0);" class="button w140" onclick="addNoteSetting();">确认</a></td>
  </tr>
    </table>
</div>
    </div>
    </div>
</div>
<!-- 引用底部公共部分 -->     
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
		   //$('#li_5').addClass('on');
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
	   });
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
	 	function checkAll_MG(e){
	   		if(e.checked){
	   			$(".mg").attr("checked","checked");
	   		}else{
	   			$(".mg").removeAttr("checked");
	   		}
   		}
   			
   		function checkAll_ML(e){
	   		if(e.checked){
	   			$(".ml").attr("checked","checked");
	   		}else{
	   			$(".ml").removeAttr("checked");
	   		}
   		}
   			
   		function checkAll_NT(e){
	   		if(e.checked){
	   			$(".nt").attr("checked","checked");
	   		}else{
	   			$(".nt").removeAttr("checked");
	   		}
   		}
   		
   		function mgcheck1(e){
   		  var len = $("input[class='mg']:checked").length;
   		  if(len>0){
   		    $("#message").attr("checked","checked");
   		  }else{
   		    $("#message").removeAttr("checked");
   		  }
   		}
   		
   		function mlcheck1(e){
   		  var len = $("input[class='ml']:checked").length;
   		  if(len>0){
   		    $("#mail").attr("checked","checked");
   		  }else{
   		    $("#mail").removeAttr("checked");
   		  }
   		}
   		
   		function ntcheck1(e){
   		  var len = $("input[class='nt']:checked").length;
   		  if(len>0){
   		    $("#note").attr("checked","checked");
   		  }else{
   		    $("#note").removeAttr("checked");
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
	</script>

</body>
</html>
