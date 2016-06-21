<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
	<head>
		<title>登录统计首页</title>
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
								<a href="javascript:void(0);">登录统计</a>
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
									    最后登录时间段:
									<input id="timeStart" class="Wdate" value="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:'readOnly'})"/>&nbsp;&nbsp;
									--
									<input id="timeEnd" class="Wdate" value="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:'readOnly'})"/>&nbsp;&nbsp;
								</td>
							</tr>
							<tr>
								<td class="f66" align="left" width="50%" height="36px">
									登录时间:
									<input id="ltimeStart" class="Wdate" value="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:'readOnly'})"/>&nbsp;&nbsp;
									--
									<input id="ltimeEnd" class="Wdate" value="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:'readOnly'})"/>&nbsp;&nbsp;
									登录次数:
									<input type="text" id="count" value=""  size="4"/>&nbsp;&nbsp;--
									<input type="text" id="countEnd" value="" size="4"/>
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
		initListInfo(param);
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
		praData["paramMap.count"] = $("#count").val();
		praData["paramMap.countEnd"] = $("#countEnd").val();
		praData["paramMap.ltimeStart"] = $("#ltimeStart").val();
		praData["paramMap.ltimeEnd"] = $("#ltimeEnd").val();
		praData["paramMap.mobilePhone"] = $("#mobilePhone").val();
		var sc = $("#count").val();
		var ec = $("#countEnd").val();
		if (sc != "" && ec != ""){
			if (parseInt(sc) > parseInt(ec)){
				alert("登陆次数输入错误！");
				$("#countEnd").focus();
				return;
			}
		}
		var st = $("#ltimeStart").val();
		var se = $("#ltimeEnd").val();
		if (st.length>8 && se.length>8 && st > se){
			alert("登录开始时间不能大于结束时间！");
			$("#ltimeEnd").focus();
			return;
		}

		var st1 = $("#timeStart").val();
		var se1 = $("#timeEnd").val();
		if (st1.length>8 && se1.length>8 && st1 > se1){
			alert("最后登录开始时间不能大于结束时间！");
			$("#timeEnd").focus();
			return;
		}

		
 		$.dispatchPost("loginStatisList.mht",praData,initCallBack);
 	}
	function exportInfo(praData){
		var userName = $("#userName").val();
		var realName = $("#realName").val();
		var timeStart = $("#timeStart").val();
		var timeEnd = $("#timeEnd").val();
		var count = $("#count").val();
		var countEnd = $("#countEnd").val();
		var ltimeStart = $("#ltimeStart").val();
		var ltimeEnd = $("#ltimeEnd").val();
		var mobilePhone = $("#mobilePhone").val();
		
		var sc = $("#count").val();
		var ec = $("#countEnd").val();
		if (sc != "" && ec != ""){
			if (parseInt(sc) > parseInt(ec)){
				alert("登陆次数输入错误！");
				$("#countEnd").focus();
				return;
			}
		}
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
		if (count != ""){
			pStr += "count="+count+"&";
		}
		if (countEnd != ""){
			pStr += "countEnd="+countEnd+"&";
		}
		if (ltimeStart != ""){
			pStr += "ltimeStart="+ltimeStart+"&";
		}
		if (ltimeEnd != ""){
			pStr += "ltimeEnd="+ltimeEnd+"&";
		}
		if (mobilePhone != ""){
			pStr += "mobilePhone="+mobilePhone+"&";
		}
		pStr = pStr.substring(0, pStr.length-1) 
		window.location.href="loginStatisExcel.mht" + pStr;
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
