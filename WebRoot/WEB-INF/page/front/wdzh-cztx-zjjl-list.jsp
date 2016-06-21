<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags"  prefix="s"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<div class="tab_ec clearfix">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
		  <tr class="leve_titbj">
		    <td>时间</td>
		    <td>类型</td>
		    <td>冻结/解冻金额</td>
		    <td>收入/支出金额</td>
		    <td>可用余额</td>
		    <td width="20%">备注</td>
		  </tr>
		  
		  <s:if test="pageBean.page!=null && pageBean.page.size>0">
		  <s:set name="counts" value="#request.pageNum"/>
            <s:iterator value="pageBean.page"  var="bean" status="s">
			    <tr>
				    <td>${bean.recordTime }</td>
				   	<td>${bean.fundMode }</td>
				    <td>${bean.freezeSum }</td>
				    
				    <s:if test="#bean.income!=0 && #bean.income!=null"><td class="green">+${bean.income }</td></s:if>
				    <s:else ><td class="red">-${bean.spending }</td></s:else>
				    
				    <td>${bean.usableSum }</td>
				    <td>${bean.remarks }</td>
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