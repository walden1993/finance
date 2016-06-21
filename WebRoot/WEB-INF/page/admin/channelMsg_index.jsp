<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
	<head>
		<title>渠道方管理</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="description" content="This is my page" />
		<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../script/jquery-2.0.js"></script>
		<script type="text/javascript" src="../script/jquery.dispatch.js"></script>
		
	</head>
	<body style="min-width: 1000px">
		<div id="right">
			<div style="padding: 15px 10px 0px 10px;">
				<div>
					<table border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="100" height="28" class="main_alll_h2">
								<a href="channelMsg.mht">渠道方管理</a>
							</td>
							<td width="100" height="28" class="xxk_all_a">
								<a href="channelMsgGotoAdd.mht">新增渠道</a>
							</td>
						</tr>
					</table>
					<div
						style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: 1120px; padding-top: 10px; background-color: #fff;">
						<table style="margin-bottom: 8px;" cellspacing="0" cellpadding="0"
							width="100%" border="0">
							<tbody>
								<tr>
									<td class="f66" align="left" width="50%" height="36px">
										渠道简称：
										<input id="channelName" name="paramMap.channelDesc" type="text"/>
										系统编码：
										<input id="sysCode" name="paramMap.sysCode" type="text"/>
										渠道编号：
										<input id="channelCode" name="paramMap.channelCode" type="text"/>
										类型：
										<select id="ctype">
										  <option value="">全部</option>
										  <option value="1">硬广</option>
										  <option value="2">网盟</option>
										  <option value="3">搜索引擎</option>
										  <option value="4">CPS</option>
										  <option value="5">社会化媒体</option>
										</select>
										
										<input id="bt_search" type="button" value="查询"  />
										<input id="bt_add" type="button" value="新增渠道"  />
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
<script type="text/javascript">
			$(function(){
				
				param["pageBean.pageNum"] = 1;
				initListInfo(param);
				$("#bt_search").click(function(){
					
					initListInfo(param);
				});
				
				$("#bt_add").click(function(){
					
					window.location.href="channelMsgGotoAdd.mht";
				});
				
				
					
			});
			
			function initListInfo(praData){
				praData["paramMap.channelDesc"] = $("#channelDesc").val();
				praData["paramMap.sysCode"] = $("#sysCode").val();
				praData["paramMap.channelCode"] = $("#channelCode").val();
				praData["paramMap.ctype"] = $("#ctype").val();
				
		 		$.post("channelMsgList.mht",praData,initCallBack);
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