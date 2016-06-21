<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<%-- <table width="100%" border="0" cellspacing="0" cellpadding="0">
<s:if test="%{paramMap.result!=''&&paramMap.result!=null}">
  <tr>
    <td>认购份数：<input type="text" id="result" name="paramMap.result" class="inp140" maxlength="20" value="${paramMap.result}" /> 份</td>
    <input type="hidden" id="amount" name="paramMap.amount" class="inp140" maxlength="20" value="1" />
  </tr>
</s:if>
<s:else>
  <tr>
    <td>投标金额：<input type="text" id="amount" name="paramMap.amount" class="inp140" maxlength="20" value="${paramMap.amount}" /> 元</td>
    <td><span class="fred"><s:fielderror fieldName="paramMap['amount']"></s:fielderror></span></td>
  </tr>
</s:else>
  <tr>
    <td>交易密码：<input type="password" id="dealPWD" name="paramMap.dealPWD" class="inp140" maxlength="20" value="${paramMap.dealPWD}" /></td>
    <td><span class="fred" id="error"><s:fielderror fieldName="paramMap['dealPWD']"></s:fielderror></span></td>
  </tr>
  <s:if test="#session.hasPWD != -1">
    <tr>
    <td>投标密码：<input type="password" id="investPWD" name="paramMap.investPWD" class="inp140" maxlength="20" /></td>
    <td><span class="fred" id="error"><s:fielderror fieldName="paramMap['investPWD']"></s:fielderror></span></td>
  </tr>
  </s:if>
  <tr>
    <td colspan="2" class="tishi">注意：点击按钮表示您将确认投标金额并同意支付贷款</td>
    </tr>
  <tr>
    <td colspan="2" align="center" style="padding-top:15px; padding-bottom:15px;"><input id="btnsave" type="button" class="qdtbbtn" value="" /></td>
    </tr>
</table>
<script type="text/javascript">
var flag = true;
$(function(){
     //样式选中
     $("#licai_hover").attr('class','nav_first');
	 $("#licai_hover div").removeClass('none');
	 $('.tabmain').find('li').click(function(){
	    $('.tabmain').find('li').removeClass('on');
	    $(this).addClass('on');
	    $('.lcmain_l').children('div').hide();
        $('.lcmain_l').children('div').eq($(this).index()).show();
	 });
	 $('#btnsave').click(function(){
	     var id =$("#id").val();
	     param['paramMap.id'] = id;
	     param["paramMap.code"] = $("#code").val();
	     param['paramMap.annualRate'] = $("#annualRate").val();
	     param["paramMap.deadline"] = $("#deadline").val();
	     param['paramMap.amount'] = $('#amount').val();
	     param['paramMap.dealPWD'] = $('#dealPWD').val();
	     param['paramMap.result'] = $('#result').val();//认购份数
	     param['paramMap.investPWD'] = $('#investPWD').val();
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
});		     
</script>
 --%>
<s:fielderror fieldName="paramMap['amount']"></s:fielderror>
<s:fielderror fieldName="paramMap['dealPWD']"></s:fielderror>
<s:fielderror fieldName="paramMap['investPWD']"></s:fielderror>