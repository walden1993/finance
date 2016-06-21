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
			param["paramMap.auditStatus"] = $("#auditStatus").val();
			param["paramMap.adminName"] = $("#adminName").val();
			param["paramMap.realName"] = $("#realName").val();
			param["paramMap.certificateName"] = $("#certificateName").val();
			param["paramMap.orgName"] = $("#orgName").val();
			param["paramMap.userType"] = $("#userType").val();
	   		$.dispatchPost("queryPersonInfolist.mht",praData,initCallBack);
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
		
		function changeUserType() {
			var userType_selected = jQuery("#userType").val();
			var auditStatus = "<option value='-1'>-请选择-</option>";
			if (userType_selected == 2) {
				auditStatus += "<option value='2'>基本信息</option>";
			} else {
				auditStatus += "<option value='1'>工作信息</option>";
				auditStatus += "<option value='2'>基本信息</option>";
				auditStatus += "<option value='3'>联系信息</option>";
			}
			jQuery("#auditStatus").html(auditStatus);
		}
		</script>

	</head>
	<body style="min-width: 1000px">
		<div id="right">
			<div style="padding: 15px 10px 0px 10px;">
				<div>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							   <td width="100" height="28"  class="main_alll_h2">
								<a href="javascript:void(0);">基本信息审核</a>
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
									&nbsp;&nbsp;&nbsp;用户名:
									<input id="userName" name="paramMap.userName" />&nbsp;&nbsp; 
									真实姓名：
									<input id="realName" name="paramMap.realName" />&nbsp;&nbsp; 
									用户类型：
									<s:select id="userType" list="#{-1:'-请选择-',2:'企业',1:'个人'}" onchange="changeUserType()" ></s:select>
									
									<!-- 
									<s:select id="userType" list="#{-1:'-请选择-',2:'企业',1:'个人'}" ></s:select>
									 -->
									 
								</td>
							</tr>
							<tr>
								<td class="f66" align="left" width="50%" height="36px">
									企业全称:
									<input id="orgName" name="paramMap.orgName" /> 
									&nbsp;&nbsp;&nbsp;&nbsp;
									审核人：
									<input id="adminName" name="paramMap.adminName" />&nbsp;&nbsp; 
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									状态：
									<s:select id="auditStatus" list="#{-1:'-请选择-',1:'工作信息',2:'基本信息',3:'联系信息'}" ></s:select>
									—
									<s:select id="certificateName" list="#{-1:'-请选择-',4:'未填写',3:'成功',1:'待审核',2:'失败'}" value="#request.types"></s:select>
									&nbsp;&nbsp;&nbsp;&nbsp;
									<input id="search" type="button" value="搜索" name="search" />
								</td>
							</tr>
						</tbody>
					</table>
					<span id="dataInfo"><img src="../images/admin/load.gif" class="load" alt="加载中..." /> </span>
				</div>
			</div>
		</div>
		
		


	</body>
</html>
