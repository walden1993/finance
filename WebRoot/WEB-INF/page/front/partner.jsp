<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<c:set value="合作伙伴" var="title"  scope="request" />
<jsp:include page="/include/head.jsp"></jsp:include>
<script type="text/javascript">var menuIndex = 5;</script>
</head>

<body>
<!-- 引用头部公共部分 -->
<jsp:include page="/include/top.jsp"></jsp:include>

<!--内容区域-->
<div class="wrap">
<!--介绍-->
<div class="w950">
   <ol class="breadcrumb mb0 bg-none">
		  <li><a href="/">首页 </a></li>
		  <li>关于我们</li>
		  <li class="active">合作伙伴</li>
	  </ol>
  	<jsp:include page="/WEB-INF/page/helpcenter/aboutme_left.jsp"></jsp:include>
<div class="r_main fr w750 mb bg-white mb20"> 
  <div class="pub_tb bottom"><h3>合作机构</h3></div><div class="clearfix"></div>
  <div class="n_about_partner clearfix m20">
    <s:if test="pageBean.page==null || pageBean.page.size==0">
   暂无数据
    </s:if>
    <s:else>
            <s:iterator value="pageBean.page.{?#this.type==5}"  var="bean">
                <a href="${bean.companyURL}" rel="nofollow"><img src="${bean.companyImg }" width="125" height="100" title="${bean.companyName }" class="mb20"/></a> &nbsp;&nbsp;
                
            </s:iterator>
    </s:else>
   </div>
   
   <!--友情链接-->
<div class="n_about_mission clearfix m20" id="showcontent">
  <div class="pb20 fang"><h2>友情链接</h2></div>
  <s:if test="pageBean.page==null || pageBean.page.size==0">
   暂无数据
    </s:if>
    <s:else> <ol class="n_about_link">
            <s:iterator value="pageBean.page.{?#this.type==1}"  var="bean">
                 <li><img src="${bean.companyImg}" width="185" height="81" /><p><a href="${bean.companyURL}" target="_blank">${bean.companyName }</a></p></li>
            </s:iterator>
            </ol>
    </s:else>
</div>
  </div>
</div>


</div>
<!--footer-->
<jsp:include page="/include/footer.jsp"></jsp:include>
<script type="text/javascript">
$(function(){
	$(".html_left a:eq(2)").addClass("hover");
})
</script>
</html>
