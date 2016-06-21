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
		  <li class="active">借款明细账</li>
	  </ol>
      <!--  div class="box"> -->
         <!-- 引用我的帐号主页左边栏 -->
         <%@include file="/include/left.jsp" %>
      <!-- /div -->
    <div class="r_main w750 fr bg-white">
      <div class="tabtil">
         <ul class="m0"><li  onclick="jumpUrl('queryMySuccessBorrowList.mht');">成功借款</li>
        <li  onclick="jumpUrl('queryMyPayingBorrowList.mht');">正在还款的借款</li>
        <li  onclick="jumpUrl('queryAllDetails.mht');">还款明细账</li>
        <li class="on" onclick="jumpUrl('queryBorrowInvestorInfo.mht');">借款明细账</li>
        <li onclick="jumpUrl('queryPayoffList.mht');">已还完的借款</li>
        </ul>
        </div>
   
<div class="box" >
        <div class="boxmain2">
         <div class="srh">
          <form action="queryBorrowInvestorInfo.mht" id="searchForm" >
        投资者：
          <input type="text" style="border:1px solid #ddd;" class="inp140" id="investor" name="investor" value="${paramMap.investor}"/>
          <a href="javascript:void(0)" class="scbtn" id="btn_search"> 搜索</a> 
          <input type="hidden" name="curPage" id="pageNum" />
          </form>
          </div>
         <div class="biaoge">
          <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <th>投资者</th>
    <th>借入总额</th>
    <th>还款总额</th>
    <th>已还本金</th>
    <th>已还利息</th>
    <th>待还本金</th>
    <th>待还利息</th>
    </tr>
    
    <s:if test="pageBean.page==null || pageBean.page.size==0">
	   <tr align="center">
		  <td colspan="7">暂无数据</td>
	   </tr>
	</s:if>
	<s:else>
	<s:iterator value="pageBean.page" var="bean">
	   <tr>
	    <td align="center">${bean.username} </td>
	    <td align="center"><strong>￥${bean.realAmount }</strong></td>
	    <td align="center">￥${bean.recivedPI}</td>
	    <td align="center">￥${bean.hasPrincipal}</td>
	    <td align="center">￥${bean.hasInterest}</td>
	    <td align="center">￥${bean.forPrincipal}</td>
	    <td align="center">￥${bean.forInterest}</td>
	  </tr>
	</s:iterator>
	</s:else>
          </table>
	 <div  class="page" >
		     <shove:page url="queryBorrowInvestorInfo.mht" curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="number" totalCount="${pageBean.totalNum}">
		    	<s:param name="investor">${paramMap.investor}</s:param>
		    	
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
<script type="text/javascript" src="script/jbox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript" src="script/jbox/jquery.jBox-zh-CN.js"></script>
<script>
$(function(){
    //$("#zh_hover").attr('class','nav_first');
	//$("#zh_hover div").removeClass('none');
	//$('#li_7').addClass('on');
	//displayDetail('2');
	$('#borrowMgrNew').addClass('on');
	
	$("#btn_search").click(function(){
		 $("#pageNum").val(1);
		 $("#searchForm").submit();
	});
	 
	 $("#jumpPage").click(function(){
		var curPage = $("#curPageText").val();
		if(isNaN(curPage)){
			alert("输入格式不正确!");
			return;
			}
	 	$("#pageNum").val(curPage);
	 	$("#searchForm").submit();
	});

});
	
	
	

	function showAgree(){
	     var url = "protocol.mht?typeId=1";
	     jQuery.jBox.open("post:"+url, "查看协议书", 600,400,{ buttons: {} });
	     
    }
    
    function jumpUrl(obj){
          window.location.href=obj;
       }
</script>

</body>
</html>
