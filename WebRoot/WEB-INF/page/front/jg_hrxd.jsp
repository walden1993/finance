<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<c:set value="走进三好资本" var="title"  scope="request" />
<jsp:include page="/include/head.jsp"></jsp:include>
<script type="text/javascript">var menuIndex = 5;</script>
<style type="text/css">
	#screen span {
		position:absolute;
		overflow:hidden;
		border:#FFF solid 1px;
		background:#FFF;
		margin-left: 50px;
	}
	#screen img{
		position:absolute;
		cursor: pointer;
		height: 106px;
		width: 160px;
	}
	#screen img:HOVER{
		height: 100%;
		width: 100%;
	}
	
	#caption, #title{
		color: #FFF;
		font-family: georgia, 'times new roman', times, veronica, serif;
		font-size: 1em;
		text-align: center;
	}
	#caption b {
		font-size: 2em;
	}

</style>
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
	    			<dd class="fl a_center w020 pt60">我们是谁(&nbsp;Who&nbsp;)</dd>
	    			<dd class="fl a_right w060"><font style="font-size: 20px;font-weight: bolder;">三</font>好贷(&nbsp;www.sanhaodai.com&nbsp;)隶属于深圳市易付通金融信息技术有限公司，是专业从事通过互联网思维嫁接投融资交易的纯中介服务平台。</dd>
    			</dl>
    		</div>
    		<div class="who_how bg-fcd8b4 h160 color1c1c1c">
    			<dl>
	    			<dd class="fl a_right w060 " style="color:#575555"><font style="font-size: 20px;font-weight: 500;">我</font>们将秉持五最原则即最专业的金融理念、最安全的优质资产、最透明的信息披露、最便捷的投融资体验、最贴心的优质服务为导向，旨在打造一个为个人、家庭及企业提供安全、高效、贴心的一站式互联网金融服务平台。</dd>
	    			<dd class="fl a_center w020 pt60" style="color:#575555">我们如何做(&nbsp;How&nbsp;)</dd>
	    			<dd class="fl a_left w020 p20" style="background: url(./images/who_bg.png) top center no-repeat;"><img alt="三好资本" src="images/about_right_pic.jpg" /></dd>
    			</dl>
    		</div>
    	</div>
    	<div class="w bg-white pl15" ><div style="background: url(./images/who_bg.png) top center no-repeat;"><img alt="宗旨" src="images/purpose.png" width="850"></div></div>
    	<img alt="发展历程" src="images/developmentmileage.jpg" class="hidden" width="881">
    	<div class="devmileage pt10"><%--历程 --%>
    	<div class="about3_top">
		    <h1>成长历程</h1>
		    <h2>一步一个脚印，脚踏实地，才能收获成长</h2>
		  </div>
    		<div class="d_left fl w040">
    			<div class="item1" style=" line-height: 120px;"></div>
    			<div class="item2"></div>
    			<div class="item2" style="line-height: 60px;"></div>
    			<div class="item2" style="height: 50px;line-height: 40px;"></div>
    			<div class="item3" style="height: 75px;line-height: 75px;"></div>
    			<div class="item2"><img title="三好资本" src="images/developmentmileage_1.png" height="60"  width="100" class="mr10"/><img title="三好资本" src="images/developmentmileage_2.png" height="60"  width="100"/></div>
    			<div class="item2"></div>
    			<div class="item2"></div>
    			<div class="item2"></div>
    		</div>
    		<div class="d_center fl w010">
    			<img title="三好资本" src="images/developmentmileage.png">
    		</div>
    		<div class="d_right fl w040">
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
    	<div class="clearfix"></div>
    	<div class="fr h400 w hidden">
	    	<div style="position: absolute;">
				<div id="title" style="position: absolute; width: 880px; height: 300px; left: 0px; top: 0px;"></div>
				<div id="border" style="border: 1px solid rgb(255, 255, 255); background: rgb(255, 255, 255) none repeat scroll 0%; position: absolute; width: 880px; height: 340px; left: 0px; top: 0px; -moz-background-clip: -moz-initial; -moz-background-origin: -moz-initial; -moz-background-inline-policy: -moz-initial;"></div>
				<div id="screen" style="background: rgb(255, 255, 255) none repeat scroll 0%; position: absolute; width: 820px; height: 300px; left: 0px; top: 0px; -moz-background-clip: -moz-initial; -moz-background-origin: -moz-initial; -moz-background-inline-policy: -moz-initial;">
					<span style="left: 0px; top: 0px; width: 400px; height: 300px; z-index: 93;"><img style="left: -11px; top: -9px;" src="images/about_b_1.jpg" alt=""></span>
					<span style="left: 0px; top: 78px; width: 400px; height: 300px; z-index: 93;"><img style="left: -11px; top: -8px;" src="images/about_b_2.jpg" alt=""></span>
					<span style="left: 0px; top: 155px; width: 400px; height: 300px; z-index: 93;"><img style="left: -11px; top: -9px;" src="images/about_b_3.jpg" alt=""></span>
					<span style="left: 0px; top: 233px; width: 400px; height: 300px; z-index: 93;"><img style="left: -11px; top: -9px;" src="images/about_b_4.jpg" alt=""></span>
					<span style="left: 103px; top: 0px; width: 400px; height: 300px; z-index: 93;"><img style="left: -11px; top: -9px;" src="images/about_b_5.jpg" alt=""></span>
					<span style="left: 103px; top: 78px; width: 400px; height: 300px; z-index: 93;"><img style="left: -11px; top: -9px;" src="images/about_b_6.jpg" alt=""></span>
					<span style="left: 103px; top: 155px; width: 400px; height: 300px; z-index: 93;"><img style="left: -11px; top: -9px;" src="images/about_b_7.jpg" alt=""></span>
					<span style="left: 103px; top: 233px; width: 400px; height: 300px; z-index: 93;"><img style="left: -11px; top: -9px;" src="images/about_b_8.jpg" alt=""></span>
				</div>
			</div>
		</div>
		<div class="clearfix"></div>
		<div class="about3 w750 bg-white h350 mb20">
  <div class="about3_top">
    <h1>办公环境</h1>
    <h2>整层2000方舒适温馨办公环境</h2>
  </div>
  <div class="btnMode" id="office_slider">    
                <a href="javascript:void(0);" class="prev btn"></a>
                <div class="scroll">	
                    <ul class="scrollContainer">
                        

                        <li class="panel " id="panel_1">
                            <a href="javascript:;" class="inside" >
                                <img width="182" height="116" alt="三好资本" title="三好资本" src="images/about_b_1.jpg" /><span>三好资本</span>
                            </a>
                        </li>
                        

                        <li class="panel " id="panel_2">
                            <a href="javascript:;" class="inside" >
                                <img width="182" height="116" alt="办公区域"  title="三好资本" src="images/about_b_6.jpg" /><span>办公区域</span>
                            </a>
                        </li>
                        

                        <li class="panel " id="panel_3">
                            <a href="javascript:;" class="inside" >
                                <img width="182" height="116" alt="办公区域"  title="三好资本" src="images/about_b_7.jpg" /><span>办公区域</span>
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
                                <img width="182" height="116" alt="办公走廊" title="三好资本" src="images/about_b_2.jpg" /><span>办公走廊</span>
                            </a>
                        </li>
                        

                        <li class="panel " id="panel_7">
                            <a href="javascript:;" class="inside" >
                                <img width="182" height="116" alt="办公走廊" title="三好资本" src="images/about_b_3.jpg" /><span>办公走廊</span>
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
	load();
})


