<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link rel="stylesheet" type="text/css" href="css/common.css" />
<link rel="stylesheet" type="text/css" href="css/index.css" />
<link rel="stylesheet" type="text/css" href="css/new_inside.css" />   
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" />
<link rel="stylesheet" href="css/lightbox.css" type="text/css" media="screen" />
<jsp:include page="/include/head.jsp"></jsp:include>

</head>

<body>
<!-- 引用头部公共部分 -->
<jsp:include page="/include/top.jsp"></jsp:include>	
<div class="content">
		<div class="n_about_top clearfix">
    <div class="n_about_top">
      <div class="w1002 location clearfix"><a href="<%=application.getAttribute("basePath")%>">首页</a> &gt; 网站公告</div>
      <div class="w1002 anchor clearfix">
      <ul class="n_about_menu">
           <li id="aboutower1"><a href="#" >关于我们</a></li>
           <li id="communique2"  class="on"><a href="#">网站公告</a></li>
           <li ><a href="callkfs.mht">平台资讯</a></li>
           <li id="teamwork4"><a href="#">合作伙伴</a></li>
           <li id="touchus5"><a href="#">联系我们</a></li>
           <li><a href="callcenter.mht">帮助中心</a></li>
       </ul>
       </div>
    </div>
</div>	
  <div id="showcontent" class="left">
  <h3>${paramMap.columName}</h3>
        <p class="zw">${paramMap.content}</p>
      <!-- 内容显示位置 -->
    </div>
    <div class="clear"></div>	
