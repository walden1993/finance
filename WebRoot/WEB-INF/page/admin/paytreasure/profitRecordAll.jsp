<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
	<head>
		<title>涨薪宝</title>
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
				 param["paramMap.username"] = $("#userName").val();
				 param["paramMap.realName"] = $("#realName").val();
				 param["paramMap.cellPhone"] = $("#phone").val();
				 param["paramMap.publishTimeEnd"] = $("#publishTimeEnd").val();
				 param["paramMap.publishTimeStart"] = $("#publishTimeStart").val();
			     initListInfo(param);
				});
			
					
			});
			
			function initListInfo(praData){
			    
		 		$.post("profitRecordAllList.mht",praData,initCallBack);
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
							<td width="100" height="28" class="xxk_all_a">
								<a href="paytreasureInit.mht">涨薪宝用户记录</a>
							</td>
							<td width="2">
								&nbsp;
							</td>
                            <td width="100" id="today" height="28"   class="xxk_all_a">
                                <a href="payInvestRecordAll.mht">转入转出记录</a>
                            </td>
                            <td width="2">
								&nbsp;
							</td>
							<td width="100" id="today" height="28"   class="main_alll_h2">
                                <a href="profitRecordAll.mht">收益记录</a>
                            </td>
                            <td width="2">
								&nbsp;
							</td>
							<td width="100" id="today" height="28"   class="xxk_all_a">
                                <a href="paydailyrecord.mht">每日统计</a>
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
										收益时间 ：
					<input type="text" id="publishTimeStart" name="publishTimeStart" value="${paramMap.publishTimeStart}" class="input_border w100" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly',maxDate:'#F{$dp.$D(\'publishTimeEnd\')}'})" readonly="">&nbsp;-&nbsp;<input type="text" id="publishTimeEnd" name="publishTimeEnd" value="${paramMap.publishTimeEnd}" class="input_border w100" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly',maxDate:'%y-%M-%d'})" readonly="">										<input id="bt_search" type="button" value="搜索"  /><input id="excel" type="button" value="导出Excel" name="excel"/>
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
