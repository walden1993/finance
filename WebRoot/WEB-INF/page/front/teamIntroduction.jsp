<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<c:set value="团队介绍" var="title"  scope="request" />
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
		  <li class="active">团队介绍</li>
	  </ol>
  	<jsp:include page="/WEB-INF/page/helpcenter/aboutme_left.jsp"></jsp:include>
    <div class="r_main fr w750 mb mb20">
    	<div class="pub_tb bottom"><h3>团队介绍</h3></div><div class="clearfix"></div>
    	<div class="about_context bg-white">
    		<div class="item p30">
    			<img alt="精英团队" class="fl" src="images/about_pic_1.jpg" height="150" width="120">
    			<div class="intrudution w070 fl">
    				<dl>
    					<dt>徐耀庆 总裁</dt>
    					<dd>武汉大学工商管理硕士<br/> 国际注册高级职业经理人 中国人民大学集团管控总裁班特聘教授<br/>
历任河海大学讲师、广东皇威集团副总、深圳市沃尔核材（股票代码：002130）第一任执行副总裁、多家企业集团的高级顾问，北大纵横合伙人。<br/>
先后为广东皇威集团、康佳集团、广东博华集团等多家大型国有、外资、民营企业提供管理咨询服务。</dd>
    				</dl>
				</div><div class="clearfix"></div>
    		</div>
    		
    		<div class="item p30">
    			<img alt="精英团队" class="fl" src="images/about_pic_5.jpg" height="150" width="120">
    			<div class="intrudution w070 fl">
    				<dl>
    					<dt>喻天柱 财务总监</dt>
    					<dd>重庆理工大学   财务管理专业  硕士 中国注册会计师<br/>
具有证券、基金、期货等从业资格；先后在中审亚太会计师事务所、招商期货有限公司等财务负责人职位；<br/>
主要服务的企业有安信证券、中投证券、华林证券、东莞证券、招商期货、三正集团、广东移动公司等，在金融财务、风控管理等方面有着丰富的实操经验。</dd>
    				</dl>
				</div><div class="clearfix"></div>
    		</div>
    	</div>
    </div>
  </div>
</div>
<!--footer-->
<jsp:include page="/include/footer.jsp"></jsp:include>
<script type="text/javascript">
$(function(){
	$(".html_left a:eq(3)").addClass("hover");
})
</script>
</body>
</html>
