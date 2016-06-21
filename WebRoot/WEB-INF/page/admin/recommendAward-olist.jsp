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
					<td> ${ sumAll } </td>
					<td> ${ sumAll } </td>
				</tr>
			</s:iterator>
			
			
		</tbody>
	</table>
</div>
<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
<script type="text/javascript">
alert("1）平台会员注册的角色分为：会员A，会员B（A推荐）；\n"+
		"2）奖励只支持一级奖励：A推荐B成功注册并投标，奖励A  2元/个，奖励上不封顶；\n"+
		"3）推荐注册的会员必须是有效会员，有效会员是指成功注册并投标(不包含体验标)的会员；\n"+
		"4）当月结算，满10元即结清，不满10元在推荐人平台账户自动累加，     不清零，次月1~5号充值到推荐人在三好资本平台的账户中；\n"+
		"5）奖励是系统自动根据注册时填写的推荐人统计，若未填写，该奖励不予计算。\n"+
		"\n"+
		"只针对外部会员！");
</script>