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
								<a href="javascript:void(0);">用户统计</a>
							</td>
							<td width="2">
								&nbsp;
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
								<td class="f66" align="left" width="50%" height="36px">
									用户名:
									<input id="userName" value="" class="inp80" />&nbsp;&nbsp;
									真实姓名:
									<input id="realName" value=""  class="inp80" />&nbsp;&nbsp;
									手机号码：
									<input id="mobilePhone" value=""  class="inp80" />&nbsp;&nbsp;
									是否公司员工：
									<select id="isEmployee">
										<option value="">请选择 </option>
										<option value="1"> 是</option>
										<option value="0"> 否</option>
									</select>
									   投资时间:
									<input id="timeStart" class="Wdate" value="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:'readOnly'})"/>&nbsp;&nbsp;
									--
									<input id="timeEnd" class="Wdate" value="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:'readOnly'})"/>&nbsp;&nbsp;
									</td>
									</tr>
									<tr>
									<td class="f66" align="left" width="50%" height="36px">
									充值时间:
									<input id="ltimeStart" class="Wdate" value="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:'readOnly'})"/>&nbsp;&nbsp;
									--
									<input id="ltimeEnd" class="Wdate" value="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:'readOnly'})"/>&nbsp;&nbsp;
									注册时间:
									<input id="rtimeStart" class="Wdate" value="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:'readOnly'})"/>&nbsp;&nbsp;
									--
									<input id="rtimeEnd" class="Wdate" value="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:'readOnly'})"/>&nbsp;&nbsp;
									可用余额：
									<input id="usableAmts"  value="" size="5"/>&nbsp;&nbsp;
									--
									<input id="usableAmte" value=""  size="8"/>&nbsp;&nbsp;<br/>
								</td>
							</tr>
							<tr>
							<td class="f66" align="left" width="50%" height="36px">
									可用余额时间点:
									<input id="usableTimeStart" class="Wdate" value="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:'readOnly'})"/>&nbsp;&nbsp;
									用户类型：
									<select id="userType">
										<option value="">请选择 </option>
										<option value="1"> 个人</option>
										<option value="2"> 企业</option>
									</select>
									<input id="search" type="button" value="查找" name="search" /> &nbsp;&nbsp;
									<input id="excelExport" type="button" value="导出" name="excelExport" />
								</td>
							</tr>
						</tbody>
					</table>
		             <span id="divList"><img src="../images/admin/load.gif" class="load" alt="加载中..." /></span>
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
		//initListInfo(param);
		$('#divList').html("");
		$('#search').click(function(){
		   param["pageBean.pageNum"]=1;
		   initListInfo(param);
		});
		
		$('#excelExport').click(function(){
		   param["pageBean.pageNum"]=1;
		   exportInfo(param);
		});
	});			
	function initListInfo(praData){
		praData["paramMap.userName"] = $("#userName").val();
		praData["paramMap.realName"] = $("#realName").val();
		praData["paramMap.timeStart"] = $("#timeStart").val();
		praData["paramMap.timeEnd"] = $("#timeEnd").val();
		praData["paramMap.rtimeStart"] = $("#rtimeStart").val();
		praData["paramMap.rtimeEnd"] = $("#rtimeEnd").val();
		praData["paramMap.ltimeStart"] = $("#ltimeStart").val();
		praData["paramMap.ltimeEnd"] = $("#ltimeEnd").val();
		praData["paramMap.mobilePhone"] = $("#mobilePhone").val();
		praData["paramMap.isEmployee"] = $("#isEmployee").val();
		praData["paramMap.usableAmts"] = $("#usableAmts").val();
		praData["paramMap.usableAmte"] = $("#usableAmte").val();
		praData["paramMap.usableTimeStart"] = $("#usableTimeStart").val();
		praData["paramMap.userType"] = $("#userType").val();
		var sc = $("#count").val();
		var ec = $("#countEnd").val();
		
 		$.dispatchPost("userstaticsList.mht",praData,initCallBack);
 	}
	function exportInfo(praData){
		var userName = $("#userName").val();
		var realName = $("#realName").val();
		var timeStart = $("#timeStart").val();
		var timeEnd = $("#timeEnd").val();
		var ltimeStart = $("#ltimeStart").val();
		var ltimeEnd = $("#ltimeEnd").val();
		var rtimeStart = $("#rtimeStart").val();
		var rtimeEnd = $("#rtimeEnd").val();
		var usableTimeStart = $("#usableTimeStart").val();
		var mobilePhone = $("#mobilePhone").val();
		var isEmployee = $("#isEmployee").val();
		var usableAmts = $("#usableAmts").val();
		var usableAmte = $("#usableAmte").val();
		
		var pStr = "?";
		if (userName != ""){
			pStr += "userName="+userName+"&";
		}
		if (realName != ""){
			pStr += "realName="+realName+"&";
		}
		if (timeStart != ""){
			pStr += "timeStart="+timeStart+"&";
		}
		if (timeEnd != ""){
			pStr += "timeEnd="+timeEnd+"&";
		}
		if (ltimeStart != ""){
			pStr += "ltimeStart="+ltimeStart+"&";
		}
		if (ltimeEnd != ""){
			pStr += "ltimeEnd="+ltimeEnd+"&";
		}
		if (rtimeStart != ""){
			pStr += "rtimeStart="+rtimeStart+"&";
		}
		if (rtimeEnd != ""){
			pStr += "rtimeEnd="+rtimeEnd+"&";
		}
		
		if (usableTimeStart != ""){
			pStr += "usableTimeStart="+usableTimeStart+"&";
		}
		
		if (mobilePhone != ""){
			pStr += "mobilePhone="+mobilePhone+"&";
		}
		if (isEmployee != ""){
			pStr += "isEmployee="+isEmployee+"&";
		}
		if (usableAmts != ""){
			pStr += "usableAmts="+usableAmts+"&";
		}
		if (usableAmte != ""){
			pStr += "usableAmte="+usableAmte+"&";
		}
		
		pStr = pStr.substring(0, pStr.length-1) 
		window.location.href="userstaticsExcel.mht" + pStr;
	    setTimeout("test()",3000);
 	}
 	
 	function initCallBack(data){
		$("#divList").html(data);
	}
 	function test(){
	   $("#excelExport").attr("disabled",false);
	}
</script>
</html>
