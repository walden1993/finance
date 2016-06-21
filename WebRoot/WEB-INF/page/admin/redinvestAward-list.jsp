<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>

<div style="padding: 15px 10px 0px 10px;">
奖励总计：<fmt:formatNumber value="${ sumAward }" pattern="#,#00.00#"/>
	<table id="gvNews" style="width: 100%; color: #333333;" cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
		<tbody>
			<tr>
				<th style="width: 10%;" scope="col" class="f66"> 序号 </th>
				<th style="width: 10%;" scope="col" class="f66"> 用户名</th>
				<th style="width: 10%;" scope="col" class="f66"> 真实姓名</th>
				<th style="width: 10%;" scope="col" class="f66"> 用户归属</th>
				<th style="width: 10%;" scope="col" class="f66"> 一级投资金额&nbsp;<span title="点击可查看投资详情" style="color: red;">?</span></th>
				<th style="width: 10%;" scope="col" class="f66"> 一级奖励</th>
				<th style="width: 10%;" scope="col" class="f66"> 二级投资金额&nbsp;<span title="点击可查看投资详情" style="color: red;">?</span></th>
				<th style="width: 10%;" scope="col" class="f66"> 二级奖励</th>
				<th style="width: 10%;" scope="col" class="f66"> 奖励共计</th>
			</tr>
			<s:set name="counts" value="#request.pageNum"/>
			<s:iterator value="pageBean.page" var="bean" status="s">
				<tr class="gvItem">
					<td> <s:property value="#s.count+#counts" /> </td>
					<td align="center"> ${username } </td>
					<td> ${realName } </td>
					<td> <s:if test="#bean.isEmployee==null">外部会员</s:if> <s:else>内部员工</s:else> </td>
					<td class="btn" onclick="recommendAwardDetail(${recommendUserId},1)"> ${ investAmount1 } </td> 
					<td> <fmt:formatNumber value="${ mylevel1 }" pattern="#,#00.00#"/> </td>
					<td onclick="recommendAwardDetail(${recommendUserId},2)"> ${ investAmount2 } </td>
					<td> <fmt:formatNumber value="${ mylevel2 }" pattern="#,#0.00"/></td>
					<td> <fmt:formatNumber value="${ sumAll }" pattern="#,#0.00"/> </td>
				</tr>
			</s:iterator>
			
			
		</tbody>
	</table>
</div>
<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
