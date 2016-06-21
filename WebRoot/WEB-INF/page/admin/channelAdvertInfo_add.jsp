<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
	<head>
		<title>广告管理</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../script/jquery-2.0.js"></script>
		<script type="text/javascript" src="../script/jquery.dispatch.js"></script>
		
	</head>
	<body>
<div style="padding: 15px 10px 0px 10px;">
	<table border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td width="100" height="28" class="xxk_all_a">
				<a href="advertInfoMsg.mht">广告管理</a>
			</td>
			<td width="100" height="28" class="main_alll_h2">
				<a href="advertInfoGotoAdd.mht">新增广告</a>
			</td>
		</tr>
	</table>
</div>

<div style="padding: 10px 10px 0px 10px;">
		<table id="addChannelTab" style="width: 100%;  "
			cellspacing="1" cellpadding="3"   border="0">
			<tbody>
			
			
			<tr >
					<td style="width: 150px;" align="right" scope="col">
						*广告名称：
					</td>
					<td >
						<input type="text" id="advertName" value=""/>
					</td>
				</tr>
				<tr >
					<td style="width: 150px;"  align="right" >
						*渠道简称：
					</td>
					<td style="width: 80px;" scope="col">
						<select id="channelId">
						  <option value="">请选择</option>
						  <option value="1">硬广</option>
						  <option value="2">网盟</option>
						  <option value="3">搜索引擎</option>
						  <option value="4">CPS</option>
						  <option value="5">社会化媒体</option>
						</select>
					</td>
				</tr>
				<tr >
					<td style="width: 150px;" align="right" scope="col">
						*跟踪编号：
					</td>
					<td >
						100002
					</td>
				</tr>
				<tr >
					<td style="width: 150px;" align="right" scope="col">
						*渠道方页面：
					</td>
					<td >
						<select id="pageId">
						  <option value="">请选择</option>
						  <option value="1">硬广</option>
						  <option value="2">网盟</option>
						  <option value="3">搜索引擎</option>
						  <option value="4">CPS</option>
						  <option value="5">社会化媒体</option>
						</select>
					</td>
				</tr>
				<tr >
					<td style="width: 150px;" align="right" scope="col">
						*广告位置：
					</td>
					<td >
						<input type="text" id="pagePosition" value=""/>
					</td>
				</tr>
				<tr >
					<td style="width: 150px;" align="right" scope="col">
						*展示方式：
					</td>
					<td >
						<input type="radio" name="dispalyStyle"/>
					</td>
				</tr>
				<tr >
					<td style="width: 150px;" align="right" scope="col">
						*广告形式：
					</td>
					<td >
						<input type="radio" name="advertStyle"/>
					</td>
				</tr>
				
				<tr >
					<td style="width: 150px;" align="right" scope="col">
						目标地址：
					</td>
					<td >
						<input type="text" id="url" value=""/>
					</td>
				</tr>
				
				<tr >
					<td style="width: 150px;" align="right" scope="col">
						目标页面名称：
					</td>
					<td >
						<input type="text" id="infoName" value=""/>
					</td>
				</tr>
				
				<tr >
					<td style="width: 150px;" align="right" scope="col">
						是否收费：
					</td>
					<td >
						<input type="radio" name="ifee" value="0"/>
					</td>
				</tr>
				<tr >
					<td style="width: 150px;" align="right" scope="col">
						计费方式：
					</td>
					<td >
						<select id="feeType">
						  <option value="">请选择</option>
						  <option value="1">硬广</option>
						  <option value="2">网盟</option>
						  <option value="3">搜索引擎</option>
						  <option value="4">CPS</option>
						  <option value="5">社会化媒体</option>
						</select>
					</td>
				</tr>
				
				<tr >
					<td style="width: 150px;" align="right" scope="col">
						计费参数：
					</td>
					<td >
					<input type="text" id="feeParam" value="0"/>
						<select id="feeParamCell">
						  <option value="">请选择</option>
						  <option value="1">硬广</option>
						  <option value="2">网盟</option>
						  <option value="3">搜索引擎</option>
						  <option value="4">CPS</option>
						  <option value="5">社会化媒体</option>
						</select>
					</td>
				</tr>
				<tr >
					<td style="width: 150px;" align="right" scope="col">
						合作时间：
					</td>
					<td >
					<input id="coStartTime" class="Wdate" value="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/>&nbsp;&nbsp;
					
					 - 
					<input id="coEndTime" class="Wdate" value="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/>
					</td>
				</tr>
				<tr >
					<td style="width: 150px;" align="right" scope="col">
						逾期是否有效：
					</td>
					<td >
					<input type="radio" name="ieffect" value="0"/>
					</td>
				</tr>
				
				<tr >
					<td style="width: 150px;" align="right" scope="col">
						创建人：
					</td>
					<td >
					${admin.userName }
					</td>
				</tr>
				
				
				
				<tr >
					<td style="width: 150px;" align="right" scope="col">
						备注：
					</td>
					<td >
						<textarea id="remark" rows="4" cols="30"></textarea>
					</td>
				</tr>
				<tr >
				<td style="width: 150px;" align="right" scope="col">
						<input type="button" id="saveBtn" value="保存" />
					</td>
					<td >
						<input type="reset"  value="取消" />
					</td>
				</tr>
			</tbody>
		</table>
</div>
</body>
<script type="text/javascript" src="../script/jquery-2.0.js"></script>
<script type="text/javascript" src="../script/jquery.dispatch.js"></script>
<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">

$(function(){
	$("#saveBtn").click(function(){

    	param["paramMap.channelId"] = $("#channelId").val();
    	param["paramMap.pageId"] = $("#pageId").val();
    	param["paramMap.advertName"] = $("#advertName").val();
    	param["paramMap.pagePosition"] = $("#pagePosition").val();
    	param["paramMap.dispalyStyle"] = $("#dispalyStyle").val();
    	param["paramMap.advertStyle"] = $("#advertStyle").val();
    	param["paramMap.url"] = $("#url").val();
    	param["paramMap.infoName"] = $("#infoName").val();
    	param["paramMap.ifee"] = $("#ifee").val();
    	param["paramMap.feeType"] = $("#feeType").val();
    	param["paramMap.feeParam"] = $("#feeParam").val();
    	param["paramMap.feeParamCell"] = $("#feeParamCell").val();
    	param["paramMap.coStartTime"] = $("#coStartTime").val();
    	param["paramMap.coEndTime"] = $("#coEndTime").val();
    	param["paramMap.ieffect"] = $("#ieffect").val();
    	param["paramMap.remark"] = $("#remark").val();
    	param["paramMap.createBy"] = $("#createBy").val();

		if ($("#advertName").val() == ""){
			alert("【广告名称】不能为空");
			return;
		}
		if ($("#channelId").val() == ""){
			alert("【渠道简称】不能为空");
			return;
		}
		if ($("#pageId").val() == ""){
			alert("【渠道方页面】不能为空");
			return;
		}
		if ($("#pagePosition").val() == ""){
			alert("【广告位置】不能为空");
			return;
		}
		if ($("#dispalyStyle").val() == ""){
			alert("【展示方式】不能为空");
			return;
		}
		if ($("#advertStyle").val() == ""){
			alert("【广告形式】不能为空");
			return;
		}
		
		$.post("pageInfoAdd.mht",param,initCallBack1);
	});
	
});


function initCallBack1(data){
	alert("保存成功");
}
</script>
</html>