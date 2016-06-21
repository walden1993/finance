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
    <link rel="stylesheet" type="text/css" href="css/user.css" />
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
      						<li  onclick="jumpUrl('owerInformationInit.mht');">
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
							<li id="li_bp" onclick="bindingPhoneLoad('boundcellphone.mht');">
								更换手机
							</li>
							<li onclick="jumpUrl('szform.mht');">
								通知设置
							</li>
							<li id="li_tx" class="on" onclick="loadBankInfo('yhbound.mht');">
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
    <th colspan="2" align="left" style="padding-top:0px;"> 提现银行<br /></th>
    </tr>
  <tr>
    <td width="18%" align="right">账户名称：<br /></td>
    <td width="83%"><span class="txt" id="cardUserName1">
    <s:if test="%{#request.realName==''}">
    	<s:property value="#request.realName" default="---" ></s:property>
    </s:if><s:else>
    <shove:sub value="#request.realName" size="1" suffix="***"/>*
    </s:else>
    </span><br /></td>
  </tr>
  <tr>
    <td align="right">开户行：<br /></td>
    <td><input type="text" class="inp188" id="bankName1" />
      <span class="txt">输入您的开户银行名称</span><br /></td>
  </tr>
  <tr>
    <td align="right">支行：<br /></td>
    <td><input type="text" class="inp188" id="subBankName1" />
      <span class="txt">输入您的开户支行</span><br /></td>
  </tr>
  <tr>
    <td align="right">卡号：<br /></td>
    <td><input type="text" class="inp188"  id="bankCard1"/>
      <span class="txt">输入您的卡号</span><br /></td>
  </tr>
  <tr>
    <td align="right">交易密码：<br /></td>
    <td><input type="password" class="inp188"  id="dealpwd"/>
      <span class="txt">输入您的交易密码</span><br /></td>
  </tr>
  <tr>
    <td align="right">&nbsp;<br /></td>
    <td style="padding-top:10px;">
    <input class="bcbtn" id="addbank" value="提交"/>
    <!--<a href="javascript:void(0);" class="bcbtn" onclick="addBankInfo()">提交</a>
    --><br /></td>
  </tr>
  <tr>
  		<td colspan="2"><span style="color: red; float: none;" id="bk1_tip" class="formtips"></span></td>
  </tr>
    </table>

    </div>
   	<div class="biaoge2">
    </div>
      <span id="bankInfo"></span>
    </div>
  </div>
    </div>
  </div>
</div>
<!-- 引用底部公共部分 -->     
<jsp:include page="/include/footer.jsp"></jsp:include>

<script type="text/javascript" src="script/jquery.dispatch.js"></script>
<script type="text/javascript" src="script/nav-zh.js"></script>
<script type="text/javascript" src="common/date/calendar.js"></script>
 <script type="text/javascript" src="css/popom.js"></script>    
<script type="text/javascript" src="script/nav-zh.js"></script>
 <script type="text/javascript" src="script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="script/jbox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript" src="script/jbox/jquery.jBox-zh-CN.js"></script>
<script>

  $(function(){
  		//displayDetail('3');
	    $('#bankCardNew').addClass('on');
		//$('#li_5').addClass('on');
		$("#jk_hover div").removeClass('none');
	});
     
  function jumpUrl(obj){
          window.location.href=obj;
       }
  
