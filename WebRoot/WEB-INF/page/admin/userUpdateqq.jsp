<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
	<head>
		<title>用户管理-用户信息管理</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../script/jquery-2.0.js"></script>
		<script type="text/javascript" src="../script/jquery.dispatch.js"></script>
		<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript">
      
			
function check(Object){
	var param1 = {};
	var retUserName = $("#retUserName").val();
	if(retUserName==""){
		$("#retNoteMsg").text("");
		$("#retId").val("");
		return false;
	}
	var userId = $("#userId").val();
	param1['paramMap.retUserName'] = retUserName;
	param1['paramMap.userId'] = userId;
	$.dispatchPost("checkRecomment.mht",param1,function(data){
		if ("0"==data){
			$("#retNoteMsg").text("您输入的用户名有误,请重新输入");
			$("#retUserName").focus();
			$("#retId").val("");
			return true;
		} else if ("-1"==data){
			$("#retNoteMsg").text("您输入的推荐人不符合规则,请重新输入");
			$("#retUserName").focus();
			$("#retId").val("");
			return false;
		} else if ("-2"==data){
			$("#retNoteMsg").text("您已经有推荐人了，不能再输入");
			$("#retId").val("");
			return false;
		} else{
			$("#retNoteMsg").text("");
			$("#retId").val(data);


			 param['paramMap.userId'] = $("#userId").val();
			  param['paramMap.retUserName'] = $("#retUserName").val();
			  param['paramMap.retId'] = $("#retId").val();
			  
			  if ($("#loginPwdHid").val() == "123456"){
				  param['paramMap.loginPwdHid'] = $("#loginPwdHid").val();
			  }
			  if ($("#tranPwdHid").val() == "654321"){
				  param['paramMap.tranPwdHid'] = $("#tranPwdHid").val();
			  }
			  $.dispatchPost("updateUserqq.mht",param,function(data){
			  var callBack = data.msg;
		      if(callBack == undefined || callBack == ''){
		          $('#right').html(data);
		          $(this).show();
		      }
			  if(data.msg == 1){
			     	alert("操作成功");
			     	var para1 = {};
			     	window.parent.initListInfo(para1);
		                  window.parent.close();//关闭弹出窗口
			        return;
			  }else{
			       	alert(data.msg);
			        return;
			  }
			  });



			
		}
  	});
	/*if ($("#retId").val()==""){
		alert("您没有修改任何信息。");
		return ;
	}*/
	
	//$(Object).hide();
	/*var pattern  = /^\d+(\.\d+)?$/;
	var flag = pattern.test($("#qq").val());
	 if(flag==false)
	  {
	   alert("请填写正确的QQ号码");
	   $(Object).show();
	   return false;
	   }else{*/
	 
	  //}
}
/**
 * 查询推荐人是否存在
 */
function checkRetUser(){
	
	var param1 = {};
	var retUserName = $("#retUserName").val();
	if(retUserName==""){
		$("#retNoteMsg").text("");
		$("#retId").val("");
		return false;
	}
	var userId = $("#userId").val();
	param1['paramMap.retUserName'] = retUserName;
	param1['paramMap.userId'] = userId;
	var xflag = true;
	$.dispatchPost("checkRecomment.mht",param1,function(data){
		if ("0"==data){
			$("#retNoteMsg").text("您输入的用户名有误,请重新输入");
			$("#retUserName").focus();
			$("#retId").val("");
			xflag = false;
			alert(xflag);
		} else if ("-1"==data){
			$("#retNoteMsg").text("您输入的推荐人不符合规则,请重新输入");
			$("#retUserName").focus();
			$("#retId").val("");
			xflag = false;
			
		} else if ("-2"==data){
			$("#retNoteMsg").text("您已经有推荐人了，不能再输入");
			$("#retId").val("");
			xflag = false;
		} else{
			$("#retNoteMsg").text("");
			$("#retId").val(data);
		}
  	});
  	alert(xflag);
	return xflag;
}

function loginPwd(){
	/*var loginPwd = $("#loginPwd");
	if(loginPwdFlag==1){
		loginPwd.val("123456");
		loginPwdFlag = 0;
		$("#loginPwdHid").val("123456");
	} else {
		loginPwd.val("初使化");
		loginPwdFlag = 1;
		$("#loginPwdHid").val("");
	}*/
	if (!confirm("确定要初使化登陆密码？")){
		return false;
	}
	var param1 = {};
	var userId = $("#userId").val();
	param1['paramMap.userId'] = userId;
	param1['paramMap.loginPwdHid'] = "123456";
	$.dispatchPost("passwordDefault.mht",param1,function(data){
		alert("修改成功！");
	});
}

function tranPwd(){
	/*var tranPwd = $("#tranPwd");
	if(tranPwdFlag==1){
		tranPwd.val("654321");
		tranPwdFlag = 0;
		$("#tranPwdHid").val("654321");
	} else {
		tranPwd.val("初使化");
		tranPwdFlag = 1;
		$("#tranPwdHid").val("");
	}*/
	
	if (!confirm("确定要初使化交易密码？")){
		return false;
	}
	var param1 = {};
	var userId = $("#userId").val();
	param1['paramMap.userId'] = userId;
	param1['paramMap.tranPwdHid'] = "654321";
	$.dispatchPost("passwordDefault.mht",param1,function(data){
		alert("修改成功！");
	});
	
}

/*
 * 修改真名，身份证号，且实名认证通过
 */
