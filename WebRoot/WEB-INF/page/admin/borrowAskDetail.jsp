<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<link rel="stylesheet" href="../script/bootstrap/css/bootstrap.css">
<script type="text/javascript" src="../css/popom.js"></script>
<script type="text/javascript" src="../script/jquery-2.0.js"></script>
<script type="text/javascript">
	
</script>
<body>
	<div class="span6 pagination-centered" style="margin-top: 20px;">
		<div class="span6">
			<table class=" table  table-bordered">
				<tr>
					<td>联系人姓名：</td>
					<td>${map.contact_name }</td>
					<td>借款类型：</td>
					<td>${map.borrowWay }</td>
				</tr>
				<tr>
					<td>联系人手机：</td>
					<td>${map.contact_phone }</td>
					<td>是否有抵押物：</td>
					<td><s:if test="#request.map.hasMortgage==0">是</s:if>
						<s:else>否</s:else>
					</td>
				</tr>
				<tr>
					<td>性别：</td>
					<td><s:if test="#request.map.sex==0">男</s:if>
						<s:else>女</s:else>
					</td>
					<td>借款金额：</td>
					<td>${map.borrowAmount }元</td>
				</tr>
				<tr>
					<td>年龄：</td>
					<td>${ map.age}岁</td>
					<td>借款期限：</td>
					<td>${map.borrowLine }个月</td>
				</tr>
				<tr>
					<td>借款方类型：</td>
					<td><s:if test="#request.map.borrowerType==1">个人</s:if>
						<s:else>企业</s:else>
					</td>
					<td>分配到：</td>
					<td>${map.userName }</td>
				</tr>
				<tr>
					<td>是否分配：</td>
					<td><s:if test="#request.map.hasDistribution==0">是</s:if>
						<s:else>否</s:else>
					</td>
					<td>分配时间：</td>
					<td>${map.distributionDate }</td>
				</tr>
				<tr>
					<td>借款描述：</td>
					<td colspan="3"  width="500px">${map.describe }</td>
				</tr>
			</table>
		</div>
	</div>
</body>
