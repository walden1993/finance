<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
  <head>
    <jsp:include page="/include/head.jsp"></jsp:include>
</head>
<body>
<!-- 引用头部公共部分 -->
<jsp:include page="/include/top.jsp"></jsp:include>
<div class="main" style="height: 300px;">
<div class="error01">
<p class="f18"> 三好资本平台友情提示：</p>
<img src="images/index11_35.jpg" width="448" height="125" />
<br/>

<p class="f16 mt20">您可以返回<a href="<%=application.getAttribute("basePath")%>" style="color:red;text-decoration: underline;">三好资本</a>首页，或联系客服:${sitemap.servicePhone }</p>
</div>

</div>
<!-- 引用底部公共部分 -->     
<jsp:include page="/include/footer.jsp"></jsp:include>
</body>
</html>