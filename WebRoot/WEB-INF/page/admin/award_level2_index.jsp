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
		    }
	    )
	    //
	   function initListInfo(praData) {
			param["level1userId"] = ${level1userId};
			param["level2userId"] = ${level2userId};
			param["luserName"] = $("#luserName").val();
	   		$.dispatchPost("queryLeve1AwardInfo.mht",praData,initCallBack);
   		}
   		
   		function initCallBack(data){
		 	$("#dataInfo").html(data);
   		}
   		
   		//分页
   		function pageInfo(pageId){
   			param["pageBean.pageNum"] = pageId;
   			initListInfo(param);
   		}
	</script>

	</head>
	<body>
		<div id="right">
			<div style="padding: 15px 10px 0px 10px;">
				<div>
					<table border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="200" height="28"  class="xxk_all_a">
								<a href="queryAwardTinit.mht">团队长提成管理</a>
							</td>
							<td width="2">
								&nbsp;
							</td>
							<td width="150" height="28"  class="xxk_all_a">
								<a href="queryAwardLevel1Init.mht?level1userId=${level1userId }">团队长提成奖励统计</a>
							</td>
							<td width="2">
								&nbsp;
							</td>
							<td   height="28"  class="main_alll_h3">
								<a href="queryLeve1AwardInit.mht?level2userId=${level2userId}&&level1userId=${level1userId }">提成奖励明细</a>
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
					style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: auto; padding-top: 10px; background-color: #fff;">
					<table style="margin-bottom: 8px;" cellspacing="0" cellpadding="0"
						width="100%" border="0">
						<tbody>
							<tr>
								<td class="f66" align="left" width="50%" height="36px">
									投资人账号： 
									<input id="luserName" />&nbsp;&nbsp; 
									<input id="search" type="button" value="搜索" name="search" />
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
