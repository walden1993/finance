<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>更新内容</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
		
		<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
		<link rel="stylesheet" href="../kindeditor/themes/default/default.css" />
		<link rel="stylesheet" href="../kindeditor/plugins/code/prettify.css" />
		<script charset="utf-8" src="../kindeditor/kindeditor.js"></script>
		<script charset="utf-8" src="../kindeditor/lang/zh_CN.js"></script>
		<script charset="utf-8" src="../kindeditor/plugins/code/prettify.js"></script>
		<script type="text/javascript" src="../script/jquery.dispatch.js"></script>
		<script type="text/javascript" src="../common/dialog/popup.js"></script>
		<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
		
		<script>
			var editor_content;
			KindEditor.ready(function(K) {
				editor_content = K.create('textarea[name="paramMap.content"]', {
					cssPath : '../kindeditor/plugins/code/prettify.css',
					uploadJson : 'kindEditorUpload.mht',
					fileManagerJson : 'kindEditorManager.mht',
					allowFileManager : true
				});
			});
			
			 $(function(){
			    
			    if(${message!=null}){
			      $("#showresult").html('${message}');
			       
			    }
		      
		      //提交表单
				$("#btn_save").click(function(){
					
					var a = window.confirm("确认要初始化数据库吗？");
					if(a){
						$(this).hide();
						$("#updateTeam1").submit();
					}else{
						return;
					}
				});		
		
		  });

			
	
		
		</script>
		
	</head>
	<body>
		<div id="right">
			<div style="padding: 15px 10px 0px 10px;">
				<div>
					<table width="240px" border="0" cellspacing="0" cellpadding="0">
						<tr > 
							<td width="100" height="28px;"  class="main_alll_h2">
								<a href="showCloseNetWork.mht">关闭网站</a>
							</td>
							<td width="100" height="28px;"  class="main_alll_h2">
								<!-- <a href="showInitMysql.mht">初始化数据</a> -->
							</td>
						</tr>
					</table>
				</div>
				 <form id="updateTeam1" name="updateTeam" action="initMysql.mht" method="post">
				<div style="width: auto; background-color: #FFF; padding: 10px;">
											<table width="100%" border="0" cellspacing="1" cellpadding="3">
						<tr>
							<td style="width: 200px; height: 25px;" align="right"
								class="blue12">
								如要清空数据数据，请点击确认按钮
							</td>
						</tr>
						<tr>
							<td height="36" align="right" class="blue12">
								<s:hidden name="action"></s:hidden>
								&nbsp;
							</td>
							<td>
								<button id="btn_save"
									style="background-image: url('../images/admin/btn_queding.jpg'); width: 70px; border: 0; height: 26px"></button>
								&nbsp;&nbsp;&nbsp;
								<span id="showresult" style="color: red;"></span>
								
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<img id="img" src="../images/NoImg.GIF"
									style="display: none; width: 100px; height: 100px;" />
							</td>
						</tr>
					</table>
					<br />
				</div>
				</form>
			</div>
		</div>
	</body>
</html>
