<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
	<head>
		<title>还款信息统计</title>
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
							<td width="100" id="today" height="28"  class="xxk_all_a">
								<a href="borrowStatisInit.mht">借款管理费统计</a>
							</td>
							<td width="2">
								&nbsp;
							</td>
							<td width="120" id="tomorrow"  class="xxk_all_a">
								<a href="borrowStatisInterestInit.mht">投资利息管理费统计</a>
							</td>
							<td>
								&nbsp;
							</td>
							<td width="120" class="xxk_all_a">
                                <a href="borrowInfoDetailInit.mht">借款信息统计</a>
                            </td>
                            <td>
                                &nbsp;
                            </td>
                            </td>
                            <td width="120"  class="main_alll_h3">
                                <a href="investInfoDetailInit.mht">还款信息统计</a>
                            </td>
                            <td>
                                &nbsp;
                            </td>
							</tr>
					</table>
				</div>
				<div style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: 1120px; padding-top: 10px; background-color: #fff;">
					<table style="margin-bottom: 8px;" cellspacing="0" cellpadding="0"
						width="100%" border="0">
						<tbody>
							<tr>
								<td class="f66" align="left" width="50%" height="36px">
                                    借款标题:
                                    <input id="borrowTitle" value="" />&nbsp;&nbsp; 
                                    用户名:
                                    <input id="username" value="" />&nbsp;&nbsp; 
                                    借款类型:
                                    <s:select id="borrowWay"  list="#{'':'----全部----',1:'净值借款' ,2:'秒还借款', 3:'普通借款' ,4:'实地考察借款', 5:'机构担保借款',7:'债权转让借款' }"/>&nbsp;&nbsp; 
                                    复审日期:
                                    <input id="timeStart" class="Wdate" value="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>&nbsp;&nbsp;
                                    --
                                    <input id="timeEnd" class="Wdate" value="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>&nbsp;&nbsp;
                                </td>
							</tr>
							<tr>
                                <td class="f66" align="left" width="50%" height="36px">
                                    发布时间:
                                    <input id="createTimeStart" class="Wdate" value="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>&nbsp;&nbsp;
                                    --
                                    <input id="createTimeEnd" class="Wdate" value="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>&nbsp;&nbsp;
                                    应还款日:
                                    <input id="investTimeStart" class="Wdate" value="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>&nbsp;&nbsp;
                                    --
                                    <input id="investTimeEnd" class="Wdate" value="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>&nbsp;&nbsp;
                                    
                                    <input id="search" type="button" value="查找" name="search" />
                                    &nbsp;&nbsp;
                                    <input id="excel" type="button" value="导出Excel" name="excel" />
                                </td>
                            </tr>
                            <tr>
                            	<td  class="f66" align="left" width="50%" height="36px"> 邀请人:
                                    <input id="reffere" value="" />&nbsp;&nbsp; </td>
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
<script type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
			$(function(){
				initListInfo(param);
				$('#search').click(function(){
				   param["pageBean.pageNum"]=1;
				   initListInfo(param);
				});	
				
				$("#excel").click(function(){
					window.location.href="exportInvestInfoDetail.mht?pageBean.pageNum=1000 &&paramMap.borrowTitle="+$("#borrowTitle").val()+"&&paramMap.timeStart="+$("#timeStart").val()+"&&paramMap.investTimeStart="+$("#investTimeStart").val()+"&&paramMap.investTimeEnd="+$("#investTimeEnd").val()+"&&paramMap.timeEnd="+$("#timeEnd").val()+"&&paramMap.createTimeStart="+$("#createTimeStart").val()+"&&paramMap.createTimeEnd="+$("#createTimeEnd").val()+"&&paramMap.username="+$("#username").val()+"&&paramMap.borrowWay="+$("#borrowWay").val()+"&&paramMap.reffere="+$("#reffere").val();
				});					
			});			
			function initListInfo(praData){
				praData["paramMap.borrowTitle"] = $("#borrowTitle").val();
                praData["paramMap.username"] = $("#username").val();
                praData["paramMap.borrowWay"] = $("#borrowWay").val();
                praData["paramMap.timeStart"] = $("#timeStart").val();
                praData["paramMap.timeEnd"] = $("#timeEnd").val();
                praData["paramMap.createTimeStart"] = $("#createTimeStart").val();
                praData["paramMap.createTimeEnd"] = $("#createTimeEnd").val();
                praData["paramMap.investTimeStart"] = $("#investTimeStart").val();
                praData["paramMap.investTimeEnd"] = $("#investTimeEnd").val();
                praData["paramMap.reffere"] = $("#reffere").val();
                $.dispatchPost("investInfoDetail.mht",praData,initCallBack);
		 	}
		 	function initCallBack(data){
				$("#divList").html(data);
			}
</script>
</html>
