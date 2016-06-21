<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
<head>
<title>新增奖品</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
<meta http-equiv="description" content="This is my page" />
<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
<link href="../css/admin/admin_custom_css.css" rel="stylesheet"
	type="text/css"
/>
</head>
<body>
	<div >
		<div style="padding: 10px;">
			<table style="border:0px">
				<tbody>
				    <tr>
                        <td class="f66" align="right" height="36px">编号：<font color="red">*</font></td>
                        <td class="f66" align="left" height="36px">${id}<input type="hidden"  id="ternId"  value="${id}"  /></td>
                    </tr>
                    <tr>
                        <td class="f66" align="left" height="36px">中奖状态：<font color="red">*</font></td>
                        <td class="f66" align="left" height="36px">
                          <s:select id="status" name="status" list="#{1:'中奖',0:'未中奖'}"></s:select>
                        </td>
                    </tr>
					<tr>
						<td class="f66" align="right"height="36px">奖品名称：<font color="red">*</font></td>
						<td class="f66" align="left" height="36px">
						<s:select id="param" name="param" list="#request.awardParams"  headerKey=""  headerValue="----请选择-----"    listKey="id" listValue="awardName"></s:select>
						</td>
					</tr>
					<tr>
                        <td class="f66" align="right" height="36px">是否可中：<font color="red">*</font></td>
                        <td class="f66" align="left" height="36px">
                        <s:select id="isUsable" name="isUsable" list="#{0:'不可中',1:'可中'}"></s:select>
                        </td>
                    </tr>
                    <tr>
                        <td class="f66" align="right"  height="36px">排序：<font color="red">*</font></td>
                        <td class="f66" align="left" height="36px"><input  type="text" name="orderby" id="orderby"/></td>
                    </tr>
                    <tr>
                        <td class="f66" align="right" height="36px">中奖概率：<font color="red">*</font></td>
                        <td class="f66" align="left" height="36px"><input type="text" name="hitRate" id="hitRate"/>%</td>
                    </tr>
                     <tr>
                        <td class="f66" align="right" height="36px">中奖次数：<font color="red">*</font></td>
                        <td class="f66" align="left" height="36px"><input type="text" name="hitTime" id="hitTime"/></td>
                    </tr>
                    <tr>
                        <td class="f66" align="right" height="36px">实际价值：<font color="red"></font></td>
                        <td class="f66" align="left" height="36px"><input type="text" value="0" name="realMoney" id="realMoney"/>(注:用于自动化发放奖品抵用为平台金额)</td>
                    </tr>
					<tr>
						<td class="f66" align="center" height="36px" colspan="2"><input type="button" onclick="savePrizeParam()" name="savePrize" value="保存"/></td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
	<script type="text/javascript" src="../script/jquery-2.0.js"></script>
	<script type="text/javascript" src="../script/jquery.dispatch.js"></script>
	<script type="text/javascript">
		function savePrizeParam() {
			param["paramMap.termId"] = ${id};
			param["paramMap.status"] = $("#status").val();
			param["paramMap.paramId"] = $("#param").val();
			param["paramMap.isUsable"] = $("#isUsable").val();
            param["paramMap.byOrder"] = $("#orderby").val();
            param["paramMap.hitRate"] = $("#hitRate").val();
            param["paramMap.hitTime"] = $("#hitTime").val();
            param["paramMap.realMoney"] = $("#realMoney").val();
            if(!$("#ternId").val()  ||  !$("#param").val() ||   !$("#orderby").val()  || !$("#hitRate").val() || !$("#hitTime").val()){
            	alert("带*的必填");
            	return;
            }
            
			$.dispatchPost("saveAwardInfo.mht", param, function(data) {
				if(data>0){
					alert("新增成功！")
					window.parent.initListInfo();
					window.parent.jBox.close();
				}else{
					alert("新增失败！")
				}
			});
		}
	</script>
</body>
</html>
