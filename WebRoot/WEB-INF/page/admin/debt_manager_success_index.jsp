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
		   
	    });
	    //加载留言信息
	   function initListInfo(praData) {
		   param["paramMap.alienatorName"] = $("#alienatorName").val();
		   param["paramMap.borrowerName"] = $("#borrowerName").val();
	   		$.dispatchPost("querySuccessAssignmentDebtInfo.mht",praData,initCallBack);
   		}
   		
   		function initCallBack(data){
		 	$("#dataInfo").html(data);
   		}
   		
   		
	</script>

	</head>
	<body style="min-width: 1200px">
		<div id="right">
			<div style="padding: 15px 10px 0px 10px;">
				<div>
					<table border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="100" height="28"  class="xxk_all_a">
									<a href="queryApplyDebtInit.mht">申请债权转让</a>
								</td>
								<td width="2">
									&nbsp;
								</td>
								<td width="100" height="28"  class="xxk_all_a">
									<a href="queryAuctingAssignmentDebtInit.mht">转让债权中</a>
								</td>
								<td width="2">
									&nbsp;
								</td>
								<td width="100" height="28" class="main_alll_h2">
									<a href="querySuccessAssignmentDebtInit.mht">转让成功</a>
								</td>
								<td width="2">
									&nbsp;
								</td>
								<td width="100" height="28" class="xxk_all_a">
									<a href="queryFailDebtInit.mht">转让失败</a>
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
									借款人：
									<input id="borrowerName" name="paramMap.borrowerName" />&nbsp;&nbsp; 
									转让人：
									<input id="alienatorName" name="paramMap.alienatorName" />&nbsp;&nbsp; 
									<input id="search" type="button" value="确定" name="search" />
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
