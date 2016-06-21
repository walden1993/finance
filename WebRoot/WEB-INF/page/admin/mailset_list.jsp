<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>借款产品参数</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
		
		
	</head>
	<body>
			<div id="right">
				<div style="padding: 15px 10px 0px 10px;">
					<div>
						<table  border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td  width="18%" height="28"  class="main_alll_h2">
									<a href="emailAndMessageindex.mht">邮件设置</a>
								</td>
								<td width="2px">
								</td>
								<td width="18%" height="28" class="xxk_all_a">
									<a href="findSMSList.mht">短信接口设置</a>
								</td>
								<td width="80">
									&nbsp;
								</td>
							</tr>
						</table>
					</div>
					 
					<div id="showcontent" style="width: auto; background-color: #FFF; padding: 10px;">
						<!-- 内容显示位置 -->
						<table id="gvNews" style="width: 100%; color: #333333;" cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
							<tr class=gvHeader>
								<th style="width: 100px; height: 25px;" align="right"
									class="blue12">
									邮件地址
								</th>
								<th style="width: 100px; height: 25px;" align="right"
									class="blue12">
								发件人昵称</th>
								<th align="left" class="f66">
								操作
								</th>
							</tr>
							<s:set name="counts" value="#request.pageNum"/>
							<s:iterator value="pageBean.page" var="bean" status="s">
							<tr  class="gvItem">
								<td style="width: 100px; height: 25px;" align="right"
									class="blue12">
									${mailaddress}
								</td>
								<td style="width: 100px; height: 25px;" align="right"
									class="blue12">
									${sendname}
								</td>
								<td align="left" class="f66">
									<a href="#" onclick="modifyEmail('${id}');">修改</a>
									<a href="#" onclick="deleteEmail('${id}');">删除</a> 
								</td>
							</tr>
							</s:iterator>
							
						</table>
						
						
						<br />
						<input type="button" value="新增邮件" onclick="addNewEmail();"/>
					</div>
				</div>
			</div>
		<script type="text/javascript" src="../script/jquery-2.0.js"></script>
		<script type="text/javascript" src="../script/jquery.dispatch.js"></script>
		<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>		
		<script>
		//新增电子邮件
		function addNewEmail(){
			window.location.href="emailAndMessagelist.mht";
		}
		
		//修改电子邮件
		function modifyEmail(id){
			window.location.href="emailAndMessageModify.mht?id="+id;
		}
		
		//删除电子邮件
		function deleteEmail(id){
			if (confirm("您确定删除吗?")){
				window.location.href="emailAndMessageDelete.mht?id="+id;
			}
		}
		</script>
	</body>
</html>
