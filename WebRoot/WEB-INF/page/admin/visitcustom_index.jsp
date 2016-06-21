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
		<link id="skin" rel="stylesheet" href="../css/jbox/Gray/jbox.css" />
	</head>
	<body>
		<div id="right">
			<div style="padding: 15px 10px 0px 10px;">
				<div>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
						  <td width="100" height="28"  class="main_alll_h2">
								<a href="javascript:void(0);">客服回访管理</a>
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
								<td class="f66" align="left" width="80%" height="36px">
									用户名:
									<input id="userName" value="" class="inp80" size="12"/>&nbsp;&nbsp;
									注册时间:
									<input id="regTimes" class="Wdate" value="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:'readOnly'})"/>--
									<input id="regTimee" class="Wdate" value="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:'readOnly'})"/>&nbsp;&nbsp;
									沟通次数:
									<input id="linkNos" type="number" value="" size="3"/>--
									<input id="linkNoe" type="number" value="" size="3"/><br/>
								</td>
							</tr>
							<tr>
								<td class="f66" align="left" width="80%" height="36px">
									投资金额:
									<input id="investAmts" type="number" value="" />--
									<input id="investAmte" type="number" value="" />&nbsp;&nbsp;
									
									账户可用余额
									<input id="usableAmts" type="number" value="" />--
									<input id="usableAmte" type="number" value="" />&nbsp;&nbsp;
									
									<input id="search" type="button" value="查找" name="search" /> &nbsp;&nbsp;
									<!-- input id="excelExport" type="button" value="导出" name="excelExport" /> -->
									<input id="export" type="button" value="导入回访记录" name="export" /> &nbsp;&nbsp;
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
<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../script/jquery.dispatch.js"></script>
<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="../script/jbox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript" src="../script/jbox/jquery.jBox-zh-CN.js"></script>
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
		
		$("#export").click(function(){
			var url= "visitCustomBindInit.mht";
            $.jBox("iframe:"+url, {
            title: "批量导入回访记录",
            width: 400,
            height: 200,
            buttons: { '关闭': false }
        });
		})
	});
	
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
	function exportInfo(praData){
		var userName = $("#userName").val();
		var realName = $("#realName").val();
		var timeStart = $("#timeStart").val();
		var timeEnd = $("#timeEnd").val();
		var ltimeStart = $("#ltimeStart").val();
		var ltimeEnd = $("#ltimeEnd").val();
		var rtimeStart = $("#rtimeStart").val();
		var rtimeEnd = $("#rtimeEnd").val();
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
		window.location.href="userstaticsExcel.do" + pStr;
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
