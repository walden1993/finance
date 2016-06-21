<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title><%=IConstants.SEO_TITLE%></title>
         <link id="skin" rel="stylesheet" href="css/jbox/Skins2/Blue/jbox.css" />
        <link rel="stylesheet" type="text/css" href="css/common.css" />
        <link rel="stylesheet" type="text/css" href="css/index.css" />
        <link rel="stylesheet" type="text/css" href="css/new_inside.css" />
        <link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" />
        <link rel="stylesheet" href="css/lightbox.css" type="text/css" media="screen" />
        <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta name="keywords" content="<%=IConstants.SEO_KEYWORDS%>">
    <meta name="description" content="<%=IConstants.SEO_DESCRIPTION %>">
    <%=IConstants.SEO_OTHERTAGS %>
</head>
<body>
<!-- 引用头部公共部分 -->
<jsp:include page="/include/top.jsp"></jsp:include>



<!--内容区域-->
<div class="ny_content clearfix">
  <div class="w1002 location"><a href="<%=application.getAttribute("basePath")%>">首页</a> > <a href="bidlist.mht">我要投资</a> > <a href="financeDetail.mht?id=${investDetailMap.id}">${investDetailMap.borrowTitle}</a> > 确认投标</div>
  <div class="bd_detail w1002 clearfix" style="margin:10px auto 20px auto;position: relative;">
  <s:if test="%{#request.investDetailMap.deadline>=3 && #request.investDetailMap.id>=167}"><a href="activityIndex_awardmon.mht" target="_blank"><img src="images/activity_ioc.png" alt=""  style="position: absolute; left: 0px; top: 0px;"/></a></s:if>
     <div class="info fl">
         <ul class="sub_bid fl clearfix"> 
            <li><h1>借款标题：${investDetailMap.borrowTitle}<s:if test="%{#request.investDetailMap.deadline>=3 && #request.investDetailMap.id>=167}"><img style="margin-left: 20px;margin-bottom: 5px;" src="images/award_ioc.png" /><font color="orange" style="font-size: 14px;margin-right: 5px;font-weight: bold;">+0.${investDetailMap.deadline}%</font><a href="activityIndex_awardmon.mht" style="color: red;float: right;"><!-- <img src="images/aictivity_detail.gif"/> --></a></s:if></h1></li>
            <li class="clearfix">
               <div class="bid_list01">借款人用户名：${fn:substring(investDetailMap.username, 0, 2)}***</div>
               <div class="bid_list02">借款年利率：<span class="orange f16">${investDetailMap.annualRate}%</span></div>
           </li>
           <li class="clearfix">
               <div class="bid_list01">借款金额：<span class="orange f16">￥${investDetailMap.borrowAmount}</span></div>
               <div class="bid_list02">还需借款：<span class="orange f16">￥${investDetailMap.investNum}</span></div>
           </li>
            <li class="clearfix">
               <div class="bid_list01">借款期限：<span class="orange f16">${investDetailMap.deadline}<s:if test="%{investDetailMap.isDayThe ==1}">个月</s:if><s:else>天</s:else></span></div>
               <div class="bid_list02">还款方式 : <span class="orange f16">
<s:if test="%{investDetailMap.paymentMode == 1}">
按月分期还款
</s:if>
<s:elseif test="%{investDetailMap.paymentMode ==2}">
按月付息,到期还本
</s:elseif>
<s:elseif test="%{investDetailMap.paymentMode ==3}">
秒还
</s:elseif>
<s:elseif test="%{investDetailMap.paymentMode ==4}">
  一次性还款