function nameCardcg(){
	if ($("#idNo").val().length == 18){
		var aflag = ValidID($("#idNo").val());
		if (aflag == false){
			return;
		}
	} else if ($("#idNo").val().length > 0 && $("#idNo").val().length != 18){
		alert("请输入18位数字的身份证号码！");
		return;
	}
	
	if (!confirm("确定要修改姓名/身份证？")){
		return false;
	}

	var realName =$("#realName").val();
	var idNo =$("#idNo").val();
	var param1 = {};
	var userId = $("#userId").val();
	param1['paramMap.userId'] = userId;
	param1['paramMap.realName'] = realName;
	param1['paramMap.idNo'] = idNo;
	$.dispatchPost("updateAuthInfo.mht",param1,function(data){
		if (data == '0'){
			alert("修改成功！");
		}else {
			$("#errMsg").html(data);
		}
		
	});
}


function getIDChar18(id) { 
    var arr = id.split(''), sum = 0, vc = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2]; 
    for (var i = 0; i < 17; i++) sum += vc[i] * parseInt(arr[i]); 
    return ['1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'][sum % 11]; 
  } 
  function ValidID(id) {
    if (/^\d{18}$/.test(id)) {
      var c = id.charAt(17), rc = getIDChar18(id); 
      if (c == rc) {
    	  return true;
      } 
      else {
    	  alert('您输入的18位身份证号码检验码错误');
    	  return false;
      }
    }
    else alert('请输入18位数字的身份证号码！'); 
    return false;
  } 
		</script>
	</head>
	<body>
		<div id="right"
			style="background-image: url(images/admin/right_bg_top.jpg); background-position: top; background-repeat: repeat-x;">
			<div style="padding: 15px 10px 0px 10px;">
				<div>
					<strong>用户信息</strong>
					<div
						style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: 1120px; padding-top: 10px; background-color: #fff;">
						
						<table style="margin-bottom: 8px;" cellspacing="0" cellpadding="0"
							width="100%" border="0">
							
							<tbody>
								<tr>
									<td class="f66" align="left" width="16%" height="36px">
										用户名：&nbsp;&nbsp;
										${username }
										&nbsp;&nbsp;
										<s:hidden id="wid" name="#request.wid" />
										<s:hidden id="userId" name="#request.userId" />
								  </td>
								  <td>
								    &nbsp;
								  </td>
								</tr>
								<s:if test="%{#request.userType==1}"> 
								<tr>
									<td class="f66" align="left" width="16%" height="36px">
										真实姓名：
										
										<input type="text" id="realName" value="${realName }" size="6" />
								  </td>
								  <td class="f66" align="left"  height="36px">
								   		身份证号：
								   		<input type="text" id="idNo" value="${idNo}"  size="18"  />
										&nbsp;
										<input type="button" id="modifyNameCard" value="修改" onclick="nameCardcg()"/>
								  </td>
								</tr>
								</s:if>
								<s:else>
								<tr>
									<td class="f66" align="left" width="16%" height="36px">
										企业名称：
										${orgName }
								  </td>
								  <td class="f66" align="left"  height="36px">
								   		&nbsp;
								  </td>
								</tr>
								</s:else>
								<tr>
									<td class="f66" align="left" width="16%" height="36px">
									网站积分：${rating }
									</td>
									<td class="f66" align="left" width="50%" height="36px">
									信用积分：${creditrating }
									</td>
								</tr>
								<tr>
									<td class="f66" align="left" width="16%" height="36px">
									注册日期：${createTime }
									</td>
									<td class="f66" align="left" width="50%" height="36px">
								          最后登录IP：${lastIP }
									</td>
								</tr>
								<tr>
									<td class="f66" align="left" width="16%" height="36px">
									邮箱：${email }
									</td>
									<td>
									&nbsp;
									</td>
								</tr>
								<tr>
									<td class="f66" align="left" width="16%" height="36px">
									绑定手机号码：${cellPhone }
									</td>
									<td class="f66" align="left" width="50%" height="36px">
									&nbsp;
									</td>
								</tr>
								
							</tbody>
						</table>
						
					</div>
					<div
                        style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: 1120px; padding-top: 10px; background-color: #fff;">
                        
                        <table style="margin-bottom: 8px;" cellspacing="0" cellpadding="0"
                            width="50%" border="0">
					
					
					<!-- tr>
					   <td class="f66" align="left" width="80%" height="36px">
					      QQ账号：
					      <input style="width:100px;" id="qq"  value="${qq }" />
					   &nbsp;&nbsp;
					   
					   </td>
					</tr -->
					<tr  class="f66" align="left" width="16%" height="36px">
					<td>推荐人 
						<input type="text" name="retUserName" id="retUserName"  value="${retUser}"/><span id="retNoteMsg" style="color:red"> </span>
					</td>
					<td></td>
					</tr>
					<tr  class="f66" align="left" width="16%" height="36px">
					<td >登录密码:  <input type="button" name="loginPwd" id="loginPwd" value="初使化" onclick="loginPwd()"/> </td>
					<td>交易密码： <input type="button" name="tranPwd" id="tranPwd" value="初使化" onclick="tranPwd()"/> </td>
					</tr>
					
					<tr  class="f66" align="left" width="16%" height="36px">
					<td>初始密码: 123456 </td>
					<td>初始密码： 654321 </td>
					</tr>
					
					</table>
					<input type="hidden" id="loginPwdHid" value=""/>
					<input type="hidden" id="tranPwdHid" value=""/>
					<input type="hidden" id="retId" value=""/>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					 <button id="btnSave" style="background-image: url('../images/admin/btn_queding.jpg');width: 70px;border: 0;height: 26px" onclick="check(this);"></button>
					</div>
					</div>
			</div>
			</div>
			<div id="errMsg"></div>
	</body>
</html>