</div>
<!-- 引用底部公共部分 -->     
<jsp:include page="/include/footer.jsp"></jsp:include>
<script type="text/javascript" src="script/jquery.dispatch.js"></script>
<script>
$(function(){
     //样式选中
	dqzt(0);
	$("#sy_hover").removeClass('nav_first');
	$("#lt_hover").attr('class','nav_first');
	 $('#communique a').addClass('li_curr');
queryGfgg(1);
	//联系我们 
$("#touchus").click(function(){
	$('#aboutower a').removeClass('li_curr');
	$('#costdesc a').removeClass('li_curr');
	$('#invite a').removeClass('li_curr');
	$('#teamwork a').removeClass('li_curr');
	$('#links a').removeClass('li_curr');
	 $('#communique a').removeClass('li_curr');
	$('#mediaReport a').removeClass('li_curr');
	 $('#touchus a').addClass('li_curr');
    $.post("queryMessageDetail.mht","typeId=7",function(data){
         $("#showcontent").html("");
         $("#showcontent").html("<div class='news_content_wrap'><div class='news_bar'><h3>"+data.columName+"</h3></div><div class='content_bar'></div>"+
                 "<div class='content_text'><p class='zw'>"+data.content+"</p></div></div>");
    });
});

//关于我们
$("#aboutower").click(function(){
	$('#costdesc a').removeClass('li_curr');
	$('#invite a').removeClass('li_curr');
	$('#teamwork a').removeClass('li_curr');
	$('#links a').removeClass('li_curr');
	 $('#communique a').removeClass('li_curr');
	$('#mediaReport a').removeClass('li_curr');
	 $('#touchus a').removeClass('li_curr');
	 $('#aboutower a').addClass('li_curr');
    $.post("queryMessageDetail.mht","typeId=4",function(data){
         $("#showcontent").html("");
         $("#showcontent").html("<div class='news_content_wrap'><div class='news_bar'><h3>"+data.columName+"</h3></div><div class='content_bar'></div>"+
                 "<div class='content_text'><p class='zw'>"+data.content+"</p></div></div>");
    });
});

	//资费说明
$("#costdesc").click(function(){
	 $('#costdesc a').addClass('li_curr');
		$('#invite a').removeClass('li_curr');
		$('#teamwork a').removeClass('li_curr');
		$('#links a').removeClass('li_curr');
		 $('#communique a').removeClass('li_curr');
		$('#mediaReport a').removeClass('li_curr');
		 $('#touchus a').removeClass('li_curr');
		 $('#aboutower a').removeClass('li_curr');
    $.post("queryMessageDetail.mht","typeId=6",function(data){
         $("#showcontent").html("");
         $("#showcontent").html("<div class='news_content_wrap'><div class='news_bar'><h3>"+data.columName+"</h3></div><div class='content_bar'></div>"+
                 "<div class='content_text'><p class='zw'>"+data.content+"</p></div></div>");
    });
});


	//招贤纳士
$("#invite").click(function(){
	 $('#invite a').addClass('li_curr');
	 $('#costdesc a').removeClass('li_curr');
	 $('#teamwork a').removeClass('li_curr');
	 $('#links a').removeClass('li_curr');
	 $('#communique a').removeClass('li_curr');
	 $('#mediaReport a').removeClass('li_curr');
	 $('#touchus a').removeClass('li_curr');
	 $('#aboutower a').removeClass('li_curr');
    $.post("queryMessageDetail.mht","typeId=10",function(data){
         $("#showcontent").html("");
         $("#showcontent").html("<div class='news_content_wrap'><div class='news_bar'><h3>"+data.columName+"</h3></div><div class='content_bar'></div>"+
                 "<div class='content_text'><p class='zw'>"+data.content+"</p></div></div>");
    });
});

		//合作伙伴
$("#teamwork").click(function(){
	 $('#invite a').removeClass('li_curr');
	 $('#communique a').removeClass('li_curr');
	 $('#costdesc a').removeClass('li_currn');
	 $('#links a').removeClass('li_curr');
	 $('#mediaReport a').removeClass('li_curr');
	 $('#touchus a').removeClass('li_curr');
	 $('#aboutower a').removeClass('li_curr');
	 $('#teamwork a').addClass('li_curr');
    $.post("queryMessageDetail.mht","typeId=11",function(data){
         $("#showcontent").html("");
         $("#showcontent").html("<div class='news_content_wrap'><div class='news_bar'><h3>"+data.columName+"</h3></div><div class='content_bar'></div>"+
                 "<div class='content_text'><p class='zw'>"+data.content+"</p></div></div>");
    });
});

			//友情链接
$("#links").click(function(){
	 $('#invite a').removeClass('li_curr');
	 $('#communique a').removeClass('li_curr');
	 $('#costdesc a').removeClass('li_curr');
	 $('#teamwork a').removeClass('li_curr');
	 $('#mediaReport a').removeClass('li_curr');
	 $('#touchus a').removeClass('li_curr');
	 $('#aboutower a').removeClass('li_curr');
	 $('#links a').addClass('li_curr');
    $.post("frontQueryMediaReportdList.mht","",function(data){
         $("#showcontent").html("");
         $("#showcontent").html("<div class='news_content_wrap_link'><div class='news_bar'><h3>友情链接</h3></div><div class='content_bar'></div>"+data+"</div>");
         $(".boxmain h3").remove();
         $(".news_content_wrap").append("<div class='clear'></div>");
         $(".boxmain").append("<div class='clear'></div>");
         $(".boxmain ul").removeClass("kefu").addClass("media_links");
         $(".media_links img").css("width","150").css("height","60px");
    });
});

				//媒体报道
$("#mediaReport").click(function(){
	 $('#invite a').removeClass('li_curr');
	 $('#costdesc a').removeClass('li_curr');
	 $('#teamwork a').removeClass('li_curr');
	 $('#links a').removeClass('li_curr');
	 $('#touchus a').removeClass('li_curr');
	 $('#aboutower a').removeClass('li_curr');
	 $('#communique a').removeClass('li_curr');
	 $('#mediaReport a').addClass('li_curr');
	  param["pageBean.pageNum"] = 1; 
	  queryMtbd(param);
});

//官方公告
$("#communique").click(function(){
	 $('#mediaReport a').removeClass('li_curr');
	 $('#invite a').removeClass('li_curr');
	 $('#costdesc a').removeClass('li_curr');
	 $('#teamwork a').removeClass('li_curr');
	 $('#links a').removeClass('li_curr');
	 $('#touchus a').removeClass('li_curr');
	 $('#aboutower a').removeClass('li_curr');
	 $('#communique a').addClass('li_curr');
	  param["pageBean.pageNum"] = 1; 
	 queryGfgg(param);
});
	
	
	
});
function doMtbdJumpPage(i){
		if(isNaN(i)){
			alert("输入格式不正确!");
			return;
		}
		$("#pageNum").val(i);
		param["pageBean.pageNum"]=i;
		//回调页面方法
		queryMtbd(param);
	}
	function queryMtbd(parDate){
		 $.post("queryMediaReportListPage.mht",parDate,function(data){
	         $("#showcontent").html("");
	         $("#showcontent").html(data);
	    });
	}

	function doqueryGfggJumpPage(i){
		if(isNaN(i)){
			alert("输入格式不正确!");
			return;
		}
		$("#pageNum").val(i);
		param["pageBean.pageNum"]=i;
		//回调页面方法
		queryGfgg(param);
	}
	function queryGfgg(parDate){
		 $.post("queryNewsListPage.mht",parDate,function(data){
	         $("#showcontent").html("");
	         $("#showcontent").html(data);
	    });
	}
</script>
</body>
</html>
