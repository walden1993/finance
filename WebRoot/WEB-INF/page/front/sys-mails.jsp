<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%> 
<%@ taglib uri="/struts-tags"  prefix="s"%>
<%@include file="/include/taglib.jsp" %>
        <div class="biaoge mt20" id="biaoge">
        <input type="hidden" id="curPage" value="${pageBean.pageNum}">
          <table width="100%" border="0" class="table table-striped" cellspacing="0" cellpadding="0">
  <tr>
    <th><input type="checkbox" name="checkbox" id="checkbox1" onclick="checkAll_Sys(this)" /></th>
    <th>标记</th>
    <th>发件人</th>
    <th>标题</th>
    <th>发送时间</th>
  </tr>
  
  	<s:if test="pageBean.page!=null && pageBean.page.size>0">
		       <s:iterator value="pageBean.page" id="querySysMails" var="bean">
		         <s:if test="#bean.mailStatus=='未读'">
				    <tr>
				      <td><input type="checkbox" value="${bean.id}"  onclick="cacel1()"  name="sysMail" id="checkbox2" class="sys" /></td>
				      <td><b>${bean.mailStatus}</b><br /></td>
				      <td><b>${bean.sender}</b><br /></td>
				      <td><a href="javascript:void(0);" onclick="showSysMailInfo(${bean.id},1)" ><b>${bean.mailTitle}</b></a><br /></td>
				      <td ><b><s:date name="#bean.sendTime" format="yyyy-MM-dd HH:mm:ss" /></b></td>
				    </tr>
			    </s:if>
			    <s:else>
			        <tr>
				      <td><input type="checkbox" value="${bean.id}" name="sysMail" id="checkbox2" class="sys" /></td>
				      <td>${bean.mailStatus}<br /></td>
				      <td>${bean.sender}<br /></td>
				      <td><a href="javascript:void(0);" onclick="showSysMailInfo(${bean.id},0)">${bean.mailTitle}</a><br /></td>
				      <td><s:date name="#bean.sendTime" format="yyyy-MM-dd HH:mm:ss" /></td>
				    </tr>
			    </s:else>
			 </s:iterator>
		</s:if>
		<s:else>
		    <tr><td colspan="10" align="center">暂无信息</td></tr>
		</s:else>
          </table>
<div class="page">
		<p>
		<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>		
	    </p>
	</div> 
          </div>