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
								<td class="f66" align="right" width="10%" height="36px">
									联系方式<font color="#ff0000">*</font>:
									</td>
									<td class="f66" align="left" height="36px">
									
									<s:if test="paramMap.linkType==1">电话</s:if>
									<s:elseif test="paramMap.linkType==2">QQ</s:elseif>
									<s:elseif test="paramMap.linkType==3">邮件</s:elseif>
									<s:elseif test="paramMap.linkType==4">短信</s:elseif>
									<s:else>其它</s:else>
									</td>
									</tr>
									<tr>
									<td class="f66" align="right" width="10%" height="36px">
									沟通类型<font color="#ff0000">*</font>:
									</td>
									<td>
									
									<s:if test="paramMap.visitType==1">有效</s:if>
									<s:else>无效</s:else>
									</td>
									</tr>
									<tr>
									<td class="f66" align="right" width="10%" height="36px">
									客户来源<font color="#ff0000">*</font>:
									</td>
									<td>
									
									<s:if test="paramMap.knowType==1">朋友推荐</s:if>
									<s:elseif test="paramMap.knowType==2">广告</s:elseif>
									<s:elseif test="paramMap.knowType==3">自行寻找</s:elseif>
									<s:elseif test="paramMap.knowType==4">活动现场</s:elseif>
									<s:else>其它</s:else>
									</td>
									</tr>
									<tr>
									<td class="f66" align="right" width="10%" height="36px">
									<span id="mynote">沟通内容</span><font color="#ff0000">*</font>：
									</td>
									<td>
									<textarea id="content" rows="12" cols="72"><s:property value="paramMap.content"/></textarea>
									</td>
									</tr>
									<tr>
									<td class="f66" align="right" width="10%" height="36px">
									预计下次沟通时间：
									</td>
									<td>
									<s:property value="paramMap.nextTime"/>
									</td>
									</tr>
									
									<tr>
									<td class="f66" align="right" width="10%" height="36px">
									用户名：
									</td>
									<td>
									<s:property value="paramMap.username"/>
									</td>
									</tr>
									
									<tr>
									<td class="f66" align="right" width="10%" height="36px">
									创建人：
									</td>
									<td>
									<s:property value="paramMap.createdBy"/>
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
