<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<c:set value="三好资本动态" var="title" scope="request"></c:set>
<jsp:include page="/include/head.jsp"></jsp:include>
<link rel="stylesheet" type="text/css" href="css/new_inside.css" />   
<script type="text/javascript">var menuIndex = 5;</script>
</head>

<body>
<!-- 引用头部公共部分 -->
<jsp:include page="/include/top.jsp"></jsp:include>

<!--内容区域-->

<!--介绍-->
<div class="wrap">
<!--介绍-->
<div class="w950">
  <div class="w950">
	  <ol class="breadcrumb mb0 bg-none">
		  <li><a href="/">首页 </a></li>
		  <li>关于我们</li>
		  <li class="active">三好资本动态</li>
	  </ol>
  	<jsp:include page="/WEB-INF/page/helpcenter/aboutme_left.jsp"></jsp:include>
      <div id="shd" class="w750 fr bg-white mb20">
     
      </div>
  </div>
</div>
</div>
<!--footer-->
<jsp:include page="/include/footer.jsp"></jsp:include>
<script type="text/javascript">

function doMtbdJumpPage1(i){
    if(isNaN(i)){
        alert("输入格式不正确!");
        return;
    }
    
    $("#pageNum").val(i);
    param["pageBean.pageNum"]=i;
    //回调页面方法
   queryMtbd1(param)
}

function GetRequest() {
    var url = location.search; //获取url中"?"符后的字串
    var theRequest = new Object();
    if (url.indexOf("?") != -1) {
       var str = url.substr(1);
       strs = str.split("&");
       for(var i = 0; i < strs.length; i ++) {
          theRequest[strs[i].split("=")[0]]=unescape(strs[i].split("=")[1]);
       }
    }
    return theRequest;
 }


function goRoll(type){
	window.location.href= "queryAboutInfo.mht?msg="+type;
}
$(function(){
    param["pageBean.pageNum"] = 1; 
    //queryGfgg(param);
    queryMtbd1(param);
    //queryMtbd2(param);
    $(".html_left a:eq(7)").addClass("hover");
})

 function queryGfgg(parDate){
      $.dispatchPost("queryNewsListPage2.mht",parDate,function(data){
          $("#showcontent").html("");
          $("#showcontent").html(data);
     });
 }

function queryMtbd1(parDate){
	
    param["newsType"] = 1; 
    var label = parseInt(GetRequest()['lable']) || '';
    param["label"] = label;
    $.post("queryMediaReportListPage2.mht",parDate,function(data){
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
</body>
</html>
