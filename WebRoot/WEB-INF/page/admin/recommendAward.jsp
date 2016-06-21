<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
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
		<style type="text/css">
		.xxk_all_ax{ width:130px;     font-size:13px;   }
		.main_alll_hx2{ font-size:13px;  background:url(../images/admin/menu_top_bg1.png) no-repeat; width:130px;  line-height:30px;  margin-top:10px;  text-align:center;}
		</style>
	</head>
	<body>
		<div id="right">
			<div  style="padding: 15px 10px 0px 10px;">
				<div>
					<table  border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td height="28" class="main_alll_hx2">
								<a href="#">推荐有效好友奖励</a>
							</td>
							<td width="250" class="xxk_all_ax"   >
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
									成为有效会员时间:
									<input id="memberTimes" name="paramMap.memberTimes" type="text" class="Wdate"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:'readOnly'})"/>
									--
									<input id="memberTimee" name="paramMap.memberTimee" type="text" class="Wdate"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:'readOnly'})"/>
									
									<input id="selectBtn" type="button" value="筛选" name="selectBtn" />
									<input id="excelBtn" type="button" value="导出" name="excelBtn" />
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
<script type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>

<script type="text/javascript">
			$(function(){
				//initListInfo(param);
				$("#divList").html("");
				$('#selectBtn').click(function(){
				   param["pageBean.pageNum"]=1;
				   initListInfo(param);
				});	
				
				$("#excelBtn").click(function(){
					exportInfo();
				});
			});	
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
				window.location.href="redregAwardExcel.mht"+pstr;
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

				
		 		$.dispatchPost("recommendAwardList.mht",praData,initCallBack);
		 	}
		 	function initCallBack(data){
				$("#divList").html(data);
			}
</script>
</html>
