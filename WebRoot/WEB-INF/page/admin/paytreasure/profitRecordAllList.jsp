<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags"  prefix="s"%>
<%@include file="/include/taglib.jsp" %>
<div class="tab_ec clearfix">
       <table  style="width: 100%; color: #333333;" cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
		  <tr  class="gvHeader">
		  <td>用户名</td>
		  <td>真实姓名</td>
		  	<td>收益金额(元)</td>
		    <td>收益时间</td>
		  </tr>
		  
		  <s:if test="pageBean.page!=null && pageBean.page.size>0">
		  <s:set name="counts" value="#request.pageNum"/>
            <s:iterator value="pageBean.page"  var="bean" status="s">
			    <tr class="gvItem">
			    <td>${bean.username }</td>
			    <td>${bean.realName }</td>
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

$(function() {
		$("#excel")
				.click(
						function() {
							 $("#excel").attr("disabled", true);
							 param["pageBean.pageNum"] = 1;
							window.location.href = "profitRecordAllExcel.mht?id="
									+ GetRequest("id")
									+ "&paramMap.publishTimeEnd="
									+ $("#publishTimeEnd").val()
										+ "&paramMap.username="
									+ $("#userName").val()
										+ "&paramMap.realName="
									+ $("#realName").val()
									+ "&paramMap.publishTimeStart="
                                    + $("#publishTimeStart").val();
							setTimeout("test()", 4000);
						});
	});
	function test() {
		$("#excel").attr("disabled", false);
	}
</script>