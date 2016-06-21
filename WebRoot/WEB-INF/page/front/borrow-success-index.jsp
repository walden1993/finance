<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>三好资本</title>
<jsp:include page="/include/head.jsp"></jsp:include>
</head>

<body>
<!-- 引用头部公共部分 -->
<jsp:include page="/include/top.jsp"></jsp:include>	
<div class="nymain wrap">
  <div class="wdzh w950">
  <ol class="breadcrumb mb0 bg-none">
		  <li><a href="/">首页 </a></li>
		  <li><a href="home.mht">我的账户</a></li>
		  <li class="active">还款</li>
	  </ol>
      <!--  div class="box"> -->
         <!-- 引用我的帐号主页左边栏 -->
         <%@include file="/include/left.jsp" %>
      <!-- /div -->
    <div class="r_main fr w750 bg-white">
      <div class="tabtil">
        <ul class="m0"><li class="on" onclick="jumpUrl('queryMySuccessBorrowList.mht');">成功借款</li>
        <li onclick="jumpUrl('queryMyPayingBorrowList.mht');">正在还款的借款</li>
        <li onclick="jumpUrl('queryAllDetails.mht');">还款明细账</li>
        <li onclick="jumpUrl('queryBorrowInvestorInfo.mht');">借款明细账</li>
        <li onclick="jumpUrl('queryPayoffList.mht');">已还完的借款</li>
        </ul>
        </div>
      <div class="box">
        <div class="boxmain2">
        	
           <div class="srh">
           <form action="queryMySuccessBorrowList.mht" id="searchForm" method="post" >
        发布时间：<input type="text" id="startTime" name="startTime" value="${paramMap.startTime }" class="inp90 Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/> 
        到 
        <input type="text" id="endTime" value="${paramMap.endTime }" name="endTime" class="inp90 Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/> 
        <span style="margin-left:20px;">关键字：</span><input type="text" class="inp90" name="title" value="${paramMap.title }" id="title_s" />
        <a href="javascript:void(0)" class="scbtn" id="btn_search"> 搜索</a> 
        <a href="javascript:void(0)" class="scbtn" onclick="excelout()">导出excel表</a></div>
        <input type="hidden" name="curPage" id="pageNum" />
        </form>
         <div class="biaoge">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">

   <tr>
    <th>标题</th>
    <th>协议</th>
    <th>借款类型</th>
    <th>借款金额</th>
    <th>年利率</th>
    <th>还款期限</th>
    <th>借款时间</th>
    <th>偿还本息</th>
    <th>已还本息</th>
    <th>未还本息</th>
    <th>状态</th>
    </tr>
    
  <s:if test="pageBean.page==null || pageBean.page.size<=0">
    <tr><td align="center" colspan="11">暂无记录</td></tr>
  </s:if>
  
  <s:else>
    <s:iterator value="pageBean.page"  var="bean"> 
       <tr>
	    <td align="center"><a href="financeDetail.mht?id=${bean.borrowId}"  target="_blank" >${bean.borrowTitle }</a></td>
	    <td align="center"><%--<a href="javascript:showAgree('${bean.borrowId}');">查看协议</a>
	      --%><a href="protocol.mht?typeId=15&&borrowId=${bean.borrowId}" target="_blank">查看协议</a>
	    </td>
	    <td align="center">${bean.borrowWay }</td>
	    <td align="center">${bean.borrowAmount }元</td>
	    <td align="center">${bean.annualRate }%</td>
	    <td align="center">${bean.deadline }
	    <s:if test="#bean.isDayThe == 1">
	      个月
	    </s:if>
	    <s:else>
	     天
	    </s:else>
	    </td>
	    <td align="center">${bean.publishTime}</td>
	    <td align="center">￥${bean.stillTotalSum }</td>
	    <td align="center">￥${bean.hasPI }</td>
	    <td align="center">￥${bean.hasSum }</td>
	    <td align="center">${bean.borrowStatus }</td>
	  </tr>
    </s:iterator>
  </s:else>
          </table>
          
  <div  class="page" >
	     <shove:page url="queryMySuccessBorrowList.mht" curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="number" totalCount="${pageBean.totalNum}">
	    	<s:param name="startTime">${paramMap.startTime}</s:param>
	    	<s:param name="endTime">${paramMap.endTime}</s:param>
	    	<s:param name="title">${paramMap.title}</s:param>
	     </shove:page>
  </div>

          </div>
    </div>
</div>
    </div>
  </div>
</div>
<!-- 引用底部公共部分 -->     
<jsp:include page="/include/footer.jsp"></jsp:include>
<script language="javascript" type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="script/jbox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript" src="script/jbox/jquery.jBox-zh-CN.js"></script>
<script>
$(function(){
	//$('#li_7').addClass('on');
	// $("#zh_hover").attr('class','nav_first');
	// $("#zh_hover div").removeClass('none');
		//displayDetail('2');
		$("#borrowMgrNew").addClass('on');
	
		 $("#btn_search").click(function(){
			//isSpace = /^[^ ]+$/;
			if($("#title_s").val()  &&  !/^[\w\u4e00-\u9fa5]+$/gi.test($("#title_s").val())){
				alert("请正确输入关键字");
		      	return;
			}
		 	if($("#startTime").val() >$("#endTime").val()){
		      	alert("开始日期不能大于结束日期");
		      	return;
		  	}
			$("#pageNum").val(1);
		 	$("#searchForm").submit();
		 });
	 
	  	$("#jumpPage").click(function(){
		  	if($("#startTime").val() >$("#endTime").val()){
		      	alert("开始日期不能大于结束日期");
		      	return;
		  	}
		 	var curPage = $("#curPageText").val();
		 	if(isNaN(curPage)){
				alert("输入格式不正确!");
				return;
			}
		 	$("#pageNum").val(curPage);
		 	$("#searchForm").submit();
		 });
	 });
	
	function showAgree(borrowId){
	     var url = "protocol.mht?typeId=15&&borrowId="+borrowId;
	     jQuery.jBox.open("post:"+url, "查看协议书", 650,400,{ buttons: { } });
	     
    }
    
    function jumpUrl(obj){
          window.location.href=obj;
       }
           
     function excelout()
     {
    	window.location.href=encodeURI(encodeURI("exportSuccessBorrow.mht?status=-1"+"&&startTime="+$("#startTime").val()+"&&endTime="+$("#endTime").val()+"&&title="+$("#title_s").val())); 
     }

</script>

</body>
</html>
