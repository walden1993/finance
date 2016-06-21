<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include  file="/include/taglib.jsp"%>
<html>
	<head>
		<title>借款管理首页</title> 
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma"  content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" /> 
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords"  content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
		<link href="../css/admin/admin_custom_css.css" rel="stylesheet" type="text/css" />
	 </head>
	<body>
		<div id="right">
			<div style="padding: 15px 10px 0px 10px;">
				<div>
					<table border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="100" id="today"  class="main_alll_h2">
								<a href="javascript:void(0)">体验标列表</a>
							</td>
							<td width="2">
								&nbsp;
							</td>
							</tr>
					</table>
				</div>
				<div  style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: 1160px; padding-top: 10px; background-color: #fff;">
					<table style="margin-bottom: 8px;" cellspacing="0" cellpadding="0"
						width="100%" border="0">
						<tbody>
						    <tr>
								<td class="f66" align="left" width="50%" height="36px">
									标题：
									<input id="title"  value=""/>&nbsp;&nbsp;
									状态：
									<s:select id="state" list="#{-1:'-全部-',0:'招标中',1:'还款中',2:'已还完'}" ></s:select>&nbsp;&nbsp;
									创建日期：
									<input id="creationStart" class="Wdate" value="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/>&nbsp;&nbsp;
									--
									<input id="creationEnd" class="Wdate" value="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/>&nbsp;&nbsp;
								</td>
							</tr>
							<tr>
								<td class="f66" align="left" width="50%" height="36px">
									待还时间：
									<input id="waitingStart" class="Wdate" value="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/>&nbsp;&nbsp;
									--
									<input id="waitingEnd" class="Wdate" value="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/>&nbsp;&nbsp;
									&nbsp;&nbsp;
									<input id="search" type="button" value="&nbsp;&nbsp;查 询&nbsp;&nbsp;" name="search" />
									&nbsp;&nbsp;
									<input id="add" type="button" value="&nbsp;新增体验标&nbsp;" name="add" />
									&nbsp;&nbsp;
                                    <input id="excel" type="button" value="&nbsp;导出&nbsp;" name="add" />
								</td>
							</tr>
						</tbody>
					</table>
		             <span id="divList"><img src="../images/admin/load.gif" class="load" alt="加载中..." /></span>
					<div>
				</div>
			</div>
			</div> 
		</div>
	</body>
<script type="text/javascript"  src="../script/jquery-2.0.js"></script>
<script type="text/javascript" src="../script/jquery.dispatch.js"></script>
<script  type="text/javascript" src="../css/admin/popom.js"></script>
<script  language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
<script  type="text/javascript">
			$(function(){
				initListInfo(param);
				$('#search').click(function(){
					param["pageBean.pageNum"] = 1;
					param["paramMap.title"] = $("#title").val();
					param["paramMap.state"] = $("#state").val();
					param["paramMap.creationStart"] = $("#creationStart").val();
					param["paramMap.creationEnd"] = $("#creationEnd").val();
					param["paramMap.waitingStart"] = $("#waitingStart").val();
					param["paramMap.waitingEnd"] = $("#waitingEnd").val();
					
					if($("#waitingStart").val() >$("#waitingEnd").val() || $("#creationStart").val() >$("#creationEnd").val()  ){
                        alert("开始日期不能大于结束日期");
                        return;
                    }
					
				   initListInfo(param);
				   
				   
				});
				
				
				$("#excel").click(function(){
                     window.location.href="exportExperienceList.mht?paramMap.title="+$("#title").val()+"&&paramMap.state="+$("#state").val()+"&&paramMap.creationStart="+$("#creationStart").val()+"&&paramMap.creationEnd="+$("#creationEnd").val()+"&&paramMap.waitingStart="+$("#waitingStart").val()+"&&paramMap.waitingEnd="+$("#waitingEnd").val();
                });
				
				$("#add").click(function(){
					window.location.href='addExperience.mht';
				});
				
				
			});		
			function initListInfo(praData){
		 		$.post("queryExperienceList.mht",praData,initCallBack);
		 	}
		 	function initCallBack(data){
				$("#divList").html(data);
			}
</script>
</html>
