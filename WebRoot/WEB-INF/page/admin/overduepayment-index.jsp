<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
	<head>
		<title>借款管理首页</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
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
							<td width="100" id="today" height="28" class="xxk_all_a">
								<a href="lateRepayInit.mht">逾期的还款</a>
							</td>
							<td width="2">
								&nbsp;
							</td>
							<td width="100" id="today" height="28"  class="main_alll_h2">
								<a href="overduePaymentInit.mht">逾期垫付</a>
							</td>
							<td width="2">
								&nbsp;
							</td>
							<td width="100" id="tomorrow" class="white12">
							</td>
							<td width="2">
								&nbsp;
							</td>
							<td width="100" id="afterday" class="white12">
							</td>
							<td>
								&nbsp;
							</td>
							</tr>
					</table>
				</div>
				<div style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: 1120px; padding-top: 10px; background-color: #fff;">
					<table style="margin-bottom: 0px;" cellspacing="0" cellpadding="0"
						width="100%" border="0">
						<tbody>
						    <tr>
								<td class="f66" align="left" width="50%" height="36px">
									用户名:
									<input id="userName" value="" maxlength="20"/>&nbsp;&nbsp;
									用户类型：
									<s:select id="userType" list="#{-1:'-请选择-',2:'企业',1:'个人'}" ></s:select>&nbsp;&nbsp;
									借款类型：
									<s:select id="borrowWay" list="#{-1:'--请选择--',1:'净值借款',2:'秒还借款',3:'信用借款',4:'实地考察借款',5:'机构担保借款'}"></s:select>
								    &nbsp;&nbsp;
									还款状态：
									<s:select id="status" list="#{-1:'--请选择--',1:'未偿还',2:'已偿还',3:'偿还中'}"></s:select>
									&nbsp;&nbsp;
									<input id="search" type="button" value="&nbsp;&nbsp;搜 索&nbsp;&nbsp;" name="search" />
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
<script type="text/javascript" src="../script/jquery-2.0.js"></script>
<script type="text/javascript" src="../script/jquery.dispatch.js"></script>
<script type="text/javascript" src="../css/admin/popom.js"></script>
<script type="text/javascript">
			$(function(){
				initListInfo(param);
				$('#search').click(function(){
				   param["pageBean.pageNum"] = 1;
				   initListInfo(param);
				});
				
				$("#excel").click(function(){
				$("#excel").attr("disabled",true);
				  window.location.href=encodeURI(encodeURI(
				  "exportlateRepay.mht?status=1"+"&&userName="+$("#userName").val()
				  +"&&userType="+$("#userType").val()
				  +"&&status="+$("#status").val()
				  +"&&borrowWay="+$("#borrowWay").val()));
				setTimeout("test()",4000);
				});
			});		
			function test(){
			   $("#excel").attr("disabled",false);
			   }	
			function initListInfo(praData){
				praData["paramMap.userName"] = $("#userName").val();
				praData["paramMap.userType"] = $("#userType").val();
				praData["paramMap.borrowWay"] = $("#borrowWay").val();
				praData["paramMap.status"] = $("#status").val();
		 		$.dispatchPost("overduePaymentList.mht",praData,initCallBack);
		 	}
		 	function initCallBack(data){
				$("#divList").html(data);
			}
			function repaySubmit(obj){
			    var r=confirm("是否确定还款?");
	            if(r == true){
	               param['paramMap.id'] = obj;
	               $.dispatchPost('overduePaymentRepaySubmit.mht',param,function(data){
		                var callBack = data.msg;
		                if(callBack == '操作成功'){
		                  alert("操作成功!");
		                  window.location.href="overduePaymentInit.mht";
		                  return false;
		                }
		                alert(callBack);
		           });
	            }
			}
</script>
</html>