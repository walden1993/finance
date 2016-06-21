<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<jsp:include page="/include/head.jsp"></jsp:include>
</head>
<body>
<input type="hidden" id="payId" value="${payMap.id}"/>
<div class="nymain" style="width:300px;margin-top:-10px;">
  <div class="wdzh"  >
    <div class="r_main"  >
      <div class="box text-center" >
        <div class="boxmain2"  >
          <div class="biaoge2"  >
        <table width="100%" style="line-height: 30px;" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>账户余额： </td>
    <td><strong>${payMap.totalSum}元 </strong></td>
  </tr>
  <tr>
    <td>可用余额： </td>
    <td><strong>${payMap.usableSum }元 </strong></td>
  </tr>
  <tr>
    <td>还款日期： </td>
    <td><strong> ${payMap.repayDate }  </strong></td>
  </tr>
  <tr>
    <td>待还本息：</td>
    <td><strong>${payMap.forPI }元</strong></td>
  </tr>
  <tr>
    <td>逾期本息：</td>
    <td><strong>${payMap.lateFI }元</strong></td>
  </tr>
  <tr>
    <td>需还总额：</td>
    <td><strong>${payMap.needSum }元</strong></td>
  </tr>
  <tr>
    <td>交易密码：</td>
    <td><input type="password" class="inp140" id="pwd" maxlength="20" style="border: 1px solid #ddd"/>
    <span style="color: red; float: none;" id="pwd_tip" class="formtips"></span>
    </td>
  </tr>
  <tr>
    <td>验证码：</td>
    <td><input type="text" class="inp100x" name="paramMap.code" style="border: 1px solid #ddd" id="code"/>
		 <img src="admin/imageCode.mht?pageId=vip" title="点击更换验证码" style="cursor: pointer;"
		 	  id="codeNum" width="46" height="18" onclick="javascript:switchCode()" /></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td style="padding-top:20px;">
    <input type="button" id="btnsave" class="button w140" style="cursor:pointer;" value="确认还款"/></td>
    <!--<a style="cursor:pointer;" id="btnsave" class="bcbtn">确认还款</a></td>-->
  </tr>
        </table>
        </div>
    </div>
</div>
    </div>
  </div>
</div>
<script>
var flag = true;
$(function(){
     $('#btnsave').click(function(){
	     param['paramMap.id'] = $("#payId").val();
	     param["paramMap.code"] = $("#code").val();
	     param['paramMap.pwd'] = $('#pwd').val();
	     if(flag){
			$("#btnsave").attr("disabled",true);
	     	$('#btnsave').attr("value","受理中...");
	     	$.dispatchPost('submitPay.mht',param,function(data){
		   		var callBack = data.msg;
		   		if(callBack == ''){
		   		  flag = false;
		          alert("操作成功!");
		          parent.location.reload();
		          return false;
		   		}
		   		alert(callBack);
		   		switchCode();
		   		$("#btnsave").attr("disabled",false);
	       		$('#btnsave').attr("value","确认还款");
	       		flag = true;
			});	     
	     }
	 });
	 switchCode();
});
function switchCode(){
	    var timenow = new Date();
	    $('#codeNum').attr('src','admin/imageCode.mht?pageId=invest&d='+timenow);
};
</script>
</body>
</html>
