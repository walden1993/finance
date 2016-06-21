<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
	<head>
		<title>用户基本信息列表</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../script/jquery-2.0.js"></script>
		<script type="text/javascript" src="../script/jquery.dispatch.js"></script>
		<script type="text/javascript">
			$(function(){
				
				initListInfo(param);
				$("#bt_search").click(function(){
				 param["pageBean.pageNum"] = 1;
				 param["paramMap.userName"] = $("#userName").val();
				 param["paramMap.realName"] = $("#realName").val();
				 param["paramMap.orgName"] = $("#orgName").val();
				 param["paramMap.userType"] = $("#userType").val();
			     initListInfo(param);
				});
				
					
			});
			
			function initListInfo(praData){
		 		$.post("queryUserManageBaseInfo.mht",praData,initCallBack);
		 	}
		 	
		 	function initCallBack(data){
		 	  
				$("#dataInfo").html(data);
			}
			
			
		 	function del(id){
		 		if(!window.confirm("确定要删除吗?")){
		 			return;
		 		}
		 		
	 			window.location.href= "deleteDownload.mht?id="+id;
		 	}
		 	
		 	function checkAll(e){
		   		if(e.checked){
		   			$(".downloadId").attr("checked","checked");
		   		}else{
		   			$(".downloadId").removeAttr("checked");
		   		}
   			}
		 	
		</script>
	</head>
	<body style="min-width: 1000px">
		<div id="right">
			<div style="padding: 15px 10px 0px 10px;">
				<div>
					<table  border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="100" height="28" class="xxk_all_a">
								<a href="queryUserManageBaseInfoindex.mht">用户基本信息</a>
							</td>
							<td width="2">
								&nbsp;
							</td>
						</tr>
					</table>
					<div
						style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: 1120px; padding-top: 10px; background-color: #fff;">
						<table style="margin-bottom: 8px;" cellspacing="0" cellpadding="0"
							width="100%" border="0">
							<tbody>
								<tr>
								<td class="f66" align="left" width="80%" height="36px">
										用户名 ：
										<input id="userName" name="paramMap.userName" type="text"/>&nbsp;&nbsp;
										真实姓名 ：
										<input id="realName" name="paramMap.realName" type="text"/>&nbsp;&nbsp;
										企业全称 ：
										<input id="orgName" name="paramMap.orgName" type="text"/>&nbsp;&nbsp;
										用户类型 ：
										<s:select list="#{-1:'--全部--',1:'个人',2:'企业'}" id="userType"></s:select>
										&nbsp;&nbsp;&nbsp;&nbsp;
										<input id="bt_search" type="button" value="搜索"  />
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
