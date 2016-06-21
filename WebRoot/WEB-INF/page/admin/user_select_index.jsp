<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
	<head>
		<title>管理首页</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" type="text/css" href="../common/date/calendar.css"/>
		<script type="text/javascript" src="../common/date/calendar.js"></script>
		<script type="text/javascript" src="../script/jquery-2.0.js"></script>
		<script type="text/javascript" src="../script/jquery.dispatch.js"></script>
		<script type="text/javascript" language="javascript">
	  	$(function(){
	    	param["pageBean.pageNum"]=1;
	    	
		    //initListInfo(param);
		    $("#dataInfo").html("");
		    
		    $("#search").click(function(){
		    param["pageBean.pageNum"] = 1;
			initListInfo(param);
		    	});
		    }
	    )
	    
	     //加载留言信息
	   function initListInfo(praData) {
		    param["paramMap.userName"] = $("#userName").val();
			param["paramMap.tausername"] = $("#tausername").val();
			param["paramMap.realName"] = $("#realName").val();
			param["paramMap.materAuthTypeId"] = $("#materAuthTypeId").val();
			param["paramMap.orgName"] = $("#orgName").val();
			param["paramMap.userType"] = $("#userType").val();
	   		$.post("queryselectinfo.mht",praData,initCallBack);
   		}
   		
   		function initCallBack(data){
		 	$("#dataInfo").html(data);
   		}
   		
   		function selectStartDate(){
			return showCalendar('startDate', '%Y-%m-%d', '24', true, 'startDate');
		}
		
		function selectEndDate(){
			return showCalendar('endDate', '%Y-%m-%d', '24', true, 'endDate');
		}
		</script>

	</head>
	
	<body style="min-width: 1000px">
		<div id="right">
			<div style="padding: 15px 10px 0px 10px;">
				<div>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
						      <td width="100" height="28" class="main_alll_h2">
								<a href="queryselectInitindex.mht">可选资料认证</a>
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
				<div
					style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: 1120px; padding-top: 10px; background-color: #fff;">
					<table style="margin-bottom: 8px;" cellspacing="0" cellpadding="0"
						width="100%" border="0">
						<tbody>
							<tr>
								<td class="f66" align="left" width="50%" height="36px">
									&nbsp;&nbsp;&nbsp;用户名：
									<input id="userName" name="paramMap.userName" />&nbsp;&nbsp; 
									企业全称：
									<input id="orgName" name="paramMap.orgName" />&nbsp;&nbsp; 
									认证状态：				
									<s:select id="materAuthTypeId"  name="paramMap.materAuthTypeId" list="#{-1:'-----请选择-----',4:'审核中的认证',3:'成功的认证',2:'失败的认证',1:'未上传的认证'}" value="#request.types" ></s:select>
								</td>
							</tr>
							<tr>
								<td class="f66" align="left" width="50%" height="36px">
									真实姓名：
									<input id="realName" name="paramMap.realName" />&nbsp;&nbsp;
									跟踪审核：
									<input id="tausername" type="text" name="paramMap.tausername"/>&nbsp;&nbsp;
									用户类型：
									<s:select id="userType" list="#{1:'个人',2:'企业'}"  name="paramMap.userType" ></s:select>
									&nbsp;&nbsp;&nbsp;&nbsp;
									<input id="search" type="button" value="搜索" name="search" />
								</td>
							</tr>
						</tbody>
					</table>
					<span id="dataInfo"> <img src="../images/admin/load.gif" class="load" alt="加载中..." /></span>
				</div>
			</div>
		</div>
	</body>
</html>
