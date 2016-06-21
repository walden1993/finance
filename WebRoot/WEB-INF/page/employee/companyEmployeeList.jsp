<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<div class="box">
        <div class="boxmain2">
         <div class="tab_ec clearfix" >
		             <table width="100%" border="0" cellspacing="0" cellpadding="0">
		  <tr class="leve_titbj">
		    <td>用户名</td>
		    <td>手机号</td>
		    <td>真实姓名</td>
		    <%-- <td>薪资</td> --%>
		    <td>注册时间</td>
		    <td>是否离职</td>
		    <td>操作</td>
		    </tr>
		  	<s:if test="pageBean.page==null || pageBean.page.size==0">
			   <tr align="center">
				  <td colspan="10">暂无数据</td>
			   </tr>
			   
			</s:if>
			<s:else>
			<s:iterator value="pageBean.page" var="bean">
			 <tr>
				<td align="center">${ bean.username}</td>
		    	<td align="center">${bean.mobilePhone} </td>
		    	<td align="center">${bean.realName}</td>
		    	<%-- <td align="center" >${ bean.wage}</td> --%>
		    	<td align="center">${bean.createTime }</td>
		    	<td align="center">${bean.statuwork }</td>
		    	<td align="center"> <a href="homeBorrowRecycleList.mht?id=${bean.id}" target="_blank">查看详情</a></td>
		       </tr>
		     </s:iterator>
			</s:else>
		</table>
		<div  class="page" >
	     <shove:page  curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="number" totalCount="${pageBean.totalNum}">
	     </shove:page> 
 		 </div>
          </div>
          </div>
    </div>