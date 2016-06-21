<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<s:if test="pageBean.page!=null || pageBean.page.size>0">
<p class="nunber">一共搜索出<font>${pageBean.totalNum}</font>个结果</p>
<s:iterator  value="pageBean.page"  var="item" status="index">
	<span class="title_list"><font>${(pageBean.pageNum-1)*pageSize+index.index+1}.</font>${question}？</span>
      			 <div>
              	${anwer}
              </div>
</s:iterator>
<div class="text-center">
      	<s:if test="pageBean.page!=null || pageBean.page.size>0">
     <div class="page">
     <shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum }"></shove:page>
     </div>
     </s:if>
     </div>
</s:if>
<s:else>
<p class="nunber">一共搜索出<font>0</font>个结果</p>
</s:else>
				
