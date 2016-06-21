<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>

		  <form id="editForm">
          <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>竞拍金额：</td>
    <td>
    <s:hidden name="paramMap.debtId"></s:hidden>
    <input type="text" id="auctionPrice" name="paramMap.auctionPrice" value="${paramMap.auctionPrice}" class="inp140" maxlength="20" /> 
    元<strong style="color: red;">*<br /><s:fielderror fieldName="paramMap.auctionPrice"></s:fielderror></strong></td>
  </tr>
  <tr>
    <td>交易密码：</td>
    <td><input type="password" id="pwd" name="paramMap.pwd" value="" class="inp140" maxlength="20" />&nbsp;<a href="querytraninput.mht">忘记密码?
    <strong style="color: red;"><br /><s:fielderror fieldName="paramMap.pwd"></s:fielderror></strong></td>
  </tr>
  <tr>
    <td> 验证码：</td>
    <td><input type="text" class="inp100x" name="paramMap.code" id="code"/>
		 <img src="admin/imageCode.mht?pageId=auction" title="点击更换验证码" style="cursor: pointer;"
		 	  id="codeNum" width="46" height="18" onclick="javascript:switchCode()" />
		 	  <strong style="color: red;"><br /><s:fielderror fieldName="paramMap.code"></s:fielderror></strong></td>
  </tr>
  <tr>
    <td colspan="2" class="tishi">注意：点击按钮表示您将确认投竞拍金额并同意支付。</td>
    </tr>
  <tr>
    <td colspan="2" align="center" style="padding-top:15px; padding-bottom:15px;"><input id="btnsave" type="button" style="cursor: pointer;" class="qdgmbtn" value=""
    /></td>
    </tr>
</table>
</form>
      


<script type="text/javascript">
var flag = true;
$(function(){
	 $('#btnsave').click(function(){
	   	 var param = $("#editForm").serializeArray();
	     if(flag){
	     flag=false;
	     $.post("addAuctingDebt.mht?showDate"+new Date().getTime(),param,function(data){
		  	if(data == "virtual"){
					window.location.href = "noPermission.mht";
					return ;
	     	}
		  	if(data == 1){
		  		alert("竞拍成功");
		  		window.location.href = "queryFrontAllDebt.mht";
		  	}else if(data == -1){
		  	   flag=true;
		  		alert("验证码错误");
		  	}else if(data == -2){
		  	    flag=true;
		  		alert("不能投自己转让的的债权");
		  	}else if(data == -3){
		  	     flag=true;
		  		alert("交易密码不对");
		  	}else if(data == -4){
		  	    flag=true;
		  		alert("可用余额不足");
		  	}else if(data == -5){
		  	     flag=true;
		  		alert("竞拍金额不能大于债权总额");
		  	}else if(data == -6){
		  	    flag=true;
		  		alert("竞拍金额不能小于转让者设置的最小竞拍金额");
		  	}else if(data == -7){
		  	    flag=true;
		  		alert("竞拍失败");
		  	}else if(data == -8){
		  	     flag=true;
		  		alert("对不起，该债权您只能竞拍两次!");
		  	}else if(data == -9){
		       	flag=true;
		  		alert("借款者不能竞拍该债权");
		  	}else if(data == -10){
		  	    flag=true;
		  		alert("第二次竞拍金额应大于第一次竞拍金额");
		  	}else if(data == -11){
		  	    flag=true;
		  		alert("竞拍金额要大于最高竞拍金额");
		  	}else{
		  	      flag=true;
		  		$("#div_editform").html(data);
		  	}
		    switchCode();
		 });
		 }
	 });
	 switchCode();
});

function switchCode(){
	    var timenow = new Date();
	    $('#codeNum').attr('src','admin/imageCode.mht?pageId=auction&d='+timenow);
};	
</script>

