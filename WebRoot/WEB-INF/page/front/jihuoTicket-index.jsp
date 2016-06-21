<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>三好资本</title>
<jsp:include page="/include/head.jsp"></jsp:include>
<link href="css/css.css" rel="stylesheet" type="text/css" />
<style>
.nymain p{padding: 0;text-indent: 0}</style>
</head>
<body>
<s:hidden name="paramMap.param" id="param"  />
<div class="nymain" style="width: 560px;margin: 0 auto;line-height: 22px;text-align: justify;height: 200px;" >
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td><h2 style="font-size: 24px;padding-top:50px;">体验券号：<input type="text"    id="ticketNo" name="paramMap.ticketNo" class="inp140" maxlength="20"  size="20"  style="width: 200px;height: 28px;"  /></h2></td>
	    <td><p class="error_tip"><span id="s_ticketNo" style="color: blue;"></span></p></td>
	  </tr>
	</table>
    <%--<div class="zw">
    </div>
    --%><div class="btn" id="btn_submit" style="margin-left:180px; margin-top:0px; position:absolute;">
    	<p><input id="btn_activate" style="margin-top: 20px;font-size: 20px;background-color: #4CB7FF;padding: 2px;width: 100px;height: 40px;color: #fff;" type="button" value="激活" onclick="jihuo()"/></p>
    </div>
</div>
<script type="text/javascript">
var flag = true;

function jihuo(){
		if($("#ticketNo").val()==""){
			alert("请输入体验券号");
			return;
		}
		param['paramMap.ticketNo'] = $("#ticketNo").val();
		$.dispatchPost('activate.mht', param, function(data) {
            if(data.ret == -1){
                alert(data.ret_desc);
                window.parent.history.go(0);
                window.parent.window.jBox.close();
            }else{
                alert(data.ret_desc)
            }
		});
}

<%--$(function(){
	 $('#btnactivate').click(function(){
	     var id =$("#id").val();
	     param['paramMap.id'] = id;
	     param['paramMap.ticketNo'] = $('#ticketNo').val();
	     
	     if(flag){
           flag = false;
	       $.dispatchPost('financeInvest.mht',param,function(data){
		      var callBack = data.msg;
		      if(callBack == undefined || callBack == ''){
		         $('#content').html(data);
		         flag = true;
		      }else{
		         if(callBack == 1){
		          alert("操作成功!");
		          window.location.href= 'financeDetail.mht?id='+id;
		          flag = false;
		          return false;
		         }
		         alert(callBack);
		         flag = true;
		      }
		    });
		 }
	 });
	 
});	--%>

//页面input失去焦点时验证
$(document).ready(function(){
	//失去焦点
	$("form :input").blur(function(){ 
		//体验券号
		if($(this).attr("id")=="ticketNo"){
			if($(this).val()==""){
				$("#s_ticketNo").attr("class", "formtips onError");
				 document.getElementById("s_ticketNo").style.color='red';
				$("#s_ticketNo").html("体验券号不能为空"); 
			}else if($(this).val()==""){
				$("#s_ticketNo").attr("class", "formtips onError");
				 document.getElementById("s_ticketNo").style.color='red';
				$("#s_ticketNo").html("体验券号不能为空"); 
			}else{
				$("#s_ticketNo").attr("class", "formtips");
				$("#s_ticketNo").html(""); 
				param['paramMap.ticketNo'] = $('#ticketNo').val();
				$.post("queryTicketNo.mht",param,function(data){
					if(data == 1){
						$("#s_ticketNo").html("绑定失败，该体验券不可用！");
					}else if(data == 2){
						$("#s_ticketNo").html("绑定失败，该体验券已经被绑定！");
					}else if(data == 3){
						$("#s_ticketNo").html("绑定失败，该体验券已过期！");
					}else{
						$("#s_ticketNo").html("");
					}
				});
				
			}
		}
	});
});
</script>


</body>
</html>
