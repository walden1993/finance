<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
	<head>
		<title>资料下载-内容维护</title>
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
				<a href="channelMsg.mht">渠道方管理</a>
			</td>
			<td width="100" height="28" class="main_alll_h2">
				<a href="channelMsgGotoAdd.mht">新增渠道</a>
			</td>
		</tr>
	</table>
</div>

<div style="padding: 10px 10px 0px 10px;">
		<table id="addChannelTab" style="width: 100%;  "
			cellspacing="1" cellpadding="3"   border="0">
			<tbody>
				<tr >
					<td style="width: 100px;"  align="right" >
						*类型：
					</td>
					<td style="width: 80px;" scope="col">
						<select id="ctype">
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
					<td style="width: 100px;" align="right" scope="col">
						*渠道全称：
					</td>
					<td >
						<input type="text" id="channelName" value=""/>
					</td>
				</tr>
				<tr >
					<td style="width: 100px;" align="right" scope="col">
						*渠道简称：
					</td>
					<td >
						<input type="text" id="channelDesc" value=""/>
					</td>
				</tr>
				<tr >
					<td style="width: 100px;" align="right" scope="col">
						*系统编码：
					</td>
					<td >
						<input type="text" id="sysCode" value=""/>
					</td>
				</tr>
				<tr >
					<td style="width: 100px;" align="right" scope="col">
						渠道编号：
					</td>
					<td >
						<input type="text" id="channelCode" value=""/>
					</td>
				</tr>
				<tr >
					<td style="width: 100px;" align="right" scope="col">
						创建人：
					</td>
					<td >
						${admin.userName }
					</td>
				</tr>
				
				<tr >
					<td style="width: 100px;" align="right" scope="col">
						备注：
					</td>
					<td >
						<textarea id="remark" rows="4" cols="30"></textarea>
					</td>
				</tr>
				<tr >
				<td  colspan="2"> 
				  <input type="button" id="saveBtn" value="保存" />
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
		param["pageBean.pageNum"] = 1;
    	param["paramMap.ctype"] = $("#ctype").val();
    	param["paramMap.channelName"] = $("#channelName").val();
    	param["paramMap.channelDesc"] = $("#channelDesc").val();
    	param["paramMap.sysCode"] = $("#sysCode").val();
    	param["paramMap.channelCode"] = $("#channelCode").val();
    	param["paramMap.remark"] = $("#remark").val();

		if ($("#ctype").val() == ""){
			alert("【类型】不能为空");
			return;
		}
		if ($("#channelName").val() == ""){
			alert("【渠道全称】不能为空");
			return;
		}
		if ($("#channelDesc").val() == ""){
			alert("【渠道简称】不能为空");
			return;
		}
		if ($("#sysCode").val() == ""){
			alert("【系统编码】不能为空");
			return;
		}
		
		$.post("channelMsgAdd.mht",param,initCallBack1);
	});
	
});


function initCallBack1(data){
	$("#dataInfo").html(data);
}
</script>
</html>