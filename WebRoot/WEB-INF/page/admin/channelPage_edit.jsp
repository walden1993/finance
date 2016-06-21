<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
	<head>
		<title>资料下载-内容维护</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../script/jquery-2.0.js"></script>
		<script type="text/javascript" src="../script/jquery.dispatch.js"></script>
		
	</head>
	<body style="min-width: 1000px">
		<div id="right">
			<div style="padding: 15px 10px 0px 10px;">
				<div>
					<!-- table border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="100" height="28" class="main_alll_h2">
								<a href="channelMsg.mht">渠道方管理</a>
							</td>
							<td width="100" height="28" class="xxk_all_a">
								<a href="channelMsgGotoAdd.mht">修改渠道</a>
							</td>
						</tr>
					</table> -->
					<div
						style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: 1120px; padding-top: 10px; background-color: #fff;">
						<table style="margin-bottom: 8px;" cellspacing="0" cellpadding="0"
							width="100%" border="0">
							<tbody>
								<tr>
									<td align="right" width="13%" height="50" >
										渠道简称：
										</td>
										<td>
										<select id="channelId">
										  <option value="">全部</option>
										  <s:iterator value="#request.list" var="bean">
												<option value="${bean.id}" <s:if test="#bean.id==#request.retMap.channelId ">selected</s:if> >${bean.channelDesc}</option>
										  </s:iterator>
										</select>
										</td>
										</tr>
										<tr>
										<td align="right" width="13%" >
										*页面名称：
										</td>
										<td><input id="pageName" name="paramMap.pageName" type="text" value="${retMap.pageName }"/>
										</td>
										</tr>
										<tr>
										<td  >
										</td>
										<td  >
										<input id="bt_save" type="button" value="保存"  /> &nbsp;&nbsp;
										<input type="reset"  value="取消"  />
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
<script type="text/javascript" src="../script/jquery-2.0.js"></script>
<script type="text/javascript" src="../script/jquery.dispatch.js"></script>
<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
			$(function(){
				
				$("#bt_save").click(function(){
					//
			    	param["paramMap.channelId"] = $("#channelId").val();
			    	param["paramMap.pageName"] = $("#pageName").val();

					if ($("#pageName").val() == ""){
						alert("【页面名称】不能为空");
						return;
					}
					if ($("#channelId").val() == ""){
						alert("【渠道简称】不能为空");
						return;
					}
					
					$.post("editAdvertPage.mht",param,initCallBack1);
				});
				
					
			});
			
		 	
		 	function initCallBack1(data){
				alert("保存成功!");
			}
			
		 	
		</script>