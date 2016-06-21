<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
	<div>
		<table id="help" style="width: 100%; color: #333333;"
			cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
			<tbody>
				<tr class=gvHeader>
					<th scope="col">
						序号
					</th>
					<th scope="col">
						用户名
					</th>
					<th scope="col">
						真实姓名
					</th>
					<th scope="col">
						企业全称
					</th>
					<th scope="col">
						用户类型
					</th>
					<th scope="col">
						手机号码
					</th>									
					<th scope="col">
						投标总额
					</th>
						<th scope="col">
						申请时间
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
							<td>
								<s:property value="#s.count+#counts"/>
							</td>
							<td>
								${username}
							</td>
							<td>
								<s:if test="#bean.realName!=null && #bean.realName!=''">${realName}</s:if>
								<s:else>--</s:else>
							</td>
							<td>
								<s:if test="#bean.orgName!=null && #bean.orgName!=''">${orgName}</s:if>
								<s:else>--</s:else>
							</td>
							<td>
								<s:if test="#bean.userType==1">个人</s:if>
								<s:if test="#bean.userType==2">企业</s:if>
							</td>	
							<td>
								<s:if test="#bean.cellPhone!=null && #bean.cellPhone!=''">${cellPhone}</s:if>
								<s:else>--</s:else>
							</td>		
							<td>
								<s:if test="#bean.amountall!=null && #bean.amountall!=''">${amountall}</s:if>
								<s:else>--</s:else>
							</td>						
							<td>
								${requsetTime}
							</td>
						</tr>
					</s:iterator>
				</s:else>
			</tbody>
		</table>
	</div>
	
<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
	