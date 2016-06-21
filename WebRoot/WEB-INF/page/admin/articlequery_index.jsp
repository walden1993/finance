<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
	<head>
		<title>媒体报道-内容维护</title>
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
		<script  language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript">
			$(function(){				
				param["pageBean.pageNum"] = 1;
				initListInfo(param);
				
				$("#bt_search").click(function(){
	                 param["pageBean.pageNum"] = 1;
	                 param["paramMap.content"] = $("#content").val();
	                 param["paramMap.createTimeStart"] = $("#createTimeStart").val();
	                 param["paramMap.createTimeEnd"] = $("#createTimeEnd").val();
	                 param["paramMap.newsType"] = $("#newsType").val();
	                 //var a = "/^[1-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2]\d|3[0-1])\s+([0-1]\d|2[0-3])\:[0-5]\d$/";
	                 
	                 
	                 initListInfo(param);
	                });
				
			});
			
			function initListInfo(praData){							    
		 		$.dispatchPost("articleListPage.mht",praData,initCallBack);
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
	 			window.location.href= "deleteMediaReport.mht?type=4&id="+ids;
		 	}
			
		 	function del(id){
		 		if(!window.confirm("确定要删除吗?")){
		 			return;
		 		}
		 		
	 			window.location.href= "deleteMediaReport.mht?type=4&id="+id;
		 	}
		 	
		 	//媒体报道预览  houli
		 	function preview(id){
		 	  var url = "preViewMediaReportInit.mht?id="+id;
              ShowIframe("媒体报道详情显示",url,600,450);
		 	  
		 	}
		 	
		 	function checkAll(e){
		   		if(e.checked){
		   			$(".downloadId").attr("checked","checked");
		   		}else{
		   			$(".downloadId").removeAttr("checked");
		   		}
   			}
   			
   			//弹出窗口关闭 houli
	   		function close(){
	   			 ClosePop();  			  
	   		}
		 	
		</script>
	</head>
	<body>
			<div style="padding: 15px 10px 0px 10px;">
				<div>
					<table border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="2">
								&nbsp;
							</td>
							<td width="100"  class="main_alll_h2">
								<a href="articleAdd.mht">动态管理</a>
							</td>
							<td width="2">
								&nbsp;
							</td>
							<td width="100"  class="xxk_all_a">
								<a href="addArticleInit.mht">添加动态</a>
							</td>
							<td width="2">
								&nbsp;
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
                                <tr>
                                <td class="f66" align="left" width="80%" height="36px">
                                        内容 ：
                                        <input id="content" name="paramMap.content"  placeholder="标题、正文关键字"  type="text"/>&nbsp;&nbsp;
                                        
                                        发布时间：
                                        <input id="createTimeStart"  class="Wdate" value="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"  name="paramMap.createTime" type="text"/>&nbsp;&nbsp;
                                        至<input id="createTimeEnd"  class="Wdate" value="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"  name="paramMap.createTime" type="text"/>&nbsp;&nbsp;
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