</script>
<script type="text/javascript"><!--
window.onerror = new Function("return true");
var obj = [];
var scr;
var spa;
var img;
var W;
var Wi;
var Hi;
var wi;
var hi;
var Sx;
var Sy;
var M;
var xm;
var ym;
var xb = 0;
var yb = 0;
var ob =  - 1;
var cl = false;

/* needed in standard mode */
px = function(x)
{
	return Math.round(x) + "px";
}

/* center image  - do not resize for perf. reason */
img_center = function(o)
{
	with(img[o])
	{
		//style.left = px( - (width - Wi) / 2);
		//style.top = px( - (height - Hi) / 2);
	}
}

//////////////////////////////////////////////////////////
var Nx = 4; //size grid x
var Ny = 2; //size grid y
var Tx = 3; // image width
var Ty = 3; // image height
var Mg = 50; // margin
var SP = 0.5; // speed
//////////////////////////////////////////////////////////

function Cobj(o, x, y)
{
	this.o = o;
	this.ix = Math.min(Nx - Tx, Math.max(0, Math.round(x - (Tx / 2))));
	this.iy = Math.min(Ny - Ty, Math.max(0, Math.round(y - (Ty / 2))));
	this.li = ((this.ix * M + this.ix * Sx) - (x * M + x * Sx)) / SP;
	this.ti = ((this.iy * M + this.iy * Sy) - (y * M + y * Sy)) / SP;
	this.l = 0;
	this.t = 0;
	this.w = 0;
	this.h = 0;
	this.s = 0;
	this.mv = false;
	this.spa = spa[o].style;
	this.img = img[o];
	this.txt = img[o].alt;
	img[o].alt = "三好资本办公环境";

	/* zooming loop */
	this.zoom = function()
	{
		with(this)
		{
			l += li * s;
			t += ti * s;
			w += wi * s;
			h += hi * s;
			if ((s > 0 && w < Wi) || (s < 0 && w > Sx))
			{
				/* force window.event */
				window.focus();
				/* loop */
				setTimeout("obj[" + o + "].zoom()", 16);
			}
			else
			{
				/* finished */
				mv = false;
				/* set final position */
				if (s > 0)
				{
					l = ix * M + ix * Sx;
					t = iy * M + iy * Sy;
					w = Wi;
					h = Hi;
				}
				else
				{
					l = x * M + x * Sx;
					t = y * M + y * Sy;
					w = Sx;
					h = Sy;
				}
			}
			/* html animation */
			with(spa)
			{
				left = px(l);
				top = px(t);
				width = px(w);
				height = px(h);
				zIndex = Math.round(w);
			}
		}
	}

	this.click = function()
	{
		with(this)
		{
			img_center(o);
			/* zooming logic */
			if ( ! mv || cl)
			{
				if (s > 0)
				{
					if (cl || Math.abs(xm - xb) > Sx * .4 || Math.abs(ym - yb) > Sy * .4)
					{
						s =  - 2;
						mv = true;
						zoom();
						cap.innerHTML = txt;
					}
				}
				else
				{
					if (cl || ob != o)
					{
						if (ob >= 0)
						{
							with(obj[ob])
							{
								s =  - 2;
								mv = true;
								zoom();
							}
						}
						ob = o;
						s = 1;
						xb = xm;
						yb = ym;
						mv = true;
						zoom();
						cap.innerHTML = txt;
					}
				}
			}
		}
	}
	
	/* hook up events */
	//img[o].onmouseover = img[o].onmousemove = img[o].onmouseout = new Function("cl=false;obj[" + o + "].click()");
	img[o].onclick = new Function("cl=true;obj[" + o + "].click()");
	img[o].onload = new Function("img_center(" + o + ")");

	/* initial display */
	this.zoom();
}

