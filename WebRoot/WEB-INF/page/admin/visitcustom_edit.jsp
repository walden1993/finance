<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
	<head>
		<title>用户统计首页</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
	</head>
	<body>
		<div id="right">
			<div style="padding: 15px 10px 0px 10px;">
				<div>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
						  <td width="100" height="28"  class="main_alll_h2">
								<a href="#">修改客服回访信息</a>
							</td>
							<td width="2" class="">
							</td>
							<td>
								&nbsp;
							</td>
						</tr>
					</table>
				</div>
				<div style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: 1120px; padding-top: 10px; background-color: #fff;">
					<table style="margin-bottom: 8px;" cellspacing="0" cellpadding="0"
						width="100%" border="0">
						<tbody>
							<tr>
								<td class="f66" align="right" width="30%" height="36px">
									联系方式<font color="#ff0000">*</font>:
									</td>
									<td class="f66" align="left" height="36px">
									<select id="linkType">
									<option value="" >请选择</option>
									<option value="1" <s:if test="paramMap.linkType==1">selected</s:if>>电话</option>
									<option value="2" <s:if test="paramMap.linkType==2">selected</s:if>>QQ</option>
									<option value="3" <s:if test="paramMap.linkType==3">selected</s:if>>邮件</option>
									<option value="4" <s:if test="paramMap.linkType==4">selected</s:if>>短信</option>
									</select>&nbsp;&nbsp;
									</td>
									</tr>
									<tr>
									<td class="f66" align="right" width="30%" height="36px">
									沟通类型<font color="#ff0000">*</font>:
									</td>
									<td>
									<select id="visitType" onchange="myvalue(this);">
									<option value="">请选择</option>
									<option value="1" <s:if test="paramMap.visitType==1">selected</s:if>>有效</option>
									<option value="0" <s:if test="paramMap.visitType==0">selected</s:if>>无效</option>
									</select>&nbsp;&nbsp;
									</td>
									</tr>
									<tr>
									<td class="f66" align="right" width="30%" height="36px">
									客户来源:
									</td>
									<td>
									<select id="knowType">
									<option value="">请选择</option>
									<option value="1" <s:if test="paramMap.knowType==1">selected</s:if>>朋友推荐</option>
									<option value="2" <s:if test="paramMap.knowType==2">selected</s:if>>广告</option>
									<option value="3" <s:if test="paramMap.knowType==3">selected</s:if>>自行寻找</option>
									<option value="4" <s:if test="paramMap.knowType==4">selected</s:if>>活动现场</option>
									<option value="5" <s:if test="paramMap.knowType==5">selected</s:if>>网络</option>
									<option value="6" <s:if test="paramMap.knowType==6">selected</s:if>>微信</option>
									</select>&nbsp;&nbsp;
									</td>
									</tr>
									<tr>
									<td class="f66" align="right" width="30%" height="36px">
									<span id="mynote">沟通内容</span><font color="#ff0000">*</font>：
									</td>
									<td>
									<textarea id="content" rows="12" cols="72"><s:property value="paramMap.content"/></textarea>
									</td>
									</tr>
									<tr>
									<td class="f66" align="right" width="30%" height="36px">
									预计下次沟通时间：
									</td>
									<td>
									<input id="nextTime" class="Wdate" value="<s:property value="paramMap.nextTime"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:'readOnly'})"/>
									</td>
									</tr>
									<tr>
									<td >
									
									</td>
									<td align="left">
									<input type="hidden" id="userId" value="${ paramMap.userId }"/>
									<input type="hidden" id="createdbBy" value="${ paramMap.createdbBy }"/>
									<input type="hidden" id="adminName" value="${ paramMap.adminName }"/>
									<input type="hidden" id="id" value="${ paramMap.id }"/>
									<input type="button" value="修改" id="saveBtn" /> &nbsp;&nbsp;
									</td>
									</tr>
						</tbody>
					</table>
					<div>
	</div>
</div>
			</div>
		</div>
	</body>
<script type="text/javascript" src="../script/jquery-2.0.js"></script>
<script type="text/javascript" src="../script/jquery.dispatch.js"></script>
<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
	$(function(){
		if (${paramMap.visitType}==0){
			$("#mynote").html("无效原因");
		}

		
		$('#saveBtn').click(function(){
			if($("#linkType").val() ==""){
				alert("请选择【联系方式】！");
				return;
			}
			if($("#visitType").val() ==""){
				alert("请选择【沟通类型】！");
				return;
			}
			if($("#content").val() ==""){
				alert("请填写【沟通内容】！");
				return;
			}
			if ($("#createdbBy").val()== $("#adminName").val()){
				alert("只能编辑自己提交的记录！");
				return;
			}
			param["paramMap.linkType"] = $("#linkType").val();
			param["paramMap.visitType"] = $("#visitType").val();
			param["paramMap.knowType"] = $("#knowType").val();
			param["paramMap.userId"] = $("#userId").val();
			param["paramMap.content"] = $("#content").val();
			param["paramMap.nextTime"] = $("#nextTime").val();
			param["paramMap.id"] = $("#id").val();
	 	    $.dispatchPost("editVisitCustom.mht",param,initCallBack2);
		});
	});

	function initCallBack2(){
		alert("修改成功");
		window.location.href="visitcustom.mht";
	}

	
	function initListInfo(praData){
		praData["paramMap.userName"] = $("#userName").val();
		praData["paramMap.regTimes"] = $("#regTimes").val();
		praData["paramMap.regTimee"] = $("#regTimee").val();
		praData["paramMap.linkNos"] = $("#linkNos").val();
		praData["paramMap.linkNoe"] = $("#linkNoe").val();
		praData["paramMap.investAmts"] = $("#investAmts").val();
		praData["paramMap.investAmte"] = $("#investAmte").val();
		praData["paramMap.usableAmts"] = $("#usableAmts").val();
		praData["paramMap.usableAmte"] = $("#usableAmte").val();
 		$.dispatchPost("visitCustomList.mht",praData,initCallBack);
 	}
	function myvalue(obj){
		var a = obj.value;
		if (a == 0){
			$("#mynote").html("无效原因");
		} else{
			$("#mynote").html("沟通内容");
		}
	}
 	
 	function initCallBack(data){
		$("#divList").html(data);
	}
 	function test(){
	   $("#excelExport").attr("disabled",false);
	}
</script>
</html>
