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
								<a href="forPaymentDueInInit.mht">待还款列表</a>
							</td>
							<td width="2">
								&nbsp;
							</td>
							<td width="100" id="today" height="28"   class="xxk_all_a">
								<a href="forPaymentInit.mht">应收款列表</a>
							</td>
							<td width="2">
								&nbsp;
							</td>
							<td width="100" id="tomorrow" class="xxk_all_a">
								 <a href="forPaymentTotalInit.mht">待收款列表</a>
							</td>
							<td width="2">
								&nbsp;
							</td>
							<td width="100" id="today" height="28"  class="xxk_all_a">
								<a href="hasRepayInit.mht">已还款列表</a>
							</td>
							<td width="2">
								&nbsp;
							</td>
							<td width="100" class="white12">
							</td>
							<td>
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
									投资人:
									<input id="investor"  value=""/>&nbsp;&nbsp;
									应收时间:
									<input id="timeStart" class="Wdate" value="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/>&nbsp;&nbsp;
									--
									<input id="timeEnd" class="Wdate" value="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/>&nbsp;&nbsp;
									借款标题:
									<input id="titles" value=""  maxlength="50"/>&nbsp;&nbsp;
									</td>
							</tr>
							<tr>
								<td class="f66" align="left" width="50%" height="36px">
									用户类型：
									<s:select id="userType" list="#{-1:'-请选择-',2:'企业',1:'个人'}" ></s:select>
									&nbsp;&nbsp;
									借款类型：
									<s:select id="borrowWay" list="#{-1:'--请选择--',1:'净值借款',2:'秒还借款',3:'信用借款',4:'实地考察借款',5:'机构担保借款',7:'债权转让借款'}"></s:select>
									&nbsp;&nbsp;
									用户组：
									<s:select id="group" list="userGroupList" listKey="selectValue" listValue="selectName" headerKey="-1" headerValue="--请选择--"></s:select>
									&nbsp;&nbsp;
									<input id="inverse" type="checkbox" name="inverse" />反选
									&nbsp;&nbsp;
									&nbsp;&nbsp;
									<input id="search" type="button" value="&nbsp;&nbsp;查 找&nbsp;&nbsp;" name="search" />
									&nbsp;&nbsp;
									<input id="excel" type="button" value="导出Excel" name="excel" />
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
				   initListInfo(param);
				});
				
				$("#excel").click(function(){
				   $("#excel").attr("disabled",true);
				       window.location.href=encodeURI(encodeURI("exportforPaymentDueIn.mht?inverse="+$("#inverse").attr("checked")+"&&borrowWayid="+$("#borrowWay").val()+"&&groupId="+$("#group").val()+"&&investor="+$("#investor").val()+"&&timeStart="+$("#timeStart").val()+"&&timeEnd="+$("#timeEnd").val()+"&&titles="+$("#titles").val()));
				    setTimeout("test()",4000);
				});
			});		
			function test(){
			   $("#excel").attr("disabled",false);
			}
			function initListInfo(praData){
				praData["paramMap.investor"] = $("#investor").val();
				praData["paramMap.timeStart"] = $("#timeStart").val();
				praData["paramMap.timeEnd"] = $("#timeEnd").val();
				praData["paramMap.title"] = $("#titles").val();
				praData["paramMap.borrowWay"] = $("#borrowWay").val();
				praData["paramMap.group"] = $("#group").val();
				praData["paramMap.userType"] = $("#userType").val();
				//-------add by houli 添加反选条件
				param["paramMap.inverse"] = $("#inverse").attr("checked");
				//-----------
		 		$.dispatchPost("forPaymentDueInList.mht",praData,initCallBack);
		 	}
		 	function initCallBack(data){
				$("#divList").html(data);
			}
			function viewDetail(id){
		 	    var url = "queryByrepayIdDueId.mht?repayId="+id;
		 	    ShowIframe("待还款详情",url,800,450);
		 	}
</script>
</html>
