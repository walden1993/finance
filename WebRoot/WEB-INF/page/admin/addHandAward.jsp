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
                        <td class="f66" align="right" height="36px">期数编号：<font color="red">*</font></td>
                        <td class="f66" align="left" height="36px"><input type="text" name="termId"  id="termId"    /></td>
                    </tr>
					<tr>
						<td class="f66" align="right"height="36px">期数奖品编号：<font color="red">*</font></td>
						<td class="f66" align="left" height="36px">
						<input  type="text" name="awardInfoId" id="awardInfoId"/>
						</td>
					</tr>
					<tr>
						<td class="f66" align="right"height="36px">奖品管理编号：<font color="red">*</font></td>
						<td class="f66" align="left" height="36px">
						<input  type="text" name="paramId" id="paramId"/>
						</td>
					</tr>
                    <tr>
                        <td class="f66" align="right"  height="36px">用户ID：<font color="red">*</font></td>
                        <td class="f66" align="left" height="36px"><input  type="text" name="userId" id="userId"/></td>
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
			param["paramMap.termId"] = $("#termId").val();
			param["paramMap.paramId"] = $("#paramId").val();
            param["paramMap.userId"] = $("#userId").val();
            param["paramMap.awardInfoId"] = $("#awardInfoId").val();
            if(!$("#termId").val()  ||  !$("#paramId").val() ||   !$("#userId").val()  || !$("#awardInfoId").val()){
            	alert("带*的必填");
            	return;
            }
            
			$.dispatchPost("addHandAward.mht", param, function(data) {
				if(data>0){
					alert("新增成功！")
				}else{
					alert("新增失败！")
				}
			});
		}
	</script>
</body>
</html>
