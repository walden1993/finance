<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
	<head>
		<title>团队介绍-内容维护</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../script/jquery-2.0.js"></script>
		<script type="text/javascript" src="../script/jquery.dispatch.js"></script>
		<script type="text/javascript" src="../css/admin/popom.js"></script>
		<script type="text/javascript">
			$(function(){				
				param["pageBean.pageNum"] = 1;
				initListInfo(param);				
			});
			
			function initListInfo(praData){							    
		 		$.dispatchPost("queryNewsListPage.mht",praData,initCallBack);
		 	}
		 	
		 	function initCallBack(data){
		 	  
				$("#dataInfo").html(data);
			}
			
			function dels(){
		 		if(!window.confirm("确定要删除吗?")){
		 			return;
		 		} 
		 		var stIdArray = [];
	 			$("input[name='cb_ids']:checked").each(function(){
	 				stIdArray.push($(this).val());
	 			});
	 			if(stIdArray.length == 0){
	 				alert("请选择需要删除的内容");
	 				return ;
	 			}
	 			var ids = stIdArray.join(",");
	 			window.location.href= "deleteNews.mht?id="+ids;
		 	}
			
		 	function del(id){
		 		if(!window.confirm("确定要删除吗?")){
		 			return;
		 		}
		 		
	 			window.location.href= "deleteNews.mht?id="+id;
		 	}
		 	
		 	function checkAll(e){
		   		if(e.checked){
		   			$(".downloadId").attr("checked","checked");
		   		}else{
		   			$(".downloadId").removeAttr("checked");
		   		}
   			}
   			
   			//网站公告预览  houli
		 	function preview(id){
		 	  var url = "previewNewsInit.mht?id="+id;
              ShowIframe("网站公告详情显示",url,600,450);
		 	  
		 	}
   			
   			//弹出窗口关闭 houli
	   		function close(){
	   			 ClosePop();  			  
	   		}
		 	
		</script>
	</head>
	<body>
		<div id="right">
			<div style="padding: 15px 10px 0px 10px;">
				<div>
					<table border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="100" height="28"  class="main_alll_h2">
								<a href="queryNewsListInit.mht">官方公告管理</a>
							</td>
							<td width="2">
								&nbsp;
							</td>
							<td width="100"  class="xxk_all_a">
								<a href="addNewsInit.mht">添加官方公告</a>
							</td>
							<td width="2">
								&nbsp;
							</td>
							<td width="100"  class="xxk_all_a">
								<a href="mediaReportListinit.mht">动态管理</a>
							</td>
							<td width="2">
								&nbsp;
							</td>
							<td width="100"  class="xxk_all_a">
								<a href="addMediaReportInit.mht">添加动态</a>
							</td>
							<td width="2">
								&nbsp;
							</td>
							<td>
								&nbsp;
							</td>
						</tr>
					</table>
					<div
						style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: 1120px; padding-top: 10px; background-color: #fff;">
						<table style="margin-bottom: 8px;" cellspacing="0" cellpadding="0"
							width="100%" border="0">
							<tbody>
							
							</tbody>
						</table>
						<span id="dataInfo"> </span>
					</div>
				</div>
			</div>
	</body>
</html>
