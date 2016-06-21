<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>网贷平台-充值、提现成功</title>
 <jsp:include page="/include/head.jsp"></jsp:include>
<link href="css/common.css"  rel="stylesheet" type="text/css" />
<link href="css/inside.css"  rel="stylesheet" type="text/css" />
<script src="script/jquery-2.0.js" type="text/javascript"></script>
<script src="script/common.js" type="text/javascript"></script>
</head>

<body>
	
    	<!--顶部状态栏 开始-->
    		<jsp:include page="/include/top.jsp"></jsp:include>
        <!--顶部主导航 结束-->
         <!--提示消息主要 开始-->
        <div class="wrap">
            <div class="main" style="text-align: center;width: 100%;margin: 30px;">
            	<span style="font-size: 26px;color:#eb6100; " >
                 	${title }
                </span>
                  <br /><br />
                 <a href="<%=application.getAttribute("basePath")%>">返回首页</a>
               
            </div>
          </div>
        <!--底部快捷导航 开始-->
         <jsp:include page="/include/footer.jsp"></jsp:include>  
</body>
</html>