</s:elseif>
               </span></div>
            </li>
            <li class="clearfix"><span class="red">温馨提示：</span>投标后您的投资资金将被冻结，满标后划拨给借款人并计息，若未满标则投资资金解冻，未满标期间不会产生收益。</li>

       </ul>
     </div>
     <div class="sub_tender fl">
       <div class="ny_news_money clearfix">
           <p class="fl">账户可投金额：<br/>
           <span class="orange f20"> ￥${userMap2.usableSum}</span></p>
           <p class="fr"> <input name=""  onclick="window.location.href='rechargeInit.mht'"  value="充值" type="button" class="n_blue_btn"    /></p>
       </div>
       <div class="mb20">投资金额：<input  id="amount"  style="ime-mode:disabled;"  ondragenter="return false" onpaste="return!clipboardData.getData('text').match(/[\u4e00-\u9fa5]/gi)" onKeyPress="if(event.keyCode<48||event.keyCode>57||event.keyCode==8)event.returnValue=false;"  onblur="validateAmount()"       name="paramMap.amount"   placeholder="起投金额：${minTenderedSum}"  <s:if test="#request.borrowAmount>0">value="${borrowAmount }" </s:if> maxlength="20"  type="text" class="inptext01 w180"/>元<br/><span class="red pl">*投资金额只能是${jebs}的整数倍</span></div>
       <div class="mb20">交易密码：<input type="password" id="dealPWD" name="paramMap.dealPWD"   maxlength="20"  class="inptext01 w90"/><a href="javascript:searchDealPwd()" class="ml15 blue"   >忘记密码？</a></div>
       <s:if test="#session.hasPWD != -1">
    <div class="mb20">投标密码：<input type="password"  id="investPWD" name="paramMap.investPWD"  maxlength="15"  class="inptext01 w90"/>元</div>
       </s:if>
       
       <div class="clearfix"><button     id="btnsave" class="btn btn-primary btn-xs ladda-button btn05" data-style="expand-right"   type="button"   ><span class="ladda-label">确认</span></button><input value="取消" type="button" class="btn06"   onclick="window.history.go(-1)" /></div>
       <div class="mt28"><i class="ui_item_detail_icon ui_item_pic_count"></i>您的预期收益：<span class="orange">￥<span id="yjsy">0.00</span>元</span> </div>
  </div>
</div>
</div>

	
<div class="nymain"   style="display: none">
<div class="ifbox1" >
  <h2>确定投标 <img src="images/neiye2_06.jpg" width="4" height="7" /></h2>
  <div class="boxmain" style="padding-top:20px; padding-bottom:20px;">
    <div class="qrtb">
      <div class="tbtop"></div>
      <div class="tbmain">
        <div class="tbinfo">
          <div class="lbox">
            <div class="pic">
              <a href="userMeg.mht?id=${investDetailMap.userId}" target="_blank">
              <shove:img src="${investDetailMap.personalHead}" defaulImg="images/default-img.jpg" width="80" height="79"></shove:img>
              </a>
              <br/>
              三好资本信用等级：<img src="images/ico_${investDetailMap.credit}.jpg" title="${investDetailMap.creditrating}分" width="33" height="22" /></div>
              <p class="">
              用户名：${investDetailMap.username}<br/>
              企业名称：${investDetailMap.orgName}<br/>
              企业所在地：${investDetailMap.orgAddress}
              
              <!-- 
籍  贯：${investDetailMap.nativePro}&nbsp;${investDetailMap.nativeCity}<br/>
居住地：${investDetailMap.address}
 -->
              </p>
          </div>
          <div class="rbox">
          <ul><li>借款标题：<strong>${investDetailMap.borrowTitle}</strong></li><li>借款金额：<strong>￥${investDetailMap.borrowAmount}</strong></li>
          <li>借款年利率：<strong>${investDetailMap.annualRate}%</strong></li>
<li>已经完成： <strong>${investDetailMap.schedules}%</strong></li>
<li>还需借款: <strong>￥${investDetailMap.investNum}</strong></li>
<li>借款期限: <strong>${investDetailMap.deadline}
<s:if test="%{investDetailMap.isDayThe ==1}">个月</s:if><s:else>天</s:else>
</strong></li>
<li>还款方式: <strong>
<s:if test="%{investDetailMap.paymentMode == 1}">
按月分期还款
</s:if>
<s:elseif test="%{investDetailMap.paymentMode ==2}">
按月付息,到期还本
</s:elseif>
<s:elseif test="%{investDetailMap.paymentMode ==3}">
秒还
</s:elseif>
<s:elseif test="%{investDetailMap.paymentMode ==4}">
  一次性还款
