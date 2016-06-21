<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<div style="padding: 10px 10px 0px 10px;">
		<table id="gvNews" style="width: 100%; color: #333333;"
			cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
			<tbody>
				<tr class=gvHeader>
					<th style="width: 50px;" scope="col">
						序号
					</th>
					<th style="width: 150px;" scope="col">
						操作金额
					</th>
					<th style="width: 100px;" scope="col">
						真实姓名
					</th>
					<th style="width: 150px;" scope="col">
						企业全称
					</th>
					<th style="width: 100px;" scope="col">
						用户类型
					</th>
					<th style="width: 100px;" scope="col">
						操作类型
					</th>
					<th style="width: 190px;" scope="col">
						详细来源
					</th>
					<th style="width: 140px;" scope="col">
						操作时间
					</th>
					<th style="width: 140px;" scope="col">
						操作
					</th>
				</tr>
				<s:if test="pageBean.page==null || pageBean.page.size==0">
					<tr align="center" class="gvItem">
						<td colspan="9">暂无数据</td>
					</tr>
				</s:if>
				<s:else>
				<s:set name="counts" value="#request.pageNum"/>
				<s:iterator value="pageBean.page" var="bean" status="s">
					<tr class="gvItem">
						<td align="center">
							<s:property value="#s.count+#counts"/>
						</td>
						<td align="center">
							${bean.amount}
						</td>
						<td align="center">
							<s:if test="#bean.realName!=null && #bean.realName!=''">${bean.realName}</s:if>
							<s:else>--</s:else>
						</td>
						<td align="center">
							<s:if test="#bean.orgName!=null && #bean.orgName!=''">${bean.orgName}</s:if>
							<s:else>--</s:else>
						</td>
						<td align="center">
							<s:if test="#bean.userType==1">个人</s:if>
							<s:if test="#bean.userType==2">企业</s:if>
							<s:if test="#bean.userType==null">--</s:if>
						</td>
						<td align="center">
							${bean.riskType}
						</td>
						<td align="center">
							${bean.resource}
						</td>
						<td align="center">
							${bean.riskDate}
						</td>
						<td align="center">
							<a href="queryRiskDetail.mht?id=${bean.id}">查看详情</a>
						</td>
					</tr>
				</s:iterator>
				</s:else>
			</tbody>
		</table>
</div>
<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>