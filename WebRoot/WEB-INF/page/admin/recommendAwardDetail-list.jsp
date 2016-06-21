<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>

<html lang="en">
<head>
<title>推荐投资明细</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div style="padding: 15px 10px 0px 10px;">
	<table id="gvNews" style="width: 100%; color: #333333;" cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
		<tbody>

			<tr>
				<th style="width: 10%;" scope="col"> 序号 </th>
				<th style="width: 10%;" scope="col"> 用户名</th>
				<th style="width: 10%;" scope="col"> 真实姓名</th>
				<th style="width: 10%;" scope="col"> 投标金额</th>
				<th style="width: 10%;" scope="col"> 交易对方</th>
				<th style="width: 10%;" scope="col"> 借款标题</th>
				<th style="width: 10%;" scope="col"> 借款类型</th>
				<th style="width: 10%;" scope="col"> 借款期限</th>
				<th style="width: 10%;" scope="col"> 投标时间</th>
			</tr>
			<s:iterator value="pageBean.page" var="bean" status="s">
				<tr class="gvItem">
					<td>${(pageBean.pageNum-1)*pageBean.pageSize+s.index+1}</td>
					<td align="center"> ${username } </td>
					<td> ${realName } </td>
					<td> ${investAmount}</td>
					<td> ${ publisher } </td>
					<td> ${ borrowTitle } </td>
					<td> ${ borrowWay } </td>
					<td> ${ deadline }${ isDayThe } </td>
					<td> ${ investTime } </td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
</div>
<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../script/jquery.dispatch.js"></script>
</body>
</html>















