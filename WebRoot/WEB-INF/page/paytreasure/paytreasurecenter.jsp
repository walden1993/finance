<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<c:set value="我的涨薪宝" scope="request" var="title"></c:set>
<jsp:include page="/include/head.jsp"></jsp:include>
<link rel="stylesheet" href="css/usercenter.css" type="text/css"  media="screen"/>
<style type="text/css">
</style>
<script type="text/javascript">
	var menuIndex = 4;
</script>
</head>
<body>
	<!-- 引用头部公共部分 -->
	<jsp:include page="/include/top.jsp"></jsp:include>
	<div class="wrap  mb20">
  <div class="w950 wdzh">
  	<ol class="breadcrumb mb0 bg-none">
	  <li><a href="/">首页 </a></li>
	  <li class="active">我的涨薪宝</li>
 	 </ol>
      <!--  div class="box"> -->
         <!-- 引用我的帐号主页左边栏 -->
         <%@include file="/include/left.jsp" %>
      <!-- /div -->
	  <div class="r_main fr w750 mb">
         	<div class="center_right uesr_border personal">
             	<div class="tj f14">
				  <div class="fl w050 bdr "><i></i>还可转入金额：<em title="三好资本可用余额" class="helplist"></em>：<span>${usableSum.usableSum}</span>元</div>
				  <div class="fr w050 "><i></i>累计收益<em title="累计收益：涨薪宝累计已到帐收益总额" class="helplist"></em>：<span><s:property default="0.00" value="#request.mypaytreasure.allprofit" /></span>元</div>
			 	 </div>
			  	<div class="text-center f30 pb20">
							<div class="pt30 bold f25"  >涨薪宝总额</div>
							<div class="bold f25" ><s:property default="0.00" value="#request.mypaytreasure.investamount" />元</div>
							<div class="f15" style="line-height: 40px;height: 40px;"><a href="paytreasuredetail.mht" class="mr20  button w100">转入</a><a href="outPayTreasureInit.mht"  class="ml20 button w100">转出</a></div>
				</div>
			  	 <ul class="nav nav-tabs">
			         <li role="presentation" class="active"><a href="#">收益记录</a></li>
			         <li role="presentation" class="" ><a href="#">转入转出记录</a></li>
			         <li role="presentation" class="" ><a href="#">涨薪宝设置</a></li>
			         <li  class="" style="float: right;">日期范围：<input type="text" id="publishTimeStart" name="publishTimeStart" value="${paramMap.publishTimeStart}" class="input_border w100" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly',maxDate:'#F{$dp.$D(\'publishTimeEnd\')}'})" readonly="">&nbsp;-&nbsp;<input type="text" id="publishTimeEnd" name="publishTimeEnd" value="${paramMap.publishTimeEnd}" class="input_border w100" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly',maxDate:'%y-%M-%d'})" readonly=""><button onclick="query();" class="button w100 ml20 h34" id="search">查询</button></li>
		        </ul>
		        <div id="sysInfo">
			        	
			        </div>
         	</div>
      </div>
  </div>
</div>
	<!-- 引用底部公共部分 -->
	<jsp:include page="/include/footer.jsp"></jsp:include>
	<script language="javascript" type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript">
		
		$(function(){
		 	//displayDetail(6,9);
			  	$('.nav').find('li:lt(3)').click(function(){
				    $('.nav').find('li').removeClass('active');
				    $(this).addClass('active');
			        if($(this).index()==0){
			        	initListInfo1("profitRecord.mht");
			        }else if($(this).index()==1){
			        	initListInfo1("payInvestRecord.mht");
			        }else{
			      	  	initListInfo1("payTreasureSetting.mht");
		       		 }
		       		 $("#publishTimeEnd").val('');
			 	     $("#publishTimeStart").val('')
		   	  });
		   	  initListInfo1("profitRecord.mht")
			});
			
			 function initListInfo1(url){
			 	param["pageBean.pageNum"] = 1;
			 	param["paramMap.publishTimeEnd"] = $("#publishTimeEnd").val();
			 	param["paramMap.publishTimeStart"] = $("#publishTimeStart").val();
			 	$.dispatchPost(url,param,function(data){
		          		$("#sysInfo").html(data);
		       	}); 
			 }
			 
			 function query(){
			 	var index = $('.nav').find('.active').index();
			 	if(index==0){
		        	initListInfo1("profitRecord.mht");
		        }else if(index==1){
		        	initListInfo1("payInvestRecord.mht");
		        }
			 }
			 
			 function saveSetting(){
				 $.dispatchPost("payTreasureSettingSave.mht",param,function(data){
		          		$("#sysInfo").html(data);
		          		initListInfo1("payTreasureSetting.mht");
		       	});
			 }
		
	</script>
</body>
</html>

