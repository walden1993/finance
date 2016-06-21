<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags"  prefix="s"%>
<%@include file="/include/taglib.jsp" %>
<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css">
<div style="padding: 15px 10px 0px 10px;">
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
							<td width="100" id="today" height="28"   class=xxk_all_a>
                                <a href="profitRecordAll.mht">收益记录</a>
                            </td>
                            <td width="2">
								&nbsp;
							</td>
							<td width="100" id="today" height="28"   class="main_alll_h2">
                                <a href="paydailyrecord.mht">每日统计</a>
                            </td>
                            <td width="2">
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
				<td class="f66" align="left" width="80%" height="36px">
					日期 ：
					<input type="text" id="publishTimeStart" name="publishTimeStart" value="${paramMap.publishTimeStart}" class="input_border w100" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly',maxDate:'#F{$dp.$D(\'publishTimeEnd\')}'})" readonly="">&nbsp;-&nbsp;<input type="text" id="publishTimeEnd" name="publishTimeEnd" value="${paramMap.publishTimeEnd}" class="input_border w100" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly',maxDate:'%y-%M-%d'})" readonly="">&nbsp;&nbsp;
					<%-- 操作类型：<s:select list="#{'':'--请选择--',1:'转入',2:'转出'}"  id="type"></s:select> --%>
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
 	$.dispatchPost("paydailyrecordList.mht",param,function(data){
        	$("#sysInfo").html(data);
     });
 }
</script>