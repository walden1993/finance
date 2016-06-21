<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<c:set value="涨薪宝转出" scope="request" var="title"></c:set>
<jsp:include page="/include/head.jsp"></jsp:include>
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
	  <li class="active">员工列表</li>
 	 </ol>
      <!--  div class="box"> -->
         <!-- 引用我的帐号主页左边栏 -->
     <%@include file="/include/left.jsp" %>
      <!-- /div -->
    
	<div class="r_main fr w750 mb">
		<div class="pub_tb bottom">
        <h3>员工列表</h3>
        <h3 style="border:none; "><a href="wageExoportEmployeeInit.mht" style="color:#a6a6a6;">薪水管理</a></h3>
        <h3 style="border:none; "><a href="exportCompanyEmployeeInit.mht" style="color:#a6a6a6;">批量员工管理</a></h3>
        </div>
        
        <div class="uesr_border personal clearfix">
             
             <div class="uesr_serch mt10 mb10">
                <span >用户名 / 真实姓名：</span>
               <input type="text" id="name"  name="publishTimeStart"  class="input_border w140"/> 
	        	&nbsp;&nbsp;<span >手机号：</span>
	        	<input type="text" id="mobile"  name="publishTimeEnd"  class="input_border w140"/>
               <button onclick="initListInfo();" class="button w100 ml20 h34" id="search" >查询</button>
               <div class="mt15">当前在职员工总人数<span class="red f20">${cou}</span>人</div>
            </div>
        </div>
        
        <div id="datainfo"></div>
        
        </div>
	
  </div>
</div>
	
	<!-- 引用底部公共部分 -->
	<jsp:include page="/include/footer.jsp"></jsp:include>
	<script type="text/javascript">
		displayDetail(7,10);
		$(function(){
		    initListInfo();
	 	});
	 
		 function initListInfo(){
	        param["pageBean.pageNum"] = 1;
	        param["paramMap.name"] = $("#name").val();
	        param["paramMap.mobile"] = $("#mobile").val();
			$.dispatchPost("companyEmployeeList.mht",param,initCallBack);
		}
		function initCallBack(data){
			$("#datainfo").html(data);
		}
	</script>
</body>
</html>

