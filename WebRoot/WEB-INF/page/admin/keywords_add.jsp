<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>新增关键字</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link href="../css/admin/admin_css.css" rel="stylesheet"
			type="text/css" />

		<script type="text/javascript" src="../script/jquery-2.0.js"></script>
		<script type="text/javascript" src="../script/jquery.dispatch.js"></script>
		<script type="text/javascript" language="javascript">
	    	$(function(){
	    		  if($('#paramMap_img').val() == ''){
				    	$("#img").attr("src","../images/NoImg.GIF");
				  }else{
						$('#img').attr('src',"../"+$('#paramMap_img').val());
				  }
				//提交表单
				$("#btn_save").click(function(){
					if($("#words").val() == "") {
						alert("必须输入关键字内容！");
						return false;
					}
					$(this).hide();
					$("#addAdmin").submit();
					return false;
				});
});
</script>
	</head>
	<body style="min-width: 1000px">
		<form id="addAdmin" action="addKeywords.mht" method="post">
			<div id="right">
				<div style="padding: 15px 10px 0px 10px;">
					<div>
						<table border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td height="28" class="xxk_all_a">
									<a href="queryKeywordsinit.mht">关键字列表</a>
								</td>
								<td width="2">
									&nbsp;
								</td>
								<td width="100" class="main_alll_h2">
									<a href="addKeywordsinit.mht">添加关键字</a>
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
					</div>
					<div style="width: auto; background-color: #FFF; padding: 10px;">
						<table width="100%" border="0" cellspacing="1" cellpadding="3">
							<tr>
								<td style="width: 100px; height: 25px;" align="right"
									class="blue12">
									关键字：
								</td>
								<td align="left" class="f66">
									<s:textfield id="words" name="paramMap.words" theme="simple"
										cssClass="in_text_2" cssStyle="width: 150px"></s:textfield>
									<span style="color: red;">*<s:fielderror
											fieldName="paramMap.words" /> </span>
								</td>
							</tr>
							<tr>
								<td style="height: 25px;" align="right" class="blue12">
									状态：
								</td>
								<td align="left" class="f66">
									<s:radio name="paramMap.isuse" id="enable" theme="simple"
										list="#{1:'启用',0:'禁用'}" value="1" />
									<span style="color: red;">*<s:fielderror
											fieldName="paramMap.isuse" /> </span>
								</td>
							</tr>
							<tr>
								<td height="36" align="right" class="blue12">
									&nbsp;
								</td>
								<td>
									<button id="btn_save"
										style="background-image: url('../images/admin/btn_queding.jpg'); width: 70px; border: 0; height: 26px"></button>
									&nbsp;
									<button type="reset"
										style="background-image: url('../images/admin/btn_chongtian.jpg'); width: 70px; height: 26px; border: 0px"></button>
									&nbsp; &nbsp;
									<span style="color: red;"> <s:fielderror
											fieldName="actionMsg" theme="simple"></s:fielderror> </span>
								</td>
							</tr>
						</table>
						<br />
					</div>
				</div>
			</div>
		</form>
	</body>
</html>
