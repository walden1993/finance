<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
	<head>
		<title>投标记录管理首页</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
		<link href="../css/admin/admin_custom_css.css" rel="stylesheet" type="text/css" />
		<link id="skin" rel="stylesheet" href="../css/jbox/Skins/Blue/jbox.css" />
		<style type="text/css">
		.xxk_all_ax{ width:130px;     font-size:13px;   }
		.main_alll_hx2{ font-size:13px;  background:url(../images/admin/menu_top_bg1.png) no-repeat; width:130px;  line-height:30px;  margin-top:10px;  text-align:center;}
		</style>
	</head>
	<body>
		<div id="right">
			<div style="padding: 15px 10px 0px 10px;">
				<div>
					<table  border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="20%" id="today" height="28"  class="xxk_all_ax">
								<a href="recommendAward.mht">推荐有效好友奖励</a>
							</td>
							<td  id="tomorrow"  class="main_alll_hx2">
								<a href="redInvestAward.mht">推荐好友投资奖励</a>
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
									版本：
									<select id="sversion">
										<option value="v1">v1.0内部版</option>
										<option value="v2">v2.0会员版</option>
									</select>&nbsp;&nbsp; 
									
									用户名:
									<input type="text" id="userName" value="" size="8"/>&nbsp;&nbsp; 
									真实姓名：
									<input type="text" id="realName" value="" size="8"/>&nbsp;&nbsp;
									用户归属：
									<select id="isEmployee">
										<option value="">全部</option>
										<option value="1">内部员工</option>
										<option value="0">外部会员</option>
									</select>&nbsp;&nbsp; 
									奖励共计：
									<input type="text" id="awardFrom" value="" size="8"/>&nbsp;&nbsp;
									--
									<input type="text" id="awardTo" value="" size="8"/>&nbsp;&nbsp;
								</td>
							</tr>
							<tr>
								<td class="f66" align="left" width="50%" height="36px">
									投资时间:
									<input id="memberTimes" name="paramMap.memberTimes" type="text" class="Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:'readOnly'})"/>
									--
									<input id="memberTimee" name="paramMap.memberTimee" type="text" class="Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:'readOnly'})"/>
									
									<input id="selectBtn" type="button" value="筛选" name="selectBtn" />
									<input id="excelBtn" type="button" value="导出" name="excelBtn" />
									<input id="roleBtn" type="button" value="奖励规则" name="roleBtn" />
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
<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../script/jbox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript" src="../script/jbox/jquery.jBox-zh-CN.js"></script>
<script type="text/javascript" src="../script/jquery.dispatch.js"></script>
<script type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
var roleEmployee= "1）平台会员注册的角色分为：会员A，会员B（A推荐），会员C（B推荐）；\n"+
		"2）会员B成功投资后对会员A设定的奖励标准见下表。\n"+
		"投资期限     比率Z%\n"+
		"3个月内（不含3个月）     1.0%\n"+
		"3个月-6个月内（不含6个月）     1.2%\n"+
		"6个月-12个月内（不含12个月）     1.6%\n"+
		"12个月-24个月（不含24个月）     2.0%\n"+
		"24个月以上     2.5%\n"+
		"注意：以上比率会根据市场策略不断调整，具体结果以公司公布为准。\n"+
		 
		"3）会员C成功投资后，对B 的奖励见上表，对A 的奖励为对B奖励比率的20%，即若B得到的奖励为1% 则 A 得到的奖励为0.2%，若C再推荐会员D， 则对A不予奖励，而是对C的奖励见上表，对B的奖励为C得到奖励比率的20%。\n"+
		"4）奖金的计算及发放：\n"+
		"A、奖励金额总额=∑B的投资额*Z%*投资期限（按月计算）/12 +∑C的投资额*Z%*20%*投资期限（按月计算）/12；\n"+
		"B、每月奖励金额总额=∑B的投资额*Z%/12 +∑C的投资额*Z%*20%/12；\n"+
		"C、每月初由财务审核后直接发放到推荐人的平台账户上。\n"+
		"D、如果客户投标后中途强行退出，当月及后续奖励终止。\n"+
		"3、以下内容不奖励\n"+
		"1)投资债权转让的不予奖励；\n"+
		"2)体验标不予奖励。\n"+
		 "\n"+
		"只针对公司内部员工!";
		
		var role = "1）平台会员注册的角色分为：会员A，会员B（A推荐）；\n"+
		"2）会员B成功投资后对会员A设定的奖励标准见下表：\n"+
		"投资期限     比率Z%\n"+
		"1个月-3个月内（不含3个月）     0.08%\n"+
		"3个月-6个月内（不含6个月）     0.10%\n"+
		"6个月-12个月内（不含12个月）     0.12%\n"+
		"注意：以上比率会根据市场策略不断调整，具体结果以公司公布为准。\n"+
		"3）投资额统计效期：6个月（从注册时间开始计算）\n"+
		 " 即推荐人A推荐的会员B在6个月内的所有投资，均按照第二条的规则给予推荐人A提成奖励。超过6个月后被推荐人B继续投资，对A不予奖励。\n"+
		"4）结算方法及发放\n"+
		"a.推荐人A的奖励金额总额=∑B的投资额*Z%*投资期限（按月计算）；\n"+
		"b.当月结算，满10元即结清，不满10元在推荐人平台账户自动累加，不清零，次月1~5号充值到推荐人在三好资本平台的账户中。\n"+
		"c.以下内容不奖励\n"+
		"1)投体验标不予奖励；\n"+
		"2)投资期不满一个月不予奖励；\n"+
		"3)债权转让的投资不予奖励。(转让方不予奖励，竞拍方给予奖励)。\n"+
		 "\n"+
		"只针对外部会员"
		
		$("#roleBtn").click(function(){
			var sversion = $("#sversion").val();
			if(sversion==='v1'){
				alert(roleEmployee);
			}else{
				alert(role);
			}
		})

