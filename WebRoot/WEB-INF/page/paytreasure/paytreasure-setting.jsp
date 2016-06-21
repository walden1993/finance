<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!-- 引用头部公共部分 -->
<div class="nymain">
  <div class="wdzh">
    <div class="r_main  bg-white">
      <div class="box">
      <div class="boxmain2">
      <div class="biaoge2" style="margin-top:0px;">
    <table border="0" cellspacing="0" cellpadding="0" class="listab text-center" > 
	  <tr>
	    <td align="right">开启自动转入：</td>
	    <td align="left"><s:select list="#{1:'关闭',0:'开启'}" name="paramMap.open"  id="open" onchange="selectAuto(this.value)"/></td>
	  </tr>
	  <tr id="tr_minBalance">
	  	<td align="right">保留金额：</td>
	  	<td align="left"><input name="paramMap.minBalance" type="text"  value="${paramMap.minBalance}" id="minBalance"   maxlength="10"   style="ime-mode:disabled;" ondragenter="return false" onpaste="return!clipboardData.getData('text').match(/[\u4e00-\u9fa5]/gi)" onkeypress="if(event.keyCode<48||event.keyCode>57||event.keyCode==8)event.returnValue=false;" placeholder="请输入保留金额" autocomplete="off"/>元</td>
	  </tr>
	  <tr>
	    <td colspan="2" style="padding-top:5px;">
	   	<a href="javascript:void(0);" id="setbtn" class="button w140">保存</a>
	    </td>
  	</tr>
    </table>
    </div>
    <p class="tips" style="margin-top:15px;text-align: left"><strong> 自动转入说明：</strong><br/>
    
1、每日早上6:00自动将保留金额之外的三好资本可用余额自动转入涨薪宝，轻松开启张新宝每日财富增值之旅。<br/>

注：转入资金可随时转出至三好资本可用余额。<br/>

        </div>
</div>
    </div>
  </div>
  <script>
$(function(){
     $('#setbtn').click(function(){
         param['paramMap.open']=$('#open').val();
         param['paramMap.minBalance']=$('#minBalance').val();
         $.dispatchPost("payTreasureSettingSave.mht",param,function(data){
         	if(data==1){
         		show("请输入保留金额")
         	}else if(data==2){
         		show("只能保留两位小数")
         	}else if(data==2){
         		show("操作失败")
         	}else{
         		show("操作成功",initListInfo1("payTreasureSetting.mht"))
         	}
      	});
     });
     selectAuto(${paramMap.open});
});      

function selectAuto(va){
	if(va==0){
		$("#tr_minBalance").show();
	}else{
		$("#tr_minBalance").hide();
	}
}
</script>
</div>

