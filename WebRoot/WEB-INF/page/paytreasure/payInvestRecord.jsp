<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags"  prefix="s"%>
<%@include file="/include/taglib.jsp" %>
<div class="tab_ec clearfix">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
		  <tr class="leve_titbj">
		    <td>转入/转出金额(元)</td>
		    <td>涨薪宝总金额(元)</td>
		    <td>操作类型</td>
		    <td>操作时间</td>
		  </tr>
		  
		  <s:if test="pageBean.page!=null && pageBean.page.size>0">
		  <s:set name="counts" value="#request.pageNum"/>
            <s:iterator value="pageBean.page"  var="bean" status="s">
			    <tr>
				    <s:if test="#bean.type!=2 && #bean.type!=null"><td class="green">+${bean.handamount }</td></s:if>
				    <s:else ><td class="red">-${bean.handamount }</td></s:else>
				    <s:if test="#bean.type!=2 && #bean.type!=null"><td>${bean.oldamount +bean.handamount }</td></s:if>
				    <s:else ><td>${bean.oldamount -bean.handamount }</td></s:else>
				    <td>
				    <s:if test="#bean.operationType==1">自动</s:if><s:elseif test="#bean.operationType==3">定时</s:elseif><s:else>手动</s:else><s:if test="#bean.type!=2 && #bean.type!=null">转入</s:if><s:else>转出</s:else>
				    </td>
				    <td>${bean.time}</td>
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
	<shove:page curPage="${pageBean.pageNum}"  showPageCount="6" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
	
    </p>
</div> 
<script>
function initListInfo(){
 	$.dispatchPost("payInvestRecord.mht",param,function(data){
        	$("#sysInfo").html(data);
     });
 }
</script>