<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<jsp:include page="/include/head.jsp"></jsp:include>
    
</head>

<body>
<!-- 引用头部公共部分 -->
<jsp:include page="/include/top.jsp"></jsp:include>	
<div class="content">
		<div class="left about_nav">
			<div class="about_navbar">关于我们</div>
			<div class="abnav_wrap">
				<ul class="abnav_ul">
						<li id="aboutower">
							<span class="list_bg"></span>
							<a href="javascript:void(0);">公司介绍</a>
						</li>
						<li id="touchus">
							<span class="list_bg"></span>
							<a href="javascript:void(0);">联系我们</a>
						</li>
						<li id="costdesc">
							<span class="list_bg"></span>
							<a href="javascript:void(0);">资费说明</a>
						</li>
						<li id="communique">
							<span class="list_bg"></span>
							<a href="javascript:void(0);">官方公告</a>
						</li>
						<li id="mediaReport">
							<span class="list_bg"></span>
							<a href="javascript:void(0);">媒体报道</a>
						</li>
						<li id="invite">
							<span class="list_bg"></span>
							<a href="javascript:void(0);">招贤纳士</a>
						</li>
						<li id="teamwork">
							<span class="list_bg"></span>
							<a href="javascript:void(0);">合作伙伴</a>
						</li>
						<li id="links">
							<span class="list_bg"></span>
							<a href="javascript:void(0);">友情链接</a>
						</li>
					</ul>
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
	$('#touchus a').addClass('li_curr');
	 $.post("queryMessageDetail.mht","typeId=7",function(data){
         $("#showcontent").html("");
         $("#showcontent").html("<div class='news_content_wrap'><div class='news_bar'><h3>"+data.columName+"</h3></div><div class='content_bar'></div>"+
                 "<div class='content_text'><p class='zw'>"+data.content+"</p></div></div>");
    });
	//联系我们 
	 $("#touchus").click(function(){
			$('#aboutower a').removeClass('li_curr');
			$('#costdesc a').removeClass('li_curr');
			$('#invite a').removeClass('li_curr');
			$('#teamwork a').removeClass('li_curr');
			$('#links a').removeClass('li_curr');
			 $('#communique').removeClass('li_curr');
			$('#mediaReport').removeClass('li_curr');
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
			 $('#communique').removeClass('li_curr');
			$('#mediaReport').removeClass('li_curr');
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
				 $('#communique').removeClass('li_curr');
				$('#mediaReport').removeClass('li_curr');
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
			 $('#communique').removeClass('li_curr');
			 $('#mediaReport').removeClass('li_curr');
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
			 $('#communique').removeClass('li_curr');
			 $('#costdesc a').removeClass('li_currn');
			 $('#links a').removeClass('li_curr');
			 $('#mediaReport').removeClass('li_curr');
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
			 $('#communique').removeClass('li_curr');
			 $('#costdesc a').removeClass('li_curr');
			 $('#teamwork a').removeClass('li_curr');
			 $('#mediaReport').removeClass('li_curr');
			 $('#touchus a').removeClass('li_curr');
			 $('#aboutower a').removeClass('li_curr');
			 $('#links a').addClass('li_curr');
		    $.post("frontQueryMediaReportdList.mht","",function(data){
		         $("#showcontent").html("");
		         $("#showcontent").html("<div class='news_content_wrap'><div class='news_bar'><h3>友情链接</h3></div><div class='content_bar'></div>"+data+"</div>");
		         $(".boxmain h3").remove();
		         $(".news_content_wrap").append("<div class='clear'></div>");
		         $(".boxmain ul").removeClass("kefu").addClass("media_links");
		         $(".media_links img").css("width","150").css("height","60px");
		    });
		});

						//媒体报道
		$("#mediaReport").click(function(){
			 $('#mediaReport a').addClass('li_curr');
			 $('#invite a').removeClass('li_curr');
			 $('#costdesc a').removeClass('li_curr');
			 $('#teamwork a').removeClass('li_curr');
			 $('#links a').removeClass('li_curr');
			 $('#touchus a').removeClass('li_curr');
			 $('#aboutower a').removeClass('li_curr');
			 $('#communique a').removeClass('li_curr');
			  param["pageBean.pageNum"] = 1; 
			  queryMtbd(param);
		});

		//官方公告
		$("#communique").click(function(){
			 $('#communique a').addClass('li_curr');
			 $('#mediaReport a').removeClass('li_curr');
			 $('#invite a').removeClass('li_curr');
			 $('#costdesc a').removeClass('li_curr');
			 $('#teamwork a').removeClass('li_curr');
			 $('#links a').removeClass('li_curr');
			 $('#touchus a').removeClass('li_curr');
			 $('#aboutower a').removeClass('li_curr');
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
