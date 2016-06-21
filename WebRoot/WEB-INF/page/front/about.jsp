<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<c:set value="走进三好资本" var="title"  scope="request" />
<jsp:include page="/include/head.jsp"></jsp:include>
<script type="text/javascript">var menuIndex = 5;</script>
</head>

<body>
<!-- 引用头部公共部分 -->
<jsp:include page="/include/top.jsp"></jsp:include>

<!--内容区域-->
<div class="wrap">
  <div class="w950">
	  <ol class="breadcrumb mb0 bg-none">
		  <li><a href="/">首页 </a></li>
		  <li>关于我们</li>
		  <li class="active">走进三好资本</li>
	  </ol>
  	<jsp:include page="/WEB-INF/page/helpcenter/aboutme_left.jsp"></jsp:include>
    <div class="r_main fr w750 mb">
    	<div class="pub_tb bottom"><h3>走进三好资本</h3></div><div class="clearfix"></div>
    	<div class="about_context p15 bg-white pb0">
    		<div class="who_how bg-e94c3d h140">
    			<dl>
	    			<dd class="fl a_left w020 p20"><img alt="三好资本" src="images/about_left_pic.jpg" /></dd>
	    			<dd class="fl a_center w025 pt60">我们是谁(&nbsp;Who&nbsp;)</dd>
	    			<dd class="fl a_right w050"><font style="font-size: 20px;font-weight: bolder;">三</font>好资本(&nbsp;www.sanhaodai.com&nbsp;)隶属于深圳市易付通金融信息技术有限公司，是专业从事通过互联网思维嫁接投融资交易的纯中介服务平台。</dd>
    			</dl>
    		</div>
    		<div class="who_how bg-fcd8b4 h160 color1c1c1c">
    			<dl>
	    			<dd class="fl a_right w053 " style="color:#575555"><font style="font-size: 20px;font-weight: 500;">我</font>们将秉持五最原则即最专业的金融理念、最安全的优质资产、最透明的信息披露、最便捷的投融资体验、最贴心的优质服务为导向，旨在打造一个为个人、家庭及企业提供安全、高效、贴心的一站式互联网金融服务平台。</dd>
	    			<dd class="fl a_center w025 pt60" style="color:#575555">我们如何做(&nbsp;How&nbsp;)</dd>
	    			<dd class="fl a_left w022 p20" style="background: url(./images/who_bg.png) top center no-repeat;"><img alt="三好资本" src="images/about_right_pic.jpg" /></dd>
    			</dl>
    		</div>
    	</div>
    	<div class="w bg-white pl15" ><div style="background: url(./images/who_bg.png) top center no-repeat;"><img alt="宗旨" data-echo="images/purpose.png" src="images/blank.gif" class="loadingImg" width="720"></div></div>
    	<img alt="发展历程" src="images/developmentmileage.jpg" class="hidden" width="881">
    	<div class="devmileage pt10"><%--历程 --%>
    	<div class="about3_top">
		    <h1>成长历程</h1>
		    <p>一步一个脚印，脚踏实地，才能收获成长</p>
		  </div>
    		<div class="d_left fl w040">
    			<div class="item1" style=" line-height: 120px;"></div>
    			<div class="item2"></div>
    			<div class="item2" style="line-height: 60px;"></div>
    			<div class="item2" style="height: 50px;line-height: 40px;"></div>
    			<div class="item3" style="height: 95px;line-height: 95px;"><a class="single_image2" href="images/developmentmileage_3.png" data-fancybox-group="gallery"><img title="三好资本"  src="images/blank.gif" class="loadingImg" data-echo="images/developmentmileage_1.png" height="60"  width="100" class="mr10"/><img title="三好资本"  src="images/blank.gif" class="loadingImg" data-echo="images/developmentmileage_2.png" height="60"  width="100"/></a></div>
    			<div class="item2"></div>
    			<div class="item2"></div>
    			<div class="item2"></div>
    			<div class="item2"></div>
    		</div>
    		<div class="d_center fl w010">
    			<img title="三好资本" data-echo="images/developmentmileage.png"  src="images/blank.gif">
    		</div>
    		<div class="d_right fl">
    			<div class="item1">持续进步</div>
    			<div class="item2">平台全新改版上线(新样式、新格局、新思维)</div>
    			<div class="item2">平台融资破亿</div>
    			<div class="item2">平台交易突破5000万</div>
    			<div class="item1" style="height: 70px;"></div>
    			<div class="item2" >⊙参加深圳金博会 ⊙会员突破10000人</div>
    			<div class="item2">交易额突破1000万</div>
    			<div class="item2">三好资本平台正式上线</div>
    			<div class="item1" style="height: 120px;"></div>
    			<div class="item2" style="height: 50px;line-height: 10px;">深圳市易付通金融信息技术有限公司成立</div>
    		</div>
    	</div>
		
		<div class="about3 w750 bg-white h350 mb40"><div class="clearfix"></div>
  <div class="about3_top">
    <h1>办公环境</h1>
    <p>整层2000平方舒适温馨办公环境</p>
  </div>
  <div class="btnMode" id="office_slider">    
                <a href="javascript:void(0);" class="prev btn"></a>
                <div class="scroll">	
                    <ul class="scrollContainer">
                        
						<li class="panel " id="panel_1">
                            <a href="javascript:;" class="inside" >
                                <img width="182" height="116" alt="办公走廊" title="三好资本" src="images/about_b_3.jpg" /><span>办公走廊</span>
                            </a>
                        </li>
                        

                        <li class="panel " id="panel_2">
                            <a href="javascript:;" class="inside" >
                                <img width="182" height="116" alt="办公区域"  title="三好资本" src="images/about_b_6.jpg" /><span>办公区域</span>
                            </a>
                        </li>
                        

                        <li class="panel " id="panel_3">
                            <a href="javascript:;" class="inside" >
                                <img width="182" height="116" alt="办公区域"  title="三好资本" src="images/about_b_1.jpg" /><span>办公区域</span>
                            </a>
                        </li>
                        

                        <li class="panel current" id="panel_4">
                            <a href="javascript:;" class="inside" >
                                <img width="182" height="116" alt="办公区域" title="三好资本"  src="images/about_b_8.jpg" /><span>办公区域</span>
                            </a>
                        </li>
                        

                        <li class="panel " id="panel_5">
                            <a href="javascript:;" class="inside" >
                                <img width="182" height="116" alt="荣誉墙" title="三好资本" src="images/about_b_5.jpg" /><span>荣誉墙</span>
                            </a>
                        </li>
                        

                        <li class="panel " id="panel_6">
                            <a href="javascript:;" class="inside" >
                                <img width="182" height="116" alt="办公走廊" title="三好资本" src="images/about_b_7.jpg" /><span>办公走廊</span>
                            </a>
                        </li>
                        

                        
                        <li class="panel " id="panel_7">
                            <a href="javascript:;" class="inside" >
                                <img width="182" height="116" alt="三好资本" title="三好资本" src="images/about_b_2.jpg" /><span>三好资本</span>
                            </a>
                        </li>
                        

                        <li class="panel " id="panel_8">
                            <a href="javascript:;" class="inside" >
                                <img width="182" height="116" alt="办公走廊" title="三好资本" src="images/about_b_4.jpg" /><span>办公走廊</span>
                            </a>
                        </li>
                    </ul>
                </div>
                <a href="javascript:void(0);" class="next btn"></a>     
            </div>
