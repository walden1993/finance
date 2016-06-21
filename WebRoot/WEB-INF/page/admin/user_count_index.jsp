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
	    
	  
	   function initListInfo(praData) {
		    param["paramMap.userName"] = $("#userName").val();
			param["paramMap.materAuthTypeId"] = $("#materAuthTypeId").val();
			param["paramMap.realName"] = $("#realName").val();
			param["paramMap.orgName"] = $("#orgName").val();
			param["paramMap.userType"] = $("#userType").val();
	   		$.post("queryDateCountInitinfo.mht",praData,initCallBack);
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
			var materAuthTypeId = "<option value='-1'>----请--选--择 ----</option>";
			if (userType_selected == 2) {
				materAuthTypeId += "<option value='17'>营业执照</option>";
				materAuthTypeId += "<option value='18'>税务登记证</option>";
				materAuthTypeId += "<option value='19'>组织机构代码证</option>";
				materAuthTypeId += "<option value='20'>公司证明</option>";
				materAuthTypeId += "<option value='21'>央行信用报告</option>";
				materAuthTypeId += "<option value='22'>近两年及今年完税证明</option>";
				materAuthTypeId += "<option value='23'>增值税纳税申请表</option>";
				materAuthTypeId += "<option value='24'>所得税纳税申请表</option>";
				materAuthTypeId += "<option value='25'>营业税纳税申请表</option>";
				materAuthTypeId += "<option value='26'>开户许可证（基本开户行）</option>";
				materAuthTypeId += "<option value='27'>开户许可证（主要结算行）</option>";
				materAuthTypeId += "<option value='28'>房产证明</option>";
				materAuthTypeId += "<option value='29'>项目承包合同书</option>";
				materAuthTypeId += "<option value='30'>相关营运资质</option>";
				materAuthTypeId += "<option value='31'>授信申请书及附件</option>";
				materAuthTypeId += "<option value='32'>年检纪录</option>";
				materAuthTypeId += "<option value='33'>贷款卡</option>";
				materAuthTypeId += "<option value='34'>担保</option>";
			} else if(userType_selected == 1){
				materAuthTypeId += "<option value='1'>身份认证</option>";
				materAuthTypeId += "<option value='2'>工作认证</option>";
				materAuthTypeId += "<option value='3'>居住地认证</option>";
				materAuthTypeId += "<option value='4'>信用报告</option>";
				materAuthTypeId += "<option value='5'>收入认证</option>";
				materAuthTypeId += "<option value='6'>房产</option>";
				materAuthTypeId += "<option value='7'>购车</option>";
				materAuthTypeId += "<option value='8'>结婚</option>";
				materAuthTypeId += "<option value='9'>学历</option>";
				materAuthTypeId += "<option value='10'>技术</option>";
				materAuthTypeId += "<option value='11'>手机</option>";
				materAuthTypeId += "<option value='12'>微博</option>";
				materAuthTypeId += "<option value='13'>现场</option>";
				materAuthTypeId += "<option value='14'>抵押认证</option>";
				materAuthTypeId += "<option value='15'>机构担保</option>";
				materAuthTypeId += "<option value='16'>其他资料</option>";
			}else{
				materAuthTypeId += "<option value='1'>身份认证</option>";
				materAuthTypeId += "<option value='2'>工作认证</option>";
				materAuthTypeId += "<option value='3'>居住地认证</option>";
				materAuthTypeId += "<option value='4'>信用报告</option>";
				materAuthTypeId += "<option value='5'>收入认证</option>";
				materAuthTypeId += "<option value='6'>房产</option>";
				materAuthTypeId += "<option value='7'>购车</option>";
				materAuthTypeId += "<option value='8'>结婚</option>";
				materAuthTypeId += "<option value='9'>学历</option>";
				materAuthTypeId += "<option value='10'>技术</option>";
				materAuthTypeId += "<option value='11'>手机</option>";
				materAuthTypeId += "<option value='12'>微博</option>";
				materAuthTypeId += "<option value='13'>现场</option>";
				materAuthTypeId += "<option value='14'>抵押认证</option>";
				materAuthTypeId += "<option value='15'>机构担保</option>";
				materAuthTypeId += "<option value='16'>其他资料</option>";
				materAuthTypeId += "<option value='17'>营业执照</option>";
				materAuthTypeId += "<option value='18'>税务登记证</option>";
				materAuthTypeId += "<option value='19'>组织机构代码证</option>";
				materAuthTypeId += "<option value='20'>公司证明</option>";
				materAuthTypeId += "<option value='21'>央行信用报告</option>";
				materAuthTypeId += "<option value='22'>近两年及今年完税证明</option>";
				materAuthTypeId += "<option value='23'>增值税纳税申请表</option>";
				materAuthTypeId += "<option value='24'>所得税纳税申请表</option>";
				materAuthTypeId += "<option value='25'>营业税纳税申请表</option>";
				materAuthTypeId += "<option value='26'>开户许可证（基本开户行）</option>";
				materAuthTypeId += "<option value='27'>开户许可证（主要结算行）</option>";
				materAuthTypeId += "<option value='28'>房产证明</option>";
				materAuthTypeId += "<option value='29'>项目承包合同书</option>";
				materAuthTypeId += "<option value='30'>相关营运资质</option>";
				materAuthTypeId += "<option value='31'>授信申请书及附件</option>";
				materAuthTypeId += "<option value='32'>年检纪录</option>";
				materAuthTypeId += "<option value='33'>贷款卡</option>";
				materAuthTypeId += "<option value='34'>担保</option>";
			}
			jQuery("#materAuthTypeId").html(materAuthTypeId);
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
								<a href="javascript:void(0);">认证资料统计</a>
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
									<input id="orgName" name="paramMap.orgName" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									 认证：
									<s:select id="materAuthTypeId" list="#{-1:'----请--选--择 ----',1:'身份验证',2:'工作验证',4:'信用报告验证',3:'居住地认证',5:'收入认证',6:'房产',7:'购车',8:'结婚',9:'学历',10:'技术',11:'手机',12:'微博',13:'现场',14:'抵押',15:'担保',16:'其他资料',17:'营业执照',18:'税务登记证',19:'组织机构代码证',20:'公司证明',21:'央行信用报告',22:'近两年及今年完税证明',23:'增值税纳税申请表',24:'所得税纳税申请表',25:'营业税纳税申请表',26:'开户许可证（基本开户行）',27:'开户许可证（主要结算行）',28:'房产证明',29:'项目承包合同书',30:'相关营运资质',31:'授信申请书及附件',32:'年检纪录',33:'贷款卡',34:'担保'}"></s:select>
								</td>
							</tr>
							<tr>
								<td class="f66" align="left" width="50%" height="36px">
									真实姓名：
									<input id="realName" type="text" name="paramMap.realName"/>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									用户类型：
									<s:select id="userType" list="#{-1:'----请--选--择 ----',2:'企业',1:'个人'}" onchange="changeUserType()" ></s:select>
									&nbsp;&nbsp;&nbsp;&nbsp;
							    	<input id="search" type="button" value="查找" name="search" />
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
