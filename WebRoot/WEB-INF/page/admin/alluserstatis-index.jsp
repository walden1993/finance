<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
	<head>
		<title>用户统计首页</title>
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
							<td width="100" id="today" height="28"  class="main_alll_h2">
								<a href="allUserStatics.mht">总报表</a>
							</td>
							<td width="2">
								&nbsp;
							</td>
							<td width="100" id="tomorrow"  class="xxk_all_a">
								<a href="monthUserStatics.mht">平台月报表</a>
							</td>
							<td>
								<a href="alldayStatics.mht" class="xxk_all_a">平台日统计</a>
							</td>
							</tr>
					</table>
					
				</div>
				<div style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: 1120px; padding-top: 10px; background-color: #fff;">
					
		            <!-- span id="divList"><img src="../images/admin/load.gif" class="load" alt="加载中..." /></span>  -->
		          <div align="right">  <input type="button" id="excelExport" value="导出" /> </div>
会员统计 
<table style="width: 100%; color: #333333;"
			cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
<tr class=gvHeader>
<td>累计会员数</td>
<td>有推荐人会员数</td>
<td>无推荐人的会员数</td>
<td>有充值记录的会员数</td>
<td>有投标记录的会员数</td>
</tr>
<tr class="gvItem">
<td>${all_user}</td>
<td>${recommendUser}</td>
<td>${justaUser}</td>
<td>${hasRecharge}</td>
<td>${hasInvest}</td>
</tr>

</table>
<br/>
借款统计
<table style="width: 100%; color: #333333;"
			cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
<tr class=gvHeader>
<td>发布借款数</td>
<td>借款金额</td>
<td>平均金额</td>
<td>最长满标时间</td>
<td>对应借款</td>
<td>最短满标时间</td>
<td>对应借款</td>
<td>平均满标时间</td>
</tr>
<tr class="gvItem">
<td>${publishBorrowNo}</td>
<td>${borrowAmt}</td>
<td>${borrowAvg}</td>
<td>${borrowTimeMax}</td>
<td>${borrowItemMax}</td>
<td>${borrowTimeMin}</td>
<td>${borrowItemMin}</td>
<td>${borrowTimeAvg}</td>
</tr>

</table>
<br/>
充值、提现统计
<table style="width: 100%; color: #333333;"
			cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
<tr class=gvHeader>
<td>累计充值金额</td>
<td> 累计线上充值金额</td>
<td>线上充值手续费 </td>
<td>累计手工充值金额 </td>
<td>累计提现金额 </td>
<td>累计提现手续费 </td>
</tr>
<tr class="gvItem">
<td>${allRecharge}</td>
<td>${onlineRecharge}</td>
<td>${onlineCostFee}</td>
<td>${backerAmt}</td>
<td>${cashToBank }</td>
<td>${cashToBankFee }</td>
</tr>

</table>
<br/>
平台统计
<table style="width: 100%; color: #333333;"
			cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
<tr class=gvHeader>
<td>累计收取管理费、服务费</td>
<td> 累计投资金额 </td>
<td> 累计发放利息 </td>
<td> 累计营销支出（奖励充值、体验券利息） </td>
</tr>
<tr class="gvItem">
<td>${manageFee}</td>
<td>${investAmt }</td>
<td>${intrestAmt }</td>
<td>${macketFee }</td>
</tr>

</table>


<div>
</div>
</div>
			</div>
		</div>
	</body>
<script type="text/javascript" src="../script/jquery-2.0.js"></script>
<script type="text/javascript" src="../script/jquery.dispatch.js"></script>
<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
	$(function(){
		
		$('#excelExport').click(function(){
		   param["pageBean.pageNum"]=1;
		   exportInfo(param);
		});
	});			
	
	function exportInfo(praData){
		window.location.href="allUserStaticsExcel.mht";
	    setTimeout("test()",3000);
 	}
 	
 	function initCallBack(data){
		$("#divList").html(data);
	}
 	function test(){
	   $("#excelExport").attr("disabled",false);
	}
	
</script>
</html>
