<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
	<head>
		<title>财务管理-用户银行卡管理</title>
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
		<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript">
			$(function(){
				param["pageBean.pageNum"] = 1;
				initListInfo(param);
				$("#bt_search").click(function(){
					param["pageBean.pageNum"] = 1;
					initListInfo(param);
				});
				
				$("#excel").click(function(){
				$("#excel").attr("disabled",true);
                        window.location.href=encodeURI(encodeURI("exportAllWithdraw.mht?userName="+$("#userName").val()+"&&startTime="+$("#startTime").val()+"&&endTime="+$("#endTime").val()+"&&statss="+$("#status").val()
                        		+"&&firstAudits="+$("#firstAudits").val()+"&&firstAudite="+$("#firstAudite").val()
                                +"&&checkAudits="+$("#checkAudits").val()+"&&checkAudite="+$("#checkAudite").val() ));
                setTimeout("test()",4000);
                });
			});
			function test(){
			   $("#excel").attr("disabled",false);
			   }
			function initListInfo(param){
				param["paramMap.userName"] = $("#userName").val();
				param["paramMap.realName"] = $("#realName").val();
				param["paramMap.userType"] = $("#userType").val();
				param["paramMap.startTime"]= $("#startTime").val();
				param["paramMap.endTime"]= $("#endTime").val();
				param["paramMap.status"]= $("#status").val();

				
				param["paramMap.firstAudits"]= $("#firstAudits").val();
				param["paramMap.firstAudite"]= $("#firstAudite").val();
				param["paramMap.checkAudits"]= $("#checkAudits").val();
				param["paramMap.checkAudite"]= $("#checkAudite").val();
				
		 		$.dispatchPost("queryAllWithdrawList.mht",param,initCallBack);
		 	}
		 	
		 	function initCallBack(data){
				$("#dataInfo").html(data);
			}
			
			//弹出窗口关闭
	   		function close(){
	   			 ClosePop();  			  
	   		}
						
		</script>
	</head>
	<body>
		<div id="right">
			<div style="padding: 15px 10px 0px 10px;">
				<div>
					<table border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="120" height="28" class="main_alll_h2">
								<a href="queryAllWithdrawInit.mht">资金管理</a>
							</td>
							<td width="2">
								&nbsp;
							</td>
							<td width="120" class="xxk_all_a">
								<a href="queryCheckInit.mht">待审核的提现</a>
							</td>
							<td width="2">
								&nbsp;
							</td>
							<td width="120" height="28" class="xxk_all_a">
								<a href="queryTransInit.mht">转账中的提现</a>
							</td>
							<td width="2">
								&nbsp;
							</td>
							<td width="120" height="28" class="xxk_all_a">
								<a href="querySuccessInit.mht">成功的提现</a>
							</td>
							<td width="2">
								&nbsp;
							</td>
							<td width="120" height="28" class="xxk_all_a">
								<a href="queryFailInit.mht">失败的提现</a>
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
									<td class="f66" align="left" width="50%" height="36px">
											&nbsp;&nbsp;&nbsp;用户名：
										<input id="userName" name="paramMap.username" type="text"/>
										&nbsp;&nbsp;
										          提交时间：
										<input id="startTime" name="startTime" type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/>
										&nbsp;－&nbsp;
										<input id="endTime" name="paramMap.endTime" type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/>
									   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								  </td>
								</tr>
								<tr>
									<td class="f66" align="left" width="50%" height="36px">
										开户名称：
										<input id="realName" name="realName" type="text"/>
										&nbsp;&nbsp;
										状  态：
									   <s:select id="status" list="status" name="paramMap.status" listKey="statusId" listValue="statusValue" />
										&nbsp;&nbsp;
										用户类型 ：
										<s:select list="#{-1:'请选择',1:'个人',2:'企业'}" id="userType"></s:select>
										&nbsp;&nbsp;
										
										初审时间:<input id="firstAudits" name="paramMap.firstAudits" type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/>
									  到<input id="firstAudite" name="paramMap.firstAudite" type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/>
										复审时间:<input id="checkAudits" name="paramMap.checkAudits" type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/>
									  到<input id="checkAudite" name="paramMap.checkAudite" type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/>
										<input id="bt_search" type="button" value="查 找"  />
										&nbsp;&nbsp;
										<input id="excel" type="button" value="导出excel"  />
								  </td>
								</tr>
								
							</tbody>
						</table>
						<span id="dataInfo"> </span>
					</div>
				</div>
			</div>
			</div>
	</body>
</html>
