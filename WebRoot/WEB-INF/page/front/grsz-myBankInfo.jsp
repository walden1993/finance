<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<jsp:include page="/include/head.jsp"></jsp:include>
</head>
<body>
<!-- 引用头部公共部分 -->
<jsp:include page="/include/top.jsp"></jsp:include>
<div class="nymain">
  <div class="wdzh w950">
  <ol class="breadcrumb mb0 bg-none">
	  <li><a href="/">首页 </a></li>
	  <li><a href="home.mht">我的账户</a></li>
	  <li class="active">提现银行卡</li>
  </ol>
      <!--  div class="box"> -->
         <!-- 引用我的帐号主页左边栏 -->
         <%@include file="/include/left.jsp" %>
      <!-- /div -->
     <div class="fr uesr_border w750 mb20 bg-white" >
           <div class="pub_tb bottom clearfix">
		  <h3 style="border:none; "><a href="rechargeInit.mht" style="color:#a6a6a6;">充值</a></h3>
		  <h3 style="border:none;"><a href="withdrawLoad.mht" style="color:#a6a6a6;">提现</a></h3>
		  <h3>我的银行卡</h3>
		  <h3 style="border:none;"><a href="queryFundrecordInit.mht" style="color:#a6a6a6;">资金记录</a></span></h3>
		  <div class="clearfix"></div>
      </div>
           <div class="mine_bank ml20"><b class="mr20">我的提现银行卡</b><span>已经绑定<font class="red">(${count})</font>张银行卡</span> </div>
           <ul class="yhgl_li tc  ml20 mb30 clearfix">
				<s:if test="#request.lists!=null && #request.lists.size>0">
					<s:iterator value="#request.lists" id="queryBankInfoInit"
						var="bean">

						<li class="clearfix">
							<p>
								${bean.bankName}-${bean.branchBankName}
							</p>
							<p style="letter-spacing: 2px;font-weight: 600;font-size: 14px;">
								<shove:sub value="#bean.cardNo" size="4" suffix=" **** **** "/>
								${fn:substring(bean.cardNo,fn:length(bean.cardNo)-4,fn:length(bean.cardNo))}
							</p>
							<p class="clearfix noborder">
					         <s:if test="#bean.cardStatus==1">
						      <span class="fl">状态：已绑定</span><a href="javascript:deleteMyBankInfo(${bean.id})" class="red fr">删除</a>
					          </s:if>
					         <s:else>
					         <s:if test="#bean.cardStatus==3">
							      <span class="fl">状态：审核失败</span><img src="images/quest_icon.jpg" width="18" height="18" title="失败原因：${bean.remark}"  alt="失败原因：${bean.remark}"  >&nbsp;&nbsp;<a href="javascript:del(${bean.id})" class="red fr">删除此记录</a>
					         </s:if>
					         <s:else>
							      <span class="fl">状态：审核中</span><a href="javascript:delquxiao(${bean.id})" class="red fr">取消</a>
						      </s:else>
						      </s:else>
							</p>
						</li>
					</s:iterator>
				</s:if>

				<s:else>
				</s:else>

				<li class="text-center">
				<a href="addtxBankInfo.mht" id="addtxBankInfo1" class="ico  fancybox.ajax" ></a>
				<a href="addtxBankInfo.mht" id="addtxBankInfo2"  class="blue fancybox.ajax" >添加提现银行</a><br/><span class="f14 mt5">最多还支持<i class="red">${9-count}</i>张提现银行卡</span></li>
           </ul>                         
         </div>
  </div>
</div>

 <!-- 引用底部公共部分 -->     
<jsp:include page="/include/footer.jsp"></jsp:include>

<script>
$(function(){

	//displayDetail(2,4);

	$("#bankCardNew").addClass('on');

	  var tt = '${bean.cardNo}'==''?'':'${bean.cardNo}';
	  var len = tt.length;
	  var tmplen1;
	  var tmplen2;
	  if(len!=0){
		  tmplen1 = tt.substr(0,4);
		  tmplen2 = tt.substr(len-4,len);
		  $('#cardNo').html(tmplen1+' **** '+tmplen2); 
	  }
	var options = {
		fitToView	: false,
		maxWidth:750,
		width		: '75%',
		height		: '90%',
		autoSize	: false,
		closeClick	: false,
		openEffect	: 'none',
		closeEffect	: 'none'
	};
	$("#addtxBankInfo1").fancybox(options);
	$("#addtxBankInfo2").fancybox(options);
});

function addtxBankInfo(){
	$.jBox("iframe:addtxBankInfo.mht", {
        title: "设置提现银行卡",
        width:750,
        height: 520,
        buttons: {}
    });
}

//删除该条已经添加的银行卡信息
function deleteMyBankInfo(id){
	if(!window.confirm("确定要删除吗?")){
		return;
	}
	$.dispatchPost("deleteMyBankInfo.mht",{bankId:id},function(data){
		window.location.href="mybank.mht";
	});
}


//删除添加的审核失败的银行卡信息
function del(id){
	if(!window.confirm("确定要删除吗?")){
		return;
	}
	$.dispatchPost("deleteBankInfo.mht",{bankId:id},function(data){
		window.location.href="mybank.mht";
	});
}

//取消添加的正在审核银行卡信息
function delquxiao(id){
	if(!window.confirm("确定要取消申请吗?")){
		return;
	}
	$.dispatchPost("deleteBankInfo.mht",{bankId:id},function(data){
		window.location.href="mybank.mht";
	});
}
</script>
</body>
</html>