<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>标的详情</title>
    
	<jsp:include page="html5meta.jsp"></jsp:include>
	<link rel="stylesheet" type="text/css"
	href="script/bootstrap/css/bootstrap.min.css">

  </head>
  
  <body>
    <div class="table-responsive">
    	<table class="table">
    		<tr>
    			<td colspan="2" style="border: none;">${borrowDetailMap.borrowTitle}</td>
    		</tr>
    		<tr>
    			<td style="border: none;">借款总额</td>
    			<td style="border: none;">${borrowDetailMap.borrowAmount}元</td>
    		</tr>
    		<tr>
    			<td style="border: none;">年收益率</td>
    			<td style="border: none;">${borrowDetailMap.annualRate}%</td>
    		</tr>
    		<tr>
    			<td style="border: none;">借款期限</td>
    			<td style="border: none;"><s:property value="#request.borrowDetailMap.deadline" default="---" /><s:if test="%{#request.borrowDetailMap.isDayThe ==1}">个月</s:if>
                                            <s:else>天</s:else><br/><em class="f30 orange">
                                            </em><em class="f14"></em></td>
    		</tr>
    		<tr>
    			<td style="border: none;">还款方式</td>
    			<td style="border: none;"><s:if test="%{#request.borrowDetailMap.isDayThe ==2}">
         到期还本付息
        </s:if> <s:elseif test="%{#request.borrowDetailMap.paymentMode == 1}">按月分期还款(等额本息还款)</s:elseif>
                                                <s:elseif
                                                    test="%{#request.borrowDetailMap.paymentMode == 2}">按月付息,到期还本</s:elseif>
                                                <s:elseif
                                                    test="%{#request.borrowDetailMap.paymentMode == 3}">秒还</s:elseif>
                                                <s:elseif
                                                    test="%{#request.borrowDetailMap.paymentMode == 4}">一次性还款</s:elseif></td>
    		</tr>
    		<tr>
    			<td style="border: none;">安全保障</td>
    			<td style="border: none;">100%本息保障</td>
    		</tr>
    		<tr>
    			<td style="border: none;">可投金额</td>
    			<td style="border: none;">${borrowDetailMap.investAmount}元</td>
    		</tr>
    		<tr>
    			<td style="border: none;">借款进度</td>
    			<td style="border: none;">
    				<div class="progress">
						  <div class="progress-bar progress-bar-danger" role="progressbar" aria-valuenow="${borrowDetailMap.schedules}" aria-valuemin="0" aria-valuemax="100" style="width: ${borrowDetailMap.schedules}%;">
						    ${borrowDetailMap.schedules}%
						  </div>
					</div>
    			</td>
    		</tr>
    		<tr>
    			<td style="border: none;">可用余额</td>
    			<td style="border: none;">${usableSum}元</td>
    		</tr>
    		<tr>
    			<td style="border: none;">投资金额</td>
    			<td style="border: none;"><input type="text" placeholder="请输入投资金额" /></td>
    		</tr>
    		<tr>
    			<td style="border: none;">交易密码</td>
    			<td style="border: none;"><input type="text" placeholder="请输入交易密码" /></td>
    		</tr>
    		<tr>
    			<td colspan="2" style="border: none;"><button type="button" class="btn  btn-danger  btn-lg btn-block">立即投标</button></td>
    		</tr>
    	</table>
    </div>
    <jsp:include page="footer.jsp"></jsp:include>
    <s:hidden name="id" value="%{investDetailMap.id}"></s:hidden>
<s:hidden name="annualRate" value="%{investDetailMap.annualRate}"></s:hidden>
<s:hidden name="deadline" value="%{investDetailMap.deadline}"></s:hidden>
<script type="text/javascript" src="script/jbox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript" src="script/jbox/jquery.jBox-zh-CN.js"></script>
<script type="text/javascript">

function rateCalculate(borrowSum){//利息计算
    param["paramMap.borrowSum"] = borrowSum;
    param["paramMap.yearRate"] = ${investDetailMap.annualRate};//利率
    param["paramMap.borrowTime"] =${investDetailMap.deadline};//期限
    param["paramMap.Way"] = ${investDetailMap.paymentMode};//方式
    param["paramMap.bidReward"] = 0;
    param["paramMap.bidRewardMoney"] = 0;
    
    $.ajax({
        type : "post",
        async:true,
        data:param,
        url : "incomeCalculate.mht",
        success : function(data){
            for(var i = 0; i < data.length; i ++){
                $("#yjsy").html(data[i].rateSum) ;
            }
        },
        error:function(){
            $("#yjsy").html("0.00") ;
        }
    });
}


