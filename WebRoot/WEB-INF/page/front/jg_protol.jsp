<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<c:set value="协议范本" var="title"  scope="request" />
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
		  <li><a href="queryAboutInfo.mht?msg=99">关于我们 </a></li>
		  <li>协议范本</li>
	  </ol>
  	<jsp:include page="/WEB-INF/page/helpcenter/aboutme_left.jsp"></jsp:include>
    <div class="r_main fr w750 mb bg-white">
    	<div class="pub_tb bottom"><h3>协议范本</h3></div><div class="clearfix"></div>
    	<div class="xieyi_wrap  clearfix">
    	<a class="fancybox.ajax" href="protocol_reg.mht" >
			<div class="xieyi_unit">
        		<div class="tit">注册协议</div>
        		<div class="dis">2015-07-31日修订</div>
        		<div class="com">sanhaodai.com</div>
        	</div>
        </a>
        <a class="fancybox.ajax"  href="protocol_privacy.mht">
			<div class="xieyi_unit">
        		<div class="tit">隐私条款</div>
        		<div class="dis">2015-07-31日修订</div>
        		<div class="com">sanhaodai.com</div>
        	</div>
        </a>
        <a class="fancybox.ajax" href="protocol_service.mht" >
			<div class="xieyi_unit">
        		<div class="tit">服务协议</div>
        		<div class="dis">2015-07-31日修订</div>
        		<div class="com">sanhaodai.com</div>
        	</div>
        </a>
        <a class="fancybox.ajax" href="protocol_borrow.mht">
			<div class="xieyi_unit">
        		<div class="tit">借款协议</div>
        		<div class="dis">2015-07-31日修订</div>
        		<div class="com">sanhaodai.com</div>
        	</div>
        </a>
        <a class="fancybox.ajax" href="protocol_dbh.mht">
			<div class="xieyi_unit">
        		<div class="tit">担保函协议</div>
        		<div class="dis">2015-07-31日修订</div>
        		<div class="com">sanhaodai.com</div>
        	</div>
        </a>
        <a class="fancybox.ajax" href="protocol_entrust.mht">
			<div class="xieyi_unit">
        		<div class="tit">委托担保合同</div>
        		<div class="dis">2015-07-31日修订</div>
        		<div class="com">sanhaodai.com</div>
        	</div>
        </a>
        <a class="fancybox.ajax" href="protocol_quickpay.mht" >
			<div class="xieyi_unit">
        		<div class="tit">快捷支付协议</div>
        		<div class="dis">2015-07-31日修订</div>
        		<div class="com">sanhaodai.com</div>
        	</div>
        </a>
        <a class="fancybox.ajax" href="protocol_Intermediate.mht" >
			<div class="xieyi_unit">
        		<div class="tit">居间服务合同</div>
        		<div class="dis">2015-07-31日修订</div>
        		<div class="com">sanhaodai.com</div>
        	</div>
        </a>
        <a class="fancybox.ajax" href="protocol_zxb_pay.mht" >
			<div class="xieyi_unit">
        		<div class="tit">涨薪宝投资协议</div>
        		<div class="dis">2015-10-21日修订</div>
        		<div class="com">sanhaodai.com</div>
        	</div>
        </a>
    	</div>
    	<p class="text-left" style="padding-left: 100px;">声明：平台公示的以上协议文本为合同范本仅供参考，平台享有最终解释权和修改权</p>
    </div>
  </div>
</div>
<div class="clearfix"></div>
<!--footer-->
<jsp:include page="/include/footer.jsp"></jsp:include>
<script type="text/javascript">
$(function(){
		$(".html_left a:eq(5)").addClass("hover");
	$('.xieyi_wrap a').fancybox();
})
</script>

</body>
</html>
