<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
	<head>
		<title>员工资金统计列表</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../script/jquery-2.0.js"></script>
		<script type="text/javascript" src="../script/jquery.dispatch.js"></script>
		<script  language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript">
			$(function(){
				
				initListInfo(param);
				$("#bt_search").click(function(){
				 param["pageBean.pageNum"] = 1;
				 param["paramMap.userName"] = $("#userName").val();
				 param["paramMap.realName"] = $("#realName").val();
				 param["paramMap.department"] = $("#department").val();
				 param["paramMap.phone"] = $("#phone").val();
				 param["paramMap.acountBalanceTime"] = $("#acountBalanceTime").val();
				 param["paramMap.investTimeStart"] = $("#investTimeStart").val();
				 param["paramMap.investTimeEnd"] = $("#investTimeEnd").val();
				 param["paramMap.rechargeTimeStart"] = $("#rechargeTimeStart").val();
                 param["paramMap.rechargeTimeEnd"] = $("#rechargeTimeEnd").val();
				 
				 if( param["paramMap.investTimeStart"]>param["paramMap.investTimeEnd"]){
					 alert("投资起始日期不能小于截至日期")
					 return;
				 }
				 
				 if( param["paramMap.rechargeTimeStart"]>param["paramMap.rechargeTimeEnd"]){
                     alert("充值起始日期不能小于截至日期")
                     return;
                 }
				 
			     initListInfo(param);
				});
			
					
			});
			
			function initListInfo(praData){
			    
		 		$.post("employeeFundInfo.mht",praData,initCallBack);
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
	<body style="min-width: 1000px">
		<div id="right">
			<div style="padding: 15px 10px 0px 10px;">
				<div>
					<table  border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="100" height="28" class="main_alll_h2">
								<a href="employeeFundInit.mht">员工资金统计</a>
							</td>
							<td width="2">
								&nbsp;
							</td>
                            <td width="100" id="today" height="28"   class="xxk_all_a">
                                <a href="departmentFundInit.mht">部门资金统计</a>
                            </td>
                            <td width="2">
                                &nbsp;
                            </td>
                            <td width="100" id="today" height="28"   class="xxk_all_a">
                                <a href="employeeRefInit.mht">员工推荐明细统计</a>
                            </td>
                            <td width="2">
                                &nbsp;
                            </td>
                            <td width="100" id="today" height="28"   class="xxk_all_a">
                                <a href="employeeLivInit.mht">员工推荐活跃度统计</a>
                            </td>
                            <td width="2">
                                &nbsp;
                            </td>
                            <td width="100" id="today" height="28"   class="xxk_all_a">
                                <a href="departmentLivInit.mht">部门推荐活跃度统计</a>
                            </td>
                            <td width="2">
                                &nbsp;
                            </td>
                            <td width="100" id="today" height="28"   class="xxk_all_a">
                                <a href="employeePerformanceInit.mht">员工绩效统计</a>
                            </td>
                            <td width="2">
                                &nbsp;
                            </td>
						</tr>
					</table>
					</div>
					<div
						style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: 1120px; padding-top: 10px; background-color: #fff;">
						<table style="margin-bottom: 8px;" cellspacing="0" cellpadding="0"
							width="100%" border="0">
							<tbody>
								<tr>
									<td class="f66" align="left" width="80%" height="36px">
										用户名 ：
										<input id="userName" name="paramMap.userName" type="text"/>&nbsp;&nbsp;
										真实姓名 ：
										<input id="realName" name="paramMap.realName" type="text"/>&nbsp;&nbsp;
										手机号：
										<input id="phone" name="paramMap.phone" type="text"/>&nbsp;&nbsp;&nbsp;
										部门：
										<s:select list="#request.departments"   listKey="selectKey"  listValue="selectName"  headerKey=""  headerValue="----请选择部门----"  name="paramMap.department" class="inp188" id="department" />
										账户余额时间点：<input id="acountBalanceTime"  class="Wdate" value="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"  name="paramMap.acountBalanceTime" type="text"/>&nbsp;&nbsp;
									</td>
								</tr>
								<tr>
                                    <td class="f66" align="left" width="80%" height="36px">
                                        投资时间：
                                        <input id="investTimeStart"  class="Wdate" value="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" type="text"/>&nbsp;&nbsp;
                                        至<input id="investTimeEnd"  class="Wdate" value="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"  type="text"/>&nbsp;&nbsp;
                                        充值时间：
                                        <input id="rechargeTimeStart"  class="Wdate" value="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" type="text"/>&nbsp;&nbsp;
                                        至<input id="rechargeTimeEnd"  class="Wdate" value="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"  type="text"/>&nbsp;&nbsp;
                                        <input id="bt_search" type="button" value="搜索"  /><input id="excel" type="button" value="导出Excel" name="excel"/>
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
