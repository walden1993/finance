<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
	<head>
		<title>管理首页</title>
		<style>
		</style>
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
	    	
		    initListInfo(param);
		    
		    $("#search").click(function(){
		    param["pageBean.pageNum"] = 1;
			initListInfo(param);
		    	});
		    }
	    )
	    
	     //加载留言信息
	   function initListInfo(praData) {
			param['paramMap.userId'] = ${userId}
			param['paramMap.materAuthTypeId'] =$('#materAuthTypeId').val();
			param['paramMap.userType'] =${userType};
	   		$.post("queryselectinofor.mht",param,initCallBack);
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

		function goBack(){
			window.location.href="queryselect.mht?userId="+${userId}+"&userType="+${userType};
		}
		</script>

	</head>
	<body>
		<div id="right"
			style="background-image: url(images/admin/right_bg_top.jpg); background-position: top; background-repeat: repeat-x;">
			<div style="padding: 15px 10px 0px 10px;">
		
		
		
			<div>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
						     <td width="100" height="28" align="center" 
								class="main_alll_h2">
								<a href="javascript:void(0);">可选资料认证</a>
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
				<div>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
				<hr/>
						</tr>
					</table>
				</div>
				<div
					style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: auto; padding-top: 10px; background-color: #fff;">
					<table style="margin-bottom: 8px;" cellspacing="0" cellpadding="0"
						width="100%" border="0">
						<tbody>
							<tr>
								<td class="f66" align="left" width="50%" height="36px">
									 证件种类：
									 <s:if test=""></s:if>
									 <s:select list="#{20:'公司证明',21:'央行信用报告',22:'近两年及今年完税证明',23:'增值税纳税申请表',24:'所得税纳税申请表',25:'营业税纳税申请表',26:'开户许可证（基本开户行）',27:'开户许可证（主要结算行）',28:'房产证明',29:'项目承包合同书',30:'相关营运资质',31:'授信申请书及附件',32:'年检纪录',33:'贷款卡',34:'担保'
									 }" theme="simple"  name="selectvalue" id="materAuthTypeId"></s:select>
									<input id="search" type="button" value="查找" name="search" class="scbtn"/>
									<input type="button" value="返回" 
			style="background-image: url(../images/sqdk_07.jpg); background-repeat: no-repeat; height: 24px; width: 78px; margin-top: 0; background-position: left center"
			onclick="javascript:goBack();" />
								</td>
							</tr>
						</tbody>
					</table>
					<span id="dataInfo"> </span>
				</div>
			</div>
		</div>
	</body>
</html>
