<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<title>银行卡设置</title>
<link rel="stylesheet" type="text/css" href="css/common.css" />
<link rel="stylesheet" href="css/user.css" type="text/css" media="screen" />
<link id="skin" rel="stylesheet" href="css/jbox/Gray/jbox.css" />
    <jsp:include page="html5meta.jsp"></jsp:include>
	<link rel="stylesheet" type="text/css"
	href="script/bootstrap/css/bootstrap.min.css">
</head>
<body>
<!-- 引用头部公共部分 -->
<jsp:include page="header.jsp"></jsp:include>
<div class="nymain">
  <div class="wdzh">
     <div class="fr" >
           <div class="pub_tb bottom mt20"><i></i>我的银行卡</div>
           <div class="mine_bank"><b class="mr40">我的提现银行</b>已经绑定<span class="red">（${count}）</span> </div>
           <ul class="yhgl_li tc  clearfix">
				<s:if test="#request.lists!=null && #request.lists.size>0">
					<s:iterator value="#request.lists" id="queryBankInfoInit"
						var="bean">

						<li style="margin-right: 3px;width: 180px;">
							<p style="margin: 0px;border: none">
								${bean.bankName}-${bean.branchBankName}
							</p>
							<p style="margin: 0px;border: none">
								<shove:sub value="#bean.cardNo" size="4" suffix="****"/>
								${fn:substring(bean.cardNo,fn:length(bean.cardNo)-4,fn:length(bean.cardNo))}
							</p>
							<p style="margin: 0px;border: none">
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

				<li>
				<a href="addtxBankInfo.mht" class="ico" onclick="addtxBankInfo();"></a>
				<a href="addtxBankInfo.mht" class="blue" >添加提现银行</a><br/><span class="f14 mt5">最多还支持<i class="red">${9-count}</i>张提现银行卡</span></li>
           </ul>                         
         </div>
  </div>
</div>

 <!-- 引用底部公共部分 -->     
<script type="text/javascript" src="script/jbox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript" src="script/jbox/jquery.jBox-zh-CN.js"></script>
<script>
$(function(){
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