<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags"  prefix="s"%>
<%@include file="/include/taglib.jsp" %>
<div class="tab_ec clearfix">
    <table  style="width: 100%; color: #333333;" cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
		  <tr  class="gvHeader">
		    <td>转入/转出金额(元)</td>
		    <td>涨薪宝总金额(元)</td>
		    <td>操作时间</td>
		  </tr>
		  
		  <s:if test="pageBean.page!=null && pageBean.page.size>0">
		  <s:set name="counts" value="#request.pageNum"/>
            <s:iterator value="pageBean.page"  var="bean" status="s">
			    <tr class="gvItem">
				    <s:if test="#bean.type!=2 && #bean.type!=null"><td class="green">+${bean.handamount }</td></s:if>
				    <s:else ><td class="red">-${bean.handamount }</td></s:else>
				    <s:if test="#bean.type!=2 && #bean.type!=null"><td>${bean.oldamount +bean.handamount }</td></s:if>
				    <s:else ><td>${bean.oldamount -bean.handamount }</td></s:else>
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

$(function() {
		$("#excel")
				.click(
						function() {
							 $("#excel").attr("disabled", true);
							window.location.href = "payInvestRecordExcel.mht?id="
									+ GetRequest("id")
									+ "&paramMap.publishTimeEnd="
									+ $("#publishTimeEnd").val()
									+ "&paramMap.type="
									+ $("#type").val()
									+ "&paramMap.publishTimeStart="
                                    + $("#publishTimeStart").val();
							setTimeout("test()", 4000);
						});
	});
	function test() {
		$("#excel").attr("disabled", false);
	}
</script>