</script>
	<script>
	   $(function(){
		   dqzt(4);
		   $('#li_5').addClass('on');
		   var bb = '${person}';//设置提现银行卡必须先填写个人资料
	 		if (bb == "0") {
	 			alert("请先完善个人信息");
	 			window.location.href="owerInformationInit.mht";
	 		} 
	   });
	}
	</script>
	<script>	
	$(function(){
		 $.dispatchPost("queryBankInfoInit.mht",null,function(data){
			    $("#bankInfo").html(data);
		  });
	       //提现银行卡设置1
	       $("#bankName1").blur(function(){
			     if($("#bankName1").val()==""){
			       $("#bk1_tip").html("开户银行名称不能为空");
			     }else{
			       $("#bk1_tip").html("");
			     }
		    });
	       $("#dealpwd").blur(function(){
			     if($("#dealpwd").val()==""){
			       $("#bk1_tip").html("交易密码不能为空");
			     }else{
			       $("#bk1_tip").html("");
			     }
		    });
		    $("#subBankName1").blur(function(){
			     if($("#subBankName1").val()==""){
			       $("#bk1_tip").html("开户支行不能为空");
			     }else{
			       $("#bk1_tip").html("");
			     }
		    });
		    
		    $("#bankCard1").blur(function(){
			     if($("#bankCard1").val()==""){
			       $("#bk1_tip").html("卡号不能为空");
			     }else if(isNaN($("#bankCard1").val())){
			       $("#bk1_tip").html("请输入正确的银行卡号");
			     }else{
			       $("#bk1_tip").html("");
			     }
		    });
	       
	       //提现银行卡设置2
	       $("#bankName2").blur(function(){
			     if($("#bankName2").val()==""){
			       $("#bk2_tip").html("开户银行名称不能为空");
			     }else{
			       $("#bk2_tip").html("");
			     }
		    });
		    
		    $("#subBankName2").blur(function(){
			     if($("#subBankName2").val()==""){
			       $("#bk2_tip").html("开户支行不能为空");
			     }else{
			       $("#bk2_tip").html("");
			     }
		    });
		    
		    $("#bankCard2").blur(function(){
			     if($("#bankCard2").val()==""){
			       $("#bk2_tip").html("卡号不能为空");
			     }else if(isNaN($("#bankCard2").val())){
			       $("#bk2_tip").html("请输入正确的银行卡号");
			     }else{
			       $("#bk2_tip").html("");
			     }
		    });
		});
		
		$("#addbank").click(function(){   
		    addBankInfo();
		     
		  
		});
	   //添加提现银行信息
	   function addBankInfo(){
	    if($("#bankName1").val()=="" || $("#subBankName1").val()=="" ||$("#bankCard1").val()=="" || $("#dealpwd").val()=="" ){
           $("#bk1_tip").html("请完整填写信息");
           return;
         }else if(isNaN($("#bankCard1").val())){
           $("#bk1_tip").html("请输入正确的银行卡号");
           return;
         }

	     param["paramMap.bankName"] = $("#bankName1").val();
         param["paramMap.subBankName"] = $("#subBankName1").val();
         param["paramMap.bankCard"] = $("#bankCard1").val();
         param["paramMap.cardUserName"] = $("#cardUserName1").text();
         param["paramMap.dealpwd"] = $("#dealpwd").val();
         
         if(!window.confirm("确定要添加银行卡绑定吗?")){
	 			return;
	 		}else{
	 		   $("#addbank").attr("disabled",true);
	 		}
         
         $.dispatchPost("addBankInfo.mht",param,function(data){
               if(data == 1){
               	$("#bk1_tip").html("请输入有效的银行卡信息");
               	$("#bankCard1").attr("value","");//银行卡信息错误，只清空银行卡信息
               	return;
               }else if(data == 2){
                  alert("你已经添加了两张银行卡信息，未绑定的银行卡信息可以删除\n否则需要申请更换银行");
               }else if(data == 3){
                  alert("请正确填写参数");
               }else if(data == 5){
                  alert("交易密码错误");
               }else{
 			    $("#bankInfo").html(data);
 			   }
               

 			   $("#bankName1").attr("value","");
 			   $("#subBankName1").attr("value","");
 			   $("#bankCard1").attr("value","");
 			  $("#dealpwd").attr("value","");
 			   $("#addbank").attr("disabled",false);
 			});
	   }
	   function jump(url) {
			
			alert("请先回答密保问题！");
			window.location.href = url;
	}
	   function addBankInfo2(){
	    if($("#bankName2").val()=="" || $("#subBankName2").val()=="" ||$("#bankCard2").val()=="" ){
           $("#bk2_tip").html("请完整填写信息");
           return;
         }else if(isNaN($("#bankCard2").val())){
           $("#bk2_tip").html("请输入正确的银行卡号");
           return;
         }
	     param["paramMap.bankName"] = $("#bankName2").val();
         param["paramMap.subBankName"] = $("#subBankName2").val();
         param["paramMap.bankCard"] = $("#bankCard2").val();
         param["paramMap.cardUserName"] = $("#cardUserName2").text();
         
         if(!window.confirm("确定要添加银行卡绑定吗?")){
	 			return;
	 		}
	 		
         $.dispatchPost("addBankInfo.mht",param,function(data){
               if(data == 1){
               	$("#bk2_tip").html("请输入有效的银行卡信息");
               	$("#bankCard2").attr("value","");//银行卡信息错误，只清空银行卡信息
               	return ;
               }else if(data == 2){
                  alert("你已经添加了两张银行卡信息，未绑定的银行卡信息可以删除\n否则需要申请更换银行");
               }else if(data == 3){
                  alert("请正确填写参数");
               }else{
 			    $("#bankInfo").html(data);
 			   }
 			   $("#bankName2").attr("value","");
 			   $("#subBankName2").attr("value","");
 			   $("#bankCard2").attr("value","");
 			});
	   }
	   function tipjBox(content)
	   {
		   $.jBox.tip(content);
	   }

	   function closejBox()
	   {
		   tipjBox("变更成功，请等待审核");
			history.go(0);
	   }
	   //删除添加的银行卡信息
	   function del(id){
	 		if(!window.confirm("确定要删除吗?")){
	 			return;
	 		}
 			$.dispatchPost("deleteBankInfo.mht",{bankId:id},function(data){
 			    $("#bankInfo").html(data);
 			});
	 	}
	 	
	 	 function changeBankCancel(id){
	 	 if(!window.confirm("确定要取消变更吗?")){
	 			return;
	 		}
	       $.dispatchPost("bankChangeCancel.mht",{bankId:id},function(data){
	        if(data == 1){
	          alert("取消失败，请重新取消");
	          return;
	        }
	         $("#bankInfo").html(data);
	       });
	     }
	</script>

 	<script>


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
 	 function bindingPhoneLoad(url) {
		/**	var bb = '${person}';//申请手机绑定必须先填写个人资料
			var cc = '${pass}';
			if (bb == "0") {
				alert("请先完善个人信息");
				window.location.href='owerInformationInit.mht';
			} else if (cc != 3) {
				alert("请等待个人信息审核通过");
				return ;
			} else {*/
				   window.location.href=url;
	//		}
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
	    function changeBankInfos(id){
   		       var url = "queryOneBankInfo.mht?bankId="+id;
   		    $.jBox("iframe:" + url, {
      		     title: "银行卡变更",
      		     width: 600,
      		     height: 400,
      		     buttons: {  }
   		 });
   		    }
	</script>
</body>
</html>
