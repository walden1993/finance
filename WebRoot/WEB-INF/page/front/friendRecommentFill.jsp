<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<div class="biaoge text-center m20">
<form id="recForm" action="fillRecomment.mht">
	<table align="center">
		<tr height="50">
			<td>
			推荐人用户名：
			</td>
			<td>
			<input type="text" id="recommentName" name="recommentName"  class="input" />
			</td>
		</tr>         
		<tr height="50">
			<td><input type="button" value="确定" class="button w100 ml20" onclick="resubmit()"/> &nbsp; &nbsp; </td>
			<td>
			<input type="button" value="取消" class="button_over w100"  onclick="closeBox()"/>
			</td>
		</tr>
	</table>
</form>
 <script type="text/javascript">
function resubmit(){
	var recommentName = $("#recommentName").val();
	if (recommentName==""){
		alert("请输入【推荐人用户名 】");
		return;
	}
	var param = {};
	param['paramMap.recommentName'] = recommentName;
	$.post("fillRecomment.mht",param,function(data){
		if ("0"==data){
			alert("您输入的用户名有误,请重新输入");
			$("#recommentName").focus();
		} else if ("2"==data){
			alert("您输入的推荐人不符合规则,请重新输入");
			$("#recommentName").focus();
		} else if ("3"==data){
			alert("您不能补填内部员工为推荐人,请重新输入");
			$("#recommentName").focus();
		} else{
			alert("补填成功!");
			window.parent.myreload();
		}
	});
}
function closeBox(){
	$.fancybox.close();
}

</script>

</div>