/* mouse */
document.onmousemove = function(e)
{
	if ( ! e)
	{
		e = window.event;
	}
	xm = (e.x || e.clientX);
	ym = (e.y || e.clientY);
}

/* init */
function load()
{
	/* html elements */
	scr = document.getElementById("screen");
	spa = scr.getElementsByTagName("span");
	img = scr.getElementsByTagName("img");
	cap = document.getElementById("caption");
	
	/* mouseover border */ 
	document.getElementById("border").onmouseover = function()
	{
		cl = true;
		if(ob >= 0 && obj[ob].s > 0) obj[ob].click();
		ob = -1;
	}

	/* global variables */
	W = parseInt(scr.style.width);
	H = parseInt(scr.style.height);
	M = W / Mg;
	Sx = (W - (Nx - 1) * M) / Nx;
	Sy = (H - (Ny - 1) * M) / Ny;
	Wi = Tx * Sx + (Tx - 1) * M;
	Hi = Ty * Sy + (Ty - 1) * M;
	SP = M * Tx * SP;
	wi = (Wi - Sx) / SP;
	hi = (Hi - Sy) / SP;
	
	
	/* create objects */
	for (k = 0, i = 0; i < Nx; i ++)
	{
		for (j = 0; j < Ny; j ++)
		{
			obj[k] = new Cobj(k ++, i, j);
		}
	}
}

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
