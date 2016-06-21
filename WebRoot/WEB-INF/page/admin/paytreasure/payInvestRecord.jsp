<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags"  prefix="s"%>
<%@include file="/include/taglib.jsp" %>
<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css">
<div style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: 1120px; padding-top: 10px; background-color: #fff;">
	<table style="margin-bottom: 8px;" cellspacing="0" cellpadding="0"
		width="100%" border="0">
		<tbody>
			<tr>
				<td class="f66" align="left" width="80%" height="36px">
					操作时间 ：
					<input type="text" id="publishTimeStart" name="publishTimeStart" value="${paramMap.publishTimeStart}" class="input_border w100" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly',maxDate:'#F{$dp.$D(\'publishTimeEnd\')}'})" readonly="">&nbsp;-&nbsp;<input type="text" id="publishTimeEnd" name="publishTimeEnd" value="${paramMap.publishTimeEnd}" class="input_border w100" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly',maxDate:'%y-%M-%d'})" readonly="">&nbsp;&nbsp;
					操作类型：<s:select list="#{'':'--请选择--',1:'转入',2:'转出'}"  id="type"></s:select>
					<input id="bt_search" type="button" value="搜索"   onclick="initListInfo()"/><input id="excel" type="button" value="导出Excel" name="excel"/>
				</td>
			</tr>
		</tbody>
	</table>
</div>
<div id="sysInfo">

</div>
<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../script/jquery.dispatch.js"></script>
<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
<script>

$(function(){
	initListInfo();
})

function initListInfo(){
	param["id"]=GetRequest("id");
	param["paramMap.publishTimeEnd"] = $("#publishTimeEnd").val();
	param["paramMap.publishTimeStart"] = $("#publishTimeStart").val();
	param["paramMap.type"] = $("#type").val();
 	$.dispatchPost("payInvestRecordList.mht",param,function(data){
        	$("#sysInfo").html(data);
     });
 }
</script>