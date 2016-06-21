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
	    );
	    
	   function initListInfo(praData) {
		    param["paramMap.userName"] = $("#userName").val();
			param["paramMap.auditStatus"] = $("#auditStatus").val();
			param["paramMap.adminName"] = $("#adminName").val();
			param["paramMap.realName"] = $("#realName").val();
			param["paramMap.certificateName"] = $("#certificateName").val();
			param["paramMap.orgName"] = $("#orgName").val();
			param["paramMap.userType"] = $("#userType").val();
	   		$.dispatchPost("rechargeecordsInf.mht",praData,initCallBack);
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
			var certificateName = "<option value='-1'>-请选择-</option>";
			if (userType_selected == 2) {
				certificateName += "<option value='17'>营业执照 </option>";
				certificateName += "<option value='18'>税务登记证  </option>";
				certificateName += "<option value='19'>组织机构代码  </option>";
			} else {
				certificateName += "<option value='1'>身份证认证 </option>";
				certificateName += "<option value='2'>工作认证</option>";
				certificateName += "<option value='3'>信用报告验证</option>";
				certificateName += "<option value='4'>居住地验证 </option>";
				certificateName += "<option value='5'>收入验证</option>";
				}
			jQuery("#certificateName").html(certificateName);
		}
		</script>

	</head>
	<body style="min-width: 1000px">
		<div id="right">
			<div style="padding: 15px 10px 0px 10px;">
				<div>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
						  <td width="100" height="30">
								<a href="javascript:void(0);" class="main_alll_h2">基本资料认证</a>
							</td>
							<td width="2">&nbsp;
								
							</td>
							<td>&nbsp;
								
							</td>
						</tr>
					</table>
				</div>
				<div style="padding-right: 10px; padding-bottom: 10px; width: 1120px; padding-top: 10px; background-color: #fff;">
					<table style="margin-bottom: 8px;" cellspacing="0" cellpadding="0"
						width="100%" border="0">
						<tbody>
							<tr>
								<td class="f66" align="left" width="50%" height="36px">
									&nbsp;&nbsp;&nbsp;用户名：
									<input id="userName" name="paramMap.userName" />&nbsp;&nbsp; 
									企业全称：
									<input id="orgName" name="paramMap.orgName" />&nbsp;&nbsp;
									用户类型：
							    	<s:select list="#{1:'个人',2:'企业'}" value="#request.types" name="paramMap.userType" id="userType" onchange="changeUserType()" ></s:select>&nbsp;&nbsp;
								</td>
							</tr>
							<tr>
								<td class="f66" align="left" width="50%" height="36px">
									真实姓名：
									<input id="realName" type="text" name="paramMap.realName"/>&nbsp;&nbsp;
									跟踪审核：
									<input id="adminName" name="paramMap.adminName" />&nbsp;&nbsp; 
									状态：
									<s:select id="auditStatus" name="paramMap.auditStatus" list="#{-1:'-请选择-',1:'待审核',3:'成功',2:'失败'}" value="1"></s:select>&nbsp;&nbsp;
									认证：
									<s:select id="certificateName" list="#{-1:'-请选择-',1:'身份证认证',2:'工作认证',3:'信用报告验证',4:'信用报告验证',5:'居住地验证'}"></s:select>&nbsp;&nbsp;
									<input id="search" type="button" value="查找" name="search" />
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
