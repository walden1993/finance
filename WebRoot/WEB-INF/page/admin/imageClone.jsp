<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<link rel="stylesheet" href="../script/bootstrap/css/bootstrap.css">
<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../css/popom.js"></script>
<script type="text/javascript" src="../script/jquery-2.0.js"></script>
<script type="text/javascript">
	
</script>
<body>
	<div class="span6 pagination-centered" style="margin-top: 20px;">
		<div class="span6">
		<form id="copyForm" name="copyForm" action="copyBorrowImage.mht">
			<table class=" table  table-bordered">
				<tr>
					<td colspan="2"> 填写克隆对应借款信息</td>
				</tr>
				<tr>
					<td>克隆借款ID：</td>
					<td><input type="text" id="toId" name="toId" onchange="queryFun(this);"/></td>
				</tr>
				<tr>
					<td>借款标题：</td>
					<td><span id="borrowTitle" ></span></td>
				</tr>
				<tr>
					<td><input type="hidden" id="fromId"  name="fromId" value=${id } />  </td>
					<td><input type="button" value="确认" name="subForm" onclick="copyBorrowImage()"/> &nbsp;&nbsp;
					<input type="reset" value="清空" />
					</td>
				</tr>
			</table>
		</form>
		</div>
	</div>

<script type="text/javascript" src="../script/jquery.dispatch.js"></script>
<script type="text/javascript">
$(function(){
	
});
function queryFun(obj){
	if ($("#toId").val() == $("#fromId").val()){
		alert("不能拷贝自己");
		return;
	}
	$.dispatchPost("queryBorrowTitle.mht?a=1&id="+obj.value+"&b=2",null,initCallBack2);
}
var aflag = false;
function initCallBack2(data){
	if (data.ret == "err"){
		$("#borrowTitle").html("<font color='#ff0000'>借款ID错误！</font>");
		return;
	}
	//alert("操作成功");
	aflag = true;
	$("#borrowTitle").html(data.ret);
}
function copyBorrowImage(){
	if (aflag){
		
		$("#copyForm").submit();
	} else {
		alert("请输入正确ID");
	}
	
}
</script>
</body>