</s:elseif>
</strong></li>
<li>交易类型: <strong>
<s:if test="%{investDetailMap.tradeType == 1}">
线上交易
</s:if>
<s:elseif test="%{investDetailMap.tradeType ==2}">
线下交易
</s:elseif>
</strong></li>
</ul>
</div>
</div>
 <div class="tbcz">
  <p class="money">
          您的帐户总额：<strong>${userMap.totalSum} 元 </strong> <a href="rechargeInit.mht" class="czbtn"><img src="images/tubiao3.png" /></a><br/>
          您的可用余额：<strong>${userMap.usableSum} 元</strong> </p>
          <p class="tips">请填写并确认下面投标金额 <input type="button" value="设置交易密码" onclick="tz()" /><br/>
          <s:if test="#request.subscribes!=1">
	<span class="f999">最低投标金额：<s:property value="#request.minTenderedSum" default="0"/>元&nbsp;最多投标金额：<s:if test="%{investDetailMap.maxTenderedSum != -1}">${investDetailMap.maxTenderedSum}</s:if><s:else>无限制</s:else>
 	 <br/>当前年利率: ${investDetailMap.annualRate}%</span>
 	 </s:if>
 	 <s:else>
 	 	<span class="f999">最小认购金额：<s:property value="#request.investMaps.smallestFlowUnit" default="0"/>元&nbsp;
 	 	当前年利率: ${investDetailMap.annualRate}%
 	 <br/>认购总份数：${investMaps.circulationNumber }份,还有：${investMaps.circulationNumber - investMaps.hasCirculationNumber }份</span>
 	 </s:else>
 	 </p>
		  <%--<div id="content" class="tbtab"> --%>
		  <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <s:if test="#request.subscribes!=1">
  <tr>
    <%--<td>投标金额：<input type="text" id="amount" name="paramMap.amount" class="inp140" maxlength="20" /> 元</td> --%>
  </tr>
  <tr>
	<%--<td>交易密码：<input type="password" id="dealPWD" name="paramMap.dealPWD" class="inp140" maxlength="20" /></td> --%>
  </tr>
  </s:if>
  <s:if test="#session.hasPWD != -1">
    <tr>
    <%--<td>投标密码：<input type="password" id="investPWD" name="paramMap.investPWD" class="inp140" maxlength="20" /></td>--%>
  </tr>
  </s:if>
  <s:if test="#request.subscribes!=1">
  <tr>
    <td colspan="2" class="tishi">注意：点击按钮表示您将确认投标金额并同意支付贷款</td>
    </tr>
  <tr>
    <%--<td colspan="2" align="center" style="padding-top:15px; padding-bottom:15px;"><input id="btnsave" type="button" class="qdtbbtn" value="" /></td> --%>
    </tr>
    </s:if>
</table>
<s:if test="#request.subscribes==1">
       <div style="background-color: #FDFAE9; height: 75px; width: 320px; margin-top:15px;border: solid 1px #F9DDD1;">
          <table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tbody><tr>
    <td width="51%" height="48" align="right"><div>
    <input type="text" class="result_btn_detail_3" value="1" id="result" style="width: 100px;"/>
份&nbsp;&nbsp; </div></td>
    <td width="49%"><div style="width:98px;">	
<input type="image" src="images/gogo_rg.gif" value="" id="invest" class="bttn_2"/>	
	</div></td>
  </tr>
  <tr>  
    <td colspan="2" align="center">您的可投标金额为：<font id="account_use" style="color:#FF0000">￥${investDetailMap.investNum}</font>元，最多可认购：<font style="color:#FF0000" id="roam_use">${investMaps.circulationNumber - investMaps.hasCirculationNumber}份</font>
    <input name="subscribes" type="hidden"  id="subscribes"  value="${subscribes}"/>	
     <input name="smallestFlowUnit" type="hidden"  id="smallestFlowUnit"  value="${investMaps.smallestFlowUnit}"/>
    </td>
    </tr>
</tbody></table>          
</div>
</s:if>
          </div>
        </div>
      </div>
      <div class="tbbot"></div>
    </div>
  </div>
  </div>
</div>
<!-- 引用底部公共部分 -->     
<jsp:include page="/include/footer.jsp"></jsp:include>
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
         var usableSum = '${userMap2.usableSum}';
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
	     
	     if(param['paramMap.amount']==null || param['paramMap.amount']==''){
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