<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!-- 引用头部公共部分 -->


<html>
    <head>
    <script type="text/javascript" src="script/jquery-1.7.1.min.js"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <title>三好资本小文章列表</title>
        <style type="text/css">
            body{ margin:0 auto; font-size:16px;}
            .data_list td{ }
        </style>

        <script type="text/javascript">
        /* 当鼠标移到表格上是，当前一行背景变色 */
      $(document).ready(function(){
            $(".data_list tr td").mouseover(function(){
                $(this).parent().find("td").css("background-color","#d5f4fe");
            });
      })
      /* 当鼠标在表格上移动时，离开的那一行背景恢复 */
      $(document).ready(function(){ 
            $(".data_list tr td").mouseout(function(){
                var bgc = $(this).parent().attr("bg");
                $(this).parent().find("td").css("background-color",bgc);
            });
      })
      
      $(document).ready(function(){ 
            var color="#ffeab3"
            $(".data_list tr:odd td").css("background-color",color);  //改变偶数行背景色
            /* 把背景色保存到属性中 */
            $(".data_list tr:odd").attr("bg",color);
            $(".data_list tr:even").attr("bg","#fff");
      })
      </script>
    </head>
    <body>
<a href="index.mht">首页 </a>
<br/>
<table class="data_list">
<tr>
<th width=10></th>
<th>作者</th>
<th>标题</th>
</tr>
<s:iterator value="pageBean.page"  var="bean" status="s">
   <tr>
    <td> </td>
    <td>${bean.source }</td>
    <td><a href="frontArticleId.mht?id=${bean.id}" target="_blank">${bean.title}</a></td>
   </tr>
</s:iterator>
		
</table>
<div class="page">
	<p>
	<shove:page curPage="${pageBean.pageNum}" showPageCount="6" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
	
    </p>
</div> 
</body>
</html>