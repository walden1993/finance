<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<jsp:include page="/include/head.jsp"></jsp:include>
    <link href="css/inside.css"  rel="stylesheet" type="text/css" />
<style type="text/css">
#imgxz img {max-width: 620px;};
</style>
</head>

<body>
<!-- 引用头部公共部分 -->
<jsp:include page="/include/top.jsp"></jsp:include>	
<div class="nymain">
  <div class="lcnav">
    <div class="tab">
<div class="tabmain">
  <ul><li class="on" style="padding:0 20px;">媒体报告详情</li>
  </ul>
    </div>
    </div>
    <div class="line">
    </div>
  </div>
  <div class="lcmain">
    <div class="lcmain_l" style="width:675px">
    <div class="lctab" style="padding:0 10px;">
    <div class="gginfo" style="padding-bottom:30px">
    <h2>${map.title}</h2>
  	<div id="imgxz">${map.content }</div>
    </div>
    <div class="sxnews">
    <ul>
    </ul>
    </div>
    </div>
    </div>
  
  </div>
</div>

<!-- 引用底部公共部分 -->     
<jsp:include page="/include/footer.jsp"></jsp:include>

<script type="text/javascript" src="script/jquery.dispatch.js"></script>
<script>
$(function(){
   

	initNewsListInfo(null);
         
   
    //样式选中
dqzt(0)
});
	
	
		function initNewsListInfo(praData){		
			$.dispatchPost("frontQueryNewsList.mht",praData,function(data){
				$("#showDongtai").html(data);
			});
			
		}
</script>
</body>
</html>