function validateAmount(){
    var  minsum = parseFloat('${minTenderedSum}'.replace(/[^\d\.-]/g, ""));
    var jebs = parseInt('${jebs}');
    var investAmount = parseFloat('${investDetailMap.investNum}'.replace(/[^\d\.-]/g, ""));
    var usableSum = parseFloat('${usableSum}'.replace(/[^\d\.-]/g, ""));
    var amount = $("#amount").val();
      if (amount>=minsum) {
          var scale = parseInt(amount/jebs);
          if(scale!=(amount/jebs) && amount!=investAmount){
              jQuery.jBox.tip("投资金额只能是"+jebs+"的倍数(特殊情况除外\n如：投资金额等于剩余可投金额)","提示");
              return false;
          }else{
              return true;
          }
      }else{
    	  jQuery.jBox.tip("投资金额不能小于最小投资金额)","提示");
    	 return false
      }
      
 }



var flag = true;
$(function(){
	
	 $("#amount").keyup(function(event){
         
		 if(/\D/.test(this.value)){
             this.value=this.value.replace(/[^\d{1,}\.\d{1,}|\d{1,}]/g,'')
             return
          }

            
         var investAmount = '${investDetailMap.investNum}'
         var usableSum = '${userMap.usableSum}';
         var jebs = parseInt('${jebs}');
         
         if(this.value>parseFloat(usableSum.replace(/[^\d\.-]/g, ""))){
             jQuery.jBox.tip("投资金额已超过你的可用余额","提示");
             var scale = parseInt(usableSum.replace(/[^\d\.-]/g, ""))/jebs;
             this.value=scale*jebs;
         }
         
         if(this.value>parseFloat(investAmount.replace(/[^\d\.-]/g, ""))){
             jQuery.jBox.tip("投资金额已超过可投金额","提示");
             var scale = parseInt(investAmount.replace(/[^\d\.-]/g, ""))/jebs;
             //this.value=scale*jebs;
             this.value = parseFloat(investAmount.replace(/[^\d\.-]/g, ""));
         }
         
         
         //处理文本框中的键盘事件
         var myEvent = event || window.event;
         var keyCode = myEvent.keyCode;
         var sy;
         //处理输入框键盘事件
         var borrowAmount = $("#amount").val();
         if(borrowAmount==null || $.trim(borrowAmount)==''){//输入框内容为空的时候
             $("#yjsy").html("0.00")
             return ;
       }else{
           rateCalculate(borrowAmount)
       }
        
     });
	
	
	var borrowSum = ${borrowAmount};
    rateCalculate(borrowSum)
	
     //样式选中
     $("#licai_hover").attr('class','nav_first');
	 $("#licai_hover div").removeClass('none');
	 $('.tabmain').find('li').click(function(){
	    $('.tabmain').find('li').removeClass('on');
	    $(this).addClass('on');
	    $('.lcmain_l').children('div').hide();
        $('.lcmain_l').children('div').eq($(this).index()).show();
	 });
	  $('#invest').click(function(){
	     var id =$("#id").val();
	     param['paramMap.id'] = id;
	     param["paramMap.code"] = $("#code").val();
	     param['paramMap.annualRate'] = $("#annualRate").val();
	     param["paramMap.deadline"] = $("#deadline").val();
	     param['paramMap.amount'] = 1;
	     param['paramMap.dealPWD'] = $('#dealPWD').val();
	     param['paramMap.investPWD'] = $('#investPWD').val();
	     param['paramMap.result'] = $('#result').val();//认购份数
	     param['paramMap.smallestFlowUnit'] = $('#smallestFlowUnit').val();//每份认购金额
	     param['paramMap.subscribes'] = $('#subscribes').val();//认购模式是否开启
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
	 $('#btnsave').click(function(){
		 if(!validateAmount()){
			 return;
		 }
		 
	     var id =$("#id").val();
	     param['paramMap.id'] = id;
	     param["paramMap.code"] = $("#code").val();
	     param['paramMap.annualRate'] = $("#annualRate").val();
	     param["paramMap.deadline"] = $("#deadline").val();
	     param['paramMap.amount'] = $('#amount').val();
	     param['paramMap.dealPWD'] = $('#dealPWD').val();
	     param['paramMap.investPWD'] = $('#investPWD').val();
	     
	     if(param['paramMap.amount']==null || param['paramMap.amount']=='' ){
             jQuery.jBox.tip("请输入投资金额");
             return ;
         }else{
        	 if(param['paramMap.amount']<=0){
        		 jQuery.jBox.tip("请输入投资金额");
                 return ;
        	 }
         }
	     
         if(param['paramMap.dealPWD']==null || param['paramMap.dealPWD']==''){
             jQuery.jBox.tip("请输入交易密码");
             return ;
         }
	     if(flag){
           flag = false;
           //var l = Ladda.create(this);
           //l.start();
           $('#btnsave>span').html('投标中...');
	       $.dispatchPost('financeInvest.mht',param,function(data){
	    	   //l.stop();
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
                 if(data.updatePwd){
                	  alert("为了您的账户安全，交易密码不能与登录密码相同，请立即修改交易密码。");
                      window.location.href = 'securityCent.mht?isOn=2';
                 }else{
                	 jQuery.jBox.tip(callBack);
                     flag = true;
                 }
		      }
		      $('#btnsave>span').html('确定');
		    });
		 }
	 });
	
});

function tz(){    
	window.location.href='updatePasswordAndDealpwd.mht';
	}
    </script>
  </body>
</html>
