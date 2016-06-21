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
		
		<script type="text/javascript" src="../script/jquery-2.0.js"></script>
		<link rel="stylesheet" href="../kindeditor/themes/default/default.css" />
		<link rel="stylesheet" href="../kindeditor/plugins/code/prettify.css" />
		<script charset="utf-8" src="../kindeditor/kindeditor.js"></script>
		<script charset="utf-8" src="../kindeditor/lang/zh_CN.js"></script>
		<script charset="utf-8" src="../kindeditor/plugins/code/prettify.js"></script>
		<script type="text/javascript" src="../script/jquery.dispatch.js"></script>
		<script type="text/javascript" src="../common/dialog/popup.js"></script>
		
	

		
	</head>
	<body>
			<div id="right">
				<div style="padding: 15px 10px 0px 10px;">
					<div>
						<table border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td  width="20%" height="28"  class="xxk_all_a">
									<a href="emailAndMessageindex.mht">邮件设置</a>
								</td>
								<td width="2px;"></td>
								<td width="20%"  class="main_alll_h2">
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
						
						<table id="gvNews" style="width: 100%; color: #333333;"
			cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
						 <tr class=gvHeader>
						   <td><h3>是否开启</h3></td>
						   <td><h3>账号</h3></td>
						   <td><h3>子账号</h3></td>
						   <td><h3>密码</h3></td>
						   <td><h3>短信接口地址</h3></td>
						   <td><h3>是否营销</h3></td>
						   <td><h3>操作</h3></td>
						 </tr>
						 <s:iterator value="listSMS" var="bean">
						    
						 <tr class="gvItem">
						 <form action="updateSMS.mht" method="post" >
						   <td>  
						 <input type="hidden" name="paramMap.id" value="${bean.id}"/>     
						   是:<input type="radio" name="paramMap.status_${bean.id}" value="1"
								 	<s:if test='#bean.status==1'>checked="checked"</s:if>
											<s:else></s:else>
								 />&nbsp;&nbsp;
								 否：<input type="radio" name="paramMap.status_${bean.id}" value="2"
								 	<s:if test='#bean.status==2'>checked="checked"</s:if>
											<s:else></s:else>
								 /></td>
						   <td><input type="text" name="paramMap.UserID" value="${bean.UserID}" size="10" /></td>
						   <td><input type="text" name="paramMap.Account" value="${bean.Account}" size="20" /> </td>
						   <td><input type="password" name="paramMap.Password" value="${bean.Password}" size="10" /></td>
						   <td><input type="text" name="paramMap.url" value="${bean.url}" size="50" /></td>
						   <td>
						   
						   是:<input type="radio" name="paramMap.isMarket_${bean.id}" value="1"
								 	<s:if test='#bean.isMarket==1'>checked="checked"</s:if>
											<s:else></s:else>
								 />&nbsp;&nbsp;
								 否：<input type="radio" name="paramMap.isMarket_${bean.id}" value="0"
								 	<s:if test='#bean.isMarket==0'>checked="checked"</s:if>
											<s:else></s:else>
								 />
						   
						   </td>
						   <td><input type="submit"  value=" "  id="btn_save"
										style="background-image: url('../images/admin/btn_update.jpg'); width: 70px; height: 26px; border: 0px"/></td>
						   </form>
						 </tr>
						 </s:iterator>
						</table>
						
						
						
						
						
						
						<br />
					</div>
				</div>
			</div>
		<script>
			
		  $(function(){
		     if(${message!=null}){
		       alert('${request.message}');
		      }
		      
		      //提交表单
				//$("#btn_save").click(function(){
				//	$(this).hide();
				//	$("#updateSMS").submit();
					//return false;
				//});
		  });
		</script>
	</body>
</html>
