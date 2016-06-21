<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
	<head>
		<title>管理首页</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link href="../css/admin/admin_css.css" rel="stylesheet"
			type="text/css" />
		<link rel="stylesheet" type="text/css"
			href="../common/date/calendar.css" />
		<script type="text/javascript" src="../common/date/calendar.js"></script>
		<script type="text/javascript" src="../script/jquery-2.0.js"></script>
		<script type="text/javascript" src="../script/jquery.dispatch.js"></script>
	</head>
</html>
<div>
	<table id="gvNews" style="width: 100%; color: #333333;" cellspacing="1"
		cellpadding="3" bgcolor="#dee7ef" border="0">
		<tbody>
			<tr class=gvHeader>
				<th scope="col" >
					序号
				</th>
				<th scope="col" >
					用户账号
				</th>
				<th scope="col" >
					用户类型
				</th>
				<th scope="col" >
					真实姓名
				</th>
				<th scope="col" >
					企业全称
				</th>
				<th scope="col" >
					注册时间
				</th>
				<th scope="col" >
					基本信息
				</th>
				<th scope="col">
					审核人
				</th>
				<th scope="col" >
					操作
				</th>
			</tr>
			<s:if test="pageBean.page==null || pageBean.page.size==0">
				<tr align="center" class="gvItem">
					<td colspan="10">
						暂无数据
					</td>
				</tr>
			</s:if>
			<s:else>
				<s:set name="counts" value="#request.pageNum" />
				<s:iterator value="pageBean.page" var="bean" status="s">
					<tr class="gvItem">
						<td>
							<s:property value="#s.count+#counts" />
						</td>
						<td >
							${username}
						</td>
						<td>
							<s:if test="#bean.userType==1">个人</s:if>
							<s:if test="#bean.userType==2">企业</s:if>
						</td>
						<td>
							<s:if test="#bean.realName!=null">
							${realName }
							 </s:if>
							<s:else>
							  --
							</s:else>
						</td>
						<td>
							<s:if test="#bean.orgName!=null">
							${orgName }
							 </s:if>
							<s:else>
							  --
							</s:else>
						</td>
						<td>
							<s:date name="#bean.createTime" format="yyyy-MM-dd" />
							<!-- 
						  <s:if test="#bean.auditStatus==1">
							等待审核
							</s:if>
							<s:elseif test="#bean.status==3">
							审通过
							</s:elseif>
							<s:else>
							审核通过
							</s:else>
						
							<s:date name="#bean.addDate" format="yyyy-MM-dd HH:mm:ss" />
							 -->
						</td>
						<td>
							<!-- 基本信息状态 -->
							<s:if test="#bean.userType==2">
								<s:if test="#bean.orgAuditStatus==1">基本信息完整<a
									style="color: gray;">(待审核)</a>
								</s:if>
								<s:elseif test="#bean.orgAuditStatus==2">基本信息完整<a
										style="color: red;">(失败)</a>
								</s:elseif>
								<s:elseif test="#bean.orgAuditStatus==3">基本信息完整<a
										style="color: blue;">(成功)</a>
								</s:elseif>
								<s:else>未填写</s:else>
							</s:if>
								<s:if test="#bean.userType==1">
								<s:if test="#bean.personauditStatus==1">基本信息完整<a
										style="color: gray;">(待审核)</a>
								</s:if>
								<s:elseif test="#bean.personauditStatus==2">基本信息完整<a
										style="color: red;">(失败)</a>
								</s:elseif>
								<s:elseif test="#bean.personauditStatus==3">基本信息完整<a
										style="color: blue;">(成功)</a>
								</s:elseif>
								<s:else>未填写</s:else>
							</s:if>
						</td>
						<td>
							<s:if test='#bean.service!=null'>
							${service }
							</s:if>
							<s:else>
							未分配
							</s:else>
						</td>
						<td>
							<a href="adminBase.mht?uid=${id}&userType=${userType}">查看</a>
						</td>
					</tr>
				</s:iterator>
			</s:else>
		</tbody>
	</table>
</div>
<shove:page curPage="${pageBean.pageNum}" showPageCount="10"
	pageSize="${pageBean.pageSize }" theme="jsNumber"
	totalCount="${pageBean.totalNum}"></shove:page>