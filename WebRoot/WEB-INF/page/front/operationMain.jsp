<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<c:set value="运营主体" var="title"  scope="request" />
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
		  <li class="active">运营主体</li>
	  </ol>
  	<jsp:include page="/WEB-INF/page/helpcenter/aboutme_left.jsp"></jsp:include>
    <div class="r_main fr w750 mb">
    	<div class="pub_tb bottom"><h3>运营主体</h3></div><div class="clearfix"></div>
    	<div class="about_context p15 bg-white mb10">
    		<div>
    			<img title="华融金融集团" src="images/about_hr_logo.png" class="fl text-center w030">
    			<div class="fl f14 mt10 pr10  w070" style="text-indent: 2em;line-height: 1.9;color: #5F5757;">深圳市易付通金融信息技术有限公司隶属于深圳华融金融集团旗下成员企业。公司成立于2010年02月05日，注册资本5000万元整，专业从事互联网金融服务业务，核心管理团队拥有丰富的互联网、金融业的从业经验。</div>
    		</div>
    		<div class="clearfix"></div>
    		<div class="mt10">
    			<div class="operate3_title">
				    股东结构
				  </div>
    			<img title="三好资本" src="images/gdjg.png" >
    		</div>
    		<div class="mt10">
    			<div class="operate3_title mb10">
				    相关资质
				  </div>
    			<a class="single_image1" href="images/abt_pic08.jpg" data-fancybox-group="gallery">
    			<img title="三好资本" src="images/abt_pic08.jpg" class="ml10 mt10" width="160"></a>
    			<a class="single_image1" href="images/abt_pic09.jpg" data-fancybox-group="gallery">
    			<img title="三好资本" src="images/abt_pic09.jpg" class="ml10 mt10" width="160"></a>
    			<a class="single_image1" href="images/abt_pic10.jpg" data-fancybox-group="gallery">
    			<img title="三好资本" src="images/abt_pic10.jpg" class="ml10 mt10" width="160"></a>
    			<a class="single_image1" href="images/abt_pic11.jpg" data-fancybox-group="gallery">
    			<img title="三好资本" src="images/abt_pic11.jpg" class="ml10 mt10" width="160"></a>
    			<a class="single_image1 hidden" href="images/qiyedj.png" data-fancybox-group="gallery">
    			<img title="三好资本" src="images/qiyedj.png" class="ml10 mt10" width="160"></a>
    		</div>
    		
    	</div>
    </div>
  </div>
</div>
<!--footer-->
<jsp:include page="/include/footer.jsp"></jsp:include>
<script type="text/javascript">
$(function(){
	$('.single_image1').fancybox();
	$(".html_left a:eq(1)").addClass("hover");
})
</script>
</body>
</html>