</script>
<script type="text/javascript">
			$(function(){
				//initListInfo(param);
				$("#divList").html("");
				$('#selectBtn').click(function(){
				   param["pageBean.pageNum"]=1;
				   initListInfo(param);
				});	
				$('#excelBtn').click(function(){
					   //param["pageBean.pageNum"]=1;
					   exportInfo();
				});	
			});	
			
			function recommendAwardDetail(id,level){
				var url= "recommendAwardDetailInit.mht?id="+id+"&level="+level+"&startInvestTime="+$("#memberTimes").val()+"&endInvestTime="+$("#memberTimee").val();
	            $.jBox("iframe:"+url, {
		            title: "推荐投资明细",
		            width: 900,
		            height: 500,
		            buttons: { '关闭': false }
	            });
            }
			
			function exportInfo(){
				var sversion = $("#sversion").val();
				var userName = $("#userName").val();
				var realName = $("#realName").val();
				var isEmployee = $("#isEmployee").val();
				var awardFrom = $("#awardFrom").val();
				var awardTo = $("#awardTo").val();
				var memberTimes = $("#memberTimes").val();
				var memberTimee = $("#memberTimee").val();
				var pstr = "?";
				if (sversion != ""){
					pstr += "sversion="+sversion+"&"
				}
				if (userName != ""){
					pstr += "userName="+userName+"&"
				}
				if (realName != ""){
					pstr += "realName="+realName+"&"
				}
				if (isEmployee != ""){
					pstr += "isEmployee="+isEmployee+"&"
				}
				if (awardFrom != ""){
					pstr += "awardFrom="+awardFrom+"&"
				}
				if (awardTo != ""){
					pstr += "awardTo="+awardTo+"&"
				}
				if (memberTimes != ""){
					pstr += "memberTimes="+memberTimes+"&"
				}
				if (memberTimee != ""){
					pstr += "memberTimee="+memberTimee+"&"
				}
				pstr = pstr.substring(0, pstr.length-1) ;
				window.location.href="redInvestAwardExcel.mht"+pstr;
			    setTimeout("test()",3000);
		 	}
			function test(){
			   $("#excelBtn").attr("disabled",false);
			   }
			function initListInfo(praData){
				praData["paramMap.sversion"] = $("#sversion").val();
				praData["paramMap.userName"] = $("#userName").val();
				praData["paramMap.realName"] = $("#realName").val();
				praData["paramMap.isEmployee"] = $("#isEmployee").val();
				praData["paramMap.awardFrom"] = $("#awardFrom").val();
				praData["paramMap.awardTo"] = $("#awardTo").val();
				praData["paramMap.memberTimes"] = $("#memberTimes").val();
				praData["paramMap.memberTimee"] = $("#memberTimee").val();

				if ($("#userName").val()=="" && $("#realName").val()=="" &&
						$("#isEmployee").val()=="" && $("#awardFrom").val()=="" &&
						$("#awardTo").val()=="" && $("#memberTimes").val()=="" &&
						$("#memberTimee").val()=="" ){
					alert("请至少选择一个查询条件！");
					return;
				}

				
		 		$.dispatchPost("retInvestAwardList.mht",praData,initCallBack);
		 	}
		 	function initCallBack(data){
				$("#divList").html(data);
			}
</script>
</html>
