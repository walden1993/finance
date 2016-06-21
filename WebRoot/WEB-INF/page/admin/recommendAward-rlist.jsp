<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>

<div style="padding: 15px 10px 0px 10px;">
奖励总计：${sumAward }
	<table id="gvNews" style="width: 100%; color: #333333;" cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
		<tbody>

			<tr>
				<th style="width: 10%;" scope="col"> 序号 </th>
				<th style="width: 10%;" scope="col"> 用户名</th>
				<th style="width: 10%;" scope="col"> 真实姓名</th>
				<th style="width: 10%;" scope="col"> 用户归属</th>
				<th style="width: 10%;" scope="col"> 一级有效会员</th>
				<th style="width: 10%;" scope="col"> 一级奖励</th>
				<th style="width: 10%;" scope="col"> 二级有效会员</th>
				<th style="width: 10%;" scope="col"> 二级奖励</th>
				<th style="width: 10%;" scope="col"> 奖励共计</th>
			</tr>
			<s:set name="counts" value="#request.pageNum"/>
			<s:iterator value="pageBean.page" var="bean" status="s">
				<tr class="gvItem">
					<td> <s:property value="#s.count+#counts" /> </td>
					<td align="center"> ${userName } </td>
					<td> ${realName } </td>
					<td> <s:if test="#bean.isEmployee==null">外部会员</s:if> <s:else>内部员工</s:else> </td>
					<td> ${ level1 } </td>
					<td> ${ fmon } </td>
					<td> ${ level2 } </td>
					<td> ${ smon } </td>
					<td> ${ sumAll } </td>
				</tr>
			</s:iterator>
			
			
		</tbody>
	</table>
</div>
<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>