</div>
    </div>
  </div>
</div>
<div class="clearfix"></div>
<!--footer-->
<jsp:include page="/include/footer.jsp"></jsp:include>
<script type="text/javascript">
$(function(){
	$(".html_left a:eq(0)").addClass("hover");
	$('.single_image2').fancybox();
})


</script>
<script type="text/javascript"><!--
$(function(){
	/*未元素的首尾添加补白*/
	var $panels				= $('#office_slider .scrollContainer > li');
	var $parent=$panels.parent();//或许当前li的最近一级的父元素
	var $length=$panels.length;//获取指定length值
	var $first=$panels.eq(0).clone().addClass("panel cloned").attr("id",'panel_'+($length+1));//获取第一个元素并复制
	var $last=$panels.eq($length-1).clone().addClass("cloned").attr("id",'panel_0');;//获取最后一个元素并复制
	$parent.append($first);//将li序列中的第一个添加到ul元素中的最后一个  $("#slide02").scrollLeft(0);
	$parent.prepend($last);//将li序列中的最后一个添加到ul元素中的第一个
	var totalPanels			= $(".scrollContainer").children().size();//所有子元素的数字，滚动元素的个数 7
	var regWidth			= $(".panel").css("width");//获取li元素的宽度
	var regImgWidth			= $(".panel img").css("width");//获取其中图片的宽度
	var movingDistance	    = 230;//每次移动的距离
	var curWidth			= 362;//当前中间li的宽度为350px
	var curHeight         =236;//当前中间li的高度未312  
	var curImgWidth  =362;
	var curImgHeight  =236;
	var othersW           =226;//其他li的宽度
	var othersH           =150;//高度
	var othersImgW           =226;//其他li的宽度
	var othersImgH           =150;//高度
	var $panels				= $('#office_slider .scrollContainer > li');//此处作用是将复制进来补白的元素重新赋值
	var $container			= $('#office_slider .scrollContainer');
	$panels.css({'float' : 'left','position' : 'relative'});
	$("#office_slider").data("currentlyMoving", false);//是否正在运动中
	$container.css('width', (($panels[0].offsetWidth+3) * $panels.length) + 738 ).css('left','0');//计算容器的总的宽度 PS：25为margin值 offsetWidth
	var scroll = $('#office_slider .scroll').css('overflow', 'hidden');
	function returnToNormal(element) {//li元素返回到正常状态
		$(element).animate({ width: othersW,height: othersH}).find("img").animate({width:othersImgW,height:othersImgH});
	};
	function growBigger(element) {//当前元素之间变大
		$(element).addClass("current").animate({ width: curWidth,height:curHeight,'margin-top':0}).siblings().removeClass("current").css('margin-top','43px')
		.end().find("img").animate({width:curImgWidth,height:curImgHeight})
	}
	//direction true = right, false = left
	function change(direction) {
		//if not at the first or last panel
		if((direction && !(curPanel < totalPanels-1)) || (!direction && (curPanel <= 0))) {
			return false;
		}	
		//if not currently moving
		if (($("#office_slider").data("currentlyMoving") == false)) {
			$("#office_slider").data("currentlyMoving", true);
			var next         = direction ? curPanel + 1 : curPanel - 1;
			var leftValue    = $(".scrollContainer").css("left");
			var movement	 = direction ? parseFloat(leftValue, 10) - movingDistance : parseFloat(leftValue, 10) + movingDistance;
			$(".scrollContainer").stop().animate({"left": movement}, function() {
				$("#office_slider").data("currentlyMoving", false);
			});
			returnToNormal("#panel_"+curPanel);
			growBigger("#panel_"+next);
			curPanel = next;
			//remove all previous bound functions
			$("#panel_"+(curPanel+1)).unbind();	
			//go forward
			$("#panel_"+(curPanel+1)).click(function(){ change(true); });
			//remove all previous bound functions															
			$("#panel_"+(curPanel-1)).unbind();
			//go back
			$("#panel_"+(curPanel-1)).click(function(){ change(false); }); 
			//remove all previous bound functions
			$("#panel_"+curPanel).unbind();
		}
	}
	// Set up "Current" panel and next and prev 设置当前元素和上下
	growBigger("#panel_3");	
	var curPanel = 3;
	$("#panel_"+(curPanel+1)).click(function(){ change(true);return false;});
	$("#panel_"+(curPanel-1)).click(function(){ change(false);return false;});
	//when the prev/next arrows are clicked
	$("#office_slider .next").click(function(){ change(true);});	
	$("#office_slider .prev").click(function(){ change(false);});
	$(window).keydown(function(event){//键盘方向键控制
		switch (event.keyCode) {
			case 13: //enter
				$(".next").click();
			break;
			case 37: //prev arrow
				$(".prev").click();
			break;
			case 39: //next arrow
				$(".next").click();
			break;
		}
	});	
	
});
//-->
</script>
</body>
</html>
