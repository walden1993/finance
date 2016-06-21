<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<c:set value="三好资本公告" var="title"  scope="request" />
<jsp:include page="/include/head.jsp"></jsp:include>
<script type="text/javascript">var menuIndex = 5;</script>
</head>

<body>
<!-- 引用头部公共部分 -->
<jsp:include page="/include/top.jsp"></jsp:include>

<!--内容区域-->
<div class="wrap">
<!--介绍-->

<div class="w950 clearfix">
	  <ol class="breadcrumb mb0 bg-none">
		  <li><a href="/">首页 </a></li>
		  <li>关于我们</li>
		  <li class="active">三好资本公告</li>
	  </ol>
  	<jsp:include page="/WEB-INF/page/helpcenter/aboutme_left.jsp"></jsp:include>
  <div class="w750 clearfix fr bg-white mb20"  id="showcontent"></div>
</div>

</div>
<!--footer-->
<jsp:include page="/include/footer.jsp"></jsp:include>
</body>
<script>
   $(function(){
	   param["pageBean.pageNum"] = 1; 
	   queryGfgg(param);
	   //queryMtbd1(param);
	   //queryMtbd2(param);
	   $(".html_left a:eq(6)").addClass("hover");
   })
   
   function doqueryGfggJumpPage(i){
    //if(i==""){
    //  alert("输入格式不正确12!");
    //  return;
    //}
    if(isNaN(i)){
        alert("输入格式不正确!");
        return;
    }
    $("#pageNum").val(i);
    param["pageBean.pageNum"]=i;
    //回调页面方法
   queryGfgg(param)
}
   

    function queryGfgg(parDate){
         $.post("queryNewsListPage1.mht",parDate,function(data){
             $("#showcontent").html("");
             $("#showcontent").html(data);
        });
    }
   
   function queryMtbd1(parDate){
	   param["newsType"] = 1; 
       $.post("queryMediaReportListPage1.mht",parDate,function(data){
           $("#shd").html("");
           $("#shd").html(data);
      });
   }
       function queryMtbd2(parDate){
    	   param["newsType"] = 2; 
           $.post("queryMediaReportListPage1.mht",parDate,function(data){
               $("#jr").html("");
               $("#jr").html(data);
          });
  }
   
</script>
</html>
