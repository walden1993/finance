<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>

            <div class="tab_ec clearfix">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
		  <tr class="leve_titbj">
		  	<td>收益金额(元)</td>
		    <td>收益时间</td>
		  </tr>
		  
		  <s:if test="pageBean.page!=null && pageBean.page.size>0">
		  <s:set name="counts" value="#request.pageNum"/>
            <s:iterator value="pageBean.page"  var="bean" status="s">
			    <tr>
			    	<td>${bean.profitamount }</td>
				    <td>${bean.time }</td>
		  		</tr>
			  </s:iterator>
		  </s:if>
		  <s:else>
		    <tr><td colspan="11" align="center">暂无信息</td></tr>
		  </s:else>
	</table>
</div>	
	<div class="page">
	<p>
	<shove:page curPage="${pageBean.pageNum}" showPageCount="6" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
	
    </p>
</div> 
<script>
function initListInfo(){
 	$.dispatchPost("profitRecord.mht",param,function(data){
         		$("#info").html(data);
      	});
 }
</script>

